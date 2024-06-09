package me.swezedcode.client.pricbot;

import java.net.*;
import java.io.*;

public class DccChat {
	private PircBot _bot;
	private String _nick;
	private String _login;
	private String _hostname;
	private BufferedReader _reader;
	private BufferedWriter _writer;
	private Socket _socket;
	private boolean _acceptable;
	private long _address;
	private int _port;

	DccChat(final PircBot bot, final String nick, final String login, final String hostname, final long address,
			final int port) {
		this._login = null;
		this._hostname = null;
		this._address = 0L;
		this._port = 0;
		this._bot = bot;
		this._address = address;
		this._port = port;
		this._nick = nick;
		this._login = login;
		this._hostname = hostname;
		this._acceptable = true;
	}

	DccChat(final PircBot bot, final String nick, final Socket socket) throws IOException {
		this._login = null;
		this._hostname = null;
		this._address = 0L;
		this._port = 0;
		this._bot = bot;
		this._nick = nick;
		this._socket = socket;
		this._reader = new BufferedReader(new InputStreamReader(this._socket.getInputStream()));
		this._writer = new BufferedWriter(new OutputStreamWriter(this._socket.getOutputStream()));
		this._acceptable = false;
	}

	public synchronized void accept() throws IOException {
		if (this._acceptable) {
			this._acceptable = false;
			final int[] ip = this._bot.longToIp(this._address);
			final String ipStr = String.valueOf(ip[0]) + "." + ip[1] + "." + ip[2] + "." + ip[3];
			this._socket = new Socket(ipStr, this._port);
			this._reader = new BufferedReader(new InputStreamReader(this._socket.getInputStream()));
			this._writer = new BufferedWriter(new OutputStreamWriter(this._socket.getOutputStream()));
		}
	}

	public String readLine() throws IOException {
		if (this._acceptable) {
			throw new IOException("You must call the accept() method of the DccChat request before you can use it.");
		}
		return this._reader.readLine();
	}

	public void sendLine(final String line) throws IOException {
		if (this._acceptable) {
			throw new IOException("You must call the accept() method of the DccChat request before you can use it.");
		}
		this._writer.write(String.valueOf(line) + "\r\n");
		this._writer.flush();
	}

	public void close() throws IOException {
		if (this._acceptable) {
			throw new IOException("You must call the accept() method of the DccChat request before you can use it.");
		}
		this._socket.close();
	}

	public String getNick() {
		return this._nick;
	}

	public String getLogin() {
		return this._login;
	}

	public String getHostname() {
		return this._hostname;
	}

	public BufferedReader getBufferedReader() {
		return this._reader;
	}

	public BufferedWriter getBufferedWriter() {
		return this._writer;
	}

	public Socket getSocket() {
		return this._socket;
	}

	public long getNumericalAddress() {
		return this._address;
	}
}
