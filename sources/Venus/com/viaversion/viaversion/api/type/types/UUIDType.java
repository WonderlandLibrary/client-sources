/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import java.util.UUID;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class UUIDType
extends Type<UUID> {
    public UUIDType() {
        super(UUID.class);
    }

    @Override
    public UUID read(ByteBuf byteBuf) {
        return new UUID(byteBuf.readLong(), byteBuf.readLong());
    }

    @Override
    public void write(ByteBuf byteBuf, UUID uUID) {
        byteBuf.writeLong(uUID.getMostSignificantBits());
        byteBuf.writeLong(uUID.getLeastSignificantBits());
    }

    @Override
    public Object read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }

    @Override
    public void write(ByteBuf byteBuf, Object object) throws Exception {
        this.write(byteBuf, (UUID)object);
    }

    public static final class OptionalUUIDType
    extends OptionalType<UUID> {
        public OptionalUUIDType() {
            super(Type.UUID);
        }
    }
}

