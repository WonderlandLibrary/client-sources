/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.server.management;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.ProfileLookupCallback;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerProfileCache {
    private static final Logger field_242114_a = LogManager.getLogger();
    private static boolean onlineMode;
    private final Map<String, ProfileEntry> usernameToProfileEntryMap = Maps.newConcurrentMap();
    private final Map<UUID, ProfileEntry> uuidToProfileEntryMap = Maps.newConcurrentMap();
    private final GameProfileRepository profileRepo;
    private final Gson gson = new GsonBuilder().create();
    private final File usercacheFile;
    private final AtomicLong field_242115_h = new AtomicLong();

    public PlayerProfileCache(GameProfileRepository gameProfileRepository, File file) {
        this.profileRepo = gameProfileRepository;
        this.usercacheFile = file;
        Lists.reverse(this.func_242116_a()).forEach(this::func_242118_a);
    }

    private void func_242118_a(ProfileEntry profileEntry) {
        UUID uUID;
        GameProfile gameProfile = profileEntry.getGameProfile();
        profileEntry.func_242126_a(this.func_242123_d());
        String string = gameProfile.getName();
        if (string != null) {
            this.usernameToProfileEntryMap.put(string.toLowerCase(Locale.ROOT), profileEntry);
        }
        if ((uUID = gameProfile.getId()) != null) {
            this.uuidToProfileEntryMap.put(uUID, profileEntry);
        }
    }

    @Nullable
    private static GameProfile lookupProfile(GameProfileRepository gameProfileRepository, String string) {
        AtomicReference atomicReference = new AtomicReference();
        ProfileLookupCallback profileLookupCallback = new ProfileLookupCallback(atomicReference){
            final AtomicReference val$atomicreference;
            {
                this.val$atomicreference = atomicReference;
            }

            @Override
            public void onProfileLookupSucceeded(GameProfile gameProfile) {
                this.val$atomicreference.set(gameProfile);
            }

            @Override
            public void onProfileLookupFailed(GameProfile gameProfile, Exception exception) {
                this.val$atomicreference.set(null);
            }
        };
        gameProfileRepository.findProfilesByNames(new String[]{string}, Agent.MINECRAFT, profileLookupCallback);
        GameProfile gameProfile = (GameProfile)atomicReference.get();
        if (!PlayerProfileCache.isOnlineMode() && gameProfile == null) {
            UUID uUID = PlayerEntity.getUUID(new GameProfile(null, string));
            gameProfile = new GameProfile(uUID, string);
        }
        return gameProfile;
    }

    public static void setOnlineMode(boolean bl) {
        onlineMode = bl;
    }

    private static boolean isOnlineMode() {
        return onlineMode;
    }

    public void addEntry(GameProfile gameProfile) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(2, 1);
        Date date = calendar.getTime();
        ProfileEntry profileEntry = new ProfileEntry(gameProfile, date);
        this.func_242118_a(profileEntry);
        this.save();
    }

    private long func_242123_d() {
        return this.field_242115_h.incrementAndGet();
    }

    @Nullable
    public GameProfile getGameProfileForUsername(String string) {
        GameProfile gameProfile;
        String string2 = string.toLowerCase(Locale.ROOT);
        ProfileEntry profileEntry = this.usernameToProfileEntryMap.get(string2);
        boolean bl = false;
        if (profileEntry != null && new Date().getTime() >= profileEntry.expirationDate.getTime()) {
            this.uuidToProfileEntryMap.remove(profileEntry.getGameProfile().getId());
            this.usernameToProfileEntryMap.remove(profileEntry.getGameProfile().getName().toLowerCase(Locale.ROOT));
            bl = true;
            profileEntry = null;
        }
        if (profileEntry != null) {
            profileEntry.func_242126_a(this.func_242123_d());
            gameProfile = profileEntry.getGameProfile();
        } else {
            gameProfile = PlayerProfileCache.lookupProfile(this.profileRepo, string2);
            if (gameProfile != null) {
                this.addEntry(gameProfile);
                bl = false;
            }
        }
        if (bl) {
            this.save();
        }
        return gameProfile;
    }

    @Nullable
    public GameProfile getProfileByUUID(UUID uUID) {
        ProfileEntry profileEntry = this.uuidToProfileEntryMap.get(uUID);
        if (profileEntry == null) {
            return null;
        }
        profileEntry.func_242126_a(this.func_242123_d());
        return profileEntry.getGameProfile();
    }

    private static DateFormat func_242124_e() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List<ProfileEntry> func_242116_a() {
        ArrayList<ProfileEntry> arrayList = Lists.newArrayList();
        try (BufferedReader bufferedReader = Files.newReader(this.usercacheFile, StandardCharsets.UTF_8);){
            JsonArray jsonArray = this.gson.fromJson((Reader)bufferedReader, JsonArray.class);
            if (jsonArray == null) {
                ArrayList<ProfileEntry> arrayList2 = arrayList;
                return arrayList2;
            }
            DateFormat dateFormat = PlayerProfileCache.func_242124_e();
            jsonArray.forEach(arg_0 -> PlayerProfileCache.lambda$func_242116_a$0(dateFormat, arrayList, arg_0));
            return arrayList;
        } catch (FileNotFoundException fileNotFoundException) {
            return arrayList;
        } catch (JsonParseException | IOException exception) {
            field_242114_a.warn("Failed to load profile cache {}", (Object)this.usercacheFile, (Object)exception);
        }
        return arrayList;
    }

    public void save() {
        JsonArray jsonArray = new JsonArray();
        DateFormat dateFormat = PlayerProfileCache.func_242124_e();
        this.func_242117_a(1000).forEach(arg_0 -> PlayerProfileCache.lambda$save$1(jsonArray, dateFormat, arg_0));
        String string = this.gson.toJson(jsonArray);
        try (BufferedWriter bufferedWriter = Files.newWriter(this.usercacheFile, StandardCharsets.UTF_8);){
            bufferedWriter.write(string);
        } catch (IOException iOException) {
            // empty catch block
        }
    }

    private Stream<ProfileEntry> func_242117_a(int n) {
        return ImmutableList.copyOf(this.uuidToProfileEntryMap.values()).stream().sorted(Comparator.comparing(ProfileEntry::func_242128_c).reversed()).limit(n);
    }

    private static JsonElement func_242119_a(ProfileEntry profileEntry, DateFormat dateFormat) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", profileEntry.getGameProfile().getName());
        UUID uUID = profileEntry.getGameProfile().getId();
        jsonObject.addProperty("uuid", uUID == null ? "" : uUID.toString());
        jsonObject.addProperty("expiresOn", dateFormat.format(profileEntry.getExpirationDate()));
        return jsonObject;
    }

    @Nullable
    private static ProfileEntry func_242121_a(JsonElement jsonElement, DateFormat dateFormat) {
        if (jsonElement.isJsonObject()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonElement jsonElement2 = jsonObject.get("name");
            JsonElement jsonElement3 = jsonObject.get("uuid");
            JsonElement jsonElement4 = jsonObject.get("expiresOn");
            if (jsonElement2 != null && jsonElement3 != null) {
                String string = jsonElement3.getAsString();
                String string2 = jsonElement2.getAsString();
                Date date = null;
                if (jsonElement4 != null) {
                    try {
                        date = dateFormat.parse(jsonElement4.getAsString());
                    } catch (ParseException parseException) {
                        // empty catch block
                    }
                }
                if (string2 != null && string != null && date != null) {
                    UUID uUID;
                    try {
                        uUID = UUID.fromString(string);
                    } catch (Throwable throwable) {
                        return null;
                    }
                    return new ProfileEntry(new GameProfile(uUID, string2), date);
                }
                return null;
            }
            return null;
        }
        return null;
    }

    private static void lambda$save$1(JsonArray jsonArray, DateFormat dateFormat, ProfileEntry profileEntry) {
        jsonArray.add(PlayerProfileCache.func_242119_a(profileEntry, dateFormat));
    }

    private static void lambda$func_242116_a$0(DateFormat dateFormat, List list, JsonElement jsonElement) {
        ProfileEntry profileEntry = PlayerProfileCache.func_242121_a(jsonElement, dateFormat);
        if (profileEntry != null) {
            list.add(profileEntry);
        }
    }

    static class ProfileEntry {
        private final GameProfile gameProfile;
        private final Date expirationDate;
        private volatile long field_242125_c;

        private ProfileEntry(GameProfile gameProfile, Date date) {
            this.gameProfile = gameProfile;
            this.expirationDate = date;
        }

        public GameProfile getGameProfile() {
            return this.gameProfile;
        }

        public Date getExpirationDate() {
            return this.expirationDate;
        }

        public void func_242126_a(long l) {
            this.field_242125_c = l;
        }

        public long func_242128_c() {
            return this.field_242125_c;
        }
    }
}

