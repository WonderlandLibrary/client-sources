package net.shoreline.client.api.account.microsoft;

import net.shoreline.client.api.account.microsoft.data.MinecraftProfile;
import net.shoreline.client.api.account.microsoft.data.OAuthData;
import net.shoreline.client.api.account.microsoft.data.XboxLoginData;
import net.shoreline.client.util.network.RequestUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.util.Session;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @see <a href="https://mojang-api-docs.gapple.pw/authentication/msa">MSA</a>
 *
 * @author Gavin
 * @since 1.0
 *
 *
 */
public class MicrosoftAuthenticator
{
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:107.0) Gecko/20100101 Firefox/107.0";
    private static final String OAUTH_AUTH_URL =
            "https://login.live.com/oauth20_authorize.srf?client_id=000000004C12AE6F&redirect_uri=https://login.live.com/oauth20_desktop.srf&scope=service::user.auth.xboxlive.com::MBI_SSL&display=touch&response_type=token&locale=en";
    private static final String XBOX_LIVE_AUTH_URL =
            "https://user.auth.xboxlive.com/user/authenticate";
    private static final String XSTS_AUTH_URL =
            "https://xsts.auth.xboxlive.com/xsts/authorize";
    private static final String LOGIN_WITH_XBOX_URL =
            "https://api.minecraftservices.com/authentication/login_with_xbox";
    private static final String PROFILE_URL =
            "https://api.minecraftservices.com/minecraft/profile";
    private static final Pattern SFTT_TAG_PATTERN = Pattern.compile("value=\"(.+?)\"");
    private static final Pattern POST_URL_PATTERN = Pattern.compile("urlPost:'(.+?)'");
    //
    private static final JsonParser PARSER = new JsonParser();

    /**
     * Logs into a Microsoft account with a username and password
     *
     * @param email The account email
     * @param password The account password
     * @return The {@link Session} for the login request or null
     * @throws IOException
     * @throws MicrosoftAuthException
     */
    public Session login(String email, String password)
            throws MicrosoftAuthException, IOException
    {
        MinecraftProfile profile = getProfileData(email, password);
        if (profile != null)
        {
            return new Session(profile.username(), profile.id(),
                    profile.accessToken(), Optional.empty(),
                    Optional.empty(), Session.AccountType.MSA);
        }
        return null;
    }

    /**
     * Gets the profile data for an email and a password
     *
     * @param email the email
     * @param password the password
     * @return the MinecraftProfile for this email and password
     * @throws IOException
     * @throws MicrosoftAuthException
     */
    public MinecraftProfile getProfileData(String email, String password)
            throws IOException, MicrosoftAuthException
    {
        OAuthData oauthData = getOAuthData();
        if (oauthData == null)
        {
            throw new NullPointerException("OAuthData cannot be null");
        }
        Map<String, String> loginData = getLoginData(oauthData, email,
                password);
        if (loginData == null || !loginData.containsKey("access_token"))
        {
            throw new MicrosoftAuthException("Access token cannot be null");
        }
        // i want to kill myself
        XboxLoginData xboxLogin = loginXbox(loginData.get("access_token"));
        if (xboxLogin == null)
        {
            throw new MicrosoftAuthException("Xbox Login data cannot be null");
        }
        XboxLoginData xstsData = getXSTSData(xboxLogin);
        if (xstsData == null)
        {
            throw new MicrosoftAuthException("XSTS data cannot be null");
        }
        String accessToken = xboxAuth(xstsData);
        return getProfile(accessToken, loginData.getOrDefault(
                "refresh_token", null));
    }

    /**
     * Gets the profile on the access token and refresh token
     *
     * @param accessToken the access token (required)
     * @param refreshToken the refresh token (optional)
     * @return the MinecraftProfile object or null if the request fails
     * @throws IOException if reading the result of the request fails
     */
    public MinecraftProfile getProfile(String accessToken, String refreshToken)
            throws IOException
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Authorization", "Bearer " + accessToken);
        String data = RequestUtil.get(PROFILE_URL, headers, false);
        if (data == null)
        {
            return null;
        }
        JsonObject obj = PARSER.parse(data).getAsJsonObject();
        return new MinecraftProfile(obj.get("name").getAsString(),
                obj.get("id").getAsString(), accessToken, refreshToken);
    }

    /**
     * Authenticates into minecraft with xbox
     *
     * @param data the login data
     * @return the access token needed for future requests
     */
    private String xboxAuth(final XboxLoginData data)
    {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        String content = RequestUtil.post(LOGIN_WITH_XBOX_URL, headers,
                false, "{\"identityToken\":\"XBL3.0 x="
                        + data.uhs() + ";" + data.token()
                        + "\",\"ensureLegacyEnabled\":true}");
        return content != null ? PARSER.parse(content).getAsJsonObject().get("access_token").getAsString() : null;
    }

    /**
     * Logs into the xbox account with the provided access token
     *
     * @param accessToken the access token
     * @return the XboxLoginData object with the Token and UHS, or null
     */
    private XboxLoginData loginXbox(final String accessToken)
    {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        String body = "{\"Properties\":{\"AuthMethod\":\"RPS\",\"SiteName\":\"user.auth.xboxlive.com\",\"RpsTicket\":\""
                + accessToken + "\"},\"RelyingParty\":\"http://auth.xboxlive.com\",\"TokenType\":\"JWT\"}";
        String data = RequestUtil.post(XBOX_LIVE_AUTH_URL, headers, false,
                body);
        if (data == null)
        {
            return null;
        }
        JsonObject obj = PARSER.parse(data).getAsJsonObject();
        String token = obj.get("Token").getAsString();
        String uhs = obj.get("DisplayClaims").getAsJsonObject().get("xui")
                .getAsJsonArray().get(0).getAsJsonObject().get("uhs").getAsString(); // wtf
        return new XboxLoginData(token, uhs);
    }

    /**
     * Gets the XSTS data needed
     *
     * @param data the previous XboxLoginData object from the xbox login request
     * @return the resulting updated XboxLoginData or null if request fails
     * @throws IOException if the output or input streams fail to write/read
     * @throws MicrosoftAuthException if the account is locked in some way from authorizing, or no result was found
     */
    private XboxLoginData getXSTSData(final XboxLoginData data)
            throws IOException, MicrosoftAuthException
    {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        HttpURLConnection connection = RequestUtil.post(
                XSTS_AUTH_URL, headers,
                "{\"Properties\":{\"SandboxId\":\"RETAIL\",\"UserTokens\":[\""
                        + data.token()
                        + "\"]},\"RelyingParty\":\"rp://api.minecraftservices.com/\",\"TokenType\":\"JWT\"})");
        if (connection == null)
        {
            return null;
        }
        String content = RequestUtil.readConnection(connection);
        if (content == null)
        {
            throw new MicrosoftAuthException("input stream cannot be null");
        }
        JsonObject jsonObject = PARSER.parse(content).getAsJsonObject();
        if (connection.getResponseCode() == 401)
        {
            long errCode = jsonObject.get("XErr").getAsLong();
            if (errCode == 2148916238L)
            {
                throw new MicrosoftAuthException("Account is owned by user under " +
                        "the age of 18");
            }
            else if (errCode == 2148916233L)
            {
                throw new MicrosoftAuthException("Account does not have an Xbox " +
                        "account created");
            }
            return null;
        }
        return new XboxLoginData(jsonObject.get("Token").getAsString(),
                data.uhs());
    }

    /**
     * Gets the user data from the oauth data
     *
     * @param oauthData The oauth data result
     * @param email The email
     * @param password The password
     * @return The map that contains the access_token, refresh_token,
     * expires_at, etc
     * @throws IOException if the output or input streams fail to write/read
     * @throws MicrosoftAuthException if the authentication with Microsoft fails
     */
    private Map<String, String> getLoginData(OAuthData oauthData, String email,
                                             String password)
            throws IOException, MicrosoftAuthException
    {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Cookie", oauthData.cookie());
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        email = URLEncoder.encode(email);
        password = URLEncoder.encode(password);
        HttpURLConnection connection = RequestUtil.post(oauthData.postUrl(), headers, "login=" + email
                + "&loginfmt=" + email
                + "&passwd=" + password
                + "&PPFT=" + oauthData.sfttTag());
        if (connection == null)
        {
            return null;
        }
        String data = RequestUtil.readConnection(connection);
        if (data == null)
        {
            return null;
        }
        String redirectedUrl = connection.getURL().toString();
        if (redirectedUrl.contains("accessToken")
                && redirectedUrl.equals(oauthData.postUrl()))
        {
            throw new MicrosoftAuthException("Login failed, [url=" + redirectedUrl + "]");
        }
        if (data.contains("Sign in to"))
        {
            throw new MicrosoftAuthException("Invalid credentials");
        }
        if (data.contains("Help us protect your account"))
        {
            throw new MicrosoftAuthException("2FA has been enabled on this account");
        }
        String raw = connection.getURL().toString().split("#")[1];
        Map<String, String> loginData = new LinkedHashMap<>();
        for (String param : raw.split("&"))
        {
            String[] paramSplit = param.split("=");
            loginData.put(paramSplit[0], paramSplit[1]);
        }
        return loginData;
    }

    /**
     * Gets the needed oauth data needed to log in
     *
     * @return The OAuthData object
     * @throws IOException If the stream fails to be read from
     */
    private OAuthData getOAuthData() throws IOException
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent", USER_AGENT);
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8");
        HttpURLConnection connection = RequestUtil.get(OAUTH_AUTH_URL, headers);
        if (connection == null)
        {
            return null;
        }
        String data = RequestUtil.readConnection(connection);
        if (data == null) {
            return null;
        }
        String sfttTag = null, postUrl = null;
        Matcher matcher = SFTT_TAG_PATTERN.matcher(data);
        if (matcher.find())
        {
            sfttTag = matcher.group(1);
        }
        if ((matcher = POST_URL_PATTERN.matcher(data)).find())
        {
            postUrl = matcher.group(1);
        }
        List<String> cookieData = connection.getHeaderFields().get("Set-Cookie");
        String cookie = String.join(";", cookieData);
        return new OAuthData(sfttTag, postUrl, cookie);
    }
}
