/*   1:    */ package net.minecraft.client.renderer.entity;
/*   2:    */ 
/*   3:    */ import net.minecraft.client.model.ModelBase;
/*   4:    */ import net.minecraft.entity.Entity;
/*   5:    */ import net.minecraft.entity.EntityLiving;
/*   6:    */ import net.minecraft.entity.EntityLivingBase;
/*   7:    */ import net.minecraft.entity.passive.EntityOcelot;
/*   8:    */ import net.minecraft.util.ResourceLocation;
/*   9:    */ import org.lwjgl.opengl.GL11;
/*  10:    */ 
/*  11:    */ public class RenderOcelot
/*  12:    */   extends RenderLiving
/*  13:    */ {
/*  14: 13 */   private static final ResourceLocation blackOcelotTextures = new ResourceLocation("textures/entity/cat/black.png");
/*  15: 14 */   private static final ResourceLocation ocelotTextures = new ResourceLocation("textures/entity/cat/ocelot.png");
/*  16: 15 */   private static final ResourceLocation redOcelotTextures = new ResourceLocation("textures/entity/cat/red.png");
/*  17: 16 */   private static final ResourceLocation siameseOcelotTextures = new ResourceLocation("textures/entity/cat/siamese.png");
/*  18:    */   private static final String __OBFID = "CL_00001017";
/*  19:    */   
/*  20:    */   public RenderOcelot(ModelBase par1ModelBase, float par2)
/*  21:    */   {
/*  22: 21 */     super(par1ModelBase, par2);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void doRender(EntityOcelot par1EntityOcelot, double par2, double par4, double par6, float par8, float par9)
/*  26:    */   {
/*  27: 32 */     super.doRender(par1EntityOcelot, par2, par4, par6, par8, par9);
/*  28:    */   }
/*  29:    */   
/*  30:    */   protected ResourceLocation getEntityTexture(EntityOcelot par1EntityOcelot)
/*  31:    */   {
/*  32: 40 */     switch (par1EntityOcelot.getTameSkin())
/*  33:    */     {
/*  34:    */     case 0: 
/*  35:    */     default: 
/*  36: 44 */       return ocelotTextures;
/*  37:    */     case 1: 
/*  38: 47 */       return blackOcelotTextures;
/*  39:    */     case 2: 
/*  40: 50 */       return redOcelotTextures;
/*  41:    */     }
/*  42: 53 */     return siameseOcelotTextures;
/*  43:    */   }
/*  44:    */   
/*  45:    */   protected void preRenderCallback(EntityOcelot par1EntityOcelot, float par2)
/*  46:    */   {
/*  47: 63 */     super.preRenderCallback(par1EntityOcelot, par2);
/*  48: 65 */     if (par1EntityOcelot.isTamed()) {
/*  49: 67 */       GL11.glScalef(0.8F, 0.8F, 0.8F);
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
/*  54:    */   {
/*  55: 79 */     doRender((EntityOcelot)par1EntityLiving, par2, par4, par6, par8, par9);
/*  56:    */   }
/*  57:    */   
/*  58:    */   protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
/*  59:    */   {
/*  60: 88 */     preRenderCallback((EntityOcelot)par1EntityLivingBase, par2);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void doRender(EntityLivingBase par1Entity, double par2, double par4, double par6, float par8, float par9)
/*  64:    */   {
/*  65: 99 */     doRender((EntityOcelot)par1Entity, par2, par4, par6, par8, par9);
/*  66:    */   }
/*  67:    */   
/*  68:    */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/*  69:    */   {
/*  70:107 */     return getEntityTexture((EntityOcelot)par1Entity);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/*  74:    */   {
/*  75:118 */     doRender((EntityOcelot)par1Entity, par2, par4, par6, par8, par9);
/*  76:    */   }
/*  77:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderOcelot
 * JD-Core Version:    0.7.0.1
 */