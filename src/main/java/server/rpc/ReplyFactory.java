package server.rpc;

import java.util.HashMap;

/**
 * Factory class to create base Netconf replies
 *
 * @author Julio Carlos Barrera
 */
public class ReplyFactory {

    public static Reply newOk(Query query, HashMap<String, String> attributes) {
        Reply reply = new Reply();
        reply.setMessageId(query.getMessageId());
        reply.setAttributes(attributes);
        reply.setOk(true);
        return reply;
    }

    public static Reply newGetConfigReply(Query query, HashMap<String, String> attributes, String configuration) {
        Reply reply = new Reply();
        reply.setMessageId(query.getMessageId());
        reply.setAttributes(attributes);

        // configuration
        reply.setContainName("data");
        reply.setContain(configuration);

        return reply;
    }
}