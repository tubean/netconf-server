package server;

import server.rpc.Query;
import server.rpc.Reply;

public class Behaviour {

    private Query query;
    private Reply reply;

    private boolean consume;

    /**
     * Creates a Behaviour that NOT consumes itself
     *
     * @param query
     * @param reply
     */
    public Behaviour(Query query, Reply reply) {
        this.query = query;
        this.reply = reply;
        this.consume = false;
    }

    /**
     * Creates a Behaviour
     *
     * @param query
     * @param reply
     * @param consume if true, the behaviour will be consumed when Query matched; otherwise not
     */
    public Behaviour(Query query, Reply reply, boolean consume) {
        this.query = query;
        this.reply = reply;
        this.consume = consume;
    }

    public Query getQuery() {
        return query;
    }

    public Reply getReply() {
        return reply;
    }

    public boolean isConsume() {
        return consume;
    }

}