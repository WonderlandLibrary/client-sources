package net;

import com.google.gson.JsonElement;
import viaversion.viaversion.api.data.UserConnection;
import viaversion.viaversion.protocols.protocol1_12to1_11_1.TranslateRewriter;

public class a6w {
   public static void a(JsonElement var0, UserConnection var1) {
      TranslateRewriter.toClient(var0, var1);
   }
}
