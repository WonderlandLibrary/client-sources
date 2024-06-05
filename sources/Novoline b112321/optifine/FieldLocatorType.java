package optifine;

import java.lang.reflect.Field;
import optifine.IFieldLocator;
import optifine.ReflectorClass;

public class FieldLocatorType implements IFieldLocator {
   private ReflectorClass reflectorClass;
   private Class targetFieldType;
   private int targetFieldIndex;

   public FieldLocatorType(ReflectorClass var1, Class var2) {
      this(var1, var2, 0);
   }

   public FieldLocatorType(ReflectorClass var1, Class var2, int var3) {
      this.reflectorClass = null;
      this.targetFieldType = null;
      this.reflectorClass = var1;
      this.targetFieldType = var2;
      this.targetFieldIndex = var3;
   }

   public Field getField() {
      // $FF: Couldn't be decompiled
   }

   private static SecurityException a(SecurityException var0) {
      return var0;
   }
}
