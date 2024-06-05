package net;

import java.security.PublicKey;
import net.minecraft.network.login.server.S01PacketEncryptionRequest;

public class aiz {
   public static String c(S01PacketEncryptionRequest var0) {
      return var0.getServerId();
   }

   public static PublicKey a(S01PacketEncryptionRequest var0) {
      return var0.getPublicKey();
   }

   public static byte[] b(S01PacketEncryptionRequest var0) {
      return var0.getVerifyToken();
   }
}
