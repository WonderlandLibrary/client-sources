/*     */ package org.newdawn.slick;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.awt.FontFormatException;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.font.GlyphVector;
/*     */ import java.awt.font.TextAttribute;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.newdawn.slick.font.Glyph;
/*     */ import org.newdawn.slick.font.GlyphPage;
/*     */ import org.newdawn.slick.font.HieroSettings;
/*     */ import org.newdawn.slick.opengl.Texture;
/*     */ import org.newdawn.slick.opengl.TextureImpl;
/*     */ import org.newdawn.slick.opengl.renderer.Renderer;
/*     */ import org.newdawn.slick.opengl.renderer.SGL;
/*     */ import org.newdawn.slick.util.ResourceLoader;
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
/*     */ public class UnicodeFont
/*     */   implements Font
/*     */ {
/*     */   private static final int DISPLAY_LIST_CACHE_SIZE = 200;
/*     */   private static final int MAX_GLYPH_CODE = 1114111;
/*     */   private static final int PAGE_SIZE = 512;
/*     */   private static final int PAGES = 2175;
/*  47 */   private static final SGL GL = Renderer.get();
/*     */   
/*  49 */   private static final DisplayList EMPTY_DISPLAY_LIST = new DisplayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Font createFont(String ttfFileRef) throws SlickException {
/*     */     try {
/*  61 */       return Font.createFont(0, ResourceLoader.getResourceAsStream(ttfFileRef));
/*  62 */     } catch (FontFormatException ex) {
/*  63 */       throw new SlickException("Invalid font: " + ttfFileRef, ex);
/*  64 */     } catch (IOException ex) {
/*  65 */       throw new SlickException("Error reading font: " + ttfFileRef, ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   private static final Comparator heightComparator = new Comparator() {
/*     */       public int compare(Object o1, Object o2) {
/*  74 */         return ((Glyph)o1).getHeight() - ((Glyph)o2).getHeight();
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   private Font font;
/*     */   
/*     */   private String ttfFileRef;
/*     */   
/*     */   private int ascent;
/*     */   
/*     */   private int descent;
/*     */   
/*     */   private int leading;
/*     */   
/*     */   private int spaceWidth;
/*     */   
/*  91 */   private final Glyph[][] glyphs = new Glyph[2175][];
/*     */   
/*  93 */   private final List glyphPages = new ArrayList();
/*     */   
/*  95 */   private final List queuedGlyphs = new ArrayList(256);
/*     */   
/*  97 */   private final List effects = new ArrayList();
/*     */ 
/*     */   
/*     */   private int paddingTop;
/*     */ 
/*     */   
/*     */   private int paddingLeft;
/*     */   
/*     */   private int paddingBottom;
/*     */   
/*     */   private int paddingRight;
/*     */   
/*     */   private int paddingAdvanceX;
/*     */   
/*     */   private int paddingAdvanceY;
/*     */   
/*     */   private Glyph missingGlyph;
/*     */   
/* 115 */   private int glyphPageWidth = 512;
/*     */   
/* 117 */   private int glyphPageHeight = 512;
/*     */ 
/*     */   
/*     */   private boolean displayListCaching = true;
/*     */   
/* 122 */   private int baseDisplayListID = -1;
/*     */   
/*     */   private int eldestDisplayListID;
/*     */   
/*     */   private DisplayList eldestDisplayList;
/*     */ 
/*     */   
/* 129 */   private final LinkedHashMap displayLists = new LinkedHashMap<Object, Object>(200, 1.0F, true) {
/*     */       protected boolean removeEldestEntry(Map.Entry eldest) {
/* 131 */         UnicodeFont.DisplayList displayList = (UnicodeFont.DisplayList)eldest.getValue();
/* 132 */         if (displayList != null) UnicodeFont.this.eldestDisplayListID = displayList.id; 
/* 133 */         return (size() > 200);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnicodeFont(String ttfFileRef, String hieroFileRef) throws SlickException {
/* 145 */     this(ttfFileRef, new HieroSettings(hieroFileRef));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnicodeFont(String ttfFileRef, HieroSettings settings) throws SlickException {
/* 156 */     this.ttfFileRef = ttfFileRef;
/* 157 */     Font font = createFont(ttfFileRef);
/* 158 */     initializeFont(font, settings.getFontSize(), settings.isBold(), settings.isItalic());
/* 159 */     loadSettings(settings);
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
/*     */   public UnicodeFont(String ttfFileRef, int size, boolean bold, boolean italic) throws SlickException {
/* 172 */     this.ttfFileRef = ttfFileRef;
/* 173 */     initializeFont(createFont(ttfFileRef), size, bold, italic);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnicodeFont(Font font, String hieroFileRef) throws SlickException {
/* 184 */     this(font, new HieroSettings(hieroFileRef));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnicodeFont(Font font, HieroSettings settings) {
/* 194 */     initializeFont(font, settings.getFontSize(), settings.isBold(), settings.isItalic());
/* 195 */     loadSettings(settings);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnicodeFont(Font font) {
/* 204 */     initializeFont(font, font.getSize(), font.isBold(), font.isItalic());
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
/*     */   public UnicodeFont(Font font, int size, boolean bold, boolean italic) {
/* 216 */     initializeFont(font, size, bold, italic);
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
/*     */   private void initializeFont(Font baseFont, int size, boolean bold, boolean italic) {
/* 228 */     Map<TextAttribute, ?> attributes = baseFont.getAttributes();
/* 229 */     attributes.put(TextAttribute.SIZE, new Float(size));
/* 230 */     attributes.put(TextAttribute.WEIGHT, bold ? TextAttribute.WEIGHT_BOLD : TextAttribute.WEIGHT_REGULAR);
/* 231 */     attributes.put(TextAttribute.POSTURE, italic ? TextAttribute.POSTURE_OBLIQUE : TextAttribute.POSTURE_REGULAR);
/*     */     try {
/* 233 */       attributes.put(TextAttribute.class.getDeclaredField("KERNING").get(null), TextAttribute.class.getDeclaredField("KERNING_ON").get(null));
/*     */     }
/* 235 */     catch (Exception ignored) {}
/*     */     
/* 237 */     this.font = baseFont.deriveFont((Map)attributes);
/*     */     
/* 239 */     FontMetrics metrics = GlyphPage.getScratchGraphics().getFontMetrics(this.font);
/* 240 */     this.ascent = metrics.getAscent();
/* 241 */     this.descent = metrics.getDescent();
/* 242 */     this.leading = metrics.getLeading();
/*     */ 
/*     */     
/* 245 */     char[] chars = " ".toCharArray();
/* 246 */     GlyphVector vector = this.font.layoutGlyphVector(GlyphPage.renderContext, chars, 0, chars.length, 0);
/* 247 */     this.spaceWidth = (vector.getGlyphLogicalBounds(0).getBounds()).width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadSettings(HieroSettings settings) {
/* 256 */     this.paddingTop = settings.getPaddingTop();
/* 257 */     this.paddingLeft = settings.getPaddingLeft();
/* 258 */     this.paddingBottom = settings.getPaddingBottom();
/* 259 */     this.paddingRight = settings.getPaddingRight();
/* 260 */     this.paddingAdvanceX = settings.getPaddingAdvanceX();
/* 261 */     this.paddingAdvanceY = settings.getPaddingAdvanceY();
/* 262 */     this.glyphPageWidth = settings.getGlyphPageWidth();
/* 263 */     this.glyphPageHeight = settings.getGlyphPageHeight();
/* 264 */     this.effects.addAll(settings.getEffects());
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
/*     */   public void addGlyphs(int startCodePoint, int endCodePoint) {
/* 278 */     for (int codePoint = startCodePoint; codePoint <= endCodePoint; codePoint++) {
/* 279 */       addGlyphs(new String(Character.toChars(codePoint)));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addGlyphs(String text) {
/* 289 */     if (text == null) throw new IllegalArgumentException("text cannot be null.");
/*     */     
/* 291 */     char[] chars = text.toCharArray();
/* 292 */     GlyphVector vector = this.font.layoutGlyphVector(GlyphPage.renderContext, chars, 0, chars.length, 0);
/* 293 */     for (int i = 0, n = vector.getNumGlyphs(); i < n; i++) {
/* 294 */       int codePoint = text.codePointAt(vector.getGlyphCharIndex(i));
/* 295 */       Rectangle bounds = getGlyphBounds(vector, i, codePoint);
/* 296 */       getGlyph(vector.getGlyphCode(i), codePoint, bounds, vector, i);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAsciiGlyphs() {
/* 305 */     addGlyphs(32, 255);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addNeheGlyphs() {
/* 313 */     addGlyphs(32, 128);
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
/*     */   public boolean loadGlyphs() throws SlickException {
/* 325 */     return loadGlyphs(-1);
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
/*     */   public boolean loadGlyphs(int maxGlyphsToLoad) throws SlickException {
/* 337 */     if (this.queuedGlyphs.isEmpty()) return false;
/*     */     
/* 339 */     if (this.effects.isEmpty()) {
/* 340 */       throw new IllegalStateException("The UnicodeFont must have at least one effect before any glyphs can be loaded.");
/*     */     }
/* 342 */     for (Iterator<Glyph> iterator = this.queuedGlyphs.iterator(); iterator.hasNext(); ) {
/* 343 */       Glyph glyph = iterator.next();
/* 344 */       int codePoint = glyph.getCodePoint();
/*     */ 
/*     */       
/* 347 */       if (glyph.getWidth() == 0 || codePoint == 32) {
/* 348 */         iterator.remove();
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 353 */       if (glyph.isMissing()) {
/* 354 */         if (this.missingGlyph != null) {
/* 355 */           if (glyph != this.missingGlyph) iterator.remove(); 
/*     */           continue;
/*     */         } 
/* 358 */         this.missingGlyph = glyph;
/*     */       } 
/*     */     } 
/*     */     
/* 362 */     Collections.sort(this.queuedGlyphs, heightComparator);
/*     */ 
/*     */     
/* 365 */     for (Iterator<GlyphPage> iter = this.glyphPages.iterator(); iter.hasNext(); ) {
/* 366 */       GlyphPage glyphPage = iter.next();
/* 367 */       maxGlyphsToLoad -= glyphPage.loadGlyphs(this.queuedGlyphs, maxGlyphsToLoad);
/* 368 */       if (maxGlyphsToLoad == 0 || this.queuedGlyphs.isEmpty()) {
/* 369 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 373 */     while (!this.queuedGlyphs.isEmpty()) {
/* 374 */       GlyphPage glyphPage = new GlyphPage(this, this.glyphPageWidth, this.glyphPageHeight);
/* 375 */       this.glyphPages.add(glyphPage);
/* 376 */       maxGlyphsToLoad -= glyphPage.loadGlyphs(this.queuedGlyphs, maxGlyphsToLoad);
/* 377 */       if (maxGlyphsToLoad == 0) return true;
/*     */     
/*     */     } 
/* 380 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearGlyphs() {
/* 387 */     for (int i = 0; i < 2175; i++) {
/* 388 */       this.glyphs[i] = null;
/*     */     }
/* 390 */     for (Iterator<GlyphPage> iter = this.glyphPages.iterator(); iter.hasNext(); ) {
/* 391 */       GlyphPage page = iter.next();
/*     */       try {
/* 393 */         page.getImage().destroy();
/* 394 */       } catch (SlickException ignored) {}
/*     */     } 
/*     */     
/* 397 */     this.glyphPages.clear();
/*     */     
/* 399 */     if (this.baseDisplayListID != -1) {
/* 400 */       GL.glDeleteLists(this.baseDisplayListID, this.displayLists.size());
/* 401 */       this.baseDisplayListID = -1;
/*     */     } 
/*     */     
/* 404 */     this.queuedGlyphs.clear();
/* 405 */     this.missingGlyph = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() {
/* 414 */     clearGlyphs();
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
/*     */   public DisplayList drawDisplayList(float x, float y, String text, Color color, int startIndex, int endIndex) {
/* 430 */     if (text == null) throw new IllegalArgumentException("text cannot be null."); 
/* 431 */     if (text.length() == 0) return EMPTY_DISPLAY_LIST; 
/* 432 */     if (color == null) throw new IllegalArgumentException("color cannot be null.");
/*     */     
/* 434 */     x -= this.paddingLeft;
/* 435 */     y -= this.paddingTop;
/*     */     
/* 437 */     String displayListKey = text.substring(startIndex, endIndex);
/*     */     
/* 439 */     color.bind();
/* 440 */     TextureImpl.bindNone();
/*     */     
/* 442 */     DisplayList displayList = null;
/* 443 */     if (this.displayListCaching && this.queuedGlyphs.isEmpty()) {
/* 444 */       if (this.baseDisplayListID == -1) {
/* 445 */         this.baseDisplayListID = GL.glGenLists(200);
/* 446 */         if (this.baseDisplayListID == 0) {
/* 447 */           this.baseDisplayListID = -1;
/* 448 */           this.displayListCaching = false;
/* 449 */           return new DisplayList();
/*     */         } 
/*     */       } 
/*     */       
/* 453 */       displayList = (DisplayList)this.displayLists.get(displayListKey);
/* 454 */       if (displayList != null) {
/* 455 */         if (displayList.invalid) {
/* 456 */           displayList.invalid = false;
/*     */         } else {
/* 458 */           GL.glTranslatef(x, y, 0.0F);
/* 459 */           GL.glCallList(displayList.id);
/* 460 */           GL.glTranslatef(-x, -y, 0.0F);
/* 461 */           return displayList;
/*     */         } 
/* 463 */       } else if (displayList == null) {
/*     */         
/* 465 */         displayList = new DisplayList();
/* 466 */         int displayListCount = this.displayLists.size();
/* 467 */         this.displayLists.put(displayListKey, displayList);
/* 468 */         if (displayListCount < 200) {
/* 469 */           displayList.id = this.baseDisplayListID + displayListCount;
/*     */         } else {
/* 471 */           displayList.id = this.eldestDisplayListID;
/*     */         } 
/* 473 */       }  this.displayLists.put(displayListKey, displayList);
/*     */     } 
/*     */     
/* 476 */     GL.glTranslatef(x, y, 0.0F);
/*     */     
/* 478 */     if (displayList != null) GL.glNewList(displayList.id, 4865);
/*     */     
/* 480 */     char[] chars = text.substring(0, endIndex).toCharArray();
/* 481 */     GlyphVector vector = this.font.layoutGlyphVector(GlyphPage.renderContext, chars, 0, chars.length, 0);
/*     */     
/* 483 */     int maxWidth = 0, totalHeight = 0, lines = 0;
/* 484 */     int extraX = 0, extraY = this.ascent;
/* 485 */     boolean startNewLine = false;
/* 486 */     Texture lastBind = null;
/* 487 */     for (int glyphIndex = 0, n = vector.getNumGlyphs(); glyphIndex < n; glyphIndex++) {
/* 488 */       int charIndex = vector.getGlyphCharIndex(glyphIndex);
/* 489 */       if (charIndex >= startIndex) {
/* 490 */         if (charIndex > endIndex)
/*     */           break; 
/* 492 */         int codePoint = text.codePointAt(charIndex);
/*     */         
/* 494 */         Rectangle bounds = getGlyphBounds(vector, glyphIndex, codePoint);
/* 495 */         Glyph glyph = getGlyph(vector.getGlyphCode(glyphIndex), codePoint, bounds, vector, glyphIndex);
/*     */         
/* 497 */         if (startNewLine && codePoint != 10) {
/* 498 */           extraX = -bounds.x;
/* 499 */           startNewLine = false;
/*     */         } 
/*     */         
/* 502 */         Image image = glyph.getImage();
/* 503 */         if (image == null && this.missingGlyph != null && glyph.isMissing()) image = this.missingGlyph.getImage(); 
/* 504 */         if (image != null) {
/*     */           
/* 506 */           Texture texture = image.getTexture();
/* 507 */           if (lastBind != null && lastBind != texture) {
/* 508 */             GL.glEnd();
/* 509 */             lastBind = null;
/*     */           } 
/* 511 */           if (lastBind == null) {
/* 512 */             texture.bind();
/* 513 */             GL.glBegin(7);
/* 514 */             lastBind = texture;
/*     */           } 
/* 516 */           image.drawEmbedded((bounds.x + extraX), (bounds.y + extraY), image.getWidth(), image.getHeight());
/*     */         } 
/*     */         
/* 519 */         if (glyphIndex >= 0) extraX += this.paddingRight + this.paddingLeft + this.paddingAdvanceX; 
/* 520 */         maxWidth = Math.max(maxWidth, bounds.x + extraX + bounds.width);
/* 521 */         totalHeight = Math.max(totalHeight, this.ascent + bounds.y + bounds.height);
/*     */         
/* 523 */         if (codePoint == 10) {
/* 524 */           startNewLine = true;
/* 525 */           extraY += getLineHeight();
/* 526 */           lines++;
/* 527 */           totalHeight = 0;
/*     */         } 
/*     */       } 
/* 530 */     }  if (lastBind != null) GL.glEnd();
/*     */     
/* 532 */     if (displayList != null) {
/* 533 */       GL.glEndList();
/*     */       
/* 535 */       if (!this.queuedGlyphs.isEmpty()) displayList.invalid = true;
/*     */     
/*     */     } 
/* 538 */     GL.glTranslatef(-x, -y, 0.0F);
/*     */     
/* 540 */     if (displayList == null) displayList = new DisplayList(); 
/* 541 */     displayList.width = (short)maxWidth;
/* 542 */     displayList.height = (short)(lines * getLineHeight() + totalHeight);
/* 543 */     return displayList;
/*     */   }
/*     */   
/*     */   public void drawString(float x, float y, String text, Color color, int startIndex, int endIndex) {
/* 547 */     drawDisplayList(x, y, text, color, startIndex, endIndex);
/*     */   }
/*     */   
/*     */   public void drawString(float x, float y, String text) {
/* 551 */     drawString(x, y, text, Color.white);
/*     */   }
/*     */   
/*     */   public void drawString(float x, float y, String text, Color col) {
/* 555 */     drawString(x, y, text, col, 0, text.length());
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
/*     */   private Glyph getGlyph(int glyphCode, int codePoint, Rectangle bounds, GlyphVector vector, int index) {
/* 570 */     if (glyphCode < 0 || glyphCode >= 1114111)
/*     */     {
/* 572 */       return new Glyph(codePoint, bounds, vector, index, this) {
/*     */           public boolean isMissing() {
/* 574 */             return true;
/*     */           }
/*     */         };
/*     */     }
/* 578 */     int pageIndex = glyphCode / 512;
/* 579 */     int glyphIndex = glyphCode & 0x1FF;
/* 580 */     Glyph glyph = null;
/* 581 */     Glyph[] page = this.glyphs[pageIndex];
/* 582 */     if (page != null) {
/* 583 */       glyph = page[glyphIndex];
/* 584 */       if (glyph != null) return glyph; 
/*     */     } else {
/* 586 */       page = this.glyphs[pageIndex] = new Glyph[512];
/*     */     } 
/* 588 */     glyph = page[glyphIndex] = new Glyph(codePoint, bounds, vector, index, this);
/* 589 */     this.queuedGlyphs.add(glyph);
/* 590 */     return glyph;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Rectangle getGlyphBounds(GlyphVector vector, int index, int codePoint) {
/* 601 */     Rectangle bounds = vector.getGlyphPixelBounds(index, GlyphPage.renderContext, 0.0F, 0.0F);
/* 602 */     if (codePoint == 32) bounds.width = this.spaceWidth; 
/* 603 */     return bounds;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSpaceWidth() {
/* 610 */     return this.spaceWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth(String text) {
/* 617 */     if (text == null) throw new IllegalArgumentException("text cannot be null."); 
/* 618 */     if (text.length() == 0) return 0;
/*     */     
/* 620 */     if (this.displayListCaching) {
/* 621 */       DisplayList displayList = (DisplayList)this.displayLists.get(text);
/* 622 */       if (displayList != null) return displayList.width;
/*     */     
/*     */     } 
/* 625 */     char[] chars = text.toCharArray();
/* 626 */     GlyphVector vector = this.font.layoutGlyphVector(GlyphPage.renderContext, chars, 0, chars.length, 0);
/*     */     
/* 628 */     int width = 0;
/* 629 */     int extraX = 0;
/* 630 */     boolean startNewLine = false;
/* 631 */     for (int glyphIndex = 0, n = vector.getNumGlyphs(); glyphIndex < n; glyphIndex++) {
/* 632 */       int charIndex = vector.getGlyphCharIndex(glyphIndex);
/* 633 */       int codePoint = text.codePointAt(charIndex);
/* 634 */       Rectangle bounds = getGlyphBounds(vector, glyphIndex, codePoint);
/*     */       
/* 636 */       if (startNewLine && codePoint != 10) extraX = -bounds.x;
/*     */       
/* 638 */       if (glyphIndex > 0) extraX += this.paddingLeft + this.paddingRight + this.paddingAdvanceX; 
/* 639 */       width = Math.max(width, bounds.x + extraX + bounds.width);
/*     */       
/* 641 */       if (codePoint == 10) startNewLine = true;
/*     */     
/*     */     } 
/* 644 */     return width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight(String text) {
/* 651 */     if (text == null) throw new IllegalArgumentException("text cannot be null."); 
/* 652 */     if (text.length() == 0) return 0;
/*     */     
/* 654 */     if (this.displayListCaching) {
/* 655 */       DisplayList displayList = (DisplayList)this.displayLists.get(text);
/* 656 */       if (displayList != null) return displayList.height;
/*     */     
/*     */     } 
/* 659 */     char[] chars = text.toCharArray();
/* 660 */     GlyphVector vector = this.font.layoutGlyphVector(GlyphPage.renderContext, chars, 0, chars.length, 0);
/*     */     
/* 662 */     int lines = 0, height = 0;
/* 663 */     for (int i = 0, n = vector.getNumGlyphs(); i < n; i++) {
/* 664 */       int charIndex = vector.getGlyphCharIndex(i);
/* 665 */       int codePoint = text.codePointAt(charIndex);
/* 666 */       if (codePoint != 32) {
/* 667 */         Rectangle bounds = getGlyphBounds(vector, i, codePoint);
/*     */         
/* 669 */         height = Math.max(height, this.ascent + bounds.y + bounds.height);
/*     */         
/* 671 */         if (codePoint == 10) {
/* 672 */           lines++;
/* 673 */           height = 0;
/*     */         } 
/*     */       } 
/* 676 */     }  return lines * getLineHeight() + height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getYOffset(String text) {
/* 687 */     if (text == null) throw new IllegalArgumentException("text cannot be null.");
/*     */     
/* 689 */     DisplayList displayList = null;
/* 690 */     if (this.displayListCaching) {
/* 691 */       displayList = (DisplayList)this.displayLists.get(text);
/* 692 */       if (displayList != null && displayList.yOffset != null) return displayList.yOffset.intValue();
/*     */     
/*     */     } 
/* 695 */     int index = text.indexOf('\n');
/* 696 */     if (index != -1) text = text.substring(0, index); 
/* 697 */     char[] chars = text.toCharArray();
/* 698 */     GlyphVector vector = this.font.layoutGlyphVector(GlyphPage.renderContext, chars, 0, chars.length, 0);
/* 699 */     int yOffset = this.ascent + (vector.getPixelBounds(null, 0.0F, 0.0F)).y;
/*     */     
/* 701 */     if (displayList != null) displayList.yOffset = new Short((short)yOffset);
/*     */     
/* 703 */     return yOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Font getFont() {
/* 712 */     return this.font;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPaddingTop() {
/* 721 */     return this.paddingTop;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPaddingTop(int paddingTop) {
/* 730 */     this.paddingTop = paddingTop;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPaddingLeft() {
/* 739 */     return this.paddingLeft;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPaddingLeft(int paddingLeft) {
/* 748 */     this.paddingLeft = paddingLeft;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPaddingBottom() {
/* 757 */     return this.paddingBottom;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPaddingBottom(int paddingBottom) {
/* 766 */     this.paddingBottom = paddingBottom;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPaddingRight() {
/* 775 */     return this.paddingRight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPaddingRight(int paddingRight) {
/* 784 */     this.paddingRight = paddingRight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPaddingAdvanceX() {
/* 793 */     return this.paddingAdvanceX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPaddingAdvanceX(int paddingAdvanceX) {
/* 803 */     this.paddingAdvanceX = paddingAdvanceX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPaddingAdvanceY() {
/* 812 */     return this.paddingAdvanceY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPaddingAdvanceY(int paddingAdvanceY) {
/* 822 */     this.paddingAdvanceY = paddingAdvanceY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLineHeight() {
/* 830 */     return this.descent + this.ascent + this.leading + this.paddingTop + this.paddingBottom + this.paddingAdvanceY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAscent() {
/* 839 */     return this.ascent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDescent() {
/* 849 */     return this.descent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLeading() {
/* 858 */     return this.leading;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getGlyphPageWidth() {
/* 867 */     return this.glyphPageWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGlyphPageWidth(int glyphPageWidth) {
/* 876 */     this.glyphPageWidth = glyphPageWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getGlyphPageHeight() {
/* 885 */     return this.glyphPageHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGlyphPageHeight(int glyphPageHeight) {
/* 894 */     this.glyphPageHeight = glyphPageHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getGlyphPages() {
/* 903 */     return this.glyphPages;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getEffects() {
/* 913 */     return this.effects;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCaching() {
/* 923 */     return this.displayListCaching;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDisplayListCaching(boolean displayListCaching) {
/* 933 */     this.displayListCaching = displayListCaching;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFontFile() {
/* 943 */     if (this.ttfFileRef == null) {
/*     */       
/*     */       try {
/* 946 */         Object font2D = Class.forName("sun.font.FontManager").getDeclaredMethod("getFont2D", new Class[] { Font.class }).invoke(null, new Object[] { this.font });
/*     */         
/* 948 */         Field platNameField = Class.forName("sun.font.PhysicalFont").getDeclaredField("platName");
/* 949 */         platNameField.setAccessible(true);
/* 950 */         this.ttfFileRef = (String)platNameField.get(font2D);
/* 951 */       } catch (Throwable ignored) {}
/*     */       
/* 953 */       if (this.ttfFileRef == null) this.ttfFileRef = ""; 
/*     */     } 
/* 955 */     if (this.ttfFileRef.length() == 0) return null; 
/* 956 */     return this.ttfFileRef;
/*     */   }
/*     */   
/*     */   public static class DisplayList {
/*     */     boolean invalid;
/*     */     int id;
/*     */     Short yOffset;
/*     */     public short width;
/*     */     public short height;
/*     */     public Object userData;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\UnicodeFont.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */