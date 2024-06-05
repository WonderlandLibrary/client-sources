package net.minecraft.client.multiplayer;

import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public enum ServerData$ServerResourceMode {
   ENABLED("enabled"),
   DISABLED("disabled"),
   PROMPT("prompt");

   private final IChatComponent motd;
   private static final ServerData$ServerResourceMode[] $VALUES = new ServerData$ServerResourceMode[]{ENABLED, DISABLED, PROMPT};

   private ServerData$ServerResourceMode(String var3) {
      this.motd = new ChatComponentTranslation("addServer.resourcePack." + var3, new Object[0]);
   }

   public IChatComponent getMotd() {
      return this.motd;
   }
}
