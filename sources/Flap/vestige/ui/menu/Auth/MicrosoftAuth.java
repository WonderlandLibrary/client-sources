package vestige.ui.menu.Auth;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.sun.net.httpserver.HttpServer;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import net.minecraft.util.Session;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public final class MicrosoftAuth {
   public static final RequestConfig REQUEST_CONFIG = RequestConfig.custom().setConnectionRequestTimeout(30000).setConnectTimeout(30000).setSocketTimeout(30000).build();
   public static final String CLIENT_ID = "42a60a84-599d-44b2-a7c6-b00cdef1d6a2";
   public static final int PORT = 25575;

   public static CompletableFuture<String> acquireMSAuthCode(Executor executor) {
      return acquireMSAuthCode(SystemUtils::openWebLink, executor);
   }

   public static CompletableFuture<String> acquireMSAuthCode(Consumer<URI> browserAction, Executor executor) {
      return CompletableFuture.supplyAsync(() -> {
         try {
            String state = RandomStringUtils.randomAlphanumeric(8);
            HttpServer server = HttpServer.create(new InetSocketAddress(25575), 0);
            CountDownLatch latch = new CountDownLatch(1);
            AtomicReference<String> authCode = new AtomicReference((Object)null);
            AtomicReference<String> errorMsg = new AtomicReference((Object)null);
            server.createContext("/callback", (exchange) -> {
               Map<String, String> query = (Map)URLEncodedUtils.parse(exchange.getRequestURI().toString().replaceAll("/callback\\?", ""), StandardCharsets.UTF_8).stream().collect(Collectors.toMap(NameValuePair::getName, NameValuePair::getValue));
               if (!state.equals(query.get("state"))) {
                  errorMsg.set(String.format("State mismatch! Expected '%s' but got '%s'.", state, query.get("state")));
               } else if (query.containsKey("code")) {
                  authCode.set((String)query.get("code"));
               } else if (query.containsKey("error")) {
                  errorMsg.set(String.format("%s: %s", query.get("error"), query.get("error_description")));
               }

               InputStream stream = MicrosoftAuth.class.getResourceAsStream("/callback.html");
               byte[] response = stream != null ? IOUtils.toByteArray(stream) : new byte[0];
               exchange.getResponseHeaders().add("Content-Type", "text/html");
               exchange.sendResponseHeaders(200, (long)response.length);
               exchange.getResponseBody().write(response);
               exchange.getResponseBody().close();
               latch.countDown();
            });
            URIBuilder uriBuilder = (new URIBuilder("https://login.live.com/oauth20_authorize.srf")).addParameter("client_id", "42a60a84-599d-44b2-a7c6-b00cdef1d6a2").addParameter("response_type", "code").addParameter("redirect_uri", String.format("http://localhost:%d/callback", server.getAddress().getPort())).addParameter("scope", "XboxLive.signin XboxLive.offline_access").addParameter("state", state).addParameter("prompt", "select_account");
            URI uri = uriBuilder.build();
            browserAction.accept(uri);

            String var8;
            try {
               server.start();
               latch.await();
               var8 = (String)Optional.ofNullable((String)authCode.get()).filter((code) -> {
                  return !StringUtils.isBlank(code);
               }).orElseThrow(() -> {
                  return new Exception((String)Optional.ofNullable((String)errorMsg.get()).orElse("There was no auth code or error description present."));
               });
            } finally {
               server.stop(2);
            }

            return var8;
         } catch (InterruptedException var14) {
            throw new CancellationException("Microsoft auth code acquisition was cancelled!");
         } catch (Exception var15) {
            throw new CompletionException("Unable to acquire Microsoft auth code!", var15);
         }
      }, executor);
   }

   public static CompletableFuture<Map<String, String>> acquireMSAccessTokens(String authCode, Executor executor) {
      return CompletableFuture.supplyAsync(() -> {
         try {
            CloseableHttpClient client = HttpClients.createMinimal();

            HashMap var8;
            try {
               HttpPost request = new HttpPost(URI.create("https://login.live.com/oauth20_token.srf"));
               request.setConfig(REQUEST_CONFIG);
               request.setHeader("Content-Type", "application/x-www-form-urlencoded");
               request.setEntity(new UrlEncodedFormEntity(Arrays.asList(new BasicNameValuePair("client_id", "42a60a84-599d-44b2-a7c6-b00cdef1d6a2"), new BasicNameValuePair("grant_type", "authorization_code"), new BasicNameValuePair("code", authCode), new BasicNameValuePair("redirect_uri", String.format("http://localhost:%d/callback", 25575))), "UTF-8"));
               HttpResponse res = client.execute(request);
               JsonObject json = (new JsonParser()).parse(EntityUtils.toString(res.getEntity())).getAsJsonObject();
               String accessToken = (String)Optional.ofNullable(json.get("access_token")).map(JsonElement::getAsString).filter((token) -> {
                  return !StringUtils.isBlank(token);
               }).orElseThrow(() -> {
                  return new Exception(json.has("error") ? String.format("%s: %s", json.get("error").getAsString(), json.get("error_description").getAsString()) : "There was no Microsoft access token or error description present.");
               });
               String refreshToken = (String)Optional.ofNullable(json.get("refresh_token")).map(JsonElement::getAsString).filter((token) -> {
                  return !StringUtils.isBlank(token);
               }).orElseThrow(() -> {
                  return new Exception(json.has("error") ? String.format("%s: %s", json.get("error").getAsString(), json.get("error_description").getAsString()) : "There was no Microsoft refresh token or error description present.");
               });
               Map<String, String> result = new HashMap();
               result.put("access_token", accessToken);
               result.put("refresh_token", refreshToken);
               var8 = (HashMap) result;
            } catch (Throwable var10) {
               if (client != null) {
                  try {
                     client.close();
                  } catch (Throwable var9) {
                     var10.addSuppressed(var9);
                  }
               }

               throw var10;
            }

            if (client != null) {
               client.close();
            }

            return var8;
         } catch (InterruptedException var11) {
            throw new CancellationException("Microsoft access tokens acquisition was cancelled!");
         } catch (Exception var12) {
            throw new CompletionException("Unable to acquire Microsoft access tokens!", var12);
         }
      }, executor);
   }

   public static CompletableFuture<Map<String, String>> refreshMSAccessTokens(String msToken, Executor executor) {
      return CompletableFuture.supplyAsync(() -> {
         try {
            CloseableHttpClient client = HttpClients.createMinimal();

            HashMap var8;
            try {
               HttpPost request = new HttpPost(URI.create("https://login.live.com/oauth20_token.srf"));
               request.setConfig(REQUEST_CONFIG);
               request.setHeader("Content-Type", "application/x-www-form-urlencoded");
               request.setEntity(new UrlEncodedFormEntity(Arrays.asList(new BasicNameValuePair("client_id", "42a60a84-599d-44b2-a7c6-b00cdef1d6a2"), new BasicNameValuePair("grant_type", "refresh_token"), new BasicNameValuePair("refresh_token", msToken), new BasicNameValuePair("redirect_uri", String.format("http://localhost:%d/callback", 25575))), "UTF-8"));
               HttpResponse res = client.execute(request);
               JsonObject json = (new JsonParser()).parse(EntityUtils.toString(res.getEntity())).getAsJsonObject();
               String accessToken = (String)Optional.ofNullable(json.get("access_token")).map(JsonElement::getAsString).filter((token) -> {
                  return !StringUtils.isBlank(token);
               }).orElseThrow(() -> {
                  return new Exception(json.has("error") ? String.format("%s: %s", json.get("error").getAsString(), json.get("error_description").getAsString()) : "There was no Microsoft access token or error description present.");
               });
               String refreshToken = (String)Optional.ofNullable(json.get("refresh_token")).map(JsonElement::getAsString).filter((token) -> {
                  return !StringUtils.isBlank(token);
               }).orElseThrow(() -> {
                  return new Exception(json.has("error") ? String.format("%s: %s", json.get("error").getAsString(), json.get("error_description").getAsString()) : "There was no Microsoft refresh token or error description present.");
               });
               Map<String, String> result = new HashMap();
               result.put("access_token", accessToken);
               result.put("refresh_token", refreshToken);
               var8 = (HashMap) result;
            } catch (Throwable var10) {
               if (client != null) {
                  try {
                     client.close();
                  } catch (Throwable var9) {
                     var10.addSuppressed(var9);
                  }
               }

               throw var10;
            }

            if (client != null) {
               client.close();
            }

            return var8;
         } catch (InterruptedException var11) {
            throw new CancellationException("Microsoft access tokens acquisition was cancelled!");
         } catch (Exception var12) {
            throw new CompletionException("Unable to acquire Microsoft access tokens!", var12);
         }
      }, executor);
   }

   public static CompletableFuture<String> acquireXboxAccessToken(String accessToken, Executor executor) {
      return CompletableFuture.supplyAsync(() -> {
         try {
            CloseableHttpClient client = HttpClients.createMinimal();

            String var7;
            try {
               HttpPost request = new HttpPost(URI.create("https://user.auth.xboxlive.com/user/authenticate"));
               JsonObject entity = new JsonObject();
               JsonObject properties = new JsonObject();
               properties.addProperty("AuthMethod", "RPS");
               properties.addProperty("SiteName", "user.auth.xboxlive.com");
               properties.addProperty("RpsTicket", String.format("d=%s", accessToken));
               entity.add("Properties", properties);
               entity.addProperty("RelyingParty", "http://auth.xboxlive.com");
               entity.addProperty("TokenType", "JWT");
               request.setConfig(REQUEST_CONFIG);
               request.setHeader("Content-Type", "application/json");
               request.setEntity(new StringEntity(entity.toString()));
               HttpResponse res = client.execute(request);
               JsonObject json = res.getStatusLine().getStatusCode() == 200 ? (new JsonParser()).parse(EntityUtils.toString(res.getEntity())).getAsJsonObject() : new JsonObject();
               var7 = (String)Optional.ofNullable(json.get("Token")).map(JsonElement::getAsString).filter((token) -> {
                  return !StringUtils.isBlank(token);
               }).orElseThrow(() -> {
                  return new Exception(json.has("XErr") ? String.format("%s: %s", json.get("XErr").getAsString(), json.get("Message").getAsString()) : "There was no access token or error description present.");
               });
            } catch (Throwable var9) {
               if (client != null) {
                  try {
                     client.close();
                  } catch (Throwable var8) {
                     var9.addSuppressed(var8);
                  }
               }

               throw var9;
            }

            if (client != null) {
               client.close();
            }

            return var7;
         } catch (InterruptedException var10) {
            throw new CancellationException("Xbox Live access token acquisition was cancelled!");
         } catch (Exception var11) {
            throw new CompletionException("Unable to acquire Xbox Live access token!", var11);
         }
      }, executor);
   }

   public static CompletableFuture<Map<String, String>> acquireXboxXstsToken(String accessToken, Executor executor) {
      return CompletableFuture.supplyAsync(() -> {
         try {
            CloseableHttpClient client = HttpClients.createMinimal();

            Map var8;
            try {
               HttpPost request = new HttpPost("https://xsts.auth.xboxlive.com/xsts/authorize");
               JsonObject entity = new JsonObject();
               JsonObject properties = new JsonObject();
               JsonArray userTokens = new JsonArray();
               userTokens.add(new JsonPrimitive(accessToken));
               properties.addProperty("SandboxId", "RETAIL");
               properties.add("UserTokens", userTokens);
               entity.add("Properties", properties);
               entity.addProperty("RelyingParty", "rp://api.minecraftservices.com/");
               entity.addProperty("TokenType", "JWT");
               request.setConfig(REQUEST_CONFIG);
               request.setHeader("Content-Type", "application/json");
               request.setEntity(new StringEntity(entity.toString()));
               HttpResponse res = client.execute(request);
               JsonObject json = res.getStatusLine().getStatusCode() == 200 ? (new JsonParser()).parse(EntityUtils.toString(res.getEntity())).getAsJsonObject() : new JsonObject();
               var8 = (Map)Optional.ofNullable(json.get("Token")).map(JsonElement::getAsString).filter((token) -> {
                  return !StringUtils.isBlank(token);
               }).map((token) -> {
                  String uhs = json.get("DisplayClaims").getAsJsonObject().get("xui").getAsJsonArray().get(0).getAsJsonObject().get("uhs").getAsString();
                  Map<String, String> result = new HashMap();
                  result.put("Token", token);
                  result.put("uhs", uhs);
                  return result;
               }).orElseThrow(() -> {
                  return new Exception(json.has("XErr") ? String.format("%s: %s", json.get("XErr").getAsString(), json.get("Message").getAsString()) : "There was no access token or error description present.");
               });
            } catch (Throwable var10) {
               if (client != null) {
                  try {
                     client.close();
                  } catch (Throwable var9) {
                     var10.addSuppressed(var9);
                  }
               }

               throw var10;
            }

            if (client != null) {
               client.close();
            }

            return var8;
         } catch (InterruptedException var11) {
            throw new CancellationException("Xbox Live XSTS token acquisition was cancelled!");
         } catch (Exception var12) {
            throw new CompletionException("Unable to acquire Xbox Live XSTS token!", var12);
         }
      }, executor);
   }

   public static CompletableFuture<String> acquireMCAccessToken(String xstsToken, String userHash, Executor executor) {
      return CompletableFuture.supplyAsync(() -> {
         try {
            CloseableHttpClient client = HttpClients.createMinimal();

            String var6;
            try {
               HttpPost request = new HttpPost(URI.create("https://api.minecraftservices.com/authentication/login_with_xbox"));
               request.setConfig(REQUEST_CONFIG);
               request.setHeader("Content-Type", "application/json");
               request.setEntity(new StringEntity(String.format("{\"identityToken\": \"XBL3.0 x=%s;%s\"}", userHash, xstsToken)));
               HttpResponse res = client.execute(request);
               JsonObject json = (new JsonParser()).parse(EntityUtils.toString(res.getEntity())).getAsJsonObject();
               var6 = (String)Optional.ofNullable(json.get("access_token")).map(JsonElement::getAsString).filter((token) -> {
                  return !StringUtils.isBlank(token);
               }).orElseThrow(() -> {
                  return new Exception(json.has("error") ? String.format("%s: %s", json.get("error").getAsString(), json.get("errorMessage").getAsString()) : "There was no access token or error description present.");
               });
            } catch (Throwable var8) {
               if (client != null) {
                  try {
                     client.close();
                  } catch (Throwable var7) {
                     var8.addSuppressed(var7);
                  }
               }

               throw var8;
            }

            if (client != null) {
               client.close();
            }

            return var6;
         } catch (InterruptedException var9) {
            throw new CancellationException("Minecraft access token acquisition was cancelled!");
         } catch (Exception var10) {
            throw new CompletionException("Unable to acquire Minecraft access token!", var10);
         }
      }, executor);
   }

   public static CompletableFuture<Session> login(String mcToken, Executor executor) {
      return CompletableFuture.supplyAsync(() -> {
         try {
            CloseableHttpClient client = HttpClients.createMinimal();

            Session var5;
            try {
               HttpGet request = new HttpGet(URI.create("https://api.minecraftservices.com/minecraft/profile"));
               request.setConfig(REQUEST_CONFIG);
               request.setHeader("Authorization", "Bearer " + mcToken);
               HttpResponse res = client.execute(request);
               JsonObject json = (new JsonParser()).parse(EntityUtils.toString(res.getEntity())).getAsJsonObject();
               var5 = (Session)Optional.ofNullable(json.get("id")).map(JsonElement::getAsString).filter((uuid) -> {
                  return !StringUtils.isBlank(uuid);
               }).map((uuid) -> {
                  return new Session(json.get("name").getAsString(), uuid, mcToken, Session.Type.MOJANG.toString());
               }).orElseThrow(() -> {
                  return new Exception(json.has("error") ? String.format("%s: %s", json.get("error").getAsString(), json.get("errorMessage").getAsString()) : "There was no profile or error description present.");
               });
            } catch (Throwable var7) {
               if (client != null) {
                  try {
                     client.close();
                  } catch (Throwable var6) {
                     var7.addSuppressed(var6);
                  }
               }

               throw var7;
            }

            if (client != null) {
               client.close();
            }

            return var5;
         } catch (InterruptedException var8) {
            throw new CancellationException("Minecraft profile fetching was cancelled!");
         } catch (Exception var9) {
            throw new CompletionException("Unable to fetch Minecraft profile!", var9);
         }
      }, executor);
   }
}
