package com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.minecraft.PlayerMessageSignature;
import java.util.Arrays;

public final class ReceivedMessagesStorage implements StorableObject {
   private final PlayerMessageSignature[] signatures = new PlayerMessageSignature[5];
   private PlayerMessageSignature lastSignature;
   private int size;
   private int unacknowledged;

   public boolean add(PlayerMessageSignature signature) {
      if (signature.equals(this.lastSignature)) {
         return false;
      } else {
         this.lastSignature = signature;
         PlayerMessageSignature toPush = signature;

         for (int i = 0; i < this.size; i++) {
            PlayerMessageSignature entry = this.signatures[i];
            this.signatures[i] = toPush;
            toPush = entry;
            if (entry.uuid().equals(signature.uuid())) {
               return true;
            }
         }

         if (this.size < this.signatures.length) {
            this.signatures[this.size++] = toPush;
         }

         return true;
      }
   }

   public PlayerMessageSignature[] lastSignatures() {
      return Arrays.copyOf(this.signatures, this.size);
   }

   public int tickUnacknowledged() {
      return this.unacknowledged++;
   }

   public void resetUnacknowledgedCount() {
      this.unacknowledged = 0;
   }
}
