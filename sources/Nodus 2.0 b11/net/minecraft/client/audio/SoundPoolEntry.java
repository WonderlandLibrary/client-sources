/*  1:   */ package net.minecraft.client.audio;
/*  2:   */ 
/*  3:   */ import net.minecraft.util.ResourceLocation;
/*  4:   */ 
/*  5:   */ public class SoundPoolEntry
/*  6:   */ {
/*  7:   */   private final ResourceLocation field_148656_a;
/*  8:   */   private final boolean field_148654_b;
/*  9:   */   private double field_148655_c;
/* 10:   */   private double field_148653_d;
/* 11:   */   private static final String __OBFID = "CL_00001140";
/* 12:   */   
/* 13:   */   public SoundPoolEntry(ResourceLocation p_i45113_1_, double p_i45113_2_, double p_i45113_4_, boolean p_i45113_6_)
/* 14:   */   {
/* 15:15 */     this.field_148656_a = p_i45113_1_;
/* 16:16 */     this.field_148655_c = p_i45113_2_;
/* 17:17 */     this.field_148653_d = p_i45113_4_;
/* 18:18 */     this.field_148654_b = p_i45113_6_;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public SoundPoolEntry(SoundPoolEntry p_i45114_1_)
/* 22:   */   {
/* 23:23 */     this.field_148656_a = p_i45114_1_.field_148656_a;
/* 24:24 */     this.field_148655_c = p_i45114_1_.field_148655_c;
/* 25:25 */     this.field_148653_d = p_i45114_1_.field_148653_d;
/* 26:26 */     this.field_148654_b = p_i45114_1_.field_148654_b;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public ResourceLocation func_148652_a()
/* 30:   */   {
/* 31:31 */     return this.field_148656_a;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public double func_148650_b()
/* 35:   */   {
/* 36:36 */     return this.field_148655_c;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void func_148651_a(double p_148651_1_)
/* 40:   */   {
/* 41:41 */     this.field_148655_c = p_148651_1_;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public double func_148649_c()
/* 45:   */   {
/* 46:46 */     return this.field_148653_d;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void func_148647_b(double p_148647_1_)
/* 50:   */   {
/* 51:51 */     this.field_148653_d = p_148647_1_;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public boolean func_148648_d()
/* 55:   */   {
/* 56:56 */     return this.field_148654_b;
/* 57:   */   }
/* 58:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.audio.SoundPoolEntry
 * JD-Core Version:    0.7.0.1
 */