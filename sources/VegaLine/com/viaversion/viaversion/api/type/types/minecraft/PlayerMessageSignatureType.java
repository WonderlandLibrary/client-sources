/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.PlayerMessageSignature;
import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import java.util.UUID;

public class PlayerMessageSignatureType
extends Type<PlayerMessageSignature> {
    public PlayerMessageSignatureType() {
        super(PlayerMessageSignature.class);
    }

    @Override
    public PlayerMessageSignature read(ByteBuf buffer) throws Exception {
        return new PlayerMessageSignature((UUID)Type.UUID.read(buffer), (byte[])Type.BYTE_ARRAY_PRIMITIVE.read(buffer));
    }

    @Override
    public void write(ByteBuf buffer, PlayerMessageSignature value) throws Exception {
        Type.UUID.write(buffer, value.uuid());
        Type.BYTE_ARRAY_PRIMITIVE.write(buffer, value.signatureBytes());
    }

    public static final class OptionalPlayerMessageSignatureType
    extends OptionalType<PlayerMessageSignature> {
        public OptionalPlayerMessageSignatureType() {
            super(Type.PLAYER_MESSAGE_SIGNATURE);
        }
    }
}

