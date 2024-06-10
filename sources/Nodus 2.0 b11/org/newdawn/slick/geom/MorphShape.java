/*   1:    */ package org.newdawn.slick.geom;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ 
/*   5:    */ public class MorphShape
/*   6:    */   extends Shape
/*   7:    */ {
/*   8: 12 */   private ArrayList shapes = new ArrayList();
/*   9:    */   private float offset;
/*  10:    */   private Shape current;
/*  11:    */   private Shape next;
/*  12:    */   
/*  13:    */   public MorphShape(Shape base)
/*  14:    */   {
/*  15: 27 */     this.shapes.add(base);
/*  16: 28 */     float[] copy = base.points;
/*  17: 29 */     this.points = new float[copy.length];
/*  18:    */     
/*  19: 31 */     this.current = base;
/*  20: 32 */     this.next = base;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void addShape(Shape shape)
/*  24:    */   {
/*  25: 41 */     if (shape.points.length != this.points.length) {
/*  26: 42 */       throw new RuntimeException("Attempt to morph between two shapes with different vertex counts");
/*  27:    */     }
/*  28: 45 */     Shape prev = (Shape)this.shapes.get(this.shapes.size() - 1);
/*  29: 46 */     if (equalShapes(prev, shape)) {
/*  30: 47 */       this.shapes.add(prev);
/*  31:    */     } else {
/*  32: 49 */       this.shapes.add(shape);
/*  33:    */     }
/*  34: 52 */     if (this.shapes.size() == 2) {
/*  35: 53 */       this.next = ((Shape)this.shapes.get(1));
/*  36:    */     }
/*  37:    */   }
/*  38:    */   
/*  39:    */   private boolean equalShapes(Shape a, Shape b)
/*  40:    */   {
/*  41: 65 */     a.checkPoints();
/*  42: 66 */     b.checkPoints();
/*  43: 68 */     for (int i = 0; i < a.points.length; i++) {
/*  44: 69 */       if (a.points[i] != b.points[i]) {
/*  45: 70 */         return false;
/*  46:    */       }
/*  47:    */     }
/*  48: 74 */     return true;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setMorphTime(float time)
/*  52:    */   {
/*  53: 84 */     int p = (int)time;
/*  54: 85 */     int n = p + 1;
/*  55: 86 */     float offset = time - p;
/*  56:    */     
/*  57: 88 */     p = rational(p);
/*  58: 89 */     n = rational(n);
/*  59:    */     
/*  60: 91 */     setFrame(p, n, offset);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void updateMorphTime(float delta)
/*  64:    */   {
/*  65:100 */     this.offset += delta;
/*  66:101 */     if (this.offset < 0.0F)
/*  67:    */     {
/*  68:102 */       int index = this.shapes.indexOf(this.current);
/*  69:103 */       if (index < 0) {
/*  70:104 */         index = this.shapes.size() - 1;
/*  71:    */       }
/*  72:107 */       int nframe = rational(index + 1);
/*  73:108 */       setFrame(index, nframe, this.offset);
/*  74:109 */       this.offset += 1.0F;
/*  75:    */     }
/*  76:110 */     else if (this.offset > 1.0F)
/*  77:    */     {
/*  78:111 */       int index = this.shapes.indexOf(this.next);
/*  79:112 */       if (index < 1) {
/*  80:113 */         index = 0;
/*  81:    */       }
/*  82:116 */       int nframe = rational(index + 1);
/*  83:117 */       setFrame(index, nframe, this.offset);
/*  84:118 */       this.offset -= 1.0F;
/*  85:    */     }
/*  86:    */     else
/*  87:    */     {
/*  88:120 */       this.pointsDirty = true;
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setExternalFrame(Shape current)
/*  93:    */   {
/*  94:130 */     this.current = current;
/*  95:131 */     this.next = ((Shape)this.shapes.get(0));
/*  96:132 */     this.offset = 0.0F;
/*  97:    */   }
/*  98:    */   
/*  99:    */   private int rational(int n)
/* 100:    */   {
/* 101:142 */     while (n >= this.shapes.size()) {
/* 102:143 */       n -= this.shapes.size();
/* 103:    */     }
/* 104:145 */     while (n < 0) {
/* 105:146 */       n += this.shapes.size();
/* 106:    */     }
/* 107:149 */     return n;
/* 108:    */   }
/* 109:    */   
/* 110:    */   private void setFrame(int a, int b, float offset)
/* 111:    */   {
/* 112:160 */     this.current = ((Shape)this.shapes.get(a));
/* 113:161 */     this.next = ((Shape)this.shapes.get(b));
/* 114:162 */     this.offset = offset;
/* 115:163 */     this.pointsDirty = true;
/* 116:    */   }
/* 117:    */   
/* 118:    */   protected void createPoints()
/* 119:    */   {
/* 120:170 */     if (this.current == this.next)
/* 121:    */     {
/* 122:171 */       System.arraycopy(this.current.points, 0, this.points, 0, this.points.length);
/* 123:172 */       return;
/* 124:    */     }
/* 125:175 */     float[] apoints = this.current.points;
/* 126:176 */     float[] bpoints = this.next.points;
/* 127:178 */     for (int i = 0; i < this.points.length; i++)
/* 128:    */     {
/* 129:179 */       this.points[i] = (apoints[i] * (1.0F - this.offset));
/* 130:180 */       this.points[i] += bpoints[i] * this.offset;
/* 131:    */     }
/* 132:    */   }
/* 133:    */   
/* 134:    */   public Shape transform(Transform transform)
/* 135:    */   {
/* 136:188 */     createPoints();
/* 137:189 */     Polygon poly = new Polygon(this.points);
/* 138:    */     
/* 139:191 */     return poly;
/* 140:    */   }
/* 141:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.geom.MorphShape
 * JD-Core Version:    0.7.0.1
 */