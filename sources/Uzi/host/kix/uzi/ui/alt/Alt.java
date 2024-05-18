package host.kix.uzi.ui.alt;

public class Alt {
	private String display, username, password;
	private String resolvedUsername;

	public Alt(String username, String password) {
		this.display = username;
		this.username = username;
		this.password = password;
	}

	public Alt(String display, String username, String password) {
		this.display = display;
		this.username = username;
		this.password = password;
	}

	public String getDisplay() {
		return display;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}
