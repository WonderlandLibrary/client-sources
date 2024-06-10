/*  1:   */ package net.minecraft.client.renderer.entity;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.model.ModelBase;
/*  4:   */ import net.minecraft.entity.Entity;
/*  5:   */ import net.minecraft.entity.EntityLiving;
/*  6:   */ import net.minecraft.entity.EntityLivingBase;
/*  7:   */ import net.minecraft.entity.passive.EntityChicken;
/*  8:   */ import net.minecraft.util.MathHelper;
/*  9:   */ import net.minecraft.util.ResourceLocation;
/* 10:   */ 
/* 11:   */ public class RenderChicken
/* 12:   */   extends RenderLiving
/* 13:   */ {
/* 14:13 */   private static final ResourceLocation chickenTextures = new ResourceLocation("textures/entity/chicken.png");
/* 15:   */   private static final String __OBFID = "CL_00000983";
/* 16:   */   
/* 17:   */   public RenderChicken(ModelBase par1ModelBase, float par2)
/* 18:   */   {
/* 19:18 */     super(par1ModelBase, par2);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void doRender(EntityChicken par1EntityChicken, double par2, double par4, double par6, float par8, float par9)
/* 23:   */   {
/* 24:29 */     super.doRender(par1EntityChicken, par2, par4, par6, par8, par9);
/* 25:   */   }
/* 26:   */   
/* 27:   */   protected ResourceLocation getEntityTexture(EntityChicken par1EntityChicken)
/* 28:   */   {
/* 29:37 */     return chickenTextures;
/* 30:   */   }
/* 31:   */   
/* 32:   */   protected float handleRotationFloat(EntityChicken par1EntityChicken, float par2)
/* 33:   */   {
/* 34:45 */     float var3 = par1EntityChicken.field_70888_h + (par1EntityChicken.field_70886_e - par1EntityChicken.field_70888_h) * par2;
/* 35:46 */     float var4 = par1EntityChicken.field_70884_g + (par1EntityChicken.destPos - par1EntityChicken.field_70884_g) * par2;
/* 36:47 */     return (MathHelper.sin(var3) + 1.0F) * var4;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
/* 40:   */   {
/* 41:58 */     doRender((EntityChicken)par1EntityLiving, par2, par4, par6, par8, par9);
/* 42:   */   }
/* 43:   */   
/* 44:   */   protected float handleRotationFloat(EntityLivingBase par1EntityLivingBase, float par2)
/* 45:   */   {
/* 46:66 */     return handleRotationFloat((EntityChicken)par1EntityLivingBase, par2);
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void doRender(EntityLivingBase par1Entity, double par2, double par4, double par6, float par8, float par9)
/* 50:   */   {
/* 51:77 */     doRender((EntityChicken)par1Entity, par2, par4, par6, par8, par9);
/* 52:   */   }
/* 53:   */   
/* 54:   */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/* 55:   */   {
/* 56:85 */     return getEntityTexture((EntityChicken)par1Entity);
/* 57:   */   }
/* 58:   */   
/* 59:   */   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/* 60:   */   {
/* 61:96 */     doRender((EntityChicken)par1Entity, par2, par4, par6, par8, par9);
/* 62:   */   }
/* 63:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderChicken
 * JD-Core Version:    0.7.0.1
 */