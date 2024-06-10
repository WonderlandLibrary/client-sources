/*  1:   */ package net.minecraft.client.gui;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.ui.NodusGuiButton;
/*  4:   */ import net.minecraft.client.settings.GameSettings.Options;
/*  5:   */ 
/*  6:   */ public class GuiOptionButton
/*  7:   */   extends NodusGuiButton
/*  8:   */ {
/*  9:   */   private final GameSettings.Options field_146137_o;
/* 10:   */   private static final String __OBFID = "CL_00000676";
/* 11:   */   
/* 12:   */   public GuiOptionButton(int p_i45011_1_, int p_i45011_2_, int p_i45011_3_, String p_i45011_4_)
/* 13:   */   {
/* 14:13 */     this(p_i45011_1_, p_i45011_2_, p_i45011_3_, null, p_i45011_4_);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public GuiOptionButton(int p_i45012_1_, int p_i45012_2_, int p_i45012_3_, int p_i45012_4_, int p_i45012_5_, String p_i45012_6_)
/* 18:   */   {
/* 19:18 */     super(p_i45012_1_, p_i45012_2_, p_i45012_3_, p_i45012_4_, p_i45012_5_, p_i45012_6_);
/* 20:19 */     this.field_146137_o = null;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public GuiOptionButton(int p_i45013_1_, int p_i45013_2_, int p_i45013_3_, GameSettings.Options p_i45013_4_, String p_i45013_5_)
/* 24:   */   {
/* 25:24 */     super(p_i45013_1_, p_i45013_2_, p_i45013_3_, 150, 20, p_i45013_5_);
/* 26:25 */     this.field_146137_o = p_i45013_4_;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public GameSettings.Options func_146136_c()
/* 30:   */   {
/* 31:30 */     return this.field_146137_o;
/* 32:   */   }
/* 33:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiOptionButton
 * JD-Core Version:    0.7.0.1
 */