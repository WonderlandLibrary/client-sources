package net;

import java.util.UUID;
import viaversion.viaversion.api.data.UserConnection;
import viaversion.viaversion.api.platform.ViaConnectionManager;

public class id {
   public static void a(ViaConnectionManager var0, UserConnection var1) {
      var0.onLoginSuccess(var1);
   }

   public static UserConnection a(ViaConnectionManager var0, UUID var1) {
      return var0.getConnectedClient(var1);
   }
}
