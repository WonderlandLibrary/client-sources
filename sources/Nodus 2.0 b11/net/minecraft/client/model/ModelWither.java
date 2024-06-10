/*   1:    */ package net.minecraft.client.model;
/*   2:    */ 
/*   3:    */ import net.minecraft.entity.Entity;
/*   4:    */ import net.minecraft.entity.EntityLivingBase;
/*   5:    */ import net.minecraft.entity.boss.EntityWither;
/*   6:    */ import net.minecraft.util.MathHelper;
/*   7:    */ 
/*   8:    */ public class ModelWither
/*   9:    */   extends ModelBase
/*  10:    */ {
/*  11:    */   private ModelRenderer[] field_82905_a;
/*  12:    */   private ModelRenderer[] field_82904_b;
/*  13:    */   private static final String __OBFID = "CL_00000867";
/*  14:    */   
/*  15:    */   public ModelWither()
/*  16:    */   {
/*  17: 16 */     this.textureWidth = 64;
/*  18: 17 */     this.textureHeight = 64;
/*  19: 18 */     this.field_82905_a = new ModelRenderer[3];
/*  20: 19 */     this.field_82905_a[0] = new ModelRenderer(this, 0, 16);
/*  21: 20 */     this.field_82905_a[0].addBox(-10.0F, 3.9F, -0.5F, 20, 3, 3);
/*  22: 21 */     this.field_82905_a[1] = new ModelRenderer(this).setTextureSize(this.textureWidth, this.textureHeight);
/*  23: 22 */     this.field_82905_a[1].setRotationPoint(-2.0F, 6.9F, -0.5F);
/*  24: 23 */     this.field_82905_a[1].setTextureOffset(0, 22).addBox(0.0F, 0.0F, 0.0F, 3, 10, 3);
/*  25: 24 */     this.field_82905_a[1].setTextureOffset(24, 22).addBox(-4.0F, 1.5F, 0.5F, 11, 2, 2);
/*  26: 25 */     this.field_82905_a[1].setTextureOffset(24, 22).addBox(-4.0F, 4.0F, 0.5F, 11, 2, 2);
/*  27: 26 */     this.field_82905_a[1].setTextureOffset(24, 22).addBox(-4.0F, 6.5F, 0.5F, 11, 2, 2);
/*  28: 27 */     this.field_82905_a[2] = new ModelRenderer(this, 12, 22);
/*  29: 28 */     this.field_82905_a[2].addBox(0.0F, 0.0F, 0.0F, 3, 6, 3);
/*  30: 29 */     this.field_82904_b = new ModelRenderer[3];
/*  31: 30 */     this.field_82904_b[0] = new ModelRenderer(this, 0, 0);
/*  32: 31 */     this.field_82904_b[0].addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
/*  33: 32 */     this.field_82904_b[1] = new ModelRenderer(this, 32, 0);
/*  34: 33 */     this.field_82904_b[1].addBox(-4.0F, -4.0F, -4.0F, 6, 6, 6);
/*  35: 34 */     this.field_82904_b[1].rotationPointX = -8.0F;
/*  36: 35 */     this.field_82904_b[1].rotationPointY = 4.0F;
/*  37: 36 */     this.field_82904_b[2] = new ModelRenderer(this, 32, 0);
/*  38: 37 */     this.field_82904_b[2].addBox(-4.0F, -4.0F, -4.0F, 6, 6, 6);
/*  39: 38 */     this.field_82904_b[2].rotationPointX = 10.0F;
/*  40: 39 */     this.field_82904_b[2].rotationPointY = 4.0F;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int func_82903_a()
/*  44:    */   {
/*  45: 44 */     return 32;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
/*  49:    */   {
/*  50: 52 */     setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
/*  51: 53 */     ModelRenderer[] var8 = this.field_82904_b;
/*  52: 54 */     int var9 = var8.length;
/*  53: 58 */     for (int var10 = 0; var10 < var9; var10++)
/*  54:    */     {
/*  55: 60 */       ModelRenderer var11 = var8[var10];
/*  56: 61 */       var11.render(par7);
/*  57:    */     }
/*  58: 64 */     var8 = this.field_82905_a;
/*  59: 65 */     var9 = var8.length;
/*  60: 67 */     for (var10 = 0; var10 < var9; var10++)
/*  61:    */     {
/*  62: 69 */       ModelRenderer var11 = var8[var10];
/*  63: 70 */       var11.render(par7);
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
/*  68:    */   {
/*  69: 81 */     float var8 = MathHelper.cos(par3 * 0.1F);
/*  70: 82 */     this.field_82905_a[1].rotateAngleX = ((0.065F + 0.05F * var8) * 3.141593F);
/*  71: 83 */     this.field_82905_a[2].setRotationPoint(-2.0F, 6.9F + MathHelper.cos(this.field_82905_a[1].rotateAngleX) * 10.0F, -0.5F + MathHelper.sin(this.field_82905_a[1].rotateAngleX) * 10.0F);
/*  72: 84 */     this.field_82905_a[2].rotateAngleX = ((0.265F + 0.1F * var8) * 3.141593F);
/*  73: 85 */     this.field_82904_b[0].rotateAngleY = (par4 / 57.295776F);
/*  74: 86 */     this.field_82904_b[0].rotateAngleX = (par5 / 57.295776F);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setLivingAnimations(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
/*  78:    */   {
/*  79: 95 */     EntityWither var5 = (EntityWither)par1EntityLivingBase;
/*  80: 97 */     for (int var6 = 1; var6 < 3; var6++)
/*  81:    */     {
/*  82: 99 */       this.field_82904_b[var6].rotateAngleY = ((var5.func_82207_a(var6 - 1) - par1EntityLivingBase.renderYawOffset) / 57.295776F);
/*  83:100 */       this.field_82904_b[var6].rotateAngleX = (var5.func_82210_r(var6 - 1) / 57.295776F);
/*  84:    */     }
/*  85:    */   }
/*  86:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.ModelWither
 * JD-Core Version:    0.7.0.1
 */