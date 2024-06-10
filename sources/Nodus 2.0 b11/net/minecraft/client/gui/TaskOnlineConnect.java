/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import io.netty.util.concurrent.GenericFutureListener;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.net.InetAddress;
/*   6:    */ import java.net.UnknownHostException;
/*   7:    */ import java.util.concurrent.atomic.AtomicInteger;
/*   8:    */ import me.connorm.Nodus.Nodus;
/*   9:    */ import net.minecraft.client.Minecraft;
/*  10:    */ import net.minecraft.client.mco.ExceptionMcoService;
/*  11:    */ import net.minecraft.client.mco.ExceptionRetryCall;
/*  12:    */ import net.minecraft.client.mco.McoClient;
/*  13:    */ import net.minecraft.client.mco.McoServer;
/*  14:    */ import net.minecraft.client.mco.McoServerAddress;
/*  15:    */ import net.minecraft.client.multiplayer.ServerAddress;
/*  16:    */ import net.minecraft.client.network.NetHandlerLoginClient;
/*  17:    */ import net.minecraft.client.resources.I18n;
/*  18:    */ import net.minecraft.network.EnumConnectionState;
/*  19:    */ import net.minecraft.network.NetworkManager;
/*  20:    */ import net.minecraft.network.handshake.client.C00Handshake;
/*  21:    */ import net.minecraft.network.login.client.C00PacketLoginStart;
/*  22:    */ import net.minecraft.util.ChatComponentTranslation;
/*  23:    */ import net.minecraft.util.Session;
/*  24:    */ import org.apache.logging.log4j.LogManager;
/*  25:    */ import org.apache.logging.log4j.Logger;
/*  26:    */ 
/*  27:    */ public class TaskOnlineConnect
/*  28:    */   extends TaskLongRunning
/*  29:    */ {
/*  30: 32 */   private static final AtomicInteger field_148439_a = new AtomicInteger(0);
/*  31: 33 */   private static final Logger logger = LogManager.getLogger();
/*  32:    */   private NetworkManager field_148436_d;
/*  33:    */   private final McoServer field_148437_e;
/*  34:    */   private final GuiScreen field_148435_f;
/*  35:    */   private static final String __OBFID = "CL_00000790";
/*  36:    */   
/*  37:    */   public TaskOnlineConnect(GuiScreen par1GuiScreen, McoServer par2McoServer)
/*  38:    */   {
/*  39: 41 */     this.field_148435_f = par1GuiScreen;
/*  40: 42 */     this.field_148437_e = par2McoServer;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void run()
/*  44:    */   {
/*  45: 47 */     func_148417_b(I18n.format("mco.connect.connecting", new Object[0]));
/*  46: 48 */     Session var1 = func_148413_b().getSession();
/*  47: 49 */     McoClient var2 = new McoClient(var1.getSessionID(), var1.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
/*  48: 50 */     boolean var3 = false;
/*  49: 51 */     boolean var4 = false;
/*  50: 52 */     int var5 = 5;
/*  51: 53 */     McoServerAddress var6 = null;
/*  52: 54 */     boolean var7 = false;
/*  53: 56 */     for (int var8 = 0; (var8 < 10) && (!func_148418_c()); var8++)
/*  54:    */     {
/*  55:    */       try
/*  56:    */       {
/*  57: 60 */         var6 = var2.func_148688_b(this.field_148437_e.field_148812_a);
/*  58: 61 */         var3 = true;
/*  59:    */       }
/*  60:    */       catch (ExceptionRetryCall var10)
/*  61:    */       {
/*  62: 65 */         var5 = var10.field_148832_d;
/*  63:    */       }
/*  64:    */       catch (ExceptionMcoService var11)
/*  65:    */       {
/*  66: 69 */         if (var11.field_148830_c == 6002)
/*  67:    */         {
/*  68: 71 */           var7 = true;
/*  69: 72 */           break;
/*  70:    */         }
/*  71: 75 */         var4 = true;
/*  72: 76 */         func_148416_a(var11.toString());
/*  73: 77 */         logger.error("Couldn't connect to world", var11);
/*  74:    */         
/*  75:    */ 
/*  76: 80 */         break;
/*  77:    */       }
/*  78:    */       catch (IOException var12)
/*  79:    */       {
/*  80: 84 */         logger.error("Couldn't parse response connecting to world", var12);
/*  81:    */       }
/*  82:    */       catch (Exception var13)
/*  83:    */       {
/*  84: 88 */         var4 = true;
/*  85: 89 */         logger.error("Couldn't connect to world", var13);
/*  86: 90 */         func_148416_a(var13.getLocalizedMessage());
/*  87:    */       }
/*  88: 93 */       if (var3) {
/*  89:    */         break;
/*  90:    */       }
/*  91: 98 */       func_148429_a(var5);
/*  92:    */     }
/*  93:101 */     if (var7) {
/*  94:103 */       func_148413_b().displayGuiScreen(new GuiScreenReamlsTOS(this.field_148435_f, this.field_148437_e));
/*  95:105 */     } else if ((!func_148418_c()) && (!var4)) {
/*  96:107 */       if (var3)
/*  97:    */       {
/*  98:109 */         ServerAddress var14 = ServerAddress.func_78860_a(var6.field_148770_a);
/*  99:110 */         func_148432_a(var14.getIP(), var14.getPort());
/* 100:    */       }
/* 101:    */       else
/* 102:    */       {
/* 103:114 */         func_148413_b().displayGuiScreen(this.field_148435_f);
/* 104:    */       }
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   private void func_148429_a(int p_148429_1_)
/* 109:    */   {
/* 110:    */     try
/* 111:    */     {
/* 112:123 */       Thread.sleep(p_148429_1_ * 1000);
/* 113:    */     }
/* 114:    */     catch (InterruptedException var3)
/* 115:    */     {
/* 116:127 */       logger.warn(var3.getLocalizedMessage());
/* 117:    */     }
/* 118:    */   }
/* 119:    */   
/* 120:    */   private void func_148432_a(final String p_148432_1_, final int p_148432_2_)
/* 121:    */   {
/* 122:191 */     new Thread("MCO Connector #" + field_148439_a.incrementAndGet())
/* 123:    */     {
/* 124:    */       private static final String __OBFID = "CL_00000791";
/* 125:    */       
/* 126:    */       public void run()
/* 127:    */       {
/* 128:    */         try
/* 129:    */         {
/* 130:140 */           if (TaskOnlineConnect.this.func_148418_c()) {
/* 131:142 */             return;
/* 132:    */           }
/* 133:145 */           TaskOnlineConnect.this.field_148436_d = NetworkManager.provideLanClient(InetAddress.getByName(p_148432_1_), p_148432_2_);
/* 134:147 */           if (TaskOnlineConnect.this.func_148418_c()) {
/* 135:149 */             return;
/* 136:    */           }
/* 137:152 */           TaskOnlineConnect.this.field_148436_d.setNetHandler(new NetHandlerLoginClient(TaskOnlineConnect.this.field_148436_d, TaskOnlineConnect.this.func_148413_b(), TaskOnlineConnect.this.field_148435_f));
/* 138:154 */           if (TaskOnlineConnect.this.func_148418_c()) {
/* 139:156 */             return;
/* 140:    */           }
/* 141:160 */           TaskOnlineConnect.this.field_148436_d.scheduleOutboundPacket(new C00Handshake(Nodus.theNodus.getMinecraftVersion(), p_148432_1_, p_148432_2_, EnumConnectionState.LOGIN), new GenericFutureListener[0]);
/* 142:162 */           if (TaskOnlineConnect.this.func_148418_c()) {
/* 143:164 */             return;
/* 144:    */           }
/* 145:167 */           TaskOnlineConnect.this.field_148436_d.scheduleOutboundPacket(new C00PacketLoginStart(TaskOnlineConnect.this.func_148413_b().getSession().func_148256_e()), new GenericFutureListener[0]);
/* 146:168 */           TaskOnlineConnect.this.func_148417_b(I18n.format("mco.connect.authorizing", new Object[0]));
/* 147:    */         }
/* 148:    */         catch (UnknownHostException var2)
/* 149:    */         {
/* 150:172 */           if (TaskOnlineConnect.this.func_148418_c()) {
/* 151:174 */             return;
/* 152:    */           }
/* 153:177 */           TaskOnlineConnect.logger.error("Couldn't connect to world", var2);
/* 154:178 */           TaskOnlineConnect.this.func_148413_b().displayGuiScreen(new GuiScreenDisconnectedOnline(TaskOnlineConnect.this.field_148435_f, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[] { "Unknown host '" + p_148432_1_ + "'" })));
/* 155:    */         }
/* 156:    */         catch (Exception var3)
/* 157:    */         {
/* 158:182 */           if (TaskOnlineConnect.this.func_148418_c()) {
/* 159:184 */             return;
/* 160:    */           }
/* 161:187 */           TaskOnlineConnect.logger.error("Couldn't connect to world", var3);
/* 162:188 */           TaskOnlineConnect.this.func_148413_b().displayGuiScreen(new GuiScreenDisconnectedOnline(TaskOnlineConnect.this.field_148435_f, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[] { var3.toString() })));
/* 163:    */         }
/* 164:    */       }
/* 165:    */     }.start();
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void func_148414_a()
/* 169:    */   {
/* 170:196 */     if (this.field_148436_d != null) {
/* 171:198 */       this.field_148436_d.processReceivedPackets();
/* 172:    */     }
/* 173:    */   }
/* 174:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.TaskOnlineConnect
 * JD-Core Version:    0.7.0.1
 */