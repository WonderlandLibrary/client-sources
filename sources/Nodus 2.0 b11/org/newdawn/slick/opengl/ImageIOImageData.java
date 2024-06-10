/*   1:    */ package org.newdawn.slick.opengl;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Graphics2D;
/*   5:    */ import java.awt.color.ColorSpace;
/*   6:    */ import java.awt.image.BufferedImage;
/*   7:    */ import java.awt.image.ColorModel;
/*   8:    */ import java.awt.image.ComponentColorModel;
/*   9:    */ import java.awt.image.DataBufferByte;
/*  10:    */ import java.awt.image.Raster;
/*  11:    */ import java.awt.image.WritableRaster;
/*  12:    */ import java.io.IOException;
/*  13:    */ import java.io.InputStream;
/*  14:    */ import java.nio.ByteBuffer;
/*  15:    */ import java.nio.ByteOrder;
/*  16:    */ import java.util.Hashtable;
/*  17:    */ import javax.imageio.ImageIO;
/*  18:    */ 
/*  19:    */ public class ImageIOImageData
/*  20:    */   implements LoadableImageData
/*  21:    */ {
/*  22: 31 */   private static final ColorModel glAlphaColorModel = new ComponentColorModel(ColorSpace.getInstance(1000), 
/*  23: 32 */     new int[] { 8, 8, 8, 8 }, 
/*  24: 33 */     true, 
/*  25: 34 */     false, 
/*  26: 35 */     3, 
/*  27: 36 */     0);
/*  28: 40 */   private static final ColorModel glColorModel = new ComponentColorModel(ColorSpace.getInstance(1000), 
/*  29: 41 */     new int[] { 8, 8, 8 }, 
/*  30: 42 */     false, 
/*  31: 43 */     false, 
/*  32: 44 */     1, 
/*  33: 45 */     0);
/*  34:    */   private int depth;
/*  35:    */   private int height;
/*  36:    */   private int width;
/*  37:    */   private int texWidth;
/*  38:    */   private int texHeight;
/*  39: 58 */   private boolean edging = true;
/*  40:    */   
/*  41:    */   public int getDepth()
/*  42:    */   {
/*  43: 64 */     return this.depth;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int getHeight()
/*  47:    */   {
/*  48: 71 */     return this.height;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public int getTexHeight()
/*  52:    */   {
/*  53: 78 */     return this.texHeight;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int getTexWidth()
/*  57:    */   {
/*  58: 85 */     return this.texWidth;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public int getWidth()
/*  62:    */   {
/*  63: 92 */     return this.width;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public ByteBuffer loadImage(InputStream fis)
/*  67:    */     throws IOException
/*  68:    */   {
/*  69: 99 */     return loadImage(fis, true, null);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public ByteBuffer loadImage(InputStream fis, boolean flipped, int[] transparent)
/*  73:    */     throws IOException
/*  74:    */   {
/*  75:106 */     return loadImage(fis, flipped, false, transparent);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public ByteBuffer loadImage(InputStream fis, boolean flipped, boolean forceAlpha, int[] transparent)
/*  79:    */     throws IOException
/*  80:    */   {
/*  81:113 */     if (transparent != null) {
/*  82:114 */       forceAlpha = true;
/*  83:    */     }
/*  84:117 */     BufferedImage bufferedImage = ImageIO.read(fis);
/*  85:118 */     return imageToByteBuffer(bufferedImage, flipped, forceAlpha, transparent);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public ByteBuffer imageToByteBuffer(BufferedImage image, boolean flipped, boolean forceAlpha, int[] transparent)
/*  89:    */   {
/*  90:122 */     ByteBuffer imageBuffer = null;
/*  91:    */     
/*  92:    */ 
/*  93:    */ 
/*  94:126 */     int texWidth = 2;
/*  95:127 */     int texHeight = 2;
/*  96:132 */     while (texWidth < image.getWidth()) {
/*  97:133 */       texWidth *= 2;
/*  98:    */     }
/*  99:135 */     while (texHeight < image.getHeight()) {
/* 100:136 */       texHeight *= 2;
/* 101:    */     }
/* 102:139 */     this.width = image.getWidth();
/* 103:140 */     this.height = image.getHeight();
/* 104:141 */     this.texHeight = texHeight;
/* 105:142 */     this.texWidth = texWidth;
/* 106:    */     
/* 107:    */ 
/* 108:    */ 
/* 109:146 */     boolean useAlpha = (image.getColorModel().hasAlpha()) || (forceAlpha);
/* 110:    */     BufferedImage texImage;
/* 111:    */     BufferedImage texImage;
/* 112:148 */     if (useAlpha)
/* 113:    */     {
/* 114:149 */       this.depth = 32;
/* 115:150 */       WritableRaster raster = Raster.createInterleavedRaster(0, texWidth, texHeight, 4, null);
/* 116:151 */       texImage = new BufferedImage(glAlphaColorModel, raster, false, new Hashtable());
/* 117:    */     }
/* 118:    */     else
/* 119:    */     {
/* 120:153 */       this.depth = 24;
/* 121:154 */       WritableRaster raster = Raster.createInterleavedRaster(0, texWidth, texHeight, 3, null);
/* 122:155 */       texImage = new BufferedImage(glColorModel, raster, false, new Hashtable());
/* 123:    */     }
/* 124:159 */     Graphics2D g = (Graphics2D)texImage.getGraphics();
/* 125:162 */     if (useAlpha)
/* 126:    */     {
/* 127:163 */       g.setColor(new Color(0.0F, 0.0F, 0.0F, 0.0F));
/* 128:164 */       g.fillRect(0, 0, texWidth, texHeight);
/* 129:    */     }
/* 130:167 */     if (flipped)
/* 131:    */     {
/* 132:168 */       g.scale(1.0D, -1.0D);
/* 133:169 */       g.drawImage(image, 0, -this.height, null);
/* 134:    */     }
/* 135:    */     else
/* 136:    */     {
/* 137:171 */       g.drawImage(image, 0, 0, null);
/* 138:    */     }
/* 139:174 */     if (this.edging)
/* 140:    */     {
/* 141:175 */       if (this.height < texHeight - 1)
/* 142:    */       {
/* 143:176 */         copyArea(texImage, 0, 0, this.width, 1, 0, texHeight - 1);
/* 144:177 */         copyArea(texImage, 0, this.height - 1, this.width, 1, 0, 1);
/* 145:    */       }
/* 146:179 */       if (this.width < texWidth - 1)
/* 147:    */       {
/* 148:180 */         copyArea(texImage, 0, 0, 1, this.height, texWidth - 1, 0);
/* 149:181 */         copyArea(texImage, this.width - 1, 0, 1, this.height, 1, 0);
/* 150:    */       }
/* 151:    */     }
/* 152:187 */     byte[] data = ((DataBufferByte)texImage.getRaster().getDataBuffer()).getData();
/* 153:189 */     if (transparent != null) {
/* 154:190 */       for (int i = 0; i < data.length; i += 4)
/* 155:    */       {
/* 156:191 */         boolean match = true;
/* 157:192 */         for (int c = 0; c < 3; c++)
/* 158:    */         {
/* 159:193 */           int value = data[(i + c)] < 0 ? 256 + data[(i + c)] : data[(i + c)];
/* 160:194 */           if (value != transparent[c]) {
/* 161:195 */             match = false;
/* 162:    */           }
/* 163:    */         }
/* 164:199 */         if (match) {
/* 165:200 */           data[(i + 3)] = 0;
/* 166:    */         }
/* 167:    */       }
/* 168:    */     }
/* 169:205 */     imageBuffer = ByteBuffer.allocateDirect(data.length);
/* 170:206 */     imageBuffer.order(ByteOrder.nativeOrder());
/* 171:207 */     imageBuffer.put(data, 0, data.length);
/* 172:208 */     imageBuffer.flip();
/* 173:209 */     g.dispose();
/* 174:    */     
/* 175:211 */     return imageBuffer;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public ByteBuffer getImageBufferData()
/* 179:    */   {
/* 180:218 */     throw new RuntimeException("ImageIOImageData doesn't store it's image.");
/* 181:    */   }
/* 182:    */   
/* 183:    */   private void copyArea(BufferedImage image, int x, int y, int width, int height, int dx, int dy)
/* 184:    */   {
/* 185:233 */     Graphics2D g = (Graphics2D)image.getGraphics();
/* 186:    */     
/* 187:235 */     g.drawImage(image.getSubimage(x, y, width, height), x + dx, y + dy, null);
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void configureEdging(boolean edging)
/* 191:    */   {
/* 192:242 */     this.edging = edging;
/* 193:    */   }
/* 194:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.opengl.ImageIOImageData
 * JD-Core Version:    0.7.0.1
 */