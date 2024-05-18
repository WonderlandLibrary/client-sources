/*     */ package org.newdawn.slick.tests;
/*     */ 
/*     */ import org.newdawn.slick.AppGameContainer;
/*     */ import org.newdawn.slick.BasicGame;
/*     */ import org.newdawn.slick.Game;
/*     */ import org.newdawn.slick.GameContainer;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.SavedState;
/*     */ import org.newdawn.slick.SlickException;
/*     */ import org.newdawn.slick.gui.AbstractComponent;
/*     */ import org.newdawn.slick.gui.ComponentListener;
/*     */ import org.newdawn.slick.gui.GUIContext;
/*     */ import org.newdawn.slick.gui.TextField;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SavedStateTest
/*     */   extends BasicGame
/*     */   implements ComponentListener
/*     */ {
/*     */   private TextField name;
/*     */   private TextField age;
/*  25 */   private String nameValue = "none";
/*     */   
/*  27 */   private int ageValue = 0;
/*     */   
/*     */   private SavedState state;
/*     */   
/*  31 */   private String message = "Enter a name and age to store";
/*     */   
/*     */   private static AppGameContainer container;
/*     */ 
/*     */   
/*     */   public SavedStateTest() {
/*  37 */     super("Saved State Test");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GameContainer container) throws SlickException {
/*  44 */     this.state = new SavedState("testdata");
/*  45 */     this.nameValue = this.state.getString("name", "DefaultName");
/*  46 */     this.ageValue = (int)this.state.getNumber("age", 64.0D);
/*     */     
/*  48 */     this.name = new TextField((GUIContext)container, container.getDefaultFont(), 100, 100, 300, 20, this);
/*  49 */     this.age = new TextField((GUIContext)container, container.getDefaultFont(), 100, 150, 201, 20, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(GameContainer container, Graphics g) {
/*  56 */     this.name.render((GUIContext)container, g);
/*  57 */     this.age.render((GUIContext)container, g);
/*     */     
/*  59 */     container.getDefaultFont().drawString(100.0F, 300.0F, "Stored Name: " + this.nameValue);
/*  60 */     container.getDefaultFont().drawString(100.0F, 350.0F, "Stored Age: " + this.ageValue);
/*  61 */     container.getDefaultFont().drawString(200.0F, 500.0F, this.message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(GameContainer container, int delta) throws SlickException {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyPressed(int key, char c) {
/*  74 */     if (key == 1) {
/*  75 */       System.exit(0);
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
/*     */   public static void main(String[] argv) {
/*     */     try {
/*  89 */       container = new AppGameContainer((Game)new SavedStateTest());
/*  90 */       container.setDisplayMode(800, 600, false);
/*  91 */       container.start();
/*  92 */     } catch (SlickException e) {
/*  93 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void componentActivated(AbstractComponent source) {
/* 101 */     if (source == this.name) {
/* 102 */       this.nameValue = this.name.getText();
/* 103 */       this.state.setString("name", this.nameValue);
/*     */     } 
/* 105 */     if (source == this.age) {
/*     */       try {
/* 107 */         this.ageValue = Integer.parseInt(this.age.getText());
/* 108 */         this.state.setNumber("age", this.ageValue);
/* 109 */       } catch (NumberFormatException e) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 115 */       this.state.save();
/* 116 */     } catch (Exception e) {
/* 117 */       this.message = System.currentTimeMillis() + " : Failed to save state";
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\SavedStateTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */