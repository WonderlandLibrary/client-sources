/*   1:    */ package org.newdawn.slick.geom;
/*   2:    */ 
/*   3:    */ public class Curve
/*   4:    */   extends Shape
/*   5:    */ {
/*   6:    */   private Vector2f p1;
/*   7:    */   private Vector2f c1;
/*   8:    */   private Vector2f c2;
/*   9:    */   private Vector2f p2;
/*  10:    */   private int segments;
/*  11:    */   
/*  12:    */   public Curve(Vector2f p1, Vector2f c1, Vector2f c2, Vector2f p2)
/*  13:    */   {
/*  14: 31 */     this(p1, c1, c2, p2, 20);
/*  15:    */   }
/*  16:    */   
/*  17:    */   public Curve(Vector2f p1, Vector2f c1, Vector2f c2, Vector2f p2, int segments)
/*  18:    */   {
/*  19: 44 */     this.p1 = new Vector2f(p1);
/*  20: 45 */     this.c1 = new Vector2f(c1);
/*  21: 46 */     this.c2 = new Vector2f(c2);
/*  22: 47 */     this.p2 = new Vector2f(p2);
/*  23:    */     
/*  24: 49 */     this.segments = segments;
/*  25: 50 */     this.pointsDirty = true;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public Vector2f pointAt(float t)
/*  29:    */   {
/*  30: 60 */     float a = 1.0F - t;
/*  31: 61 */     float b = t;
/*  32:    */     
/*  33: 63 */     float f1 = a * a * a;
/*  34: 64 */     float f2 = 3.0F * a * a * b;
/*  35: 65 */     float f3 = 3.0F * a * b * b;
/*  36: 66 */     float f4 = b * b * b;
/*  37:    */     
/*  38: 68 */     float nx = this.p1.x * f1 + this.c1.x * f2 + this.c2.x * f3 + this.p2.x * f4;
/*  39: 69 */     float ny = this.p1.y * f1 + this.c1.y * f2 + this.c2.y * f3 + this.p2.y * f4;
/*  40:    */     
/*  41: 71 */     return new Vector2f(nx, ny);
/*  42:    */   }
/*  43:    */   
/*  44:    */   protected void createPoints()
/*  45:    */   {
/*  46: 78 */     float step = 1.0F / this.segments;
/*  47: 79 */     this.points = new float[(this.segments + 1) * 2];
/*  48: 80 */     for (int i = 0; i < this.segments + 1; i++)
/*  49:    */     {
/*  50: 81 */       float t = i * step;
/*  51:    */       
/*  52: 83 */       Vector2f p = pointAt(t);
/*  53: 84 */       this.points[(i * 2)] = p.x;
/*  54: 85 */       this.points[(i * 2 + 1)] = p.y;
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58:    */   public Shape transform(Transform transform)
/*  59:    */   {
/*  60: 93 */     float[] pts = new float[8];
/*  61: 94 */     float[] dest = new float[8];
/*  62: 95 */     pts[0] = this.p1.x;pts[1] = this.p1.y;
/*  63: 96 */     pts[2] = this.c1.x;pts[3] = this.c1.y;
/*  64: 97 */     pts[4] = this.c2.x;pts[5] = this.c2.y;
/*  65: 98 */     pts[6] = this.p2.x;pts[7] = this.p2.y;
/*  66: 99 */     transform.transform(pts, 0, dest, 0, 4);
/*  67:    */     
/*  68:101 */     return new Curve(new Vector2f(dest[0], dest[1]), new Vector2f(dest[2], dest[3]), 
/*  69:102 */       new Vector2f(dest[4], dest[5]), new Vector2f(dest[6], dest[7]));
/*  70:    */   }
/*  71:    */   
/*  72:    */   public boolean closed()
/*  73:    */   {
/*  74:111 */     return false;
/*  75:    */   }
/*  76:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.geom.Curve
 * JD-Core Version:    0.7.0.1
 */