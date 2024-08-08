package org.spongepowered.asm.mixin.gen;

import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.FieldNode;

public abstract class AccessorGeneratorField extends AccessorGenerator {
   protected final FieldNode targetField;
   protected final Type targetType;
   protected final boolean isInstanceField;

   public AccessorGeneratorField(AccessorInfo var1) {
      super(var1);
      this.targetField = var1.getTargetField();
      this.targetType = var1.getTargetFieldType();
      this.isInstanceField = (this.targetField.access & 8) == 0;
   }
}
