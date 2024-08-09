/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client.methods;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.Configurable;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.message.HeaderGroup;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.Args;

public class RequestBuilder {
    private String method;
    private Charset charset;
    private ProtocolVersion version;
    private URI uri;
    private HeaderGroup headerGroup;
    private HttpEntity entity;
    private List<NameValuePair> parameters;
    private RequestConfig config;

    RequestBuilder(String string) {
        this.charset = Consts.UTF_8;
        this.method = string;
    }

    RequestBuilder(String string, URI uRI) {
        this.method = string;
        this.uri = uRI;
    }

    RequestBuilder(String string, String string2) {
        this.method = string;
        this.uri = string2 != null ? URI.create(string2) : null;
    }

    RequestBuilder() {
        this(null);
    }

    public static RequestBuilder create(String string) {
        Args.notBlank(string, "HTTP method");
        return new RequestBuilder(string);
    }

    public static RequestBuilder get() {
        return new RequestBuilder("GET");
    }

    public static RequestBuilder get(URI uRI) {
        return new RequestBuilder("GET", uRI);
    }

    public static RequestBuilder get(String string) {
        return new RequestBuilder("GET", string);
    }

    public static RequestBuilder head() {
        return new RequestBuilder("HEAD");
    }

    public static RequestBuilder head(URI uRI) {
        return new RequestBuilder("HEAD", uRI);
    }

    public static RequestBuilder head(String string) {
        return new RequestBuilder("HEAD", string);
    }

    public static RequestBuilder patch() {
        return new RequestBuilder("PATCH");
    }

    public static RequestBuilder patch(URI uRI) {
        return new RequestBuilder("PATCH", uRI);
    }

    public static RequestBuilder patch(String string) {
        return new RequestBuilder("PATCH", string);
    }

    public static RequestBuilder post() {
        return new RequestBuilder("POST");
    }

    public static RequestBuilder post(URI uRI) {
        return new RequestBuilder("POST", uRI);
    }

    public static RequestBuilder post(String string) {
        return new RequestBuilder("POST", string);
    }

    public static RequestBuilder put() {
        return new RequestBuilder("PUT");
    }

    public static RequestBuilder put(URI uRI) {
        return new RequestBuilder("PUT", uRI);
    }

    public static RequestBuilder put(String string) {
        return new RequestBuilder("PUT", string);
    }

    public static RequestBuilder delete() {
        return new RequestBuilder("DELETE");
    }

    public static RequestBuilder delete(URI uRI) {
        return new RequestBuilder("DELETE", uRI);
    }

    public static RequestBuilder delete(String string) {
        return new RequestBuilder("DELETE", string);
    }

    public static RequestBuilder trace() {
        return new RequestBuilder("TRACE");
    }

    public static RequestBuilder trace(URI uRI) {
        return new RequestBuilder("TRACE", uRI);
    }

    public static RequestBuilder trace(String string) {
        return new RequestBuilder("TRACE", string);
    }

    public static RequestBuilder options() {
        return new RequestBuilder("OPTIONS");
    }

    public static RequestBuilder options(URI uRI) {
        return new RequestBuilder("OPTIONS", uRI);
    }

    public static RequestBuilder options(String string) {
        return new RequestBuilder("OPTIONS", string);
    }

    public static RequestBuilder copy(HttpRequest httpRequest) {
        Args.notNull(httpRequest, "HTTP request");
        return new RequestBuilder().doCopy(httpRequest);
    }

    private RequestBuilder doCopy(HttpRequest httpRequest) {
        if (httpRequest == null) {
            return this;
        }
        this.method = httpRequest.getRequestLine().getMethod();
        this.version = httpRequest.getRequestLine().getProtocolVersion();
        if (this.headerGroup == null) {
            this.headerGroup = new HeaderGroup();
        }
        this.headerGroup.clear();
        this.headerGroup.setHeaders(httpRequest.getAllHeaders());
        this.parameters = null;
        this.entity = null;
        if (httpRequest instanceof HttpEntityEnclosingRequest) {
            HttpEntity httpEntity = ((HttpEntityEnclosingRequest)httpRequest).getEntity();
            ContentType contentType = ContentType.get(httpEntity);
            if (contentType != null && contentType.getMimeType().equals(ContentType.APPLICATION_FORM_URLENCODED.getMimeType())) {
                try {
                    this.charset = contentType.getCharset();
                    List<NameValuePair> list = URLEncodedUtils.parse(httpEntity);
                    if (!list.isEmpty()) {
                        this.parameters = list;
                    }
                } catch (IOException iOException) {}
            } else {
                this.entity = httpEntity;
            }
        }
        this.uri = httpRequest instanceof HttpUriRequest ? ((HttpUriRequest)httpRequest).getURI() : URI.create(httpRequest.getRequestLine().getUri());
        this.config = httpRequest instanceof Configurable ? ((Configurable)((Object)httpRequest)).getConfig() : null;
        return this;
    }

    public RequestBuilder setCharset(Charset charset) {
        this.charset = charset;
        return this;
    }

    public Charset getCharset() {
        return this.charset;
    }

    public String getMethod() {
        return this.method;
    }

    public ProtocolVersion getVersion() {
        return this.version;
    }

    public RequestBuilder setVersion(ProtocolVersion protocolVersion) {
        this.version = protocolVersion;
        return this;
    }

    public URI getUri() {
        return this.uri;
    }

    public RequestBuilder setUri(URI uRI) {
        this.uri = uRI;
        return this;
    }

    public RequestBuilder setUri(String string) {
        this.uri = string != null ? URI.create(string) : null;
        return this;
    }

    public Header getFirstHeader(String string) {
        return this.headerGroup != null ? this.headerGroup.getFirstHeader(string) : null;
    }

    public Header getLastHeader(String string) {
        return this.headerGroup != null ? this.headerGroup.getLastHeader(string) : null;
    }

    public Header[] getHeaders(String string) {
        return this.headerGroup != null ? this.headerGroup.getHeaders(string) : null;
    }

    public RequestBuilder addHeader(Header header) {
        if (this.headerGroup == null) {
            this.headerGroup = new HeaderGroup();
        }
        this.headerGroup.addHeader(header);
        return this;
    }

    public RequestBuilder addHeader(String string, String string2) {
        if (this.headerGroup == null) {
            this.headerGroup = new HeaderGroup();
        }
        this.headerGroup.addHeader(new BasicHeader(string, string2));
        return this;
    }

    public RequestBuilder removeHeader(Header header) {
        if (this.headerGroup == null) {
            this.headerGroup = new HeaderGroup();
        }
        this.headerGroup.removeHeader(header);
        return this;
    }

    public RequestBuilder removeHeaders(String string) {
        if (string == null || this.headerGroup == null) {
            return this;
        }
        HeaderIterator headerIterator = this.headerGroup.iterator();
        while (headerIterator.hasNext()) {
            Header header = headerIterator.nextHeader();
            if (!string.equalsIgnoreCase(header.getName())) continue;
            headerIterator.remove();
        }
        return this;
    }

    public RequestBuilder setHeader(Header header) {
        if (this.headerGroup == null) {
            this.headerGroup = new HeaderGroup();
        }
        this.headerGroup.updateHeader(header);
        return this;
    }

    public RequestBuilder setHeader(String string, String string2) {
        if (this.headerGroup == null) {
            this.headerGroup = new HeaderGroup();
        }
        this.headerGroup.updateHeader(new BasicHeader(string, string2));
        return this;
    }

    public HttpEntity getEntity() {
        return this.entity;
    }

    public RequestBuilder setEntity(HttpEntity httpEntity) {
        this.entity = httpEntity;
        return this;
    }

    public List<NameValuePair> getParameters() {
        return this.parameters != null ? new ArrayList<NameValuePair>(this.parameters) : new ArrayList();
    }

    public RequestBuilder addParameter(NameValuePair nameValuePair) {
        Args.notNull(nameValuePair, "Name value pair");
        if (this.parameters == null) {
            this.parameters = new LinkedList<NameValuePair>();
        }
        this.parameters.add(nameValuePair);
        return this;
    }

    public RequestBuilder addParameter(String string, String string2) {
        return this.addParameter(new BasicNameValuePair(string, string2));
    }

    public RequestBuilder addParameters(NameValuePair ... nameValuePairArray) {
        for (NameValuePair nameValuePair : nameValuePairArray) {
            this.addParameter(nameValuePair);
        }
        return this;
    }

    public RequestConfig getConfig() {
        return this.config;
    }

    public RequestBuilder setConfig(RequestConfig requestConfig) {
        this.config = requestConfig;
        return this;
    }

    public HttpUriRequest build() {
        HttpRequestBase httpRequestBase;
        URI uRI = this.uri != null ? this.uri : URI.create("/");
        HttpEntity httpEntity = this.entity;
        if (this.parameters != null && !this.parameters.isEmpty()) {
            if (httpEntity == null && ("POST".equalsIgnoreCase(this.method) || "PUT".equalsIgnoreCase(this.method))) {
                httpEntity = new UrlEncodedFormEntity(this.parameters, this.charset != null ? this.charset : HTTP.DEF_CONTENT_CHARSET);
            } else {
                try {
                    uRI = new URIBuilder(uRI).setCharset(this.charset).addParameters(this.parameters).build();
                } catch (URISyntaxException uRISyntaxException) {
                    // empty catch block
                }
            }
        }
        if (httpEntity == null) {
            httpRequestBase = new InternalRequest(this.method);
        } else {
            InternalEntityEclosingRequest internalEntityEclosingRequest = new InternalEntityEclosingRequest(this.method);
            internalEntityEclosingRequest.setEntity(httpEntity);
            httpRequestBase = internalEntityEclosingRequest;
        }
        httpRequestBase.setProtocolVersion(this.version);
        httpRequestBase.setURI(uRI);
        if (this.headerGroup != null) {
            httpRequestBase.setHeaders(this.headerGroup.getAllHeaders());
        }
        httpRequestBase.setConfig(this.config);
        return httpRequestBase;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("RequestBuilder [method=");
        stringBuilder.append(this.method);
        stringBuilder.append(", charset=");
        stringBuilder.append(this.charset);
        stringBuilder.append(", version=");
        stringBuilder.append(this.version);
        stringBuilder.append(", uri=");
        stringBuilder.append(this.uri);
        stringBuilder.append(", headerGroup=");
        stringBuilder.append(this.headerGroup);
        stringBuilder.append(", entity=");
        stringBuilder.append(this.entity);
        stringBuilder.append(", parameters=");
        stringBuilder.append(this.parameters);
        stringBuilder.append(", config=");
        stringBuilder.append(this.config);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    static class InternalEntityEclosingRequest
    extends HttpEntityEnclosingRequestBase {
        private final String method;

        InternalEntityEclosingRequest(String string) {
            this.method = string;
        }

        @Override
        public String getMethod() {
            return this.method;
        }
    }

    static class InternalRequest
    extends HttpRequestBase {
        private final String method;

        InternalRequest(String string) {
            this.method = string;
        }

        @Override
        public String getMethod() {
            return this.method;
        }
    }
}

