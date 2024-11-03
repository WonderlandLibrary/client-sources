package com.viaversion.viaversion.api.type.types.misc;

import com.viaversion.viaversion.api.minecraft.ProfileKey;
import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public class ProfileKeyType extends Type<ProfileKey> {
   public ProfileKeyType() {
      super(ProfileKey.class);
   }

   public ProfileKey read(ByteBuf buffer) throws Exception {
      return new ProfileKey(buffer.readLong(), Type.BYTE_ARRAY_PRIMITIVE.read(buffer), Type.BYTE_ARRAY_PRIMITIVE.read(buffer));
   }

   public void write(ByteBuf buffer, ProfileKey object) throws Exception {
      buffer.writeLong(object.expiresAt());
      Type.BYTE_ARRAY_PRIMITIVE.write(buffer, object.publicKey());
      Type.BYTE_ARRAY_PRIMITIVE.write(buffer, object.keySignature());
   }

   public static final class OptionalProfileKeyType extends OptionalType<ProfileKey> {
      public OptionalProfileKeyType() {
         super(Type.PROFILE_KEY);
      }
   }
}
