package net.minecraft.network.login.server;

import com.mojang.authlib.GameProfile;
import java.io.IOException;
import java.util.UUID;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginClient;

public class S02PacketLoginSuccess implements Packet {
   private GameProfile profile;

   public S02PacketLoginSuccess() {
   }

   public S02PacketLoginSuccess(GameProfile var1) {
      this.profile = var1;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      String var2 = var1.a(36);
      String var3 = var1.a(16);
      UUID var4 = UUID.fromString(var2);
      this.profile = new GameProfile(var4, var3);
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      UUID var2 = this.profile.getId();
      var1.writeString("");
      var1.writeString(this.profile.getName());
   }

   public void processPacket(INetHandlerLoginClient var1) {
      var1.handleLoginSuccess(this);
   }

   public GameProfile getProfile() {
      return this.profile;
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
