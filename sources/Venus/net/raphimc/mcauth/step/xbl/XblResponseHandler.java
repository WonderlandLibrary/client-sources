/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.raphimc.mcauth.step.xbl;

import java.io.IOException;
import net.raphimc.mcauth.util.MicrosoftConstants;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class XblResponseHandler
implements ResponseHandler<String> {
    @Override
    public String handleResponse(HttpResponse httpResponse) throws IOException {
        StatusLine statusLine = httpResponse.getStatusLine();
        HttpEntity httpEntity = httpResponse.getEntity();
        if (statusLine.getStatusCode() >= 300) {
            EntityUtils.consumeQuietly(httpEntity);
            if (httpResponse.containsHeader("X-Err")) {
                throw new HttpResponseException(statusLine.getStatusCode(), MicrosoftConstants.XBOX_LIVE_ERRORS.getOrDefault(Long.valueOf(httpResponse.getFirstHeader("X-Err").getValue()), "Error code: " + httpResponse.getFirstHeader("X-Err").getValue()));
            }
            throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
        }
        return httpEntity == null ? null : EntityUtils.toString(httpEntity);
    }

    @Override
    public Object handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
        return this.handleResponse(httpResponse);
    }
}

