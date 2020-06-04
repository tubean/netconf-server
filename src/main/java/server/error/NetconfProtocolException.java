package server.error;

public class NetconfProtocolException extends NetconfException {
    public NetconfProtocolException(String description) {
        super(description);
    }
}