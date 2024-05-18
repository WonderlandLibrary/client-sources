/*    */ package net.minecraft.client.resources;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.client.renderer.texture.TextureUtil;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.ColorizerGrass;
/*    */ 
/*    */ public class GrassColorReloadListener
/*    */   implements IResourceManagerReloadListener {
/* 10 */   private static final ResourceLocation LOC_GRASS_PNG = new ResourceLocation("textures/colormap/grass.png");
/*    */ 
/*    */ 
/*    */   
/*    */   public void onResourceManagerReload(IResourceManager resourceManager) {
/*    */     try {
/* 16 */       ColorizerGrass.setGrassBiomeColorizer(TextureUtil.readImageData(resourceManager, LOC_GRASS_PNG));
/*    */     }
/* 18 */     catch (IOException iOException) {}
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\resources\GrassColorReloadListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */