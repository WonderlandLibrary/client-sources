/*     */ package org.newdawn.slick.util;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import org.newdawn.slick.opengl.ImageIOImageData;
/*     */ import org.newdawn.slick.opengl.InternalTextureLoader;
/*     */ import org.newdawn.slick.opengl.Texture;
/*     */ import org.newdawn.slick.opengl.TextureImpl;
/*     */ import org.newdawn.slick.opengl.renderer.Renderer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BufferedImageUtil
/*     */ {
/*     */   public static Texture getTexture(String resourceName, BufferedImage resourceImage) throws IOException {
/*  39 */     Texture tex = getTexture(resourceName, resourceImage, 3553, 6408, 9729, 9729);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  45 */     return tex;
/*     */   }
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
/*     */   public static Texture getTexture(String resourceName, BufferedImage resourceImage, int filter) throws IOException {
/*  61 */     Texture tex = getTexture(resourceName, resourceImage, 3553, 6408, filter, filter);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  67 */     return tex;
/*     */   }
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
/*     */   
/*     */   public static Texture getTexture(String resourceName, BufferedImage resourceimage, int target, int dstPixelFormat, int minFilter, int magFilter) throws IOException {
/*  92 */     ImageIOImageData data = new ImageIOImageData(); int srcPixelFormat = 0;
/*     */ 
/*     */     
/*  95 */     int textureID = InternalTextureLoader.createTextureID();
/*  96 */     TextureImpl texture = new TextureImpl(resourceName, target, textureID);
/*     */ 
/*     */     
/*  99 */     Renderer.get().glEnable(3553);
/*     */ 
/*     */     
/* 102 */     Renderer.get().glBindTexture(target, textureID);
/*     */     
/* 104 */     BufferedImage bufferedImage = resourceimage;
/* 105 */     texture.setWidth(bufferedImage.getWidth());
/* 106 */     texture.setHeight(bufferedImage.getHeight());
/*     */     
/* 108 */     if (bufferedImage.getColorModel().hasAlpha()) {
/* 109 */       srcPixelFormat = 6408;
/*     */     } else {
/* 111 */       srcPixelFormat = 6407;
/*     */     } 
/*     */ 
/*     */     
/* 115 */     ByteBuffer textureBuffer = data.imageToByteBuffer(bufferedImage, false, false, null);
/* 116 */     texture.setTextureHeight(data.getTexHeight());
/* 117 */     texture.setTextureWidth(data.getTexWidth());
/* 118 */     texture.setAlpha((data.getDepth() == 32));
/*     */     
/* 120 */     if (target == 3553) {
/* 121 */       Renderer.get().glTexParameteri(target, 10241, minFilter);
/* 122 */       Renderer.get().glTexParameteri(target, 10240, magFilter);
/*     */       
/* 124 */       if (Renderer.get().canTextureMirrorClamp()) {
/* 125 */         Renderer.get().glTexParameteri(3553, 10242, 34627);
/* 126 */         Renderer.get().glTexParameteri(3553, 10243, 34627);
/*     */       } else {
/* 128 */         Renderer.get().glTexParameteri(3553, 10242, 10496);
/* 129 */         Renderer.get().glTexParameteri(3553, 10243, 10496);
/*     */       } 
/*     */     } 
/*     */     
/* 133 */     Renderer.get().glTexImage2D(target, 0, dstPixelFormat, texture.getTextureWidth(), texture.getTextureHeight(), 0, srcPixelFormat, 5121, textureBuffer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 143 */     return (Texture)texture;
/*     */   }
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
/*     */   private static void copyArea(BufferedImage image, int x, int y, int width, int height, int dx, int dy) {
/* 158 */     Graphics2D g = (Graphics2D)image.getGraphics();
/*     */     
/* 160 */     g.drawImage(image.getSubimage(x, y, width, height), x + dx, y + dy, null);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slic\\util\BufferedImageUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */