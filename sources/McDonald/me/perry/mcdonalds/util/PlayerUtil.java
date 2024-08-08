// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.util;

import net.minecraft.entity.player.EntityPlayer;
import java.io.IOException;
import java.util.Arrays;
import org.apache.commons.io.IOUtils;
import com.mojang.util.UUIDTypeAdapter;
import me.perry.mcdonalds.features.command.Command;
import java.util.Collection;
import net.minecraft.client.network.NetworkPlayerInfo;
import java.util.ArrayList;
import java.util.Objects;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.advancements.AdvancementManager;
import java.io.DataOutputStream;
import javax.net.ssl.HttpsURLConnection;
import com.google.gson.JsonObject;
import java.util.Iterator;
import com.google.gson.JsonArray;
import java.util.Collections;
import java.util.Date;
import com.google.gson.JsonElement;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Scanner;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedInputStream;
import java.nio.charset.StandardCharsets;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import com.google.gson.JsonParser;

public class PlayerUtil implements Util
{
    private static final JsonParser PARSER;
    
    public static String getNameFromUUID(final UUID uuid) {
        try {
            final lookUpName process = new lookUpName(uuid);
            final Thread thread = new Thread(process);
            thread.start();
            thread.join();
            return process.getName();
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public static String getNameFromUUID(final String uuid) {
        try {
            final lookUpName process = new lookUpName(uuid);
            final Thread thread = new Thread(process);
            thread.start();
            thread.join();
            return process.getName();
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public static UUID getUUIDFromName(final String name) {
        try {
            final lookUpUUID process = new lookUpUUID(name);
            final Thread thread = new Thread(process);
            thread.start();
            thread.join();
            return process.getUUID();
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public static String requestIDs(final String data) {
        try {
            final String query = "https://api.mojang.com/profiles/minecraft";
            final URL url = new URL(query);
            final HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            final OutputStream os = conn.getOutputStream();
            os.write(data.getBytes(StandardCharsets.UTF_8));
            os.close();
            final InputStream in = new BufferedInputStream(conn.getInputStream());
            final String res = convertStreamToString(in);
            in.close();
            conn.disconnect();
            return res;
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public static String convertStreamToString(final InputStream is) {
        final Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "/";
    }
    
    public static List<String> getHistoryOfNames(final UUID id) {
        try {
            final JsonArray array = getResources(new URL("https://api.mojang.com/user/profiles/" + getIdNoHyphens(id) + "/names"), "GET").getAsJsonArray();
            final List<String> temp = (List<String>)Lists.newArrayList();
            for (final JsonElement e : array) {
                final JsonObject node = e.getAsJsonObject();
                final String name = node.get("name").getAsString();
                final long changedAt = node.has("changedToAt") ? node.get("changedToAt").getAsLong() : 0L;
                temp.add(name + "\u00c3\u201a\u00c2§8" + new Date(changedAt).toString());
            }
            Collections.sort(temp);
            return temp;
        }
        catch (Exception ignored) {
            return null;
        }
    }
    
    public static String getIdNoHyphens(final UUID uuid) {
        return uuid.toString().replaceAll("-", "");
    }
    
    private static JsonElement getResources(final URL url, final String request) throws Exception {
        return getResources(url, request, null);
    }
    
    private static JsonElement getResources(final URL url, final String request, final JsonElement element) throws Exception {
        HttpsURLConnection connection = null;
        try {
            connection = (HttpsURLConnection)url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod(request);
            connection.setRequestProperty("Content-Type", "application/json");
            if (element != null) {
                final DataOutputStream output = new DataOutputStream(connection.getOutputStream());
                output.writeBytes(AdvancementManager.GSON.toJson(element));
                output.close();
            }
            final Scanner scanner = new Scanner(connection.getInputStream());
            final StringBuilder builder = new StringBuilder();
            while (scanner.hasNextLine()) {
                builder.append(scanner.nextLine());
                builder.append('\n');
            }
            scanner.close();
            final String json = builder.toString();
            final JsonElement data = PlayerUtil.PARSER.parse(json);
            return data;
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    
    static {
        PARSER = new JsonParser();
    }
    
    public static class lookUpUUID implements Runnable
    {
        private final String name;
        private volatile UUID uuid;
        
        public lookUpUUID(final String name) {
            this.name = name;
        }
        
        @Override
        public void run() {
            NetworkPlayerInfo profile;
            try {
                final ArrayList<NetworkPlayerInfo> infoMap = new ArrayList<NetworkPlayerInfo>(Objects.requireNonNull(Util.mc.getConnection()).getPlayerInfoMap());
                profile = infoMap.stream().filter(networkPlayerInfo -> networkPlayerInfo.getGameProfile().getName().equalsIgnoreCase(this.name)).findFirst().orElse(null);
                assert profile != null;
                this.uuid = profile.getGameProfile().getId();
            }
            catch (Exception e2) {
                profile = null;
            }
            if (profile == null) {
                Command.sendMessage("Player isn't online. Looking up UUID..");
                final String s = PlayerUtil.requestIDs("[\"" + this.name + "\"]");
                if (s == null || s.isEmpty()) {
                    Command.sendMessage("Couldn't find player ID. Are you connected to the internet? (0)");
                }
                else {
                    final JsonElement element = new JsonParser().parse(s);
                    if (element.getAsJsonArray().size() == 0) {
                        Command.sendMessage("Couldn't find player ID. (1)");
                    }
                    else {
                        try {
                            final String id = element.getAsJsonArray().get(0).getAsJsonObject().get("id").getAsString();
                            this.uuid = UUIDTypeAdapter.fromString(id);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            Command.sendMessage("Couldn't find player ID. (2)");
                        }
                    }
                }
            }
        }
        
        public UUID getUUID() {
            return this.uuid;
        }
        
        public String getName() {
            return this.name;
        }
    }
    
    public static class lookUpName implements Runnable
    {
        private final String uuid;
        private final UUID uuidID;
        private volatile String name;
        
        public lookUpName(final String input) {
            this.uuid = input;
            this.uuidID = UUID.fromString(input);
        }
        
        public lookUpName(final UUID input) {
            this.uuidID = input;
            this.uuid = input.toString();
        }
        
        @Override
        public void run() {
            this.name = this.lookUpName();
        }
        
        public String lookUpName() {
            EntityPlayer player = null;
            if (Util.mc.world != null) {
                player = Util.mc.world.getPlayerEntityByUUID(this.uuidID);
            }
            if (player == null) {
                final String url = "https://api.mojang.com/user/profiles/" + this.uuid.replace("-", "") + "/names";
                try {
                    final String nameJson = IOUtils.toString(new URL(url));
                    if (nameJson.contains(",")) {
                        final List<String> names = Arrays.asList(nameJson.split(","));
                        Collections.reverse(names);
                        return names.get(1).replace("{\"name\":\"", "").replace("\"", "");
                    }
                    return nameJson.replace("[{\"name\":\"", "").replace("\"}]", "");
                }
                catch (IOException exception) {
                    exception.printStackTrace();
                    return null;
                }
            }
            return player.getName();
        }
        
        public String getName() {
            return this.name;
        }
    }
}
