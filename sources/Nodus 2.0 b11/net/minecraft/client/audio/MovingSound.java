/*  1:   */ package net.minecraft.client.audio;
/*  2:   */ 
/*  3:   */ import net.minecraft.util.ResourceLocation;
/*  4:   */ 
/*  5:   */ public abstract class MovingSound
/*  6:   */   extends PositionedSound
/*  7:   */   implements ITickableSound
/*  8:   */ {
/*  9: 7 */   protected boolean field_147668_j = false;
/* 10:   */   private static final String __OBFID = "CL_00001117";
/* 11:   */   
/* 12:   */   protected MovingSound(ResourceLocation p_i45104_1_)
/* 13:   */   {
/* 14:12 */     super(p_i45104_1_);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public boolean func_147667_k()
/* 18:   */   {
/* 19:17 */     return this.field_147668_j;
/* 20:   */   }
/* 21:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.audio.MovingSound
 * JD-Core Version:    0.7.0.1
 */