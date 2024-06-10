/*  1:   */ package net.minecraft.client.renderer.entity;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.model.ModelBase;
/*  4:   */ import net.minecraft.entity.Entity;
/*  5:   */ import net.minecraft.entity.EntityLivingBase;
/*  6:   */ import net.minecraft.entity.passive.EntityPig;
/*  7:   */ import net.minecraft.util.ResourceLocation;
/*  8:   */ 
/*  9:   */ public class RenderPig
/* 10:   */   extends RenderLiving
/* 11:   */ {
/* 12:11 */   private static final ResourceLocation saddledPigTextures = new ResourceLocation("textures/entity/pig/pig_saddle.png");
/* 13:12 */   private static final ResourceLocation pigTextures = new ResourceLocation("textures/entity/pig/pig.png");
/* 14:   */   private static final String __OBFID = "CL_00001019";
/* 15:   */   
/* 16:   */   public RenderPig(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3)
/* 17:   */   {
/* 18:17 */     super(par1ModelBase, par3);
/* 19:18 */     setRenderPassModel(par2ModelBase);
/* 20:   */   }
/* 21:   */   
/* 22:   */   protected int shouldRenderPass(EntityPig par1EntityPig, int par2, float par3)
/* 23:   */   {
/* 24:26 */     if ((par2 == 0) && (par1EntityPig.getSaddled()))
/* 25:   */     {
/* 26:28 */       bindTexture(saddledPigTextures);
/* 27:29 */       return 1;
/* 28:   */     }
/* 29:33 */     return -1;
/* 30:   */   }
/* 31:   */   
/* 32:   */   protected ResourceLocation getEntityTexture(EntityPig par1EntityPig)
/* 33:   */   {
/* 34:42 */     return pigTextures;
/* 35:   */   }
/* 36:   */   
/* 37:   */   protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
/* 38:   */   {
/* 39:50 */     return shouldRenderPass((EntityPig)par1EntityLivingBase, par2, par3);
/* 40:   */   }
/* 41:   */   
/* 42:   */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/* 43:   */   {
/* 44:58 */     return getEntityTexture((EntityPig)par1Entity);
/* 45:   */   }
/* 46:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderPig
 * JD-Core Version:    0.7.0.1
 */