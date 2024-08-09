/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client.utils;

import java.io.Closeable;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

public class HttpClientUtils {
    private HttpClientUtils() {
    }

    public static void closeQuietly(HttpResponse httpResponse) {
        HttpEntity httpEntity;
        if (httpResponse != null && (httpEntity = httpResponse.getEntity()) != null) {
            try {
                EntityUtils.consume(httpEntity);
            } catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void closeQuietly(CloseableHttpResponse closeableHttpResponse) {
        if (closeableHttpResponse != null) {
            try {
                try {
                    EntityUtils.consume(closeableHttpResponse.getEntity());
                } finally {
                    closeableHttpResponse.close();
                }
            } catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    public static void closeQuietly(HttpClient httpClient) {
        if (httpClient != null && httpClient instanceof Closeable) {
            try {
                ((Closeable)((Object)httpClient)).close();
            } catch (IOException iOException) {
                // empty catch block
            }
        }
    }
}

