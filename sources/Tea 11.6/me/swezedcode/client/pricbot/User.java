package me.swezedcode.client.pricbot;

public class User {
	private String _prefix;
	private String _nick;
	private String _lowerNick;

	User(final String prefix, final String nick) {
		this._prefix = prefix;
		this._nick = nick;
		this._lowerNick = nick.toLowerCase();
	}

	public String getPrefix() {
		return this._prefix;
	}

	public boolean isOp() {
		return this._prefix.indexOf(64) >= 0;
	}

	public boolean hasVoice() {
		return this._prefix.indexOf(43) >= 0;
	}

	public String getNick() {
		return this._nick;
	}

	@Override
	public String toString() {
		return String.valueOf(this.getPrefix()) + this.getNick();
	}

	public boolean equals(final String nick) {
		return nick.toLowerCase().equals(this._lowerNick);
	}

	@Override
	public boolean equals(final Object o) {
		if (o instanceof User) {
			final User other = (User) o;
			return other._lowerNick.equals(this._lowerNick);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this._lowerNick.hashCode();
	}

	public int compareTo(final Object o) {
		if (o instanceof User) {
			final User other = (User) o;
			return other._lowerNick.compareTo(this._lowerNick);
		}
		return -1;
	}
}
