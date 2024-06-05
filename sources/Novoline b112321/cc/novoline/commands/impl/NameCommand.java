package cc.novoline.commands.impl;

import cc.novoline.Novoline;
import cc.novoline.commands.NovoCommand;
import cc.novoline.commands.impl.NameCommand$1;
import cc.novoline.commands.impl.NameCommand$Name;
import cc.novoline.commands.impl.NameCommand$Player;
import cc.novoline.utils.java.Checks;
import cc.novoline.utils.java.HttpUtils;
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
import net.a_E;
import net.minecraft.util.EnumChatFormatting;

public final class NameCommand extends NovoCommand {
   private final Cache cache = CacheBuilder.newBuilder().expireAfterAccess(5L, TimeUnit.MINUTES).build();
   private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");

   public NameCommand(Novoline var1) {
      super(var1, "name", "names");
   }

   public void process(String[] var1) {
      int[] var2 = a_E.b();
      if(var1.length < 1) {
         this.notifyError("Use .name (name)");
      } else {
         String var3 = var1[0];
         this.print(var3, 0);
      }
   }

   public void print(String var1, int var2) {
      ForkJoinPool.commonPool().execute(this::lambda$print$0);
   }

   public NameCommand$Player getPlayer(String var1) throws ExecutionException {
      Checks.notBlank(var1, "name");
      return (NameCommand$Player)this.cache.get(var1.toLowerCase(), this::lambda$getPlayer$3);
   }

   private String namesToFormattedString(NameCommand$Player var1) {
      a_E.b();
      List var3 = var1.getNames();
      if(var3.isEmpty()) {
         throw new RuntimeException();
      } else if(var3.size() == 1) {
         return "\n  " + EnumChatFormatting.GOLD + EnumChatFormatting.BOLD + ((NameCommand$Name)var3.get(0)).getName() + "\n";
      } else {
         NameCommand$Name var4 = (NameCommand$Name)var3.get(0);
         String var5 = "\n  " + EnumChatFormatting.GOLD.toString() + EnumChatFormatting.BOLD + var4.getName() + EnumChatFormatting.GOLD + " (Original name)\n";
         StringBuilder var6 = new StringBuilder(var5);
         int var7 = 1;
         if(var7 < var3.size() - 1) {
            var4 = (NameCommand$Name)var3.get(var7++);
            var6.append("  ").append(EnumChatFormatting.GOLD).append(" - ").append(var4.getName()).append(" (").append(this.formatTimestamp(var4.getTimestamp())).append(")\n");
         }

         var4 = (NameCommand$Name)var3.get(var7);
         var6.append("  ").append(EnumChatFormatting.GOLD).append(" - ").append(var4.getName()).append(" (").append(this.formatTimestamp(var4.getTimestamp())).append(")");
         return var6.toString();
      }
   }

   private String formatTimestamp(long var1) {
      return this.dateFormatter.format(new Date(var1));
   }

   private NameCommand$Player lambda$getPlayer$3(String var1) throws Exception {
      HttpURLConnection var3 = HttpUtils.createConnection("https://api.mojang.com/users/profiles/minecraft/" + var1);
      a_E.b();
      JsonElement var4 = HttpUtils.parseConnectionInput(var3, this::lambda$null$1, JsonElement.class);
      if(var4 == null) {
         return null;
      } else {
         var4 = var4.getAsJsonObject().get("id");
         if(var4 != null && var4.isJsonPrimitive()) {
            String var5 = var4.getAsString();
            var3 = HttpUtils.createConnection("https://api.mojang.com/user/profiles/" + var5 + "/names");
            JsonArray var6 = (JsonArray)HttpUtils.parseConnectionInput(var3, this::lambda$null$2, JsonArray.class);
            return new NameCommand$Player((List)HttpUtils.getGson().fromJson(var6, (new NameCommand$1(this)).getType()), (NameCommand$1)null);
         } else {
            throw new RuntimeException("Mojang API schema has been changed!");
         }
      }
   }

   private void lambda$null$2(HttpURLConnection var1) {
      this.send(EnumChatFormatting.RED + "Mojang API schema has been changed. Let the developers know!");
   }

   private void lambda$null$1(String var1, HttpURLConnection var2) {
      try {
         this.send(EnumChatFormatting.RED + "Could not find name " + var1 + "! (" + var2.getResponseCode() + ")");
      } catch (IOException var4) {
         this.send(EnumChatFormatting.RED + "Could not find name " + var1 + "!");
         this.getLogger().warn("An I/O error occurred while trying to get response code!", var4);
      }

   }

   private void lambda$print$0(String var1, int var2) {
      int[] var3 = a_E.b();
      NameCommand var10000 = this;
      String var10001 = var1;

      try {
         NameCommand$Player var4 = var10000.getPlayer(var10001);
      } catch (Throwable var5) {
         this.getLogger().warn("An error occurred while trying to reach Mojang API!", var5);
      }
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
