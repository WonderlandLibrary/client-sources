/*   1:    */ package org.newdawn.slick.tests;
/*   2:    */ 
/*   3:    */ import org.newdawn.slick.AppGameContainer;
/*   4:    */ import org.newdawn.slick.BasicGame;
/*   5:    */ import org.newdawn.slick.Color;
/*   6:    */ import org.newdawn.slick.GameContainer;
/*   7:    */ import org.newdawn.slick.Graphics;
/*   8:    */ import org.newdawn.slick.Image;
/*   9:    */ import org.newdawn.slick.SlickException;
/*  10:    */ import org.newdawn.slick.fills.GradientFill;
/*  11:    */ import org.newdawn.slick.geom.Polygon;
/*  12:    */ import org.newdawn.slick.geom.Rectangle;
/*  13:    */ import org.newdawn.slick.geom.Shape;
/*  14:    */ 
/*  15:    */ public class GradientImageTest
/*  16:    */   extends BasicGame
/*  17:    */ {
/*  18:    */   private Image image1;
/*  19:    */   private Image image2;
/*  20:    */   private GradientFill fill;
/*  21:    */   private Shape shape;
/*  22:    */   private Polygon poly;
/*  23:    */   private GameContainer container;
/*  24:    */   private float ang;
/*  25: 37 */   private boolean rotating = false;
/*  26:    */   
/*  27:    */   public GradientImageTest()
/*  28:    */   {
/*  29: 43 */     super("Gradient Image Test");
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void init(GameContainer container)
/*  33:    */     throws SlickException
/*  34:    */   {
/*  35: 50 */     this.container = container;
/*  36:    */     
/*  37: 52 */     this.image1 = new Image("testdata/grass.png");
/*  38: 53 */     this.image2 = new Image("testdata/rocks.png");
/*  39:    */     
/*  40: 55 */     this.fill = new GradientFill(-64.0F, 0.0F, new Color(1.0F, 1.0F, 1.0F, 1.0F), 64.0F, 0.0F, new Color(0, 0, 0, 0));
/*  41: 56 */     this.shape = new Rectangle(336.0F, 236.0F, 128.0F, 128.0F);
/*  42: 57 */     this.poly = new Polygon();
/*  43: 58 */     this.poly.addPoint(320.0F, 220.0F);
/*  44: 59 */     this.poly.addPoint(350.0F, 200.0F);
/*  45: 60 */     this.poly.addPoint(450.0F, 200.0F);
/*  46: 61 */     this.poly.addPoint(480.0F, 220.0F);
/*  47: 62 */     this.poly.addPoint(420.0F, 400.0F);
/*  48: 63 */     this.poly.addPoint(400.0F, 390.0F);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void render(GameContainer container, Graphics g)
/*  52:    */   {
/*  53: 70 */     g.drawString("R - Toggle Rotationg", 10.0F, 50.0F);
/*  54: 71 */     g.drawImage(this.image1, 100.0F, 236.0F);
/*  55: 72 */     g.drawImage(this.image2, 600.0F, 236.0F);
/*  56:    */     
/*  57: 74 */     g.translate(0.0F, -150.0F);
/*  58: 75 */     g.rotate(400.0F, 300.0F, this.ang);
/*  59: 76 */     g.texture(this.shape, this.image2);
/*  60: 77 */     g.texture(this.shape, this.image1, this.fill);
/*  61: 78 */     g.resetTransform();
/*  62:    */     
/*  63: 80 */     g.translate(0.0F, 150.0F);
/*  64: 81 */     g.rotate(400.0F, 300.0F, this.ang);
/*  65: 82 */     g.texture(this.poly, this.image2);
/*  66: 83 */     g.texture(this.poly, this.image1, this.fill);
/*  67: 84 */     g.resetTransform();
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void update(GameContainer container, int delta)
/*  71:    */   {
/*  72: 91 */     if (this.rotating) {
/*  73: 92 */       this.ang += delta * 0.1F;
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   public static void main(String[] argv)
/*  78:    */   {
/*  79:    */     try
/*  80:    */     {
/*  81:103 */       AppGameContainer container = new AppGameContainer(new GradientImageTest());
/*  82:104 */       container.setDisplayMode(800, 600, false);
/*  83:105 */       container.start();
/*  84:    */     }
/*  85:    */     catch (SlickException e)
/*  86:    */     {
/*  87:107 */       e.printStackTrace();
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void keyPressed(int key, char c)
/*  92:    */   {
/*  93:115 */     if (key == 19) {
/*  94:116 */       this.rotating = (!this.rotating);
/*  95:    */     }
/*  96:118 */     if (key == 1) {
/*  97:119 */       this.container.exit();
/*  98:    */     }
/*  99:    */   }
/* 100:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.GradientImageTest
 * JD-Core Version:    0.7.0.1
 */