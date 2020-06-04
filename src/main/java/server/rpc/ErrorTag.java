package server.rpc;

public enum ErrorTag {

    IN_USE("in-use"),
    INVALID_VALUE("invalid-value"),
    TOO_BIG("too-big"),
    MISSING_ATTRIBUTE("missing-attribute"),
    BAD_ATTRIBUTE("bad-attribute"),
    UNKNOWN_ATTRIBUTE("unknown-attribute"),
    MISSING_ELEMENT("missing-element"),
    BAD_ELEMENT("bad-element"),
    UNKNOWN_ELEMENT("unknown-element"),
    UNKNOWN_NAMESPACE("unkown-namespace"),
    ACCESS_DENIED("access-denied"),
    LOCK_DENIED("lock-denied"),
    RESOURCE_DENIED("resource-denied"),
    ROLLBACK_FAILED("rollback-failed"),
    DATA_EXISTS("data-exists"),
    DATA_MISSING("data-missing"),
    OPERATION_NOT_SUPPORTED("operation-not-supported"),
    OPERATION_FAILED("operation-failed"),
    PARTIAL_OPERATION("partial-operation"),
    MALFORMED_MESSAGE("malformed-message"); // introduced in base:1.1

    String tag;

    ErrorTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return tag;
    }

    /**
     * @param value
     * @return ErrorTag constant matching given value
     * @throws IllegalArgumentException if given value does not match any allowed value.
     */
    public static ErrorTag getErrorTagByValue(String value) {
        for (ErrorTag errorTag : values()) {
            if (errorTag.getTag().equals(value)) {
                return errorTag;
            }
        }
        throw new IllegalArgumentException("No enum const class " + ErrorTag.class.getName() + "." + value);
    }

}