/*   1:    */ package org.newdawn.slick.opengl.renderer;
/*   2:    */ 
/*   3:    */ public class QuadBasedLineStripRenderer
/*   4:    */   implements LineStripRenderer
/*   5:    */ {
/*   6: 10 */   private SGL GL = Renderer.get();
/*   7: 13 */   public static int MAX_POINTS = 10000;
/*   8:    */   private boolean antialias;
/*   9: 17 */   private float width = 1.0F;
/*  10:    */   private float[] points;
/*  11:    */   private float[] colours;
/*  12:    */   private int pts;
/*  13:    */   private int cpt;
/*  14: 28 */   private DefaultLineStripRenderer def = new DefaultLineStripRenderer();
/*  15:    */   private boolean renderHalf;
/*  16: 33 */   private boolean lineCaps = false;
/*  17:    */   
/*  18:    */   public QuadBasedLineStripRenderer()
/*  19:    */   {
/*  20: 39 */     this.points = new float[MAX_POINTS * 2];
/*  21: 40 */     this.colours = new float[MAX_POINTS * 4];
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void setLineCaps(boolean caps)
/*  25:    */   {
/*  26: 49 */     this.lineCaps = caps;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void start()
/*  30:    */   {
/*  31: 56 */     if (this.width == 1.0F)
/*  32:    */     {
/*  33: 57 */       this.def.start();
/*  34: 58 */       return;
/*  35:    */     }
/*  36: 61 */     this.pts = 0;
/*  37: 62 */     this.cpt = 0;
/*  38: 63 */     this.GL.flush();
/*  39:    */     
/*  40: 65 */     float[] col = this.GL.getCurrentColor();
/*  41: 66 */     color(col[0], col[1], col[2], col[3]);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void end()
/*  45:    */   {
/*  46: 73 */     if (this.width == 1.0F)
/*  47:    */     {
/*  48: 74 */       this.def.end();
/*  49: 75 */       return;
/*  50:    */     }
/*  51: 78 */     renderLines(this.points, this.pts);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void vertex(float x, float y)
/*  55:    */   {
/*  56: 85 */     if (this.width == 1.0F)
/*  57:    */     {
/*  58: 86 */       this.def.vertex(x, y);
/*  59: 87 */       return;
/*  60:    */     }
/*  61: 90 */     this.points[(this.pts * 2)] = x;
/*  62: 91 */     this.points[(this.pts * 2 + 1)] = y;
/*  63: 92 */     this.pts += 1;
/*  64:    */     
/*  65: 94 */     int index = this.pts - 1;
/*  66: 95 */     color(this.colours[(index * 4)], this.colours[(index * 4 + 1)], this.colours[(index * 4 + 2)], this.colours[(index * 4 + 3)]);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setWidth(float width)
/*  70:    */   {
/*  71:102 */     this.width = width;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setAntiAlias(boolean antialias)
/*  75:    */   {
/*  76:109 */     this.def.setAntiAlias(antialias);
/*  77:110 */     this.antialias = antialias;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void renderLines(float[] points, int count)
/*  81:    */   {
/*  82:120 */     if (this.antialias)
/*  83:    */     {
/*  84:121 */       this.GL.glEnable(2881);
/*  85:122 */       renderLinesImpl(points, count, this.width + 1.0F);
/*  86:    */     }
/*  87:125 */     this.GL.glDisable(2881);
/*  88:126 */     renderLinesImpl(points, count, this.width);
/*  89:128 */     if (this.antialias) {
/*  90:129 */       this.GL.glEnable(2881);
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void renderLinesImpl(float[] points, int count, float w)
/*  95:    */   {
/*  96:141 */     float width = w / 2.0F;
/*  97:    */     
/*  98:143 */     float lastx1 = 0.0F;
/*  99:144 */     float lasty1 = 0.0F;
/* 100:145 */     float lastx2 = 0.0F;
/* 101:146 */     float lasty2 = 0.0F;
/* 102:    */     
/* 103:148 */     this.GL.glBegin(7);
/* 104:149 */     for (int i = 0; i < count + 1; i++)
/* 105:    */     {
/* 106:150 */       int current = i;
/* 107:151 */       int next = i + 1;
/* 108:152 */       int prev = i - 1;
/* 109:153 */       if (prev < 0) {
/* 110:154 */         prev += count;
/* 111:    */       }
/* 112:156 */       if (next >= count) {
/* 113:157 */         next -= count;
/* 114:    */       }
/* 115:159 */       if (current >= count) {
/* 116:160 */         current -= count;
/* 117:    */       }
/* 118:163 */       float x1 = points[(current * 2)];
/* 119:164 */       float y1 = points[(current * 2 + 1)];
/* 120:165 */       float x2 = points[(next * 2)];
/* 121:166 */       float y2 = points[(next * 2 + 1)];
/* 122:    */       
/* 123:    */ 
/* 124:169 */       float dx = x2 - x1;
/* 125:170 */       float dy = y2 - y1;
/* 126:172 */       if ((dx != 0.0F) || (dy != 0.0F))
/* 127:    */       {
/* 128:176 */         float d2 = dx * dx + dy * dy;
/* 129:177 */         float d = (float)Math.sqrt(d2);
/* 130:178 */         dx *= width;
/* 131:179 */         dy *= width;
/* 132:180 */         dx /= d;
/* 133:181 */         dy /= d;
/* 134:    */         
/* 135:183 */         float tx = dy;
/* 136:184 */         float ty = -dx;
/* 137:186 */         if (i != 0)
/* 138:    */         {
/* 139:187 */           bindColor(prev);
/* 140:188 */           this.GL.glVertex3f(lastx1, lasty1, 0.0F);
/* 141:189 */           this.GL.glVertex3f(lastx2, lasty2, 0.0F);
/* 142:190 */           bindColor(current);
/* 143:191 */           this.GL.glVertex3f(x1 + tx, y1 + ty, 0.0F);
/* 144:192 */           this.GL.glVertex3f(x1 - tx, y1 - ty, 0.0F);
/* 145:    */         }
/* 146:195 */         lastx1 = x2 - tx;
/* 147:196 */         lasty1 = y2 - ty;
/* 148:197 */         lastx2 = x2 + tx;
/* 149:198 */         lasty2 = y2 + ty;
/* 150:200 */         if (i < count - 1)
/* 151:    */         {
/* 152:201 */           bindColor(current);
/* 153:202 */           this.GL.glVertex3f(x1 + tx, y1 + ty, 0.0F);
/* 154:203 */           this.GL.glVertex3f(x1 - tx, y1 - ty, 0.0F);
/* 155:204 */           bindColor(next);
/* 156:205 */           this.GL.glVertex3f(x2 - tx, y2 - ty, 0.0F);
/* 157:206 */           this.GL.glVertex3f(x2 + tx, y2 + ty, 0.0F);
/* 158:    */         }
/* 159:    */       }
/* 160:    */     }
/* 161:210 */     this.GL.glEnd();
/* 162:    */     
/* 163:212 */     float step = width <= 12.5F ? 5.0F : 180.0F / (float)Math.ceil(width / 2.5D);
/* 164:215 */     if (this.lineCaps)
/* 165:    */     {
/* 166:216 */       float dx = points[2] - points[0];
/* 167:217 */       float dy = points[3] - points[1];
/* 168:218 */       float fang = (float)Math.toDegrees(Math.atan2(dy, dx)) + 90.0F;
/* 169:220 */       if ((dx != 0.0F) || (dy != 0.0F))
/* 170:    */       {
/* 171:221 */         this.GL.glBegin(6);
/* 172:222 */         bindColor(0);
/* 173:223 */         this.GL.glVertex2f(points[0], points[1]);
/* 174:224 */         for (int i = 0; i < 180.0F + step; i = (int)(i + step))
/* 175:    */         {
/* 176:225 */           float ang = (float)Math.toRadians(fang + i);
/* 177:226 */           this.GL.glVertex2f(points[0] + (float)(Math.cos(ang) * width), 
/* 178:227 */             points[1] + (float)(Math.sin(ang) * width));
/* 179:    */         }
/* 180:229 */         this.GL.glEnd();
/* 181:    */       }
/* 182:    */     }
/* 183:234 */     if (this.lineCaps)
/* 184:    */     {
/* 185:235 */       float dx = points[(count * 2 - 2)] - points[(count * 2 - 4)];
/* 186:236 */       float dy = points[(count * 2 - 1)] - points[(count * 2 - 3)];
/* 187:237 */       float fang = (float)Math.toDegrees(Math.atan2(dy, dx)) - 90.0F;
/* 188:239 */       if ((dx != 0.0F) || (dy != 0.0F))
/* 189:    */       {
/* 190:240 */         this.GL.glBegin(6);
/* 191:241 */         bindColor(count - 1);
/* 192:242 */         this.GL.glVertex2f(points[(count * 2 - 2)], points[(count * 2 - 1)]);
/* 193:243 */         for (int i = 0; i < 180.0F + step; i = (int)(i + step))
/* 194:    */         {
/* 195:244 */           float ang = (float)Math.toRadians(fang + i);
/* 196:245 */           this.GL.glVertex2f(points[(count * 2 - 2)] + (float)(Math.cos(ang) * width), 
/* 197:246 */             points[(count * 2 - 1)] + (float)(Math.sin(ang) * width));
/* 198:    */         }
/* 199:248 */         this.GL.glEnd();
/* 200:    */       }
/* 201:    */     }
/* 202:    */   }
/* 203:    */   
/* 204:    */   private void bindColor(int index)
/* 205:    */   {
/* 206:259 */     if (index < this.cpt) {
/* 207:260 */       if (this.renderHalf) {
/* 208:261 */         this.GL.glColor4f(this.colours[(index * 4)] * 0.5F, this.colours[(index * 4 + 1)] * 0.5F, 
/* 209:262 */           this.colours[(index * 4 + 2)] * 0.5F, this.colours[(index * 4 + 3)] * 0.5F);
/* 210:    */       } else {
/* 211:264 */         this.GL.glColor4f(this.colours[(index * 4)], this.colours[(index * 4 + 1)], 
/* 212:265 */           this.colours[(index * 4 + 2)], this.colours[(index * 4 + 3)]);
/* 213:    */       }
/* 214:    */     }
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void color(float r, float g, float b, float a)
/* 218:    */   {
/* 219:274 */     if (this.width == 1.0F)
/* 220:    */     {
/* 221:275 */       this.def.color(r, g, b, a);
/* 222:276 */       return;
/* 223:    */     }
/* 224:279 */     this.colours[(this.pts * 4)] = r;
/* 225:280 */     this.colours[(this.pts * 4 + 1)] = g;
/* 226:281 */     this.colours[(this.pts * 4 + 2)] = b;
/* 227:282 */     this.colours[(this.pts * 4 + 3)] = a;
/* 228:283 */     this.cpt += 1;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public boolean applyGLLineFixes()
/* 232:    */   {
/* 233:287 */     if (this.width == 1.0F) {
/* 234:288 */       return this.def.applyGLLineFixes();
/* 235:    */     }
/* 236:291 */     return this.def.applyGLLineFixes();
/* 237:    */   }
/* 238:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.opengl.renderer.QuadBasedLineStripRenderer
 * JD-Core Version:    0.7.0.1
 */