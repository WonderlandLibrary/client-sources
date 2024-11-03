package com.viaversion.viaversion.libs.gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public final class FieldAttributes {
   private final Field field;

   public FieldAttributes(Field f) {
      this.field = Objects.requireNonNull(f);
   }

   public Class<?> getDeclaringClass() {
      return this.field.getDeclaringClass();
   }

   public String getName() {
      return this.field.getName();
   }

   public Type getDeclaredType() {
      return this.field.getGenericType();
   }

   public Class<?> getDeclaredClass() {
      return this.field.getType();
   }

   public <T extends Annotation> T getAnnotation(Class<T> annotation) {
      return this.field.getAnnotation(annotation);
   }

   public Collection<Annotation> getAnnotations() {
      return Arrays.asList(this.field.getAnnotations());
   }

   public boolean hasModifier(int modifier) {
      return (this.field.getModifiers() & modifier) != 0;
   }

   @Override
   public String toString() {
      return this.field.toString();
   }
}
