/*     */ package org.newdawn.slick.tests;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import org.newdawn.slick.AppGameContainer;
/*     */ import org.newdawn.slick.BasicGame;
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.Game;
/*     */ import org.newdawn.slick.GameContainer;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.SlickException;
/*     */ import org.newdawn.slick.geom.Circle;
/*     */ import org.newdawn.slick.geom.Ellipse;
/*     */ import org.newdawn.slick.geom.Polygon;
/*     */ import org.newdawn.slick.geom.Rectangle;
/*     */ import org.newdawn.slick.geom.RoundedRectangle;
/*     */ import org.newdawn.slick.geom.Shape;
/*     */ import org.newdawn.slick.opengl.renderer.Renderer;
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
/*     */ public class ShapeTest
/*     */   extends BasicGame
/*     */ {
/*     */   private Rectangle rect;
/*     */   private RoundedRectangle roundRect;
/*     */   private Ellipse ellipse;
/*     */   private Circle circle;
/*     */   private Polygon polygon;
/*     */   private ArrayList shapes;
/*     */   private boolean[] keys;
/*     */   private char[] lastChar;
/*  43 */   private Polygon randomShape = new Polygon();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShapeTest() {
/*  49 */     super("Geom Test");
/*     */   }
/*     */   
/*     */   public void createPoly(float x, float y) {
/*  53 */     int size = 20;
/*  54 */     int change = 10;
/*     */     
/*  56 */     this.randomShape = new Polygon();
/*     */     
/*  58 */     this.randomShape.addPoint((0 + (int)(Math.random() * change)), (0 + (int)(Math.random() * change)));
/*  59 */     this.randomShape.addPoint((size - (int)(Math.random() * change)), (0 + (int)(Math.random() * change)));
/*  60 */     this.randomShape.addPoint((size - (int)(Math.random() * change)), (size - (int)(Math.random() * change)));
/*  61 */     this.randomShape.addPoint((0 + (int)(Math.random() * change)), (size - (int)(Math.random() * change)));
/*     */ 
/*     */     
/*  64 */     this.randomShape.setCenterX(x);
/*  65 */     this.randomShape.setCenterY(y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GameContainer container) throws SlickException {
/*  72 */     this.shapes = new ArrayList();
/*  73 */     this.rect = new Rectangle(10.0F, 10.0F, 100.0F, 80.0F);
/*  74 */     this.shapes.add(this.rect);
/*  75 */     this.roundRect = new RoundedRectangle(150.0F, 10.0F, 60.0F, 80.0F, 20.0F);
/*  76 */     this.shapes.add(this.roundRect);
/*  77 */     this.ellipse = new Ellipse(350.0F, 40.0F, 50.0F, 30.0F);
/*  78 */     this.shapes.add(this.ellipse);
/*  79 */     this.circle = new Circle(470.0F, 60.0F, 50.0F);
/*  80 */     this.shapes.add(this.circle);
/*  81 */     this.polygon = new Polygon(new float[] { 550.0F, 10.0F, 600.0F, 40.0F, 620.0F, 100.0F, 570.0F, 130.0F });
/*  82 */     this.shapes.add(this.polygon);
/*     */     
/*  84 */     this.keys = new boolean[256];
/*  85 */     this.lastChar = new char[256];
/*  86 */     createPoly(200.0F, 200.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(GameContainer container, Graphics g) {
/*  93 */     g.setColor(Color.green);
/*     */     
/*  95 */     for (int i = 0; i < this.shapes.size(); i++) {
/*  96 */       g.fill(this.shapes.get(i));
/*     */     }
/*  98 */     g.fill((Shape)this.randomShape);
/*  99 */     g.setColor(Color.black);
/* 100 */     g.setAntiAlias(true);
/* 101 */     g.draw((Shape)this.randomShape);
/* 102 */     g.setAntiAlias(false);
/*     */     
/* 104 */     g.setColor(Color.white);
/* 105 */     g.drawString("keys", 10.0F, 300.0F);
/* 106 */     g.drawString("wasd - move rectangle", 10.0F, 315.0F);
/* 107 */     g.drawString("WASD - resize rectangle", 10.0F, 330.0F);
/* 108 */     g.drawString("tgfh - move rounded rectangle", 10.0F, 345.0F);
/* 109 */     g.drawString("TGFH - resize rounded rectangle", 10.0F, 360.0F);
/* 110 */     g.drawString("ry - resize corner radius on rounded rectangle", 10.0F, 375.0F);
/* 111 */     g.drawString("ikjl - move ellipse", 10.0F, 390.0F);
/* 112 */     g.drawString("IKJL - resize ellipse", 10.0F, 405.0F);
/* 113 */     g.drawString("Arrows - move circle", 10.0F, 420.0F);
/* 114 */     g.drawString("Page Up/Page Down - resize circle", 10.0F, 435.0F);
/* 115 */     g.drawString("numpad 8546 - move polygon", 10.0F, 450.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(GameContainer container, int delta) {
/* 123 */     createPoly(200.0F, 200.0F);
/* 124 */     if (this.keys[1]) {
/* 125 */       System.exit(0);
/*     */     }
/* 127 */     if (this.keys[17]) {
/* 128 */       if (this.lastChar[17] == 'w') {
/* 129 */         this.rect.setY(this.rect.getY() - 1.0F);
/*     */       } else {
/*     */         
/* 132 */         this.rect.setHeight(this.rect.getHeight() - 1.0F);
/*     */       } 
/*     */     }
/* 135 */     if (this.keys[31]) {
/* 136 */       if (this.lastChar[31] == 's') {
/* 137 */         this.rect.setY(this.rect.getY() + 1.0F);
/*     */       } else {
/*     */         
/* 140 */         this.rect.setHeight(this.rect.getHeight() + 1.0F);
/*     */       } 
/*     */     }
/* 143 */     if (this.keys[30]) {
/* 144 */       if (this.lastChar[30] == 'a') {
/* 145 */         this.rect.setX(this.rect.getX() - 1.0F);
/*     */       } else {
/*     */         
/* 148 */         this.rect.setWidth(this.rect.getWidth() - 1.0F);
/*     */       } 
/*     */     }
/* 151 */     if (this.keys[32]) {
/* 152 */       if (this.lastChar[32] == 'd') {
/* 153 */         this.rect.setX(this.rect.getX() + 1.0F);
/*     */       } else {
/*     */         
/* 156 */         this.rect.setWidth(this.rect.getWidth() + 1.0F);
/*     */       } 
/*     */     }
/* 159 */     if (this.keys[20]) {
/* 160 */       if (this.lastChar[20] == 't') {
/* 161 */         this.roundRect.setY(this.roundRect.getY() - 1.0F);
/*     */       } else {
/*     */         
/* 164 */         this.roundRect.setHeight(this.roundRect.getHeight() - 1.0F);
/*     */       } 
/*     */     }
/* 167 */     if (this.keys[34]) {
/* 168 */       if (this.lastChar[34] == 'g') {
/* 169 */         this.roundRect.setY(this.roundRect.getY() + 1.0F);
/*     */       } else {
/*     */         
/* 172 */         this.roundRect.setHeight(this.roundRect.getHeight() + 1.0F);
/*     */       } 
/*     */     }
/* 175 */     if (this.keys[33]) {
/* 176 */       if (this.lastChar[33] == 'f') {
/* 177 */         this.roundRect.setX(this.roundRect.getX() - 1.0F);
/*     */       } else {
/*     */         
/* 180 */         this.roundRect.setWidth(this.roundRect.getWidth() - 1.0F);
/*     */       } 
/*     */     }
/* 183 */     if (this.keys[35]) {
/* 184 */       if (this.lastChar[35] == 'h') {
/* 185 */         this.roundRect.setX(this.roundRect.getX() + 1.0F);
/*     */       } else {
/*     */         
/* 188 */         this.roundRect.setWidth(this.roundRect.getWidth() + 1.0F);
/*     */       } 
/*     */     }
/* 191 */     if (this.keys[19]) {
/* 192 */       this.roundRect.setCornerRadius(this.roundRect.getCornerRadius() - 1.0F);
/*     */     }
/* 194 */     if (this.keys[21]) {
/* 195 */       this.roundRect.setCornerRadius(this.roundRect.getCornerRadius() + 1.0F);
/*     */     }
/* 197 */     if (this.keys[23]) {
/* 198 */       if (this.lastChar[23] == 'i') {
/* 199 */         this.ellipse.setCenterY(this.ellipse.getCenterY() - 1.0F);
/*     */       } else {
/*     */         
/* 202 */         this.ellipse.setRadius2(this.ellipse.getRadius2() - 1.0F);
/*     */       } 
/*     */     }
/* 205 */     if (this.keys[37]) {
/* 206 */       if (this.lastChar[37] == 'k') {
/* 207 */         this.ellipse.setCenterY(this.ellipse.getCenterY() + 1.0F);
/*     */       } else {
/*     */         
/* 210 */         this.ellipse.setRadius2(this.ellipse.getRadius2() + 1.0F);
/*     */       } 
/*     */     }
/* 213 */     if (this.keys[36]) {
/* 214 */       if (this.lastChar[36] == 'j') {
/* 215 */         this.ellipse.setCenterX(this.ellipse.getCenterX() - 1.0F);
/*     */       } else {
/*     */         
/* 218 */         this.ellipse.setRadius1(this.ellipse.getRadius1() - 1.0F);
/*     */       } 
/*     */     }
/* 221 */     if (this.keys[38]) {
/* 222 */       if (this.lastChar[38] == 'l') {
/* 223 */         this.ellipse.setCenterX(this.ellipse.getCenterX() + 1.0F);
/*     */       } else {
/*     */         
/* 226 */         this.ellipse.setRadius1(this.ellipse.getRadius1() + 1.0F);
/*     */       } 
/*     */     }
/* 229 */     if (this.keys[200]) {
/* 230 */       this.circle.setCenterY(this.circle.getCenterY() - 1.0F);
/*     */     }
/* 232 */     if (this.keys[208]) {
/* 233 */       this.circle.setCenterY(this.circle.getCenterY() + 1.0F);
/*     */     }
/* 235 */     if (this.keys[203]) {
/* 236 */       this.circle.setCenterX(this.circle.getCenterX() - 1.0F);
/*     */     }
/* 238 */     if (this.keys[205]) {
/* 239 */       this.circle.setCenterX(this.circle.getCenterX() + 1.0F);
/*     */     }
/* 241 */     if (this.keys[201]) {
/* 242 */       this.circle.setRadius(this.circle.getRadius() - 1.0F);
/*     */     }
/* 244 */     if (this.keys[209]) {
/* 245 */       this.circle.setRadius(this.circle.getRadius() + 1.0F);
/*     */     }
/* 247 */     if (this.keys[72]) {
/* 248 */       this.polygon.setY(this.polygon.getY() - 1.0F);
/*     */     }
/* 250 */     if (this.keys[76]) {
/* 251 */       this.polygon.setY(this.polygon.getY() + 1.0F);
/*     */     }
/* 253 */     if (this.keys[75]) {
/* 254 */       this.polygon.setX(this.polygon.getX() - 1.0F);
/*     */     }
/* 256 */     if (this.keys[77]) {
/* 257 */       this.polygon.setX(this.polygon.getX() + 1.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyPressed(int key, char c) {
/* 265 */     this.keys[key] = true;
/* 266 */     this.lastChar[key] = c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyReleased(int key, char c) {
/* 273 */     this.keys[key] = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] argv) {
/*     */     try {
/* 283 */       Renderer.setRenderer(2);
/* 284 */       AppGameContainer container = new AppGameContainer((Game)new ShapeTest());
/* 285 */       container.setDisplayMode(800, 600, false);
/* 286 */       container.start();
/* 287 */     } catch (SlickException e) {
/* 288 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\ShapeTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */