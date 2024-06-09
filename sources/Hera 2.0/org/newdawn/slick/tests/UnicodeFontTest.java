/*    */ package org.newdawn.slick.tests;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.io.IOException;
/*    */ import org.newdawn.slick.AppGameContainer;
/*    */ import org.newdawn.slick.BasicGame;
/*    */ import org.newdawn.slick.Color;
/*    */ import org.newdawn.slick.Game;
/*    */ import org.newdawn.slick.GameContainer;
/*    */ import org.newdawn.slick.Graphics;
/*    */ import org.newdawn.slick.Input;
/*    */ import org.newdawn.slick.SlickException;
/*    */ import org.newdawn.slick.UnicodeFont;
/*    */ import org.newdawn.slick.font.effects.ColorEffect;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UnicodeFontTest
/*    */   extends BasicGame
/*    */ {
/*    */   private UnicodeFont unicodeFont;
/*    */   
/*    */   public UnicodeFontTest() {
/* 30 */     super("Font Test");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(GameContainer container) throws SlickException {
/* 37 */     container.setShowFPS(false);
/*    */ 
/*    */     
/* 40 */     this.unicodeFont = new UnicodeFont("c:/windows/fonts/arial.ttf", 48, false, false);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 45 */     this.unicodeFont.getEffects().add(new ColorEffect(Color.white));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 53 */     container.getGraphics().setBackground(Color.darkGray);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(GameContainer container, Graphics g) {
/* 60 */     g.setColor(Color.white);
/*    */     
/* 62 */     String text = "This is UnicodeFont!\nIt rockz. Kerning: T,";
/* 63 */     this.unicodeFont.drawString(10.0F, 33.0F, text);
/*    */     
/* 65 */     g.setColor(Color.red);
/* 66 */     g.drawRect(10.0F, 33.0F, this.unicodeFont.getWidth(text), this.unicodeFont.getLineHeight());
/* 67 */     g.setColor(Color.blue);
/* 68 */     int yOffset = this.unicodeFont.getYOffset(text);
/* 69 */     g.drawRect(10.0F, (33 + yOffset), this.unicodeFont.getWidth(text), (this.unicodeFont.getHeight(text) - yOffset));
/*    */ 
/*    */ 
/*    */     
/* 73 */     this.unicodeFont.addGlyphs("~!@!#!#$%___--");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update(GameContainer container, int delta) throws SlickException {
/* 82 */     this.unicodeFont.loadGlyphs(1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void main(String[] args) throws SlickException, IOException {
/* 93 */     Input.disableControllers();
/* 94 */     AppGameContainer container = new AppGameContainer((Game)new UnicodeFontTest());
/* 95 */     container.setDisplayMode(512, 600, false);
/* 96 */     container.setTargetFrameRate(20);
/* 97 */     container.start();
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\UnicodeFontTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */