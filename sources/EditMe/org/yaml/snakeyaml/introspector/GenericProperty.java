package org.yaml.snakeyaml.introspector;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class GenericProperty extends Property {
   private Type genType;
   private boolean actualClassesChecked;
   private Class[] actualClasses;

   public GenericProperty(String var1, Class var2, Type var3) {
      super(var1, var2);
      this.genType = var3;
      this.actualClassesChecked = var3 == null;
   }

   public Class[] getActualTypeArguments() {
      if (!this.actualClassesChecked) {
         if (this.genType instanceof ParameterizedType) {
            ParameterizedType var6 = (ParameterizedType)this.genType;
            Type[] var2 = var6.getActualTypeArguments();
            if (var2.length > 0) {
               this.actualClasses = new Class[var2.length];

               for(int var3 = 0; var3 < var2.length; ++var3) {
                  if (var2[var3] instanceof Class) {
                     this.actualClasses[var3] = (Class)var2[var3];
                  } else if (var2[var3] instanceof ParameterizedType) {
                     this.actualClasses[var3] = (Class)((ParameterizedType)var2[var3]).getRawType();
                  } else {
                     if (!(var2[var3] instanceof GenericArrayType)) {
                        this.actualClasses = null;
                        break;
                     }

                     Type var4 = ((GenericArrayType)var2[var3]).getGenericComponentType();
                     if (!(var4 instanceof Class)) {
                        this.actualClasses = null;
                        break;
                     }

                     this.actualClasses[var3] = Array.newInstance((Class)var4, 0).getClass();
                  }
               }
            }
         } else if (this.genType instanceof GenericArrayType) {
            Type var1 = ((GenericArrayType)this.genType).getGenericComponentType();
            if (var1 instanceof Class) {
               this.actualClasses = new Class[]{(Class)var1};
            }
         } else if (this.genType instanceof Class) {
            Class var5 = (Class)this.genType;
            if (var5.isArray()) {
               this.actualClasses = new Class[1];
               this.actualClasses[0] = this.getType().getComponentType();
            }
         }

         this.actualClassesChecked = true;
      }

      return this.actualClasses;
   }
}
