/*  1:   */ package net.minecraft.client.particle;
/*  2:   */ 
/*  3:   */ import net.minecraft.world.World;
/*  4:   */ 
/*  5:   */ public class EntitySplashFX
/*  6:   */   extends EntityRainFX
/*  7:   */ {
/*  8:   */   private static final String __OBFID = "CL_00000927";
/*  9:   */   
/* 10:   */   public EntitySplashFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
/* 11:   */   {
/* 12:11 */     super(par1World, par2, par4, par6);
/* 13:12 */     this.particleGravity = 0.04F;
/* 14:13 */     nextTextureIndexX();
/* 15:15 */     if ((par10 == 0.0D) && ((par8 != 0.0D) || (par12 != 0.0D)))
/* 16:   */     {
/* 17:17 */       this.motionX = par8;
/* 18:18 */       this.motionY = (par10 + 0.1D);
/* 19:19 */       this.motionZ = par12;
/* 20:   */     }
/* 21:   */   }
/* 22:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.particle.EntitySplashFX
 * JD-Core Version:    0.7.0.1
 */