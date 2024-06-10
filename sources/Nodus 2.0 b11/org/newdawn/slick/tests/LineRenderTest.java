/*  1:   */ package org.newdawn.slick.tests;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.AppGameContainer;
/*  4:   */ import org.newdawn.slick.BasicGame;
/*  5:   */ import org.newdawn.slick.Color;
/*  6:   */ import org.newdawn.slick.GameContainer;
/*  7:   */ import org.newdawn.slick.Graphics;
/*  8:   */ import org.newdawn.slick.Input;
/*  9:   */ import org.newdawn.slick.SlickException;
/* 10:   */ import org.newdawn.slick.geom.Path;
/* 11:   */ import org.newdawn.slick.geom.Polygon;
/* 12:   */ import org.newdawn.slick.opengl.renderer.LineStripRenderer;
/* 13:   */ import org.newdawn.slick.opengl.renderer.Renderer;
/* 14:   */ 
/* 15:   */ public class LineRenderTest
/* 16:   */   extends BasicGame
/* 17:   */ {
/* 18:21 */   private Polygon polygon = new Polygon();
/* 19:23 */   private Path path = new Path(100.0F, 100.0F);
/* 20:25 */   private float width = 10.0F;
/* 21:27 */   private boolean antialias = true;
/* 22:   */   
/* 23:   */   public LineRenderTest()
/* 24:   */   {
/* 25:33 */     super("LineRenderTest");
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void init(GameContainer container)
/* 29:   */     throws SlickException
/* 30:   */   {
/* 31:40 */     this.polygon.addPoint(100.0F, 100.0F);
/* 32:41 */     this.polygon.addPoint(200.0F, 80.0F);
/* 33:42 */     this.polygon.addPoint(320.0F, 150.0F);
/* 34:43 */     this.polygon.addPoint(230.0F, 210.0F);
/* 35:44 */     this.polygon.addPoint(170.0F, 260.0F);
/* 36:   */     
/* 37:46 */     this.path.curveTo(200.0F, 200.0F, 200.0F, 100.0F, 100.0F, 200.0F);
/* 38:47 */     this.path.curveTo(400.0F, 100.0F, 400.0F, 200.0F, 200.0F, 100.0F);
/* 39:48 */     this.path.curveTo(500.0F, 500.0F, 400.0F, 200.0F, 200.0F, 100.0F);
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void update(GameContainer container, int delta)
/* 43:   */     throws SlickException
/* 44:   */   {
/* 45:55 */     if (container.getInput().isKeyPressed(57)) {
/* 46:56 */       this.antialias = (!this.antialias);
/* 47:   */     }
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void render(GameContainer container, Graphics g)
/* 51:   */     throws SlickException
/* 52:   */   {
/* 53:64 */     g.setAntiAlias(this.antialias);
/* 54:65 */     g.setLineWidth(50.0F);
/* 55:66 */     g.setColor(Color.red);
/* 56:67 */     g.draw(this.path);
/* 57:   */   }
/* 58:   */   
/* 59:   */   public static void main(String[] argv)
/* 60:   */   {
/* 61:   */     try
/* 62:   */     {
/* 63:92 */       Renderer.setLineStripRenderer(4);
/* 64:93 */       Renderer.getLineStripRenderer().setLineCaps(true);
/* 65:   */       
/* 66:95 */       AppGameContainer container = new AppGameContainer(new LineRenderTest());
/* 67:96 */       container.setDisplayMode(800, 600, false);
/* 68:97 */       container.start();
/* 69:   */     }
/* 70:   */     catch (SlickException e)
/* 71:   */     {
/* 72:99 */       e.printStackTrace();
/* 73:   */     }
/* 74:   */   }
/* 75:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.LineRenderTest
 * JD-Core Version:    0.7.0.1
 */