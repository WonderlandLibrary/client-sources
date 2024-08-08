package org.spongepowered.asm.mixin.injection.modify;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.LocalVariableNode;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.Locals;
import org.spongepowered.asm.util.PrettyPrinter;
import org.spongepowered.asm.util.SignaturePrinter;

public class LocalVariableDiscriminator {
   private final boolean argsOnly;
   private final int ordinal;
   private final int index;
   private final Set names;
   private final boolean print;

   public LocalVariableDiscriminator(boolean var1, int var2, int var3, Set var4, boolean var5) {
      this.argsOnly = var1;
      this.ordinal = var2;
      this.index = var3;
      this.names = Collections.unmodifiableSet(var4);
      this.print = var5;
   }

   public boolean isArgsOnly() {
      return this.argsOnly;
   }

   public int getOrdinal() {
      return this.ordinal;
   }

   public int getIndex() {
      return this.index;
   }

   public Set getNames() {
      return this.names;
   }

   public boolean hasNames() {
      return !this.names.isEmpty();
   }

   public boolean printLVT() {
      return this.print;
   }

   protected boolean isImplicit(LocalVariableDiscriminator.Context var1) {
      return this.ordinal < 0 && this.index < var1.baseArgIndex && this.names.isEmpty();
   }

   public int findLocal(Type var1, boolean var2, Target var3, AbstractInsnNode var4) {
      try {
         return this.findLocal(new LocalVariableDiscriminator.Context(var1, var2, var3, var4));
      } catch (InvalidImplicitDiscriminatorException var6) {
         return -2;
      }
   }

   public int findLocal(LocalVariableDiscriminator.Context var1) {
      return this.isImplicit(var1) ? this.findImplicitLocal(var1) : this.findExplicitLocal(var1);
   }

   private int findImplicitLocal(LocalVariableDiscriminator.Context var1) {
      int var2 = 0;
      int var3 = 0;

      for(int var4 = var1.baseArgIndex; var4 < var1.locals.length; ++var4) {
         LocalVariableDiscriminator.Context.Local var5 = var1.locals[var4];
         if (var5 != null && var5.type.equals(var1.returnType)) {
            ++var3;
            var2 = var4;
         }
      }

      if (var3 == 1) {
         return var2;
      } else {
         throw new InvalidImplicitDiscriminatorException("Found " + var3 + " candidate variables but exactly 1 is required.");
      }
   }

   private int findExplicitLocal(LocalVariableDiscriminator.Context var1) {
      for(int var2 = var1.baseArgIndex; var2 < var1.locals.length; ++var2) {
         LocalVariableDiscriminator.Context.Local var3 = var1.locals[var2];
         if (var3 != null && var3.type.equals(var1.returnType)) {
            if (this.ordinal > -1) {
               if (this.ordinal == var3.ord) {
                  return var2;
               }
            } else if (this.index >= var1.baseArgIndex) {
               if (this.index == var2) {
                  return var2;
               }
            } else if (this.names.contains(var3.name)) {
               return var2;
            }
         }
      }

      return -1;
   }

   public static LocalVariableDiscriminator parse(AnnotationNode var0) {
      boolean var1 = (Boolean)Annotations.getValue(var0, "argsOnly", (Object)Boolean.FALSE);
      int var2 = (Integer)Annotations.getValue(var0, "ordinal", (int)-1);
      int var3 = (Integer)Annotations.getValue(var0, "index", (int)-1);
      boolean var4 = (Boolean)Annotations.getValue(var0, "print", (Object)Boolean.FALSE);
      HashSet var5 = new HashSet();
      List var6 = (List)Annotations.getValue(var0, "name", (Object)((List)null));
      if (var6 != null) {
         var5.addAll(var6);
      }

      return new LocalVariableDiscriminator(var1, var2, var3, var5, var4);
   }

   public static class Context implements PrettyPrinter.IPrettyPrintable {
      final Target target;
      final Type returnType;
      final AbstractInsnNode node;
      final int baseArgIndex;
      final LocalVariableDiscriminator.Context.Local[] locals;
      private final boolean isStatic;

      public Context(Type var1, boolean var2, Target var3, AbstractInsnNode var4) {
         this.isStatic = Bytecode.methodIsStatic(var3.method);
         this.returnType = var1;
         this.target = var3;
         this.node = var4;
         this.baseArgIndex = this.isStatic ? 0 : 1;
         this.locals = this.initLocals(var3, var2, var4);
         this.initOrdinals();
      }

      private LocalVariableDiscriminator.Context.Local[] initLocals(Target var1, boolean var2, AbstractInsnNode var3) {
         if (!var2) {
            LocalVariableNode[] var4 = Locals.getLocalsAt(var1.classNode, var1.method, var3);
            if (var4 != null) {
               LocalVariableDiscriminator.Context.Local[] var8 = new LocalVariableDiscriminator.Context.Local[var4.length];

               for(int var9 = 0; var9 < var4.length; ++var9) {
                  if (var4[var9] != null) {
                     var8[var9] = new LocalVariableDiscriminator.Context.Local(this, var4[var9].name, Type.getType(var4[var9].desc));
                  }
               }

               return var8;
            }
         }

         LocalVariableDiscriminator.Context.Local[] var7 = new LocalVariableDiscriminator.Context.Local[this.baseArgIndex + var1.arguments.length];
         if (!this.isStatic) {
            var7[0] = new LocalVariableDiscriminator.Context.Local(this, "this", Type.getType(var1.classNode.name));
         }

         for(int var5 = this.baseArgIndex; var5 < var7.length; ++var5) {
            Type var6 = var1.arguments[var5 - this.baseArgIndex];
            var7[var5] = new LocalVariableDiscriminator.Context.Local(this, "arg" + var5, var6);
         }

         return var7;
      }

      private void initOrdinals() {
         HashMap var1 = new HashMap();

         for(int var2 = 0; var2 < this.locals.length; ++var2) {
            Integer var3 = 0;
            if (this.locals[var2] != null) {
               var3 = (Integer)var1.get(this.locals[var2].type);
               var1.put(this.locals[var2].type, var3 = var3 == null ? 0 : var3 + 1);
               this.locals[var2].ord = var3;
            }
         }

      }

      public void print(PrettyPrinter var1) {
         var1.add("%5s  %7s  %30s  %-50s  %s", "INDEX", "ORDINAL", "TYPE", "NAME", "CANDIDATE");

         for(int var2 = this.baseArgIndex; var2 < this.locals.length; ++var2) {
            LocalVariableDiscriminator.Context.Local var3 = this.locals[var2];
            if (var3 != null) {
               Type var8 = var3.type;
               String var9 = var3.name;
               int var6 = var3.ord;
               String var7 = this.returnType.equals(var8) ? "YES" : "-";
               var1.add("[%3d]    [%3d]  %30s  %-50s  %s", var2, var6, SignaturePrinter.getTypeName(var8, false), var9, var7);
            } else if (var2 > 0) {
               LocalVariableDiscriminator.Context.Local var4 = this.locals[var2 - 1];
               boolean var5 = var4 != null && var4.type != null && var4.type.getSize() > 1;
               var1.add("[%3d]           %30s", var2, var5 ? "<top>" : "-");
            }
         }

      }

      public class Local {
         int ord;
         String name;
         Type type;
         final LocalVariableDiscriminator.Context this$0;

         public Local(LocalVariableDiscriminator.Context var1, String var2, Type var3) {
            this.this$0 = var1;
            this.ord = 0;
            this.name = var2;
            this.type = var3;
         }

         public String toString() {
            return String.format("Local[ordinal=%d, name=%s, type=%s]", this.ord, this.name, this.type);
         }
      }
   }
}
