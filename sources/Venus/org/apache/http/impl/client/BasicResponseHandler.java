/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.client;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.client.HttpResponseException;
import org.apache.http.impl.client.AbstractResponseHandler;
import org.apache.http.util.EntityUtils;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class BasicResponseHandler
extends AbstractResponseHandler<String> {
    @Override
    public String handleEntity(HttpEntity httpEntity) throws IOException {
        return EntityUtils.toString(httpEntity);
    }

    @Override
    public String handleResponse(HttpResponse httpResponse) throws HttpResponseException, IOException {
        return (String)super.handleResponse(httpResponse);
    }

    @Override
    public Object handleEntity(HttpEntity httpEntity) throws IOException {
        return this.handleEntity(httpEntity);
    }

    @Override
    public Object handleResponse(HttpResponse httpResponse) throws HttpResponseException, IOException {
        return this.handleResponse(httpResponse);
    }
}

