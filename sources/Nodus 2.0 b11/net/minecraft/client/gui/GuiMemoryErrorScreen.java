/*  1:   */ package net.minecraft.client.gui;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import me.connorm.Nodus.ui.NodusGuiButton;
/*  5:   */ import me.connorm.Nodus.ui.NodusGuiMainMenu;
/*  6:   */ import net.minecraft.client.Minecraft;
/*  7:   */ import net.minecraft.client.resources.I18n;
/*  8:   */ 
/*  9:   */ public class GuiMemoryErrorScreen
/* 10:   */   extends GuiScreen
/* 11:   */ {
/* 12:   */   private static final String __OBFID = "CL_00000702";
/* 13:   */   
/* 14:   */   public void initGui()
/* 15:   */   {
/* 16:16 */     this.buttonList.clear();
/* 17:17 */     this.buttonList.add(new GuiOptionButton(0, width / 2 - 155, height / 4 + 120 + 12, I18n.format("gui.toMenu", new Object[0])));
/* 18:18 */     this.buttonList.add(new GuiOptionButton(1, width / 2 - 155 + 160, height / 4 + 120 + 12, I18n.format("menu.quit", new Object[0])));
/* 19:   */   }
/* 20:   */   
/* 21:   */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/* 22:   */   {
/* 23:23 */     if (p_146284_1_.id == 0) {
/* 24:25 */       this.mc.displayGuiScreen(new NodusGuiMainMenu());
/* 25:27 */     } else if (p_146284_1_.id == 1) {
/* 26:29 */       this.mc.shutdown();
/* 27:   */     }
/* 28:   */   }
/* 29:   */   
/* 30:   */   protected void keyTyped(char par1, int par2) {}
/* 31:   */   
/* 32:   */   public void drawScreen(int par1, int par2, float par3)
/* 33:   */   {
/* 34:43 */     drawDefaultBackground();
/* 35:44 */     drawCenteredString(this.fontRendererObj, "Out of memory!", width / 2, height / 4 - 60 + 20, 16777215);
/* 36:45 */     drawString(this.fontRendererObj, "Minecraft has run out of memory.", width / 2 - 140, height / 4 - 60 + 60 + 0, 10526880);
/* 37:46 */     drawString(this.fontRendererObj, "This could be caused by a bug in the game or by the", width / 2 - 140, height / 4 - 60 + 60 + 18, 10526880);
/* 38:47 */     drawString(this.fontRendererObj, "Java Virtual Machine not being allocated enough", width / 2 - 140, height / 4 - 60 + 60 + 27, 10526880);
/* 39:48 */     drawString(this.fontRendererObj, "memory.", width / 2 - 140, height / 4 - 60 + 60 + 36, 10526880);
/* 40:49 */     drawString(this.fontRendererObj, "To prevent level corruption, the current game has quit.", width / 2 - 140, height / 4 - 60 + 60 + 54, 10526880);
/* 41:50 */     drawString(this.fontRendererObj, "We've tried to free up enough memory to let you go back to", width / 2 - 140, height / 4 - 60 + 60 + 63, 10526880);
/* 42:51 */     drawString(this.fontRendererObj, "the main menu and back to playing, but this may not have worked.", width / 2 - 140, height / 4 - 60 + 60 + 72, 10526880);
/* 43:52 */     drawString(this.fontRendererObj, "Please restart the game if you see this message again.", width / 2 - 140, height / 4 - 60 + 60 + 81, 10526880);
/* 44:53 */     super.drawScreen(par1, par2, par3);
/* 45:   */   }
/* 46:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiMemoryErrorScreen
 * JD-Core Version:    0.7.0.1
 */