package org.spongepowered.asm.bridge;

import org.spongepowered.asm.mixin.extensibility.*;
import org.spongepowered.asm.util.*;
import org.objectweb.asm.commons.*;
import org.apache.logging.log4j.*;

public abstract class RemapperAdapter implements IRemapper, ObfuscationUtil.IClassRemapper
{
    protected final Logger logger;
    protected final Remapper remapper;
    
    public RemapperAdapter(final Remapper remapper) {
        super();
        this.logger = LogManager.getLogger("mixin");
        this.remapper = remapper;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
    
    @Override
    public String mapMethodName(final String owner, final String s, final String descriptor) {
        this.logger.debug("{} is remapping method {}{} for {}", new Object[] { this, s, descriptor, owner });
        final String mapMethodName = this.remapper.mapMethodName(owner, s, descriptor);
        if (!mapMethodName.equals(s)) {
            return mapMethodName;
        }
        final String unmap = this.unmap(owner);
        final String unmapDesc = this.unmapDesc(descriptor);
        this.logger.debug("{} is remapping obfuscated method {}{} for {}", new Object[] { this, s, unmapDesc, unmap });
        return this.remapper.mapMethodName(unmap, s, unmapDesc);
    }
    
    @Override
    public String mapFieldName(final String owner, final String s, final String descriptor) {
        this.logger.debug("{} is remapping field {}{} for {}", new Object[] { this, s, descriptor, owner });
        final String mapFieldName = this.remapper.mapFieldName(owner, s, descriptor);
        if (!mapFieldName.equals(s)) {
            return mapFieldName;
        }
        final String unmap = this.unmap(owner);
        final String unmapDesc = this.unmapDesc(descriptor);
        this.logger.debug("{} is remapping obfuscated field {}{} for {}", new Object[] { this, s, unmapDesc, unmap });
        return this.remapper.mapFieldName(unmap, s, unmapDesc);
    }
    
    @Override
    public String map(final String internalName) {
        this.logger.debug("{} is remapping class {}", new Object[] { this, internalName });
        return this.remapper.map(internalName);
    }
    
    @Override
    public String unmap(final String s) {
        return s;
    }
    
    @Override
    public String mapDesc(final String descriptor) {
        return this.remapper.mapDesc(descriptor);
    }
    
    @Override
    public String unmapDesc(final String s) {
        return ObfuscationUtil.unmapDescriptor(s, this);
    }
}
