/*  1:   */ package net.minecraft.client.mco;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import me.connorm.Nodus.ui.NodusGuiButton;
/*  5:   */ import net.minecraft.client.Minecraft;
/*  6:   */ import net.minecraft.client.gui.GuiScreen;
/*  7:   */ import net.minecraft.client.resources.I18n;
/*  8:   */ 
/*  9:   */ public class GuiScreenClientOutdated
/* 10:   */   extends GuiScreen
/* 11:   */ {
/* 12:   */   private final GuiScreen field_146901_a;
/* 13:   */   private static final String __OBFID = "CL_00000772";
/* 14:   */   
/* 15:   */   public GuiScreenClientOutdated(GuiScreen par1GuiScreen)
/* 16:   */   {
/* 17:14 */     this.field_146901_a = par1GuiScreen;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void initGui()
/* 21:   */   {
/* 22:22 */     this.buttonList.clear();
/* 23:23 */     this.buttonList.add(new NodusGuiButton(0, width / 2 - 100, height / 4 + 120 + 12, "Back"));
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void drawScreen(int par1, int par2, float par3)
/* 27:   */   {
/* 28:31 */     drawDefaultBackground();
/* 29:32 */     String var4 = I18n.format("mco.client.outdated.title", new Object[0]);
/* 30:33 */     String var5 = I18n.format("mco.client.outdated.msg", new Object[0]);
/* 31:34 */     drawCenteredString(this.fontRendererObj, var4, width / 2, height / 2 - 50, 16711680);
/* 32:35 */     drawCenteredString(this.fontRendererObj, var5, width / 2, height / 2 - 30, 16777215);
/* 33:36 */     super.drawScreen(par1, par2, par3);
/* 34:   */   }
/* 35:   */   
/* 36:   */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/* 37:   */   {
/* 38:41 */     if (p_146284_1_.id == 0) {
/* 39:43 */       this.mc.displayGuiScreen(this.field_146901_a);
/* 40:   */     }
/* 41:   */   }
/* 42:   */   
/* 43:   */   protected void keyTyped(char par1, int par2)
/* 44:   */   {
/* 45:52 */     if ((par2 == 28) || (par2 == 156)) {
/* 46:54 */       this.mc.displayGuiScreen(this.field_146901_a);
/* 47:   */     }
/* 48:   */   }
/* 49:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.mco.GuiScreenClientOutdated
 * JD-Core Version:    0.7.0.1
 */