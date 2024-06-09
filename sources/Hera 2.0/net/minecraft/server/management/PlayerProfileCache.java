/*     */ package net.minecraft.server.management;
/*     */ 
/*     */ import com.google.common.base.Charsets;
/*     */ import com.google.common.collect.Iterators;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.io.Files;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import com.google.gson.JsonSerializer;
/*     */ import com.mojang.authlib.Agent;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.ProfileLookupCallback;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerProfileCache
/*     */ {
/*  45 */   public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
/*  46 */   private final Map<String, ProfileEntry> usernameToProfileEntryMap = Maps.newHashMap();
/*  47 */   private final Map<UUID, ProfileEntry> uuidToProfileEntryMap = Maps.newHashMap();
/*  48 */   private final LinkedList<GameProfile> gameProfiles = Lists.newLinkedList();
/*     */   
/*     */   private final MinecraftServer mcServer;
/*     */   
/*  52 */   private static final ParameterizedType TYPE = new ParameterizedType()
/*     */     {
/*     */       public Type[] getActualTypeArguments()
/*     */       {
/*  56 */         return new Type[] { PlayerProfileCache.ProfileEntry.class };
/*     */       }
/*     */       
/*     */       public Type getRawType() {
/*  60 */         return List.class;
/*     */       }
/*     */       
/*     */       public Type getOwnerType() {
/*  64 */         return null;
/*     */       }
/*     */     };
/*     */   protected final Gson gson; private final File usercacheFile;
/*     */   
/*     */   public PlayerProfileCache(MinecraftServer server, File cacheFile) {
/*  70 */     this.mcServer = server;
/*  71 */     this.usercacheFile = cacheFile;
/*  72 */     GsonBuilder gsonbuilder = new GsonBuilder();
/*  73 */     gsonbuilder.registerTypeHierarchyAdapter(ProfileEntry.class, new Serializer(null));
/*  74 */     this.gson = gsonbuilder.create();
/*  75 */     load();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static GameProfile getGameProfile(MinecraftServer server, String username) {
/*  86 */     final GameProfile[] agameprofile = new GameProfile[1];
/*  87 */     ProfileLookupCallback profilelookupcallback = new ProfileLookupCallback()
/*     */       {
/*     */         public void onProfileLookupSucceeded(GameProfile p_onProfileLookupSucceeded_1_)
/*     */         {
/*  91 */           agameprofile[0] = p_onProfileLookupSucceeded_1_;
/*     */         }
/*     */         
/*     */         public void onProfileLookupFailed(GameProfile p_onProfileLookupFailed_1_, Exception p_onProfileLookupFailed_2_) {
/*  95 */           agameprofile[0] = null;
/*     */         }
/*     */       };
/*  98 */     server.getGameProfileRepository().findProfilesByNames(new String[] { username }, Agent.MINECRAFT, profilelookupcallback);
/*     */     
/* 100 */     if (!server.isServerInOnlineMode() && agameprofile[0] == null) {
/*     */       
/* 102 */       UUID uuid = EntityPlayer.getUUID(new GameProfile(null, username));
/* 103 */       GameProfile gameprofile = new GameProfile(uuid, username);
/* 104 */       profilelookupcallback.onProfileLookupSucceeded(gameprofile);
/*     */     } 
/*     */     
/* 107 */     return agameprofile[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEntry(GameProfile gameProfile) {
/* 115 */     addEntry(gameProfile, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addEntry(GameProfile gameProfile, Date expirationDate) {
/* 123 */     UUID uuid = gameProfile.getId();
/*     */     
/* 125 */     if (expirationDate == null) {
/*     */       
/* 127 */       Calendar calendar = Calendar.getInstance();
/* 128 */       calendar.setTime(new Date());
/* 129 */       calendar.add(2, 1);
/* 130 */       expirationDate = calendar.getTime();
/*     */     } 
/*     */     
/* 133 */     String s = gameProfile.getName().toLowerCase(Locale.ROOT);
/* 134 */     ProfileEntry playerprofilecache$profileentry = new ProfileEntry(gameProfile, expirationDate, null);
/*     */     
/* 136 */     if (this.uuidToProfileEntryMap.containsKey(uuid)) {
/*     */       
/* 138 */       ProfileEntry playerprofilecache$profileentry1 = this.uuidToProfileEntryMap.get(uuid);
/* 139 */       this.usernameToProfileEntryMap.remove(playerprofilecache$profileentry1.getGameProfile().getName().toLowerCase(Locale.ROOT));
/* 140 */       this.gameProfiles.remove(gameProfile);
/*     */     } 
/*     */     
/* 143 */     this.usernameToProfileEntryMap.put(gameProfile.getName().toLowerCase(Locale.ROOT), playerprofilecache$profileentry);
/* 144 */     this.uuidToProfileEntryMap.put(uuid, playerprofilecache$profileentry);
/* 145 */     this.gameProfiles.addFirst(gameProfile);
/* 146 */     save();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GameProfile getGameProfileForUsername(String username) {
/* 155 */     String s = username.toLowerCase(Locale.ROOT);
/* 156 */     ProfileEntry playerprofilecache$profileentry = this.usernameToProfileEntryMap.get(s);
/*     */     
/* 158 */     if (playerprofilecache$profileentry != null && (new Date()).getTime() >= playerprofilecache$profileentry.expirationDate.getTime()) {
/*     */       
/* 160 */       this.uuidToProfileEntryMap.remove(playerprofilecache$profileentry.getGameProfile().getId());
/* 161 */       this.usernameToProfileEntryMap.remove(playerprofilecache$profileentry.getGameProfile().getName().toLowerCase(Locale.ROOT));
/* 162 */       this.gameProfiles.remove(playerprofilecache$profileentry.getGameProfile());
/* 163 */       playerprofilecache$profileentry = null;
/*     */     } 
/*     */     
/* 166 */     if (playerprofilecache$profileentry != null) {
/*     */       
/* 168 */       GameProfile gameprofile = playerprofilecache$profileentry.getGameProfile();
/* 169 */       this.gameProfiles.remove(gameprofile);
/* 170 */       this.gameProfiles.addFirst(gameprofile);
/*     */     }
/*     */     else {
/*     */       
/* 174 */       GameProfile gameprofile1 = getGameProfile(this.mcServer, s);
/*     */       
/* 176 */       if (gameprofile1 != null) {
/*     */         
/* 178 */         addEntry(gameprofile1);
/* 179 */         playerprofilecache$profileentry = this.usernameToProfileEntryMap.get(s);
/*     */       } 
/*     */     } 
/*     */     
/* 183 */     save();
/* 184 */     return (playerprofilecache$profileentry == null) ? null : playerprofilecache$profileentry.getGameProfile();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getUsernames() {
/* 192 */     List<String> list = Lists.newArrayList(this.usernameToProfileEntryMap.keySet());
/* 193 */     return list.<String>toArray(new String[list.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GameProfile getProfileByUUID(UUID uuid) {
/* 201 */     ProfileEntry playerprofilecache$profileentry = this.uuidToProfileEntryMap.get(uuid);
/* 202 */     return (playerprofilecache$profileentry == null) ? null : playerprofilecache$profileentry.getGameProfile();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ProfileEntry getByUUID(UUID uuid) {
/* 210 */     ProfileEntry playerprofilecache$profileentry = this.uuidToProfileEntryMap.get(uuid);
/*     */     
/* 212 */     if (playerprofilecache$profileentry != null) {
/*     */       
/* 214 */       GameProfile gameprofile = playerprofilecache$profileentry.getGameProfile();
/* 215 */       this.gameProfiles.remove(gameprofile);
/* 216 */       this.gameProfiles.addFirst(gameprofile);
/*     */     } 
/*     */     
/* 219 */     return playerprofilecache$profileentry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void load() {
/* 227 */     BufferedReader bufferedreader = null;
/*     */ 
/*     */     
/*     */     try {
/* 231 */       bufferedreader = Files.newReader(this.usercacheFile, Charsets.UTF_8);
/* 232 */       List<ProfileEntry> list = (List<ProfileEntry>)this.gson.fromJson(bufferedreader, TYPE);
/* 233 */       this.usernameToProfileEntryMap.clear();
/* 234 */       this.uuidToProfileEntryMap.clear();
/* 235 */       this.gameProfiles.clear();
/*     */       
/* 237 */       for (ProfileEntry playerprofilecache$profileentry : Lists.reverse(list))
/*     */       {
/* 239 */         if (playerprofilecache$profileentry != null)
/*     */         {
/* 241 */           addEntry(playerprofilecache$profileentry.getGameProfile(), playerprofilecache$profileentry.getExpirationDate());
/*     */         }
/*     */       }
/*     */     
/* 245 */     } catch (FileNotFoundException fileNotFoundException) {
/*     */ 
/*     */     
/*     */     }
/* 249 */     catch (JsonParseException jsonParseException) {
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 255 */       IOUtils.closeQuietly(bufferedreader);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void save() {
/* 264 */     String s = this.gson.toJson(getEntriesWithLimit(1000));
/* 265 */     BufferedWriter bufferedwriter = null;
/*     */ 
/*     */     
/*     */     try {
/* 269 */       bufferedwriter = Files.newWriter(this.usercacheFile, Charsets.UTF_8);
/* 270 */       bufferedwriter.write(s);
/*     */       
/*     */       return;
/* 273 */     } catch (FileNotFoundException fileNotFoundException) {
/*     */ 
/*     */     
/*     */     }
/* 277 */     catch (IOException var9) {
/*     */       
/*     */       return;
/*     */     }
/*     */     finally {
/*     */       
/* 283 */       IOUtils.closeQuietly(bufferedwriter);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private List<ProfileEntry> getEntriesWithLimit(int limitSize) {
/* 289 */     ArrayList<ProfileEntry> arraylist = Lists.newArrayList();
/*     */     
/* 291 */     for (GameProfile gameprofile : Lists.newArrayList(Iterators.limit(this.gameProfiles.iterator(), limitSize))) {
/*     */       
/* 293 */       ProfileEntry playerprofilecache$profileentry = getByUUID(gameprofile.getId());
/*     */       
/* 295 */       if (playerprofilecache$profileentry != null)
/*     */       {
/* 297 */         arraylist.add(playerprofilecache$profileentry);
/*     */       }
/*     */     } 
/*     */     
/* 301 */     return arraylist;
/*     */   }
/*     */ 
/*     */   
/*     */   class ProfileEntry
/*     */   {
/*     */     private final GameProfile gameProfile;
/*     */     private final Date expirationDate;
/*     */     
/*     */     private ProfileEntry(GameProfile gameProfileIn, Date expirationDateIn) {
/* 311 */       this.gameProfile = gameProfileIn;
/* 312 */       this.expirationDate = expirationDateIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public GameProfile getGameProfile() {
/* 317 */       return this.gameProfile;
/*     */     }
/*     */ 
/*     */     
/*     */     public Date getExpirationDate() {
/* 322 */       return this.expirationDate;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   class Serializer
/*     */     implements JsonDeserializer<ProfileEntry>, JsonSerializer<ProfileEntry>
/*     */   {
/*     */     private Serializer() {}
/*     */ 
/*     */     
/*     */     public JsonElement serialize(PlayerProfileCache.ProfileEntry p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
/* 334 */       JsonObject jsonobject = new JsonObject();
/* 335 */       jsonobject.addProperty("name", p_serialize_1_.getGameProfile().getName());
/* 336 */       UUID uuid = p_serialize_1_.getGameProfile().getId();
/* 337 */       jsonobject.addProperty("uuid", (uuid == null) ? "" : uuid.toString());
/* 338 */       jsonobject.addProperty("expiresOn", PlayerProfileCache.dateFormat.format(p_serialize_1_.getExpirationDate()));
/* 339 */       return (JsonElement)jsonobject;
/*     */     }
/*     */ 
/*     */     
/*     */     public PlayerProfileCache.ProfileEntry deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 344 */       if (p_deserialize_1_.isJsonObject()) {
/*     */         
/* 346 */         JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/* 347 */         JsonElement jsonelement = jsonobject.get("name");
/* 348 */         JsonElement jsonelement1 = jsonobject.get("uuid");
/* 349 */         JsonElement jsonelement2 = jsonobject.get("expiresOn");
/*     */         
/* 351 */         if (jsonelement != null && jsonelement1 != null) {
/*     */           
/* 353 */           String s = jsonelement1.getAsString();
/* 354 */           String s1 = jsonelement.getAsString();
/* 355 */           Date date = null;
/*     */           
/* 357 */           if (jsonelement2 != null) {
/*     */             
/*     */             try {
/*     */               
/* 361 */               date = PlayerProfileCache.dateFormat.parse(jsonelement2.getAsString());
/*     */             }
/* 363 */             catch (ParseException var14) {
/*     */               
/* 365 */               date = null;
/*     */             } 
/*     */           }
/*     */           
/* 369 */           if (s1 != null && s != null) {
/*     */             UUID uuid;
/*     */ 
/*     */ 
/*     */             
/*     */             try {
/* 375 */               uuid = UUID.fromString(s);
/*     */             }
/* 377 */             catch (Throwable var13) {
/*     */               
/* 379 */               return null;
/*     */             } 
/*     */             
/* 382 */             PlayerProfileCache.this.getClass(); PlayerProfileCache.ProfileEntry playerprofilecache$profileentry = new PlayerProfileCache.ProfileEntry(new GameProfile(uuid, s1), date, null);
/* 383 */             return playerprofilecache$profileentry;
/*     */           } 
/*     */ 
/*     */           
/* 387 */           return null;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 392 */         return null;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 397 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\server\management\PlayerProfileCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */