/*  1:   */ package org.newdawn.slick.tests;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import org.newdawn.slick.AngelCodeFont;
/*  5:   */ import org.newdawn.slick.AppGameContainer;
/*  6:   */ import org.newdawn.slick.BasicGame;
/*  7:   */ import org.newdawn.slick.Color;
/*  8:   */ import org.newdawn.slick.GameContainer;
/*  9:   */ import org.newdawn.slick.Graphics;
/* 10:   */ import org.newdawn.slick.SlickException;
/* 11:   */ 
/* 12:   */ public class FontPerformanceTest
/* 13:   */   extends BasicGame
/* 14:   */ {
/* 15:   */   private AngelCodeFont font;
/* 16:24 */   private String text = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Proin bibendum. Aliquam ac sapien a elit congue iaculis. Quisque et justo quis mi mattis euismod. Donec elementum, mi quis aliquet varius, nisi leo volutpat magna, quis ultricies eros augue at risus. Integer non magna at lorem sodales molestie. Integer diam nulla, ornare sit amet, mattis quis, euismod et, mauris. Proin eget tellus non nisl mattis laoreet. Nunc at nunc id elit pretium tempor. Duis vulputate, nibh eget rhoncus eleifend, tellus lectus sollicitudin mi, rhoncus tincidunt nisi massa vitae ipsum. Praesent tellus diam, luctus ut, eleifend nec, auctor et, orci. Praesent eu elit. Pellentesque ante orci, volutpat placerat, ornare eget, cursus sit amet, eros. Duis pede sapien, euismod a, volutpat pellentesque, convallis eu, mauris. Nunc eros. Ut eu risus et felis laoreet viverra. Curabitur a metus.";
/* 17:26 */   private ArrayList lines = new ArrayList();
/* 18:28 */   private boolean visible = true;
/* 19:   */   
/* 20:   */   public FontPerformanceTest()
/* 21:   */   {
/* 22:34 */     super("Font Performance Test");
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void init(GameContainer container)
/* 26:   */     throws SlickException
/* 27:   */   {
/* 28:41 */     this.font = new AngelCodeFont("testdata/perffont.fnt", "testdata/perffont.png");
/* 29:43 */     for (int j = 0; j < 2; j++)
/* 30:   */     {
/* 31:44 */       int lineLen = 90;
/* 32:45 */       for (int i = 0; i < this.text.length(); i += lineLen)
/* 33:   */       {
/* 34:46 */         if (i + lineLen > this.text.length()) {
/* 35:47 */           lineLen = this.text.length() - i;
/* 36:   */         }
/* 37:50 */         this.lines.add(this.text.substring(i, i + lineLen));
/* 38:   */       }
/* 39:52 */       this.lines.add("");
/* 40:   */     }
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void render(GameContainer container, Graphics g)
/* 44:   */   {
/* 45:60 */     g.setFont(this.font);
/* 46:62 */     if (this.visible) {
/* 47:63 */       for (int i = 0; i < this.lines.size(); i++) {
/* 48:64 */         this.font.drawString(10.0F, 50 + i * 20, (String)this.lines.get(i), i > 10 ? Color.red : Color.green);
/* 49:   */       }
/* 50:   */     }
/* 51:   */   }
/* 52:   */   
/* 53:   */   public void update(GameContainer container, int delta)
/* 54:   */     throws SlickException
/* 55:   */   {}
/* 56:   */   
/* 57:   */   public void keyPressed(int key, char c)
/* 58:   */   {
/* 59:79 */     if (key == 1) {
/* 60:80 */       System.exit(0);
/* 61:   */     }
/* 62:82 */     if (key == 57) {
/* 63:83 */       this.visible = (!this.visible);
/* 64:   */     }
/* 65:   */   }
/* 66:   */   
/* 67:   */   public static void main(String[] argv)
/* 68:   */   {
/* 69:   */     try
/* 70:   */     {
/* 71:94 */       AppGameContainer container = new AppGameContainer(new FontPerformanceTest());
/* 72:95 */       container.setDisplayMode(800, 600, false);
/* 73:96 */       container.start();
/* 74:   */     }
/* 75:   */     catch (SlickException e)
/* 76:   */     {
/* 77:98 */       e.printStackTrace();
/* 78:   */     }
/* 79:   */   }
/* 80:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.FontPerformanceTest
 * JD-Core Version:    0.7.0.1
 */