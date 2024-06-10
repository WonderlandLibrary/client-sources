/*  1:   */ package net.minecraft.client.renderer.entity;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.Entity;
/*  4:   */ import net.minecraft.entity.EntityLivingBase;
/*  5:   */ import net.minecraft.entity.monster.EntityCaveSpider;
/*  6:   */ import net.minecraft.entity.monster.EntitySpider;
/*  7:   */ import net.minecraft.util.ResourceLocation;
/*  8:   */ import org.lwjgl.opengl.GL11;
/*  9:   */ 
/* 10:   */ public class RenderCaveSpider
/* 11:   */   extends RenderSpider
/* 12:   */ {
/* 13:12 */   private static final ResourceLocation caveSpiderTextures = new ResourceLocation("textures/entity/spider/cave_spider.png");
/* 14:   */   private static final String __OBFID = "CL_00000982";
/* 15:   */   
/* 16:   */   public RenderCaveSpider()
/* 17:   */   {
/* 18:17 */     this.shadowSize *= 0.7F;
/* 19:   */   }
/* 20:   */   
/* 21:   */   protected void preRenderCallback(EntityCaveSpider par1EntityCaveSpider, float par2)
/* 22:   */   {
/* 23:26 */     GL11.glScalef(0.7F, 0.7F, 0.7F);
/* 24:   */   }
/* 25:   */   
/* 26:   */   protected ResourceLocation getEntityTexture(EntityCaveSpider par1EntityCaveSpider)
/* 27:   */   {
/* 28:34 */     return caveSpiderTextures;
/* 29:   */   }
/* 30:   */   
/* 31:   */   protected ResourceLocation getEntityTexture(EntitySpider par1EntitySpider)
/* 32:   */   {
/* 33:42 */     return getEntityTexture((EntityCaveSpider)par1EntitySpider);
/* 34:   */   }
/* 35:   */   
/* 36:   */   protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
/* 37:   */   {
/* 38:51 */     preRenderCallback((EntityCaveSpider)par1EntityLivingBase, par2);
/* 39:   */   }
/* 40:   */   
/* 41:   */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/* 42:   */   {
/* 43:59 */     return getEntityTexture((EntityCaveSpider)par1Entity);
/* 44:   */   }
/* 45:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderCaveSpider
 * JD-Core Version:    0.7.0.1
 */