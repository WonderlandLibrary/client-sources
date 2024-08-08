package org.yaml.snakeyaml.introspector;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.yaml.snakeyaml.error.YAMLException;

public class PropertySubstitute extends Property {
   private static final Logger log = Logger.getLogger(PropertySubstitute.class.getPackage().getName());
   protected Class targetType;
   private final String readMethod;
   private final String writeMethod;
   private transient Method read;
   private transient Method write;
   private Field field;
   protected Class[] parameters;
   private Property delegate;
   private boolean filler;

   public PropertySubstitute(String var1, Class var2, String var3, String var4, Class... var5) {
      super(var1, var2);
      this.readMethod = var3;
      this.writeMethod = var4;
      this.setActualTypeArguments(var5);
      this.filler = false;
   }

   public PropertySubstitute(String var1, Class var2, Class... var3) {
      this(var1, var2, (String)null, (String)null, var3);
   }

   public Class[] getActualTypeArguments() {
      return this.parameters == null && this.delegate != null ? this.delegate.getActualTypeArguments() : this.parameters;
   }

   public void setActualTypeArguments(Class... var1) {
      if (var1 != null && var1.length > 0) {
         this.parameters = var1;
      } else {
         this.parameters = null;
      }

   }

   public void set(Object var1, Object var2) throws Exception {
      if (this.write != null) {
         if (!this.filler) {
            this.write.invoke(var1, var2);
         } else if (var2 != null) {
            Iterator var4;
            if (var2 instanceof Collection) {
               Collection var3 = (Collection)var2;
               var4 = var3.iterator();

               while(var4.hasNext()) {
                  Object var5 = var4.next();
                  this.write.invoke(var1, var5);
               }
            } else if (var2 instanceof Map) {
               Map var6 = (Map)var2;
               var4 = var6.entrySet().iterator();

               while(var4.hasNext()) {
                  Entry var9 = (Entry)var4.next();
                  this.write.invoke(var1, var9.getKey(), var9.getValue());
               }
            } else if (var2.getClass().isArray()) {
               int var7 = Array.getLength(var2);

               for(int var8 = 0; var8 < var7; ++var8) {
                  this.write.invoke(var1, Array.get(var2, var8));
               }
            }
         }
      } else if (this.field != null) {
         this.field.set(var1, var2);
      } else if (this.delegate != null) {
         this.delegate.set(var1, var2);
      } else {
         log.warning("No setter/delegate for '" + this.getName() + "' on object " + var1);
      }

   }

   public Object get(Object var1) {
      try {
         if (this.read != null) {
            return this.read.invoke(var1);
         }

         if (this.field != null) {
            return this.field.get(var1);
         }
      } catch (Exception var3) {
         throw new YAMLException("Unable to find getter for property '" + this.getName() + "' on object " + var1 + ":" + var3);
      }

      if (this.delegate != null) {
         return this.delegate.get(var1);
      } else {
         throw new YAMLException("No getter or delegate for property '" + this.getName() + "' on object " + var1);
      }
   }

   public List getAnnotations() {
      Annotation[] var1 = null;
      if (this.read != null) {
         var1 = this.read.getAnnotations();
      } else if (this.field != null) {
         var1 = this.field.getAnnotations();
      }

      return var1 != null ? Arrays.asList(var1) : this.delegate.getAnnotations();
   }

   public Annotation getAnnotation(Class var1) {
      Annotation var2;
      if (this.read != null) {
         var2 = this.read.getAnnotation(var1);
      } else if (this.field != null) {
         var2 = this.field.getAnnotation(var1);
      } else {
         var2 = this.delegate.getAnnotation(var1);
      }

      return var2;
   }

   public void setTargetType(Class var1) {
      if (this.targetType != var1) {
         this.targetType = var1;
         String var2 = this.getName();

         for(Class var3 = var1; var3 != null; var3 = var3.getSuperclass()) {
            Field[] var4 = var3.getDeclaredFields();
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               Field var7 = var4[var6];
               if (var7.getName().equals(var2)) {
                  int var8 = var7.getModifiers();
                  if (!Modifier.isStatic(var8) && !Modifier.isTransient(var8)) {
                     var7.setAccessible(true);
                     this.field = var7;
                  }
                  break;
               }
            }
         }

         if (this.field == null && log.isLoggable(Level.FINE)) {
            log.fine(String.format("Failed to find field for %s.%s", var1.getName(), this.getName()));
         }

         if (this.readMethod != null) {
            this.read = this.discoverMethod(var1, this.readMethod);
         }

         if (this.writeMethod != null) {
            this.filler = false;
            this.write = this.discoverMethod(var1, this.writeMethod, this.getType());
            if (this.write == null && this.parameters != null) {
               this.filler = true;
               this.write = this.discoverMethod(var1, this.writeMethod, this.parameters);
            }
         }
      }

   }

   private Method discoverMethod(Class var1, String var2, Class... var3) {
      for(Class var4 = var1; var4 != null; var4 = var4.getSuperclass()) {
         Method[] var5 = var4.getDeclaredMethods();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Method var8 = var5[var7];
            if (var2.equals(var8.getName())) {
               Class[] var9 = var8.getParameterTypes();
               if (var9.length == var3.length) {
                  boolean var10 = true;

                  for(int var11 = 0; var11 < var9.length; ++var11) {
                     if (!var9[var11].isAssignableFrom(var3[var11])) {
                        var10 = false;
                     }
                  }

                  if (var10) {
                     var8.setAccessible(true);
                     return var8;
                  }
               }
            }
         }
      }

      if (log.isLoggable(Level.FINE)) {
         log.fine(String.format("Failed to find [%s(%d args)] for %s.%s", var2, var3.length, this.targetType.getName(), this.getName()));
      }

      return null;
   }

   public String getName() {
      String var1 = super.getName();
      if (var1 != null) {
         return var1;
      } else {
         return this.delegate != null ? this.delegate.getName() : null;
      }
   }

   public Class getType() {
      Class var1 = super.getType();
      if (var1 != null) {
         return var1;
      } else {
         return this.delegate != null ? this.delegate.getType() : null;
      }
   }

   public boolean isReadable() {
      return this.read != null || this.field != null || this.delegate != null && this.delegate.isReadable();
   }

   public boolean isWritable() {
      return this.write != null || this.field != null || this.delegate != null && this.delegate.isWritable();
   }

   public void setDelegate(Property var1) {
      this.delegate = var1;
      if (this.writeMethod != null && this.write == null && !this.filler) {
         this.filler = true;
         this.write = this.discoverMethod(this.targetType, this.writeMethod, this.getActualTypeArguments());
      }

   }
}
