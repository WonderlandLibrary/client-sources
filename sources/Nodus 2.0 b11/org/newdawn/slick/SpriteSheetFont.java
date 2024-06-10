/*   1:    */ package org.newdawn.slick;
/*   2:    */ 
/*   3:    */ import java.io.UnsupportedEncodingException;
/*   4:    */ import org.newdawn.slick.util.Log;
/*   5:    */ 
/*   6:    */ public class SpriteSheetFont
/*   7:    */   implements Font
/*   8:    */ {
/*   9:    */   private SpriteSheet font;
/*  10:    */   private char startingCharacter;
/*  11:    */   private int charWidth;
/*  12:    */   private int charHeight;
/*  13:    */   private int horizontalCount;
/*  14:    */   private int numChars;
/*  15:    */   
/*  16:    */   public SpriteSheetFont(SpriteSheet font, char startingCharacter)
/*  17:    */   {
/*  18: 44 */     this.font = font;
/*  19: 45 */     this.startingCharacter = startingCharacter;
/*  20: 46 */     this.horizontalCount = font.getHorizontalCount();
/*  21: 47 */     int verticalCount = font.getVerticalCount();
/*  22: 48 */     this.charWidth = (font.getWidth() / this.horizontalCount);
/*  23: 49 */     this.charHeight = (font.getHeight() / verticalCount);
/*  24: 50 */     this.numChars = (this.horizontalCount * verticalCount);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void drawString(float x, float y, String text)
/*  28:    */   {
/*  29: 57 */     drawString(x, y, text, Color.white);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void drawString(float x, float y, String text, Color col)
/*  33:    */   {
/*  34: 64 */     drawString(x, y, text, col, 0, text.length() - 1);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void drawString(float x, float y, String text, Color col, int startIndex, int endIndex)
/*  38:    */   {
/*  39:    */     try
/*  40:    */     {
/*  41: 72 */       byte[] data = text.getBytes("US-ASCII");
/*  42: 73 */       for (int i = 0; i < data.length; i++)
/*  43:    */       {
/*  44: 74 */         int index = data[i] - this.startingCharacter;
/*  45: 75 */         if (index < this.numChars)
/*  46:    */         {
/*  47: 76 */           int xPos = index % this.horizontalCount;
/*  48: 77 */           int yPos = index / this.horizontalCount;
/*  49: 79 */           if ((i >= startIndex) || (i <= endIndex)) {
/*  50: 81 */             this.font.getSprite(xPos, yPos).draw(x + i * this.charWidth, y, col);
/*  51:    */           }
/*  52:    */         }
/*  53:    */       }
/*  54:    */     }
/*  55:    */     catch (UnsupportedEncodingException e)
/*  56:    */     {
/*  57: 87 */       Log.error(e);
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   public int getHeight(String text)
/*  62:    */   {
/*  63: 95 */     return this.charHeight;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getWidth(String text)
/*  67:    */   {
/*  68:102 */     return this.charWidth * text.length();
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int getLineHeight()
/*  72:    */   {
/*  73:109 */     return this.charHeight;
/*  74:    */   }
/*  75:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.SpriteSheetFont
 * JD-Core Version:    0.7.0.1
 */