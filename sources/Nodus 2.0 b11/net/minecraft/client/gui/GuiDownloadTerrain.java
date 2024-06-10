/*  1:   */ package net.minecraft.client.gui;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import net.minecraft.client.network.NetHandlerPlayClient;
/*  5:   */ import net.minecraft.client.resources.I18n;
/*  6:   */ import net.minecraft.network.play.client.C00PacketKeepAlive;
/*  7:   */ 
/*  8:   */ public class GuiDownloadTerrain
/*  9:   */   extends GuiScreen
/* 10:   */ {
/* 11:   */   private NetHandlerPlayClient field_146594_a;
/* 12:   */   private int field_146593_f;
/* 13:   */   private static final String __OBFID = "CL_00000708";
/* 14:   */   
/* 15:   */   public GuiDownloadTerrain(NetHandlerPlayClient p_i45023_1_)
/* 16:   */   {
/* 17:15 */     this.field_146594_a = p_i45023_1_;
/* 18:   */   }
/* 19:   */   
/* 20:   */   protected void keyTyped(char par1, int par2) {}
/* 21:   */   
/* 22:   */   public void initGui()
/* 23:   */   {
/* 24:28 */     this.buttonList.clear();
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void updateScreen()
/* 28:   */   {
/* 29:36 */     this.field_146593_f += 1;
/* 30:38 */     if (this.field_146593_f % 20 == 0) {
/* 31:40 */       this.field_146594_a.addToSendQueue(new C00PacketKeepAlive());
/* 32:   */     }
/* 33:43 */     if (this.field_146594_a != null) {
/* 34:45 */       this.field_146594_a.onNetworkTick();
/* 35:   */     }
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void drawScreen(int par1, int par2, float par3)
/* 39:   */   {
/* 40:54 */     func_146278_c(0);
/* 41:55 */     drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.downloadingTerrain", new Object[0]), width / 2, height / 2 - 50, 16777215);
/* 42:56 */     super.drawScreen(par1, par2, par3);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public boolean doesGuiPauseGame()
/* 46:   */   {
/* 47:64 */     return false;
/* 48:   */   }
/* 49:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiDownloadTerrain
 * JD-Core Version:    0.7.0.1
 */