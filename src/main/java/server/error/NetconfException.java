package server.error;

public class NetconfException extends Exception {

    public NetconfException(String description) {
        super(description);
    }

    public NetconfException(String description, Exception cause) {
        super(description, cause);
    }
}