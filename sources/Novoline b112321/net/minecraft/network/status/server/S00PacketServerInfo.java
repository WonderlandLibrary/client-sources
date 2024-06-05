package net.minecraft.network.status.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import net.aON;
import net.ary;
import net.at_;
import net.bg1;
import net.t5;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.network.ServerStatusResponse$MinecraftProtocolVersionIdentifier$Serializer;
import net.minecraft.network.status.INetHandlerStatusClient;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumTypeAdapterFactory;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IChatComponent$Serializer;

public class S00PacketServerInfo implements Packet {
   private static final Gson GSON = (new GsonBuilder()).registerTypeAdapter(at_.class, new ServerStatusResponse$MinecraftProtocolVersionIdentifier$Serializer()).registerTypeAdapter(ary.class, new aON()).registerTypeAdapter(ServerStatusResponse.class, new bg1()).registerTypeHierarchyAdapter(IChatComponent.class, new IChatComponent$Serializer()).registerTypeHierarchyAdapter(ChatStyle.class, new t5()).registerTypeAdapterFactory(new EnumTypeAdapterFactory()).create();
   private ServerStatusResponse response;

   public S00PacketServerInfo() {
   }

   public S00PacketServerInfo(ServerStatusResponse var1) {
      this.response = var1;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.response = (ServerStatusResponse)GSON.fromJson(var1.a(32767), ServerStatusResponse.class);
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeString(GSON.toJson(this.response));
   }

   public void processPacket(INetHandlerStatusClient var1) {
      var1.handleServerInfo(this);
   }

   public ServerStatusResponse getResponse() {
      return this.response;
   }
}
