package org.yaml.snakeyaml.introspector;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.internal.Logger;

public class PropertySubstitute extends Property {
   private static final Logger log = Logger.getLogger(PropertySubstitute.class.getPackage().getName());
   protected Class<?> targetType;
   private final String readMethod;
   private final String writeMethod;
   private transient Method read;
   private transient Method write;
   private Field field;
   protected Class<?>[] parameters;
   private Property delegate;
   private boolean filler;

   public PropertySubstitute(String name, Class<?> type, String readMethod, String writeMethod, Class<?>... params) {
      super(name, type);
      this.readMethod = readMethod;
      this.writeMethod = writeMethod;
      this.setActualTypeArguments(params);
      this.filler = false;
   }

   public PropertySubstitute(String name, Class<?> type, Class<?>... params) {
      this(name, type, null, null, params);
   }

   @Override
   public Class<?>[] getActualTypeArguments() {
      return this.parameters == null && this.delegate != null ? this.delegate.getActualTypeArguments() : this.parameters;
   }

   public void setActualTypeArguments(Class<?>... args) {
      if (args != null && args.length > 0) {
         this.parameters = args;
      } else {
         this.parameters = null;
      }
   }

   @Override
   public void set(Object object, Object value) throws Exception {
      if (this.write != null) {
         if (!this.filler) {
            this.write.invoke(object, value);
         } else if (value != null) {
            if (value instanceof Collection) {
               for (Object val : (Collection)value) {
                  this.write.invoke(object, val);
               }
            } else if (value instanceof Map) {
               Map<?, ?> map = (Map<?, ?>)value;

               for (Entry<?, ?> entry : map.entrySet()) {
                  this.write.invoke(object, entry.getKey(), entry.getValue());
               }
            } else if (value.getClass().isArray()) {
               int len = Array.getLength(value);

               for (int i = 0; i < len; i++) {
                  this.write.invoke(object, Array.get(value, i));
               }
            }
         }
      } else if (this.field != null) {
         this.field.set(object, value);
      } else if (this.delegate != null) {
         this.delegate.set(object, value);
      } else {
         log.warn("No setter/delegate for '" + this.getName() + "' on object " + object);
      }
   }

   @Override
   public Object get(Object object) {
      try {
         if (this.read != null) {
            return this.read.invoke(object);
         }

         if (this.field != null) {
            return this.field.get(object);
         }
      } catch (Exception var3) {
         throw new YAMLException("Unable to find getter for property '" + this.getName() + "' on object " + object + ":" + var3);
      }

      if (this.delegate != null) {
         return this.delegate.get(object);
      } else {
         throw new YAMLException("No getter or delegate for property '" + this.getName() + "' on object " + object);
      }
   }

   @Override
   public List<Annotation> getAnnotations() {
      Annotation[] annotations = null;
      if (this.read != null) {
         annotations = this.read.getAnnotations();
      } else if (this.field != null) {
         annotations = this.field.getAnnotations();
      }

      return annotations != null ? Arrays.asList(annotations) : this.delegate.getAnnotations();
   }

   @Override
   public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
      A annotation;
      if (this.read != null) {
         annotation = this.read.getAnnotation(annotationType);
      } else if (this.field != null) {
         annotation = this.field.getAnnotation(annotationType);
      } else {
         annotation = this.delegate.getAnnotation(annotationType);
      }

      return annotation;
   }

   public void setTargetType(Class<?> targetType) {
      if (this.targetType != targetType) {
         this.targetType = targetType;
         String name = this.getName();

         for (Class<?> c = targetType; c != null; c = c.getSuperclass()) {
            for (Field f : c.getDeclaredFields()) {
               if (f.getName().equals(name)) {
                  int modifiers = f.getModifiers();
                  if (!Modifier.isStatic(modifiers) && !Modifier.isTransient(modifiers)) {
                     f.setAccessible(true);
                     this.field = f;
                  }
                  break;
               }
            }
         }

         if (this.field == null && log.isLoggable(Logger.Level.WARNING)) {
            log.warn(String.format("Failed to find field for %s.%s", targetType.getName(), this.getName()));
         }

         if (this.readMethod != null) {
            this.read = this.discoverMethod(targetType, this.readMethod);
         }

         if (this.writeMethod != null) {
            this.filler = false;
            this.write = this.discoverMethod(targetType, this.writeMethod, this.getType());
            if (this.write == null && this.parameters != null) {
               this.filler = true;
               this.write = this.discoverMethod(targetType, this.writeMethod, this.parameters);
            }
         }
      }
   }

   private Method discoverMethod(Class<?> type, String name, Class<?>... params) {
      for (Class<?> c = type; c != null; c = c.getSuperclass()) {
         for (Method method : c.getDeclaredMethods()) {
            if (name.equals(method.getName())) {
               Class<?>[] parameterTypes = method.getParameterTypes();
               if (parameterTypes.length == params.length) {
                  boolean found = true;

                  for (int i = 0; i < parameterTypes.length; i++) {
                     if (!parameterTypes[i].isAssignableFrom(params[i])) {
                        found = false;
                     }
                  }

                  if (found) {
                     method.setAccessible(true);
                     return method;
                  }
               }
            }
         }
      }

      if (log.isLoggable(Logger.Level.WARNING)) {
         log.warn(String.format("Failed to find [%s(%d args)] for %s.%s", name, params.length, this.targetType.getName(), this.getName()));
      }

      return null;
   }

   @Override
   public String getName() {
      String n = super.getName();
      if (n != null) {
         return n;
      } else {
         return this.delegate != null ? this.delegate.getName() : null;
      }
   }

   @Override
   public Class<?> getType() {
      Class<?> t = super.getType();
      if (t != null) {
         return t;
      } else {
         return this.delegate != null ? this.delegate.getType() : null;
      }
   }

   @Override
   public boolean isReadable() {
      return this.read != null || this.field != null || this.delegate != null && this.delegate.isReadable();
   }

   @Override
   public boolean isWritable() {
      return this.write != null || this.field != null || this.delegate != null && this.delegate.isWritable();
   }

   public void setDelegate(Property delegate) {
      this.delegate = delegate;
      if (this.writeMethod != null && this.write == null && !this.filler) {
         this.filler = true;
         this.write = this.discoverMethod(this.targetType, this.writeMethod, this.getActualTypeArguments());
      }
   }
}
