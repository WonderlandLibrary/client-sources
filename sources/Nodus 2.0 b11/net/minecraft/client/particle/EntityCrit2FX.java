/*  1:   */ package net.minecraft.client.particle;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.client.renderer.Tessellator;
/*  5:   */ import net.minecraft.entity.Entity;
/*  6:   */ import net.minecraft.util.AxisAlignedBB;
/*  7:   */ import net.minecraft.world.World;
/*  8:   */ 
/*  9:   */ public class EntityCrit2FX
/* 10:   */   extends EntityFX
/* 11:   */ {
/* 12:   */   private Entity theEntity;
/* 13:   */   private int currentLife;
/* 14:   */   private int maximumLife;
/* 15:   */   private String particleName;
/* 16:   */   private static final String __OBFID = "CL_00000899";
/* 17:   */   
/* 18:   */   public EntityCrit2FX(World par1World, Entity par2Entity)
/* 19:   */   {
/* 20:18 */     this(par1World, par2Entity, "crit");
/* 21:   */   }
/* 22:   */   
/* 23:   */   public EntityCrit2FX(World par1World, Entity par2Entity, String par3Str)
/* 24:   */   {
/* 25:23 */     super(par1World, par2Entity.posX, par2Entity.boundingBox.minY + par2Entity.height / 2.0F, par2Entity.posZ, par2Entity.motionX, par2Entity.motionY, par2Entity.motionZ);
/* 26:24 */     this.theEntity = par2Entity;
/* 27:25 */     this.maximumLife = 3;
/* 28:26 */     this.particleName = par3Str;
/* 29:27 */     onUpdate();
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {}
/* 33:   */   
/* 34:   */   public void onUpdate()
/* 35:   */   {
/* 36:37 */     for (int var1 = 0; var1 < 16; var1++)
/* 37:   */     {
/* 38:39 */       double var2 = this.rand.nextFloat() * 2.0F - 1.0F;
/* 39:40 */       double var4 = this.rand.nextFloat() * 2.0F - 1.0F;
/* 40:41 */       double var6 = this.rand.nextFloat() * 2.0F - 1.0F;
/* 41:43 */       if (var2 * var2 + var4 * var4 + var6 * var6 <= 1.0D)
/* 42:   */       {
/* 43:45 */         double var8 = this.theEntity.posX + var2 * this.theEntity.width / 4.0D;
/* 44:46 */         double var10 = this.theEntity.boundingBox.minY + this.theEntity.height / 2.0F + var4 * this.theEntity.height / 4.0D;
/* 45:47 */         double var12 = this.theEntity.posZ + var6 * this.theEntity.width / 4.0D;
/* 46:48 */         this.worldObj.spawnParticle(this.particleName, var8, var10, var12, var2, var4 + 0.2D, var6);
/* 47:   */       }
/* 48:   */     }
/* 49:52 */     this.currentLife += 1;
/* 50:54 */     if (this.currentLife >= this.maximumLife) {
/* 51:56 */       setDead();
/* 52:   */     }
/* 53:   */   }
/* 54:   */   
/* 55:   */   public int getFXLayer()
/* 56:   */   {
/* 57:62 */     return 3;
/* 58:   */   }
/* 59:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.particle.EntityCrit2FX
 * JD-Core Version:    0.7.0.1
 */