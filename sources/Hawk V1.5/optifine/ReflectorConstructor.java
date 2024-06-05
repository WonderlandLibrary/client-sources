package optifine;

import java.lang.reflect.Constructor;

public class ReflectorConstructor {
   private Class[] parameterTypes = null;
   private Constructor targetConstructor = null;
   private boolean checked = false;
   private ReflectorClass reflectorClass = null;

   public Constructor getTargetConstructor() {
      if (this.checked) {
         return this.targetConstructor;
      } else {
         this.checked = true;
         Class var1 = this.reflectorClass.getTargetClass();
         if (var1 == null) {
            return null;
         } else {
            try {
               this.targetConstructor = findConstructor(var1, this.parameterTypes);
               if (this.targetConstructor == null) {
                  Config.dbg(String.valueOf((new StringBuilder("(Reflector) Constructor not present: ")).append(var1.getName()).append(", params: ").append(Config.arrayToString((Object[])this.parameterTypes))));
               }

               if (this.targetConstructor != null) {
                  this.targetConstructor.setAccessible(true);
               }
            } catch (Throwable var3) {
               var3.printStackTrace();
            }

            return this.targetConstructor;
         }
      }
   }

   public void deactivate() {
      this.checked = true;
      this.targetConstructor = null;
   }

   public boolean exists() {
      return this.checked ? this.targetConstructor != null : this.getTargetConstructor() != null;
   }

   private static Constructor findConstructor(Class var0, Class[] var1) {
      Constructor[] var2 = var0.getDeclaredConstructors();

      for(int var3 = 0; var3 < var2.length; ++var3) {
         Constructor var4 = var2[var3];
         Class[] var5 = var4.getParameterTypes();
         if (Reflector.matchesTypes(var1, var5)) {
            return var4;
         }
      }

      return null;
   }

   public ReflectorConstructor(ReflectorClass var1, Class[] var2) {
      this.reflectorClass = var1;
      this.parameterTypes = var2;
      Constructor var3 = this.getTargetConstructor();
   }
}
