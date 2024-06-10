/*   1:    */ package org.newdawn.slick;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InputStream;
/*   6:    */ import java.io.InputStreamReader;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import java.util.LinkedHashMap;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import java.util.Map.Entry;
/*  14:    */ import java.util.Set;
/*  15:    */ import java.util.StringTokenizer;
/*  16:    */ import org.newdawn.slick.opengl.renderer.Renderer;
/*  17:    */ import org.newdawn.slick.opengl.renderer.SGL;
/*  18:    */ import org.newdawn.slick.util.Log;
/*  19:    */ import org.newdawn.slick.util.ResourceLoader;
/*  20:    */ 
/*  21:    */ public class AngelCodeFont
/*  22:    */   implements Font
/*  23:    */ {
/*  24: 37 */   private static SGL GL = ;
/*  25:    */   private static final int DISPLAY_LIST_CACHE_SIZE = 200;
/*  26:    */   private static final int MAX_CHAR = 255;
/*  27: 49 */   private boolean displayListCaching = true;
/*  28:    */   private Image fontImage;
/*  29:    */   private CharDef[] chars;
/*  30:    */   private int lineHeight;
/*  31: 58 */   private int baseDisplayListID = -1;
/*  32:    */   private int eldestDisplayListID;
/*  33:    */   private DisplayList eldestDisplayList;
/*  34: 65 */   private final LinkedHashMap displayLists = new LinkedHashMap(200, 1.0F, true)
/*  35:    */   {
/*  36:    */     protected boolean removeEldestEntry(Map.Entry eldest)
/*  37:    */     {
/*  38: 67 */       AngelCodeFont.this.eldestDisplayList = ((AngelCodeFont.DisplayList)eldest.getValue());
/*  39: 68 */       AngelCodeFont.this.eldestDisplayListID = AngelCodeFont.this.eldestDisplayList.id;
/*  40:    */       
/*  41: 70 */       return false;
/*  42:    */     }
/*  43:    */   };
/*  44:    */   
/*  45:    */   public AngelCodeFont(String fntFile, Image image)
/*  46:    */     throws SlickException
/*  47:    */   {
/*  48: 87 */     this.fontImage = image;
/*  49:    */     
/*  50: 89 */     parseFnt(ResourceLoader.getResourceAsStream(fntFile));
/*  51:    */   }
/*  52:    */   
/*  53:    */   public AngelCodeFont(String fntFile, String imgFile)
/*  54:    */     throws SlickException
/*  55:    */   {
/*  56:104 */     this.fontImage = new Image(imgFile);
/*  57:    */     
/*  58:106 */     parseFnt(ResourceLoader.getResourceAsStream(fntFile));
/*  59:    */   }
/*  60:    */   
/*  61:    */   public AngelCodeFont(String fntFile, Image image, boolean caching)
/*  62:    */     throws SlickException
/*  63:    */   {
/*  64:124 */     this.fontImage = image;
/*  65:125 */     this.displayListCaching = caching;
/*  66:126 */     parseFnt(ResourceLoader.getResourceAsStream(fntFile));
/*  67:    */   }
/*  68:    */   
/*  69:    */   public AngelCodeFont(String fntFile, String imgFile, boolean caching)
/*  70:    */     throws SlickException
/*  71:    */   {
/*  72:144 */     this.fontImage = new Image(imgFile);
/*  73:145 */     this.displayListCaching = caching;
/*  74:146 */     parseFnt(ResourceLoader.getResourceAsStream(fntFile));
/*  75:    */   }
/*  76:    */   
/*  77:    */   public AngelCodeFont(String name, InputStream fntFile, InputStream imgFile)
/*  78:    */     throws SlickException
/*  79:    */   {
/*  80:164 */     this.fontImage = new Image(imgFile, name, false);
/*  81:    */     
/*  82:166 */     parseFnt(fntFile);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public AngelCodeFont(String name, InputStream fntFile, InputStream imgFile, boolean caching)
/*  86:    */     throws SlickException
/*  87:    */   {
/*  88:186 */     this.fontImage = new Image(imgFile, name, false);
/*  89:    */     
/*  90:188 */     this.displayListCaching = caching;
/*  91:189 */     parseFnt(fntFile);
/*  92:    */   }
/*  93:    */   
/*  94:    */   private void parseFnt(InputStream fntFile)
/*  95:    */     throws SlickException
/*  96:    */   {
/*  97:200 */     if (this.displayListCaching)
/*  98:    */     {
/*  99:201 */       this.baseDisplayListID = GL.glGenLists(200);
/* 100:202 */       if (this.baseDisplayListID == 0) {
/* 101:202 */         this.displayListCaching = false;
/* 102:    */       }
/* 103:    */     }
/* 104:    */     try
/* 105:    */     {
/* 106:207 */       BufferedReader in = new BufferedReader(new InputStreamReader(
/* 107:208 */         fntFile));
/* 108:209 */       String info = in.readLine();
/* 109:210 */       String common = in.readLine();
/* 110:211 */       String page = in.readLine();
/* 111:    */       
/* 112:213 */       Map kerning = new HashMap(64);
/* 113:214 */       List charDefs = new ArrayList(255);
/* 114:215 */       int maxChar = 0;
/* 115:216 */       boolean done = false;
/* 116:217 */       while (!done)
/* 117:    */       {
/* 118:218 */         String line = in.readLine();
/* 119:219 */         if (line == null)
/* 120:    */         {
/* 121:220 */           done = true;
/* 122:    */         }
/* 123:    */         else
/* 124:    */         {
/* 125:222 */           if (!line.startsWith("chars c")) {
/* 126:224 */             if (line.startsWith("char"))
/* 127:    */             {
/* 128:225 */               CharDef def = parseChar(line);
/* 129:226 */               if (def != null)
/* 130:    */               {
/* 131:227 */                 maxChar = Math.max(maxChar, def.id);
/* 132:228 */                 charDefs.add(def);
/* 133:    */               }
/* 134:    */             }
/* 135:    */           }
/* 136:231 */           if (!line.startsWith("kernings c")) {
/* 137:233 */             if (line.startsWith("kerning"))
/* 138:    */             {
/* 139:234 */               StringTokenizer tokens = new StringTokenizer(line, " =");
/* 140:235 */               tokens.nextToken();
/* 141:236 */               tokens.nextToken();
/* 142:237 */               short first = Short.parseShort(tokens.nextToken());
/* 143:238 */               tokens.nextToken();
/* 144:239 */               int second = Integer.parseInt(tokens.nextToken());
/* 145:240 */               tokens.nextToken();
/* 146:241 */               int offset = Integer.parseInt(tokens.nextToken());
/* 147:242 */               List values = (List)kerning.get(new Short(first));
/* 148:243 */               if (values == null)
/* 149:    */               {
/* 150:244 */                 values = new ArrayList();
/* 151:245 */                 kerning.put(new Short(first), values);
/* 152:    */               }
/* 153:248 */               values.add(new Short((short)(offset << 8 | second)));
/* 154:    */             }
/* 155:    */           }
/* 156:    */         }
/* 157:    */       }
/* 158:253 */       this.chars = new CharDef[maxChar + 1];
/* 159:254 */       for (Iterator iter = charDefs.iterator(); iter.hasNext();)
/* 160:    */       {
/* 161:255 */         CharDef def = (CharDef)iter.next();
/* 162:256 */         this.chars[def.id] = def;
/* 163:    */       }
/* 164:260 */       for (Iterator iter = kerning.entrySet().iterator(); iter.hasNext();)
/* 165:    */       {
/* 166:261 */         Map.Entry entry = (Map.Entry)iter.next();
/* 167:262 */         short first = ((Short)entry.getKey()).shortValue();
/* 168:263 */         List valueList = (List)entry.getValue();
/* 169:264 */         short[] valueArray = new short[valueList.size()];
/* 170:265 */         int i = 0;
/* 171:266 */         for (Iterator valueIter = valueList.iterator(); valueIter.hasNext(); i++) {
/* 172:267 */           valueArray[i] = ((Short)valueIter.next()).shortValue();
/* 173:    */         }
/* 174:268 */         this.chars[first].kerning = valueArray;
/* 175:    */       }
/* 176:    */     }
/* 177:    */     catch (IOException e)
/* 178:    */     {
/* 179:271 */       Log.error(e);
/* 180:272 */       throw new SlickException("Failed to parse font file: " + fntFile);
/* 181:    */     }
/* 182:    */   }
/* 183:    */   
/* 184:    */   private CharDef parseChar(String line)
/* 185:    */     throws SlickException
/* 186:    */   {
/* 187:285 */     CharDef def = new CharDef(null);
/* 188:286 */     StringTokenizer tokens = new StringTokenizer(line, " =");
/* 189:    */     
/* 190:288 */     tokens.nextToken();
/* 191:289 */     tokens.nextToken();
/* 192:290 */     def.id = Short.parseShort(tokens.nextToken());
/* 193:291 */     if (def.id < 0) {
/* 194:292 */       return null;
/* 195:    */     }
/* 196:294 */     if (def.id > 255) {
/* 197:295 */       throw new SlickException("Invalid character '" + def.id + 
/* 198:296 */         "': AngelCodeFont does not support characters above " + 255);
/* 199:    */     }
/* 200:299 */     tokens.nextToken();
/* 201:300 */     def.x = Short.parseShort(tokens.nextToken());
/* 202:301 */     tokens.nextToken();
/* 203:302 */     def.y = Short.parseShort(tokens.nextToken());
/* 204:303 */     tokens.nextToken();
/* 205:304 */     def.width = Short.parseShort(tokens.nextToken());
/* 206:305 */     tokens.nextToken();
/* 207:306 */     def.height = Short.parseShort(tokens.nextToken());
/* 208:307 */     tokens.nextToken();
/* 209:308 */     def.xoffset = Short.parseShort(tokens.nextToken());
/* 210:309 */     tokens.nextToken();
/* 211:310 */     def.yoffset = Short.parseShort(tokens.nextToken());
/* 212:311 */     tokens.nextToken();
/* 213:312 */     def.xadvance = Short.parseShort(tokens.nextToken());
/* 214:    */     
/* 215:314 */     def.init();
/* 216:316 */     if (def.id != 32) {
/* 217:317 */       this.lineHeight = Math.max(def.height + def.yoffset, this.lineHeight);
/* 218:    */     }
/* 219:320 */     return def;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void drawString(float x, float y, String text)
/* 223:    */   {
/* 224:327 */     drawString(x, y, text, Color.white);
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void drawString(float x, float y, String text, Color col)
/* 228:    */   {
/* 229:335 */     drawString(x, y, text, col, 0, text.length() - 1);
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void drawString(float x, float y, String text, Color col, int startIndex, int endIndex)
/* 233:    */   {
/* 234:343 */     this.fontImage.bind();
/* 235:344 */     col.bind();
/* 236:    */     
/* 237:346 */     GL.glTranslatef(x, y, 0.0F);
/* 238:347 */     if ((this.displayListCaching) && (startIndex == 0) && (endIndex == text.length() - 1))
/* 239:    */     {
/* 240:348 */       DisplayList displayList = (DisplayList)this.displayLists.get(text);
/* 241:349 */       if (displayList != null)
/* 242:    */       {
/* 243:350 */         GL.glCallList(displayList.id);
/* 244:    */       }
/* 245:    */       else
/* 246:    */       {
/* 247:353 */         displayList = new DisplayList(null);
/* 248:354 */         displayList.text = text;
/* 249:355 */         int displayListCount = this.displayLists.size();
/* 250:356 */         if (displayListCount < 200)
/* 251:    */         {
/* 252:357 */           displayList.id = (this.baseDisplayListID + displayListCount);
/* 253:    */         }
/* 254:    */         else
/* 255:    */         {
/* 256:359 */           displayList.id = this.eldestDisplayListID;
/* 257:360 */           this.displayLists.remove(this.eldestDisplayList.text);
/* 258:    */         }
/* 259:363 */         this.displayLists.put(text, displayList);
/* 260:    */         
/* 261:365 */         GL.glNewList(displayList.id, 4865);
/* 262:366 */         render(text, startIndex, endIndex);
/* 263:367 */         GL.glEndList();
/* 264:    */       }
/* 265:    */     }
/* 266:    */     else
/* 267:    */     {
/* 268:370 */       render(text, startIndex, endIndex);
/* 269:    */     }
/* 270:372 */     GL.glTranslatef(-x, -y, 0.0F);
/* 271:    */   }
/* 272:    */   
/* 273:    */   private void render(String text, int start, int end)
/* 274:    */   {
/* 275:383 */     GL.glBegin(7);
/* 276:    */     
/* 277:385 */     int x = 0;int y = 0;
/* 278:386 */     CharDef lastCharDef = null;
/* 279:387 */     char[] data = text.toCharArray();
/* 280:388 */     for (int i = 0; i < data.length; i++)
/* 281:    */     {
/* 282:389 */       int id = data[i];
/* 283:390 */       if (id == 10)
/* 284:    */       {
/* 285:391 */         x = 0;
/* 286:392 */         y += getLineHeight();
/* 287:    */       }
/* 288:395 */       else if (id < this.chars.length)
/* 289:    */       {
/* 290:398 */         CharDef charDef = this.chars[id];
/* 291:399 */         if (charDef != null)
/* 292:    */         {
/* 293:403 */           if (lastCharDef != null) {
/* 294:403 */             x += lastCharDef.getKerning(id);
/* 295:    */           }
/* 296:404 */           lastCharDef = charDef;
/* 297:406 */           if ((i >= start) && (i <= end)) {
/* 298:407 */             charDef.draw(x, y);
/* 299:    */           }
/* 300:410 */           x += charDef.xadvance;
/* 301:    */         }
/* 302:    */       }
/* 303:    */     }
/* 304:412 */     GL.glEnd();
/* 305:    */   }
/* 306:    */   
/* 307:    */   public int getYOffset(String text)
/* 308:    */   {
/* 309:423 */     DisplayList displayList = null;
/* 310:424 */     if (this.displayListCaching)
/* 311:    */     {
/* 312:425 */       displayList = (DisplayList)this.displayLists.get(text);
/* 313:426 */       if ((displayList != null) && (displayList.yOffset != null)) {
/* 314:426 */         return displayList.yOffset.intValue();
/* 315:    */       }
/* 316:    */     }
/* 317:429 */     int stopIndex = text.indexOf('\n');
/* 318:430 */     if (stopIndex == -1) {
/* 319:430 */       stopIndex = text.length();
/* 320:    */     }
/* 321:432 */     int minYOffset = 10000;
/* 322:433 */     for (int i = 0; i < stopIndex; i++)
/* 323:    */     {
/* 324:434 */       int id = text.charAt(i);
/* 325:435 */       CharDef charDef = this.chars[id];
/* 326:436 */       if (charDef != null) {
/* 327:439 */         minYOffset = Math.min(charDef.yoffset, minYOffset);
/* 328:    */       }
/* 329:    */     }
/* 330:442 */     if (displayList != null) {
/* 331:442 */       displayList.yOffset = new Short((short)minYOffset);
/* 332:    */     }
/* 333:444 */     return minYOffset;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public int getHeight(String text)
/* 337:    */   {
/* 338:451 */     DisplayList displayList = null;
/* 339:452 */     if (this.displayListCaching)
/* 340:    */     {
/* 341:453 */       displayList = (DisplayList)this.displayLists.get(text);
/* 342:454 */       if ((displayList != null) && (displayList.height != null)) {
/* 343:454 */         return displayList.height.intValue();
/* 344:    */       }
/* 345:    */     }
/* 346:457 */     int lines = 0;
/* 347:458 */     int maxHeight = 0;
/* 348:459 */     for (int i = 0; i < text.length(); i++)
/* 349:    */     {
/* 350:460 */       int id = text.charAt(i);
/* 351:461 */       if (id == 10)
/* 352:    */       {
/* 353:462 */         lines++;
/* 354:463 */         maxHeight = 0;
/* 355:    */       }
/* 356:467 */       else if (id != 32)
/* 357:    */       {
/* 358:470 */         CharDef charDef = this.chars[id];
/* 359:471 */         if (charDef != null) {
/* 360:475 */           maxHeight = Math.max(charDef.height + charDef.yoffset, 
/* 361:476 */             maxHeight);
/* 362:    */         }
/* 363:    */       }
/* 364:    */     }
/* 365:479 */     maxHeight += lines * getLineHeight();
/* 366:481 */     if (displayList != null) {
/* 367:481 */       displayList.height = new Short((short)maxHeight);
/* 368:    */     }
/* 369:483 */     return maxHeight;
/* 370:    */   }
/* 371:    */   
/* 372:    */   public int getWidth(String text)
/* 373:    */   {
/* 374:490 */     DisplayList displayList = null;
/* 375:491 */     if (this.displayListCaching)
/* 376:    */     {
/* 377:492 */       displayList = (DisplayList)this.displayLists.get(text);
/* 378:493 */       if ((displayList != null) && (displayList.width != null)) {
/* 379:493 */         return displayList.width.intValue();
/* 380:    */       }
/* 381:    */     }
/* 382:496 */     int maxWidth = 0;
/* 383:497 */     int width = 0;
/* 384:498 */     CharDef lastCharDef = null;
/* 385:499 */     int i = 0;
/* 386:499 */     for (int n = text.length(); i < n; i++)
/* 387:    */     {
/* 388:500 */       int id = text.charAt(i);
/* 389:501 */       if (id == 10)
/* 390:    */       {
/* 391:502 */         width = 0;
/* 392:    */       }
/* 393:505 */       else if (id < this.chars.length)
/* 394:    */       {
/* 395:508 */         CharDef charDef = this.chars[id];
/* 396:509 */         if (charDef != null)
/* 397:    */         {
/* 398:513 */           if (lastCharDef != null) {
/* 399:513 */             width += lastCharDef.getKerning(id);
/* 400:    */           }
/* 401:514 */           lastCharDef = charDef;
/* 402:516 */           if (i < n - 1) {
/* 403:517 */             width += charDef.xadvance;
/* 404:    */           } else {
/* 405:519 */             width += charDef.width;
/* 406:    */           }
/* 407:521 */           maxWidth = Math.max(maxWidth, width);
/* 408:    */         }
/* 409:    */       }
/* 410:    */     }
/* 411:524 */     if (displayList != null) {
/* 412:524 */       displayList.width = new Short((short)maxWidth);
/* 413:    */     }
/* 414:526 */     return maxWidth;
/* 415:    */   }
/* 416:    */   
/* 417:    */   private class CharDef
/* 418:    */   {
/* 419:    */     public short id;
/* 420:    */     public short x;
/* 421:    */     public short y;
/* 422:    */     public short width;
/* 423:    */     public short height;
/* 424:    */     public short xoffset;
/* 425:    */     public short yoffset;
/* 426:    */     public short xadvance;
/* 427:    */     public Image image;
/* 428:    */     public short dlIndex;
/* 429:    */     public short[] kerning;
/* 430:    */     
/* 431:    */     private CharDef() {}
/* 432:    */     
/* 433:    */     public void init()
/* 434:    */     {
/* 435:565 */       this.image = AngelCodeFont.this.fontImage.getSubImage(this.x, this.y, this.width, this.height);
/* 436:    */     }
/* 437:    */     
/* 438:    */     public String toString()
/* 439:    */     {
/* 440:572 */       return "[CharDef id=" + this.id + " x=" + this.x + " y=" + this.y + "]";
/* 441:    */     }
/* 442:    */     
/* 443:    */     public void draw(float x, float y)
/* 444:    */     {
/* 445:584 */       this.image.drawEmbedded(x + this.xoffset, y + this.yoffset, this.width, this.height);
/* 446:    */     }
/* 447:    */     
/* 448:    */     public int getKerning(int otherCodePoint)
/* 449:    */     {
/* 450:593 */       if (this.kerning == null) {
/* 451:593 */         return 0;
/* 452:    */       }
/* 453:594 */       int low = 0;
/* 454:595 */       int high = this.kerning.length - 1;
/* 455:596 */       while (low <= high)
/* 456:    */       {
/* 457:597 */         int midIndex = low + high >>> 1;
/* 458:598 */         int value = this.kerning[midIndex];
/* 459:599 */         int foundCodePoint = value & 0xFF;
/* 460:600 */         if (foundCodePoint < otherCodePoint) {
/* 461:601 */           low = midIndex + 1;
/* 462:602 */         } else if (foundCodePoint > otherCodePoint) {
/* 463:603 */           high = midIndex - 1;
/* 464:    */         } else {
/* 465:605 */           return value >> 8;
/* 466:    */         }
/* 467:    */       }
/* 468:607 */       return 0;
/* 469:    */     }
/* 470:    */   }
/* 471:    */   
/* 472:    */   public int getLineHeight()
/* 473:    */   {
/* 474:615 */     return this.lineHeight;
/* 475:    */   }
/* 476:    */   
/* 477:    */   private static class DisplayList
/* 478:    */   {
/* 479:    */     int id;
/* 480:    */     Short yOffset;
/* 481:    */     Short width;
/* 482:    */     Short height;
/* 483:    */     String text;
/* 484:    */   }
/* 485:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.AngelCodeFont
 * JD-Core Version:    0.7.0.1
 */