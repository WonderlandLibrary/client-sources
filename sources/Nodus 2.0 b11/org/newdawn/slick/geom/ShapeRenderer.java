/*   1:    */ package org.newdawn.slick.geom;
/*   2:    */ 
/*   3:    */ import org.newdawn.slick.Color;
/*   4:    */ import org.newdawn.slick.Image;
/*   5:    */ import org.newdawn.slick.ShapeFill;
/*   6:    */ import org.newdawn.slick.opengl.Texture;
/*   7:    */ import org.newdawn.slick.opengl.TextureImpl;
/*   8:    */ import org.newdawn.slick.opengl.renderer.LineStripRenderer;
/*   9:    */ import org.newdawn.slick.opengl.renderer.Renderer;
/*  10:    */ import org.newdawn.slick.opengl.renderer.SGL;
/*  11:    */ 
/*  12:    */ public final class ShapeRenderer
/*  13:    */ {
/*  14: 18 */   private static SGL GL = ;
/*  15: 20 */   private static LineStripRenderer LSR = Renderer.getLineStripRenderer();
/*  16:    */   
/*  17:    */   public static final void draw(Shape shape)
/*  18:    */   {
/*  19: 29 */     Texture t = TextureImpl.getLastBind();
/*  20: 30 */     TextureImpl.bindNone();
/*  21:    */     
/*  22: 32 */     float[] points = shape.getPoints();
/*  23:    */     
/*  24: 34 */     LSR.start();
/*  25: 35 */     for (int i = 0; i < points.length; i += 2) {
/*  26: 36 */       LSR.vertex(points[i], points[(i + 1)]);
/*  27:    */     }
/*  28: 39 */     if (shape.closed()) {
/*  29: 40 */       LSR.vertex(points[0], points[1]);
/*  30:    */     }
/*  31: 43 */     LSR.end();
/*  32: 45 */     if (t == null) {
/*  33: 46 */       TextureImpl.bindNone();
/*  34:    */     } else {
/*  35: 48 */       t.bind();
/*  36:    */     }
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static final void draw(Shape shape, ShapeFill fill)
/*  40:    */   {
/*  41: 60 */     float[] points = shape.getPoints();
/*  42:    */     
/*  43: 62 */     Texture t = TextureImpl.getLastBind();
/*  44: 63 */     TextureImpl.bindNone();
/*  45:    */     
/*  46: 65 */     float[] center = shape.getCenter();
/*  47: 66 */     GL.glBegin(3);
/*  48: 67 */     for (int i = 0; i < points.length; i += 2)
/*  49:    */     {
/*  50: 68 */       fill.colorAt(shape, points[i], points[(i + 1)]).bind();
/*  51: 69 */       Vector2f offset = fill.getOffsetAt(shape, points[i], points[(i + 1)]);
/*  52: 70 */       GL.glVertex2f(points[i] + offset.x, points[(i + 1)] + offset.y);
/*  53:    */     }
/*  54: 73 */     if (shape.closed())
/*  55:    */     {
/*  56: 74 */       fill.colorAt(shape, points[0], points[1]).bind();
/*  57: 75 */       Vector2f offset = fill.getOffsetAt(shape, points[0], points[1]);
/*  58: 76 */       GL.glVertex2f(points[0] + offset.x, points[1] + offset.y);
/*  59:    */     }
/*  60: 78 */     GL.glEnd();
/*  61: 80 */     if (t == null) {
/*  62: 81 */       TextureImpl.bindNone();
/*  63:    */     } else {
/*  64: 83 */       t.bind();
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static boolean validFill(Shape shape)
/*  69:    */   {
/*  70: 94 */     if (shape.getTriangles() == null) {
/*  71: 95 */       return false;
/*  72:    */     }
/*  73: 97 */     return shape.getTriangles().getTriangleCount() != 0;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static final void fill(Shape shape)
/*  77:    */   {
/*  78:107 */     if (!validFill(shape)) {
/*  79:108 */       return;
/*  80:    */     }
/*  81:111 */     Texture t = TextureImpl.getLastBind();
/*  82:112 */     TextureImpl.bindNone();
/*  83:    */     
/*  84:114 */     fill(shape, new PointCallback()
/*  85:    */     {
/*  86:    */       public float[] preRenderPoint(Shape shape, float x, float y)
/*  87:    */       {
/*  88:117 */         return null;
/*  89:    */       }
/*  90:    */     });
/*  91:121 */     if (t == null) {
/*  92:122 */       TextureImpl.bindNone();
/*  93:    */     } else {
/*  94:124 */       t.bind();
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   private static final void fill(Shape shape, PointCallback callback)
/*  99:    */   {
/* 100:136 */     Triangulator tris = shape.getTriangles();
/* 101:    */     
/* 102:138 */     GL.glBegin(4);
/* 103:139 */     for (int i = 0; i < tris.getTriangleCount(); i++) {
/* 104:140 */       for (int p = 0; p < 3; p++)
/* 105:    */       {
/* 106:141 */         float[] pt = tris.getTrianglePoint(i, p);
/* 107:142 */         float[] np = callback.preRenderPoint(shape, pt[0], pt[1]);
/* 108:144 */         if (np == null) {
/* 109:145 */           GL.glVertex2f(pt[0], pt[1]);
/* 110:    */         } else {
/* 111:147 */           GL.glVertex2f(np[0], np[1]);
/* 112:    */         }
/* 113:    */       }
/* 114:    */     }
/* 115:151 */     GL.glEnd();
/* 116:    */   }
/* 117:    */   
/* 118:    */   public static final void texture(Shape shape, Image image)
/* 119:    */   {
/* 120:162 */     texture(shape, image, 0.01F, 0.01F);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public static final void textureFit(Shape shape, Image image)
/* 124:    */   {
/* 125:174 */     textureFit(shape, image, 1.0F, 1.0F);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public static final void texture(Shape shape, final Image image, float scaleX, final float scaleY)
/* 129:    */   {
/* 130:187 */     if (!validFill(shape)) {
/* 131:188 */       return;
/* 132:    */     }
/* 133:191 */     Texture t = TextureImpl.getLastBind();
/* 134:192 */     image.getTexture().bind();
/* 135:    */     
/* 136:194 */     fill(shape, new PointCallback()
/* 137:    */     {
/* 138:    */       public float[] preRenderPoint(Shape shape, float x, float y)
/* 139:    */       {
/* 140:196 */         float tx = x * this.val$scaleX;
/* 141:197 */         float ty = y * scaleY;
/* 142:    */         
/* 143:199 */         tx = image.getTextureOffsetX() + image.getTextureWidth() * tx;
/* 144:200 */         ty = image.getTextureOffsetY() + image.getTextureHeight() * ty;
/* 145:    */         
/* 146:202 */         ShapeRenderer.GL.glTexCoord2f(tx, ty);
/* 147:203 */         return null;
/* 148:    */       }
/* 149:206 */     });
/* 150:207 */     float[] points = shape.getPoints();
/* 151:209 */     if (t == null) {
/* 152:210 */       TextureImpl.bindNone();
/* 153:    */     } else {
/* 154:212 */       t.bind();
/* 155:    */     }
/* 156:    */   }
/* 157:    */   
/* 158:    */   public static final void textureFit(Shape shape, final Image image, float scaleX, final float scaleY)
/* 159:    */   {
/* 160:227 */     if (!validFill(shape)) {
/* 161:228 */       return;
/* 162:    */     }
/* 163:231 */     float[] points = shape.getPoints();
/* 164:    */     
/* 165:233 */     Texture t = TextureImpl.getLastBind();
/* 166:234 */     image.getTexture().bind();
/* 167:    */     
/* 168:236 */     float minX = shape.getX();
/* 169:237 */     float minY = shape.getY();
/* 170:238 */     float maxX = shape.getMaxX() - minX;
/* 171:239 */     float maxY = shape.getMaxY() - minY;
/* 172:    */     
/* 173:241 */     fill(shape, new PointCallback()
/* 174:    */     {
/* 175:    */       public float[] preRenderPoint(Shape shape, float x, float y)
/* 176:    */       {
/* 177:243 */         x -= shape.getMinX();
/* 178:244 */         y -= shape.getMinY();
/* 179:    */         
/* 180:246 */         x /= (shape.getMaxX() - shape.getMinX());
/* 181:247 */         y /= (shape.getMaxY() - shape.getMinY());
/* 182:    */         
/* 183:249 */         float tx = x * this.val$scaleX;
/* 184:250 */         float ty = y * scaleY;
/* 185:    */         
/* 186:252 */         tx = image.getTextureOffsetX() + image.getTextureWidth() * tx;
/* 187:253 */         ty = image.getTextureOffsetY() + image.getTextureHeight() * ty;
/* 188:    */         
/* 189:255 */         ShapeRenderer.GL.glTexCoord2f(tx, ty);
/* 190:256 */         return null;
/* 191:    */       }
/* 192:    */     });
/* 193:260 */     if (t == null) {
/* 194:261 */       TextureImpl.bindNone();
/* 195:    */     } else {
/* 196:263 */       t.bind();
/* 197:    */     }
/* 198:    */   }
/* 199:    */   
/* 200:    */   public static final void fill(Shape shape, ShapeFill fill)
/* 201:    */   {
/* 202:275 */     if (!validFill(shape)) {
/* 203:276 */       return;
/* 204:    */     }
/* 205:279 */     Texture t = TextureImpl.getLastBind();
/* 206:280 */     TextureImpl.bindNone();
/* 207:    */     
/* 208:282 */     float[] center = shape.getCenter();
/* 209:283 */     fill(shape, new PointCallback()
/* 210:    */     {
/* 211:    */       public float[] preRenderPoint(Shape shape, float x, float y)
/* 212:    */       {
/* 213:285 */         ShapeRenderer.this.colorAt(shape, x, y).bind();
/* 214:286 */         Vector2f offset = ShapeRenderer.this.getOffsetAt(shape, x, y);
/* 215:    */         
/* 216:288 */         return new float[] { offset.x + x, offset.y + y };
/* 217:    */       }
/* 218:    */     });
/* 219:292 */     if (t == null) {
/* 220:293 */       TextureImpl.bindNone();
/* 221:    */     } else {
/* 222:295 */       t.bind();
/* 223:    */     }
/* 224:    */   }
/* 225:    */   
/* 226:    */   public static final void texture(Shape shape, final Image image, final float scaleX, final float scaleY, ShapeFill fill)
/* 227:    */   {
/* 228:311 */     if (!validFill(shape)) {
/* 229:312 */       return;
/* 230:    */     }
/* 231:315 */     Texture t = TextureImpl.getLastBind();
/* 232:316 */     image.getTexture().bind();
/* 233:    */     
/* 234:318 */     final float[] center = shape.getCenter();
/* 235:319 */     fill(shape, new PointCallback()
/* 236:    */     {
/* 237:    */       public float[] preRenderPoint(Shape shape, float x, float y)
/* 238:    */       {
/* 239:321 */         ShapeRenderer.this.colorAt(shape, x - center[0], y - center[1]).bind();
/* 240:322 */         Vector2f offset = ShapeRenderer.this.getOffsetAt(shape, x, y);
/* 241:    */         
/* 242:324 */         x += offset.x;
/* 243:325 */         y += offset.y;
/* 244:    */         
/* 245:327 */         float tx = x * scaleX;
/* 246:328 */         float ty = y * scaleY;
/* 247:    */         
/* 248:330 */         tx = image.getTextureOffsetX() + image.getTextureWidth() * tx;
/* 249:331 */         ty = image.getTextureOffsetY() + image.getTextureHeight() * ty;
/* 250:    */         
/* 251:333 */         ShapeRenderer.GL.glTexCoord2f(tx, ty);
/* 252:    */         
/* 253:335 */         return new float[] { offset.x + x, offset.y + y };
/* 254:    */       }
/* 255:    */     });
/* 256:339 */     if (t == null) {
/* 257:340 */       TextureImpl.bindNone();
/* 258:    */     } else {
/* 259:342 */       t.bind();
/* 260:    */     }
/* 261:    */   }
/* 262:    */   
/* 263:    */   public static final void texture(Shape shape, Image image, TexCoordGenerator gen)
/* 264:    */   {
/* 265:354 */     Texture t = TextureImpl.getLastBind();
/* 266:    */     
/* 267:356 */     image.getTexture().bind();
/* 268:    */     
/* 269:358 */     float[] center = shape.getCenter();
/* 270:359 */     fill(shape, new PointCallback()
/* 271:    */     {
/* 272:    */       public float[] preRenderPoint(Shape shape, float x, float y)
/* 273:    */       {
/* 274:361 */         Vector2f tex = ShapeRenderer.this.getCoordFor(x, y);
/* 275:362 */         ShapeRenderer.GL.glTexCoord2f(tex.x, tex.y);
/* 276:    */         
/* 277:364 */         return new float[] { x, y };
/* 278:    */       }
/* 279:    */     });
/* 280:368 */     if (t == null) {
/* 281:369 */       TextureImpl.bindNone();
/* 282:    */     } else {
/* 283:371 */       t.bind();
/* 284:    */     }
/* 285:    */   }
/* 286:    */   
/* 287:    */   private static abstract interface PointCallback
/* 288:    */   {
/* 289:    */     public abstract float[] preRenderPoint(Shape paramShape, float paramFloat1, float paramFloat2);
/* 290:    */   }
/* 291:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.geom.ShapeRenderer
 * JD-Core Version:    0.7.0.1
 */