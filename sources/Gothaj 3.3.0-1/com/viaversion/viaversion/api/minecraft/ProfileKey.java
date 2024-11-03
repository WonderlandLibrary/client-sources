package com.viaversion.viaversion.api.minecraft;

public final class ProfileKey {
   private final long expiresAt;
   private final byte[] publicKey;
   private final byte[] keySignature;

   public ProfileKey(long expiresAt, byte[] publicKey, byte[] keySignature) {
      this.expiresAt = expiresAt;
      this.publicKey = publicKey;
      this.keySignature = keySignature;
   }

   public long expiresAt() {
      return this.expiresAt;
   }

   public byte[] publicKey() {
      return this.publicKey;
   }

   public byte[] keySignature() {
      return this.keySignature;
   }
}
