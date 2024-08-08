package org.spongepowered.tools.obfuscation.mirror;

import java.util.Iterator;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import org.spongepowered.asm.util.SignaturePrinter;

public abstract class TypeUtils {
   private static final int MAX_GENERIC_RECURSION_DEPTH = 5;
   private static final String OBJECT_SIG = "java.lang.Object";
   private static final String OBJECT_REF = "java/lang/Object";

   private TypeUtils() {
   }

   public static PackageElement getPackage(TypeMirror var0) {
      return !(var0 instanceof DeclaredType) ? null : getPackage((TypeElement)((DeclaredType)var0).asElement());
   }

   public static PackageElement getPackage(TypeElement var0) {
      Element var1;
      for(var1 = var0.getEnclosingElement(); var1 != null && !(var1 instanceof PackageElement); var1 = var1.getEnclosingElement()) {
      }

      return (PackageElement)var1;
   }

   public static String getElementType(Element var0) {
      if (var0 instanceof TypeElement) {
         return "TypeElement";
      } else if (var0 instanceof ExecutableElement) {
         return "ExecutableElement";
      } else if (var0 instanceof VariableElement) {
         return "VariableElement";
      } else if (var0 instanceof PackageElement) {
         return "PackageElement";
      } else {
         return var0 instanceof TypeParameterElement ? "TypeParameterElement" : var0.getClass().getSimpleName();
      }
   }

   public static String stripGenerics(String var0) {
      StringBuilder var1 = new StringBuilder();
      int var2 = 0;

      for(int var3 = 0; var2 < var0.length(); ++var2) {
         char var4 = var0.charAt(var2);
         if (var4 == '<') {
            ++var3;
         }

         if (var3 == 0) {
            var1.append(var4);
         } else if (var4 == '>') {
            --var3;
         }
      }

      return var1.toString();
   }

   public static String getName(VariableElement var0) {
      return var0 != null ? var0.getSimpleName().toString() : null;
   }

   public static String getName(ExecutableElement var0) {
      return var0 != null ? var0.getSimpleName().toString() : null;
   }

   public static String getJavaSignature(Element var0) {
      if (var0 instanceof ExecutableElement) {
         ExecutableElement var1 = (ExecutableElement)var0;
         StringBuilder var2 = (new StringBuilder()).append("(");
         boolean var3 = false;

         for(Iterator var4 = var1.getParameters().iterator(); var4.hasNext(); var3 = true) {
            VariableElement var5 = (VariableElement)var4.next();
            if (var3) {
               var2.append(',');
            }

            var2.append(getTypeName(var5.asType()));
         }

         var2.append(')').append(getTypeName(var1.getReturnType()));
         return var2.toString();
      } else {
         return getTypeName(var0.asType());
      }
   }

   public static String getJavaSignature(String var0) {
      return (new SignaturePrinter("", var0)).setFullyQualified(true).toDescriptor();
   }

   public static String getTypeName(TypeMirror var0) {
      switch(var0.getKind()) {
      case ARRAY:
         return getTypeName(((ArrayType)var0).getComponentType()) + "[]";
      case DECLARED:
         return getTypeName((DeclaredType)var0);
      case TYPEVAR:
         return getTypeName(getUpperBound(var0));
      case ERROR:
         return "java.lang.Object";
      default:
         return var0.toString();
      }
   }

   public static String getTypeName(DeclaredType var0) {
      return var0 == null ? "java.lang.Object" : getInternalName((TypeElement)var0.asElement()).replace('/', '.');
   }

   public static String getDescriptor(Element var0) {
      if (var0 instanceof ExecutableElement) {
         return getDescriptor((ExecutableElement)var0);
      } else {
         return var0 instanceof VariableElement ? getInternalName((VariableElement)var0) : getInternalName(var0.asType());
      }
   }

   public static String getDescriptor(ExecutableElement var0) {
      if (var0 == null) {
         return null;
      } else {
         StringBuilder var1 = new StringBuilder();
         Iterator var2 = var0.getParameters().iterator();

         while(var2.hasNext()) {
            VariableElement var3 = (VariableElement)var2.next();
            var1.append(getInternalName(var3));
         }

         String var4 = getInternalName(var0.getReturnType());
         return String.format("(%s)%s", var1, var4);
      }
   }

   public static String getInternalName(VariableElement var0) {
      return var0 == null ? null : getInternalName(var0.asType());
   }

   public static String getInternalName(TypeMirror var0) {
      switch(var0.getKind()) {
      case ARRAY:
         return "[" + getInternalName(((ArrayType)var0).getComponentType());
      case DECLARED:
         return "L" + getInternalName((DeclaredType)var0) + ";";
      case TYPEVAR:
         return "L" + getInternalName(getUpperBound(var0)) + ";";
      case ERROR:
         return "Ljava/lang/Object;";
      case BOOLEAN:
         return "Z";
      case BYTE:
         return "B";
      case CHAR:
         return "C";
      case DOUBLE:
         return "D";
      case FLOAT:
         return "F";
      case INT:
         return "I";
      case LONG:
         return "J";
      case SHORT:
         return "S";
      case VOID:
         return "V";
      default:
         throw new IllegalArgumentException("Unable to parse type symbol " + var0 + " with " + var0.getKind() + " to equivalent bytecode type");
      }
   }

   public static String getInternalName(DeclaredType var0) {
      return var0 == null ? "java/lang/Object" : getInternalName((TypeElement)var0.asElement());
   }

   public static String getInternalName(TypeElement var0) {
      if (var0 == null) {
         return null;
      } else {
         StringBuilder var1 = new StringBuilder();
         var1.append(var0.getSimpleName());

         for(Element var2 = var0.getEnclosingElement(); var2 != null; var2 = var2.getEnclosingElement()) {
            if (var2 instanceof TypeElement) {
               var1.insert(0, "$").insert(0, var2.getSimpleName());
            } else if (var2 instanceof PackageElement) {
               var1.insert(0, "/").insert(0, ((PackageElement)var2).getQualifiedName().toString().replace('.', '/'));
            }
         }

         return var1.toString();
      }
   }

   private static DeclaredType getUpperBound(TypeMirror var0) {
      try {
         return getUpperBound0(var0, 5);
      } catch (IllegalStateException var2) {
         throw new IllegalArgumentException("Type symbol \"" + var0 + "\" is too complex", var2);
      } catch (IllegalArgumentException var3) {
         throw new IllegalArgumentException("Unable to compute upper bound of type symbol " + var0, var3);
      }
   }

   private static DeclaredType getUpperBound0(TypeMirror var0, int var1) {
      if (var1 == 0) {
         throw new IllegalStateException("Generic symbol \"" + var0 + "\" is too complex, exceeded " + 5 + " iterations attempting to determine upper bound");
      } else if (var0 instanceof DeclaredType) {
         return (DeclaredType)var0;
      } else if (var0 instanceof TypeVariable) {
         try {
            TypeMirror var2 = ((TypeVariable)var0).getUpperBound();
            --var1;
            return getUpperBound0(var2, var1);
         } catch (IllegalStateException var3) {
            throw var3;
         } catch (IllegalArgumentException var4) {
            throw var4;
         } catch (Exception var5) {
            throw new IllegalArgumentException("Unable to compute upper bound of type symbol " + var0);
         }
      } else {
         return null;
      }
   }

   public static boolean isAssignable(ProcessingEnvironment var0, TypeMirror var1, TypeMirror var2) {
      boolean var3 = var0.getTypeUtils().isAssignable(var1, var2);
      if (!var3 && var1 instanceof DeclaredType && var2 instanceof DeclaredType) {
         TypeMirror var4 = toRawType(var0, (DeclaredType)var1);
         TypeMirror var5 = toRawType(var0, (DeclaredType)var2);
         return var0.getTypeUtils().isAssignable(var4, var5);
      } else {
         return var3;
      }
   }

   private static TypeMirror toRawType(ProcessingEnvironment var0, DeclaredType var1) {
      return var0.getElementUtils().getTypeElement(((TypeElement)var1.asElement()).getQualifiedName()).asType();
   }

   public static Visibility getVisibility(Element var0) {
      if (var0 == null) {
         return null;
      } else {
         Iterator var1 = var0.getModifiers().iterator();

         while(var1.hasNext()) {
            Modifier var2 = (Modifier)var1.next();
            switch(var2) {
            case PUBLIC:
               return Visibility.PUBLIC;
            case PROTECTED:
               return Visibility.PROTECTED;
            case PRIVATE:
               return Visibility.PRIVATE;
            }
         }

         return Visibility.PACKAGE;
      }
   }
}
