/*    */ package org.newdawn.slick.svg;
/*    */ 
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
/*    */ public class RadialGradientFill
/*    */   implements TexCoordGenerator
/*    */ {
/*    */   private Vector2f centre;
/*    */   private float radius;
/*    */   private Gradient gradient;
/*    */   private Shape shape;
/*    */   
/*    */   public RadialGradientFill(Shape shape, Transform trans, Gradient gradient) {
/* 31 */     this.gradient = gradient;
/*    */     
/* 33 */     this.radius = gradient.getR();
/* 34 */     float x = gradient.getX1();
/* 35 */     float y = gradient.getY1();
/*    */     
/* 37 */     float[] c = { x, y };
/* 38 */     gradient.getTransform().transform(c, 0, c, 0, 1);
/* 39 */     trans.transform(c, 0, c, 0, 1);
/* 40 */     float[] rt = { x, y - this.radius };
/* 41 */     gradient.getTransform().transform(rt, 0, rt, 0, 1);
/* 42 */     trans.transform(rt, 0, rt, 0, 1);
/*    */     
/* 44 */     this.centre = new Vector2f(c[0], c[1]);
/* 45 */     Vector2f dis = new Vector2f(rt[0], rt[1]);
/* 46 */     this.radius = dis.distance(this.centre);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Vector2f getCoordFor(float x, float y) {
/* 53 */     float u = this.centre.distance(new Vector2f(x, y));
/* 54 */     u /= this.radius;
/*    */     
/* 56 */     if (u > 0.99F) {
/* 57 */       u = 0.99F;
/*    */     }
/*    */     
/* 60 */     return new Vector2f(u, 0.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\svg\RadialGradientFill.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */