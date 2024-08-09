/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.Quaternion;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class QuaternionType
extends Type<Quaternion> {
    public QuaternionType() {
        super(Quaternion.class);
    }

    @Override
    public Quaternion read(ByteBuf byteBuf) throws Exception {
        float f = byteBuf.readFloat();
        float f2 = byteBuf.readFloat();
        float f3 = byteBuf.readFloat();
        float f4 = byteBuf.readFloat();
        return new Quaternion(f, f2, f3, f4);
    }

    @Override
    public void write(ByteBuf byteBuf, Quaternion quaternion) throws Exception {
        byteBuf.writeFloat(quaternion.x());
        byteBuf.writeFloat(quaternion.y());
        byteBuf.writeFloat(quaternion.z());
        byteBuf.writeFloat(quaternion.w());
    }

    @Override
    public Object read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }

    @Override
    public void write(ByteBuf byteBuf, Object object) throws Exception {
        this.write(byteBuf, (Quaternion)object);
    }
}

