/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna;

import com.sun.jna.FromNativeContext;
import com.sun.jna.Structure;
import java.lang.reflect.Field;

/*
 * Multiple versions of this class in jar - see https://www.benf.org/other/cfr/multi-version-jar.html
 */
public class StructureReadContext
extends FromNativeContext {
    private Structure structure;
    private Field field;

    StructureReadContext(Structure struct, Field field) {
        super(field.getType());
        this.structure = struct;
        this.field = field;
    }

    public Structure getStructure() {
        return this.structure;
    }

    public Field getField() {
        return this.field;
    }
}

