/*  1:   */ package net.minecraft.client.particle;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.renderer.OpenGlHelper;
/*  4:   */ import net.minecraft.client.renderer.Tessellator;
/*  5:   */ import net.minecraft.client.renderer.entity.RenderManager;
/*  6:   */ import net.minecraft.entity.Entity;
/*  7:   */ import net.minecraft.world.World;
/*  8:   */ import org.lwjgl.opengl.GL11;
/*  9:   */ 
/* 10:   */ public class EntityPickupFX
/* 11:   */   extends EntityFX
/* 12:   */ {
/* 13:   */   private Entity entityToPickUp;
/* 14:   */   private Entity entityPickingUp;
/* 15:   */   private int age;
/* 16:   */   private int maxAge;
/* 17:   */   private float yOffs;
/* 18:   */   private static final String __OBFID = "CL_00000930";
/* 19:   */   
/* 20:   */   public EntityPickupFX(World par1World, Entity par2Entity, Entity par3Entity, float par4)
/* 21:   */   {
/* 22:23 */     super(par1World, par2Entity.posX, par2Entity.posY, par2Entity.posZ, par2Entity.motionX, par2Entity.motionY, par2Entity.motionZ);
/* 23:24 */     this.entityToPickUp = par2Entity;
/* 24:25 */     this.entityPickingUp = par3Entity;
/* 25:26 */     this.maxAge = 3;
/* 26:27 */     this.yOffs = par4;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
/* 30:   */   {
/* 31:32 */     float var8 = (this.age + par2) / this.maxAge;
/* 32:33 */     var8 *= var8;
/* 33:34 */     double var9 = this.entityToPickUp.posX;
/* 34:35 */     double var11 = this.entityToPickUp.posY;
/* 35:36 */     double var13 = this.entityToPickUp.posZ;
/* 36:37 */     double var15 = this.entityPickingUp.lastTickPosX + (this.entityPickingUp.posX - this.entityPickingUp.lastTickPosX) * par2;
/* 37:38 */     double var17 = this.entityPickingUp.lastTickPosY + (this.entityPickingUp.posY - this.entityPickingUp.lastTickPosY) * par2 + this.yOffs;
/* 38:39 */     double var19 = this.entityPickingUp.lastTickPosZ + (this.entityPickingUp.posZ - this.entityPickingUp.lastTickPosZ) * par2;
/* 39:40 */     double var21 = var9 + (var15 - var9) * var8;
/* 40:41 */     double var23 = var11 + (var17 - var11) * var8;
/* 41:42 */     double var25 = var13 + (var19 - var13) * var8;
/* 42:43 */     int var27 = getBrightnessForRender(par2);
/* 43:44 */     int var28 = var27 % 65536;
/* 44:45 */     int var29 = var27 / 65536;
/* 45:46 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var28 / 1.0F, var29 / 1.0F);
/* 46:47 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 47:48 */     var21 -= interpPosX;
/* 48:49 */     var23 -= interpPosY;
/* 49:50 */     var25 -= interpPosZ;
/* 50:51 */     RenderManager.instance.func_147940_a(this.entityToPickUp, (float)var21, (float)var23, (float)var25, this.entityToPickUp.rotationYaw, par2);
/* 51:   */   }
/* 52:   */   
/* 53:   */   public void onUpdate()
/* 54:   */   {
/* 55:59 */     this.age += 1;
/* 56:61 */     if (this.age == this.maxAge) {
/* 57:63 */       setDead();
/* 58:   */     }
/* 59:   */   }
/* 60:   */   
/* 61:   */   public int getFXLayer()
/* 62:   */   {
/* 63:69 */     return 3;
/* 64:   */   }
/* 65:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.particle.EntityPickupFX
 * JD-Core Version:    0.7.0.1
 */