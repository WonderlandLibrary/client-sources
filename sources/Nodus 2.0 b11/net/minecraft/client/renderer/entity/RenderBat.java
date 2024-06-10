/*   1:    */ package net.minecraft.client.renderer.entity;
/*   2:    */ 
/*   3:    */ import net.minecraft.client.model.ModelBat;
/*   4:    */ import net.minecraft.entity.Entity;
/*   5:    */ import net.minecraft.entity.EntityLiving;
/*   6:    */ import net.minecraft.entity.EntityLivingBase;
/*   7:    */ import net.minecraft.entity.passive.EntityBat;
/*   8:    */ import net.minecraft.util.MathHelper;
/*   9:    */ import net.minecraft.util.ResourceLocation;
/*  10:    */ import org.lwjgl.opengl.GL11;
/*  11:    */ 
/*  12:    */ public class RenderBat
/*  13:    */   extends RenderLiving
/*  14:    */ {
/*  15: 14 */   private static final ResourceLocation batTextures = new ResourceLocation("textures/entity/bat.png");
/*  16:    */   private int renderedBatSize;
/*  17:    */   private static final String __OBFID = "CL_00000979";
/*  18:    */   
/*  19:    */   public RenderBat()
/*  20:    */   {
/*  21: 25 */     super(new ModelBat(), 0.25F);
/*  22: 26 */     this.renderedBatSize = ((ModelBat)this.mainModel).getBatSize();
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void doRender(EntityBat par1EntityBat, double par2, double par4, double par6, float par8, float par9)
/*  26:    */   {
/*  27: 37 */     int var10 = ((ModelBat)this.mainModel).getBatSize();
/*  28: 39 */     if (var10 != this.renderedBatSize)
/*  29:    */     {
/*  30: 41 */       this.renderedBatSize = var10;
/*  31: 42 */       this.mainModel = new ModelBat();
/*  32:    */     }
/*  33: 45 */     super.doRender(par1EntityBat, par2, par4, par6, par8, par9);
/*  34:    */   }
/*  35:    */   
/*  36:    */   protected ResourceLocation getEntityTexture(EntityBat par1EntityBat)
/*  37:    */   {
/*  38: 53 */     return batTextures;
/*  39:    */   }
/*  40:    */   
/*  41:    */   protected void preRenderCallback(EntityBat par1EntityBat, float par2)
/*  42:    */   {
/*  43: 62 */     GL11.glScalef(0.35F, 0.35F, 0.35F);
/*  44:    */   }
/*  45:    */   
/*  46:    */   protected void renderLivingAt(EntityBat par1EntityBat, double par2, double par4, double par6)
/*  47:    */   {
/*  48: 70 */     super.renderLivingAt(par1EntityBat, par2, par4, par6);
/*  49:    */   }
/*  50:    */   
/*  51:    */   protected void rotateCorpse(EntityBat par1EntityBat, float par2, float par3, float par4)
/*  52:    */   {
/*  53: 75 */     if (!par1EntityBat.getIsBatHanging()) {
/*  54: 77 */       GL11.glTranslatef(0.0F, MathHelper.cos(par2 * 0.3F) * 0.1F, 0.0F);
/*  55:    */     } else {
/*  56: 81 */       GL11.glTranslatef(0.0F, -0.1F, 0.0F);
/*  57:    */     }
/*  58: 84 */     super.rotateCorpse(par1EntityBat, par2, par3, par4);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
/*  62:    */   {
/*  63: 95 */     doRender((EntityBat)par1EntityLiving, par2, par4, par6, par8, par9);
/*  64:    */   }
/*  65:    */   
/*  66:    */   protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
/*  67:    */   {
/*  68:104 */     preRenderCallback((EntityBat)par1EntityLivingBase, par2);
/*  69:    */   }
/*  70:    */   
/*  71:    */   protected void rotateCorpse(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
/*  72:    */   {
/*  73:109 */     rotateCorpse((EntityBat)par1EntityLivingBase, par2, par3, par4);
/*  74:    */   }
/*  75:    */   
/*  76:    */   protected void renderLivingAt(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6)
/*  77:    */   {
/*  78:117 */     renderLivingAt((EntityBat)par1EntityLivingBase, par2, par4, par6);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void doRender(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, float par8, float par9)
/*  82:    */   {
/*  83:128 */     doRender((EntityBat)par1EntityLivingBase, par2, par4, par6, par8, par9);
/*  84:    */   }
/*  85:    */   
/*  86:    */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/*  87:    */   {
/*  88:136 */     return getEntityTexture((EntityBat)par1Entity);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/*  92:    */   {
/*  93:147 */     doRender((EntityBat)par1Entity, par2, par4, par6, par8, par9);
/*  94:    */   }
/*  95:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderBat
 * JD-Core Version:    0.7.0.1
 */