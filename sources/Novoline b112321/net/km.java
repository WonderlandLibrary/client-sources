package net;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import java.util.Iterator;
import net.a7B;
import net.a7m;
import net.a7u;
import viaversion.viaversion.api.Via;
import viaversion.viaversion.api.protocol.ClientboundPacketType;
import viaversion.viaversion.api.protocol.Protocol;
import viaversion.viaversion.api.rewriters.ComponentRewriter$1;
import viaversion.viaversion.api.rewriters.MetadataRewriter;
import viaversion.viaversion.util.GsonUtil;

public class km {
   protected final Protocol b;
   private static int a;

   public km(Protocol var1) {
      this.b = var1;
   }

   public km() {
      this.b = null;
   }

   public void registerChatMessage(ClientboundPacketType var1) {
      this.b.a((ClientboundPacketType)var1, new ComponentRewriter$1(this));
   }

   public void b(ClientboundPacketType var1) {
      this.b.a((ClientboundPacketType)var1, new a7B(this));
   }

   public void c(ClientboundPacketType var1) {
      this.b.a((ClientboundPacketType)var1, new a7m(this));
   }

   public void d(ClientboundPacketType var1) {
      this.b.a((ClientboundPacketType)var1, new a7u(this));
   }

   public JsonElement a(String var1) {
      boolean var2 = MetadataRewriter.c();

      try {
         JsonElement var3 = GsonUtil.getJsonParser().parse(var1);
         this.processText(var3);
         return var3;
      } catch (JsonSyntaxException var4) {
         if(Via.getManager().isDebug()) {
            Via.getPlatform().getLogger().severe("Error when trying to parse json: " + var1);
            throw var4;
         } else {
            return new JsonPrimitive(var1);
         }
      }
   }

   public void processText(JsonElement var1) {
      boolean var2 = MetadataRewriter.c();
      if(var1 != null && !var1.isJsonNull()) {
         if(var1.isJsonArray()) {
            this.b(var1);
         } else if(var1.isJsonPrimitive()) {
            this.a(var1.getAsJsonPrimitive());
         } else {
            JsonObject var3 = var1.getAsJsonObject();
            JsonPrimitive var4 = var3.getAsJsonPrimitive("text");
            if(var4 != null) {
               this.a(var4);
            }

            JsonElement var5 = var3.get("translate");
            if(var5 != null) {
               this.handleTranslate(var3, var5.getAsString());
               JsonElement var6 = var3.get("with");
               if(var6 != null) {
                  this.b(var6);
               }
            }

            JsonElement var8 = var3.get("extra");
            if(var8 != null) {
               this.b(var8);
            }

            JsonObject var7 = var3.getAsJsonObject("hoverEvent");
            this.a(var7);
         }
      }
   }

   protected void a(JsonPrimitive var1) {
   }

   protected void handleTranslate(JsonObject var1, String var2) {
   }

   protected void a(JsonObject var1) {
      MetadataRewriter.c();
      String var3 = var1.getAsJsonPrimitive("action").getAsString();
      if(var3.equals("show_text")) {
         JsonElement var4 = var1.get("value");
         this.processText(var4 != null?var4:var1.get("contents"));
      }

      if(var3.equals("show_entity")) {
         JsonObject var5 = var1.getAsJsonObject("contents");
         this.processText(var5.get("name"));
      }

   }

   private void b(JsonElement var1) {
      MetadataRewriter.c();
      Iterator var3 = var1.getAsJsonArray().iterator();
      if(var3.hasNext()) {
         JsonElement var4 = (JsonElement)var3.next();
         this.processText(var4);
      }

   }

   public Protocol a() {
      return this.b;
   }

   public static void b(int var0) {
      a = var0;
   }

   public static int c() {
      return a;
   }

   public static int d() {
      int var0 = c();
      return 80;
   }

   private static JsonSyntaxException a(JsonSyntaxException var0) {
      return var0;
   }

   static {
      b(0);
   }
}
