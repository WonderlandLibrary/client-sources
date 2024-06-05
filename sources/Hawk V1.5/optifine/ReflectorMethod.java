package optifine;

import java.lang.reflect.Method;

public class ReflectorMethod {
   private boolean checked;
   private Method targetMethod;
   private String targetMethodName;
   private ReflectorClass reflectorClass;
   private Class[] targetMethodParameterTypes;

   public Class getReturnType() {
      Method var1 = this.getTargetMethod();
      return var1 == null ? null : var1.getReturnType();
   }

   public ReflectorMethod(ReflectorClass var1, String var2) {
      this(var1, var2, (Class[])null, false);
   }

   public ReflectorMethod(ReflectorClass var1, String var2, Class[] var3) {
      this(var1, var2, var3, false);
   }

   public Method getTargetMethod() {
      if (this.checked) {
         return this.targetMethod;
      } else {
         this.checked = true;
         Class var1 = this.reflectorClass.getTargetClass();
         if (var1 == null) {
            return null;
         } else {
            try {
               if (this.targetMethodParameterTypes == null) {
                  Method[] var2 = Reflector.getMethods(var1, this.targetMethodName);
                  if (var2.length <= 0) {
                     Config.log(String.valueOf((new StringBuilder("(Reflector) Method not present: ")).append(var1.getName()).append(".").append(this.targetMethodName)));
                     return null;
                  }

                  if (var2.length > 1) {
                     Config.warn(String.valueOf((new StringBuilder("(Reflector) More than one method found: ")).append(var1.getName()).append(".").append(this.targetMethodName)));

                     for(int var3 = 0; var3 < var2.length; ++var3) {
                        Method var4 = var2[var3];
                        Config.warn(String.valueOf((new StringBuilder("(Reflector)  - ")).append(var4)));
                     }

                     return null;
                  }

                  this.targetMethod = var2[0];
               } else {
                  this.targetMethod = Reflector.getMethod(var1, this.targetMethodName, this.targetMethodParameterTypes);
               }

               if (this.targetMethod == null) {
                  Config.log(String.valueOf((new StringBuilder("(Reflector) Method not present: ")).append(var1.getName()).append(".").append(this.targetMethodName)));
                  return null;
               } else {
                  this.targetMethod.setAccessible(true);
                  return this.targetMethod;
               }
            } catch (Throwable var5) {
               var5.printStackTrace();
               return null;
            }
         }
      }
   }

   public void deactivate() {
      this.checked = true;
      this.targetMethod = null;
   }

   public boolean exists() {
      return this.checked ? this.targetMethod != null : this.getTargetMethod() != null;
   }

   public ReflectorMethod(ReflectorClass var1, String var2, Class[] var3, boolean var4) {
      this.reflectorClass = null;
      this.targetMethodName = null;
      this.targetMethodParameterTypes = null;
      this.checked = false;
      this.targetMethod = null;
      this.reflectorClass = var1;
      this.targetMethodName = var2;
      this.targetMethodParameterTypes = var3;
      if (!var4) {
         Method var5 = this.getTargetMethod();
      }

   }
}
