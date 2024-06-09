/*    */ package org.newdawn.slick.svg;
/*    */ 
/*    */ import org.newdawn.slick.geom.Line;
/*    */ import org.newdawn.slick.geom.Shape;
/*    */ import org.newdawn.slick.geom.TexCoordGenerator;
/*    */ import org.newdawn.slick.geom.Transform;
/*    */ import org.newdawn.slick.geom.Vector2f;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LinearGradientFill
/*    */   implements TexCoordGenerator
/*    */ {
/*    */   private Vector2f start;
/*    */   private Vector2f end;
/*    */   private Gradient gradient;
/*    */   private Line line;
/*    */   private Shape shape;
/*    */   
/*    */   public LinearGradientFill(Shape shape, Transform trans, Gradient gradient) {
/* 34 */     this.gradient = gradient;
/*    */     
/* 36 */     float x = gradient.getX1();
/* 37 */     float y = gradient.getY1();
/* 38 */     float mx = gradient.getX2();
/* 39 */     float my = gradient.getY2();
/*    */     
/* 41 */     float h = my - y;
/* 42 */     float w = mx - x;
/*    */     
/* 44 */     float[] s = { x, y + h / 2.0F };
/* 45 */     gradient.getTransform().transform(s, 0, s, 0, 1);
/* 46 */     trans.transform(s, 0, s, 0, 1);
/* 47 */     float[] e = { x + w, y + h / 2.0F };
/* 48 */     gradient.getTransform().transform(e, 0, e, 0, 1);
/* 49 */     trans.transform(e, 0, e, 0, 1);
/*    */     
/* 51 */     this.start = new Vector2f(s[0], s[1]);
/* 52 */     this.end = new Vector2f(e[0], e[1]);
/*    */     
/* 54 */     this.line = new Line(this.start, this.end);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Vector2f getCoordFor(float x, float y) {
/* 61 */     Vector2f result = new Vector2f();
/* 62 */     this.line.getClosestPoint(new Vector2f(x, y), result);
/* 63 */     float u = result.distance(this.start);
/* 64 */     u /= this.line.length();
/*    */     
/* 66 */     return new Vector2f(u, 0.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\svg\LinearGradientFill.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */