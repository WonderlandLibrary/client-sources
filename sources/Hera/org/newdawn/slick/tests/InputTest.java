/*     */ package org.newdawn.slick.tests;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import org.newdawn.slick.AppGameContainer;
/*     */ import org.newdawn.slick.BasicGame;
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.Game;
/*     */ import org.newdawn.slick.GameContainer;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.Input;
/*     */ import org.newdawn.slick.SlickException;
/*     */ import org.newdawn.slick.util.Log;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InputTest
/*     */   extends BasicGame
/*     */ {
/*  21 */   private String message = "Press any key, mouse button, or drag the mouse";
/*     */   
/*  23 */   private ArrayList lines = new ArrayList();
/*     */   
/*     */   private boolean buttonDown;
/*     */   
/*     */   private float x;
/*     */   
/*     */   private float y;
/*     */   
/*  31 */   private Color[] cols = new Color[] { Color.red, Color.green, Color.blue, Color.white, Color.magenta, Color.cyan };
/*     */ 
/*     */   
/*     */   private int index;
/*     */ 
/*     */   
/*     */   private Input input;
/*     */ 
/*     */   
/*     */   private int ypos;
/*     */   
/*     */   private AppGameContainer app;
/*     */   
/*     */   private boolean space;
/*     */   
/*     */   private boolean lshift;
/*     */   
/*     */   private boolean rshift;
/*     */ 
/*     */   
/*     */   public InputTest() {
/*  52 */     super("Input Test");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GameContainer container) throws SlickException {
/*  59 */     if (container instanceof AppGameContainer) {
/*  60 */       this.app = (AppGameContainer)container;
/*     */     }
/*     */     
/*  63 */     this.input = container.getInput();
/*  64 */     this.x = 300.0F;
/*  65 */     this.y = 300.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(GameContainer container, Graphics g) {
/*  72 */     g.drawString("left shift down: " + this.lshift, 100.0F, 240.0F);
/*  73 */     g.drawString("right shift down: " + this.rshift, 100.0F, 260.0F);
/*  74 */     g.drawString("space down: " + this.space, 100.0F, 280.0F);
/*     */     
/*  76 */     g.setColor(Color.white);
/*  77 */     g.drawString(this.message, 10.0F, 50.0F);
/*  78 */     g.drawString("" + container.getInput().getMouseY(), 10.0F, 400.0F);
/*  79 */     g.drawString("Use the primary gamepad to control the blob, and hit a gamepad button to change the color", 10.0F, 90.0F);
/*     */     
/*  81 */     for (int i = 0; i < this.lines.size(); i++) {
/*  82 */       Line line = this.lines.get(i);
/*  83 */       line.draw(g);
/*     */     } 
/*     */     
/*  86 */     g.setColor(this.cols[this.index]);
/*  87 */     g.fillOval((int)this.x, (int)this.y, 50.0F, 50.0F);
/*  88 */     g.setColor(Color.yellow);
/*  89 */     g.fillRect(50.0F, (200 + this.ypos), 40.0F, 40.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(GameContainer container, int delta) {
/*  96 */     this.lshift = container.getInput().isKeyDown(42);
/*  97 */     this.rshift = container.getInput().isKeyDown(54);
/*  98 */     this.space = container.getInput().isKeyDown(57);
/*     */     
/* 100 */     if (this.controllerLeft[0]) {
/* 101 */       this.x -= delta * 0.1F;
/*     */     }
/* 103 */     if (this.controllerRight[0]) {
/* 104 */       this.x += delta * 0.1F;
/*     */     }
/* 106 */     if (this.controllerUp[0]) {
/* 107 */       this.y -= delta * 0.1F;
/*     */     }
/* 109 */     if (this.controllerDown[0]) {
/* 110 */       this.y += delta * 0.1F;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyPressed(int key, char c) {
/* 118 */     if (key == 1) {
/* 119 */       System.exit(0);
/*     */     }
/* 121 */     if (key == 59 && 
/* 122 */       this.app != null) {
/*     */       
/* 124 */       try { this.app.setDisplayMode(600, 600, false);
/* 125 */         this.app.reinit(); }
/* 126 */       catch (Exception e) { Log.error(e); }
/*     */     
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyReleased(int key, char c) {
/* 135 */     this.message = "You pressed key code " + key + " (character = " + c + ")";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mousePressed(int button, int x, int y) {
/* 142 */     if (button == 0) {
/* 143 */       this.buttonDown = true;
/*     */     }
/*     */     
/* 146 */     this.message = "Mouse pressed " + button + " " + x + "," + y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseReleased(int button, int x, int y) {
/* 153 */     if (button == 0) {
/* 154 */       this.buttonDown = false;
/*     */     }
/*     */     
/* 157 */     this.message = "Mouse released " + button + " " + x + "," + y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseClicked(int button, int x, int y, int clickCount) {
/* 164 */     System.out.println("CLICKED:" + x + "," + y + " " + clickCount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseWheelMoved(int change) {
/* 171 */     this.message = "Mouse wheel moved: " + change;
/*     */     
/* 173 */     if (change < 0) {
/* 174 */       this.ypos -= 10;
/*     */     }
/* 176 */     if (change > 0) {
/* 177 */       this.ypos += 10;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseMoved(int oldx, int oldy, int newx, int newy) {
/* 185 */     if (this.buttonDown) {
/* 186 */       this.lines.add(new Line(oldx, oldy, newx, newy));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class Line
/*     */   {
/*     */     private int oldx;
/*     */ 
/*     */ 
/*     */     
/*     */     private int oldy;
/*     */ 
/*     */ 
/*     */     
/*     */     private int newx;
/*     */ 
/*     */ 
/*     */     
/*     */     private int newy;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Line(int oldx, int oldy, int newx, int newy) {
/* 214 */       this.oldx = oldx;
/* 215 */       this.oldy = oldy;
/* 216 */       this.newx = newx;
/* 217 */       this.newy = newy;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void draw(Graphics g) {
/* 226 */       g.drawLine(this.oldx, this.oldy, this.newx, this.newy);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void controllerButtonPressed(int controller, int button) {
/* 234 */     super.controllerButtonPressed(controller, button);
/*     */     
/* 236 */     this.index++;
/* 237 */     this.index %= this.cols.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] argv) {
/*     */     try {
/* 247 */       AppGameContainer container = new AppGameContainer((Game)new InputTest());
/* 248 */       container.setDisplayMode(800, 600, false);
/* 249 */       container.start();
/* 250 */     } catch (SlickException e) {
/* 251 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\InputTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */