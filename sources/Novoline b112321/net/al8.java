package net;

import com.google.gson.JsonObject;
import viaversion.viaversion.api.data.UserConnection;
import viaversion.viaversion.protocols.protocol1_9to1_8.chat.ChatRewriter;

public class al8 {
   public static void a(JsonObject var0, UserConnection var1) {
      ChatRewriter.toClient(var0, var1);
   }
}
