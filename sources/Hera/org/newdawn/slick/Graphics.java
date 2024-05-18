/*      */ package org.newdawn.slick;
/*      */ 
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.DoubleBuffer;
/*      */ import java.nio.FloatBuffer;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.ArrayList;
/*      */ import org.lwjgl.BufferUtils;
/*      */ import org.newdawn.slick.geom.Rectangle;
/*      */ import org.newdawn.slick.geom.Shape;
/*      */ import org.newdawn.slick.geom.ShapeRenderer;
/*      */ import org.newdawn.slick.opengl.TextureImpl;
/*      */ import org.newdawn.slick.opengl.renderer.LineStripRenderer;
/*      */ import org.newdawn.slick.opengl.renderer.Renderer;
/*      */ import org.newdawn.slick.opengl.renderer.SGL;
/*      */ import org.newdawn.slick.util.FastTrig;
/*      */ import org.newdawn.slick.util.Log;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Graphics
/*      */ {
/*   29 */   protected static SGL GL = Renderer.get();
/*      */   
/*   31 */   private static LineStripRenderer LSR = Renderer.getLineStripRenderer();
/*      */ 
/*      */   
/*   34 */   public static int MODE_NORMAL = 1;
/*      */ 
/*      */   
/*   37 */   public static int MODE_ALPHA_MAP = 2;
/*      */ 
/*      */   
/*   40 */   public static int MODE_ALPHA_BLEND = 3;
/*      */ 
/*      */   
/*   43 */   public static int MODE_COLOR_MULTIPLY = 4;
/*      */ 
/*      */   
/*   46 */   public static int MODE_ADD = 5;
/*      */ 
/*      */   
/*   49 */   public static int MODE_SCREEN = 6;
/*      */ 
/*      */   
/*      */   private static final int DEFAULT_SEGMENTS = 50;
/*      */ 
/*      */   
/*   55 */   protected static Graphics currentGraphics = null;
/*      */ 
/*      */   
/*      */   protected static Font DEFAULT_FONT;
/*      */ 
/*      */   
/*   61 */   private float sx = 1.0F;
/*      */   
/*   63 */   private float sy = 1.0F;
/*      */ 
/*      */   
/*      */   private Font font;
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setCurrent(Graphics current) {
/*   71 */     if (currentGraphics != current) {
/*   72 */       if (currentGraphics != null) {
/*   73 */         currentGraphics.disable();
/*      */       }
/*   75 */       currentGraphics = current;
/*   76 */       currentGraphics.enable();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   84 */   private Color currentColor = Color.white;
/*      */ 
/*      */   
/*      */   protected int screenWidth;
/*      */ 
/*      */   
/*      */   protected int screenHeight;
/*      */ 
/*      */   
/*      */   private boolean pushed;
/*      */ 
/*      */   
/*      */   private Rectangle clip;
/*      */ 
/*      */   
/*   99 */   private DoubleBuffer worldClip = BufferUtils.createDoubleBuffer(4);
/*      */ 
/*      */   
/*  102 */   private ByteBuffer readBuffer = BufferUtils.createByteBuffer(4);
/*      */ 
/*      */   
/*      */   private boolean antialias;
/*      */ 
/*      */   
/*      */   private Rectangle worldClipRecord;
/*      */ 
/*      */   
/*  111 */   private int currentDrawingMode = MODE_NORMAL;
/*      */ 
/*      */   
/*  114 */   private float lineWidth = 1.0F;
/*      */ 
/*      */   
/*  117 */   private ArrayList stack = new ArrayList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int stackIndex;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Graphics() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Graphics(int width, int height) {
/*  137 */     if (DEFAULT_FONT == null) {
/*  138 */       AccessController.doPrivileged(new PrivilegedAction() {
/*      */             public Object run() {
/*      */               try {
/*  141 */                 Graphics.DEFAULT_FONT = new AngelCodeFont("org/newdawn/slick/data/defaultfont.fnt", "org/newdawn/slick/data/defaultfont.png");
/*      */               
/*      */               }
/*  144 */               catch (SlickException e) {
/*  145 */                 Log.error(e);
/*      */               } 
/*  147 */               return null;
/*      */             }
/*      */           });
/*      */     }
/*      */     
/*  152 */     this.font = DEFAULT_FONT;
/*  153 */     this.screenWidth = width;
/*  154 */     this.screenHeight = height;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void setDimensions(int width, int height) {
/*  164 */     this.screenWidth = width;
/*  165 */     this.screenHeight = height;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDrawMode(int mode) {
/*  179 */     predraw();
/*  180 */     this.currentDrawingMode = mode;
/*  181 */     if (this.currentDrawingMode == MODE_NORMAL) {
/*  182 */       GL.glEnable(3042);
/*  183 */       GL.glColorMask(true, true, true, true);
/*  184 */       GL.glBlendFunc(770, 771);
/*      */     } 
/*  186 */     if (this.currentDrawingMode == MODE_ALPHA_MAP) {
/*  187 */       GL.glDisable(3042);
/*  188 */       GL.glColorMask(false, false, false, true);
/*      */     } 
/*  190 */     if (this.currentDrawingMode == MODE_ALPHA_BLEND) {
/*  191 */       GL.glEnable(3042);
/*  192 */       GL.glColorMask(true, true, true, false);
/*  193 */       GL.glBlendFunc(772, 773);
/*      */     } 
/*  195 */     if (this.currentDrawingMode == MODE_COLOR_MULTIPLY) {
/*  196 */       GL.glEnable(3042);
/*  197 */       GL.glColorMask(true, true, true, true);
/*  198 */       GL.glBlendFunc(769, 768);
/*      */     } 
/*  200 */     if (this.currentDrawingMode == MODE_ADD) {
/*  201 */       GL.glEnable(3042);
/*  202 */       GL.glColorMask(true, true, true, true);
/*  203 */       GL.glBlendFunc(1, 1);
/*      */     } 
/*  205 */     if (this.currentDrawingMode == MODE_SCREEN) {
/*  206 */       GL.glEnable(3042);
/*  207 */       GL.glColorMask(true, true, true, true);
/*  208 */       GL.glBlendFunc(1, 769);
/*      */     } 
/*  210 */     postdraw();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearAlphaMap() {
/*  219 */     pushTransform();
/*  220 */     GL.glLoadIdentity();
/*      */     
/*  222 */     int originalMode = this.currentDrawingMode;
/*  223 */     setDrawMode(MODE_ALPHA_MAP);
/*  224 */     setColor(new Color(0, 0, 0, 0));
/*  225 */     fillRect(0.0F, 0.0F, this.screenWidth, this.screenHeight);
/*  226 */     setColor(this.currentColor);
/*  227 */     setDrawMode(originalMode);
/*      */     
/*  229 */     popTransform();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void predraw() {
/*  237 */     setCurrent(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void postdraw() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void enable() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void flush() {
/*  257 */     if (currentGraphics == this) {
/*  258 */       currentGraphics.disable();
/*  259 */       currentGraphics = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void disable() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Font getFont() {
/*  275 */     return this.font;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBackground(Color color) {
/*  287 */     predraw();
/*  288 */     GL.glClearColor(color.r, color.g, color.b, color.a);
/*  289 */     postdraw();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Color getBackground() {
/*  298 */     predraw();
/*  299 */     FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
/*  300 */     GL.glGetFloat(3106, buffer);
/*  301 */     postdraw();
/*      */     
/*  303 */     return new Color(buffer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*  310 */     predraw();
/*  311 */     GL.glClear(16384);
/*  312 */     postdraw();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetTransform() {
/*  319 */     this.sx = 1.0F;
/*  320 */     this.sy = 1.0F;
/*      */     
/*  322 */     if (this.pushed) {
/*  323 */       predraw();
/*  324 */       GL.glPopMatrix();
/*  325 */       this.pushed = false;
/*  326 */       postdraw();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkPush() {
/*  334 */     if (!this.pushed) {
/*  335 */       predraw();
/*  336 */       GL.glPushMatrix();
/*  337 */       this.pushed = true;
/*  338 */       postdraw();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void scale(float sx, float sy) {
/*  351 */     this.sx *= sx;
/*  352 */     this.sy *= sy;
/*      */     
/*  354 */     checkPush();
/*      */     
/*  356 */     predraw();
/*  357 */     GL.glScalef(sx, sy, 1.0F);
/*  358 */     postdraw();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void rotate(float rx, float ry, float ang) {
/*  372 */     checkPush();
/*      */     
/*  374 */     predraw();
/*  375 */     translate(rx, ry);
/*  376 */     GL.glRotatef(ang, 0.0F, 0.0F, 1.0F);
/*  377 */     translate(-rx, -ry);
/*  378 */     postdraw();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void translate(float x, float y) {
/*  390 */     checkPush();
/*      */     
/*  392 */     predraw();
/*  393 */     GL.glTranslatef(x, y, 0.0F);
/*  394 */     postdraw();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFont(Font font) {
/*  404 */     this.font = font;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetFont() {
/*  411 */     this.font = DEFAULT_FONT;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setColor(Color color) {
/*  421 */     if (color == null) {
/*      */       return;
/*      */     }
/*      */     
/*  425 */     this.currentColor = new Color(color);
/*  426 */     predraw();
/*  427 */     this.currentColor.bind();
/*  428 */     postdraw();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Color getColor() {
/*  437 */     return new Color(this.currentColor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawLine(float x1, float y1, float x2, float y2) {
/*  453 */     float lineWidth = this.lineWidth - 1.0F;
/*      */     
/*  455 */     if (LSR.applyGLLineFixes()) {
/*  456 */       if (x1 == x2) {
/*  457 */         if (y1 > y2) {
/*  458 */           float temp = y2;
/*  459 */           y2 = y1;
/*  460 */           y1 = temp;
/*      */         } 
/*  462 */         float step = 1.0F / this.sy;
/*  463 */         lineWidth /= this.sy;
/*  464 */         fillRect(x1 - lineWidth / 2.0F, y1 - lineWidth / 2.0F, lineWidth + step, y2 - y1 + lineWidth + step); return;
/*      */       } 
/*  466 */       if (y1 == y2) {
/*  467 */         if (x1 > x2) {
/*  468 */           float temp = x2;
/*  469 */           x2 = x1;
/*  470 */           x1 = temp;
/*      */         } 
/*  472 */         float step = 1.0F / this.sx;
/*  473 */         lineWidth /= this.sx;
/*  474 */         fillRect(x1 - lineWidth / 2.0F, y1 - lineWidth / 2.0F, x2 - x1 + lineWidth + step, lineWidth + step);
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*  479 */     predraw();
/*  480 */     this.currentColor.bind();
/*  481 */     TextureImpl.bindNone();
/*      */     
/*  483 */     LSR.start();
/*  484 */     LSR.vertex(x1, y1);
/*  485 */     LSR.vertex(x2, y2);
/*  486 */     LSR.end();
/*      */     
/*  488 */     postdraw();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void draw(Shape shape, ShapeFill fill) {
/*  500 */     predraw();
/*  501 */     TextureImpl.bindNone();
/*      */     
/*  503 */     ShapeRenderer.draw(shape, fill);
/*      */     
/*  505 */     this.currentColor.bind();
/*  506 */     postdraw();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void fill(Shape shape, ShapeFill fill) {
/*  518 */     predraw();
/*  519 */     TextureImpl.bindNone();
/*      */     
/*  521 */     ShapeRenderer.fill(shape, fill);
/*      */     
/*  523 */     this.currentColor.bind();
/*  524 */     postdraw();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void draw(Shape shape) {
/*  534 */     predraw();
/*  535 */     TextureImpl.bindNone();
/*  536 */     this.currentColor.bind();
/*      */     
/*  538 */     ShapeRenderer.draw(shape);
/*      */     
/*  540 */     postdraw();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void fill(Shape shape) {
/*  550 */     predraw();
/*  551 */     TextureImpl.bindNone();
/*  552 */     this.currentColor.bind();
/*      */     
/*  554 */     ShapeRenderer.fill(shape);
/*      */     
/*  556 */     postdraw();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void texture(Shape shape, Image image) {
/*  568 */     texture(shape, image, 0.01F, 0.01F, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void texture(Shape shape, Image image, ShapeFill fill) {
/*  582 */     texture(shape, image, 0.01F, 0.01F, fill);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void texture(Shape shape, Image image, boolean fit) {
/*  596 */     if (fit) {
/*  597 */       texture(shape, image, 1.0F, 1.0F, true);
/*      */     } else {
/*  599 */       texture(shape, image, 0.01F, 0.01F, false);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void texture(Shape shape, Image image, float scaleX, float scaleY) {
/*  616 */     texture(shape, image, scaleX, scaleY, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void texture(Shape shape, Image image, float scaleX, float scaleY, boolean fit) {
/*  635 */     predraw();
/*  636 */     TextureImpl.bindNone();
/*  637 */     this.currentColor.bind();
/*      */     
/*  639 */     if (fit) {
/*  640 */       ShapeRenderer.textureFit(shape, image, scaleX, scaleY);
/*      */     } else {
/*  642 */       ShapeRenderer.texture(shape, image, scaleX, scaleY);
/*      */     } 
/*      */     
/*  645 */     postdraw();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void texture(Shape shape, Image image, float scaleX, float scaleY, ShapeFill fill) {
/*  664 */     predraw();
/*  665 */     TextureImpl.bindNone();
/*  666 */     this.currentColor.bind();
/*      */     
/*  668 */     ShapeRenderer.texture(shape, image, scaleX, scaleY, fill);
/*      */     
/*  670 */     postdraw();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawRect(float x1, float y1, float width, float height) {
/*  686 */     float lineWidth = getLineWidth();
/*      */     
/*  688 */     drawLine(x1, y1, x1 + width, y1);
/*  689 */     drawLine(x1 + width, y1, x1 + width, y1 + height);
/*  690 */     drawLine(x1 + width, y1 + height, x1, y1 + height);
/*  691 */     drawLine(x1, y1 + height, x1, y1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearClip() {
/*  699 */     this.clip = null;
/*  700 */     predraw();
/*  701 */     GL.glDisable(3089);
/*  702 */     postdraw();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setWorldClip(float x, float y, float width, float height) {
/*  721 */     predraw();
/*  722 */     this.worldClipRecord = new Rectangle(x, y, width, height);
/*      */     
/*  724 */     GL.glEnable(12288);
/*  725 */     this.worldClip.put(1.0D).put(0.0D).put(0.0D).put(-x).flip();
/*  726 */     GL.glClipPlane(12288, this.worldClip);
/*  727 */     GL.glEnable(12289);
/*  728 */     this.worldClip.put(-1.0D).put(0.0D).put(0.0D).put((x + width)).flip();
/*  729 */     GL.glClipPlane(12289, this.worldClip);
/*      */     
/*  731 */     GL.glEnable(12290);
/*  732 */     this.worldClip.put(0.0D).put(1.0D).put(0.0D).put(-y).flip();
/*  733 */     GL.glClipPlane(12290, this.worldClip);
/*  734 */     GL.glEnable(12291);
/*  735 */     this.worldClip.put(0.0D).put(-1.0D).put(0.0D).put((y + height)).flip();
/*  736 */     GL.glClipPlane(12291, this.worldClip);
/*  737 */     postdraw();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearWorldClip() {
/*  744 */     predraw();
/*  745 */     this.worldClipRecord = null;
/*  746 */     GL.glDisable(12288);
/*  747 */     GL.glDisable(12289);
/*  748 */     GL.glDisable(12290);
/*  749 */     GL.glDisable(12291);
/*  750 */     postdraw();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setWorldClip(Rectangle clip) {
/*  761 */     if (clip == null) {
/*  762 */       clearWorldClip();
/*      */     } else {
/*  764 */       setWorldClip(clip.getX(), clip.getY(), clip.getWidth(), clip.getHeight());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Rectangle getWorldClip() {
/*  775 */     return this.worldClipRecord;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setClip(int x, int y, int width, int height) {
/*  793 */     predraw();
/*      */     
/*  795 */     if (this.clip == null) {
/*  796 */       GL.glEnable(3089);
/*  797 */       this.clip = new Rectangle(x, y, width, height);
/*      */     } else {
/*  799 */       this.clip.setBounds(x, y, width, height);
/*      */     } 
/*      */     
/*  802 */     GL.glScissor(x, this.screenHeight - y - height, width, height);
/*  803 */     postdraw();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setClip(Rectangle rect) {
/*  816 */     if (rect == null) {
/*  817 */       clearClip();
/*      */       
/*      */       return;
/*      */     } 
/*  821 */     setClip((int)rect.getX(), (int)rect.getY(), (int)rect.getWidth(), (int)rect.getHeight());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Rectangle getClip() {
/*  832 */     return this.clip;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void fillRect(float x, float y, float width, float height, Image pattern, float offX, float offY) {
/*  856 */     int cols = (int)Math.ceil((width / pattern.getWidth())) + 2;
/*  857 */     int rows = (int)Math.ceil((height / pattern.getHeight())) + 2;
/*      */     
/*  859 */     Rectangle preClip = getWorldClip();
/*  860 */     setWorldClip(x, y, width, height);
/*      */     
/*  862 */     predraw();
/*      */     
/*  864 */     for (int c = 0; c < cols; c++) {
/*  865 */       for (int r = 0; r < rows; r++) {
/*  866 */         pattern.draw((c * pattern.getWidth()) + x - offX, (r * pattern.getHeight()) + y - offY);
/*      */       }
/*      */     } 
/*      */     
/*  870 */     postdraw();
/*      */     
/*  872 */     setWorldClip(preClip);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void fillRect(float x1, float y1, float width, float height) {
/*  888 */     predraw();
/*  889 */     TextureImpl.bindNone();
/*  890 */     this.currentColor.bind();
/*      */     
/*  892 */     GL.glBegin(7);
/*  893 */     GL.glVertex2f(x1, y1);
/*  894 */     GL.glVertex2f(x1 + width, y1);
/*  895 */     GL.glVertex2f(x1 + width, y1 + height);
/*  896 */     GL.glVertex2f(x1, y1 + height);
/*  897 */     GL.glEnd();
/*  898 */     postdraw();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawOval(float x1, float y1, float width, float height) {
/*  916 */     drawOval(x1, y1, width, height, 50);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawOval(float x1, float y1, float width, float height, int segments) {
/*  937 */     drawArc(x1, y1, width, height, segments, 0.0F, 360.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawArc(float x1, float y1, float width, float height, float start, float end) {
/*  960 */     drawArc(x1, y1, width, height, 50, start, end);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawArc(float x1, float y1, float width, float height, int segments, float start, float end) {
/*  985 */     predraw();
/*  986 */     TextureImpl.bindNone();
/*  987 */     this.currentColor.bind();
/*      */     
/*  989 */     while (end < start) {
/*  990 */       end += 360.0F;
/*      */     }
/*      */     
/*  993 */     float cx = x1 + width / 2.0F;
/*  994 */     float cy = y1 + height / 2.0F;
/*      */     
/*  996 */     LSR.start();
/*  997 */     int step = 360 / segments;
/*      */     int a;
/*  999 */     for (a = (int)start; a < (int)(end + step); a += step) {
/* 1000 */       float ang = a;
/* 1001 */       if (ang > end) {
/* 1002 */         ang = end;
/*      */       }
/* 1004 */       float x = (float)(cx + FastTrig.cos(Math.toRadians(ang)) * width / 2.0D);
/* 1005 */       float y = (float)(cy + FastTrig.sin(Math.toRadians(ang)) * height / 2.0D);
/*      */       
/* 1007 */       LSR.vertex(x, y);
/*      */     } 
/* 1009 */     LSR.end();
/* 1010 */     postdraw();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void fillOval(float x1, float y1, float width, float height) {
/* 1028 */     fillOval(x1, y1, width, height, 50);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void fillOval(float x1, float y1, float width, float height, int segments) {
/* 1049 */     fillArc(x1, y1, width, height, segments, 0.0F, 360.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void fillArc(float x1, float y1, float width, float height, float start, float end) {
/* 1072 */     fillArc(x1, y1, width, height, 50, start, end);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void fillArc(float x1, float y1, float width, float height, int segments, float start, float end) {
/* 1097 */     predraw();
/* 1098 */     TextureImpl.bindNone();
/* 1099 */     this.currentColor.bind();
/*      */     
/* 1101 */     while (end < start) {
/* 1102 */       end += 360.0F;
/*      */     }
/*      */     
/* 1105 */     float cx = x1 + width / 2.0F;
/* 1106 */     float cy = y1 + height / 2.0F;
/*      */     
/* 1108 */     GL.glBegin(6);
/* 1109 */     int step = 360 / segments;
/*      */     
/* 1111 */     GL.glVertex2f(cx, cy);
/*      */     int a;
/* 1113 */     for (a = (int)start; a < (int)(end + step); a += step) {
/* 1114 */       float ang = a;
/* 1115 */       if (ang > end) {
/* 1116 */         ang = end;
/*      */       }
/*      */       
/* 1119 */       float x = (float)(cx + FastTrig.cos(Math.toRadians(ang)) * width / 2.0D);
/* 1120 */       float y = (float)(cy + FastTrig.sin(Math.toRadians(ang)) * height / 2.0D);
/*      */       
/* 1122 */       GL.glVertex2f(x, y);
/*      */     } 
/* 1124 */     GL.glEnd();
/*      */     
/* 1126 */     if (this.antialias) {
/* 1127 */       GL.glBegin(6);
/* 1128 */       GL.glVertex2f(cx, cy);
/* 1129 */       if (end != 360.0F) {
/* 1130 */         end -= 10.0F;
/*      */       }
/*      */       
/* 1133 */       for (a = (int)start; a < (int)(end + step); a += step) {
/* 1134 */         float ang = a;
/* 1135 */         if (ang > end) {
/* 1136 */           ang = end;
/*      */         }
/*      */         
/* 1139 */         float x = (float)(cx + FastTrig.cos(Math.toRadians((ang + 10.0F))) * width / 2.0D);
/*      */         
/* 1141 */         float y = (float)(cy + FastTrig.sin(Math.toRadians((ang + 10.0F))) * height / 2.0D);
/*      */ 
/*      */         
/* 1144 */         GL.glVertex2f(x, y);
/*      */       } 
/* 1146 */       GL.glEnd();
/*      */     } 
/*      */     
/* 1149 */     postdraw();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawRoundRect(float x, float y, float width, float height, int cornerRadius) {
/* 1168 */     drawRoundRect(x, y, width, height, cornerRadius, 50);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawRoundRect(float x, float y, float width, float height, int cornerRadius, int segs) {
/* 1189 */     if (cornerRadius < 0)
/* 1190 */       throw new IllegalArgumentException("corner radius must be > 0"); 
/* 1191 */     if (cornerRadius == 0) {
/* 1192 */       drawRect(x, y, width, height);
/*      */       
/*      */       return;
/*      */     } 
/* 1196 */     int mr = (int)Math.min(width, height) / 2;
/*      */     
/* 1198 */     if (cornerRadius > mr) {
/* 1199 */       cornerRadius = mr;
/*      */     }
/*      */     
/* 1202 */     drawLine(x + cornerRadius, y, x + width - cornerRadius, y);
/* 1203 */     drawLine(x, y + cornerRadius, x, y + height - cornerRadius);
/* 1204 */     drawLine(x + width, y + cornerRadius, x + width, y + height - cornerRadius);
/*      */     
/* 1206 */     drawLine(x + cornerRadius, y + height, x + width - cornerRadius, y + height);
/*      */ 
/*      */     
/* 1209 */     float d = (cornerRadius * 2);
/*      */     
/* 1211 */     drawArc(x + width - d, y + height - d, d, d, segs, 0.0F, 90.0F);
/*      */     
/* 1213 */     drawArc(x, y + height - d, d, d, segs, 90.0F, 180.0F);
/*      */     
/* 1215 */     drawArc(x + width - d, y, d, d, segs, 270.0F, 360.0F);
/*      */     
/* 1217 */     drawArc(x, y, d, d, segs, 180.0F, 270.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void fillRoundRect(float x, float y, float width, float height, int cornerRadius) {
/* 1236 */     fillRoundRect(x, y, width, height, cornerRadius, 50);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void fillRoundRect(float x, float y, float width, float height, int cornerRadius, int segs) {
/* 1257 */     if (cornerRadius < 0)
/* 1258 */       throw new IllegalArgumentException("corner radius must be > 0"); 
/* 1259 */     if (cornerRadius == 0) {
/* 1260 */       fillRect(x, y, width, height);
/*      */       
/*      */       return;
/*      */     } 
/* 1264 */     int mr = (int)Math.min(width, height) / 2;
/*      */     
/* 1266 */     if (cornerRadius > mr) {
/* 1267 */       cornerRadius = mr;
/*      */     }
/*      */     
/* 1270 */     float d = (cornerRadius * 2);
/*      */     
/* 1272 */     fillRect(x + cornerRadius, y, width - d, cornerRadius);
/* 1273 */     fillRect(x, y + cornerRadius, cornerRadius, height - d);
/* 1274 */     fillRect(x + width - cornerRadius, y + cornerRadius, cornerRadius, height - d);
/*      */     
/* 1276 */     fillRect(x + cornerRadius, y + height - cornerRadius, width - d, cornerRadius);
/*      */     
/* 1278 */     fillRect(x + cornerRadius, y + cornerRadius, width - d, height - d);
/*      */ 
/*      */     
/* 1281 */     fillArc(x + width - d, y + height - d, d, d, segs, 0.0F, 90.0F);
/*      */     
/* 1283 */     fillArc(x, y + height - d, d, d, segs, 90.0F, 180.0F);
/*      */     
/* 1285 */     fillArc(x + width - d, y, d, d, segs, 270.0F, 360.0F);
/*      */     
/* 1287 */     fillArc(x, y, d, d, segs, 180.0F, 270.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLineWidth(float width) {
/* 1298 */     predraw();
/* 1299 */     this.lineWidth = width;
/* 1300 */     LSR.setWidth(width);
/* 1301 */     GL.glPointSize(width);
/* 1302 */     postdraw();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getLineWidth() {
/* 1311 */     return this.lineWidth;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetLineWidth() {
/* 1318 */     predraw();
/*      */     
/* 1320 */     Renderer.getLineStripRenderer().setWidth(1.0F);
/* 1321 */     GL.glLineWidth(1.0F);
/* 1322 */     GL.glPointSize(1.0F);
/*      */     
/* 1324 */     postdraw();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAntiAlias(boolean anti) {
/* 1334 */     predraw();
/* 1335 */     this.antialias = anti;
/* 1336 */     LSR.setAntiAlias(anti);
/* 1337 */     if (anti) {
/* 1338 */       GL.glEnable(2881);
/*      */     } else {
/* 1340 */       GL.glDisable(2881);
/*      */     } 
/* 1342 */     postdraw();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAntiAlias() {
/* 1351 */     return this.antialias;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawString(String str, float x, float y) {
/* 1365 */     predraw();
/* 1366 */     this.font.drawString(x, y, str, this.currentColor);
/* 1367 */     postdraw();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawImage(Image image, float x, float y, Color col) {
/* 1383 */     predraw();
/* 1384 */     image.draw(x, y, col);
/* 1385 */     this.currentColor.bind();
/* 1386 */     postdraw();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawAnimation(Animation anim, float x, float y) {
/* 1400 */     drawAnimation(anim, x, y, Color.white);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawAnimation(Animation anim, float x, float y, Color col) {
/* 1416 */     predraw();
/* 1417 */     anim.draw(x, y, col);
/* 1418 */     this.currentColor.bind();
/* 1419 */     postdraw();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawImage(Image image, float x, float y) {
/* 1433 */     drawImage(image, x, y, Color.white);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawImage(Image image, float x, float y, float x2, float y2, float srcx, float srcy, float srcx2, float srcy2) {
/* 1465 */     predraw();
/* 1466 */     image.draw(x, y, x2, y2, srcx, srcy, srcx2, srcy2);
/* 1467 */     this.currentColor.bind();
/* 1468 */     postdraw();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawImage(Image image, float x, float y, float srcx, float srcy, float srcx2, float srcy2) {
/* 1496 */     drawImage(image, x, y, x + image.getWidth(), y + image.getHeight(), srcx, srcy, srcx2, srcy2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void copyArea(Image target, int x, int y) {
/* 1512 */     int format = target.getTexture().hasAlpha() ? 6408 : 6407;
/* 1513 */     target.bind();
/* 1514 */     GL.glCopyTexImage2D(3553, 0, format, x, this.screenHeight - y + target.getHeight(), target.getTexture().getTextureWidth(), target.getTexture().getTextureHeight(), 0);
/*      */ 
/*      */     
/* 1517 */     target.ensureInverted();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int translate(byte b) {
/* 1528 */     if (b < 0) {
/* 1529 */       return 256 + b;
/*      */     }
/*      */     
/* 1532 */     return b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Color getPixel(int x, int y) {
/* 1545 */     predraw();
/* 1546 */     GL.glReadPixels(x, this.screenHeight - y, 1, 1, 6408, 5121, this.readBuffer);
/*      */     
/* 1548 */     postdraw();
/*      */     
/* 1550 */     return new Color(translate(this.readBuffer.get(0)), translate(this.readBuffer.get(1)), translate(this.readBuffer.get(2)), translate(this.readBuffer.get(3)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getArea(int x, int y, int width, int height, ByteBuffer target) {
/* 1566 */     if (target.capacity() < width * height * 4)
/*      */     {
/* 1568 */       throw new IllegalArgumentException("Byte buffer provided to get area is not big enough");
/*      */     }
/*      */     
/* 1571 */     predraw();
/* 1572 */     GL.glReadPixels(x, this.screenHeight - y - height, width, height, 6408, 5121, target);
/*      */     
/* 1574 */     postdraw();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawImage(Image image, float x, float y, float x2, float y2, float srcx, float srcy, float srcx2, float srcy2, Color col) {
/* 1608 */     predraw();
/* 1609 */     image.draw(x, y, x2, y2, srcx, srcy, srcx2, srcy2, col);
/* 1610 */     this.currentColor.bind();
/* 1611 */     postdraw();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawImage(Image image, float x, float y, float srcx, float srcy, float srcx2, float srcy2, Color col) {
/* 1641 */     drawImage(image, x, y, x + image.getWidth(), y + image.getHeight(), srcx, srcy, srcx2, srcy2, col);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawGradientLine(float x1, float y1, float red1, float green1, float blue1, float alpha1, float x2, float y2, float red2, float green2, float blue2, float alpha2) {
/* 1676 */     predraw();
/*      */     
/* 1678 */     TextureImpl.bindNone();
/*      */     
/* 1680 */     GL.glBegin(1);
/*      */     
/* 1682 */     GL.glColor4f(red1, green1, blue1, alpha1);
/* 1683 */     GL.glVertex2f(x1, y1);
/*      */     
/* 1685 */     GL.glColor4f(red2, green2, blue2, alpha2);
/* 1686 */     GL.glVertex2f(x2, y2);
/*      */     
/* 1688 */     GL.glEnd();
/*      */     
/* 1690 */     postdraw();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawGradientLine(float x1, float y1, Color Color1, float x2, float y2, Color Color2) {
/* 1711 */     predraw();
/*      */     
/* 1713 */     TextureImpl.bindNone();
/*      */     
/* 1715 */     GL.glBegin(1);
/*      */     
/* 1717 */     Color1.bind();
/* 1718 */     GL.glVertex2f(x1, y1);
/*      */     
/* 1720 */     Color2.bind();
/* 1721 */     GL.glVertex2f(x2, y2);
/*      */     
/* 1723 */     GL.glEnd();
/*      */     
/* 1725 */     postdraw();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void pushTransform() {
/*      */     FloatBuffer buffer;
/* 1735 */     predraw();
/*      */ 
/*      */     
/* 1738 */     if (this.stackIndex >= this.stack.size()) {
/* 1739 */       buffer = BufferUtils.createFloatBuffer(18);
/* 1740 */       this.stack.add(buffer);
/*      */     } else {
/* 1742 */       buffer = this.stack.get(this.stackIndex);
/*      */     } 
/*      */     
/* 1745 */     GL.glGetFloat(2982, buffer);
/* 1746 */     buffer.put(16, this.sx);
/* 1747 */     buffer.put(17, this.sy);
/* 1748 */     this.stackIndex++;
/*      */     
/* 1750 */     postdraw();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void popTransform() {
/* 1758 */     if (this.stackIndex == 0) {
/* 1759 */       throw new RuntimeException("Attempt to pop a transform that hasn't be pushed");
/*      */     }
/*      */     
/* 1762 */     predraw();
/*      */     
/* 1764 */     this.stackIndex--;
/* 1765 */     FloatBuffer oldBuffer = this.stack.get(this.stackIndex);
/* 1766 */     GL.glLoadMatrix(oldBuffer);
/* 1767 */     this.sx = oldBuffer.get(16);
/* 1768 */     this.sy = oldBuffer.get(17);
/*      */     
/* 1770 */     postdraw();
/*      */   }
/*      */   
/*      */   public void destroy() {}
/*      */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\Graphics.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */