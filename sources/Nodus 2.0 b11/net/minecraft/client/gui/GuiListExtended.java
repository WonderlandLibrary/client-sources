/*  1:   */ package net.minecraft.client.gui;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.Minecraft;
/*  4:   */ import net.minecraft.client.renderer.Tessellator;
/*  5:   */ 
/*  6:   */ public abstract class GuiListExtended
/*  7:   */   extends GuiSlot
/*  8:   */ {
/*  9:   */   private static final String __OBFID = "CL_00000674";
/* 10:   */   
/* 11:   */   public GuiListExtended(Minecraft p_i45010_1_, int p_i45010_2_, int p_i45010_3_, int p_i45010_4_, int p_i45010_5_, int p_i45010_6_)
/* 12:   */   {
/* 13:12 */     super(p_i45010_1_, p_i45010_2_, p_i45010_3_, p_i45010_4_, p_i45010_5_, p_i45010_6_);
/* 14:   */   }
/* 15:   */   
/* 16:   */   protected void elementClicked(int p_148144_1_, boolean p_148144_2_, int p_148144_3_, int p_148144_4_) {}
/* 17:   */   
/* 18:   */   protected boolean isSelected(int p_148131_1_)
/* 19:   */   {
/* 20:19 */     return false;
/* 21:   */   }
/* 22:   */   
/* 23:   */   protected void drawBackground() {}
/* 24:   */   
/* 25:   */   protected void drawSlot(int p_148126_1_, int p_148126_2_, int p_148126_3_, int p_148126_4_, Tessellator p_148126_5_, int p_148126_6_, int p_148126_7_)
/* 26:   */   {
/* 27:26 */     func_148180_b(p_148126_1_).func_148279_a(p_148126_1_, p_148126_2_, p_148126_3_, func_148139_c(), p_148126_4_, p_148126_5_, p_148126_6_, p_148126_7_, func_148124_c(p_148126_6_, p_148126_7_) == p_148126_1_);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public boolean func_148179_a(int p_148179_1_, int p_148179_2_, int p_148179_3_)
/* 31:   */   {
/* 32:31 */     if (func_148141_e(p_148179_2_))
/* 33:   */     {
/* 34:33 */       int var4 = func_148124_c(p_148179_1_, p_148179_2_);
/* 35:35 */       if (var4 >= 0)
/* 36:   */       {
/* 37:37 */         int var5 = this.field_148152_e + this.field_148155_a / 2 - func_148139_c() / 2 + 2;
/* 38:38 */         int var6 = this.field_148153_b + 4 - func_148148_g() + var4 * this.field_148149_f + this.field_148160_j;
/* 39:39 */         int var7 = p_148179_1_ - var5;
/* 40:40 */         int var8 = p_148179_2_ - var6;
/* 41:42 */         if (func_148180_b(var4).func_148278_a(var4, p_148179_1_, p_148179_2_, p_148179_3_, var7, var8))
/* 42:   */         {
/* 43:44 */           func_148143_b(false);
/* 44:45 */           return true;
/* 45:   */         }
/* 46:   */       }
/* 47:   */     }
/* 48:50 */     return false;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public boolean func_148181_b(int p_148181_1_, int p_148181_2_, int p_148181_3_)
/* 52:   */   {
/* 53:55 */     for (int var4 = 0; var4 < getSize(); var4++)
/* 54:   */     {
/* 55:57 */       int var5 = this.field_148152_e + this.field_148155_a / 2 - func_148139_c() / 2 + 2;
/* 56:58 */       int var6 = this.field_148153_b + 4 - func_148148_g() + var4 * this.field_148149_f + this.field_148160_j;
/* 57:59 */       int var7 = p_148181_1_ - var5;
/* 58:60 */       int var8 = p_148181_2_ - var6;
/* 59:61 */       func_148180_b(var4).func_148277_b(var4, p_148181_1_, p_148181_2_, p_148181_3_, var7, var8);
/* 60:   */     }
/* 61:64 */     func_148143_b(true);
/* 62:65 */     return false;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public abstract IGuiListEntry func_148180_b(int paramInt);
/* 66:   */   
/* 67:   */   public static abstract interface IGuiListEntry
/* 68:   */   {
/* 69:   */     public abstract void func_148279_a(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, Tessellator paramTessellator, int paramInt6, int paramInt7, boolean paramBoolean);
/* 70:   */     
/* 71:   */     public abstract boolean func_148278_a(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/* 72:   */     
/* 73:   */     public abstract void func_148277_b(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/* 74:   */   }
/* 75:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiListExtended
 * JD-Core Version:    0.7.0.1
 */