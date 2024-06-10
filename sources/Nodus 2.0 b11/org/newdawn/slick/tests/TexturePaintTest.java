/*  1:   */ package org.newdawn.slick.tests;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.AppGameContainer;
/*  4:   */ import org.newdawn.slick.BasicGame;
/*  5:   */ import org.newdawn.slick.Color;
/*  6:   */ import org.newdawn.slick.GameContainer;
/*  7:   */ import org.newdawn.slick.Graphics;
/*  8:   */ import org.newdawn.slick.Image;
/*  9:   */ import org.newdawn.slick.SlickException;
/* 10:   */ import org.newdawn.slick.geom.Polygon;
/* 11:   */ import org.newdawn.slick.geom.Rectangle;
/* 12:   */ import org.newdawn.slick.geom.ShapeRenderer;
/* 13:   */ import org.newdawn.slick.geom.TexCoordGenerator;
/* 14:   */ import org.newdawn.slick.geom.Vector2f;
/* 15:   */ 
/* 16:   */ public class TexturePaintTest
/* 17:   */   extends BasicGame
/* 18:   */ {
/* 19:23 */   private Polygon poly = new Polygon();
/* 20:   */   private Image image;
/* 21:28 */   private Rectangle texRect = new Rectangle(50.0F, 50.0F, 100.0F, 100.0F);
/* 22:   */   private TexCoordGenerator texPaint;
/* 23:   */   
/* 24:   */   public TexturePaintTest()
/* 25:   */   {
/* 26:36 */     super("Texture Paint Test");
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void init(GameContainer container)
/* 30:   */     throws SlickException
/* 31:   */   {
/* 32:43 */     this.poly.addPoint(120.0F, 120.0F);
/* 33:44 */     this.poly.addPoint(420.0F, 100.0F);
/* 34:45 */     this.poly.addPoint(620.0F, 420.0F);
/* 35:46 */     this.poly.addPoint(300.0F, 320.0F);
/* 36:   */     
/* 37:48 */     this.image = new Image("testdata/rocks.png");
/* 38:   */     
/* 39:50 */     this.texPaint = new TexCoordGenerator()
/* 40:   */     {
/* 41:   */       public Vector2f getCoordFor(float x, float y)
/* 42:   */       {
/* 43:52 */         float tx = (TexturePaintTest.this.texRect.getX() - x) / TexturePaintTest.this.texRect.getWidth();
/* 44:53 */         float ty = (TexturePaintTest.this.texRect.getY() - y) / TexturePaintTest.this.texRect.getHeight();
/* 45:   */         
/* 46:55 */         return new Vector2f(tx, ty);
/* 47:   */       }
/* 48:   */     };
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void update(GameContainer container, int delta)
/* 52:   */     throws SlickException
/* 53:   */   {}
/* 54:   */   
/* 55:   */   public void render(GameContainer container, Graphics g)
/* 56:   */     throws SlickException
/* 57:   */   {
/* 58:70 */     g.setColor(Color.white);
/* 59:71 */     g.texture(this.poly, this.image);
/* 60:   */     
/* 61:73 */     ShapeRenderer.texture(this.poly, this.image, this.texPaint);
/* 62:   */   }
/* 63:   */   
/* 64:   */   public static void main(String[] argv)
/* 65:   */   {
/* 66:   */     try
/* 67:   */     {
/* 68:83 */       AppGameContainer container = new AppGameContainer(new TexturePaintTest());
/* 69:84 */       container.setDisplayMode(800, 600, false);
/* 70:85 */       container.start();
/* 71:   */     }
/* 72:   */     catch (SlickException e)
/* 73:   */     {
/* 74:87 */       e.printStackTrace();
/* 75:   */     }
/* 76:   */   }
/* 77:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.TexturePaintTest
 * JD-Core Version:    0.7.0.1
 */