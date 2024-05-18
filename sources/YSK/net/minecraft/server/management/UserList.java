package net.minecraft.server.management;

import org.apache.logging.log4j.*;
import java.lang.reflect.*;
import java.util.*;
import com.google.common.base.*;
import com.google.common.io.*;
import org.apache.commons.io.*;
import java.io.*;
import com.google.common.collect.*;
import com.google.gson.*;

public class UserList<K, V extends UserListEntry<K>>
{
    protected static final Logger logger;
    private final File saveFile;
    protected final Gson gson;
    private static final String[] I;
    private final Map<String, V> values;
    private boolean lanServer;
    private static final ParameterizedType saveFileFormat;
    
    protected boolean hasEntry(final K k) {
        return this.values.containsKey(this.getObjectKey(k));
    }
    
    private void removeExpired() {
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<V> iterator = this.values.values().iterator();
        "".length();
        if (2 < 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final UserListEntry<K> userListEntry = iterator.next();
            if (userListEntry.hasBanExpired()) {
                arrayList.add(userListEntry.getValue());
            }
        }
        final Iterator<Object> iterator2 = arrayList.iterator();
        "".length();
        if (1 >= 2) {
            throw null;
        }
        while (iterator2.hasNext()) {
            this.values.remove(iterator2.next());
        }
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I(")\u0000&/5J\u0001<7q\u0019\u000e%&q\u001e\u00076c=\u0003\u001c'c0\f\u001b61q\u000b\u000b7*?\rO2c$\u0019\n!m", "joSCQ");
        UserList.I[" ".length()] = I("\"!\u001c\u0007>A \u0006\u001fz\u0012/\u001f\u000ez\u0015&\fK6\b=\u001dK;\u0007:\f\u0019z\u0013+\u0004\u0004,\b \u000eK;A;\u001a\u000e(O", "aNikZ");
    }
    
    protected Map<String, V> getValues() {
        return this.values;
    }
    
    protected UserListEntry<K> createEntry(final JsonObject jsonObject) {
        return new UserListEntry<K>(null, jsonObject);
    }
    
    public String[] getKeys() {
        return this.values.keySet().toArray(new String[this.values.size()]);
    }
    
    public boolean isLanServer() {
        return this.lanServer;
    }
    
    static {
        I();
        logger = LogManager.getLogger();
        saveFileFormat = new ParameterizedType() {
            @Override
            public Type getRawType() {
                return List.class;
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
                    if (-1 == 1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public Type[] getActualTypeArguments() {
                final Type[] array = new Type[" ".length()];
                array["".length()] = UserListEntry.class;
                return array;
            }
            
            @Override
            public Type getOwnerType() {
                return null;
            }
        };
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
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void writeChanges() throws IOException {
        final String json = this.gson.toJson((Object)this.values.values());
        Writer writer = null;
        try {
            writer = Files.newWriter(this.saveFile, Charsets.UTF_8);
            writer.write(json);
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        finally {
            IOUtils.closeQuietly(writer);
        }
        IOUtils.closeQuietly(writer);
    }
    
    public UserList(final File saveFile) {
        this.values = (Map<String, V>)Maps.newHashMap();
        this.lanServer = (" ".length() != 0);
        this.saveFile = saveFile;
        final GsonBuilder setPrettyPrinting = new GsonBuilder().setPrettyPrinting();
        setPrettyPrinting.registerTypeHierarchyAdapter((Class)UserListEntry.class, (Object)new Serializer(null));
        this.gson = setPrettyPrinting.create();
    }
    
    public void setLanServer(final boolean lanServer) {
        this.lanServer = lanServer;
    }
    
    protected String getObjectKey(final K k) {
        return k.toString();
    }
    
    public void removeEntry(final K k) {
        this.values.remove(this.getObjectKey(k));
        try {
            this.writeChanges();
            "".length();
            if (-1 == 0) {
                throw null;
            }
        }
        catch (IOException ex) {
            UserList.logger.warn(UserList.I[" ".length()], (Throwable)ex);
        }
    }
    
    public V getEntry(final K k) {
        this.removeExpired();
        return this.values.get(this.getObjectKey(k));
    }
    
    public void addEntry(final V v) {
        this.values.put(this.getObjectKey(v.getValue()), v);
        try {
            this.writeChanges();
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        catch (IOException ex) {
            UserList.logger.warn(UserList.I["".length()], (Throwable)ex);
        }
    }
    
    class Serializer implements JsonDeserializer<UserListEntry<K>>, JsonSerializer<UserListEntry<K>>
    {
        final UserList this$0;
        
        public JsonElement serialize(final UserListEntry<K> userListEntry, final Type type, final JsonSerializationContext jsonSerializationContext) {
            final JsonObject jsonObject = new JsonObject();
            userListEntry.onSerialization(jsonObject);
            return (JsonElement)jsonObject;
        }
        
        private Serializer(final UserList this$0) {
            this.this$0 = this$0;
        }
        
        public UserListEntry<K> deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            if (jsonElement.isJsonObject()) {
                return this.this$0.createEntry(jsonElement.getAsJsonObject());
            }
            return null;
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
                if (2 != 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public JsonElement serialize(final Object o, final Type type, final JsonSerializationContext jsonSerializationContext) {
            return this.serialize((UserListEntry<K>)o, type, jsonSerializationContext);
        }
        
        Serializer(final UserList list, final Serializer serializer) {
            this(list);
        }
    }
}
