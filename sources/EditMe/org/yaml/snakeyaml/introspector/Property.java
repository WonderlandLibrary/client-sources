package org.yaml.snakeyaml.introspector;

import java.lang.annotation.Annotation;
import java.util.List;

public abstract class Property implements Comparable {
   private final String name;
   private final Class type;

   public Property(String var1, Class var2) {
      this.name = var1;
      this.type = var2;
   }

   public Class getType() {
      return this.type;
   }

   public abstract Class[] getActualTypeArguments();

   public String getName() {
      return this.name;
   }

   public String toString() {
      return this.getName() + " of " + this.getType();
   }

   public int compareTo(Property var1) {
      return this.getName().compareTo(var1.getName());
   }

   public boolean isWritable() {
      return true;
   }

   public boolean isReadable() {
      return true;
   }

   public abstract void set(Object var1, Object var2) throws Exception;

   public abstract Object get(Object var1);

   public abstract List getAnnotations();

   public abstract Annotation getAnnotation(Class var1);

   public int hashCode() {
      return this.getName().hashCode() + this.getType().hashCode();
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof Property)) {
         return false;
      } else {
         Property var2 = (Property)var1;
         return this.getName().equals(var2.getName()) && this.getType().equals(var2.getType());
      }
   }

   public int compareTo(Object var1) {
      return this.compareTo((Property)var1);
   }
}
