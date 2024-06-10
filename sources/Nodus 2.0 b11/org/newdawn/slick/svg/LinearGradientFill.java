/*  1:   */ package org.newdawn.slick.svg;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.geom.Line;
/*  4:   */ import org.newdawn.slick.geom.Shape;
/*  5:   */ import org.newdawn.slick.geom.TexCoordGenerator;
/*  6:   */ import org.newdawn.slick.geom.Transform;
/*  7:   */ import org.newdawn.slick.geom.Vector2f;
/*  8:   */ 
/*  9:   */ public class LinearGradientFill
/* 10:   */   implements TexCoordGenerator
/* 11:   */ {
/* 12:   */   private Vector2f start;
/* 13:   */   private Vector2f end;
/* 14:   */   private Gradient gradient;
/* 15:   */   private Line line;
/* 16:   */   private Shape shape;
/* 17:   */   
/* 18:   */   public LinearGradientFill(Shape shape, Transform trans, Gradient gradient)
/* 19:   */   {
/* 20:34 */     this.gradient = gradient;
/* 21:   */     
/* 22:36 */     float x = gradient.getX1();
/* 23:37 */     float y = gradient.getY1();
/* 24:38 */     float mx = gradient.getX2();
/* 25:39 */     float my = gradient.getY2();
/* 26:   */     
/* 27:41 */     float h = my - y;
/* 28:42 */     float w = mx - x;
/* 29:   */     
/* 30:44 */     float[] s = { x, y + h / 2.0F };
/* 31:45 */     gradient.getTransform().transform(s, 0, s, 0, 1);
/* 32:46 */     trans.transform(s, 0, s, 0, 1);
/* 33:47 */     float[] e = { x + w, y + h / 2.0F };
/* 34:48 */     gradient.getTransform().transform(e, 0, e, 0, 1);
/* 35:49 */     trans.transform(e, 0, e, 0, 1);
/* 36:   */     
/* 37:51 */     this.start = new Vector2f(s[0], s[1]);
/* 38:52 */     this.end = new Vector2f(e[0], e[1]);
/* 39:   */     
/* 40:54 */     this.line = new Line(this.start, this.end);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public Vector2f getCoordFor(float x, float y)
/* 44:   */   {
/* 45:61 */     Vector2f result = new Vector2f();
/* 46:62 */     this.line.getClosestPoint(new Vector2f(x, y), result);
/* 47:63 */     float u = result.distance(this.start);
/* 48:64 */     u /= this.line.length();
/* 49:   */     
/* 50:66 */     return new Vector2f(u, 0.0F);
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.svg.LinearGradientFill
 * JD-Core Version:    0.7.0.1
 */