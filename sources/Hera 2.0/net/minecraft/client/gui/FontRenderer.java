/*      */ package net.minecraft.client.gui;
/*      */ 
/*      */ import com.ibm.icu.text.ArabicShaping;
/*      */ import com.ibm.icu.text.ArabicShapingException;
/*      */ import com.ibm.icu.text.Bidi;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.util.Arrays;
/*      */ import java.util.List;
/*      */ import java.util.Properties;
/*      */ import java.util.Random;
/*      */ import me.eagler.Client;
/*      */ import me.eagler.gui.GuiChangeName;
/*      */ import me.eagler.gui.GuiFriends;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.Tessellator;
/*      */ import net.minecraft.client.renderer.WorldRenderer;
/*      */ import net.minecraft.client.renderer.texture.TextureManager;
/*      */ import net.minecraft.client.renderer.texture.TextureUtil;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.client.resources.IResourceManager;
/*      */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*      */ import net.minecraft.client.settings.GameSettings;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import optfine.Config;
/*      */ import org.apache.commons.io.IOUtils;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ 
/*      */ 
/*      */ 
/*      */ public class FontRenderer
/*      */   implements IResourceManagerReloadListener
/*      */ {
/*   37 */   private static final ResourceLocation[] unicodePageLocations = new ResourceLocation[256];
/*      */ 
/*      */   
/*   40 */   private float[] charWidth = new float[256];
/*      */ 
/*      */   
/*   43 */   public int FONT_HEIGHT = 9;
/*   44 */   public Random fontRandom = new Random();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   49 */   private byte[] glyphWidth = new byte[65536];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   55 */   private int[] colorCode = new int[32];
/*      */ 
/*      */   
/*      */   private ResourceLocation locationFontTexture;
/*      */ 
/*      */   
/*      */   private final TextureManager renderEngine;
/*      */ 
/*      */   
/*      */   private float posX;
/*      */ 
/*      */   
/*      */   private float posY;
/*      */ 
/*      */   
/*      */   private boolean unicodeFlag;
/*      */ 
/*      */   
/*      */   private boolean bidiFlag;
/*      */ 
/*      */   
/*      */   private float red;
/*      */ 
/*      */   
/*      */   private float blue;
/*      */ 
/*      */   
/*      */   private float green;
/*      */ 
/*      */   
/*      */   private float alpha;
/*      */ 
/*      */   
/*      */   private int textColor;
/*      */ 
/*      */   
/*      */   private boolean randomStyle;
/*      */ 
/*      */   
/*      */   private boolean boldStyle;
/*      */ 
/*      */   
/*      */   private boolean italicStyle;
/*      */ 
/*      */   
/*      */   private boolean underlineStyle;
/*      */ 
/*      */   
/*      */   private boolean strikethroughStyle;
/*      */ 
/*      */   
/*      */   private static final String __OBFID = "CL_00000660";
/*      */   
/*      */   public GameSettings gameSettings;
/*      */   
/*      */   public ResourceLocation locationFontTextureBase;
/*      */   
/*      */   public boolean enabled = true;
/*      */   
/*  114 */   public float scaleFactor = 1.0F;
/*      */ 
/*      */   
/*      */   public FontRenderer(GameSettings gameSettingsIn, ResourceLocation location, TextureManager textureManagerIn, boolean unicode) {
/*  118 */     this.gameSettings = gameSettingsIn;
/*  119 */     this.locationFontTextureBase = location;
/*  120 */     this.locationFontTexture = location;
/*  121 */     this.renderEngine = textureManagerIn;
/*  122 */     this.unicodeFlag = unicode;
/*  123 */     this.locationFontTexture = getHdFontLocation(this.locationFontTextureBase);
/*  124 */     textureManagerIn.bindTexture(this.locationFontTexture);
/*      */     
/*  126 */     for (int i = 0; i < 32; i++) {
/*      */       
/*  128 */       int j = (i >> 3 & 0x1) * 85;
/*  129 */       int k = (i >> 2 & 0x1) * 170 + j;
/*  130 */       int l = (i >> 1 & 0x1) * 170 + j;
/*  131 */       int i1 = (i >> 0 & 0x1) * 170 + j;
/*      */       
/*  133 */       if (i == 6)
/*      */       {
/*  135 */         k += 85;
/*      */       }
/*      */       
/*  138 */       if (gameSettingsIn.anaglyph) {
/*      */         
/*  140 */         int j1 = (k * 30 + l * 59 + i1 * 11) / 100;
/*  141 */         int k1 = (k * 30 + l * 70) / 100;
/*  142 */         int l1 = (k * 30 + i1 * 70) / 100;
/*  143 */         k = j1;
/*  144 */         l = k1;
/*  145 */         i1 = l1;
/*      */       } 
/*      */       
/*  148 */       if (i >= 16) {
/*      */         
/*  150 */         k /= 4;
/*  151 */         l /= 4;
/*  152 */         i1 /= 4;
/*      */       } 
/*      */       
/*  155 */       this.colorCode[i] = (k & 0xFF) << 16 | (l & 0xFF) << 8 | i1 & 0xFF;
/*      */     } 
/*      */     
/*  158 */     readGlyphSizes();
/*      */   }
/*      */ 
/*      */   
/*      */   public void onResourceManagerReload(IResourceManager resourceManager) {
/*  163 */     this.locationFontTexture = getHdFontLocation(this.locationFontTextureBase);
/*      */     
/*  165 */     for (int i = 0; i < unicodePageLocations.length; i++)
/*      */     {
/*  167 */       unicodePageLocations[i] = null;
/*      */     }
/*      */     
/*  170 */     readFontTexture();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readFontTexture() {
/*      */     BufferedImage bufferedimage;
/*      */     try {
/*  179 */       bufferedimage = TextureUtil.readBufferedImage(Minecraft.getMinecraft().getResourceManager().getResource(this.locationFontTexture).getInputStream());
/*      */     }
/*  181 */     catch (IOException ioexception) {
/*      */       
/*  183 */       throw new RuntimeException(ioexception);
/*      */     } 
/*      */     
/*  186 */     int i = bufferedimage.getWidth();
/*  187 */     int j = bufferedimage.getHeight();
/*  188 */     int k = i / 16;
/*  189 */     int l = j / 16;
/*  190 */     float f = i / 128.0F;
/*  191 */     this.scaleFactor = f;
/*  192 */     int[] aint = new int[i * j];
/*  193 */     bufferedimage.getRGB(0, 0, i, j, aint, 0, i);
/*      */     
/*  195 */     for (int i1 = 0; i1 < 256; i1++) {
/*      */       
/*  197 */       int j1 = i1 % 16;
/*  198 */       int k1 = i1 / 16;
/*  199 */       int l1 = 0;
/*      */       
/*  201 */       for (l1 = k - 1; l1 >= 0; l1--) {
/*      */         
/*  203 */         int i2 = j1 * k + l1;
/*  204 */         boolean flag = true;
/*      */         
/*  206 */         for (int j2 = 0; j2 < l && flag; j2++) {
/*      */           
/*  208 */           int k2 = (k1 * l + j2) * i;
/*  209 */           int l2 = aint[i2 + k2];
/*  210 */           int i3 = l2 >> 24 & 0xFF;
/*      */           
/*  212 */           if (i3 > 16)
/*      */           {
/*  214 */             flag = false;
/*      */           }
/*      */         } 
/*      */         
/*  218 */         if (!flag) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  224 */       if (i1 == 65)
/*      */       {
/*  226 */         i1 = i1;
/*      */       }
/*      */       
/*  229 */       if (i1 == 32)
/*      */       {
/*  231 */         if (k <= 8) {
/*      */           
/*  233 */           l1 = (int)(2.0F * f);
/*      */         }
/*      */         else {
/*      */           
/*  237 */           l1 = (int)(1.5F * f);
/*      */         } 
/*      */       }
/*      */       
/*  241 */       this.charWidth[i1] = (l1 + 1) / f + 1.0F;
/*      */     } 
/*      */     
/*  244 */     readCustomCharWidths();
/*      */   }
/*      */ 
/*      */   
/*      */   private void readGlyphSizes() {
/*  249 */     InputStream inputstream = null;
/*      */ 
/*      */     
/*      */     try {
/*  253 */       inputstream = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("font/glyph_sizes.bin")).getInputStream();
/*  254 */       inputstream.read(this.glyphWidth);
/*      */     }
/*  256 */     catch (IOException ioexception) {
/*      */       
/*  258 */       throw new RuntimeException(ioexception);
/*      */     }
/*      */     finally {
/*      */       
/*  262 */       IOUtils.closeQuietly(inputstream);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private float func_181559_a(char p_181559_1_, boolean p_181559_2_) {
/*  268 */     if (p_181559_1_ == ' ')
/*      */     {
/*  270 */       return this.charWidth[p_181559_1_];
/*      */     }
/*      */ 
/*      */     
/*  274 */     int i = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\000\000\000\000\000\000\000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\000".indexOf(p_181559_1_);
/*  275 */     return (i != -1 && !this.unicodeFlag) ? renderDefaultChar(i, p_181559_2_) : renderUnicodeChar(p_181559_1_, p_181559_2_);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float renderDefaultChar(int p_78266_1_, boolean p_78266_2_) {
/*  284 */     int i = p_78266_1_ % 16 * 8;
/*  285 */     int j = p_78266_1_ / 16 * 8;
/*  286 */     int k = p_78266_2_ ? 1 : 0;
/*  287 */     this.renderEngine.bindTexture(this.locationFontTexture);
/*  288 */     float f = this.charWidth[p_78266_1_];
/*  289 */     float f1 = 7.99F;
/*  290 */     GL11.glBegin(5);
/*  291 */     GL11.glTexCoord2f(i / 128.0F, j / 128.0F);
/*  292 */     GL11.glVertex3f(this.posX + k, this.posY, 0.0F);
/*  293 */     GL11.glTexCoord2f(i / 128.0F, (j + 7.99F) / 128.0F);
/*  294 */     GL11.glVertex3f(this.posX - k, this.posY + 7.99F, 0.0F);
/*  295 */     GL11.glTexCoord2f((i + f1 - 1.0F) / 128.0F, j / 128.0F);
/*  296 */     GL11.glVertex3f(this.posX + f1 - 1.0F + k, this.posY, 0.0F);
/*  297 */     GL11.glTexCoord2f((i + f1 - 1.0F) / 128.0F, (j + 7.99F) / 128.0F);
/*  298 */     GL11.glVertex3f(this.posX + f1 - 1.0F - k, this.posY + 7.99F, 0.0F);
/*  299 */     GL11.glEnd();
/*  300 */     return f;
/*      */   }
/*      */ 
/*      */   
/*      */   private ResourceLocation getUnicodePageLocation(int p_111271_1_) {
/*  305 */     if (unicodePageLocations[p_111271_1_] == null) {
/*      */       
/*  307 */       unicodePageLocations[p_111271_1_] = new ResourceLocation(String.format("textures/font/unicode_page_%02x.png", new Object[] { Integer.valueOf(p_111271_1_) }));
/*  308 */       unicodePageLocations[p_111271_1_] = getHdFontLocation(unicodePageLocations[p_111271_1_]);
/*      */     } 
/*      */     
/*  311 */     return unicodePageLocations[p_111271_1_];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void loadGlyphTexture(int p_78257_1_) {
/*  319 */     this.renderEngine.bindTexture(getUnicodePageLocation(p_78257_1_));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float renderUnicodeChar(char p_78277_1_, boolean p_78277_2_) {
/*  327 */     if (this.glyphWidth[p_78277_1_] == 0)
/*      */     {
/*  329 */       return 0.0F;
/*      */     }
/*      */ 
/*      */     
/*  333 */     int i = p_78277_1_ / 256;
/*  334 */     loadGlyphTexture(i);
/*  335 */     int j = this.glyphWidth[p_78277_1_] >>> 4;
/*  336 */     int k = this.glyphWidth[p_78277_1_] & 0xF;
/*  337 */     float f = j;
/*  338 */     float f1 = (k + 1);
/*  339 */     float f2 = (p_78277_1_ % 16 * 16) + f;
/*  340 */     float f3 = ((p_78277_1_ & 0xFF) / 16 * 16);
/*  341 */     float f4 = f1 - f - 0.02F;
/*  342 */     float f5 = p_78277_2_ ? 1.0F : 0.0F;
/*  343 */     GL11.glBegin(5);
/*  344 */     GL11.glTexCoord2f(f2 / 256.0F, f3 / 256.0F);
/*  345 */     GL11.glVertex3f(this.posX + f5, this.posY, 0.0F);
/*  346 */     GL11.glTexCoord2f(f2 / 256.0F, (f3 + 15.98F) / 256.0F);
/*  347 */     GL11.glVertex3f(this.posX - f5, this.posY + 7.99F, 0.0F);
/*  348 */     GL11.glTexCoord2f((f2 + f4) / 256.0F, f3 / 256.0F);
/*  349 */     GL11.glVertex3f(this.posX + f4 / 2.0F + f5, this.posY, 0.0F);
/*  350 */     GL11.glTexCoord2f((f2 + f4) / 256.0F, (f3 + 15.98F) / 256.0F);
/*  351 */     GL11.glVertex3f(this.posX + f4 / 2.0F - f5, this.posY + 7.99F, 0.0F);
/*  352 */     GL11.glEnd();
/*  353 */     return (f1 - f) / 2.0F + 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int drawStringWithShadow(String text, float x, float y, int color) {
/*  362 */     return drawString(text, x, y, color, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int drawString(String text, int x, int y, int color) {
/*  370 */     return !this.enabled ? 0 : drawString(text, x, y, color, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int drawString(String text, float x, float y, int color, boolean dropShadow) {
/*      */     int i;
/*  378 */     GlStateManager.enableAlpha();
/*  379 */     resetStyles();
/*      */ 
/*      */     
/*  382 */     if (dropShadow) {
/*      */       
/*  384 */       i = renderString(text, x + 1.0F, y + 1.0F, color, true);
/*  385 */       i = Math.max(i, renderString(text, x, y, color, false));
/*      */     }
/*      */     else {
/*      */       
/*  389 */       i = renderString(text, x, y, color, false);
/*      */     } 
/*      */     
/*  392 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String bidiReorder(String p_147647_1_) {
/*      */     try {
/*  402 */       Bidi bidi = new Bidi((new ArabicShaping(8)).shape(p_147647_1_), 127);
/*  403 */       bidi.setReorderingMode(0);
/*  404 */       return bidi.writeReordered(2);
/*      */     }
/*  406 */     catch (ArabicShapingException var3) {
/*      */       
/*  408 */       return p_147647_1_;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void resetStyles() {
/*  417 */     this.randomStyle = false;
/*  418 */     this.boldStyle = false;
/*  419 */     this.italicStyle = false;
/*  420 */     this.underlineStyle = false;
/*  421 */     this.strikethroughStyle = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderStringAtPos(String p_78255_1_, boolean p_78255_2_) {
/*  429 */     for (int i = 0; i < p_78255_1_.length(); i++) {
/*      */       
/*  431 */       char c0 = p_78255_1_.charAt(i);
/*      */       
/*  433 */       if (c0 == '§' && i + 1 < p_78255_1_.length()) {
/*      */         
/*  435 */         int i1 = "0123456789abcdefklmnor".indexOf(p_78255_1_.toLowerCase().charAt(i + 1));
/*      */         
/*  437 */         if (i1 < 16) {
/*      */           
/*  439 */           this.randomStyle = false;
/*  440 */           this.boldStyle = false;
/*  441 */           this.strikethroughStyle = false;
/*  442 */           this.underlineStyle = false;
/*  443 */           this.italicStyle = false;
/*      */           
/*  445 */           if (i1 < 0 || i1 > 15)
/*      */           {
/*  447 */             i1 = 15;
/*      */           }
/*      */           
/*  450 */           if (p_78255_2_)
/*      */           {
/*  452 */             i1 += 16;
/*      */           }
/*      */           
/*  455 */           int j1 = this.colorCode[i1];
/*  456 */           this.textColor = j1;
/*  457 */           GlStateManager.color((j1 >> 16) / 255.0F, (j1 >> 8 & 0xFF) / 255.0F, (j1 & 0xFF) / 255.0F, this.alpha);
/*      */         }
/*  459 */         else if (i1 == 16) {
/*      */           
/*  461 */           this.randomStyle = true;
/*      */         }
/*  463 */         else if (i1 == 17) {
/*      */           
/*  465 */           this.boldStyle = true;
/*      */         }
/*  467 */         else if (i1 == 18) {
/*      */           
/*  469 */           this.strikethroughStyle = true;
/*      */         }
/*  471 */         else if (i1 == 19) {
/*      */           
/*  473 */           this.underlineStyle = true;
/*      */         }
/*  475 */         else if (i1 == 20) {
/*      */           
/*  477 */           this.italicStyle = true;
/*      */         }
/*  479 */         else if (i1 == 21) {
/*      */           
/*  481 */           this.randomStyle = false;
/*  482 */           this.boldStyle = false;
/*  483 */           this.strikethroughStyle = false;
/*  484 */           this.underlineStyle = false;
/*  485 */           this.italicStyle = false;
/*  486 */           GlStateManager.color(this.red, this.blue, this.green, this.alpha);
/*      */         } 
/*      */         
/*  489 */         i++;
/*      */       }
/*      */       else {
/*      */         
/*  493 */         int j = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\000\000\000\000\000\000\000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\000".indexOf(c0);
/*      */         
/*  495 */         if (this.randomStyle && j != -1) {
/*      */           char c1;
/*  497 */           int k = getCharWidth(c0);
/*      */ 
/*      */ 
/*      */           
/*      */           do {
/*  502 */             j = this.fontRandom.nextInt("ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\000\000\000\000\000\000\000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\000".length());
/*  503 */             c1 = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\000\000\000\000\000\000\000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\000".charAt(j);
/*      */           }
/*  505 */           while (k != getCharWidth(c1));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  511 */           c0 = c1;
/*      */         } 
/*      */         
/*  514 */         float f1 = this.unicodeFlag ? 0.5F : (1.0F / this.scaleFactor);
/*  515 */         boolean flag = ((c0 == '\000' || j == -1 || this.unicodeFlag) && p_78255_2_);
/*      */         
/*  517 */         if (flag) {
/*      */           
/*  519 */           this.posX -= f1;
/*  520 */           this.posY -= f1;
/*      */         } 
/*      */         
/*  523 */         float f = func_181559_a(c0, this.italicStyle);
/*      */         
/*  525 */         if (flag) {
/*      */           
/*  527 */           this.posX += f1;
/*  528 */           this.posY += f1;
/*      */         } 
/*      */         
/*  531 */         if (this.boldStyle) {
/*      */           
/*  533 */           this.posX += f1;
/*      */           
/*  535 */           if (flag) {
/*      */             
/*  537 */             this.posX -= f1;
/*  538 */             this.posY -= f1;
/*      */           } 
/*      */           
/*  541 */           func_181559_a(c0, this.italicStyle);
/*  542 */           this.posX -= f1;
/*      */           
/*  544 */           if (flag) {
/*      */             
/*  546 */             this.posX += f1;
/*  547 */             this.posY += f1;
/*      */           } 
/*      */           
/*  550 */           f += f1;
/*      */         } 
/*      */         
/*  553 */         if (this.strikethroughStyle) {
/*      */           
/*  555 */           Tessellator tessellator = Tessellator.getInstance();
/*  556 */           WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  557 */           GlStateManager.disableTexture2D();
/*  558 */           worldrenderer.begin(7, DefaultVertexFormats.POSITION);
/*  559 */           worldrenderer.pos(this.posX, (this.posY + (this.FONT_HEIGHT / 2)), 0.0D).endVertex();
/*  560 */           worldrenderer.pos((this.posX + f), (this.posY + (this.FONT_HEIGHT / 2)), 0.0D).endVertex();
/*  561 */           worldrenderer.pos((this.posX + f), (this.posY + (this.FONT_HEIGHT / 2) - 1.0F), 0.0D).endVertex();
/*  562 */           worldrenderer.pos(this.posX, (this.posY + (this.FONT_HEIGHT / 2) - 1.0F), 0.0D).endVertex();
/*  563 */           tessellator.draw();
/*  564 */           GlStateManager.enableTexture2D();
/*      */         } 
/*      */         
/*  567 */         if (this.underlineStyle) {
/*      */           
/*  569 */           Tessellator tessellator1 = Tessellator.getInstance();
/*  570 */           WorldRenderer worldrenderer1 = tessellator1.getWorldRenderer();
/*  571 */           GlStateManager.disableTexture2D();
/*  572 */           worldrenderer1.begin(7, DefaultVertexFormats.POSITION);
/*  573 */           int l = this.underlineStyle ? -1 : 0;
/*  574 */           worldrenderer1.pos((this.posX + l), (this.posY + this.FONT_HEIGHT), 0.0D).endVertex();
/*  575 */           worldrenderer1.pos((this.posX + f), (this.posY + this.FONT_HEIGHT), 0.0D).endVertex();
/*  576 */           worldrenderer1.pos((this.posX + f), (this.posY + this.FONT_HEIGHT - 1.0F), 0.0D).endVertex();
/*  577 */           worldrenderer1.pos((this.posX + l), (this.posY + this.FONT_HEIGHT - 1.0F), 0.0D).endVertex();
/*  578 */           tessellator1.draw();
/*  579 */           GlStateManager.enableTexture2D();
/*      */         } 
/*      */         
/*  582 */         this.posX += f;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int renderStringAligned(String text, int x, int y, int p_78274_4_, int color, boolean dropShadow) {
/*  592 */     if (this.bidiFlag) {
/*      */       
/*  594 */       int i = getStringWidth(bidiReorder(text));
/*  595 */       x = x + p_78274_4_ - i;
/*      */     } 
/*      */     
/*  598 */     return renderString(text, x, y, color, dropShadow);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int renderString(String text, float x, float y, int color, boolean dropShadow) {
/*  607 */     if (Client.instance.getModuleManager().getModuleByName("NameChange").isEnabled())
/*      */     {
/*  609 */       if ((Client.instance.getMc()).thePlayer != null && (Client.instance.getMc()).theWorld != null)
/*      */       {
/*  611 */         if (text.contains((Client.instance.getMc()).thePlayer.getName()))
/*      */         {
/*  613 */           text = text.replaceAll((Client.instance.getMc()).thePlayer.getName(), GuiChangeName.name);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  621 */     if (GuiFriends.friends.size() != 0)
/*      */     {
/*  623 */       for (int i = 0; i < GuiFriends.friends.size(); i++) {
/*      */         
/*  625 */         if (text.contains(GuiFriends.friends.get(i)))
/*      */         {
/*  627 */           if (GuiFriends.friendsnick.containsKey(GuiFriends.friends.get(i))) {
/*      */             
/*  629 */             String nick = (String)GuiFriends.friendsnick.get(GuiFriends.friends.get(i));
/*      */             
/*  631 */             text = text.replaceAll(GuiFriends.friends.get(i), nick);
/*      */           } 
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  641 */     if (text == null)
/*      */     {
/*  643 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*  647 */     if (this.bidiFlag)
/*      */     {
/*  649 */       text = bidiReorder(text);
/*      */     }
/*      */     
/*  652 */     if ((color & 0xFC000000) == 0)
/*      */     {
/*  654 */       color |= 0xFF000000;
/*      */     }
/*      */     
/*  657 */     if (dropShadow)
/*      */     {
/*  659 */       color = (color & 0xFCFCFC) >> 2 | color & 0xFF000000;
/*      */     }
/*      */     
/*  662 */     this.red = (color >> 16 & 0xFF) / 255.0F;
/*  663 */     this.blue = (color >> 8 & 0xFF) / 255.0F;
/*  664 */     this.green = (color & 0xFF) / 255.0F;
/*  665 */     this.alpha = (color >> 24 & 0xFF) / 255.0F;
/*  666 */     GlStateManager.color(this.red, this.blue, this.green, this.alpha);
/*  667 */     this.posX = x;
/*  668 */     this.posY = y;
/*  669 */     renderStringAtPos(text, dropShadow);
/*  670 */     return (int)this.posX;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getStringWidth(String text) {
/*  679 */     if (text == null)
/*      */     {
/*  681 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*  685 */     float f = 0.0F;
/*  686 */     boolean flag = false;
/*      */     
/*  688 */     for (int i = 0; i < text.length(); i++) {
/*      */       
/*  690 */       char c0 = text.charAt(i);
/*  691 */       float f1 = getCharWidthFloat(c0);
/*      */       
/*  693 */       if (f1 < 0.0F && i < text.length() - 1) {
/*      */         
/*  695 */         i++;
/*  696 */         c0 = text.charAt(i);
/*      */         
/*  698 */         if (c0 != 'l' && c0 != 'L') {
/*      */           
/*  700 */           if (c0 == 'r' || c0 == 'R')
/*      */           {
/*  702 */             flag = false;
/*      */           }
/*      */         }
/*      */         else {
/*      */           
/*  707 */           flag = true;
/*      */         } 
/*      */         
/*  710 */         f1 = 0.0F;
/*      */       } 
/*      */       
/*  713 */       f += f1;
/*      */       
/*  715 */       if (flag && f1 > 0.0F)
/*      */       {
/*  717 */         f++;
/*      */       }
/*      */     } 
/*      */     
/*  721 */     return (int)f;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getCharWidth(char character) {
/*  730 */     return Math.round(getCharWidthFloat(character));
/*      */   }
/*      */ 
/*      */   
/*      */   private float getCharWidthFloat(char p_getCharWidthFloat_1_) {
/*  735 */     if (p_getCharWidthFloat_1_ == '§')
/*      */     {
/*  737 */       return -1.0F;
/*      */     }
/*  739 */     if (p_getCharWidthFloat_1_ == ' ')
/*      */     {
/*  741 */       return this.charWidth[32];
/*      */     }
/*      */ 
/*      */     
/*  745 */     int i = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\000\000\000\000\000\000\000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\000".indexOf(p_getCharWidthFloat_1_);
/*      */     
/*  747 */     if (p_getCharWidthFloat_1_ > '\000' && i != -1 && !this.unicodeFlag)
/*      */     {
/*  749 */       return this.charWidth[i];
/*      */     }
/*  751 */     if (this.glyphWidth[p_getCharWidthFloat_1_] != 0) {
/*      */       
/*  753 */       int j = this.glyphWidth[p_getCharWidthFloat_1_] >>> 4;
/*  754 */       int k = this.glyphWidth[p_getCharWidthFloat_1_] & 0xF;
/*      */       
/*  756 */       if (k > 7) {
/*      */         
/*  758 */         k = 15;
/*  759 */         j = 0;
/*      */       } 
/*      */       
/*  762 */       k++;
/*  763 */       return ((k - j) / 2 + 1);
/*      */     } 
/*      */ 
/*      */     
/*  767 */     return 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String trimStringToWidth(String text, int width) {
/*  777 */     return trimStringToWidth(text, width, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String trimStringToWidth(String text, int width, boolean reverse) {
/*  785 */     StringBuilder stringbuilder = new StringBuilder();
/*  786 */     float f = 0.0F;
/*  787 */     int i = reverse ? (text.length() - 1) : 0;
/*  788 */     int j = reverse ? -1 : 1;
/*  789 */     boolean flag = false;
/*  790 */     boolean flag1 = false;
/*      */     
/*  792 */     for (int k = i; k >= 0 && k < text.length() && f < width; k += j) {
/*      */       
/*  794 */       char c0 = text.charAt(k);
/*  795 */       float f1 = getCharWidthFloat(c0);
/*      */       
/*  797 */       if (flag) {
/*      */         
/*  799 */         flag = false;
/*      */         
/*  801 */         if (c0 != 'l' && c0 != 'L')
/*      */         {
/*  803 */           if (c0 == 'r' || c0 == 'R')
/*      */           {
/*  805 */             flag1 = false;
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  810 */           flag1 = true;
/*      */         }
/*      */       
/*  813 */       } else if (f1 < 0.0F) {
/*      */         
/*  815 */         flag = true;
/*      */       }
/*      */       else {
/*      */         
/*  819 */         f += f1;
/*      */         
/*  821 */         if (flag1)
/*      */         {
/*  823 */           f++;
/*      */         }
/*      */       } 
/*      */       
/*  827 */       if (f > width) {
/*      */         break;
/*      */       }
/*      */ 
/*      */       
/*  832 */       if (reverse) {
/*      */         
/*  834 */         stringbuilder.insert(0, c0);
/*      */       }
/*      */       else {
/*      */         
/*  838 */         stringbuilder.append(c0);
/*      */       } 
/*      */     } 
/*      */     
/*  842 */     return stringbuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String trimStringNewline(String text) {
/*  850 */     while (text != null && text.endsWith("\n"))
/*      */     {
/*  852 */       text = text.substring(0, text.length() - 1);
/*      */     }
/*      */     
/*  855 */     return text;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawSplitString(String str, int x, int y, int wrapWidth, int textColor) {
/*  863 */     resetStyles();
/*  864 */     this.textColor = textColor;
/*  865 */     str = trimStringNewline(str);
/*  866 */     renderSplitString(str, x, y, wrapWidth, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderSplitString(String str, int x, int y, int wrapWidth, boolean addShadow) {
/*  875 */     for (Object s : listFormattedStringToWidth(str, wrapWidth)) {
/*      */       
/*  877 */       renderStringAligned((String)s, x, y, wrapWidth, this.textColor, addShadow);
/*  878 */       y += this.FONT_HEIGHT;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int splitStringWidth(String p_78267_1_, int p_78267_2_) {
/*  887 */     return this.FONT_HEIGHT * listFormattedStringToWidth(p_78267_1_, p_78267_2_).size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUnicodeFlag(boolean unicodeFlagIn) {
/*  896 */     this.unicodeFlag = unicodeFlagIn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUnicodeFlag() {
/*  905 */     return this.unicodeFlag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBidiFlag(boolean bidiFlagIn) {
/*  913 */     this.bidiFlag = bidiFlagIn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List listFormattedStringToWidth(String str, int wrapWidth) {
/*  921 */     return Arrays.asList(wrapFormattedStringToWidth(str, wrapWidth).split("\n"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String wrapFormattedStringToWidth(String str, int wrapWidth) {
/*  929 */     int i = sizeStringToWidth(str, wrapWidth);
/*      */     
/*  931 */     if (str.length() <= i)
/*      */     {
/*  933 */       return str;
/*      */     }
/*      */ 
/*      */     
/*  937 */     String s = str.substring(0, i);
/*  938 */     char c0 = str.charAt(i);
/*  939 */     boolean flag = !(c0 != ' ' && c0 != '\n');
/*  940 */     String s1 = String.valueOf(getFormatFromString(s)) + str.substring(i + (flag ? 1 : 0));
/*  941 */     return String.valueOf(s) + "\n" + wrapFormattedStringToWidth(s1, wrapWidth);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int sizeStringToWidth(String str, int wrapWidth) {
/*  950 */     int i = str.length();
/*  951 */     float f = 0.0F;
/*  952 */     int j = 0;
/*  953 */     int k = -1;
/*      */     
/*  955 */     for (boolean flag = false; j < i; j++) {
/*      */       
/*  957 */       char c0 = str.charAt(j);
/*      */       
/*  959 */       switch (c0) {
/*      */         
/*      */         case '\n':
/*  962 */           j--;
/*      */           break;
/*      */         
/*      */         case ' ':
/*  966 */           k = j;
/*      */         
/*      */         default:
/*  969 */           f += getCharWidthFloat(c0);
/*      */           
/*  971 */           if (flag)
/*      */           {
/*  973 */             f++;
/*      */           }
/*      */           break;
/*      */ 
/*      */         
/*      */         case '§':
/*  979 */           if (j < i - 1) {
/*      */             
/*  981 */             j++;
/*  982 */             char c1 = str.charAt(j);
/*      */             
/*  984 */             if (c1 != 'l' && c1 != 'L') {
/*      */               
/*  986 */               if (c1 == 'r' || c1 == 'R' || isFormatColor(c1))
/*      */               {
/*  988 */                 flag = false;
/*      */               }
/*      */               
/*      */               break;
/*      */             } 
/*  993 */             flag = true;
/*      */           } 
/*      */           break;
/*      */       } 
/*      */       
/*  998 */       if (c0 == '\n') {
/*      */ 
/*      */         
/* 1001 */         k = ++j;
/*      */         
/*      */         break;
/*      */       } 
/* 1005 */       if (f > wrapWidth) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1011 */     return (j != i && k != -1 && k < j) ? k : j;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isFormatColor(char colorChar) {
/* 1019 */     return !((colorChar < '0' || colorChar > '9') && (colorChar < 'a' || colorChar > 'f') && (colorChar < 'A' || colorChar > 'F'));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isFormatSpecial(char formatChar) {
/* 1027 */     return !((formatChar < 'k' || formatChar > 'o') && (formatChar < 'K' || formatChar > 'O') && formatChar != 'r' && formatChar != 'R');
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getFormatFromString(String text) {
/* 1035 */     String s = "";
/* 1036 */     int i = -1;
/* 1037 */     int j = text.length();
/*      */     
/* 1039 */     while ((i = text.indexOf('§', i + 1)) != -1) {
/*      */       
/* 1041 */       if (i < j - 1) {
/*      */         
/* 1043 */         char c0 = text.charAt(i + 1);
/*      */         
/* 1045 */         if (isFormatColor(c0)) {
/*      */           
/* 1047 */           s = "§" + c0; continue;
/*      */         } 
/* 1049 */         if (isFormatSpecial(c0))
/*      */         {
/* 1051 */           s = String.valueOf(s) + "§" + c0;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1056 */     return s;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getBidiFlag() {
/* 1064 */     return this.bidiFlag;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getColorCode(char character) {
/* 1069 */     int i = "0123456789abcdef".indexOf(character);
/* 1070 */     return (i >= 0 && i < this.colorCode.length) ? this.colorCode[i] : 16777215;
/*      */   }
/*      */ 
/*      */   
/*      */   private void readCustomCharWidths() {
/* 1075 */     String s = this.locationFontTexture.getResourcePath();
/* 1076 */     String s1 = ".png";
/*      */     
/* 1078 */     if (s.endsWith(s1)) {
/*      */       
/* 1080 */       String s2 = String.valueOf(s.substring(0, s.length() - s1.length())) + ".properties";
/*      */ 
/*      */       
/*      */       try {
/* 1084 */         ResourceLocation resourcelocation = new ResourceLocation(this.locationFontTexture.getResourceDomain(), s2);
/* 1085 */         InputStream inputstream = Config.getResourceStream(Config.getResourceManager(), resourcelocation);
/*      */         
/* 1087 */         if (inputstream == null) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/* 1092 */         Config.log("Loading " + s2);
/* 1093 */         Properties properties = new Properties();
/* 1094 */         properties.load(inputstream);
/*      */         
/* 1096 */         for (Object s30 : properties.keySet()) {
/*      */           
/* 1098 */           String s3 = (String)s30;
/* 1099 */           String s4 = "width.";
/*      */           
/* 1101 */           if (s3.startsWith(s4)) {
/*      */             
/* 1103 */             String s5 = s3.substring(s4.length());
/* 1104 */             int i = Config.parseInt(s5, -1);
/*      */             
/* 1106 */             if (i >= 0 && i < this.charWidth.length)
/*      */             {
/* 1108 */               String s6 = properties.getProperty(s3);
/* 1109 */               float f = Config.parseFloat(s6, -1.0F);
/*      */               
/* 1111 */               if (f >= 0.0F)
/*      */               {
/* 1113 */                 this.charWidth[i] = f;
/*      */               }
/*      */             }
/*      */           
/*      */           } 
/*      */         } 
/* 1119 */       } catch (FileNotFoundException fileNotFoundException) {
/*      */ 
/*      */       
/*      */       }
/* 1123 */       catch (IOException ioexception) {
/*      */         
/* 1125 */         ioexception.printStackTrace();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static ResourceLocation getHdFontLocation(ResourceLocation p_getHdFontLocation_0_) {
/* 1132 */     if (!Config.isCustomFonts())
/*      */     {
/* 1134 */       return p_getHdFontLocation_0_;
/*      */     }
/* 1136 */     if (p_getHdFontLocation_0_ == null)
/*      */     {
/* 1138 */       return p_getHdFontLocation_0_;
/*      */     }
/*      */ 
/*      */     
/* 1142 */     String s = p_getHdFontLocation_0_.getResourcePath();
/* 1143 */     String s1 = "textures/";
/* 1144 */     String s2 = "mcpatcher/";
/*      */     
/* 1146 */     if (!s.startsWith(s1))
/*      */     {
/* 1148 */       return p_getHdFontLocation_0_;
/*      */     }
/*      */ 
/*      */     
/* 1152 */     s = s.substring(s1.length());
/* 1153 */     s = String.valueOf(s2) + s;
/* 1154 */     ResourceLocation resourcelocation = new ResourceLocation(p_getHdFontLocation_0_.getResourceDomain(), s);
/* 1155 */     return Config.hasResource(Config.getResourceManager(), resourcelocation) ? resourcelocation : p_getHdFontLocation_0_;
/*      */   }
/*      */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\FontRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */