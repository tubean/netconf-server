package server;


import java.util.List;

public interface BehaviourContainer {

    /**
     * Allows defining a Netconf server behaviour
     *
     * @param query Netconf query
     * @param reply Expected Netconf reply
     */
    public void defineBehaviour(Behaviour behaviour);

    /**
     * @return the behaviors list
     */
    public List<Behaviour> getBehaviours();
}