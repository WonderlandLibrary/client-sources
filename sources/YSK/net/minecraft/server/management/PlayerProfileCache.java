package net.minecraft.server.management;

import net.minecraft.server.*;
import com.google.common.collect.*;
import java.lang.reflect.*;
import com.google.common.base.*;
import com.google.common.io.*;
import org.apache.commons.io.*;
import net.minecraft.entity.player.*;
import com.mojang.authlib.*;
import java.io.*;
import java.util.*;
import com.google.gson.*;
import java.text.*;

public class PlayerProfileCache
{
    private final MinecraftServer mcServer;
    private static final ParameterizedType TYPE;
    private final LinkedList<GameProfile> gameProfiles;
    private static final String[] I;
    private final File usercacheFile;
    private final Map<UUID, ProfileEntry> uuidToProfileEntryMap;
    private final Map<String, ProfileEntry> usernameToProfileEntryMap;
    public static final SimpleDateFormat dateFormat;
    protected final Gson gson;
    
    public PlayerProfileCache(final MinecraftServer mcServer, final File usercacheFile) {
        this.usernameToProfileEntryMap = (Map<String, ProfileEntry>)Maps.newHashMap();
        this.uuidToProfileEntryMap = (Map<UUID, ProfileEntry>)Maps.newHashMap();
        this.gameProfiles = (LinkedList<GameProfile>)Lists.newLinkedList();
        this.mcServer = mcServer;
        this.usercacheFile = usercacheFile;
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeHierarchyAdapter((Class)ProfileEntry.class, (Object)new Serializer(null));
        this.gson = gsonBuilder.create();
        this.load();
    }
    
    public GameProfile getGameProfileForUsername(final String s) {
        final String lowerCase = s.toLowerCase(Locale.ROOT);
        ProfileEntry profileEntry = this.usernameToProfileEntryMap.get(lowerCase);
        if (profileEntry != null && new Date().getTime() >= ProfileEntry.access$1(profileEntry).getTime()) {
            this.uuidToProfileEntryMap.remove(profileEntry.getGameProfile().getId());
            this.usernameToProfileEntryMap.remove(profileEntry.getGameProfile().getName().toLowerCase(Locale.ROOT));
            this.gameProfiles.remove(profileEntry.getGameProfile());
            profileEntry = null;
        }
        if (profileEntry != null) {
            final GameProfile gameProfile = profileEntry.getGameProfile();
            this.gameProfiles.remove(gameProfile);
            this.gameProfiles.addFirst(gameProfile);
            "".length();
            if (4 == 2) {
                throw null;
            }
        }
        else {
            final GameProfile gameProfile2 = getGameProfile(this.mcServer, lowerCase);
            if (gameProfile2 != null) {
                this.addEntry(gameProfile2);
                profileEntry = this.usernameToProfileEntryMap.get(lowerCase);
            }
        }
        this.save();
        GameProfile gameProfile3;
        if (profileEntry == null) {
            gameProfile3 = null;
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        else {
            gameProfile3 = profileEntry.getGameProfile();
        }
        return gameProfile3;
    }
    
    private List<ProfileEntry> getEntriesWithLimit(final int n) {
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<GameProfile> iterator = Lists.newArrayList(Iterators.limit((Iterator)this.gameProfiles.iterator(), n)).iterator();
        "".length();
        if (0 >= 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final ProfileEntry byUUID = this.getByUUID(iterator.next().getId());
            if (byUUID != null) {
                arrayList.add(byUUID);
            }
        }
        return (List<ProfileEntry>)arrayList;
    }
    
    static {
        I();
        dateFormat = new SimpleDateFormat(PlayerProfileCache.I["".length()]);
        TYPE = new ParameterizedType() {
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (1 == 4) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public Type getRawType() {
                return List.class;
            }
            
            @Override
            public Type getOwnerType() {
                return null;
            }
            
            @Override
            public Type[] getActualTypeArguments() {
                final Type[] array = new Type[" ".length()];
                array["".length()] = ProfileEntry.class;
                return array;
            }
        };
    }
    
    public void load() {
        Reader reader = null;
        try {
            reader = Files.newReader(this.usercacheFile, Charsets.UTF_8);
            final List list = (List)this.gson.fromJson(reader, (Type)PlayerProfileCache.TYPE);
            this.usernameToProfileEntryMap.clear();
            this.uuidToProfileEntryMap.clear();
            this.gameProfiles.clear();
            final Iterator iterator = Lists.reverse(list).iterator();
            "".length();
            if (3 >= 4) {
                throw null;
            }
            while (iterator.hasNext()) {
                final ProfileEntry profileEntry = iterator.next();
                if (profileEntry != null) {
                    this.addEntry(profileEntry.getGameProfile(), profileEntry.getExpirationDate());
                }
            }
            "".length();
            if (true != true) {
                throw null;
            }
        }
        catch (FileNotFoundException ex) {
            IOUtils.closeQuietly(reader);
            "".length();
            if (3 < 2) {
                throw null;
            }
            return;
        }
        catch (JsonParseException ex2) {
            IOUtils.closeQuietly(reader);
            "".length();
            if (4 <= -1) {
                throw null;
            }
            return;
        }
        finally {
            IOUtils.closeQuietly(reader);
        }
        IOUtils.closeQuietly(reader);
    }
    
    public GameProfile getProfileByUUID(final UUID uuid) {
        final ProfileEntry profileEntry = this.uuidToProfileEntryMap.get(uuid);
        GameProfile gameProfile;
        if (profileEntry == null) {
            gameProfile = null;
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            gameProfile = profileEntry.getGameProfile();
        }
        return gameProfile;
    }
    
    public String[] getUsernames() {
        final ArrayList arrayList = Lists.newArrayList((Iterable)this.usernameToProfileEntryMap.keySet());
        return (String[])arrayList.toArray(new String[arrayList.size()]);
    }
    
    private static GameProfile getGameProfile(final MinecraftServer minecraftServer, final String s) {
        final GameProfile[] array = new GameProfile[" ".length()];
        final ProfileLookupCallback profileLookupCallback = (ProfileLookupCallback)new ProfileLookupCallback(array) {
            private final GameProfile[] val$agameprofile;
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (4 <= 1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public void onProfileLookupFailed(final GameProfile gameProfile, final Exception ex) {
                this.val$agameprofile["".length()] = null;
            }
            
            public void onProfileLookupSucceeded(final GameProfile gameProfile) {
                this.val$agameprofile["".length()] = gameProfile;
            }
        };
        final GameProfileRepository gameProfileRepository = minecraftServer.getGameProfileRepository();
        final String[] array2 = new String[" ".length()];
        array2["".length()] = s;
        gameProfileRepository.findProfilesByNames(array2, Agent.MINECRAFT, (ProfileLookupCallback)profileLookupCallback);
        if (!minecraftServer.isServerInOnlineMode() && array["".length()] == null) {
            ((ProfileLookupCallback)profileLookupCallback).onProfileLookupSucceeded(new GameProfile(EntityPlayer.getUUID(new GameProfile((UUID)null, s)), s));
        }
        return array["".length()];
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("-\t\u001d<u\u0019=I!<t8,\u007f59J\u00176x\u000e", "TpdEX");
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void save() {
        final String json = this.gson.toJson((Object)this.getEntriesWithLimit(215 + 136 + 335 + 314));
        Writer writer = null;
        try {
            writer = Files.newWriter(this.usercacheFile, Charsets.UTF_8);
            writer.write(json);
        }
        catch (FileNotFoundException ex) {
            IOUtils.closeQuietly(writer);
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        catch (IOException ex2) {}
        finally {
            IOUtils.closeQuietly(writer);
        }
    }
    
    private void addEntry(final GameProfile gameProfile, Date time) {
        final UUID id = gameProfile.getId();
        if (time == null) {
            final Calendar instance = Calendar.getInstance();
            instance.setTime(new Date());
            instance.add("  ".length(), " ".length());
            time = instance.getTime();
        }
        gameProfile.getName().toLowerCase(Locale.ROOT);
        final ProfileEntry profileEntry = new ProfileEntry(gameProfile, time, null);
        if (this.uuidToProfileEntryMap.containsKey(id)) {
            this.usernameToProfileEntryMap.remove(this.uuidToProfileEntryMap.get(id).getGameProfile().getName().toLowerCase(Locale.ROOT));
            this.gameProfiles.remove(gameProfile);
        }
        this.usernameToProfileEntryMap.put(gameProfile.getName().toLowerCase(Locale.ROOT), profileEntry);
        this.uuidToProfileEntryMap.put(id, profileEntry);
        this.gameProfiles.addFirst(gameProfile);
        this.save();
    }
    
    public void addEntry(final GameProfile gameProfile) {
        this.addEntry(gameProfile, null);
    }
    
    private ProfileEntry getByUUID(final UUID uuid) {
        final ProfileEntry profileEntry = this.uuidToProfileEntryMap.get(uuid);
        if (profileEntry != null) {
            final GameProfile gameProfile = profileEntry.getGameProfile();
            this.gameProfiles.remove(gameProfile);
            this.gameProfiles.addFirst(gameProfile);
        }
        return profileEntry;
    }
    
    class Serializer implements JsonDeserializer<ProfileEntry>, JsonSerializer<ProfileEntry>
    {
        private static final String[] I;
        final PlayerProfileCache this$0;
        
        public JsonElement serialize(final ProfileEntry profileEntry, final Type type, final JsonSerializationContext jsonSerializationContext) {
            final JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(Serializer.I["".length()], profileEntry.getGameProfile().getName());
            final UUID id = profileEntry.getGameProfile().getId();
            final JsonObject jsonObject2 = jsonObject;
            final String s = Serializer.I[" ".length()];
            String string;
            if (id == null) {
                string = Serializer.I["  ".length()];
                "".length();
                if (0 <= -1) {
                    throw null;
                }
            }
            else {
                string = id.toString();
            }
            jsonObject2.addProperty(s, string);
            jsonObject.addProperty(Serializer.I["   ".length()], PlayerProfileCache.dateFormat.format(profileEntry.getExpirationDate()));
            return (JsonElement)jsonObject;
        }
        
        public ProfileEntry deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            if (!jsonElement.isJsonObject()) {
                return null;
            }
            final JsonObject asJsonObject = jsonElement.getAsJsonObject();
            final JsonElement value = asJsonObject.get(Serializer.I[0x78 ^ 0x7C]);
            final JsonElement value2 = asJsonObject.get(Serializer.I[0x9A ^ 0x9F]);
            final JsonElement value3 = asJsonObject.get(Serializer.I[0xA ^ 0xC]);
            if (value == null || value2 == null) {
                return null;
            }
            final String asString = value2.getAsString();
            final String asString2 = value.getAsString();
            Date parse = null;
            if (value3 != null) {
                try {
                    parse = PlayerProfileCache.dateFormat.parse(value3.getAsString());
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                }
                catch (ParseException ex) {
                    parse = null;
                }
            }
            if (asString2 != null && asString != null) {
                UUID fromString;
                try {
                    fromString = UUID.fromString(asString);
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                }
                catch (Throwable t) {
                    return null;
                }
                final PlayerProfileCache this$0 = this.this$0;
                this$0.getClass();
                return this$0.new ProfileEntry(new GameProfile(fromString, asString2), parse, null);
            }
            return null;
        }
        
        Serializer(final PlayerProfileCache playerProfileCache, final Serializer serializer) {
            this(playerProfileCache);
        }
        
        static {
            I();
        }
        
        public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (0 < 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public JsonElement serialize(final Object o, final Type type, final JsonSerializationContext jsonSerializationContext) {
            return this.serialize((ProfileEntry)o, type, jsonSerializationContext);
        }
        
        private Serializer(final PlayerProfileCache this$0) {
            this.this$0 = this$0;
        }
        
        private static void I() {
            (I = new String[0x56 ^ 0x51])["".length()] = I("-(\u0001!", "CIlDj");
            Serializer.I[" ".length()] = I("$\f\u0005/", "QylKJ");
            Serializer.I["  ".length()] = I("", "UbEly");
            Serializer.I["   ".length()] = I("+\u001b*\u0011\u0004+\u0010\u0015\u0016", "NcZxv");
            Serializer.I[0x20 ^ 0x24] = I("\u0001\u0012*+", "osGNT");
            Serializer.I[0x70 ^ 0x75] = I("\u0004\u001d'\u0007", "qhNcQ");
            Serializer.I[0xA3 ^ 0xA5] = I("\u0014\u00113\u0010\"\u0014\u001a\f\u0017", "qiCyP");
        }
    }
    
    class ProfileEntry
    {
        private final GameProfile gameProfile;
        private final Date expirationDate;
        final PlayerProfileCache this$0;
        
        public Date getExpirationDate() {
            return this.expirationDate;
        }
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (4 <= 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public GameProfile getGameProfile() {
            return this.gameProfile;
        }
        
        static Date access$1(final ProfileEntry profileEntry) {
            return profileEntry.expirationDate;
        }
        
        private ProfileEntry(final PlayerProfileCache this$0, final GameProfile gameProfile, final Date expirationDate) {
            this.this$0 = this$0;
            this.gameProfile = gameProfile;
            this.expirationDate = expirationDate;
        }
        
        ProfileEntry(final PlayerProfileCache playerProfileCache, final GameProfile gameProfile, final Date date, final ProfileEntry profileEntry) {
            this(playerProfileCache, gameProfile, date);
        }
    }
}
