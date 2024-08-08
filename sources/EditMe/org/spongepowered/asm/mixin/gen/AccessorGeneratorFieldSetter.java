package org.spongepowered.asm.mixin.gen;

import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.InsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;

public class AccessorGeneratorFieldSetter extends AccessorGeneratorField {
   public AccessorGeneratorFieldSetter(AccessorInfo var1) {
      super(var1);
   }

   public MethodNode generate() {
      int var1 = this.isInstanceField ? 1 : 0;
      int var2 = var1 + this.targetType.getSize();
      int var3 = var1 + this.targetType.getSize();
      MethodNode var4 = this.createMethod(var2, var3);
      if (this.isInstanceField) {
         var4.instructions.add((AbstractInsnNode)(new VarInsnNode(25, 0)));
      }

      var4.instructions.add((AbstractInsnNode)(new VarInsnNode(this.targetType.getOpcode(21), var1)));
      int var5 = this.isInstanceField ? 181 : 179;
      var4.instructions.add((AbstractInsnNode)(new FieldInsnNode(var5, this.info.getClassNode().name, this.targetField.name, this.targetField.desc)));
      var4.instructions.add((AbstractInsnNode)(new InsnNode(177)));
      return var4;
   }
}
