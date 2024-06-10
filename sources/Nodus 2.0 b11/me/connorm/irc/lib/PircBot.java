/*    1:     */ package me.connorm.irc.lib;
/*    2:     */ 
/*    3:     */ import java.io.BufferedReader;
/*    4:     */ import java.io.BufferedWriter;
/*    5:     */ import java.io.File;
/*    6:     */ import java.io.IOException;
/*    7:     */ import java.io.InputStreamReader;
/*    8:     */ import java.io.OutputStreamWriter;
/*    9:     */ import java.io.UnsupportedEncodingException;
/*   10:     */ import java.net.InetAddress;
/*   11:     */ import java.net.ServerSocket;
/*   12:     */ import java.net.Socket;
/*   13:     */ import java.util.Date;
/*   14:     */ import java.util.Enumeration;
/*   15:     */ import java.util.Hashtable;
/*   16:     */ import java.util.StringTokenizer;
/*   17:     */ 
/*   18:     */ public abstract class PircBot
/*   19:     */   implements ReplyConstants
/*   20:     */ {
/*   21:     */   public static final String VERSION = "1.5.0";
/*   22:     */   private static final int OP_ADD = 1;
/*   23:     */   private static final int OP_REMOVE = 2;
/*   24:     */   private static final int VOICE_ADD = 3;
/*   25:     */   private static final int VOICE_REMOVE = 4;
/*   26:  26 */   private InputThread _inputThread = null;
/*   27:  27 */   private OutputThread _outputThread = null;
/*   28:  28 */   private String _charset = null;
/*   29:  29 */   private InetAddress _inetAddress = null;
/*   30:  31 */   private String _server = null;
/*   31:  32 */   private int _port = -1;
/*   32:  33 */   private String _password = null;
/*   33:  35 */   private Queue _outQueue = new Queue();
/*   34:  36 */   private long _messageDelay = 1000L;
/*   35:  38 */   private Hashtable _channels = new Hashtable();
/*   36:  40 */   private Hashtable _topics = new Hashtable();
/*   37:  42 */   private DccManager _dccManager = new DccManager(this);
/*   38:  43 */   private int[] _dccPorts = null;
/*   39:  44 */   private InetAddress _dccInetAddress = null;
/*   40:  46 */   private boolean _autoNickChange = false;
/*   41:  47 */   private boolean _verbose = false;
/*   42:  48 */   private String _name = "PircBot";
/*   43:  49 */   private String _nick = this._name;
/*   44:  50 */   private String _login = "PircBot";
/*   45:  51 */   private String _version = "PircBot 1.5.0 Java IRC Bot - www.jibble.org";
/*   46:  52 */   private String _finger = "You ought to be arrested for fingering a bot!";
/*   47:  54 */   private String _channelPrefixes = "#&+!";
/*   48:     */   
/*   49:     */   public final synchronized void connect(String hostname)
/*   50:     */     throws IOException, IrcException, NickAlreadyInUseException
/*   51:     */   {
/*   52:  59 */     connect(hostname, 6667, null);
/*   53:     */   }
/*   54:     */   
/*   55:     */   public final synchronized void connect(String hostname, int port)
/*   56:     */     throws IOException, IrcException, NickAlreadyInUseException
/*   57:     */   {
/*   58:  65 */     connect(hostname, port, null);
/*   59:     */   }
/*   60:     */   
/*   61:     */   public final synchronized void connect(String hostname, int port, String password)
/*   62:     */     throws IOException, IrcException, NickAlreadyInUseException
/*   63:     */   {
/*   64:  71 */     this._server = hostname;
/*   65:  72 */     this._port = port;
/*   66:  73 */     this._password = password;
/*   67:  75 */     if (isConnected()) {
/*   68:  76 */       throw new IOException("The PircBot is already connected to an IRC server.  Disconnect first.");
/*   69:     */     }
/*   70:  79 */     removeAllChannels();
/*   71:     */     
/*   72:  81 */     Socket socket = new Socket(hostname, port);
/*   73:  82 */     log("*** Connected to server.");
/*   74:     */     
/*   75:  84 */     this._inetAddress = socket.getLocalAddress();
/*   76:     */     
/*   77:  86 */     InputStreamReader inputStreamReader = null;
/*   78:  87 */     OutputStreamWriter outputStreamWriter = null;
/*   79:  88 */     if (getEncoding() != null)
/*   80:     */     {
/*   81:  90 */       inputStreamReader = new InputStreamReader(socket.getInputStream(), getEncoding());
/*   82:  91 */       outputStreamWriter = new OutputStreamWriter(socket.getOutputStream(), getEncoding());
/*   83:     */     }
/*   84:     */     else
/*   85:     */     {
/*   86:  95 */       inputStreamReader = new InputStreamReader(socket.getInputStream());
/*   87:  96 */       outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
/*   88:     */     }
/*   89:  99 */     BufferedReader breader = new BufferedReader(inputStreamReader);
/*   90: 100 */     BufferedWriter bwriter = new BufferedWriter(outputStreamWriter);
/*   91: 102 */     if ((password != null) && (!password.equals(""))) {
/*   92: 103 */       OutputThread.sendRawLine(this, bwriter, "PASS " + password);
/*   93:     */     }
/*   94: 105 */     String nick = getName();
/*   95: 106 */     OutputThread.sendRawLine(this, bwriter, "NICK " + nick);
/*   96: 107 */     OutputThread.sendRawLine(this, bwriter, "USER " + getLogin() + " 8 * :" + getVersion());
/*   97:     */     
/*   98: 109 */     this._inputThread = new InputThread(this, socket, breader, bwriter);
/*   99:     */     
/*  100: 111 */     String line = null;
/*  101: 112 */     int tries = 1;
/*  102: 113 */     while ((line = breader.readLine()) != null)
/*  103:     */     {
/*  104: 115 */       handleLine(line);
/*  105:     */       
/*  106: 117 */       int firstSpace = line.indexOf(" ");
/*  107: 118 */       int secondSpace = line.indexOf(" ", firstSpace + 1);
/*  108: 119 */       if (secondSpace >= 0)
/*  109:     */       {
/*  110: 120 */         String code = line.substring(firstSpace + 1, secondSpace);
/*  111: 122 */         if (code.equals("004")) {
/*  112:     */           break;
/*  113:     */         }
/*  114: 126 */         if (code.equals("433"))
/*  115:     */         {
/*  116: 127 */           if (this._autoNickChange)
/*  117:     */           {
/*  118: 128 */             tries++;
/*  119: 129 */             nick = getName() + tries;
/*  120: 130 */             OutputThread.sendRawLine(this, bwriter, "NICK " + nick);
/*  121:     */           }
/*  122:     */           else
/*  123:     */           {
/*  124: 133 */             socket.close();
/*  125: 134 */             this._inputThread = null;
/*  126: 135 */             throw new NickAlreadyInUseException(line);
/*  127:     */           }
/*  128:     */         }
/*  129: 138 */         else if (!code.equals("439")) {
/*  130: 140 */           if ((code.startsWith("5")) || (code.startsWith("4")))
/*  131:     */           {
/*  132: 141 */             socket.close();
/*  133: 142 */             this._inputThread = null;
/*  134: 143 */             throw new IrcException("Could not log into the IRC server: " + line);
/*  135:     */           }
/*  136:     */         }
/*  137:     */       }
/*  138: 147 */       setNick(nick);
/*  139:     */     }
/*  140: 150 */     log("*** Logged onto server.");
/*  141:     */     
/*  142: 152 */     socket.setSoTimeout(300000);
/*  143:     */     
/*  144: 154 */     this._inputThread.start();
/*  145: 156 */     if (this._outputThread == null)
/*  146:     */     {
/*  147: 157 */       this._outputThread = new OutputThread(this, this._outQueue);
/*  148: 158 */       this._outputThread.start();
/*  149:     */     }
/*  150: 161 */     onConnect();
/*  151:     */   }
/*  152:     */   
/*  153:     */   public final synchronized void reconnect()
/*  154:     */     throws IOException, IrcException, NickAlreadyInUseException
/*  155:     */   {
/*  156: 167 */     if (getServer() == null) {
/*  157: 168 */       throw new IrcException("Cannot reconnect to an IRC server because we were never connected to one previously!");
/*  158:     */     }
/*  159: 170 */     connect(getServer(), getPort(), getPassword());
/*  160:     */   }
/*  161:     */   
/*  162:     */   public final synchronized void disconnect()
/*  163:     */   {
/*  164: 175 */     quitServer();
/*  165:     */   }
/*  166:     */   
/*  167:     */   public void setAutoNickChange(boolean autoNickChange)
/*  168:     */   {
/*  169: 180 */     this._autoNickChange = autoNickChange;
/*  170:     */   }
/*  171:     */   
/*  172:     */   public final void startIdentServer()
/*  173:     */   {
/*  174: 185 */     new IdentServer(this, getLogin());
/*  175:     */   }
/*  176:     */   
/*  177:     */   public final void joinChannel(String channel)
/*  178:     */   {
/*  179: 190 */     sendRawLine("JOIN " + channel);
/*  180:     */   }
/*  181:     */   
/*  182:     */   public final void joinChannel(String channel, String key)
/*  183:     */   {
/*  184: 195 */     joinChannel(channel + " " + key);
/*  185:     */   }
/*  186:     */   
/*  187:     */   public final void partChannel(String channel)
/*  188:     */   {
/*  189: 200 */     sendRawLine("PART " + channel);
/*  190:     */   }
/*  191:     */   
/*  192:     */   public final void partChannel(String channel, String reason)
/*  193:     */   {
/*  194: 205 */     sendRawLine("PART " + channel + " :" + reason);
/*  195:     */   }
/*  196:     */   
/*  197:     */   public final void quitServer()
/*  198:     */   {
/*  199: 210 */     quitServer("");
/*  200:     */   }
/*  201:     */   
/*  202:     */   public final void quitServer(String reason)
/*  203:     */   {
/*  204: 215 */     sendRawLine("QUIT :" + reason);
/*  205:     */   }
/*  206:     */   
/*  207:     */   public final synchronized void sendRawLine(String line)
/*  208:     */   {
/*  209: 220 */     if (isConnected()) {
/*  210: 221 */       this._inputThread.sendRawLine(line);
/*  211:     */     }
/*  212:     */   }
/*  213:     */   
/*  214:     */   public final synchronized void sendRawLineViaQueue(String line)
/*  215:     */   {
/*  216: 226 */     if (line == null) {
/*  217: 227 */       throw new NullPointerException("Cannot send null messages to server");
/*  218:     */     }
/*  219: 229 */     if (isConnected()) {
/*  220: 230 */       this._outQueue.add(line);
/*  221:     */     }
/*  222:     */   }
/*  223:     */   
/*  224:     */   public final void sendMessage(String target, String message)
/*  225:     */   {
/*  226: 235 */     this._outQueue.add("PRIVMSG " + target + " :" + message);
/*  227:     */   }
/*  228:     */   
/*  229:     */   public final void sendAction(String target, String action)
/*  230:     */   {
/*  231: 240 */     sendCTCPCommand(target, "ACTION " + action);
/*  232:     */   }
/*  233:     */   
/*  234:     */   public final void sendNotice(String target, String notice)
/*  235:     */   {
/*  236: 245 */     this._outQueue.add("NOTICE " + target + " :" + notice);
/*  237:     */   }
/*  238:     */   
/*  239:     */   public final void sendCTCPCommand(String target, String command)
/*  240:     */   {
/*  241: 250 */     this._outQueue.add("PRIVMSG " + target + " :\001" + command + "\001");
/*  242:     */   }
/*  243:     */   
/*  244:     */   public final void changeNick(String newNick)
/*  245:     */   {
/*  246: 255 */     sendRawLine("NICK " + newNick);
/*  247:     */   }
/*  248:     */   
/*  249:     */   public final void identify(String password)
/*  250:     */   {
/*  251: 260 */     sendRawLine("NICKSERV IDENTIFY " + password);
/*  252:     */   }
/*  253:     */   
/*  254:     */   public final void setMode(String channel, String mode)
/*  255:     */   {
/*  256: 265 */     sendRawLine("MODE " + channel + " " + mode);
/*  257:     */   }
/*  258:     */   
/*  259:     */   public final void sendInvite(String nick, String channel)
/*  260:     */   {
/*  261: 270 */     sendRawLine("INVITE " + nick + " :" + channel);
/*  262:     */   }
/*  263:     */   
/*  264:     */   public final void ban(String channel, String hostmask)
/*  265:     */   {
/*  266: 275 */     sendRawLine("MODE " + channel + " +b " + hostmask);
/*  267:     */   }
/*  268:     */   
/*  269:     */   public final void unBan(String channel, String hostmask)
/*  270:     */   {
/*  271: 280 */     sendRawLine("MODE " + channel + " -b " + hostmask);
/*  272:     */   }
/*  273:     */   
/*  274:     */   public final void op(String channel, String nick)
/*  275:     */   {
/*  276: 285 */     setMode(channel, "+o " + nick);
/*  277:     */   }
/*  278:     */   
/*  279:     */   public final void deOp(String channel, String nick)
/*  280:     */   {
/*  281: 290 */     setMode(channel, "-o " + nick);
/*  282:     */   }
/*  283:     */   
/*  284:     */   public final void voice(String channel, String nick)
/*  285:     */   {
/*  286: 295 */     setMode(channel, "+v " + nick);
/*  287:     */   }
/*  288:     */   
/*  289:     */   public final void deVoice(String channel, String nick)
/*  290:     */   {
/*  291: 300 */     setMode(channel, "-v " + nick);
/*  292:     */   }
/*  293:     */   
/*  294:     */   public final void setTopic(String channel, String topic)
/*  295:     */   {
/*  296: 305 */     sendRawLine("TOPIC " + channel + " :" + topic);
/*  297:     */   }
/*  298:     */   
/*  299:     */   public final void kick(String channel, String nick)
/*  300:     */   {
/*  301: 310 */     kick(channel, nick, "");
/*  302:     */   }
/*  303:     */   
/*  304:     */   public final void kick(String channel, String nick, String reason)
/*  305:     */   {
/*  306: 315 */     sendRawLine("KICK " + channel + " " + nick + " :" + reason);
/*  307:     */   }
/*  308:     */   
/*  309:     */   public final void listChannels()
/*  310:     */   {
/*  311: 320 */     listChannels(null);
/*  312:     */   }
/*  313:     */   
/*  314:     */   public final void listChannels(String parameters)
/*  315:     */   {
/*  316: 325 */     if (parameters == null) {
/*  317: 326 */       sendRawLine("LIST");
/*  318:     */     } else {
/*  319: 329 */       sendRawLine("LIST " + parameters);
/*  320:     */     }
/*  321:     */   }
/*  322:     */   
/*  323:     */   public final DccFileTransfer dccSendFile(File file, String nick, int timeout)
/*  324:     */   {
/*  325: 334 */     DccFileTransfer transfer = new DccFileTransfer(this, this._dccManager, file, nick, timeout);
/*  326: 335 */     transfer.doSend(true);
/*  327: 336 */     return transfer;
/*  328:     */   }
/*  329:     */   
/*  330:     */   /**
/*  331:     */    * @deprecated
/*  332:     */    */
/*  333:     */   protected final void dccReceiveFile(File file, long address, int port, int size)
/*  334:     */   {
/*  335: 342 */     throw new RuntimeException("dccReceiveFile is deprecated, please use sendFile");
/*  336:     */   }
/*  337:     */   
/*  338:     */   public final DccChat dccSendChatRequest(String nick, int timeout)
/*  339:     */   {
/*  340: 347 */     DccChat chat = null;
/*  341:     */     try
/*  342:     */     {
/*  343: 349 */       ServerSocket ss = null;
/*  344:     */       
/*  345: 351 */       int[] ports = getDccPorts();
/*  346: 352 */       if (ports == null)
/*  347:     */       {
/*  348: 354 */         ss = new ServerSocket(0);
/*  349:     */       }
/*  350:     */       else
/*  351:     */       {
/*  352: 357 */         for (int i = 0; i < ports.length;) {
/*  353:     */           try
/*  354:     */           {
/*  355: 359 */             ss = new ServerSocket(ports[i]);
/*  356:     */           }
/*  357:     */           catch (Exception localException)
/*  358:     */           {
/*  359: 363 */             i++;
/*  360:     */           }
/*  361:     */         }
/*  362: 368 */         if (ss == null) {
/*  363: 370 */           throw new IOException("All ports returned by getDccPorts() are in use.");
/*  364:     */         }
/*  365:     */       }
/*  366: 374 */       ss.setSoTimeout(timeout);
/*  367: 375 */       int port = ss.getLocalPort();
/*  368:     */       
/*  369: 377 */       InetAddress inetAddress = getDccInetAddress();
/*  370: 378 */       if (inetAddress == null) {
/*  371: 379 */         inetAddress = getInetAddress();
/*  372:     */       }
/*  373: 381 */       byte[] ip = inetAddress.getAddress();
/*  374: 382 */       long ipNum = ipToLong(ip);
/*  375:     */       
/*  376: 384 */       sendCTCPCommand(nick, "DCC CHAT chat " + ipNum + " " + port);
/*  377:     */       
/*  378: 386 */       Socket socket = ss.accept();
/*  379:     */       
/*  380: 388 */       ss.close();
/*  381:     */       
/*  382: 390 */       chat = new DccChat(this, nick, socket);
/*  383:     */     }
/*  384:     */     catch (Exception localException1) {}
/*  385: 395 */     return chat;
/*  386:     */   }
/*  387:     */   
/*  388:     */   /**
/*  389:     */    * @deprecated
/*  390:     */    */
/*  391:     */   protected final DccChat dccAcceptChatRequest(String sourceNick, long address, int port)
/*  392:     */   {
/*  393: 401 */     throw new RuntimeException("dccAcceptChatRequest is deprecated, please use onIncomingChatRequest");
/*  394:     */   }
/*  395:     */   
/*  396:     */   public void log(String line) {}
/*  397:     */   
/*  398:     */   protected void handleLine(String line)
/*  399:     */   {
/*  400: 410 */     if (line.startsWith("PING "))
/*  401:     */     {
/*  402: 412 */       onServerPing(line.substring(5));
/*  403: 413 */       return;
/*  404:     */     }
/*  405: 416 */     String sourceNick = "";
/*  406: 417 */     String sourceLogin = "";
/*  407: 418 */     String sourceHostname = "";
/*  408:     */     
/*  409: 420 */     StringTokenizer tokenizer = new StringTokenizer(line);
/*  410: 421 */     String senderInfo = tokenizer.nextToken();
/*  411: 422 */     String command = tokenizer.nextToken();
/*  412: 423 */     String target = null;
/*  413:     */     
/*  414: 425 */     int exclamation = senderInfo.indexOf("!");
/*  415: 426 */     int at = senderInfo.indexOf("@");
/*  416: 427 */     if (senderInfo.startsWith(":")) {
/*  417: 428 */       if ((exclamation > 0) && (at > 0) && (exclamation < at))
/*  418:     */       {
/*  419: 429 */         sourceNick = senderInfo.substring(1, exclamation);
/*  420: 430 */         sourceLogin = senderInfo.substring(exclamation + 1, at);
/*  421: 431 */         sourceHostname = senderInfo.substring(at + 1);
/*  422:     */       }
/*  423: 433 */       else if (tokenizer.hasMoreTokens())
/*  424:     */       {
/*  425: 434 */         String token = command;
/*  426:     */         
/*  427: 436 */         int code = -1;
/*  428:     */         try
/*  429:     */         {
/*  430: 438 */           code = Integer.parseInt(token);
/*  431:     */         }
/*  432:     */         catch (NumberFormatException localNumberFormatException) {}
/*  433: 444 */         if (code != -1)
/*  434:     */         {
/*  435: 445 */           String errorStr = token;
/*  436: 446 */           String response = line.substring(line.indexOf(errorStr, senderInfo.length()) + 4, line.length());
/*  437: 447 */           processServerResponse(code, response);
/*  438:     */           
/*  439: 449 */           return;
/*  440:     */         }
/*  441: 452 */         sourceNick = senderInfo;
/*  442: 453 */         target = token;
/*  443:     */       }
/*  444:     */       else
/*  445:     */       {
/*  446: 457 */         onUnknown(line);
/*  447:     */         
/*  448: 459 */         return;
/*  449:     */       }
/*  450:     */     }
/*  451: 464 */     command = command.toUpperCase();
/*  452: 465 */     if (sourceNick.startsWith(":")) {
/*  453: 466 */       sourceNick = sourceNick.substring(1);
/*  454:     */     }
/*  455: 468 */     if (target == null) {
/*  456: 469 */       target = tokenizer.nextToken();
/*  457:     */     }
/*  458: 471 */     if (target.startsWith(":")) {
/*  459: 472 */       target = target.substring(1);
/*  460:     */     }
/*  461: 475 */     if ((command.equals("PRIVMSG")) && (line.indexOf(":\001") > 0) && (line.endsWith("\001")))
/*  462:     */     {
/*  463: 476 */       String request = line.substring(line.indexOf(":\001") + 2, line.length() - 1);
/*  464: 477 */       if (request.equals("VERSION"))
/*  465:     */       {
/*  466: 479 */         onVersion(sourceNick, sourceLogin, sourceHostname, target);
/*  467:     */       }
/*  468: 481 */       else if (request.startsWith("ACTION "))
/*  469:     */       {
/*  470: 483 */         onAction(sourceNick, sourceLogin, sourceHostname, target, request.substring(7));
/*  471:     */       }
/*  472: 485 */       else if (request.startsWith("PING "))
/*  473:     */       {
/*  474: 487 */         onPing(sourceNick, sourceLogin, sourceHostname, target, request.substring(5));
/*  475:     */       }
/*  476: 489 */       else if (request.equals("TIME"))
/*  477:     */       {
/*  478: 491 */         onTime(sourceNick, sourceLogin, sourceHostname, target);
/*  479:     */       }
/*  480: 493 */       else if (request.equals("FINGER"))
/*  481:     */       {
/*  482: 495 */         onFinger(sourceNick, sourceLogin, sourceHostname, target);
/*  483:     */       }
/*  484: 497 */       else if (((tokenizer = new StringTokenizer(request)).countTokens() >= 5) && (tokenizer.nextToken().equals("DCC")))
/*  485:     */       {
/*  486: 499 */         boolean success = this._dccManager.processRequest(sourceNick, sourceLogin, sourceHostname, request);
/*  487: 500 */         if (!success) {
/*  488: 502 */           onUnknown(line);
/*  489:     */         }
/*  490:     */       }
/*  491:     */       else
/*  492:     */       {
/*  493: 507 */         onUnknown(line);
/*  494:     */       }
/*  495:     */     }
/*  496: 510 */     else if ((command.equals("PRIVMSG")) && (this._channelPrefixes.indexOf(target.charAt(0)) >= 0))
/*  497:     */     {
/*  498: 512 */       onMessage(target, sourceNick, sourceLogin, sourceHostname, line.substring(line.indexOf(" :") + 2));
/*  499:     */     }
/*  500: 514 */     else if (command.equals("PRIVMSG"))
/*  501:     */     {
/*  502: 516 */       onPrivateMessage(sourceNick, sourceLogin, sourceHostname, line.substring(line.indexOf(" :") + 2));
/*  503:     */     }
/*  504: 518 */     else if (command.equals("JOIN"))
/*  505:     */     {
/*  506: 520 */       String channel = target;
/*  507: 521 */       addUser(channel, new User("", sourceNick));
/*  508: 522 */       onJoin(channel, sourceNick, sourceLogin, sourceHostname);
/*  509:     */     }
/*  510: 524 */     else if (command.equals("PART"))
/*  511:     */     {
/*  512: 526 */       removeUser(target, sourceNick);
/*  513: 527 */       if (sourceNick.equals(getNick())) {
/*  514: 528 */         removeChannel(target);
/*  515:     */       }
/*  516: 530 */       onPart(target, sourceNick, sourceLogin, sourceHostname);
/*  517:     */     }
/*  518: 532 */     else if (command.equals("NICK"))
/*  519:     */     {
/*  520: 534 */       String newNick = target;
/*  521: 535 */       renameUser(sourceNick, newNick);
/*  522: 536 */       if (sourceNick.equals(getNick())) {
/*  523: 538 */         setNick(newNick);
/*  524:     */       }
/*  525: 540 */       onNickChange(sourceNick, sourceLogin, sourceHostname, newNick);
/*  526:     */     }
/*  527: 542 */     else if (command.equals("NOTICE"))
/*  528:     */     {
/*  529: 544 */       onNotice(sourceNick, sourceLogin, sourceHostname, target, line.substring(line.indexOf(" :") + 2));
/*  530:     */     }
/*  531: 546 */     else if (command.equals("QUIT"))
/*  532:     */     {
/*  533: 548 */       if (sourceNick.equals(getNick())) {
/*  534: 549 */         removeAllChannels();
/*  535:     */       } else {
/*  536: 552 */         removeUser(sourceNick);
/*  537:     */       }
/*  538: 554 */       onQuit(sourceNick, sourceLogin, sourceHostname, line.substring(line.indexOf(" :") + 2));
/*  539:     */     }
/*  540: 556 */     else if (command.equals("KICK"))
/*  541:     */     {
/*  542: 558 */       String recipient = tokenizer.nextToken();
/*  543: 559 */       if (recipient.equals(getNick())) {
/*  544: 560 */         removeChannel(target);
/*  545:     */       }
/*  546: 562 */       removeUser(target, recipient);
/*  547: 563 */       onKick(target, sourceNick, sourceLogin, sourceHostname, recipient, line.substring(line.indexOf(" :") + 2));
/*  548:     */     }
/*  549: 565 */     else if (command.equals("MODE"))
/*  550:     */     {
/*  551: 567 */       String mode = line.substring(line.indexOf(target, 2) + target.length() + 1);
/*  552: 568 */       if (mode.startsWith(":")) {
/*  553: 569 */         mode = mode.substring(1);
/*  554:     */       }
/*  555: 571 */       processMode(target, sourceNick, sourceLogin, sourceHostname, mode);
/*  556:     */     }
/*  557: 573 */     else if (command.equals("TOPIC"))
/*  558:     */     {
/*  559: 575 */       onTopic(target, line.substring(line.indexOf(" :") + 2), sourceNick, System.currentTimeMillis(), true);
/*  560:     */     }
/*  561: 577 */     else if (command.equals("INVITE"))
/*  562:     */     {
/*  563: 579 */       onInvite(target, sourceNick, sourceLogin, sourceHostname, line.substring(line.indexOf(" :") + 2));
/*  564:     */     }
/*  565:     */     else
/*  566:     */     {
/*  567: 583 */       onUnknown(line);
/*  568:     */     }
/*  569:     */   }
/*  570:     */   
/*  571:     */   protected void onConnect() {}
/*  572:     */   
/*  573:     */   protected void onDisconnect() {}
/*  574:     */   
/*  575:     */   private final void processServerResponse(int code, String response)
/*  576:     */   {
/*  577: 597 */     if (code == 322)
/*  578:     */     {
/*  579: 599 */       int firstSpace = response.indexOf(' ');
/*  580: 600 */       int secondSpace = response.indexOf(' ', firstSpace + 1);
/*  581: 601 */       int thirdSpace = response.indexOf(' ', secondSpace + 1);
/*  582: 602 */       int colon = response.indexOf(':');
/*  583: 603 */       String channel = response.substring(firstSpace + 1, secondSpace);
/*  584: 604 */       int userCount = 0;
/*  585:     */       try
/*  586:     */       {
/*  587: 606 */         userCount = Integer.parseInt(response.substring(secondSpace + 1, thirdSpace));
/*  588:     */       }
/*  589:     */       catch (NumberFormatException localNumberFormatException) {}
/*  590: 611 */       String topic = response.substring(colon + 1);
/*  591: 612 */       onChannelInfo(channel, userCount, topic);
/*  592:     */     }
/*  593: 614 */     else if (code == 332)
/*  594:     */     {
/*  595: 616 */       int firstSpace = response.indexOf(' ');
/*  596: 617 */       int secondSpace = response.indexOf(' ', firstSpace + 1);
/*  597: 618 */       int colon = response.indexOf(':');
/*  598: 619 */       String channel = response.substring(firstSpace + 1, secondSpace);
/*  599: 620 */       String topic = response.substring(colon + 1);
/*  600:     */       
/*  601: 622 */       this._topics.put(channel, topic);
/*  602:     */       
/*  603: 624 */       onTopic(channel, topic);
/*  604:     */     }
/*  605: 626 */     else if (code == 333)
/*  606:     */     {
/*  607: 627 */       StringTokenizer tokenizer = new StringTokenizer(response);
/*  608: 628 */       tokenizer.nextToken();
/*  609: 629 */       String channel = tokenizer.nextToken();
/*  610: 630 */       String setBy = tokenizer.nextToken();
/*  611: 631 */       long date = 0L;
/*  612:     */       try
/*  613:     */       {
/*  614: 633 */         date = Long.parseLong(tokenizer.nextToken()) * 1000L;
/*  615:     */       }
/*  616:     */       catch (NumberFormatException localNumberFormatException1) {}
/*  617: 639 */       String topic = (String)this._topics.get(channel);
/*  618: 640 */       this._topics.remove(channel);
/*  619:     */       
/*  620: 642 */       onTopic(channel, topic, setBy, date, false);
/*  621:     */     }
/*  622: 644 */     else if (code == 353)
/*  623:     */     {
/*  624: 646 */       int channelEndIndex = response.indexOf(" :");
/*  625: 647 */       String channel = response.substring(response.lastIndexOf(' ', channelEndIndex - 1) + 1, channelEndIndex);
/*  626:     */       
/*  627: 649 */       StringTokenizer tokenizer = new StringTokenizer(response.substring(response.indexOf(" :") + 2));
/*  628: 650 */       while (tokenizer.hasMoreTokens())
/*  629:     */       {
/*  630: 651 */         String nick = tokenizer.nextToken();
/*  631: 652 */         String prefix = "";
/*  632: 653 */         if (nick.startsWith("@")) {
/*  633: 655 */           prefix = "@";
/*  634: 657 */         } else if (nick.startsWith("+")) {
/*  635: 659 */           prefix = "+";
/*  636: 661 */         } else if (nick.startsWith(".")) {
/*  637: 663 */           prefix = ".";
/*  638:     */         }
/*  639: 665 */         nick = nick.substring(prefix.length());
/*  640: 666 */         addUser(channel, new User(prefix, nick));
/*  641:     */       }
/*  642:     */     }
/*  643: 669 */     else if (code == 366)
/*  644:     */     {
/*  645: 671 */       String channel = response.substring(response.indexOf(' ') + 1, response.indexOf(" :"));
/*  646: 672 */       User[] users = getUsers(channel);
/*  647: 673 */       onUserList(channel, users);
/*  648:     */     }
/*  649: 676 */     onServerResponse(code, response);
/*  650:     */   }
/*  651:     */   
/*  652:     */   protected void onServerResponse(int code, String response) {}
/*  653:     */   
/*  654:     */   protected void onUserList(String channel, User[] users) {}
/*  655:     */   
/*  656:     */   protected void onMessage(String channel, String sender, String login, String hostname, String message) {}
/*  657:     */   
/*  658:     */   protected void onPrivateMessage(String sender, String login, String hostname, String message) {}
/*  659:     */   
/*  660:     */   protected void onAction(String sender, String login, String hostname, String target, String action) {}
/*  661:     */   
/*  662:     */   protected void onNotice(String sourceNick, String sourceLogin, String sourceHostname, String target, String notice) {}
/*  663:     */   
/*  664:     */   protected void onJoin(String channel, String sender, String login, String hostname) {}
/*  665:     */   
/*  666:     */   protected void onPart(String channel, String sender, String login, String hostname) {}
/*  667:     */   
/*  668:     */   protected void onNickChange(String oldNick, String login, String hostname, String newNick) {}
/*  669:     */   
/*  670:     */   protected void onKick(String channel, String kickerNick, String kickerLogin, String kickerHostname, String recipientNick, String reason) {}
/*  671:     */   
/*  672:     */   protected void onQuit(String sourceNick, String sourceLogin, String sourceHostname, String reason) {}
/*  673:     */   
/*  674:     */   /**
/*  675:     */    * @deprecated
/*  676:     */    */
/*  677:     */   protected void onTopic(String channel, String topic) {}
/*  678:     */   
/*  679:     */   protected void onTopic(String channel, String topic, String setBy, long date, boolean changed) {}
/*  680:     */   
/*  681:     */   protected void onChannelInfo(String channel, int userCount, String topic) {}
/*  682:     */   
/*  683:     */   private final void processMode(String target, String sourceNick, String sourceLogin, String sourceHostname, String mode)
/*  684:     */   {
/*  685: 738 */     if (this._channelPrefixes.indexOf(target.charAt(0)) >= 0)
/*  686:     */     {
/*  687: 740 */       String channel = target;
/*  688: 741 */       StringTokenizer tok = new StringTokenizer(mode);
/*  689: 742 */       String[] params = new String[tok.countTokens()];
/*  690:     */       
/*  691: 744 */       int t = 0;
/*  692: 745 */       while (tok.hasMoreTokens())
/*  693:     */       {
/*  694: 746 */         params[t] = tok.nextToken();
/*  695: 747 */         t++;
/*  696:     */       }
/*  697: 750 */       char pn = ' ';
/*  698: 751 */       int p = 1;
/*  699: 753 */       for (int i = 0; i < params[0].length(); i++)
/*  700:     */       {
/*  701: 754 */         char atPos = params[0].charAt(i);
/*  702: 756 */         if ((atPos == '+') || (atPos == '-'))
/*  703:     */         {
/*  704: 757 */           pn = atPos;
/*  705:     */         }
/*  706: 759 */         else if (atPos == 'o')
/*  707:     */         {
/*  708: 760 */           if (pn == '+')
/*  709:     */           {
/*  710: 761 */             updateUser(channel, 1, params[p]);
/*  711: 762 */             onOp(channel, sourceNick, sourceLogin, sourceHostname, params[p]);
/*  712:     */           }
/*  713:     */           else
/*  714:     */           {
/*  715: 765 */             updateUser(channel, 2, params[p]);
/*  716: 766 */             onDeop(channel, sourceNick, sourceLogin, sourceHostname, params[p]);
/*  717:     */           }
/*  718: 768 */           p++;
/*  719:     */         }
/*  720: 770 */         else if (atPos == 'v')
/*  721:     */         {
/*  722: 771 */           if (pn == '+')
/*  723:     */           {
/*  724: 772 */             updateUser(channel, 3, params[p]);
/*  725: 773 */             onVoice(channel, sourceNick, sourceLogin, sourceHostname, params[p]);
/*  726:     */           }
/*  727:     */           else
/*  728:     */           {
/*  729: 776 */             updateUser(channel, 4, params[p]);
/*  730: 777 */             onDeVoice(channel, sourceNick, sourceLogin, sourceHostname, params[p]);
/*  731:     */           }
/*  732: 779 */           p++;
/*  733:     */         }
/*  734: 781 */         else if (atPos == 'k')
/*  735:     */         {
/*  736: 782 */           if (pn == '+') {
/*  737: 783 */             onSetChannelKey(channel, sourceNick, sourceLogin, sourceHostname, params[p]);
/*  738:     */           } else {
/*  739: 786 */             onRemoveChannelKey(channel, sourceNick, sourceLogin, sourceHostname, params[p]);
/*  740:     */           }
/*  741: 788 */           p++;
/*  742:     */         }
/*  743: 790 */         else if (atPos == 'l')
/*  744:     */         {
/*  745: 791 */           if (pn == '+')
/*  746:     */           {
/*  747: 792 */             onSetChannelLimit(channel, sourceNick, sourceLogin, sourceHostname, Integer.parseInt(params[p]));
/*  748: 793 */             p++;
/*  749:     */           }
/*  750:     */           else
/*  751:     */           {
/*  752: 796 */             onRemoveChannelLimit(channel, sourceNick, sourceLogin, sourceHostname);
/*  753:     */           }
/*  754:     */         }
/*  755: 799 */         else if (atPos == 'b')
/*  756:     */         {
/*  757: 800 */           if (pn == '+') {
/*  758: 801 */             onSetChannelBan(channel, sourceNick, sourceLogin, sourceHostname, params[p]);
/*  759:     */           } else {
/*  760: 804 */             onRemoveChannelBan(channel, sourceNick, sourceLogin, sourceHostname, params[p]);
/*  761:     */           }
/*  762: 806 */           p++;
/*  763:     */         }
/*  764: 808 */         else if (atPos == 't')
/*  765:     */         {
/*  766: 809 */           if (pn == '+') {
/*  767: 810 */             onSetTopicProtection(channel, sourceNick, sourceLogin, sourceHostname);
/*  768:     */           } else {
/*  769: 813 */             onRemoveTopicProtection(channel, sourceNick, sourceLogin, sourceHostname);
/*  770:     */           }
/*  771:     */         }
/*  772: 816 */         else if (atPos == 'n')
/*  773:     */         {
/*  774: 817 */           if (pn == '+') {
/*  775: 818 */             onSetNoExternalMessages(channel, sourceNick, sourceLogin, sourceHostname);
/*  776:     */           } else {
/*  777: 821 */             onRemoveNoExternalMessages(channel, sourceNick, sourceLogin, sourceHostname);
/*  778:     */           }
/*  779:     */         }
/*  780: 824 */         else if (atPos == 'i')
/*  781:     */         {
/*  782: 825 */           if (pn == '+') {
/*  783: 826 */             onSetInviteOnly(channel, sourceNick, sourceLogin, sourceHostname);
/*  784:     */           } else {
/*  785: 829 */             onRemoveInviteOnly(channel, sourceNick, sourceLogin, sourceHostname);
/*  786:     */           }
/*  787:     */         }
/*  788: 832 */         else if (atPos == 'm')
/*  789:     */         {
/*  790: 833 */           if (pn == '+') {
/*  791: 834 */             onSetModerated(channel, sourceNick, sourceLogin, sourceHostname);
/*  792:     */           } else {
/*  793: 837 */             onRemoveModerated(channel, sourceNick, sourceLogin, sourceHostname);
/*  794:     */           }
/*  795:     */         }
/*  796: 840 */         else if (atPos == 'p')
/*  797:     */         {
/*  798: 841 */           if (pn == '+') {
/*  799: 842 */             onSetPrivate(channel, sourceNick, sourceLogin, sourceHostname);
/*  800:     */           } else {
/*  801: 845 */             onRemovePrivate(channel, sourceNick, sourceLogin, sourceHostname);
/*  802:     */           }
/*  803:     */         }
/*  804: 848 */         else if (atPos == 's')
/*  805:     */         {
/*  806: 849 */           if (pn == '+') {
/*  807: 850 */             onSetSecret(channel, sourceNick, sourceLogin, sourceHostname);
/*  808:     */           } else {
/*  809: 853 */             onRemoveSecret(channel, sourceNick, sourceLogin, sourceHostname);
/*  810:     */           }
/*  811:     */         }
/*  812:     */       }
/*  813: 858 */       onMode(channel, sourceNick, sourceLogin, sourceHostname, mode);
/*  814:     */     }
/*  815:     */     else
/*  816:     */     {
/*  817: 862 */       String nick = target;
/*  818: 863 */       onUserMode(nick, sourceNick, sourceLogin, sourceHostname, mode);
/*  819:     */     }
/*  820:     */   }
/*  821:     */   
/*  822:     */   protected void onMode(String channel, String sourceNick, String sourceLogin, String sourceHostname, String mode) {}
/*  823:     */   
/*  824:     */   protected void onUserMode(String targetNick, String sourceNick, String sourceLogin, String sourceHostname, String mode) {}
/*  825:     */   
/*  826:     */   protected void onOp(String channel, String sourceNick, String sourceLogin, String sourceHostname, String recipient) {}
/*  827:     */   
/*  828:     */   protected void onDeop(String channel, String sourceNick, String sourceLogin, String sourceHostname, String recipient) {}
/*  829:     */   
/*  830:     */   protected void onVoice(String channel, String sourceNick, String sourceLogin, String sourceHostname, String recipient) {}
/*  831:     */   
/*  832:     */   protected void onDeVoice(String channel, String sourceNick, String sourceLogin, String sourceHostname, String recipient) {}
/*  833:     */   
/*  834:     */   protected void onSetChannelKey(String channel, String sourceNick, String sourceLogin, String sourceHostname, String key) {}
/*  835:     */   
/*  836:     */   protected void onRemoveChannelKey(String channel, String sourceNick, String sourceLogin, String sourceHostname, String key) {}
/*  837:     */   
/*  838:     */   protected void onSetChannelLimit(String channel, String sourceNick, String sourceLogin, String sourceHostname, int limit) {}
/*  839:     */   
/*  840:     */   protected void onRemoveChannelLimit(String channel, String sourceNick, String sourceLogin, String sourceHostname) {}
/*  841:     */   
/*  842:     */   protected void onSetChannelBan(String channel, String sourceNick, String sourceLogin, String sourceHostname, String hostmask) {}
/*  843:     */   
/*  844:     */   protected void onRemoveChannelBan(String channel, String sourceNick, String sourceLogin, String sourceHostname, String hostmask) {}
/*  845:     */   
/*  846:     */   protected void onSetTopicProtection(String channel, String sourceNick, String sourceLogin, String sourceHostname) {}
/*  847:     */   
/*  848:     */   protected void onRemoveTopicProtection(String channel, String sourceNick, String sourceLogin, String sourceHostname) {}
/*  849:     */   
/*  850:     */   protected void onSetNoExternalMessages(String channel, String sourceNick, String sourceLogin, String sourceHostname) {}
/*  851:     */   
/*  852:     */   protected void onRemoveNoExternalMessages(String channel, String sourceNick, String sourceLogin, String sourceHostname) {}
/*  853:     */   
/*  854:     */   protected void onSetInviteOnly(String channel, String sourceNick, String sourceLogin, String sourceHostname) {}
/*  855:     */   
/*  856:     */   protected void onRemoveInviteOnly(String channel, String sourceNick, String sourceLogin, String sourceHostname) {}
/*  857:     */   
/*  858:     */   protected void onSetModerated(String channel, String sourceNick, String sourceLogin, String sourceHostname) {}
/*  859:     */   
/*  860:     */   protected void onRemoveModerated(String channel, String sourceNick, String sourceLogin, String sourceHostname) {}
/*  861:     */   
/*  862:     */   protected void onSetPrivate(String channel, String sourceNick, String sourceLogin, String sourceHostname) {}
/*  863:     */   
/*  864:     */   protected void onRemovePrivate(String channel, String sourceNick, String sourceLogin, String sourceHostname) {}
/*  865:     */   
/*  866:     */   protected void onSetSecret(String channel, String sourceNick, String sourceLogin, String sourceHostname) {}
/*  867:     */   
/*  868:     */   protected void onRemoveSecret(String channel, String sourceNick, String sourceLogin, String sourceHostname) {}
/*  869:     */   
/*  870:     */   protected void onInvite(String targetNick, String sourceNick, String sourceLogin, String sourceHostname, String channel) {}
/*  871:     */   
/*  872:     */   /**
/*  873:     */    * @deprecated
/*  874:     */    */
/*  875:     */   protected void onDccSendRequest(String sourceNick, String sourceLogin, String sourceHostname, String filename, long address, int port, int size) {}
/*  876:     */   
/*  877:     */   /**
/*  878:     */    * @deprecated
/*  879:     */    */
/*  880:     */   protected void onDccChatRequest(String sourceNick, String sourceLogin, String sourceHostname, long address, int port) {}
/*  881:     */   
/*  882:     */   protected void onIncomingFileTransfer(DccFileTransfer transfer) {}
/*  883:     */   
/*  884:     */   protected void onFileTransferFinished(DccFileTransfer transfer, Exception e) {}
/*  885:     */   
/*  886:     */   protected void onIncomingChatRequest(DccChat chat) {}
/*  887:     */   
/*  888:     */   protected void onVersion(String sourceNick, String sourceLogin, String sourceHostname, String target)
/*  889:     */   {
/*  890: 991 */     sendRawLine("NOTICE " + sourceNick + " :\001VERSION " + this._version + "\001");
/*  891:     */   }
/*  892:     */   
/*  893:     */   protected void onPing(String sourceNick, String sourceLogin, String sourceHostname, String target, String pingValue)
/*  894:     */   {
/*  895: 996 */     sendRawLine("NOTICE " + sourceNick + " :\001PING " + pingValue + "\001");
/*  896:     */   }
/*  897:     */   
/*  898:     */   protected void onServerPing(String response)
/*  899:     */   {
/*  900:1001 */     sendRawLine("PONG " + response);
/*  901:     */   }
/*  902:     */   
/*  903:     */   protected void onTime(String sourceNick, String sourceLogin, String sourceHostname, String target)
/*  904:     */   {
/*  905:1006 */     sendRawLine("NOTICE " + sourceNick + " :\001TIME " + new Date().toString() + "\001");
/*  906:     */   }
/*  907:     */   
/*  908:     */   protected void onFinger(String sourceNick, String sourceLogin, String sourceHostname, String target)
/*  909:     */   {
/*  910:1011 */     sendRawLine("NOTICE " + sourceNick + " :\001FINGER " + this._finger + "\001");
/*  911:     */   }
/*  912:     */   
/*  913:     */   protected void onUnknown(String line) {}
/*  914:     */   
/*  915:     */   public final void setVerbose(boolean verbose)
/*  916:     */   {
/*  917:1020 */     this._verbose = verbose;
/*  918:     */   }
/*  919:     */   
/*  920:     */   protected final void setName(String name)
/*  921:     */   {
/*  922:1025 */     this._name = name;
/*  923:     */   }
/*  924:     */   
/*  925:     */   private final void setNick(String nick)
/*  926:     */   {
/*  927:1030 */     this._nick = nick;
/*  928:     */   }
/*  929:     */   
/*  930:     */   protected final void setLogin(String login)
/*  931:     */   {
/*  932:1035 */     this._login = login;
/*  933:     */   }
/*  934:     */   
/*  935:     */   protected final void setVersion(String version)
/*  936:     */   {
/*  937:1040 */     this._version = version;
/*  938:     */   }
/*  939:     */   
/*  940:     */   protected final void setFinger(String finger)
/*  941:     */   {
/*  942:1045 */     this._finger = finger;
/*  943:     */   }
/*  944:     */   
/*  945:     */   public final String getName()
/*  946:     */   {
/*  947:1050 */     return this._name;
/*  948:     */   }
/*  949:     */   
/*  950:     */   public String getNick()
/*  951:     */   {
/*  952:1055 */     return this._nick;
/*  953:     */   }
/*  954:     */   
/*  955:     */   public final String getLogin()
/*  956:     */   {
/*  957:1060 */     return this._login;
/*  958:     */   }
/*  959:     */   
/*  960:     */   public final String getVersion()
/*  961:     */   {
/*  962:1065 */     return this._version;
/*  963:     */   }
/*  964:     */   
/*  965:     */   public final String getFinger()
/*  966:     */   {
/*  967:1070 */     return this._finger;
/*  968:     */   }
/*  969:     */   
/*  970:     */   public final synchronized boolean isConnected()
/*  971:     */   {
/*  972:1075 */     return (this._inputThread != null) && (this._inputThread.isConnected());
/*  973:     */   }
/*  974:     */   
/*  975:     */   public final void setMessageDelay(long delay)
/*  976:     */   {
/*  977:1080 */     if (delay < 0L) {
/*  978:1081 */       throw new IllegalArgumentException("Cannot have a negative time.");
/*  979:     */     }
/*  980:1083 */     this._messageDelay = delay;
/*  981:     */   }
/*  982:     */   
/*  983:     */   public final long getMessageDelay()
/*  984:     */   {
/*  985:1088 */     return this._messageDelay;
/*  986:     */   }
/*  987:     */   
/*  988:     */   public final int getMaxLineLength()
/*  989:     */   {
/*  990:1093 */     return 512;
/*  991:     */   }
/*  992:     */   
/*  993:     */   public final int getOutgoingQueueSize()
/*  994:     */   {
/*  995:1098 */     return this._outQueue.size();
/*  996:     */   }
/*  997:     */   
/*  998:     */   public final String getServer()
/*  999:     */   {
/* 1000:1103 */     return this._server;
/* 1001:     */   }
/* 1002:     */   
/* 1003:     */   public final int getPort()
/* 1004:     */   {
/* 1005:1108 */     return this._port;
/* 1006:     */   }
/* 1007:     */   
/* 1008:     */   public final String getPassword()
/* 1009:     */   {
/* 1010:1113 */     return this._password;
/* 1011:     */   }
/* 1012:     */   
/* 1013:     */   public int[] longToIp(long address)
/* 1014:     */   {
/* 1015:1118 */     int[] ip = new int[4];
/* 1016:1119 */     for (int i = 3; i >= 0; i--)
/* 1017:     */     {
/* 1018:1120 */       ip[i] = ((int)(address % 256L));
/* 1019:1121 */       address /= 256L;
/* 1020:     */     }
/* 1021:1123 */     return ip;
/* 1022:     */   }
/* 1023:     */   
/* 1024:     */   public long ipToLong(byte[] address)
/* 1025:     */   {
/* 1026:1128 */     if (address.length != 4) {
/* 1027:1129 */       throw new IllegalArgumentException("byte array must be of length 4");
/* 1028:     */     }
/* 1029:1131 */     long ipNum = 0L;
/* 1030:1132 */     long multiplier = 1L;
/* 1031:1133 */     for (int i = 3; i >= 0; i--)
/* 1032:     */     {
/* 1033:1134 */       int byteVal = (address[i] + 256) % 256;
/* 1034:1135 */       ipNum += byteVal * multiplier;
/* 1035:1136 */       multiplier *= 256L;
/* 1036:     */     }
/* 1037:1138 */     return ipNum;
/* 1038:     */   }
/* 1039:     */   
/* 1040:     */   public void setEncoding(String charset)
/* 1041:     */     throws UnsupportedEncodingException
/* 1042:     */   {
/* 1043:1144 */     "".getBytes(charset);
/* 1044:     */     
/* 1045:1146 */     this._charset = charset;
/* 1046:     */   }
/* 1047:     */   
/* 1048:     */   public String getEncoding()
/* 1049:     */   {
/* 1050:1151 */     return this._charset;
/* 1051:     */   }
/* 1052:     */   
/* 1053:     */   public InetAddress getInetAddress()
/* 1054:     */   {
/* 1055:1156 */     return this._inetAddress;
/* 1056:     */   }
/* 1057:     */   
/* 1058:     */   public void setDccInetAddress(InetAddress dccInetAddress)
/* 1059:     */   {
/* 1060:1161 */     this._dccInetAddress = dccInetAddress;
/* 1061:     */   }
/* 1062:     */   
/* 1063:     */   public InetAddress getDccInetAddress()
/* 1064:     */   {
/* 1065:1166 */     return this._dccInetAddress;
/* 1066:     */   }
/* 1067:     */   
/* 1068:     */   public int[] getDccPorts()
/* 1069:     */   {
/* 1070:1171 */     if ((this._dccPorts == null) || (this._dccPorts.length == 0)) {
/* 1071:1172 */       return null;
/* 1072:     */     }
/* 1073:1175 */     return (int[])this._dccPorts.clone();
/* 1074:     */   }
/* 1075:     */   
/* 1076:     */   public void setDccPorts(int[] ports)
/* 1077:     */   {
/* 1078:1180 */     if ((ports == null) || (ports.length == 0)) {
/* 1079:1181 */       this._dccPorts = null;
/* 1080:     */     } else {
/* 1081:1185 */       this._dccPorts = ((int[])ports.clone());
/* 1082:     */     }
/* 1083:     */   }
/* 1084:     */   
/* 1085:     */   public boolean equals(Object o)
/* 1086:     */   {
/* 1087:1191 */     if ((o instanceof PircBot))
/* 1088:     */     {
/* 1089:1192 */       PircBot other = (PircBot)o;
/* 1090:1193 */       return other == this;
/* 1091:     */     }
/* 1092:1195 */     return false;
/* 1093:     */   }
/* 1094:     */   
/* 1095:     */   public int hashCode()
/* 1096:     */   {
/* 1097:1200 */     return super.hashCode();
/* 1098:     */   }
/* 1099:     */   
/* 1100:     */   public String toString()
/* 1101:     */   {
/* 1102:1205 */     return 
/* 1103:     */     
/* 1104:     */ 
/* 1105:     */ 
/* 1106:1209 */       "Version{" + this._version + "}" + " Connected{" + isConnected() + "}" + " Server{" + this._server + "}" + " Port{" + this._port + "}" + " Password{" + this._password + "}";
/* 1107:     */   }
/* 1108:     */   
/* 1109:     */   public final User[] getUsers(String channel)
/* 1110:     */   {
/* 1111:1214 */     channel = channel.toLowerCase();
/* 1112:1215 */     User[] userArray = new User[0];
/* 1113:1216 */     synchronized (this._channels)
/* 1114:     */     {
/* 1115:1217 */       Hashtable users = (Hashtable)this._channels.get(channel);
/* 1116:1218 */       if (users != null)
/* 1117:     */       {
/* 1118:1219 */         userArray = new User[users.size()];
/* 1119:1220 */         Enumeration enumeration = users.elements();
/* 1120:1221 */         for (int i = 0; i < userArray.length; i++)
/* 1121:     */         {
/* 1122:1222 */           User user = (User)enumeration.nextElement();
/* 1123:1223 */           userArray[i] = user;
/* 1124:     */         }
/* 1125:     */       }
/* 1126:     */     }
/* 1127:1227 */     return userArray;
/* 1128:     */   }
/* 1129:     */   
/* 1130:     */   public final String[] getChannels()
/* 1131:     */   {
/* 1132:1232 */     String[] channels = new String[0];
/* 1133:1233 */     synchronized (this._channels)
/* 1134:     */     {
/* 1135:1234 */       channels = new String[this._channels.size()];
/* 1136:1235 */       Enumeration enumeration = this._channels.keys();
/* 1137:1236 */       for (int i = 0; i < channels.length; i++) {
/* 1138:1237 */         channels[i] = ((String)enumeration.nextElement());
/* 1139:     */       }
/* 1140:     */     }
/* 1141:1240 */     return channels;
/* 1142:     */   }
/* 1143:     */   
/* 1144:     */   public synchronized void dispose()
/* 1145:     */   {
/* 1146:1245 */     this._outputThread.interrupt();
/* 1147:1246 */     this._inputThread.dispose();
/* 1148:     */   }
/* 1149:     */   
/* 1150:     */   private final void addUser(String channel, User user)
/* 1151:     */   {
/* 1152:1251 */     channel = channel.toLowerCase();
/* 1153:1252 */     synchronized (this._channels)
/* 1154:     */     {
/* 1155:1253 */       Hashtable users = (Hashtable)this._channels.get(channel);
/* 1156:1254 */       if (users == null)
/* 1157:     */       {
/* 1158:1255 */         users = new Hashtable();
/* 1159:1256 */         this._channels.put(channel, users);
/* 1160:     */       }
/* 1161:1258 */       users.put(user, user);
/* 1162:     */     }
/* 1163:     */   }
/* 1164:     */   
/* 1165:     */   private final User removeUser(String channel, String nick)
/* 1166:     */   {
/* 1167:1264 */     channel = channel.toLowerCase();
/* 1168:1265 */     User user = new User("", nick);
/* 1169:1266 */     synchronized (this._channels)
/* 1170:     */     {
/* 1171:1267 */       Hashtable users = (Hashtable)this._channels.get(channel);
/* 1172:1268 */       if (users != null) {
/* 1173:1269 */         return (User)users.remove(user);
/* 1174:     */       }
/* 1175:     */     }
/* 1176:1272 */     return null;
/* 1177:     */   }
/* 1178:     */   
/* 1179:     */   private final void removeUser(String nick)
/* 1180:     */   {
/* 1181:1277 */     synchronized (this._channels)
/* 1182:     */     {
/* 1183:1278 */       Enumeration enumeration = this._channels.keys();
/* 1184:1279 */       while (enumeration.hasMoreElements())
/* 1185:     */       {
/* 1186:1280 */         String channel = (String)enumeration.nextElement();
/* 1187:1281 */         removeUser(channel, nick);
/* 1188:     */       }
/* 1189:     */     }
/* 1190:     */   }
/* 1191:     */   
/* 1192:     */   private final void renameUser(String oldNick, String newNick)
/* 1193:     */   {
/* 1194:1288 */     synchronized (this._channels)
/* 1195:     */     {
/* 1196:1289 */       Enumeration enumeration = this._channels.keys();
/* 1197:1290 */       while (enumeration.hasMoreElements())
/* 1198:     */       {
/* 1199:1291 */         String channel = (String)enumeration.nextElement();
/* 1200:1292 */         User user = removeUser(channel, oldNick);
/* 1201:1293 */         if (user != null)
/* 1202:     */         {
/* 1203:1294 */           user = new User(user.getPrefix(), newNick);
/* 1204:1295 */           addUser(channel, user);
/* 1205:     */         }
/* 1206:     */       }
/* 1207:     */     }
/* 1208:     */   }
/* 1209:     */   
/* 1210:     */   private final void removeChannel(String channel)
/* 1211:     */   {
/* 1212:1303 */     channel = channel.toLowerCase();
/* 1213:1304 */     synchronized (this._channels)
/* 1214:     */     {
/* 1215:1305 */       this._channels.remove(channel);
/* 1216:     */     }
/* 1217:     */   }
/* 1218:     */   
/* 1219:     */   private final void removeAllChannels()
/* 1220:     */   {
/* 1221:1311 */     synchronized (this._channels)
/* 1222:     */     {
/* 1223:1312 */       this._channels = new Hashtable();
/* 1224:     */     }
/* 1225:     */   }
/* 1226:     */   
/* 1227:     */   private final void updateUser(String channel, int userMode, String nick)
/* 1228:     */   {
/* 1229:1318 */     channel = channel.toLowerCase();
/* 1230:1319 */     synchronized (this._channels)
/* 1231:     */     {
/* 1232:1320 */       Hashtable users = (Hashtable)this._channels.get(channel);
/* 1233:1321 */       User newUser = null;
/* 1234:1322 */       if (users != null)
/* 1235:     */       {
/* 1236:1323 */         Enumeration enumeration = users.elements();
/* 1237:1324 */         while (enumeration.hasMoreElements())
/* 1238:     */         {
/* 1239:1325 */           User userObj = (User)enumeration.nextElement();
/* 1240:1326 */           if (userObj.getNick().equalsIgnoreCase(nick)) {
/* 1241:1327 */             if (userMode == 1)
/* 1242:     */             {
/* 1243:1328 */               if (userObj.hasVoice()) {
/* 1244:1329 */                 newUser = new User("@+", nick);
/* 1245:     */               } else {
/* 1246:1332 */                 newUser = new User("@", nick);
/* 1247:     */               }
/* 1248:     */             }
/* 1249:1335 */             else if (userMode == 2)
/* 1250:     */             {
/* 1251:1336 */               if (userObj.hasVoice()) {
/* 1252:1337 */                 newUser = new User("+", nick);
/* 1253:     */               } else {
/* 1254:1340 */                 newUser = new User("", nick);
/* 1255:     */               }
/* 1256:     */             }
/* 1257:1343 */             else if (userMode == 3)
/* 1258:     */             {
/* 1259:1344 */               if (userObj.isOp()) {
/* 1260:1345 */                 newUser = new User("@+", nick);
/* 1261:     */               } else {
/* 1262:1348 */                 newUser = new User("+", nick);
/* 1263:     */               }
/* 1264:     */             }
/* 1265:1351 */             else if (userMode == 4) {
/* 1266:1352 */               if (userObj.isOp()) {
/* 1267:1353 */                 newUser = new User("@", nick);
/* 1268:     */               } else {
/* 1269:1356 */                 newUser = new User("", nick);
/* 1270:     */               }
/* 1271:     */             }
/* 1272:     */           }
/* 1273:     */         }
/* 1274:     */       }
/* 1275:1362 */       if (newUser != null)
/* 1276:     */       {
/* 1277:1363 */         users.put(newUser, newUser);
/* 1278:     */       }
/* 1279:     */       else
/* 1280:     */       {
/* 1281:1367 */         newUser = new User("", nick);
/* 1282:1368 */         users.put(newUser, newUser);
/* 1283:     */       }
/* 1284:     */     }
/* 1285:     */   }
/* 1286:     */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.irc.lib.PircBot
 * JD-Core Version:    0.7.0.1
 */