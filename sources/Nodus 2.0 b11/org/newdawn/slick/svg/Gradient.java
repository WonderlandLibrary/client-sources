/*   1:    */ package org.newdawn.slick.svg;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import org.newdawn.slick.Color;
/*   5:    */ import org.newdawn.slick.Image;
/*   6:    */ import org.newdawn.slick.ImageBuffer;
/*   7:    */ import org.newdawn.slick.geom.Transform;
/*   8:    */ 
/*   9:    */ public class Gradient
/*  10:    */ {
/*  11:    */   private String name;
/*  12: 19 */   private ArrayList steps = new ArrayList();
/*  13:    */   private float x1;
/*  14:    */   private float x2;
/*  15:    */   private float y1;
/*  16:    */   private float y2;
/*  17:    */   private float r;
/*  18:    */   private Image image;
/*  19:    */   private boolean radial;
/*  20:    */   private Transform transform;
/*  21:    */   private String ref;
/*  22:    */   
/*  23:    */   public Gradient(String name, boolean radial)
/*  24:    */   {
/*  25: 46 */     this.name = name;
/*  26: 47 */     this.radial = radial;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public boolean isRadial()
/*  30:    */   {
/*  31: 56 */     return this.radial;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void setTransform(Transform trans)
/*  35:    */   {
/*  36: 65 */     this.transform = trans;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public Transform getTransform()
/*  40:    */   {
/*  41: 74 */     return this.transform;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void reference(String ref)
/*  45:    */   {
/*  46: 83 */     this.ref = ref;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void resolve(Diagram diagram)
/*  50:    */   {
/*  51: 92 */     if (this.ref == null) {
/*  52: 93 */       return;
/*  53:    */     }
/*  54: 96 */     Gradient other = diagram.getGradient(this.ref);
/*  55: 98 */     for (int i = 0; i < other.steps.size(); i++) {
/*  56: 99 */       this.steps.add(other.steps.get(i));
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void genImage()
/*  61:    */   {
/*  62:107 */     if (this.image == null)
/*  63:    */     {
/*  64:108 */       ImageBuffer buffer = new ImageBuffer(128, 16);
/*  65:109 */       for (int i = 0; i < 128; i++)
/*  66:    */       {
/*  67:110 */         Color col = getColorAt(i / 128.0F);
/*  68:111 */         for (int j = 0; j < 16; j++) {
/*  69:112 */           buffer.setRGBA(i, j, col.getRedByte(), col.getGreenByte(), col.getBlueByte(), col.getAlphaByte());
/*  70:    */         }
/*  71:    */       }
/*  72:115 */       this.image = buffer.getImage();
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Image getImage()
/*  77:    */   {
/*  78:125 */     genImage();
/*  79:    */     
/*  80:127 */     return this.image;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setR(float r)
/*  84:    */   {
/*  85:136 */     this.r = r;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setX1(float x1)
/*  89:    */   {
/*  90:145 */     this.x1 = x1;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setX2(float x2)
/*  94:    */   {
/*  95:154 */     this.x2 = x2;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setY1(float y1)
/*  99:    */   {
/* 100:163 */     this.y1 = y1;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setY2(float y2)
/* 104:    */   {
/* 105:172 */     this.y2 = y2;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public float getR()
/* 109:    */   {
/* 110:181 */     return this.r;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public float getX1()
/* 114:    */   {
/* 115:190 */     return this.x1;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public float getX2()
/* 119:    */   {
/* 120:199 */     return this.x2;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public float getY1()
/* 124:    */   {
/* 125:208 */     return this.y1;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public float getY2()
/* 129:    */   {
/* 130:217 */     return this.y2;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void addStep(float location, Color c)
/* 134:    */   {
/* 135:227 */     this.steps.add(new Step(location, c));
/* 136:    */   }
/* 137:    */   
/* 138:    */   public Color getColorAt(float p)
/* 139:    */   {
/* 140:237 */     if (p <= 0.0F) {
/* 141:238 */       return ((Step)this.steps.get(0)).col;
/* 142:    */     }
/* 143:240 */     if (p > 1.0F) {
/* 144:241 */       return ((Step)this.steps.get(this.steps.size() - 1)).col;
/* 145:    */     }
/* 146:244 */     for (int i = 1; i < this.steps.size(); i++)
/* 147:    */     {
/* 148:245 */       Step prev = (Step)this.steps.get(i - 1);
/* 149:246 */       Step current = (Step)this.steps.get(i);
/* 150:248 */       if (p <= current.location)
/* 151:    */       {
/* 152:249 */         float dis = current.location - prev.location;
/* 153:250 */         p -= prev.location;
/* 154:251 */         float v = p / dis;
/* 155:    */         
/* 156:253 */         Color c = new Color(1, 1, 1, 1);
/* 157:254 */         c.a = (prev.col.a * (1.0F - v) + current.col.a * v);
/* 158:255 */         c.r = (prev.col.r * (1.0F - v) + current.col.r * v);
/* 159:256 */         c.g = (prev.col.g * (1.0F - v) + current.col.g * v);
/* 160:257 */         c.b = (prev.col.b * (1.0F - v) + current.col.b * v);
/* 161:    */         
/* 162:259 */         return c;
/* 163:    */       }
/* 164:    */     }
/* 165:264 */     return Color.black;
/* 166:    */   }
/* 167:    */   
/* 168:    */   private class Step
/* 169:    */   {
/* 170:    */     float location;
/* 171:    */     Color col;
/* 172:    */     
/* 173:    */     public Step(float location, Color c)
/* 174:    */     {
/* 175:285 */       this.location = location;
/* 176:286 */       this.col = c;
/* 177:    */     }
/* 178:    */   }
/* 179:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.svg.Gradient
 * JD-Core Version:    0.7.0.1
 */