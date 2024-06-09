/*    */ package net.minecraft.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChatAllowedCharacters
/*    */ {
/*  8 */   public static final char[] allowedCharactersArray = new char[] { '/', '\n', '\r', '\t', '\f', '`', '?', '*', '\\', '<', '>', '|', '"', ':' };
/*    */ 
/*    */   
/*    */   public static boolean isAllowedCharacter(char character) {
/* 12 */     return (character != '§' && character >= ' ' && character != '');
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String filterAllowedCharacters(String input) {
/* 20 */     StringBuilder stringbuilder = new StringBuilder(); byte b; int i;
/*    */     char[] arrayOfChar;
/* 22 */     for (i = (arrayOfChar = input.toCharArray()).length, b = 0; b < i; ) { char c0 = arrayOfChar[b];
/*    */       
/* 24 */       if (isAllowedCharacter(c0))
/*    */       {
/* 26 */         stringbuilder.append(c0);
/*    */       }
/*    */       b++; }
/*    */     
/* 30 */     return stringbuilder.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraf\\util\ChatAllowedCharacters.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */