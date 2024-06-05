package net.minecraft.server.management;

import com.google.common.base.Charsets;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.ProfileLookupCallback;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import org.apache.commons.io.IOUtils;

public class PlayerProfileCache {
   private static final String __OBFID = "CL_00001888";
   private final Map field_152662_d = Maps.newHashMap();
   private static final ParameterizedType field_152666_h = new ParameterizedType() {
      private static final String __OBFID = "CL_00001886";

      public Type getOwnerType() {
         return null;
      }

      public Type[] getActualTypeArguments() {
         return new Type[]{PlayerProfileCache.ProfileEntry.class};
      }

      public Type getRawType() {
         return List.class;
      }
   };
   public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
   protected final Gson gson;
   private final File usercacheFile;
   private final LinkedList field_152663_e = Lists.newLinkedList();
   private final MinecraftServer field_152664_f;
   private final Map field_152661_c = Maps.newHashMap();

   public void func_152649_a(GameProfile var1) {
      this.func_152651_a(var1, (Date)null);
   }

   public PlayerProfileCache(MinecraftServer var1, File var2) {
      this.field_152664_f = var1;
      this.usercacheFile = var2;
      GsonBuilder var3 = new GsonBuilder();
      var3.registerTypeHierarchyAdapter(PlayerProfileCache.ProfileEntry.class, new PlayerProfileCache.Serializer(this, (Object)null));
      this.gson = var3.create();
      this.func_152657_b();
   }

   public String[] func_152654_a() {
      ArrayList var1 = Lists.newArrayList(this.field_152661_c.keySet());
      return (String[])var1.toArray(new String[var1.size()]);
   }

   private static GameProfile func_152650_a(MinecraftServer var0, String var1) {
      GameProfile[] var2 = new GameProfile[1];
      ProfileLookupCallback var3 = new ProfileLookupCallback(var2) {
         private static final String __OBFID = "CL_00001887";
         private final GameProfile[] val$var2;

         public void onProfileLookupSucceeded(GameProfile var1) {
            this.val$var2[0] = var1;
         }

         public void onProfileLookupFailed(GameProfile var1, Exception var2) {
            this.val$var2[0] = null;
         }

         {
            this.val$var2 = var1;
         }
      };
      var0.getGameProfileRepository().findProfilesByNames(new String[]{var1}, Agent.MINECRAFT, var3);
      if (!var0.isServerInOnlineMode() && var2[0] == null) {
         UUID var4 = EntityPlayer.getUUID(new GameProfile((UUID)null, var1));
         GameProfile var5 = new GameProfile(var4, var1);
         var3.onProfileLookupSucceeded(var5);
      }

      return var2[0];
   }

   private List func_152656_a(int var1) {
      ArrayList var2 = Lists.newArrayList();
      ArrayList var3 = Lists.newArrayList(Iterators.limit(this.field_152663_e.iterator(), var1));
      Iterator var4 = var3.iterator();

      while(var4.hasNext()) {
         GameProfile var5 = (GameProfile)var4.next();
         PlayerProfileCache.ProfileEntry var6 = this.func_152653_b(var5.getId());
         if (var6 != null) {
            var2.add(var6);
         }
      }

      return var2;
   }

   public GameProfile getGameProfileForUsername(String var1) {
      String var2 = var1.toLowerCase(Locale.ROOT);
      PlayerProfileCache.ProfileEntry var3 = (PlayerProfileCache.ProfileEntry)this.field_152661_c.get(var2);
      if (var3 != null && (new Date()).getTime() >= PlayerProfileCache.ProfileEntry.access$0(var3).getTime()) {
         this.field_152662_d.remove(var3.func_152668_a().getId());
         this.field_152661_c.remove(var3.func_152668_a().getName().toLowerCase(Locale.ROOT));
         this.field_152663_e.remove(var3.func_152668_a());
         var3 = null;
      }

      GameProfile var4;
      if (var3 != null) {
         var4 = var3.func_152668_a();
         this.field_152663_e.remove(var4);
         this.field_152663_e.addFirst(var4);
      } else {
         var4 = func_152650_a(this.field_152664_f, var2);
         if (var4 != null) {
            this.func_152649_a(var4);
            var3 = (PlayerProfileCache.ProfileEntry)this.field_152661_c.get(var2);
         }
      }

      this.func_152658_c();
      return var3 == null ? null : var3.func_152668_a();
   }

   private PlayerProfileCache.ProfileEntry func_152653_b(UUID var1) {
      PlayerProfileCache.ProfileEntry var2 = (PlayerProfileCache.ProfileEntry)this.field_152662_d.get(var1);
      if (var2 != null) {
         GameProfile var3 = var2.func_152668_a();
         this.field_152663_e.remove(var3);
         this.field_152663_e.addFirst(var3);
      }

      return var2;
   }

   public GameProfile func_152652_a(UUID var1) {
      PlayerProfileCache.ProfileEntry var2 = (PlayerProfileCache.ProfileEntry)this.field_152662_d.get(var1);
      return var2 == null ? null : var2.func_152668_a();
   }

   private void func_152651_a(GameProfile var1, Date var2) {
      UUID var3 = var1.getId();
      if (var2 == null) {
         Calendar var4 = Calendar.getInstance();
         var4.setTime(new Date());
         var4.add(2, 1);
         var2 = var4.getTime();
      }

      String var7 = var1.getName().toLowerCase(Locale.ROOT);
      PlayerProfileCache.ProfileEntry var5 = new PlayerProfileCache.ProfileEntry(this, var1, var2, (Object)null);
      if (this.field_152662_d.containsKey(var3)) {
         PlayerProfileCache.ProfileEntry var6 = (PlayerProfileCache.ProfileEntry)this.field_152662_d.get(var3);
         this.field_152661_c.remove(var6.func_152668_a().getName().toLowerCase(Locale.ROOT));
         this.field_152661_c.put(var1.getName().toLowerCase(Locale.ROOT), var5);
         this.field_152663_e.remove(var1);
      } else {
         this.field_152662_d.put(var3, var5);
         this.field_152661_c.put(var7, var5);
      }

      this.field_152663_e.addFirst(var1);
   }

   public void func_152657_b() {
      List var1 = null;
      BufferedReader var2 = null;

      label66: {
         try {
            var2 = Files.newReader(this.usercacheFile, Charsets.UTF_8);
            var1 = (List)this.gson.fromJson(var2, field_152666_h);
            break label66;
         } catch (FileNotFoundException var7) {
         } finally {
            IOUtils.closeQuietly(var2);
         }

         return;
      }

      if (var1 != null) {
         this.field_152661_c.clear();
         this.field_152662_d.clear();
         this.field_152663_e.clear();
         var1 = Lists.reverse(var1);
         Iterator var3 = var1.iterator();

         while(var3.hasNext()) {
            PlayerProfileCache.ProfileEntry var4 = (PlayerProfileCache.ProfileEntry)var3.next();
            if (var4 != null) {
               this.func_152651_a(var4.func_152668_a(), var4.func_152670_b());
            }
         }
      }

   }

   public void func_152658_c() {
      String var1 = this.gson.toJson(this.func_152656_a(1000));
      BufferedWriter var2 = null;

      try {
         var2 = Files.newWriter(this.usercacheFile, Charsets.UTF_8);
         var2.write(var1);
         return;
      } catch (FileNotFoundException var8) {
         return;
      } catch (IOException var9) {
      } finally {
         IOUtils.closeQuietly(var2);
      }

   }

   class ProfileEntry {
      final PlayerProfileCache this$0;
      private final Date field_152673_c;
      private final GameProfile field_152672_b;
      private static final String __OBFID = "CL_00001885";

      public Date func_152670_b() {
         return this.field_152673_c;
      }

      static Date access$0(PlayerProfileCache.ProfileEntry var0) {
         return var0.field_152673_c;
      }

      public GameProfile func_152668_a() {
         return this.field_152672_b;
      }

      ProfileEntry(PlayerProfileCache var1, GameProfile var2, Date var3, Object var4) {
         this(var1, var2, var3);
      }

      private ProfileEntry(PlayerProfileCache var1, GameProfile var2, Date var3) {
         this.this$0 = var1;
         this.field_152672_b = var2;
         this.field_152673_c = var3;
      }
   }

   class Serializer implements JsonSerializer, JsonDeserializer {
      final PlayerProfileCache this$0;
      private static final String __OBFID = "CL_00001884";

      public JsonElement serialize(Object var1, Type var2, JsonSerializationContext var3) {
         return this.func_152676_a((PlayerProfileCache.ProfileEntry)var1, var2, var3);
      }

      public PlayerProfileCache.ProfileEntry func_152675_a(JsonElement var1, Type var2, JsonDeserializationContext var3) {
         if (var1.isJsonObject()) {
            JsonObject var4 = var1.getAsJsonObject();
            JsonElement var5 = var4.get("name");
            JsonElement var6 = var4.get("uuid");
            JsonElement var7 = var4.get("expiresOn");
            if (var5 != null && var6 != null) {
               String var8 = var6.getAsString();
               String var9 = var5.getAsString();
               Date var10 = null;
               if (var7 != null) {
                  try {
                     var10 = PlayerProfileCache.dateFormat.parse(var7.getAsString());
                  } catch (ParseException var14) {
                     var10 = null;
                  }
               }

               if (var9 != null && var8 != null) {
                  UUID var11;
                  try {
                     var11 = UUID.fromString(var8);
                  } catch (Throwable var13) {
                     return null;
                  }

                  PlayerProfileCache.ProfileEntry var12 = this.this$0.new ProfileEntry(this.this$0, new GameProfile(var11, var9), var10, (Object)null);
                  return var12;
               } else {
                  return null;
               }
            } else {
               return null;
            }
         } else {
            return null;
         }
      }

      public Object deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) {
         return this.func_152675_a(var1, var2, var3);
      }

      private Serializer(PlayerProfileCache var1) {
         this.this$0 = var1;
      }

      public JsonElement func_152676_a(PlayerProfileCache.ProfileEntry var1, Type var2, JsonSerializationContext var3) {
         JsonObject var4 = new JsonObject();
         var4.addProperty("name", var1.func_152668_a().getName());
         UUID var5 = var1.func_152668_a().getId();
         var4.addProperty("uuid", var5 == null ? "" : var5.toString());
         var4.addProperty("expiresOn", PlayerProfileCache.dateFormat.format(var1.func_152670_b()));
         return var4;
      }

      Serializer(PlayerProfileCache var1, Object var2) {
         this(var1);
      }
   }
}
