/*   1:    */ package org.newdawn.slick.tests;
/*   2:    */ 
/*   3:    */ import org.newdawn.slick.AppGameContainer;
/*   4:    */ import org.newdawn.slick.BasicGame;
/*   5:    */ import org.newdawn.slick.Color;
/*   6:    */ import org.newdawn.slick.GameContainer;
/*   7:    */ import org.newdawn.slick.Graphics;
/*   8:    */ import org.newdawn.slick.SlickException;
/*   9:    */ import org.newdawn.slick.fills.GradientFill;
/*  10:    */ import org.newdawn.slick.geom.Polygon;
/*  11:    */ import org.newdawn.slick.geom.Rectangle;
/*  12:    */ import org.newdawn.slick.geom.RoundedRectangle;
/*  13:    */ import org.newdawn.slick.opengl.renderer.Renderer;
/*  14:    */ 
/*  15:    */ public class GradientTest
/*  16:    */   extends BasicGame
/*  17:    */ {
/*  18:    */   private GameContainer container;
/*  19:    */   private GradientFill gradient;
/*  20:    */   private GradientFill gradient2;
/*  21:    */   private GradientFill gradient4;
/*  22:    */   private Rectangle rect;
/*  23:    */   private Rectangle center;
/*  24:    */   private RoundedRectangle round;
/*  25:    */   private RoundedRectangle round2;
/*  26:    */   private Polygon poly;
/*  27:    */   private float ang;
/*  28:    */   
/*  29:    */   public GradientTest()
/*  30:    */   {
/*  31: 47 */     super("Gradient Test");
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void init(GameContainer container)
/*  35:    */     throws SlickException
/*  36:    */   {
/*  37: 54 */     this.container = container;
/*  38:    */     
/*  39: 56 */     this.rect = new Rectangle(400.0F, 100.0F, 200.0F, 150.0F);
/*  40: 57 */     this.round = new RoundedRectangle(150.0F, 100.0F, 200.0F, 150.0F, 50.0F);
/*  41: 58 */     this.round2 = new RoundedRectangle(150.0F, 300.0F, 200.0F, 150.0F, 50.0F);
/*  42: 59 */     this.center = new Rectangle(350.0F, 250.0F, 100.0F, 100.0F);
/*  43:    */     
/*  44: 61 */     this.poly = new Polygon();
/*  45: 62 */     this.poly.addPoint(400.0F, 350.0F);
/*  46: 63 */     this.poly.addPoint(550.0F, 320.0F);
/*  47: 64 */     this.poly.addPoint(600.0F, 380.0F);
/*  48: 65 */     this.poly.addPoint(620.0F, 450.0F);
/*  49: 66 */     this.poly.addPoint(500.0F, 450.0F);
/*  50:    */     
/*  51: 68 */     this.gradient = new GradientFill(0.0F, -75.0F, Color.red, 0.0F, 75.0F, Color.yellow, true);
/*  52: 69 */     this.gradient2 = new GradientFill(0.0F, -75.0F, Color.blue, 0.0F, 75.0F, Color.white, true);
/*  53: 70 */     this.gradient4 = new GradientFill(-50.0F, -40.0F, Color.green, 50.0F, 40.0F, Color.cyan, true);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void render(GameContainer container, Graphics g)
/*  57:    */   {
/*  58: 78 */     g.rotate(400.0F, 300.0F, this.ang);
/*  59: 79 */     g.fill(this.rect, this.gradient);
/*  60: 80 */     g.fill(this.round, this.gradient);
/*  61: 81 */     g.fill(this.poly, this.gradient2);
/*  62: 82 */     g.fill(this.center, this.gradient4);
/*  63:    */     
/*  64: 84 */     g.setAntiAlias(true);
/*  65: 85 */     g.setLineWidth(10.0F);
/*  66: 86 */     g.draw(this.round2, this.gradient2);
/*  67: 87 */     g.setLineWidth(2.0F);
/*  68: 88 */     g.draw(this.poly, this.gradient);
/*  69: 89 */     g.setAntiAlias(false);
/*  70:    */     
/*  71: 91 */     g.fill(this.center, this.gradient4);
/*  72: 92 */     g.setAntiAlias(true);
/*  73: 93 */     g.setColor(Color.black);
/*  74: 94 */     g.draw(this.center);
/*  75: 95 */     g.setAntiAlias(false);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void update(GameContainer container, int delta)
/*  79:    */   {
/*  80:102 */     this.ang += delta * 0.01F;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static void main(String[] argv)
/*  84:    */   {
/*  85:    */     try
/*  86:    */     {
/*  87:112 */       Renderer.setRenderer(2);
/*  88:    */       
/*  89:114 */       AppGameContainer container = new AppGameContainer(new GradientTest());
/*  90:115 */       container.setDisplayMode(800, 600, false);
/*  91:116 */       container.start();
/*  92:    */     }
/*  93:    */     catch (SlickException e)
/*  94:    */     {
/*  95:118 */       e.printStackTrace();
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void keyPressed(int key, char c)
/* 100:    */   {
/* 101:126 */     if (key == 1) {
/* 102:127 */       this.container.exit();
/* 103:    */     }
/* 104:    */   }
/* 105:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.GradientTest
 * JD-Core Version:    0.7.0.1
 */