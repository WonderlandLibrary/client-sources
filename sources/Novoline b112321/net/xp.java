package net;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.List;
import net.a7G;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.Via;
import viaversion.viaversion.api.protocol.ProtocolRegistry;
import viaversion.viaversion.api.protocol.ProtocolVersion;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.base.ProtocolInfo;
import viaversion.viaversion.protocols.base.VersionProvider;
import viaversion.viaversion.util.GsonUtil;

class xp implements PacketHandler {
   final a7G a;

   xp(a7G var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      ProtocolInfo.d();
      ProtocolInfo var3 = var1.user().getProtocolInfo();
      String var4 = (String)var1.get(Type.STRING, 0);

      try {
         Object var5;
         JsonObject var6;
         int var7;
         label25: {
            var5 = (JsonElement)GsonUtil.getGson().fromJson(var4, JsonElement.class);
            var7 = 0;
            if(((JsonElement)var5).isJsonObject()) {
               if(((JsonElement)var5).getAsJsonObject().has("version")) {
                  var6 = ((JsonElement)var5).getAsJsonObject().get("version").getAsJsonObject();
                  if(!var6.has("protocol")) {
                     break label25;
                  }

                  var7 = Long.valueOf(var6.get("protocol").getAsLong()).intValue();
               }

               ((JsonElement)var5).getAsJsonObject().add("version", new JsonObject());
            }

            var5 = new JsonObject();
            ((JsonElement)var5).getAsJsonObject().add("version", var6 = new JsonObject());
         }

         if(Via.getConfig().isSendSupportedVersions()) {
            var6.add("supportedVersions", GsonUtil.getGson().toJsonTree(Via.getAPI().getSupportedVersions()));
         }

         if(ProtocolRegistry.SERVER_PROTOCOL == -1) {
            ProtocolRegistry.SERVER_PROTOCOL = ProtocolVersion.getProtocol(var7).getVersion();
         }

         VersionProvider var8 = (VersionProvider)Via.getManager().f().b(VersionProvider.class);
         if(var8 == null) {
            var1.user().setActive(false);
            return;
         }

         int var9 = var8.getServerProtocol(var1.user());
         List var10 = null;
         if(var3.getProtocolVersion() >= var9 || Via.getPlatform().isOldClientsAllowed()) {
            var10 = ProtocolRegistry.getProtocolPath(var3.getProtocolVersion(), var9);
         }

         if(var7 == var9 || var7 == 0) {
            ProtocolVersion var11 = ProtocolVersion.getProtocol(var3.getProtocolVersion());
            var6.addProperty("protocol", Integer.valueOf(var11.getOriginalVersion()));
            var1.user().setActive(false);
         }

         if(Via.getConfig().getBlockedProtocols().contains(var3.getProtocolVersion())) {
            var6.addProperty("protocol", Integer.valueOf(-1));
         }

         var1.set(Type.STRING, 0, GsonUtil.getGson().toJson((JsonElement)var5));
      } catch (JsonParseException var12) {
         var12.printStackTrace();
      }

   }

   private static JsonParseException a(JsonParseException var0) {
      return var0;
   }
}
