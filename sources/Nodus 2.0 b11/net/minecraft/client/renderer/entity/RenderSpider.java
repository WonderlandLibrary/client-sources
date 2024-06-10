/*  1:   */ package net.minecraft.client.renderer.entity;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.model.ModelSpider;
/*  4:   */ import net.minecraft.client.renderer.OpenGlHelper;
/*  5:   */ import net.minecraft.entity.Entity;
/*  6:   */ import net.minecraft.entity.EntityLivingBase;
/*  7:   */ import net.minecraft.entity.monster.EntitySpider;
/*  8:   */ import net.minecraft.util.ResourceLocation;
/*  9:   */ import org.lwjgl.opengl.GL11;
/* 10:   */ 
/* 11:   */ public class RenderSpider
/* 12:   */   extends RenderLiving
/* 13:   */ {
/* 14:13 */   private static final ResourceLocation spiderEyesTextures = new ResourceLocation("textures/entity/spider_eyes.png");
/* 15:14 */   private static final ResourceLocation spiderTextures = new ResourceLocation("textures/entity/spider/spider.png");
/* 16:   */   private static final String __OBFID = "CL_00001027";
/* 17:   */   
/* 18:   */   public RenderSpider()
/* 19:   */   {
/* 20:19 */     super(new ModelSpider(), 1.0F);
/* 21:20 */     setRenderPassModel(new ModelSpider());
/* 22:   */   }
/* 23:   */   
/* 24:   */   protected float getDeathMaxRotation(EntitySpider par1EntitySpider)
/* 25:   */   {
/* 26:25 */     return 180.0F;
/* 27:   */   }
/* 28:   */   
/* 29:   */   protected int shouldRenderPass(EntitySpider par1EntitySpider, int par2, float par3)
/* 30:   */   {
/* 31:33 */     if (par2 != 0) {
/* 32:35 */       return -1;
/* 33:   */     }
/* 34:39 */     bindTexture(spiderEyesTextures);
/* 35:40 */     GL11.glEnable(3042);
/* 36:41 */     GL11.glDisable(3008);
/* 37:42 */     GL11.glBlendFunc(1, 1);
/* 38:44 */     if (par1EntitySpider.isInvisible()) {
/* 39:46 */       GL11.glDepthMask(false);
/* 40:   */     } else {
/* 41:50 */       GL11.glDepthMask(true);
/* 42:   */     }
/* 43:53 */     char var4 = 61680;
/* 44:54 */     int var5 = var4 % 65536;
/* 45:55 */     int var6 = var4 / 65536;
/* 46:56 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var5 / 1.0F, var6 / 1.0F);
/* 47:57 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 48:58 */     return 1;
/* 49:   */   }
/* 50:   */   
/* 51:   */   protected ResourceLocation getEntityTexture(EntitySpider par1EntitySpider)
/* 52:   */   {
/* 53:67 */     return spiderTextures;
/* 54:   */   }
/* 55:   */   
/* 56:   */   protected float getDeathMaxRotation(EntityLivingBase par1EntityLivingBase)
/* 57:   */   {
/* 58:72 */     return getDeathMaxRotation((EntitySpider)par1EntityLivingBase);
/* 59:   */   }
/* 60:   */   
/* 61:   */   protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
/* 62:   */   {
/* 63:80 */     return shouldRenderPass((EntitySpider)par1EntityLivingBase, par2, par3);
/* 64:   */   }
/* 65:   */   
/* 66:   */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/* 67:   */   {
/* 68:88 */     return getEntityTexture((EntitySpider)par1Entity);
/* 69:   */   }
/* 70:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderSpider
 * JD-Core Version:    0.7.0.1
 */