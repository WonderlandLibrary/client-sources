package com.shroomclient.shroomclientnextgen.auth;

import com.google.gson.*;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class MSAuth {

    private static final String clientId =
        "54fd49e4-2103-4044-9603-2b028c814ec3";
    private static final String scope =
        "XboxLive.signin XboxLive.offline_access";

    private static AccessRefreshToken authCode(String code) throws Exception {
        HttpPost req = new HttpPost("https://login.live.com/oauth20_token.srf");
        req.addHeader("Content-Type", "application/x-www-form-urlencoded");

        ArrayList<NameValuePair> payload = new ArrayList<>();
        payload.add(new BasicNameValuePair("client_id", clientId));
        payload.add(new BasicNameValuePair("code", code));
        payload.add(new BasicNameValuePair("grant_type", "authorization_code"));
        payload.add(
            new BasicNameValuePair("redirect_uri", "http://localhost:59125/")
        );
        payload.add(new BasicNameValuePair("scope", scope));
        req.setEntity(new UrlEncodedFormEntity(payload));

        JsonElement res = new Gson()
            .fromJson(
                EntityUtils.toString(
                    HttpClients.createDefault().execute(req).getEntity()
                ),
                JsonElement.class
            );
        System.out.println(res);
        String accessToken = res
            .getAsJsonObject()
            .get("access_token")
            .getAsString();
        String refreshToken = res
            .getAsJsonObject()
            .get("refresh_token")
            .getAsString();

        return new AccessRefreshToken(accessToken, refreshToken);
    }

    // refreshToken from authCode() or refreshToken()
    private static AccessRefreshToken refreshToken(String refreshToken)
        throws Exception {
        HttpPost req = new HttpPost("https://login.live.com/oauth20_token.srf");
        req.addHeader("Content-Type", "application/x-www-form-urlencoded");

        ArrayList<NameValuePair> payload = new ArrayList<>();
        payload.add(new BasicNameValuePair("client_id", clientId));
        payload.add(new BasicNameValuePair("refresh_token", refreshToken));
        payload.add(new BasicNameValuePair("grant_type", "refresh_token"));
        payload.add(
            new BasicNameValuePair("redirect_uri", "http://localhost:59125/")
        );
        req.setEntity(new UrlEncodedFormEntity(payload));

        JsonElement res = new Gson()
            .fromJson(
                EntityUtils.toString(
                    HttpClients.createDefault().execute(req).getEntity()
                ),
                JsonElement.class
            );
        String accessToken = res
            .getAsJsonObject()
            .get("access_token")
            .getAsString();
        String newRefreshToken = res
            .getAsJsonObject()
            .get("refresh_token")
            .getAsString();

        return new AccessRefreshToken(accessToken, newRefreshToken);
    }

    // authToken is accessToken from authFlow() or refreshToken()
    private static String authXBL(String authToken) throws Exception {
        HttpPost req = new HttpPost(
            "https://user.auth.xboxlive.com/user/authenticate"
        );
        req.addHeader("Content-Type", "application/json");
        req.addHeader("Accept", "application/json");

        JsonObject payload = new JsonObject();
        JsonObject payloadProps = new JsonObject();
        payloadProps.addProperty("AuthMethod", "RPS");
        payloadProps.addProperty("SiteName", "user.auth.xboxlive.com");
        payloadProps.addProperty("RpsTicket", "d=" + authToken);
        payload.add("Properties", payloadProps);
        payload.addProperty("RelyingParty", "http://auth.xboxlive.com");
        payload.addProperty("TokenType", "JWT");

        BasicHttpEntity ent = new BasicHttpEntity();
        ent.setContent(new ByteArrayInputStream(payload.toString().getBytes()));
        req.setEntity(ent);

        JsonElement res = new Gson()
            .fromJson(
                EntityUtils.toString(
                    HttpClients.createDefault().execute(req).getEntity()
                ),
                JsonElement.class
            );
        String token = res.getAsJsonObject().get("Token").getAsString();

        return token;
    }

    // XBLToken from authXBL()
    private static XBLTokenUhs authXSTS(String XBLToken) throws Exception {
        HttpPost req = new HttpPost(
            "https://xsts.auth.xboxlive.com/xsts/authorize"
        );
        req.addHeader("Content-Type", "application/json");
        req.addHeader("Accept", "application/json");

        JsonObject payload = new JsonObject();
        JsonObject payloadProps = new JsonObject();
        JsonArray userTokens = new JsonArray();
        userTokens.add(new JsonPrimitive(XBLToken));
        payloadProps.add("UserTokens", userTokens);
        payloadProps.addProperty("SandboxId", "RETAIL");
        payload.add("Properties", payloadProps);
        payload.addProperty("RelyingParty", "rp://api.minecraftservices.com/");
        payload.addProperty("TokenType", "JWT");

        BasicHttpEntity ent = new BasicHttpEntity();
        ent.setContent(new ByteArrayInputStream(payload.toString().getBytes()));
        req.setEntity(ent);

        JsonElement res = new Gson()
            .fromJson(
                EntityUtils.toString(
                    HttpClients.createDefault().execute(req).getEntity()
                ),
                JsonElement.class
            );
        String token = res.getAsJsonObject().get("Token").getAsString();
        String uhs = res
            .getAsJsonObject()
            .get("DisplayClaims")
            .getAsJsonObject()
            .get("xui")
            .getAsJsonArray()
            .get(0)
            .getAsJsonObject()
            .get("uhs")
            .getAsString();

        return new XBLTokenUhs(token, uhs);
    }

    // uhs from authXSTS()
    private static String authMinecraft(String XSTSToken, String uhs)
        throws Exception {
        HttpPost req = new HttpPost(
            "https://api.minecraftservices.com/authentication/login_with_xbox"
        );
        req.addHeader("Content-Type", "application/json");
        req.addHeader("Accept", "application/json");

        JsonObject payload = new JsonObject();
        payload.addProperty(
            "identityToken",
            "XBL3.0 x=" + uhs + ";" + XSTSToken
        );

        BasicHttpEntity ent = new BasicHttpEntity();
        ent.setContent(new ByteArrayInputStream(payload.toString().getBytes()));
        req.setEntity(ent);

        JsonElement res = new Gson()
            .fromJson(
                EntityUtils.toString(
                    HttpClients.createDefault().execute(req).getEntity()
                ),
                JsonElement.class
            );
        String accessToken = res
            .getAsJsonObject()
            .get("access_token")
            .getAsString();

        return accessToken;
    }

    // accessToken from authMinecraft()
    public static UUIDUsername getProfile(String accessToken) throws Exception {
        HttpGet req = new HttpGet(
            "https://api.minecraftservices.com/minecraft/profile"
        );
        req.addHeader("Authorization", "Bearer " + accessToken);

        JsonElement res = new Gson()
            .fromJson(
                EntityUtils.toString(
                    HttpClients.createDefault().execute(req).getEntity()
                ),
                JsonElement.class
            );
        String uuid = res.getAsJsonObject().get("id").getAsString();
        String username = res.getAsJsonObject().get("name").getAsString();

        return new UUIDUsername(uuid, username);
    }

    private static MSASession authWithAccessRefreshToken(
        AccessRefreshToken accessRefreshToken
    ) throws Exception {
        String XBLToken = authXBL(accessRefreshToken.accessToken);
        XBLTokenUhs xblTokenUhs = authXSTS(XBLToken);
        String accessToken = authMinecraft(
            xblTokenUhs.XBLToken,
            xblTokenUhs.uhs
        );
        UUIDUsername uuidUsername = getProfile(accessToken);

        return new MSASession(
            uuidUsername.uuid,
            uuidUsername.username,
            accessRefreshToken.refreshToken,
            accessToken
        );
    }

    public static MSASession authWithCode(String code) throws Exception {
        return authWithAccessRefreshToken(authCode(code));
    }

    public static MSASession authWithRefreshToken(String refreshToken)
        throws Exception {
        return authWithAccessRefreshToken(refreshToken(refreshToken));
    }

    private static class AccessRefreshToken {

        public final String accessToken;
        public final String refreshToken;

        public AccessRefreshToken(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }

    private static class XBLTokenUhs {

        public final String XBLToken;
        public final String uhs;

        public XBLTokenUhs(String XBLToken, String uhs) {
            this.XBLToken = XBLToken;
            this.uhs = uhs;
        }
    }

    public static class UUIDUsername {

        public final String uuid;
        public final String username;

        public UUIDUsername(String uuid, String username) {
            this.uuid = uuid;
            this.username = username;
        }
    }

    public static class MSASession {

        public final String uuid;
        public final String username;
        public final String refreshToken;
        public final String accessToken;

        public MSASession(
            String uuid,
            String username,
            String refreshToken,
            String accessToken
        ) {
            this.uuid = uuid;
            this.username = username;
            this.refreshToken = refreshToken;
            this.accessToken = accessToken;
        }
    }
}
