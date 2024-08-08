package org.yaml.snakeyaml.introspector;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.util.ArrayUtils;

public class MethodProperty extends GenericProperty {
   private final PropertyDescriptor property;
   private final boolean readable;
   private final boolean writable;

   private static Type discoverGenericType(PropertyDescriptor var0) {
      Method var1 = var0.getReadMethod();
      if (var1 != null) {
         return var1.getGenericReturnType();
      } else {
         Method var2 = var0.getWriteMethod();
         if (var2 != null) {
            Type[] var3 = var2.getGenericParameterTypes();
            if (var3.length > 0) {
               return var3[0];
            }
         }

         return null;
      }
   }

   public MethodProperty(PropertyDescriptor var1) {
      super(var1.getName(), var1.getPropertyType(), discoverGenericType(var1));
      this.property = var1;
      this.readable = var1.getReadMethod() != null;
      this.writable = var1.getWriteMethod() != null;
   }

   public void set(Object var1, Object var2) throws Exception {
      if (!this.writable) {
         throw new YAMLException("No writable property '" + this.getName() + "' on class: " + var1.getClass().getName());
      } else {
         this.property.getWriteMethod().invoke(var1, var2);
      }
   }

   public Object get(Object var1) {
      try {
         this.property.getReadMethod().setAccessible(true);
         return this.property.getReadMethod().invoke(var1);
      } catch (Exception var3) {
         throw new YAMLException("Unable to find getter for property '" + this.property.getName() + "' on object " + var1 + ":" + var3);
      }
   }

   public List getAnnotations() {
      List var1;
      if (this.isReadable() && this.isWritable()) {
         var1 = ArrayUtils.toUnmodifiableCompositeList(this.property.getReadMethod().getAnnotations(), this.property.getWriteMethod().getAnnotations());
      } else if (this.isReadable()) {
         var1 = ArrayUtils.toUnmodifiableList(this.property.getReadMethod().getAnnotations());
      } else {
         var1 = ArrayUtils.toUnmodifiableList(this.property.getWriteMethod().getAnnotations());
      }

      return var1;
   }

   public Annotation getAnnotation(Class var1) {
      Annotation var2 = null;
      if (this.isReadable()) {
         var2 = this.property.getReadMethod().getAnnotation(var1);
      }

      if (var2 == null && this.isWritable()) {
         var2 = this.property.getWriteMethod().getAnnotation(var1);
      }

      return var2;
   }

   public boolean isWritable() {
      return this.writable;
   }

   public boolean isReadable() {
      return this.readable;
   }
}
