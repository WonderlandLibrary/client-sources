package com.viaversion.viaversion.api.minecraft.signature.model.chain.v1_19_1;

import com.viaversion.viaversion.api.minecraft.signature.util.DataConsumer;
import java.util.UUID;

public class MessageHeader {
   private final byte[] precedingSignature;
   private final UUID sender;

   public MessageHeader(byte[] precedingSignature, UUID sender) {
      this.precedingSignature = precedingSignature;
      this.sender = sender;
   }

   public void update(DataConsumer dataConsumer) {
      if (this.precedingSignature != null) {
         dataConsumer.accept(this.precedingSignature);
      }

      dataConsumer.accept(this.sender);
   }
}
