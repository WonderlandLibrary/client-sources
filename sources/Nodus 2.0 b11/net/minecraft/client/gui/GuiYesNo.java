/*  1:   */ package net.minecraft.client.gui;
/*  2:   */ 
/*  3:   */ import java.util.Iterator;
/*  4:   */ import java.util.List;
/*  5:   */ import me.connorm.Nodus.ui.NodusGuiButton;
/*  6:   */ import net.minecraft.client.resources.I18n;
/*  7:   */ 
/*  8:   */ public class GuiYesNo
/*  9:   */   extends GuiScreen
/* 10:   */ {
/* 11:   */   protected GuiScreen field_146355_a;
/* 12:   */   protected String field_146351_f;
/* 13:   */   private String field_146354_r;
/* 14:   */   protected String field_146352_g;
/* 15:   */   protected String field_146356_h;
/* 16:   */   protected int field_146357_i;
/* 17:   */   private int field_146353_s;
/* 18:   */   private static final String __OBFID = "CL_00000684";
/* 19:   */   
/* 20:   */   public GuiYesNo(GuiScreen par1GuiScreen, String par2Str, String par3Str, int par4)
/* 21:   */   {
/* 22:21 */     this.field_146355_a = par1GuiScreen;
/* 23:22 */     this.field_146351_f = par2Str;
/* 24:23 */     this.field_146354_r = par3Str;
/* 25:24 */     this.field_146357_i = par4;
/* 26:25 */     this.field_146352_g = I18n.format("gui.yes", new Object[0]);
/* 27:26 */     this.field_146356_h = I18n.format("gui.no", new Object[0]);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public GuiYesNo(GuiScreen par1GuiScreen, String par2Str, String par3Str, String par4Str, String par5Str, int par6)
/* 31:   */   {
/* 32:31 */     this.field_146355_a = par1GuiScreen;
/* 33:32 */     this.field_146351_f = par2Str;
/* 34:33 */     this.field_146354_r = par3Str;
/* 35:34 */     this.field_146352_g = par4Str;
/* 36:35 */     this.field_146356_h = par5Str;
/* 37:36 */     this.field_146357_i = par6;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void initGui()
/* 41:   */   {
/* 42:44 */     this.buttonList.add(new GuiOptionButton(0, width / 2 - 155, height / 6 + 96, this.field_146352_g));
/* 43:45 */     this.buttonList.add(new GuiOptionButton(1, width / 2 - 155 + 160, height / 6 + 96, this.field_146356_h));
/* 44:   */   }
/* 45:   */   
/* 46:   */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/* 47:   */   {
/* 48:50 */     this.field_146355_a.confirmClicked(p_146284_1_.id == 0, this.field_146357_i);
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void drawScreen(int par1, int par2, float par3)
/* 52:   */   {
/* 53:58 */     drawDefaultBackground();
/* 54:59 */     drawCenteredString(this.fontRendererObj, this.field_146351_f, width / 2, 70, 16777215);
/* 55:60 */     drawCenteredString(this.fontRendererObj, this.field_146354_r, width / 2, 90, 16777215);
/* 56:61 */     super.drawScreen(par1, par2, par3);
/* 57:   */   }
/* 58:   */   
/* 59:   */   public void func_146350_a(int p_146350_1_)
/* 60:   */   {
/* 61:66 */     this.field_146353_s = p_146350_1_;
/* 62:   */     NodusGuiButton var3;
/* 63:69 */     for (Iterator var2 = this.buttonList.iterator(); var2.hasNext(); var3.enabled = false) {
/* 64:71 */       var3 = (NodusGuiButton)var2.next();
/* 65:   */     }
/* 66:   */   }
/* 67:   */   
/* 68:   */   public void updateScreen()
/* 69:   */   {
/* 70:80 */     super.updateScreen();
/* 71:83 */     if (--this.field_146353_s == 0)
/* 72:   */     {
/* 73:   */       NodusGuiButton var2;
/* 74:85 */       for (Iterator var1 = this.buttonList.iterator(); var1.hasNext(); var2.enabled = true) {
/* 75:87 */         var2 = (NodusGuiButton)var1.next();
/* 76:   */       }
/* 77:   */     }
/* 78:   */   }
/* 79:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiYesNo
 * JD-Core Version:    0.7.0.1
 */