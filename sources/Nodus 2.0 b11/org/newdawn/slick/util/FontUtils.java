/*   1:    */ package org.newdawn.slick.util;
/*   2:    */ 
/*   3:    */ import org.newdawn.slick.Color;
/*   4:    */ import org.newdawn.slick.Font;
/*   5:    */ 
/*   6:    */ public class FontUtils
/*   7:    */ {
/*   8:    */   public static void drawLeft(Font font, String s, int x, int y)
/*   9:    */   {
/*  10: 39 */     drawString(font, s, 1, x, y, 0, Color.white);
/*  11:    */   }
/*  12:    */   
/*  13:    */   public static void drawCenter(Font font, String s, int x, int y, int width)
/*  14:    */   {
/*  15: 52 */     drawString(font, s, 2, x, y, width, Color.white);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public static void drawCenter(Font font, String s, int x, int y, int width, Color color)
/*  19:    */   {
/*  20: 67 */     drawString(font, s, 2, x, y, width, color);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public static void drawRight(Font font, String s, int x, int y, int width)
/*  24:    */   {
/*  25: 80 */     drawString(font, s, 3, x, y, width, Color.white);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public static void drawRight(Font font, String s, int x, int y, int width, Color color)
/*  29:    */   {
/*  30: 95 */     drawString(font, s, 3, x, y, width, color);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public static final int drawString(Font font, String s, int alignment, int x, int y, int width, Color color)
/*  34:    */   {
/*  35:113 */     int resultingXCoordinate = 0;
/*  36:114 */     if (alignment == 1)
/*  37:    */     {
/*  38:115 */       font.drawString(x, y, s, color);
/*  39:    */     }
/*  40:116 */     else if (alignment == 2)
/*  41:    */     {
/*  42:117 */       font.drawString(x + width / 2 - font.getWidth(s) / 2, y, s, 
/*  43:118 */         color);
/*  44:    */     }
/*  45:119 */     else if (alignment == 3)
/*  46:    */     {
/*  47:120 */       font.drawString(x + width - font.getWidth(s), y, s, color);
/*  48:    */     }
/*  49:121 */     else if (alignment == 4)
/*  50:    */     {
/*  51:123 */       int leftWidth = width - font.getWidth(s);
/*  52:124 */       if (leftWidth <= 0) {
/*  53:126 */         font.drawString(x, y, s, color);
/*  54:    */       }
/*  55:129 */       return drawJustifiedSpaceSeparatedSubstrings(font, s, x, 
/*  56:130 */         y, calculateWidthOfJustifiedSpaceInPixels(font, 
/*  57:131 */         s, leftWidth));
/*  58:    */     }
/*  59:134 */     return resultingXCoordinate;
/*  60:    */   }
/*  61:    */   
/*  62:    */   private static int calculateWidthOfJustifiedSpaceInPixels(Font font, String s, int leftWidth)
/*  63:    */   {
/*  64:154 */     int space = 0;
/*  65:155 */     int curpos = 0;
/*  66:158 */     while (curpos < s.length()) {
/*  67:159 */       if (s.charAt(curpos++) == ' ') {
/*  68:160 */         space++;
/*  69:    */       }
/*  70:    */     }
/*  71:164 */     if (space > 0) {
/*  72:167 */       space = (leftWidth + font.getWidth(" ") * space) / space;
/*  73:    */     }
/*  74:169 */     return space;
/*  75:    */   }
/*  76:    */   
/*  77:    */   private static int drawJustifiedSpaceSeparatedSubstrings(Font font, String s, int x, int y, int justifiedSpaceWidth)
/*  78:    */   {
/*  79:200 */     int curpos = 0;
/*  80:201 */     int endpos = 0;
/*  81:202 */     int resultingXCoordinate = x;
/*  82:203 */     while (curpos < s.length())
/*  83:    */     {
/*  84:204 */       endpos = s.indexOf(' ', curpos);
/*  85:205 */       if (endpos == -1) {
/*  86:206 */         endpos = s.length();
/*  87:    */       }
/*  88:208 */       String substring = s.substring(curpos, endpos);
/*  89:    */       
/*  90:210 */       font.drawString(resultingXCoordinate, y, substring);
/*  91:    */       
/*  92:    */ 
/*  93:213 */       resultingXCoordinate = resultingXCoordinate + (font.getWidth(substring) + justifiedSpaceWidth);
/*  94:    */       
/*  95:215 */       curpos = endpos + 1;
/*  96:    */     }
/*  97:218 */     return resultingXCoordinate;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public class Alignment
/* 101:    */   {
/* 102:    */     public static final int LEFT = 1;
/* 103:    */     public static final int CENTER = 2;
/* 104:    */     public static final int RIGHT = 3;
/* 105:    */     public static final int JUSTIFY = 4;
/* 106:    */     
/* 107:    */     public Alignment() {}
/* 108:    */   }
/* 109:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.util.FontUtils
 * JD-Core Version:    0.7.0.1
 */