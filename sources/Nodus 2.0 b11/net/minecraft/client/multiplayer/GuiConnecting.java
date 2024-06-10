/*   1:    */ package net.minecraft.client.multiplayer;
/*   2:    */ 
/*   3:    */ import io.netty.util.concurrent.GenericFutureListener;
/*   4:    */ import java.net.InetAddress;
/*   5:    */ import java.net.UnknownHostException;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.concurrent.atomic.AtomicInteger;
/*   8:    */ import me.connorm.Nodus.Nodus;
/*   9:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*  10:    */ import net.minecraft.client.Minecraft;
/*  11:    */ import net.minecraft.client.gui.GuiDisconnected;
/*  12:    */ import net.minecraft.client.gui.GuiScreen;
/*  13:    */ import net.minecraft.client.network.NetHandlerLoginClient;
/*  14:    */ import net.minecraft.client.resources.I18n;
/*  15:    */ import net.minecraft.network.EnumConnectionState;
/*  16:    */ import net.minecraft.network.INetHandler;
/*  17:    */ import net.minecraft.network.NetworkManager;
/*  18:    */ import net.minecraft.network.handshake.client.C00Handshake;
/*  19:    */ import net.minecraft.network.login.client.C00PacketLoginStart;
/*  20:    */ import net.minecraft.util.ChatComponentText;
/*  21:    */ import net.minecraft.util.ChatComponentTranslation;
/*  22:    */ import net.minecraft.util.Session;
/*  23:    */ import org.apache.logging.log4j.LogManager;
/*  24:    */ import org.apache.logging.log4j.Logger;
/*  25:    */ 
/*  26:    */ public class GuiConnecting
/*  27:    */   extends GuiScreen
/*  28:    */ {
/*  29: 28 */   private static final AtomicInteger field_146372_a = new AtomicInteger(0);
/*  30: 29 */   private static final Logger logger = LogManager.getLogger();
/*  31:    */   private NetworkManager field_146371_g;
/*  32:    */   private boolean field_146373_h;
/*  33:    */   private final GuiScreen field_146374_i;
/*  34:    */   private static final String __OBFID = "CL_00000685";
/*  35:    */   
/*  36:    */   public GuiConnecting(GuiScreen par1GuiScreen, Minecraft par2Minecraft, ServerData par3ServerData)
/*  37:    */   {
/*  38: 37 */     this.mc = par2Minecraft;
/*  39: 38 */     this.field_146374_i = par1GuiScreen;
/*  40: 39 */     ServerAddress var4 = ServerAddress.func_78860_a(par3ServerData.serverIP);
/*  41: 40 */     par2Minecraft.loadWorld(null);
/*  42: 41 */     par2Minecraft.setServerData(par3ServerData);
/*  43: 42 */     func_146367_a(var4.getIP(), var4.getPort());
/*  44:    */   }
/*  45:    */   
/*  46:    */   public GuiConnecting(GuiScreen par1GuiScreen, Minecraft par2Minecraft, String par3Str, int par4)
/*  47:    */   {
/*  48: 47 */     this.mc = par2Minecraft;
/*  49: 48 */     this.field_146374_i = par1GuiScreen;
/*  50: 49 */     par2Minecraft.loadWorld(null);
/*  51: 50 */     func_146367_a(par3Str, par4);
/*  52:    */   }
/*  53:    */   
/*  54:    */   private void func_146367_a(final String p_146367_1_, final int p_146367_2_)
/*  55:    */   {
/*  56: 55 */     logger.info("Connecting to " + p_146367_1_ + ", " + p_146367_2_);
/*  57: 56 */     new Thread("Server Connector #" + field_146372_a.incrementAndGet())
/*  58:    */     {
/*  59:    */       private static final String __OBFID = "CL_00000686";
/*  60:    */       
/*  61:    */       public void run()
/*  62:    */       {
/*  63:    */         try
/*  64:    */         {
/*  65: 63 */           if (GuiConnecting.this.field_146373_h) {
/*  66: 65 */             return;
/*  67:    */           }
/*  68: 68 */           GuiConnecting.this.field_146371_g = NetworkManager.provideLanClient(InetAddress.getByName(p_146367_1_), p_146367_2_);
/*  69: 69 */           GuiConnecting.this.field_146371_g.setNetHandler(new NetHandlerLoginClient(GuiConnecting.this.field_146371_g, GuiConnecting.this.mc, GuiConnecting.this.field_146374_i));
/*  70:    */           
/*  71: 71 */           GuiConnecting.this.field_146371_g.scheduleOutboundPacket(new C00Handshake(Nodus.theNodus.getMinecraftVersion(), p_146367_1_, p_146367_2_, EnumConnectionState.LOGIN), new GenericFutureListener[0]);
/*  72: 72 */           GuiConnecting.this.field_146371_g.scheduleOutboundPacket(new C00PacketLoginStart(GuiConnecting.this.mc.getSession().func_148256_e()), new GenericFutureListener[0]);
/*  73:    */         }
/*  74:    */         catch (UnknownHostException var2)
/*  75:    */         {
/*  76: 76 */           if (GuiConnecting.this.field_146373_h) {
/*  77: 78 */             return;
/*  78:    */           }
/*  79: 81 */           GuiConnecting.logger.error("Couldn't connect to server", var2);
/*  80: 82 */           GuiConnecting.this.mc.displayGuiScreen(new GuiDisconnected(GuiConnecting.this.field_146374_i, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[] { "Unknown host '" + p_146367_1_ + "'" })));
/*  81:    */         }
/*  82:    */         catch (Exception var3)
/*  83:    */         {
/*  84: 86 */           if (GuiConnecting.this.field_146373_h) {
/*  85: 88 */             return;
/*  86:    */           }
/*  87: 91 */           GuiConnecting.logger.error("Couldn't connect to server", var3);
/*  88: 92 */           GuiConnecting.this.mc.displayGuiScreen(new GuiDisconnected(GuiConnecting.this.field_146374_i, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[] { var3.toString() })));
/*  89:    */         }
/*  90:    */       }
/*  91:    */     }.start();
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void updateScreen()
/*  95:    */   {
/*  96:103 */     if (this.field_146371_g != null) {
/*  97:105 */       if (this.field_146371_g.isChannelOpen()) {
/*  98:107 */         this.field_146371_g.processReceivedPackets();
/*  99:109 */       } else if (this.field_146371_g.getExitMessage() != null) {
/* 100:111 */         this.field_146371_g.getNetHandler().onDisconnect(this.field_146371_g.getExitMessage());
/* 101:    */       }
/* 102:    */     }
/* 103:    */   }
/* 104:    */   
/* 105:    */   protected void keyTyped(char par1, int par2) {}
/* 106:    */   
/* 107:    */   public void initGui()
/* 108:    */   {
/* 109:126 */     this.buttonList.clear();
/* 110:127 */     this.buttonList.add(new NodusGuiButton(0, width / 2 - 100, height / 4 + 120 + 12, I18n.format("gui.cancel", new Object[0])));
/* 111:    */   }
/* 112:    */   
/* 113:    */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/* 114:    */   {
/* 115:132 */     if (p_146284_1_.id == 0)
/* 116:    */     {
/* 117:134 */       this.field_146373_h = true;
/* 118:136 */       if (this.field_146371_g != null) {
/* 119:138 */         this.field_146371_g.closeChannel(new ChatComponentText("Aborted"));
/* 120:    */       }
/* 121:141 */       this.mc.displayGuiScreen(this.field_146374_i);
/* 122:    */     }
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void drawScreen(int par1, int par2, float par3)
/* 126:    */   {
/* 127:150 */     drawDefaultBackground();
/* 128:152 */     if (this.field_146371_g == null) {
/* 129:154 */       drawCenteredString(this.fontRendererObj, I18n.format("connect.connecting", new Object[0]), width / 2, height / 2 - 50, 16777215);
/* 130:    */     } else {
/* 131:158 */       drawCenteredString(this.fontRendererObj, I18n.format("connect.authorizing", new Object[0]), width / 2, height / 2 - 50, 16777215);
/* 132:    */     }
/* 133:161 */     super.drawScreen(par1, par2, par3);
/* 134:    */   }
/* 135:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.multiplayer.GuiConnecting
 * JD-Core Version:    0.7.0.1
 */