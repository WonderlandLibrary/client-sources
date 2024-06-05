package net;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.login.server.S02PacketLoginSuccess;

public class ou {
   public static GameProfile a(S02PacketLoginSuccess var0) {
      return var0.getProfile();
   }
}
