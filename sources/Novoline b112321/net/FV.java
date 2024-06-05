package net;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import net.FJ;
import net.G4;
import net.Ql;
import net.a0h;
import net.a_E;
import net.ajV;
import net.atv;
import net.gZ;
import net.minecraft.util.EnumChatFormatting;

public final class FV extends FJ {
   private final Cache k = CacheBuilder.newBuilder().expireAfterAccess(5L, TimeUnit.MINUTES).build();
   private final SimpleDateFormat j = new SimpleDateFormat("dd.MM.yyyy");

   public FV(gZ var1) {
      super(var1, "name", "names");
   }

   public void b(String[] var1) {
      int[] var2 = a_E.b();
      if(var1.length < 1) {
         this.f("Use .name (name)");
      } else {
         String var3 = var1[0];
         this.d(var3, 0);
      }
   }

   public void d(String var1, int var2) {
      ForkJoinPool.commonPool().execute(this::lambda$print$0);
   }

   public G4 a(String var1) throws ExecutionException {
      Ql.a(var1, "name");
      return (G4)this.k.get(var1.toLowerCase(), this::lambda$getPlayer$3);
   }

   private String a(G4 var1) {
      a_E.b();
      List var3 = var1.a();
      if(var3.isEmpty()) {
         throw new RuntimeException();
      } else if(var3.size() == 1) {
         return "\n  " + EnumChatFormatting.GOLD + EnumChatFormatting.BOLD + ((a0h)var3.get(0)).a() + "\n";
      } else {
         a0h var4 = (a0h)var3.get(0);
         String var5 = "\n  " + EnumChatFormatting.GOLD.toString() + EnumChatFormatting.BOLD + var4.a() + EnumChatFormatting.GOLD + " (Original name)\n";
         StringBuilder var6 = new StringBuilder(var5);
         int var7 = 1;
         if(var7 < var3.size() - 1) {
            var4 = (a0h)var3.get(var7++);
            var6.append("  ").append(EnumChatFormatting.GOLD).append(" - ").append(var4.a()).append(" (").append(this.a(var4.c())).append(")\n");
         }

         var4 = (a0h)var3.get(var7);
         var6.append("  ").append(EnumChatFormatting.GOLD).append(" - ").append(var4.a()).append(" (").append(this.a(var4.c())).append(")");
         return var6.toString();
      }
   }

   private String a(long var1) {
      return this.j.format(new Date(var1));
   }

   private G4 lambda$getPlayer$3(String var1) throws Exception {
      HttpURLConnection var3 = ajV.a("https://api.mojang.com/users/profiles/minecraft/" + var1);
      a_E.b();
      JsonElement var4 = ajV.a(var3, this::lambda$null$1, JsonElement.class);
      if(var4 == null) {
         return null;
      } else {
         var4 = var4.getAsJsonObject().get("id");
         if(var4 != null && var4.isJsonPrimitive()) {
            String var5 = var4.getAsString();
            var3 = ajV.a("https://api.mojang.com/user/profiles/" + var5 + "/names");
            JsonArray var6 = (JsonArray)ajV.a(var3, this::lambda$null$2, JsonArray.class);
            return new G4((List)ajV.a().fromJson(var6, (new atv(this)).getType()), (atv)null);
         } else {
            throw new RuntimeException("Mojang API schema has been changed!");
         }
      }
   }

   private void lambda$null$2(HttpURLConnection var1) {
      this.c(EnumChatFormatting.RED + "Mojang API schema has been changed. Let the developers know!");
   }

   private void lambda$null$1(String var1, HttpURLConnection var2) {
      try {
         this.c(EnumChatFormatting.RED + "Could not find name " + var1 + "! (" + var2.getResponseCode() + ")");
      } catch (IOException var4) {
         this.c(EnumChatFormatting.RED + "Could not find name " + var1 + "!");
         this.d().warn("An I/O error occurred while trying to get response code!", var4);
      }

   }

   private void lambda$print$0(String var1, int var2) {
      int[] var3 = a_E.b();
      FV var10000 = this;
      String var10001 = var1;

      try {
         G4 var4 = var10000.a(var10001);
      } catch (Throwable var5) {
         this.d().warn("An error occurred while trying to reach Mojang API!", var5);
      }
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
