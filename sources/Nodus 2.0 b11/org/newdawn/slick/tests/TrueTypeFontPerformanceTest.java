/*   1:    */ package org.newdawn.slick.tests;
/*   2:    */ 
/*   3:    */ import java.awt.Font;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import org.newdawn.slick.AppGameContainer;
/*   6:    */ import org.newdawn.slick.BasicGame;
/*   7:    */ import org.newdawn.slick.Color;
/*   8:    */ import org.newdawn.slick.GameContainer;
/*   9:    */ import org.newdawn.slick.Graphics;
/*  10:    */ import org.newdawn.slick.SlickException;
/*  11:    */ import org.newdawn.slick.TrueTypeFont;
/*  12:    */ 
/*  13:    */ public class TrueTypeFontPerformanceTest
/*  14:    */   extends BasicGame
/*  15:    */ {
/*  16:    */   private Font awtFont;
/*  17:    */   private TrueTypeFont font;
/*  18: 28 */   private String text = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Proin bibendum. Aliquam ac sapien a elit congue iaculis. Quisque et justo quis mi mattis euismod. Donec elementum, mi quis aliquet varius, nisi leo volutpat magna, quis ultricies eros augue at risus. Integer non magna at lorem sodales molestie. Integer diam nulla, ornare sit amet, mattis quis, euismod et, mauris. Proin eget tellus non nisl mattis laoreet. Nunc at nunc id elit pretium tempor. Duis vulputate, nibh eget rhoncus eleifend, tellus lectus sollicitudin mi, rhoncus tincidunt nisi massa vitae ipsum. Praesent tellus diam, luctus ut, eleifend nec, auctor et, orci. Praesent eu elit. Pellentesque ante orci, volutpat placerat, ornare eget, cursus sit amet, eros. Duis pede sapien, euismod a, volutpat pellentesque, convallis eu, mauris. Nunc eros. Ut eu risus et felis laoreet viverra. Curabitur a metus.";
/*  19: 30 */   private ArrayList lines = new ArrayList();
/*  20: 32 */   private boolean visible = true;
/*  21:    */   
/*  22:    */   public TrueTypeFontPerformanceTest()
/*  23:    */   {
/*  24: 38 */     super("Font Performance Test");
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void init(GameContainer container)
/*  28:    */     throws SlickException
/*  29:    */   {
/*  30: 45 */     this.awtFont = new Font("Verdana", 0, 16);
/*  31: 46 */     this.font = new TrueTypeFont(this.awtFont, false);
/*  32: 48 */     for (int j = 0; j < 2; j++)
/*  33:    */     {
/*  34: 49 */       int lineLen = 90;
/*  35: 50 */       for (int i = 0; i < this.text.length(); i += lineLen)
/*  36:    */       {
/*  37: 51 */         if (i + lineLen > this.text.length()) {
/*  38: 52 */           lineLen = this.text.length() - i;
/*  39:    */         }
/*  40: 55 */         this.lines.add(this.text.substring(i, i + lineLen));
/*  41:    */       }
/*  42: 57 */       this.lines.add("");
/*  43:    */     }
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void render(GameContainer container, Graphics g)
/*  47:    */   {
/*  48: 66 */     g.setFont(this.font);
/*  49: 68 */     if (this.visible) {
/*  50: 69 */       for (int i = 0; i < this.lines.size(); i++) {
/*  51: 70 */         this.font.drawString(10.0F, 50 + i * 20, (String)this.lines.get(i), 
/*  52: 71 */           i > 10 ? Color.red : Color.green);
/*  53:    */       }
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void update(GameContainer container, int delta)
/*  58:    */     throws SlickException
/*  59:    */   {}
/*  60:    */   
/*  61:    */   public void keyPressed(int key, char c)
/*  62:    */   {
/*  63: 88 */     if (key == 1) {
/*  64: 89 */       System.exit(0);
/*  65:    */     }
/*  66: 91 */     if (key == 57) {
/*  67: 92 */       this.visible = (!this.visible);
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   public static void main(String[] argv)
/*  72:    */   {
/*  73:    */     try
/*  74:    */     {
/*  75:104 */       AppGameContainer container = new AppGameContainer(
/*  76:105 */         new TrueTypeFontPerformanceTest());
/*  77:106 */       container.setDisplayMode(800, 600, false);
/*  78:107 */       container.start();
/*  79:    */     }
/*  80:    */     catch (SlickException e)
/*  81:    */     {
/*  82:109 */       e.printStackTrace();
/*  83:    */     }
/*  84:    */   }
/*  85:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.TrueTypeFontPerformanceTest
 * JD-Core Version:    0.7.0.1
 */