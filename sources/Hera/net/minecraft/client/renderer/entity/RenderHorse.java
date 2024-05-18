/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.google.common.collect.Maps;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelHorse;
/*    */ import net.minecraft.client.renderer.texture.ITextureObject;
/*    */ import net.minecraft.client.renderer.texture.LayeredTexture;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.EntityHorse;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderHorse extends RenderLiving<EntityHorse> {
/* 14 */   private static final Map<String, ResourceLocation> field_110852_a = Maps.newHashMap();
/* 15 */   private static final ResourceLocation whiteHorseTextures = new ResourceLocation("textures/entity/horse/horse_white.png");
/* 16 */   private static final ResourceLocation muleTextures = new ResourceLocation("textures/entity/horse/mule.png");
/* 17 */   private static final ResourceLocation donkeyTextures = new ResourceLocation("textures/entity/horse/donkey.png");
/* 18 */   private static final ResourceLocation zombieHorseTextures = new ResourceLocation("textures/entity/horse/horse_zombie.png");
/* 19 */   private static final ResourceLocation skeletonHorseTextures = new ResourceLocation("textures/entity/horse/horse_skeleton.png");
/*    */ 
/*    */   
/*    */   public RenderHorse(RenderManager rendermanagerIn, ModelHorse model, float shadowSizeIn) {
/* 23 */     super(rendermanagerIn, (ModelBase)model, shadowSizeIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void preRenderCallback(EntityHorse entitylivingbaseIn, float partialTickTime) {
/* 32 */     float f = 1.0F;
/* 33 */     int i = entitylivingbaseIn.getHorseType();
/*    */     
/* 35 */     if (i == 1) {
/*    */       
/* 37 */       f *= 0.87F;
/*    */     }
/* 39 */     else if (i == 2) {
/*    */       
/* 41 */       f *= 0.92F;
/*    */     } 
/*    */     
/* 44 */     GlStateManager.scale(f, f, f);
/* 45 */     super.preRenderCallback(entitylivingbaseIn, partialTickTime);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityHorse entity) {
/* 53 */     if (!entity.func_110239_cn()) {
/*    */       
/* 55 */       switch (entity.getHorseType()) {
/*    */ 
/*    */         
/*    */         default:
/* 59 */           return whiteHorseTextures;
/*    */         
/*    */         case 1:
/* 62 */           return donkeyTextures;
/*    */         
/*    */         case 2:
/* 65 */           return muleTextures;
/*    */         
/*    */         case 3:
/* 68 */           return zombieHorseTextures;
/*    */         case 4:
/*    */           break;
/* 71 */       }  return skeletonHorseTextures;
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 76 */     return func_110848_b(entity);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private ResourceLocation func_110848_b(EntityHorse horse) {
/* 82 */     String s = horse.getHorseTexture();
/*    */     
/* 84 */     if (!horse.func_175507_cI())
/*    */     {
/* 86 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 90 */     ResourceLocation resourcelocation = field_110852_a.get(s);
/*    */     
/* 92 */     if (resourcelocation == null) {
/*    */       
/* 94 */       resourcelocation = new ResourceLocation(s);
/* 95 */       Minecraft.getMinecraft().getTextureManager().loadTexture(resourcelocation, (ITextureObject)new LayeredTexture(horse.getVariantTexturePaths()));
/* 96 */       field_110852_a.put(s, resourcelocation);
/*    */     } 
/*    */     
/* 99 */     return resourcelocation;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\renderer\entity\RenderHorse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */