/*  1:   */ package net.minecraft.client.renderer.texture;
/*  2:   */ 
/*  3:   */ import java.awt.image.BufferedImage;
/*  4:   */ import java.io.IOException;
/*  5:   */ import java.io.InputStream;
/*  6:   */ import javax.imageio.ImageIO;
/*  7:   */ import net.minecraft.client.resources.IResource;
/*  8:   */ import net.minecraft.client.resources.IResourceManager;
/*  9:   */ import net.minecraft.client.resources.data.TextureMetadataSection;
/* 10:   */ import net.minecraft.util.ResourceLocation;
/* 11:   */ import org.apache.logging.log4j.LogManager;
/* 12:   */ import org.apache.logging.log4j.Logger;
/* 13:   */ 
/* 14:   */ public class SimpleTexture
/* 15:   */   extends AbstractTexture
/* 16:   */ {
/* 17:16 */   private static final Logger logger = ;
/* 18:   */   protected final ResourceLocation textureLocation;
/* 19:   */   private static final String __OBFID = "CL_00001052";
/* 20:   */   
/* 21:   */   public SimpleTexture(ResourceLocation par1ResourceLocation)
/* 22:   */   {
/* 23:22 */     this.textureLocation = par1ResourceLocation;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void loadTexture(IResourceManager par1ResourceManager)
/* 27:   */     throws IOException
/* 28:   */   {
/* 29:27 */     func_147631_c();
/* 30:28 */     InputStream var2 = null;
/* 31:   */     try
/* 32:   */     {
/* 33:32 */       IResource var3 = par1ResourceManager.getResource(this.textureLocation);
/* 34:33 */       var2 = var3.getInputStream();
/* 35:34 */       BufferedImage var4 = ImageIO.read(var2);
/* 36:35 */       boolean var5 = false;
/* 37:36 */       boolean var6 = false;
/* 38:38 */       if (var3.hasMetadata()) {
/* 39:   */         try
/* 40:   */         {
/* 41:42 */           TextureMetadataSection var7 = (TextureMetadataSection)var3.getMetadata("texture");
/* 42:44 */           if (var7 != null)
/* 43:   */           {
/* 44:46 */             var5 = var7.getTextureBlur();
/* 45:47 */             var6 = var7.getTextureClamp();
/* 46:   */           }
/* 47:   */         }
/* 48:   */         catch (RuntimeException var11)
/* 49:   */         {
/* 50:52 */           logger.warn("Failed reading metadata of: " + this.textureLocation, var11);
/* 51:   */         }
/* 52:   */       }
/* 53:56 */       TextureUtil.uploadTextureImageAllocate(getGlTextureId(), var4, var5, var6);
/* 54:   */     }
/* 55:   */     finally
/* 56:   */     {
/* 57:60 */       if (var2 != null) {
/* 58:62 */         var2.close();
/* 59:   */       }
/* 60:   */     }
/* 61:   */   }
/* 62:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.texture.SimpleTexture
 * JD-Core Version:    0.7.0.1
 */