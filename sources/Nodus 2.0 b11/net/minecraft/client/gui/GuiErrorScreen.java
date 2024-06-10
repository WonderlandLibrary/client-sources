/*  1:   */ package net.minecraft.client.gui;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import me.connorm.Nodus.ui.NodusGuiButton;
/*  5:   */ import net.minecraft.client.Minecraft;
/*  6:   */ import net.minecraft.client.resources.I18n;
/*  7:   */ 
/*  8:   */ public class GuiErrorScreen
/*  9:   */   extends GuiScreen
/* 10:   */ {
/* 11:   */   private String field_146313_a;
/* 12:   */   private String field_146312_f;
/* 13:   */   private static final String __OBFID = "CL_00000696";
/* 14:   */   
/* 15:   */   public GuiErrorScreen(String par1Str, String par2Str)
/* 16:   */   {
/* 17:14 */     this.field_146313_a = par1Str;
/* 18:15 */     this.field_146312_f = par2Str;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void initGui()
/* 22:   */   {
/* 23:23 */     super.initGui();
/* 24:24 */     this.buttonList.add(new NodusGuiButton(0, width / 2 - 100, 140, I18n.format("gui.cancel", new Object[0])));
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void drawScreen(int par1, int par2, float par3)
/* 28:   */   {
/* 29:32 */     drawGradientRect(0, 0, width, height, -12574688, -11530224);
/* 30:33 */     drawCenteredString(this.fontRendererObj, this.field_146313_a, width / 2, 90, 16777215);
/* 31:34 */     drawCenteredString(this.fontRendererObj, this.field_146312_f, width / 2, 110, 16777215);
/* 32:35 */     super.drawScreen(par1, par2, par3);
/* 33:   */   }
/* 34:   */   
/* 35:   */   protected void keyTyped(char par1, int par2) {}
/* 36:   */   
/* 37:   */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/* 38:   */   {
/* 39:45 */     this.mc.displayGuiScreen(null);
/* 40:   */   }
/* 41:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiErrorScreen
 * JD-Core Version:    0.7.0.1
 */