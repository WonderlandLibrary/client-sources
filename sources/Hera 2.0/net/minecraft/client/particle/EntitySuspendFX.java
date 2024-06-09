/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntitySuspendFX
/*    */   extends EntityFX
/*    */ {
/*    */   protected EntitySuspendFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
/* 11 */     super(worldIn, xCoordIn, yCoordIn - 0.125D, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/* 12 */     this.particleRed = 0.4F;
/* 13 */     this.particleGreen = 0.4F;
/* 14 */     this.particleBlue = 0.7F;
/* 15 */     setParticleTextureIndex(0);
/* 16 */     setSize(0.01F, 0.01F);
/* 17 */     this.particleScale *= this.rand.nextFloat() * 0.6F + 0.2F;
/* 18 */     this.motionX = xSpeedIn * 0.0D;
/* 19 */     this.motionY = ySpeedIn * 0.0D;
/* 20 */     this.motionZ = zSpeedIn * 0.0D;
/* 21 */     this.particleMaxAge = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 29 */     this.prevPosX = this.posX;
/* 30 */     this.prevPosY = this.posY;
/* 31 */     this.prevPosZ = this.posZ;
/* 32 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*    */     
/* 34 */     if (this.worldObj.getBlockState(new BlockPos(this)).getBlock().getMaterial() != Material.water)
/*    */     {
/* 36 */       setDead();
/*    */     }
/*    */     
/* 39 */     if (this.particleMaxAge-- <= 0)
/*    */     {
/* 41 */       setDead();
/*    */     }
/*    */   }
/*    */   
/*    */   public static class Factory
/*    */     implements IParticleFactory
/*    */   {
/*    */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 49 */       return new EntitySuspendFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\particle\EntitySuspendFX.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */