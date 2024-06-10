/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.List;
/*   5:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*   6:    */ import me.connorm.Nodus.ui.NodusGuiMainMenu;
/*   7:    */ import net.minecraft.client.Minecraft;
/*   8:    */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*   9:    */ import net.minecraft.client.multiplayer.WorldClient;
/*  10:    */ import net.minecraft.client.resources.I18n;
/*  11:    */ import net.minecraft.util.EnumChatFormatting;
/*  12:    */ import net.minecraft.world.storage.WorldInfo;
/*  13:    */ import org.lwjgl.opengl.GL11;
/*  14:    */ 
/*  15:    */ public class GuiGameOver
/*  16:    */   extends GuiScreen
/*  17:    */ {
/*  18:    */   private int field_146347_a;
/*  19: 16 */   private boolean field_146346_f = false;
/*  20:    */   private static final String __OBFID = "CL_00000690";
/*  21:    */   
/*  22:    */   public void initGui()
/*  23:    */   {
/*  24: 24 */     this.buttonList.clear();
/*  25: 26 */     if (this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled())
/*  26:    */     {
/*  27: 28 */       if (this.mc.isIntegratedServerRunning()) {
/*  28: 30 */         this.buttonList.add(new NodusGuiButton(1, width / 2 - 100, height / 4 + 96, I18n.format("deathScreen.deleteWorld", new Object[0])));
/*  29:    */       } else {
/*  30: 34 */         this.buttonList.add(new NodusGuiButton(1, width / 2 - 100, height / 4 + 96, I18n.format("deathScreen.leaveServer", new Object[0])));
/*  31:    */       }
/*  32:    */     }
/*  33:    */     else
/*  34:    */     {
/*  35: 39 */       this.buttonList.add(new NodusGuiButton(0, width / 2 - 100, height / 4 + 72, I18n.format("deathScreen.respawn", new Object[0])));
/*  36: 40 */       this.buttonList.add(new NodusGuiButton(1, width / 2 - 100, height / 4 + 96, I18n.format("deathScreen.titleScreen", new Object[0])));
/*  37: 42 */       if (this.mc.getSession() == null) {
/*  38: 44 */         ((NodusGuiButton)this.buttonList.get(1)).enabled = false;
/*  39:    */       }
/*  40:    */     }
/*  41:    */     NodusGuiButton var2;
/*  42: 50 */     for (Iterator var1 = this.buttonList.iterator(); var1.hasNext(); var2.enabled = false) {
/*  43: 52 */       var2 = (NodusGuiButton)var1.next();
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   protected void keyTyped(char par1, int par2) {}
/*  48:    */   
/*  49:    */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/*  50:    */   {
/*  51: 63 */     switch (p_146284_1_.id)
/*  52:    */     {
/*  53:    */     case 0: 
/*  54: 66 */       this.mc.thePlayer.respawnPlayer();
/*  55: 67 */       this.mc.displayGuiScreen(null);
/*  56: 68 */       break;
/*  57:    */     case 1: 
/*  58: 71 */       GuiYesNo var2 = new GuiYesNo(this, I18n.format("deathScreen.quit.confirm", new Object[0]), "", I18n.format("deathScreen.titleScreen", new Object[0]), I18n.format("deathScreen.respawn", new Object[0]), 0);
/*  59: 72 */       this.mc.displayGuiScreen(var2);
/*  60: 73 */       var2.func_146350_a(20);
/*  61:    */     }
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void confirmClicked(boolean par1, int par2)
/*  65:    */   {
/*  66: 79 */     if (par1)
/*  67:    */     {
/*  68: 81 */       this.mc.theWorld.sendQuittingDisconnectingPacket();
/*  69: 82 */       this.mc.loadWorld(null);
/*  70: 83 */       this.mc.displayGuiScreen(new NodusGuiMainMenu());
/*  71:    */     }
/*  72:    */     else
/*  73:    */     {
/*  74: 87 */       this.mc.thePlayer.respawnPlayer();
/*  75: 88 */       this.mc.displayGuiScreen(null);
/*  76:    */     }
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void drawScreen(int par1, int par2, float par3)
/*  80:    */   {
/*  81: 97 */     drawGradientRect(0, 0, width, height, 1615855616, -1602211792);
/*  82: 98 */     GL11.glPushMatrix();
/*  83: 99 */     GL11.glScalef(2.0F, 2.0F, 2.0F);
/*  84:100 */     boolean var4 = this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled();
/*  85:101 */     String var5 = var4 ? I18n.format("deathScreen.title.hardcore", new Object[0]) : I18n.format("deathScreen.title", new Object[0]);
/*  86:102 */     drawCenteredString(this.fontRendererObj, var5, width / 2 / 2, 30, 16777215);
/*  87:103 */     GL11.glPopMatrix();
/*  88:105 */     if (var4) {
/*  89:107 */       drawCenteredString(this.fontRendererObj, I18n.format("deathScreen.hardcoreInfo", new Object[0]), width / 2, 144, 16777215);
/*  90:    */     }
/*  91:110 */     drawCenteredString(this.fontRendererObj, I18n.format("deathScreen.score", new Object[0]) + ": " + EnumChatFormatting.YELLOW + this.mc.thePlayer.getScore(), width / 2, 100, 16777215);
/*  92:111 */     super.drawScreen(par1, par2, par3);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public boolean doesGuiPauseGame()
/*  96:    */   {
/*  97:119 */     return false;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void updateScreen()
/* 101:    */   {
/* 102:127 */     super.updateScreen();
/* 103:128 */     this.field_146347_a += 1;
/* 104:131 */     if (this.field_146347_a == 20)
/* 105:    */     {
/* 106:    */       NodusGuiButton var2;
/* 107:133 */       for (Iterator var1 = this.buttonList.iterator(); var1.hasNext(); var2.enabled = true) {
/* 108:135 */         var2 = (NodusGuiButton)var1.next();
/* 109:    */       }
/* 110:    */     }
/* 111:    */   }
/* 112:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiGameOver
 * JD-Core Version:    0.7.0.1
 */