/*   1:    */ package me.connorm.irc.lib;
/*   2:    */ 
/*   3:    */ import java.io.BufferedWriter;
/*   4:    */ 
/*   5:    */ public class OutputThread
/*   6:    */   extends Thread
/*   7:    */ {
/*   8:    */   OutputThread(PircBot bot, Queue outQueue)
/*   9:    */   {
/*  10: 44 */     this._bot = bot;
/*  11: 45 */     this._outQueue = outQueue;
/*  12: 46 */     setName(getClass() + "-Thread");
/*  13:    */   }
/*  14:    */   
/*  15:    */   static void sendRawLine(PircBot bot, BufferedWriter bwriter, String line)
/*  16:    */   {
/*  17: 61 */     if (line.length() > bot.getMaxLineLength() - 2) {
/*  18: 62 */       line = line.substring(0, bot.getMaxLineLength() - 2);
/*  19:    */     }
/*  20: 64 */     synchronized (bwriter)
/*  21:    */     {
/*  22:    */       try
/*  23:    */       {
/*  24: 66 */         bwriter.write(line + "\r\n");
/*  25: 67 */         bwriter.flush();
/*  26: 68 */         bot.log(">>>" + line);
/*  27:    */       }
/*  28:    */       catch (Exception localException) {}
/*  29:    */     }
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void run()
/*  33:    */   {
/*  34:    */     try
/*  35:    */     {
/*  36: 83 */       boolean running = true;
/*  37: 84 */       while (running)
/*  38:    */       {
/*  39: 86 */         Thread.sleep(this._bot.getMessageDelay());
/*  40:    */         
/*  41: 88 */         String line = (String)this._outQueue.next();
/*  42: 89 */         if (line != null) {
/*  43: 90 */           this._bot.sendRawLine(line);
/*  44:    */         } else {
/*  45: 93 */           running = false;
/*  46:    */         }
/*  47:    */       }
/*  48:    */     }
/*  49:    */     catch (InterruptedException localInterruptedException) {}
/*  50:    */   }
/*  51:    */   
/*  52:102 */   private PircBot _bot = null;
/*  53:103 */   private Queue _outQueue = null;
/*  54:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.irc.lib.OutputThread
 * JD-Core Version:    0.7.0.1
 */