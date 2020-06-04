package server.messageQueue;

import java.util.LinkedHashMap;
import java.util.Vector;
import java.util.concurrent.*;

import com.google.common.util.concurrent.SimpleTimeLimiter;
import com.google.common.util.concurrent.UncheckedTimeoutException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import server.rpc.RPCElement;

public class MessageQueue {

    static Log log = LogFactory.getLog(MessageQueue.class);
    Vector<MessageQueueListener> listeners;
    LinkedHashMap<String, RPCElement> queue;

    public MessageQueue() {
        listeners = new Vector<>();
        queue = new LinkedHashMap<>();
    }

    /**
     * put() element in (internal) queue, but triggers event to listeners before returning.
     * <p>
     * This methods is thread safe.
     */
    public RPCElement put(String key, RPCElement value) {

        RPCElement element;

        synchronized (queue) {
            log.debug("Received new message (" + value.getMessageId() + ")(waking up waiting threads)");
            element = queue.put(key, value);
            queue.notifyAll();
        }

        log.debug("Notify listeners");
        for (MessageQueueListener listener : listeners)
            listener.receiveRPCElement(element);

        return element;
    }

    /**
     * Commodity method. Same as put(k,v) but takes the key by calling getMessageId from the value.
     *
     * @param value
     * @return
     */
    public RPCElement put(RPCElement value) {
        return put(value.getMessageId(), value);
    }

    public void addListener(MessageQueueListener listener) {
        log.debug("New listener");
        listeners.add(listener);
    }

    public RPCElement consume() {
        synchronized (queue) {
            RPCElement element = null;
            if (queue.keySet().iterator().hasNext()) {
                element = queue.remove(queue.keySet().iterator().next()); // get first (older)
            }
            if (element != null)
                log.debug("Consuming message");
            return element;
        }
    }

    public RPCElement consumeById(String messageId) {
        synchronized (queue) {
            RPCElement element = queue.remove(messageId); // get first (older)
            if (element != null)
                log.debug("Consuming message with id " + messageId);
            return element;
        }
    }

    // TODO: Create a version that takes a timeout.
    public RPCElement blockingConsume() {

        RPCElement element;

        synchronized (queue) {
            while ((element = consume()) == null) {
                try {
                    log.debug("Waiting...");
                    queue.wait();
                } catch (InterruptedException e) {
                    log.warn("Interrupted exception");
                }
            }
        }
        return element;
    }

    public RPCElement blockingConsumeById(String messageId) throws Exception {
        return blockingConsumeById(messageId, 0);
    }

    /**
     * Wait for a new message with the id <code>messageId</code> to arrive in the queue.
     *
     * @param messageId a string identifying the message to consume.
     * @param timeout   a long indicating the length of the timeout in milliseconds. If zero or less, no timeout.
     * @return
     * @throws Exception an UncheckedTimeoutException if there is no message with <code>messageId</code> after waiting for the specified timeout.
     */
    public RPCElement blockingConsumeById(String messageId, long timeout) throws Exception {

        final String messageIdFinal = messageId;
        Callable<RPCElement> consumeCaller = () -> {
            RPCElement element;
            synchronized (queue) {
                while ((element = consumeById(messageIdFinal)) == null) {
                    try {
                        log.debug("Waiting (" + messageIdFinal + ")...");
                        queue.wait();
                    } catch (InterruptedException e) {
                        // Do nothing. It's probably a timeout.
                    }
                }
            }
            return element;
        };

        if (timeout <= 0) {
            return consumeCaller.call();
        }

        SimpleTimeLimiter timeLimiter = SimpleTimeLimiter.create(Executors.newSingleThreadExecutor());
        try {
            return timeLimiter.callWithTimeout(consumeCaller, timeout, TimeUnit.MILLISECONDS);
        } catch (UncheckedTimeoutException e) {
            log.debug("BlockingConsumeById(messageId=" + messageId + ") failed due to timeout.", e);
            throw e;
        } catch (Exception e) {
            log.debug("BlockingConsumeById(messageId=" + messageId + ") failed.", e);
            throw e;
        }
    }
}