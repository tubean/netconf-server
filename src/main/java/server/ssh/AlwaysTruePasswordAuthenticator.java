package server.ssh;

import org.apache.sshd.server.PasswordAuthenticator;
import org.apache.sshd.server.session.ServerSession;

public class AlwaysTruePasswordAuthenticator implements PasswordAuthenticator {

	public boolean authenticate(String username, String password, ServerSession session) {
		return true;
	}

}