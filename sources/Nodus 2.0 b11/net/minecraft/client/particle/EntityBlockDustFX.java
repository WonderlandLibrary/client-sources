/*  1:   */ package net.minecraft.client.particle;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.Block;
/*  4:   */ import net.minecraft.world.World;
/*  5:   */ 
/*  6:   */ public class EntityBlockDustFX
/*  7:   */   extends EntityDiggingFX
/*  8:   */ {
/*  9:   */   private static final String __OBFID = "CL_00000931";
/* 10:   */   
/* 11:   */   public EntityBlockDustFX(World p_i45072_1_, double p_i45072_2_, double p_i45072_4_, double p_i45072_6_, double p_i45072_8_, double p_i45072_10_, double p_i45072_12_, Block p_i45072_14_, int p_i45072_15_)
/* 12:   */   {
/* 13:12 */     super(p_i45072_1_, p_i45072_2_, p_i45072_4_, p_i45072_6_, p_i45072_8_, p_i45072_10_, p_i45072_12_, p_i45072_14_, p_i45072_15_);
/* 14:13 */     this.motionX = p_i45072_8_;
/* 15:14 */     this.motionY = p_i45072_10_;
/* 16:15 */     this.motionZ = p_i45072_12_;
/* 17:   */   }
/* 18:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.particle.EntityBlockDustFX
 * JD-Core Version:    0.7.0.1
 */