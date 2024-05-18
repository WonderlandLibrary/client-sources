/*    */ package org.newdawn.slick.util;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.util.Date;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultLogSystem
/*    */   implements LogSystem
/*    */ {
/* 13 */   public static PrintStream out = System.out;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void error(String message, Throwable e) {
/* 22 */     error(message);
/* 23 */     error(e);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void error(Throwable e) {
/* 32 */     out.println(new Date() + " ERROR:" + e.getMessage());
/* 33 */     e.printStackTrace(out);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void error(String message) {
/* 42 */     out.println(new Date() + " ERROR:" + message);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void warn(String message) {
/* 51 */     out.println(new Date() + " WARN:" + message);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void info(String message) {
/* 60 */     out.println(new Date() + " INFO:" + message);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void debug(String message) {
/* 69 */     out.println(new Date() + " DEBUG:" + message);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void warn(String message, Throwable e) {
/* 79 */     warn(message);
/* 80 */     e.printStackTrace(out);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slic\\util\DefaultLogSystem.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */