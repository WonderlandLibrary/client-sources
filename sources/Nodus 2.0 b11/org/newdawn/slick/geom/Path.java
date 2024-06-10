/*   1:    */ package org.newdawn.slick.geom;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ 
/*   5:    */ public class Path
/*   6:    */   extends Shape
/*   7:    */ {
/*   8: 13 */   private ArrayList localPoints = new ArrayList();
/*   9:    */   private float cx;
/*  10:    */   private float cy;
/*  11:    */   private boolean closed;
/*  12: 21 */   private ArrayList holes = new ArrayList();
/*  13:    */   private ArrayList hole;
/*  14:    */   
/*  15:    */   public Path(float sx, float sy)
/*  16:    */   {
/*  17: 32 */     this.localPoints.add(new float[] { sx, sy });
/*  18: 33 */     this.cx = sx;
/*  19: 34 */     this.cy = sy;
/*  20: 35 */     this.pointsDirty = true;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void startHole(float sx, float sy)
/*  24:    */   {
/*  25: 45 */     this.hole = new ArrayList();
/*  26: 46 */     this.holes.add(this.hole);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void lineTo(float x, float y)
/*  30:    */   {
/*  31: 57 */     if (this.hole != null) {
/*  32: 58 */       this.hole.add(new float[] { x, y });
/*  33:    */     } else {
/*  34: 60 */       this.localPoints.add(new float[] { x, y });
/*  35:    */     }
/*  36: 62 */     this.cx = x;
/*  37: 63 */     this.cy = y;
/*  38: 64 */     this.pointsDirty = true;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void close()
/*  42:    */   {
/*  43: 71 */     this.closed = true;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void curveTo(float x, float y, float cx1, float cy1, float cx2, float cy2)
/*  47:    */   {
/*  48: 85 */     curveTo(x, y, cx1, cy1, cx2, cy2, 10);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void curveTo(float x, float y, float cx1, float cy1, float cx2, float cy2, int segments)
/*  52:    */   {
/*  53:101 */     if ((this.cx == x) && (this.cy == y)) {
/*  54:102 */       return;
/*  55:    */     }
/*  56:105 */     Curve curve = new Curve(new Vector2f(this.cx, this.cy), new Vector2f(cx1, cy1), new Vector2f(cx2, cy2), new Vector2f(x, y));
/*  57:106 */     float step = 1.0F / segments;
/*  58:108 */     for (int i = 1; i < segments + 1; i++)
/*  59:    */     {
/*  60:109 */       float t = i * step;
/*  61:110 */       Vector2f p = curve.pointAt(t);
/*  62:111 */       if (this.hole != null) {
/*  63:112 */         this.hole.add(new float[] { p.x, p.y });
/*  64:    */       } else {
/*  65:114 */         this.localPoints.add(new float[] { p.x, p.y });
/*  66:    */       }
/*  67:116 */       this.cx = p.x;
/*  68:117 */       this.cy = p.y;
/*  69:    */     }
/*  70:119 */     this.pointsDirty = true;
/*  71:    */   }
/*  72:    */   
/*  73:    */   protected void createPoints()
/*  74:    */   {
/*  75:126 */     this.points = new float[this.localPoints.size() * 2];
/*  76:127 */     for (int i = 0; i < this.localPoints.size(); i++)
/*  77:    */     {
/*  78:128 */       float[] p = (float[])this.localPoints.get(i);
/*  79:129 */       this.points[(i * 2)] = p[0];
/*  80:130 */       this.points[(i * 2 + 1)] = p[1];
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   public Shape transform(Transform transform)
/*  85:    */   {
/*  86:138 */     Path p = new Path(this.cx, this.cy);
/*  87:139 */     p.localPoints = transform(this.localPoints, transform);
/*  88:140 */     for (int i = 0; i < this.holes.size(); i++) {
/*  89:141 */       p.holes.add(transform((ArrayList)this.holes.get(i), transform));
/*  90:    */     }
/*  91:143 */     p.closed = this.closed;
/*  92:    */     
/*  93:145 */     return p;
/*  94:    */   }
/*  95:    */   
/*  96:    */   private ArrayList transform(ArrayList pts, Transform t)
/*  97:    */   {
/*  98:156 */     float[] in = new float[pts.size() * 2];
/*  99:157 */     float[] out = new float[pts.size() * 2];
/* 100:159 */     for (int i = 0; i < pts.size(); i++)
/* 101:    */     {
/* 102:160 */       in[(i * 2)] = ((float[])pts.get(i))[0];
/* 103:161 */       in[(i * 2 + 1)] = ((float[])pts.get(i))[1];
/* 104:    */     }
/* 105:163 */     t.transform(in, 0, out, 0, pts.size());
/* 106:    */     
/* 107:165 */     ArrayList outList = new ArrayList();
/* 108:166 */     for (int i = 0; i < pts.size(); i++) {
/* 109:167 */       outList.add(new float[] { out[(i * 2)], out[(i * 2 + 1)] });
/* 110:    */     }
/* 111:170 */     return outList;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public boolean closed()
/* 115:    */   {
/* 116:239 */     return this.closed;
/* 117:    */   }
/* 118:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.geom.Path
 * JD-Core Version:    0.7.0.1
 */