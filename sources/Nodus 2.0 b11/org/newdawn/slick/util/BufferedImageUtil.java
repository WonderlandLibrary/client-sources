/*   1:    */ package org.newdawn.slick.util;
/*   2:    */ 
/*   3:    */ import java.awt.Graphics2D;
/*   4:    */ import java.awt.image.BufferedImage;
/*   5:    */ import java.awt.image.ColorModel;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.nio.ByteBuffer;
/*   8:    */ import org.newdawn.slick.opengl.ImageIOImageData;
/*   9:    */ import org.newdawn.slick.opengl.InternalTextureLoader;
/*  10:    */ import org.newdawn.slick.opengl.Texture;
/*  11:    */ import org.newdawn.slick.opengl.TextureImpl;
/*  12:    */ import org.newdawn.slick.opengl.renderer.Renderer;
/*  13:    */ import org.newdawn.slick.opengl.renderer.SGL;
/*  14:    */ 
/*  15:    */ public class BufferedImageUtil
/*  16:    */ {
/*  17:    */   public static Texture getTexture(String resourceName, BufferedImage resourceImage)
/*  18:    */     throws IOException
/*  19:    */   {
/*  20: 39 */     Texture tex = getTexture(resourceName, resourceImage, 
/*  21: 40 */       3553, 
/*  22: 41 */       6408, 
/*  23: 42 */       9729, 
/*  24: 43 */       9729);
/*  25:    */     
/*  26: 45 */     return tex;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static Texture getTexture(String resourceName, BufferedImage resourceImage, int filter)
/*  30:    */     throws IOException
/*  31:    */   {
/*  32: 61 */     Texture tex = getTexture(resourceName, resourceImage, 
/*  33: 62 */       3553, 
/*  34: 63 */       6408, 
/*  35: 64 */       filter, 
/*  36: 65 */       filter);
/*  37:    */     
/*  38: 67 */     return tex;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static Texture getTexture(String resourceName, BufferedImage resourceimage, int target, int dstPixelFormat, int minFilter, int magFilter)
/*  42:    */     throws IOException
/*  43:    */   {
/*  44: 92 */     ImageIOImageData data = new ImageIOImageData();int srcPixelFormat = 0;
/*  45:    */     
/*  46:    */ 
/*  47: 95 */     int textureID = InternalTextureLoader.createTextureID();
/*  48: 96 */     TextureImpl texture = new TextureImpl(resourceName, target, textureID);
/*  49:    */     
/*  50:    */ 
/*  51: 99 */     Renderer.get().glEnable(3553);
/*  52:    */     
/*  53:    */ 
/*  54:102 */     Renderer.get().glBindTexture(target, textureID);
/*  55:    */     
/*  56:104 */     BufferedImage bufferedImage = resourceimage;
/*  57:105 */     texture.setWidth(bufferedImage.getWidth());
/*  58:106 */     texture.setHeight(bufferedImage.getHeight());
/*  59:108 */     if (bufferedImage.getColorModel().hasAlpha()) {
/*  60:109 */       srcPixelFormat = 6408;
/*  61:    */     } else {
/*  62:111 */       srcPixelFormat = 6407;
/*  63:    */     }
/*  64:115 */     ByteBuffer textureBuffer = data.imageToByteBuffer(bufferedImage, false, false, null);
/*  65:116 */     texture.setTextureHeight(data.getTexHeight());
/*  66:117 */     texture.setTextureWidth(data.getTexWidth());
/*  67:118 */     texture.setAlpha(data.getDepth() == 32);
/*  68:120 */     if (target == 3553)
/*  69:    */     {
/*  70:121 */       Renderer.get().glTexParameteri(target, 10241, minFilter);
/*  71:122 */       Renderer.get().glTexParameteri(target, 10240, magFilter);
/*  72:124 */       if (Renderer.get().canTextureMirrorClamp())
/*  73:    */       {
/*  74:125 */         Renderer.get().glTexParameteri(3553, 10242, 34627);
/*  75:126 */         Renderer.get().glTexParameteri(3553, 10243, 34627);
/*  76:    */       }
/*  77:    */       else
/*  78:    */       {
/*  79:128 */         Renderer.get().glTexParameteri(3553, 10242, 10496);
/*  80:129 */         Renderer.get().glTexParameteri(3553, 10243, 10496);
/*  81:    */       }
/*  82:    */     }
/*  83:133 */     Renderer.get().glTexImage2D(target, 
/*  84:134 */       0, 
/*  85:135 */       dstPixelFormat, 
/*  86:136 */       texture.getTextureWidth(), 
/*  87:137 */       texture.getTextureHeight(), 
/*  88:138 */       0, 
/*  89:139 */       srcPixelFormat, 
/*  90:140 */       5121, 
/*  91:141 */       textureBuffer);
/*  92:    */     
/*  93:143 */     return texture;
/*  94:    */   }
/*  95:    */   
/*  96:    */   private static void copyArea(BufferedImage image, int x, int y, int width, int height, int dx, int dy)
/*  97:    */   {
/*  98:158 */     Graphics2D g = (Graphics2D)image.getGraphics();
/*  99:    */     
/* 100:160 */     g.drawImage(image.getSubimage(x, y, width, height), x + dx, y + dy, null);
/* 101:    */   }
/* 102:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.util.BufferedImageUtil
 * JD-Core Version:    0.7.0.1
 */