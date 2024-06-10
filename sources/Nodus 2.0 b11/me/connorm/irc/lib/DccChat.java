/*   1:    */ package me.connorm.irc.lib;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.BufferedWriter;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.InputStreamReader;
/*   7:    */ import java.io.OutputStreamWriter;
/*   8:    */ import java.net.Socket;
/*   9:    */ 
/*  10:    */ public class DccChat
/*  11:    */ {
/*  12:    */   private PircBot _bot;
/*  13:    */   private String _nick;
/*  14:    */   
/*  15:    */   DccChat(PircBot bot, String nick, String login, String hostname, long address, int port)
/*  16:    */   {
/*  17: 44 */     this._bot = bot;
/*  18: 45 */     this._address = address;
/*  19: 46 */     this._port = port;
/*  20: 47 */     this._nick = nick;
/*  21: 48 */     this._login = login;
/*  22: 49 */     this._hostname = hostname;
/*  23: 50 */     this._acceptable = true;
/*  24:    */   }
/*  25:    */   
/*  26:    */   DccChat(PircBot bot, String nick, Socket socket)
/*  27:    */     throws IOException
/*  28:    */   {
/*  29: 66 */     this._bot = bot;
/*  30: 67 */     this._nick = nick;
/*  31: 68 */     this._socket = socket;
/*  32: 69 */     this._reader = new BufferedReader(new InputStreamReader(this._socket.getInputStream()));
/*  33: 70 */     this._writer = new BufferedWriter(new OutputStreamWriter(this._socket.getOutputStream()));
/*  34: 71 */     this._acceptable = false;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public synchronized void accept()
/*  38:    */     throws IOException
/*  39:    */   {
/*  40: 82 */     if (this._acceptable)
/*  41:    */     {
/*  42: 83 */       this._acceptable = false;
/*  43: 84 */       int[] ip = this._bot.longToIp(this._address);
/*  44: 85 */       String ipStr = ip[0] + "." + ip[1] + "." + ip[2] + "." + ip[3];
/*  45: 86 */       this._socket = new Socket(ipStr, this._port);
/*  46: 87 */       this._reader = new BufferedReader(new InputStreamReader(this._socket.getInputStream()));
/*  47: 88 */       this._writer = new BufferedWriter(new OutputStreamWriter(this._socket.getOutputStream()));
/*  48:    */     }
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String readLine()
/*  52:    */     throws IOException
/*  53:    */   {
/*  54:104 */     if (this._acceptable) {
/*  55:105 */       throw new IOException("You must call the accept() method of the DccChat request before you can use it.");
/*  56:    */     }
/*  57:107 */     return this._reader.readLine();
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void sendLine(String line)
/*  61:    */     throws IOException
/*  62:    */   {
/*  63:121 */     if (this._acceptable) {
/*  64:122 */       throw new IOException("You must call the accept() method of the DccChat request before you can use it.");
/*  65:    */     }
/*  66:125 */     this._writer.write(line + "\r\n");
/*  67:126 */     this._writer.flush();
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void close()
/*  71:    */     throws IOException
/*  72:    */   {
/*  73:136 */     if (this._acceptable) {
/*  74:137 */       throw new IOException("You must call the accept() method of the DccChat request before you can use it.");
/*  75:    */     }
/*  76:139 */     this._socket.close();
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String getNick()
/*  80:    */   {
/*  81:150 */     return this._nick;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String getLogin()
/*  85:    */   {
/*  86:161 */     return this._login;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String getHostname()
/*  90:    */   {
/*  91:172 */     return this._hostname;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public BufferedReader getBufferedReader()
/*  95:    */   {
/*  96:182 */     return this._reader;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public BufferedWriter getBufferedWriter()
/* 100:    */   {
/* 101:192 */     return this._writer;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public Socket getSocket()
/* 105:    */   {
/* 106:202 */     return this._socket;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public long getNumericalAddress()
/* 110:    */   {
/* 111:212 */     return this._address;
/* 112:    */   }
/* 113:    */   
/* 114:218 */   private String _login = null;
/* 115:219 */   private String _hostname = null;
/* 116:    */   private BufferedReader _reader;
/* 117:    */   private BufferedWriter _writer;
/* 118:    */   private Socket _socket;
/* 119:    */   private boolean _acceptable;
/* 120:224 */   private long _address = 0L;
/* 121:225 */   private int _port = 0;
/* 122:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.irc.lib.DccChat
 * JD-Core Version:    0.7.0.1
 */