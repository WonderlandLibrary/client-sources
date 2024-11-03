package com.viaversion.viaversion.api.minecraft;

import java.util.UUID;

public final class PlayerMessageSignature {
   private final UUID uuid;
   private final byte[] signatureBytes;

   public PlayerMessageSignature(UUID uuid, byte[] signatureBytes) {
      this.uuid = uuid;
      this.signatureBytes = signatureBytes;
   }

   public UUID uuid() {
      return this.uuid;
   }

   public byte[] signatureBytes() {
      return this.signatureBytes;
   }
}
