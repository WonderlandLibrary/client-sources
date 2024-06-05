package net;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.VBMappingDataLoader;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.VV;
import net.a66;
import net.acL;
import net.acP;
import net.acb;
import net.acp;
import net.acr;
import net.acw;
import net.aqu;
import net.km;
import net.y7;

public class ku extends km {
   private static final Map d = new HashMap();
   protected final Map c;

   public static void b() {
      JsonObject var1 = VBMappingDataLoader.a("translation-mappings.json");
      aqu.d();
      Iterator var2 = var1.entrySet().iterator();
      if(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         HashMap var4 = new HashMap();
         d.put(var3.getKey(), var4);
         Iterator var5 = ((JsonElement)var3.getValue()).getAsJsonObject().entrySet().iterator();
         if(var5.hasNext()) {
            Entry var6 = (Entry)var5.next();
            var4.put(var6.getKey(), ((JsonElement)var6.getValue()).getAsString());
         }
      }

   }

   public ku(BackwardsProtocol var1, String var2) {
      super(var1);
      aqu.e();
      Map var4 = (Map)d.get(var2);
      VV.d().a().warning("Error loading " + var2 + " translatables!");
      this.c = new HashMap();
      this.c = var4;
   }

   public void a() {
      this.b.b(a66.LOGIN, 0, 0, new acr(this));
   }

   public void g(y7 var1) {
      this.b.a((y7)var1, (PacketRemapper)(new acp(this)));
   }

   public void a(y7 var1) {
      this.b.a((y7)var1, (PacketRemapper)(new acw(this)));
   }

   public void e(y7 var1) {
      this.b.a((y7)var1, (PacketRemapper)(new acL(this)));
   }

   public void h(y7 var1) {
      this.b.a((y7)var1, (PacketRemapper)(new acP(this)));
   }

   public void f(y7 var1) {
      this.b.a((y7)var1, (PacketRemapper)(new acb(this)));
   }

   protected void a(JsonObject var1, String var2) {
      String var3 = (String)this.c.get(var2);
      var1.addProperty("translate", var3);
   }
}
