/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.raphimc.mcauth.step.bedrock;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class PlayFabResponseHandler
implements ResponseHandler<String> {
    @Override
    public String handleResponse(HttpResponse httpResponse) throws IOException {
        String string;
        StatusLine statusLine = httpResponse.getStatusLine();
        HttpEntity httpEntity = httpResponse.getEntity();
        String string2 = string = httpEntity == null ? null : EntityUtils.toString(httpEntity);
        if (statusLine.getStatusCode() >= 300) {
            JsonObject jsonObject;
            if (string != null && ContentType.getOrDefault(httpEntity).getMimeType().equals(ContentType.APPLICATION_JSON.getMimeType()) && (jsonObject = (JsonObject)JsonParser.parseString(string)).has("error") && jsonObject.has("errorMessage")) {
                throw new HttpResponseException(statusLine.getStatusCode(), jsonObject.get("error").getAsString() + ": " + jsonObject.get("errorMessage").getAsString());
            }
            throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
        }
        return string;
    }

    @Override
    public Object handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
        return this.handleResponse(httpResponse);
    }
}

