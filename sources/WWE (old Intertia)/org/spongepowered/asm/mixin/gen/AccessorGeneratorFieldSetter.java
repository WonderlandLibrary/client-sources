package org.spongepowered.asm.mixin.gen;

import org.spongepowered.asm.lib.tree.*;

public class AccessorGeneratorFieldSetter extends AccessorGeneratorField
{
    public AccessorGeneratorFieldSetter(final AccessorInfo accessorInfo) {
        super(accessorInfo);
    }
    
    @Override
    public MethodNode generate() {
        final int isInstanceField = this.isInstanceField ? 1 : 0;
        final MethodNode method = this.createMethod(isInstanceField + this.targetType.getSize(), isInstanceField + this.targetType.getSize());
        if (this.isInstanceField) {
            method.instructions.add(new VarInsnNode(25, 0));
        }
        method.instructions.add(new VarInsnNode(this.targetType.getOpcode(21), isInstanceField));
        method.instructions.add(new FieldInsnNode(this.isInstanceField ? 181 : 179, this.info.getClassNode().name, this.targetField.name, this.targetField.desc));
        method.instructions.add(new InsnNode(177));
        return method;
    }
}
