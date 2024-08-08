package org.spongepowered.asm.mixin.gen;

import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.InsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;

public class AccessorGeneratorFieldGetter extends AccessorGeneratorField {
   public AccessorGeneratorFieldGetter(AccessorInfo var1) {
      super(var1);
   }

   public MethodNode generate() {
      MethodNode var1 = this.createMethod(this.targetType.getSize(), this.targetType.getSize());
      if (this.isInstanceField) {
         var1.instructions.add((AbstractInsnNode)(new VarInsnNode(25, 0)));
      }

      int var2 = this.isInstanceField ? 180 : 178;
      var1.instructions.add((AbstractInsnNode)(new FieldInsnNode(var2, this.info.getClassNode().name, this.targetField.name, this.targetField.desc)));
      var1.instructions.add((AbstractInsnNode)(new InsnNode(this.targetType.getOpcode(172))));
      return var1;
   }
}
