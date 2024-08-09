/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.raphimc.mcauth.step.msa;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class MsaCredentialsResponseHandler
implements ResponseHandler<String> {
    @Override
    public String handleResponse(HttpResponse httpResponse) throws IOException {
        StatusLine statusLine = httpResponse.getStatusLine();
        HttpEntity httpEntity = httpResponse.getEntity();
        if (statusLine.getStatusCode() != 302) {
            String string;
            String string2 = string = httpEntity == null ? null : EntityUtils.toString(httpEntity);
            if (string != null && ContentType.getOrDefault(httpEntity).getMimeType().equals(ContentType.TEXT_HTML.getMimeType())) {
                throw new IllegalStateException("Credentials login failed");
            }
            throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
        }
        EntityUtils.consumeQuietly(httpEntity);
        try {
            URI uRI = new URI(httpResponse.getFirstHeader("Location").getValue());
            return URLEncodedUtils.parse(uRI, StandardCharsets.UTF_8).stream().filter(MsaCredentialsResponseHandler::lambda$handleResponse$0).map(NameValuePair::getValue).findFirst().orElseThrow(MsaCredentialsResponseHandler::lambda$handleResponse$1);
        } catch (URISyntaxException uRISyntaxException) {
            throw new RuntimeException(uRISyntaxException);
        }
    }

    @Override
    public Object handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
        return this.handleResponse(httpResponse);
    }

    private static IllegalStateException lambda$handleResponse$1() {
        return new IllegalStateException("Could not extract code from redirect url");
    }

    private static boolean lambda$handleResponse$0(NameValuePair nameValuePair) {
        return nameValuePair.getName().equals("code");
    }
}

