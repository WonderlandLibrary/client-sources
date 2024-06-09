/*     */ package org.newdawn.slick.util;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
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
/*     */ public final class Log
/*     */ {
/*     */   private static boolean verbose = true;
/*     */   private static boolean forcedVerbose = false;
/*     */   private static final String forceVerboseProperty = "org.newdawn.slick.forceVerboseLog";
/*     */   private static final String forceVerbosePropertyOnValue = "true";
/*  30 */   private static LogSystem logSystem = new DefaultLogSystem();
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
/*     */   public static void setLogSystem(LogSystem system) {
/*  46 */     logSystem = system;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setVerbose(boolean v) {
/*  57 */     if (forcedVerbose)
/*     */       return; 
/*  59 */     verbose = v;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void checkVerboseLogSetting() {
/*     */     try {
/*  68 */       AccessController.doPrivileged(new PrivilegedAction() {
/*     */             public Object run() {
/*  70 */               String val = System.getProperty("org.newdawn.slick.forceVerboseLog");
/*  71 */               if (val != null && val.equalsIgnoreCase("true")) {
/*  72 */                 Log.setForcedVerboseOn();
/*     */               }
/*     */               
/*  75 */               return null;
/*     */             }
/*     */           });
/*  78 */     } catch (Throwable e) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setForcedVerboseOn() {
/*  89 */     forcedVerbose = true;
/*  90 */     verbose = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void error(String message, Throwable e) {
/* 100 */     logSystem.error(message, e);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void error(Throwable e) {
/* 109 */     logSystem.error(e);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void error(String message) {
/* 118 */     logSystem.error(message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void warn(String message) {
/* 127 */     logSystem.warn(message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void warn(String message, Throwable e) {
/* 137 */     logSystem.warn(message, e);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void info(String message) {
/* 146 */     if (verbose || forcedVerbose) {
/* 147 */       logSystem.info(message);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void debug(String message) {
/* 157 */     if (verbose || forcedVerbose)
/* 158 */       logSystem.debug(message); 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slic\\util\Log.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */