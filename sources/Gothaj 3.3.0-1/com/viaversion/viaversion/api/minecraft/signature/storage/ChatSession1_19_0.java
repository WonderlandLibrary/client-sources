package com.viaversion.viaversion.api.minecraft.signature.storage;

import com.viaversion.viaversion.api.minecraft.ProfileKey;
import com.viaversion.viaversion.api.minecraft.signature.model.DecoratableMessage;
import com.viaversion.viaversion.api.minecraft.signature.model.MessageMetadata;
import com.viaversion.viaversion.libs.mcstructs.text.utils.JsonUtils;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.util.UUID;

public class ChatSession1_19_0 extends ChatSession {
   public ChatSession1_19_0(UUID uuid, PrivateKey privateKey, ProfileKey profileKey) {
      super(uuid, privateKey, profileKey);
   }

   public byte[] signChatMessage(MessageMetadata metadata, DecoratableMessage content) throws SignatureException {
      return this.sign(signer -> {
         byte[] data = new byte[32];
         ByteBuffer buffer = ByteBuffer.wrap(data).order(ByteOrder.BIG_ENDIAN);
         buffer.putLong(metadata.salt());
         buffer.putLong(metadata.sender().getMostSignificantBits()).putLong(metadata.sender().getLeastSignificantBits());
         buffer.putLong(metadata.timestamp().getEpochSecond());
         signer.accept(data);
         signer.accept(JsonUtils.toSortedString(content.decorated(), null).getBytes(StandardCharsets.UTF_8));
      });
   }
}
