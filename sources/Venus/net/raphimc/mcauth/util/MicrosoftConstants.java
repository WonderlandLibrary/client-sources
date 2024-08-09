/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.raphimc.mcauth.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;

public class MicrosoftConstants {
    public static final String JAVA_TITLE_ID = "00000000402b5328";
    public static final String BEDROCK_NINTENDO_TITLE_ID = "00000000441cc96b";
    public static final String BEDROCK_ANDROID_TITLE_ID = "0000000048183522";
    public static final String BEDROCK_IOS_TITLE_ID = "000000004c17c01a";
    public static final String BEDROCK_PLAY_FAB_TITLE_ID = "20CA2";
    public static final String SCOPE1 = "XboxLive.signin XboxLive.offline_access";
    public static final String SCOPE2 = "XboxLive.signin offline_access";
    public static final String SCOPE3 = "offline_access XboxLive.signin XboxLive.offline_access";
    public static final String SCOPE_TITLE_AUTH = "service::user.auth.xboxlive.com::MBI_SSL";
    public static final String LIVE_OAUTH_DESKTOP_URL = "https://login.live.com/oauth20_desktop.srf";
    public static final String XBL_XSTS_RELYING_PARTY = "http://xboxlive.com";
    public static final String JAVA_XSTS_RELYING_PARTY = "rp://api.minecraftservices.com/";
    public static final String BEDROCK_XSTS_RELYING_PARTY = "https://multiplayer.minecraft.net/";
    public static final String BEDROCK_PLAY_FAB_XSTS_RELYING_PARTY = "https://b980a380.minecraft.playfabapi.com/";
    public static final Map<Long, String> XBOX_LIVE_ERRORS = new HashMap<Long, String>();

    public static CloseableHttpClient createHttpClient() {
        int n = 5;
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000).setSocketTimeout(5000).build();
        List<Header> list = MicrosoftConstants.getDefaultHeaders();
        list.add(new BasicHeader("User-Agent", "MinecraftAuth/2.1"));
        return HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).setDefaultHeaders(list).build();
    }

    public static List<Header> getDefaultHeaders() {
        ArrayList<Header> arrayList = new ArrayList<Header>();
        arrayList.add(new BasicHeader("Accept", ContentType.APPLICATION_JSON.getMimeType()));
        arrayList.add(new BasicHeader("Accept-Language", "en-US,en"));
        return arrayList;
    }

    static {
        XBOX_LIVE_ERRORS.put(2148916227L, "Your account was banned by Xbox for violating one or more Community Standards for Xbox and is unable to be used.");
        XBOX_LIVE_ERRORS.put(2148916229L, "Your account is currently restricted and your guardian has not given you permission to play online. Login to https://account.microsoft.com/family/ and have your guardian change your permissions.");
        XBOX_LIVE_ERRORS.put(2148916233L, "Your account currently does not have an Xbox profile. Please create one at https://signup.live.com/signup");
        XBOX_LIVE_ERRORS.put(2148916234L, "Your account has not accepted Xbox's Terms of Service. Please login and accept them.");
        XBOX_LIVE_ERRORS.put(2148916235L, "Your account resides in a region that Xbox has not authorized use from. Xbox has blocked your attempt at logging in.");
        XBOX_LIVE_ERRORS.put(2148916236L, "Your account requires proof of age. Please login to https://login.live.com/login.srf and provide proof of age.");
        XBOX_LIVE_ERRORS.put(2148916237L, "Your account has reached the its limit for playtime. Your account has been blocked from logging in.");
        XBOX_LIVE_ERRORS.put(2148916238L, "The account date of birth is under 18 years and cannot proceed unless the account is added to a family by an adult.");
    }
}

