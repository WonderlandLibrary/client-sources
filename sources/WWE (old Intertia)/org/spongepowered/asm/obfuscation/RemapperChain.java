package org.spongepowered.asm.obfuscation;

import org.spongepowered.asm.mixin.extensibility.*;
import java.util.*;

public class RemapperChain implements IRemapper
{
    private final List<IRemapper> remappers;
    
    public RemapperChain() {
        super();
        this.remappers = new ArrayList<IRemapper>();
    }
    
    @Override
    public String toString() {
        return String.format("RemapperChain[%d]", this.remappers.size());
    }
    
    public RemapperChain add(final IRemapper remapper) {
        this.remappers.add(remapper);
        return this;
    }
    
    @Override
    public String mapMethodName(final String s, String s2, final String s3) {
        final Iterator<IRemapper> iterator = this.remappers.iterator();
        while (iterator.hasNext()) {
            final String mapMethodName = iterator.next().mapMethodName(s, s2, s3);
            if (mapMethodName != null && !mapMethodName.equals(s2)) {
                s2 = mapMethodName;
            }
        }
        return s2;
    }
    
    @Override
    public String mapFieldName(final String s, String s2, final String s3) {
        final Iterator<IRemapper> iterator = this.remappers.iterator();
        while (iterator.hasNext()) {
            final String mapFieldName = iterator.next().mapFieldName(s, s2, s3);
            if (mapFieldName != null && !mapFieldName.equals(s2)) {
                s2 = mapFieldName;
            }
        }
        return s2;
    }
    
    @Override
    public String map(String s) {
        final Iterator<IRemapper> iterator = this.remappers.iterator();
        while (iterator.hasNext()) {
            final String map = iterator.next().map(s);
            if (map != null && !map.equals(s)) {
                s = map;
            }
        }
        return s;
    }
    
    @Override
    public String unmap(String s) {
        final Iterator<IRemapper> iterator = this.remappers.iterator();
        while (iterator.hasNext()) {
            final String unmap = iterator.next().unmap(s);
            if (unmap != null && !unmap.equals(s)) {
                s = unmap;
            }
        }
        return s;
    }
    
    @Override
    public String mapDesc(String s) {
        final Iterator<IRemapper> iterator = this.remappers.iterator();
        while (iterator.hasNext()) {
            final String mapDesc = iterator.next().mapDesc(s);
            if (mapDesc != null && !mapDesc.equals(s)) {
                s = mapDesc;
            }
        }
        return s;
    }
    
    @Override
    public String unmapDesc(String s) {
        final Iterator<IRemapper> iterator = this.remappers.iterator();
        while (iterator.hasNext()) {
            final String unmapDesc = iterator.next().unmapDesc(s);
            if (unmapDesc != null && !unmapDesc.equals(s)) {
                s = unmapDesc;
            }
        }
        return s;
    }
}
