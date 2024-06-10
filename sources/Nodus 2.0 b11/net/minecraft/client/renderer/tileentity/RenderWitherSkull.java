/*  1:   */ package net.minecraft.client.renderer.tileentity;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.model.ModelSkeletonHead;
/*  4:   */ import net.minecraft.client.renderer.entity.Render;
/*  5:   */ import net.minecraft.entity.Entity;
/*  6:   */ import net.minecraft.entity.projectile.EntityWitherSkull;
/*  7:   */ import net.minecraft.util.ResourceLocation;
/*  8:   */ import org.lwjgl.opengl.GL11;
/*  9:   */ 
/* 10:   */ public class RenderWitherSkull
/* 11:   */   extends Render
/* 12:   */ {
/* 13:13 */   private static final ResourceLocation invulnerableWitherTextures = new ResourceLocation("textures/entity/wither/wither_invulnerable.png");
/* 14:14 */   private static final ResourceLocation witherTextures = new ResourceLocation("textures/entity/wither/wither.png");
/* 15:17 */   private final ModelSkeletonHead skeletonHeadModel = new ModelSkeletonHead();
/* 16:   */   private static final String __OBFID = "CL_00001035";
/* 17:   */   
/* 18:   */   private float func_82400_a(float par1, float par2, float par3)
/* 19:   */   {
/* 20:24 */     for (float var4 = par2 - par1; var4 < -180.0F; var4 += 360.0F) {}
/* 21:29 */     while (var4 >= 180.0F) {
/* 22:31 */       var4 -= 360.0F;
/* 23:   */     }
/* 24:34 */     return par1 + par3 * var4;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void doRender(EntityWitherSkull par1EntityWitherSkull, double par2, double par4, double par6, float par8, float par9)
/* 28:   */   {
/* 29:45 */     GL11.glPushMatrix();
/* 30:46 */     GL11.glDisable(2884);
/* 31:47 */     float var10 = func_82400_a(par1EntityWitherSkull.prevRotationYaw, par1EntityWitherSkull.rotationYaw, par9);
/* 32:48 */     float var11 = par1EntityWitherSkull.prevRotationPitch + (par1EntityWitherSkull.rotationPitch - par1EntityWitherSkull.prevRotationPitch) * par9;
/* 33:49 */     GL11.glTranslatef((float)par2, (float)par4, (float)par6);
/* 34:50 */     float var12 = 0.0625F;
/* 35:51 */     GL11.glEnable(32826);
/* 36:52 */     GL11.glScalef(-1.0F, -1.0F, 1.0F);
/* 37:53 */     GL11.glEnable(3008);
/* 38:54 */     bindEntityTexture(par1EntityWitherSkull);
/* 39:55 */     this.skeletonHeadModel.render(par1EntityWitherSkull, 0.0F, 0.0F, 0.0F, var10, var11, var12);
/* 40:56 */     GL11.glPopMatrix();
/* 41:   */   }
/* 42:   */   
/* 43:   */   protected ResourceLocation getEntityTexture(EntityWitherSkull par1EntityWitherSkull)
/* 44:   */   {
/* 45:64 */     return par1EntityWitherSkull.isInvulnerable() ? invulnerableWitherTextures : witherTextures;
/* 46:   */   }
/* 47:   */   
/* 48:   */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/* 49:   */   {
/* 50:72 */     return getEntityTexture((EntityWitherSkull)par1Entity);
/* 51:   */   }
/* 52:   */   
/* 53:   */   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/* 54:   */   {
/* 55:83 */     doRender((EntityWitherSkull)par1Entity, par2, par4, par6, par8, par9);
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.tileentity.RenderWitherSkull
 * JD-Core Version:    0.7.0.1
 */