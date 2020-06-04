package server.rpc;

public class Operation implements java.io.Serializable {

	private static final long		serialVersionUID	= 403825246463035849L;

	private String					name				= "";

	private Capability				capability			= Capability.BASE;

	public static final Operation	GET_CONFIG			= new Operation("get-config", Capability.BASE);
	public static final Operation	EDIT_CONFIG			= new Operation("edit-config", Capability.BASE);
	public static final Operation	COPY_CONFIG			= new Operation("copy-config", Capability.BASE);
	public static final Operation	DELETE_CONFIG		= new Operation("delete-config", Capability.BASE);
	public static final Operation	LOCK				= new Operation("lock", Capability.BASE);
	public static final Operation	UNLOCK				= new Operation("unlock", Capability.BASE);
	public static final Operation	GET					= new Operation("get", Capability.BASE);
	public static final Operation	CLOSE_SESSION		= new Operation("close-session", Capability.BASE);
	public static final Operation	KILL_SESSION		= new Operation("kill-session", Capability.BASE);
	public static final Operation	COMMIT				= new Operation("commit", Capability.BASE);

	public static final Operation	SET_LOGICAL_ROUTER	= new Operation("set-logical-router", Capability.JUNOS);
	public static final Operation	GET_ROUTE_INFO		= new Operation("get-route-information", Capability.JUNOS);
	public static final Operation	GET_INTERFACE_INFO	= new Operation("get-interface-information", Capability.JUNOS);
	public static final Operation	GET_SOFTWARE_INFO	= new Operation("get-software-information", Capability.JUNOS);
	public static final Operation	GET_ROLLBACK_INFO	= new Operation("get-rollback-information", Capability.JUNOS);
	public static final Operation	DISCARD				= new Operation("discard-changes", Capability.JUNOS);
	public static final Operation	OPEN_CONFIG			= new Operation("open-configuration", Capability.JUNOS);
	public static final Operation	CLOSE_CONFIG		= new Operation("close-configuration", Capability.JUNOS);
	public static final Operation	LOAD_CONFIGURATION  = new Operation("load-configuration", Capability.JUNOS);
	public static final Operation	VALIDATE			= new Operation("validate", Capability.VALIDATE);

	protected Operation(String name, Capability capability) {
		this.name = name;
		this.capability = capability;
	}

	public boolean equals(Operation compareOperation) {
		return (this.name.equals(compareOperation.name) && this.capability
				.equals(compareOperation.capability));

	}

	public String getName() {
		return name;
	}

	// public static final Operation
}