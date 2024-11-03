package com.viaversion.viaversion.api.type.types.misc;

import com.viaversion.viaversion.api.minecraft.PlayerMessageSignature;
import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public class PlayerMessageSignatureType extends Type<PlayerMessageSignature> {
   public PlayerMessageSignatureType() {
      super(PlayerMessageSignature.class);
   }

   public PlayerMessageSignature read(ByteBuf buffer) throws Exception {
      return new PlayerMessageSignature(Type.UUID.read(buffer), Type.BYTE_ARRAY_PRIMITIVE.read(buffer));
   }

   public void write(ByteBuf buffer, PlayerMessageSignature value) throws Exception {
      Type.UUID.write(buffer, value.uuid());
      Type.BYTE_ARRAY_PRIMITIVE.write(buffer, value.signatureBytes());
   }

   public static final class OptionalPlayerMessageSignatureType extends OptionalType<PlayerMessageSignature> {
      public OptionalPlayerMessageSignatureType() {
         super(Type.PLAYER_MESSAGE_SIGNATURE);
      }
   }
}
