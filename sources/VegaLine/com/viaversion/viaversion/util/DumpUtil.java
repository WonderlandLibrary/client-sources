/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.util;

import com.google.common.io.CharStreams;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.dump.DumpTemplate;
import com.viaversion.viaversion.libs.gson.GsonBuilder;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.util.GsonUtil;
import com.viaversion.viaversion.util.VersionInfo;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InvalidObjectException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class DumpUtil {
    public static CompletableFuture<String> postDump(@Nullable UUID playerToSample) {
        com.viaversion.viaversion.dump.VersionInfo version = new com.viaversion.viaversion.dump.VersionInfo(System.getProperty("java.version"), System.getProperty("os.name"), Via.getAPI().getServerVersion().lowestSupportedVersion(), Via.getManager().getProtocolManager().getSupportedVersions(), Via.getPlatform().getPlatformName(), Via.getPlatform().getPlatformVersion(), Via.getPlatform().getPluginVersion(), VersionInfo.getImplementationVersion(), Via.getManager().getSubPlatforms());
        Map<String, Object> configuration = Via.getPlatform().getConfigurationProvider().getValues();
        DumpTemplate template = new DumpTemplate(version, configuration, Via.getPlatform().getDump(), Via.getManager().getInjector().getDump(), DumpUtil.getPlayerSample(playerToSample));
        CompletableFuture<String> result = new CompletableFuture<String>();
        Via.getPlatform().runAsync(() -> {
            HttpURLConnection con;
            try {
                con = (HttpURLConnection)new URL("https://dump.viaversion.com/documents").openConnection();
            } catch (IOException e) {
                Via.getPlatform().getLogger().log(Level.SEVERE, "Error when opening connection to ViaVersion dump service", e);
                result.completeExceptionally(new DumpException(DumpErrorType.CONNECTION, e));
                return;
            }
            try {
                String rawOutput;
                con.setRequestProperty("Content-Type", "application/json");
                con.addRequestProperty("User-Agent", "ViaVersion-" + Via.getPlatform().getPlatformName() + "/" + version.getPluginVersion());
                con.setRequestMethod("POST");
                con.setDoOutput(true);
                try (OutputStream out = con.getOutputStream();){
                    out.write(new GsonBuilder().setPrettyPrinting().create().toJson(template).getBytes(StandardCharsets.UTF_8));
                }
                if (con.getResponseCode() == 429) {
                    result.completeExceptionally(new DumpException(DumpErrorType.RATE_LIMITED));
                    return;
                }
                try (InputStream inputStream = con.getInputStream();){
                    rawOutput = CharStreams.toString(new InputStreamReader(inputStream));
                }
                JsonObject output = GsonUtil.getGson().fromJson(rawOutput, JsonObject.class);
                if (!output.has("key")) {
                    throw new InvalidObjectException("Key is not given in Hastebin output");
                }
                result.complete(DumpUtil.urlForId(output.get("key").getAsString()));
            } catch (Exception e) {
                Via.getPlatform().getLogger().log(Level.SEVERE, "Error when posting ViaVersion dump", e);
                result.completeExceptionally(new DumpException(DumpErrorType.POST, e));
                DumpUtil.printFailureInfo(con);
            }
        });
        return result;
    }

    private static void printFailureInfo(HttpURLConnection connection) {
        block14: {
            try {
                if (connection.getResponseCode() >= 200 && connection.getResponseCode() <= 400) break block14;
                try (InputStream errorStream = connection.getErrorStream();){
                    String rawOutput = CharStreams.toString(new InputStreamReader(errorStream));
                    Via.getPlatform().getLogger().log(Level.SEVERE, "Page returned: " + rawOutput);
                }
            } catch (IOException e) {
                Via.getPlatform().getLogger().log(Level.SEVERE, "Failed to capture further info", e);
            }
        }
    }

    public static String urlForId(String id) {
        return String.format("https://dump.viaversion.com/%s", id);
    }

    /*
     * WARNING - void declaration
     */
    private static JsonObject getPlayerSample(@Nullable UUID uuid) {
        UserConnection userConnection;
        JsonObject playerSample = new JsonObject();
        JsonObject versions = new JsonObject();
        playerSample.add("versions", versions);
        TreeMap<ProtocolVersion, Integer> playerVersions = new TreeMap<ProtocolVersion, Integer>((o1, o2) -> ProtocolVersion.getIndex(o2) - ProtocolVersion.getIndex(o1));
        for (UserConnection userConnection2 : Via.getManager().getConnectionManager().getConnections()) {
            ProtocolVersion protocolVersion = ProtocolVersion.getProtocol(userConnection2.getProtocolInfo().getProtocolVersion());
            playerVersions.compute(protocolVersion, (v, num) -> num != null ? num + 1 : 1);
        }
        for (Map.Entry entry : playerVersions.entrySet()) {
            versions.addProperty(((ProtocolVersion)entry.getKey()).getName(), (Number)entry.getValue());
        }
        HashSet<List<String>> pipelines = new HashSet<List<String>>();
        if (uuid != null && (userConnection = Via.getAPI().getConnection(uuid)) != null && userConnection.getChannel() != null) {
            pipelines.add(userConnection.getChannel().pipeline().names());
        }
        for (UserConnection connection : Via.getManager().getConnectionManager().getConnections()) {
            List<String> list;
            if (connection.getChannel() != null && pipelines.add(list = connection.getChannel().pipeline().names()) && pipelines.size() == 3) break;
        }
        boolean bl = false;
        for (List list : pipelines) {
            void var5_12;
            JsonArray senderPipeline = new JsonArray(list.size());
            for (String name : list) {
                senderPipeline.add(name);
            }
            playerSample.add("pipeline-" + (int)(++var5_12), senderPipeline);
        }
        return playerSample;
    }

    public static enum DumpErrorType {
        CONNECTION("Failed to dump, please check the console for more information"),
        RATE_LIMITED("Please wait before creating another dump"),
        POST("Failed to dump, please check the console for more information");

        private final String message;

        private DumpErrorType(String message) {
            this.message = message;
        }

        public String message() {
            return this.message;
        }
    }

    public static final class DumpException
    extends RuntimeException {
        private final DumpErrorType errorType;

        private DumpException(DumpErrorType errorType, Throwable cause) {
            super(errorType.message(), cause);
            this.errorType = errorType;
        }

        private DumpException(DumpErrorType errorType) {
            super(errorType.message());
            this.errorType = errorType;
        }

        public DumpErrorType errorType() {
            return this.errorType;
        }
    }
}

