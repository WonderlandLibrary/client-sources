package com.viaversion.viaversion.api.command;

import java.util.UUID;

public interface ViaCommandSender {
   boolean hasPermission(String var1);

   void sendMessage(String var1);

   UUID getUUID();

   String getName();
}
