/*  1:   */ package net.minecraft.client.particle;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.renderer.Tessellator;
/*  4:   */ import net.minecraft.client.renderer.texture.TextureManager;
/*  5:   */ import net.minecraft.util.MathHelper;
/*  6:   */ import net.minecraft.util.ResourceLocation;
/*  7:   */ import net.minecraft.world.World;
/*  8:   */ import org.lwjgl.opengl.GL11;
/*  9:   */ 
/* 10:   */ public class EntityFootStepFX
/* 11:   */   extends EntityFX
/* 12:   */ {
/* 13:12 */   private static final ResourceLocation field_110126_a = new ResourceLocation("textures/particle/footprint.png");
/* 14:   */   private int footstepAge;
/* 15:   */   private int footstepMaxAge;
/* 16:   */   private TextureManager currentFootSteps;
/* 17:   */   private static final String __OBFID = "CL_00000908";
/* 18:   */   
/* 19:   */   public EntityFootStepFX(TextureManager par1TextureManager, World par2World, double par3, double par5, double par7)
/* 20:   */   {
/* 21:20 */     super(par2World, par3, par5, par7, 0.0D, 0.0D, 0.0D);
/* 22:21 */     this.currentFootSteps = par1TextureManager;
/* 23:22 */     this.motionX = (this.motionY = this.motionZ = 0.0D);
/* 24:23 */     this.footstepMaxAge = 200;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
/* 28:   */   {
/* 29:28 */     float var8 = (this.footstepAge + par2) / this.footstepMaxAge;
/* 30:29 */     var8 *= var8;
/* 31:30 */     float var9 = 2.0F - var8 * 2.0F;
/* 32:32 */     if (var9 > 1.0F) {
/* 33:34 */       var9 = 1.0F;
/* 34:   */     }
/* 35:37 */     var9 *= 0.2F;
/* 36:38 */     GL11.glDisable(2896);
/* 37:39 */     float var10 = 0.125F;
/* 38:40 */     float var11 = (float)(this.posX - interpPosX);
/* 39:41 */     float var12 = (float)(this.posY - interpPosY);
/* 40:42 */     float var13 = (float)(this.posZ - interpPosZ);
/* 41:43 */     float var14 = this.worldObj.getLightBrightness(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
/* 42:44 */     this.currentFootSteps.bindTexture(field_110126_a);
/* 43:45 */     GL11.glEnable(3042);
/* 44:46 */     GL11.glBlendFunc(770, 771);
/* 45:47 */     par1Tessellator.startDrawingQuads();
/* 46:48 */     par1Tessellator.setColorRGBA_F(var14, var14, var14, var9);
/* 47:49 */     par1Tessellator.addVertexWithUV(var11 - var10, var12, var13 + var10, 0.0D, 1.0D);
/* 48:50 */     par1Tessellator.addVertexWithUV(var11 + var10, var12, var13 + var10, 1.0D, 1.0D);
/* 49:51 */     par1Tessellator.addVertexWithUV(var11 + var10, var12, var13 - var10, 1.0D, 0.0D);
/* 50:52 */     par1Tessellator.addVertexWithUV(var11 - var10, var12, var13 - var10, 0.0D, 0.0D);
/* 51:53 */     par1Tessellator.draw();
/* 52:54 */     GL11.glDisable(3042);
/* 53:55 */     GL11.glEnable(2896);
/* 54:   */   }
/* 55:   */   
/* 56:   */   public void onUpdate()
/* 57:   */   {
/* 58:63 */     this.footstepAge += 1;
/* 59:65 */     if (this.footstepAge == this.footstepMaxAge) {
/* 60:67 */       setDead();
/* 61:   */     }
/* 62:   */   }
/* 63:   */   
/* 64:   */   public int getFXLayer()
/* 65:   */   {
/* 66:73 */     return 3;
/* 67:   */   }
/* 68:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.particle.EntityFootStepFX
 * JD-Core Version:    0.7.0.1
 */