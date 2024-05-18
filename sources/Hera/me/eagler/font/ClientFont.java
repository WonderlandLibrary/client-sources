/*     */ package me.eagler.font;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ImageObserver;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class ClientFont {
/*  14 */   public int IMAGE_WIDTH = 1024;
/*     */   
/*  16 */   public int IMAGE_HEIGHT = 1024;
/*     */   
/*     */   private int texID;
/*     */   
/*  20 */   private final IntObject[] chars = new IntObject[2048];
/*     */   
/*     */   private final Font font;
/*     */   
/*     */   private boolean antiAlias;
/*     */   
/*  26 */   private int fontHeight = -1;
/*     */   
/*  28 */   private int charOffset = 8;
/*     */   
/*     */   public ClientFont(Font font, boolean antiAlias, int charOffset) {
/*  31 */     this.font = font;
/*  32 */     this.antiAlias = antiAlias;
/*  33 */     this.charOffset = charOffset;
/*  34 */     setupTexture(antiAlias);
/*     */   }
/*     */   
/*     */   public ClientFont(Font font, boolean antiAlias) {
/*  38 */     this.font = font;
/*  39 */     this.antiAlias = antiAlias;
/*  40 */     this.charOffset = 8;
/*  41 */     setupTexture(antiAlias);
/*     */   }
/*     */   
/*     */   private void setupTexture(boolean antiAlias) {
/*  45 */     if (this.font.getSize() <= 15) {
/*  46 */       this.IMAGE_WIDTH = 256;
/*  47 */       this.IMAGE_HEIGHT = 256;
/*     */     } 
/*  49 */     if (this.font.getSize() <= 43) {
/*  50 */       this.IMAGE_WIDTH = 512;
/*  51 */       this.IMAGE_HEIGHT = 512;
/*  52 */     } else if (this.font.getSize() <= 91) {
/*  53 */       this.IMAGE_WIDTH = 1024;
/*  54 */       this.IMAGE_HEIGHT = 1024;
/*     */     } else {
/*  56 */       this.IMAGE_WIDTH = 2048;
/*  57 */       this.IMAGE_HEIGHT = 2048;
/*     */     } 
/*  59 */     BufferedImage img = new BufferedImage(this.IMAGE_WIDTH, this.IMAGE_HEIGHT, 2);
/*  60 */     Graphics2D g = (Graphics2D)img.getGraphics();
/*  61 */     g.setFont(this.font);
/*  62 */     g.setColor(new Color(255, 255, 255, 0));
/*  63 */     g.fillRect(0, 0, this.IMAGE_WIDTH, this.IMAGE_HEIGHT);
/*  64 */     g.setColor(Color.white);
/*  65 */     int rowHeight = 0;
/*  66 */     int positionX = 0;
/*  67 */     int positionY = 0;
/*  68 */     for (int i = 0; i < 2048; i++) {
/*  69 */       char ch = (char)i;
/*  70 */       BufferedImage fontImage = getFontImage(ch, antiAlias);
/*  71 */       IntObject newIntObject = new IntObject(null);
/*  72 */       newIntObject.width = fontImage.getWidth();
/*  73 */       newIntObject.height = fontImage.getHeight();
/*  74 */       if (positionX + newIntObject.width >= this.IMAGE_WIDTH) {
/*  75 */         positionX = 0;
/*  76 */         positionY += rowHeight;
/*  77 */         rowHeight = 0;
/*     */       } 
/*  79 */       newIntObject.storedX = positionX;
/*  80 */       newIntObject.storedY = positionY;
/*  81 */       if (newIntObject.height > this.fontHeight)
/*  82 */         this.fontHeight = newIntObject.height; 
/*  83 */       if (newIntObject.height > rowHeight)
/*  84 */         rowHeight = newIntObject.height; 
/*  85 */       this.chars[i] = newIntObject;
/*  86 */       g.drawImage(fontImage, positionX, positionY, (ImageObserver)null);
/*  87 */       positionX += newIntObject.width;
/*     */     } 
/*     */     try {
/*  90 */       this.texID = TextureUtil.uploadTextureImageAllocate(TextureUtil.glGenTextures(), img, true, true);
/*  91 */     } catch (NullPointerException e) {
/*  92 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private BufferedImage getFontImage(char ch, boolean antiAlias) {
/*  97 */     BufferedImage tempfontImage = new BufferedImage(1, 1, 2);
/*  98 */     Graphics2D g = (Graphics2D)tempfontImage.getGraphics();
/*  99 */     if (antiAlias) {
/* 100 */       g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/*     */     } else {
/* 102 */       g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
/*     */     } 
/* 104 */     g.setFont(this.font);
/* 105 */     FontMetrics fontMetrics = g.getFontMetrics();
/* 106 */     int charwidth = fontMetrics.charWidth(ch) + 8;
/* 107 */     if (charwidth <= 0)
/* 108 */       charwidth = 7; 
/* 109 */     int charheight = fontMetrics.getHeight() + 3;
/* 110 */     if (charheight <= 0)
/* 111 */       charheight = this.font.getSize(); 
/* 112 */     BufferedImage fontImage = new BufferedImage(charwidth, charheight, 2);
/* 113 */     Graphics2D gt = (Graphics2D)fontImage.getGraphics();
/* 114 */     if (antiAlias) {
/* 115 */       gt.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/*     */     } else {
/* 117 */       gt.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
/*     */     } 
/* 119 */     gt.setFont(this.font);
/* 120 */     gt.setColor(Color.WHITE);
/* 121 */     int charx = 3;
/* 122 */     int chary = 1;
/* 123 */     gt.drawString(String.valueOf(ch), 3, 1 + fontMetrics.getAscent());
/* 124 */     return fontImage;
/*     */   }
/*     */   
/*     */   public void drawChar(char c, float x, float y) throws ArrayIndexOutOfBoundsException {
/*     */     try {
/* 129 */       drawQuad(x, y, (this.chars[c]).width, (this.chars[c]).height, (this.chars[c]).storedX, 
/* 130 */           (this.chars[c]).storedY, (this.chars[c]).width, (this.chars[c]).height);
/* 131 */     } catch (Exception e) {
/* 132 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawQuad(float x, float y, float width, float height, float srcX, float srcY, float srcWidth, float srcHeight) {
/* 138 */     float renderSRCX = srcX / this.IMAGE_WIDTH, renderSRCY = srcY / this.IMAGE_HEIGHT;
/* 139 */     float renderSRCWidth = srcWidth / this.IMAGE_WIDTH, renderSRCHeight = srcHeight / this.IMAGE_HEIGHT;
/* 140 */     GL11.glBegin(4);
/* 141 */     GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
/* 142 */     GL11.glVertex2d((x + width), y);
/* 143 */     GL11.glTexCoord2f(renderSRCX, renderSRCY);
/* 144 */     GL11.glVertex2d(x, y);
/* 145 */     GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
/* 146 */     GL11.glVertex2d(x, (y + height));
/* 147 */     GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
/* 148 */     GL11.glVertex2d(x, (y + height));
/* 149 */     GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY + renderSRCHeight);
/* 150 */     GL11.glVertex2d((x + width), (y + height));
/* 151 */     GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
/* 152 */     GL11.glVertex2d((x + width), y);
/* 153 */     GL11.glEnd();
/*     */   }
/*     */   
/*     */   public void drawString(String text, double x, double y, Color color, boolean shadow) {
/* 157 */     x *= 2.0D;
/* 158 */     y = y * 2.0D - 2.0D;
/* 159 */     GL11.glPushMatrix();
/* 160 */     GL11.glScaled(0.25D, 0.25D, 0.25D);
/* 161 */     TextureUtil.bindTexture(this.texID);
/* 162 */     glColor(shadow ? new Color(0.05F, 0.05F, 0.05F, color.getAlpha() / 255.0F) : color);
/* 163 */     int size = text.length();
/* 164 */     for (int indexInString = 0; indexInString < size; indexInString++) {
/* 165 */       char character = text.charAt(indexInString);
/* 166 */       if (character < this.chars.length && character >= '\000') {
/* 167 */         drawChar(character, (float)x, (float)y);
/* 168 */         x += ((this.chars[character]).width - this.charOffset);
/*     */       } 
/*     */     } 
/* 171 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public void glColor(Color color) {
/* 175 */     float red = color.getRed() / 255.0F, green = color.getGreen() / 255.0F, blue = color.getBlue() / 255.0F;
/* 176 */     float alpha = color.getAlpha() / 255.0F;
/* 177 */     GL11.glColor4f(red, green, blue, alpha);
/*     */   }
/*     */   
/*     */   public int getStringHeight(String text) {
/* 181 */     int lines = 1;
/*     */     byte b;
/*     */     int i;
/*     */     char[] arrayOfChar;
/* 185 */     for (i = (arrayOfChar = text.toCharArray()).length, b = 0; b < i; ) {
/* 186 */       char c = arrayOfChar[b];
/* 187 */       if (c == '\n')
/* 188 */         lines++; 
/* 189 */       b = (byte)(b + 1);
/*     */     } 
/* 191 */     return (this.fontHeight - this.charOffset) / 2 * lines;
/*     */   }
/*     */   
/*     */   public int getHeight() {
/* 195 */     return (this.fontHeight - this.charOffset) / 2;
/*     */   }
/*     */   
/*     */   public int getStringWidth(String text) {
/* 199 */     int width = 0;
/*     */     byte b;
/*     */     int i;
/*     */     char[] arrayOfChar;
/* 203 */     for (i = (arrayOfChar = text.toCharArray()).length, b = 0; b < i; ) {
/* 204 */       char c = arrayOfChar[b];
/* 205 */       if (c < this.chars.length && c >= '\000')
/* 206 */         width += (this.chars[c]).width - this.charOffset; 
/* 207 */       b = (byte)(b + 1);
/*     */     } 
/* 209 */     return width / 2;
/*     */   }
/*     */   
/*     */   public boolean isAntiAlias() {
/* 213 */     return this.antiAlias;
/*     */   }
/*     */   
/*     */   public void setAntiAlias(boolean antiAlias) {
/* 217 */     if (this.antiAlias != antiAlias) {
/* 218 */       this.antiAlias = antiAlias;
/* 219 */       setupTexture(antiAlias);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Font getFont() {
/* 224 */     return this.font;
/*     */   }
/*     */   
/*     */   private class IntObject {
/*     */     public int width;
/*     */     public int height;
/*     */     public int storedX;
/*     */     public int storedY;
/*     */     
/*     */     private IntObject() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\font\ClientFont.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */