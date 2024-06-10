/*     */ package nightmare.fonts.impl;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.awt.image.BufferedImage;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import nightmare.fonts.api.FontRenderer;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class SimpleFontRenderer
/*     */   implements FontRenderer
/*     */ {
/*  21 */   private static final int[] COLOR_CODES = setupMinecraftColorCodes();
/*     */   
/*     */   private static final String COLORS = "0123456789abcdefklmnor";
/*     */   
/*     */   private static final char COLOR_PREFIX = '§';
/*     */   private static final short CHARS = 256;
/*     */   private static final float IMG_SIZE = 512.0F;
/*  28 */   private final CharData[] charData = new CharData[256];
/*  29 */   private final CharData[] boldChars = new CharData[256];
/*  30 */   private final CharData[] italicChars = new CharData[256];
/*  31 */   private final CharData[] boldItalicChars = new CharData[256];
/*     */   
/*     */   private final Font awtFont;
/*     */   
/*     */   private final boolean antiAlias;
/*     */   private final boolean fractionalMetrics;
/*     */   private DynamicTexture texturePlain;
/*     */   private DynamicTexture textureBold;
/*     */   private DynamicTexture textureItalic;
/*     */   private DynamicTexture textureItalicBold;
/*  41 */   private int fontHeight = -1;
/*     */   
/*     */   private SimpleFontRenderer(Font awtFont, boolean antiAlias, boolean fractionalMetrics) {
/*  44 */     this.awtFont = awtFont;
/*  45 */     this.antiAlias = antiAlias;
/*  46 */     this.fractionalMetrics = fractionalMetrics;
/*  47 */     setupBoldItalicFonts();
/*     */   }
/*     */   
/*     */   static FontRenderer create(Font font, boolean antiAlias, boolean fractionalMetrics) {
/*  51 */     return new SimpleFontRenderer(font, antiAlias, fractionalMetrics);
/*     */   }
/*     */   
/*     */   public static FontRenderer create(Font font) {
/*  55 */     return create(font, true, true);
/*     */   }
/*     */   
/*     */   private DynamicTexture setupTexture(Font font, boolean antiAlias, boolean fractionalMetrics, CharData[] chars) {
/*  59 */     return new DynamicTexture(generateFontImage(font, antiAlias, fractionalMetrics, chars));
/*     */   }
/*     */   
/*     */   private BufferedImage generateFontImage(Font font, boolean antiAlias, boolean fractionalMetrics, CharData[] chars) {
/*  63 */     int imgSize = 512;
/*  64 */     BufferedImage bufferedImage = new BufferedImage(512, 512, 2);
/*  65 */     Graphics2D graphics = (Graphics2D)bufferedImage.getGraphics();
/*     */     
/*  67 */     graphics.setFont(font);
/*  68 */     graphics.setColor(new Color(255, 255, 255, 0));
/*  69 */     graphics.fillRect(0, 0, 512, 512);
/*  70 */     graphics.setColor(Color.WHITE);
/*     */     
/*  72 */     graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/*  73 */     graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/*  74 */     graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
/*     */     
/*  76 */     if (this.fractionalMetrics) {
/*  77 */       graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
/*     */     } else {
/*  79 */       graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
/*     */     } 
/*     */     
/*  82 */     FontMetrics fontMetrics = graphics.getFontMetrics();
/*  83 */     int charHeight = 0, positionX = 0, positionY = 1;
/*     */     
/*  85 */     for (int i = 0; i < chars.length; i++) {
/*  86 */       char ch = (char)i;
/*  87 */       CharData charData = new CharData();
/*  88 */       Rectangle2D dimensions = fontMetrics.getStringBounds(String.valueOf(ch), graphics);
/*     */       
/*  90 */       charData.width = (dimensions.getBounds()).width + 8;
/*  91 */       charData.height = (dimensions.getBounds()).height;
/*     */       
/*  93 */       if (positionX + charData.width >= 512) {
/*  94 */         positionX = 0;
/*  95 */         positionY += charHeight;
/*  96 */         charHeight = 0;
/*     */       } 
/*     */       
/*  99 */       if (charData.height > charHeight) {
/* 100 */         charHeight = charData.height;
/*     */       }
/*     */       
/* 103 */       charData.storedX = positionX;
/* 104 */       charData.storedY = positionY;
/*     */       
/* 106 */       if (charData.height > this.fontHeight) {
/* 107 */         this.fontHeight = charData.height;
/*     */       }
/*     */       
/* 110 */       chars[i] = charData;
/*     */       
/* 112 */       graphics.drawString(String.valueOf(ch), positionX + 2, positionY + fontMetrics.getAscent());
/* 113 */       positionX += charData.width;
/*     */     } 
/*     */     
/* 116 */     return bufferedImage;
/*     */   }
/*     */   
/*     */   private void setupBoldItalicFonts() {
/* 120 */     this.texturePlain = setupTexture(this.awtFont, this.antiAlias, this.fractionalMetrics, this.charData);
/* 121 */     this.textureBold = setupTexture(this.awtFont.deriveFont(1), this.antiAlias, this.fractionalMetrics, this.boldChars);
/* 122 */     this.textureItalic = setupTexture(this.awtFont.deriveFont(2), this.antiAlias, this.fractionalMetrics, this.italicChars);
/* 123 */     this.textureItalicBold = setupTexture(this.awtFont.deriveFont(3), this.antiAlias, this.fractionalMetrics, this.boldItalicChars);
/*     */   }
/*     */ 
/*     */   
/*     */   public float drawString(CharSequence text, double x, double y, int color, boolean dropShadow) {
/* 128 */     if (dropShadow) {
/* 129 */       float shadowWidth = drawStringInternal(text, x + 0.5D, y + 0.5D, color, true);
/* 130 */       return Math.max(shadowWidth, drawStringInternal(text, x, y, color, false));
/*     */     } 
/* 132 */     return drawStringInternal(text, x, y, color, false);
/*     */   }
/*     */ 
/*     */   
/*     */   private float drawStringInternal(CharSequence text, double x, double y, int color, boolean shadow) {
/* 137 */     x--;
/*     */     
/* 139 */     if (text == null) return 0.0F;
/*     */     
/* 141 */     if (color == 553648127) color = 16777215; 
/* 142 */     if ((color & 0xFC000000) == 0) color |= 0xFF000000;
/*     */     
/* 144 */     if (shadow) {
/* 145 */       color = (color & 0xFCFCFC) >> 2 | color & 0xFF000000;
/*     */     }
/*     */     
/* 148 */     CharData[] charData = this.charData;
/* 149 */     float alpha = (color >> 24 & 0xFF) / 255.0F;
/*     */     
/* 151 */     x *= 2.0D;
/* 152 */     y = (y - 3.0D) * 2.0D;
/*     */     
/* 154 */     GL11.glPushMatrix();
/* 155 */     GlStateManager.func_179139_a(0.5D, 0.5D, 0.5D);
/* 156 */     GlStateManager.func_179147_l();
/* 157 */     GlStateManager.func_179112_b(770, 771);
/* 158 */     GL11.glColor4f((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, alpha);
/* 159 */     GlStateManager.func_179131_c((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, alpha);
/* 160 */     GlStateManager.func_179098_w();
/* 161 */     GlStateManager.func_179144_i(this.texturePlain.func_110552_b());
/*     */     
/* 163 */     boolean underline = false;
/* 164 */     boolean strikethrough = false;
/* 165 */     boolean italic = false;
/* 166 */     boolean bold = false;
/*     */     
/* 168 */     for (int i = 0, size = text.length(); i < size; i++) {
/* 169 */       char character = text.charAt(i);
/*     */       
/* 171 */       if (character == '§' && i + 1 < size) {
/* 172 */         int colorIndex = "0123456789abcdefklmnor".indexOf(text.charAt(i + 1));
/*     */         
/* 174 */         if (colorIndex < 16) {
/* 175 */           bold = false;
/* 176 */           italic = false;
/* 177 */           underline = false;
/* 178 */           strikethrough = false;
/* 179 */           GlStateManager.func_179144_i(this.texturePlain.func_110552_b());
/* 180 */           charData = this.charData;
/*     */           
/* 182 */           if (colorIndex < 0) colorIndex = 15; 
/* 183 */           if (shadow) colorIndex += 16;
/*     */           
/* 185 */           int colorCode = COLOR_CODES[colorIndex];
/* 186 */           GlStateManager.func_179131_c((colorCode >> 16 & 0xFF) / 255.0F, (colorCode >> 8 & 0xFF) / 255.0F, (colorCode & 0xFF) / 255.0F, 255.0F);
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 191 */         else if (colorIndex == 17) {
/* 192 */           bold = true;
/*     */           
/* 194 */           if (italic) {
/* 195 */             GlStateManager.func_179144_i(this.textureItalicBold.func_110552_b());
/* 196 */             charData = this.boldItalicChars;
/*     */           } else {
/* 198 */             GlStateManager.func_179144_i(this.textureBold.func_110552_b());
/* 199 */             charData = this.boldChars;
/*     */           } 
/* 201 */         } else if (colorIndex == 18) {
/* 202 */           strikethrough = true;
/* 203 */         } else if (colorIndex == 19) {
/* 204 */           underline = true;
/* 205 */         } else if (colorIndex == 20) {
/* 206 */           italic = true;
/*     */           
/* 208 */           if (bold) {
/* 209 */             GlStateManager.func_179144_i(this.textureItalicBold.func_110552_b());
/* 210 */             charData = this.boldItalicChars;
/*     */           } else {
/* 212 */             GlStateManager.func_179144_i(this.textureItalic.func_110552_b());
/* 213 */             charData = this.italicChars;
/*     */           } 
/* 215 */         } else if (colorIndex == 21) {
/* 216 */           bold = false;
/* 217 */           italic = false;
/* 218 */           underline = false;
/* 219 */           strikethrough = false;
/*     */           
/* 221 */           GlStateManager.func_179131_c((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, 255.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 226 */           GlStateManager.func_179144_i(this.texturePlain.func_110552_b());
/*     */           
/* 228 */           charData = this.charData;
/*     */         } 
/*     */         
/* 231 */         i++;
/* 232 */       } else if (character < charData.length) {
/* 233 */         GL11.glBegin(4);
/* 234 */         drawChar(charData, character, (float)x, (float)y);
/* 235 */         GL11.glEnd();
/*     */         
/* 237 */         if (strikethrough) {
/* 238 */           drawLine(x, y + ((charData[character])
/* 239 */               .height / 2.0F), x + (charData[character])
/* 240 */               .width - 8.0D, y + ((charData[character])
/* 241 */               .height / 2.0F), 1.0F);
/*     */         }
/*     */ 
/*     */         
/* 245 */         if (underline) {
/* 246 */           drawLine(x, y + (charData[character])
/* 247 */               .height - 2.0D, x + (charData[character])
/* 248 */               .width - 8.0D, y + (charData[character])
/* 249 */               .height - 2.0D, 1.0F);
/*     */         }
/*     */ 
/*     */         
/* 253 */         x += ((charData[character]).width - ((character == ' ') ? 8 : 9));
/*     */       } 
/*     */     } 
/*     */     
/* 257 */     GL11.glPopMatrix();
/*     */     
/* 259 */     return (float)x / 2.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String trimStringToWidth(CharSequence text, int width, boolean reverse) {
/* 266 */     StringBuilder builder = new StringBuilder();
/*     */     
/* 268 */     float f = 0.0F;
/* 269 */     int i = reverse ? (text.length() - 1) : 0;
/* 270 */     int j = reverse ? -1 : 1;
/* 271 */     boolean flag = false;
/* 272 */     boolean flag1 = false;
/*     */     int k;
/* 274 */     for (k = i; k >= 0 && k < text.length() && f < width; k += j) {
/* 275 */       char c0 = text.charAt(k);
/* 276 */       float f1 = getStringWidth(String.valueOf(c0));
/*     */       
/* 278 */       if (flag) {
/* 279 */         flag = false;
/*     */         
/* 281 */         if (c0 != 'l' && c0 != 'L') {
/* 282 */           if (c0 == 'r' || c0 == 'R') {
/* 283 */             flag1 = false;
/*     */           }
/*     */         } else {
/* 286 */           flag1 = true;
/*     */         } 
/* 288 */       } else if (f1 < 0.0F) {
/* 289 */         flag = true;
/*     */       } else {
/* 291 */         f += f1;
/* 292 */         if (flag1) f++;
/*     */       
/*     */       } 
/* 295 */       if (f > width)
/*     */         break; 
/* 297 */       if (reverse) {
/* 298 */         builder.insert(0, c0);
/*     */       } else {
/* 300 */         builder.append(c0);
/*     */       } 
/*     */     } 
/*     */     
/* 304 */     return builder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStringWidth(CharSequence text) {
/* 309 */     if (text == null) return 0;
/*     */     
/* 311 */     int width = 0;
/* 312 */     CharData[] currentData = this.charData;
/* 313 */     boolean bold = false;
/* 314 */     boolean italic = false;
/*     */     
/* 316 */     for (int i = 0, size = text.length(); i < size; i++) {
/* 317 */       char character = text.charAt(i);
/*     */       
/* 319 */       if (character == '§' && i + 1 < size) {
/* 320 */         int colorIndex = "0123456789abcdefklmnor".indexOf(text.charAt(i + 1));
/*     */         
/* 322 */         if (colorIndex < 16) {
/* 323 */           bold = false;
/* 324 */           italic = false;
/* 325 */         } else if (colorIndex == 17) {
/* 326 */           bold = true;
/* 327 */           if (italic) { currentData = this.boldItalicChars; }
/* 328 */           else { currentData = this.boldChars; } 
/* 329 */         } else if (colorIndex == 20) {
/* 330 */           italic = true;
/* 331 */           if (bold) { currentData = this.boldItalicChars; }
/* 332 */           else { currentData = this.italicChars; } 
/* 333 */         } else if (colorIndex == 21) {
/* 334 */           bold = false;
/* 335 */           italic = false;
/* 336 */           currentData = this.charData;
/*     */         } 
/* 338 */         i++;
/* 339 */       } else if (character < currentData.length) {
/* 340 */         width += (currentData[character]).width - ((character == ' ') ? 8 : 9);
/*     */       } 
/*     */     } 
/*     */     
/* 344 */     return width / 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getCharWidth(char s) {
/* 349 */     return (((this.charData[s]).width - 8) / 2);
/*     */   }
/*     */   
/*     */   public CharData[] getCharData() {
/* 353 */     return this.charData;
/*     */   }
/*     */   
/*     */   private static int[] setupMinecraftColorCodes() {
/* 357 */     int[] colorCodes = new int[32];
/*     */     
/* 359 */     for (int i = 0; i < 32; i++) {
/* 360 */       int noClue = (i >> 3 & 0x1) * 85;
/* 361 */       int red = (i >> 2 & 0x1) * 170 + noClue;
/* 362 */       int green = (i >> 1 & 0x1) * 170 + noClue;
/* 363 */       int blue = (i & 0x1) * 170 + noClue;
/*     */       
/* 365 */       if (i == 6) {
/* 366 */         red += 85;
/*     */       }
/*     */       
/* 369 */       if (i >= 16) {
/* 370 */         red >>= 2;
/* 371 */         green >>= 2;
/* 372 */         blue >>= 2;
/*     */       } 
/*     */       
/* 375 */       colorCodes[i] = (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF;
/*     */     } 
/*     */     
/* 378 */     return colorCodes;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class CharData
/*     */   {
/*     */     private int width;
/*     */     private int height;
/*     */     private int storedX;
/*     */     private int storedY;
/*     */     
/*     */     private CharData() {}
/*     */   }
/*     */   
/*     */   private static void drawChar(CharData[] chars, char c, float x, float y) {
/* 393 */     drawQuad(x, y, (chars[c]).width, (chars[c]).height, (chars[c]).storedX, (chars[c]).storedY, (chars[c]).width, (chars[c]).height);
/*     */   }
/*     */   
/*     */   private static void drawQuad(float x, float y, float width, float height, float srcX, float srcY, float srcWidth, float srcHeight) {
/* 397 */     float renderSRCX = srcX / 512.0F;
/* 398 */     float renderSRCY = srcY / 512.0F;
/* 399 */     float renderSRCWidth = srcWidth / 512.0F;
/* 400 */     float renderSRCHeight = srcHeight / 512.0F;
/*     */ 
/*     */     
/* 403 */     GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
/* 404 */     GL11.glVertex2d((x + width), y);
/* 405 */     GL11.glTexCoord2f(renderSRCX, renderSRCY);
/* 406 */     GL11.glVertex2d(x, y);
/* 407 */     GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
/* 408 */     GL11.glVertex2d(x, (y + height));
/* 409 */     GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
/* 410 */     GL11.glVertex2d(x, (y + height));
/* 411 */     GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY + renderSRCHeight);
/* 412 */     GL11.glVertex2d((x + width), (y + height));
/* 413 */     GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
/* 414 */     GL11.glVertex2d((x + width), y);
/*     */   }
/*     */   
/*     */   private static void drawLine(double x, double y, double x1, double y1, float width) {
/* 418 */     GL11.glDisable(3553);
/* 419 */     GL11.glLineWidth(width);
/* 420 */     GL11.glBegin(1);
/* 421 */     GL11.glVertex2d(x, y);
/* 422 */     GL11.glVertex2d(x1, y1);
/* 423 */     GL11.glEnd();
/* 424 */     GL11.glEnable(3553);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 429 */     return this.awtFont.getFamily();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 434 */     return (this.fontHeight - 8) / 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAntiAlias() {
/* 439 */     return this.antiAlias;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFractionalMetrics() {
/* 444 */     return this.fractionalMetrics;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\fonts\impl\SimpleFontRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */