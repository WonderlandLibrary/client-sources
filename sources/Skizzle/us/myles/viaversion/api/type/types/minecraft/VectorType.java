/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package us.myles.ViaVersion.api.type.types.minecraft;

import io.netty.buffer.ByteBuf;
import us.myles.ViaVersion.api.minecraft.Vector;
import us.myles.ViaVersion.api.type.Type;

public class VectorType
extends Type<Vector> {
    public VectorType() {
        super(Vector.class);
    }

    @Override
    public Vector read(ByteBuf buffer) throws Exception {
        int x = (Integer)Type.INT.read(buffer);
        int y = (Integer)Type.INT.read(buffer);
        int z = (Integer)Type.INT.read(buffer);
        return new Vector(x, y, z);
    }

    @Override
    public void write(ByteBuf buffer, Vector object) throws Exception {
        Type.INT.write(buffer, object.getBlockX());
        Type.INT.write(buffer, object.getBlockY());
        Type.INT.write(buffer, object.getBlockZ());
    }
}

