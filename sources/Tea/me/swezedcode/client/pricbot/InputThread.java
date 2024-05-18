package me.swezedcode.client.pricbot;

import java.net.*;
import java.util.*;
import java.io.*;

public class InputThread extends Thread {
	private PircBot _bot;
	private Socket _socket;
	private BufferedReader _breader;
	private BufferedWriter _bwriter;
	private boolean _isConnected;
	private boolean _disposed;
	public static final int MAX_LINE_LENGTH = 512;

	InputThread(final PircBot bot, final Socket socket, final BufferedReader breader, final BufferedWriter bwriter) {
		this._bot = null;
		this._socket = null;
		this._breader = null;
		this._bwriter = null;
		this._isConnected = true;
		this._disposed = false;
		this._bot = bot;
		this._socket = socket;
		this._breader = breader;
		this._bwriter = bwriter;
		this.setName(this.getClass() + "-Thread");
	}

	void sendRawLine(final String line) {
		OutputThread.sendRawLine(this._bot, this._bwriter, line);
	}

	boolean isConnected() {
		return this._isConnected;
	}

	@Override
	public void run() {
		try {
			boolean running = true;
			while (running) {
				try {
					String line = null;
					while ((line = this._breader.readLine()) != null) {
						try {
							this._bot.handleLine(line);
						} catch (Throwable t) {
							final StringWriter sw = new StringWriter();
							final PrintWriter pw = new PrintWriter(sw);
							t.printStackTrace(pw);
							pw.flush();
							final StringTokenizer tokenizer = new StringTokenizer(sw.toString(), "\r\n");
							synchronized (this._bot) {
								this._bot.log("### Your implementation of PircBot is faulty and you have");
								this._bot.log("### allowed an uncaught Exception or Error to propagate in your");
								this._bot.log("### code. It may be possible for PircBot to continue operating");
								this._bot.log("### normally. Here is the stack trace that was produced: -");
								this._bot.log("### ");
								while (tokenizer.hasMoreTokens()) {
									this._bot.log("### " + tokenizer.nextToken());
								}
							}
							// monitorexit(this._bot)
						}
					}
					if (line != null) {
						continue;
					}
					running = false;
				} catch (InterruptedIOException iioe) {
					this.sendRawLine("PING " + System.currentTimeMillis() / 1000L);
				}
			}
		} catch (Exception ex) {
		}
		try {
			this._socket.close();
		} catch (Exception ex2) {
		}
		if (!this._disposed) {
			this._bot.log("*** Disconnected.");
			this._isConnected = false;
			this._bot.onDisconnect();
		}
	}

	public void dispose() {
		try {
			this._disposed = true;
			this._socket.close();
		} catch (Exception ex) {
		}
	}
}
