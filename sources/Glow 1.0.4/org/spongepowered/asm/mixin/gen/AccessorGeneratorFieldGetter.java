package org.spongepowered.asm.mixin.gen;

import org.spongepowered.asm.lib.tree.*;

public class AccessorGeneratorFieldGetter extends AccessorGeneratorField
{
    public AccessorGeneratorFieldGetter(final AccessorInfo accessorInfo) {
        super(accessorInfo);
    }
    
    @Override
    public MethodNode generate() {
        final MethodNode method = this.createMethod(this.targetType.getSize(), this.targetType.getSize());
        if (this.isInstanceField) {
            method.instructions.add(new VarInsnNode(25, 0));
        }
        method.instructions.add(new FieldInsnNode(this.isInstanceField ? 180 : 178, this.info.getClassNode().name, this.targetField.name, this.targetField.desc));
        method.instructions.add(new InsnNode(this.targetType.getOpcode(172)));
        return method;
    }
}
