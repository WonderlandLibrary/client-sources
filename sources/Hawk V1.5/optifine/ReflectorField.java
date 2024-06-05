package optifine;

import java.lang.reflect.Field;

public class ReflectorField {
   private boolean checked = false;
   private Field targetField = null;
   private String targetFieldName = null;
   private ReflectorClass reflectorClass = null;

   public void setValue(Object var1) {
      Reflector.setFieldValue((Object)null, this, var1);
   }

   public boolean exists() {
      return this.checked ? this.targetField != null : this.getTargetField() != null;
   }

   public ReflectorField(ReflectorClass var1, String var2) {
      this.reflectorClass = var1;
      this.targetFieldName = var2;
      Field var3 = this.getTargetField();
   }

   public Object getValue() {
      return Reflector.getFieldValue((Object)null, this);
   }

   public Field getTargetField() {
      if (this.checked) {
         return this.targetField;
      } else {
         this.checked = true;
         Class var1 = this.reflectorClass.getTargetClass();
         if (var1 == null) {
            return null;
         } else {
            try {
               this.targetField = var1.getDeclaredField(this.targetFieldName);
               this.targetField.setAccessible(true);
            } catch (NoSuchFieldException var3) {
               Config.log(String.valueOf((new StringBuilder("(Reflector) Field not present: ")).append(var1.getName()).append(".").append(this.targetFieldName)));
            } catch (SecurityException var4) {
               var4.printStackTrace();
            } catch (Throwable var5) {
               var5.printStackTrace();
            }

            return this.targetField;
         }
      }
   }
}
