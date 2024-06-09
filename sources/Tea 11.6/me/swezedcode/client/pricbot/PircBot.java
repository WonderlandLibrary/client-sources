package me.swezedcode.client.pricbot;

import java.net.*;
import java.io.*;
import java.util.*;

import net.minecraft.client.Minecraft;

public abstract class PircBot implements ReplyConstants {
	public static final String VERSION = "1.5.0";
	private static final int OP_ADD = 1;
	private static final int OP_REMOVE = 2;
	private static final int VOICE_ADD = 3;
	private static final int VOICE_REMOVE = 4;
	private InputThread _inputThread;
	private OutputThread _outputThread;
	private String _charset;
	private InetAddress _inetAddress;
	private String _server;
	private int _port;
	private String _password;
	private Queue _outQueue;
	private long _messageDelay;
	private Hashtable _channels;
	private Hashtable _topics;
	private DccManager _dccManager;
	private int[] _dccPorts;
	private InetAddress _dccInetAddress;
	private boolean _autoNickChange;
	private boolean _verbose;
	private String _name;
	private String _nick;
	private String _login;
	private String _version;
	private String _finger;
	private String _channelPrefixes;

	public PircBot() {
		this._inputThread = null;
		this._outputThread = null;
		this._charset = null;
		this._inetAddress = null;
		this._server = null;
		this._port = -1;
		this._password = null;
		this._outQueue = new Queue();
		this._messageDelay = 1000L;
		this._channels = new Hashtable();
		this._topics = new Hashtable();
		this._dccManager = new DccManager(this);
		this._dccPorts = null;
		this._dccInetAddress = null;
		this._autoNickChange = true;
		this._verbose = false;
		this._name = "PircBot";
		this._nick = this._name;
		this._login = "PircBot";
		this._version = "PircBot 1.5.0 Java IRC Bot - www.jibble.org";
		this._finger = "You ought to be arrested for fingering a bot!";
		this._channelPrefixes = "#&+!";
	}

	public final synchronized void connect(final String hostname)
			throws IOException, IrcException, NickAlreadyInUseException {
		this.connect(hostname, 6667, null);
	}

	public final synchronized void connect(final String hostname, final int port)
			throws IOException, IrcException, NickAlreadyInUseException {
		this.connect(hostname, port, null);
	}

	public final synchronized void connect(final String hostname, final int port, final String password)
			throws IOException, IrcException, NickAlreadyInUseException {
		this._server = hostname;
		this._port = port;
		this._password = password;
		if (this.isConnected()) {
			throw new IOException("The PircBot is already connected to an IRC server.  Disconnect first.");
		}
		this.removeAllChannels();
		final Socket socket = new Socket(hostname, port);
		this.log("*** Connected to server.");
		this._inetAddress = socket.getLocalAddress();
		InputStreamReader inputStreamReader = null;
		OutputStreamWriter outputStreamWriter = null;
		if (this.getEncoding() != null) {
			inputStreamReader = new InputStreamReader(socket.getInputStream(), this.getEncoding());
			outputStreamWriter = new OutputStreamWriter(socket.getOutputStream(), this.getEncoding());
		} else {
			inputStreamReader = new InputStreamReader(socket.getInputStream());
			outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
		}
		final BufferedReader breader = new BufferedReader(inputStreamReader);
		final BufferedWriter bwriter = new BufferedWriter(outputStreamWriter);
		if (password != null && !password.equals("")) {
			OutputThread.sendRawLine(this, bwriter, "PASS " + password);
		}
		String nick = this.getName();
		OutputThread.sendRawLine(this, bwriter, "NICK " + nick);
		OutputThread.sendRawLine(this, bwriter, "USER " + this.getLogin() + " 8 * :" + this.getVersion());
		this._inputThread = new InputThread(this, socket, breader, bwriter);
		String line = null;
		int tries = 1;
		while ((line = breader.readLine()) != null) {
			this.handleLine(line);
			final int firstSpace = line.indexOf(" ");
			final int secondSpace = line.indexOf(" ", firstSpace + 1);
			if (secondSpace >= 0) {
				final String code = line.substring(firstSpace + 1, secondSpace);
				if (code.equals("004")) {
					break;
				}
				if (code.equals("433")) {
					if (!this._autoNickChange) {
						socket.close();
						this._inputThread = null;
						throw new NickAlreadyInUseException(line);
					}
					++tries;
					nick = Minecraft.getMinecraft().session.getUsername();

					OutputThread.sendRawLine(this, bwriter, "NICK " + nick);
				} else if (!code.equals("439") && (code.startsWith("5") || code.startsWith("4"))) {
					socket.close();
					this._inputThread = null;
					throw new IrcException("Could not log into the IRC server: " + line);
				}
			}
			this.setNick(nick);
		}
		this.log("*** Logged onto server.");
		socket.setSoTimeout(300000);
		this._inputThread.start();
		if (this._outputThread == null) {
			(this._outputThread = new OutputThread(this, this._outQueue)).start();
		}
		this.onConnect();
	}

	public final synchronized void reconnect() throws IOException, IrcException, NickAlreadyInUseException {
		if (this.getServer() == null) {
			throw new IrcException(
					"Cannot reconnect to an IRC server because we were never connected to one previously!");
		}
		this.connect(this.getServer(), this.getPort(), this.getPassword());
	}

	public final synchronized void disconnect() {
		this.quitServer();
	}

	public void setAutoNickChange(final boolean autoNickChange) {
		this._autoNickChange = autoNickChange;
	}

	public final void startIdentServer() {
		new IdentServer(this, this.getLogin());
	}

	public final void joinChannel(final String channel) {
		this.sendRawLine("JOIN " + channel);
	}

	public final void joinChannel(final String channel, final String key) {
		this.joinChannel(String.valueOf(channel) + " " + key);
	}

	public final void partChannel(final String channel) {
		this.sendRawLine("PART " + channel);
	}

	public final void partChannel(final String channel, final String reason) {
		this.sendRawLine("PART " + channel + " :" + reason);
	}

	public final void quitServer() {
		this.quitServer("");
	}

	public final void quitServer(final String reason) {
		this.sendRawLine("QUIT :" + reason);
	}

	public final synchronized void sendRawLine(final String line) {
		if (this.isConnected()) {
			this._inputThread.sendRawLine(line);
		}
	}

	public final synchronized void sendRawLineViaQueue(final String line) {
		if (line == null) {
			throw new NullPointerException("Cannot send null messages to server");
		}
		if (this.isConnected()) {
			this._outQueue.add(line);
		}
	}

	public final void sendMessage(final String target, final String message) {
		this._outQueue.add("PRIVMSG " + target + " :" + message);
	}

	public final void sendAction(final String target, final String action) {
		this.sendCTCPCommand(target, "ACTION " + action);
	}

	public final void sendNotice(final String target, final String notice) {
		this._outQueue.add("NOTICE " + target + " :" + notice);
	}

	public final void sendCTCPCommand(final String target, final String command) {
		this._outQueue.add("PRIVMSG " + target + " :\u0001" + command + "\u0001");
	}

	public final void changeNick(final String newNick) {
		this.sendRawLine("NICK " + newNick);
	}

	public final void identify(final String password) {
		this.sendRawLine("NICKSERV IDENTIFY " + password);
	}

	public final void setMode(final String channel, final String mode) {
		this.sendRawLine("MODE " + channel + " " + mode);
	}

	public final void sendInvite(final String nick, final String channel) {
		this.sendRawLine("INVITE " + nick + " :" + channel);
	}

	public final void ban(final String channel, final String hostmask) {
		this.sendRawLine("MODE " + channel + " +b " + hostmask);
	}

	public final void unBan(final String channel, final String hostmask) {
		this.sendRawLine("MODE " + channel + " -b " + hostmask);
	}

	public final void op(final String channel, final String nick) {
		this.setMode(channel, "+o " + nick);
	}

	public final void deOp(final String channel, final String nick) {
		this.setMode(channel, "-o " + nick);
	}

	public final void voice(final String channel, final String nick) {
		this.setMode(channel, "+v " + nick);
	}

	public final void deVoice(final String channel, final String nick) {
		this.setMode(channel, "-v " + nick);
	}

	public final void setTopic(final String channel, final String topic) {
		this.sendRawLine("TOPIC " + channel + " :" + topic);
	}

	public final void kick(final String channel, final String nick) {
		this.kick(channel, nick, "");
	}

	public final void kick(final String channel, final String nick, final String reason) {
		this.sendRawLine("KICK " + channel + " " + nick + " :" + reason);
	}

	public final void listChannels() {
		this.listChannels(null);
	}

	public final void listChannels(final String parameters) {
		if (parameters == null) {
			this.sendRawLine("LIST");
		} else {
			this.sendRawLine("LIST " + parameters);
		}
	}

	public final DccFileTransfer dccSendFile(final File file, final String nick, final int timeout) {
		final DccFileTransfer transfer = new DccFileTransfer(this, this._dccManager, file, nick, timeout);
		transfer.doSend(true);
		return transfer;
	}

	protected final void dccReceiveFile(final File file, final long address, final int port, final int size) {
		throw new RuntimeException("dccReceiveFile is deprecated, please use sendFile");
	}

	public final DccChat dccSendChatRequest(final String nick, final int timeout) {
		DccChat chat = null;
		try {
			ServerSocket ss = null;
			final int[] ports = this.getDccPorts();
			if (ports == null) {
				ss = new ServerSocket(0);
			} else {
				int i = 0;
				while (i < ports.length) {
					try {
						ss = new ServerSocket(ports[i]);
						break;
					} catch (Exception ex) {
						++i;
					}
				}
				if (ss == null) {
					throw new IOException("All ports returned by getDccPorts() are in use.");
				}
			}
			ss.setSoTimeout(timeout);
			final int port = ss.getLocalPort();
			InetAddress inetAddress = this.getDccInetAddress();
			if (inetAddress == null) {
				inetAddress = this.getInetAddress();
			}
			final byte[] ip = inetAddress.getAddress();
			final long ipNum = this.ipToLong(ip);
			this.sendCTCPCommand(nick, "DCC CHAT chat " + ipNum + " " + port);
			final Socket socket = ss.accept();
			ss.close();
			chat = new DccChat(this, nick, socket);
		} catch (Exception ex2) {
		}
		return chat;
	}

	protected final DccChat dccAcceptChatRequest(final String sourceNick, final long address, final int port) {
		throw new RuntimeException("dccAcceptChatRequest is deprecated, please use onIncomingChatRequest");
	}

	public void log(final String line) {
		if (this._verbose) {
			System.out.println(String.valueOf(System.currentTimeMillis()) + " " + line);
		}
	}

	protected void handleLine(final String line) {
		this.log(line);
		if (line.startsWith("PING ")) {
			this.onServerPing(line.substring(5));
			return;
		}
		String sourceNick = "";
		String sourceLogin = "";
		String sourceHostname = "";
		StringTokenizer tokenizer = new StringTokenizer(line);
		final String senderInfo = tokenizer.nextToken();
		String command = tokenizer.nextToken();
		String target = null;
		final int exclamation = senderInfo.indexOf("!");
		final int at = senderInfo.indexOf("@");
		if (senderInfo.startsWith(":")) {
			if (exclamation > 0 && at > 0 && exclamation < at) {
				sourceNick = senderInfo.substring(1, exclamation);
				sourceLogin = senderInfo.substring(exclamation + 1, at);
				sourceHostname = senderInfo.substring(at + 1);
			} else {
				if (!tokenizer.hasMoreTokens()) {
					this.onUnknown(line);
					return;
				}
				final String token = command;
				int code = -1;
				try {
					code = Integer.parseInt(token);
				} catch (NumberFormatException ex) {
				}
				if (code != -1) {
					final String errorStr = token;
					final String response = line.substring(line.indexOf(errorStr, senderInfo.length()) + 4,
							line.length());
					this.processServerResponse(code, response);
					return;
				}
				sourceNick = senderInfo;
				target = token;
			}
		}
		command = command.toUpperCase();
		if (sourceNick.startsWith(":")) {
			sourceNick = sourceNick.substring(1);
		}
		if (target == null) {
			target = tokenizer.nextToken();
		}
		if (target.startsWith(":")) {
			target = target.substring(1);
		}
		if (command.equals("PRIVMSG") && line.indexOf(":\u0001") > 0 && line.endsWith("\u0001")) {
			final String request = line.substring(line.indexOf(":\u0001") + 2, line.length() - 1);
			if (request.equals("VERSION")) {
				this.onVersion(sourceNick, sourceLogin, sourceHostname, target);
			} else if (request.startsWith("ACTION ")) {
				this.onAction(sourceNick, sourceLogin, sourceHostname, target, request.substring(7));
			} else if (request.startsWith("PING ")) {
				this.onPing(sourceNick, sourceLogin, sourceHostname, target, request.substring(5));
			} else if (request.equals("TIME")) {
				this.onTime(sourceNick, sourceLogin, sourceHostname, target);
			} else if (request.equals("FINGER")) {
				this.onFinger(sourceNick, sourceLogin, sourceHostname, target);
			} else if ((tokenizer = new StringTokenizer(request)).countTokens() >= 5
					&& tokenizer.nextToken().equals("DCC")) {
				final boolean success = this._dccManager.processRequest(sourceNick, sourceLogin, sourceHostname,
						request);
				if (!success) {
					this.onUnknown(line);
				}
			} else {
				this.onUnknown(line);
			}
		} else if (command.equals("PRIVMSG") && this._channelPrefixes.indexOf(target.charAt(0)) >= 0) {
			this.onMessage(target, sourceNick, sourceLogin, sourceHostname, line.substring(line.indexOf(" :") + 2));
		} else if (command.equals("PRIVMSG")) {
			this.onPrivateMessage(sourceNick, sourceLogin, sourceHostname, line.substring(line.indexOf(" :") + 2));
		} else if (command.equals("JOIN")) {
			final String channel = target;
			this.addUser(channel, new User("", sourceNick));
			this.onJoin(channel, sourceNick, sourceLogin, sourceHostname);
		} else if (command.equals("PART")) {
			this.removeUser(target, sourceNick);
			if (sourceNick.equals(this.getNick())) {
				this.removeChannel(target);
			}
			this.onPart(target, sourceNick, sourceLogin, sourceHostname);
		} else if (command.equals("NICK")) {
			final String newNick = target;
			this.renameUser(sourceNick, newNick);
			if (sourceNick.equals(this.getNick())) {
				this.setNick(newNick);
			}
			this.onNickChange(sourceNick, sourceLogin, sourceHostname, newNick);
		} else if (command.equals("NOTICE")) {
			this.onNotice(sourceNick, sourceLogin, sourceHostname, target, line.substring(line.indexOf(" :") + 2));
		} else if (command.equals("QUIT")) {
			if (sourceNick.equals(this.getNick())) {
				this.removeAllChannels();
			} else {
				this.removeUser(sourceNick);
			}
			this.onQuit(sourceNick, sourceLogin, sourceHostname, line.substring(line.indexOf(" :") + 2));
		} else if (command.equals("KICK")) {
			final String recipient = tokenizer.nextToken();
			if (recipient.equals(this.getNick())) {
				this.removeChannel(target);
			}
			this.removeUser(target, recipient);
			this.onKick(target, sourceNick, sourceLogin, sourceHostname, recipient,
					line.substring(line.indexOf(" :") + 2));
		} else if (command.equals("MODE")) {
			String mode = line.substring(line.indexOf(target, 2) + target.length() + 1);
			if (mode.startsWith(":")) {
				mode = mode.substring(1);
			}
			this.processMode(target, sourceNick, sourceLogin, sourceHostname, mode);
		} else if (command.equals("TOPIC")) {
			this.onTopic(target, line.substring(line.indexOf(" :") + 2), sourceNick, System.currentTimeMillis(), true);
		} else if (command.equals("INVITE")) {
			this.onInvite(target, sourceNick, sourceLogin, sourceHostname, line.substring(line.indexOf(" :") + 2));
		} else {
			this.onUnknown(line);
		}
	}

	protected void onConnect() {
	}

	protected void onDisconnect() {
	}

	private final void processServerResponse(final int code, final String response) {
		if (code == 322) {
			final int firstSpace = response.indexOf(32);
			final int secondSpace = response.indexOf(32, firstSpace + 1);
			final int thirdSpace = response.indexOf(32, secondSpace + 1);
			final int colon = response.indexOf(58);
			final String channel = response.substring(firstSpace + 1, secondSpace);
			int userCount = 0;
			try {
				userCount = Integer.parseInt(response.substring(secondSpace + 1, thirdSpace));
			} catch (NumberFormatException ex) {
			}
			final String topic = response.substring(colon + 1);
			this.onChannelInfo(channel, userCount, topic);
		} else if (code == 332) {
			final int firstSpace = response.indexOf(32);
			final int secondSpace = response.indexOf(32, firstSpace + 1);
			final int colon2 = response.indexOf(58);
			final String channel2 = response.substring(firstSpace + 1, secondSpace);
			final String topic2 = response.substring(colon2 + 1);
			this._topics.put(channel2, topic2);
			this.onTopic(channel2, topic2);
		} else if (code == 333) {
			final StringTokenizer tokenizer = new StringTokenizer(response);
			tokenizer.nextToken();
			final String channel3 = tokenizer.nextToken();
			final String setBy = tokenizer.nextToken();
			long date = 0L;
			try {
				date = Long.parseLong(tokenizer.nextToken()) * 1000L;
			} catch (NumberFormatException ex2) {
			}
			String topic3 = (String) this._topics.get(channel3);
			this._topics.remove(channel3);
			this.onTopic(channel3, topic3, setBy, date, false);
		} else if (code == 353) {
			final int channelEndIndex = response.indexOf(" :");
			final String channel3 = response.substring(response.lastIndexOf(32, channelEndIndex - 1) + 1,
					channelEndIndex);
			final StringTokenizer tokenizer2 = new StringTokenizer(response.substring(response.indexOf(" :") + 2));
			while (tokenizer2.hasMoreTokens()) {
				String nick = tokenizer2.nextToken();
				String prefix = "";
				if (nick.startsWith("@")) {
					prefix = "@";
				} else if (nick.startsWith("+")) {
					prefix = "+";
				} else if (nick.startsWith(".")) {
					prefix = ".";
				}
				nick = nick.substring(prefix.length());
				this.addUser(channel3, new User(prefix, nick));
			}
		} else if (code == 366) {
			final String channel4 = response.substring(response.indexOf(32) + 1, response.indexOf(" :"));
			final User[] users = this.getUsers(channel4);
			this.onUserList(channel4, users);
		}
		this.onServerResponse(code, response);
	}

	protected void onServerResponse(final int code, final String response) {
	}

	protected void onUserList(final String channel, final User[] users) {
	}

	protected void onMessage(final String channel, final String sender, final String login, final String hostname,
			final String message) {
	}

	protected void onPrivateMessage(final String sender, final String login, final String hostname,
			final String message) {
	}

	protected void onAction(final String sender, final String login, final String hostname, final String target,
			final String action) {
	}

	protected void onNotice(final String sourceNick, final String sourceLogin, final String sourceHostname,
			final String target, final String notice) {
	}

	protected void onJoin(final String channel, final String sender, final String login, final String hostname) {
	}

	protected void onPart(final String channel, final String sender, final String login, final String hostname) {
	}

	protected void onNickChange(final String oldNick, final String login, final String hostname, final String newNick) {
	}

	protected void onKick(final String channel, final String kickerNick, final String kickerLogin,
			final String kickerHostname, final String recipientNick, final String reason) {
	}

	protected void onQuit(final String sourceNick, final String sourceLogin, final String sourceHostname,
			final String reason) {
	}

	protected void onTopic(final String channel, final String topic) {
	}

	protected void onTopic(final String channel, final String topic, final String setBy, final long date,
			final boolean changed) {
	}

	protected void onChannelInfo(final String channel, final int userCount, final String topic) {
	}

	private final void processMode(final String target, final String sourceNick, final String sourceLogin,
			final String sourceHostname, final String mode) {
		if (this._channelPrefixes.indexOf(target.charAt(0)) >= 0) {
			final StringTokenizer tok = new StringTokenizer(mode);
			final String[] params = new String[tok.countTokens()];
			int t = 0;
			while (tok.hasMoreTokens()) {
				params[t] = tok.nextToken();
				++t;
			}
			char pn = ' ';
			int p = 1;
			for (int i = 0; i < params[0].length(); ++i) {
				final char atPos = params[0].charAt(i);
				if (atPos == '+' || atPos == '-') {
					pn = atPos;
				} else if (atPos == 'o') {
					if (pn == '+') {
						this.updateUser(target, 1, params[p]);
						this.onOp(target, sourceNick, sourceLogin, sourceHostname, params[p]);
					} else {
						this.updateUser(target, 2, params[p]);
						this.onDeop(target, sourceNick, sourceLogin, sourceHostname, params[p]);
					}
					++p;
				} else if (atPos == 'v') {
					if (pn == '+') {
						this.updateUser(target, 3, params[p]);
						this.onVoice(target, sourceNick, sourceLogin, sourceHostname, params[p]);
					} else {
						this.updateUser(target, 4, params[p]);
						this.onDeVoice(target, sourceNick, sourceLogin, sourceHostname, params[p]);
					}
					++p;
				} else if (atPos == 'k') {
					if (pn == '+') {
						this.onSetChannelKey(target, sourceNick, sourceLogin, sourceHostname, params[p]);
					} else {
						this.onRemoveChannelKey(target, sourceNick, sourceLogin, sourceHostname, params[p]);
					}
					++p;
				} else if (atPos == 'l') {
					if (pn == '+') {
						this.onSetChannelLimit(target, sourceNick, sourceLogin, sourceHostname,
								Integer.parseInt(params[p]));
						++p;
					} else {
						this.onRemoveChannelLimit(target, sourceNick, sourceLogin, sourceHostname);
					}
				} else if (atPos == 'b') {
					if (pn == '+') {
						this.onSetChannelBan(target, sourceNick, sourceLogin, sourceHostname, params[p]);
					} else {
						this.onRemoveChannelBan(target, sourceNick, sourceLogin, sourceHostname, params[p]);
					}
					++p;
				} else if (atPos == 't') {
					if (pn == '+') {
						this.onSetTopicProtection(target, sourceNick, sourceLogin, sourceHostname);
					} else {
						this.onRemoveTopicProtection(target, sourceNick, sourceLogin, sourceHostname);
					}
				} else if (atPos == 'n') {
					if (pn == '+') {
						this.onSetNoExternalMessages(target, sourceNick, sourceLogin, sourceHostname);
					} else {
						this.onRemoveNoExternalMessages(target, sourceNick, sourceLogin, sourceHostname);
					}
				} else if (atPos == 'i') {
					if (pn == '+') {
						this.onSetInviteOnly(target, sourceNick, sourceLogin, sourceHostname);
					} else {
						this.onRemoveInviteOnly(target, sourceNick, sourceLogin, sourceHostname);
					}
				} else if (atPos == 'm') {
					if (pn == '+') {
						this.onSetModerated(target, sourceNick, sourceLogin, sourceHostname);
					} else {
						this.onRemoveModerated(target, sourceNick, sourceLogin, sourceHostname);
					}
				} else if (atPos == 'p') {
					if (pn == '+') {
						this.onSetPrivate(target, sourceNick, sourceLogin, sourceHostname);
					} else {
						this.onRemovePrivate(target, sourceNick, sourceLogin, sourceHostname);
					}
				} else if (atPos == 's') {
					if (pn == '+') {
						this.onSetSecret(target, sourceNick, sourceLogin, sourceHostname);
					} else {
						this.onRemoveSecret(target, sourceNick, sourceLogin, sourceHostname);
					}
				}
			}
			this.onMode(target, sourceNick, sourceLogin, sourceHostname, mode);
		} else {
			this.onUserMode(target, sourceNick, sourceLogin, sourceHostname, mode);
		}
	}

	protected void onMode(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname, final String mode) {
	}

	protected void onUserMode(final String targetNick, final String sourceNick, final String sourceLogin,
			final String sourceHostname, final String mode) {
	}

	protected void onOp(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname, final String recipient) {
	}

	protected void onDeop(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname, final String recipient) {
	}

	protected void onVoice(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname, final String recipient) {
	}

	protected void onDeVoice(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname, final String recipient) {
	}

	protected void onSetChannelKey(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname, final String key) {
	}

	protected void onRemoveChannelKey(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname, final String key) {
	}

	protected void onSetChannelLimit(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname, final int limit) {
	}

	protected void onRemoveChannelLimit(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname) {
	}

	protected void onSetChannelBan(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname, final String hostmask) {
	}

	protected void onRemoveChannelBan(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname, final String hostmask) {
	}

	protected void onSetTopicProtection(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname) {
	}

	protected void onRemoveTopicProtection(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname) {
	}

	protected void onSetNoExternalMessages(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname) {
	}

	protected void onRemoveNoExternalMessages(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname) {
	}

	protected void onSetInviteOnly(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname) {
	}

	protected void onRemoveInviteOnly(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname) {
	}

	protected void onSetModerated(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname) {
	}

	protected void onRemoveModerated(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname) {
	}

	protected void onSetPrivate(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname) {
	}

	protected void onRemovePrivate(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname) {
	}

	protected void onSetSecret(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname) {
	}

	protected void onRemoveSecret(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname) {
	}

	protected void onInvite(final String targetNick, final String sourceNick, final String sourceLogin,
			final String sourceHostname, final String channel) {
	}

	protected void onDccSendRequest(final String sourceNick, final String sourceLogin, final String sourceHostname,
			final String filename, final long address, final int port, final int size) {
	}

	protected void onDccChatRequest(final String sourceNick, final String sourceLogin, final String sourceHostname,
			final long address, final int port) {
	}

	protected void onIncomingFileTransfer(final DccFileTransfer transfer) {
	}

	protected void onFileTransferFinished(final DccFileTransfer transfer, final Exception e) {
	}

	protected void onIncomingChatRequest(final DccChat chat) {
	}

	protected void onVersion(final String sourceNick, final String sourceLogin, final String sourceHostname,
			final String target) {
		this.sendRawLine("NOTICE " + sourceNick + " :\u0001VERSION " + this._version + "\u0001");
	}

	protected void onPing(final String sourceNick, final String sourceLogin, final String sourceHostname,
			final String target, final String pingValue) {
		this.sendRawLine("NOTICE " + sourceNick + " :\u0001PING " + pingValue + "\u0001");
	}

	protected void onServerPing(final String response) {
		this.sendRawLine("PONG " + response);
	}

	protected void onTime(final String sourceNick, final String sourceLogin, final String sourceHostname,
			final String target) {
		this.sendRawLine("NOTICE " + sourceNick + " :\u0001TIME " + new Date().toString() + "\u0001");
	}

	protected void onFinger(final String sourceNick, final String sourceLogin, final String sourceHostname,
			final String target) {
		this.sendRawLine("NOTICE " + sourceNick + " :\u0001FINGER " + this._finger + "\u0001");
	}

	protected void onUnknown(final String line) {
	}

	public final void setVerbose(final boolean verbose) {
		this._verbose = verbose;
	}

	protected final void setName(final String name) {
		this._name = name;
	}

	private final void setNick(final String nick) {
		this._nick = nick;
	}

	protected final void setLogin(final String login) {
		this._login = login;
	}

	protected final void setVersion(final String version) {
		this._version = version;
	}

	protected final void setFinger(final String finger) {
		this._finger = finger;
	}

	public final String getName() {
		return this._name;
	}

	public String getNick() {
		return this._nick;
	}

	public final String getLogin() {
		return this._login;
	}

	public final String getVersion() {
		return this._version;
	}

	public final String getFinger() {
		return this._finger;
	}

	public final synchronized boolean isConnected() {
		return this._inputThread != null && this._inputThread.isConnected();
	}

	public final void setMessageDelay(final long delay) {
		if (delay < 0L) {
			throw new IllegalArgumentException("Cannot have a negative time.");
		}
		this._messageDelay = delay;
	}

	public final long getMessageDelay() {
		return this._messageDelay;
	}

	public final int getMaxLineLength() {
		return 512;
	}

	public final int getOutgoingQueueSize() {
		return this._outQueue.size();
	}

	public final String getServer() {
		return this._server;
	}

	public final int getPort() {
		return this._port;
	}

	public final String getPassword() {
		return this._password;
	}

	public int[] longToIp(long address) {
		final int[] ip = new int[4];
		for (int i = 3; i >= 0; --i) {
			ip[i] = (int) (address % 256L);
			address /= 256L;
		}
		return ip;
	}

	public long ipToLong(final byte[] address) {
		if (address.length != 4) {
			throw new IllegalArgumentException("byte array must be of length 4");
		}
		long ipNum = 0L;
		long multiplier = 1L;
		for (int i = 3; i >= 0; --i) {
			final int byteVal = (address[i] + 256) % 256;
			ipNum += byteVal * multiplier;
			multiplier *= 256L;
		}
		return ipNum;
	}

	public void setEncoding(final String charset) throws UnsupportedEncodingException {
		"".getBytes(charset);
		this._charset = charset;
	}

	public String getEncoding() {
		return this._charset;
	}

	public InetAddress getInetAddress() {
		return this._inetAddress;
	}

	public void setDccInetAddress(final InetAddress dccInetAddress) {
		this._dccInetAddress = dccInetAddress;
	}

	public InetAddress getDccInetAddress() {
		return this._dccInetAddress;
	}

	public int[] getDccPorts() {
		if (this._dccPorts == null || this._dccPorts.length == 0) {
			return null;
		}
		return this._dccPorts.clone();
	}

	public void setDccPorts(final int[] ports) {
		if (ports == null || ports.length == 0) {
			this._dccPorts = null;
		} else {
			this._dccPorts = ports.clone();
		}
	}

	@Override
	public boolean equals(final Object o) {
		if (o instanceof PircBot) {
			final PircBot other = (PircBot) o;
			return other == this;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public String toString() {
		return "Version{" + this._version + "}" + " Connected{" + this.isConnected() + "}" + " Server{" + this._server
				+ "}" + " Port{" + this._port + "}" + " Password{" + this._password + "}";
	}

	public final User[] getUsers(String channel) {
		channel = channel.toLowerCase();
		User[] userArray = new User[0];
		synchronized (this._channels) {
			Hashtable users = (Hashtable) this._channels.get(channel);
			if (users != null) {
				userArray = new User[users.size()];
				final Enumeration enumeration = users.elements();
				for (int i = 0; i < userArray.length; ++i) {
					User user = (User) enumeration.nextElement();
					userArray[i] = user;
				}
			}
		}
		// monitorexit(this._channels)
		return userArray;
	}

	public final String[] getChannels() {
		String[] channels = new String[0];
		synchronized (this._channels) {
			channels = new String[this._channels.size()];
			final Enumeration enumeration = this._channels.keys();
			for (int i = 0; i < channels.length; ++i) {
				channels[i] = (String) enumeration.nextElement();
			}
		}
		// monitorexit(this._channels)
		return channels;
	}

	public synchronized void dispose() {
		this._outputThread.interrupt();
		this._inputThread.dispose();
	}

	private final void addUser(String channel, final User user) {
		channel = channel.toLowerCase();
		synchronized (this._channels) {
			Hashtable users = (Hashtable) this._channels.get(channel);
			if (users == null) {
				users = new Hashtable();
				this._channels.put(channel, users);
			}
			users.put(user, user);
		}
		// monitorexit(this._channels)
	}

	private final User removeUser(String channel, final String nick) {
		channel = channel.toLowerCase();
		final User user = new User("", nick);
		synchronized (this._channels) {
			final Hashtable users = (Hashtable) this._channels.get(channel);
			if (users != null) {
				// monitorexit(this._channels)
				return (User) users.remove(user);
			}
		}
		// monitorexit(this._channels)
		return null;
	}

	private final void removeUser(final String nick) {
		synchronized (this._channels) {
			final Enumeration enumeration = this._channels.keys();
			while (enumeration.hasMoreElements()) {
				final String channel = (String) enumeration.nextElement();
				this.removeUser(channel, nick);
			}
		}
		// monitorexit(this._channels)
	}

	private final void renameUser(final String oldNick, final String newNick) {
		synchronized (this._channels) {
			final Enumeration enumeration = this._channels.keys();
			while (enumeration.hasMoreElements()) {
				final String channel = (String) enumeration.nextElement();
				User user = this.removeUser(channel, oldNick);
				if (user != null) {
					user = new User(user.getPrefix(), newNick);
					this.addUser(channel, user);
				}
			}
		}
		// monitorexit(this._channels)
	}

	private final void removeChannel(String channel) {
		channel = channel.toLowerCase();
		synchronized (this._channels) {
			this._channels.remove(channel);
		}
		// monitorexit(this._channels)
	}

	private final void removeAllChannels() {
		synchronized (this._channels) {
		}
		// monitorexit(this._channels = new Hashtable())
	}

	private final void updateUser(String channel, final int userMode, final String nick) {
		channel = channel.toLowerCase();
		synchronized (this._channels) {
			final Hashtable users = (Hashtable) this._channels.get(channel);
			User newUser = null;
			if (users != null) {
				final Enumeration enumeration = users.elements();
				while (enumeration.hasMoreElements()) {
					User userObj = (User) enumeration.nextElement();
					if (userObj.getNick().equalsIgnoreCase(nick)) {
						if (userMode == 1) {
							if (userObj.hasVoice()) {
								newUser = new User("@+", nick);
							} else {
								newUser = new User("@", nick);
							}
						} else if (userMode == 2) {
							if (userObj.hasVoice()) {
								newUser = new User("+", nick);
							} else {
								newUser = new User("", nick);
							}
						} else if (userMode == 3) {
							if (userObj.isOp()) {
								newUser = new User("@+", nick);
							} else {
								newUser = new User("+", nick);
							}
						} else {
							if (userMode != 4) {
								continue;
							}
							if (userObj.isOp()) {
								newUser = new User("@", nick);
							} else {
								newUser = new User("", nick);
							}
						}
					}
				}
			}
			if (newUser != null) {
				users.put(newUser, newUser);
			} else {
				newUser = new User("", nick);
				users.put(newUser, newUser);
			}
		}
		// monitorexit(this._channels)
	}
}
