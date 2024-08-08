package org.spongepowered.tools.obfuscation.mirror;

import java.io.Serializable;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

public class TypeReference implements Serializable, Comparable {
   private static final long serialVersionUID = 1L;
   private final String name;
   private transient TypeHandle handle;

   public TypeReference(TypeHandle var1) {
      this.name = var1.getName();
      this.handle = var1;
   }

   public TypeReference(String var1) {
      this.name = var1;
   }

   public String getName() {
      return this.name;
   }

   public String getClassName() {
      return this.name.replace('/', '.');
   }

   public TypeHandle getHandle(ProcessingEnvironment var1) {
      if (this.handle == null) {
         TypeElement var2 = var1.getElementUtils().getTypeElement(this.getClassName());

         try {
            this.handle = new TypeHandle(var2);
         } catch (Exception var4) {
            var4.printStackTrace();
         }
      }

      return this.handle;
   }

   public String toString() {
      return String.format("TypeReference[%s]", this.name);
   }

   public int compareTo(TypeReference var1) {
      return var1 == null ? -1 : this.name.compareTo(var1.name);
   }

   public boolean equals(Object var1) {
      return var1 instanceof TypeReference && this.compareTo((TypeReference)var1) == 0;
   }

   public int hashCode() {
      return this.name.hashCode();
   }

   public int compareTo(Object var1) {
      return this.compareTo((TypeReference)var1);
   }
}
