/*     */ package org.newdawn.slick.geom;
/*     */ 
/*     */ import org.newdawn.slick.Image;
/*     */ import org.newdawn.slick.ShapeFill;
/*     */ import org.newdawn.slick.opengl.Texture;
/*     */ import org.newdawn.slick.opengl.TextureImpl;
/*     */ import org.newdawn.slick.opengl.renderer.LineStripRenderer;
/*     */ import org.newdawn.slick.opengl.renderer.Renderer;
/*     */ import org.newdawn.slick.opengl.renderer.SGL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ShapeRenderer
/*     */ {
/*  18 */   private static SGL GL = Renderer.get();
/*     */   
/*  20 */   private static LineStripRenderer LSR = Renderer.getLineStripRenderer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final void draw(Shape shape) {
/*  29 */     Texture t = TextureImpl.getLastBind();
/*  30 */     TextureImpl.bindNone();
/*     */     
/*  32 */     float[] points = shape.getPoints();
/*     */     
/*  34 */     LSR.start();
/*  35 */     for (int i = 0; i < points.length; i += 2) {
/*  36 */       LSR.vertex(points[i], points[i + 1]);
/*     */     }
/*     */     
/*  39 */     if (shape.closed()) {
/*  40 */       LSR.vertex(points[0], points[1]);
/*     */     }
/*     */     
/*  43 */     LSR.end();
/*     */     
/*  45 */     if (t == null) {
/*  46 */       TextureImpl.bindNone();
/*     */     } else {
/*  48 */       t.bind();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final void draw(Shape shape, ShapeFill fill) {
/*  60 */     float[] points = shape.getPoints();
/*     */     
/*  62 */     Texture t = TextureImpl.getLastBind();
/*  63 */     TextureImpl.bindNone();
/*     */     
/*  65 */     float[] center = shape.getCenter();
/*  66 */     GL.glBegin(3);
/*  67 */     for (int i = 0; i < points.length; i += 2) {
/*  68 */       fill.colorAt(shape, points[i], points[i + 1]).bind();
/*  69 */       Vector2f offset = fill.getOffsetAt(shape, points[i], points[i + 1]);
/*  70 */       GL.glVertex2f(points[i] + offset.x, points[i + 1] + offset.y);
/*     */     } 
/*     */     
/*  73 */     if (shape.closed()) {
/*  74 */       fill.colorAt(shape, points[0], points[1]).bind();
/*  75 */       Vector2f offset = fill.getOffsetAt(shape, points[0], points[1]);
/*  76 */       GL.glVertex2f(points[0] + offset.x, points[1] + offset.y);
/*     */     } 
/*  78 */     GL.glEnd();
/*     */     
/*  80 */     if (t == null) {
/*  81 */       TextureImpl.bindNone();
/*     */     } else {
/*  83 */       t.bind();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean validFill(Shape shape) {
/*  94 */     if (shape.getTriangles() == null) {
/*  95 */       return false;
/*     */     }
/*  97 */     return (shape.getTriangles().getTriangleCount() != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final void fill(Shape shape) {
/* 107 */     if (!validFill(shape)) {
/*     */       return;
/*     */     }
/*     */     
/* 111 */     Texture t = TextureImpl.getLastBind();
/* 112 */     TextureImpl.bindNone();
/*     */     
/* 114 */     fill(shape, new PointCallback()
/*     */         {
/*     */           public float[] preRenderPoint(Shape shape, float x, float y) {
/* 117 */             return null;
/*     */           }
/*     */         });
/*     */     
/* 121 */     if (t == null) {
/* 122 */       TextureImpl.bindNone();
/*     */     } else {
/* 124 */       t.bind();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final void fill(Shape shape, PointCallback callback) {
/* 136 */     Triangulator tris = shape.getTriangles();
/*     */     
/* 138 */     GL.glBegin(4);
/* 139 */     for (int i = 0; i < tris.getTriangleCount(); i++) {
/* 140 */       for (int p = 0; p < 3; p++) {
/* 141 */         float[] pt = tris.getTrianglePoint(i, p);
/* 142 */         float[] np = callback.preRenderPoint(shape, pt[0], pt[1]);
/*     */         
/* 144 */         if (np == null) {
/* 145 */           GL.glVertex2f(pt[0], pt[1]);
/*     */         } else {
/* 147 */           GL.glVertex2f(np[0], np[1]);
/*     */         } 
/*     */       } 
/*     */     } 
/* 151 */     GL.glEnd();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final void texture(Shape shape, Image image) {
/* 162 */     texture(shape, image, 0.01F, 0.01F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final void textureFit(Shape shape, Image image) {
/* 174 */     textureFit(shape, image, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final void texture(Shape shape, final Image image, final float scaleX, final float scaleY) {
/* 187 */     if (!validFill(shape)) {
/*     */       return;
/*     */     }
/*     */     
/* 191 */     Texture t = TextureImpl.getLastBind();
/* 192 */     image.getTexture().bind();
/*     */     
/* 194 */     fill(shape, new PointCallback() {
/*     */           public float[] preRenderPoint(Shape shape, float x, float y) {
/* 196 */             float tx = x * scaleX;
/* 197 */             float ty = y * scaleY;
/*     */             
/* 199 */             tx = image.getTextureOffsetX() + image.getTextureWidth() * tx;
/* 200 */             ty = image.getTextureOffsetY() + image.getTextureHeight() * ty;
/*     */             
/* 202 */             ShapeRenderer.GL.glTexCoord2f(tx, ty);
/* 203 */             return null;
/*     */           }
/*     */         });
/*     */     
/* 207 */     float[] points = shape.getPoints();
/*     */     
/* 209 */     if (t == null) {
/* 210 */       TextureImpl.bindNone();
/*     */     } else {
/* 212 */       t.bind();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final void textureFit(Shape shape, final Image image, final float scaleX, final float scaleY) {
/* 227 */     if (!validFill(shape)) {
/*     */       return;
/*     */     }
/*     */     
/* 231 */     float[] points = shape.getPoints();
/*     */     
/* 233 */     Texture t = TextureImpl.getLastBind();
/* 234 */     image.getTexture().bind();
/*     */     
/* 236 */     float minX = shape.getX();
/* 237 */     float minY = shape.getY();
/* 238 */     float maxX = shape.getMaxX() - minX;
/* 239 */     float maxY = shape.getMaxY() - minY;
/*     */     
/* 241 */     fill(shape, new PointCallback() {
/*     */           public float[] preRenderPoint(Shape shape, float x, float y) {
/* 243 */             x -= shape.getMinX();
/* 244 */             y -= shape.getMinY();
/*     */             
/* 246 */             x /= shape.getMaxX() - shape.getMinX();
/* 247 */             y /= shape.getMaxY() - shape.getMinY();
/*     */             
/* 249 */             float tx = x * scaleX;
/* 250 */             float ty = y * scaleY;
/*     */             
/* 252 */             tx = image.getTextureOffsetX() + image.getTextureWidth() * tx;
/* 253 */             ty = image.getTextureOffsetY() + image.getTextureHeight() * ty;
/*     */             
/* 255 */             ShapeRenderer.GL.glTexCoord2f(tx, ty);
/* 256 */             return null;
/*     */           }
/*     */         });
/*     */     
/* 260 */     if (t == null) {
/* 261 */       TextureImpl.bindNone();
/*     */     } else {
/* 263 */       t.bind();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final void fill(Shape shape, final ShapeFill fill) {
/* 275 */     if (!validFill(shape)) {
/*     */       return;
/*     */     }
/*     */     
/* 279 */     Texture t = TextureImpl.getLastBind();
/* 280 */     TextureImpl.bindNone();
/*     */     
/* 282 */     float[] center = shape.getCenter();
/* 283 */     fill(shape, new PointCallback() {
/*     */           public float[] preRenderPoint(Shape shape, float x, float y) {
/* 285 */             fill.colorAt(shape, x, y).bind();
/* 286 */             Vector2f offset = fill.getOffsetAt(shape, x, y);
/*     */             
/* 288 */             return new float[] { offset.x + x, offset.y + y };
/*     */           }
/*     */         });
/*     */     
/* 292 */     if (t == null) {
/* 293 */       TextureImpl.bindNone();
/*     */     } else {
/* 295 */       t.bind();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final void texture(Shape shape, final Image image, final float scaleX, final float scaleY, final ShapeFill fill) {
/* 311 */     if (!validFill(shape)) {
/*     */       return;
/*     */     }
/*     */     
/* 315 */     Texture t = TextureImpl.getLastBind();
/* 316 */     image.getTexture().bind();
/*     */     
/* 318 */     final float[] center = shape.getCenter();
/* 319 */     fill(shape, new PointCallback() {
/*     */           public float[] preRenderPoint(Shape shape, float x, float y) {
/* 321 */             fill.colorAt(shape, x - center[0], y - center[1]).bind();
/* 322 */             Vector2f offset = fill.getOffsetAt(shape, x, y);
/*     */             
/* 324 */             x += offset.x;
/* 325 */             y += offset.y;
/*     */             
/* 327 */             float tx = x * scaleX;
/* 328 */             float ty = y * scaleY;
/*     */             
/* 330 */             tx = image.getTextureOffsetX() + image.getTextureWidth() * tx;
/* 331 */             ty = image.getTextureOffsetY() + image.getTextureHeight() * ty;
/*     */             
/* 333 */             ShapeRenderer.GL.glTexCoord2f(tx, ty);
/*     */             
/* 335 */             return new float[] { offset.x + x, offset.y + y };
/*     */           }
/*     */         });
/*     */     
/* 339 */     if (t == null) {
/* 340 */       TextureImpl.bindNone();
/*     */     } else {
/* 342 */       t.bind();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final void texture(Shape shape, Image image, final TexCoordGenerator gen) {
/* 354 */     Texture t = TextureImpl.getLastBind();
/*     */     
/* 356 */     image.getTexture().bind();
/*     */     
/* 358 */     float[] center = shape.getCenter();
/* 359 */     fill(shape, new PointCallback() {
/*     */           public float[] preRenderPoint(Shape shape, float x, float y) {
/* 361 */             Vector2f tex = gen.getCoordFor(x, y);
/* 362 */             ShapeRenderer.GL.glTexCoord2f(tex.x, tex.y);
/*     */             
/* 364 */             return new float[] { x, y };
/*     */           }
/*     */         });
/*     */     
/* 368 */     if (t == null) {
/* 369 */       TextureImpl.bindNone();
/*     */     } else {
/* 371 */       t.bind();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static interface PointCallback {
/*     */     float[] preRenderPoint(Shape param1Shape, float param1Float1, float param1Float2);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\geom\ShapeRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */