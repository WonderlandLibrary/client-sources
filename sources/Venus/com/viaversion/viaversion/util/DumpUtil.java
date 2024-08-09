/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.util;

import com.google.common.io.CharStreams;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.dump.DumpTemplate;
import com.viaversion.viaversion.dump.VersionInfo;
import com.viaversion.viaversion.libs.gson.GsonBuilder;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.util.GsonUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InvalidObjectException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class DumpUtil {
    public static CompletableFuture<String> postDump(@Nullable UUID uUID) {
        VersionInfo versionInfo = new VersionInfo(System.getProperty("java.version"), System.getProperty("os.name"), Via.getAPI().getServerVersion().lowestSupportedVersion(), Via.getManager().getProtocolManager().getSupportedVersions(), Via.getPlatform().getPlatformName(), Via.getPlatform().getPlatformVersion(), Via.getPlatform().getPluginVersion(), "git-ViaVersion-4.7.1-SNAPSHOT:1aee8a7a", Via.getManager().getSubPlatforms());
        Map<String, Object> map = Via.getPlatform().getConfigurationProvider().getValues();
        DumpTemplate dumpTemplate = new DumpTemplate(versionInfo, map, Via.getPlatform().getDump(), Via.getManager().getInjector().getDump(), DumpUtil.getPlayerSample(uUID));
        CompletableFuture<String> completableFuture = new CompletableFuture<String>();
        Via.getPlatform().runAsync(() -> DumpUtil.lambda$postDump$0(completableFuture, versionInfo, dumpTemplate));
        return completableFuture;
    }

    private static void printFailureInfo(HttpURLConnection httpURLConnection) {
        block14: {
            try {
                if (httpURLConnection.getResponseCode() >= 200 && httpURLConnection.getResponseCode() <= 400) break block14;
                try (InputStream inputStream = httpURLConnection.getErrorStream();){
                    String string = CharStreams.toString(new InputStreamReader(inputStream));
                    Via.getPlatform().getLogger().log(Level.SEVERE, "Page returned: " + string);
                }
            } catch (IOException iOException) {
                Via.getPlatform().getLogger().log(Level.SEVERE, "Failed to capture further info", iOException);
            }
        }
    }

    public static String urlForId(String string) {
        return String.format("https://dump.viaversion.com/%s", string);
    }

    /*
     * WARNING - void declaration
     */
    private static JsonObject getPlayerSample(@Nullable UUID uUID) {
        List list;
        UserConnection userConnection;
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject2 = new JsonObject();
        jsonObject.add("versions", jsonObject2);
        TreeMap<ProtocolVersion, Integer> treeMap = new TreeMap<ProtocolVersion, Integer>(DumpUtil::lambda$getPlayerSample$1);
        for (UserConnection iterator32 : Via.getManager().getConnectionManager().getConnections()) {
            ProtocolVersion protocolVersion = ProtocolVersion.getProtocol(iterator32.getProtocolInfo().getProtocolVersion());
            treeMap.compute(protocolVersion, DumpUtil::lambda$getPlayerSample$2);
        }
        for (Map.Entry entry : treeMap.entrySet()) {
            jsonObject2.addProperty(((ProtocolVersion)entry.getKey()).getName(), (Number)entry.getValue());
        }
        HashSet hashSet = new HashSet();
        if (uUID != null && (userConnection = Via.getAPI().getConnection(uUID)) != null && userConnection.getChannel() != null) {
            hashSet.add(userConnection.getChannel().pipeline().names());
        }
        for (UserConnection userConnection2 : Via.getManager().getConnectionManager().getConnections()) {
            if (userConnection2.getChannel() != null && hashSet.add(list = userConnection2.getChannel().pipeline().names()) && hashSet.size() == 3) break;
        }
        boolean bl = false;
        Iterator iterator2 = hashSet.iterator();
        while (iterator2.hasNext()) {
            void var5_12;
            list = (List)iterator2.next();
            JsonArray jsonArray = new JsonArray(list.size());
            for (String string : list) {
                jsonArray.add(string);
            }
            jsonObject.add("pipeline-" + (int)(++var5_12), jsonArray);
        }
        return jsonObject;
    }

    private static Integer lambda$getPlayerSample$2(ProtocolVersion protocolVersion, Integer n) {
        return n != null ? n + 1 : 1;
    }

    private static int lambda$getPlayerSample$1(ProtocolVersion protocolVersion, ProtocolVersion protocolVersion2) {
        return ProtocolVersion.getIndex(protocolVersion2) - ProtocolVersion.getIndex(protocolVersion);
    }

    private static void lambda$postDump$0(CompletableFuture completableFuture, VersionInfo versionInfo, DumpTemplate dumpTemplate) {
        HttpURLConnection httpURLConnection;
        try {
            httpURLConnection = (HttpURLConnection)new URL("https://dump.viaversion.com/documents").openConnection();
        } catch (IOException iOException) {
            Via.getPlatform().getLogger().log(Level.SEVERE, "Error when opening connection to ViaVersion dump service", iOException);
            completableFuture.completeExceptionally(new DumpException(DumpErrorType.CONNECTION, iOException, null));
            return;
        }
        try {
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.addRequestProperty("User-Agent", "ViaVersion-" + Via.getPlatform().getPlatformName() + "/" + versionInfo.getPluginVersion());
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(false);
            Object object = httpURLConnection.getOutputStream();
            Object object2 = null;
            try {
                ((OutputStream)object).write(new GsonBuilder().setPrettyPrinting().create().toJson(dumpTemplate).getBytes(StandardCharsets.UTF_8));
            } catch (Throwable throwable) {
                object2 = throwable;
                throw throwable;
            } finally {
                if (object != null) {
                    if (object2 != null) {
                        try {
                            ((OutputStream)object).close();
                        } catch (Throwable throwable) {
                            ((Throwable)object2).addSuppressed(throwable);
                        }
                    } else {
                        ((OutputStream)object).close();
                    }
                }
            }
            if (httpURLConnection.getResponseCode() == 429) {
                completableFuture.completeExceptionally(new DumpException(DumpErrorType.RATE_LIMITED, null));
                return;
            }
            object2 = httpURLConnection.getInputStream();
            Throwable throwable = null;
            try {
                object = CharStreams.toString(new InputStreamReader((InputStream)object2));
            } catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            } finally {
                if (object2 != null) {
                    if (throwable != null) {
                        try {
                            ((InputStream)object2).close();
                        } catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                    } else {
                        ((InputStream)object2).close();
                    }
                }
            }
            object2 = GsonUtil.getGson().fromJson((String)object, JsonObject.class);
            if (!((JsonObject)object2).has("key")) {
                throw new InvalidObjectException("Key is not given in Hastebin output");
            }
            completableFuture.complete(DumpUtil.urlForId(((JsonObject)object2).get("key").getAsString()));
        } catch (Exception exception) {
            Via.getPlatform().getLogger().log(Level.SEVERE, "Error when posting ViaVersion dump", exception);
            completableFuture.completeExceptionally(new DumpException(DumpErrorType.POST, exception, null));
            DumpUtil.printFailureInfo(httpURLConnection);
        }
    }

    public static enum DumpErrorType {
        CONNECTION("Failed to dump, please check the console for more information"),
        RATE_LIMITED("Please wait before creating another dump"),
        POST("Failed to dump, please check the console for more information");

        private final String message;

        private DumpErrorType(String string2) {
            this.message = string2;
        }

        public String message() {
            return this.message;
        }
    }

    public static final class DumpException
    extends RuntimeException {
        private final DumpErrorType errorType;

        private DumpException(DumpErrorType dumpErrorType, Throwable throwable) {
            super(dumpErrorType.message(), throwable);
            this.errorType = dumpErrorType;
        }

        private DumpException(DumpErrorType dumpErrorType) {
            super(dumpErrorType.message());
            this.errorType = dumpErrorType;
        }

        public DumpErrorType errorType() {
            return this.errorType;
        }

        DumpException(DumpErrorType dumpErrorType, Throwable throwable, 1 var3_3) {
            this(dumpErrorType, throwable);
        }

        DumpException(DumpErrorType dumpErrorType, 1 var2_2) {
            this(dumpErrorType);
        }
    }
}

