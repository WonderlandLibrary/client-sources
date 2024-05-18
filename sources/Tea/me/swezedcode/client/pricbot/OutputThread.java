package me.swezedcode.client.pricbot;

import java.io.BufferedWriter;

public class OutputThread extends Thread {
	private PircBot _bot;
	private Queue _outQueue;

	OutputThread(final PircBot bot, final Queue outQueue) {
		this._bot = null;
		this._outQueue = null;
		this._bot = bot;
		this._outQueue = outQueue;
		this.setName(this.getClass() + "-Thread");
	}

	static void sendRawLine(final PircBot bot, final BufferedWriter bwriter, String line) {
		if (line.length() > bot.getMaxLineLength() - 2) {
			line = line.substring(0, bot.getMaxLineLength() - 2);
		}
		synchronized (bwriter) {
			try {
				bwriter.write(String.valueOf(line) + "\r\n");
				bwriter.flush();
				bot.log(">>>" + line);
			} catch (Exception ex) {
			}
		}
	}

	@Override
	public void run() {
		try {
			boolean running = true;
			while (running) {
				Thread.sleep(this._bot.getMessageDelay());
				final String line = (String) this._outQueue.next();
				if (line != null) {
					this._bot.sendRawLine(line);
				} else {
					running = false;
				}
			}
		} catch (InterruptedException ex) {
		}
	}
}
