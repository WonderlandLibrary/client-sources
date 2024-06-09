package me.swezedcode.client.pricbot;

import java.util.*;

public class DccManager {
	private PircBot _bot;

	DccManager(PircBot bot) {
		this._bot = bot;
	}

	boolean processRequest(String nick, String login, String hostname, String request) {
		StringTokenizer tokenizer = new StringTokenizer(request);
		tokenizer.nextToken();
		String type = tokenizer.nextToken();
		String filename = tokenizer.nextToken();
		if (type.equals("SEND")) {
			long address = Long.parseLong(tokenizer.nextToken());
			int port = Integer.parseInt(tokenizer.nextToken());
			long size = -1L;
			try {
				size = Long.parseLong(tokenizer.nextToken());
			} catch (Exception localException) {
			}
			DccFileTransfer transfer = new DccFileTransfer(this._bot, this, nick, login, hostname, type, filename,
					address, port, size);
			this._bot.onIncomingFileTransfer(transfer);
		} else if (type.equals("RESUME")) {
			int port = Integer.parseInt(tokenizer.nextToken());
			long progress = Long.parseLong(tokenizer.nextToken());

			DccFileTransfer transfer = null;
			synchronized (this._awaitingResume) {
				for (int i = 0; i < this._awaitingResume.size(); i++) {
					transfer = (DccFileTransfer) this._awaitingResume.elementAt(i);
					if ((transfer.getNick().equals(nick)) && (transfer.getPort() == port)) {
						this._awaitingResume.removeElementAt(i);
						break;
					}
				}
			}
			if (transfer != null) {
				transfer.setProgress(progress);
				this._bot.sendCTCPCommand(nick, "DCC ACCEPT file.ext " + port + " " + progress);
			}
		} else if (type.equals("ACCEPT")) {
			int port = Integer.parseInt(tokenizer.nextToken());
			long progress = Long.parseLong(tokenizer.nextToken());

			DccFileTransfer transfer = null;
			synchronized (this._awaitingResume) {
				for (int i = 0; i < this._awaitingResume.size(); i++) {
					transfer = (DccFileTransfer) this._awaitingResume.elementAt(i);
					if ((transfer.getNick().equals(nick)) && (transfer.getPort() == port)) {
						this._awaitingResume.removeElementAt(i);
						break;
					}
				}
			}
			if (transfer != null) {
				transfer.doReceive(transfer.getFile(), true);
			}
		} else if (type.equals("CHAT")) {
			long address = Long.parseLong(tokenizer.nextToken());
			int port = Integer.parseInt(tokenizer.nextToken());

			final DccChat chat = new DccChat(this._bot, nick, login, hostname, address, port);

			new Thread() {
				public void run() {
					DccManager.this._bot.onIncomingChatRequest(chat);
				}
			}.start();
		} else {
			return false;
		}
		return true;
	}

	void addAwaitingResume(DccFileTransfer transfer) {
		synchronized (this._awaitingResume) {
			this._awaitingResume.addElement(transfer);
		}
	}

	void removeAwaitingResume(DccFileTransfer transfer) {
		this._awaitingResume.removeElement(transfer);
	}

	private Vector _awaitingResume = new Vector();
}
