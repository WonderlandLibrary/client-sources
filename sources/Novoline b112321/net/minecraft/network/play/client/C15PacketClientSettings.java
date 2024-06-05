package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer$EnumChatVisibility;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C15PacketClientSettings implements Packet {
   private String lang;
   private int view;
   private EntityPlayer$EnumChatVisibility chatVisibility;
   private boolean enableColors;
   private int modelPartFlags;

   public C15PacketClientSettings() {
   }

   public C15PacketClientSettings(String var1, int var2, EntityPlayer$EnumChatVisibility var3, boolean var4, int var5) {
      this.lang = var1;
      this.view = var2;
      this.chatVisibility = var3;
      this.enableColors = var4;
      this.modelPartFlags = var5;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.lang = var1.a(7);
      this.view = var1.readByte();
      this.chatVisibility = EntityPlayer$EnumChatVisibility.getEnumChatVisibility(var1.readByte());
      this.enableColors = var1.readBoolean();
      this.modelPartFlags = var1.readUnsignedByte();
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeString(this.lang);
      var1.writeByte(this.view);
      var1.writeByte(this.chatVisibility.getChatVisibility());
      var1.writeBoolean(this.enableColors);
      var1.writeByte(this.modelPartFlags);
   }

   public void processPacket(INetHandlerPlayServer var1) {
      var1.processClientSettings(this);
   }

   public String getLang() {
      return this.lang;
   }

   public EntityPlayer$EnumChatVisibility getChatVisibility() {
      return this.chatVisibility;
   }

   public boolean isColorsEnabled() {
      return this.enableColors;
   }

   public int getModelPartFlags() {
      return this.modelPartFlags;
   }

   public int getView() {
      return this.view;
   }
}
