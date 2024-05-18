package me.protocol_client.irc;

public class IrcLine {
	private int		index;
	private String	line;
	private String	sender;
	private boolean	read;

	public IrcLine(int index, String line, String sender, boolean read) {
		this.index = index;
		this.line = line;
		this.sender = sender;
		this.read = read;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}
}
