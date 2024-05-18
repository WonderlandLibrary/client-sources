/*      */ package org.newdawn.slick;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import org.newdawn.slick.opengl.ImageData;
/*      */ import org.newdawn.slick.opengl.InternalTextureLoader;
/*      */ import org.newdawn.slick.opengl.Texture;
/*      */ import org.newdawn.slick.opengl.TextureImpl;
/*      */ import org.newdawn.slick.opengl.pbuffer.GraphicsFactory;
/*      */ import org.newdawn.slick.opengl.renderer.Renderer;
/*      */ import org.newdawn.slick.opengl.renderer.SGL;
/*      */ import org.newdawn.slick.util.Log;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Image
/*      */   implements Renderable
/*      */ {
/*      */   public static final int TOP_LEFT = 0;
/*      */   public static final int TOP_RIGHT = 1;
/*      */   public static final int BOTTOM_RIGHT = 2;
/*      */   public static final int BOTTOM_LEFT = 3;
/*   31 */   protected static SGL GL = Renderer.get();
/*      */ 
/*      */   
/*      */   protected static Image inUse;
/*      */ 
/*      */   
/*      */   public static final int FILTER_LINEAR = 1;
/*      */   
/*      */   public static final int FILTER_NEAREST = 2;
/*      */   
/*      */   protected Texture texture;
/*      */   
/*      */   protected int width;
/*      */   
/*      */   protected int height;
/*      */   
/*      */   protected float textureWidth;
/*      */   
/*      */   protected float textureHeight;
/*      */   
/*      */   protected float textureOffsetX;
/*      */   
/*      */   protected float textureOffsetY;
/*      */   
/*      */   protected float angle;
/*      */   
/*   57 */   protected float alpha = 1.0F;
/*      */ 
/*      */   
/*      */   protected String ref;
/*      */ 
/*      */   
/*      */   protected boolean inited = false;
/*      */ 
/*      */   
/*      */   protected byte[] pixelData;
/*      */   
/*      */   protected boolean destroyed;
/*      */   
/*      */   protected float centerX;
/*      */   
/*      */   protected float centerY;
/*      */   
/*      */   protected String name;
/*      */   
/*      */   protected Color[] corners;
/*      */   
/*   78 */   private int filter = 9729;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean flipped;
/*      */ 
/*      */ 
/*      */   
/*      */   private Color transparent;
/*      */ 
/*      */ 
/*      */   
/*      */   protected Image(Image other) {
/*   91 */     this.width = other.getWidth();
/*   92 */     this.height = other.getHeight();
/*   93 */     this.texture = other.texture;
/*   94 */     this.textureWidth = other.textureWidth;
/*   95 */     this.textureHeight = other.textureHeight;
/*   96 */     this.ref = other.ref;
/*   97 */     this.textureOffsetX = other.textureOffsetX;
/*   98 */     this.textureOffsetY = other.textureOffsetY;
/*      */     
/*  100 */     this.centerX = (this.width / 2);
/*  101 */     this.centerY = (this.height / 2);
/*  102 */     this.inited = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Image() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Image(Texture texture) {
/*  118 */     this.texture = texture;
/*  119 */     this.ref = texture.toString();
/*  120 */     clampTexture();
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
/*      */   public Image(String ref) throws SlickException {
/*  132 */     this(ref, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Image(String ref, Color trans) throws SlickException {
/*  143 */     this(ref, false, 1, trans);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Image(String ref, boolean flipped) throws SlickException {
/*  154 */     this(ref, flipped, 1);
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
/*      */   public Image(String ref, boolean flipped, int filter) throws SlickException {
/*  166 */     this(ref, flipped, filter, (Color)null);
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
/*      */   public Image(String ref, boolean flipped, int f, Color transparent) throws SlickException {
/*  179 */     this.filter = (f == 1) ? 9729 : 9728;
/*  180 */     this.transparent = transparent;
/*  181 */     this.flipped = flipped;
/*      */     
/*      */     try {
/*  184 */       this.ref = ref;
/*  185 */       int[] trans = null;
/*  186 */       if (transparent != null) {
/*  187 */         trans = new int[3];
/*  188 */         trans[0] = (int)(transparent.r * 255.0F);
/*  189 */         trans[1] = (int)(transparent.g * 255.0F);
/*  190 */         trans[2] = (int)(transparent.b * 255.0F);
/*      */       } 
/*  192 */       this.texture = InternalTextureLoader.get().getTexture(ref, flipped, this.filter, trans);
/*  193 */     } catch (IOException e) {
/*  194 */       Log.error(e);
/*  195 */       throw new SlickException("Failed to load image from: " + ref, e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFilter(int f) {
/*  206 */     this.filter = (f == 1) ? 9729 : 9728;
/*      */     
/*  208 */     this.texture.bind();
/*  209 */     GL.glTexParameteri(3553, 10241, this.filter);
/*  210 */     GL.glTexParameteri(3553, 10240, this.filter);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Image(int width, int height) throws SlickException {
/*  221 */     this(width, height, 2);
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
/*      */   public Image(int width, int height, int f) throws SlickException {
/*  233 */     this.ref = super.toString();
/*  234 */     this.filter = (f == 1) ? 9729 : 9728;
/*      */     
/*      */     try {
/*  237 */       this.texture = InternalTextureLoader.get().createTexture(width, height, this.filter);
/*  238 */     } catch (IOException e) {
/*  239 */       Log.error(e);
/*  240 */       throw new SlickException("Failed to create empty image " + width + "x" + height);
/*      */     } 
/*      */     
/*  243 */     init();
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
/*      */   public Image(InputStream in, String ref, boolean flipped) throws SlickException {
/*  255 */     this(in, ref, flipped, 1);
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
/*      */   public Image(InputStream in, String ref, boolean flipped, int filter) throws SlickException {
/*  268 */     load(in, ref, flipped, filter, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Image(ImageBuffer buffer) {
/*  277 */     this(buffer, 1);
/*  278 */     TextureImpl.bindNone();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Image(ImageBuffer buffer, int filter) {
/*  288 */     this(buffer, filter);
/*  289 */     TextureImpl.bindNone();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Image(ImageData data) {
/*  298 */     this(data, 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Image(ImageData data, int f) {
/*      */     try {
/*  309 */       this.filter = (f == 1) ? 9729 : 9728;
/*  310 */       this.texture = InternalTextureLoader.get().getTexture(data, this.filter);
/*  311 */       this.ref = this.texture.toString();
/*  312 */     } catch (IOException e) {
/*  313 */       Log.error(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getFilter() {
/*  323 */     return this.filter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getResourceReference() {
/*  333 */     return this.ref;
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
/*      */   public void setImageColor(float r, float g, float b, float a) {
/*  345 */     setColor(0, r, g, b, a);
/*  346 */     setColor(1, r, g, b, a);
/*  347 */     setColor(3, r, g, b, a);
/*  348 */     setColor(2, r, g, b, a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setImageColor(float r, float g, float b) {
/*  359 */     setColor(0, r, g, b);
/*  360 */     setColor(1, r, g, b);
/*  361 */     setColor(3, r, g, b);
/*  362 */     setColor(2, r, g, b);
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
/*      */   public void setColor(int corner, float r, float g, float b, float a) {
/*  376 */     if (this.corners == null) {
/*  377 */       this.corners = new Color[] { new Color(1.0F, 1.0F, 1.0F, 1.0F), new Color(1.0F, 1.0F, 1.0F, 1.0F), new Color(1.0F, 1.0F, 1.0F, 1.0F), new Color(1.0F, 1.0F, 1.0F, 1.0F) };
/*      */     }
/*      */     
/*  380 */     (this.corners[corner]).r = r;
/*  381 */     (this.corners[corner]).g = g;
/*  382 */     (this.corners[corner]).b = b;
/*  383 */     (this.corners[corner]).a = a;
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
/*      */   public void setColor(int corner, float r, float g, float b) {
/*  396 */     if (this.corners == null) {
/*  397 */       this.corners = new Color[] { new Color(1.0F, 1.0F, 1.0F, 1.0F), new Color(1.0F, 1.0F, 1.0F, 1.0F), new Color(1.0F, 1.0F, 1.0F, 1.0F), new Color(1.0F, 1.0F, 1.0F, 1.0F) };
/*      */     }
/*      */     
/*  400 */     (this.corners[corner]).r = r;
/*  401 */     (this.corners[corner]).g = g;
/*  402 */     (this.corners[corner]).b = b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clampTexture() {
/*  409 */     if (GL.canTextureMirrorClamp()) {
/*  410 */       GL.glTexParameteri(3553, 10242, 34627);
/*  411 */       GL.glTexParameteri(3553, 10243, 34627);
/*      */     } else {
/*  413 */       GL.glTexParameteri(3553, 10242, 10496);
/*  414 */       GL.glTexParameteri(3553, 10243, 10496);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setName(String name) {
/*  425 */     this.name = name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/*  434 */     return this.name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Graphics getGraphics() throws SlickException {
/*  444 */     return GraphicsFactory.getGraphicsForImage(this);
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
/*      */   private void load(InputStream in, String ref, boolean flipped, int f, Color transparent) throws SlickException {
/*  458 */     this.filter = (f == 1) ? 9729 : 9728;
/*      */     
/*      */     try {
/*  461 */       this.ref = ref;
/*  462 */       int[] trans = null;
/*  463 */       if (transparent != null) {
/*  464 */         trans = new int[3];
/*  465 */         trans[0] = (int)(transparent.r * 255.0F);
/*  466 */         trans[1] = (int)(transparent.g * 255.0F);
/*  467 */         trans[2] = (int)(transparent.b * 255.0F);
/*      */       } 
/*  469 */       this.texture = (Texture)InternalTextureLoader.get().getTexture(in, ref, flipped, this.filter, trans);
/*  470 */     } catch (IOException e) {
/*  471 */       Log.error(e);
/*  472 */       throw new SlickException("Failed to load image from: " + ref, e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void bind() {
/*  480 */     this.texture.bind();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void reinit() {
/*  487 */     this.inited = false;
/*  488 */     init();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void init() {
/*  495 */     if (this.inited) {
/*      */       return;
/*      */     }
/*      */     
/*  499 */     this.inited = true;
/*  500 */     if (this.texture != null) {
/*  501 */       this.width = this.texture.getImageWidth();
/*  502 */       this.height = this.texture.getImageHeight();
/*  503 */       this.textureOffsetX = 0.0F;
/*  504 */       this.textureOffsetY = 0.0F;
/*  505 */       this.textureWidth = this.texture.getWidth();
/*  506 */       this.textureHeight = this.texture.getHeight();
/*      */     } 
/*      */     
/*  509 */     initImpl();
/*      */     
/*  511 */     this.centerX = (this.width / 2);
/*  512 */     this.centerY = (this.height / 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void initImpl() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void draw() {
/*  526 */     draw(0.0F, 0.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawCentered(float x, float y) {
/*  536 */     draw(x - (getWidth() / 2), y - (getHeight() / 2));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void draw(float x, float y) {
/*  546 */     init();
/*  547 */     draw(x, y, this.width, this.height);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void draw(float x, float y, Color filter) {
/*  558 */     init();
/*  559 */     draw(x, y, this.width, this.height, filter);
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
/*      */   public void drawEmbedded(float x, float y, float width, float height) {
/*  571 */     init();
/*      */     
/*  573 */     if (this.corners == null) {
/*  574 */       GL.glTexCoord2f(this.textureOffsetX, this.textureOffsetY);
/*  575 */       GL.glVertex3f(x, y, 0.0F);
/*  576 */       GL.glTexCoord2f(this.textureOffsetX, this.textureOffsetY + this.textureHeight);
/*  577 */       GL.glVertex3f(x, y + height, 0.0F);
/*  578 */       GL.glTexCoord2f(this.textureOffsetX + this.textureWidth, this.textureOffsetY + this.textureHeight);
/*      */       
/*  580 */       GL.glVertex3f(x + width, y + height, 0.0F);
/*  581 */       GL.glTexCoord2f(this.textureOffsetX + this.textureWidth, this.textureOffsetY);
/*  582 */       GL.glVertex3f(x + width, y, 0.0F);
/*      */     } else {
/*  584 */       this.corners[0].bind();
/*  585 */       GL.glTexCoord2f(this.textureOffsetX, this.textureOffsetY);
/*  586 */       GL.glVertex3f(x, y, 0.0F);
/*  587 */       this.corners[3].bind();
/*  588 */       GL.glTexCoord2f(this.textureOffsetX, this.textureOffsetY + this.textureHeight);
/*  589 */       GL.glVertex3f(x, y + height, 0.0F);
/*  590 */       this.corners[2].bind();
/*  591 */       GL.glTexCoord2f(this.textureOffsetX + this.textureWidth, this.textureOffsetY + this.textureHeight);
/*      */       
/*  593 */       GL.glVertex3f(x + width, y + height, 0.0F);
/*  594 */       this.corners[1].bind();
/*  595 */       GL.glTexCoord2f(this.textureOffsetX + this.textureWidth, this.textureOffsetY);
/*  596 */       GL.glVertex3f(x + width, y, 0.0F);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getTextureOffsetX() {
/*  606 */     init();
/*      */     
/*  608 */     return this.textureOffsetX;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getTextureOffsetY() {
/*  617 */     init();
/*      */     
/*  619 */     return this.textureOffsetY;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getTextureWidth() {
/*  628 */     init();
/*      */     
/*  630 */     return this.textureWidth;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getTextureHeight() {
/*  639 */     init();
/*      */     
/*  641 */     return this.textureHeight;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void draw(float x, float y, float scale) {
/*  652 */     init();
/*  653 */     draw(x, y, this.width * scale, this.height * scale, Color.white);
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
/*      */   public void draw(float x, float y, float scale, Color filter) {
/*  665 */     init();
/*  666 */     draw(x, y, this.width * scale, this.height * scale, filter);
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
/*      */   public void draw(float x, float y, float width, float height) {
/*  682 */     init();
/*  683 */     draw(x, y, width, height, Color.white);
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
/*      */   public void drawSheared(float x, float y, float hshear, float vshear) {
/*  695 */     drawSheared(x, y, hshear, vshear, Color.white);
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
/*      */   public void drawSheared(float x, float y, float hshear, float vshear, Color filter) {
/*  707 */     if (this.alpha != 1.0F) {
/*  708 */       if (filter == null) {
/*  709 */         filter = Color.white;
/*      */       }
/*      */       
/*  712 */       filter = new Color(filter);
/*  713 */       filter.a *= this.alpha;
/*      */     } 
/*  715 */     if (filter != null) {
/*  716 */       filter.bind();
/*      */     }
/*      */     
/*  719 */     this.texture.bind();
/*      */     
/*  721 */     GL.glTranslatef(x, y, 0.0F);
/*  722 */     if (this.angle != 0.0F) {
/*  723 */       GL.glTranslatef(this.centerX, this.centerY, 0.0F);
/*  724 */       GL.glRotatef(this.angle, 0.0F, 0.0F, 1.0F);
/*  725 */       GL.glTranslatef(-this.centerX, -this.centerY, 0.0F);
/*      */     } 
/*      */     
/*  728 */     GL.glBegin(7);
/*  729 */     init();
/*      */     
/*  731 */     GL.glTexCoord2f(this.textureOffsetX, this.textureOffsetY);
/*  732 */     GL.glVertex3f(0.0F, 0.0F, 0.0F);
/*  733 */     GL.glTexCoord2f(this.textureOffsetX, this.textureOffsetY + this.textureHeight);
/*  734 */     GL.glVertex3f(hshear, this.height, 0.0F);
/*  735 */     GL.glTexCoord2f(this.textureOffsetX + this.textureWidth, this.textureOffsetY + this.textureHeight);
/*      */     
/*  737 */     GL.glVertex3f(this.width + hshear, this.height + vshear, 0.0F);
/*  738 */     GL.glTexCoord2f(this.textureOffsetX + this.textureWidth, this.textureOffsetY);
/*  739 */     GL.glVertex3f(this.width, vshear, 0.0F);
/*  740 */     GL.glEnd();
/*      */     
/*  742 */     if (this.angle != 0.0F) {
/*  743 */       GL.glTranslatef(this.centerX, this.centerY, 0.0F);
/*  744 */       GL.glRotatef(-this.angle, 0.0F, 0.0F, 1.0F);
/*  745 */       GL.glTranslatef(-this.centerX, -this.centerY, 0.0F);
/*      */     } 
/*  747 */     GL.glTranslatef(-x, -y, 0.0F);
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
/*      */   public void draw(float x, float y, float width, float height, Color filter) {
/*  760 */     if (this.alpha != 1.0F) {
/*  761 */       if (filter == null) {
/*  762 */         filter = Color.white;
/*      */       }
/*      */       
/*  765 */       filter = new Color(filter);
/*  766 */       filter.a *= this.alpha;
/*      */     } 
/*  768 */     if (filter != null) {
/*  769 */       filter.bind();
/*      */     }
/*      */     
/*  772 */     this.texture.bind();
/*      */     
/*  774 */     GL.glTranslatef(x, y, 0.0F);
/*  775 */     if (this.angle != 0.0F) {
/*  776 */       GL.glTranslatef(this.centerX, this.centerY, 0.0F);
/*  777 */       GL.glRotatef(this.angle, 0.0F, 0.0F, 1.0F);
/*  778 */       GL.glTranslatef(-this.centerX, -this.centerY, 0.0F);
/*      */     } 
/*      */     
/*  781 */     GL.glBegin(7);
/*  782 */     drawEmbedded(0.0F, 0.0F, width, height);
/*  783 */     GL.glEnd();
/*      */     
/*  785 */     if (this.angle != 0.0F) {
/*  786 */       GL.glTranslatef(this.centerX, this.centerY, 0.0F);
/*  787 */       GL.glRotatef(-this.angle, 0.0F, 0.0F, 1.0F);
/*  788 */       GL.glTranslatef(-this.centerX, -this.centerY, 0.0F);
/*      */     } 
/*  790 */     GL.glTranslatef(-x, -y, 0.0F);
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
/*      */   public void drawFlash(float x, float y, float width, float height) {
/*  802 */     drawFlash(x, y, width, height, Color.white);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCenterOfRotation(float x, float y) {
/*  812 */     this.centerX = x;
/*  813 */     this.centerY = y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getCenterOfRotationX() {
/*  822 */     init();
/*      */     
/*  824 */     return this.centerX;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getCenterOfRotationY() {
/*  833 */     init();
/*      */     
/*  835 */     return this.centerY;
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
/*      */   public void drawFlash(float x, float y, float width, float height, Color col) {
/*  848 */     init();
/*      */     
/*  850 */     col.bind();
/*  851 */     this.texture.bind();
/*      */     
/*  853 */     if (GL.canSecondaryColor()) {
/*  854 */       GL.glEnable(33880);
/*  855 */       GL.glSecondaryColor3ubEXT((byte)(int)(col.r * 255.0F), (byte)(int)(col.g * 255.0F), (byte)(int)(col.b * 255.0F));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  860 */     GL.glTexEnvi(8960, 8704, 8448);
/*      */     
/*  862 */     GL.glTranslatef(x, y, 0.0F);
/*  863 */     if (this.angle != 0.0F) {
/*  864 */       GL.glTranslatef(this.centerX, this.centerY, 0.0F);
/*  865 */       GL.glRotatef(this.angle, 0.0F, 0.0F, 1.0F);
/*  866 */       GL.glTranslatef(-this.centerX, -this.centerY, 0.0F);
/*      */     } 
/*      */     
/*  869 */     GL.glBegin(7);
/*  870 */     drawEmbedded(0.0F, 0.0F, width, height);
/*  871 */     GL.glEnd();
/*      */     
/*  873 */     if (this.angle != 0.0F) {
/*  874 */       GL.glTranslatef(this.centerX, this.centerY, 0.0F);
/*  875 */       GL.glRotatef(-this.angle, 0.0F, 0.0F, 1.0F);
/*  876 */       GL.glTranslatef(-this.centerX, -this.centerY, 0.0F);
/*      */     } 
/*  878 */     GL.glTranslatef(-x, -y, 0.0F);
/*      */     
/*  880 */     if (GL.canSecondaryColor()) {
/*  881 */       GL.glDisable(33880);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawFlash(float x, float y) {
/*  892 */     drawFlash(x, y, getWidth(), getHeight());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRotation(float angle) {
/*  902 */     this.angle = angle % 360.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getRotation() {
/*  912 */     return this.angle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getAlpha() {
/*  921 */     return this.alpha;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAlpha(float alpha) {
/*  930 */     this.alpha = alpha;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void rotate(float angle) {
/*  940 */     this.angle += angle;
/*  941 */     this.angle %= 360.0F;
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
/*      */   public Image getSubImage(int x, int y, int width, int height) {
/*  955 */     init();
/*      */     
/*  957 */     float newTextureOffsetX = x / this.width * this.textureWidth + this.textureOffsetX;
/*  958 */     float newTextureOffsetY = y / this.height * this.textureHeight + this.textureOffsetY;
/*  959 */     float newTextureWidth = width / this.width * this.textureWidth;
/*  960 */     float newTextureHeight = height / this.height * this.textureHeight;
/*      */     
/*  962 */     Image sub = new Image();
/*  963 */     sub.inited = true;
/*  964 */     sub.texture = this.texture;
/*  965 */     sub.textureOffsetX = newTextureOffsetX;
/*  966 */     sub.textureOffsetY = newTextureOffsetY;
/*  967 */     sub.textureWidth = newTextureWidth;
/*  968 */     sub.textureHeight = newTextureHeight;
/*      */     
/*  970 */     sub.width = width;
/*  971 */     sub.height = height;
/*  972 */     sub.ref = this.ref;
/*  973 */     sub.centerX = (width / 2);
/*  974 */     sub.centerY = (height / 2);
/*      */     
/*  976 */     return sub;
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
/*      */   public void draw(float x, float y, float srcx, float srcy, float srcx2, float srcy2) {
/*  990 */     draw(x, y, x + this.width, y + this.height, srcx, srcy, srcx2, srcy2);
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
/*      */   public void draw(float x, float y, float x2, float y2, float srcx, float srcy, float srcx2, float srcy2) {
/* 1006 */     draw(x, y, x2, y2, srcx, srcy, srcx2, srcy2, Color.white);
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
/*      */   public void draw(float x, float y, float x2, float y2, float srcx, float srcy, float srcx2, float srcy2, Color filter) {
/* 1023 */     init();
/*      */     
/* 1025 */     if (this.alpha != 1.0F) {
/* 1026 */       if (filter == null) {
/* 1027 */         filter = Color.white;
/*      */       }
/*      */       
/* 1030 */       filter = new Color(filter);
/* 1031 */       filter.a *= this.alpha;
/*      */     } 
/* 1033 */     filter.bind();
/* 1034 */     this.texture.bind();
/*      */     
/* 1036 */     GL.glTranslatef(x, y, 0.0F);
/* 1037 */     if (this.angle != 0.0F) {
/* 1038 */       GL.glTranslatef(this.centerX, this.centerY, 0.0F);
/* 1039 */       GL.glRotatef(this.angle, 0.0F, 0.0F, 1.0F);
/* 1040 */       GL.glTranslatef(-this.centerX, -this.centerY, 0.0F);
/*      */     } 
/*      */     
/* 1043 */     GL.glBegin(7);
/* 1044 */     drawEmbedded(0.0F, 0.0F, x2 - x, y2 - y, srcx, srcy, srcx2, srcy2);
/* 1045 */     GL.glEnd();
/*      */     
/* 1047 */     if (this.angle != 0.0F) {
/* 1048 */       GL.glTranslatef(this.centerX, this.centerY, 0.0F);
/* 1049 */       GL.glRotatef(-this.angle, 0.0F, 0.0F, 1.0F);
/* 1050 */       GL.glTranslatef(-this.centerX, -this.centerY, 0.0F);
/*      */     } 
/* 1052 */     GL.glTranslatef(-x, -y, 0.0F);
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
/*      */   public void drawEmbedded(float x, float y, float x2, float y2, float srcx, float srcy, float srcx2, float srcy2) {
/* 1073 */     drawEmbedded(x, y, x2, y2, srcx, srcy, srcx2, srcy2, null);
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
/*      */   public void drawEmbedded(float x, float y, float x2, float y2, float srcx, float srcy, float srcx2, float srcy2, Color filter) {
/* 1091 */     if (filter != null) {
/* 1092 */       filter.bind();
/*      */     }
/*      */     
/* 1095 */     float mywidth = x2 - x;
/* 1096 */     float myheight = y2 - y;
/* 1097 */     float texwidth = srcx2 - srcx;
/* 1098 */     float texheight = srcy2 - srcy;
/*      */     
/* 1100 */     float newTextureOffsetX = srcx / this.width * this.textureWidth + this.textureOffsetX;
/*      */     
/* 1102 */     float newTextureOffsetY = srcy / this.height * this.textureHeight + this.textureOffsetY;
/*      */     
/* 1104 */     float newTextureWidth = texwidth / this.width * this.textureWidth;
/*      */     
/* 1106 */     float newTextureHeight = texheight / this.height * this.textureHeight;
/*      */ 
/*      */     
/* 1109 */     GL.glTexCoord2f(newTextureOffsetX, newTextureOffsetY);
/* 1110 */     GL.glVertex3f(x, y, 0.0F);
/* 1111 */     GL.glTexCoord2f(newTextureOffsetX, newTextureOffsetY + newTextureHeight);
/*      */     
/* 1113 */     GL.glVertex3f(x, y + myheight, 0.0F);
/* 1114 */     GL.glTexCoord2f(newTextureOffsetX + newTextureWidth, newTextureOffsetY + newTextureHeight);
/*      */     
/* 1116 */     GL.glVertex3f(x + mywidth, y + myheight, 0.0F);
/* 1117 */     GL.glTexCoord2f(newTextureOffsetX + newTextureWidth, newTextureOffsetY);
/*      */     
/* 1119 */     GL.glVertex3f(x + mywidth, y, 0.0F);
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
/*      */   public void drawWarped(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
/* 1136 */     Color.white.bind();
/* 1137 */     this.texture.bind();
/*      */     
/* 1139 */     GL.glTranslatef(x1, y1, 0.0F);
/* 1140 */     if (this.angle != 0.0F) {
/* 1141 */       GL.glTranslatef(this.centerX, this.centerY, 0.0F);
/* 1142 */       GL.glRotatef(this.angle, 0.0F, 0.0F, 1.0F);
/* 1143 */       GL.glTranslatef(-this.centerX, -this.centerY, 0.0F);
/*      */     } 
/*      */     
/* 1146 */     GL.glBegin(7);
/* 1147 */     init();
/*      */     
/* 1149 */     GL.glTexCoord2f(this.textureOffsetX, this.textureOffsetY);
/* 1150 */     GL.glVertex3f(0.0F, 0.0F, 0.0F);
/* 1151 */     GL.glTexCoord2f(this.textureOffsetX, this.textureOffsetY + this.textureHeight);
/* 1152 */     GL.glVertex3f(x2 - x1, y2 - y1, 0.0F);
/* 1153 */     GL.glTexCoord2f(this.textureOffsetX + this.textureWidth, this.textureOffsetY + this.textureHeight);
/*      */     
/* 1155 */     GL.glVertex3f(x3 - x1, y3 - y1, 0.0F);
/* 1156 */     GL.glTexCoord2f(this.textureOffsetX + this.textureWidth, this.textureOffsetY);
/* 1157 */     GL.glVertex3f(x4 - x1, y4 - y1, 0.0F);
/* 1158 */     GL.glEnd();
/*      */     
/* 1160 */     if (this.angle != 0.0F) {
/* 1161 */       GL.glTranslatef(this.centerX, this.centerY, 0.0F);
/* 1162 */       GL.glRotatef(-this.angle, 0.0F, 0.0F, 1.0F);
/* 1163 */       GL.glTranslatef(-this.centerX, -this.centerY, 0.0F);
/*      */     } 
/* 1165 */     GL.glTranslatef(-x1, -y1, 0.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getWidth() {
/* 1174 */     init();
/* 1175 */     return this.width;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getHeight() {
/* 1184 */     init();
/* 1185 */     return this.height;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Image copy() {
/* 1195 */     init();
/* 1196 */     return getSubImage(0, 0, this.width, this.height);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Image getScaledCopy(float scale) {
/* 1206 */     init();
/* 1207 */     return getScaledCopy((int)(this.width * scale), (int)(this.height * scale));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Image getScaledCopy(int width, int height) {
/* 1218 */     init();
/* 1219 */     Image image = copy();
/* 1220 */     image.width = width;
/* 1221 */     image.height = height;
/* 1222 */     image.centerX = (width / 2);
/* 1223 */     image.centerY = (height / 2);
/* 1224 */     return image;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureInverted() {
/* 1231 */     if (this.textureHeight > 0.0F) {
/* 1232 */       this.textureOffsetY += this.textureHeight;
/* 1233 */       this.textureHeight = -this.textureHeight;
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
/*      */   public Image getFlippedCopy(boolean flipHorizontal, boolean flipVertical) {
/* 1245 */     init();
/* 1246 */     Image image = copy();
/*      */     
/* 1248 */     if (flipHorizontal) {
/* 1249 */       this.textureOffsetX += this.textureWidth;
/* 1250 */       image.textureWidth = -this.textureWidth;
/*      */     } 
/* 1252 */     if (flipVertical) {
/* 1253 */       this.textureOffsetY += this.textureHeight;
/* 1254 */       image.textureHeight = -this.textureHeight;
/*      */     } 
/*      */     
/* 1257 */     return image;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endUse() {
/* 1266 */     if (inUse != this) {
/* 1267 */       throw new RuntimeException("The sprite sheet is not currently in use");
/*      */     }
/* 1269 */     inUse = null;
/* 1270 */     GL.glEnd();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void startUse() {
/* 1280 */     if (inUse != null) {
/* 1281 */       throw new RuntimeException("Attempt to start use of a sprite sheet before ending use with another - see endUse()");
/*      */     }
/* 1283 */     inUse = this;
/* 1284 */     init();
/*      */     
/* 1286 */     Color.white.bind();
/* 1287 */     this.texture.bind();
/* 1288 */     GL.glBegin(7);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1295 */     init();
/*      */     
/* 1297 */     return "[Image " + this.ref + " " + this.width + "x" + this.height + "  " + this.textureOffsetX + "," + this.textureOffsetY + "," + this.textureWidth + "," + this.textureHeight + "]";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Texture getTexture() {
/* 1306 */     return this.texture;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTexture(Texture texture) {
/* 1315 */     this.texture = texture;
/* 1316 */     reinit();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int translate(byte b) {
/* 1326 */     if (b < 0) {
/* 1327 */       return 256 + b;
/*      */     }
/*      */     
/* 1330 */     return b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Color getColor(int x, int y) {
/* 1341 */     if (this.pixelData == null) {
/* 1342 */       this.pixelData = this.texture.getTextureData();
/*      */     }
/*      */     
/* 1345 */     int xo = (int)(this.textureOffsetX * this.texture.getTextureWidth());
/* 1346 */     int yo = (int)(this.textureOffsetY * this.texture.getTextureHeight());
/*      */     
/* 1348 */     if (this.textureWidth < 0.0F) {
/* 1349 */       x = xo - x;
/*      */     } else {
/* 1351 */       x = xo + x;
/*      */     } 
/*      */     
/* 1354 */     if (this.textureHeight < 0.0F) {
/* 1355 */       y = yo - y;
/*      */     } else {
/* 1357 */       y = yo + y;
/*      */     } 
/*      */     
/* 1360 */     int offset = x + y * this.texture.getTextureWidth();
/* 1361 */     offset *= this.texture.hasAlpha() ? 4 : 3;
/*      */     
/* 1363 */     if (this.texture.hasAlpha()) {
/* 1364 */       return new Color(translate(this.pixelData[offset]), translate(this.pixelData[offset + 1]), translate(this.pixelData[offset + 2]), translate(this.pixelData[offset + 3]));
/*      */     }
/*      */     
/* 1367 */     return new Color(translate(this.pixelData[offset]), translate(this.pixelData[offset + 1]), translate(this.pixelData[offset + 2]));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDestroyed() {
/* 1378 */     return this.destroyed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void destroy() throws SlickException {
/* 1388 */     if (isDestroyed()) {
/*      */       return;
/*      */     }
/*      */     
/* 1392 */     this.destroyed = true;
/* 1393 */     this.texture.release();
/* 1394 */     GraphicsFactory.releaseGraphicsForImage(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void flushPixelData() {
/* 1401 */     this.pixelData = null;
/*      */   }
/*      */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\Image.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */