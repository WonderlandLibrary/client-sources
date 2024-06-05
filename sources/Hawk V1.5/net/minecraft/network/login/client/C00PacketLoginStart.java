package net.minecraft.network.login.client;

import com.mojang.authlib.GameProfile;
import java.io.IOException;
import java.util.UUID;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginServer;

public class C00PacketLoginStart implements Packet {
   private static final String __OBFID = "CL_00001379";
   private GameProfile profile;

   public C00PacketLoginStart() {
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.profile = new GameProfile((UUID)null, var1.readStringFromBuffer(16));
   }

   public void func_180773_a(INetHandlerLoginServer var1) {
      var1.processLoginStart(this);
   }

   public C00PacketLoginStart(GameProfile var1) {
      this.profile = var1;
   }

   public void processPacket(INetHandler var1) {
      this.func_180773_a((INetHandlerLoginServer)var1);
   }

   public GameProfile getProfile() {
      return this.profile;
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeString(this.profile.getName());
   }
}
