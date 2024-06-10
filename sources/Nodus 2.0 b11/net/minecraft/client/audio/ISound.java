/*  1:   */ package net.minecraft.client.audio;
/*  2:   */ 
/*  3:   */ import net.minecraft.util.ResourceLocation;
/*  4:   */ 
/*  5:   */ public abstract interface ISound
/*  6:   */ {
/*  7:   */   public abstract ResourceLocation func_147650_b();
/*  8:   */   
/*  9:   */   public abstract boolean func_147657_c();
/* 10:   */   
/* 11:   */   public abstract int func_147652_d();
/* 12:   */   
/* 13:   */   public abstract float func_147653_e();
/* 14:   */   
/* 15:   */   public abstract float func_147655_f();
/* 16:   */   
/* 17:   */   public abstract float func_147649_g();
/* 18:   */   
/* 19:   */   public abstract float func_147654_h();
/* 20:   */   
/* 21:   */   public abstract float func_147651_i();
/* 22:   */   
/* 23:   */   public abstract AttenuationType func_147656_j();
/* 24:   */   
/* 25:   */   public static enum AttenuationType
/* 26:   */   {
/* 27:27 */     NONE("NONE", 0, 0),  LINEAR("LINEAR", 1, 2);
/* 28:   */     
/* 29:   */     private final int field_148589_c;
/* 30:31 */     private static final AttenuationType[] $VALUES = { NONE, LINEAR };
/* 31:   */     private static final String __OBFID = "CL_00001126";
/* 32:   */     
/* 33:   */     private AttenuationType(String p_i45110_1_, int p_i45110_2_, int p_i45110_3_)
/* 34:   */     {
/* 35:36 */       this.field_148589_c = p_i45110_3_;
/* 36:   */     }
/* 37:   */     
/* 38:   */     public int func_148586_a()
/* 39:   */     {
/* 40:41 */       return this.field_148589_c;
/* 41:   */     }
/* 42:   */   }
/* 43:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.audio.ISound
 * JD-Core Version:    0.7.0.1
 */