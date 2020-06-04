package server.rpc;

public enum ErrorSeverity {
	ERROR("error"),
	WARNING("warning"),
	OTHER("other");

	private String	value;
	String			other;

	ErrorSeverity(String value) {
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
	 * @return ErrorSeverity constant matching given value
	 * @throws IllegalArgumentException
	 *             if given value does not match any allowed value.
	 */
	public static ErrorSeverity getErrorSeverityByValue(String value) {
		for (ErrorSeverity errorSeverity : values()) {
			if (errorSeverity.value.equals(value)) {
				return errorSeverity;
			}
		}
		throw new IllegalArgumentException("No enum const class " + ErrorSeverity.class.getName() + "." + value);
	}

}