package com.viaversion.viabackwards.protocol.protocol1_19to1_19_1.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class NonceStorage implements StorableObject {
   private final byte[] nonce;

   public NonceStorage(@Nullable byte[] nonce) {
      this.nonce = nonce;
   }

   @Nullable
   public byte[] nonce() {
      return this.nonce;
   }
}
