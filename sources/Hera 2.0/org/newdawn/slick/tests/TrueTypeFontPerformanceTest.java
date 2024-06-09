/*     */ package org.newdawn.slick.tests;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.util.ArrayList;
/*     */ import org.newdawn.slick.AppGameContainer;
/*     */ import org.newdawn.slick.BasicGame;
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.Font;
/*     */ import org.newdawn.slick.Game;
/*     */ import org.newdawn.slick.GameContainer;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.SlickException;
/*     */ import org.newdawn.slick.TrueTypeFont;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TrueTypeFontPerformanceTest
/*     */   extends BasicGame
/*     */ {
/*     */   private Font awtFont;
/*     */   private TrueTypeFont font;
/*  28 */   private String text = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Proin bibendum. Aliquam ac sapien a elit congue iaculis. Quisque et justo quis mi mattis euismod. Donec elementum, mi quis aliquet varius, nisi leo volutpat magna, quis ultricies eros augue at risus. Integer non magna at lorem sodales molestie. Integer diam nulla, ornare sit amet, mattis quis, euismod et, mauris. Proin eget tellus non nisl mattis laoreet. Nunc at nunc id elit pretium tempor. Duis vulputate, nibh eget rhoncus eleifend, tellus lectus sollicitudin mi, rhoncus tincidunt nisi massa vitae ipsum. Praesent tellus diam, luctus ut, eleifend nec, auctor et, orci. Praesent eu elit. Pellentesque ante orci, volutpat placerat, ornare eget, cursus sit amet, eros. Duis pede sapien, euismod a, volutpat pellentesque, convallis eu, mauris. Nunc eros. Ut eu risus et felis laoreet viverra. Curabitur a metus.";
/*     */   
/*  30 */   private ArrayList lines = new ArrayList();
/*     */ 
/*     */   
/*     */   private boolean visible = true;
/*     */ 
/*     */ 
/*     */   
/*     */   public TrueTypeFontPerformanceTest() {
/*  38 */     super("Font Performance Test");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GameContainer container) throws SlickException {
/*  45 */     this.awtFont = new Font("Verdana", 0, 16);
/*  46 */     this.font = new TrueTypeFont(this.awtFont, false);
/*     */     
/*  48 */     for (int j = 0; j < 2; j++) {
/*  49 */       int lineLen = 90; int i;
/*  50 */       for (i = 0; i < this.text.length(); i += lineLen) {
/*  51 */         if (i + lineLen > this.text.length()) {
/*  52 */           lineLen = this.text.length() - i;
/*     */         }
/*     */         
/*  55 */         this.lines.add(this.text.substring(i, i + lineLen));
/*     */       } 
/*  57 */       this.lines.add("");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(GameContainer container, Graphics g) {
/*  66 */     g.setFont((Font)this.font);
/*     */     
/*  68 */     if (this.visible) {
/*  69 */       for (int i = 0; i < this.lines.size(); i++) {
/*  70 */         this.font.drawString(10.0F, (50 + i * 20), this.lines.get(i), (i > 10) ? Color.red : Color.green);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
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
/*     */ 
/*     */   
/*     */   public void keyPressed(int key, char c) {
/*  88 */     if (key == 1) {
/*  89 */       System.exit(0);
/*     */     }
/*  91 */     if (key == 57) {
/*  92 */       this.visible = !this.visible;
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
/* 104 */       AppGameContainer container = new AppGameContainer((Game)new TrueTypeFontPerformanceTest());
/*     */       
/* 106 */       container.setDisplayMode(800, 600, false);
/* 107 */       container.start();
/* 108 */     } catch (SlickException e) {
/* 109 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\TrueTypeFontPerformanceTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */