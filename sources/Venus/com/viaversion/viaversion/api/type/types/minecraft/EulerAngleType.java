/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.EulerAngle;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class EulerAngleType
extends Type<EulerAngle> {
    public EulerAngleType() {
        super(EulerAngle.class);
    }

    @Override
    public EulerAngle read(ByteBuf byteBuf) throws Exception {
        float f = Type.FLOAT.readPrimitive(byteBuf);
        float f2 = Type.FLOAT.readPrimitive(byteBuf);
        float f3 = Type.FLOAT.readPrimitive(byteBuf);
        return new EulerAngle(f, f2, f3);
    }

    @Override
    public void write(ByteBuf byteBuf, EulerAngle eulerAngle) throws Exception {
        Type.FLOAT.writePrimitive(byteBuf, eulerAngle.x());
        Type.FLOAT.writePrimitive(byteBuf, eulerAngle.y());
        Type.FLOAT.writePrimitive(byteBuf, eulerAngle.z());
    }

    @Override
    public Object read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }

    @Override
    public void write(ByteBuf byteBuf, Object object) throws Exception {
        this.write(byteBuf, (EulerAngle)object);
    }
}

