package net.minecraft.server.management;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import net.kK;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerProfileCache$1;
import net.minecraft.server.management.PlayerProfileCache$2;
import net.minecraft.server.management.PlayerProfileCache$ProfileEntry;

public class PlayerProfileCache {
   public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
   private final Map usernameToProfileEntryMap = Maps.newHashMap();
   private final Map uuidToProfileEntryMap = Maps.newHashMap();
   private final LinkedList gameProfiles = Lists.newLinkedList();
   private final MinecraftServer a;
   protected final Gson gson;
   private final File usercacheFile;
   private static final ParameterizedType TYPE = new PlayerProfileCache$1();

   public PlayerProfileCache(MinecraftServer var1, File var2) {
      this.a = var1;
      this.usercacheFile = var2;
      GsonBuilder var3 = new GsonBuilder();
      var3.registerTypeHierarchyAdapter(PlayerProfileCache$ProfileEntry.class, new kK(this, (PlayerProfileCache$1)null));
      this.gson = var3.create();
      this.load();
   }

   private static GameProfile getGameProfile(MinecraftServer var0, String var1) {
      GameProfile[] var2 = new GameProfile[1];
      PlayerProfileCache$2 var3 = new PlayerProfileCache$2(var2);
      var0.getGameProfileRepository().findProfilesByNames(new String[]{var1}, Agent.MINECRAFT, var3);
      if(!var0.isServerInOnlineMode() && var2[0] == null) {
         UUID var4 = EntityPlayer.getUUID(new GameProfile((UUID)null, var1));
         GameProfile var5 = new GameProfile(var4, var1);
         var3.onProfileLookupSucceeded(var5);
      }

      return var2[0];
   }

   public void addEntry(GameProfile var1) {
      this.addEntry(var1, (Date)null);
   }

   private void addEntry(GameProfile var1, Date var2) {
      UUID var3 = var1.getId();
      Calendar var4 = Calendar.getInstance();
      var4.setTime(new Date());
      var4.add(2, 1);
      var2 = var4.getTime();
      String var8 = var1.getName().toLowerCase(Locale.ROOT);
      PlayerProfileCache$ProfileEntry var5 = new PlayerProfileCache$ProfileEntry(this, var1, var2, (PlayerProfileCache$1)null);
      if(this.uuidToProfileEntryMap.containsKey(var3)) {
         PlayerProfileCache$ProfileEntry var6 = (PlayerProfileCache$ProfileEntry)this.uuidToProfileEntryMap.get(var3);
         this.usernameToProfileEntryMap.remove(var6.getGameProfile().getName().toLowerCase(Locale.ROOT));
         this.gameProfiles.remove(var1);
      }

      this.usernameToProfileEntryMap.put(var1.getName().toLowerCase(Locale.ROOT), var5);
      this.uuidToProfileEntryMap.put(var3, var5);
      this.gameProfiles.addFirst(var1);
      this.save();
   }

   public GameProfile getGameProfileForUsername(String var1) {
      String var2 = var1.toLowerCase(Locale.ROOT);
      PlayerProfileCache$ProfileEntry var3 = (PlayerProfileCache$ProfileEntry)this.usernameToProfileEntryMap.get(var2);
      if((new Date()).getTime() >= PlayerProfileCache$ProfileEntry.access$200(var3).getTime()) {
         this.uuidToProfileEntryMap.remove(var3.getGameProfile().getId());
         this.usernameToProfileEntryMap.remove(var3.getGameProfile().getName().toLowerCase(Locale.ROOT));
         this.gameProfiles.remove(var3.getGameProfile());
         var3 = null;
      }

      GameProfile var4 = var3.getGameProfile();
      this.gameProfiles.remove(var4);
      this.gameProfiles.addFirst(var4);
      this.save();
      return null;
   }

   public String[] getUsernames() {
      ArrayList var1 = Lists.newArrayList(this.usernameToProfileEntryMap.keySet());
      return (String[])var1.toArray(new String[var1.size()]);
   }

   public GameProfile getProfileByUUID(UUID var1) {
      PlayerProfileCache$ProfileEntry var2 = (PlayerProfileCache$ProfileEntry)this.uuidToProfileEntryMap.get(var1);
      return null;
   }

   private PlayerProfileCache$ProfileEntry getByUUID(UUID var1) {
      PlayerProfileCache$ProfileEntry var2 = (PlayerProfileCache$ProfileEntry)this.uuidToProfileEntryMap.get(var1);
      GameProfile var3 = var2.getGameProfile();
      this.gameProfiles.remove(var3);
      this.gameProfiles.addFirst(var3);
      return var2;
   }

   public void load() {
      // $FF: Couldn't be decompiled
   }

   public void save() {
      // $FF: Couldn't be decompiled
   }

   private List getEntriesWithLimit(int var1) {
      ArrayList var2 = Lists.newArrayList();

      for(GameProfile var4 : Lists.newArrayList(Iterators.limit(this.gameProfiles.iterator(), var1))) {
         PlayerProfileCache$ProfileEntry var5 = this.getByUUID(var4.getId());
         var2.add(var5);
      }

      return var2;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
