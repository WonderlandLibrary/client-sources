/*    */ package me.eagler.utils;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import me.eagler.Client;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Utils
/*    */ {
/*    */   public ArrayList<String> getLetters() {
/* 11 */     ArrayList<String> letters = new ArrayList<String>();
/*    */     
/* 13 */     letters.add("a");
/* 14 */     letters.add("b");
/* 15 */     letters.add("c");
/* 16 */     letters.add("d");
/* 17 */     letters.add("e");
/* 18 */     letters.add("f");
/* 19 */     letters.add("g");
/* 20 */     letters.add("h");
/* 21 */     letters.add("i");
/* 22 */     letters.add("j");
/* 23 */     letters.add("k");
/* 24 */     letters.add("l");
/* 25 */     letters.add("m");
/* 26 */     letters.add("n");
/* 27 */     letters.add("o");
/* 28 */     letters.add("p");
/* 29 */     letters.add("q");
/* 30 */     letters.add("r");
/* 31 */     letters.add("s");
/* 32 */     letters.add("t");
/* 33 */     letters.add("u");
/* 34 */     letters.add("v");
/* 35 */     letters.add("w");
/* 36 */     letters.add("x");
/* 37 */     letters.add("y");
/* 38 */     letters.add("z");
/*    */     
/* 40 */     return letters;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean containsLetter(String text) {
/* 46 */     text = text.toLowerCase();
/*    */     
/* 48 */     for (int i = 0; i < getLetters().size() - 1; i++) {
/*    */       
/* 50 */       if (text.contains(getLetters().get(i)))
/*    */       {
/* 52 */         return true;
/*    */       }
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 58 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isTheme(String theme) {
/* 64 */     if (theme.equalsIgnoreCase("Dark"))
/*    */     {
/* 66 */       if (Client.instance.getSettingManager().getSettingByName("ThemeMode").getMode().equalsIgnoreCase("Dark"))
/*    */       {
/* 68 */         return true;
/*    */       }
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 74 */     if (theme.equalsIgnoreCase("Bright"))
/*    */     {
/* 76 */       if (Client.instance.getSettingManager().getSettingByName("ThemeMode").getMode().equalsIgnoreCase("Bright"))
/*    */       {
/* 78 */         return true;
/*    */       }
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 84 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagle\\utils\Utils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */