package zamorozka.ui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.util.UUIDTypeAdapter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class UUIDFetcher {
    public static final long FEBRUARY_2015 = 1422748800000L;
    private static Gson gson;
    private static final String UUID_URL = "https://api.mojang.com/users/profiles/minecraft/%s?at=%d";
    private static final String NAME_URL = "https://api.mojang.com/user/profiles/%s/names";
    private static Map<String, UUID> uuidCache;
    private static Map<UUID, String> nameCache;
    private static ExecutorService pool;
    private String name;
    private UUID id;

    static {
        UUIDFetcher.gson = new GsonBuilder().registerTypeAdapter((Type) UUID.class, (Object) new UUIDTypeAdapter()).create();
        UUIDFetcher.uuidCache = new HashMap<String, UUID>();
        UUIDFetcher.nameCache = new HashMap<UUID, String>();
        UUIDFetcher.pool = Executors.newCachedThreadPool();
    }
    public static UUID getUUID(final String s) {
        return getUUIDAt(s, System.currentTimeMillis());
    }

    public static UUID getUUIDAt(String lowerCase, final long n) {
        lowerCase = lowerCase.toLowerCase();
        if (UUIDFetcher.uuidCache.containsKey(lowerCase)) {
            return UUIDFetcher.uuidCache.get(lowerCase);
        }
        try {
            final HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(String.format("https://api.mojang.com/users/profiles/minecraft/%s?at=%d", lowerCase, n / 1000L)).openConnection();
            httpURLConnection.setReadTimeout(5000);
            final UUIDFetcher uuidFetcher = (UUIDFetcher) UUIDFetcher.gson.fromJson((Reader) new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream())), (Class) UUIDFetcher.class);
            UUIDFetcher.uuidCache.put(lowerCase, uuidFetcher.id);
            UUIDFetcher.nameCache.put(uuidFetcher.id, uuidFetcher.name);
            return uuidFetcher.id;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String getName(final UUID uuid) {
        if (UUIDFetcher.nameCache.containsKey(uuid)) {
            return UUIDFetcher.nameCache.get(uuid);
        }
        try {
            final HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(String.format("https://api.mojang.com/user/profiles/%s/names", UUIDTypeAdapter.fromUUID(uuid))).openConnection();
            httpURLConnection.setReadTimeout(5000);
            final UUIDFetcher[] array = (UUIDFetcher[]) UUIDFetcher.gson.fromJson((Reader) new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream())), (Class) UUIDFetcher[].class);
            final UUIDFetcher uuidFetcher = array[array.length - 1];
            UUIDFetcher.uuidCache.put(uuidFetcher.name.toLowerCase(), uuid);
            UUIDFetcher.nameCache.put(uuid, uuidFetcher.name);
            return uuidFetcher.name;
        } catch (Exception ex) {
            return null;
        }
    }

    private static void lambda$0(final Consumer consumer, final String s) {
        consumer.accept(getUUID(s));
    }

    private static void lambda$1(final Consumer consumer, final String s, final long n) {
        consumer.accept(getUUIDAt(s, n));
    }

    private static void lambda$2(final Consumer consumer, final UUID uuid) {
        consumer.accept(getName(uuid));
    }
}