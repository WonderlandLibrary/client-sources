/*    1:     */ package net.minecraft.client.gui;
/*    2:     */ 
/*    3:     */ import com.ibm.icu.text.ArabicShaping;
/*    4:     */ import com.ibm.icu.text.ArabicShapingException;
/*    5:     */ import com.ibm.icu.text.Bidi;
/*    6:     */ import java.awt.image.BufferedImage;
/*    7:     */ import java.io.FileNotFoundException;
/*    8:     */ import java.io.IOException;
/*    9:     */ import java.io.InputStream;
/*   10:     */ import java.util.Arrays;
/*   11:     */ import java.util.Iterator;
/*   12:     */ import java.util.List;
/*   13:     */ import java.util.Properties;
/*   14:     */ import java.util.Random;
/*   15:     */ import java.util.Set;
/*   16:     */ import javax.imageio.ImageIO;
/*   17:     */ import net.minecraft.client.Minecraft;
/*   18:     */ import net.minecraft.client.renderer.Tessellator;
/*   19:     */ import net.minecraft.client.renderer.texture.TextureManager;
/*   20:     */ import net.minecraft.client.resources.IResource;
/*   21:     */ import net.minecraft.client.resources.IResourceManager;
/*   22:     */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*   23:     */ import net.minecraft.client.settings.GameSettings;
/*   24:     */ import net.minecraft.src.Config;
/*   25:     */ import net.minecraft.util.ResourceLocation;
/*   26:     */ import org.lwjgl.opengl.GL11;
/*   27:     */ 
/*   28:     */ public class FontRenderer
/*   29:     */   implements IResourceManagerReloadListener
/*   30:     */ {
/*   31:  29 */   private static final ResourceLocation[] unicodePageLocations = new ResourceLocation[256];
/*   32:  32 */   private float[] charWidth = new float[256];
/*   33:  35 */   public int FONT_HEIGHT = 9;
/*   34:  36 */   public Random fontRandom = new Random();
/*   35:  41 */   private byte[] glyphWidth = new byte[65536];
/*   36:  47 */   private int[] colorCode = new int[32];
/*   37:     */   private ResourceLocation locationFontTexture;
/*   38:     */   private final TextureManager renderEngine;
/*   39:     */   private float posX;
/*   40:     */   private float posY;
/*   41:     */   private boolean unicodeFlag;
/*   42:     */   private boolean bidiFlag;
/*   43:     */   private float red;
/*   44:     */   private float blue;
/*   45:     */   private float green;
/*   46:     */   private float alpha;
/*   47:     */   private int textColor;
/*   48:     */   private boolean randomStyle;
/*   49:     */   private boolean boldStyle;
/*   50:     */   private boolean italicStyle;
/*   51:     */   private boolean underlineStyle;
/*   52:     */   private boolean strikethroughStyle;
/*   53:     */   public GameSettings gameSettings;
/*   54:     */   public ResourceLocation locationFontTextureBase;
/*   55: 104 */   public boolean enabled = true;
/*   56:     */   private static final String __OBFID = "CL_00000660";
/*   57:     */   
/*   58:     */   public FontRenderer(GameSettings par1GameSettings, ResourceLocation par2ResourceLocation, TextureManager par3TextureManager, boolean par4)
/*   59:     */   {
/*   60: 109 */     this.gameSettings = par1GameSettings;
/*   61: 110 */     this.locationFontTextureBase = par2ResourceLocation;
/*   62: 111 */     this.locationFontTexture = par2ResourceLocation;
/*   63: 112 */     this.renderEngine = par3TextureManager;
/*   64: 113 */     this.unicodeFlag = par4;
/*   65: 114 */     this.locationFontTexture = getHdFontLocation(this.locationFontTextureBase);
/*   66: 115 */     par3TextureManager.bindTexture(this.locationFontTexture);
/*   67: 117 */     for (int var5 = 0; var5 < 32; var5++)
/*   68:     */     {
/*   69: 119 */       int var6 = (var5 >> 3 & 0x1) * 85;
/*   70: 120 */       int var7 = (var5 >> 2 & 0x1) * 170 + var6;
/*   71: 121 */       int var8 = (var5 >> 1 & 0x1) * 170 + var6;
/*   72: 122 */       int var9 = (var5 >> 0 & 0x1) * 170 + var6;
/*   73: 124 */       if (var5 == 6) {
/*   74: 126 */         var7 += 85;
/*   75:     */       }
/*   76: 129 */       if (par1GameSettings.anaglyph)
/*   77:     */       {
/*   78: 131 */         int var10 = (var7 * 30 + var8 * 59 + var9 * 11) / 100;
/*   79: 132 */         int var11 = (var7 * 30 + var8 * 70) / 100;
/*   80: 133 */         int var12 = (var7 * 30 + var9 * 70) / 100;
/*   81: 134 */         var7 = var10;
/*   82: 135 */         var8 = var11;
/*   83: 136 */         var9 = var12;
/*   84:     */       }
/*   85: 139 */       if (var5 >= 16)
/*   86:     */       {
/*   87: 141 */         var7 /= 4;
/*   88: 142 */         var8 /= 4;
/*   89: 143 */         var9 /= 4;
/*   90:     */       }
/*   91: 146 */       this.colorCode[var5] = ((var7 & 0xFF) << 16 | (var8 & 0xFF) << 8 | var9 & 0xFF);
/*   92:     */     }
/*   93: 149 */     readGlyphSizes();
/*   94:     */   }
/*   95:     */   
/*   96:     */   public void onResourceManagerReload(IResourceManager par1ResourceManager)
/*   97:     */   {
/*   98: 154 */     this.locationFontTexture = getHdFontLocation(this.locationFontTextureBase);
/*   99: 156 */     for (int i = 0; i < unicodePageLocations.length; i++) {
/*  100: 158 */       unicodePageLocations[i] = null;
/*  101:     */     }
/*  102: 161 */     readFontTexture();
/*  103:     */   }
/*  104:     */   
/*  105:     */   private void readFontTexture()
/*  106:     */   {
/*  107:     */     try
/*  108:     */     {
/*  109: 170 */       bufferedimage = ImageIO.read(Minecraft.getMinecraft().getResourceManager().getResource(this.locationFontTexture).getInputStream());
/*  110:     */     }
/*  111:     */     catch (IOException var18)
/*  112:     */     {
/*  113:     */       BufferedImage bufferedimage;
/*  114: 174 */       throw new RuntimeException(var18);
/*  115:     */     }
/*  116:     */     BufferedImage bufferedimage;
/*  117: 177 */     int imgWidth = bufferedimage.getWidth();
/*  118: 178 */     int imgHeight = bufferedimage.getHeight();
/*  119: 179 */     int charW = imgWidth / 16;
/*  120: 180 */     int charH = imgHeight / 16;
/*  121: 181 */     float kx = imgWidth / 128.0F;
/*  122: 182 */     int[] ai = new int[imgWidth * imgHeight];
/*  123: 183 */     bufferedimage.getRGB(0, 0, imgWidth, imgHeight, ai, 0, imgWidth);
/*  124: 184 */     int k = 0;
/*  125: 186 */     while (k < 256)
/*  126:     */     {
/*  127: 188 */       int cx = k % 16;
/*  128: 189 */       int cy = k / 16;
/*  129: 190 */       boolean px = false;
/*  130: 191 */       int var19 = charW - 1;
/*  131: 195 */       while (var19 >= 0)
/*  132:     */       {
/*  133: 197 */         int x = cx * charW + var19;
/*  134: 198 */         boolean flag = true;
/*  135: 200 */         for (int py = 0; (py < charH) && (flag); py++)
/*  136:     */         {
/*  137: 202 */           int ypos = (cy * charH + py) * imgWidth;
/*  138: 203 */           int col = ai[(x + ypos)];
/*  139: 204 */           int al = col >> 24 & 0xFF;
/*  140: 206 */           if (al > 16) {
/*  141: 208 */             flag = false;
/*  142:     */           }
/*  143:     */         }
/*  144: 212 */         if (!flag) {
/*  145:     */           break;
/*  146:     */         }
/*  147: 214 */         var19--;
/*  148:     */       }
/*  149: 223 */       if (k == 32) {
/*  150: 225 */         if (charW <= 8) {
/*  151: 227 */           var19 = (int)(2.0F * kx);
/*  152:     */         } else {
/*  153: 231 */           var19 = (int)(1.5F * kx);
/*  154:     */         }
/*  155:     */       }
/*  156: 235 */       this.charWidth[k] = ((var19 + 1) / kx + 1.0F);
/*  157: 236 */       k++;
/*  158:     */     }
/*  159: 241 */     readCustomCharWidths();
/*  160:     */   }
/*  161:     */   
/*  162:     */   private void readGlyphSizes()
/*  163:     */   {
/*  164:     */     try
/*  165:     */     {
/*  166: 248 */       InputStream var2 = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("font/glyph_sizes.bin")).getInputStream();
/*  167: 249 */       var2.read(this.glyphWidth);
/*  168:     */     }
/*  169:     */     catch (IOException var21)
/*  170:     */     {
/*  171: 253 */       throw new RuntimeException(var21);
/*  172:     */     }
/*  173:     */   }
/*  174:     */   
/*  175:     */   private float renderCharAtPos(int par1, char par2, boolean par3)
/*  176:     */   {
/*  177: 262 */     return ("".indexOf(par2) != -1) && (!this.unicodeFlag) ? renderDefaultChar(par1, par3) : par2 == ' ' ? 4.0F : par2 == ' ' ? this.charWidth[par2] : renderUnicodeChar(par2, par3);
/*  178:     */   }
/*  179:     */   
/*  180:     */   private float renderDefaultChar(int par1, boolean par2)
/*  181:     */   {
/*  182: 270 */     float var3 = par1 % 16 * 8;
/*  183: 271 */     float var4 = par1 / 16 * 8;
/*  184: 272 */     float var5 = par2 ? 1.0F : 0.0F;
/*  185: 273 */     this.renderEngine.bindTexture(this.locationFontTexture);
/*  186: 274 */     float var6 = this.charWidth[par1] - 0.01F;
/*  187: 275 */     GL11.glBegin(5);
/*  188: 276 */     GL11.glTexCoord2f(var3 / 128.0F, var4 / 128.0F);
/*  189: 277 */     GL11.glVertex3f(this.posX + var5, this.posY, 0.0F);
/*  190: 278 */     GL11.glTexCoord2f(var3 / 128.0F, (var4 + 7.99F) / 128.0F);
/*  191: 279 */     GL11.glVertex3f(this.posX - var5, this.posY + 7.99F, 0.0F);
/*  192: 280 */     GL11.glTexCoord2f((var3 + var6 - 1.0F) / 128.0F, var4 / 128.0F);
/*  193: 281 */     GL11.glVertex3f(this.posX + var6 - 1.0F + var5, this.posY, 0.0F);
/*  194: 282 */     GL11.glTexCoord2f((var3 + var6 - 1.0F) / 128.0F, (var4 + 7.99F) / 128.0F);
/*  195: 283 */     GL11.glVertex3f(this.posX + var6 - 1.0F - var5, this.posY + 7.99F, 0.0F);
/*  196: 284 */     GL11.glEnd();
/*  197: 285 */     return this.charWidth[par1];
/*  198:     */   }
/*  199:     */   
/*  200:     */   private ResourceLocation getUnicodePageLocation(int par1)
/*  201:     */   {
/*  202: 290 */     if (unicodePageLocations[par1] == null)
/*  203:     */     {
/*  204: 292 */       unicodePageLocations[par1] = new ResourceLocation(String.format("textures/font/unicode_page_%02x.png", new Object[] { Integer.valueOf(par1) }));
/*  205: 293 */       unicodePageLocations[par1] = getHdFontLocation(unicodePageLocations[par1]);
/*  206:     */     }
/*  207: 296 */     return unicodePageLocations[par1];
/*  208:     */   }
/*  209:     */   
/*  210:     */   private void loadGlyphTexture(int par1)
/*  211:     */   {
/*  212: 304 */     this.renderEngine.bindTexture(getUnicodePageLocation(par1));
/*  213:     */   }
/*  214:     */   
/*  215:     */   private float renderUnicodeChar(char par1, boolean par2)
/*  216:     */   {
/*  217: 312 */     if (this.glyphWidth[par1] == 0) {
/*  218: 314 */       return 0.0F;
/*  219:     */     }
/*  220: 318 */     int var3 = par1 / 'Ā';
/*  221: 319 */     loadGlyphTexture(var3);
/*  222: 320 */     int var4 = this.glyphWidth[par1] >>> 4;
/*  223: 321 */     int var5 = this.glyphWidth[par1] & 0xF;
/*  224: 322 */     float var6 = var4;
/*  225: 323 */     float var7 = var5 + 1;
/*  226: 324 */     float var8 = par1 % '\020' * 16 + var6;
/*  227: 325 */     float var9 = (par1 & 0xFF) / '\020' * 16;
/*  228: 326 */     float var10 = var7 - var6 - 0.02F;
/*  229: 327 */     float var11 = par2 ? 1.0F : 0.0F;
/*  230: 328 */     GL11.glBegin(5);
/*  231: 329 */     GL11.glTexCoord2f(var8 / 256.0F, var9 / 256.0F);
/*  232: 330 */     GL11.glVertex3f(this.posX + var11, this.posY, 0.0F);
/*  233: 331 */     GL11.glTexCoord2f(var8 / 256.0F, (var9 + 15.98F) / 256.0F);
/*  234: 332 */     GL11.glVertex3f(this.posX - var11, this.posY + 7.99F, 0.0F);
/*  235: 333 */     GL11.glTexCoord2f((var8 + var10) / 256.0F, var9 / 256.0F);
/*  236: 334 */     GL11.glVertex3f(this.posX + var10 / 2.0F + var11, this.posY, 0.0F);
/*  237: 335 */     GL11.glTexCoord2f((var8 + var10) / 256.0F, (var9 + 15.98F) / 256.0F);
/*  238: 336 */     GL11.glVertex3f(this.posX + var10 / 2.0F - var11, this.posY + 7.99F, 0.0F);
/*  239: 337 */     GL11.glEnd();
/*  240: 338 */     return (var7 - var6) / 2.0F + 1.0F;
/*  241:     */   }
/*  242:     */   
/*  243:     */   public int drawStringWithShadow(String par1Str, int par2, int par3, int par4)
/*  244:     */   {
/*  245: 347 */     return drawString(par1Str, par2, par3, par4, true);
/*  246:     */   }
/*  247:     */   
/*  248:     */   public int drawString(String par1Str, int par2, int par3, int par4)
/*  249:     */   {
/*  250: 355 */     return !this.enabled ? 0 : drawString(par1Str, par2, par3, par4, false);
/*  251:     */   }
/*  252:     */   
/*  253:     */   public int drawString(String par1Str, int par2, int par3, int par4, boolean par5)
/*  254:     */   {
/*  255: 363 */     GL11.glEnable(3008);
/*  256: 364 */     resetStyles();
/*  257:     */     int var6;
/*  258: 367 */     if (par5)
/*  259:     */     {
/*  260: 369 */       int var6 = renderString(par1Str, par2 + 1, par3 + 1, par4, true);
/*  261: 370 */       var6 = Math.max(var6, renderString(par1Str, par2, par3, par4, false));
/*  262:     */     }
/*  263:     */     else
/*  264:     */     {
/*  265: 374 */       var6 = renderString(par1Str, par2, par3, par4, false);
/*  266:     */     }
/*  267: 377 */     return var6;
/*  268:     */   }
/*  269:     */   
/*  270:     */   private String func_147647_b(String p_147647_1_)
/*  271:     */   {
/*  272:     */     try
/*  273:     */     {
/*  274: 384 */       Bidi var3 = new Bidi(new ArabicShaping(8).shape(p_147647_1_), 127);
/*  275: 385 */       var3.setReorderingMode(0);
/*  276: 386 */       return var3.writeReordered(2);
/*  277:     */     }
/*  278:     */     catch (ArabicShapingException var31) {}
/*  279: 390 */     return p_147647_1_;
/*  280:     */   }
/*  281:     */   
/*  282:     */   private void resetStyles()
/*  283:     */   {
/*  284: 399 */     this.randomStyle = false;
/*  285: 400 */     this.boldStyle = false;
/*  286: 401 */     this.italicStyle = false;
/*  287: 402 */     this.underlineStyle = false;
/*  288: 403 */     this.strikethroughStyle = false;
/*  289:     */   }
/*  290:     */   
/*  291:     */   private void renderStringAtPos(String par1Str, boolean par2)
/*  292:     */   {
/*  293: 411 */     for (int var3 = 0; var3 < par1Str.length(); var3++)
/*  294:     */     {
/*  295: 413 */       char var4 = par1Str.charAt(var3);
/*  296: 417 */       if ((var4 == '§') && (var3 + 1 < par1Str.length()))
/*  297:     */       {
/*  298: 419 */         int var5 = "0123456789abcdefklmnor".indexOf(par1Str.toLowerCase().charAt(var3 + 1));
/*  299: 421 */         if (var5 < 16)
/*  300:     */         {
/*  301: 423 */           this.randomStyle = false;
/*  302: 424 */           this.boldStyle = false;
/*  303: 425 */           this.strikethroughStyle = false;
/*  304: 426 */           this.underlineStyle = false;
/*  305: 427 */           this.italicStyle = false;
/*  306: 429 */           if ((var5 < 0) || (var5 > 15)) {
/*  307: 431 */             var5 = 15;
/*  308:     */           }
/*  309: 434 */           if (par2) {
/*  310: 436 */             var5 += 16;
/*  311:     */           }
/*  312: 439 */           int var6 = this.colorCode[var5];
/*  313: 440 */           this.textColor = var6;
/*  314: 441 */           GL11.glColor4f((var6 >> 16) / 255.0F, (var6 >> 8 & 0xFF) / 255.0F, (var6 & 0xFF) / 255.0F, this.alpha);
/*  315:     */         }
/*  316: 443 */         else if (var5 == 16)
/*  317:     */         {
/*  318: 445 */           this.randomStyle = true;
/*  319:     */         }
/*  320: 447 */         else if (var5 == 17)
/*  321:     */         {
/*  322: 449 */           this.boldStyle = true;
/*  323:     */         }
/*  324: 451 */         else if (var5 == 18)
/*  325:     */         {
/*  326: 453 */           this.strikethroughStyle = true;
/*  327:     */         }
/*  328: 455 */         else if (var5 == 19)
/*  329:     */         {
/*  330: 457 */           this.underlineStyle = true;
/*  331:     */         }
/*  332: 459 */         else if (var5 == 20)
/*  333:     */         {
/*  334: 461 */           this.italicStyle = true;
/*  335:     */         }
/*  336: 463 */         else if (var5 == 21)
/*  337:     */         {
/*  338: 465 */           this.randomStyle = false;
/*  339: 466 */           this.boldStyle = false;
/*  340: 467 */           this.strikethroughStyle = false;
/*  341: 468 */           this.underlineStyle = false;
/*  342: 469 */           this.italicStyle = false;
/*  343: 470 */           GL11.glColor4f(this.red, this.blue, this.green, this.alpha);
/*  344:     */         }
/*  345: 473 */         var3++;
/*  346:     */       }
/*  347:     */       else
/*  348:     */       {
/*  349: 477 */         int var5 = "".indexOf(var4);
/*  350: 479 */         if ((this.randomStyle) && (var5 != -1))
/*  351:     */         {
/*  352:     */           int var6;
/*  353:     */           do
/*  354:     */           {
/*  355: 483 */             var6 = this.fontRandom.nextInt(this.charWidth.length);
/*  356: 485 */           } while ((int)this.charWidth[var5] != (int)this.charWidth[var6]);
/*  357: 487 */           var5 = var6;
/*  358:     */         }
/*  359: 490 */         float var11 = this.unicodeFlag ? 0.5F : 1.0F;
/*  360: 491 */         boolean var7 = ((var4 == 0) || (var5 == -1) || (this.unicodeFlag)) && (par2);
/*  361: 493 */         if (var7)
/*  362:     */         {
/*  363: 495 */           this.posX -= var11;
/*  364: 496 */           this.posY -= var11;
/*  365:     */         }
/*  366: 499 */         float var8 = renderCharAtPos(var5, var4, this.italicStyle);
/*  367: 501 */         if (var7)
/*  368:     */         {
/*  369: 503 */           this.posX += var11;
/*  370: 504 */           this.posY += var11;
/*  371:     */         }
/*  372: 507 */         if (this.boldStyle)
/*  373:     */         {
/*  374: 509 */           this.posX += var11;
/*  375: 511 */           if (var7)
/*  376:     */           {
/*  377: 513 */             this.posX -= var11;
/*  378: 514 */             this.posY -= var11;
/*  379:     */           }
/*  380: 517 */           renderCharAtPos(var5, var4, this.italicStyle);
/*  381: 518 */           this.posX -= var11;
/*  382: 520 */           if (var7)
/*  383:     */           {
/*  384: 522 */             this.posX += var11;
/*  385: 523 */             this.posY += var11;
/*  386:     */           }
/*  387: 526 */           var8 += 1.0F;
/*  388:     */         }
/*  389: 531 */         if (this.strikethroughStyle)
/*  390:     */         {
/*  391: 533 */           Tessellator var9 = Tessellator.instance;
/*  392: 534 */           GL11.glDisable(3553);
/*  393: 535 */           var9.startDrawingQuads();
/*  394: 536 */           var9.addVertex(this.posX, this.posY + this.FONT_HEIGHT / 2, 0.0D);
/*  395: 537 */           var9.addVertex(this.posX + var8, this.posY + this.FONT_HEIGHT / 2, 0.0D);
/*  396: 538 */           var9.addVertex(this.posX + var8, this.posY + this.FONT_HEIGHT / 2 - 1.0F, 0.0D);
/*  397: 539 */           var9.addVertex(this.posX, this.posY + this.FONT_HEIGHT / 2 - 1.0F, 0.0D);
/*  398: 540 */           var9.draw();
/*  399: 541 */           GL11.glEnable(3553);
/*  400:     */         }
/*  401: 544 */         if (this.underlineStyle)
/*  402:     */         {
/*  403: 546 */           Tessellator var9 = Tessellator.instance;
/*  404: 547 */           GL11.glDisable(3553);
/*  405: 548 */           var9.startDrawingQuads();
/*  406: 549 */           int var10 = this.underlineStyle ? -1 : 0;
/*  407: 550 */           var9.addVertex(this.posX + var10, this.posY + this.FONT_HEIGHT, 0.0D);
/*  408: 551 */           var9.addVertex(this.posX + var8, this.posY + this.FONT_HEIGHT, 0.0D);
/*  409: 552 */           var9.addVertex(this.posX + var8, this.posY + this.FONT_HEIGHT - 1.0F, 0.0D);
/*  410: 553 */           var9.addVertex(this.posX + var10, this.posY + this.FONT_HEIGHT - 1.0F, 0.0D);
/*  411: 554 */           var9.draw();
/*  412: 555 */           GL11.glEnable(3553);
/*  413:     */         }
/*  414: 558 */         this.posX += var8;
/*  415:     */       }
/*  416:     */     }
/*  417:     */   }
/*  418:     */   
/*  419:     */   private int renderStringAligned(String par1Str, int par2, int par3, int par4, int par5, boolean par6)
/*  420:     */   {
/*  421: 568 */     if (this.bidiFlag)
/*  422:     */     {
/*  423: 570 */       int var7 = getStringWidth(func_147647_b(par1Str));
/*  424: 571 */       par2 = par2 + par4 - var7;
/*  425:     */     }
/*  426: 574 */     return renderString(par1Str, par2, par3, par5, par6);
/*  427:     */   }
/*  428:     */   
/*  429:     */   private int renderString(String par1Str, int par2, int par3, int par4, boolean par5)
/*  430:     */   {
/*  431: 582 */     if (par1Str == null) {
/*  432: 584 */       return 0;
/*  433:     */     }
/*  434: 588 */     if (this.bidiFlag) {
/*  435: 590 */       par1Str = func_147647_b(par1Str);
/*  436:     */     }
/*  437: 593 */     if ((par4 & 0xFC000000) == 0) {
/*  438: 595 */       par4 |= 0xFF000000;
/*  439:     */     }
/*  440: 598 */     if (par5) {
/*  441: 600 */       par4 = (par4 & 0xFCFCFC) >> 2 | par4 & 0xFF000000;
/*  442:     */     }
/*  443: 603 */     this.red = ((par4 >> 16 & 0xFF) / 255.0F);
/*  444: 604 */     this.blue = ((par4 >> 8 & 0xFF) / 255.0F);
/*  445: 605 */     this.green = ((par4 & 0xFF) / 255.0F);
/*  446: 606 */     this.alpha = ((par4 >> 24 & 0xFF) / 255.0F);
/*  447: 607 */     GL11.glColor4f(this.red, this.blue, this.green, this.alpha);
/*  448: 608 */     this.posX = par2;
/*  449: 609 */     this.posY = par3;
/*  450: 610 */     renderStringAtPos(par1Str, par5);
/*  451: 611 */     return (int)this.posX;
/*  452:     */   }
/*  453:     */   
/*  454:     */   public int getStringWidth(String par1Str)
/*  455:     */   {
/*  456: 620 */     if (par1Str == null) {
/*  457: 622 */       return 0;
/*  458:     */     }
/*  459: 626 */     float var2 = 0.0F;
/*  460: 627 */     boolean var3 = false;
/*  461: 629 */     for (int var4 = 0; var4 < par1Str.length(); var4++)
/*  462:     */     {
/*  463: 631 */       char var5 = par1Str.charAt(var4);
/*  464: 632 */       float var6 = getCharWidthFloat(var5);
/*  465: 634 */       if ((var6 < 0.0F) && (var4 < par1Str.length() - 1))
/*  466:     */       {
/*  467: 636 */         var4++;
/*  468: 637 */         var5 = par1Str.charAt(var4);
/*  469: 639 */         if ((var5 != 'l') && (var5 != 'L'))
/*  470:     */         {
/*  471: 641 */           if ((var5 == 'r') || (var5 == 'R')) {
/*  472: 643 */             var3 = false;
/*  473:     */           }
/*  474:     */         }
/*  475:     */         else {
/*  476: 648 */           var3 = true;
/*  477:     */         }
/*  478: 651 */         var6 = 0.0F;
/*  479:     */       }
/*  480: 654 */       var2 += var6;
/*  481: 656 */       if (var3) {
/*  482: 658 */         var2 += 1.0F;
/*  483:     */       }
/*  484:     */     }
/*  485: 662 */     return (int)var2;
/*  486:     */   }
/*  487:     */   
/*  488:     */   public int getCharWidth(char par1)
/*  489:     */   {
/*  490: 671 */     return Math.round(getCharWidthFloat(par1));
/*  491:     */   }
/*  492:     */   
/*  493:     */   private float getCharWidthFloat(char par1)
/*  494:     */   {
/*  495: 676 */     if (par1 == '§') {
/*  496: 678 */       return -1.0F;
/*  497:     */     }
/*  498: 680 */     if (par1 == ' ') {
/*  499: 682 */       return this.charWidth[32];
/*  500:     */     }
/*  501: 686 */     int var2 = "".indexOf(par1);
/*  502: 688 */     if ((par1 > 0) && (var2 != -1) && (!this.unicodeFlag)) {
/*  503: 690 */       return this.charWidth[var2];
/*  504:     */     }
/*  505: 692 */     if (this.glyphWidth[par1] != 0)
/*  506:     */     {
/*  507: 694 */       int var3 = this.glyphWidth[par1] >>> 4;
/*  508: 695 */       int var4 = this.glyphWidth[par1] & 0xF;
/*  509: 697 */       if (var4 > 7)
/*  510:     */       {
/*  511: 699 */         var4 = 15;
/*  512: 700 */         var3 = 0;
/*  513:     */       }
/*  514: 703 */       var4++;
/*  515: 704 */       return (var4 - var3) / 2 + 1;
/*  516:     */     }
/*  517: 708 */     return 0.0F;
/*  518:     */   }
/*  519:     */   
/*  520:     */   public String trimStringToWidth(String par1Str, int par2)
/*  521:     */   {
/*  522: 718 */     return trimStringToWidth(par1Str, par2, false);
/*  523:     */   }
/*  524:     */   
/*  525:     */   public String trimStringToWidth(String par1Str, int par2, boolean par3)
/*  526:     */   {
/*  527: 726 */     StringBuilder var4 = new StringBuilder();
/*  528: 727 */     float var5 = 0.0F;
/*  529: 728 */     int var6 = par3 ? par1Str.length() - 1 : 0;
/*  530: 729 */     int var7 = par3 ? -1 : 1;
/*  531: 730 */     boolean var8 = false;
/*  532: 731 */     boolean var9 = false;
/*  533: 733 */     for (int var10 = var6; (var10 >= 0) && (var10 < par1Str.length()) && (var5 < par2); var10 += var7)
/*  534:     */     {
/*  535: 735 */       char var11 = par1Str.charAt(var10);
/*  536: 736 */       float var12 = getCharWidthFloat(var11);
/*  537: 738 */       if (var8)
/*  538:     */       {
/*  539: 740 */         var8 = false;
/*  540: 742 */         if ((var11 != 'l') && (var11 != 'L'))
/*  541:     */         {
/*  542: 744 */           if ((var11 == 'r') || (var11 == 'R')) {
/*  543: 746 */             var9 = false;
/*  544:     */           }
/*  545:     */         }
/*  546:     */         else {
/*  547: 751 */           var9 = true;
/*  548:     */         }
/*  549:     */       }
/*  550: 754 */       else if (var12 < 0.0F)
/*  551:     */       {
/*  552: 756 */         var8 = true;
/*  553:     */       }
/*  554:     */       else
/*  555:     */       {
/*  556: 760 */         var5 += var12;
/*  557: 762 */         if (var9) {
/*  558: 764 */           var5 += 1.0F;
/*  559:     */         }
/*  560:     */       }
/*  561: 768 */       if (var5 > par2) {
/*  562:     */         break;
/*  563:     */       }
/*  564: 773 */       if (par3) {
/*  565: 775 */         var4.insert(0, var11);
/*  566:     */       } else {
/*  567: 779 */         var4.append(var11);
/*  568:     */       }
/*  569:     */     }
/*  570: 783 */     return var4.toString();
/*  571:     */   }
/*  572:     */   
/*  573:     */   private String trimStringNewline(String par1Str)
/*  574:     */   {
/*  575: 791 */     while ((par1Str != null) && (par1Str.endsWith("\n"))) {
/*  576: 793 */       par1Str = par1Str.substring(0, par1Str.length() - 1);
/*  577:     */     }
/*  578: 796 */     return par1Str;
/*  579:     */   }
/*  580:     */   
/*  581:     */   public void drawSplitString(String par1Str, int par2, int par3, int par4, int par5)
/*  582:     */   {
/*  583: 804 */     resetStyles();
/*  584: 805 */     this.textColor = par5;
/*  585: 806 */     par1Str = trimStringNewline(par1Str);
/*  586: 807 */     renderSplitString(par1Str, par2, par3, par4, false);
/*  587:     */   }
/*  588:     */   
/*  589:     */   private void renderSplitString(String par1Str, int par2, int par3, int par4, boolean par5)
/*  590:     */   {
/*  591: 816 */     List var6 = listFormattedStringToWidth(par1Str, par4);
/*  592: 818 */     for (Iterator var7 = var6.iterator(); var7.hasNext(); par3 += this.FONT_HEIGHT)
/*  593:     */     {
/*  594: 820 */       String var8 = (String)var7.next();
/*  595: 821 */       renderStringAligned(var8, par2, par3, par4, this.textColor, par5);
/*  596:     */     }
/*  597:     */   }
/*  598:     */   
/*  599:     */   public int splitStringWidth(String par1Str, int par2)
/*  600:     */   {
/*  601: 830 */     return this.FONT_HEIGHT * listFormattedStringToWidth(par1Str, par2).size();
/*  602:     */   }
/*  603:     */   
/*  604:     */   public void setUnicodeFlag(boolean par1)
/*  605:     */   {
/*  606: 839 */     this.unicodeFlag = par1;
/*  607:     */   }
/*  608:     */   
/*  609:     */   public boolean getUnicodeFlag()
/*  610:     */   {
/*  611: 848 */     return this.unicodeFlag;
/*  612:     */   }
/*  613:     */   
/*  614:     */   public void setBidiFlag(boolean par1)
/*  615:     */   {
/*  616: 856 */     this.bidiFlag = par1;
/*  617:     */   }
/*  618:     */   
/*  619:     */   public List listFormattedStringToWidth(String par1Str, int par2)
/*  620:     */   {
/*  621: 864 */     return Arrays.asList(wrapFormattedStringToWidth(par1Str, par2).split("\n"));
/*  622:     */   }
/*  623:     */   
/*  624:     */   String wrapFormattedStringToWidth(String par1Str, int par2)
/*  625:     */   {
/*  626: 872 */     int var3 = sizeStringToWidth(par1Str, par2);
/*  627: 874 */     if (par1Str.length() <= var3) {
/*  628: 876 */       return par1Str;
/*  629:     */     }
/*  630: 880 */     String var4 = par1Str.substring(0, var3);
/*  631: 881 */     char var5 = par1Str.charAt(var3);
/*  632: 882 */     boolean var6 = (var5 == ' ') || (var5 == '\n');
/*  633: 883 */     String var7 = getFormatFromString(var4) + par1Str.substring(var3 + (var6 ? 1 : 0));
/*  634: 884 */     return var4 + "\n" + wrapFormattedStringToWidth(var7, par2);
/*  635:     */   }
/*  636:     */   
/*  637:     */   private int sizeStringToWidth(String par1Str, int par2)
/*  638:     */   {
/*  639: 893 */     int var3 = par1Str.length();
/*  640: 894 */     float var4 = 0.0F;
/*  641: 895 */     int var5 = 0;
/*  642: 896 */     int var6 = -1;
/*  643: 898 */     for (boolean var7 = false; var5 < var3; var5++)
/*  644:     */     {
/*  645: 900 */       char var8 = par1Str.charAt(var5);
/*  646: 902 */       switch (var8)
/*  647:     */       {
/*  648:     */       case '\n': 
/*  649: 905 */         var5--;
/*  650: 906 */         break;
/*  651:     */       case '§': 
/*  652: 909 */         if (var5 < var3 - 1)
/*  653:     */         {
/*  654: 911 */           var5++;
/*  655: 912 */           char var9 = par1Str.charAt(var5);
/*  656: 914 */           if ((var9 != 'l') && (var9 != 'L'))
/*  657:     */           {
/*  658: 916 */             if ((var9 == 'r') || (var9 == 'R') || (isFormatColor(var9))) {
/*  659: 918 */               var7 = false;
/*  660:     */             }
/*  661:     */           }
/*  662:     */           else {
/*  663: 923 */             var7 = true;
/*  664:     */           }
/*  665:     */         }
/*  666: 927 */         break;
/*  667:     */       case ' ': 
/*  668: 930 */         var6 = var5;
/*  669:     */       default: 
/*  670: 933 */         var4 += getCharWidthFloat(var8);
/*  671: 935 */         if (var7) {
/*  672: 937 */           var4 += 1.0F;
/*  673:     */         }
/*  674:     */         break;
/*  675:     */       }
/*  676: 941 */       if (var8 == '\n')
/*  677:     */       {
/*  678: 943 */         var5++;
/*  679: 944 */         var6 = var5;
/*  680:     */       }
/*  681:     */       else
/*  682:     */       {
/*  683: 948 */         if (var4 > par2) {
/*  684:     */           break;
/*  685:     */         }
/*  686:     */       }
/*  687:     */     }
/*  688: 954 */     return (var5 != var3) && (var6 != -1) && (var6 < var5) ? var6 : var5;
/*  689:     */   }
/*  690:     */   
/*  691:     */   private static boolean isFormatColor(char par0)
/*  692:     */   {
/*  693: 962 */     return ((par0 >= '0') && (par0 <= '9')) || ((par0 >= 'a') && (par0 <= 'f')) || ((par0 >= 'A') && (par0 <= 'F'));
/*  694:     */   }
/*  695:     */   
/*  696:     */   private static boolean isFormatSpecial(char par0)
/*  697:     */   {
/*  698: 970 */     return ((par0 >= 'k') && (par0 <= 'o')) || ((par0 >= 'K') && (par0 <= 'O')) || (par0 == 'r') || (par0 == 'R');
/*  699:     */   }
/*  700:     */   
/*  701:     */   private static String getFormatFromString(String par0Str)
/*  702:     */   {
/*  703: 978 */     String var1 = "";
/*  704: 979 */     int var2 = -1;
/*  705: 980 */     int var3 = par0Str.length();
/*  706: 982 */     while ((var2 = par0Str.indexOf('§', var2 + 1)) != -1) {
/*  707: 984 */       if (var2 < var3 - 1)
/*  708:     */       {
/*  709: 986 */         char var4 = par0Str.charAt(var2 + 1);
/*  710: 988 */         if (isFormatColor(var4)) {
/*  711: 990 */           var1 = "§" + var4;
/*  712: 992 */         } else if (isFormatSpecial(var4)) {
/*  713: 994 */           var1 = var1 + "§" + var4;
/*  714:     */         }
/*  715:     */       }
/*  716:     */     }
/*  717: 999 */     return var1;
/*  718:     */   }
/*  719:     */   
/*  720:     */   public boolean getBidiFlag()
/*  721:     */   {
/*  722:1007 */     return this.bidiFlag;
/*  723:     */   }
/*  724:     */   
/*  725:     */   private void readCustomCharWidths()
/*  726:     */   {
/*  727:1012 */     String fontFileName = this.locationFontTexture.getResourcePath();
/*  728:1013 */     String suffix = ".png";
/*  729:1015 */     if (fontFileName.endsWith(suffix))
/*  730:     */     {
/*  731:1017 */       String fileName = fontFileName.substring(0, fontFileName.length() - suffix.length()) + ".properties";
/*  732:     */       try
/*  733:     */       {
/*  734:1021 */         ResourceLocation e = new ResourceLocation(this.locationFontTexture.getResourceDomain(), fileName);
/*  735:1022 */         InputStream in = Config.getResourceStream(Config.getResourceManager(), e);
/*  736:1024 */         if (in == null) {
/*  737:1026 */           return;
/*  738:     */         }
/*  739:1029 */         Config.log("Loading " + fileName);
/*  740:1030 */         Properties props = new Properties();
/*  741:1031 */         props.load(in);
/*  742:1032 */         Set keySet = props.keySet();
/*  743:1033 */         Iterator iter = keySet.iterator();
/*  744:1035 */         while (iter.hasNext())
/*  745:     */         {
/*  746:1037 */           String key = (String)iter.next();
/*  747:1038 */           String prefix = "width.";
/*  748:1040 */           if (key.startsWith(prefix))
/*  749:     */           {
/*  750:1042 */             String numStr = key.substring(prefix.length());
/*  751:1043 */             int num = Config.parseInt(numStr, -1);
/*  752:1045 */             if ((num >= 0) && (num < this.charWidth.length))
/*  753:     */             {
/*  754:1047 */               String value = props.getProperty(key);
/*  755:1048 */               float width = Config.parseFloat(value, -1.0F);
/*  756:1050 */               if (width >= 0.0F) {
/*  757:1052 */                 this.charWidth[num] = width;
/*  758:     */               }
/*  759:     */             }
/*  760:     */           }
/*  761:     */         }
/*  762:     */       }
/*  763:     */       catch (FileNotFoundException localFileNotFoundException) {}catch (IOException var16)
/*  764:     */       {
/*  765:1064 */         var16.printStackTrace();
/*  766:     */       }
/*  767:     */     }
/*  768:     */   }
/*  769:     */   
/*  770:     */   private static ResourceLocation getHdFontLocation(ResourceLocation fontLoc)
/*  771:     */   {
/*  772:1071 */     if (!Config.isCustomFonts()) {
/*  773:1073 */       return fontLoc;
/*  774:     */     }
/*  775:1075 */     if (fontLoc == null) {
/*  776:1077 */       return fontLoc;
/*  777:     */     }
/*  778:1081 */     String fontName = fontLoc.getResourcePath();
/*  779:1082 */     String texturesStr = "textures/";
/*  780:1083 */     String mcpatcherStr = "mcpatcher/";
/*  781:1085 */     if (!fontName.startsWith(texturesStr)) {
/*  782:1087 */       return fontLoc;
/*  783:     */     }
/*  784:1091 */     fontName = fontName.substring(texturesStr.length());
/*  785:1092 */     fontName = mcpatcherStr + fontName;
/*  786:1093 */     ResourceLocation fontLocHD = new ResourceLocation(fontLoc.getResourceDomain(), fontName);
/*  787:1094 */     return Config.hasResource(Config.getResourceManager(), fontLocHD) ? fontLocHD : fontLoc;
/*  788:     */   }
/*  789:     */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.FontRenderer
 * JD-Core Version:    0.7.0.1
 */