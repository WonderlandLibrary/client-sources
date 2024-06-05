package net.optifine;

import java.lang.reflect.Field;
import net.af_;
import net.optifine.Config;
import net.optifine.IFieldLocator;
import net.optifine.MatchBlock;
import net.optifine.ReflectorClass;

public class FieldLocatorName implements IFieldLocator {
   private ReflectorClass reflectorClass = null;
   private String targetFieldName = null;

   public FieldLocatorName(ReflectorClass var1, String var2) {
      this.reflectorClass = var1;
      this.targetFieldName = var2;
   }

   public Field getField() {
      MatchBlock.b();
      Class var2 = this.reflectorClass.getTargetClass();
      if(var2 == null) {
         return null;
      } else {
         Throwable var3;
         try {
            Class var10000 = var2;
            FieldLocatorName var10001 = this;

            try {
               Field var7 = af_.c(var10000, var10001.targetFieldName);
               var7.setAccessible(true);
               return var7;
            } catch (Throwable var4) {
               var3 = var4;
            }
         } catch (NoSuchFieldException var5) {
            Config.log("(Reflector) Field not present: " + var2.getName() + "." + this.targetFieldName);
            return null;
         } catch (SecurityException var6) {
            var6.printStackTrace();
            return null;
         }

         var3.printStackTrace();
         return null;
      }
   }

   private static NoSuchFieldException a(NoSuchFieldException var0) {
      return var0;
   }
}
