package server.rpc;

public class Error {

    /**
     * Defines the conceptual layer that the error occurred.
     */
    private ErrorType type;

    /**
     * Contains a string identifying the error condition.
     */
    private ErrorTag tag;

    /**
     * Contains a string identifying the error severity, as determined by the
     * device.
     */
    private ErrorSeverity severity;

    /**
     * Contains a string identifying the data-model-specific or
     * implementation-specific error condition, if one exists. This element will
     * not be present if no appropriate application error tag can be associated
     * with a particular error condition.
     */
    private String appTag;

    /**
     * Contains the absolute XPath expression identifying the element path to
     * the node that is associated with the error being reported in a particular
     * rpc-error element. This element will not be present if no appropriate
     * payload element can be associated with a particular error condition, or
     * if the 'bad-element' QString returned in the 'error-info' container is
     * sufficient to identify the node associated with the error.
     */
    private String path;

    /**
     * Contains a string suitable for human display that describes the error
     * condition. This element will not be present if no appropriate message is
     * provided for a particular error condition.
     */
    private String message;

    /**
     * Contains protocol- or data-model-specific error content.
     */
    private String info;

    public boolean isType(ErrorType type) {
        return (this.type == type);
    }

    public ErrorType getType() {
        return type;
    }

    public void setType(ErrorType type) {
        this.type = type;
    }

    public ErrorTag getTag() {
        return tag;
    }

    public void setTag(ErrorTag tag) {
        this.tag = tag;
    }

    public boolean isServerity(ErrorSeverity severity) {
        return (this.severity == severity);
    }

    public ErrorSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(ErrorSeverity severity) {
        this.severity = severity;
    }

    public String getAppTag() {
        return appTag;
    }

    public void setAppTag(String appTag) {
        this.appTag = appTag;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String toString() {
        String serror = "Netconf RPC Error:";
        serror += "\n Type: " + this.getType();
        serror += "\n Tag: " + this.getTag();
        serror += "\n Severity: " + this.getSeverity();
        serror += "\n Application Tag: " + this.getAppTag();
        serror += "\n Path: " + this.getPath();
        serror += "\n Description: " + this.getMessage();
        serror += "\n Specific Info: " + this.getInfo() + "\n";
        return serror;
    }

}