/*     */ package org.newdawn.slick;
/*     */ 
/*     */ import java.applet.Applet;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Canvas;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.Label;
/*     */ import java.awt.Panel;
/*     */ import java.awt.TextArea;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.nio.ByteBuffer;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.LWJGLException;
/*     */ import org.lwjgl.input.Cursor;
/*     */ import org.lwjgl.input.Mouse;
/*     */ import org.lwjgl.opengl.Display;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.PixelFormat;
/*     */ import org.newdawn.slick.openal.SoundStore;
/*     */ import org.newdawn.slick.opengl.CursorLoader;
/*     */ import org.newdawn.slick.opengl.ImageData;
/*     */ import org.newdawn.slick.opengl.InternalTextureLoader;
/*     */ import org.newdawn.slick.util.Log;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AppletGameContainer
/*     */   extends Applet
/*     */ {
/*     */   protected ContainerPanel canvas;
/*     */   protected Container container;
/*     */   protected Canvas displayParent;
/*     */   protected Thread gameThread;
/*     */   protected boolean alphaSupport = true;
/*     */   
/*     */   public void destroy() {
/*  53 */     if (this.displayParent != null) {
/*  54 */       remove(this.displayParent);
/*     */     }
/*  56 */     super.destroy();
/*     */     
/*  58 */     Log.info("Clear up");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void destroyLWJGL() {
/*  65 */     this.container.stopApplet();
/*     */     
/*     */     try {
/*  68 */       this.gameThread.join();
/*  69 */     } catch (InterruptedException e) {
/*  70 */       Log.error(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startLWJGL() {
/*  85 */     if (this.gameThread != null) {
/*     */       return;
/*     */     }
/*     */     
/*  89 */     this.gameThread = new Thread() {
/*     */         public void run() {
/*     */           try {
/*  92 */             AppletGameContainer.this.canvas.start();
/*     */           }
/*  94 */           catch (Exception e) {
/*  95 */             e.printStackTrace();
/*  96 */             if (Display.isCreated()) {
/*  97 */               Display.destroy();
/*     */             }
/*  99 */             AppletGameContainer.this.displayParent.setVisible(false);
/* 100 */             AppletGameContainer.this.add(new AppletGameContainer.ConsolePanel(e));
/* 101 */             AppletGameContainer.this.validate();
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 106 */     this.gameThread.start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stop() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/* 119 */     removeAll();
/* 120 */     setLayout(new BorderLayout());
/* 121 */     setIgnoreRepaint(true);
/*     */     
/*     */     try {
/* 124 */       Game game = (Game)Class.forName(getParameter("game")).newInstance();
/*     */       
/* 126 */       this.container = new Container(game);
/* 127 */       this.canvas = new ContainerPanel(this.container);
/* 128 */       this.displayParent = new Canvas() {
/*     */           public final void addNotify() {
/* 130 */             super.addNotify();
/* 131 */             AppletGameContainer.this.startLWJGL();
/*     */           }
/*     */           public final void removeNotify() {
/* 134 */             AppletGameContainer.this.destroyLWJGL();
/* 135 */             super.removeNotify();
/*     */           }
/*     */         };
/*     */ 
/*     */       
/* 140 */       this.displayParent.setSize(getWidth(), getHeight());
/* 141 */       add(this.displayParent);
/* 142 */       this.displayParent.setFocusable(true);
/* 143 */       this.displayParent.requestFocus();
/* 144 */       this.displayParent.setIgnoreRepaint(true);
/* 145 */       setVisible(true);
/* 146 */     } catch (Exception e) {
/* 147 */       Log.error(e);
/* 148 */       throw new RuntimeException("Unable to create game container");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GameContainer getContainer() {
/* 158 */     return this.container;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class ContainerPanel
/*     */   {
/*     */     private AppletGameContainer.Container container;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ContainerPanel(AppletGameContainer.Container container) {
/* 176 */       this.container = container;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void createDisplay() throws Exception {
/*     */       try {
/* 187 */         Display.create(new PixelFormat(8, 8, GameContainer.stencil ? 8 : 0));
/* 188 */         AppletGameContainer.this.alphaSupport = true;
/* 189 */       } catch (Exception e) {
/*     */         
/* 191 */         AppletGameContainer.this.alphaSupport = false;
/* 192 */         Display.destroy();
/*     */         
/* 194 */         Display.create();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void start() throws Exception {
/* 204 */       Display.setParent(AppletGameContainer.this.displayParent);
/* 205 */       Display.setVSyncEnabled(true);
/*     */       
/*     */       try {
/* 208 */         createDisplay();
/* 209 */       } catch (LWJGLException e) {
/* 210 */         e.printStackTrace();
/*     */         
/* 212 */         Thread.sleep(1000L);
/* 213 */         createDisplay();
/*     */       } 
/*     */       
/* 216 */       initGL();
/* 217 */       AppletGameContainer.this.displayParent.requestFocus();
/* 218 */       this.container.runloop();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void initGL() {
/*     */       try {
/* 226 */         InternalTextureLoader.get().clear();
/* 227 */         SoundStore.get().clear();
/*     */         
/* 229 */         this.container.initApplet();
/* 230 */       } catch (Exception e) {
/* 231 */         Log.error(e);
/* 232 */         this.container.stopApplet();
/*     */       } 
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
/*     */   public class Container
/*     */     extends GameContainer
/*     */   {
/*     */     public Container(Game game) {
/* 249 */       super(game);
/*     */       
/* 251 */       this.width = AppletGameContainer.this.getWidth();
/* 252 */       this.height = AppletGameContainer.this.getHeight();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void initApplet() throws SlickException {
/* 261 */       initSystem();
/* 262 */       enterOrtho();
/*     */       
/*     */       try {
/* 265 */         getInput().initControllers();
/* 266 */       } catch (SlickException e) {
/* 267 */         Log.info("Controllers not available");
/* 268 */       } catch (Throwable e) {
/* 269 */         Log.info("Controllers not available");
/*     */       } 
/*     */       
/* 272 */       this.game.init(this);
/* 273 */       getDelta();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isRunning() {
/* 282 */       return this.running;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void stopApplet() {
/* 289 */       this.running = false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getScreenHeight() {
/* 296 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getScreenWidth() {
/* 303 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean supportsAlphaInBackBuffer() {
/* 312 */       return AppletGameContainer.this.alphaSupport;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasFocus() {
/* 319 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Applet getApplet() {
/* 327 */       return AppletGameContainer.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setIcon(String ref) throws SlickException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setMouseGrabbed(boolean grabbed) {
/* 341 */       Mouse.setGrabbed(grabbed);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isMouseGrabbed() {
/* 348 */       return Mouse.isGrabbed();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setMouseCursor(String ref, int hotSpotX, int hotSpotY) throws SlickException {
/*     */       try {
/* 357 */         Cursor cursor = CursorLoader.get().getCursor(ref, hotSpotX, hotSpotY);
/* 358 */         Mouse.setNativeCursor(cursor);
/* 359 */       } catch (Throwable e) {
/* 360 */         Log.error("Failed to load and apply cursor.", e);
/* 361 */         throw new SlickException("Failed to set mouse cursor", e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int get2Fold(int fold) {
/* 372 */       int ret = 2;
/* 373 */       while (ret < fold) {
/* 374 */         ret *= 2;
/*     */       }
/* 376 */       return ret;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setMouseCursor(Image image, int hotSpotX, int hotSpotY) throws SlickException {
/*     */       try {
/* 384 */         Image temp = new Image(get2Fold(image.getWidth()), get2Fold(image.getHeight()));
/* 385 */         Graphics g = temp.getGraphics();
/*     */         
/* 387 */         ByteBuffer buffer = BufferUtils.createByteBuffer(temp.getWidth() * temp.getHeight() * 4);
/* 388 */         g.drawImage(image.getFlippedCopy(false, true), 0.0F, 0.0F);
/* 389 */         g.flush();
/* 390 */         g.getArea(0, 0, temp.getWidth(), temp.getHeight(), buffer);
/*     */         
/* 392 */         Cursor cursor = CursorLoader.get().getCursor(buffer, hotSpotX, hotSpotY, temp.getWidth(), temp.getHeight());
/* 393 */         Mouse.setNativeCursor(cursor);
/* 394 */       } catch (Throwable e) {
/* 395 */         Log.error("Failed to load and apply cursor.", e);
/* 396 */         throw new SlickException("Failed to set mouse cursor", e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setIcons(String[] refs) throws SlickException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setMouseCursor(ImageData data, int hotSpotX, int hotSpotY) throws SlickException {
/*     */       try {
/* 412 */         Cursor cursor = CursorLoader.get().getCursor(data, hotSpotX, hotSpotY);
/* 413 */         Mouse.setNativeCursor(cursor);
/* 414 */       } catch (Throwable e) {
/* 415 */         Log.error("Failed to load and apply cursor.", e);
/* 416 */         throw new SlickException("Failed to set mouse cursor", e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setMouseCursor(Cursor cursor, int hotSpotX, int hotSpotY) throws SlickException {
/*     */       try {
/* 425 */         Mouse.setNativeCursor(cursor);
/* 426 */       } catch (Throwable e) {
/* 427 */         Log.error("Failed to load and apply cursor.", e);
/* 428 */         throw new SlickException("Failed to set mouse cursor", e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void setDefaultMouseCursor() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isFullscreen() {
/* 439 */       return Display.isFullscreen();
/*     */     }
/*     */     
/*     */     public void setFullscreen(boolean fullscreen) throws SlickException {
/* 443 */       if (fullscreen == isFullscreen()) {
/*     */         return;
/*     */       }
/*     */       
/*     */       try {
/* 448 */         if (fullscreen) {
/*     */           
/* 450 */           int newWidth, newHeight, screenWidth = Display.getDisplayMode().getWidth();
/* 451 */           int screenHeight = Display.getDisplayMode().getHeight();
/*     */ 
/*     */           
/* 454 */           float gameAspectRatio = this.width / this.height;
/* 455 */           float screenAspectRatio = screenWidth / screenHeight;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 463 */           if (gameAspectRatio >= screenAspectRatio) {
/* 464 */             newWidth = screenWidth;
/* 465 */             newHeight = (int)(this.height / this.width / screenWidth);
/*     */           } else {
/* 467 */             newWidth = (int)(this.width / this.height / screenHeight);
/* 468 */             newHeight = screenHeight;
/*     */           } 
/*     */ 
/*     */           
/* 472 */           int xoffset = (screenWidth - newWidth) / 2;
/* 473 */           int yoffset = (screenHeight - newHeight) / 2;
/*     */ 
/*     */           
/* 476 */           GL11.glViewport(xoffset, yoffset, newWidth, newHeight);
/*     */           
/* 478 */           enterOrtho();
/*     */ 
/*     */           
/* 481 */           getInput().setOffset(-xoffset * this.width / newWidth, -yoffset * this.height / newHeight);
/*     */ 
/*     */ 
/*     */           
/* 485 */           getInput().setScale(this.width / newWidth, this.height / newHeight);
/*     */ 
/*     */           
/* 488 */           this.width = screenWidth;
/* 489 */           this.height = screenHeight;
/* 490 */           Display.setFullscreen(true);
/*     */         } else {
/*     */           
/* 493 */           getInput().setOffset(0.0F, 0.0F);
/* 494 */           getInput().setScale(1.0F, 1.0F);
/* 495 */           this.width = AppletGameContainer.this.getWidth();
/* 496 */           this.height = AppletGameContainer.this.getHeight();
/* 497 */           GL11.glViewport(0, 0, this.width, this.height);
/*     */           
/* 499 */           enterOrtho();
/*     */           
/* 501 */           Display.setFullscreen(false);
/*     */         } 
/* 503 */       } catch (LWJGLException e) {
/* 504 */         Log.error((Throwable)e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void runloop() throws Exception {
/* 515 */       while (this.running) {
/* 516 */         int delta = getDelta();
/*     */         
/* 518 */         updateAndRender(delta);
/*     */         
/* 520 */         updateFPS();
/* 521 */         Display.update();
/*     */       } 
/*     */       
/* 524 */       Display.destroy();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class ConsolePanel
/*     */     extends Panel
/*     */   {
/* 536 */     TextArea textArea = new TextArea();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ConsolePanel(Exception e) {
/* 544 */       setLayout(new BorderLayout());
/* 545 */       setBackground(Color.black);
/* 546 */       setForeground(Color.white);
/*     */       
/* 548 */       Font consoleFont = new Font("Arial", 1, 14);
/*     */       
/* 550 */       Label slickLabel = new Label("SLICK CONSOLE", 1);
/* 551 */       slickLabel.setFont(consoleFont);
/* 552 */       add(slickLabel, "First");
/*     */       
/* 554 */       StringWriter sw = new StringWriter();
/* 555 */       e.printStackTrace(new PrintWriter(sw));
/*     */       
/* 557 */       this.textArea.setText(sw.toString());
/* 558 */       this.textArea.setEditable(false);
/* 559 */       add(this.textArea, "Center");
/*     */ 
/*     */       
/* 562 */       add(new Panel(), "Before");
/* 563 */       add(new Panel(), "After");
/*     */       
/* 565 */       Panel bottomPanel = new Panel();
/* 566 */       bottomPanel.setLayout(new GridLayout(0, 1));
/* 567 */       Label infoLabel1 = new Label("An error occured while running the applet.", 1);
/* 568 */       Label infoLabel2 = new Label("Plese contact support to resolve this issue.", 1);
/* 569 */       infoLabel1.setFont(consoleFont);
/* 570 */       infoLabel2.setFont(consoleFont);
/* 571 */       bottomPanel.add(infoLabel1);
/* 572 */       bottomPanel.add(infoLabel2);
/* 573 */       add(bottomPanel, "Last");
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\AppletGameContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */