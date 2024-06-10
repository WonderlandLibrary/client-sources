/*   1:    */ package net.minecraft.client.renderer.entity;
/*   2:    */ 
/*   3:    */ import net.minecraft.client.model.ModelIronGolem;
/*   4:    */ import net.minecraft.client.model.ModelRenderer;
/*   5:    */ import net.minecraft.client.renderer.OpenGlHelper;
/*   6:    */ import net.minecraft.client.renderer.RenderBlocks;
/*   7:    */ import net.minecraft.client.renderer.texture.TextureMap;
/*   8:    */ import net.minecraft.entity.Entity;
/*   9:    */ import net.minecraft.entity.EntityLiving;
/*  10:    */ import net.minecraft.entity.EntityLivingBase;
/*  11:    */ import net.minecraft.entity.monster.EntityIronGolem;
/*  12:    */ import net.minecraft.init.Blocks;
/*  13:    */ import net.minecraft.util.ResourceLocation;
/*  14:    */ import org.lwjgl.opengl.GL11;
/*  15:    */ 
/*  16:    */ public class RenderIronGolem
/*  17:    */   extends RenderLiving
/*  18:    */ {
/*  19: 17 */   private static final ResourceLocation ironGolemTextures = new ResourceLocation("textures/entity/iron_golem.png");
/*  20:    */   private final ModelIronGolem ironGolemModel;
/*  21:    */   private static final String __OBFID = "CL_00001031";
/*  22:    */   
/*  23:    */   public RenderIronGolem()
/*  24:    */   {
/*  25: 25 */     super(new ModelIronGolem(), 0.5F);
/*  26: 26 */     this.ironGolemModel = ((ModelIronGolem)this.mainModel);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void doRender(EntityIronGolem par1EntityIronGolem, double par2, double par4, double par6, float par8, float par9)
/*  30:    */   {
/*  31: 37 */     super.doRender(par1EntityIronGolem, par2, par4, par6, par8, par9);
/*  32:    */   }
/*  33:    */   
/*  34:    */   protected ResourceLocation getEntityTexture(EntityIronGolem par1EntityIronGolem)
/*  35:    */   {
/*  36: 45 */     return ironGolemTextures;
/*  37:    */   }
/*  38:    */   
/*  39:    */   protected void rotateCorpse(EntityIronGolem par1EntityIronGolem, float par2, float par3, float par4)
/*  40:    */   {
/*  41: 50 */     super.rotateCorpse(par1EntityIronGolem, par2, par3, par4);
/*  42: 52 */     if (par1EntityIronGolem.limbSwingAmount >= 0.01D)
/*  43:    */     {
/*  44: 54 */       float var5 = 13.0F;
/*  45: 55 */       float var6 = par1EntityIronGolem.limbSwing - par1EntityIronGolem.limbSwingAmount * (1.0F - par4) + 6.0F;
/*  46: 56 */       float var7 = (Math.abs(var6 % var5 - var5 * 0.5F) - var5 * 0.25F) / (var5 * 0.25F);
/*  47: 57 */       GL11.glRotatef(6.5F * var7, 0.0F, 0.0F, 1.0F);
/*  48:    */     }
/*  49:    */   }
/*  50:    */   
/*  51:    */   protected void renderEquippedItems(EntityIronGolem par1EntityIronGolem, float par2)
/*  52:    */   {
/*  53: 63 */     super.renderEquippedItems(par1EntityIronGolem, par2);
/*  54: 65 */     if (par1EntityIronGolem.getHoldRoseTick() != 0)
/*  55:    */     {
/*  56: 67 */       GL11.glEnable(32826);
/*  57: 68 */       GL11.glPushMatrix();
/*  58: 69 */       GL11.glRotatef(5.0F + 180.0F * this.ironGolemModel.ironGolemRightArm.rotateAngleX / 3.141593F, 1.0F, 0.0F, 0.0F);
/*  59: 70 */       GL11.glTranslatef(-0.6875F, 1.25F, -0.9375F);
/*  60: 71 */       GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/*  61: 72 */       float var3 = 0.8F;
/*  62: 73 */       GL11.glScalef(var3, -var3, var3);
/*  63: 74 */       int var4 = par1EntityIronGolem.getBrightnessForRender(par2);
/*  64: 75 */       int var5 = var4 % 65536;
/*  65: 76 */       int var6 = var4 / 65536;
/*  66: 77 */       OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var5 / 1.0F, var6 / 1.0F);
/*  67: 78 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  68: 79 */       bindTexture(TextureMap.locationBlocksTexture);
/*  69: 80 */       this.field_147909_c.renderBlockAsItem(Blocks.red_flower, 0, 1.0F);
/*  70: 81 */       GL11.glPopMatrix();
/*  71: 82 */       GL11.glDisable(32826);
/*  72:    */     }
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
/*  76:    */   {
/*  77: 94 */     doRender((EntityIronGolem)par1EntityLiving, par2, par4, par6, par8, par9);
/*  78:    */   }
/*  79:    */   
/*  80:    */   protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2)
/*  81:    */   {
/*  82: 99 */     renderEquippedItems((EntityIronGolem)par1EntityLivingBase, par2);
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected void rotateCorpse(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
/*  86:    */   {
/*  87:104 */     rotateCorpse((EntityIronGolem)par1EntityLivingBase, par2, par3, par4);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void doRender(EntityLivingBase par1Entity, double par2, double par4, double par6, float par8, float par9)
/*  91:    */   {
/*  92:115 */     doRender((EntityIronGolem)par1Entity, par2, par4, par6, par8, par9);
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/*  96:    */   {
/*  97:123 */     return getEntityTexture((EntityIronGolem)par1Entity);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/* 101:    */   {
/* 102:134 */     doRender((EntityIronGolem)par1Entity, par2, par4, par6, par8, par9);
/* 103:    */   }
/* 104:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderIronGolem
 * JD-Core Version:    0.7.0.1
 */