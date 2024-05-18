/*    */ package net.minecraft.client.renderer.texture;
/*    */ 
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import net.minecraft.client.resources.IResource;
/*    */ import net.minecraft.client.resources.IResourceManager;
/*    */ import net.minecraft.client.resources.data.TextureMetadataSection;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class SimpleTexture
/*    */   extends AbstractTexture {
/* 15 */   private static final Logger logger = LogManager.getLogger();
/*    */   
/*    */   protected final ResourceLocation textureLocation;
/*    */   
/*    */   public SimpleTexture(ResourceLocation textureResourceLocation) {
/* 20 */     this.textureLocation = textureResourceLocation;
/*    */   }
/*    */ 
/*    */   
/*    */   public void loadTexture(IResourceManager resourceManager) throws IOException {
/* 25 */     deleteGlTexture();
/* 26 */     InputStream inputstream = null;
/*    */ 
/*    */     
/*    */     try {
/* 30 */       IResource iresource = resourceManager.getResource(this.textureLocation);
/* 31 */       inputstream = iresource.getInputStream();
/* 32 */       BufferedImage bufferedimage = TextureUtil.readBufferedImage(inputstream);
/* 33 */       boolean flag = false;
/* 34 */       boolean flag1 = false;
/*    */       
/* 36 */       if (iresource.hasMetadata()) {
/*    */         
/*    */         try {
/*    */           
/* 40 */           TextureMetadataSection texturemetadatasection = (TextureMetadataSection)iresource.getMetadata("texture");
/*    */           
/* 42 */           if (texturemetadatasection != null)
/*    */           {
/* 44 */             flag = texturemetadatasection.getTextureBlur();
/* 45 */             flag1 = texturemetadatasection.getTextureClamp();
/*    */           }
/*    */         
/* 48 */         } catch (RuntimeException runtimeexception) {
/*    */           
/* 50 */           logger.warn("Failed reading metadata of: " + this.textureLocation, runtimeexception);
/*    */         } 
/*    */       }
/*    */       
/* 54 */       TextureUtil.uploadTextureImageAllocate(getGlTextureId(), bufferedimage, flag, flag1);
/*    */     }
/*    */     finally {
/*    */       
/* 58 */       if (inputstream != null)
/*    */       {
/* 60 */         inputstream.close();
/*    */       }
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\renderer\texture\SimpleTexture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */