package net;

import javax.script.ScriptEngine;
import jdk.nashorn.api.scripting.ClassFilter;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import net.acE;

public class aei {
   private static acE[] b;

   public static ScriptEngine a(NashornScriptEngineFactory var0, ClassFilter var1) {
      return var0.getScriptEngine(var1);
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new acE[3]);
      }

   }
}
