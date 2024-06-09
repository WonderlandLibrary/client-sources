/*     */ package org.newdawn.slick.tests;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import org.newdawn.slick.AppGameContainer;
/*     */ import org.newdawn.slick.BasicGame;
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.Game;
/*     */ import org.newdawn.slick.GameContainer;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.SlickException;
/*     */ import org.newdawn.slick.opengl.SlickCallable;
/*     */ import org.newdawn.slick.util.Log;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TestBox
/*     */   extends BasicGame
/*     */ {
/*  23 */   private ArrayList games = new ArrayList();
/*     */ 
/*     */   
/*     */   private BasicGame currentGame;
/*     */ 
/*     */   
/*     */   private int index;
/*     */   
/*     */   private AppGameContainer container;
/*     */ 
/*     */   
/*     */   public TestBox() {
/*  35 */     super("Test Box");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addGame(Class<?> game) {
/*  44 */     this.games.add(game);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void nextGame() {
/*  51 */     if (this.index == -1) {
/*     */       return;
/*     */     }
/*     */     
/*  55 */     this.index++;
/*  56 */     if (this.index >= this.games.size()) {
/*  57 */       this.index = 0;
/*     */     }
/*     */     
/*  60 */     startGame();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void startGame() {
/*     */     try {
/*  68 */       this.currentGame = ((Class<BasicGame>)this.games.get(this.index)).newInstance();
/*  69 */       this.container.getGraphics().setBackground(Color.black);
/*  70 */       this.currentGame.init((GameContainer)this.container);
/*  71 */       this.currentGame.render((GameContainer)this.container, this.container.getGraphics());
/*  72 */     } catch (Exception e) {
/*  73 */       Log.error(e);
/*     */     } 
/*     */     
/*  76 */     this.container.setTitle(this.currentGame.getTitle());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GameContainer c) throws SlickException {
/*  83 */     if (this.games.size() == 0) {
/*  84 */       this.currentGame = new BasicGame("NULL")
/*     */         {
/*     */           public void init(GameContainer container) throws SlickException {}
/*     */ 
/*     */           
/*     */           public void update(GameContainer container, int delta) throws SlickException {}
/*     */ 
/*     */           
/*     */           public void render(GameContainer container, Graphics g) throws SlickException {}
/*     */         };
/*  94 */       this.currentGame.init(c);
/*  95 */       this.index = -1;
/*     */     } else {
/*  97 */       this.index = 0;
/*  98 */       this.container = (AppGameContainer)c;
/*  99 */       startGame();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(GameContainer container, int delta) throws SlickException {
/* 107 */     this.currentGame.update(container, delta);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(GameContainer container, Graphics g) throws SlickException {
/* 114 */     SlickCallable.enterSafeBlock();
/* 115 */     this.currentGame.render(container, g);
/* 116 */     SlickCallable.leaveSafeBlock();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void controllerButtonPressed(int controller, int button) {
/* 123 */     this.currentGame.controllerButtonPressed(controller, button);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void controllerButtonReleased(int controller, int button) {
/* 130 */     this.currentGame.controllerButtonReleased(controller, button);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void controllerDownPressed(int controller) {
/* 137 */     this.currentGame.controllerDownPressed(controller);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void controllerDownReleased(int controller) {
/* 144 */     this.currentGame.controllerDownReleased(controller);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void controllerLeftPressed(int controller) {
/* 151 */     this.currentGame.controllerLeftPressed(controller);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void controllerLeftReleased(int controller) {
/* 158 */     this.currentGame.controllerLeftReleased(controller);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void controllerRightPressed(int controller) {
/* 165 */     this.currentGame.controllerRightPressed(controller);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void controllerRightReleased(int controller) {
/* 172 */     this.currentGame.controllerRightReleased(controller);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void controllerUpPressed(int controller) {
/* 179 */     this.currentGame.controllerUpPressed(controller);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void controllerUpReleased(int controller) {
/* 186 */     this.currentGame.controllerUpReleased(controller);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyPressed(int key, char c) {
/* 193 */     this.currentGame.keyPressed(key, c);
/*     */     
/* 195 */     if (key == 28) {
/* 196 */       nextGame();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyReleased(int key, char c) {
/* 204 */     this.currentGame.keyReleased(key, c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseMoved(int oldx, int oldy, int newx, int newy) {
/* 211 */     this.currentGame.mouseMoved(oldx, oldy, newx, newy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mousePressed(int button, int x, int y) {
/* 218 */     this.currentGame.mousePressed(button, x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseReleased(int button, int x, int y) {
/* 225 */     this.currentGame.mouseReleased(button, x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseWheelMoved(int change) {
/* 232 */     this.currentGame.mouseWheelMoved(change);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] argv) {
/*     */     try {
/* 242 */       TestBox box = new TestBox();
/* 243 */       box.addGame(AnimationTest.class);
/* 244 */       box.addGame(AntiAliasTest.class);
/* 245 */       box.addGame(BigImageTest.class);
/* 246 */       box.addGame(ClipTest.class);
/* 247 */       box.addGame(DuplicateEmitterTest.class);
/* 248 */       box.addGame(FlashTest.class);
/* 249 */       box.addGame(FontPerformanceTest.class);
/* 250 */       box.addGame(FontTest.class);
/* 251 */       box.addGame(GeomTest.class);
/* 252 */       box.addGame(GradientTest.class);
/* 253 */       box.addGame(GraphicsTest.class);
/* 254 */       box.addGame(ImageBufferTest.class);
/* 255 */       box.addGame(ImageReadTest.class);
/* 256 */       box.addGame(ImageTest.class);
/* 257 */       box.addGame(KeyRepeatTest.class);
/* 258 */       box.addGame(MusicListenerTest.class);
/* 259 */       box.addGame(PackedSheetTest.class);
/* 260 */       box.addGame(PedigreeTest.class);
/* 261 */       box.addGame(PureFontTest.class);
/* 262 */       box.addGame(ShapeTest.class);
/* 263 */       box.addGame(SoundTest.class);
/* 264 */       box.addGame(SpriteSheetFontTest.class);
/* 265 */       box.addGame(TransparentColorTest.class);
/*     */       
/* 267 */       AppGameContainer container = new AppGameContainer((Game)box);
/* 268 */       container.setDisplayMode(800, 600, false);
/* 269 */       container.start();
/* 270 */     } catch (SlickException e) {
/* 271 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\TestBox.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */