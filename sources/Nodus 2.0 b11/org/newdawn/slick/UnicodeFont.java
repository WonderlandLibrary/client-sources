/*   1:    */ package org.newdawn.slick;
/*   2:    */ 
/*   3:    */ import java.awt.FontFormatException;
/*   4:    */ import java.awt.FontMetrics;
/*   5:    */ import java.awt.Graphics2D;
/*   6:    */ import java.awt.Rectangle;
/*   7:    */ import java.awt.Shape;
/*   8:    */ import java.awt.font.GlyphVector;
/*   9:    */ import java.awt.font.TextAttribute;
/*  10:    */ import java.io.IOException;
/*  11:    */ import java.lang.reflect.Field;
/*  12:    */ import java.lang.reflect.Method;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.Collections;
/*  15:    */ import java.util.Comparator;
/*  16:    */ import java.util.Iterator;
/*  17:    */ import java.util.LinkedHashMap;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.Map;
/*  20:    */ import java.util.Map.Entry;
/*  21:    */ import org.newdawn.slick.font.Glyph;
/*  22:    */ import org.newdawn.slick.font.GlyphPage;
/*  23:    */ import org.newdawn.slick.font.HieroSettings;
/*  24:    */ import org.newdawn.slick.opengl.Texture;
/*  25:    */ import org.newdawn.slick.opengl.TextureImpl;
/*  26:    */ import org.newdawn.slick.opengl.renderer.Renderer;
/*  27:    */ import org.newdawn.slick.opengl.renderer.SGL;
/*  28:    */ import org.newdawn.slick.util.ResourceLoader;
/*  29:    */ 
/*  30:    */ public class UnicodeFont
/*  31:    */   implements Font
/*  32:    */ {
/*  33:    */   private static final int DISPLAY_LIST_CACHE_SIZE = 200;
/*  34:    */   private static final int MAX_GLYPH_CODE = 1114111;
/*  35:    */   private static final int PAGE_SIZE = 512;
/*  36:    */   private static final int PAGES = 2175;
/*  37: 47 */   private static final SGL GL = ;
/*  38: 49 */   private static final DisplayList EMPTY_DISPLAY_LIST = new DisplayList();
/*  39:    */   
/*  40:    */   private static java.awt.Font createFont(String ttfFileRef)
/*  41:    */     throws SlickException
/*  42:    */   {
/*  43:    */     try
/*  44:    */     {
/*  45: 61 */       return java.awt.Font.createFont(0, ResourceLoader.getResourceAsStream(ttfFileRef));
/*  46:    */     }
/*  47:    */     catch (FontFormatException ex)
/*  48:    */     {
/*  49: 63 */       throw new SlickException("Invalid font: " + ttfFileRef, ex);
/*  50:    */     }
/*  51:    */     catch (IOException ex)
/*  52:    */     {
/*  53: 65 */       throw new SlickException("Error reading font: " + ttfFileRef, ex);
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57: 72 */   private static final Comparator heightComparator = new Comparator()
/*  58:    */   {
/*  59:    */     public int compare(Object o1, Object o2)
/*  60:    */     {
/*  61: 74 */       return ((Glyph)o1).getHeight() - ((Glyph)o2).getHeight();
/*  62:    */     }
/*  63:    */   };
/*  64:    */   private java.awt.Font font;
/*  65:    */   private String ttfFileRef;
/*  66:    */   private int ascent;
/*  67:    */   private int descent;
/*  68:    */   private int leading;
/*  69:    */   private int spaceWidth;
/*  70: 91 */   private final Glyph[][] glyphs = new Glyph[2175][];
/*  71: 93 */   private final List glyphPages = new ArrayList();
/*  72: 95 */   private final List queuedGlyphs = new ArrayList(256);
/*  73: 97 */   private final List effects = new ArrayList();
/*  74:    */   private int paddingTop;
/*  75:    */   private int paddingLeft;
/*  76:    */   private int paddingBottom;
/*  77:    */   private int paddingRight;
/*  78:    */   private int paddingAdvanceX;
/*  79:    */   private int paddingAdvanceY;
/*  80:    */   private Glyph missingGlyph;
/*  81:115 */   private int glyphPageWidth = 512;
/*  82:117 */   private int glyphPageHeight = 512;
/*  83:120 */   private boolean displayListCaching = true;
/*  84:122 */   private int baseDisplayListID = -1;
/*  85:    */   private int eldestDisplayListID;
/*  86:    */   private DisplayList eldestDisplayList;
/*  87:129 */   private final LinkedHashMap displayLists = new LinkedHashMap(200, 1.0F, true)
/*  88:    */   {
/*  89:    */     protected boolean removeEldestEntry(Map.Entry eldest)
/*  90:    */     {
/*  91:131 */       UnicodeFont.DisplayList displayList = (UnicodeFont.DisplayList)eldest.getValue();
/*  92:132 */       if (displayList != null) {
/*  93:132 */         UnicodeFont.this.eldestDisplayListID = displayList.id;
/*  94:    */       }
/*  95:133 */       return size() > 200;
/*  96:    */     }
/*  97:    */   };
/*  98:    */   
/*  99:    */   public UnicodeFont(String ttfFileRef, String hieroFileRef)
/* 100:    */     throws SlickException
/* 101:    */   {
/* 102:145 */     this(ttfFileRef, new HieroSettings(hieroFileRef));
/* 103:    */   }
/* 104:    */   
/* 105:    */   public UnicodeFont(String ttfFileRef, HieroSettings settings)
/* 106:    */     throws SlickException
/* 107:    */   {
/* 108:156 */     this.ttfFileRef = ttfFileRef;
/* 109:157 */     java.awt.Font font = createFont(ttfFileRef);
/* 110:158 */     initializeFont(font, settings.getFontSize(), settings.isBold(), settings.isItalic());
/* 111:159 */     loadSettings(settings);
/* 112:    */   }
/* 113:    */   
/* 114:    */   public UnicodeFont(String ttfFileRef, int size, boolean bold, boolean italic)
/* 115:    */     throws SlickException
/* 116:    */   {
/* 117:172 */     this.ttfFileRef = ttfFileRef;
/* 118:173 */     initializeFont(createFont(ttfFileRef), size, bold, italic);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public UnicodeFont(java.awt.Font font, String hieroFileRef)
/* 122:    */     throws SlickException
/* 123:    */   {
/* 124:184 */     this(font, new HieroSettings(hieroFileRef));
/* 125:    */   }
/* 126:    */   
/* 127:    */   public UnicodeFont(java.awt.Font font, HieroSettings settings)
/* 128:    */   {
/* 129:194 */     initializeFont(font, settings.getFontSize(), settings.isBold(), settings.isItalic());
/* 130:195 */     loadSettings(settings);
/* 131:    */   }
/* 132:    */   
/* 133:    */   public UnicodeFont(java.awt.Font font)
/* 134:    */   {
/* 135:204 */     initializeFont(font, font.getSize(), font.isBold(), font.isItalic());
/* 136:    */   }
/* 137:    */   
/* 138:    */   public UnicodeFont(java.awt.Font font, int size, boolean bold, boolean italic)
/* 139:    */   {
/* 140:216 */     initializeFont(font, size, bold, italic);
/* 141:    */   }
/* 142:    */   
/* 143:    */   private void initializeFont(java.awt.Font baseFont, int size, boolean bold, boolean italic)
/* 144:    */   {
/* 145:228 */     Map attributes = baseFont.getAttributes();
/* 146:229 */     attributes.put(TextAttribute.SIZE, new Float(size));
/* 147:230 */     attributes.put(TextAttribute.WEIGHT, bold ? TextAttribute.WEIGHT_BOLD : TextAttribute.WEIGHT_REGULAR);
/* 148:231 */     attributes.put(TextAttribute.POSTURE, italic ? TextAttribute.POSTURE_OBLIQUE : TextAttribute.POSTURE_REGULAR);
/* 149:    */     try
/* 150:    */     {
/* 151:233 */       attributes.put(TextAttribute.class.getDeclaredField("KERNING").get(null), TextAttribute.class.getDeclaredField(
/* 152:234 */         "KERNING_ON").get(null));
/* 153:    */     }
/* 154:    */     catch (Exception localException) {}
/* 155:237 */     this.font = baseFont.deriveFont(attributes);
/* 156:    */     
/* 157:239 */     FontMetrics metrics = GlyphPage.getScratchGraphics().getFontMetrics(this.font);
/* 158:240 */     this.ascent = metrics.getAscent();
/* 159:241 */     this.descent = metrics.getDescent();
/* 160:242 */     this.leading = metrics.getLeading();
/* 161:    */     
/* 162:    */ 
/* 163:245 */     char[] chars = " ".toCharArray();
/* 164:246 */     GlyphVector vector = this.font.layoutGlyphVector(GlyphPage.renderContext, chars, 0, chars.length, 0);
/* 165:247 */     this.spaceWidth = vector.getGlyphLogicalBounds(0).getBounds().width;
/* 166:    */   }
/* 167:    */   
/* 168:    */   private void loadSettings(HieroSettings settings)
/* 169:    */   {
/* 170:256 */     this.paddingTop = settings.getPaddingTop();
/* 171:257 */     this.paddingLeft = settings.getPaddingLeft();
/* 172:258 */     this.paddingBottom = settings.getPaddingBottom();
/* 173:259 */     this.paddingRight = settings.getPaddingRight();
/* 174:260 */     this.paddingAdvanceX = settings.getPaddingAdvanceX();
/* 175:261 */     this.paddingAdvanceY = settings.getPaddingAdvanceY();
/* 176:262 */     this.glyphPageWidth = settings.getGlyphPageWidth();
/* 177:263 */     this.glyphPageHeight = settings.getGlyphPageHeight();
/* 178:264 */     this.effects.addAll(settings.getEffects());
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void addGlyphs(int startCodePoint, int endCodePoint)
/* 182:    */   {
/* 183:278 */     for (int codePoint = startCodePoint; codePoint <= endCodePoint; codePoint++) {
/* 184:279 */       addGlyphs(new String(Character.toChars(codePoint)));
/* 185:    */     }
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void addGlyphs(String text)
/* 189:    */   {
/* 190:289 */     if (text == null) {
/* 191:289 */       throw new IllegalArgumentException("text cannot be null.");
/* 192:    */     }
/* 193:291 */     char[] chars = text.toCharArray();
/* 194:292 */     GlyphVector vector = this.font.layoutGlyphVector(GlyphPage.renderContext, chars, 0, chars.length, 0);
/* 195:293 */     int i = 0;
/* 196:293 */     for (int n = vector.getNumGlyphs(); i < n; i++)
/* 197:    */     {
/* 198:294 */       int codePoint = text.codePointAt(vector.getGlyphCharIndex(i));
/* 199:295 */       Rectangle bounds = getGlyphBounds(vector, i, codePoint);
/* 200:296 */       getGlyph(vector.getGlyphCode(i), codePoint, bounds, vector, i);
/* 201:    */     }
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void addAsciiGlyphs()
/* 205:    */   {
/* 206:305 */     addGlyphs(32, 255);
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void addNeheGlyphs()
/* 210:    */   {
/* 211:313 */     addGlyphs(32, 128);
/* 212:    */   }
/* 213:    */   
/* 214:    */   public boolean loadGlyphs()
/* 215:    */     throws SlickException
/* 216:    */   {
/* 217:325 */     return loadGlyphs(-1);
/* 218:    */   }
/* 219:    */   
/* 220:    */   public boolean loadGlyphs(int maxGlyphsToLoad)
/* 221:    */     throws SlickException
/* 222:    */   {
/* 223:337 */     if (this.queuedGlyphs.isEmpty()) {
/* 224:337 */       return false;
/* 225:    */     }
/* 226:339 */     if (this.effects.isEmpty()) {
/* 227:340 */       throw new IllegalStateException("The UnicodeFont must have at least one effect before any glyphs can be loaded.");
/* 228:    */     }
/* 229:342 */     for (Iterator iter = this.queuedGlyphs.iterator(); iter.hasNext();)
/* 230:    */     {
/* 231:343 */       Glyph glyph = (Glyph)iter.next();
/* 232:344 */       int codePoint = glyph.getCodePoint();
/* 233:347 */       if ((glyph.getWidth() == 0) || (codePoint == 32)) {
/* 234:348 */         iter.remove();
/* 235:353 */       } else if (glyph.isMissing()) {
/* 236:354 */         if (this.missingGlyph != null)
/* 237:    */         {
/* 238:355 */           if (glyph != this.missingGlyph) {
/* 239:355 */             iter.remove();
/* 240:    */           }
/* 241:    */         }
/* 242:    */         else {
/* 243:358 */           this.missingGlyph = glyph;
/* 244:    */         }
/* 245:    */       }
/* 246:    */     }
/* 247:362 */     Collections.sort(this.queuedGlyphs, heightComparator);
/* 248:365 */     for (Iterator iter = this.glyphPages.iterator(); iter.hasNext();)
/* 249:    */     {
/* 250:366 */       GlyphPage glyphPage = (GlyphPage)iter.next();
/* 251:367 */       maxGlyphsToLoad -= glyphPage.loadGlyphs(this.queuedGlyphs, maxGlyphsToLoad);
/* 252:368 */       if ((maxGlyphsToLoad == 0) || (this.queuedGlyphs.isEmpty())) {
/* 253:369 */         return true;
/* 254:    */       }
/* 255:    */     }
/* 256:373 */     while (!this.queuedGlyphs.isEmpty())
/* 257:    */     {
/* 258:374 */       GlyphPage glyphPage = new GlyphPage(this, this.glyphPageWidth, this.glyphPageHeight);
/* 259:375 */       this.glyphPages.add(glyphPage);
/* 260:376 */       maxGlyphsToLoad -= glyphPage.loadGlyphs(this.queuedGlyphs, maxGlyphsToLoad);
/* 261:377 */       if (maxGlyphsToLoad == 0) {
/* 262:377 */         return true;
/* 263:    */       }
/* 264:    */     }
/* 265:380 */     return true;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public void clearGlyphs()
/* 269:    */   {
/* 270:387 */     for (int i = 0; i < 2175; i++) {
/* 271:388 */       this.glyphs[i] = null;
/* 272:    */     }
/* 273:390 */     for (Iterator iter = this.glyphPages.iterator(); iter.hasNext();)
/* 274:    */     {
/* 275:391 */       GlyphPage page = (GlyphPage)iter.next();
/* 276:    */       try
/* 277:    */       {
/* 278:393 */         page.getImage().destroy();
/* 279:    */       }
/* 280:    */       catch (SlickException localSlickException) {}
/* 281:    */     }
/* 282:397 */     this.glyphPages.clear();
/* 283:399 */     if (this.baseDisplayListID != -1)
/* 284:    */     {
/* 285:400 */       GL.glDeleteLists(this.baseDisplayListID, this.displayLists.size());
/* 286:401 */       this.baseDisplayListID = -1;
/* 287:    */     }
/* 288:404 */     this.queuedGlyphs.clear();
/* 289:405 */     this.missingGlyph = null;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void destroy()
/* 293:    */   {
/* 294:414 */     clearGlyphs();
/* 295:    */   }
/* 296:    */   
/* 297:    */   public DisplayList drawDisplayList(float x, float y, String text, Color color, int startIndex, int endIndex)
/* 298:    */   {
/* 299:430 */     if (text == null) {
/* 300:430 */       throw new IllegalArgumentException("text cannot be null.");
/* 301:    */     }
/* 302:431 */     if (text.length() == 0) {
/* 303:431 */       return EMPTY_DISPLAY_LIST;
/* 304:    */     }
/* 305:432 */     if (color == null) {
/* 306:432 */       throw new IllegalArgumentException("color cannot be null.");
/* 307:    */     }
/* 308:434 */     x -= this.paddingLeft;
/* 309:435 */     y -= this.paddingTop;
/* 310:    */     
/* 311:437 */     String displayListKey = text.substring(startIndex, endIndex);
/* 312:    */     
/* 313:439 */     color.bind();
/* 314:440 */     TextureImpl.bindNone();
/* 315:    */     
/* 316:442 */     DisplayList displayList = null;
/* 317:443 */     if ((this.displayListCaching) && (this.queuedGlyphs.isEmpty()))
/* 318:    */     {
/* 319:444 */       if (this.baseDisplayListID == -1)
/* 320:    */       {
/* 321:445 */         this.baseDisplayListID = GL.glGenLists(200);
/* 322:446 */         if (this.baseDisplayListID == 0)
/* 323:    */         {
/* 324:447 */           this.baseDisplayListID = -1;
/* 325:448 */           this.displayListCaching = false;
/* 326:449 */           return new DisplayList();
/* 327:    */         }
/* 328:    */       }
/* 329:453 */       displayList = (DisplayList)this.displayLists.get(displayListKey);
/* 330:454 */       if (displayList != null)
/* 331:    */       {
/* 332:455 */         if (displayList.invalid)
/* 333:    */         {
/* 334:456 */           displayList.invalid = false;
/* 335:    */         }
/* 336:    */         else
/* 337:    */         {
/* 338:458 */           GL.glTranslatef(x, y, 0.0F);
/* 339:459 */           GL.glCallList(displayList.id);
/* 340:460 */           GL.glTranslatef(-x, -y, 0.0F);
/* 341:461 */           return displayList;
/* 342:    */         }
/* 343:    */       }
/* 344:463 */       else if (displayList == null)
/* 345:    */       {
/* 346:465 */         displayList = new DisplayList();
/* 347:466 */         int displayListCount = this.displayLists.size();
/* 348:467 */         this.displayLists.put(displayListKey, displayList);
/* 349:468 */         if (displayListCount < 200) {
/* 350:469 */           displayList.id = (this.baseDisplayListID + displayListCount);
/* 351:    */         } else {
/* 352:471 */           displayList.id = this.eldestDisplayListID;
/* 353:    */         }
/* 354:    */       }
/* 355:473 */       this.displayLists.put(displayListKey, displayList);
/* 356:    */     }
/* 357:476 */     GL.glTranslatef(x, y, 0.0F);
/* 358:478 */     if (displayList != null) {
/* 359:478 */       GL.glNewList(displayList.id, 4865);
/* 360:    */     }
/* 361:480 */     char[] chars = text.substring(0, endIndex).toCharArray();
/* 362:481 */     GlyphVector vector = this.font.layoutGlyphVector(GlyphPage.renderContext, chars, 0, chars.length, 0);
/* 363:    */     
/* 364:483 */     int maxWidth = 0;int totalHeight = 0;int lines = 0;
/* 365:484 */     int extraX = 0;int extraY = this.ascent;
/* 366:485 */     boolean startNewLine = false;
/* 367:486 */     Texture lastBind = null;
/* 368:487 */     int glyphIndex = 0;
/* 369:487 */     for (int n = vector.getNumGlyphs(); glyphIndex < n; glyphIndex++)
/* 370:    */     {
/* 371:488 */       int charIndex = vector.getGlyphCharIndex(glyphIndex);
/* 372:489 */       if (charIndex >= startIndex)
/* 373:    */       {
/* 374:490 */         if (charIndex > endIndex) {
/* 375:    */           break;
/* 376:    */         }
/* 377:492 */         int codePoint = text.codePointAt(charIndex);
/* 378:    */         
/* 379:494 */         Rectangle bounds = getGlyphBounds(vector, glyphIndex, codePoint);
/* 380:495 */         Glyph glyph = getGlyph(vector.getGlyphCode(glyphIndex), codePoint, bounds, vector, glyphIndex);
/* 381:497 */         if ((startNewLine) && (codePoint != 10))
/* 382:    */         {
/* 383:498 */           extraX = -bounds.x;
/* 384:499 */           startNewLine = false;
/* 385:    */         }
/* 386:502 */         Image image = glyph.getImage();
/* 387:503 */         if ((image == null) && (this.missingGlyph != null) && (glyph.isMissing())) {
/* 388:503 */           image = this.missingGlyph.getImage();
/* 389:    */         }
/* 390:504 */         if (image != null)
/* 391:    */         {
/* 392:506 */           Texture texture = image.getTexture();
/* 393:507 */           if ((lastBind != null) && (lastBind != texture))
/* 394:    */           {
/* 395:508 */             GL.glEnd();
/* 396:509 */             lastBind = null;
/* 397:    */           }
/* 398:511 */           if (lastBind == null)
/* 399:    */           {
/* 400:512 */             texture.bind();
/* 401:513 */             GL.glBegin(7);
/* 402:514 */             lastBind = texture;
/* 403:    */           }
/* 404:516 */           image.drawEmbedded(bounds.x + extraX, bounds.y + extraY, image.getWidth(), image.getHeight());
/* 405:    */         }
/* 406:519 */         if (glyphIndex >= 0) {
/* 407:519 */           extraX += this.paddingRight + this.paddingLeft + this.paddingAdvanceX;
/* 408:    */         }
/* 409:520 */         maxWidth = Math.max(maxWidth, bounds.x + extraX + bounds.width);
/* 410:521 */         totalHeight = Math.max(totalHeight, this.ascent + bounds.y + bounds.height);
/* 411:523 */         if (codePoint == 10)
/* 412:    */         {
/* 413:524 */           startNewLine = true;
/* 414:525 */           extraY += getLineHeight();
/* 415:526 */           lines++;
/* 416:527 */           totalHeight = 0;
/* 417:    */         }
/* 418:    */       }
/* 419:    */     }
/* 420:530 */     if (lastBind != null) {
/* 421:530 */       GL.glEnd();
/* 422:    */     }
/* 423:532 */     if (displayList != null)
/* 424:    */     {
/* 425:533 */       GL.glEndList();
/* 426:535 */       if (!this.queuedGlyphs.isEmpty()) {
/* 427:535 */         displayList.invalid = true;
/* 428:    */       }
/* 429:    */     }
/* 430:538 */     GL.glTranslatef(-x, -y, 0.0F);
/* 431:540 */     if (displayList == null) {
/* 432:540 */       displayList = new DisplayList();
/* 433:    */     }
/* 434:541 */     displayList.width = ((short)maxWidth);
/* 435:542 */     displayList.height = ((short)(lines * getLineHeight() + totalHeight));
/* 436:543 */     return displayList;
/* 437:    */   }
/* 438:    */   
/* 439:    */   public void drawString(float x, float y, String text, Color color, int startIndex, int endIndex)
/* 440:    */   {
/* 441:547 */     drawDisplayList(x, y, text, color, startIndex, endIndex);
/* 442:    */   }
/* 443:    */   
/* 444:    */   public void drawString(float x, float y, String text)
/* 445:    */   {
/* 446:551 */     drawString(x, y, text, Color.white);
/* 447:    */   }
/* 448:    */   
/* 449:    */   public void drawString(float x, float y, String text, Color col)
/* 450:    */   {
/* 451:555 */     drawString(x, y, text, col, 0, text.length());
/* 452:    */   }
/* 453:    */   
/* 454:    */   private Glyph getGlyph(int glyphCode, int codePoint, Rectangle bounds, GlyphVector vector, int index)
/* 455:    */   {
/* 456:570 */     if ((glyphCode < 0) || (glyphCode >= 1114111)) {
/* 457:572 */       new Glyph(codePoint, bounds, vector, index, this)
/* 458:    */       {
/* 459:    */         public boolean isMissing()
/* 460:    */         {
/* 461:574 */           return true;
/* 462:    */         }
/* 463:    */       };
/* 464:    */     }
/* 465:578 */     int pageIndex = glyphCode / 512;
/* 466:579 */     int glyphIndex = glyphCode & 0x1FF;
/* 467:580 */     Glyph glyph = null;
/* 468:581 */     Glyph[] page = this.glyphs[pageIndex];
/* 469:582 */     if (page != null)
/* 470:    */     {
/* 471:583 */       glyph = page[glyphIndex];
/* 472:584 */       if (glyph != null) {
/* 473:584 */         return glyph;
/* 474:    */       }
/* 475:    */     }
/* 476:    */     else
/* 477:    */     {
/* 478:586 */       page = this.glyphs[pageIndex] =  = new Glyph[512];
/* 479:    */     }
/* 480:588 */     glyph = page[glyphIndex] =  = new Glyph(codePoint, bounds, vector, index, this);
/* 481:589 */     this.queuedGlyphs.add(glyph);
/* 482:590 */     return glyph;
/* 483:    */   }
/* 484:    */   
/* 485:    */   private Rectangle getGlyphBounds(GlyphVector vector, int index, int codePoint)
/* 486:    */   {
/* 487:601 */     Rectangle bounds = vector.getGlyphPixelBounds(index, GlyphPage.renderContext, 0.0F, 0.0F);
/* 488:602 */     if (codePoint == 32) {
/* 489:602 */       bounds.width = this.spaceWidth;
/* 490:    */     }
/* 491:603 */     return bounds;
/* 492:    */   }
/* 493:    */   
/* 494:    */   public int getSpaceWidth()
/* 495:    */   {
/* 496:610 */     return this.spaceWidth;
/* 497:    */   }
/* 498:    */   
/* 499:    */   public int getWidth(String text)
/* 500:    */   {
/* 501:617 */     if (text == null) {
/* 502:617 */       throw new IllegalArgumentException("text cannot be null.");
/* 503:    */     }
/* 504:618 */     if (text.length() == 0) {
/* 505:618 */       return 0;
/* 506:    */     }
/* 507:620 */     if (this.displayListCaching)
/* 508:    */     {
/* 509:621 */       DisplayList displayList = (DisplayList)this.displayLists.get(text);
/* 510:622 */       if (displayList != null) {
/* 511:622 */         return displayList.width;
/* 512:    */       }
/* 513:    */     }
/* 514:625 */     char[] chars = text.toCharArray();
/* 515:626 */     GlyphVector vector = this.font.layoutGlyphVector(GlyphPage.renderContext, chars, 0, chars.length, 0);
/* 516:    */     
/* 517:628 */     int width = 0;
/* 518:629 */     int extraX = 0;
/* 519:630 */     boolean startNewLine = false;
/* 520:631 */     int glyphIndex = 0;
/* 521:631 */     for (int n = vector.getNumGlyphs(); glyphIndex < n; glyphIndex++)
/* 522:    */     {
/* 523:632 */       int charIndex = vector.getGlyphCharIndex(glyphIndex);
/* 524:633 */       int codePoint = text.codePointAt(charIndex);
/* 525:634 */       Rectangle bounds = getGlyphBounds(vector, glyphIndex, codePoint);
/* 526:636 */       if ((startNewLine) && (codePoint != 10)) {
/* 527:636 */         extraX = -bounds.x;
/* 528:    */       }
/* 529:638 */       if (glyphIndex > 0) {
/* 530:638 */         extraX += this.paddingLeft + this.paddingRight + this.paddingAdvanceX;
/* 531:    */       }
/* 532:639 */       width = Math.max(width, bounds.x + extraX + bounds.width);
/* 533:641 */       if (codePoint == 10) {
/* 534:641 */         startNewLine = true;
/* 535:    */       }
/* 536:    */     }
/* 537:644 */     return width;
/* 538:    */   }
/* 539:    */   
/* 540:    */   public int getHeight(String text)
/* 541:    */   {
/* 542:651 */     if (text == null) {
/* 543:651 */       throw new IllegalArgumentException("text cannot be null.");
/* 544:    */     }
/* 545:652 */     if (text.length() == 0) {
/* 546:652 */       return 0;
/* 547:    */     }
/* 548:654 */     if (this.displayListCaching)
/* 549:    */     {
/* 550:655 */       DisplayList displayList = (DisplayList)this.displayLists.get(text);
/* 551:656 */       if (displayList != null) {
/* 552:656 */         return displayList.height;
/* 553:    */       }
/* 554:    */     }
/* 555:659 */     char[] chars = text.toCharArray();
/* 556:660 */     GlyphVector vector = this.font.layoutGlyphVector(GlyphPage.renderContext, chars, 0, chars.length, 0);
/* 557:    */     
/* 558:662 */     int lines = 0;int height = 0;
/* 559:663 */     int i = 0;
/* 560:663 */     for (int n = vector.getNumGlyphs(); i < n; i++)
/* 561:    */     {
/* 562:664 */       int charIndex = vector.getGlyphCharIndex(i);
/* 563:665 */       int codePoint = text.codePointAt(charIndex);
/* 564:666 */       if (codePoint != 32)
/* 565:    */       {
/* 566:667 */         Rectangle bounds = getGlyphBounds(vector, i, codePoint);
/* 567:    */         
/* 568:669 */         height = Math.max(height, this.ascent + bounds.y + bounds.height);
/* 569:671 */         if (codePoint == 10)
/* 570:    */         {
/* 571:672 */           lines++;
/* 572:673 */           height = 0;
/* 573:    */         }
/* 574:    */       }
/* 575:    */     }
/* 576:676 */     return lines * getLineHeight() + height;
/* 577:    */   }
/* 578:    */   
/* 579:    */   public int getYOffset(String text)
/* 580:    */   {
/* 581:687 */     if (text == null) {
/* 582:687 */       throw new IllegalArgumentException("text cannot be null.");
/* 583:    */     }
/* 584:689 */     DisplayList displayList = null;
/* 585:690 */     if (this.displayListCaching)
/* 586:    */     {
/* 587:691 */       displayList = (DisplayList)this.displayLists.get(text);
/* 588:692 */       if ((displayList != null) && (displayList.yOffset != null)) {
/* 589:692 */         return displayList.yOffset.intValue();
/* 590:    */       }
/* 591:    */     }
/* 592:695 */     int index = text.indexOf('\n');
/* 593:696 */     if (index != -1) {
/* 594:696 */       text = text.substring(0, index);
/* 595:    */     }
/* 596:697 */     char[] chars = text.toCharArray();
/* 597:698 */     GlyphVector vector = this.font.layoutGlyphVector(GlyphPage.renderContext, chars, 0, chars.length, 0);
/* 598:699 */     int yOffset = this.ascent + vector.getPixelBounds(null, 0.0F, 0.0F).y;
/* 599:701 */     if (displayList != null) {
/* 600:701 */       displayList.yOffset = new Short((short)yOffset);
/* 601:    */     }
/* 602:703 */     return yOffset;
/* 603:    */   }
/* 604:    */   
/* 605:    */   public java.awt.Font getFont()
/* 606:    */   {
/* 607:712 */     return this.font;
/* 608:    */   }
/* 609:    */   
/* 610:    */   public int getPaddingTop()
/* 611:    */   {
/* 612:721 */     return this.paddingTop;
/* 613:    */   }
/* 614:    */   
/* 615:    */   public void setPaddingTop(int paddingTop)
/* 616:    */   {
/* 617:730 */     this.paddingTop = paddingTop;
/* 618:    */   }
/* 619:    */   
/* 620:    */   public int getPaddingLeft()
/* 621:    */   {
/* 622:739 */     return this.paddingLeft;
/* 623:    */   }
/* 624:    */   
/* 625:    */   public void setPaddingLeft(int paddingLeft)
/* 626:    */   {
/* 627:748 */     this.paddingLeft = paddingLeft;
/* 628:    */   }
/* 629:    */   
/* 630:    */   public int getPaddingBottom()
/* 631:    */   {
/* 632:757 */     return this.paddingBottom;
/* 633:    */   }
/* 634:    */   
/* 635:    */   public void setPaddingBottom(int paddingBottom)
/* 636:    */   {
/* 637:766 */     this.paddingBottom = paddingBottom;
/* 638:    */   }
/* 639:    */   
/* 640:    */   public int getPaddingRight()
/* 641:    */   {
/* 642:775 */     return this.paddingRight;
/* 643:    */   }
/* 644:    */   
/* 645:    */   public void setPaddingRight(int paddingRight)
/* 646:    */   {
/* 647:784 */     this.paddingRight = paddingRight;
/* 648:    */   }
/* 649:    */   
/* 650:    */   public int getPaddingAdvanceX()
/* 651:    */   {
/* 652:793 */     return this.paddingAdvanceX;
/* 653:    */   }
/* 654:    */   
/* 655:    */   public void setPaddingAdvanceX(int paddingAdvanceX)
/* 656:    */   {
/* 657:803 */     this.paddingAdvanceX = paddingAdvanceX;
/* 658:    */   }
/* 659:    */   
/* 660:    */   public int getPaddingAdvanceY()
/* 661:    */   {
/* 662:812 */     return this.paddingAdvanceY;
/* 663:    */   }
/* 664:    */   
/* 665:    */   public void setPaddingAdvanceY(int paddingAdvanceY)
/* 666:    */   {
/* 667:822 */     this.paddingAdvanceY = paddingAdvanceY;
/* 668:    */   }
/* 669:    */   
/* 670:    */   public int getLineHeight()
/* 671:    */   {
/* 672:830 */     return this.descent + this.ascent + this.leading + this.paddingTop + this.paddingBottom + this.paddingAdvanceY;
/* 673:    */   }
/* 674:    */   
/* 675:    */   public int getAscent()
/* 676:    */   {
/* 677:839 */     return this.ascent;
/* 678:    */   }
/* 679:    */   
/* 680:    */   public int getDescent()
/* 681:    */   {
/* 682:849 */     return this.descent;
/* 683:    */   }
/* 684:    */   
/* 685:    */   public int getLeading()
/* 686:    */   {
/* 687:858 */     return this.leading;
/* 688:    */   }
/* 689:    */   
/* 690:    */   public int getGlyphPageWidth()
/* 691:    */   {
/* 692:867 */     return this.glyphPageWidth;
/* 693:    */   }
/* 694:    */   
/* 695:    */   public void setGlyphPageWidth(int glyphPageWidth)
/* 696:    */   {
/* 697:876 */     this.glyphPageWidth = glyphPageWidth;
/* 698:    */   }
/* 699:    */   
/* 700:    */   public int getGlyphPageHeight()
/* 701:    */   {
/* 702:885 */     return this.glyphPageHeight;
/* 703:    */   }
/* 704:    */   
/* 705:    */   public void setGlyphPageHeight(int glyphPageHeight)
/* 706:    */   {
/* 707:894 */     this.glyphPageHeight = glyphPageHeight;
/* 708:    */   }
/* 709:    */   
/* 710:    */   public List getGlyphPages()
/* 711:    */   {
/* 712:903 */     return this.glyphPages;
/* 713:    */   }
/* 714:    */   
/* 715:    */   public List getEffects()
/* 716:    */   {
/* 717:913 */     return this.effects;
/* 718:    */   }
/* 719:    */   
/* 720:    */   public boolean isCaching()
/* 721:    */   {
/* 722:923 */     return this.displayListCaching;
/* 723:    */   }
/* 724:    */   
/* 725:    */   public void setDisplayListCaching(boolean displayListCaching)
/* 726:    */   {
/* 727:933 */     this.displayListCaching = displayListCaching;
/* 728:    */   }
/* 729:    */   
/* 730:    */   public String getFontFile()
/* 731:    */   {
/* 732:943 */     if (this.ttfFileRef == null)
/* 733:    */     {
/* 734:    */       try
/* 735:    */       {
/* 736:946 */         Object font2D = Class.forName("sun.font.FontManager").getDeclaredMethod("getFont2D", new Class[] { java.awt.Font.class })
/* 737:947 */           .invoke(null, new Object[] { this.font });
/* 738:948 */         Field platNameField = Class.forName("sun.font.PhysicalFont").getDeclaredField("platName");
/* 739:949 */         platNameField.setAccessible(true);
/* 740:950 */         this.ttfFileRef = ((String)platNameField.get(font2D));
/* 741:    */       }
/* 742:    */       catch (Throwable localThrowable) {}
/* 743:953 */       if (this.ttfFileRef == null) {
/* 744:953 */         this.ttfFileRef = "";
/* 745:    */       }
/* 746:    */     }
/* 747:955 */     if (this.ttfFileRef.length() == 0) {
/* 748:955 */       return null;
/* 749:    */     }
/* 750:956 */     return this.ttfFileRef;
/* 751:    */   }
/* 752:    */   
/* 753:    */   public static class DisplayList
/* 754:    */   {
/* 755:    */     boolean invalid;
/* 756:    */     int id;
/* 757:    */     Short yOffset;
/* 758:    */     public short width;
/* 759:    */     public short height;
/* 760:    */     public Object userData;
/* 761:    */   }
/* 762:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.UnicodeFont
 * JD-Core Version:    0.7.0.1
 */