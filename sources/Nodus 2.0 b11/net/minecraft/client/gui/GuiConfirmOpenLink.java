/*  1:   */ package net.minecraft.client.gui;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import me.connorm.Nodus.ui.NodusGuiButton;
/*  5:   */ import net.minecraft.client.resources.I18n;
/*  6:   */ 
/*  7:   */ public class GuiConfirmOpenLink
/*  8:   */   extends GuiYesNo
/*  9:   */ {
/* 10:   */   private String field_146363_r;
/* 11:   */   private String field_146362_s;
/* 12:   */   private String field_146361_t;
/* 13:11 */   private boolean field_146360_u = true;
/* 14:   */   private static final String __OBFID = "CL_00000683";
/* 15:   */   
/* 16:   */   public GuiConfirmOpenLink(GuiScreen par1GuiScreen, String par2Str, int par3, boolean par4)
/* 17:   */   {
/* 18:16 */     super(par1GuiScreen, I18n.format(par4 ? "chat.link.confirmTrusted" : "chat.link.confirm", new Object[0]), par2Str, par3);
/* 19:17 */     this.field_146352_g = I18n.format(par4 ? "chat.link.open" : "gui.yes", new Object[0]);
/* 20:18 */     this.field_146356_h = I18n.format(par4 ? "gui.cancel" : "gui.no", new Object[0]);
/* 21:19 */     this.field_146362_s = I18n.format("chat.copy", new Object[0]);
/* 22:20 */     this.field_146363_r = I18n.format("chat.link.warning", new Object[0]);
/* 23:21 */     this.field_146361_t = par2Str;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void initGui()
/* 27:   */   {
/* 28:29 */     this.buttonList.add(new NodusGuiButton(0, width / 3 - 83 + 0, height / 6 + 96, 100, 20, this.field_146352_g));
/* 29:30 */     this.buttonList.add(new NodusGuiButton(2, width / 3 - 83 + 105, height / 6 + 96, 100, 20, this.field_146362_s));
/* 30:31 */     this.buttonList.add(new NodusGuiButton(1, width / 3 - 83 + 210, height / 6 + 96, 100, 20, this.field_146356_h));
/* 31:   */   }
/* 32:   */   
/* 33:   */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/* 34:   */   {
/* 35:36 */     if (p_146284_1_.id == 2) {
/* 36:38 */       func_146359_e();
/* 37:   */     }
/* 38:41 */     this.field_146355_a.confirmClicked(p_146284_1_.id == 0, this.field_146357_i);
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void func_146359_e()
/* 42:   */   {
/* 43:46 */     setClipboardString(this.field_146361_t);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void drawScreen(int par1, int par2, float par3)
/* 47:   */   {
/* 48:54 */     super.drawScreen(par1, par2, par3);
/* 49:56 */     if (this.field_146360_u) {
/* 50:58 */       drawCenteredString(this.fontRendererObj, this.field_146363_r, width / 2, 110, 16764108);
/* 51:   */     }
/* 52:   */   }
/* 53:   */   
/* 54:   */   public void func_146358_g()
/* 55:   */   {
/* 56:64 */     this.field_146360_u = false;
/* 57:   */   }
/* 58:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiConfirmOpenLink
 * JD-Core Version:    0.7.0.1
 */