/*  1:   */ package net.minecraft.client.audio;
/*  2:   */ 
/*  3:   */ import net.minecraft.util.ResourceLocation;
/*  4:   */ 
/*  5:   */ public abstract class PositionedSound
/*  6:   */   implements ISound
/*  7:   */ {
/*  8:   */   protected final ResourceLocation field_147664_a;
/*  9: 8 */   protected float field_147662_b = 1.0F;
/* 10: 9 */   protected float field_147663_c = 1.0F;
/* 11:   */   protected float field_147660_d;
/* 12:   */   protected float field_147661_e;
/* 13:   */   protected float field_147658_f;
/* 14:13 */   protected boolean field_147659_g = false;
/* 15:14 */   protected int field_147665_h = 0;
/* 16:   */   protected ISound.AttenuationType field_147666_i;
/* 17:   */   private static final String __OBFID = "CL_00001116";
/* 18:   */   
/* 19:   */   protected PositionedSound(ResourceLocation p_i45103_1_)
/* 20:   */   {
/* 21:20 */     this.field_147666_i = ISound.AttenuationType.LINEAR;
/* 22:21 */     this.field_147664_a = p_i45103_1_;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public ResourceLocation func_147650_b()
/* 26:   */   {
/* 27:26 */     return this.field_147664_a;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public boolean func_147657_c()
/* 31:   */   {
/* 32:31 */     return this.field_147659_g;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public int func_147652_d()
/* 36:   */   {
/* 37:36 */     return this.field_147665_h;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public float func_147653_e()
/* 41:   */   {
/* 42:41 */     return this.field_147662_b;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public float func_147655_f()
/* 46:   */   {
/* 47:46 */     return this.field_147663_c;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public float func_147649_g()
/* 51:   */   {
/* 52:51 */     return this.field_147660_d;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public float func_147654_h()
/* 56:   */   {
/* 57:56 */     return this.field_147661_e;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public float func_147651_i()
/* 61:   */   {
/* 62:61 */     return this.field_147658_f;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public ISound.AttenuationType func_147656_j()
/* 66:   */   {
/* 67:66 */     return this.field_147666_i;
/* 68:   */   }
/* 69:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.audio.PositionedSound
 * JD-Core Version:    0.7.0.1
 */