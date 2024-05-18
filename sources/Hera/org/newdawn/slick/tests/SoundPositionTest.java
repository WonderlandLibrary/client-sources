/*    */ package org.newdawn.slick.tests;
/*    */ 
/*    */ import org.newdawn.slick.AppGameContainer;
/*    */ import org.newdawn.slick.BasicGame;
/*    */ import org.newdawn.slick.Color;
/*    */ import org.newdawn.slick.Game;
/*    */ import org.newdawn.slick.GameContainer;
/*    */ import org.newdawn.slick.Graphics;
/*    */ import org.newdawn.slick.Music;
/*    */ import org.newdawn.slick.SlickException;
/*    */ import org.newdawn.slick.openal.SoundStore;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SoundPositionTest
/*    */   extends BasicGame
/*    */ {
/*    */   private GameContainer myContainer;
/*    */   private Music music;
/* 25 */   private int[] engines = new int[3];
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SoundPositionTest() {
/* 31 */     super("Music Position Test");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(GameContainer container) throws SlickException {
/* 38 */     SoundStore.get().setMaxSources(32);
/*    */     
/* 40 */     this.myContainer = container;
/* 41 */     this.music = new Music("testdata/kirby.ogg", true);
/* 42 */     this.music.play();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(GameContainer container, Graphics g) {
/* 49 */     g.setColor(Color.white);
/* 50 */     g.drawString("Position: " + this.music.getPosition(), 100.0F, 100.0F);
/* 51 */     g.drawString("Space - Pause/Resume", 100.0F, 130.0F);
/* 52 */     g.drawString("Right Arrow - Advance 5 seconds", 100.0F, 145.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update(GameContainer container, int delta) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void keyPressed(int key, char c) {
/* 65 */     if (key == 57) {
/* 66 */       if (this.music.playing()) {
/* 67 */         this.music.pause();
/*    */       } else {
/* 69 */         this.music.resume();
/*    */       } 
/*    */     }
/* 72 */     if (key == 205) {
/* 73 */       this.music.setPosition(this.music.getPosition() + 5.0F);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void main(String[] argv) {
/*    */     try {
/* 84 */       AppGameContainer container = new AppGameContainer((Game)new SoundPositionTest());
/* 85 */       container.setDisplayMode(800, 600, false);
/* 86 */       container.start();
/* 87 */     } catch (SlickException e) {
/* 88 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\SoundPositionTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */