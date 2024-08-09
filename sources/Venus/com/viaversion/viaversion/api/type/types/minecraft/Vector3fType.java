/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.Vector3f;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Vector3fType
extends Type<Vector3f> {
    public Vector3fType() {
        super(Vector3f.class);
    }

    @Override
    public Vector3f read(ByteBuf byteBuf) throws Exception {
        float f = byteBuf.readFloat();
        float f2 = byteBuf.readFloat();
        float f3 = byteBuf.readFloat();
        return new Vector3f(f, f2, f3);
    }

    @Override
    public void write(ByteBuf byteBuf, Vector3f vector3f) throws Exception {
        byteBuf.writeFloat(vector3f.x());
        byteBuf.writeFloat(vector3f.y());
        byteBuf.writeFloat(vector3f.z());
    }

    @Override
    public Object read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }

    @Override
    public void write(ByteBuf byteBuf, Object object) throws Exception {
        this.write(byteBuf, (Vector3f)object);
    }
}

