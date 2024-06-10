/*  1:   */ package org.newdawn.slick.svg;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.geom.Shape;
/*  4:   */ import org.newdawn.slick.geom.Transform;
/*  5:   */ 
/*  6:   */ public class Figure
/*  7:   */ {
/*  8:   */   public static final int ELLIPSE = 1;
/*  9:   */   public static final int LINE = 2;
/* 10:   */   public static final int RECTANGLE = 3;
/* 11:   */   public static final int PATH = 4;
/* 12:   */   public static final int POLYGON = 5;
/* 13:   */   private int type;
/* 14:   */   private Shape shape;
/* 15:   */   private NonGeometricData data;
/* 16:   */   private Transform transform;
/* 17:   */   
/* 18:   */   public Figure(int type, Shape shape, NonGeometricData data, Transform transform)
/* 19:   */   {
/* 20:42 */     this.shape = shape;
/* 21:43 */     this.data = data;
/* 22:44 */     this.type = type;
/* 23:45 */     this.transform = transform;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public Transform getTransform()
/* 27:   */   {
/* 28:55 */     return this.transform;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public int getType()
/* 32:   */   {
/* 33:64 */     return this.type;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public Shape getShape()
/* 37:   */   {
/* 38:73 */     return this.shape;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public NonGeometricData getData()
/* 42:   */   {
/* 43:82 */     return this.data;
/* 44:   */   }
/* 45:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.svg.Figure
 * JD-Core Version:    0.7.0.1
 */