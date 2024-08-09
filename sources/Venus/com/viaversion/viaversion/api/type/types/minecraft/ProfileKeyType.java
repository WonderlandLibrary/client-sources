/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.ProfileKey;
import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ProfileKeyType
extends Type<ProfileKey> {
    public ProfileKeyType() {
        super(ProfileKey.class);
    }

    @Override
    public ProfileKey read(ByteBuf byteBuf) throws Exception {
        return new ProfileKey(byteBuf.readLong(), (byte[])Type.BYTE_ARRAY_PRIMITIVE.read(byteBuf), (byte[])Type.BYTE_ARRAY_PRIMITIVE.read(byteBuf));
    }

    @Override
    public void write(ByteBuf byteBuf, ProfileKey profileKey) throws Exception {
        byteBuf.writeLong(profileKey.expiresAt());
        Type.BYTE_ARRAY_PRIMITIVE.write(byteBuf, profileKey.publicKey());
        Type.BYTE_ARRAY_PRIMITIVE.write(byteBuf, profileKey.keySignature());
    }

    @Override
    public Object read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }

    @Override
    public void write(ByteBuf byteBuf, Object object) throws Exception {
        this.write(byteBuf, (ProfileKey)object);
    }

    public static final class OptionalProfileKeyType
    extends OptionalType<ProfileKey> {
        public OptionalProfileKeyType() {
            super(Type.PROFILE_KEY);
        }
    }
}

