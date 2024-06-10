/*   1:    */ package net.minecraft.client.model;
/*   2:    */ 
/*   3:    */ import net.minecraft.entity.Entity;
/*   4:    */ import net.minecraft.entity.passive.EntityBat;
/*   5:    */ import net.minecraft.util.MathHelper;
/*   6:    */ 
/*   7:    */ public class ModelBat
/*   8:    */   extends ModelBase
/*   9:    */ {
/*  10:    */   private ModelRenderer batHead;
/*  11:    */   private ModelRenderer batBody;
/*  12:    */   private ModelRenderer batRightWing;
/*  13:    */   private ModelRenderer batLeftWing;
/*  14:    */   private ModelRenderer batOuterRightWing;
/*  15:    */   private ModelRenderer batOuterLeftWing;
/*  16:    */   private static final String __OBFID = "CL_00000830";
/*  17:    */   
/*  18:    */   public ModelBat()
/*  19:    */   {
/*  20: 29 */     this.textureWidth = 64;
/*  21: 30 */     this.textureHeight = 64;
/*  22: 31 */     this.batHead = new ModelRenderer(this, 0, 0);
/*  23: 32 */     this.batHead.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6);
/*  24: 33 */     ModelRenderer var1 = new ModelRenderer(this, 24, 0);
/*  25: 34 */     var1.addBox(-4.0F, -6.0F, -2.0F, 3, 4, 1);
/*  26: 35 */     this.batHead.addChild(var1);
/*  27: 36 */     ModelRenderer var2 = new ModelRenderer(this, 24, 0);
/*  28: 37 */     var2.mirror = true;
/*  29: 38 */     var2.addBox(1.0F, -6.0F, -2.0F, 3, 4, 1);
/*  30: 39 */     this.batHead.addChild(var2);
/*  31: 40 */     this.batBody = new ModelRenderer(this, 0, 16);
/*  32: 41 */     this.batBody.addBox(-3.0F, 4.0F, -3.0F, 6, 12, 6);
/*  33: 42 */     this.batBody.setTextureOffset(0, 34).addBox(-5.0F, 16.0F, 0.0F, 10, 6, 1);
/*  34: 43 */     this.batRightWing = new ModelRenderer(this, 42, 0);
/*  35: 44 */     this.batRightWing.addBox(-12.0F, 1.0F, 1.5F, 10, 16, 1);
/*  36: 45 */     this.batOuterRightWing = new ModelRenderer(this, 24, 16);
/*  37: 46 */     this.batOuterRightWing.setRotationPoint(-12.0F, 1.0F, 1.5F);
/*  38: 47 */     this.batOuterRightWing.addBox(-8.0F, 1.0F, 0.0F, 8, 12, 1);
/*  39: 48 */     this.batLeftWing = new ModelRenderer(this, 42, 0);
/*  40: 49 */     this.batLeftWing.mirror = true;
/*  41: 50 */     this.batLeftWing.addBox(2.0F, 1.0F, 1.5F, 10, 16, 1);
/*  42: 51 */     this.batOuterLeftWing = new ModelRenderer(this, 24, 16);
/*  43: 52 */     this.batOuterLeftWing.mirror = true;
/*  44: 53 */     this.batOuterLeftWing.setRotationPoint(12.0F, 1.0F, 1.5F);
/*  45: 54 */     this.batOuterLeftWing.addBox(0.0F, 1.0F, 0.0F, 8, 12, 1);
/*  46: 55 */     this.batBody.addChild(this.batRightWing);
/*  47: 56 */     this.batBody.addChild(this.batLeftWing);
/*  48: 57 */     this.batRightWing.addChild(this.batOuterRightWing);
/*  49: 58 */     this.batLeftWing.addChild(this.batOuterLeftWing);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public int getBatSize()
/*  53:    */   {
/*  54: 67 */     return 36;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
/*  58:    */   {
/*  59: 75 */     EntityBat var8 = (EntityBat)par1Entity;
/*  60: 78 */     if (var8.getIsBatHanging())
/*  61:    */     {
/*  62: 80 */       float var9 = 57.295776F;
/*  63: 81 */       this.batHead.rotateAngleX = (par6 / 57.295776F);
/*  64: 82 */       this.batHead.rotateAngleY = (3.141593F - par5 / 57.295776F);
/*  65: 83 */       this.batHead.rotateAngleZ = 3.141593F;
/*  66: 84 */       this.batHead.setRotationPoint(0.0F, -2.0F, 0.0F);
/*  67: 85 */       this.batRightWing.setRotationPoint(-3.0F, 0.0F, 3.0F);
/*  68: 86 */       this.batLeftWing.setRotationPoint(3.0F, 0.0F, 3.0F);
/*  69: 87 */       this.batBody.rotateAngleX = 3.141593F;
/*  70: 88 */       this.batRightWing.rotateAngleX = -0.1570796F;
/*  71: 89 */       this.batRightWing.rotateAngleY = -1.256637F;
/*  72: 90 */       this.batOuterRightWing.rotateAngleY = -1.727876F;
/*  73: 91 */       this.batLeftWing.rotateAngleX = this.batRightWing.rotateAngleX;
/*  74: 92 */       this.batLeftWing.rotateAngleY = (-this.batRightWing.rotateAngleY);
/*  75: 93 */       this.batOuterLeftWing.rotateAngleY = (-this.batOuterRightWing.rotateAngleY);
/*  76:    */     }
/*  77:    */     else
/*  78:    */     {
/*  79: 97 */       float var9 = 57.295776F;
/*  80: 98 */       this.batHead.rotateAngleX = (par6 / 57.295776F);
/*  81: 99 */       this.batHead.rotateAngleY = (par5 / 57.295776F);
/*  82:100 */       this.batHead.rotateAngleZ = 0.0F;
/*  83:101 */       this.batHead.setRotationPoint(0.0F, 0.0F, 0.0F);
/*  84:102 */       this.batRightWing.setRotationPoint(0.0F, 0.0F, 0.0F);
/*  85:103 */       this.batLeftWing.setRotationPoint(0.0F, 0.0F, 0.0F);
/*  86:104 */       this.batBody.rotateAngleX = (0.7853982F + MathHelper.cos(par4 * 0.1F) * 0.15F);
/*  87:105 */       this.batBody.rotateAngleY = 0.0F;
/*  88:106 */       this.batRightWing.rotateAngleY = (MathHelper.cos(par4 * 1.3F) * 3.141593F * 0.25F);
/*  89:107 */       this.batLeftWing.rotateAngleY = (-this.batRightWing.rotateAngleY);
/*  90:108 */       this.batOuterRightWing.rotateAngleY = (this.batRightWing.rotateAngleY * 0.5F);
/*  91:109 */       this.batOuterLeftWing.rotateAngleY = (-this.batRightWing.rotateAngleY * 0.5F);
/*  92:    */     }
/*  93:112 */     this.batHead.render(par7);
/*  94:113 */     this.batBody.render(par7);
/*  95:    */   }
/*  96:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.ModelBat
 * JD-Core Version:    0.7.0.1
 */