/*   1:    */ package org.newdawn.slick.util;
/*   2:    */ 
/*   3:    */ import java.security.AccessController;
/*   4:    */ import java.security.PrivilegedAction;
/*   5:    */ 
/*   6:    */ public final class Log
/*   7:    */ {
/*   8: 13 */   private static boolean verbose = true;
/*   9: 15 */   private static boolean forcedVerbose = false;
/*  10:    */   private static final String forceVerboseProperty = "org.newdawn.slick.forceVerboseLog";
/*  11:    */   private static final String forceVerbosePropertyOnValue = "true";
/*  12: 30 */   private static LogSystem logSystem = new DefaultLogSystem();
/*  13:    */   
/*  14:    */   public static void setLogSystem(LogSystem system)
/*  15:    */   {
/*  16: 46 */     logSystem = system;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public static void setVerbose(boolean v)
/*  20:    */   {
/*  21: 57 */     if (forcedVerbose) {
/*  22: 58 */       return;
/*  23:    */     }
/*  24: 59 */     verbose = v;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public static void checkVerboseLogSetting()
/*  28:    */   {
/*  29:    */     try
/*  30:    */     {
/*  31: 68 */       AccessController.doPrivileged(new PrivilegedAction()
/*  32:    */       {
/*  33:    */         public Object run()
/*  34:    */         {
/*  35: 70 */           String val = System.getProperty("org.newdawn.slick.forceVerboseLog");
/*  36: 71 */           if ((val != null) && (val.equalsIgnoreCase("true"))) {
/*  37: 72 */             Log.setForcedVerboseOn();
/*  38:    */           }
/*  39: 75 */           return null;
/*  40:    */         }
/*  41:    */       });
/*  42:    */     }
/*  43:    */     catch (Throwable localThrowable) {}
/*  44:    */   }
/*  45:    */   
/*  46:    */   public static void setForcedVerboseOn()
/*  47:    */   {
/*  48: 89 */     forcedVerbose = true;
/*  49: 90 */     verbose = true;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static void error(String message, Throwable e)
/*  53:    */   {
/*  54:100 */     logSystem.error(message, e);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public static void error(Throwable e)
/*  58:    */   {
/*  59:109 */     logSystem.error(e);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static void error(String message)
/*  63:    */   {
/*  64:118 */     logSystem.error(message);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public static void warn(String message)
/*  68:    */   {
/*  69:127 */     logSystem.warn(message);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public static void warn(String message, Throwable e)
/*  73:    */   {
/*  74:137 */     logSystem.warn(message, e);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public static void info(String message)
/*  78:    */   {
/*  79:146 */     if ((verbose) || (forcedVerbose)) {
/*  80:147 */       logSystem.info(message);
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   public static void debug(String message)
/*  85:    */   {
/*  86:157 */     if ((verbose) || (forcedVerbose)) {
/*  87:158 */       logSystem.debug(message);
/*  88:    */     }
/*  89:    */   }
/*  90:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.util.Log
 * JD-Core Version:    0.7.0.1
 */