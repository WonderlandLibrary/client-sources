// 
// Decompiled by Procyon v0.5.30
// 

package org.jibble.pircbot;

import java.util.Enumeration;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.StringTokenizer;
import java.net.ServerSocket;
import java.io.File;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.IOException;
import java.util.Hashtable;
import java.net.InetAddress;

public abstract class PircBot implements ReplyConstants
{
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
        this._autoNickChange = false;
        this._verbose = false;
        this._name = "PircBot";
        this._nick = this._name;
        this._login = "PircBot";
        this._version = "PircBot 1.5.0 Java IRC Bot - www.jibble.org";
        this._finger = "You ought to be arrested for fingering a bot!";
        this._channelPrefixes = "#&+!";
    }
    
    public final synchronized void connect(final String s) throws IOException, IrcException, NickAlreadyInUseException {
        this.connect(s, 6667, null);
    }
    
    public final synchronized void connect(final String s, final int n) throws IOException, IrcException, NickAlreadyInUseException {
        this.connect(s, n, null);
    }
    
    public final synchronized void connect(final String server, final int port, final String password) throws IOException, IrcException, NickAlreadyInUseException {
        this._server = server;
        this._port = port;
        this._password = password;
        if (this.isConnected()) {
            throw new IOException("The PircBot is already connected to an IRC server.  Disconnect first.");
        }
        this.removeAllChannels();
        final Socket socket = new Socket(server, port);
        this.log("*** Connected to server.");
        this._inetAddress = socket.getLocalAddress();
        InputStreamReader inputStreamReader;
        OutputStreamWriter outputStreamWriter;
        if (this.getEncoding() != null) {
            inputStreamReader = new InputStreamReader(socket.getInputStream(), this.getEncoding());
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream(), this.getEncoding());
        }
        else {
            inputStreamReader = new InputStreamReader(socket.getInputStream());
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
        }
        final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        final BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        if (password != null && !password.equals("")) {
            OutputThread.sendRawLine(this, bufferedWriter, "PASS " + password);
        }
        String nick = this.getName();
        OutputThread.sendRawLine(this, bufferedWriter, "NICK " + nick);
        OutputThread.sendRawLine(this, bufferedWriter, "USER " + this.getLogin() + " 8 * :" + this.getVersion());
        this._inputThread = new InputThread(this, socket, bufferedReader, bufferedWriter);
        int n = 1;
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            this.handleLine(line);
            final int index = line.indexOf(" ");
            final int index2 = line.indexOf(" ", index + 1);
            if (index2 >= 0) {
                final String substring = line.substring(index + 1, index2);
                if (substring.equals("004")) {
                    break;
                }
                if (substring.equals("433")) {
                    if (!this._autoNickChange) {
                        socket.close();
                        this._inputThread = null;
                        throw new NickAlreadyInUseException(line);
                    }
                    ++n;
                    nick = this.getName() + n;
                    OutputThread.sendRawLine(this, bufferedWriter, "NICK " + nick);
                }
                else if (!substring.equals("439")) {
                    if (substring.startsWith("5") || substring.startsWith("4")) {
                        socket.close();
                        this._inputThread = null;
                        throw new IrcException("Could not log into the IRC server: " + line);
                    }
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
            throw new IrcException("Cannot reconnect to an IRC server because we were never connected to one previously!");
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
    
    public final void joinChannel(final String s) {
        this.sendRawLine("JOIN " + s);
    }
    
    public final void joinChannel(final String s, final String s2) {
        this.joinChannel(s + " " + s2);
    }
    
    public final void partChannel(final String s) {
        this.sendRawLine("PART " + s);
    }
    
    public final void partChannel(final String s, final String s2) {
        this.sendRawLine("PART " + s + " :" + s2);
    }
    
    public final void quitServer() {
        this.quitServer("");
    }
    
    public final void quitServer(final String s) {
        this.sendRawLine("QUIT :" + s);
    }
    
    public final synchronized void sendRawLine(final String s) {
        if (this.isConnected()) {
            this._inputThread.sendRawLine(s);
        }
    }
    
    public final synchronized void sendRawLineViaQueue(final String s) {
        if (s == null) {
            throw new NullPointerException("Cannot send null messages to server");
        }
        if (this.isConnected()) {
            this._outQueue.add(s);
        }
    }
    
    public final void sendMessage(final String s, final String s2) {
        this._outQueue.add("PRIVMSG " + s + " :" + s2);
    }
    
    public final void sendAction(final String s, final String s2) {
        this.sendCTCPCommand(s, "ACTION " + s2);
    }
    
    public final void sendNotice(final String s, final String s2) {
        this._outQueue.add("NOTICE " + s + " :" + s2);
    }
    
    public final void sendCTCPCommand(final String s, final String s2) {
        this._outQueue.add("PRIVMSG " + s + " :\u0001" + s2 + "\u0001");
    }
    
    public final void changeNick(final String s) {
        this.sendRawLine("NICK " + s);
    }
    
    public final void identify(final String s) {
        this.sendRawLine("NICKSERV IDENTIFY " + s);
    }
    
    public final void setMode(final String s, final String s2) {
        this.sendRawLine("MODE " + s + " " + s2);
    }
    
    public final void sendInvite(final String s, final String s2) {
        this.sendRawLine("INVITE " + s + " :" + s2);
    }
    
    public final void ban(final String s, final String s2) {
        this.sendRawLine("MODE " + s + " +b " + s2);
    }
    
    public final void unBan(final String s, final String s2) {
        this.sendRawLine("MODE " + s + " -b " + s2);
    }
    
    public final void op(final String s, final String s2) {
        this.setMode(s, "+o " + s2);
    }
    
    public final void deOp(final String s, final String s2) {
        this.setMode(s, "-o " + s2);
    }
    
    public final void voice(final String s, final String s2) {
        this.setMode(s, "+v " + s2);
    }
    
    public final void deVoice(final String s, final String s2) {
        this.setMode(s, "-v " + s2);
    }
    
    public final void setTopic(final String s, final String s2) {
        this.sendRawLine("TOPIC " + s + " :" + s2);
    }
    
    public final void kick(final String s, final String s2) {
        this.kick(s, s2, "");
    }
    
    public final void kick(final String s, final String s2, final String s3) {
        this.sendRawLine("KICK " + s + " " + s2 + " :" + s3);
    }
    
    public final void listChannels() {
        this.listChannels(null);
    }
    
    public final void listChannels(final String s) {
        if (s == null) {
            this.sendRawLine("LIST");
        }
        else {
            this.sendRawLine("LIST " + s);
        }
    }
    
    public final DccFileTransfer dccSendFile(final File file, final String s, final int n) {
        final DccFileTransfer dccFileTransfer = new DccFileTransfer(this, this._dccManager, file, s, n);
        dccFileTransfer.doSend(true);
        return dccFileTransfer;
    }
    
    protected final void dccReceiveFile(final File file, final long n, final int n2, final int n3) {
        throw new RuntimeException("dccReceiveFile is deprecated, please use sendFile");
    }
    
    public final DccChat dccSendChatRequest(final String s, final int soTimeout) {
        DccChat dccChat = null;
        try {
            ServerSocket serverSocket = null;
            final int[] dccPorts = this.getDccPorts();
            if (dccPorts == null) {
                serverSocket = new ServerSocket(0);
            }
            else {
                int i = 0;
                while (i < dccPorts.length) {
                    try {
                        serverSocket = new ServerSocket(dccPorts[i]);
                    }
                    catch (Exception ex) {
                        ++i;
                        continue;
                    }
                    break;
                }
                if (serverSocket == null) {
                    throw new IOException("All ports returned by getDccPorts() are in use.");
                }
            }
            serverSocket.setSoTimeout(soTimeout);
            final int localPort = serverSocket.getLocalPort();
            InetAddress inetAddress = this.getDccInetAddress();
            if (inetAddress == null) {
                inetAddress = this.getInetAddress();
            }
            this.sendCTCPCommand(s, "DCC CHAT chat " + this.ipToLong(inetAddress.getAddress()) + " " + localPort);
            final Socket accept = serverSocket.accept();
            serverSocket.close();
            dccChat = new DccChat(this, s, accept);
        }
        catch (Exception ex2) {}
        return dccChat;
    }
    
    protected final DccChat dccAcceptChatRequest(final String s, final long n, final int n2) {
        throw new RuntimeException("dccAcceptChatRequest is deprecated, please use onIncomingChatRequest");
    }
    
    public void log(final String s) {
        if (this._verbose) {
            System.out.println(System.currentTimeMillis() + " " + s);
        }
    }
    
    protected void handleLine(final String s) {
        this.log(s);
        if (s.startsWith("PING ")) {
            this.onServerPing(s.substring(5));
            return;
        }
        String s2 = "";
        String substring = "";
        String substring2 = "";
        final StringTokenizer stringTokenizer = new StringTokenizer(s);
        final String nextToken = stringTokenizer.nextToken();
        final String nextToken2 = stringTokenizer.nextToken();
        String s3 = null;
        final int index = nextToken.indexOf("!");
        final int index2 = nextToken.indexOf("@");
        if (nextToken.startsWith(":")) {
            if (index > 0 && index2 > 0 && index < index2) {
                s2 = nextToken.substring(1, index);
                substring = nextToken.substring(index + 1, index2);
                substring2 = nextToken.substring(index2 + 1);
            }
            else {
                if (!stringTokenizer.hasMoreTokens()) {
                    this.onUnknown(s);
                    return;
                }
                final String s4 = nextToken2;
                int int1 = -1;
                try {
                    int1 = Integer.parseInt(s4);
                }
                catch (NumberFormatException ex) {}
                if (int1 != -1) {
                    this.processServerResponse(int1, s.substring(s.indexOf(s4, nextToken.length()) + 4, s.length()));
                    return;
                }
                s2 = nextToken;
                s3 = s4;
            }
        }
        final String upperCase = nextToken2.toUpperCase();
        if (s2.startsWith(":")) {
            s2 = s2.substring(1);
        }
        if (s3 == null) {
            s3 = stringTokenizer.nextToken();
        }
        if (s3.startsWith(":")) {
            s3 = s3.substring(1);
        }
        if (upperCase.equals("PRIVMSG") && s.indexOf(":\u0001") > 0 && s.endsWith("\u0001")) {
            final String substring3 = s.substring(s.indexOf(":\u0001") + 2, s.length() - 1);
            if (substring3.equals("VERSION")) {
                this.onVersion(s2, substring, substring2, s3);
            }
            else if (substring3.startsWith("ACTION ")) {
                this.onAction(s2, substring, substring2, s3, substring3.substring(7));
            }
            else if (substring3.startsWith("PING ")) {
                this.onPing(s2, substring, substring2, s3, substring3.substring(5));
            }
            else if (substring3.equals("TIME")) {
                this.onTime(s2, substring, substring2, s3);
            }
            else if (substring3.equals("FINGER")) {
                this.onFinger(s2, substring, substring2, s3);
            }
            else {
                final StringTokenizer stringTokenizer2;
                if ((stringTokenizer2 = new StringTokenizer(substring3)).countTokens() >= 5 && stringTokenizer2.nextToken().equals("DCC")) {
                    if (!this._dccManager.processRequest(s2, substring, substring2, substring3)) {
                        this.onUnknown(s);
                    }
                }
                else {
                    this.onUnknown(s);
                }
            }
        }
        else if (upperCase.equals("PRIVMSG") && this._channelPrefixes.indexOf(s3.charAt(0)) >= 0) {
            this.onMessage(s3, s2, substring, substring2, s.substring(s.indexOf(" :") + 2));
        }
        else if (upperCase.equals("PRIVMSG")) {
            this.onPrivateMessage(s2, substring, substring2, s.substring(s.indexOf(" :") + 2));
        }
        else if (upperCase.equals("JOIN")) {
            final String s5 = s3;
            this.addUser(s5, new User("", s2));
            this.onJoin(s5, s2, substring, substring2);
        }
        else if (upperCase.equals("PART")) {
            this.removeUser(s3, s2);
            if (s2.equals(this.getNick())) {
                this.removeChannel(s3);
            }
            this.onPart(s3, s2, substring, substring2);
        }
        else if (upperCase.equals("NICK")) {
            final String nick = s3;
            this.renameUser(s2, nick);
            if (s2.equals(this.getNick())) {
                this.setNick(nick);
            }
            this.onNickChange(s2, substring, substring2, nick);
        }
        else if (upperCase.equals("NOTICE")) {
            this.onNotice(s2, substring, substring2, s3, s.substring(s.indexOf(" :") + 2));
        }
        else if (upperCase.equals("QUIT")) {
            if (s2.equals(this.getNick())) {
                this.removeAllChannels();
            }
            else {
                this.removeUser(s2);
            }
            this.onQuit(s2, substring, substring2, s.substring(s.indexOf(" :") + 2));
        }
        else if (upperCase.equals("KICK")) {
            final String nextToken3 = stringTokenizer.nextToken();
            if (nextToken3.equals(this.getNick())) {
                this.removeChannel(s3);
            }
            this.removeUser(s3, nextToken3);
            this.onKick(s3, s2, substring, substring2, nextToken3, s.substring(s.indexOf(" :") + 2));
        }
        else if (upperCase.equals("MODE")) {
            String s6 = s.substring(s.indexOf(s3, 2) + s3.length() + 1);
            if (s6.startsWith(":")) {
                s6 = s6.substring(1);
            }
            this.processMode(s3, s2, substring, substring2, s6);
        }
        else if (upperCase.equals("TOPIC")) {
            this.onTopic(s3, s.substring(s.indexOf(" :") + 2), s2, System.currentTimeMillis(), true);
        }
        else if (upperCase.equals("INVITE")) {
            this.onInvite(s3, s2, substring, substring2, s.substring(s.indexOf(" :") + 2));
        }
        else {
            this.onUnknown(s);
        }
    }
    
    protected void onConnect() {
    }
    
    protected void onDisconnect() {
    }
    
    private final void processServerResponse(final int n, final String s) {
        if (n == 322) {
            final int index = s.indexOf(32);
            final int index2 = s.indexOf(32, index + 1);
            final int index3 = s.indexOf(32, index2 + 1);
            final int index4 = s.indexOf(58);
            final String substring = s.substring(index + 1, index2);
            int int1 = 0;
            try {
                int1 = Integer.parseInt(s.substring(index2 + 1, index3));
            }
            catch (NumberFormatException ex) {}
            this.onChannelInfo(substring, int1, s.substring(index4 + 1));
        }
        else if (n == 332) {
            final int index5 = s.indexOf(32);
            final int index6 = s.indexOf(32, index5 + 1);
            final int index7 = s.indexOf(58);
            final String substring2 = s.substring(index5 + 1, index6);
            final String substring3 = s.substring(index7 + 1);
            this._topics.put(substring2, substring3);
            this.onTopic(substring2, substring3);
        }
        else if (n == 333) {
            final StringTokenizer stringTokenizer = new StringTokenizer(s);
            stringTokenizer.nextToken();
            final String nextToken = stringTokenizer.nextToken();
            final String nextToken2 = stringTokenizer.nextToken();
            long n2 = 0L;
            try {
                n2 = Long.parseLong(stringTokenizer.nextToken()) * 1000L;
            }
            catch (NumberFormatException ex2) {}
            final String s2 = this._topics.get(nextToken);
            this._topics.remove(nextToken);
            this.onTopic(nextToken, s2, nextToken2, n2, false);
        }
        else if (n == 353) {
            final int index8 = s.indexOf(" :");
            final String substring4 = s.substring(s.lastIndexOf(32, index8 - 1) + 1, index8);
            final StringTokenizer stringTokenizer2 = new StringTokenizer(s.substring(s.indexOf(" :") + 2));
            while (stringTokenizer2.hasMoreTokens()) {
                final String nextToken3 = stringTokenizer2.nextToken();
                String s3 = "";
                if (nextToken3.startsWith("@")) {
                    s3 = "@";
                }
                else if (nextToken3.startsWith("+")) {
                    s3 = "+";
                }
                else if (nextToken3.startsWith(".")) {
                    s3 = ".";
                }
                this.addUser(substring4, new User(s3, nextToken3.substring(s3.length())));
            }
        }
        else if (n == 366) {
            final String substring5 = s.substring(s.indexOf(32) + 1, s.indexOf(" :"));
            this.onUserList(substring5, this.getUsers(substring5));
        }
        this.onServerResponse(n, s);
    }
    
    protected void onServerResponse(final int n, final String s) {
    }
    
    protected void onUserList(final String s, final User[] array) {
    }
    
    protected void onMessage(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    protected void onPrivateMessage(final String s, final String s2, final String s3, final String s4) {
    }
    
    protected void onAction(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    protected void onNotice(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    protected void onJoin(final String s, final String s2, final String s3, final String s4) {
    }
    
    protected void onPart(final String s, final String s2, final String s3, final String s4) {
    }
    
    protected void onNickChange(final String s, final String s2, final String s3, final String s4) {
    }
    
    protected void onKick(final String s, final String s2, final String s3, final String s4, final String s5, final String s6) {
    }
    
    protected void onQuit(final String s, final String s2, final String s3, final String s4) {
    }
    
    protected void onTopic(final String s, final String s2) {
    }
    
    protected void onTopic(final String s, final String s2, final String s3, final long n, final boolean b) {
    }
    
    protected void onChannelInfo(final String s, final int n, final String s2) {
    }
    
    private final void processMode(final String s, final String s2, final String s3, final String s4, final String s5) {
        if (this._channelPrefixes.indexOf(s.charAt(0)) >= 0) {
            final StringTokenizer stringTokenizer = new StringTokenizer(s5);
            final String[] array = new String[stringTokenizer.countTokens()];
            int n = 0;
            while (stringTokenizer.hasMoreTokens()) {
                array[n] = stringTokenizer.nextToken();
                ++n;
            }
            int n2 = 32;
            int n3 = 1;
            for (int i = 0; i < array[0].length(); ++i) {
                final char char1 = array[0].charAt(i);
                if (char1 == '+' || char1 == '-') {
                    n2 = char1;
                }
                else if (char1 == 'o') {
                    if (n2 == 43) {
                        this.updateUser(s, 1, array[n3]);
                        this.onOp(s, s2, s3, s4, array[n3]);
                    }
                    else {
                        this.updateUser(s, 2, array[n3]);
                        this.onDeop(s, s2, s3, s4, array[n3]);
                    }
                    ++n3;
                }
                else if (char1 == 'v') {
                    if (n2 == 43) {
                        this.updateUser(s, 3, array[n3]);
                        this.onVoice(s, s2, s3, s4, array[n3]);
                    }
                    else {
                        this.updateUser(s, 4, array[n3]);
                        this.onDeVoice(s, s2, s3, s4, array[n3]);
                    }
                    ++n3;
                }
                else if (char1 == 'k') {
                    if (n2 == 43) {
                        this.onSetChannelKey(s, s2, s3, s4, array[n3]);
                    }
                    else {
                        this.onRemoveChannelKey(s, s2, s3, s4, array[n3]);
                    }
                    ++n3;
                }
                else if (char1 == 'l') {
                    if (n2 == 43) {
                        this.onSetChannelLimit(s, s2, s3, s4, Integer.parseInt(array[n3]));
                        ++n3;
                    }
                    else {
                        this.onRemoveChannelLimit(s, s2, s3, s4);
                    }
                }
                else if (char1 == 'b') {
                    if (n2 == 43) {
                        this.onSetChannelBan(s, s2, s3, s4, array[n3]);
                    }
                    else {
                        this.onRemoveChannelBan(s, s2, s3, s4, array[n3]);
                    }
                    ++n3;
                }
                else if (char1 == 't') {
                    if (n2 == 43) {
                        this.onSetTopicProtection(s, s2, s3, s4);
                    }
                    else {
                        this.onRemoveTopicProtection(s, s2, s3, s4);
                    }
                }
                else if (char1 == 'n') {
                    if (n2 == 43) {
                        this.onSetNoExternalMessages(s, s2, s3, s4);
                    }
                    else {
                        this.onRemoveNoExternalMessages(s, s2, s3, s4);
                    }
                }
                else if (char1 == 'i') {
                    if (n2 == 43) {
                        this.onSetInviteOnly(s, s2, s3, s4);
                    }
                    else {
                        this.onRemoveInviteOnly(s, s2, s3, s4);
                    }
                }
                else if (char1 == 'm') {
                    if (n2 == 43) {
                        this.onSetModerated(s, s2, s3, s4);
                    }
                    else {
                        this.onRemoveModerated(s, s2, s3, s4);
                    }
                }
                else if (char1 == 'p') {
                    if (n2 == 43) {
                        this.onSetPrivate(s, s2, s3, s4);
                    }
                    else {
                        this.onRemovePrivate(s, s2, s3, s4);
                    }
                }
                else if (char1 == 's') {
                    if (n2 == 43) {
                        this.onSetSecret(s, s2, s3, s4);
                    }
                    else {
                        this.onRemoveSecret(s, s2, s3, s4);
                    }
                }
            }
            this.onMode(s, s2, s3, s4, s5);
        }
        else {
            this.onUserMode(s, s2, s3, s4, s5);
        }
    }
    
    protected void onMode(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    protected void onUserMode(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    protected void onOp(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    protected void onDeop(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    protected void onVoice(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    protected void onDeVoice(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    protected void onSetChannelKey(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    protected void onRemoveChannelKey(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    protected void onSetChannelLimit(final String s, final String s2, final String s3, final String s4, final int n) {
    }
    
    protected void onRemoveChannelLimit(final String s, final String s2, final String s3, final String s4) {
    }
    
    protected void onSetChannelBan(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    protected void onRemoveChannelBan(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    protected void onSetTopicProtection(final String s, final String s2, final String s3, final String s4) {
    }
    
    protected void onRemoveTopicProtection(final String s, final String s2, final String s3, final String s4) {
    }
    
    protected void onSetNoExternalMessages(final String s, final String s2, final String s3, final String s4) {
    }
    
    protected void onRemoveNoExternalMessages(final String s, final String s2, final String s3, final String s4) {
    }
    
    protected void onSetInviteOnly(final String s, final String s2, final String s3, final String s4) {
    }
    
    protected void onRemoveInviteOnly(final String s, final String s2, final String s3, final String s4) {
    }
    
    protected void onSetModerated(final String s, final String s2, final String s3, final String s4) {
    }
    
    protected void onRemoveModerated(final String s, final String s2, final String s3, final String s4) {
    }
    
    protected void onSetPrivate(final String s, final String s2, final String s3, final String s4) {
    }
    
    protected void onRemovePrivate(final String s, final String s2, final String s3, final String s4) {
    }
    
    protected void onSetSecret(final String s, final String s2, final String s3, final String s4) {
    }
    
    protected void onRemoveSecret(final String s, final String s2, final String s3, final String s4) {
    }
    
    protected void onInvite(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    protected void onDccSendRequest(final String s, final String s2, final String s3, final String s4, final long n, final int n2, final int n3) {
    }
    
    protected void onDccChatRequest(final String s, final String s2, final String s3, final long n, final int n2) {
    }
    
    protected void onIncomingFileTransfer(final DccFileTransfer dccFileTransfer) {
    }
    
    protected void onFileTransferFinished(final DccFileTransfer dccFileTransfer, final Exception ex) {
    }
    
    protected void onIncomingChatRequest(final DccChat dccChat) {
    }
    
    protected void onVersion(final String s, final String s2, final String s3, final String s4) {
        this.sendRawLine("NOTICE " + s + " :\u0001VERSION " + this._version + "\u0001");
    }
    
    protected void onPing(final String s, final String s2, final String s3, final String s4, final String s5) {
        this.sendRawLine("NOTICE " + s + " :\u0001PING " + s5 + "\u0001");
    }
    
    protected void onServerPing(final String s) {
        this.sendRawLine("PONG " + s);
    }
    
    protected void onTime(final String s, final String s2, final String s3, final String s4) {
        this.sendRawLine("NOTICE " + s + " :\u0001TIME " + new Date().toString() + "\u0001");
    }
    
    protected void onFinger(final String s, final String s2, final String s3, final String s4) {
        this.sendRawLine("NOTICE " + s + " :\u0001FINGER " + this._finger + "\u0001");
    }
    
    protected void onUnknown(final String s) {
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
    
    public final void setMessageDelay(final long messageDelay) {
        if (messageDelay < 0L) {
            throw new IllegalArgumentException("Cannot have a negative time.");
        }
        this._messageDelay = messageDelay;
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
    
    public int[] longToIp(long n) {
        final int[] array = new int[4];
        for (int i = 3; i >= 0; --i) {
            array[i] = (int)(n % 256L);
            n /= 256L;
        }
        return array;
    }
    
    public long ipToLong(final byte[] array) {
        if (array.length != 4) {
            throw new IllegalArgumentException("byte array must be of length 4");
        }
        long n = 0L;
        long n2 = 1L;
        for (int i = 3; i >= 0; --i) {
            n += (array[i] + 256) % 256 * n2;
            n2 *= 256L;
        }
        return n;
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
    
    public void setDccPorts(final int[] array) {
        if (array == null || array.length == 0) {
            this._dccPorts = null;
        }
        else {
            this._dccPorts = array.clone();
        }
    }
    
    public boolean equals(final Object o) {
        return o instanceof PircBot && o == this;
    }
    
    public int hashCode() {
        return super.hashCode();
    }
    
    public String toString() {
        return "Version{" + this._version + "}" + " Connected{" + this.isConnected() + "}" + " Server{" + this._server + "}" + " Port{" + this._port + "}" + " Password{" + this._password + "}";
    }
    
    public final User[] getUsers(String lowerCase) {
        lowerCase = lowerCase.toLowerCase();
        User[] array = new User[0];
        synchronized (this._channels) {
            final Hashtable<Object, User> hashtable = this._channels.get(lowerCase);
            if (hashtable != null) {
                array = new User[hashtable.size()];
                final Enumeration<User> elements = hashtable.elements();
                for (int i = 0; i < array.length; ++i) {
                    array[i] = elements.nextElement();
                }
            }
        }
        return array;
    }
    
    public final String[] getChannels() {
        String[] array = new String[0];
        synchronized (this._channels) {
            array = new String[this._channels.size()];
            final Enumeration<String> keys = this._channels.keys();
            for (int i = 0; i < array.length; ++i) {
                array[i] = keys.nextElement();
            }
        }
        return array;
    }
    
    public synchronized void dispose() {
        this._outputThread.interrupt();
        this._inputThread.dispose();
    }
    
    private final void addUser(String lowerCase, final User user) {
        lowerCase = lowerCase.toLowerCase();
        synchronized (this._channels) {
            Hashtable<User, User> hashtable = this._channels.get(lowerCase);
            if (hashtable == null) {
                hashtable = new Hashtable<User, User>();
                this._channels.put(lowerCase, hashtable);
            }
            hashtable.put(user, user);
        }
    }
    
    private final User removeUser(String lowerCase, final String s) {
        lowerCase = lowerCase.toLowerCase();
        final User user = new User("", s);
        synchronized (this._channels) {
            final Hashtable<Object, User> hashtable = this._channels.get(lowerCase);
            if (hashtable != null) {
                return hashtable.remove(user);
            }
        }
        return null;
    }
    
    private final void removeUser(final String s) {
        synchronized (this._channels) {
            final Enumeration<String> keys = this._channels.keys();
            while (keys.hasMoreElements()) {
                this.removeUser(keys.nextElement(), s);
            }
        }
    }
    
    private final void renameUser(final String s, final String s2) {
        synchronized (this._channels) {
            final Enumeration<String> keys = this._channels.keys();
            while (keys.hasMoreElements()) {
                final String s3 = keys.nextElement();
                final User removeUser = this.removeUser(s3, s);
                if (removeUser != null) {
                    this.addUser(s3, new User(removeUser.getPrefix(), s2));
                }
            }
        }
    }
    
    private final void removeChannel(String lowerCase) {
        lowerCase = lowerCase.toLowerCase();
        synchronized (this._channels) {
            this._channels.remove(lowerCase);
        }
    }
    
    private final void removeAllChannels() {
        synchronized (this._channels) {
            this._channels = new Hashtable();
        }
    }
    
    private final void updateUser(String lowerCase, final int n, final String s) {
        lowerCase = lowerCase.toLowerCase();
        synchronized (this._channels) {
            final Hashtable<Object, User> hashtable = this._channels.get(lowerCase);
            User user = null;
            if (hashtable != null) {
                final Enumeration<User> elements = hashtable.elements();
                while (elements.hasMoreElements()) {
                    final User user2 = elements.nextElement();
                    if (user2.getNick().equalsIgnoreCase(s)) {
                        if (n == 1) {
                            if (user2.hasVoice()) {
                                user = new User("@+", s);
                            }
                            else {
                                user = new User("@", s);
                            }
                        }
                        else if (n == 2) {
                            if (user2.hasVoice()) {
                                user = new User("+", s);
                            }
                            else {
                                user = new User("", s);
                            }
                        }
                        else if (n == 3) {
                            if (user2.isOp()) {
                                user = new User("@+", s);
                            }
                            else {
                                user = new User("+", s);
                            }
                        }
                        else {
                            if (n != 4) {
                                continue;
                            }
                            if (user2.isOp()) {
                                user = new User("@", s);
                            }
                            else {
                                user = new User("", s);
                            }
                        }
                    }
                }
            }
            if (user != null) {
                hashtable.put(user, user);
            }
            else {
                final User user3 = new User("", s);
                hashtable.put(user3, user3);
            }
        }
    }
}
