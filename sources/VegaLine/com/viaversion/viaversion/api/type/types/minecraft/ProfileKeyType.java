/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.ProfileKey;
import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public class ProfileKeyType
extends Type<ProfileKey> {
    public ProfileKeyType() {
        super(ProfileKey.class);
    }

    @Override
    public ProfileKey read(ByteBuf buffer) throws Exception {
        return new ProfileKey(buffer.readLong(), (byte[])Type.BYTE_ARRAY_PRIMITIVE.read(buffer), (byte[])Type.BYTE_ARRAY_PRIMITIVE.read(buffer));
    }

    @Override
    public void write(ByteBuf buffer, ProfileKey object) throws Exception {
        buffer.writeLong(object.expiresAt());
        Type.BYTE_ARRAY_PRIMITIVE.write(buffer, object.publicKey());
        Type.BYTE_ARRAY_PRIMITIVE.write(buffer, object.keySignature());
    }

    public static final class OptionalProfileKeyType
    extends OptionalType<ProfileKey> {
        public OptionalProfileKeyType() {
            super(Type.PROFILE_KEY);
        }
    }
}

