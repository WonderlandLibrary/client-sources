/*     */ package org.newdawn.slick.gui;
/*     */ 
/*     */ import org.lwjgl.Sys;
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.Font;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.geom.Rectangle;
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
/*     */ public class TextField
/*     */   extends AbstractComponent
/*     */ {
/*     */   private static final int INITIAL_KEY_REPEAT_INTERVAL = 400;
/*     */   private static final int KEY_REPEAT_INTERVAL = 50;
/*     */   private int width;
/*     */   private int height;
/*     */   protected int x;
/*     */   protected int y;
/*  34 */   private int maxCharacter = 10000;
/*     */ 
/*     */   
/*  37 */   private String value = "";
/*     */ 
/*     */   
/*     */   private Font font;
/*     */ 
/*     */   
/*  43 */   private Color border = Color.white;
/*     */ 
/*     */   
/*  46 */   private Color text = Color.white;
/*     */ 
/*     */   
/*  49 */   private Color background = new Color(0.0F, 0.0F, 0.0F, 0.5F);
/*     */ 
/*     */   
/*     */   private int cursorPos;
/*     */ 
/*     */   
/*     */   private boolean visibleCursor = true;
/*     */ 
/*     */   
/*  58 */   private int lastKey = -1;
/*     */ 
/*     */   
/*  61 */   private char lastChar = Character.MIN_VALUE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long repeatTimer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String oldText;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int oldCursorPos;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean consume = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextField(GUIContext container, Font font, int x, int y, int width, int height, ComponentListener listener) {
/*  95 */     this(container, font, x, y, width, height);
/*  96 */     addListener(listener);
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
/*     */   
/*     */   public TextField(GUIContext container, Font font, int x, int y, int width, int height) {
/* 117 */     super(container);
/*     */     
/* 119 */     this.font = font;
/*     */     
/* 121 */     setLocation(x, y);
/* 122 */     this.width = width;
/* 123 */     this.height = height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setConsumeEvents(boolean consume) {
/* 132 */     this.consume = consume;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deactivate() {
/* 139 */     setFocus(false);
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
/*     */   public void setLocation(int x, int y) {
/* 151 */     this.x = x;
/* 152 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getX() {
/* 161 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getY() {
/* 170 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 179 */     return this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 188 */     return this.height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBackgroundColor(Color color) {
/* 198 */     this.background = color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBorderColor(Color color) {
/* 208 */     this.border = color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextColor(Color color) {
/* 218 */     this.text = color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(GUIContext container, Graphics g) {
/* 226 */     if (this.lastKey != -1) {
/* 227 */       if (this.input.isKeyDown(this.lastKey)) {
/* 228 */         if (this.repeatTimer < System.currentTimeMillis()) {
/* 229 */           this.repeatTimer = System.currentTimeMillis() + 50L;
/* 230 */           keyPressed(this.lastKey, this.lastChar);
/*     */         } 
/*     */       } else {
/* 233 */         this.lastKey = -1;
/*     */       } 
/*     */     }
/* 236 */     Rectangle oldClip = g.getClip();
/* 237 */     g.setWorldClip(this.x, this.y, this.width, this.height);
/*     */ 
/*     */     
/* 240 */     Color clr = g.getColor();
/*     */     
/* 242 */     if (this.background != null) {
/* 243 */       g.setColor(this.background.multiply(clr));
/* 244 */       g.fillRect(this.x, this.y, this.width, this.height);
/*     */     } 
/* 246 */     g.setColor(this.text.multiply(clr));
/* 247 */     Font temp = g.getFont();
/*     */     
/* 249 */     int cpos = this.font.getWidth(this.value.substring(0, this.cursorPos));
/* 250 */     int tx = 0;
/* 251 */     if (cpos > this.width) {
/* 252 */       tx = this.width - cpos - this.font.getWidth("_");
/*     */     }
/*     */     
/* 255 */     g.translate((tx + 2), 0.0F);
/* 256 */     g.setFont(this.font);
/* 257 */     g.drawString(this.value, (this.x + 1), (this.y + 1));
/*     */     
/* 259 */     if (hasFocus() && this.visibleCursor) {
/* 260 */       g.drawString("_", (this.x + 1 + cpos + 2), (this.y + 1));
/*     */     }
/*     */     
/* 263 */     g.translate((-tx - 2), 0.0F);
/*     */     
/* 265 */     if (this.border != null) {
/* 266 */       g.setColor(this.border.multiply(clr));
/* 267 */       g.drawRect(this.x, this.y, this.width, this.height);
/*     */     } 
/* 269 */     g.setColor(clr);
/* 270 */     g.setFont(temp);
/* 271 */     g.clearWorldClip();
/* 272 */     g.setClip(oldClip);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getText() {
/* 281 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setText(String value) {
/* 291 */     this.value = value;
/* 292 */     if (this.cursorPos > value.length()) {
/* 293 */       this.cursorPos = value.length();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCursorPos(int pos) {
/* 304 */     this.cursorPos = pos;
/* 305 */     if (this.cursorPos > this.value.length()) {
/* 306 */       this.cursorPos = this.value.length();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCursorVisible(boolean visibleCursor) {
/* 317 */     this.visibleCursor = visibleCursor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxLength(int length) {
/* 327 */     this.maxCharacter = length;
/* 328 */     if (this.value.length() > this.maxCharacter) {
/* 329 */       this.value = this.value.substring(0, this.maxCharacter);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doPaste(String text) {
/* 339 */     recordOldPosition();
/*     */     
/* 341 */     for (int i = 0; i < text.length(); i++) {
/* 342 */       keyPressed(-1, text.charAt(i));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void recordOldPosition() {
/* 350 */     this.oldText = getText();
/* 351 */     this.oldCursorPos = this.cursorPos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doUndo(int oldCursorPos, String oldText) {
/* 361 */     if (oldText != null) {
/* 362 */       setText(oldText);
/* 363 */       setCursorPos(oldCursorPos);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyPressed(int key, char c) {
/* 371 */     if (hasFocus()) {
/* 372 */       if (key != -1) {
/*     */         
/* 374 */         if (key == 47 && (this.input.isKeyDown(29) || this.input.isKeyDown(157))) {
/*     */           
/* 376 */           String text = Sys.getClipboard();
/* 377 */           if (text != null) {
/* 378 */             doPaste(text);
/*     */           }
/*     */           return;
/*     */         } 
/* 382 */         if (key == 44 && (this.input.isKeyDown(29) || this.input.isKeyDown(157))) {
/*     */           
/* 384 */           if (this.oldText != null) {
/* 385 */             doUndo(this.oldCursorPos, this.oldText);
/*     */           }
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 391 */         if (this.input.isKeyDown(29) || this.input.isKeyDown(157)) {
/*     */           return;
/*     */         }
/* 394 */         if (this.input.isKeyDown(56) || this.input.isKeyDown(184)) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */       
/* 399 */       if (this.lastKey != key) {
/* 400 */         this.lastKey = key;
/* 401 */         this.repeatTimer = System.currentTimeMillis() + 400L;
/*     */       } else {
/* 403 */         this.repeatTimer = System.currentTimeMillis() + 50L;
/*     */       } 
/* 405 */       this.lastChar = c;
/*     */       
/* 407 */       if (key == 203) {
/* 408 */         if (this.cursorPos > 0) {
/* 409 */           this.cursorPos--;
/*     */         }
/*     */         
/* 412 */         if (this.consume) {
/* 413 */           this.container.getInput().consumeEvent();
/*     */         }
/* 415 */       } else if (key == 205) {
/* 416 */         if (this.cursorPos < this.value.length()) {
/* 417 */           this.cursorPos++;
/*     */         }
/*     */         
/* 420 */         if (this.consume) {
/* 421 */           this.container.getInput().consumeEvent();
/*     */         }
/* 423 */       } else if (key == 14) {
/* 424 */         if (this.cursorPos > 0 && this.value.length() > 0) {
/* 425 */           if (this.cursorPos < this.value.length()) {
/* 426 */             this.value = this.value.substring(0, this.cursorPos - 1) + this.value.substring(this.cursorPos);
/*     */           } else {
/*     */             
/* 429 */             this.value = this.value.substring(0, this.cursorPos - 1);
/*     */           } 
/* 431 */           this.cursorPos--;
/*     */         } 
/*     */         
/* 434 */         if (this.consume) {
/* 435 */           this.container.getInput().consumeEvent();
/*     */         }
/* 437 */       } else if (key == 211) {
/* 438 */         if (this.value.length() > this.cursorPos) {
/* 439 */           this.value = this.value.substring(0, this.cursorPos) + this.value.substring(this.cursorPos + 1);
/*     */         }
/*     */         
/* 442 */         if (this.consume) {
/* 443 */           this.container.getInput().consumeEvent();
/*     */         }
/* 445 */       } else if (c < '' && c > '\037' && this.value.length() < this.maxCharacter) {
/* 446 */         if (this.cursorPos < this.value.length()) {
/* 447 */           this.value = this.value.substring(0, this.cursorPos) + c + this.value.substring(this.cursorPos);
/*     */         } else {
/*     */           
/* 450 */           this.value = this.value.substring(0, this.cursorPos) + c;
/*     */         } 
/* 452 */         this.cursorPos++;
/*     */         
/* 454 */         if (this.consume) {
/* 455 */           this.container.getInput().consumeEvent();
/*     */         }
/* 457 */       } else if (key == 28) {
/* 458 */         notifyListeners();
/*     */         
/* 460 */         if (this.consume) {
/* 461 */           this.container.getInput().consumeEvent();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFocus(boolean focus) {
/* 472 */     this.lastKey = -1;
/*     */     
/* 474 */     super.setFocus(focus);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\gui\TextField.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */