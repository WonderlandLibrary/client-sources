/*  1:   */ package net.minecraft.client.gui;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import me.connorm.Nodus.ui.NodusGuiButton;
/*  5:   */ import net.minecraft.client.resources.I18n;
/*  6:   */ 
/*  7:   */ public class GuiScreenConfirmation
/*  8:   */   extends GuiScreen
/*  9:   */ {
/* 10:   */   private final ConfirmationType field_146937_i;
/* 11:   */   private final String field_146934_r;
/* 12:   */   private final String field_146933_s;
/* 13:   */   protected final GuiScreen field_146935_a;
/* 14:   */   protected final String field_146931_f;
/* 15:   */   protected final String field_146932_g;
/* 16:   */   protected final int field_146936_h;
/* 17:   */   private static final String __OBFID = "CL_00000781";
/* 18:   */   
/* 19:   */   public GuiScreenConfirmation(GuiScreen par1GuiScreen, ConfirmationType par2GuiScreenConfirmationType, String par3Str, String par4Str, int par5)
/* 20:   */   {
/* 21:19 */     this.field_146935_a = par1GuiScreen;
/* 22:20 */     this.field_146936_h = par5;
/* 23:21 */     this.field_146937_i = par2GuiScreenConfirmationType;
/* 24:22 */     this.field_146934_r = par3Str;
/* 25:23 */     this.field_146933_s = par4Str;
/* 26:24 */     this.field_146931_f = I18n.format("gui.yes", new Object[0]);
/* 27:25 */     this.field_146932_g = I18n.format("gui.no", new Object[0]);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void initGui()
/* 31:   */   {
/* 32:33 */     this.buttonList.add(new GuiOptionButton(0, width / 2 - 155, height / 6 + 112, this.field_146931_f));
/* 33:34 */     this.buttonList.add(new GuiOptionButton(1, width / 2 - 155 + 160, height / 6 + 112, this.field_146932_g));
/* 34:   */   }
/* 35:   */   
/* 36:   */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/* 37:   */   {
/* 38:39 */     this.field_146935_a.confirmClicked(p_146284_1_.id == 0, this.field_146936_h);
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void drawScreen(int par1, int par2, float par3)
/* 42:   */   {
/* 43:47 */     drawDefaultBackground();
/* 44:48 */     drawCenteredString(this.fontRendererObj, this.field_146937_i.field_148515_d, width / 2, 70, this.field_146937_i.field_148518_c);
/* 45:49 */     drawCenteredString(this.fontRendererObj, this.field_146934_r, width / 2, 90, 16777215);
/* 46:50 */     drawCenteredString(this.fontRendererObj, this.field_146933_s, width / 2, 110, 16777215);
/* 47:51 */     super.drawScreen(par1, par2, par3);
/* 48:   */   }
/* 49:   */   
/* 50:   */   public static enum ConfirmationType
/* 51:   */   {
/* 52:56 */     Warning("Warning", 0, "Warning!", 16711680),  Info("Info", 1, "Info!", 8226750);
/* 53:   */     
/* 54:   */     public final int field_148518_c;
/* 55:   */     public final String field_148515_d;
/* 56:61 */     private static final ConfirmationType[] $VALUES = { Warning, Info };
/* 57:   */     private static final String __OBFID = "CL_00000782";
/* 58:   */     
/* 59:   */     private ConfirmationType(String par1Str, int par2, String par3Str, int par4)
/* 60:   */     {
/* 61:66 */       this.field_148515_d = par3Str;
/* 62:67 */       this.field_148518_c = par4;
/* 63:   */     }
/* 64:   */   }
/* 65:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiScreenConfirmation
 * JD-Core Version:    0.7.0.1
 */