package org.spongepowered.tools.obfuscation.mirror;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

public final class AnnotationHandle {
   public static final AnnotationHandle MISSING = new AnnotationHandle((AnnotationMirror)null);
   private final AnnotationMirror annotation;

   private AnnotationHandle(AnnotationMirror var1) {
      this.annotation = var1;
   }

   public AnnotationMirror asMirror() {
      return this.annotation;
   }

   public boolean exists() {
      return this.annotation != null;
   }

   public String toString() {
      return this.annotation == null ? "@{UnknownAnnotation}" : "@" + this.annotation.getAnnotationType().asElement().getSimpleName();
   }

   public Object getValue(String var1, Object var2) {
      if (this.annotation == null) {
         return var2;
      } else {
         AnnotationValue var3 = this.getAnnotationValue(var1);
         if (var2 instanceof Enum && var3 != null) {
            VariableElement var4 = (VariableElement)var3.getValue();
            return var4 == null ? var2 : Enum.valueOf(var2.getClass(), var4.getSimpleName().toString());
         } else {
            return var3 != null ? var3.getValue() : var2;
         }
      }
   }

   public Object getValue() {
      return this.getValue("value", (Object)null);
   }

   public Object getValue(String var1) {
      return this.getValue(var1, (Object)null);
   }

   public boolean getBoolean(String var1, boolean var2) {
      return (Boolean)this.getValue(var1, var2);
   }

   public AnnotationHandle getAnnotation(String var1) {
      Object var2 = this.getValue(var1);
      if (var2 instanceof AnnotationMirror) {
         return of((AnnotationMirror)var2);
      } else {
         if (var2 instanceof AnnotationValue) {
            Object var3 = ((AnnotationValue)var2).getValue();
            if (var3 instanceof AnnotationMirror) {
               return of((AnnotationMirror)var3);
            }
         }

         return null;
      }
   }

   public List getList() {
      return this.getList("value");
   }

   public List getList(String var1) {
      List var2 = (List)this.getValue(var1, Collections.emptyList());
      return unwrapAnnotationValueList(var2);
   }

   public List getAnnotationList(String var1) {
      Object var2 = this.getValue(var1, (Object)null);
      if (var2 == null) {
         return Collections.emptyList();
      } else if (var2 instanceof AnnotationMirror) {
         return ImmutableList.of(of((AnnotationMirror)var2));
      } else {
         List var3 = (List)var2;
         ArrayList var4 = new ArrayList(var3.size());
         Iterator var5 = var3.iterator();

         while(var5.hasNext()) {
            AnnotationValue var6 = (AnnotationValue)var5.next();
            var4.add(new AnnotationHandle((AnnotationMirror)var6.getValue()));
         }

         return Collections.unmodifiableList(var4);
      }
   }

   protected AnnotationValue getAnnotationValue(String var1) {
      Iterator var2 = this.annotation.getElementValues().keySet().iterator();

      ExecutableElement var3;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         var3 = (ExecutableElement)var2.next();
      } while(!var3.getSimpleName().contentEquals(var1));

      return (AnnotationValue)this.annotation.getElementValues().get(var3);
   }

   protected static List unwrapAnnotationValueList(List var0) {
      if (var0 == null) {
         return Collections.emptyList();
      } else {
         ArrayList var1 = new ArrayList(var0.size());
         Iterator var2 = var0.iterator();

         while(var2.hasNext()) {
            AnnotationValue var3 = (AnnotationValue)var2.next();
            var1.add(var3.getValue());
         }

         return var1;
      }
   }

   protected static AnnotationMirror getAnnotation(Element var0, Class var1) {
      if (var0 == null) {
         return null;
      } else {
         List var2 = var0.getAnnotationMirrors();
         if (var2 == null) {
            return null;
         } else {
            Iterator var3 = var2.iterator();

            while(var3.hasNext()) {
               AnnotationMirror var4 = (AnnotationMirror)var3.next();
               Element var5 = var4.getAnnotationType().asElement();
               if (var5 instanceof TypeElement) {
                  TypeElement var6 = (TypeElement)var5;
                  if (var6.getQualifiedName().contentEquals(var1.getName())) {
                     return var4;
                  }
               }
            }

            return null;
         }
      }
   }

   public static AnnotationHandle of(AnnotationMirror var0) {
      return new AnnotationHandle(var0);
   }

   public static AnnotationHandle of(Element var0, Class var1) {
      return new AnnotationHandle(getAnnotation(var0, var1));
   }
}
