/*     */ package org.newdawn.slick.tests;
/*     */ 
/*     */ import org.newdawn.slick.AppGameContainer;
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.Game;
/*     */ import org.newdawn.slick.GameContainer;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.Image;
/*     */ import org.newdawn.slick.SlickException;
/*     */ import org.newdawn.slick.state.BasicGameState;
/*     */ import org.newdawn.slick.state.GameState;
/*     */ import org.newdawn.slick.state.StateBasedGame;
/*     */ import org.newdawn.slick.state.transition.BlobbyTransition;
/*     */ import org.newdawn.slick.state.transition.FadeInTransition;
/*     */ import org.newdawn.slick.state.transition.FadeOutTransition;
/*     */ import org.newdawn.slick.state.transition.HorizontalSplitTransition;
/*     */ import org.newdawn.slick.state.transition.RotateTransition;
/*     */ import org.newdawn.slick.state.transition.SelectTransition;
/*     */ import org.newdawn.slick.state.transition.Transition;
/*     */ import org.newdawn.slick.state.transition.VerticalSplitTransition;
/*     */ import org.newdawn.slick.util.Log;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TransitionTest
/*     */   extends StateBasedGame
/*     */ {
/*  29 */   private Class[][] transitions = new Class[][] { { null, VerticalSplitTransition.class }, { FadeOutTransition.class, FadeInTransition.class }, { null, RotateTransition.class }, { null, HorizontalSplitTransition.class }, { null, BlobbyTransition.class }, { null, SelectTransition.class } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int index;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TransitionTest() {
/*  44 */     super("Transition Test - Hit Space To Transition");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initStatesList(GameContainer container) throws SlickException {
/*  51 */     addState((GameState)new ImageState(0, "testdata/wallpaper/paper1.png", 1));
/*  52 */     addState((GameState)new ImageState(1, "testdata/wallpaper/paper2.png", 2));
/*  53 */     addState((GameState)new ImageState(2, "testdata/bigimage.tga", 0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Transition[] getNextTransitionPair() {
/*  62 */     Transition[] pair = new Transition[2];
/*     */     
/*     */     try {
/*  65 */       if (this.transitions[this.index][0] != null) {
/*  66 */         pair[0] = this.transitions[this.index][0].newInstance();
/*     */       }
/*  68 */       if (this.transitions[this.index][1] != null) {
/*  69 */         pair[1] = this.transitions[this.index][1].newInstance();
/*     */       }
/*  71 */     } catch (Throwable e) {
/*  72 */       Log.error(e);
/*     */     } 
/*     */     
/*  75 */     this.index++;
/*  76 */     if (this.index >= this.transitions.length) {
/*  77 */       this.index = 0;
/*     */     }
/*     */     
/*  80 */     return pair;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class ImageState
/*     */     extends BasicGameState
/*     */   {
/*     */     private int id;
/*     */ 
/*     */ 
/*     */     
/*     */     private int next;
/*     */ 
/*     */ 
/*     */     
/*     */     private String ref;
/*     */ 
/*     */ 
/*     */     
/*     */     private Image image;
/*     */ 
/*     */ 
/*     */     
/*     */     public ImageState(int id, String ref, int next) {
/* 106 */       this.ref = ref;
/* 107 */       this.id = id;
/* 108 */       this.next = next;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getID() {
/* 115 */       return this.id;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void init(GameContainer container, StateBasedGame game) throws SlickException {
/* 122 */       this.image = new Image(this.ref);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
/* 129 */       this.image.draw(0.0F, 0.0F, 800.0F, 600.0F);
/* 130 */       g.setColor(Color.red);
/* 131 */       g.fillRect(-50.0F, 200.0F, 50.0F, 50.0F);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
/* 138 */       if (container.getInput().isKeyPressed(57)) {
/* 139 */         Transition[] pair = TransitionTest.this.getNextTransitionPair();
/* 140 */         game.enterState(this.next, pair[0], pair[1]);
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
/*     */   public static void main(String[] argv) {
/*     */     try {
/* 153 */       AppGameContainer container = new AppGameContainer((Game)new TransitionTest());
/*     */       
/* 155 */       container.setDisplayMode(800, 600, false);
/* 156 */       container.start();
/* 157 */     } catch (SlickException e) {
/* 158 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\TransitionTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */