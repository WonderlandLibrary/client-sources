/*   1:    */ package me.connorm.irc.lib;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.BufferedWriter;
/*   5:    */ import java.io.InputStreamReader;
/*   6:    */ import java.io.OutputStreamWriter;
/*   7:    */ import java.net.ServerSocket;
/*   8:    */ import java.net.Socket;
/*   9:    */ 
/*  10:    */ public class IdentServer
/*  11:    */   extends Thread
/*  12:    */ {
/*  13:    */   private PircBot _bot;
/*  14:    */   private String _login;
/*  15:    */   
/*  16:    */   IdentServer(PircBot bot, String login)
/*  17:    */   {
/*  18: 60 */     this._bot = bot;
/*  19: 61 */     this._login = login;
/*  20:    */     try
/*  21:    */     {
/*  22: 64 */       this._ss = new ServerSocket(113);
/*  23: 65 */       this._ss.setSoTimeout(60000);
/*  24:    */     }
/*  25:    */     catch (Exception e)
/*  26:    */     {
/*  27: 68 */       this._bot.log("*** Could not start the ident server on port 113.");
/*  28: 69 */       return;
/*  29:    */     }
/*  30: 72 */     this._bot.log("*** Ident server running on port 113 for the next 60 seconds...");
/*  31: 73 */     setName(getClass() + "-Thread");
/*  32: 74 */     start();
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void run()
/*  36:    */   {
/*  37:    */     try
/*  38:    */     {
/*  39: 85 */       Socket socket = this._ss.accept();
/*  40: 86 */       socket.setSoTimeout(60000);
/*  41:    */       
/*  42: 88 */       BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
/*  43: 89 */       BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
/*  44:    */       
/*  45: 91 */       String line = reader.readLine();
/*  46: 92 */       if (line != null)
/*  47:    */       {
/*  48: 93 */         this._bot.log("*** Ident request received: " + line);
/*  49: 94 */         line = line + " : USERID : UNIX : " + this._login;
/*  50: 95 */         writer.write(line + "\r\n");
/*  51: 96 */         writer.flush();
/*  52: 97 */         this._bot.log("*** Ident reply sent: " + line);
/*  53: 98 */         writer.close();
/*  54:    */       }
/*  55:    */     }
/*  56:    */     catch (Exception localException) {}
/*  57:    */     try
/*  58:    */     {
/*  59:106 */       this._ss.close();
/*  60:    */     }
/*  61:    */     catch (Exception localException1) {}
/*  62:112 */     this._bot.log("*** The Ident server has been shut down.");
/*  63:    */   }
/*  64:    */   
/*  65:117 */   private ServerSocket _ss = null;
/*  66:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.irc.lib.IdentServer
 * JD-Core Version:    0.7.0.1
 */