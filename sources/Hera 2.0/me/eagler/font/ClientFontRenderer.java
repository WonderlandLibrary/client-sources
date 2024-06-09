/*     */ package me.eagler.font;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import me.eagler.clickgui.Colors;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.util.ChatAllowedCharacters;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class ClientFontRenderer
/*     */   extends FontRenderer {
/*  22 */   public final Random fontRandom = new Random();
/*  23 */   private final Color[] customColorCodes = new Color[256];
/*  24 */   private final int[] colorCode = new int[32];
/*     */   private ClientFont font;
/*     */   private ClientFont boldFont;
/*     */   private ClientFont italicFont;
/*     */   private ClientFont boldItalicFont;
/*  29 */   private String colorcodeIdentifiers = "0123456789abcdefklmnor";
/*     */   
/*     */   private boolean bidi;
/*     */   
/*     */   public ClientFontRenderer(Font font, boolean antiAlias, int charOffset) {
/*  34 */     super((Minecraft.getMinecraft()).gameSettings, new ResourceLocation("textures/font/ascii.png"), Minecraft.getMinecraft().getTextureManager(), false);
/*  35 */     setFont(font, antiAlias, charOffset);
/*  36 */     this.customColorCodes[113] = new Color(0, 90, 163);
/*  37 */     this.colorcodeIdentifiers = setupColorcodeIdentifier();
/*  38 */     setupMinecraftColorcodes();
/*  39 */     this.FONT_HEIGHT = getHeight();
/*     */   }
/*     */   
/*     */   public int drawString(String s, double d, double e, int color) {
/*  43 */     return drawString(s, d, e, color, false);
/*     */   }
/*     */   
/*     */   public int drawStringWithShadow(String s, double d, double e, int color) {
/*  47 */     return drawString(s, d, e, color, false);
/*     */   }
/*     */   
/*     */   public void drawCenteredString(String s, double d, double e, int color, boolean shadow) {
/*  51 */     if (shadow) {
/*  52 */       drawStringWithShadow(s, d - (getStringWidth(s) / 2), e, color);
/*     */     } else {
/*  54 */       drawString(s, d - (getStringWidth(s) / 2), e, color);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawCenteredString2(String s, double d, double e, int color, boolean shadow) {
/*  60 */     if (shadow) {
/*  61 */       drawStringWithShadow(s, d - (getStringWidth(s) / 2), e, color);
/*     */     } else {
/*  63 */       drawString(s, d, e, color);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawStringWithBG(String s, double x, double y, Color color) {
/*  70 */     Gui.drawRectWithEdge(x - 3.0D, y + 1.0D, (getStringWidth(s) + 5), (getStringHeight(s) + 1), Colors.getPrimary());
/*     */     
/*  72 */     drawCenteredString2(s, x, y, Colors.getText().getRGB(), false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawStringWithBGColor(String s, double x, double y, Color color, Color rectcolor) {
/*  78 */     Gui.drawRectWithEdge(x - 3.0D, y + 1.0D, (getStringWidth(s) + 5), (getStringHeight(s) + 1), rectcolor);
/*     */     
/*  80 */     drawCenteredString2(s, x, y, color.getRGB(), false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawStringWithBGShadow(String s, double x, double y, Color color) {
/*  86 */     Gui.drawRectWithShadow(x - 3.0D, y - 1.0D, (getStringWidth(s) + 4), (getStringHeight(s) + 4), "right", "bottom", Colors.getPrimary());
/*     */     
/*  88 */     drawCenteredString2(s, x, y, Colors.getText().getRGB(), false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawStringWithBGShadowColor(String s, double x, double y, Color color, Color rectcolor) {
/*  94 */     Gui.drawRectWithShadow(x - 3.0D, y - 1.0D, (getStringWidth(s) + 4), (getStringHeight(s) + 4), "right", "bottom", rectcolor);
/*     */     
/*  96 */     drawCenteredString2(s, x, y, color.getRGB(), false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawCenteredStringXY(String s, int x, int y, int color, boolean shadow) {
/* 101 */     drawCenteredString(s, x, (y - getHeight() / 2), color, shadow);
/*     */   }
/*     */   
/*     */   public void drawCenteredString(String s, int x, int y, int color) {
/* 105 */     drawStringWithShadow(s, (x - getStringWidth(s) / 2), y, color);
/*     */   }
/*     */   
/*     */   public int drawString(String text, double d, double e, int color, boolean shadow) {
/* 109 */     int result = 0;
/* 110 */     String[] lines = text.split("\n");
/*     */     
/* 112 */     for (int i = 0; i < lines.length; i++) {
/* 113 */       result = drawLine(lines[i], d, e + (i * getHeight()), color, shadow);
/*     */     }
/*     */     
/* 116 */     return result;
/*     */   }
/*     */   
/*     */   private int drawLine(String text, double d, double e, int color, boolean shadow) {
/* 120 */     if (text == null) {
/* 121 */       return 0;
/*     */     }
/* 123 */     GL11.glPushMatrix();
/* 124 */     GL11.glTranslated(d - 1.5D, e + 0.5D, 0.0D);
/* 125 */     boolean wasBlend = GL11.glGetBoolean(3042);
/* 126 */     GlStateManager.enableAlpha();
/* 127 */     GL11.glEnable(3042);
/* 128 */     GL11.glBlendFunc(770, 771);
/* 129 */     GL11.glEnable(3553);
/* 130 */     if ((color & 0xFC000000) == 0) {
/* 131 */       color |= 0xFF000000;
/*     */     }
/*     */     
/* 134 */     if (shadow) {
/* 135 */       color = (color & 0xFCFCFC) >> 2 | color & 0xFF000000;
/*     */     }
/*     */     
/* 138 */     float red = (color >> 16 & 0xFF) / 255.0F;
/* 139 */     float green = (color >> 8 & 0xFF) / 255.0F;
/* 140 */     float blue = (color & 0xFF) / 255.0F;
/* 141 */     float alpha = (color >> 24 & 0xFF) / 255.0F;
/* 142 */     Color c = new Color(red, green, blue, alpha);
/* 143 */     if (text.contains("§")) {
/* 144 */       String[] parts = text.split("§");
/* 145 */       Color currentColor = c;
/* 146 */       ClientFont currentFont = this.font;
/* 147 */       int width = 0;
/* 148 */       boolean randomCase = false;
/* 149 */       boolean bold = false;
/* 150 */       boolean italic = false;
/* 151 */       boolean strikethrough = false;
/* 152 */       boolean underline = false;
/*     */       
/* 154 */       for (int index = 0; index < parts.length; index++) {
/* 155 */         if (parts[index].length() > 0) {
/* 156 */           if (index == 0) {
/* 157 */             currentFont.drawString(parts[index], width, 0.0D, currentColor, shadow);
/* 158 */             width += currentFont.getStringWidth(parts[index]);
/*     */           } else {
/* 160 */             String words = parts[index].substring(1);
/* 161 */             char type = parts[index].charAt(0);
/* 162 */             int colorIndex = this.colorcodeIdentifiers.indexOf(type);
/* 163 */             if (colorIndex != -1) {
/* 164 */               if (colorIndex < 16) {
/* 165 */                 int colorcode = this.colorCode[colorIndex];
/* 166 */                 currentColor = getColor(colorcode, alpha);
/* 167 */                 bold = false;
/* 168 */                 italic = false;
/* 169 */                 randomCase = false;
/* 170 */                 underline = false;
/* 171 */                 strikethrough = false;
/* 172 */               } else if (colorIndex == 16) {
/* 173 */                 randomCase = true;
/* 174 */               } else if (colorIndex == 17) {
/* 175 */                 bold = true;
/* 176 */               } else if (colorIndex == 18) {
/* 177 */                 strikethrough = true;
/* 178 */               } else if (colorIndex == 19) {
/* 179 */                 underline = true;
/* 180 */               } else if (colorIndex == 20) {
/* 181 */                 italic = true;
/* 182 */               } else if (colorIndex == 21) {
/* 183 */                 bold = false;
/* 184 */                 italic = false;
/* 185 */                 randomCase = false;
/* 186 */                 underline = false;
/* 187 */                 strikethrough = false;
/* 188 */                 currentColor = c;
/* 189 */               } else if (colorIndex > 21) {
/* 190 */                 Color customColor = this.customColorCodes[type];
/* 191 */                 currentColor = new Color(customColor.getRed() / 255.0F, 
/* 192 */                     customColor.getGreen() / 255.0F, 
/* 193 */                     customColor.getBlue() / 255.0F, alpha);
/*     */               } 
/*     */             }
/*     */             
/* 197 */             if (bold && italic) {
/* 198 */               this.boldItalicFont.drawString(randomCase ? toRandom(currentFont, words) : words, 
/* 199 */                   width, 0.0D, currentColor, shadow);
/* 200 */               currentFont = this.boldItalicFont;
/* 201 */             } else if (bold) {
/* 202 */               this.boldFont.drawString(randomCase ? toRandom(currentFont, words) : words, 
/* 203 */                   width, 0.0D, currentColor, shadow);
/* 204 */               currentFont = this.boldFont;
/* 205 */             } else if (italic) {
/* 206 */               this.italicFont.drawString(randomCase ? toRandom(currentFont, words) : words, 
/* 207 */                   width, 0.0D, currentColor, shadow);
/* 208 */               currentFont = this.italicFont;
/*     */             } else {
/* 210 */               this.font.drawString(randomCase ? toRandom(currentFont, words) : words, 
/* 211 */                   width, 0.0D, currentColor, shadow);
/* 212 */               currentFont = this.font;
/*     */             } 
/*     */             
/* 215 */             float u = this.font.getHeight() / 16.0F;
/* 216 */             int h = currentFont.getStringHeight(words);
/* 217 */             if (strikethrough) {
/* 218 */               drawLine(width / 2.0D + 1.0D, (h / 3), (
/* 219 */                   width + currentFont.getStringWidth(words)) / 2.0D + 1.0D, (
/* 220 */                   h / 3), u);
/*     */             }
/*     */             
/* 223 */             if (underline) {
/* 224 */               drawLine(width / 2.0D + 1.0D, (h / 2), (
/* 225 */                   width + currentFont.getStringWidth(words)) / 2.0D + 1.0D, (
/* 226 */                   h / 2), u);
/*     */             }
/*     */             
/* 229 */             width += currentFont.getStringWidth(words);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } else {
/* 234 */       this.font.drawString(text, 0.0D, 0.0D, c, shadow);
/*     */     } 
/*     */     
/* 237 */     if (!wasBlend) {
/* 238 */       GL11.glDisable(3042);
/*     */     }
/*     */     
/* 241 */     GL11.glPopMatrix();
/* 242 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 243 */     return (int)(d + getStringWidth(text));
/*     */   }
/*     */ 
/*     */   
/*     */   private String toRandom(ClientFont font, String text) {
/* 248 */     String newText = "";
/* 249 */     String allowedCharacters = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\000\000\000\000\000\000\000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\000";
/*     */     char[] var8;
/* 251 */     int var7 = (var8 = text.toCharArray()).length;
/*     */     
/* 253 */     for (int var6 = 0; var6 < var7; var6++) {
/* 254 */       char c = var8[var6];
/* 255 */       if (ChatAllowedCharacters.isAllowedCharacter(c)) {
/* 256 */         int index = this.fontRandom.nextInt(
/* 257 */             "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\000\000\000\000\000\000\000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\000"
/* 258 */             .length());
/* 259 */         newText = String.valueOf(newText) + 
/* 260 */           "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\000\000\000\000\000\000\000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\000"
/* 261 */           .toCharArray()[index];
/*     */       } 
/*     */     } 
/*     */     
/* 265 */     return newText;
/*     */   }
/*     */   
/*     */   public int getStringHeight(String text) {
/* 269 */     return (text == null) ? 0 : (this.font.getStringHeight(text) / 2);
/*     */   }
/*     */   
/*     */   public int getHeight() {
/* 273 */     return this.font.getHeight() / 2;
/*     */   }
/*     */   
/*     */   public static String getFormatFromString(String p_78282_0_) {
/* 277 */     String var1 = "";
/* 278 */     int var2 = -1;
/* 279 */     int var3 = p_78282_0_.length();
/*     */     
/* 281 */     while ((var2 = p_78282_0_.indexOf('§', var2 + 1)) != -1) {
/* 282 */       if (var2 < var3 - 1) {
/* 283 */         char var4 = p_78282_0_.charAt(var2 + 1);
/* 284 */         if (isFormatColor(var4)) {
/* 285 */           var1 = "§" + var4; continue;
/* 286 */         }  if (isFormatSpecial(var4)) {
/* 287 */           var1 = String.valueOf(var1) + "§" + var4;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 292 */     return var1;
/*     */   }
/*     */   
/*     */   private static boolean isFormatSpecial(char formatChar) {
/* 296 */     return !((formatChar < 'k' || formatChar > 'o') && (formatChar < 'K' || formatChar > 'O') && formatChar != 'r' && 
/* 297 */       formatChar != 'R');
/*     */   }
/*     */   
/*     */   public int getColorCode(char p_175064_1_) {
/* 301 */     return this.colorCode["0123456789abcdef".indexOf(p_175064_1_)];
/*     */   }
/*     */   
/*     */   public void setBidiFlag(boolean state) {
/* 305 */     this.bidi = state;
/*     */   }
/*     */   
/*     */   public boolean getBidiFlag() {
/* 309 */     return this.bidi;
/*     */   }
/*     */   
/*     */   private int sizeStringToWidth(String str, int wrapWidth) {
/* 313 */     int var3 = str.length();
/* 314 */     int var4 = 0;
/* 315 */     int var5 = 0;
/* 316 */     int var6 = -1;
/*     */     
/* 318 */     for (boolean var7 = false; var5 < var3; var5++) {
/* 319 */       char var8 = str.charAt(var5);
/* 320 */       switch (var8) {
/*     */         case '\n':
/* 322 */           var5--;
/*     */           break;
/*     */         case ' ':
/* 325 */           var6 = var5;
/*     */         default:
/* 327 */           var4 += getStringWidth(Character.toString(var8));
/* 328 */           if (var7) {
/* 329 */             var4++;
/*     */           }
/*     */           break;
/*     */         case '§':
/* 333 */           if (var5 < var3 - 1) {
/* 334 */             var5++;
/* 335 */             char var9 = str.charAt(var5);
/* 336 */             if (var9 != 'l' && var9 != 'L') {
/* 337 */               if (var9 == 'r' || var9 == 'R' || isFormatColor(var9))
/* 338 */                 var7 = false; 
/*     */               break;
/*     */             } 
/* 341 */             var7 = true;
/*     */           } 
/*     */           break;
/*     */       } 
/*     */       
/* 346 */       if (var8 == '\n') {
/*     */         
/* 348 */         var6 = ++var5;
/*     */         
/*     */         break;
/*     */       } 
/* 352 */       if (var4 > wrapWidth) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 357 */     return (var5 != var3 && var6 != -1 && var6 < var5) ? var6 : var5;
/*     */   }
/*     */   
/*     */   private static boolean isFormatColor(char colorChar) {
/* 361 */     return !((colorChar < '0' || colorChar > '9') && (colorChar < 'a' || colorChar > 'f') && (
/* 362 */       colorChar < 'A' || colorChar > 'F'));
/*     */   }
/*     */   
/*     */   public int getCharWidth(char c) {
/* 366 */     return getStringWidth(Character.toString(c));
/*     */   }
/*     */   
/*     */   public int getStringWidth(String text) {
/* 370 */     if (text == null)
/* 371 */       return 0; 
/* 372 */     if (!text.contains("§")) {
/* 373 */       return this.font.getStringWidth(text) / 2;
/*     */     }
/* 375 */     String[] parts = text.split("§");
/* 376 */     ClientFont currentFont = this.font;
/* 377 */     int width = 0;
/* 378 */     boolean bold = false;
/* 379 */     boolean italic = false;
/*     */     
/* 381 */     for (int index = 0; index < parts.length; index++) {
/* 382 */       if (parts[index].length() > 0) {
/* 383 */         if (index == 0) {
/* 384 */           width += currentFont.getStringWidth(parts[index]);
/*     */         } else {
/* 386 */           String words = parts[index].substring(1);
/* 387 */           char type = parts[index].charAt(0);
/* 388 */           int colorIndex = this.colorcodeIdentifiers.indexOf(type);
/* 389 */           if (colorIndex != -1) {
/* 390 */             if (colorIndex < 16) {
/* 391 */               bold = false;
/* 392 */               italic = false;
/* 393 */             } else if (colorIndex != 16) {
/* 394 */               if (colorIndex == 17) {
/* 395 */                 bold = true;
/* 396 */               } else if (colorIndex != 18 && colorIndex != 19) {
/* 397 */                 if (colorIndex == 20) {
/* 398 */                   italic = true;
/* 399 */                 } else if (colorIndex == 21) {
/* 400 */                   bold = false;
/* 401 */                   italic = false;
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           }
/*     */           
/* 407 */           if (bold && italic) {
/* 408 */             currentFont = this.boldItalicFont;
/* 409 */           } else if (bold) {
/* 410 */             currentFont = this.boldFont;
/* 411 */           } else if (italic) {
/* 412 */             currentFont = this.italicFont;
/*     */           } else {
/* 414 */             currentFont = this.font;
/*     */           } 
/*     */           
/* 417 */           width += currentFont.getStringWidth(words);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 422 */     return width / 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFont(Font font, boolean antiAlias, int charOffset) {
/* 427 */     synchronized (this) {
/* 428 */       this.font = new ClientFont(font, antiAlias, charOffset);
/* 429 */       this.boldFont = new ClientFont(font.deriveFont(1), antiAlias, charOffset);
/* 430 */       this.italicFont = new ClientFont(font.deriveFont(2), antiAlias, charOffset);
/* 431 */       this.boldItalicFont = new ClientFont(font.deriveFont(3), antiAlias, charOffset);
/* 432 */       this.FONT_HEIGHT = getHeight();
/*     */     } 
/*     */   }
/*     */   
/*     */   public ClientFont getFont() {
/* 437 */     return this.font;
/*     */   }
/*     */   
/*     */   public String getFontName() {
/* 441 */     return this.font.getFont().getFontName();
/*     */   }
/*     */   
/*     */   public int getSize() {
/* 445 */     return this.font.getFont().getSize();
/*     */   }
/*     */   
/*     */   public List<String> wrapWords(String text, double width) {
/* 449 */     List<String> finalWords = new ArrayList<String>();
/* 450 */     if (getStringWidth(text) > width) {
/* 451 */       String[] words = text.split(" ");
/* 452 */       String currentWord = "";
/* 453 */       char lastColorCode = Character.MAX_VALUE;
/* 454 */       String[] var11 = words;
/* 455 */       int var10 = words.length;
/*     */ 
/*     */       
/* 458 */       for (int var9 = 0; var9 < var10; var9++) {
/* 459 */         String s = var11[var9];
/*     */         
/* 461 */         for (int i = 0; i < (s.toCharArray()).length; i++) {
/* 462 */           char c = s.toCharArray()[i];
/* 463 */           if (c == '§' && i < (s.toCharArray()).length - 1) {
/* 464 */             lastColorCode = s.toCharArray()[i + 1];
/*     */           }
/*     */         } 
/*     */         
/* 468 */         if (getStringWidth(String.valueOf(currentWord) + s + " ") < width) {
/* 469 */           currentWord = String.valueOf(currentWord) + s + " ";
/*     */         } else {
/* 471 */           finalWords.add(currentWord);
/* 472 */           currentWord = (lastColorCode == -1) ? (String.valueOf(s) + " ") : ("§" + lastColorCode + s + " ");
/*     */         } 
/*     */       } 
/*     */       
/* 476 */       if (!currentWord.equals("")) {
/* 477 */         if (getStringWidth(currentWord) < width) {
/* 478 */           finalWords.add((lastColorCode == -1) ? (String.valueOf(currentWord) + " ") : ("§" + lastColorCode + currentWord + " "));
/* 479 */           currentWord = "";
/*     */         } else {
/* 481 */           Iterator<String> var14 = formatString(currentWord, width).iterator();
/*     */           
/* 483 */           while (var14.hasNext()) {
/* 484 */             String s = var14.next();
/* 485 */             finalWords.add(s);
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } else {
/* 490 */       finalWords.add(text);
/*     */     } 
/*     */     
/* 493 */     return finalWords;
/*     */   }
/*     */   
/*     */   public List<String> formatString(String s, double width) {
/* 497 */     List<String> finalWords = new ArrayList<String>();
/* 498 */     String currentWord = "";
/* 499 */     char lastColorCode = Character.MAX_VALUE;
/*     */     
/* 501 */     for (int i = 0; i < (s.toCharArray()).length; i++) {
/* 502 */       char c = s.toCharArray()[i];
/* 503 */       if (c == '§' && i < (s.toCharArray()).length - 1) {
/* 504 */         lastColorCode = s.toCharArray()[i + 1];
/*     */       }
/*     */       
/* 507 */       if (getStringWidth(String.valueOf(currentWord) + c) < width) {
/* 508 */         currentWord = String.valueOf(currentWord) + c;
/*     */       } else {
/* 510 */         finalWords.add(currentWord);
/* 511 */         currentWord = (lastColorCode == -1) ? String.valueOf(c) : ("§" + lastColorCode + c);
/*     */       } 
/*     */     } 
/*     */     
/* 515 */     if (!currentWord.equals("")) {
/* 516 */       finalWords.add(currentWord);
/*     */     }
/*     */     
/* 519 */     return finalWords;
/*     */   }
/*     */   
/*     */   private void drawLine(double x, double y, double x1, double y1, float width) {
/* 523 */     GL11.glDisable(3553);
/* 524 */     GL11.glLineWidth(width);
/* 525 */     GL11.glBegin(1);
/* 526 */     GL11.glVertex2d(x, y);
/* 527 */     GL11.glVertex2d(x1, y1);
/* 528 */     GL11.glEnd();
/* 529 */     GL11.glEnable(3553);
/*     */   }
/*     */   
/*     */   public boolean isAntiAliasing() {
/* 533 */     return (this.font.isAntiAlias() && this.boldFont.isAntiAlias() && this.italicFont.isAntiAlias() && 
/* 534 */       this.boldItalicFont.isAntiAlias());
/*     */   }
/*     */   
/*     */   public void setAntiAliasing(boolean antiAlias) {
/* 538 */     this.font.setAntiAlias(antiAlias);
/* 539 */     this.boldFont.setAntiAlias(antiAlias);
/* 540 */     this.italicFont.setAntiAlias(antiAlias);
/* 541 */     this.boldItalicFont.setAntiAlias(antiAlias);
/*     */   }
/*     */   
/*     */   private void setupMinecraftColorcodes() {
/* 545 */     for (int index = 0; index < 32; index++) {
/* 546 */       int var6 = (index >> 3 & 0x1) * 85;
/* 547 */       int var7 = (index >> 2 & 0x1) * 170 + var6;
/* 548 */       int var8 = (index >> 1 & 0x1) * 170 + var6;
/* 549 */       int var9 = (index >> 0 & 0x1) * 170 + var6;
/* 550 */       if (index == 6) {
/* 551 */         var7 += 85;
/*     */       }
/*     */       
/* 554 */       if (index >= 16) {
/* 555 */         var7 /= 4;
/* 556 */         var8 /= 4;
/* 557 */         var9 /= 4;
/*     */       } 
/*     */       
/* 560 */       this.colorCode[index] = (var7 & 0xFF) << 16 | (var8 & 0xFF) << 8 | var9 & 0xFF;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String trimStringToWidth(String p_78269_1_, int p_78269_2_) {
/* 566 */     return trimStringToWidth(p_78269_1_, p_78269_2_, false);
/*     */   }
/*     */   
/*     */   public String trimStringToWidth(String p_78262_1_, int p_78262_2_, boolean p_78262_3_) {
/* 570 */     StringBuilder var4 = new StringBuilder();
/* 571 */     int var5 = 0;
/* 572 */     int var6 = p_78262_3_ ? (p_78262_1_.length() - 1) : 0;
/* 573 */     int var7 = p_78262_3_ ? -1 : 1;
/* 574 */     boolean var8 = false;
/* 575 */     boolean var9 = false;
/*     */     
/* 577 */     for (int var10 = var6; var10 >= 0 && var10 < p_78262_1_.length() && var5 < p_78262_2_; var10 += var7) {
/* 578 */       char var11 = p_78262_1_.charAt(var10);
/* 579 */       int var12 = getStringWidth(Character.toString(var11));
/* 580 */       if (var8) {
/* 581 */         var8 = false;
/* 582 */         if (var11 != 'l' && var11 != 'L') {
/* 583 */           if (var11 == 'r' || var11 == 'R') {
/* 584 */             var9 = false;
/*     */           }
/*     */         } else {
/* 587 */           var9 = true;
/*     */         } 
/* 589 */       } else if (var12 < 0) {
/* 590 */         var8 = true;
/*     */       } else {
/* 592 */         var5 += var12;
/* 593 */         if (var9) {
/* 594 */           var5++;
/*     */         }
/*     */       } 
/*     */       
/* 598 */       if (var5 > p_78262_2_) {
/*     */         break;
/*     */       }
/*     */       
/* 602 */       if (p_78262_3_) {
/* 603 */         var4.insert(0, var11);
/*     */       } else {
/* 605 */         var4.append(var11);
/*     */       } 
/*     */     } 
/*     */     
/* 609 */     return var4.toString();
/*     */   }
/*     */   
/*     */   public List<String> listFormattedStringToWidth(String str, int wrapWidth) {
/* 613 */     return Arrays.asList(wrapFormattedStringToWidth(str, wrapWidth).split("\n"));
/*     */   }
/*     */   
/*     */   protected String wrapFormattedStringToWidth(String str, int wrapWidth) {
/* 617 */     int var3 = sizeStringToWidth(str, wrapWidth);
/* 618 */     if (str.length() <= var3) {
/* 619 */       return str;
/*     */     }
/* 621 */     String var4 = str.substring(0, var3);
/* 622 */     char var5 = str.charAt(var3);
/* 623 */     boolean var6 = !(var5 != ' ' && var5 != '\n');
/* 624 */     String var7 = String.valueOf(getFormatFromString(var4)) + str.substring(var3 + (var6 ? 1 : 0));
/* 625 */     return String.valueOf(var4) + "\n" + wrapFormattedStringToWidth(var7, wrapWidth);
/*     */   }
/*     */ 
/*     */   
/*     */   public Color getColor(int colorCode, float alpha) {
/* 630 */     return new Color((colorCode >> 16) / 255.0F, (colorCode >> 8 & 0xFF) / 255.0F, (
/* 631 */         colorCode & 0xFF) / 255.0F, alpha);
/*     */   }
/*     */   
/*     */   private String setupColorcodeIdentifier() {
/* 635 */     String minecraftColorCodes = "0123456789abcdefklmnor";
/*     */     
/* 637 */     for (int i = 0; i < this.customColorCodes.length; i++) {
/* 638 */       if (this.customColorCodes[i] != null) {
/* 639 */         minecraftColorCodes = String.valueOf(minecraftColorCodes) + (char)i;
/*     */       }
/*     */     } 
/*     */     
/* 643 */     return minecraftColorCodes;
/*     */   }
/*     */   
/*     */   public void onResourceManagerReload(IResourceManager p_110549_1_) {}
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\font\ClientFontRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */