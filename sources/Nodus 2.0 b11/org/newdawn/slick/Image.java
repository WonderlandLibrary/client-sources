/*    1:     */ package org.newdawn.slick;
/*    2:     */ 
/*    3:     */ import java.io.IOException;
/*    4:     */ import java.io.InputStream;
/*    5:     */ import org.newdawn.slick.opengl.ImageData;
/*    6:     */ import org.newdawn.slick.opengl.InternalTextureLoader;
/*    7:     */ import org.newdawn.slick.opengl.Texture;
/*    8:     */ import org.newdawn.slick.opengl.TextureImpl;
/*    9:     */ import org.newdawn.slick.opengl.pbuffer.GraphicsFactory;
/*   10:     */ import org.newdawn.slick.opengl.renderer.Renderer;
/*   11:     */ import org.newdawn.slick.opengl.renderer.SGL;
/*   12:     */ import org.newdawn.slick.util.Log;
/*   13:     */ 
/*   14:     */ public class Image
/*   15:     */   implements Renderable
/*   16:     */ {
/*   17:     */   public static final int TOP_LEFT = 0;
/*   18:     */   public static final int TOP_RIGHT = 1;
/*   19:     */   public static final int BOTTOM_RIGHT = 2;
/*   20:     */   public static final int BOTTOM_LEFT = 3;
/*   21:  31 */   protected static SGL GL = ;
/*   22:     */   protected static Image inUse;
/*   23:     */   public static final int FILTER_LINEAR = 1;
/*   24:     */   public static final int FILTER_NEAREST = 2;
/*   25:     */   protected Texture texture;
/*   26:     */   protected int width;
/*   27:     */   protected int height;
/*   28:     */   protected float textureWidth;
/*   29:     */   protected float textureHeight;
/*   30:     */   protected float textureOffsetX;
/*   31:     */   protected float textureOffsetY;
/*   32:     */   protected float angle;
/*   33:  57 */   protected float alpha = 1.0F;
/*   34:     */   protected String ref;
/*   35:  61 */   protected boolean inited = false;
/*   36:     */   protected byte[] pixelData;
/*   37:     */   protected boolean destroyed;
/*   38:     */   protected float centerX;
/*   39:     */   protected float centerY;
/*   40:     */   protected String name;
/*   41:     */   protected Color[] corners;
/*   42:  78 */   private int filter = 9729;
/*   43:     */   private boolean flipped;
/*   44:     */   private Color transparent;
/*   45:     */   
/*   46:     */   protected Image(Image other)
/*   47:     */   {
/*   48:  91 */     this.width = other.getWidth();
/*   49:  92 */     this.height = other.getHeight();
/*   50:  93 */     this.texture = other.texture;
/*   51:  94 */     this.textureWidth = other.textureWidth;
/*   52:  95 */     this.textureHeight = other.textureHeight;
/*   53:  96 */     this.ref = other.ref;
/*   54:  97 */     this.textureOffsetX = other.textureOffsetX;
/*   55:  98 */     this.textureOffsetY = other.textureOffsetY;
/*   56:     */     
/*   57: 100 */     this.centerX = (this.width / 2);
/*   58: 101 */     this.centerY = (this.height / 2);
/*   59: 102 */     this.inited = true;
/*   60:     */   }
/*   61:     */   
/*   62:     */   protected Image() {}
/*   63:     */   
/*   64:     */   public Image(Texture texture)
/*   65:     */   {
/*   66: 118 */     this.texture = texture;
/*   67: 119 */     this.ref = texture.toString();
/*   68: 120 */     clampTexture();
/*   69:     */   }
/*   70:     */   
/*   71:     */   public Image(String ref)
/*   72:     */     throws SlickException
/*   73:     */   {
/*   74: 132 */     this(ref, false);
/*   75:     */   }
/*   76:     */   
/*   77:     */   public Image(String ref, Color trans)
/*   78:     */     throws SlickException
/*   79:     */   {
/*   80: 143 */     this(ref, false, 1, trans);
/*   81:     */   }
/*   82:     */   
/*   83:     */   public Image(String ref, boolean flipped)
/*   84:     */     throws SlickException
/*   85:     */   {
/*   86: 154 */     this(ref, flipped, 1);
/*   87:     */   }
/*   88:     */   
/*   89:     */   public Image(String ref, boolean flipped, int filter)
/*   90:     */     throws SlickException
/*   91:     */   {
/*   92: 166 */     this(ref, flipped, filter, null);
/*   93:     */   }
/*   94:     */   
/*   95:     */   public Image(String ref, boolean flipped, int f, Color transparent)
/*   96:     */     throws SlickException
/*   97:     */   {
/*   98: 179 */     this.filter = (f == 1 ? 9729 : 9728);
/*   99: 180 */     this.transparent = transparent;
/*  100: 181 */     this.flipped = flipped;
/*  101:     */     try
/*  102:     */     {
/*  103: 184 */       this.ref = ref;
/*  104: 185 */       int[] trans = null;
/*  105: 186 */       if (transparent != null)
/*  106:     */       {
/*  107: 187 */         trans = new int[3];
/*  108: 188 */         trans[0] = ((int)(transparent.r * 255.0F));
/*  109: 189 */         trans[1] = ((int)(transparent.g * 255.0F));
/*  110: 190 */         trans[2] = ((int)(transparent.b * 255.0F));
/*  111:     */       }
/*  112: 192 */       this.texture = InternalTextureLoader.get().getTexture(ref, flipped, this.filter, trans);
/*  113:     */     }
/*  114:     */     catch (IOException e)
/*  115:     */     {
/*  116: 194 */       Log.error(e);
/*  117: 195 */       throw new SlickException("Failed to load image from: " + ref, e);
/*  118:     */     }
/*  119:     */   }
/*  120:     */   
/*  121:     */   public void setFilter(int f)
/*  122:     */   {
/*  123: 206 */     this.filter = (f == 1 ? 9729 : 9728);
/*  124:     */     
/*  125: 208 */     this.texture.bind();
/*  126: 209 */     GL.glTexParameteri(3553, 10241, this.filter);
/*  127: 210 */     GL.glTexParameteri(3553, 10240, this.filter);
/*  128:     */   }
/*  129:     */   
/*  130:     */   public Image(int width, int height)
/*  131:     */     throws SlickException
/*  132:     */   {
/*  133: 221 */     this(width, height, 2);
/*  134:     */   }
/*  135:     */   
/*  136:     */   public Image(int width, int height, int f)
/*  137:     */     throws SlickException
/*  138:     */   {
/*  139: 233 */     this.ref = super.toString();
/*  140: 234 */     this.filter = (f == 1 ? 9729 : 9728);
/*  141:     */     try
/*  142:     */     {
/*  143: 237 */       this.texture = InternalTextureLoader.get().createTexture(width, height, this.filter);
/*  144:     */     }
/*  145:     */     catch (IOException e)
/*  146:     */     {
/*  147: 239 */       Log.error(e);
/*  148: 240 */       throw new SlickException("Failed to create empty image " + width + "x" + height);
/*  149:     */     }
/*  150: 243 */     init();
/*  151:     */   }
/*  152:     */   
/*  153:     */   public Image(InputStream in, String ref, boolean flipped)
/*  154:     */     throws SlickException
/*  155:     */   {
/*  156: 255 */     this(in, ref, flipped, 1);
/*  157:     */   }
/*  158:     */   
/*  159:     */   public Image(InputStream in, String ref, boolean flipped, int filter)
/*  160:     */     throws SlickException
/*  161:     */   {
/*  162: 268 */     load(in, ref, flipped, filter, null);
/*  163:     */   }
/*  164:     */   
/*  165:     */   Image(ImageBuffer buffer)
/*  166:     */   {
/*  167: 277 */     this(buffer, 1);
/*  168: 278 */     TextureImpl.bindNone();
/*  169:     */   }
/*  170:     */   
/*  171:     */   Image(ImageBuffer buffer, int filter)
/*  172:     */   {
/*  173: 288 */     this(buffer, filter);
/*  174: 289 */     TextureImpl.bindNone();
/*  175:     */   }
/*  176:     */   
/*  177:     */   public Image(ImageData data)
/*  178:     */   {
/*  179: 298 */     this(data, 1);
/*  180:     */   }
/*  181:     */   
/*  182:     */   public Image(ImageData data, int f)
/*  183:     */   {
/*  184:     */     try
/*  185:     */     {
/*  186: 309 */       this.filter = (f == 1 ? 9729 : 9728);
/*  187: 310 */       this.texture = InternalTextureLoader.get().getTexture(data, this.filter);
/*  188: 311 */       this.ref = this.texture.toString();
/*  189:     */     }
/*  190:     */     catch (IOException e)
/*  191:     */     {
/*  192: 313 */       Log.error(e);
/*  193:     */     }
/*  194:     */   }
/*  195:     */   
/*  196:     */   public int getFilter()
/*  197:     */   {
/*  198: 323 */     return this.filter;
/*  199:     */   }
/*  200:     */   
/*  201:     */   public String getResourceReference()
/*  202:     */   {
/*  203: 333 */     return this.ref;
/*  204:     */   }
/*  205:     */   
/*  206:     */   public void setImageColor(float r, float g, float b, float a)
/*  207:     */   {
/*  208: 345 */     setColor(0, r, g, b, a);
/*  209: 346 */     setColor(1, r, g, b, a);
/*  210: 347 */     setColor(3, r, g, b, a);
/*  211: 348 */     setColor(2, r, g, b, a);
/*  212:     */   }
/*  213:     */   
/*  214:     */   public void setImageColor(float r, float g, float b)
/*  215:     */   {
/*  216: 359 */     setColor(0, r, g, b);
/*  217: 360 */     setColor(1, r, g, b);
/*  218: 361 */     setColor(3, r, g, b);
/*  219: 362 */     setColor(2, r, g, b);
/*  220:     */   }
/*  221:     */   
/*  222:     */   public void setColor(int corner, float r, float g, float b, float a)
/*  223:     */   {
/*  224: 376 */     if (this.corners == null) {
/*  225: 377 */       this.corners = new Color[] { new Color(1.0F, 1.0F, 1.0F, 1.0F), new Color(1.0F, 1.0F, 1.0F, 1.0F), new Color(1.0F, 1.0F, 1.0F, 1.0F), new Color(1.0F, 1.0F, 1.0F, 1.0F) };
/*  226:     */     }
/*  227: 380 */     this.corners[corner].r = r;
/*  228: 381 */     this.corners[corner].g = g;
/*  229: 382 */     this.corners[corner].b = b;
/*  230: 383 */     this.corners[corner].a = a;
/*  231:     */   }
/*  232:     */   
/*  233:     */   public void setColor(int corner, float r, float g, float b)
/*  234:     */   {
/*  235: 396 */     if (this.corners == null) {
/*  236: 397 */       this.corners = new Color[] { new Color(1.0F, 1.0F, 1.0F, 1.0F), new Color(1.0F, 1.0F, 1.0F, 1.0F), new Color(1.0F, 1.0F, 1.0F, 1.0F), new Color(1.0F, 1.0F, 1.0F, 1.0F) };
/*  237:     */     }
/*  238: 400 */     this.corners[corner].r = r;
/*  239: 401 */     this.corners[corner].g = g;
/*  240: 402 */     this.corners[corner].b = b;
/*  241:     */   }
/*  242:     */   
/*  243:     */   public void clampTexture()
/*  244:     */   {
/*  245: 409 */     if (GL.canTextureMirrorClamp())
/*  246:     */     {
/*  247: 410 */       GL.glTexParameteri(3553, 10242, 34627);
/*  248: 411 */       GL.glTexParameteri(3553, 10243, 34627);
/*  249:     */     }
/*  250:     */     else
/*  251:     */     {
/*  252: 413 */       GL.glTexParameteri(3553, 10242, 10496);
/*  253: 414 */       GL.glTexParameteri(3553, 10243, 10496);
/*  254:     */     }
/*  255:     */   }
/*  256:     */   
/*  257:     */   public void setName(String name)
/*  258:     */   {
/*  259: 425 */     this.name = name;
/*  260:     */   }
/*  261:     */   
/*  262:     */   public String getName()
/*  263:     */   {
/*  264: 434 */     return this.name;
/*  265:     */   }
/*  266:     */   
/*  267:     */   public Graphics getGraphics()
/*  268:     */     throws SlickException
/*  269:     */   {
/*  270: 444 */     return GraphicsFactory.getGraphicsForImage(this);
/*  271:     */   }
/*  272:     */   
/*  273:     */   private void load(InputStream in, String ref, boolean flipped, int f, Color transparent)
/*  274:     */     throws SlickException
/*  275:     */   {
/*  276: 458 */     this.filter = (f == 1 ? 9729 : 9728);
/*  277:     */     try
/*  278:     */     {
/*  279: 461 */       this.ref = ref;
/*  280: 462 */       int[] trans = null;
/*  281: 463 */       if (transparent != null)
/*  282:     */       {
/*  283: 464 */         trans = new int[3];
/*  284: 465 */         trans[0] = ((int)(transparent.r * 255.0F));
/*  285: 466 */         trans[1] = ((int)(transparent.g * 255.0F));
/*  286: 467 */         trans[2] = ((int)(transparent.b * 255.0F));
/*  287:     */       }
/*  288: 469 */       this.texture = InternalTextureLoader.get().getTexture(in, ref, flipped, this.filter, trans);
/*  289:     */     }
/*  290:     */     catch (IOException e)
/*  291:     */     {
/*  292: 471 */       Log.error(e);
/*  293: 472 */       throw new SlickException("Failed to load image from: " + ref, e);
/*  294:     */     }
/*  295:     */   }
/*  296:     */   
/*  297:     */   public void bind()
/*  298:     */   {
/*  299: 480 */     this.texture.bind();
/*  300:     */   }
/*  301:     */   
/*  302:     */   protected void reinit()
/*  303:     */   {
/*  304: 487 */     this.inited = false;
/*  305: 488 */     init();
/*  306:     */   }
/*  307:     */   
/*  308:     */   protected final void init()
/*  309:     */   {
/*  310: 495 */     if (this.inited) {
/*  311: 496 */       return;
/*  312:     */     }
/*  313: 499 */     this.inited = true;
/*  314: 500 */     if (this.texture != null)
/*  315:     */     {
/*  316: 501 */       this.width = this.texture.getImageWidth();
/*  317: 502 */       this.height = this.texture.getImageHeight();
/*  318: 503 */       this.textureOffsetX = 0.0F;
/*  319: 504 */       this.textureOffsetY = 0.0F;
/*  320: 505 */       this.textureWidth = this.texture.getWidth();
/*  321: 506 */       this.textureHeight = this.texture.getHeight();
/*  322:     */     }
/*  323: 509 */     initImpl();
/*  324:     */     
/*  325: 511 */     this.centerX = (this.width / 2);
/*  326: 512 */     this.centerY = (this.height / 2);
/*  327:     */   }
/*  328:     */   
/*  329:     */   protected void initImpl() {}
/*  330:     */   
/*  331:     */   public void draw()
/*  332:     */   {
/*  333: 526 */     draw(0.0F, 0.0F);
/*  334:     */   }
/*  335:     */   
/*  336:     */   public void drawCentered(float x, float y)
/*  337:     */   {
/*  338: 536 */     draw(x - getWidth() / 2, y - getHeight() / 2);
/*  339:     */   }
/*  340:     */   
/*  341:     */   public void draw(float x, float y)
/*  342:     */   {
/*  343: 546 */     init();
/*  344: 547 */     draw(x, y, this.width, this.height);
/*  345:     */   }
/*  346:     */   
/*  347:     */   public void draw(float x, float y, Color filter)
/*  348:     */   {
/*  349: 558 */     init();
/*  350: 559 */     draw(x, y, this.width, this.height, filter);
/*  351:     */   }
/*  352:     */   
/*  353:     */   public void drawEmbedded(float x, float y, float width, float height)
/*  354:     */   {
/*  355: 571 */     init();
/*  356: 573 */     if (this.corners == null)
/*  357:     */     {
/*  358: 574 */       GL.glTexCoord2f(this.textureOffsetX, this.textureOffsetY);
/*  359: 575 */       GL.glVertex3f(x, y, 0.0F);
/*  360: 576 */       GL.glTexCoord2f(this.textureOffsetX, this.textureOffsetY + this.textureHeight);
/*  361: 577 */       GL.glVertex3f(x, y + height, 0.0F);
/*  362: 578 */       GL.glTexCoord2f(this.textureOffsetX + this.textureWidth, this.textureOffsetY + 
/*  363: 579 */         this.textureHeight);
/*  364: 580 */       GL.glVertex3f(x + width, y + height, 0.0F);
/*  365: 581 */       GL.glTexCoord2f(this.textureOffsetX + this.textureWidth, this.textureOffsetY);
/*  366: 582 */       GL.glVertex3f(x + width, y, 0.0F);
/*  367:     */     }
/*  368:     */     else
/*  369:     */     {
/*  370: 584 */       this.corners[0].bind();
/*  371: 585 */       GL.glTexCoord2f(this.textureOffsetX, this.textureOffsetY);
/*  372: 586 */       GL.glVertex3f(x, y, 0.0F);
/*  373: 587 */       this.corners[3].bind();
/*  374: 588 */       GL.glTexCoord2f(this.textureOffsetX, this.textureOffsetY + this.textureHeight);
/*  375: 589 */       GL.glVertex3f(x, y + height, 0.0F);
/*  376: 590 */       this.corners[2].bind();
/*  377: 591 */       GL.glTexCoord2f(this.textureOffsetX + this.textureWidth, this.textureOffsetY + 
/*  378: 592 */         this.textureHeight);
/*  379: 593 */       GL.glVertex3f(x + width, y + height, 0.0F);
/*  380: 594 */       this.corners[1].bind();
/*  381: 595 */       GL.glTexCoord2f(this.textureOffsetX + this.textureWidth, this.textureOffsetY);
/*  382: 596 */       GL.glVertex3f(x + width, y, 0.0F);
/*  383:     */     }
/*  384:     */   }
/*  385:     */   
/*  386:     */   public float getTextureOffsetX()
/*  387:     */   {
/*  388: 606 */     init();
/*  389:     */     
/*  390: 608 */     return this.textureOffsetX;
/*  391:     */   }
/*  392:     */   
/*  393:     */   public float getTextureOffsetY()
/*  394:     */   {
/*  395: 617 */     init();
/*  396:     */     
/*  397: 619 */     return this.textureOffsetY;
/*  398:     */   }
/*  399:     */   
/*  400:     */   public float getTextureWidth()
/*  401:     */   {
/*  402: 628 */     init();
/*  403:     */     
/*  404: 630 */     return this.textureWidth;
/*  405:     */   }
/*  406:     */   
/*  407:     */   public float getTextureHeight()
/*  408:     */   {
/*  409: 639 */     init();
/*  410:     */     
/*  411: 641 */     return this.textureHeight;
/*  412:     */   }
/*  413:     */   
/*  414:     */   public void draw(float x, float y, float scale)
/*  415:     */   {
/*  416: 652 */     init();
/*  417: 653 */     draw(x, y, this.width * scale, this.height * scale, Color.white);
/*  418:     */   }
/*  419:     */   
/*  420:     */   public void draw(float x, float y, float scale, Color filter)
/*  421:     */   {
/*  422: 665 */     init();
/*  423: 666 */     draw(x, y, this.width * scale, this.height * scale, filter);
/*  424:     */   }
/*  425:     */   
/*  426:     */   public void draw(float x, float y, float width, float height)
/*  427:     */   {
/*  428: 682 */     init();
/*  429: 683 */     draw(x, y, width, height, Color.white);
/*  430:     */   }
/*  431:     */   
/*  432:     */   public void drawSheared(float x, float y, float hshear, float vshear)
/*  433:     */   {
/*  434: 695 */     drawSheared(x, y, hshear, vshear, Color.white);
/*  435:     */   }
/*  436:     */   
/*  437:     */   public void drawSheared(float x, float y, float hshear, float vshear, Color filter)
/*  438:     */   {
/*  439: 707 */     if (this.alpha != 1.0F)
/*  440:     */     {
/*  441: 708 */       if (filter == null) {
/*  442: 709 */         filter = Color.white;
/*  443:     */       }
/*  444: 712 */       filter = new Color(filter);
/*  445: 713 */       filter.a *= this.alpha;
/*  446:     */     }
/*  447: 715 */     if (filter != null) {
/*  448: 716 */       filter.bind();
/*  449:     */     }
/*  450: 719 */     this.texture.bind();
/*  451:     */     
/*  452: 721 */     GL.glTranslatef(x, y, 0.0F);
/*  453: 722 */     if (this.angle != 0.0F)
/*  454:     */     {
/*  455: 723 */       GL.glTranslatef(this.centerX, this.centerY, 0.0F);
/*  456: 724 */       GL.glRotatef(this.angle, 0.0F, 0.0F, 1.0F);
/*  457: 725 */       GL.glTranslatef(-this.centerX, -this.centerY, 0.0F);
/*  458:     */     }
/*  459: 728 */     GL.glBegin(7);
/*  460: 729 */     init();
/*  461:     */     
/*  462: 731 */     GL.glTexCoord2f(this.textureOffsetX, this.textureOffsetY);
/*  463: 732 */     GL.glVertex3f(0.0F, 0.0F, 0.0F);
/*  464: 733 */     GL.glTexCoord2f(this.textureOffsetX, this.textureOffsetY + this.textureHeight);
/*  465: 734 */     GL.glVertex3f(hshear, this.height, 0.0F);
/*  466: 735 */     GL.glTexCoord2f(this.textureOffsetX + this.textureWidth, this.textureOffsetY + 
/*  467: 736 */       this.textureHeight);
/*  468: 737 */     GL.glVertex3f(this.width + hshear, this.height + vshear, 0.0F);
/*  469: 738 */     GL.glTexCoord2f(this.textureOffsetX + this.textureWidth, this.textureOffsetY);
/*  470: 739 */     GL.glVertex3f(this.width, vshear, 0.0F);
/*  471: 740 */     GL.glEnd();
/*  472: 742 */     if (this.angle != 0.0F)
/*  473:     */     {
/*  474: 743 */       GL.glTranslatef(this.centerX, this.centerY, 0.0F);
/*  475: 744 */       GL.glRotatef(-this.angle, 0.0F, 0.0F, 1.0F);
/*  476: 745 */       GL.glTranslatef(-this.centerX, -this.centerY, 0.0F);
/*  477:     */     }
/*  478: 747 */     GL.glTranslatef(-x, -y, 0.0F);
/*  479:     */   }
/*  480:     */   
/*  481:     */   public void draw(float x, float y, float width, float height, Color filter)
/*  482:     */   {
/*  483: 760 */     if (this.alpha != 1.0F)
/*  484:     */     {
/*  485: 761 */       if (filter == null) {
/*  486: 762 */         filter = Color.white;
/*  487:     */       }
/*  488: 765 */       filter = new Color(filter);
/*  489: 766 */       filter.a *= this.alpha;
/*  490:     */     }
/*  491: 768 */     if (filter != null) {
/*  492: 769 */       filter.bind();
/*  493:     */     }
/*  494: 772 */     this.texture.bind();
/*  495:     */     
/*  496: 774 */     GL.glTranslatef(x, y, 0.0F);
/*  497: 775 */     if (this.angle != 0.0F)
/*  498:     */     {
/*  499: 776 */       GL.glTranslatef(this.centerX, this.centerY, 0.0F);
/*  500: 777 */       GL.glRotatef(this.angle, 0.0F, 0.0F, 1.0F);
/*  501: 778 */       GL.glTranslatef(-this.centerX, -this.centerY, 0.0F);
/*  502:     */     }
/*  503: 781 */     GL.glBegin(7);
/*  504: 782 */     drawEmbedded(0.0F, 0.0F, width, height);
/*  505: 783 */     GL.glEnd();
/*  506: 785 */     if (this.angle != 0.0F)
/*  507:     */     {
/*  508: 786 */       GL.glTranslatef(this.centerX, this.centerY, 0.0F);
/*  509: 787 */       GL.glRotatef(-this.angle, 0.0F, 0.0F, 1.0F);
/*  510: 788 */       GL.glTranslatef(-this.centerX, -this.centerY, 0.0F);
/*  511:     */     }
/*  512: 790 */     GL.glTranslatef(-x, -y, 0.0F);
/*  513:     */   }
/*  514:     */   
/*  515:     */   public void drawFlash(float x, float y, float width, float height)
/*  516:     */   {
/*  517: 802 */     drawFlash(x, y, width, height, Color.white);
/*  518:     */   }
/*  519:     */   
/*  520:     */   public void setCenterOfRotation(float x, float y)
/*  521:     */   {
/*  522: 812 */     this.centerX = x;
/*  523: 813 */     this.centerY = y;
/*  524:     */   }
/*  525:     */   
/*  526:     */   public float getCenterOfRotationX()
/*  527:     */   {
/*  528: 822 */     init();
/*  529:     */     
/*  530: 824 */     return this.centerX;
/*  531:     */   }
/*  532:     */   
/*  533:     */   public float getCenterOfRotationY()
/*  534:     */   {
/*  535: 833 */     init();
/*  536:     */     
/*  537: 835 */     return this.centerY;
/*  538:     */   }
/*  539:     */   
/*  540:     */   public void drawFlash(float x, float y, float width, float height, Color col)
/*  541:     */   {
/*  542: 848 */     init();
/*  543:     */     
/*  544: 850 */     col.bind();
/*  545: 851 */     this.texture.bind();
/*  546: 853 */     if (GL.canSecondaryColor())
/*  547:     */     {
/*  548: 854 */       GL.glEnable(33880);
/*  549: 855 */       GL.glSecondaryColor3ubEXT((byte)(int)(col.r * 255.0F), 
/*  550: 856 */         (byte)(int)(col.g * 255.0F), 
/*  551: 857 */         (byte)(int)(col.b * 255.0F));
/*  552:     */     }
/*  553: 860 */     GL.glTexEnvi(8960, 8704, 8448);
/*  554:     */     
/*  555: 862 */     GL.glTranslatef(x, y, 0.0F);
/*  556: 863 */     if (this.angle != 0.0F)
/*  557:     */     {
/*  558: 864 */       GL.glTranslatef(this.centerX, this.centerY, 0.0F);
/*  559: 865 */       GL.glRotatef(this.angle, 0.0F, 0.0F, 1.0F);
/*  560: 866 */       GL.glTranslatef(-this.centerX, -this.centerY, 0.0F);
/*  561:     */     }
/*  562: 869 */     GL.glBegin(7);
/*  563: 870 */     drawEmbedded(0.0F, 0.0F, width, height);
/*  564: 871 */     GL.glEnd();
/*  565: 873 */     if (this.angle != 0.0F)
/*  566:     */     {
/*  567: 874 */       GL.glTranslatef(this.centerX, this.centerY, 0.0F);
/*  568: 875 */       GL.glRotatef(-this.angle, 0.0F, 0.0F, 1.0F);
/*  569: 876 */       GL.glTranslatef(-this.centerX, -this.centerY, 0.0F);
/*  570:     */     }
/*  571: 878 */     GL.glTranslatef(-x, -y, 0.0F);
/*  572: 880 */     if (GL.canSecondaryColor()) {
/*  573: 881 */       GL.glDisable(33880);
/*  574:     */     }
/*  575:     */   }
/*  576:     */   
/*  577:     */   public void drawFlash(float x, float y)
/*  578:     */   {
/*  579: 892 */     drawFlash(x, y, getWidth(), getHeight());
/*  580:     */   }
/*  581:     */   
/*  582:     */   public void setRotation(float angle)
/*  583:     */   {
/*  584: 902 */     this.angle = (angle % 360.0F);
/*  585:     */   }
/*  586:     */   
/*  587:     */   public float getRotation()
/*  588:     */   {
/*  589: 912 */     return this.angle;
/*  590:     */   }
/*  591:     */   
/*  592:     */   public float getAlpha()
/*  593:     */   {
/*  594: 921 */     return this.alpha;
/*  595:     */   }
/*  596:     */   
/*  597:     */   public void setAlpha(float alpha)
/*  598:     */   {
/*  599: 930 */     this.alpha = alpha;
/*  600:     */   }
/*  601:     */   
/*  602:     */   public void rotate(float angle)
/*  603:     */   {
/*  604: 940 */     this.angle += angle;
/*  605: 941 */     this.angle %= 360.0F;
/*  606:     */   }
/*  607:     */   
/*  608:     */   public Image getSubImage(int x, int y, int width, int height)
/*  609:     */   {
/*  610: 955 */     init();
/*  611:     */     
/*  612: 957 */     float newTextureOffsetX = x / this.width * this.textureWidth + this.textureOffsetX;
/*  613: 958 */     float newTextureOffsetY = y / this.height * this.textureHeight + this.textureOffsetY;
/*  614: 959 */     float newTextureWidth = width / this.width * this.textureWidth;
/*  615: 960 */     float newTextureHeight = height / this.height * this.textureHeight;
/*  616:     */     
/*  617: 962 */     Image sub = new Image();
/*  618: 963 */     sub.inited = true;
/*  619: 964 */     sub.texture = this.texture;
/*  620: 965 */     sub.textureOffsetX = newTextureOffsetX;
/*  621: 966 */     sub.textureOffsetY = newTextureOffsetY;
/*  622: 967 */     sub.textureWidth = newTextureWidth;
/*  623: 968 */     sub.textureHeight = newTextureHeight;
/*  624:     */     
/*  625: 970 */     sub.width = width;
/*  626: 971 */     sub.height = height;
/*  627: 972 */     sub.ref = this.ref;
/*  628: 973 */     sub.centerX = (width / 2);
/*  629: 974 */     sub.centerY = (height / 2);
/*  630:     */     
/*  631: 976 */     return sub;
/*  632:     */   }
/*  633:     */   
/*  634:     */   public void draw(float x, float y, float srcx, float srcy, float srcx2, float srcy2)
/*  635:     */   {
/*  636: 990 */     draw(x, y, x + this.width, y + this.height, srcx, srcy, srcx2, srcy2);
/*  637:     */   }
/*  638:     */   
/*  639:     */   public void draw(float x, float y, float x2, float y2, float srcx, float srcy, float srcx2, float srcy2)
/*  640:     */   {
/*  641:1006 */     draw(x, y, x2, y2, srcx, srcy, srcx2, srcy2, Color.white);
/*  642:     */   }
/*  643:     */   
/*  644:     */   public void draw(float x, float y, float x2, float y2, float srcx, float srcy, float srcx2, float srcy2, Color filter)
/*  645:     */   {
/*  646:1023 */     init();
/*  647:1025 */     if (this.alpha != 1.0F)
/*  648:     */     {
/*  649:1026 */       if (filter == null) {
/*  650:1027 */         filter = Color.white;
/*  651:     */       }
/*  652:1030 */       filter = new Color(filter);
/*  653:1031 */       filter.a *= this.alpha;
/*  654:     */     }
/*  655:1033 */     filter.bind();
/*  656:1034 */     this.texture.bind();
/*  657:     */     
/*  658:1036 */     GL.glTranslatef(x, y, 0.0F);
/*  659:1037 */     if (this.angle != 0.0F)
/*  660:     */     {
/*  661:1038 */       GL.glTranslatef(this.centerX, this.centerY, 0.0F);
/*  662:1039 */       GL.glRotatef(this.angle, 0.0F, 0.0F, 1.0F);
/*  663:1040 */       GL.glTranslatef(-this.centerX, -this.centerY, 0.0F);
/*  664:     */     }
/*  665:1043 */     GL.glBegin(7);
/*  666:1044 */     drawEmbedded(0.0F, 0.0F, x2 - x, y2 - y, srcx, srcy, srcx2, srcy2);
/*  667:1045 */     GL.glEnd();
/*  668:1047 */     if (this.angle != 0.0F)
/*  669:     */     {
/*  670:1048 */       GL.glTranslatef(this.centerX, this.centerY, 0.0F);
/*  671:1049 */       GL.glRotatef(-this.angle, 0.0F, 0.0F, 1.0F);
/*  672:1050 */       GL.glTranslatef(-this.centerX, -this.centerY, 0.0F);
/*  673:     */     }
/*  674:1052 */     GL.glTranslatef(-x, -y, 0.0F);
/*  675:     */   }
/*  676:     */   
/*  677:     */   public void drawEmbedded(float x, float y, float x2, float y2, float srcx, float srcy, float srcx2, float srcy2)
/*  678:     */   {
/*  679:1073 */     drawEmbedded(x, y, x2, y2, srcx, srcy, srcx2, srcy2, null);
/*  680:     */   }
/*  681:     */   
/*  682:     */   public void drawEmbedded(float x, float y, float x2, float y2, float srcx, float srcy, float srcx2, float srcy2, Color filter)
/*  683:     */   {
/*  684:1091 */     if (filter != null) {
/*  685:1092 */       filter.bind();
/*  686:     */     }
/*  687:1095 */     float mywidth = x2 - x;
/*  688:1096 */     float myheight = y2 - y;
/*  689:1097 */     float texwidth = srcx2 - srcx;
/*  690:1098 */     float texheight = srcy2 - srcy;
/*  691:     */     
/*  692:1100 */     float newTextureOffsetX = srcx / this.width * this.textureWidth + 
/*  693:1101 */       this.textureOffsetX;
/*  694:1102 */     float newTextureOffsetY = srcy / this.height * this.textureHeight + 
/*  695:1103 */       this.textureOffsetY;
/*  696:1104 */     float newTextureWidth = texwidth / this.width * 
/*  697:1105 */       this.textureWidth;
/*  698:1106 */     float newTextureHeight = texheight / this.height * 
/*  699:1107 */       this.textureHeight;
/*  700:     */     
/*  701:1109 */     GL.glTexCoord2f(newTextureOffsetX, newTextureOffsetY);
/*  702:1110 */     GL.glVertex3f(x, y, 0.0F);
/*  703:1111 */     GL.glTexCoord2f(newTextureOffsetX, newTextureOffsetY + 
/*  704:1112 */       newTextureHeight);
/*  705:1113 */     GL.glVertex3f(x, y + myheight, 0.0F);
/*  706:1114 */     GL.glTexCoord2f(newTextureOffsetX + newTextureWidth, 
/*  707:1115 */       newTextureOffsetY + newTextureHeight);
/*  708:1116 */     GL.glVertex3f(x + mywidth, y + myheight, 0.0F);
/*  709:1117 */     GL.glTexCoord2f(newTextureOffsetX + newTextureWidth, 
/*  710:1118 */       newTextureOffsetY);
/*  711:1119 */     GL.glVertex3f(x + mywidth, y, 0.0F);
/*  712:     */   }
/*  713:     */   
/*  714:     */   public void drawWarped(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4)
/*  715:     */   {
/*  716:1136 */     Color.white.bind();
/*  717:1137 */     this.texture.bind();
/*  718:     */     
/*  719:1139 */     GL.glTranslatef(x1, y1, 0.0F);
/*  720:1140 */     if (this.angle != 0.0F)
/*  721:     */     {
/*  722:1141 */       GL.glTranslatef(this.centerX, this.centerY, 0.0F);
/*  723:1142 */       GL.glRotatef(this.angle, 0.0F, 0.0F, 1.0F);
/*  724:1143 */       GL.glTranslatef(-this.centerX, -this.centerY, 0.0F);
/*  725:     */     }
/*  726:1146 */     GL.glBegin(7);
/*  727:1147 */     init();
/*  728:     */     
/*  729:1149 */     GL.glTexCoord2f(this.textureOffsetX, this.textureOffsetY);
/*  730:1150 */     GL.glVertex3f(0.0F, 0.0F, 0.0F);
/*  731:1151 */     GL.glTexCoord2f(this.textureOffsetX, this.textureOffsetY + this.textureHeight);
/*  732:1152 */     GL.glVertex3f(x2 - x1, y2 - y1, 0.0F);
/*  733:1153 */     GL.glTexCoord2f(this.textureOffsetX + this.textureWidth, this.textureOffsetY + 
/*  734:1154 */       this.textureHeight);
/*  735:1155 */     GL.glVertex3f(x3 - x1, y3 - y1, 0.0F);
/*  736:1156 */     GL.glTexCoord2f(this.textureOffsetX + this.textureWidth, this.textureOffsetY);
/*  737:1157 */     GL.glVertex3f(x4 - x1, y4 - y1, 0.0F);
/*  738:1158 */     GL.glEnd();
/*  739:1160 */     if (this.angle != 0.0F)
/*  740:     */     {
/*  741:1161 */       GL.glTranslatef(this.centerX, this.centerY, 0.0F);
/*  742:1162 */       GL.glRotatef(-this.angle, 0.0F, 0.0F, 1.0F);
/*  743:1163 */       GL.glTranslatef(-this.centerX, -this.centerY, 0.0F);
/*  744:     */     }
/*  745:1165 */     GL.glTranslatef(-x1, -y1, 0.0F);
/*  746:     */   }
/*  747:     */   
/*  748:     */   public int getWidth()
/*  749:     */   {
/*  750:1174 */     init();
/*  751:1175 */     return this.width;
/*  752:     */   }
/*  753:     */   
/*  754:     */   public int getHeight()
/*  755:     */   {
/*  756:1184 */     init();
/*  757:1185 */     return this.height;
/*  758:     */   }
/*  759:     */   
/*  760:     */   public Image copy()
/*  761:     */   {
/*  762:1195 */     init();
/*  763:1196 */     return getSubImage(0, 0, this.width, this.height);
/*  764:     */   }
/*  765:     */   
/*  766:     */   public Image getScaledCopy(float scale)
/*  767:     */   {
/*  768:1206 */     init();
/*  769:1207 */     return getScaledCopy((int)(this.width * scale), (int)(this.height * scale));
/*  770:     */   }
/*  771:     */   
/*  772:     */   public Image getScaledCopy(int width, int height)
/*  773:     */   {
/*  774:1218 */     init();
/*  775:1219 */     Image image = copy();
/*  776:1220 */     image.width = width;
/*  777:1221 */     image.height = height;
/*  778:1222 */     image.centerX = (width / 2);
/*  779:1223 */     image.centerY = (height / 2);
/*  780:1224 */     return image;
/*  781:     */   }
/*  782:     */   
/*  783:     */   public void ensureInverted()
/*  784:     */   {
/*  785:1231 */     if (this.textureHeight > 0.0F)
/*  786:     */     {
/*  787:1232 */       this.textureOffsetY += this.textureHeight;
/*  788:1233 */       this.textureHeight = (-this.textureHeight);
/*  789:     */     }
/*  790:     */   }
/*  791:     */   
/*  792:     */   public Image getFlippedCopy(boolean flipHorizontal, boolean flipVertical)
/*  793:     */   {
/*  794:1245 */     init();
/*  795:1246 */     Image image = copy();
/*  796:1248 */     if (flipHorizontal)
/*  797:     */     {
/*  798:1249 */       this.textureOffsetX += this.textureWidth;
/*  799:1250 */       image.textureWidth = (-this.textureWidth);
/*  800:     */     }
/*  801:1252 */     if (flipVertical)
/*  802:     */     {
/*  803:1253 */       this.textureOffsetY += this.textureHeight;
/*  804:1254 */       image.textureHeight = (-this.textureHeight);
/*  805:     */     }
/*  806:1257 */     return image;
/*  807:     */   }
/*  808:     */   
/*  809:     */   public void endUse()
/*  810:     */   {
/*  811:1266 */     if (inUse != this) {
/*  812:1267 */       throw new RuntimeException("The sprite sheet is not currently in use");
/*  813:     */     }
/*  814:1269 */     inUse = null;
/*  815:1270 */     GL.glEnd();
/*  816:     */   }
/*  817:     */   
/*  818:     */   public void startUse()
/*  819:     */   {
/*  820:1280 */     if (inUse != null) {
/*  821:1281 */       throw new RuntimeException("Attempt to start use of a sprite sheet before ending use with another - see endUse()");
/*  822:     */     }
/*  823:1283 */     inUse = this;
/*  824:1284 */     init();
/*  825:     */     
/*  826:1286 */     Color.white.bind();
/*  827:1287 */     this.texture.bind();
/*  828:1288 */     GL.glBegin(7);
/*  829:     */   }
/*  830:     */   
/*  831:     */   public String toString()
/*  832:     */   {
/*  833:1295 */     init();
/*  834:     */     
/*  835:1297 */     return "[Image " + this.ref + " " + this.width + "x" + this.height + "  " + this.textureOffsetX + "," + this.textureOffsetY + "," + this.textureWidth + "," + this.textureHeight + "]";
/*  836:     */   }
/*  837:     */   
/*  838:     */   public Texture getTexture()
/*  839:     */   {
/*  840:1306 */     return this.texture;
/*  841:     */   }
/*  842:     */   
/*  843:     */   public void setTexture(Texture texture)
/*  844:     */   {
/*  845:1315 */     this.texture = texture;
/*  846:1316 */     reinit();
/*  847:     */   }
/*  848:     */   
/*  849:     */   private int translate(byte b)
/*  850:     */   {
/*  851:1326 */     if (b < 0) {
/*  852:1327 */       return 256 + b;
/*  853:     */     }
/*  854:1330 */     return b;
/*  855:     */   }
/*  856:     */   
/*  857:     */   public Color getColor(int x, int y)
/*  858:     */   {
/*  859:1341 */     if (this.pixelData == null) {
/*  860:1342 */       this.pixelData = this.texture.getTextureData();
/*  861:     */     }
/*  862:1345 */     int xo = (int)(this.textureOffsetX * this.texture.getTextureWidth());
/*  863:1346 */     int yo = (int)(this.textureOffsetY * this.texture.getTextureHeight());
/*  864:1348 */     if (this.textureWidth < 0.0F) {
/*  865:1349 */       x = xo - x;
/*  866:     */     } else {
/*  867:1351 */       x = xo + x;
/*  868:     */     }
/*  869:1354 */     if (this.textureHeight < 0.0F) {
/*  870:1355 */       y = yo - y;
/*  871:     */     } else {
/*  872:1357 */       y = yo + y;
/*  873:     */     }
/*  874:1360 */     int offset = x + y * this.texture.getTextureWidth();
/*  875:1361 */     offset *= (this.texture.hasAlpha() ? 4 : 3);
/*  876:1363 */     if (this.texture.hasAlpha()) {
/*  877:1364 */       return new Color(translate(this.pixelData[offset]), translate(this.pixelData[(offset + 1)]), 
/*  878:1365 */         translate(this.pixelData[(offset + 2)]), translate(this.pixelData[(offset + 3)]));
/*  879:     */     }
/*  880:1367 */     return new Color(translate(this.pixelData[offset]), translate(this.pixelData[(offset + 1)]), 
/*  881:1368 */       translate(this.pixelData[(offset + 2)]));
/*  882:     */   }
/*  883:     */   
/*  884:     */   public boolean isDestroyed()
/*  885:     */   {
/*  886:1378 */     return this.destroyed;
/*  887:     */   }
/*  888:     */   
/*  889:     */   public void destroy()
/*  890:     */     throws SlickException
/*  891:     */   {
/*  892:1388 */     if (isDestroyed()) {
/*  893:1389 */       return;
/*  894:     */     }
/*  895:1392 */     this.destroyed = true;
/*  896:1393 */     this.texture.release();
/*  897:1394 */     GraphicsFactory.releaseGraphicsForImage(this);
/*  898:     */   }
/*  899:     */   
/*  900:     */   public void flushPixelData()
/*  901:     */   {
/*  902:1401 */     this.pixelData = null;
/*  903:     */   }
/*  904:     */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.Image
 * JD-Core Version:    0.7.0.1
 */