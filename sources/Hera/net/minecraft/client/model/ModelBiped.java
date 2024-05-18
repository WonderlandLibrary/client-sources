/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModelBiped
/*     */   extends ModelBase
/*     */ {
/*     */   public ModelRenderer bipedHead;
/*     */   public ModelRenderer bipedHeadwear;
/*     */   public ModelRenderer bipedBody;
/*     */   public ModelRenderer bipedRightArm;
/*     */   public ModelRenderer bipedLeftArm;
/*     */   public ModelRenderer bipedRightLeg;
/*     */   public ModelRenderer bipedLeftLeg;
/*     */   public int heldItemLeft;
/*     */   public int heldItemRight;
/*     */   public boolean isSneak;
/*     */   public boolean aimedBow;
/*     */   
/*     */   public ModelBiped() {
/*  43 */     this(0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelBiped(float modelSize) {
/*  48 */     this(modelSize, 0.0F, 64, 32);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelBiped(float modelSize, float p_i1149_2_, int textureWidthIn, int textureHeightIn) {
/*  53 */     this.textureWidth = textureWidthIn;
/*  54 */     this.textureHeight = textureHeightIn;
/*  55 */     this.bipedHead = new ModelRenderer(this, 0, 0);
/*  56 */     this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, modelSize);
/*  57 */     this.bipedHead.setRotationPoint(0.0F, 0.0F + p_i1149_2_, 0.0F);
/*  58 */     this.bipedHeadwear = new ModelRenderer(this, 32, 0);
/*  59 */     this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, modelSize + 0.5F);
/*  60 */     this.bipedHeadwear.setRotationPoint(0.0F, 0.0F + p_i1149_2_, 0.0F);
/*  61 */     this.bipedBody = new ModelRenderer(this, 16, 16);
/*  62 */     this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, modelSize);
/*  63 */     this.bipedBody.setRotationPoint(0.0F, 0.0F + p_i1149_2_, 0.0F);
/*  64 */     this.bipedRightArm = new ModelRenderer(this, 40, 16);
/*  65 */     this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
/*  66 */     this.bipedRightArm.setRotationPoint(-5.0F, 2.0F + p_i1149_2_, 0.0F);
/*  67 */     this.bipedLeftArm = new ModelRenderer(this, 40, 16);
/*  68 */     this.bipedLeftArm.mirror = true;
/*  69 */     this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
/*  70 */     this.bipedLeftArm.setRotationPoint(5.0F, 2.0F + p_i1149_2_, 0.0F);
/*  71 */     this.bipedRightLeg = new ModelRenderer(this, 0, 16);
/*  72 */     this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize);
/*  73 */     this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F + p_i1149_2_, 0.0F);
/*  74 */     this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
/*  75 */     this.bipedLeftLeg.mirror = true;
/*  76 */     this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize);
/*  77 */     this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F + p_i1149_2_, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale) {
/*  85 */     setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale, entityIn);
/*  86 */     GlStateManager.pushMatrix();
/*     */     
/*  88 */     if (this.isChild) {
/*     */       
/*  90 */       float f = 2.0F;
/*  91 */       GlStateManager.scale(1.5F / f, 1.5F / f, 1.5F / f);
/*  92 */       GlStateManager.translate(0.0F, 16.0F * scale, 0.0F);
/*  93 */       this.bipedHead.render(scale);
/*  94 */       GlStateManager.popMatrix();
/*  95 */       GlStateManager.pushMatrix();
/*  96 */       GlStateManager.scale(1.0F / f, 1.0F / f, 1.0F / f);
/*  97 */       GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
/*  98 */       this.bipedBody.render(scale);
/*  99 */       this.bipedRightArm.render(scale);
/* 100 */       this.bipedLeftArm.render(scale);
/* 101 */       this.bipedRightLeg.render(scale);
/* 102 */       this.bipedLeftLeg.render(scale);
/* 103 */       this.bipedHeadwear.render(scale);
/*     */     }
/*     */     else {
/*     */       
/* 107 */       if (entityIn.isSneaking())
/*     */       {
/* 109 */         GlStateManager.translate(0.0F, 0.2F, 0.0F);
/*     */       }
/*     */       
/* 112 */       this.bipedHead.render(scale);
/* 113 */       this.bipedBody.render(scale);
/* 114 */       this.bipedRightArm.render(scale);
/* 115 */       this.bipedLeftArm.render(scale);
/* 116 */       this.bipedRightLeg.render(scale);
/* 117 */       this.bipedLeftLeg.render(scale);
/* 118 */       this.bipedHeadwear.render(scale);
/*     */     } 
/*     */     
/* 121 */     GlStateManager.popMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity entityIn) {
/* 131 */     this.bipedHead.rotateAngleY = p_78087_4_ / 57.295776F;
/* 132 */     this.bipedHead.rotateAngleX = p_78087_5_ / 57.295776F;
/* 133 */     this.bipedRightArm.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F + 3.1415927F) * 2.0F * p_78087_2_ * 0.5F;
/* 134 */     this.bipedLeftArm.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F) * 2.0F * p_78087_2_ * 0.5F;
/* 135 */     this.bipedRightArm.rotateAngleZ = 0.0F;
/* 136 */     this.bipedLeftArm.rotateAngleZ = 0.0F;
/* 137 */     this.bipedRightLeg.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F) * 1.4F * p_78087_2_;
/* 138 */     this.bipedLeftLeg.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F + 3.1415927F) * 1.4F * p_78087_2_;
/* 139 */     this.bipedRightLeg.rotateAngleY = 0.0F;
/* 140 */     this.bipedLeftLeg.rotateAngleY = 0.0F;
/*     */     
/* 142 */     if (this.isRiding) {
/*     */       
/* 144 */       this.bipedRightArm.rotateAngleX += -0.62831855F;
/* 145 */       this.bipedLeftArm.rotateAngleX += -0.62831855F;
/* 146 */       this.bipedRightLeg.rotateAngleX = -1.2566371F;
/* 147 */       this.bipedLeftLeg.rotateAngleX = -1.2566371F;
/* 148 */       this.bipedRightLeg.rotateAngleY = 0.31415927F;
/* 149 */       this.bipedLeftLeg.rotateAngleY = -0.31415927F;
/*     */     } 
/*     */     
/* 152 */     if (this.heldItemLeft != 0)
/*     */     {
/* 154 */       this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5F - 0.31415927F * this.heldItemLeft;
/*     */     }
/*     */     
/* 157 */     this.bipedRightArm.rotateAngleY = 0.0F;
/* 158 */     this.bipedRightArm.rotateAngleZ = 0.0F;
/*     */     
/* 160 */     switch (this.heldItemRight) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 168 */         this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F - 0.31415927F * this.heldItemRight;
/*     */         break;
/*     */       
/*     */       case 3:
/* 172 */         this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F - 0.31415927F * this.heldItemRight;
/* 173 */         this.bipedRightArm.rotateAngleY = -0.5235988F;
/*     */         break;
/*     */     } 
/* 176 */     this.bipedLeftArm.rotateAngleY = 0.0F;
/*     */     
/* 178 */     if (this.swingProgress > -9990.0F) {
/*     */       
/* 180 */       float f = this.swingProgress;
/* 181 */       this.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(f) * 3.1415927F * 2.0F) * 0.2F;
/* 182 */       this.bipedRightArm.rotationPointZ = MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
/* 183 */       this.bipedRightArm.rotationPointX = -MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
/* 184 */       this.bipedLeftArm.rotationPointZ = -MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
/* 185 */       this.bipedLeftArm.rotationPointX = MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
/* 186 */       this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY;
/* 187 */       this.bipedLeftArm.rotateAngleY += this.bipedBody.rotateAngleY;
/* 188 */       this.bipedLeftArm.rotateAngleX += this.bipedBody.rotateAngleY;
/* 189 */       f = 1.0F - this.swingProgress;
/* 190 */       f *= f;
/* 191 */       f *= f;
/* 192 */       f = 1.0F - f;
/* 193 */       float f1 = MathHelper.sin(f * 3.1415927F);
/* 194 */       float f2 = MathHelper.sin(this.swingProgress * 3.1415927F) * -(this.bipedHead.rotateAngleX - 0.7F) * 0.75F;
/* 195 */       this.bipedRightArm.rotateAngleX = (float)(this.bipedRightArm.rotateAngleX - f1 * 1.2D + f2);
/* 196 */       this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY * 2.0F;
/* 197 */       this.bipedRightArm.rotateAngleZ += MathHelper.sin(this.swingProgress * 3.1415927F) * -0.4F;
/*     */     } 
/*     */     
/* 200 */     if (this.isSneak) {
/*     */       
/* 202 */       this.bipedBody.rotateAngleX = 0.5F;
/* 203 */       this.bipedRightArm.rotateAngleX += 0.4F;
/* 204 */       this.bipedLeftArm.rotateAngleX += 0.4F;
/* 205 */       this.bipedRightLeg.rotationPointZ = 4.0F;
/* 206 */       this.bipedLeftLeg.rotationPointZ = 4.0F;
/* 207 */       this.bipedRightLeg.rotationPointY = 9.0F;
/* 208 */       this.bipedLeftLeg.rotationPointY = 9.0F;
/* 209 */       this.bipedHead.rotationPointY = 1.0F;
/*     */     }
/*     */     else {
/*     */       
/* 213 */       this.bipedBody.rotateAngleX = 0.0F;
/* 214 */       this.bipedRightLeg.rotationPointZ = 0.1F;
/* 215 */       this.bipedLeftLeg.rotationPointZ = 0.1F;
/* 216 */       this.bipedRightLeg.rotationPointY = 12.0F;
/* 217 */       this.bipedLeftLeg.rotationPointY = 12.0F;
/* 218 */       this.bipedHead.rotationPointY = 0.0F;
/*     */     } 
/*     */     
/* 221 */     this.bipedRightArm.rotateAngleZ += MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
/* 222 */     this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
/* 223 */     this.bipedRightArm.rotateAngleX += MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
/* 224 */     this.bipedLeftArm.rotateAngleX -= MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
/*     */     
/* 226 */     if (this.aimedBow) {
/*     */       
/* 228 */       float f3 = 0.0F;
/* 229 */       float f4 = 0.0F;
/* 230 */       this.bipedRightArm.rotateAngleZ = 0.0F;
/* 231 */       this.bipedLeftArm.rotateAngleZ = 0.0F;
/* 232 */       this.bipedRightArm.rotateAngleY = -(0.1F - f3 * 0.6F) + this.bipedHead.rotateAngleY;
/* 233 */       this.bipedLeftArm.rotateAngleY = 0.1F - f3 * 0.6F + this.bipedHead.rotateAngleY + 0.4F;
/* 234 */       this.bipedRightArm.rotateAngleX = -1.5707964F + this.bipedHead.rotateAngleX;
/* 235 */       this.bipedLeftArm.rotateAngleX = -1.5707964F + this.bipedHead.rotateAngleX;
/* 236 */       this.bipedRightArm.rotateAngleX -= f3 * 1.2F - f4 * 0.4F;
/* 237 */       this.bipedLeftArm.rotateAngleX -= f3 * 1.2F - f4 * 0.4F;
/* 238 */       this.bipedRightArm.rotateAngleZ += MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
/* 239 */       this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
/* 240 */       this.bipedRightArm.rotateAngleX += MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
/* 241 */       this.bipedLeftArm.rotateAngleX -= MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
/*     */     } 
/*     */     
/* 244 */     copyModelAngles(this.bipedHead, this.bipedHeadwear);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setModelAttributes(ModelBase model) {
/* 249 */     super.setModelAttributes(model);
/*     */     
/* 251 */     if (model instanceof ModelBiped) {
/*     */       
/* 253 */       ModelBiped modelbiped = (ModelBiped)model;
/* 254 */       this.heldItemLeft = modelbiped.heldItemLeft;
/* 255 */       this.heldItemRight = modelbiped.heldItemRight;
/* 256 */       this.isSneak = modelbiped.isSneak;
/* 257 */       this.aimedBow = modelbiped.aimedBow;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setInvisible(boolean invisible) {
/* 263 */     this.bipedHead.showModel = invisible;
/* 264 */     this.bipedHeadwear.showModel = invisible;
/* 265 */     this.bipedBody.showModel = invisible;
/* 266 */     this.bipedRightArm.showModel = invisible;
/* 267 */     this.bipedLeftArm.showModel = invisible;
/* 268 */     this.bipedRightLeg.showModel = invisible;
/* 269 */     this.bipedLeftLeg.showModel = invisible;
/*     */   }
/*     */ 
/*     */   
/*     */   public void postRenderArm(float scale) {
/* 274 */     this.bipedRightArm.postRender(scale);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\model\ModelBiped.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */