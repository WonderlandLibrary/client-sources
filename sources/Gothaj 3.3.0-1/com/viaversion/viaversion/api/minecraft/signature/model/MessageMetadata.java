package com.viaversion.viaversion.api.minecraft.signature.model;

import java.time.Instant;
import java.util.UUID;

public class MessageMetadata {
   private final UUID sender;
   private final Instant timestamp;
   private final long salt;

   public MessageMetadata(UUID sender, Instant timestamp, long salt) {
      this.sender = sender;
      this.timestamp = timestamp;
      this.salt = salt;
   }

   public MessageMetadata(UUID sender, long timestamp, long salt) {
      this(sender, Instant.ofEpochMilli(timestamp), salt);
   }

   public UUID sender() {
      return this.sender;
   }

   public Instant timestamp() {
      return this.timestamp;
   }

   public long salt() {
      return this.salt;
   }
}
