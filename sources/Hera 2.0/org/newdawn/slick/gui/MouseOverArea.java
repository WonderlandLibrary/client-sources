/*     */ package org.newdawn.slick.gui;
/*     */ 
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.Image;
/*     */ import org.newdawn.slick.Input;
/*     */ import org.newdawn.slick.Sound;
/*     */ import org.newdawn.slick.geom.Rectangle;
/*     */ import org.newdawn.slick.geom.Shape;
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
/*     */ public class MouseOverArea
/*     */   extends AbstractComponent
/*     */ {
/*     */   private static final int NORMAL = 1;
/*     */   private static final int MOUSE_DOWN = 2;
/*     */   private static final int MOUSE_OVER = 3;
/*     */   private Image normalImage;
/*     */   private Image mouseOverImage;
/*     */   private Image mouseDownImage;
/*  36 */   private Color normalColor = Color.white;
/*     */ 
/*     */   
/*  39 */   private Color mouseOverColor = Color.white;
/*     */ 
/*     */   
/*  42 */   private Color mouseDownColor = Color.white;
/*     */ 
/*     */   
/*     */   private Sound mouseOverSound;
/*     */ 
/*     */   
/*     */   private Sound mouseDownSound;
/*     */ 
/*     */   
/*     */   private Shape area;
/*     */ 
/*     */   
/*     */   private Image currentImage;
/*     */ 
/*     */   
/*     */   private Color currentColor;
/*     */ 
/*     */   
/*     */   private boolean over;
/*     */ 
/*     */   
/*     */   private boolean mouseDown;
/*     */ 
/*     */   
/*  66 */   private int state = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean mouseUp;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MouseOverArea(GUIContext container, Image image, int x, int y, ComponentListener listener) {
/*  86 */     this(container, image, x, y, image.getWidth(), image.getHeight());
/*  87 */     addListener(listener);
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
/*     */   public MouseOverArea(GUIContext container, Image image, int x, int y) {
/* 103 */     this(container, image, x, y, image.getWidth(), image.getHeight());
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
/*     */ 
/*     */   
/*     */   public MouseOverArea(GUIContext container, Image image, int x, int y, int width, int height, ComponentListener listener) {
/* 126 */     this(container, image, x, y, width, height);
/* 127 */     addListener(listener);
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
/*     */   public MouseOverArea(GUIContext container, Image image, int x, int y, int width, int height) {
/* 148 */     this(container, image, (Shape)new Rectangle(x, y, width, height));
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
/*     */   public MouseOverArea(GUIContext container, Image image, Shape shape) {
/* 162 */     super(container);
/*     */     
/* 164 */     this.area = shape;
/* 165 */     this.normalImage = image;
/* 166 */     this.currentImage = image;
/* 167 */     this.mouseOverImage = image;
/* 168 */     this.mouseDownImage = image;
/*     */     
/* 170 */     this.currentColor = this.normalColor;
/*     */     
/* 172 */     this.state = 1;
/* 173 */     Input input = container.getInput();
/* 174 */     this.over = this.area.contains(input.getMouseX(), input.getMouseY());
/* 175 */     this.mouseDown = input.isMouseButtonDown(0);
/* 176 */     updateImage();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLocation(float x, float y) {
/* 186 */     if (this.area != null) {
/* 187 */       this.area.setX(x);
/* 188 */       this.area.setY(y);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setX(float x) {
/* 198 */     this.area.setX(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setY(float y) {
/* 207 */     this.area.setY(y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getX() {
/* 216 */     return (int)this.area.getX();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getY() {
/* 225 */     return (int)this.area.getY();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNormalColor(Color color) {
/* 235 */     this.normalColor = color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMouseOverColor(Color color) {
/* 245 */     this.mouseOverColor = color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMouseDownColor(Color color) {
/* 255 */     this.mouseDownColor = color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNormalImage(Image image) {
/* 265 */     this.normalImage = image;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMouseOverImage(Image image) {
/* 275 */     this.mouseOverImage = image;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMouseDownImage(Image image) {
/* 285 */     this.mouseDownImage = image;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(GUIContext container, Graphics g) {
/* 293 */     if (this.currentImage != null) {
/*     */       
/* 295 */       int xp = (int)(this.area.getX() + ((getWidth() - this.currentImage.getWidth()) / 2));
/* 296 */       int yp = (int)(this.area.getY() + ((getHeight() - this.currentImage.getHeight()) / 2));
/*     */       
/* 298 */       this.currentImage.draw(xp, yp, this.currentColor);
/*     */     } else {
/* 300 */       g.setColor(this.currentColor);
/* 301 */       g.fill(this.area);
/*     */     } 
/* 303 */     updateImage();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateImage() {
/* 310 */     if (!this.over) {
/* 311 */       this.currentImage = this.normalImage;
/* 312 */       this.currentColor = this.normalColor;
/* 313 */       this.state = 1;
/* 314 */       this.mouseUp = false;
/*     */     } else {
/* 316 */       if (this.mouseDown) {
/* 317 */         if (this.state != 2 && this.mouseUp) {
/* 318 */           if (this.mouseDownSound != null) {
/* 319 */             this.mouseDownSound.play();
/*     */           }
/* 321 */           this.currentImage = this.mouseDownImage;
/* 322 */           this.currentColor = this.mouseDownColor;
/* 323 */           this.state = 2;
/*     */           
/* 325 */           notifyListeners();
/* 326 */           this.mouseUp = false;
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/* 331 */       this.mouseUp = true;
/* 332 */       if (this.state != 3) {
/* 333 */         if (this.mouseOverSound != null) {
/* 334 */           this.mouseOverSound.play();
/*     */         }
/* 336 */         this.currentImage = this.mouseOverImage;
/* 337 */         this.currentColor = this.mouseOverColor;
/* 338 */         this.state = 3;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 343 */     this.mouseDown = false;
/* 344 */     this.state = 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMouseOverSound(Sound sound) {
/* 354 */     this.mouseOverSound = sound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMouseDownSound(Sound sound) {
/* 364 */     this.mouseDownSound = sound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseMoved(int oldx, int oldy, int newx, int newy) {
/* 371 */     this.over = this.area.contains(newx, newy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseDragged(int oldx, int oldy, int newx, int newy) {
/* 378 */     mouseMoved(oldx, oldy, newx, newy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mousePressed(int button, int mx, int my) {
/* 385 */     this.over = this.area.contains(mx, my);
/* 386 */     if (button == 0) {
/* 387 */       this.mouseDown = true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseReleased(int button, int mx, int my) {
/* 395 */     this.over = this.area.contains(mx, my);
/* 396 */     if (button == 0) {
/* 397 */       this.mouseDown = false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 405 */     return (int)(this.area.getMaxY() - this.area.getY());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 412 */     return (int)(this.area.getMaxX() - this.area.getX());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMouseOver() {
/* 421 */     return this.over;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLocation(int x, int y) {
/* 431 */     setLocation(x, y);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\gui\MouseOverArea.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */