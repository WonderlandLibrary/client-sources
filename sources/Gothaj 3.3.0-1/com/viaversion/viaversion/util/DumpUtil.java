package com.viaversion.viaversion.util;

import com.google.common.io.CharStreams;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.dump.DumpTemplate;
import com.viaversion.viaversion.libs.gson.GsonBuilder;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonObject;
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
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class DumpUtil {
   public static CompletableFuture<String> postDump(@Nullable UUID playerToSample) {
      com.viaversion.viaversion.dump.VersionInfo version = new com.viaversion.viaversion.dump.VersionInfo(
         System.getProperty("java.version"),
         System.getProperty("os.name"),
         Via.getAPI().getServerVersion().lowestSupportedVersion(),
         Via.getManager().getProtocolManager().getSupportedVersions(),
         Via.getPlatform().getPlatformName(),
         Via.getPlatform().getPlatformVersion(),
         Via.getPlatform().getPluginVersion(),
         VersionInfo.getImplementationVersion(),
         Via.getManager().getSubPlatforms()
      );
      Map<String, Object> configuration = ((Config)Via.getConfig()).getValues();
      DumpTemplate template = new DumpTemplate(
         version, configuration, Via.getPlatform().getDump(), Via.getManager().getInjector().getDump(), getPlayerSample(playerToSample)
      );
      CompletableFuture<String> result = new CompletableFuture<>();
      Via.getPlatform().runAsync(() -> {
         HttpURLConnection con;
         try {
            con = (HttpURLConnection)new URL("https://dump.viaversion.com/documents").openConnection();
         } catch (IOException var34) {
            Via.getPlatform().getLogger().log(Level.SEVERE, "Error when opening connection to ViaVersion dump service", (Throwable)var34);
            result.completeExceptionally(new DumpUtil.DumpException(DumpUtil.DumpErrorType.CONNECTION, var34));
            return;
         }

         try {
            con.setRequestProperty("Content-Type", "application/json");
            con.addRequestProperty("User-Agent", "ViaVersion-" + Via.getPlatform().getPlatformName() + "/" + version.getPluginVersion());
            con.setRequestMethod("POST");
            con.setDoOutput(true);

            try (OutputStream out = con.getOutputStream()) {
               out.write(new GsonBuilder().setPrettyPrinting().create().toJson(template).getBytes(StandardCharsets.UTF_8));
            }

            if (con.getResponseCode() == 429) {
               result.completeExceptionally(new DumpUtil.DumpException(DumpUtil.DumpErrorType.RATE_LIMITED));
               return;
            }

            String rawOutput;
            try (InputStream inputStream = con.getInputStream()) {
               rawOutput = CharStreams.toString(new InputStreamReader(inputStream));
            }

            JsonObject output = GsonUtil.getGson().fromJson(rawOutput, JsonObject.class);
            if (!output.has("key")) {
               throw new InvalidObjectException("Key is not given in Hastebin output");
            }

            result.complete(urlForId(output.get("key").getAsString()));
         } catch (Exception var37) {
            Via.getPlatform().getLogger().log(Level.SEVERE, "Error when posting ViaVersion dump", (Throwable)var37);
            result.completeExceptionally(new DumpUtil.DumpException(DumpUtil.DumpErrorType.POST, var37));
            printFailureInfo(con);
         }
      });
      return result;
   }

   private static void printFailureInfo(HttpURLConnection connection) {
      try {
         if (connection.getResponseCode() < 200 || connection.getResponseCode() > 400) {
            try (InputStream errorStream = connection.getErrorStream()) {
               String rawOutput = CharStreams.toString(new InputStreamReader(errorStream));
               Via.getPlatform().getLogger().log(Level.SEVERE, "Page returned: " + rawOutput);
            }
         }
      } catch (IOException var14) {
         Via.getPlatform().getLogger().log(Level.SEVERE, "Failed to capture further info", (Throwable)var14);
      }
   }

   public static String urlForId(String id) {
      return String.format("https://dump.viaversion.com/%s", id);
   }

   private static JsonObject getPlayerSample(@Nullable UUID uuid) {
      JsonObject playerSample = new JsonObject();
      JsonObject versions = new JsonObject();
      playerSample.add("versions", versions);
      Map<ProtocolVersion, Integer> playerVersions = new TreeMap<>((o1, o2) -> ProtocolVersion.getIndex(o2) - ProtocolVersion.getIndex(o1));

      for (UserConnection connection : Via.getManager().getConnectionManager().getConnections()) {
         ProtocolVersion protocolVersion = ProtocolVersion.getProtocol(connection.getProtocolInfo().getProtocolVersion());
         playerVersions.compute(protocolVersion, (v, num) -> num != null ? num + 1 : 1);
      }

      for (Entry<ProtocolVersion, Integer> entry : playerVersions.entrySet()) {
         versions.addProperty(entry.getKey().getName(), entry.getValue());
      }

      Set<List<String>> pipelines = new HashSet<>();
      if (uuid != null) {
         UserConnection senderConnection = Via.getAPI().getConnection(uuid);
         if (senderConnection != null && senderConnection.getChannel() != null) {
            pipelines.add(senderConnection.getChannel().pipeline().names());
         }
      }

      for (UserConnection connection : Via.getManager().getConnectionManager().getConnections()) {
         if (connection.getChannel() != null) {
            List<String> names = connection.getChannel().pipeline().names();
            if (pipelines.add(names) && pipelines.size() == 3) {
               break;
            }
         }
      }

      int i = 0;

      for (List<String> pipeline : pipelines) {
         JsonArray senderPipeline = new JsonArray(pipeline.size());

         for (String name : pipeline) {
            senderPipeline.add(name);
         }

         playerSample.add("pipeline-" + i++, senderPipeline);
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

   public static final class DumpException extends RuntimeException {
      private final DumpUtil.DumpErrorType errorType;

      private DumpException(DumpUtil.DumpErrorType errorType, Throwable cause) {
         super(errorType.message(), cause);
         this.errorType = errorType;
      }

      private DumpException(DumpUtil.DumpErrorType errorType) {
         super(errorType.message());
         this.errorType = errorType;
      }

      public DumpUtil.DumpErrorType errorType() {
         return this.errorType;
      }
   }
}
