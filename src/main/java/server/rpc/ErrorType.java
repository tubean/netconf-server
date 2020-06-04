package server.rpc;

public enum ErrorType {
	TRANSPORT("transport"),
	RPC("rpc"),
	PROTOCOL("protocol"),
	APPLICATION("application"),
	OTHER("other");

	private String	value;
	String			other;

	ErrorType(String value) {
		this.value = value;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	@Override
	public String toString() {
		return value;
	}

	/**
	 * 
	 * @param value
	 * @return ErrorType constant matching given value
	 * @throws IllegalArgumentException
	 *             if given value does not match any allowed value.
	 */
	public static ErrorType getErrorTypeByValue(String value) {
		for (ErrorType errorType : values()) {
			if (errorType.value.equals(value)) {
				return errorType;
			}
		}
		throw new IllegalArgumentException("No enum const class " + ErrorType.class.getName() + "." + value);
	}
}