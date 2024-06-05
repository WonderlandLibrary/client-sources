package cc.novoline.modules;

import cc.novoline.Novoline;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.Config;
import cc.novoline.modules.PlayerManager$EnumPlayerType;
import cc.novoline.utils.java.Checks;
import cc.novoline.utils.java.Helpers;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.acE;
import ninja.leaping.configurate.ConfigurationNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class PlayerManager {
   private final Logger logger = LogManager.getLogger();
   private final Config config;
   private final Map players = new Object2ObjectArrayMap();
   private static acE[] b;

   public PlayerManager(@NotNull Novoline var1, @NotNull String var2) {
      this.config = Config.fromPath(var1.getDataFolder().resolve(var2));
      this.config.load();
      this.addPlayersFromConfig();
   }

   private void addPlayersFromConfig() {
      AbstractModule.d();
      PlayerManager$EnumPlayerType[] var2 = PlayerManager$EnumPlayerType.values();
      Set var3 = this.config.getRootNode().getChildrenMap().entrySet();
      Object2ObjectArrayMap var4 = new Object2ObjectArrayMap();
      Iterator var5 = var3.iterator();
      if(var5.hasNext()) {
         Entry var6 = (Entry)var5.next();
         String var7 = var6.getKey().toString().toLowerCase(Locale.ROOT);
         PlayerManager$EnumPlayerType var8 = var2[((ConfigurationNode)var6.getValue()).getInt()];
         var4.put(var7, var8);
      }

      this.players.putAll(var4);
   }

   public List whoHas(@NotNull PlayerManager$EnumPlayerType var1) {
      return (List)this.players.entrySet().stream().filter(PlayerManager::lambda$whoHas$0).map(Entry::getKey).collect(Collectors.toCollection(ObjectArrayList::<init>));
   }

   @Nullable
   public PlayerManager$EnumPlayerType getType(String var1) {
      int[] var2 = AbstractModule.d();
      if(Helpers.isBlank(var1)) {
         return null;
      } else {
         String var3 = var1.toLowerCase(Locale.ROOT);
         return (PlayerManager$EnumPlayerType)this.players.get(var3);
      }
   }

   public boolean hasType(String var1, @NotNull PlayerManager$EnumPlayerType var2) {
      int[] var3 = AbstractModule.d();
      if(Helpers.isBlank(var1)) {
         return false;
      } else {
         String var4 = var1.toLowerCase(Locale.ROOT);
         return this.players.get(var4) == var2;
      }
   }

   public boolean setType(String var1, @NotNull PlayerManager$EnumPlayerType var2) {
      Checks.notBlank(var1, "name");
      AbstractModule.d();
      String var4 = var1.toLowerCase(Locale.ROOT);
      PlayerManager$EnumPlayerType var5 = (PlayerManager$EnumPlayerType)this.players.get(var4);
      if(var5 == var2) {
         return false;
      } else {
         boolean var6 = this.players.put(var4, var2) == var5;
         if(var6) {
            this.updateConfigNode();
         }

         return var6;
      }
   }

   public boolean removeType(@NotNull PlayerManager$EnumPlayerType var1, @NotNull Predicate var2) {
      AbstractModule.d();
      boolean var4 = this.players.entrySet().removeIf(var2);
      if(var4) {
         this.updateConfigNode();
      }

      return var4;
   }

   public boolean removeType(String var1, @NotNull PlayerManager$EnumPlayerType var2) {
      int[] var3 = AbstractModule.d();
      if(Helpers.isBlank(var1)) {
         return false;
      } else {
         String var4 = var1.toLowerCase(Locale.ROOT);
         PlayerManager$EnumPlayerType var5 = (PlayerManager$EnumPlayerType)this.players.get(var4);
         boolean var6 = var5 == var2 && this.players.remove(var4) == var5;
         if(var6) {
            this.updateConfigNode();
         }

         return var6;
      }
   }

   public boolean removePlayer(String var1) {
      AbstractModule.d();
      Checks.notBlank(var1, "name");
      String var3 = var1.toLowerCase(Locale.ROOT);
      boolean var4 = this.players.remove(var3) != null;
      if(var4) {
         this.updateConfigNode();
      }

      return var4;
   }

   private void updateConfigNode() {
      AbstractModule.d();
      ConfigurationNode var2 = this.config.getLoader().createEmptyNode();
      Iterator var3 = this.players.entrySet().iterator();
      if(var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         String var5 = (String)var4.getKey();
         PlayerManager$EnumPlayerType var6 = (PlayerManager$EnumPlayerType)var4.getValue();
         var2.getNode(new Object[]{var5}).setValue(Integer.valueOf(var6.ordinal()));
      }

      this.config.setRootNode(var2);
   }

   public Config getConfig() {
      return this.config;
   }

   public Logger getLogger() {
      return this.logger;
   }

   private static boolean lambda$whoHas$0(PlayerManager$EnumPlayerType var0, Entry var1) {
      return var1.getValue() == var0;
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      b((acE[])null);
   }
}
