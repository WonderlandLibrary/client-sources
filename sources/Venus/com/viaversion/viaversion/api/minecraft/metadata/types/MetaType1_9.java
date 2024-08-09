/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.minecraft.metadata.types;

import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.type.Type;

public enum MetaType1_9 implements MetaType
{
    Byte(0, Type.BYTE),
    VarInt(1, Type.VAR_INT),
    Float(2, Type.FLOAT),
    String(3, Type.STRING),
    Chat(4, Type.COMPONENT),
    Slot(5, Type.ITEM),
    Boolean(6, Type.BOOLEAN),
    Vector3F(7, Type.ROTATION),
    Position(8, Type.POSITION),
    OptPosition(9, Type.OPTIONAL_POSITION),
    Direction(10, Type.VAR_INT),
    OptUUID(11, Type.OPTIONAL_UUID),
    BlockID(12, Type.VAR_INT);

    private final int typeID;
    private final Type type;

    private MetaType1_9(int n2, Type type) {
        this.typeID = n2;
        this.type = type;
    }

    public static MetaType1_9 byId(int n) {
        return MetaType1_9.values()[n];
    }

    @Override
    public int typeId() {
        return this.typeID;
    }

    @Override
    public Type type() {
        return this.type;
    }
}

