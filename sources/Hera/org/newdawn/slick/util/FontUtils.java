/*     */ package org.newdawn.slick.util;
/*     */ 
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.Font;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FontUtils
/*     */ {
/*     */   public class Alignment
/*     */   {
/*     */     public static final int LEFT = 1;
/*     */     public static final int CENTER = 2;
/*     */     public static final int RIGHT = 3;
/*     */     public static final int JUSTIFY = 4;
/*     */   }
/*     */   
/*     */   public static void drawLeft(Font font, String s, int x, int y) {
/*  39 */     drawString(font, s, 1, x, y, 0, Color.white);
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
/*     */   
/*     */   public static void drawCenter(Font font, String s, int x, int y, int width) {
/*  52 */     drawString(font, s, 2, x, y, width, Color.white);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawCenter(Font font, String s, int x, int y, int width, Color color) {
/*  67 */     drawString(font, s, 2, x, y, width, color);
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
/*     */   
/*     */   public static void drawRight(Font font, String s, int x, int y, int width) {
/*  80 */     drawString(font, s, 3, x, y, width, Color.white);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawRight(Font font, String s, int x, int y, int width, Color color) {
/*  95 */     drawString(font, s, 3, x, y, width, color);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int drawString(Font font, String s, int alignment, int x, int y, int width, Color color) {
/* 113 */     int resultingXCoordinate = 0;
/* 114 */     if (alignment == 1) {
/* 115 */       font.drawString(x, y, s, color);
/* 116 */     } else if (alignment == 2) {
/* 117 */       font.drawString((x + width / 2 - font.getWidth(s) / 2), y, s, color);
/*     */     }
/* 119 */     else if (alignment == 3) {
/* 120 */       font.drawString((x + width - font.getWidth(s)), y, s, color);
/* 121 */     } else if (alignment == 4) {
/*     */       
/* 123 */       int leftWidth = width - font.getWidth(s);
/* 124 */       if (leftWidth <= 0)
/*     */       {
/* 126 */         font.drawString(x, y, s, color);
/*     */       }
/*     */       
/* 129 */       return drawJustifiedSpaceSeparatedSubstrings(font, s, x, y, calculateWidthOfJustifiedSpaceInPixels(font, s, leftWidth));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 134 */     return resultingXCoordinate;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int calculateWidthOfJustifiedSpaceInPixels(Font font, String s, int leftWidth) {
/* 154 */     int space = 0;
/* 155 */     int curpos = 0;
/*     */ 
/*     */     
/* 158 */     while (curpos < s.length()) {
/* 159 */       if (s.charAt(curpos++) == ' ') {
/* 160 */         space++;
/*     */       }
/*     */     } 
/*     */     
/* 164 */     if (space > 0)
/*     */     {
/*     */       
/* 167 */       space = (leftWidth + font.getWidth(" ") * space) / space;
/*     */     }
/* 169 */     return space;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int drawJustifiedSpaceSeparatedSubstrings(Font font, String s, int x, int y, int justifiedSpaceWidth) {
/* 200 */     int curpos = 0;
/* 201 */     int endpos = 0;
/* 202 */     int resultingXCoordinate = x;
/* 203 */     while (curpos < s.length()) {
/* 204 */       endpos = s.indexOf(' ', curpos);
/* 205 */       if (endpos == -1) {
/* 206 */         endpos = s.length();
/*     */       }
/* 208 */       String substring = s.substring(curpos, endpos);
/*     */       
/* 210 */       font.drawString(resultingXCoordinate, y, substring);
/*     */       
/* 212 */       resultingXCoordinate += font.getWidth(substring) + justifiedSpaceWidth;
/*     */ 
/*     */       
/* 215 */       curpos = endpos + 1;
/*     */     } 
/*     */     
/* 218 */     return resultingXCoordinate;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slic\\util\FontUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */