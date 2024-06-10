/*   1:    */ package me.connorm.irc.lib;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.BufferedWriter;
/*   5:    */ import java.io.InterruptedIOException;
/*   6:    */ import java.io.PrintWriter;
/*   7:    */ import java.io.StringWriter;
/*   8:    */ import java.net.Socket;
/*   9:    */ import java.util.StringTokenizer;
/*  10:    */ 
/*  11:    */ public class InputThread
/*  12:    */   extends Thread
/*  13:    */ {
/*  14:    */   InputThread(PircBot bot, Socket socket, BufferedReader breader, BufferedWriter bwriter)
/*  15:    */   {
/*  16: 42 */     this._bot = bot;
/*  17: 43 */     this._socket = socket;
/*  18: 44 */     this._breader = breader;
/*  19: 45 */     this._bwriter = bwriter;
/*  20: 46 */     setName(getClass() + "-Thread");
/*  21:    */   }
/*  22:    */   
/*  23:    */   void sendRawLine(String line)
/*  24:    */   {
/*  25: 57 */     OutputThread.sendRawLine(this._bot, this._bwriter, line);
/*  26:    */   }
/*  27:    */   
/*  28:    */   boolean isConnected()
/*  29:    */   {
/*  30: 69 */     return this._isConnected;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void run()
/*  34:    */   {
/*  35:    */     try
/*  36:    */     {
/*  37: 86 */       boolean running = true;
/*  38: 87 */       while (running) {
/*  39:    */         try
/*  40:    */         {
/*  41: 89 */           String line = null;
/*  42: 90 */           while ((line = this._breader.readLine()) != null) {
/*  43:    */             try
/*  44:    */             {
/*  45: 92 */               this._bot.handleLine(line);
/*  46:    */             }
/*  47:    */             catch (Throwable t)
/*  48:    */             {
/*  49: 96 */               StringWriter sw = new StringWriter();
/*  50: 97 */               PrintWriter pw = new PrintWriter(sw);
/*  51: 98 */               t.printStackTrace(pw);
/*  52: 99 */               pw.flush();
/*  53:100 */               StringTokenizer tokenizer = new StringTokenizer(sw.toString(), "\r\n");
/*  54:101 */               synchronized (this._bot)
/*  55:    */               {
/*  56:102 */                 this._bot.log("### Your implementation of PircBot is faulty and you have");
/*  57:103 */                 this._bot.log("### allowed an uncaught Exception or Error to propagate in your");
/*  58:104 */                 this._bot.log("### code. It may be possible for PircBot to continue operating");
/*  59:105 */                 this._bot.log("### normally. Here is the stack trace that was produced: -");
/*  60:106 */                 this._bot.log("### ");
/*  61:107 */                 while (tokenizer.hasMoreTokens()) {
/*  62:108 */                   this._bot.log("### " + tokenizer.nextToken());
/*  63:    */                 }
/*  64:    */               }
/*  65:    */             }
/*  66:    */           }
/*  67:113 */           if (line == null) {
/*  68:115 */             running = false;
/*  69:    */           }
/*  70:    */         }
/*  71:    */         catch (InterruptedIOException iioe)
/*  72:    */         {
/*  73:121 */           sendRawLine("PING " + System.currentTimeMillis() / 1000L);
/*  74:    */         }
/*  75:    */       }
/*  76:    */     }
/*  77:    */     catch (Exception localException) {}
/*  78:    */     try
/*  79:    */     {
/*  80:132 */       this._socket.close();
/*  81:    */     }
/*  82:    */     catch (Exception localException1) {}
/*  83:138 */     if (!this._disposed)
/*  84:    */     {
/*  85:139 */       this._bot.log("*** Disconnected.");
/*  86:140 */       this._isConnected = false;
/*  87:141 */       this._bot.onDisconnect();
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void dispose()
/*  92:    */   {
/*  93:    */     try
/*  94:    */     {
/*  95:152 */       this._disposed = true;
/*  96:153 */       this._socket.close();
/*  97:    */     }
/*  98:    */     catch (Exception localException) {}
/*  99:    */   }
/* 100:    */   
/* 101:160 */   private PircBot _bot = null;
/* 102:161 */   private Socket _socket = null;
/* 103:162 */   private BufferedReader _breader = null;
/* 104:163 */   private BufferedWriter _bwriter = null;
/* 105:164 */   private boolean _isConnected = true;
/* 106:165 */   private boolean _disposed = false;
/* 107:    */   public static final int MAX_LINE_LENGTH = 512;
/* 108:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.irc.lib.InputThread
 * JD-Core Version:    0.7.0.1
 */