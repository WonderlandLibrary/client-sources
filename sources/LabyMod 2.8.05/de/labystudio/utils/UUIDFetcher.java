package de.labystudio.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.util.UUIDTypeAdapter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class UUIDFetcher
{
    public static final long FEBRUARY_2015 = 1422748800000L;
    private static Gson gson = (new GsonBuilder()).registerTypeAdapter(UUID.class, new UUIDTypeAdapter()).create();
    private static final String UUID_URL = "https://api.mojang.com/users/profiles/minecraft/%s?at=%d";
    private static final String NAME_URL = "https://api.mojang.com/user/profiles/%s/names";
    private static Map<String, UUID> uuidCache = new HashMap();
    private static Map<UUID, String> nameCache = new HashMap();
    private static ExecutorService pool = Executors.newCachedThreadPool();
    public String name;
    public UUID id;
    public long changedToAt;

    public static void getUUID(final String name, final Consumer<UUID> action)
    {
        pool.execute(new Runnable()
        {
            public void run()
            {
                action.accept(UUIDFetcher.getUUID(name));
            }
        });
    }

    public static UUID getUUID(String name)
    {
        return getUUIDAt(name, System.currentTimeMillis());
    }

    public static void getUUIDAt(final String name, final long timestamp, final Consumer<UUID> action)
    {
        pool.execute(new Runnable()
        {
            public void run()
            {
                action.accept(UUIDFetcher.getUUIDAt(name, timestamp));
            }
        });
    }

    public static UUID getUUIDAt(String name, long timestamp)
    {
        name = name.toLowerCase();

        if (uuidCache.containsKey(name))
        {
            return (UUID)uuidCache.get(name);
        }
        else
        {
            try
            {
                HttpURLConnection httpurlconnection = (HttpURLConnection)(new URL(String.format("https://api.mojang.com/users/profiles/minecraft/%s?at=%d", new Object[] {name, Long.valueOf(timestamp / 1000L)}))).openConnection();
                httpurlconnection.setReadTimeout(5000);
                UUIDFetcher uuidfetcher = (UUIDFetcher)gson.fromJson((Reader)(new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream()))), UUIDFetcher.class);

                if (uuidfetcher == null)
                {
                    return null;
                }
                else
                {
                    uuidCache.put(name, uuidfetcher.id);
                    nameCache.put(uuidfetcher.id, uuidfetcher.name);
                    return uuidfetcher.id;
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                return null;
            }
        }
    }

    public static void getName(final UUID uuid, final Consumer<String> action)
    {
        pool.execute(new Runnable()
        {
            public void run()
            {
                action.accept(UUIDFetcher.getName(uuid));
            }
        });
    }

    public static String getName(UUID uuid)
    {
        if (nameCache.containsKey(uuid))
        {
            return (String)nameCache.get(uuid);
        }
        else
        {
            try
            {
                HttpURLConnection httpurlconnection = (HttpURLConnection)(new URL(String.format("https://api.mojang.com/user/profiles/%s/names", new Object[] {UUIDTypeAdapter.fromUUID(uuid)}))).openConnection();
                httpurlconnection.setReadTimeout(5000);
                UUIDFetcher[] auuidfetcher = (UUIDFetcher[])gson.fromJson((Reader)(new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream()))), UUIDFetcher[].class);
                UUIDFetcher uuidfetcher = auuidfetcher[auuidfetcher.length - 1];
                uuidCache.put(uuidfetcher.name.toLowerCase(), uuid);
                nameCache.put(uuid, uuidfetcher.name);
                return uuidfetcher.name;
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                return null;
            }
        }
    }

    public static NameHistory getHistory(UUID uuid)
    {
        try
        {
            HttpURLConnection httpurlconnection = (HttpURLConnection)(new URL(String.format("https://api.mojang.com/user/profiles/%s/names", new Object[] {UUIDTypeAdapter.fromUUID(uuid)}))).openConnection();
            httpurlconnection.setReadTimeout(5000);
            UUIDFetcher[] auuidfetcher = (UUIDFetcher[])gson.fromJson((Reader)(new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream()))), UUIDFetcher[].class);
            return new NameHistory(uuid, auuidfetcher);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return null;
        }
    }
}
