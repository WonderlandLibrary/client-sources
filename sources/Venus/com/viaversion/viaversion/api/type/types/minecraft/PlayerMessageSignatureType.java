/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.PlayerMessageSignature;
import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import java.util.UUID;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class PlayerMessageSignatureType
extends Type<PlayerMessageSignature> {
    public PlayerMessageSignatureType() {
        super(PlayerMessageSignature.class);
    }

    @Override
    public PlayerMessageSignature read(ByteBuf byteBuf) throws Exception {
        return new PlayerMessageSignature((UUID)Type.UUID.read(byteBuf), (byte[])Type.BYTE_ARRAY_PRIMITIVE.read(byteBuf));
    }

    @Override
    public void write(ByteBuf byteBuf, PlayerMessageSignature playerMessageSignature) throws Exception {
        Type.UUID.write(byteBuf, playerMessageSignature.uuid());
        Type.BYTE_ARRAY_PRIMITIVE.write(byteBuf, playerMessageSignature.signatureBytes());
    }

    @Override
    public Object read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }

    @Override
    public void write(ByteBuf byteBuf, Object object) throws Exception {
        this.write(byteBuf, (PlayerMessageSignature)object);
    }

    public static final class OptionalPlayerMessageSignatureType
    extends OptionalType<PlayerMessageSignature> {
        public OptionalPlayerMessageSignatureType() {
            super(Type.PLAYER_MESSAGE_SIGNATURE);
        }
    }
}

