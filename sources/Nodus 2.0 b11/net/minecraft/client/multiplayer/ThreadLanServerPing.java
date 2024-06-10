/*   1:    */ package net.minecraft.client.multiplayer;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.net.DatagramPacket;
/*   5:    */ import java.net.DatagramSocket;
/*   6:    */ import java.net.InetAddress;
/*   7:    */ import java.util.concurrent.atomic.AtomicInteger;
/*   8:    */ import org.apache.logging.log4j.LogManager;
/*   9:    */ import org.apache.logging.log4j.Logger;
/*  10:    */ 
/*  11:    */ public class ThreadLanServerPing
/*  12:    */   extends Thread
/*  13:    */ {
/*  14: 13 */   private static final AtomicInteger field_148658_a = new AtomicInteger(0);
/*  15: 14 */   private static final Logger logger = LogManager.getLogger();
/*  16:    */   private final String motd;
/*  17:    */   private final DatagramSocket socket;
/*  18: 19 */   private boolean isStopping = true;
/*  19:    */   private final String address;
/*  20:    */   private static final String __OBFID = "CL_00001137";
/*  21:    */   
/*  22:    */   public ThreadLanServerPing(String par1Str, String par2Str)
/*  23:    */     throws IOException
/*  24:    */   {
/*  25: 25 */     super("LanServerPinger #" + field_148658_a.incrementAndGet());
/*  26: 26 */     this.motd = par1Str;
/*  27: 27 */     this.address = par2Str;
/*  28: 28 */     setDaemon(true);
/*  29: 29 */     this.socket = new DatagramSocket();
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void run()
/*  33:    */   {
/*  34: 34 */     String var1 = getPingResponse(this.motd, this.address);
/*  35: 35 */     byte[] var2 = var1.getBytes();
/*  36: 37 */     while ((!isInterrupted()) && (this.isStopping))
/*  37:    */     {
/*  38:    */       try
/*  39:    */       {
/*  40: 41 */         InetAddress var3 = InetAddress.getByName("224.0.2.60");
/*  41: 42 */         DatagramPacket var4 = new DatagramPacket(var2, var2.length, var3, 4445);
/*  42: 43 */         this.socket.send(var4);
/*  43:    */       }
/*  44:    */       catch (IOException var6)
/*  45:    */       {
/*  46: 47 */         logger.warn("LanServerPinger: " + var6.getMessage());
/*  47: 48 */         break;
/*  48:    */       }
/*  49:    */       try
/*  50:    */       {
/*  51: 53 */         sleep(1500L);
/*  52:    */       }
/*  53:    */       catch (InterruptedException localInterruptedException) {}
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void interrupt()
/*  58:    */   {
/*  59: 64 */     super.interrupt();
/*  60: 65 */     this.isStopping = false;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public static String getPingResponse(String par0Str, String par1Str)
/*  64:    */   {
/*  65: 70 */     return "[MOTD]" + par0Str + "[/MOTD][AD]" + par1Str + "[/AD]";
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static String getMotdFromPingResponse(String par0Str)
/*  69:    */   {
/*  70: 75 */     int var1 = par0Str.indexOf("[MOTD]");
/*  71: 77 */     if (var1 < 0) {
/*  72: 79 */       return "missing no";
/*  73:    */     }
/*  74: 83 */     int var2 = par0Str.indexOf("[/MOTD]", var1 + "[MOTD]".length());
/*  75: 84 */     return var2 < var1 ? "missing no" : par0Str.substring(var1 + "[MOTD]".length(), var2);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public static String getAdFromPingResponse(String par0Str)
/*  79:    */   {
/*  80: 90 */     int var1 = par0Str.indexOf("[/MOTD]");
/*  81: 92 */     if (var1 < 0) {
/*  82: 94 */       return null;
/*  83:    */     }
/*  84: 98 */     int var2 = par0Str.indexOf("[/MOTD]", var1 + "[/MOTD]".length());
/*  85:100 */     if (var2 >= 0) {
/*  86:102 */       return null;
/*  87:    */     }
/*  88:106 */     int var3 = par0Str.indexOf("[AD]", var1 + "[/MOTD]".length());
/*  89:108 */     if (var3 < 0) {
/*  90:110 */       return null;
/*  91:    */     }
/*  92:114 */     int var4 = par0Str.indexOf("[/AD]", var3 + "[AD]".length());
/*  93:115 */     return var4 < var3 ? null : par0Str.substring(var3 + "[AD]".length(), var4);
/*  94:    */   }
/*  95:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.multiplayer.ThreadLanServerPing
 * JD-Core Version:    0.7.0.1
 */