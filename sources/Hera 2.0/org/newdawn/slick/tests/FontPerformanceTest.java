/*    */ package org.newdawn.slick.tests;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import org.newdawn.slick.AngelCodeFont;
/*    */ import org.newdawn.slick.AppGameContainer;
/*    */ import org.newdawn.slick.BasicGame;
/*    */ import org.newdawn.slick.Color;
/*    */ import org.newdawn.slick.Font;
/*    */ import org.newdawn.slick.Game;
/*    */ import org.newdawn.slick.GameContainer;
/*    */ import org.newdawn.slick.Graphics;
/*    */ import org.newdawn.slick.SlickException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FontPerformanceTest
/*    */   extends BasicGame
/*    */ {
/*    */   private AngelCodeFont font;
/* 24 */   private String text = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Proin bibendum. Aliquam ac sapien a elit congue iaculis. Quisque et justo quis mi mattis euismod. Donec elementum, mi quis aliquet varius, nisi leo volutpat magna, quis ultricies eros augue at risus. Integer non magna at lorem sodales molestie. Integer diam nulla, ornare sit amet, mattis quis, euismod et, mauris. Proin eget tellus non nisl mattis laoreet. Nunc at nunc id elit pretium tempor. Duis vulputate, nibh eget rhoncus eleifend, tellus lectus sollicitudin mi, rhoncus tincidunt nisi massa vitae ipsum. Praesent tellus diam, luctus ut, eleifend nec, auctor et, orci. Praesent eu elit. Pellentesque ante orci, volutpat placerat, ornare eget, cursus sit amet, eros. Duis pede sapien, euismod a, volutpat pellentesque, convallis eu, mauris. Nunc eros. Ut eu risus et felis laoreet viverra. Curabitur a metus.";
/*    */   
/* 26 */   private ArrayList lines = new ArrayList();
/*    */ 
/*    */   
/*    */   private boolean visible = true;
/*    */ 
/*    */ 
/*    */   
/*    */   public FontPerformanceTest() {
/* 34 */     super("Font Performance Test");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(GameContainer container) throws SlickException {
/* 41 */     this.font = new AngelCodeFont("testdata/perffont.fnt", "testdata/perffont.png");
/*    */     
/* 43 */     for (int j = 0; j < 2; j++) {
/* 44 */       int lineLen = 90; int i;
/* 45 */       for (i = 0; i < this.text.length(); i += lineLen) {
/* 46 */         if (i + lineLen > this.text.length()) {
/* 47 */           lineLen = this.text.length() - i;
/*    */         }
/*    */         
/* 50 */         this.lines.add(this.text.substring(i, i + lineLen));
/*    */       } 
/* 52 */       this.lines.add("");
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(GameContainer container, Graphics g) {
/* 60 */     g.setFont((Font)this.font);
/*    */     
/* 62 */     if (this.visible) {
/* 63 */       for (int i = 0; i < this.lines.size(); i++) {
/* 64 */         this.font.drawString(10.0F, (50 + i * 20), this.lines.get(i), (i > 10) ? Color.red : Color.green);
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update(GameContainer container, int delta) throws SlickException {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void keyPressed(int key, char c) {
/* 79 */     if (key == 1) {
/* 80 */       System.exit(0);
/*    */     }
/* 82 */     if (key == 57) {
/* 83 */       this.visible = !this.visible;
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
/* 94 */       AppGameContainer container = new AppGameContainer((Game)new FontPerformanceTest());
/* 95 */       container.setDisplayMode(800, 600, false);
/* 96 */       container.start();
/* 97 */     } catch (SlickException e) {
/* 98 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\FontPerformanceTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */