// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.alt.design;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import java.util.StringJoiner;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.io.InputStream;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.UUID;
import com.mojang.authlib.exceptions.AuthenticationException;
import java.net.URISyntaxException;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.net.URI;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonObject;
import java.net.HttpURLConnection;
import java.net.URL;
import net.minecraft.util.Session;

public class Login
{
    private static final String clientId = "00000000402b5328";
    private static final String scopeUrl = "service::user.auth.xboxlive.com::MBI_SSL";
    private static final String servicesApi = "https://api.minecraftservices.com";
    private static String loginUrl;
    private static String loginCookie;
    private static String loginPPFT;
    
    public static Session logIn(final String email, final String password) throws IOException, URISyntaxException, AuthenticationException {
        final MicrosoftToken microsoftToken = generateTokenPair(generateLoginCode(email, password));
        final XboxLiveToken xboxLiveToken = generateXboxTokenPair(microsoftToken);
        final XboxToken xboxToken = generateXboxTokenPair(xboxLiveToken);
        final URL url = new URL("https://api.minecraftservices.com/authentication/login_with_xbox");
        final URLConnection urlConnection = url.openConnection();
        final HttpURLConnection httpURLConnection = (HttpURLConnection)urlConnection;
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        final JsonObject request = new JsonObject();
        request.add("identityToken", new JsonPrimitive("XBL3.0 x=" + xboxToken.uhs + ";" + xboxToken.token));
        final String requestBody = request.toString();
        httpURLConnection.setFixedLengthStreamingMode(requestBody.length());
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        httpURLConnection.setRequestProperty("Host", new URI("https://api.minecraftservices.com").getPath());
        httpURLConnection.connect();
        httpURLConnection.getOutputStream().write(requestBody.getBytes(StandardCharsets.US_ASCII));
        final JsonObject jsonObject = parseResponseData(httpURLConnection);
        final MinecraftProfile minecraftProfile = checkOwnership(jsonObject.get("access_token").getAsString());
        return new Session(minecraftProfile.username, minecraftProfile.uuid.toString(), jsonObject.get("access_token").getAsString(), "LEGACY");
    }
    
    public static MinecraftProfile checkOwnership(final String minecraftToken) throws AuthenticationException {
        try {
            final URL url = new URL("https://api.minecraftservices.com/minecraft/profile");
            final URLConnection urlConnection = url.openConnection();
            final HttpURLConnection httpURLConnection = (HttpURLConnection)urlConnection;
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Authorization", "Bearer " + minecraftToken);
            httpURLConnection.setRequestProperty("Host", new URI("https://api.minecraftservices.com").getPath());
            httpURLConnection.connect();
            final JsonObject jsonObject = parseResponseData(httpURLConnection);
            final UUID uuid = generateUUID(jsonObject.get("id").getAsString());
            final String name = jsonObject.get("name").getAsString();
            return new MinecraftProfile(uuid, name);
        }
        catch (IOException exception) {
            throw new AuthenticationException(String.format("Authentication error. Request could not be made! Cause: '%s'", exception.getMessage()));
        }
        catch (AuthenticationException | URISyntaxException ex2) {
            final Exception ex;
            final Exception e = ex;
            throw new RuntimeException(e);
        }
    }
    
    private static UUID generateUUID(final String trimmedUUID) {
        final StringBuilder builder = new StringBuilder(trimmedUUID.trim());
        builder.insert(20, "-");
        builder.insert(16, "-");
        builder.insert(12, "-");
        builder.insert(8, "-");
        return UUID.fromString(builder.toString());
    }
    
    public static String generateLoginCode(final String email, final String password) throws AuthenticationException {
        try {
            final URL url = new URL("https://login.live.com/oauth20_authorize.srf?redirect_uri=https://login.live.com/oauth20_desktop.srf&scope=service::user.auth.xboxlive.com::MBI_SSL&display=touch&response_type=code&locale=en&client_id=00000000402b5328");
            final HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            final InputStream inputStream = (httpURLConnection.getResponseCode() == 200) ? httpURLConnection.getInputStream() : httpURLConnection.getErrorStream();
            Login.loginCookie = httpURLConnection.getHeaderField("set-cookie");
            final String responseData = new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining());
            Matcher bodyMatcher = Pattern.compile("sFTTag:[ ]?'.*value=\"(.*)\"/>'").matcher(responseData);
            if (!bodyMatcher.find()) {
                throw new AuthenticationException("Authentication error. Could not find 'LOGIN-PFTT' tag from response!");
            }
            Login.loginPPFT = bodyMatcher.group(1);
            bodyMatcher = Pattern.compile("urlPost:[ ]?'(.+?(?='))").matcher(responseData);
            if (!bodyMatcher.find()) {
                throw new AuthenticationException("Authentication error. Could not find 'LOGIN-URL' tag from response!");
            }
            Login.loginUrl = bodyMatcher.group(1);
            if (Login.loginCookie == null || Login.loginPPFT == null || Login.loginUrl == null) {
                throw new AuthenticationException("Authentication error. Error in authentication process!");
            }
        }
        catch (IOException exception) {
            throw new AuthenticationException(String.format("Authentication error. Request could not be made! Cause: '%s'", exception.getMessage()));
        }
        return sendCodeData(email, password);
    }
    
    private static String sendCodeData(final String email, final String password) throws AuthenticationException {
        final HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("login", email);
        requestData.put("loginfmt", email);
        requestData.put("passwd", password);
        requestData.put("PPFT", Login.loginPPFT);
        final String postData = encodeURL(requestData);
        String authToken;
        try {
            final byte[] data = postData.getBytes(StandardCharsets.UTF_8);
            final HttpURLConnection connection = (HttpURLConnection)new URL(Login.loginUrl).openConnection();
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            connection.setRequestProperty("Content-Length", String.valueOf(data.length));
            connection.setRequestProperty("Cookie", Login.loginCookie);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.getOutputStream().write(data);
            if (connection.getResponseCode() != 200 || connection.getURL().toString().equalsIgnoreCase(Login.loginUrl)) {
                throw new AuthenticationException("Authentication error. Username or password is not valid.");
            }
            final Pattern pattern = Pattern.compile("[?|&]code=([\\w.-]+)");
            final Matcher tokenMatcher = pattern.matcher(URLDecoder.decode(connection.getURL().toString(), StandardCharsets.UTF_8.name()));
            if (!tokenMatcher.find()) {
                throw new AuthenticationException("Authentication error. Could not handle data from response.");
            }
            authToken = tokenMatcher.group(1);
        }
        catch (IOException exception) {
            throw new AuthenticationException(String.format("Authentication error. Request could not be made! Cause: '%s'", exception.getMessage()));
        }
        Login.loginUrl = null;
        Login.loginCookie = null;
        Login.loginPPFT = null;
        return authToken;
    }
    
    private static void sendXboxRequest(final HttpURLConnection httpURLConnection, final JsonObject request, final JsonObject properties) throws IOException {
        request.add("Properties", properties);
        final String requestBody = request.toString();
        httpURLConnection.setFixedLengthStreamingMode(requestBody.length());
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.connect();
        httpURLConnection.getOutputStream().write(requestBody.getBytes(StandardCharsets.US_ASCII));
    }
    
    public static MicrosoftToken generateTokenPair(final String authToken) throws AuthenticationException {
        try {
            final HashMap<String, String> arguments = new HashMap<String, String>();
            arguments.put("client_id", "00000000402b5328");
            arguments.put("code", authToken);
            arguments.put("grant_type", "authorization_code");
            arguments.put("redirect_uri", "https://login.live.com/oauth20_desktop.srf");
            arguments.put("scope", "service::user.auth.xboxlive.com::MBI_SSL");
            final StringJoiner argumentBuilder = new StringJoiner("&");
            arguments.forEach((key, value) -> argumentBuilder.add(encodeURL(key) + "=" + encodeURL(value)));
            final byte[] data = argumentBuilder.toString().getBytes(StandardCharsets.UTF_8);
            final URL url = new URL("https://login.live.com/oauth20_token.srf");
            final URLConnection urlConnection = url.openConnection();
            final HttpURLConnection httpURLConnection = (HttpURLConnection)urlConnection;
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setFixedLengthStreamingMode(data.length);
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.connect();
            httpURLConnection.getOutputStream().write(data);
            final JsonObject jsonObject = parseResponseData(httpURLConnection);
            return new MicrosoftToken(jsonObject.get("access_token").getAsString(), jsonObject.get("refresh_token").getAsString());
        }
        catch (IOException exception) {
            throw new AuthenticationException(String.format("Authentication error. Request could not be made! Cause: '%s'", exception.getMessage()));
        }
    }
    
    public static XboxLiveToken generateXboxTokenPair(final MicrosoftToken microsoftToken) throws AuthenticationException {
        try {
            final URL url = new URL("https://user.auth.xboxlive.com/user/authenticate");
            final URLConnection urlConnection = url.openConnection();
            final HttpURLConnection httpURLConnection = (HttpURLConnection)urlConnection;
            httpURLConnection.setDoOutput(true);
            final JsonObject request = new JsonObject();
            request.add("RelyingParty", new JsonPrimitive("http://auth.xboxlive.com"));
            request.add("TokenType", new JsonPrimitive("JWT"));
            final JsonObject properties = new JsonObject();
            properties.add("AuthMethod", new JsonPrimitive("RPS"));
            properties.add("SiteName", new JsonPrimitive("user.auth.xboxlive.com"));
            properties.add("RpsTicket", new JsonPrimitive(microsoftToken.token));
            sendXboxRequest(httpURLConnection, request, properties);
            final JsonObject jsonObject = parseResponseData(httpURLConnection);
            final String uhs = jsonObject.get("DisplayClaims").getAsJsonObject().getAsJsonArray("xui").get(0).getAsJsonObject().get("uhs").getAsString();
            return new XboxLiveToken(jsonObject.get("Token").getAsString(), uhs);
        }
        catch (IOException exception) {
            throw new AuthenticationException(String.format("Authentication error. Request could not be made! Cause: '%s'", exception.getMessage()));
        }
    }
    
    public static XboxToken generateXboxTokenPair(final XboxLiveToken xboxLiveToken) {
        try {
            try {
                final URL url = new URL("https://xsts.auth.xboxlive.com/xsts/authorize");
                final URLConnection urlConnection = url.openConnection();
                final HttpURLConnection httpURLConnection = (HttpURLConnection)urlConnection;
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                final JsonObject request = new JsonObject();
                request.add("RelyingParty", new JsonPrimitive("rp://api.minecraftservices.com/"));
                request.add("TokenType", new JsonPrimitive("JWT"));
                final JsonObject properties = new JsonObject();
                properties.add("SandboxId", new JsonPrimitive("RETAIL"));
                final JsonArray userTokens = new JsonArray();
                userTokens.add(new JsonPrimitive(xboxLiveToken.token));
                properties.add("UserTokens", userTokens);
                sendXboxRequest(httpURLConnection, request, properties);
                if (httpURLConnection.getResponseCode() == 401) {
                    throw new AuthenticationException("No xbox account was found!");
                }
                final JsonObject jsonObject = parseResponseData(httpURLConnection);
                final String uhs = jsonObject.get("DisplayClaims").getAsJsonObject().get("xui").getAsJsonArray().get(0).getAsJsonObject().get("uhs").getAsString();
                return new XboxToken(jsonObject.get("Token").getAsString(), uhs);
            }
            catch (IOException exception) {
                throw new AuthenticationException(String.format("Authentication error. Request could not be made! Cause: '%s'", exception.getMessage()));
            }
        }
        catch (Throwable $ex) {
            throw $ex;
        }
    }
    
    private static JsonObject parseResponseData(final HttpURLConnection httpURLConnection) throws IOException, AuthenticationException {
        final BufferedReader bufferedReader = (httpURLConnection.getResponseCode() != 200) ? new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream())) : new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        final String lines = bufferedReader.lines().collect(Collectors.joining());
        final JsonObject jsonObject = new GsonBuilder().create().fromJson(lines, JsonObject.class);
        if (jsonObject.has("error")) {
            throw new AuthenticationException(jsonObject.get("error").toString() + ": " + jsonObject.get("error_description"));
        }
        return jsonObject;
    }
    
    private static String encodeURL(final String url) {
        try {
            return URLEncoder.encode(url, "UTF-8");
        }
        catch (UnsupportedEncodingException exception) {
            throw new UnsupportedOperationException(exception);
        }
    }
    
    private static String encodeURL(final HashMap<String, String> map) {
        final StringBuilder sb = new StringBuilder();
        final StringBuilder sb2;
        map.forEach((key, value) -> {
            if (sb2.length() != 0) {
                sb2.append("&");
            }
            sb2.append(String.format("%s=%s", encodeURL(key), encodeURL(value)));
            return;
        });
        return sb.toString();
    }
    
    static {
        Login.loginUrl = null;
        Login.loginCookie = null;
        Login.loginPPFT = null;
    }
    
    public static class MicrosoftToken
    {
        public final String token;
        public final String refreshToken;
        
        public MicrosoftToken(final String token, final String refreshToken) {
            this.token = token;
            this.refreshToken = refreshToken;
        }
    }
    
    public static class XboxLiveToken
    {
        public final String token;
        public final String uhs;
        
        public XboxLiveToken(final String token, final String uhs) {
            this.token = token;
            this.uhs = uhs;
        }
    }
    
    public static class XboxToken
    {
        public final String token;
        public final String uhs;
        
        public XboxToken(final String token, final String uhs) {
            this.token = token;
            this.uhs = uhs;
        }
    }
    
    public static class MinecraftProfile
    {
        public final UUID uuid;
        public final String username;
        
        public MinecraftProfile(final UUID uuid, final String username) {
            this.uuid = uuid;
            this.username = username;
        }
    }
}
