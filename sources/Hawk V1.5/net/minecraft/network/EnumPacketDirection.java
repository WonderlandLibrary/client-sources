package net.minecraft.network;

public enum EnumPacketDirection {
   private static final EnumPacketDirection[] ENUM$VALUES = new EnumPacketDirection[]{SERVERBOUND, CLIENTBOUND};
   private static final String __OBFID = "CL_00002307";
   CLIENTBOUND("CLIENTBOUND", 1);

   private static final EnumPacketDirection[] $VALUES = new EnumPacketDirection[]{SERVERBOUND, CLIENTBOUND};
   SERVERBOUND("SERVERBOUND", 0);

   private EnumPacketDirection(String var3, int var4) {
   }
}
