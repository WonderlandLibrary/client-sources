/*   1:    */ package org.newdawn.slick.opengl.renderer;
/*   2:    */ 
/*   3:    */ import java.nio.DoubleBuffer;
/*   4:    */ import java.nio.FloatBuffer;
/*   5:    */ import org.lwjgl.BufferUtils;
/*   6:    */ import org.lwjgl.opengl.GL11;
/*   7:    */ 
/*   8:    */ public class VAOGLRenderer
/*   9:    */   extends ImmediateModeOGLRenderer
/*  10:    */ {
/*  11:    */   private static final int TOLERANCE = 20;
/*  12:    */   public static final int NONE = -1;
/*  13:    */   public static final int MAX_VERTS = 5000;
/*  14: 24 */   private int currentType = -1;
/*  15: 26 */   private float[] color = { 1.0F, 1.0F, 1.0F, 1.0F };
/*  16: 28 */   private float[] tex = { 0.0F, 0.0F };
/*  17:    */   private int vertIndex;
/*  18: 33 */   private float[] verts = new float[15000];
/*  19: 35 */   private float[] cols = new float[20000];
/*  20: 37 */   private float[] texs = new float[15000];
/*  21: 40 */   private FloatBuffer vertices = BufferUtils.createFloatBuffer(15000);
/*  22: 42 */   private FloatBuffer colors = BufferUtils.createFloatBuffer(20000);
/*  23: 44 */   private FloatBuffer textures = BufferUtils.createFloatBuffer(10000);
/*  24: 47 */   private int listMode = 0;
/*  25:    */   
/*  26:    */   public void initDisplay(int width, int height)
/*  27:    */   {
/*  28: 53 */     super.initDisplay(width, height);
/*  29:    */     
/*  30: 55 */     startBuffer();
/*  31: 56 */     GL11.glEnableClientState(32884);
/*  32: 57 */     GL11.glEnableClientState(32888);
/*  33: 58 */     GL11.glEnableClientState(32886);
/*  34:    */   }
/*  35:    */   
/*  36:    */   private void startBuffer()
/*  37:    */   {
/*  38: 65 */     this.vertIndex = 0;
/*  39:    */   }
/*  40:    */   
/*  41:    */   private void flushBuffer()
/*  42:    */   {
/*  43: 72 */     if (this.vertIndex == 0) {
/*  44: 73 */       return;
/*  45:    */     }
/*  46: 75 */     if (this.currentType == -1) {
/*  47: 76 */       return;
/*  48:    */     }
/*  49: 79 */     if (this.vertIndex < 20)
/*  50:    */     {
/*  51: 80 */       GL11.glBegin(this.currentType);
/*  52: 81 */       for (int i = 0; i < this.vertIndex; i++)
/*  53:    */       {
/*  54: 82 */         GL11.glColor4f(this.cols[(i * 4 + 0)], this.cols[(i * 4 + 1)], this.cols[(i * 4 + 2)], this.cols[(i * 4 + 3)]);
/*  55: 83 */         GL11.glTexCoord2f(this.texs[(i * 2 + 0)], this.texs[(i * 2 + 1)]);
/*  56: 84 */         GL11.glVertex3f(this.verts[(i * 3 + 0)], this.verts[(i * 3 + 1)], this.verts[(i * 3 + 2)]);
/*  57:    */       }
/*  58: 86 */       GL11.glEnd();
/*  59: 87 */       this.currentType = -1;
/*  60: 88 */       return;
/*  61:    */     }
/*  62: 90 */     this.vertices.clear();
/*  63: 91 */     this.colors.clear();
/*  64: 92 */     this.textures.clear();
/*  65:    */     
/*  66: 94 */     this.vertices.put(this.verts, 0, this.vertIndex * 3);
/*  67: 95 */     this.colors.put(this.cols, 0, this.vertIndex * 4);
/*  68: 96 */     this.textures.put(this.texs, 0, this.vertIndex * 2);
/*  69:    */     
/*  70: 98 */     this.vertices.flip();
/*  71: 99 */     this.colors.flip();
/*  72:100 */     this.textures.flip();
/*  73:    */     
/*  74:102 */     GL11.glVertexPointer(3, 0, this.vertices);
/*  75:103 */     GL11.glColorPointer(4, 0, this.colors);
/*  76:104 */     GL11.glTexCoordPointer(2, 0, this.textures);
/*  77:    */     
/*  78:106 */     GL11.glDrawArrays(this.currentType, 0, this.vertIndex);
/*  79:107 */     this.currentType = -1;
/*  80:    */   }
/*  81:    */   
/*  82:    */   private void applyBuffer()
/*  83:    */   {
/*  84:114 */     if (this.listMode > 0) {
/*  85:115 */       return;
/*  86:    */     }
/*  87:118 */     if (this.vertIndex != 0)
/*  88:    */     {
/*  89:119 */       flushBuffer();
/*  90:120 */       startBuffer();
/*  91:    */     }
/*  92:123 */     super.glColor4f(this.color[0], this.color[1], this.color[2], this.color[3]);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void flush()
/*  96:    */   {
/*  97:130 */     super.flush();
/*  98:    */     
/*  99:132 */     applyBuffer();
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void glBegin(int geomType)
/* 103:    */   {
/* 104:139 */     if (this.listMode > 0)
/* 105:    */     {
/* 106:140 */       super.glBegin(geomType);
/* 107:141 */       return;
/* 108:    */     }
/* 109:144 */     if (this.currentType != geomType)
/* 110:    */     {
/* 111:145 */       applyBuffer();
/* 112:146 */       this.currentType = geomType;
/* 113:    */     }
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void glColor4f(float r, float g, float b, float a)
/* 117:    */   {
/* 118:154 */     a *= this.alphaScale;
/* 119:    */     
/* 120:156 */     this.color[0] = r;
/* 121:157 */     this.color[1] = g;
/* 122:158 */     this.color[2] = b;
/* 123:159 */     this.color[3] = a;
/* 124:161 */     if (this.listMode > 0)
/* 125:    */     {
/* 126:162 */       super.glColor4f(r, g, b, a);
/* 127:163 */       return;
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void glEnd()
/* 132:    */   {
/* 133:171 */     if (this.listMode > 0)
/* 134:    */     {
/* 135:172 */       super.glEnd();
/* 136:173 */       return;
/* 137:    */     }
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void glTexCoord2f(float u, float v)
/* 141:    */   {
/* 142:181 */     if (this.listMode > 0)
/* 143:    */     {
/* 144:182 */       super.glTexCoord2f(u, v);
/* 145:183 */       return;
/* 146:    */     }
/* 147:186 */     this.tex[0] = u;
/* 148:187 */     this.tex[1] = v;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void glVertex2f(float x, float y)
/* 152:    */   {
/* 153:194 */     if (this.listMode > 0)
/* 154:    */     {
/* 155:195 */       super.glVertex2f(x, y);
/* 156:196 */       return;
/* 157:    */     }
/* 158:199 */     glVertex3f(x, y, 0.0F);
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void glVertex3f(float x, float y, float z)
/* 162:    */   {
/* 163:206 */     if (this.listMode > 0)
/* 164:    */     {
/* 165:207 */       super.glVertex3f(x, y, z);
/* 166:208 */       return;
/* 167:    */     }
/* 168:211 */     this.verts[(this.vertIndex * 3 + 0)] = x;
/* 169:212 */     this.verts[(this.vertIndex * 3 + 1)] = y;
/* 170:213 */     this.verts[(this.vertIndex * 3 + 2)] = z;
/* 171:214 */     this.cols[(this.vertIndex * 4 + 0)] = this.color[0];
/* 172:215 */     this.cols[(this.vertIndex * 4 + 1)] = this.color[1];
/* 173:216 */     this.cols[(this.vertIndex * 4 + 2)] = this.color[2];
/* 174:217 */     this.cols[(this.vertIndex * 4 + 3)] = this.color[3];
/* 175:218 */     this.texs[(this.vertIndex * 2 + 0)] = this.tex[0];
/* 176:219 */     this.texs[(this.vertIndex * 2 + 1)] = this.tex[1];
/* 177:220 */     this.vertIndex += 1;
/* 178:222 */     if ((this.vertIndex > 4950) && 
/* 179:223 */       (isSplittable(this.vertIndex, this.currentType)))
/* 180:    */     {
/* 181:224 */       int type = this.currentType;
/* 182:225 */       applyBuffer();
/* 183:226 */       this.currentType = type;
/* 184:    */     }
/* 185:    */   }
/* 186:    */   
/* 187:    */   private boolean isSplittable(int count, int type)
/* 188:    */   {
/* 189:239 */     switch (type)
/* 190:    */     {
/* 191:    */     case 7: 
/* 192:241 */       return count % 4 == 0;
/* 193:    */     case 4: 
/* 194:243 */       return count % 3 == 0;
/* 195:    */     case 6913: 
/* 196:245 */       return count % 2 == 0;
/* 197:    */     }
/* 198:248 */     return false;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void glBindTexture(int target, int id)
/* 202:    */   {
/* 203:255 */     applyBuffer();
/* 204:256 */     super.glBindTexture(target, id);
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void glBlendFunc(int src, int dest)
/* 208:    */   {
/* 209:263 */     applyBuffer();
/* 210:264 */     super.glBlendFunc(src, dest);
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void glCallList(int id)
/* 214:    */   {
/* 215:271 */     applyBuffer();
/* 216:272 */     super.glCallList(id);
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void glClear(int value)
/* 220:    */   {
/* 221:279 */     applyBuffer();
/* 222:280 */     super.glClear(value);
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void glClipPlane(int plane, DoubleBuffer buffer)
/* 226:    */   {
/* 227:287 */     applyBuffer();
/* 228:288 */     super.glClipPlane(plane, buffer);
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void glColorMask(boolean red, boolean green, boolean blue, boolean alpha)
/* 232:    */   {
/* 233:295 */     applyBuffer();
/* 234:296 */     super.glColorMask(red, green, blue, alpha);
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void glDisable(int item)
/* 238:    */   {
/* 239:303 */     applyBuffer();
/* 240:304 */     super.glDisable(item);
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void glEnable(int item)
/* 244:    */   {
/* 245:311 */     applyBuffer();
/* 246:312 */     super.glEnable(item);
/* 247:    */   }
/* 248:    */   
/* 249:    */   public void glLineWidth(float width)
/* 250:    */   {
/* 251:319 */     applyBuffer();
/* 252:320 */     super.glLineWidth(width);
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void glPointSize(float size)
/* 256:    */   {
/* 257:327 */     applyBuffer();
/* 258:328 */     super.glPointSize(size);
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void glPopMatrix()
/* 262:    */   {
/* 263:335 */     applyBuffer();
/* 264:336 */     super.glPopMatrix();
/* 265:    */   }
/* 266:    */   
/* 267:    */   public void glPushMatrix()
/* 268:    */   {
/* 269:343 */     applyBuffer();
/* 270:344 */     super.glPushMatrix();
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void glRotatef(float angle, float x, float y, float z)
/* 274:    */   {
/* 275:351 */     applyBuffer();
/* 276:352 */     super.glRotatef(angle, x, y, z);
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void glScalef(float x, float y, float z)
/* 280:    */   {
/* 281:359 */     applyBuffer();
/* 282:360 */     super.glScalef(x, y, z);
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void glScissor(int x, int y, int width, int height)
/* 286:    */   {
/* 287:367 */     applyBuffer();
/* 288:368 */     super.glScissor(x, y, width, height);
/* 289:    */   }
/* 290:    */   
/* 291:    */   public void glTexEnvi(int target, int mode, int value)
/* 292:    */   {
/* 293:375 */     applyBuffer();
/* 294:376 */     super.glTexEnvi(target, mode, value);
/* 295:    */   }
/* 296:    */   
/* 297:    */   public void glTranslatef(float x, float y, float z)
/* 298:    */   {
/* 299:383 */     applyBuffer();
/* 300:384 */     super.glTranslatef(x, y, z);
/* 301:    */   }
/* 302:    */   
/* 303:    */   public void glEndList()
/* 304:    */   {
/* 305:391 */     this.listMode -= 1;
/* 306:392 */     super.glEndList();
/* 307:    */   }
/* 308:    */   
/* 309:    */   public void glNewList(int id, int option)
/* 310:    */   {
/* 311:399 */     this.listMode += 1;
/* 312:400 */     super.glNewList(id, option);
/* 313:    */   }
/* 314:    */   
/* 315:    */   public float[] getCurrentColor()
/* 316:    */   {
/* 317:407 */     return this.color;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public void glLoadMatrix(FloatBuffer buffer)
/* 321:    */   {
/* 322:414 */     flushBuffer();
/* 323:415 */     super.glLoadMatrix(buffer);
/* 324:    */   }
/* 325:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.opengl.renderer.VAOGLRenderer
 * JD-Core Version:    0.7.0.1
 */