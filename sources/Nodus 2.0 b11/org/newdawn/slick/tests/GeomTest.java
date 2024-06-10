/*   1:    */ package org.newdawn.slick.tests;
/*   2:    */ 
/*   3:    */ import org.newdawn.slick.AppGameContainer;
/*   4:    */ import org.newdawn.slick.BasicGame;
/*   5:    */ import org.newdawn.slick.Color;
/*   6:    */ import org.newdawn.slick.GameContainer;
/*   7:    */ import org.newdawn.slick.Graphics;
/*   8:    */ import org.newdawn.slick.SlickException;
/*   9:    */ import org.newdawn.slick.geom.Circle;
/*  10:    */ import org.newdawn.slick.geom.Ellipse;
/*  11:    */ import org.newdawn.slick.geom.Rectangle;
/*  12:    */ import org.newdawn.slick.geom.RoundedRectangle;
/*  13:    */ import org.newdawn.slick.geom.Shape;
/*  14:    */ import org.newdawn.slick.geom.Transform;
/*  15:    */ import org.newdawn.slick.opengl.renderer.Renderer;
/*  16:    */ 
/*  17:    */ public class GeomTest
/*  18:    */   extends BasicGame
/*  19:    */ {
/*  20: 25 */   private Shape rect = new Rectangle(100.0F, 100.0F, 100.0F, 100.0F);
/*  21: 27 */   private Shape circle = new Circle(500.0F, 200.0F, 50.0F);
/*  22: 29 */   private Shape rect1 = new Rectangle(150.0F, 120.0F, 50.0F, 100.0F).transform(Transform.createTranslateTransform(50.0F, 50.0F));
/*  23: 31 */   private Shape rect2 = new Rectangle(310.0F, 210.0F, 50.0F, 100.0F)
/*  24: 32 */     .transform(Transform.createRotateTransform((float)Math.toRadians(45.0D), 335.0F, 260.0F));
/*  25: 34 */   private Shape circle1 = new Circle(150.0F, 90.0F, 30.0F);
/*  26: 36 */   private Shape circle2 = new Circle(310.0F, 110.0F, 70.0F);
/*  27: 38 */   private Shape circle3 = new Ellipse(510.0F, 150.0F, 70.0F, 70.0F);
/*  28: 40 */   private Shape circle4 = new Ellipse(510.0F, 350.0F, 30.0F, 30.0F).transform(
/*  29: 41 */     Transform.createTranslateTransform(-510.0F, -350.0F)).transform(
/*  30: 42 */     Transform.createScaleTransform(2.0F, 2.0F)).transform(
/*  31: 43 */     Transform.createTranslateTransform(510.0F, 350.0F));
/*  32: 45 */   private Shape roundRect = new RoundedRectangle(50.0F, 175.0F, 100.0F, 100.0F, 20.0F);
/*  33: 47 */   private Shape roundRect2 = new RoundedRectangle(50.0F, 280.0F, 50.0F, 50.0F, 20.0F, 20, 5);
/*  34:    */   
/*  35:    */   public GeomTest()
/*  36:    */   {
/*  37: 53 */     super("Geom Test");
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void init(GameContainer container)
/*  41:    */     throws SlickException
/*  42:    */   {}
/*  43:    */   
/*  44:    */   public void render(GameContainer container, Graphics g)
/*  45:    */   {
/*  46: 66 */     g.setColor(Color.white);
/*  47: 67 */     g.drawString("Red indicates a collision, green indicates no collision", 50.0F, 420.0F);
/*  48: 68 */     g.drawString("White are the targets", 50.0F, 435.0F);
/*  49:    */     
/*  50: 70 */     g.pushTransform();
/*  51: 71 */     g.translate(100.0F, 100.0F);
/*  52: 72 */     g.pushTransform();
/*  53: 73 */     g.translate(-50.0F, -50.0F);
/*  54: 74 */     g.scale(10.0F, 10.0F);
/*  55: 75 */     g.setColor(Color.red);
/*  56: 76 */     g.fillRect(0.0F, 0.0F, 5.0F, 5.0F);
/*  57: 77 */     g.setColor(Color.white);
/*  58: 78 */     g.drawRect(0.0F, 0.0F, 5.0F, 5.0F);
/*  59: 79 */     g.popTransform();
/*  60: 80 */     g.setColor(Color.green);
/*  61: 81 */     g.fillRect(20.0F, 20.0F, 50.0F, 50.0F);
/*  62: 82 */     g.popTransform();
/*  63:    */     
/*  64: 84 */     g.setColor(Color.white);
/*  65: 85 */     g.draw(this.rect);
/*  66: 86 */     g.draw(this.circle);
/*  67:    */     
/*  68: 88 */     g.setColor(this.rect1.intersects(this.rect) ? Color.red : Color.green);
/*  69: 89 */     g.draw(this.rect1);
/*  70: 90 */     g.setColor(this.rect2.intersects(this.rect) ? Color.red : Color.green);
/*  71: 91 */     g.draw(this.rect2);
/*  72: 92 */     g.setColor(this.roundRect.intersects(this.rect) ? Color.red : Color.green);
/*  73: 93 */     g.draw(this.roundRect);
/*  74: 94 */     g.setColor(this.circle1.intersects(this.rect) ? Color.red : Color.green);
/*  75: 95 */     g.draw(this.circle1);
/*  76: 96 */     g.setColor(this.circle2.intersects(this.rect) ? Color.red : Color.green);
/*  77: 97 */     g.draw(this.circle2);
/*  78: 98 */     g.setColor(this.circle3.intersects(this.circle) ? Color.red : Color.green);
/*  79: 99 */     g.fill(this.circle3);
/*  80:100 */     g.setColor(this.circle4.intersects(this.circle) ? Color.red : Color.green);
/*  81:101 */     g.draw(this.circle4);
/*  82:    */     
/*  83:103 */     g.fill(this.roundRect2);
/*  84:104 */     g.setColor(Color.blue);
/*  85:105 */     g.draw(this.roundRect2);
/*  86:106 */     g.setColor(Color.blue);
/*  87:107 */     g.draw(new Circle(100.0F, 100.0F, 50.0F));
/*  88:108 */     g.drawRect(50.0F, 50.0F, 100.0F, 100.0F);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void update(GameContainer container, int delta) {}
/*  92:    */   
/*  93:    */   public void keyPressed(int key, char c)
/*  94:    */   {
/*  95:122 */     if (key == 1) {
/*  96:123 */       System.exit(0);
/*  97:    */     }
/*  98:    */   }
/*  99:    */   
/* 100:    */   public static void main(String[] argv)
/* 101:    */   {
/* 102:    */     try
/* 103:    */     {
/* 104:134 */       Renderer.setRenderer(2);
/* 105:    */       
/* 106:136 */       AppGameContainer container = new AppGameContainer(new GeomTest());
/* 107:137 */       container.setDisplayMode(800, 600, false);
/* 108:138 */       container.start();
/* 109:    */     }
/* 110:    */     catch (SlickException e)
/* 111:    */     {
/* 112:140 */       e.printStackTrace();
/* 113:    */     }
/* 114:    */   }
/* 115:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.GeomTest
 * JD-Core Version:    0.7.0.1
 */