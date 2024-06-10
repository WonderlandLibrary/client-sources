/*   1:    */ package net.minecraft.client.renderer.entity;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.block.material.Material;
/*   6:    */ import net.minecraft.client.model.ModelEnderman;
/*   7:    */ import net.minecraft.client.renderer.OpenGlHelper;
/*   8:    */ import net.minecraft.client.renderer.RenderBlocks;
/*   9:    */ import net.minecraft.client.renderer.texture.TextureMap;
/*  10:    */ import net.minecraft.entity.Entity;
/*  11:    */ import net.minecraft.entity.EntityLiving;
/*  12:    */ import net.minecraft.entity.EntityLivingBase;
/*  13:    */ import net.minecraft.entity.monster.EntityEnderman;
/*  14:    */ import net.minecraft.util.ResourceLocation;
/*  15:    */ import org.lwjgl.opengl.GL11;
/*  16:    */ 
/*  17:    */ public class RenderEnderman
/*  18:    */   extends RenderLiving
/*  19:    */ {
/*  20: 18 */   private static final ResourceLocation endermanEyesTexture = new ResourceLocation("textures/entity/enderman/enderman_eyes.png");
/*  21: 19 */   private static final ResourceLocation endermanTextures = new ResourceLocation("textures/entity/enderman/enderman.png");
/*  22:    */   private ModelEnderman endermanModel;
/*  23: 23 */   private Random rnd = new Random();
/*  24:    */   private static final String __OBFID = "CL_00000989";
/*  25:    */   
/*  26:    */   public RenderEnderman()
/*  27:    */   {
/*  28: 28 */     super(new ModelEnderman(), 0.5F);
/*  29: 29 */     this.endermanModel = ((ModelEnderman)this.mainModel);
/*  30: 30 */     setRenderPassModel(this.endermanModel);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void doRender(EntityEnderman par1EntityEnderman, double par2, double par4, double par6, float par8, float par9)
/*  34:    */   {
/*  35: 41 */     this.endermanModel.isCarrying = (par1EntityEnderman.func_146080_bZ().getMaterial() != Material.air);
/*  36: 42 */     this.endermanModel.isAttacking = par1EntityEnderman.isScreaming();
/*  37: 44 */     if (par1EntityEnderman.isScreaming())
/*  38:    */     {
/*  39: 46 */       double var10 = 0.02D;
/*  40: 47 */       par2 += this.rnd.nextGaussian() * var10;
/*  41: 48 */       par6 += this.rnd.nextGaussian() * var10;
/*  42:    */     }
/*  43: 51 */     super.doRender(par1EntityEnderman, par2, par4, par6, par8, par9);
/*  44:    */   }
/*  45:    */   
/*  46:    */   protected ResourceLocation getEntityTexture(EntityEnderman par1EntityEnderman)
/*  47:    */   {
/*  48: 59 */     return endermanTextures;
/*  49:    */   }
/*  50:    */   
/*  51:    */   protected void renderEquippedItems(EntityEnderman par1EntityEnderman, float par2)
/*  52:    */   {
/*  53: 64 */     super.renderEquippedItems(par1EntityEnderman, par2);
/*  54: 66 */     if (par1EntityEnderman.func_146080_bZ().getMaterial() != Material.air)
/*  55:    */     {
/*  56: 68 */       GL11.glEnable(32826);
/*  57: 69 */       GL11.glPushMatrix();
/*  58: 70 */       float var3 = 0.5F;
/*  59: 71 */       GL11.glTranslatef(0.0F, 0.6875F, -0.75F);
/*  60: 72 */       var3 *= 1.0F;
/*  61: 73 */       GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
/*  62: 74 */       GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
/*  63: 75 */       GL11.glScalef(-var3, -var3, var3);
/*  64: 76 */       int var4 = par1EntityEnderman.getBrightnessForRender(par2);
/*  65: 77 */       int var5 = var4 % 65536;
/*  66: 78 */       int var6 = var4 / 65536;
/*  67: 79 */       OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var5 / 1.0F, var6 / 1.0F);
/*  68: 80 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  69: 81 */       bindTexture(TextureMap.locationBlocksTexture);
/*  70: 82 */       this.field_147909_c.renderBlockAsItem(par1EntityEnderman.func_146080_bZ(), par1EntityEnderman.getCarryingData(), 1.0F);
/*  71: 83 */       GL11.glPopMatrix();
/*  72: 84 */       GL11.glDisable(32826);
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   protected int shouldRenderPass(EntityEnderman par1EntityEnderman, int par2, float par3)
/*  77:    */   {
/*  78: 93 */     if (par2 != 0) {
/*  79: 95 */       return -1;
/*  80:    */     }
/*  81: 99 */     bindTexture(endermanEyesTexture);
/*  82:100 */     float var4 = 1.0F;
/*  83:101 */     GL11.glEnable(3042);
/*  84:102 */     GL11.glDisable(3008);
/*  85:103 */     GL11.glBlendFunc(1, 1);
/*  86:104 */     GL11.glDisable(2896);
/*  87:106 */     if (par1EntityEnderman.isInvisible()) {
/*  88:108 */       GL11.glDepthMask(false);
/*  89:    */     } else {
/*  90:112 */       GL11.glDepthMask(true);
/*  91:    */     }
/*  92:115 */     char var5 = 61680;
/*  93:116 */     int var6 = var5 % 65536;
/*  94:117 */     int var7 = var5 / 65536;
/*  95:118 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var6 / 1.0F, var7 / 1.0F);
/*  96:119 */     GL11.glEnable(2896);
/*  97:120 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, var4);
/*  98:121 */     return 1;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
/* 102:    */   {
/* 103:133 */     doRender((EntityEnderman)par1EntityLiving, par2, par4, par6, par8, par9);
/* 104:    */   }
/* 105:    */   
/* 106:    */   protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
/* 107:    */   {
/* 108:141 */     return shouldRenderPass((EntityEnderman)par1EntityLivingBase, par2, par3);
/* 109:    */   }
/* 110:    */   
/* 111:    */   protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2)
/* 112:    */   {
/* 113:146 */     renderEquippedItems((EntityEnderman)par1EntityLivingBase, par2);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void doRender(EntityLivingBase par1Entity, double par2, double par4, double par6, float par8, float par9)
/* 117:    */   {
/* 118:157 */     doRender((EntityEnderman)par1Entity, par2, par4, par6, par8, par9);
/* 119:    */   }
/* 120:    */   
/* 121:    */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/* 122:    */   {
/* 123:165 */     return getEntityTexture((EntityEnderman)par1Entity);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/* 127:    */   {
/* 128:176 */     doRender((EntityEnderman)par1Entity, par2, par4, par6, par8, par9);
/* 129:    */   }
/* 130:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderEnderman
 * JD-Core Version:    0.7.0.1
 */