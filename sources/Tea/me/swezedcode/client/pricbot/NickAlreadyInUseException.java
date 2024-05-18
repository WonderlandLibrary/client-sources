package me.swezedcode.client.pricbot;

public class NickAlreadyInUseException extends IrcException {
	public NickAlreadyInUseException(final String e) {
		super(e);
	}
}
