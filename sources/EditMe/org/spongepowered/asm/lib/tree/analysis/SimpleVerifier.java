package org.spongepowered.asm.lib.tree.analysis;

import java.util.List;
import org.spongepowered.asm.lib.Type;

public class SimpleVerifier extends BasicVerifier {
   private final Type currentClass;
   private final Type currentSuperClass;
   private final List currentClassInterfaces;
   private final boolean isInterface;
   private ClassLoader loader;

   public SimpleVerifier() {
      this((Type)null, (Type)null, false);
   }

   public SimpleVerifier(Type var1, Type var2, boolean var3) {
      this(var1, var2, (List)null, var3);
   }

   public SimpleVerifier(Type var1, Type var2, List var3, boolean var4) {
      this(327680, var1, var2, var3, var4);
   }

   protected SimpleVerifier(int var1, Type var2, Type var3, List var4, boolean var5) {
      super(var1);
      this.loader = this.getClass().getClassLoader();
      this.currentClass = var2;
      this.currentSuperClass = var3;
      this.currentClassInterfaces = var4;
      this.isInterface = var5;
   }

   public void setClassLoader(ClassLoader var1) {
      this.loader = var1;
   }

   public BasicValue newValue(Type var1) {
      if (var1 == null) {
         return BasicValue.UNINITIALIZED_VALUE;
      } else {
         boolean var2 = var1.getSort() == 9;
         if (var2) {
            switch(var1.getElementType().getSort()) {
            case 1:
            case 2:
            case 3:
            case 4:
               return new BasicValue(var1);
            }
         }

         BasicValue var3 = super.newValue(var1);
         if (BasicValue.REFERENCE_VALUE.equals(var3)) {
            if (var2) {
               var3 = this.newValue(var1.getElementType());
               String var4 = var3.getType().getDescriptor();

               for(int var5 = 0; var5 < var1.getDimensions(); ++var5) {
                  var4 = '[' + var4;
               }

               var3 = new BasicValue(Type.getType(var4));
            } else {
               var3 = new BasicValue(var1);
            }
         }

         return var3;
      }
   }

   protected boolean isArrayValue(BasicValue var1) {
      Type var2 = var1.getType();
      return var2 != null && ("Lnull;".equals(var2.getDescriptor()) || var2.getSort() == 9);
   }

   protected BasicValue getElementValue(BasicValue var1) throws AnalyzerException {
      Type var2 = var1.getType();
      if (var2 != null) {
         if (var2.getSort() == 9) {
            return this.newValue(Type.getType(var2.getDescriptor().substring(1)));
         }

         if ("Lnull;".equals(var2.getDescriptor())) {
            return var1;
         }
      }

      throw new Error("Internal error");
   }

   protected boolean isSubTypeOf(BasicValue var1, BasicValue var2) {
      Type var3 = var2.getType();
      Type var4 = var1.getType();
      switch(var3.getSort()) {
      case 5:
      case 6:
      case 7:
      case 8:
         return var4.equals(var3);
      case 9:
      case 10:
         if ("Lnull;".equals(var4.getDescriptor())) {
            return true;
         } else {
            if (var4.getSort() != 10 && var4.getSort() != 9) {
               return false;
            }

            return this.isAssignableFrom(var3, var4);
         }
      default:
         throw new Error("Internal error");
      }
   }

   public BasicValue merge(BasicValue var1, BasicValue var2) {
      if (var1.equals(var2)) {
         return var1;
      } else {
         Type var3 = var1.getType();
         Type var4 = var2.getType();
         if (var3 != null && (var3.getSort() == 10 || var3.getSort() == 9) && var4 != null && (var4.getSort() == 10 || var4.getSort() == 9)) {
            if ("Lnull;".equals(var3.getDescriptor())) {
               return var2;
            } else if ("Lnull;".equals(var4.getDescriptor())) {
               return var1;
            } else if (this.isAssignableFrom(var3, var4)) {
               return var1;
            } else if (this.isAssignableFrom(var4, var3)) {
               return var2;
            } else {
               while(var3 != null && !this.isInterface(var3)) {
                  var3 = this.getSuperClass(var3);
                  if (this.isAssignableFrom(var3, var4)) {
                     return this.newValue(var3);
                  }
               }

               return BasicValue.REFERENCE_VALUE;
            }
         } else {
            return BasicValue.UNINITIALIZED_VALUE;
         }
      }
   }

   protected boolean isInterface(Type var1) {
      return this.currentClass != null && var1.equals(this.currentClass) ? this.isInterface : this.getClass(var1).isInterface();
   }

   protected Type getSuperClass(Type var1) {
      if (this.currentClass != null && var1.equals(this.currentClass)) {
         return this.currentSuperClass;
      } else {
         Class var2 = this.getClass(var1).getSuperclass();
         return var2 == null ? null : Type.getType(var2);
      }
   }

   protected boolean isAssignableFrom(Type var1, Type var2) {
      if (var1.equals(var2)) {
         return true;
      } else if (this.currentClass != null && var1.equals(this.currentClass)) {
         if (this.getSuperClass(var2) == null) {
            return false;
         } else if (!this.isInterface) {
            return this.isAssignableFrom(var1, this.getSuperClass(var2));
         } else {
            return var2.getSort() == 10 || var2.getSort() == 9;
         }
      } else if (this.currentClass != null && var2.equals(this.currentClass)) {
         if (this.isAssignableFrom(var1, this.currentSuperClass)) {
            return true;
         } else {
            if (this.currentClassInterfaces != null) {
               for(int var5 = 0; var5 < this.currentClassInterfaces.size(); ++var5) {
                  Type var4 = (Type)this.currentClassInterfaces.get(var5);
                  if (this.isAssignableFrom(var1, var4)) {
                     return true;
                  }
               }
            }

            return false;
         }
      } else {
         Class var3 = this.getClass(var1);
         if (var3.isInterface()) {
            var3 = Object.class;
         }

         return var3.isAssignableFrom(this.getClass(var2));
      }
   }

   protected Class getClass(Type var1) {
      try {
         return var1.getSort() == 9 ? Class.forName(var1.getDescriptor().replace('/', '.'), false, this.loader) : Class.forName(var1.getClassName(), false, this.loader);
      } catch (ClassNotFoundException var3) {
         throw new RuntimeException(var3.toString());
      }
   }

   public Value merge(Value var1, Value var2) {
      return this.merge((BasicValue)var1, (BasicValue)var2);
   }

   public Value newValue(Type var1) {
      return this.newValue(var1);
   }
}
