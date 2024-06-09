/*     */ package net.minecraft.client.resources.model;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.block.model.BreakingFour;
/*     */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*     */ import net.minecraft.client.renderer.block.model.ModelBlock;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ 
/*     */ public class SimpleBakedModel
/*     */   implements IBakedModel
/*     */ {
/*     */   protected final List<BakedQuad> generalQuads;
/*     */   protected final List<List<BakedQuad>> faceQuads;
/*     */   protected final boolean ambientOcclusion;
/*     */   protected final boolean gui3d;
/*     */   protected final TextureAtlasSprite texture;
/*     */   protected final ItemCameraTransforms cameraTransforms;
/*     */   
/*     */   public SimpleBakedModel(List<BakedQuad> p_i46077_1_, List<List<BakedQuad>> p_i46077_2_, boolean p_i46077_3_, boolean p_i46077_4_, TextureAtlasSprite p_i46077_5_, ItemCameraTransforms p_i46077_6_) {
/*  23 */     this.generalQuads = p_i46077_1_;
/*  24 */     this.faceQuads = p_i46077_2_;
/*  25 */     this.ambientOcclusion = p_i46077_3_;
/*  26 */     this.gui3d = p_i46077_4_;
/*  27 */     this.texture = p_i46077_5_;
/*  28 */     this.cameraTransforms = p_i46077_6_;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BakedQuad> getFaceQuads(EnumFacing p_177551_1_) {
/*  33 */     return this.faceQuads.get(p_177551_1_.ordinal());
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BakedQuad> getGeneralQuads() {
/*  38 */     return this.generalQuads;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAmbientOcclusion() {
/*  43 */     return this.ambientOcclusion;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isGui3d() {
/*  48 */     return this.gui3d;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBuiltInRenderer() {
/*  53 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAtlasSprite getParticleTexture() {
/*  58 */     return this.texture;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemCameraTransforms getItemCameraTransforms() {
/*  63 */     return this.cameraTransforms;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Builder
/*     */   {
/*     */     private final List<BakedQuad> builderGeneralQuads;
/*     */     private final List<List<BakedQuad>> builderFaceQuads;
/*     */     private final boolean builderAmbientOcclusion;
/*     */     private TextureAtlasSprite builderTexture;
/*     */     private boolean builderGui3d;
/*     */     private ItemCameraTransforms builderCameraTransforms;
/*     */     
/*     */     public Builder(ModelBlock p_i46074_1_) {
/*  77 */       this(p_i46074_1_.isAmbientOcclusion(), p_i46074_1_.isGui3d(), p_i46074_1_.func_181682_g());
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder(IBakedModel p_i46075_1_, TextureAtlasSprite p_i46075_2_) {
/*  82 */       this(p_i46075_1_.isAmbientOcclusion(), p_i46075_1_.isGui3d(), p_i46075_1_.getItemCameraTransforms());
/*  83 */       this.builderTexture = p_i46075_1_.getParticleTexture(); byte b; int i;
/*     */       EnumFacing[] arrayOfEnumFacing;
/*  85 */       for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*     */         
/*  87 */         addFaceBreakingFours(p_i46075_1_, p_i46075_2_, enumfacing);
/*     */         b++; }
/*     */       
/*  90 */       addGeneralBreakingFours(p_i46075_1_, p_i46075_2_);
/*     */     }
/*     */ 
/*     */     
/*     */     private void addFaceBreakingFours(IBakedModel p_177649_1_, TextureAtlasSprite p_177649_2_, EnumFacing p_177649_3_) {
/*  95 */       for (BakedQuad bakedquad : p_177649_1_.getFaceQuads(p_177649_3_))
/*     */       {
/*  97 */         addFaceQuad(p_177649_3_, (BakedQuad)new BreakingFour(bakedquad, p_177649_2_));
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     private void addGeneralBreakingFours(IBakedModel p_177647_1_, TextureAtlasSprite p_177647_2_) {
/* 103 */       for (BakedQuad bakedquad : p_177647_1_.getGeneralQuads())
/*     */       {
/* 105 */         addGeneralQuad((BakedQuad)new BreakingFour(bakedquad, p_177647_2_));
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     private Builder(boolean p_i46076_1_, boolean p_i46076_2_, ItemCameraTransforms p_i46076_3_) {
/* 111 */       this.builderGeneralQuads = Lists.newArrayList();
/* 112 */       this.builderFaceQuads = Lists.newArrayListWithCapacity(6); byte b; int i;
/*     */       EnumFacing[] arrayOfEnumFacing;
/* 114 */       for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*     */         
/* 116 */         this.builderFaceQuads.add(Lists.newArrayList());
/*     */         b++; }
/*     */       
/* 119 */       this.builderAmbientOcclusion = p_i46076_1_;
/* 120 */       this.builderGui3d = p_i46076_2_;
/* 121 */       this.builderCameraTransforms = p_i46076_3_;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder addFaceQuad(EnumFacing p_177650_1_, BakedQuad p_177650_2_) {
/* 126 */       ((List<BakedQuad>)this.builderFaceQuads.get(p_177650_1_.ordinal())).add(p_177650_2_);
/* 127 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder addGeneralQuad(BakedQuad p_177648_1_) {
/* 132 */       this.builderGeneralQuads.add(p_177648_1_);
/* 133 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder setTexture(TextureAtlasSprite p_177646_1_) {
/* 138 */       this.builderTexture = p_177646_1_;
/* 139 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public IBakedModel makeBakedModel() {
/* 144 */       if (this.builderTexture == null)
/*     */       {
/* 146 */         throw new RuntimeException("Missing particle!");
/*     */       }
/*     */ 
/*     */       
/* 150 */       return new SimpleBakedModel(this.builderGeneralQuads, this.builderFaceQuads, this.builderAmbientOcclusion, this.builderGui3d, this.builderTexture, this.builderCameraTransforms);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\resources\model\SimpleBakedModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */