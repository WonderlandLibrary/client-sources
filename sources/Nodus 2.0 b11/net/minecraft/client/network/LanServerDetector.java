/*   1:    */ package net.minecraft.client.network;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.net.DatagramPacket;
/*   5:    */ import java.net.InetAddress;
/*   6:    */ import java.net.MulticastSocket;
/*   7:    */ import java.net.SocketTimeoutException;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Collections;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.concurrent.atomic.AtomicInteger;
/*  13:    */ import net.minecraft.client.Minecraft;
/*  14:    */ import net.minecraft.client.multiplayer.ThreadLanServerPing;
/*  15:    */ import org.apache.logging.log4j.LogManager;
/*  16:    */ import org.apache.logging.log4j.Logger;
/*  17:    */ 
/*  18:    */ public class LanServerDetector
/*  19:    */ {
/*  20: 20 */   private static final AtomicInteger field_148551_a = new AtomicInteger(0);
/*  21: 21 */   private static final Logger logger = LogManager.getLogger();
/*  22:    */   private static final String __OBFID = "CL_00001133";
/*  23:    */   
/*  24:    */   public static class ThreadLanServerFind
/*  25:    */     extends Thread
/*  26:    */   {
/*  27:    */     private final LanServerDetector.LanServerList localServerList;
/*  28:    */     private final InetAddress broadcastAddress;
/*  29:    */     private final MulticastSocket socket;
/*  30:    */     private static final String __OBFID = "CL_00001135";
/*  31:    */     
/*  32:    */     public ThreadLanServerFind(LanServerDetector.LanServerList par1LanServerList)
/*  33:    */       throws IOException
/*  34:    */     {
/*  35: 33 */       super();
/*  36: 34 */       this.localServerList = par1LanServerList;
/*  37: 35 */       setDaemon(true);
/*  38: 36 */       this.socket = new MulticastSocket(4445);
/*  39: 37 */       this.broadcastAddress = InetAddress.getByName("224.0.2.60");
/*  40: 38 */       this.socket.setSoTimeout(5000);
/*  41: 39 */       this.socket.joinGroup(this.broadcastAddress);
/*  42:    */     }
/*  43:    */     
/*  44:    */     public void run()
/*  45:    */     {
/*  46: 44 */       byte[] var2 = new byte[1024];
/*  47: 46 */       while (!isInterrupted())
/*  48:    */       {
/*  49: 48 */         DatagramPacket var1 = new DatagramPacket(var2, var2.length);
/*  50:    */         try
/*  51:    */         {
/*  52: 52 */           this.socket.receive(var1);
/*  53:    */         }
/*  54:    */         catch (SocketTimeoutException var5)
/*  55:    */         {
/*  56:    */           continue;
/*  57:    */         }
/*  58:    */         catch (IOException var6)
/*  59:    */         {
/*  60: 60 */           LanServerDetector.logger.error("Couldn't ping server", var6);
/*  61: 61 */           break;
/*  62:    */         }
/*  63: 64 */         String var3 = new String(var1.getData(), var1.getOffset(), var1.getLength());
/*  64: 65 */         LanServerDetector.logger.debug(var1.getAddress() + ": " + var3);
/*  65: 66 */         this.localServerList.func_77551_a(var3, var1.getAddress());
/*  66:    */       }
/*  67:    */       try
/*  68:    */       {
/*  69: 71 */         this.socket.leaveGroup(this.broadcastAddress);
/*  70:    */       }
/*  71:    */       catch (IOException localIOException1) {}
/*  72: 78 */       this.socket.close();
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static class LanServer
/*  77:    */   {
/*  78:    */     private String lanServerMotd;
/*  79:    */     private String lanServerIpPort;
/*  80:    */     private long timeLastSeen;
/*  81:    */     private static final String __OBFID = "CL_00001134";
/*  82:    */     
/*  83:    */     public LanServer(String par1Str, String par2Str)
/*  84:    */     {
/*  85: 91 */       this.lanServerMotd = par1Str;
/*  86: 92 */       this.lanServerIpPort = par2Str;
/*  87: 93 */       this.timeLastSeen = Minecraft.getSystemTime();
/*  88:    */     }
/*  89:    */     
/*  90:    */     public String getServerMotd()
/*  91:    */     {
/*  92: 98 */       return this.lanServerMotd;
/*  93:    */     }
/*  94:    */     
/*  95:    */     public String getServerIpPort()
/*  96:    */     {
/*  97:103 */       return this.lanServerIpPort;
/*  98:    */     }
/*  99:    */     
/* 100:    */     public void updateLastSeen()
/* 101:    */     {
/* 102:108 */       this.timeLastSeen = Minecraft.getSystemTime();
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   public static class LanServerList
/* 107:    */   {
/* 108:114 */     private ArrayList listOfLanServers = new ArrayList();
/* 109:    */     boolean wasUpdated;
/* 110:    */     private static final String __OBFID = "CL_00001136";
/* 111:    */     
/* 112:    */     public synchronized boolean getWasUpdated()
/* 113:    */     {
/* 114:120 */       return this.wasUpdated;
/* 115:    */     }
/* 116:    */     
/* 117:    */     public synchronized void setWasNotUpdated()
/* 118:    */     {
/* 119:125 */       this.wasUpdated = false;
/* 120:    */     }
/* 121:    */     
/* 122:    */     public synchronized List getLanServers()
/* 123:    */     {
/* 124:130 */       return Collections.unmodifiableList(this.listOfLanServers);
/* 125:    */     }
/* 126:    */     
/* 127:    */     public synchronized void func_77551_a(String par1Str, InetAddress par2InetAddress)
/* 128:    */     {
/* 129:135 */       String var3 = ThreadLanServerPing.getMotdFromPingResponse(par1Str);
/* 130:136 */       String var4 = ThreadLanServerPing.getAdFromPingResponse(par1Str);
/* 131:138 */       if (var4 != null)
/* 132:    */       {
/* 133:140 */         var4 = par2InetAddress.getHostAddress() + ":" + var4;
/* 134:141 */         boolean var5 = false;
/* 135:142 */         Iterator var6 = this.listOfLanServers.iterator();
/* 136:144 */         while (var6.hasNext())
/* 137:    */         {
/* 138:146 */           LanServerDetector.LanServer var7 = (LanServerDetector.LanServer)var6.next();
/* 139:148 */           if (var7.getServerIpPort().equals(var4))
/* 140:    */           {
/* 141:150 */             var7.updateLastSeen();
/* 142:151 */             var5 = true;
/* 143:152 */             break;
/* 144:    */           }
/* 145:    */         }
/* 146:156 */         if (!var5)
/* 147:    */         {
/* 148:158 */           this.listOfLanServers.add(new LanServerDetector.LanServer(var3, var4));
/* 149:159 */           this.wasUpdated = true;
/* 150:    */         }
/* 151:    */       }
/* 152:    */     }
/* 153:    */   }
/* 154:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.network.LanServerDetector
 * JD-Core Version:    0.7.0.1
 */