/*   1:    */ package org.newdawn.slick.tests;
/*   2:    */ 
/*   3:    */ import org.newdawn.slick.AppGameContainer;
/*   4:    */ import org.newdawn.slick.BasicGame;
/*   5:    */ import org.newdawn.slick.Color;
/*   6:    */ import org.newdawn.slick.GameContainer;
/*   7:    */ import org.newdawn.slick.Graphics;
/*   8:    */ import org.newdawn.slick.SlickException;
/*   9:    */ import org.newdawn.slick.geom.Curve;
/*  10:    */ import org.newdawn.slick.geom.Polygon;
/*  11:    */ import org.newdawn.slick.geom.Vector2f;
/*  12:    */ 
/*  13:    */ public class CurveTest
/*  14:    */   extends BasicGame
/*  15:    */ {
/*  16:    */   private Curve curve;
/*  17: 22 */   private Vector2f p1 = new Vector2f(100.0F, 300.0F);
/*  18: 24 */   private Vector2f c1 = new Vector2f(100.0F, 100.0F);
/*  19: 26 */   private Vector2f c2 = new Vector2f(300.0F, 100.0F);
/*  20: 28 */   private Vector2f p2 = new Vector2f(300.0F, 300.0F);
/*  21:    */   private Polygon poly;
/*  22:    */   
/*  23:    */   public CurveTest()
/*  24:    */   {
/*  25: 37 */     super("Curve Test");
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void init(GameContainer container)
/*  29:    */     throws SlickException
/*  30:    */   {
/*  31: 44 */     container.getGraphics().setBackground(Color.white);
/*  32:    */     
/*  33: 46 */     this.curve = new Curve(this.p2, this.c2, this.c1, this.p1);
/*  34: 47 */     this.poly = new Polygon();
/*  35: 48 */     this.poly.addPoint(500.0F, 200.0F);
/*  36: 49 */     this.poly.addPoint(600.0F, 200.0F);
/*  37: 50 */     this.poly.addPoint(700.0F, 300.0F);
/*  38: 51 */     this.poly.addPoint(400.0F, 300.0F);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void update(GameContainer container, int delta)
/*  42:    */     throws SlickException
/*  43:    */   {}
/*  44:    */   
/*  45:    */   private void drawMarker(Graphics g, Vector2f p)
/*  46:    */   {
/*  47: 67 */     g.drawRect(p.x - 5.0F, p.y - 5.0F, 10.0F, 10.0F);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void render(GameContainer container, Graphics g)
/*  51:    */     throws SlickException
/*  52:    */   {
/*  53: 74 */     g.setColor(Color.gray);
/*  54: 75 */     drawMarker(g, this.p1);
/*  55: 76 */     drawMarker(g, this.p2);
/*  56: 77 */     g.setColor(Color.red);
/*  57: 78 */     drawMarker(g, this.c1);
/*  58: 79 */     drawMarker(g, this.c2);
/*  59:    */     
/*  60: 81 */     g.setColor(Color.black);
/*  61: 82 */     g.draw(this.curve);
/*  62: 83 */     g.fill(this.curve);
/*  63:    */     
/*  64: 85 */     g.draw(this.poly);
/*  65: 86 */     g.fill(this.poly);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static void main(String[] argv)
/*  69:    */   {
/*  70:    */     try
/*  71:    */     {
/*  72: 96 */       AppGameContainer container = new AppGameContainer(new CurveTest());
/*  73: 97 */       container.setDisplayMode(800, 600, false);
/*  74: 98 */       container.start();
/*  75:    */     }
/*  76:    */     catch (SlickException e)
/*  77:    */     {
/*  78:100 */       e.printStackTrace();
/*  79:    */     }
/*  80:    */   }
/*  81:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.CurveTest
 * JD-Core Version:    0.7.0.1
 */