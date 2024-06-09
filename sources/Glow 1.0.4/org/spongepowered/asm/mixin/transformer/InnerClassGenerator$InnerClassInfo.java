package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.lib.commons.*;
import org.spongepowered.asm.service.*;
import java.io.*;

static class InnerClassInfo extends Remapper
{
    private final String name;
    private final String originalName;
    private final MixinInfo owner;
    private final MixinTargetContext target;
    private final String ownerName;
    private final String targetName;
    
    InnerClassInfo(final String name, final String originalName, final MixinInfo owner, final MixinTargetContext target) {
        super();
        this.name = name;
        this.originalName = originalName;
        this.owner = owner;
        this.ownerName = owner.getClassRef();
        this.target = target;
        this.targetName = target.getTargetClassRef();
    }
    
    String getName() {
        return this.name;
    }
    
    String getOriginalName() {
        return this.originalName;
    }
    
    MixinInfo getOwner() {
        return this.owner;
    }
    
    MixinTargetContext getTarget() {
        return this.target;
    }
    
    String getOwnerName() {
        return this.ownerName;
    }
    
    String getTargetName() {
        return this.targetName;
    }
    
    byte[] getClassBytes() throws ClassNotFoundException, IOException {
        return MixinService.getService().getBytecodeProvider().getClassBytes(this.originalName, true);
    }
    
    @Override
    public String mapMethodName(final String s, final String s2, final String s3) {
        if (this.ownerName.equalsIgnoreCase(s)) {
            final ClassInfo.Method method = this.owner.getClassInfo().findMethod(s2, s3, 10);
            if (method != null) {
                return method.getName();
            }
        }
        return super.mapMethodName(s, s2, s3);
    }
    
    @Override
    public String map(final String s) {
        if (this.originalName.equals(s)) {
            return this.name;
        }
        if (this.ownerName.equals(s)) {
            return this.targetName;
        }
        return s;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
}
