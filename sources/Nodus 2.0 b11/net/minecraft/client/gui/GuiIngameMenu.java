/*  1:   */ package net.minecraft.client.gui;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import me.connorm.Nodus.ui.NodusGuiButton;
/*  5:   */ import me.connorm.Nodus.ui.NodusGuiMainMenu;
/*  6:   */ import net.minecraft.client.Minecraft;
/*  7:   */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*  8:   */ import net.minecraft.client.gui.achievement.GuiAchievements;
/*  9:   */ import net.minecraft.client.gui.achievement.GuiStats;
/* 10:   */ import net.minecraft.client.multiplayer.WorldClient;
/* 11:   */ import net.minecraft.client.resources.I18n;
/* 12:   */ import net.minecraft.server.integrated.IntegratedServer;
/* 13:   */ 
/* 14:   */ public class GuiIngameMenu
/* 15:   */   extends GuiScreen
/* 16:   */ {
/* 17:   */   private int field_146445_a;
/* 18:   */   private int field_146444_f;
/* 19:   */   private static final String __OBFID = "CL_00000703";
/* 20:   */   
/* 21:   */   public void initGui()
/* 22:   */   {
/* 23:21 */     this.field_146445_a = 0;
/* 24:22 */     this.buttonList.clear();
/* 25:23 */     byte var1 = -16;
/* 26:24 */     boolean var2 = true;
/* 27:25 */     this.buttonList.add(new NodusGuiButton(1, width / 2 - 100, height / 4 + 120 + var1, I18n.format("menu.returnToMenu", new Object[0])));
/* 28:27 */     if (!this.mc.isIntegratedServerRunning()) {
/* 29:29 */       ((NodusGuiButton)this.buttonList.get(0)).displayString = I18n.format("menu.disconnect", new Object[0]);
/* 30:   */     }
/* 31:32 */     this.buttonList.add(new NodusGuiButton(4, width / 2 - 100, height / 4 + 24 + var1, I18n.format("menu.returnToGame", new Object[0])));
/* 32:33 */     this.buttonList.add(new NodusGuiButton(0, width / 2 - 100, height / 4 + 96 + var1, 98, 20, I18n.format("menu.options", new Object[0])));
/* 33:   */     NodusGuiButton var3;
/* 34:35 */     this.buttonList.add(var3 = new NodusGuiButton(7, width / 2 + 2, height / 4 + 96 + var1, 98, 20, I18n.format("menu.shareToLan", new Object[0])));
/* 35:36 */     this.buttonList.add(new NodusGuiButton(5, width / 2 - 100, height / 4 + 48 + var1, 98, 20, I18n.format("gui.achievements", new Object[0])));
/* 36:37 */     this.buttonList.add(new NodusGuiButton(6, width / 2 + 2, height / 4 + 48 + var1, 98, 20, I18n.format("gui.stats", new Object[0])));
/* 37:38 */     var3.enabled = ((this.mc.isSingleplayer()) && (!this.mc.getIntegratedServer().getPublic()));
/* 38:   */   }
/* 39:   */   
/* 40:   */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/* 41:   */   {
/* 42:43 */     switch (p_146284_1_.id)
/* 43:   */     {
/* 44:   */     case 0: 
/* 45:46 */       this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
/* 46:47 */       break;
/* 47:   */     case 1: 
/* 48:50 */       p_146284_1_.enabled = false;
/* 49:51 */       this.mc.theWorld.sendQuittingDisconnectingPacket();
/* 50:52 */       this.mc.loadWorld(null);
/* 51:53 */       this.mc.displayGuiScreen(new NodusGuiMainMenu());
/* 52:   */     case 2: 
/* 53:   */     case 3: 
/* 54:   */     default: 
/* 55:   */       break;
/* 56:   */     case 4: 
/* 57:61 */       this.mc.displayGuiScreen(null);
/* 58:62 */       this.mc.setIngameFocus();
/* 59:63 */       break;
/* 60:   */     case 5: 
/* 61:66 */       this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.thePlayer.func_146107_m()));
/* 62:67 */       break;
/* 63:   */     case 6: 
/* 64:70 */       this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.func_146107_m()));
/* 65:71 */       break;
/* 66:   */     case 7: 
/* 67:74 */       this.mc.displayGuiScreen(new GuiShareToLan(this));
/* 68:   */     }
/* 69:   */   }
/* 70:   */   
/* 71:   */   public void updateScreen()
/* 72:   */   {
/* 73:83 */     super.updateScreen();
/* 74:84 */     this.field_146444_f += 1;
/* 75:   */   }
/* 76:   */   
/* 77:   */   public void drawScreen(int par1, int par2, float par3)
/* 78:   */   {
/* 79:92 */     drawDefaultBackground();
/* 80:93 */     drawCenteredString(this.fontRendererObj, "Game menu", width / 2, 40, 16777215);
/* 81:94 */     super.drawScreen(par1, par2, par3);
/* 82:   */   }
/* 83:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiIngameMenu
 * JD-Core Version:    0.7.0.1
 */