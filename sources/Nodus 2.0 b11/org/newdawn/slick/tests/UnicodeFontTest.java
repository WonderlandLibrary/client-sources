/*  1:   */ package org.newdawn.slick.tests;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.util.List;
/*  5:   */ import org.newdawn.slick.AppGameContainer;
/*  6:   */ import org.newdawn.slick.BasicGame;
/*  7:   */ import org.newdawn.slick.GameContainer;
/*  8:   */ import org.newdawn.slick.Graphics;
/*  9:   */ import org.newdawn.slick.Input;
/* 10:   */ import org.newdawn.slick.SlickException;
/* 11:   */ import org.newdawn.slick.UnicodeFont;
/* 12:   */ import org.newdawn.slick.font.effects.ColorEffect;
/* 13:   */ 
/* 14:   */ public class UnicodeFontTest
/* 15:   */   extends BasicGame
/* 16:   */ {
/* 17:   */   private UnicodeFont unicodeFont;
/* 18:   */   
/* 19:   */   public UnicodeFontTest()
/* 20:   */   {
/* 21:30 */     super("Font Test");
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void init(GameContainer container)
/* 25:   */     throws SlickException
/* 26:   */   {
/* 27:37 */     container.setShowFPS(false);
/* 28:   */     
/* 29:   */ 
/* 30:40 */     this.unicodeFont = new UnicodeFont("c:/windows/fonts/arial.ttf", 48, false, false);
/* 31:   */     
/* 32:   */ 
/* 33:   */ 
/* 34:   */ 
/* 35:45 */     this.unicodeFont.getEffects().add(new ColorEffect(java.awt.Color.white));
/* 36:   */     
/* 37:   */ 
/* 38:   */ 
/* 39:   */ 
/* 40:   */ 
/* 41:   */ 
/* 42:   */ 
/* 43:53 */     container.getGraphics().setBackground(org.newdawn.slick.Color.darkGray);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void render(GameContainer container, Graphics g)
/* 47:   */   {
/* 48:60 */     g.setColor(org.newdawn.slick.Color.white);
/* 49:   */     
/* 50:62 */     String text = "This is UnicodeFont!\nIt rockz. Kerning: T,";
/* 51:63 */     this.unicodeFont.drawString(10.0F, 33.0F, text);
/* 52:   */     
/* 53:65 */     g.setColor(org.newdawn.slick.Color.red);
/* 54:66 */     g.drawRect(10.0F, 33.0F, this.unicodeFont.getWidth(text), this.unicodeFont.getLineHeight());
/* 55:67 */     g.setColor(org.newdawn.slick.Color.blue);
/* 56:68 */     int yOffset = this.unicodeFont.getYOffset(text);
/* 57:69 */     g.drawRect(10.0F, 33 + yOffset, this.unicodeFont.getWidth(text), this.unicodeFont.getHeight(text) - yOffset);
/* 58:   */     
/* 59:   */ 
/* 60:   */ 
/* 61:73 */     this.unicodeFont.addGlyphs("~!@!#!#$%___--");
/* 62:   */   }
/* 63:   */   
/* 64:   */   public void update(GameContainer container, int delta)
/* 65:   */     throws SlickException
/* 66:   */   {
/* 67:82 */     this.unicodeFont.loadGlyphs(1);
/* 68:   */   }
/* 69:   */   
/* 70:   */   public static void main(String[] args)
/* 71:   */     throws SlickException, IOException
/* 72:   */   {
/* 73:93 */     Input.disableControllers();
/* 74:94 */     AppGameContainer container = new AppGameContainer(new UnicodeFontTest());
/* 75:95 */     container.setDisplayMode(512, 600, false);
/* 76:96 */     container.setTargetFrameRate(20);
/* 77:97 */     container.start();
/* 78:   */   }
/* 79:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.UnicodeFontTest
 * JD-Core Version:    0.7.0.1
 */