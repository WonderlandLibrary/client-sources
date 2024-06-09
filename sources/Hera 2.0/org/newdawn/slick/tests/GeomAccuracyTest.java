/*     */ package org.newdawn.slick.tests;
/*     */ 
/*     */ import org.newdawn.slick.AppGameContainer;
/*     */ import org.newdawn.slick.BasicGame;
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.Game;
/*     */ import org.newdawn.slick.GameContainer;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.Image;
/*     */ import org.newdawn.slick.SlickException;
/*     */ import org.newdawn.slick.geom.Ellipse;
/*     */ import org.newdawn.slick.geom.Rectangle;
/*     */ import org.newdawn.slick.geom.RoundedRectangle;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GeomAccuracyTest
/*     */   extends BasicGame
/*     */ {
/*     */   private GameContainer container;
/*     */   private Color geomColor;
/*     */   private Color overlayColor;
/*     */   private boolean hideOverlay;
/*     */   private int colorIndex;
/*     */   private int curTest;
/*     */   private static final int NUMTESTS = 3;
/*     */   private Image magImage;
/*     */   
/*     */   public GeomAccuracyTest() {
/*  49 */     super("Geometry Accuracy Tests");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GameContainer container) throws SlickException {
/*  56 */     this.container = container;
/*     */     
/*  58 */     this.geomColor = Color.magenta;
/*  59 */     this.overlayColor = Color.white;
/*     */     
/*  61 */     this.magImage = new Image(21, 21);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(GameContainer container, Graphics g) {
/*  69 */     String text = new String();
/*     */     
/*  71 */     switch (this.curTest) {
/*     */       
/*     */       case 0:
/*  74 */         text = "Rectangles";
/*  75 */         rectTest(g);
/*     */         break;
/*     */       
/*     */       case 1:
/*  79 */         text = "Ovals";
/*  80 */         ovalTest(g);
/*     */         break;
/*     */       
/*     */       case 2:
/*  84 */         text = "Arcs";
/*  85 */         arcTest(g);
/*     */         break;
/*     */     } 
/*     */     
/*  89 */     g.setColor(Color.white);
/*  90 */     g.drawString("Press T to toggle overlay", 200.0F, 55.0F);
/*  91 */     g.drawString("Press N to switch tests", 200.0F, 35.0F);
/*  92 */     g.drawString("Press C to cycle drawing colors", 200.0F, 15.0F);
/*  93 */     g.drawString("Current Test:", 400.0F, 35.0F);
/*  94 */     g.setColor(Color.blue);
/*  95 */     g.drawString(text, 485.0F, 35.0F);
/*     */     
/*  97 */     g.setColor(Color.white);
/*  98 */     g.drawString("Normal:", 10.0F, 150.0F);
/*  99 */     g.drawString("Filled:", 10.0F, 300.0F);
/*     */     
/* 101 */     g.drawString("Drawn with Graphics context", 125.0F, 400.0F);
/* 102 */     g.drawString("Drawn using Shapes", 450.0F, 400.0F);
/*     */ 
/*     */     
/* 105 */     g.copyArea(this.magImage, container.getInput().getMouseX() - 10, container.getInput().getMouseY() - 10);
/* 106 */     this.magImage.draw(351.0F, 451.0F, 5.0F);
/* 107 */     g.drawString("Mag Area -", 250.0F, 475.0F);
/* 108 */     g.setColor(Color.darkGray);
/* 109 */     g.drawRect(350.0F, 450.0F, 106.0F, 106.0F);
/*     */     
/* 111 */     g.setColor(Color.white);
/* 112 */     g.drawString("NOTE:", 500.0F, 450.0F);
/* 113 */     g.drawString("lines should be flush with edges", 525.0F, 470.0F);
/* 114 */     g.drawString("corners should be symetric", 525.0F, 490.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void arcTest(Graphics g) {
/* 124 */     if (!this.hideOverlay) {
/* 125 */       g.setColor(this.overlayColor);
/* 126 */       g.drawLine(198.0F, 100.0F, 198.0F, 198.0F);
/* 127 */       g.drawLine(100.0F, 198.0F, 198.0F, 198.0F);
/*     */     } 
/*     */     
/* 130 */     g.setColor(this.geomColor);
/* 131 */     g.drawArc(100.0F, 100.0F, 99.0F, 99.0F, 0.0F, 90.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void ovalTest(Graphics g) {
/* 142 */     g.setColor(this.geomColor);
/* 143 */     g.drawOval(100.0F, 100.0F, 99.0F, 99.0F);
/* 144 */     g.fillOval(100.0F, 250.0F, 99.0F, 99.0F);
/*     */ 
/*     */     
/* 147 */     Ellipse elip = new Ellipse(449.0F, 149.0F, 49.0F, 49.0F);
/* 148 */     g.draw((Shape)elip);
/* 149 */     elip = new Ellipse(449.0F, 299.0F, 49.0F, 49.0F);
/* 150 */     g.fill((Shape)elip);
/*     */     
/* 152 */     if (!this.hideOverlay) {
/* 153 */       g.setColor(this.overlayColor);
/* 154 */       g.drawLine(100.0F, 149.0F, 198.0F, 149.0F);
/* 155 */       g.drawLine(149.0F, 100.0F, 149.0F, 198.0F);
/*     */       
/* 157 */       g.drawLine(100.0F, 299.0F, 198.0F, 299.0F);
/* 158 */       g.drawLine(149.0F, 250.0F, 149.0F, 348.0F);
/*     */       
/* 160 */       g.drawLine(400.0F, 149.0F, 498.0F, 149.0F);
/* 161 */       g.drawLine(449.0F, 100.0F, 449.0F, 198.0F);
/*     */       
/* 163 */       g.drawLine(400.0F, 299.0F, 498.0F, 299.0F);
/* 164 */       g.drawLine(449.0F, 250.0F, 449.0F, 348.0F);
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
/*     */   void rectTest(Graphics g) {
/* 176 */     g.setColor(this.geomColor);
/*     */ 
/*     */     
/* 179 */     g.drawRect(100.0F, 100.0F, 99.0F, 99.0F);
/* 180 */     g.fillRect(100.0F, 250.0F, 99.0F, 99.0F);
/*     */     
/* 182 */     g.drawRoundRect(250.0F, 100.0F, 99.0F, 99.0F, 10);
/* 183 */     g.fillRoundRect(250.0F, 250.0F, 99.0F, 99.0F, 10);
/*     */ 
/*     */     
/* 186 */     Rectangle rect = new Rectangle(400.0F, 100.0F, 99.0F, 99.0F);
/* 187 */     g.draw((Shape)rect);
/* 188 */     rect = new Rectangle(400.0F, 250.0F, 99.0F, 99.0F);
/* 189 */     g.fill((Shape)rect);
/*     */     
/* 191 */     RoundedRectangle rrect = new RoundedRectangle(550.0F, 100.0F, 99.0F, 99.0F, 10.0F);
/* 192 */     g.draw((Shape)rrect);
/* 193 */     rrect = new RoundedRectangle(550.0F, 250.0F, 99.0F, 99.0F, 10.0F);
/* 194 */     g.fill((Shape)rrect);
/*     */ 
/*     */     
/* 197 */     if (!this.hideOverlay) {
/* 198 */       g.setColor(this.overlayColor);
/*     */ 
/*     */       
/* 201 */       g.drawLine(100.0F, 149.0F, 198.0F, 149.0F);
/* 202 */       g.drawLine(149.0F, 100.0F, 149.0F, 198.0F);
/*     */       
/* 204 */       g.drawLine(250.0F, 149.0F, 348.0F, 149.0F);
/* 205 */       g.drawLine(299.0F, 100.0F, 299.0F, 198.0F);
/*     */       
/* 207 */       g.drawLine(400.0F, 149.0F, 498.0F, 149.0F);
/* 208 */       g.drawLine(449.0F, 100.0F, 449.0F, 198.0F);
/*     */       
/* 210 */       g.drawLine(550.0F, 149.0F, 648.0F, 149.0F);
/* 211 */       g.drawLine(599.0F, 100.0F, 599.0F, 198.0F);
/*     */ 
/*     */       
/* 214 */       g.drawLine(100.0F, 299.0F, 198.0F, 299.0F);
/* 215 */       g.drawLine(149.0F, 250.0F, 149.0F, 348.0F);
/*     */       
/* 217 */       g.drawLine(250.0F, 299.0F, 348.0F, 299.0F);
/* 218 */       g.drawLine(299.0F, 250.0F, 299.0F, 348.0F);
/*     */       
/* 220 */       g.drawLine(400.0F, 299.0F, 498.0F, 299.0F);
/* 221 */       g.drawLine(449.0F, 250.0F, 449.0F, 348.0F);
/*     */       
/* 223 */       g.drawLine(550.0F, 299.0F, 648.0F, 299.0F);
/* 224 */       g.drawLine(599.0F, 250.0F, 599.0F, 348.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(GameContainer container, int delta) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyPressed(int key, char c) {
/* 239 */     if (key == 1) {
/* 240 */       System.exit(0);
/*     */     }
/*     */     
/* 243 */     if (key == 49) {
/* 244 */       this.curTest++;
/* 245 */       this.curTest %= 3;
/*     */     } 
/*     */     
/* 248 */     if (key == 46) {
/* 249 */       this.colorIndex++;
/*     */       
/* 251 */       this.colorIndex %= 4;
/* 252 */       setColors();
/*     */     } 
/*     */     
/* 255 */     if (key == 20) {
/* 256 */       this.hideOverlay = !this.hideOverlay;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setColors() {
/* 266 */     switch (this.colorIndex) {
/*     */       
/*     */       case 0:
/* 269 */         this.overlayColor = Color.white;
/* 270 */         this.geomColor = Color.magenta;
/*     */         break;
/*     */       
/*     */       case 1:
/* 274 */         this.overlayColor = Color.magenta;
/* 275 */         this.geomColor = Color.white;
/*     */         break;
/*     */       
/*     */       case 2:
/* 279 */         this.overlayColor = Color.red;
/* 280 */         this.geomColor = Color.green;
/*     */         break;
/*     */       
/*     */       case 3:
/* 284 */         this.overlayColor = Color.red;
/* 285 */         this.geomColor = Color.white;
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] argv) {
/*     */     try {
/* 298 */       AppGameContainer container = new AppGameContainer((Game)new GeomAccuracyTest());
/* 299 */       container.setDisplayMode(800, 600, false);
/* 300 */       container.start();
/* 301 */     } catch (SlickException e) {
/* 302 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\GeomAccuracyTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */