package exhibition.management.spotify;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import exhibition.Client;
import exhibition.management.notifications.user.Notifications;
import exhibition.management.spotify.othershit.SpotifyError;
import exhibition.management.spotify.othershit.SpotifyHttpClient;
import exhibition.management.spotify.othershit.SpotifyStatus;
import exhibition.management.spotify.othershit.SpotifyTrack;
import exhibition.management.spotify.othershit.SpotifyWebAPI;
import io.netty.channel.nio.NioEventLoopGroup;

public class SpotifyManager {
   public static final Gson gson = new Gson();
   public static final int SPOTIFY_COLOR_ACCENT = -14756000;
   public static final int SPOTIFY_COLOR_SECOND = -12566464;
   public static final int SPOTIFY_COLOR_BACKGROUND = -14145496;
   private static final int PORT_START = 4380;
   private static final int PORT_END = 4389;
   private static final int DEFAULT_TIMEOUT_MILLIS = 10000;
   private static final int POLL_TIME = 60;
   public static NioEventLoopGroup CLIENT_NIO_EVENTLOOP;
   private static final String characters = "abcdefghijklmnopqrstuvwxyz";
   private static final Map originHeader = Collections.singletonMap("Origin", "https://open.spotify.com");
   private volatile boolean connected = false;
   private boolean connecting = false;
   private int reconnectTicks = 0;
   private SpotifyError disconnectError = null;
   private int port = 4380;
   private String oAuthToken;
   private String csrfToken;
   private SpotifyStatus status;
   private long lastStatusReceived;
   private final SpotifyWebAPI webAPI;
   private AsyncExecutor asyncExecutor = new AsyncExecutor();
   private Random random = new Random();

   public SpotifyManager() {
      Client.instance.sout("Initializing Spotify Manager!");
      CLIENT_NIO_EVENTLOOP = new NioEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Exhibition Thingy from 5Zig lol").setDaemon(true).build());
      this.webAPI = new SpotifyWebAPI();
   }

   public SpotifyWebAPI getWebAPI() {
      return this.webAPI;
   }

   public void onEvent() {
      if (!this.connected && !this.connecting && --this.reconnectTicks <= 0 && CLIENT_NIO_EVENTLOOP != null) {
         System.out.println("Connecting");
         this.connecting = true;
         this.asyncExecutor.execute(new Runnable() {
            public void run() {
               SpotifyManager.this.connect();
            }
         });
      } else if (this.connected && System.currentTimeMillis() - this.lastStatusReceived > 90000L) {
         this.reconnect(0);
      }

   }

   private void connect() {
       this.detectPort(new Callback() {
    	   
		@Override
		public void call(Object callback) {}

		@Override
		public void call(Integer callback) {
			if (callback == null) {
                System.out.println("Could not find instance!");
                SpotifyManager.this.reconnect(60);
            } else {
                SpotifyManager.this.port = callback;
                System.out.println("Found running Spotify instance on port " + SpotifyManager.this.port + "!");
                SpotifyManager.this.doAuth(new Runnable() {
                    @Override
                    public void run() {
                        SpotifyManager.this.lastStatusReceived = System.currentTimeMillis();
                        SpotifyManager.this.connecting = false;
                        SpotifyManager.this.disconnectError = null;
                        SpotifyManager.this.connected = true;
                        SpotifyManager.this.loadInitialStatus();
                    }
                });
            }
		}

       });
   }

   private void reconnect() {
      this.reconnect(10);
   }

   private void reconnect(int time) {
      if (this.disconnectError == null) {
         this.disconnectError = SpotifyError.UNKNOWN;
      }

      this.reconnectTicks = 20 * time;
      this.connecting = false;
      this.connected = false;
      this.csrfToken = null;
      this.oAuthToken = null;
      System.out.println("Reconnecting to Spotify...");
   }

   public boolean isConnected() {
      return this.connected;
   }

   public SpotifyError getDisconnectError() {
      return this.disconnectError;
   }

   private void detectPort(Callback callback) {
      this.checkPort(4380, callback);
   }

   private void checkPort(final int port, final Callback callback) {
      this.makeRequest(this.getURL("/service/version.json?service=remote", port), Collections.singletonMap("service", "remote"), originHeader, new HttpResponseCallback() {
         public void call(String response, int responseCode, Throwable throwable) {
            if (responseCode == 200) {
               callback.call(port);
            } else if (port < 4389) {
               SpotifyManager.this.checkPort(port + 1, callback);
            } else {
               callback.call(null);
            }

         }
      });
   }

   private void doAuth(final Runnable onDone) {
      this.loadOAuthToken(new Runnable() {
         public void run() {
            SpotifyManager.this.loadCsrfToken(onDone);
         }
      });
   }

   private void loadOAuthToken(final Runnable runnable) {
      if (this.oAuthToken != null) {
         runnable.run();
      } else {
         this.makeRequest("https://open.spotify.com/token", Collections.emptyMap(), Collections.emptyMap(), new HttpResponseCallback() {
            public void call(String response, int responseCode, Throwable throwable) {
               if (throwable != null) {
                  System.out.println("[Spotify Error]: Could not load Spotify OAuth-Token! " + throwable);
                  SpotifyManager.this.reconnect();
               } else if (response != null) {
                  JsonElement element = (new JsonParser()).parse(response);
                  if (!SpotifyManager.this.parseError(element)) {
                     if (element.isJsonObject() && element.getAsJsonObject().has("t") && element.getAsJsonObject().get("t").isJsonPrimitive()) {
                        SpotifyManager.this.oAuthToken = element.getAsJsonObject().get("t").getAsString();
                        runnable.run();
                     } else {
                        System.out.println("[Spotify Error]: Could not load Spotify OAuth-Token!");
                        SpotifyManager.this.reconnect();
                     }
                  }
               }

            }
         });
      }

   }

   private void loadCsrfToken(final Runnable runnable) {
      this.makeRequest(this.getURL("/simplecsrf/token.json"), Collections.emptyMap(), originHeader, new HttpResponseCallback() {
         public void call(String response, int responseCode, Throwable throwable) {
            if (throwable != null) {
               System.out.println("[Spotify Error]: Could not load Spotify Csrf-Token! " + throwable);
               SpotifyManager.this.reconnect();
            } else if (response != null) {
               JsonElement element = (new JsonParser()).parse(response);
               if (!SpotifyManager.this.parseError(element)) {
                  if (element.isJsonObject() && element.getAsJsonObject().has("token") && element.getAsJsonObject().get("token").isJsonPrimitive()) {
                     SpotifyManager.this.csrfToken = element.getAsJsonObject().get("token").getAsString();
                     runnable.run();
                  } else {
                     System.out.println("[Spotify Error]: Could not load Spotify Csrf-Token: " + response);
                     SpotifyManager.this.reconnect();
                  }
               }
            }

         }
      });
   }

   private void loadInitialStatus() {
      Map authParams = this.createAuthParams();
      this.makeRequest(this.getURL("/remote/status.json"), authParams, originHeader, new HttpResponseCallback() {
         public void call(String response, int responseCode, Throwable throwable) {
            if (throwable != null) {
               System.out.println("[Spotify Error]: Could not load Spotify Status! " + throwable);
               SpotifyManager.this.reconnect();
            } else if (response != null) {
               JsonElement element = (new JsonParser()).parse(response);
               if (!SpotifyManager.this.parseError(element)) {
                  SpotifyManager.this.setStatus((SpotifyStatus)SpotifyManager.gson.fromJson(element, SpotifyStatus.class));
                  SpotifyManager.this.pollStatus();
               }
            }

         }
      });
   }

   private void pollStatus() {
      Map authParams = this.createAuthParams();
      authParams.put("returnafter", String.valueOf(60));
      authParams.put("returnon", "login,logout,play,pause,error,ap");
      this.makeRequest(this.getURL("/remote/status.json"), authParams, originHeader, new HttpResponseCallback() {
         public void call(String response, int responseCode, Throwable throwable) {
            if (throwable != null) {
               System.out.println("Error while polling Spotify status! " + throwable);
               SpotifyManager.this.setStatus((SpotifyStatus)null);
            } else if (response != null) {
               JsonElement element = (new JsonParser()).parse(response);
               if (!SpotifyManager.this.parseError(element)) {
                  try {
                     SpotifyManager.this.setStatus((SpotifyStatus)SpotifyManager.gson.fromJson(element, SpotifyStatus.class));
                  } catch (Exception var6) {
                     var6.printStackTrace();
                  }

                  SpotifyManager.this.pollStatus();
               }
            }

         }
      }, 70000);
   }

   private void play(String uri) {
      Map authParams = this.createAuthParams();
      authParams.put("uri", uri);
      authParams.put("context", uri);
      this.makeRequest(this.getURL("/remote/play.json"), authParams, originHeader, new HttpResponseCallback() {
         public void call(String response, int responseCode, Throwable throwable) {
         }
      });
   }

   public void pauseSong(boolean pause) {
      Map authParams = this.createAuthParams();
      authParams.put("pause", String.valueOf(pause));
      this.makeRequest(this.getURL("/remote/pause.json"), authParams, originHeader, new HttpResponseCallback() {
         public void call(String response, int responseCode, Throwable throwable) {
            if (throwable != null) {
               System.out.println("Could not pause! " + throwable);
            } else if (response != null) {
               JsonElement var4 = (new JsonParser()).parse(response);
            }

         }
      });
   }

   public SpotifyStatus getStatus() {
      return this.status;
   }

   public void setStatus(SpotifyStatus status) {
      long currentTimeMillis = System.currentTimeMillis();
      this.lastStatusReceived = currentTimeMillis;

      try {
         if (status != null) {
            status.setServerTime(currentTimeMillis);
            if (status.getTrack() != null && status.getTrack().hasTrackInformation()) {
               if (this.status != null) {
                  boolean playing = status.isPlaying();
                  boolean sameTrack = status.getTrack() != null && this.status.getTrack() != null && (status.getTrack().equals(this.status.getTrack()) || status.getTrack().getTrackInformation().getName().equals(this.status.getTrack().getTrackInformation().getName()));
                  if (sameTrack && this.status.isPlaying() != playing) {
                     Notifications.getManager().post("Status", "Spotify is now " + (playing ? "playing" : "paused") + "!", 750L, Notifications.Type.SPOTIFY);
                  }
               }

               if (this.status == null || !status.getTrack().equals(this.status.getTrack()) && !status.getTrack().getTrackInformation().getName().equals(this.status.getTrack().getTrackInformation().getName())) {
                  Notifications.getManager().post("Now Playing", status.getTrack().getTrackInformation().getName() + " by " + status.getTrack().getArtistInformation().getName(), 1250L, Notifications.Type.SPOTIFY);
               }

               this.resolveTrackImage(status.getTrack());
            }
         }
      } catch (Exception var6) {
         var6.printStackTrace();
         Notifications.getManager().post("Spotify Error", "Exception! Toggle off and on again if broken.", Notifications.Type.SPOTIFY);
      }

      if (status != null) {
         this.status = status;
      }

   }

   public void resolveTrackImage(SpotifyTrack track) {
      this.webAPI.resolveTrackImage(track);
   }

   private String getURL(String path, int port) {
      return "http://" + this.generateLocalHostname() + ":" + port + path;
   }

   private String getURL(String path) {
      return this.getURL(path, this.port);
   }

   private String generateLocalHostname() {
      StringBuilder buffer = new StringBuilder(10);

      for(int i = 0; i < buffer.capacity(); ++i) {
         buffer.append("abcdefghijklmnopqrstuvwxyz".charAt(this.random.nextInt("abcdefghijklmnopqrstuvwxyz".length())));
      }

      return buffer.toString() + ".spotilocal.com";
   }

   private Map createAuthParams() {
      Map params = Maps.newHashMap();
      params.put("oauth", this.oAuthToken);
      params.put("csrf", this.csrfToken);
      return params;
   }

   private void makeRequest(String url, Map params, Map headers, HttpResponseCallback callback) {
      this.makeRequest(url, params, headers, callback, 10000);
   }

   private void makeRequest(String url, Map params, Map headers, HttpResponseCallback callback, int timeout) {
      if (!params.isEmpty()) {
         StringBuilder path = new StringBuilder();
         Iterator var7 = params.entrySet().iterator();

         while(var7.hasNext()) {
            Entry entry = (Entry)var7.next();
            path.append("&").append((String)entry.getKey()).append("=").append((String)entry.getValue());
         }

         path.replace(0, 1, "?");
         url = url + path.toString();
      }

      SpotifyHttpClient.get(url, headers, timeout, true, CLIENT_NIO_EVENTLOOP, callback);
   }

   private boolean parseError(JsonElement element) {
      if (element.isJsonObject() && element.getAsJsonObject().has("error") && element.getAsJsonObject().get("error").isJsonObject()) {
         JsonObject error = element.getAsJsonObject().get("error").getAsJsonObject();
         if (error.has("type") && error.get("type").isJsonPrimitive()) {
            int type = error.get("type").getAsInt();
            this.disconnectError = SpotifyError.byId(type);
            if (this.disconnectError != null) {
               System.out.println("Could not connect to Spotify: " + this.disconnectError);
            }
         }

         this.reconnect();
         return true;
      } else {
         return false;
      }
   }
}
