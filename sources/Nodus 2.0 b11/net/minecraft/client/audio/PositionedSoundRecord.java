/*  1:   */ package net.minecraft.client.audio;
/*  2:   */ 
/*  3:   */ import net.minecraft.util.ResourceLocation;
/*  4:   */ 
/*  5:   */ public class PositionedSoundRecord
/*  6:   */   extends PositionedSound
/*  7:   */ {
/*  8:   */   private static final String __OBFID = "CL_00001120";
/*  9:   */   
/* 10:   */   public static PositionedSoundRecord func_147674_a(ResourceLocation p_147674_0_, float p_147674_1_)
/* 11:   */   {
/* 12:11 */     return new PositionedSoundRecord(p_147674_0_, 0.25F, p_147674_1_, false, 0, ISound.AttenuationType.NONE, 0.0F, 0.0F, 0.0F);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public static PositionedSoundRecord func_147673_a(ResourceLocation p_147673_0_)
/* 16:   */   {
/* 17:16 */     return new PositionedSoundRecord(p_147673_0_, 1.0F, 1.0F, false, 0, ISound.AttenuationType.NONE, 0.0F, 0.0F, 0.0F);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public static PositionedSoundRecord func_147675_a(ResourceLocation p_147675_0_, float p_147675_1_, float p_147675_2_, float p_147675_3_)
/* 21:   */   {
/* 22:21 */     return new PositionedSoundRecord(p_147675_0_, 4.0F, 1.0F, false, 0, ISound.AttenuationType.LINEAR, p_147675_1_, p_147675_2_, p_147675_3_);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public PositionedSoundRecord(ResourceLocation p_i45107_1_, float p_i45107_2_, float p_i45107_3_, float p_i45107_4_, float p_i45107_5_, float p_i45107_6_)
/* 26:   */   {
/* 27:26 */     this(p_i45107_1_, p_i45107_2_, p_i45107_3_, false, 0, ISound.AttenuationType.LINEAR, p_i45107_4_, p_i45107_5_, p_i45107_6_);
/* 28:   */   }
/* 29:   */   
/* 30:   */   private PositionedSoundRecord(ResourceLocation p_i45108_1_, float p_i45108_2_, float p_i45108_3_, boolean p_i45108_4_, int p_i45108_5_, ISound.AttenuationType p_i45108_6_, float p_i45108_7_, float p_i45108_8_, float p_i45108_9_)
/* 31:   */   {
/* 32:31 */     super(p_i45108_1_);
/* 33:32 */     this.field_147662_b = p_i45108_2_;
/* 34:33 */     this.field_147663_c = p_i45108_3_;
/* 35:34 */     this.field_147660_d = p_i45108_7_;
/* 36:35 */     this.field_147661_e = p_i45108_8_;
/* 37:36 */     this.field_147658_f = p_i45108_9_;
/* 38:37 */     this.field_147659_g = p_i45108_4_;
/* 39:38 */     this.field_147665_h = p_i45108_5_;
/* 40:39 */     this.field_147666_i = p_i45108_6_;
/* 41:   */   }
/* 42:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.audio.PositionedSoundRecord
 * JD-Core Version:    0.7.0.1
 */