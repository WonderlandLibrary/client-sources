/*    1:     */ package org.newdawn.slick;
/*    2:     */ 
/*    3:     */ import java.nio.ByteBuffer;
/*    4:     */ import java.nio.DoubleBuffer;
/*    5:     */ import java.nio.FloatBuffer;
/*    6:     */ import java.security.AccessController;
/*    7:     */ import java.security.PrivilegedAction;
/*    8:     */ import java.util.ArrayList;
/*    9:     */ import org.lwjgl.BufferUtils;
/*   10:     */ import org.newdawn.slick.geom.Rectangle;
/*   11:     */ import org.newdawn.slick.geom.Shape;
/*   12:     */ import org.newdawn.slick.geom.ShapeRenderer;
/*   13:     */ import org.newdawn.slick.opengl.Texture;
/*   14:     */ import org.newdawn.slick.opengl.TextureImpl;
/*   15:     */ import org.newdawn.slick.opengl.renderer.LineStripRenderer;
/*   16:     */ import org.newdawn.slick.opengl.renderer.Renderer;
/*   17:     */ import org.newdawn.slick.opengl.renderer.SGL;
/*   18:     */ import org.newdawn.slick.util.FastTrig;
/*   19:     */ import org.newdawn.slick.util.Log;
/*   20:     */ 
/*   21:     */ public class Graphics
/*   22:     */ {
/*   23:  29 */   protected static SGL GL = ;
/*   24:  31 */   private static LineStripRenderer LSR = Renderer.getLineStripRenderer();
/*   25:  34 */   public static int MODE_NORMAL = 1;
/*   26:  37 */   public static int MODE_ALPHA_MAP = 2;
/*   27:  40 */   public static int MODE_ALPHA_BLEND = 3;
/*   28:  43 */   public static int MODE_COLOR_MULTIPLY = 4;
/*   29:  46 */   public static int MODE_ADD = 5;
/*   30:  49 */   public static int MODE_SCREEN = 6;
/*   31:     */   private static final int DEFAULT_SEGMENTS = 50;
/*   32:  55 */   protected static Graphics currentGraphics = null;
/*   33:     */   protected static Font DEFAULT_FONT;
/*   34:  61 */   private float sx = 1.0F;
/*   35:  63 */   private float sy = 1.0F;
/*   36:     */   private Font font;
/*   37:     */   
/*   38:     */   public static void setCurrent(Graphics current)
/*   39:     */   {
/*   40:  71 */     if (currentGraphics != current)
/*   41:     */     {
/*   42:  72 */       if (currentGraphics != null) {
/*   43:  73 */         currentGraphics.disable();
/*   44:     */       }
/*   45:  75 */       currentGraphics = current;
/*   46:  76 */       currentGraphics.enable();
/*   47:     */     }
/*   48:     */   }
/*   49:     */   
/*   50:  84 */   private Color currentColor = Color.white;
/*   51:     */   protected int screenWidth;
/*   52:     */   protected int screenHeight;
/*   53:     */   private boolean pushed;
/*   54:     */   private Rectangle clip;
/*   55:  99 */   private DoubleBuffer worldClip = BufferUtils.createDoubleBuffer(4);
/*   56: 102 */   private ByteBuffer readBuffer = BufferUtils.createByteBuffer(4);
/*   57:     */   private boolean antialias;
/*   58:     */   private Rectangle worldClipRecord;
/*   59: 111 */   private int currentDrawingMode = MODE_NORMAL;
/*   60: 114 */   private float lineWidth = 1.0F;
/*   61: 117 */   private ArrayList stack = new ArrayList();
/*   62:     */   private int stackIndex;
/*   63:     */   
/*   64:     */   public Graphics() {}
/*   65:     */   
/*   66:     */   public Graphics(int width, int height)
/*   67:     */   {
/*   68: 137 */     if (DEFAULT_FONT == null) {
/*   69: 138 */       AccessController.doPrivileged(new PrivilegedAction()
/*   70:     */       {
/*   71:     */         public Object run()
/*   72:     */         {
/*   73:     */           try
/*   74:     */           {
/*   75: 141 */             Graphics.DEFAULT_FONT = new AngelCodeFont(
/*   76: 142 */               "org/newdawn/slick/data/defaultfont.fnt", 
/*   77: 143 */               "org/newdawn/slick/data/defaultfont.png");
/*   78:     */           }
/*   79:     */           catch (SlickException e)
/*   80:     */           {
/*   81: 145 */             Log.error(e);
/*   82:     */           }
/*   83: 147 */           return null;
/*   84:     */         }
/*   85:     */       });
/*   86:     */     }
/*   87: 152 */     this.font = DEFAULT_FONT;
/*   88: 153 */     this.screenWidth = width;
/*   89: 154 */     this.screenHeight = height;
/*   90:     */   }
/*   91:     */   
/*   92:     */   void setDimensions(int width, int height)
/*   93:     */   {
/*   94: 164 */     this.screenWidth = width;
/*   95: 165 */     this.screenHeight = height;
/*   96:     */   }
/*   97:     */   
/*   98:     */   public void setDrawMode(int mode)
/*   99:     */   {
/*  100: 179 */     predraw();
/*  101: 180 */     this.currentDrawingMode = mode;
/*  102: 181 */     if (this.currentDrawingMode == MODE_NORMAL)
/*  103:     */     {
/*  104: 182 */       GL.glEnable(3042);
/*  105: 183 */       GL.glColorMask(true, true, true, true);
/*  106: 184 */       GL.glBlendFunc(770, 771);
/*  107:     */     }
/*  108: 186 */     if (this.currentDrawingMode == MODE_ALPHA_MAP)
/*  109:     */     {
/*  110: 187 */       GL.glDisable(3042);
/*  111: 188 */       GL.glColorMask(false, false, false, true);
/*  112:     */     }
/*  113: 190 */     if (this.currentDrawingMode == MODE_ALPHA_BLEND)
/*  114:     */     {
/*  115: 191 */       GL.glEnable(3042);
/*  116: 192 */       GL.glColorMask(true, true, true, false);
/*  117: 193 */       GL.glBlendFunc(772, 773);
/*  118:     */     }
/*  119: 195 */     if (this.currentDrawingMode == MODE_COLOR_MULTIPLY)
/*  120:     */     {
/*  121: 196 */       GL.glEnable(3042);
/*  122: 197 */       GL.glColorMask(true, true, true, true);
/*  123: 198 */       GL.glBlendFunc(769, 768);
/*  124:     */     }
/*  125: 200 */     if (this.currentDrawingMode == MODE_ADD)
/*  126:     */     {
/*  127: 201 */       GL.glEnable(3042);
/*  128: 202 */       GL.glColorMask(true, true, true, true);
/*  129: 203 */       GL.glBlendFunc(1, 1);
/*  130:     */     }
/*  131: 205 */     if (this.currentDrawingMode == MODE_SCREEN)
/*  132:     */     {
/*  133: 206 */       GL.glEnable(3042);
/*  134: 207 */       GL.glColorMask(true, true, true, true);
/*  135: 208 */       GL.glBlendFunc(1, 769);
/*  136:     */     }
/*  137: 210 */     postdraw();
/*  138:     */   }
/*  139:     */   
/*  140:     */   public void clearAlphaMap()
/*  141:     */   {
/*  142: 219 */     pushTransform();
/*  143: 220 */     GL.glLoadIdentity();
/*  144:     */     
/*  145: 222 */     int originalMode = this.currentDrawingMode;
/*  146: 223 */     setDrawMode(MODE_ALPHA_MAP);
/*  147: 224 */     setColor(new Color(0, 0, 0, 0));
/*  148: 225 */     fillRect(0.0F, 0.0F, this.screenWidth, this.screenHeight);
/*  149: 226 */     setColor(this.currentColor);
/*  150: 227 */     setDrawMode(originalMode);
/*  151:     */     
/*  152: 229 */     popTransform();
/*  153:     */   }
/*  154:     */   
/*  155:     */   private void predraw()
/*  156:     */   {
/*  157: 237 */     setCurrent(this);
/*  158:     */   }
/*  159:     */   
/*  160:     */   private void postdraw() {}
/*  161:     */   
/*  162:     */   protected void enable() {}
/*  163:     */   
/*  164:     */   public void flush()
/*  165:     */   {
/*  166: 257 */     if (currentGraphics == this)
/*  167:     */     {
/*  168: 258 */       currentGraphics.disable();
/*  169: 259 */       currentGraphics = null;
/*  170:     */     }
/*  171:     */   }
/*  172:     */   
/*  173:     */   protected void disable() {}
/*  174:     */   
/*  175:     */   public Font getFont()
/*  176:     */   {
/*  177: 275 */     return this.font;
/*  178:     */   }
/*  179:     */   
/*  180:     */   public void setBackground(Color color)
/*  181:     */   {
/*  182: 287 */     predraw();
/*  183: 288 */     GL.glClearColor(color.r, color.g, color.b, color.a);
/*  184: 289 */     postdraw();
/*  185:     */   }
/*  186:     */   
/*  187:     */   public Color getBackground()
/*  188:     */   {
/*  189: 298 */     predraw();
/*  190: 299 */     FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
/*  191: 300 */     GL.glGetFloat(3106, buffer);
/*  192: 301 */     postdraw();
/*  193:     */     
/*  194: 303 */     return new Color(buffer);
/*  195:     */   }
/*  196:     */   
/*  197:     */   public void clear()
/*  198:     */   {
/*  199: 310 */     predraw();
/*  200: 311 */     GL.glClear(16384);
/*  201: 312 */     postdraw();
/*  202:     */   }
/*  203:     */   
/*  204:     */   public void resetTransform()
/*  205:     */   {
/*  206: 319 */     this.sx = 1.0F;
/*  207: 320 */     this.sy = 1.0F;
/*  208: 322 */     if (this.pushed)
/*  209:     */     {
/*  210: 323 */       predraw();
/*  211: 324 */       GL.glPopMatrix();
/*  212: 325 */       this.pushed = false;
/*  213: 326 */       postdraw();
/*  214:     */     }
/*  215:     */   }
/*  216:     */   
/*  217:     */   private void checkPush()
/*  218:     */   {
/*  219: 334 */     if (!this.pushed)
/*  220:     */     {
/*  221: 335 */       predraw();
/*  222: 336 */       GL.glPushMatrix();
/*  223: 337 */       this.pushed = true;
/*  224: 338 */       postdraw();
/*  225:     */     }
/*  226:     */   }
/*  227:     */   
/*  228:     */   public void scale(float sx, float sy)
/*  229:     */   {
/*  230: 351 */     this.sx *= sx;
/*  231: 352 */     this.sy *= sy;
/*  232:     */     
/*  233: 354 */     checkPush();
/*  234:     */     
/*  235: 356 */     predraw();
/*  236: 357 */     GL.glScalef(sx, sy, 1.0F);
/*  237: 358 */     postdraw();
/*  238:     */   }
/*  239:     */   
/*  240:     */   public void rotate(float rx, float ry, float ang)
/*  241:     */   {
/*  242: 372 */     checkPush();
/*  243:     */     
/*  244: 374 */     predraw();
/*  245: 375 */     translate(rx, ry);
/*  246: 376 */     GL.glRotatef(ang, 0.0F, 0.0F, 1.0F);
/*  247: 377 */     translate(-rx, -ry);
/*  248: 378 */     postdraw();
/*  249:     */   }
/*  250:     */   
/*  251:     */   public void translate(float x, float y)
/*  252:     */   {
/*  253: 390 */     checkPush();
/*  254:     */     
/*  255: 392 */     predraw();
/*  256: 393 */     GL.glTranslatef(x, y, 0.0F);
/*  257: 394 */     postdraw();
/*  258:     */   }
/*  259:     */   
/*  260:     */   public void setFont(Font font)
/*  261:     */   {
/*  262: 404 */     this.font = font;
/*  263:     */   }
/*  264:     */   
/*  265:     */   public void resetFont()
/*  266:     */   {
/*  267: 411 */     this.font = DEFAULT_FONT;
/*  268:     */   }
/*  269:     */   
/*  270:     */   public void setColor(Color color)
/*  271:     */   {
/*  272: 421 */     if (color == null) {
/*  273: 422 */       return;
/*  274:     */     }
/*  275: 425 */     this.currentColor = new Color(color);
/*  276: 426 */     predraw();
/*  277: 427 */     this.currentColor.bind();
/*  278: 428 */     postdraw();
/*  279:     */   }
/*  280:     */   
/*  281:     */   public Color getColor()
/*  282:     */   {
/*  283: 437 */     return new Color(this.currentColor);
/*  284:     */   }
/*  285:     */   
/*  286:     */   public void drawLine(float x1, float y1, float x2, float y2)
/*  287:     */   {
/*  288: 453 */     float lineWidth = this.lineWidth - 1.0F;
/*  289: 455 */     if (LSR.applyGLLineFixes())
/*  290:     */     {
/*  291: 456 */       if (x1 == x2)
/*  292:     */       {
/*  293: 457 */         if (y1 > y2)
/*  294:     */         {
/*  295: 458 */           float temp = y2;
/*  296: 459 */           y2 = y1;
/*  297: 460 */           y1 = temp;
/*  298:     */         }
/*  299: 462 */         float step = 1.0F / this.sy;
/*  300: 463 */         lineWidth /= this.sy;
/*  301: 464 */         fillRect(x1 - lineWidth / 2.0F, y1 - lineWidth / 2.0F, lineWidth + step, y2 - y1 + lineWidth + step);
/*  302: 465 */         return;
/*  303:     */       }
/*  304: 466 */       if (y1 == y2)
/*  305:     */       {
/*  306: 467 */         if (x1 > x2)
/*  307:     */         {
/*  308: 468 */           float temp = x2;
/*  309: 469 */           x2 = x1;
/*  310: 470 */           x1 = temp;
/*  311:     */         }
/*  312: 472 */         float step = 1.0F / this.sx;
/*  313: 473 */         lineWidth /= this.sx;
/*  314: 474 */         fillRect(x1 - lineWidth / 2.0F, y1 - lineWidth / 2.0F, x2 - x1 + lineWidth + step, lineWidth + step);
/*  315: 475 */         return;
/*  316:     */       }
/*  317:     */     }
/*  318: 479 */     predraw();
/*  319: 480 */     this.currentColor.bind();
/*  320: 481 */     TextureImpl.bindNone();
/*  321:     */     
/*  322: 483 */     LSR.start();
/*  323: 484 */     LSR.vertex(x1, y1);
/*  324: 485 */     LSR.vertex(x2, y2);
/*  325: 486 */     LSR.end();
/*  326:     */     
/*  327: 488 */     postdraw();
/*  328:     */   }
/*  329:     */   
/*  330:     */   public void draw(Shape shape, ShapeFill fill)
/*  331:     */   {
/*  332: 500 */     predraw();
/*  333: 501 */     TextureImpl.bindNone();
/*  334:     */     
/*  335: 503 */     ShapeRenderer.draw(shape, fill);
/*  336:     */     
/*  337: 505 */     this.currentColor.bind();
/*  338: 506 */     postdraw();
/*  339:     */   }
/*  340:     */   
/*  341:     */   public void fill(Shape shape, ShapeFill fill)
/*  342:     */   {
/*  343: 518 */     predraw();
/*  344: 519 */     TextureImpl.bindNone();
/*  345:     */     
/*  346: 521 */     ShapeRenderer.fill(shape, fill);
/*  347:     */     
/*  348: 523 */     this.currentColor.bind();
/*  349: 524 */     postdraw();
/*  350:     */   }
/*  351:     */   
/*  352:     */   public void draw(Shape shape)
/*  353:     */   {
/*  354: 534 */     predraw();
/*  355: 535 */     TextureImpl.bindNone();
/*  356: 536 */     this.currentColor.bind();
/*  357:     */     
/*  358: 538 */     ShapeRenderer.draw(shape);
/*  359:     */     
/*  360: 540 */     postdraw();
/*  361:     */   }
/*  362:     */   
/*  363:     */   public void fill(Shape shape)
/*  364:     */   {
/*  365: 550 */     predraw();
/*  366: 551 */     TextureImpl.bindNone();
/*  367: 552 */     this.currentColor.bind();
/*  368:     */     
/*  369: 554 */     ShapeRenderer.fill(shape);
/*  370:     */     
/*  371: 556 */     postdraw();
/*  372:     */   }
/*  373:     */   
/*  374:     */   public void texture(Shape shape, Image image)
/*  375:     */   {
/*  376: 568 */     texture(shape, image, 0.01F, 0.01F, false);
/*  377:     */   }
/*  378:     */   
/*  379:     */   public void texture(Shape shape, Image image, ShapeFill fill)
/*  380:     */   {
/*  381: 582 */     texture(shape, image, 0.01F, 0.01F, fill);
/*  382:     */   }
/*  383:     */   
/*  384:     */   public void texture(Shape shape, Image image, boolean fit)
/*  385:     */   {
/*  386: 596 */     if (fit) {
/*  387: 597 */       texture(shape, image, 1.0F, 1.0F, true);
/*  388:     */     } else {
/*  389: 599 */       texture(shape, image, 0.01F, 0.01F, false);
/*  390:     */     }
/*  391:     */   }
/*  392:     */   
/*  393:     */   public void texture(Shape shape, Image image, float scaleX, float scaleY)
/*  394:     */   {
/*  395: 616 */     texture(shape, image, scaleX, scaleY, false);
/*  396:     */   }
/*  397:     */   
/*  398:     */   public void texture(Shape shape, Image image, float scaleX, float scaleY, boolean fit)
/*  399:     */   {
/*  400: 635 */     predraw();
/*  401: 636 */     TextureImpl.bindNone();
/*  402: 637 */     this.currentColor.bind();
/*  403: 639 */     if (fit) {
/*  404: 640 */       ShapeRenderer.textureFit(shape, image, scaleX, scaleY);
/*  405:     */     } else {
/*  406: 642 */       ShapeRenderer.texture(shape, image, scaleX, scaleY);
/*  407:     */     }
/*  408: 645 */     postdraw();
/*  409:     */   }
/*  410:     */   
/*  411:     */   public void texture(Shape shape, Image image, float scaleX, float scaleY, ShapeFill fill)
/*  412:     */   {
/*  413: 664 */     predraw();
/*  414: 665 */     TextureImpl.bindNone();
/*  415: 666 */     this.currentColor.bind();
/*  416:     */     
/*  417: 668 */     ShapeRenderer.texture(shape, image, scaleX, scaleY, fill);
/*  418:     */     
/*  419: 670 */     postdraw();
/*  420:     */   }
/*  421:     */   
/*  422:     */   public void drawRect(float x1, float y1, float width, float height)
/*  423:     */   {
/*  424: 686 */     float lineWidth = getLineWidth();
/*  425:     */     
/*  426: 688 */     drawLine(x1, y1, x1 + width, y1);
/*  427: 689 */     drawLine(x1 + width, y1, x1 + width, y1 + height);
/*  428: 690 */     drawLine(x1 + width, y1 + height, x1, y1 + height);
/*  429: 691 */     drawLine(x1, y1 + height, x1, y1);
/*  430:     */   }
/*  431:     */   
/*  432:     */   public void clearClip()
/*  433:     */   {
/*  434: 699 */     this.clip = null;
/*  435: 700 */     predraw();
/*  436: 701 */     GL.glDisable(3089);
/*  437: 702 */     postdraw();
/*  438:     */   }
/*  439:     */   
/*  440:     */   public void setWorldClip(float x, float y, float width, float height)
/*  441:     */   {
/*  442: 721 */     predraw();
/*  443: 722 */     this.worldClipRecord = new Rectangle(x, y, width, height);
/*  444:     */     
/*  445: 724 */     GL.glEnable(12288);
/*  446: 725 */     this.worldClip.put(1.0D).put(0.0D).put(0.0D).put(-x).flip();
/*  447: 726 */     GL.glClipPlane(12288, this.worldClip);
/*  448: 727 */     GL.glEnable(12289);
/*  449: 728 */     this.worldClip.put(-1.0D).put(0.0D).put(0.0D).put(x + width).flip();
/*  450: 729 */     GL.glClipPlane(12289, this.worldClip);
/*  451:     */     
/*  452: 731 */     GL.glEnable(12290);
/*  453: 732 */     this.worldClip.put(0.0D).put(1.0D).put(0.0D).put(-y).flip();
/*  454: 733 */     GL.glClipPlane(12290, this.worldClip);
/*  455: 734 */     GL.glEnable(12291);
/*  456: 735 */     this.worldClip.put(0.0D).put(-1.0D).put(0.0D).put(y + height).flip();
/*  457: 736 */     GL.glClipPlane(12291, this.worldClip);
/*  458: 737 */     postdraw();
/*  459:     */   }
/*  460:     */   
/*  461:     */   public void clearWorldClip()
/*  462:     */   {
/*  463: 744 */     predraw();
/*  464: 745 */     this.worldClipRecord = null;
/*  465: 746 */     GL.glDisable(12288);
/*  466: 747 */     GL.glDisable(12289);
/*  467: 748 */     GL.glDisable(12290);
/*  468: 749 */     GL.glDisable(12291);
/*  469: 750 */     postdraw();
/*  470:     */   }
/*  471:     */   
/*  472:     */   public void setWorldClip(Rectangle clip)
/*  473:     */   {
/*  474: 761 */     if (clip == null) {
/*  475: 762 */       clearWorldClip();
/*  476:     */     } else {
/*  477: 764 */       setWorldClip(clip.getX(), clip.getY(), clip.getWidth(), clip
/*  478: 765 */         .getHeight());
/*  479:     */     }
/*  480:     */   }
/*  481:     */   
/*  482:     */   public Rectangle getWorldClip()
/*  483:     */   {
/*  484: 775 */     return this.worldClipRecord;
/*  485:     */   }
/*  486:     */   
/*  487:     */   public void setClip(int x, int y, int width, int height)
/*  488:     */   {
/*  489: 793 */     predraw();
/*  490: 795 */     if (this.clip == null)
/*  491:     */     {
/*  492: 796 */       GL.glEnable(3089);
/*  493: 797 */       this.clip = new Rectangle(x, y, width, height);
/*  494:     */     }
/*  495:     */     else
/*  496:     */     {
/*  497: 799 */       this.clip.setBounds(x, y, width, height);
/*  498:     */     }
/*  499: 802 */     GL.glScissor(x, this.screenHeight - y - height, width, height);
/*  500: 803 */     postdraw();
/*  501:     */   }
/*  502:     */   
/*  503:     */   public void setClip(Rectangle rect)
/*  504:     */   {
/*  505: 816 */     if (rect == null)
/*  506:     */     {
/*  507: 817 */       clearClip();
/*  508: 818 */       return;
/*  509:     */     }
/*  510: 821 */     setClip((int)rect.getX(), (int)rect.getY(), (int)rect.getWidth(), 
/*  511: 822 */       (int)rect.getHeight());
/*  512:     */   }
/*  513:     */   
/*  514:     */   public Rectangle getClip()
/*  515:     */   {
/*  516: 832 */     return this.clip;
/*  517:     */   }
/*  518:     */   
/*  519:     */   public void fillRect(float x, float y, float width, float height, Image pattern, float offX, float offY)
/*  520:     */   {
/*  521: 856 */     int cols = (int)Math.ceil(width / pattern.getWidth()) + 2;
/*  522: 857 */     int rows = (int)Math.ceil(height / pattern.getHeight()) + 2;
/*  523:     */     
/*  524: 859 */     Rectangle preClip = getWorldClip();
/*  525: 860 */     setWorldClip(x, y, width, height);
/*  526:     */     
/*  527: 862 */     predraw();
/*  528: 864 */     for (int c = 0; c < cols; c++) {
/*  529: 865 */       for (int r = 0; r < rows; r++) {
/*  530: 866 */         pattern.draw(c * pattern.getWidth() + x - offX, r * 
/*  531: 867 */           pattern.getHeight() + y - offY);
/*  532:     */       }
/*  533:     */     }
/*  534: 870 */     postdraw();
/*  535:     */     
/*  536: 872 */     setWorldClip(preClip);
/*  537:     */   }
/*  538:     */   
/*  539:     */   public void fillRect(float x1, float y1, float width, float height)
/*  540:     */   {
/*  541: 888 */     predraw();
/*  542: 889 */     TextureImpl.bindNone();
/*  543: 890 */     this.currentColor.bind();
/*  544:     */     
/*  545: 892 */     GL.glBegin(7);
/*  546: 893 */     GL.glVertex2f(x1, y1);
/*  547: 894 */     GL.glVertex2f(x1 + width, y1);
/*  548: 895 */     GL.glVertex2f(x1 + width, y1 + height);
/*  549: 896 */     GL.glVertex2f(x1, y1 + height);
/*  550: 897 */     GL.glEnd();
/*  551: 898 */     postdraw();
/*  552:     */   }
/*  553:     */   
/*  554:     */   public void drawOval(float x1, float y1, float width, float height)
/*  555:     */   {
/*  556: 916 */     drawOval(x1, y1, width, height, 50);
/*  557:     */   }
/*  558:     */   
/*  559:     */   public void drawOval(float x1, float y1, float width, float height, int segments)
/*  560:     */   {
/*  561: 937 */     drawArc(x1, y1, width, height, segments, 0.0F, 360.0F);
/*  562:     */   }
/*  563:     */   
/*  564:     */   public void drawArc(float x1, float y1, float width, float height, float start, float end)
/*  565:     */   {
/*  566: 960 */     drawArc(x1, y1, width, height, 50, start, end);
/*  567:     */   }
/*  568:     */   
/*  569:     */   public void drawArc(float x1, float y1, float width, float height, int segments, float start, float end)
/*  570:     */   {
/*  571: 985 */     predraw();
/*  572: 986 */     TextureImpl.bindNone();
/*  573: 987 */     this.currentColor.bind();
/*  574: 989 */     while (end < start) {
/*  575: 990 */       end += 360.0F;
/*  576:     */     }
/*  577: 993 */     float cx = x1 + width / 2.0F;
/*  578: 994 */     float cy = y1 + height / 2.0F;
/*  579:     */     
/*  580: 996 */     LSR.start();
/*  581: 997 */     int step = 360 / segments;
/*  582: 999 */     for (int a = (int)start; a < (int)(end + step); a += step)
/*  583:     */     {
/*  584:1000 */       float ang = a;
/*  585:1001 */       if (ang > end) {
/*  586:1002 */         ang = end;
/*  587:     */       }
/*  588:1004 */       float x = (float)(cx + FastTrig.cos(Math.toRadians(ang)) * width / 2.0D);
/*  589:1005 */       float y = (float)(cy + FastTrig.sin(Math.toRadians(ang)) * height / 2.0D);
/*  590:     */       
/*  591:1007 */       LSR.vertex(x, y);
/*  592:     */     }
/*  593:1009 */     LSR.end();
/*  594:1010 */     postdraw();
/*  595:     */   }
/*  596:     */   
/*  597:     */   public void fillOval(float x1, float y1, float width, float height)
/*  598:     */   {
/*  599:1028 */     fillOval(x1, y1, width, height, 50);
/*  600:     */   }
/*  601:     */   
/*  602:     */   public void fillOval(float x1, float y1, float width, float height, int segments)
/*  603:     */   {
/*  604:1049 */     fillArc(x1, y1, width, height, segments, 0.0F, 360.0F);
/*  605:     */   }
/*  606:     */   
/*  607:     */   public void fillArc(float x1, float y1, float width, float height, float start, float end)
/*  608:     */   {
/*  609:1072 */     fillArc(x1, y1, width, height, 50, start, end);
/*  610:     */   }
/*  611:     */   
/*  612:     */   public void fillArc(float x1, float y1, float width, float height, int segments, float start, float end)
/*  613:     */   {
/*  614:1097 */     predraw();
/*  615:1098 */     TextureImpl.bindNone();
/*  616:1099 */     this.currentColor.bind();
/*  617:1101 */     while (end < start) {
/*  618:1102 */       end += 360.0F;
/*  619:     */     }
/*  620:1105 */     float cx = x1 + width / 2.0F;
/*  621:1106 */     float cy = y1 + height / 2.0F;
/*  622:     */     
/*  623:1108 */     GL.glBegin(6);
/*  624:1109 */     int step = 360 / segments;
/*  625:     */     
/*  626:1111 */     GL.glVertex2f(cx, cy);
/*  627:1113 */     for (int a = (int)start; a < (int)(end + step); a += step)
/*  628:     */     {
/*  629:1114 */       float ang = a;
/*  630:1115 */       if (ang > end) {
/*  631:1116 */         ang = end;
/*  632:     */       }
/*  633:1119 */       float x = (float)(cx + FastTrig.cos(Math.toRadians(ang)) * width / 2.0D);
/*  634:1120 */       float y = (float)(cy + FastTrig.sin(Math.toRadians(ang)) * height / 2.0D);
/*  635:     */       
/*  636:1122 */       GL.glVertex2f(x, y);
/*  637:     */     }
/*  638:1124 */     GL.glEnd();
/*  639:1126 */     if (this.antialias)
/*  640:     */     {
/*  641:1127 */       GL.glBegin(6);
/*  642:1128 */       GL.glVertex2f(cx, cy);
/*  643:1129 */       if (end != 360.0F) {
/*  644:1130 */         end -= 10.0F;
/*  645:     */       }
/*  646:1133 */       for (int a = (int)start; a < (int)(end + step); a += step)
/*  647:     */       {
/*  648:1134 */         float ang = a;
/*  649:1135 */         if (ang > end) {
/*  650:1136 */           ang = end;
/*  651:     */         }
/*  652:1139 */         float x = (float)(cx + FastTrig.cos(Math.toRadians(ang + 10.0F)) * 
/*  653:1140 */           width / 2.0D);
/*  654:1141 */         float y = (float)(cy + FastTrig.sin(Math.toRadians(ang + 10.0F)) * 
/*  655:1142 */           height / 2.0D);
/*  656:     */         
/*  657:1144 */         GL.glVertex2f(x, y);
/*  658:     */       }
/*  659:1146 */       GL.glEnd();
/*  660:     */     }
/*  661:1149 */     postdraw();
/*  662:     */   }
/*  663:     */   
/*  664:     */   public void drawRoundRect(float x, float y, float width, float height, int cornerRadius)
/*  665:     */   {
/*  666:1168 */     drawRoundRect(x, y, width, height, cornerRadius, 50);
/*  667:     */   }
/*  668:     */   
/*  669:     */   public void drawRoundRect(float x, float y, float width, float height, int cornerRadius, int segs)
/*  670:     */   {
/*  671:1189 */     if (cornerRadius < 0) {
/*  672:1190 */       throw new IllegalArgumentException("corner radius must be > 0");
/*  673:     */     }
/*  674:1191 */     if (cornerRadius == 0)
/*  675:     */     {
/*  676:1192 */       drawRect(x, y, width, height);
/*  677:1193 */       return;
/*  678:     */     }
/*  679:1196 */     int mr = (int)Math.min(width, height) / 2;
/*  680:1198 */     if (cornerRadius > mr) {
/*  681:1199 */       cornerRadius = mr;
/*  682:     */     }
/*  683:1202 */     drawLine(x + cornerRadius, y, x + width - cornerRadius, y);
/*  684:1203 */     drawLine(x, y + cornerRadius, x, y + height - cornerRadius);
/*  685:1204 */     drawLine(x + width, y + cornerRadius, x + width, y + height - 
/*  686:1205 */       cornerRadius);
/*  687:1206 */     drawLine(x + cornerRadius, y + height, x + width - cornerRadius, y + 
/*  688:1207 */       height);
/*  689:     */     
/*  690:1209 */     float d = cornerRadius * 2;
/*  691:     */     
/*  692:1211 */     drawArc(x + width - d, y + height - d, d, d, segs, 0.0F, 90.0F);
/*  693:     */     
/*  694:1213 */     drawArc(x, y + height - d, d, d, segs, 90.0F, 180.0F);
/*  695:     */     
/*  696:1215 */     drawArc(x + width - d, y, d, d, segs, 270.0F, 360.0F);
/*  697:     */     
/*  698:1217 */     drawArc(x, y, d, d, segs, 180.0F, 270.0F);
/*  699:     */   }
/*  700:     */   
/*  701:     */   public void fillRoundRect(float x, float y, float width, float height, int cornerRadius)
/*  702:     */   {
/*  703:1236 */     fillRoundRect(x, y, width, height, cornerRadius, 50);
/*  704:     */   }
/*  705:     */   
/*  706:     */   public void fillRoundRect(float x, float y, float width, float height, int cornerRadius, int segs)
/*  707:     */   {
/*  708:1257 */     if (cornerRadius < 0) {
/*  709:1258 */       throw new IllegalArgumentException("corner radius must be > 0");
/*  710:     */     }
/*  711:1259 */     if (cornerRadius == 0)
/*  712:     */     {
/*  713:1260 */       fillRect(x, y, width, height);
/*  714:1261 */       return;
/*  715:     */     }
/*  716:1264 */     int mr = (int)Math.min(width, height) / 2;
/*  717:1266 */     if (cornerRadius > mr) {
/*  718:1267 */       cornerRadius = mr;
/*  719:     */     }
/*  720:1270 */     float d = cornerRadius * 2;
/*  721:     */     
/*  722:1272 */     fillRect(x + cornerRadius, y, width - d, cornerRadius);
/*  723:1273 */     fillRect(x, y + cornerRadius, cornerRadius, height - d);
/*  724:1274 */     fillRect(x + width - cornerRadius, y + cornerRadius, cornerRadius, 
/*  725:1275 */       height - d);
/*  726:1276 */     fillRect(x + cornerRadius, y + height - cornerRadius, width - d, 
/*  727:1277 */       cornerRadius);
/*  728:1278 */     fillRect(x + cornerRadius, y + cornerRadius, width - d, height - d);
/*  729:     */     
/*  730:     */ 
/*  731:1281 */     fillArc(x + width - d, y + height - d, d, d, segs, 0.0F, 90.0F);
/*  732:     */     
/*  733:1283 */     fillArc(x, y + height - d, d, d, segs, 90.0F, 180.0F);
/*  734:     */     
/*  735:1285 */     fillArc(x + width - d, y, d, d, segs, 270.0F, 360.0F);
/*  736:     */     
/*  737:1287 */     fillArc(x, y, d, d, segs, 180.0F, 270.0F);
/*  738:     */   }
/*  739:     */   
/*  740:     */   public void setLineWidth(float width)
/*  741:     */   {
/*  742:1298 */     predraw();
/*  743:1299 */     this.lineWidth = width;
/*  744:1300 */     LSR.setWidth(width);
/*  745:1301 */     GL.glPointSize(width);
/*  746:1302 */     postdraw();
/*  747:     */   }
/*  748:     */   
/*  749:     */   public float getLineWidth()
/*  750:     */   {
/*  751:1311 */     return this.lineWidth;
/*  752:     */   }
/*  753:     */   
/*  754:     */   public void resetLineWidth()
/*  755:     */   {
/*  756:1318 */     predraw();
/*  757:     */     
/*  758:1320 */     Renderer.getLineStripRenderer().setWidth(1.0F);
/*  759:1321 */     GL.glLineWidth(1.0F);
/*  760:1322 */     GL.glPointSize(1.0F);
/*  761:     */     
/*  762:1324 */     postdraw();
/*  763:     */   }
/*  764:     */   
/*  765:     */   public void setAntiAlias(boolean anti)
/*  766:     */   {
/*  767:1334 */     predraw();
/*  768:1335 */     this.antialias = anti;
/*  769:1336 */     LSR.setAntiAlias(anti);
/*  770:1337 */     if (anti) {
/*  771:1338 */       GL.glEnable(2881);
/*  772:     */     } else {
/*  773:1340 */       GL.glDisable(2881);
/*  774:     */     }
/*  775:1342 */     postdraw();
/*  776:     */   }
/*  777:     */   
/*  778:     */   public boolean isAntiAlias()
/*  779:     */   {
/*  780:1351 */     return this.antialias;
/*  781:     */   }
/*  782:     */   
/*  783:     */   public void drawString(String str, float x, float y)
/*  784:     */   {
/*  785:1365 */     predraw();
/*  786:1366 */     this.font.drawString(x, y, str, this.currentColor);
/*  787:1367 */     postdraw();
/*  788:     */   }
/*  789:     */   
/*  790:     */   public void drawImage(Image image, float x, float y, Color col)
/*  791:     */   {
/*  792:1383 */     predraw();
/*  793:1384 */     image.draw(x, y, col);
/*  794:1385 */     this.currentColor.bind();
/*  795:1386 */     postdraw();
/*  796:     */   }
/*  797:     */   
/*  798:     */   public void drawAnimation(Animation anim, float x, float y)
/*  799:     */   {
/*  800:1400 */     drawAnimation(anim, x, y, Color.white);
/*  801:     */   }
/*  802:     */   
/*  803:     */   public void drawAnimation(Animation anim, float x, float y, Color col)
/*  804:     */   {
/*  805:1416 */     predraw();
/*  806:1417 */     anim.draw(x, y, col);
/*  807:1418 */     this.currentColor.bind();
/*  808:1419 */     postdraw();
/*  809:     */   }
/*  810:     */   
/*  811:     */   public void drawImage(Image image, float x, float y)
/*  812:     */   {
/*  813:1433 */     drawImage(image, x, y, Color.white);
/*  814:     */   }
/*  815:     */   
/*  816:     */   public void drawImage(Image image, float x, float y, float x2, float y2, float srcx, float srcy, float srcx2, float srcy2)
/*  817:     */   {
/*  818:1465 */     predraw();
/*  819:1466 */     image.draw(x, y, x2, y2, srcx, srcy, srcx2, srcy2);
/*  820:1467 */     this.currentColor.bind();
/*  821:1468 */     postdraw();
/*  822:     */   }
/*  823:     */   
/*  824:     */   public void drawImage(Image image, float x, float y, float srcx, float srcy, float srcx2, float srcy2)
/*  825:     */   {
/*  826:1496 */     drawImage(image, x, y, x + image.getWidth(), y + image.getHeight(), 
/*  827:1497 */       srcx, srcy, srcx2, srcy2);
/*  828:     */   }
/*  829:     */   
/*  830:     */   public void copyArea(Image target, int x, int y)
/*  831:     */   {
/*  832:1512 */     int format = target.getTexture().hasAlpha() ? 6408 : 6407;
/*  833:1513 */     target.bind();
/*  834:1514 */     GL.glCopyTexImage2D(3553, 0, format, x, this.screenHeight - (
/*  835:1515 */       y + target.getHeight()), target.getTexture()
/*  836:1516 */       .getTextureWidth(), target.getTexture().getTextureHeight(), 0);
/*  837:1517 */     target.ensureInverted();
/*  838:     */   }
/*  839:     */   
/*  840:     */   private int translate(byte b)
/*  841:     */   {
/*  842:1528 */     if (b < 0) {
/*  843:1529 */       return 256 + b;
/*  844:     */     }
/*  845:1532 */     return b;
/*  846:     */   }
/*  847:     */   
/*  848:     */   public Color getPixel(int x, int y)
/*  849:     */   {
/*  850:1545 */     predraw();
/*  851:1546 */     GL.glReadPixels(x, this.screenHeight - y, 1, 1, 6408, 
/*  852:1547 */       5121, this.readBuffer);
/*  853:1548 */     postdraw();
/*  854:     */     
/*  855:1550 */     return new Color(translate(this.readBuffer.get(0)), translate(this.readBuffer
/*  856:1551 */       .get(1)), translate(this.readBuffer.get(2)), translate(this.readBuffer
/*  857:1552 */       .get(3)));
/*  858:     */   }
/*  859:     */   
/*  860:     */   public void getArea(int x, int y, int width, int height, ByteBuffer target)
/*  861:     */   {
/*  862:1566 */     if (target.capacity() < width * height * 4) {
/*  863:1568 */       throw new IllegalArgumentException("Byte buffer provided to get area is not big enough");
/*  864:     */     }
/*  865:1571 */     predraw();
/*  866:1572 */     GL.glReadPixels(x, this.screenHeight - y - height, width, height, 6408, 
/*  867:1573 */       5121, target);
/*  868:1574 */     postdraw();
/*  869:     */   }
/*  870:     */   
/*  871:     */   public void drawImage(Image image, float x, float y, float x2, float y2, float srcx, float srcy, float srcx2, float srcy2, Color col)
/*  872:     */   {
/*  873:1608 */     predraw();
/*  874:1609 */     image.draw(x, y, x2, y2, srcx, srcy, srcx2, srcy2, col);
/*  875:1610 */     this.currentColor.bind();
/*  876:1611 */     postdraw();
/*  877:     */   }
/*  878:     */   
/*  879:     */   public void drawImage(Image image, float x, float y, float srcx, float srcy, float srcx2, float srcy2, Color col)
/*  880:     */   {
/*  881:1641 */     drawImage(image, x, y, x + image.getWidth(), y + image.getHeight(), 
/*  882:1642 */       srcx, srcy, srcx2, srcy2, col);
/*  883:     */   }
/*  884:     */   
/*  885:     */   public void drawGradientLine(float x1, float y1, float red1, float green1, float blue1, float alpha1, float x2, float y2, float red2, float green2, float blue2, float alpha2)
/*  886:     */   {
/*  887:1676 */     predraw();
/*  888:     */     
/*  889:1678 */     TextureImpl.bindNone();
/*  890:     */     
/*  891:1680 */     GL.glBegin(1);
/*  892:     */     
/*  893:1682 */     GL.glColor4f(red1, green1, blue1, alpha1);
/*  894:1683 */     GL.glVertex2f(x1, y1);
/*  895:     */     
/*  896:1685 */     GL.glColor4f(red2, green2, blue2, alpha2);
/*  897:1686 */     GL.glVertex2f(x2, y2);
/*  898:     */     
/*  899:1688 */     GL.glEnd();
/*  900:     */     
/*  901:1690 */     postdraw();
/*  902:     */   }
/*  903:     */   
/*  904:     */   public void drawGradientLine(float x1, float y1, Color Color1, float x2, float y2, Color Color2)
/*  905:     */   {
/*  906:1711 */     predraw();
/*  907:     */     
/*  908:1713 */     TextureImpl.bindNone();
/*  909:     */     
/*  910:1715 */     GL.glBegin(1);
/*  911:     */     
/*  912:1717 */     Color1.bind();
/*  913:1718 */     GL.glVertex2f(x1, y1);
/*  914:     */     
/*  915:1720 */     Color2.bind();
/*  916:1721 */     GL.glVertex2f(x2, y2);
/*  917:     */     
/*  918:1723 */     GL.glEnd();
/*  919:     */     
/*  920:1725 */     postdraw();
/*  921:     */   }
/*  922:     */   
/*  923:     */   public void pushTransform()
/*  924:     */   {
/*  925:1735 */     predraw();
/*  926:     */     FloatBuffer buffer;
/*  927:1738 */     if (this.stackIndex >= this.stack.size())
/*  928:     */     {
/*  929:1739 */       FloatBuffer buffer = BufferUtils.createFloatBuffer(18);
/*  930:1740 */       this.stack.add(buffer);
/*  931:     */     }
/*  932:     */     else
/*  933:     */     {
/*  934:1742 */       buffer = (FloatBuffer)this.stack.get(this.stackIndex);
/*  935:     */     }
/*  936:1745 */     GL.glGetFloat(2982, buffer);
/*  937:1746 */     buffer.put(16, this.sx);
/*  938:1747 */     buffer.put(17, this.sy);
/*  939:1748 */     this.stackIndex += 1;
/*  940:     */     
/*  941:1750 */     postdraw();
/*  942:     */   }
/*  943:     */   
/*  944:     */   public void popTransform()
/*  945:     */   {
/*  946:1758 */     if (this.stackIndex == 0) {
/*  947:1759 */       throw new RuntimeException("Attempt to pop a transform that hasn't be pushed");
/*  948:     */     }
/*  949:1762 */     predraw();
/*  950:     */     
/*  951:1764 */     this.stackIndex -= 1;
/*  952:1765 */     FloatBuffer oldBuffer = (FloatBuffer)this.stack.get(this.stackIndex);
/*  953:1766 */     GL.glLoadMatrix(oldBuffer);
/*  954:1767 */     this.sx = oldBuffer.get(16);
/*  955:1768 */     this.sy = oldBuffer.get(17);
/*  956:     */     
/*  957:1770 */     postdraw();
/*  958:     */   }
/*  959:     */   
/*  960:     */   public void destroy() {}
/*  961:     */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.Graphics
 * JD-Core Version:    0.7.0.1
 */