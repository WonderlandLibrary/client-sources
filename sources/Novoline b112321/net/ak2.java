package net;

import net.acE;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ak2 {
   private static acE[] b;

   public static Logger c() {
      return LogManager.getLogger();
   }

   public static Logger a(Class var0) {
      return LogManager.getLogger(var0);
   }

   public static Logger a(String var0) {
      return LogManager.getLogger(var0);
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new acE[2]);
      }

   }
}
