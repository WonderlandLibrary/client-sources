/*  1:   */ package net.minecraft.client.renderer.entity;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.model.ModelBase;
/*  4:   */ import net.minecraft.entity.Entity;
/*  5:   */ import net.minecraft.entity.EntityLivingBase;
/*  6:   */ import net.minecraft.entity.passive.EntityWolf;
/*  7:   */ import net.minecraft.util.ResourceLocation;
/*  8:   */ import org.lwjgl.opengl.GL11;
/*  9:   */ 
/* 10:   */ public class RenderWolf
/* 11:   */   extends RenderLiving
/* 12:   */ {
/* 13:13 */   private static final ResourceLocation wolfTextures = new ResourceLocation("textures/entity/wolf/wolf.png");
/* 14:14 */   private static final ResourceLocation tamedWolfTextures = new ResourceLocation("textures/entity/wolf/wolf_tame.png");
/* 15:15 */   private static final ResourceLocation anrgyWolfTextures = new ResourceLocation("textures/entity/wolf/wolf_angry.png");
/* 16:16 */   private static final ResourceLocation wolfCollarTextures = new ResourceLocation("textures/entity/wolf/wolf_collar.png");
/* 17:   */   private static final String __OBFID = "CL_00001036";
/* 18:   */   
/* 19:   */   public RenderWolf(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3)
/* 20:   */   {
/* 21:21 */     super(par1ModelBase, par3);
/* 22:22 */     setRenderPassModel(par2ModelBase);
/* 23:   */   }
/* 24:   */   
/* 25:   */   protected float handleRotationFloat(EntityWolf par1EntityWolf, float par2)
/* 26:   */   {
/* 27:30 */     return par1EntityWolf.getTailRotation();
/* 28:   */   }
/* 29:   */   
/* 30:   */   protected int shouldRenderPass(EntityWolf par1EntityWolf, int par2, float par3)
/* 31:   */   {
/* 32:38 */     if ((par2 == 0) && (par1EntityWolf.getWolfShaking()))
/* 33:   */     {
/* 34:40 */       float var5 = par1EntityWolf.getBrightness(par3) * par1EntityWolf.getShadingWhileShaking(par3);
/* 35:41 */       bindTexture(wolfTextures);
/* 36:42 */       GL11.glColor3f(var5, var5, var5);
/* 37:43 */       return 1;
/* 38:   */     }
/* 39:45 */     if ((par2 == 1) && (par1EntityWolf.isTamed()))
/* 40:   */     {
/* 41:47 */       bindTexture(wolfCollarTextures);
/* 42:48 */       int var4 = par1EntityWolf.getCollarColor();
/* 43:49 */       GL11.glColor3f(net.minecraft.entity.passive.EntitySheep.fleeceColorTable[var4][0], net.minecraft.entity.passive.EntitySheep.fleeceColorTable[var4][1], net.minecraft.entity.passive.EntitySheep.fleeceColorTable[var4][2]);
/* 44:50 */       return 1;
/* 45:   */     }
/* 46:54 */     return -1;
/* 47:   */   }
/* 48:   */   
/* 49:   */   protected ResourceLocation getEntityTexture(EntityWolf par1EntityWolf)
/* 50:   */   {
/* 51:63 */     return par1EntityWolf.isAngry() ? anrgyWolfTextures : par1EntityWolf.isTamed() ? tamedWolfTextures : wolfTextures;
/* 52:   */   }
/* 53:   */   
/* 54:   */   protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
/* 55:   */   {
/* 56:71 */     return shouldRenderPass((EntityWolf)par1EntityLivingBase, par2, par3);
/* 57:   */   }
/* 58:   */   
/* 59:   */   protected float handleRotationFloat(EntityLivingBase par1EntityLivingBase, float par2)
/* 60:   */   {
/* 61:79 */     return handleRotationFloat((EntityWolf)par1EntityLivingBase, par2);
/* 62:   */   }
/* 63:   */   
/* 64:   */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/* 65:   */   {
/* 66:87 */     return getEntityTexture((EntityWolf)par1Entity);
/* 67:   */   }
/* 68:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderWolf
 * JD-Core Version:    0.7.0.1
 */