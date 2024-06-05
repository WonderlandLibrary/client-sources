// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.utils.player;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import java.nio.charset.StandardCharsets;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

public final class HypixelUtils
{
    public static String getHypixelBanInformation(final String banId, final String username) {
        final CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        final HttpGet httpGet = new HttpGet("https://hypixel.net/api/players/" + username + "/ban/" + banId);
        httpGet.setHeader("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.94 Safari/537.36");
        httpGet.setHeader("xf-api-key", "LnM-qSeQqtJlJmJnVt76GhU-SoiolWs9");
        String result = null;
        try {
            final CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute((HttpUriRequest)httpGet);
            result = IOUtils.toString(closeableHttpResponse.getEntity().getContent(), StandardCharsets.UTF_8);
        }
        catch (final IOException ioe) {
            ioe.printStackTrace();
        }
        return result;
    }
}
