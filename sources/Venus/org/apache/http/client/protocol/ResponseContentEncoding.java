/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client.protocol;

import java.io.IOException;
import java.util.Locale;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.DecompressingEntity;
import org.apache.http.client.entity.DeflateInputStreamFactory;
import org.apache.http.client.entity.GZIPInputStreamFactory;
import org.apache.http.client.entity.InputStreamFactory;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Lookup;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.protocol.HttpContext;

@Contract(threading=ThreadingBehavior.IMMUTABLE_CONDITIONAL)
public class ResponseContentEncoding
implements HttpResponseInterceptor {
    public static final String UNCOMPRESSED = "http.client.response.uncompressed";
    private final Lookup<InputStreamFactory> decoderRegistry;
    private final boolean ignoreUnknown;

    public ResponseContentEncoding(Lookup<InputStreamFactory> lookup, boolean bl) {
        this.decoderRegistry = lookup != null ? lookup : RegistryBuilder.create().register("gzip", GZIPInputStreamFactory.getInstance()).register("x-gzip", GZIPInputStreamFactory.getInstance()).register("deflate", (GZIPInputStreamFactory)((Object)DeflateInputStreamFactory.getInstance())).build();
        this.ignoreUnknown = bl;
    }

    public ResponseContentEncoding(boolean bl) {
        this(null, bl);
    }

    public ResponseContentEncoding(Lookup<InputStreamFactory> lookup) {
        this(lookup, true);
    }

    public ResponseContentEncoding() {
        this(null);
    }

    @Override
    public void process(HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
        Header header;
        HttpEntity httpEntity = httpResponse.getEntity();
        HttpClientContext httpClientContext = HttpClientContext.adapt(httpContext);
        RequestConfig requestConfig = httpClientContext.getRequestConfig();
        if (requestConfig.isContentCompressionEnabled() && httpEntity != null && httpEntity.getContentLength() != 0L && (header = httpEntity.getContentEncoding()) != null) {
            HeaderElement[] headerElementArray;
            for (HeaderElement headerElement : headerElementArray = header.getElements()) {
                String string = headerElement.getName().toLowerCase(Locale.ROOT);
                InputStreamFactory inputStreamFactory = this.decoderRegistry.lookup(string);
                if (inputStreamFactory != null) {
                    httpResponse.setEntity(new DecompressingEntity(httpResponse.getEntity(), inputStreamFactory));
                    httpResponse.removeHeaders("Content-Length");
                    httpResponse.removeHeaders("Content-Encoding");
                    httpResponse.removeHeaders("Content-MD5");
                    continue;
                }
                if ("identity".equals(string) || this.ignoreUnknown) continue;
                throw new HttpException("Unsupported Content-Encoding: " + headerElement.getName());
            }
        }
    }
}

