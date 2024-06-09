/*     */ package org.newdawn.slick;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ import org.newdawn.slick.opengl.renderer.Renderer;
/*     */ import org.newdawn.slick.opengl.renderer.SGL;
/*     */ import org.newdawn.slick.util.Log;
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
/*     */ 
/*     */ 
/*     */ public class AngelCodeFont
/*     */   implements Font
/*     */ {
/*  37 */   private static SGL GL = Renderer.get();
/*     */ 
/*     */   
/*     */   private static final int DISPLAY_LIST_CACHE_SIZE = 200;
/*     */ 
/*     */   
/*     */   private static final int MAX_CHAR = 255;
/*     */ 
/*     */   
/*     */   private boolean displayListCaching = true;
/*     */ 
/*     */   
/*     */   private Image fontImage;
/*     */ 
/*     */   
/*     */   private CharDef[] chars;
/*     */ 
/*     */   
/*     */   private int lineHeight;
/*     */ 
/*     */   
/*  58 */   private int baseDisplayListID = -1;
/*     */   
/*     */   private int eldestDisplayListID;
/*     */   
/*     */   private DisplayList eldestDisplayList;
/*     */ 
/*     */   
/*  65 */   private final LinkedHashMap displayLists = new LinkedHashMap<Object, Object>(200, 1.0F, true) {
/*     */       protected boolean removeEldestEntry(Map.Entry eldest) {
/*  67 */         AngelCodeFont.this.eldestDisplayList = (AngelCodeFont.DisplayList)eldest.getValue();
/*  68 */         AngelCodeFont.this.eldestDisplayListID = AngelCodeFont.this.eldestDisplayList.id;
/*     */         
/*  70 */         return false;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AngelCodeFont(String fntFile, Image image) throws SlickException {
/*  87 */     this.fontImage = image;
/*     */     
/*  89 */     parseFnt(ResourceLoader.getResourceAsStream(fntFile));
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
/*     */   public AngelCodeFont(String fntFile, String imgFile) throws SlickException {
/* 104 */     this.fontImage = new Image(imgFile);
/*     */     
/* 106 */     parseFnt(ResourceLoader.getResourceAsStream(fntFile));
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
/*     */   public AngelCodeFont(String fntFile, Image image, boolean caching) throws SlickException {
/* 124 */     this.fontImage = image;
/* 125 */     this.displayListCaching = caching;
/* 126 */     parseFnt(ResourceLoader.getResourceAsStream(fntFile));
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
/*     */   public AngelCodeFont(String fntFile, String imgFile, boolean caching) throws SlickException {
/* 144 */     this.fontImage = new Image(imgFile);
/* 145 */     this.displayListCaching = caching;
/* 146 */     parseFnt(ResourceLoader.getResourceAsStream(fntFile));
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
/*     */   public AngelCodeFont(String name, InputStream fntFile, InputStream imgFile) throws SlickException {
/* 164 */     this.fontImage = new Image(imgFile, name, false);
/*     */     
/* 166 */     parseFnt(fntFile);
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
/*     */   public AngelCodeFont(String name, InputStream fntFile, InputStream imgFile, boolean caching) throws SlickException {
/* 186 */     this.fontImage = new Image(imgFile, name, false);
/*     */     
/* 188 */     this.displayListCaching = caching;
/* 189 */     parseFnt(fntFile);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void parseFnt(InputStream fntFile) throws SlickException {
/* 200 */     if (this.displayListCaching) {
/* 201 */       this.baseDisplayListID = GL.glGenLists(200);
/* 202 */       if (this.baseDisplayListID == 0) this.displayListCaching = false;
/*     */     
/*     */     } 
/*     */     
/*     */     try {
/* 207 */       BufferedReader in = new BufferedReader(new InputStreamReader(fntFile));
/*     */       
/* 209 */       String info = in.readLine();
/* 210 */       String common = in.readLine();
/* 211 */       String page = in.readLine();
/*     */       
/* 213 */       Map<Object, Object> kerning = new HashMap<Object, Object>(64);
/* 214 */       List<CharDef> charDefs = new ArrayList(255);
/* 215 */       int maxChar = 0;
/* 216 */       boolean done = false;
/* 217 */       while (!done) {
/* 218 */         String line = in.readLine();
/* 219 */         if (line == null) {
/* 220 */           done = true; continue;
/*     */         } 
/* 222 */         if (!line.startsWith("chars c"))
/*     */         {
/* 224 */           if (line.startsWith("char")) {
/* 225 */             CharDef def = parseChar(line);
/* 226 */             if (def != null) {
/* 227 */               maxChar = Math.max(maxChar, def.id);
/* 228 */               charDefs.add(def);
/*     */             } 
/*     */           }  } 
/* 231 */         if (line.startsWith("kernings c"))
/*     */           continue; 
/* 233 */         if (line.startsWith("kerning")) {
/* 234 */           StringTokenizer tokens = new StringTokenizer(line, " =");
/* 235 */           tokens.nextToken();
/* 236 */           tokens.nextToken();
/* 237 */           short first = Short.parseShort(tokens.nextToken());
/* 238 */           tokens.nextToken();
/* 239 */           int second = Integer.parseInt(tokens.nextToken());
/* 240 */           tokens.nextToken();
/* 241 */           int offset = Integer.parseInt(tokens.nextToken());
/* 242 */           List<Short> values = (List)kerning.get(new Short(first));
/* 243 */           if (values == null) {
/* 244 */             values = new ArrayList();
/* 245 */             kerning.put(new Short(first), values);
/*     */           } 
/*     */           
/* 248 */           values.add(new Short((short)(offset << 8 | second)));
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 253 */       this.chars = new CharDef[maxChar + 1];
/* 254 */       for (Iterator<CharDef> iterator = charDefs.iterator(); iterator.hasNext(); ) {
/* 255 */         CharDef def = iterator.next();
/* 256 */         this.chars[def.id] = def;
/*     */       } 
/*     */ 
/*     */       
/* 260 */       for (Iterator<Map.Entry> iter = kerning.entrySet().iterator(); iter.hasNext(); ) {
/* 261 */         Map.Entry entry = iter.next();
/* 262 */         short first = ((Short)entry.getKey()).shortValue();
/* 263 */         List valueList = (List)entry.getValue();
/* 264 */         short[] valueArray = new short[valueList.size()];
/* 265 */         int i = 0;
/* 266 */         for (Iterator valueIter = valueList.iterator(); valueIter.hasNext(); i++)
/* 267 */           valueArray[i] = ((Short)valueIter.next()).shortValue(); 
/* 268 */         (this.chars[first]).kerning = valueArray;
/*     */       } 
/* 270 */     } catch (IOException e) {
/* 271 */       Log.error(e);
/* 272 */       throw new SlickException("Failed to parse font file: " + fntFile);
/*     */     } 
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
/*     */   private CharDef parseChar(String line) throws SlickException {
/* 285 */     CharDef def = new CharDef();
/* 286 */     StringTokenizer tokens = new StringTokenizer(line, " =");
/*     */     
/* 288 */     tokens.nextToken();
/* 289 */     tokens.nextToken();
/* 290 */     def.id = Short.parseShort(tokens.nextToken());
/* 291 */     if (def.id < 0) {
/* 292 */       return null;
/*     */     }
/* 294 */     if (def.id > 255) {
/* 295 */       throw new SlickException("Invalid character '" + def.id + "': AngelCodeFont does not support characters above " + 'ÿ');
/*     */     }
/*     */ 
/*     */     
/* 299 */     tokens.nextToken();
/* 300 */     def.x = Short.parseShort(tokens.nextToken());
/* 301 */     tokens.nextToken();
/* 302 */     def.y = Short.parseShort(tokens.nextToken());
/* 303 */     tokens.nextToken();
/* 304 */     def.width = Short.parseShort(tokens.nextToken());
/* 305 */     tokens.nextToken();
/* 306 */     def.height = Short.parseShort(tokens.nextToken());
/* 307 */     tokens.nextToken();
/* 308 */     def.xoffset = Short.parseShort(tokens.nextToken());
/* 309 */     tokens.nextToken();
/* 310 */     def.yoffset = Short.parseShort(tokens.nextToken());
/* 311 */     tokens.nextToken();
/* 312 */     def.xadvance = Short.parseShort(tokens.nextToken());
/*     */     
/* 314 */     def.init();
/*     */     
/* 316 */     if (def.id != 32) {
/* 317 */       this.lineHeight = Math.max(def.height + def.yoffset, this.lineHeight);
/*     */     }
/*     */     
/* 320 */     return def;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawString(float x, float y, String text) {
/* 327 */     drawString(x, y, text, Color.white);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawString(float x, float y, String text, Color col) {
/* 335 */     drawString(x, y, text, col, 0, text.length() - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawString(float x, float y, String text, Color col, int startIndex, int endIndex) {
/* 343 */     this.fontImage.bind();
/* 344 */     col.bind();
/*     */     
/* 346 */     GL.glTranslatef(x, y, 0.0F);
/* 347 */     if (this.displayListCaching && startIndex == 0 && endIndex == text.length() - 1) {
/* 348 */       DisplayList displayList = (DisplayList)this.displayLists.get(text);
/* 349 */       if (displayList != null) {
/* 350 */         GL.glCallList(displayList.id);
/*     */       } else {
/*     */         
/* 353 */         displayList = new DisplayList();
/* 354 */         displayList.text = text;
/* 355 */         int displayListCount = this.displayLists.size();
/* 356 */         if (displayListCount < 200) {
/* 357 */           displayList.id = this.baseDisplayListID + displayListCount;
/*     */         } else {
/* 359 */           displayList.id = this.eldestDisplayListID;
/* 360 */           this.displayLists.remove(this.eldestDisplayList.text);
/*     */         } 
/*     */         
/* 363 */         this.displayLists.put(text, displayList);
/*     */         
/* 365 */         GL.glNewList(displayList.id, 4865);
/* 366 */         render(text, startIndex, endIndex);
/* 367 */         GL.glEndList();
/*     */       } 
/*     */     } else {
/* 370 */       render(text, startIndex, endIndex);
/*     */     } 
/* 372 */     GL.glTranslatef(-x, -y, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void render(String text, int start, int end) {
/* 383 */     GL.glBegin(7);
/*     */     
/* 385 */     int x = 0, y = 0;
/* 386 */     CharDef lastCharDef = null;
/* 387 */     char[] data = text.toCharArray();
/* 388 */     for (int i = 0; i < data.length; i++) {
/* 389 */       int id = data[i];
/* 390 */       if (id == 10) {
/* 391 */         x = 0;
/* 392 */         y += getLineHeight();
/*     */       
/*     */       }
/* 395 */       else if (id < this.chars.length) {
/*     */ 
/*     */         
/* 398 */         CharDef charDef = this.chars[id];
/* 399 */         if (charDef != null)
/*     */         
/*     */         { 
/*     */           
/* 403 */           if (lastCharDef != null) x += lastCharDef.getKerning(id); 
/* 404 */           lastCharDef = charDef;
/*     */           
/* 406 */           if (i >= start && i <= end) {
/* 407 */             charDef.draw(x, y);
/*     */           }
/*     */           
/* 410 */           x += charDef.xadvance; } 
/*     */       } 
/* 412 */     }  GL.glEnd();
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
/* 423 */     DisplayList displayList = null;
/* 424 */     if (this.displayListCaching) {
/* 425 */       displayList = (DisplayList)this.displayLists.get(text);
/* 426 */       if (displayList != null && displayList.yOffset != null) return displayList.yOffset.intValue();
/*     */     
/*     */     } 
/* 429 */     int stopIndex = text.indexOf('\n');
/* 430 */     if (stopIndex == -1) stopIndex = text.length();
/*     */     
/* 432 */     int minYOffset = 10000;
/* 433 */     for (int i = 0; i < stopIndex; i++) {
/* 434 */       int id = text.charAt(i);
/* 435 */       CharDef charDef = this.chars[id];
/* 436 */       if (charDef != null)
/*     */       {
/*     */         
/* 439 */         minYOffset = Math.min(charDef.yoffset, minYOffset);
/*     */       }
/*     */     } 
/* 442 */     if (displayList != null) displayList.yOffset = new Short((short)minYOffset);
/*     */     
/* 444 */     return minYOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight(String text) {
/* 451 */     DisplayList displayList = null;
/* 452 */     if (this.displayListCaching) {
/* 453 */       displayList = (DisplayList)this.displayLists.get(text);
/* 454 */       if (displayList != null && displayList.height != null) return displayList.height.intValue();
/*     */     
/*     */     } 
/* 457 */     int lines = 0;
/* 458 */     int maxHeight = 0;
/* 459 */     for (int i = 0; i < text.length(); i++) {
/* 460 */       int id = text.charAt(i);
/* 461 */       if (id == 10) {
/* 462 */         lines++;
/* 463 */         maxHeight = 0;
/*     */ 
/*     */       
/*     */       }
/* 467 */       else if (id != 32) {
/*     */ 
/*     */         
/* 470 */         CharDef charDef = this.chars[id];
/* 471 */         if (charDef != null)
/*     */         {
/*     */ 
/*     */           
/* 475 */           maxHeight = Math.max(charDef.height + charDef.yoffset, maxHeight);
/*     */         }
/*     */       } 
/*     */     } 
/* 479 */     maxHeight += lines * getLineHeight();
/*     */     
/* 481 */     if (displayList != null) displayList.height = new Short((short)maxHeight);
/*     */     
/* 483 */     return maxHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth(String text) {
/* 490 */     DisplayList displayList = null;
/* 491 */     if (this.displayListCaching) {
/* 492 */       displayList = (DisplayList)this.displayLists.get(text);
/* 493 */       if (displayList != null && displayList.width != null) return displayList.width.intValue();
/*     */     
/*     */     } 
/* 496 */     int maxWidth = 0;
/* 497 */     int width = 0;
/* 498 */     CharDef lastCharDef = null;
/* 499 */     for (int i = 0, n = text.length(); i < n; i++) {
/* 500 */       int id = text.charAt(i);
/* 501 */       if (id == 10) {
/* 502 */         width = 0;
/*     */       
/*     */       }
/* 505 */       else if (id < this.chars.length) {
/*     */ 
/*     */         
/* 508 */         CharDef charDef = this.chars[id];
/* 509 */         if (charDef != null) {
/*     */ 
/*     */ 
/*     */           
/* 513 */           if (lastCharDef != null) width += lastCharDef.getKerning(id); 
/* 514 */           lastCharDef = charDef;
/*     */           
/* 516 */           if (i < n - 1) {
/* 517 */             width += charDef.xadvance;
/*     */           } else {
/* 519 */             width += charDef.width;
/*     */           } 
/* 521 */           maxWidth = Math.max(maxWidth, width);
/*     */         } 
/*     */       } 
/* 524 */     }  if (displayList != null) displayList.width = new Short((short)maxWidth);
/*     */     
/* 526 */     return maxWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class CharDef
/*     */   {
/*     */     public short id;
/*     */ 
/*     */     
/*     */     public short x;
/*     */ 
/*     */     
/*     */     public short y;
/*     */ 
/*     */     
/*     */     public short width;
/*     */ 
/*     */     
/*     */     public short height;
/*     */ 
/*     */     
/*     */     public short xoffset;
/*     */     
/*     */     public short yoffset;
/*     */     
/*     */     public short xadvance;
/*     */     
/*     */     public Image image;
/*     */     
/*     */     public short dlIndex;
/*     */     
/*     */     public short[] kerning;
/*     */ 
/*     */     
/*     */     private CharDef() {}
/*     */ 
/*     */     
/*     */     public void init() {
/* 565 */       this.image = AngelCodeFont.this.fontImage.getSubImage(this.x, this.y, this.width, this.height);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 572 */       return "[CharDef id=" + this.id + " x=" + this.x + " y=" + this.y + "]";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void draw(float x, float y) {
/* 584 */       this.image.drawEmbedded(x + this.xoffset, y + this.yoffset, this.width, this.height);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getKerning(int otherCodePoint) {
/* 593 */       if (this.kerning == null) return 0; 
/* 594 */       int low = 0;
/* 595 */       int high = this.kerning.length - 1;
/* 596 */       while (low <= high) {
/* 597 */         int midIndex = low + high >>> 1;
/* 598 */         int value = this.kerning[midIndex];
/* 599 */         int foundCodePoint = value & 0xFF;
/* 600 */         if (foundCodePoint < otherCodePoint) {
/* 601 */           low = midIndex + 1; continue;
/* 602 */         }  if (foundCodePoint > otherCodePoint) {
/* 603 */           high = midIndex - 1; continue;
/*     */         } 
/* 605 */         return value >> 8;
/*     */       } 
/* 607 */       return 0;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLineHeight() {
/* 615 */     return this.lineHeight;
/*     */   }
/*     */   
/*     */   private static class DisplayList {
/*     */     int id;
/*     */     Short yOffset;
/*     */     Short width;
/*     */     Short height;
/*     */     String text;
/*     */     
/*     */     private DisplayList() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\AngelCodeFont.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */