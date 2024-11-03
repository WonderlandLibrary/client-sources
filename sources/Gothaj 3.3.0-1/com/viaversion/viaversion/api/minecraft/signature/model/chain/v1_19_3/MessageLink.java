package com.viaversion.viaversion.api.minecraft.signature.model.chain.v1_19_3;

import com.google.common.primitives.Ints;
import com.viaversion.viaversion.api.minecraft.signature.util.DataConsumer;
import java.util.UUID;

public class MessageLink {
   private final int index;
   private final UUID sender;
   private final UUID sessionId;

   public MessageLink(UUID sender, UUID sessionId) {
      this(0, sender, sessionId);
   }

   public MessageLink(int index, UUID sender, UUID sessionId) {
      this.index = index;
      this.sender = sender;
      this.sessionId = sessionId;
   }

   public void update(DataConsumer dataConsumer) {
      dataConsumer.accept(this.sender);
      dataConsumer.accept(this.sessionId);
      dataConsumer.accept(Ints.toByteArray(this.index));
   }

   public MessageLink next() {
      return this.index == Integer.MAX_VALUE ? null : new MessageLink(this.index + 1, this.sender, this.sessionId);
   }
}
