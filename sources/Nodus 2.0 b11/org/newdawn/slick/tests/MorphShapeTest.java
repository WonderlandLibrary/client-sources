/*  1:   */ package org.newdawn.slick.tests;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.AppGameContainer;
/*  4:   */ import org.newdawn.slick.BasicGame;
/*  5:   */ import org.newdawn.slick.Color;
/*  6:   */ import org.newdawn.slick.GameContainer;
/*  7:   */ import org.newdawn.slick.Graphics;
/*  8:   */ import org.newdawn.slick.SlickException;
/*  9:   */ import org.newdawn.slick.geom.MorphShape;
/* 10:   */ import org.newdawn.slick.geom.Rectangle;
/* 11:   */ import org.newdawn.slick.geom.Shape;
/* 12:   */ import org.newdawn.slick.geom.Transform;
/* 13:   */ 
/* 14:   */ public class MorphShapeTest
/* 15:   */   extends BasicGame
/* 16:   */ {
/* 17:   */   private Shape a;
/* 18:   */   private Shape b;
/* 19:   */   private Shape c;
/* 20:   */   private MorphShape morph;
/* 21:   */   private float time;
/* 22:   */   
/* 23:   */   public MorphShapeTest()
/* 24:   */   {
/* 25:35 */     super("MorphShapeTest");
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void init(GameContainer container)
/* 29:   */     throws SlickException
/* 30:   */   {
/* 31:42 */     this.a = new Rectangle(100.0F, 100.0F, 50.0F, 200.0F);
/* 32:43 */     this.a = this.a.transform(Transform.createRotateTransform(0.1F, 100.0F, 100.0F));
/* 33:44 */     this.b = new Rectangle(200.0F, 100.0F, 50.0F, 200.0F);
/* 34:45 */     this.b = this.b.transform(Transform.createRotateTransform(-0.6F, 100.0F, 100.0F));
/* 35:46 */     this.c = new Rectangle(300.0F, 100.0F, 50.0F, 200.0F);
/* 36:47 */     this.c = this.c.transform(Transform.createRotateTransform(-0.2F, 100.0F, 100.0F));
/* 37:   */     
/* 38:49 */     this.morph = new MorphShape(this.a);
/* 39:50 */     this.morph.addShape(this.b);
/* 40:51 */     this.morph.addShape(this.c);
/* 41:   */     
/* 42:53 */     container.setVSync(true);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void update(GameContainer container, int delta)
/* 46:   */     throws SlickException
/* 47:   */   {
/* 48:61 */     this.time += delta * 0.001F;
/* 49:62 */     this.morph.setMorphTime(this.time);
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void render(GameContainer container, Graphics g)
/* 53:   */     throws SlickException
/* 54:   */   {
/* 55:70 */     g.setColor(Color.green);
/* 56:71 */     g.draw(this.a);
/* 57:72 */     g.setColor(Color.red);
/* 58:73 */     g.draw(this.b);
/* 59:74 */     g.setColor(Color.blue);
/* 60:75 */     g.draw(this.c);
/* 61:76 */     g.setColor(Color.white);
/* 62:77 */     g.draw(this.morph);
/* 63:   */   }
/* 64:   */   
/* 65:   */   public static void main(String[] argv)
/* 66:   */   {
/* 67:   */     try
/* 68:   */     {
/* 69:88 */       AppGameContainer container = new AppGameContainer(
/* 70:89 */         new MorphShapeTest());
/* 71:90 */       container.setDisplayMode(800, 600, false);
/* 72:91 */       container.start();
/* 73:   */     }
/* 74:   */     catch (SlickException e)
/* 75:   */     {
/* 76:93 */       e.printStackTrace();
/* 77:   */     }
/* 78:   */   }
/* 79:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.MorphShapeTest
 * JD-Core Version:    0.7.0.1
 */