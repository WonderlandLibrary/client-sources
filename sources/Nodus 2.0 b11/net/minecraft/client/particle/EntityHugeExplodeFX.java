/*  1:   */ package net.minecraft.client.particle;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.client.renderer.Tessellator;
/*  5:   */ import net.minecraft.world.World;
/*  6:   */ 
/*  7:   */ public class EntityHugeExplodeFX
/*  8:   */   extends EntityFX
/*  9:   */ {
/* 10:   */   private int timeSinceStart;
/* 11:11 */   private int maximumTime = 8;
/* 12:   */   private static final String __OBFID = "CL_00000911";
/* 13:   */   
/* 14:   */   public EntityHugeExplodeFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
/* 15:   */   {
/* 16:16 */     super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {}
/* 20:   */   
/* 21:   */   public void onUpdate()
/* 22:   */   {
/* 23:26 */     for (int var1 = 0; var1 < 6; var1++)
/* 24:   */     {
/* 25:28 */       double var2 = this.posX + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0D;
/* 26:29 */       double var4 = this.posY + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0D;
/* 27:30 */       double var6 = this.posZ + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0D;
/* 28:31 */       this.worldObj.spawnParticle("largeexplode", var2, var4, var6, this.timeSinceStart / this.maximumTime, 0.0D, 0.0D);
/* 29:   */     }
/* 30:34 */     this.timeSinceStart += 1;
/* 31:36 */     if (this.timeSinceStart == this.maximumTime) {
/* 32:38 */       setDead();
/* 33:   */     }
/* 34:   */   }
/* 35:   */   
/* 36:   */   public int getFXLayer()
/* 37:   */   {
/* 38:44 */     return 1;
/* 39:   */   }
/* 40:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.particle.EntityHugeExplodeFX
 * JD-Core Version:    0.7.0.1
 */