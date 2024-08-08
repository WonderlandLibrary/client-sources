package org.spongepowered.asm.util;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.MethodNode;

public final class Annotations {
   private Annotations() {
   }

   public static void setVisible(FieldNode var0, Class var1, Object... var2) {
      AnnotationNode var3 = createNode(Type.getDescriptor(var1), var2);
      var0.visibleAnnotations = add(var0.visibleAnnotations, var3);
   }

   public static void setInvisible(FieldNode var0, Class var1, Object... var2) {
      AnnotationNode var3 = createNode(Type.getDescriptor(var1), var2);
      var0.invisibleAnnotations = add(var0.invisibleAnnotations, var3);
   }

   public static void setVisible(MethodNode var0, Class var1, Object... var2) {
      AnnotationNode var3 = createNode(Type.getDescriptor(var1), var2);
      var0.visibleAnnotations = add(var0.visibleAnnotations, var3);
   }

   public static void setInvisible(MethodNode var0, Class var1, Object... var2) {
      AnnotationNode var3 = createNode(Type.getDescriptor(var1), var2);
      var0.invisibleAnnotations = add(var0.invisibleAnnotations, var3);
   }

   private static AnnotationNode createNode(String var0, Object... var1) {
      AnnotationNode var2 = new AnnotationNode(var0);

      for(int var3 = 0; var3 < var1.length - 1; var3 += 2) {
         if (!(var1[var3] instanceof String)) {
            throw new IllegalArgumentException("Annotation keys must be strings, found " + var1[var3].getClass().getSimpleName() + " with " + var1[var3].toString() + " at index " + var3 + " creating " + var0);
         }

         var2.visit((String)var1[var3], var1[var3 + 1]);
      }

      return var2;
   }

   private static List add(List var0, AnnotationNode var1) {
      if (var0 == null) {
         var0 = new ArrayList(1);
      } else {
         ((List)var0).remove(get((List)var0, var1.desc));
      }

      ((List)var0).add(var1);
      return (List)var0;
   }

   public static AnnotationNode getVisible(FieldNode var0, Class var1) {
      return get(var0.visibleAnnotations, Type.getDescriptor(var1));
   }

   public static AnnotationNode getInvisible(FieldNode var0, Class var1) {
      return get(var0.invisibleAnnotations, Type.getDescriptor(var1));
   }

   public static AnnotationNode getVisible(MethodNode var0, Class var1) {
      return get(var0.visibleAnnotations, Type.getDescriptor(var1));
   }

   public static AnnotationNode getInvisible(MethodNode var0, Class var1) {
      return get(var0.invisibleAnnotations, Type.getDescriptor(var1));
   }

   public static AnnotationNode getSingleVisible(MethodNode var0, Class... var1) {
      return getSingle(var0.visibleAnnotations, var1);
   }

   public static AnnotationNode getSingleInvisible(MethodNode var0, Class... var1) {
      return getSingle(var0.invisibleAnnotations, var1);
   }

   public static AnnotationNode getVisible(ClassNode var0, Class var1) {
      return get(var0.visibleAnnotations, Type.getDescriptor(var1));
   }

   public static AnnotationNode getInvisible(ClassNode var0, Class var1) {
      return get(var0.invisibleAnnotations, Type.getDescriptor(var1));
   }

   public static AnnotationNode getVisibleParameter(MethodNode var0, Class var1, int var2) {
      return getParameter(var0.visibleParameterAnnotations, Type.getDescriptor(var1), var2);
   }

   public static AnnotationNode getInvisibleParameter(MethodNode var0, Class var1, int var2) {
      return getParameter(var0.invisibleParameterAnnotations, Type.getDescriptor(var1), var2);
   }

   public static AnnotationNode getParameter(List[] var0, String var1, int var2) {
      return var0 != null && var2 >= 0 && var2 < var0.length ? get(var0[var2], var1) : null;
   }

   public static AnnotationNode get(List var0, String var1) {
      if (var0 == null) {
         return null;
      } else {
         Iterator var2 = var0.iterator();

         AnnotationNode var3;
         do {
            if (!var2.hasNext()) {
               return null;
            }

            var3 = (AnnotationNode)var2.next();
         } while(!var1.equals(var3.desc));

         return var3;
      }
   }

   private static AnnotationNode getSingle(List var0, Class[] var1) {
      ArrayList var2 = new ArrayList();
      Class[] var3 = var1;
      int var4 = var1.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Class var6 = var3[var5];
         AnnotationNode var7 = get(var0, Type.getDescriptor(var6));
         if (var7 != null) {
            var2.add(var7);
         }
      }

      int var8 = var2.size();
      if (var8 > 1) {
         throw new IllegalArgumentException("Conflicting annotations found: " + Lists.transform(var2, new Function() {
            public String apply(AnnotationNode var1) {
               return var1.desc;
            }

            public Object apply(Object var1) {
               return this.apply((AnnotationNode)var1);
            }
         }));
      } else {
         return var8 == 0 ? null : (AnnotationNode)var2.get(0);
      }
   }

   public static Object getValue(AnnotationNode var0) {
      return getValue(var0, "value");
   }

   public static Object getValue(AnnotationNode var0, String var1, Object var2) {
      Object var3 = getValue(var0, var1);
      return var3 != null ? var3 : var2;
   }

   public static Object getValue(AnnotationNode var0, String var1, Class var2) {
      Preconditions.checkNotNull(var2, "annotationClass cannot be null");
      Object var3 = getValue(var0, var1);
      if (var3 == null) {
         try {
            var3 = var2.getDeclaredMethod(var1).getDefaultValue();
         } catch (NoSuchMethodException var5) {
         }
      }

      return var3;
   }

   public static Object getValue(AnnotationNode var0, String var1) {
      boolean var2 = false;
      if (var0 != null && var0.values != null) {
         Iterator var3 = var0.values.iterator();

         while(var3.hasNext()) {
            Object var4 = var3.next();
            if (var2) {
               return var4;
            }

            if (var4.equals(var1)) {
               var2 = true;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public static Enum getValue(AnnotationNode var0, String var1, Class var2, Enum var3) {
      String[] var4 = (String[])getValue(var0, var1);
      return var4 == null ? var3 : toEnumValue(var2, var4);
   }

   public static List getValue(AnnotationNode var0, String var1, boolean var2) {
      Object var3 = getValue(var0, var1);
      if (var3 instanceof List) {
         return (List)var3;
      } else if (var3 != null) {
         ArrayList var4 = new ArrayList();
         var4.add(var3);
         return var4;
      } else {
         return Collections.emptyList();
      }
   }

   public static List getValue(AnnotationNode var0, String var1, boolean var2, Class var3) {
      Object var4 = getValue(var0, var1);
      if (!(var4 instanceof List)) {
         if (var4 instanceof String[]) {
            ArrayList var6 = new ArrayList();
            var6.add(toEnumValue(var3, (String[])((String[])var4)));
            return var6;
         } else {
            return Collections.emptyList();
         }
      } else {
         ListIterator var5 = ((List)var4).listIterator();

         while(var5.hasNext()) {
            var5.set(toEnumValue(var3, (String[])((String[])var5.next())));
         }

         return (List)var4;
      }
   }

   private static Enum toEnumValue(Class var0, String[] var1) {
      if (!var0.getName().equals(Type.getType(var1[0]).getClassName())) {
         throw new IllegalArgumentException("The supplied enum class does not match the stored enum value");
      } else {
         return Enum.valueOf(var0, var1[1]);
      }
   }
}
