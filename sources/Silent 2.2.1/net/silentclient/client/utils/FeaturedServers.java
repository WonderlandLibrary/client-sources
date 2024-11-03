package net.silentclient.client.utils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.silentclient.client.ServerDataFeature;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FeaturedServers {
    public static ArrayList<FeaturedServerInfo> get() {
        try {
            String content = Requests.get("https://api.silentclient.net/_next/servers");
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            Type listType = new TypeToken<ArrayList<FeaturedServerInfo>>(){}.getType();
            ArrayList<FeaturedServerInfo> response = gson.fromJson(content.toString(), listType);
            return response;
        } catch (Exception e) {
            return null;
        }
    }

    public static class FeaturedServerInfo {
        public String name;
        public String ip;

        public FeaturedServerInfo(String name, String ip) {
            this.name = name;
            this.ip = ip;
        }

        public String getName() {
            return name;
        }

        public String getIp() {
            return ip;
        }
    }

    public static ServerDataFeature findByIPServerDataFeature(String ip, ArrayList<ServerDataFeature> servers) {
        for (ServerDataFeature server : servers) {
            if (server.serverIP.equals(ip)) {
                return server;
            }
        }
        return null;
    }

    public static FeaturedServerInfo findByIPFeaturedServerInfo(String ip, ArrayList<FeaturedServerInfo> servers) {
        for (FeaturedServerInfo server : servers) {
            if (server.getIp().equals(ip)) {
                return server;
            }
        }
        return null;
    }
}