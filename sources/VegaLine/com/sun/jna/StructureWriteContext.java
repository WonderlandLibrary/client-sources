/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna;

import com.sun.jna.Structure;
import com.sun.jna.ToNativeContext;
import java.lang.reflect.Field;

/*
 * Multiple versions of this class in jar - see https://www.benf.org/other/cfr/multi-version-jar.html
 */
public class StructureWriteContext
extends ToNativeContext {
    private Structure struct;
    private Field field;

    StructureWriteContext(Structure struct, Field field) {
        this.struct = struct;
        this.field = field;
    }

    public Structure getStructure() {
        return this.struct;
    }

    public Field getField() {
        return this.field;
    }
}

