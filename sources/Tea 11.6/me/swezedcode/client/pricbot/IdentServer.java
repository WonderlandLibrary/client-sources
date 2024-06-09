package me.swezedcode.client.pricbot;

import java.io.*;
import java.net.*;

public class IdentServer extends Thread {
	private PircBot _bot;
	private String _login;
	private ServerSocket _ss;

	IdentServer(final PircBot bot, final String login) {
		this._ss = null;
		this._bot = bot;
		this._login = login;
		try {
			(this._ss = new ServerSocket(113)).setSoTimeout(60000);
		} catch (Exception e) {
			this._bot.log("*** Could not start the ident server on port 113.");
			return;
		}
		this._bot.log("*** Ident server running on port 113 for the next 60 seconds...");
		this.setName(this.getClass() + "-Thread");
		this.start();
	}

	@Override
	public void run() {
		try {
			final Socket socket = this._ss.accept();
			socket.setSoTimeout(60000);
			final BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			String line = reader.readLine();
			if (line != null) {
				this._bot.log("*** Ident request received: " + line);
				line = String.valueOf(line) + " : USERID : UNIX : " + this._login;
				writer.write(String.valueOf(line) + "\r\n");
				writer.flush();
				this._bot.log("*** Ident reply sent: " + line);
				writer.close();
			}
		} catch (Exception ex) {
		}
		try {
			this._ss.close();
		} catch (Exception ex2) {
		}
		this._bot.log("*** The Ident server has been shut down.");
	}
}
