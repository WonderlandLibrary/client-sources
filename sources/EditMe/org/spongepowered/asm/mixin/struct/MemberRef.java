package org.spongepowered.asm.mixin.struct;

import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.mixin.transformer.throwables.MixinTransformerError;
import org.spongepowered.asm.util.Bytecode;

public abstract class MemberRef {
   private static final int[] H_OPCODES = new int[]{0, 180, 178, 181, 179, 182, 184, 183, 183, 185};

   public abstract boolean isField();

   public abstract int getOpcode();

   public abstract void setOpcode(int var1);

   public abstract String getOwner();

   public abstract void setOwner(String var1);

   public abstract String getName();

   public abstract void setName(String var1);

   public abstract String getDesc();

   public abstract void setDesc(String var1);

   public String toString() {
      String var1 = Bytecode.getOpcodeName(this.getOpcode());
      return String.format("%s for %s.%s%s%s", var1, this.getOwner(), this.getName(), this.isField() ? ":" : "", this.getDesc());
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof MemberRef)) {
         return false;
      } else {
         MemberRef var2 = (MemberRef)var1;
         return this.getOpcode() == var2.getOpcode() && this.getOwner().equals(var2.getOwner()) && this.getName().equals(var2.getName()) && this.getDesc().equals(var2.getDesc());
      }
   }

   public int hashCode() {
      return this.toString().hashCode();
   }

   static int opcodeFromTag(int var0) {
      return var0 >= 0 && var0 < H_OPCODES.length ? H_OPCODES[var0] : 0;
   }

   static int tagFromOpcode(int var0) {
      for(int var1 = 1; var1 < H_OPCODES.length; ++var1) {
         if (H_OPCODES[var1] == var0) {
            return var1;
         }
      }

      return 0;
   }

   public static final class Handle extends MemberRef {
      private org.spongepowered.asm.lib.Handle handle;

      public Handle(org.spongepowered.asm.lib.Handle var1) {
         this.handle = var1;
      }

      public org.spongepowered.asm.lib.Handle getMethodHandle() {
         return this.handle;
      }

      public boolean isField() {
         switch(this.handle.getTag()) {
         case 1:
         case 2:
         case 3:
         case 4:
            return true;
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
            return false;
         default:
            throw new MixinTransformerError("Invalid tag " + this.handle.getTag() + " for method handle " + this.handle + ".");
         }
      }

      public int getOpcode() {
         int var1 = MemberRef.opcodeFromTag(this.handle.getTag());
         if (var1 == 0) {
            throw new MixinTransformerError("Invalid tag " + this.handle.getTag() + " for method handle " + this.handle + ".");
         } else {
            return var1;
         }
      }

      public void setOpcode(int var1) {
         int var2 = MemberRef.tagFromOpcode(var1);
         if (var2 == 0) {
            throw new MixinTransformerError("Invalid opcode " + Bytecode.getOpcodeName(var1) + " for method handle " + this.handle + ".");
         } else {
            boolean var3 = var2 == 9;
            this.handle = new org.spongepowered.asm.lib.Handle(var2, this.handle.getOwner(), this.handle.getName(), this.handle.getDesc(), var3);
         }
      }

      public String getOwner() {
         return this.handle.getOwner();
      }

      public void setOwner(String var1) {
         boolean var2 = this.handle.getTag() == 9;
         this.handle = new org.spongepowered.asm.lib.Handle(this.handle.getTag(), var1, this.handle.getName(), this.handle.getDesc(), var2);
      }

      public String getName() {
         return this.handle.getName();
      }

      public void setName(String var1) {
         boolean var2 = this.handle.getTag() == 9;
         this.handle = new org.spongepowered.asm.lib.Handle(this.handle.getTag(), this.handle.getOwner(), var1, this.handle.getDesc(), var2);
      }

      public String getDesc() {
         return this.handle.getDesc();
      }

      public void setDesc(String var1) {
         boolean var2 = this.handle.getTag() == 9;
         this.handle = new org.spongepowered.asm.lib.Handle(this.handle.getTag(), this.handle.getOwner(), this.handle.getName(), var1, var2);
      }
   }

   public static final class Field extends MemberRef {
      private static final int OPCODES = 183;
      public final FieldInsnNode insn;

      public Field(FieldInsnNode var1) {
         this.insn = var1;
      }

      public boolean isField() {
         return true;
      }

      public int getOpcode() {
         return this.insn.getOpcode();
      }

      public void setOpcode(int var1) {
         if ((var1 & 183) == 0) {
            throw new IllegalArgumentException("Invalid opcode for field instruction: 0x" + Integer.toHexString(var1));
         } else {
            this.insn.setOpcode(var1);
         }
      }

      public String getOwner() {
         return this.insn.owner;
      }

      public void setOwner(String var1) {
         this.insn.owner = var1;
      }

      public String getName() {
         return this.insn.name;
      }

      public void setName(String var1) {
         this.insn.name = var1;
      }

      public String getDesc() {
         return this.insn.desc;
      }

      public void setDesc(String var1) {
         this.insn.desc = var1;
      }
   }

   public static final class Method extends MemberRef {
      private static final int OPCODES = 191;
      public final MethodInsnNode insn;

      public Method(MethodInsnNode var1) {
         this.insn = var1;
      }

      public boolean isField() {
         return false;
      }

      public int getOpcode() {
         return this.insn.getOpcode();
      }

      public void setOpcode(int var1) {
         if ((var1 & 191) == 0) {
            throw new IllegalArgumentException("Invalid opcode for method instruction: 0x" + Integer.toHexString(var1));
         } else {
            this.insn.setOpcode(var1);
         }
      }

      public String getOwner() {
         return this.insn.owner;
      }

      public void setOwner(String var1) {
         this.insn.owner = var1;
      }

      public String getName() {
         return this.insn.name;
      }

      public void setName(String var1) {
         this.insn.name = var1;
      }

      public String getDesc() {
         return this.insn.desc;
      }

      public void setDesc(String var1) {
         this.insn.desc = var1;
      }
   }
}
