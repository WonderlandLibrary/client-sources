package net;

import java.util.List;
import java.util.UUID;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage$GameProfile;

public class dH {
   public static GameProfileStorage$GameProfile b(GameProfileStorage var0, UUID var1) {
      return var0.get(var1);
   }

   public static List a(GameProfileStorage var0, String var1, boolean var2) {
      return var0.getAllWithPrefix(var1, var2);
   }

   public static GameProfileStorage$GameProfile b(GameProfileStorage var0, String var1, boolean var2) {
      return var0.get(var1, var2);
   }

   public static GameProfileStorage$GameProfile a(GameProfileStorage var0, UUID var1, String var2) {
      return var0.put(var1, var2);
   }

   public static GameProfileStorage$GameProfile a(GameProfileStorage var0, UUID var1) {
      return var0.remove(var1);
   }
}
