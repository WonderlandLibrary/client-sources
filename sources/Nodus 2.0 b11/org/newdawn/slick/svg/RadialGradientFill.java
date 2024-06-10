/*  1:   */ package org.newdawn.slick.svg;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.geom.Shape;
/*  4:   */ import org.newdawn.slick.geom.TexCoordGenerator;
/*  5:   */ import org.newdawn.slick.geom.Transform;
/*  6:   */ import org.newdawn.slick.geom.Vector2f;
/*  7:   */ 
/*  8:   */ public class RadialGradientFill
/*  9:   */   implements TexCoordGenerator
/* 10:   */ {
/* 11:   */   private Vector2f centre;
/* 12:   */   private float radius;
/* 13:   */   private Gradient gradient;
/* 14:   */   private Shape shape;
/* 15:   */   
/* 16:   */   public RadialGradientFill(Shape shape, Transform trans, Gradient gradient)
/* 17:   */   {
/* 18:31 */     this.gradient = gradient;
/* 19:   */     
/* 20:33 */     this.radius = gradient.getR();
/* 21:34 */     float x = gradient.getX1();
/* 22:35 */     float y = gradient.getY1();
/* 23:   */     
/* 24:37 */     float[] c = { x, y };
/* 25:38 */     gradient.getTransform().transform(c, 0, c, 0, 1);
/* 26:39 */     trans.transform(c, 0, c, 0, 1);
/* 27:40 */     float[] rt = { x, y - this.radius };
/* 28:41 */     gradient.getTransform().transform(rt, 0, rt, 0, 1);
/* 29:42 */     trans.transform(rt, 0, rt, 0, 1);
/* 30:   */     
/* 31:44 */     this.centre = new Vector2f(c[0], c[1]);
/* 32:45 */     Vector2f dis = new Vector2f(rt[0], rt[1]);
/* 33:46 */     this.radius = dis.distance(this.centre);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public Vector2f getCoordFor(float x, float y)
/* 37:   */   {
/* 38:53 */     float u = this.centre.distance(new Vector2f(x, y));
/* 39:54 */     u /= this.radius;
/* 40:56 */     if (u > 0.99F) {
/* 41:57 */       u = 0.99F;
/* 42:   */     }
/* 43:60 */     return new Vector2f(u, 0.0F);
/* 44:   */   }
/* 45:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.svg.RadialGradientFill
 * JD-Core Version:    0.7.0.1
 */