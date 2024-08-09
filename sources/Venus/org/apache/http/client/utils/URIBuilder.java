/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.util.InetAddressUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.TextUtils;

public class URIBuilder {
    private String scheme;
    private String encodedSchemeSpecificPart;
    private String encodedAuthority;
    private String userInfo;
    private String encodedUserInfo;
    private String host;
    private int port;
    private String encodedPath;
    private List<String> pathSegments;
    private String encodedQuery;
    private List<NameValuePair> queryParams;
    private String query;
    private Charset charset;
    private String fragment;
    private String encodedFragment;

    public URIBuilder() {
        this.port = -1;
    }

    public URIBuilder(String string) throws URISyntaxException {
        this(new URI(string), null);
    }

    public URIBuilder(URI uRI) {
        this(uRI, null);
    }

    public URIBuilder(String string, Charset charset) throws URISyntaxException {
        this(new URI(string), charset);
    }

    public URIBuilder(URI uRI, Charset charset) {
        this.setCharset(charset);
        this.digestURI(uRI);
    }

    public URIBuilder setCharset(Charset charset) {
        this.charset = charset;
        return this;
    }

    public Charset getCharset() {
        return this.charset;
    }

    private List<NameValuePair> parseQuery(String string, Charset charset) {
        if (string != null && !string.isEmpty()) {
            return URLEncodedUtils.parse(string, charset);
        }
        return null;
    }

    private List<String> parsePath(String string, Charset charset) {
        if (string != null && !string.isEmpty()) {
            return URLEncodedUtils.parsePathSegments(string, charset);
        }
        return null;
    }

    public URI build() throws URISyntaxException {
        return new URI(this.buildString());
    }

    private String buildString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.scheme != null) {
            stringBuilder.append(this.scheme).append(':');
        }
        if (this.encodedSchemeSpecificPart != null) {
            stringBuilder.append(this.encodedSchemeSpecificPart);
        } else {
            if (this.encodedAuthority != null) {
                stringBuilder.append("//").append(this.encodedAuthority);
            } else if (this.host != null) {
                stringBuilder.append("//");
                if (this.encodedUserInfo != null) {
                    stringBuilder.append(this.encodedUserInfo).append("@");
                } else if (this.userInfo != null) {
                    stringBuilder.append(this.encodeUserInfo(this.userInfo)).append("@");
                }
                if (InetAddressUtils.isIPv6Address(this.host)) {
                    stringBuilder.append("[").append(this.host).append("]");
                } else {
                    stringBuilder.append(this.host);
                }
                if (this.port >= 0) {
                    stringBuilder.append(":").append(this.port);
                }
            }
            if (this.encodedPath != null) {
                stringBuilder.append(URIBuilder.normalizePath(this.encodedPath, stringBuilder.length() == 0));
            } else if (this.pathSegments != null) {
                stringBuilder.append(this.encodePath(this.pathSegments));
            }
            if (this.encodedQuery != null) {
                stringBuilder.append("?").append(this.encodedQuery);
            } else if (this.queryParams != null && !this.queryParams.isEmpty()) {
                stringBuilder.append("?").append(this.encodeUrlForm(this.queryParams));
            } else if (this.query != null) {
                stringBuilder.append("?").append(this.encodeUric(this.query));
            }
        }
        if (this.encodedFragment != null) {
            stringBuilder.append("#").append(this.encodedFragment);
        } else if (this.fragment != null) {
            stringBuilder.append("#").append(this.encodeUric(this.fragment));
        }
        return stringBuilder.toString();
    }

    private static String normalizePath(String string, boolean bl) {
        String string2 = string;
        if (TextUtils.isBlank(string2)) {
            return "";
        }
        if (!bl && !string2.startsWith("/")) {
            string2 = "/" + string2;
        }
        return string2;
    }

    private void digestURI(URI uRI) {
        this.scheme = uRI.getScheme();
        this.encodedSchemeSpecificPart = uRI.getRawSchemeSpecificPart();
        this.encodedAuthority = uRI.getRawAuthority();
        this.host = uRI.getHost();
        this.port = uRI.getPort();
        this.encodedUserInfo = uRI.getRawUserInfo();
        this.userInfo = uRI.getUserInfo();
        this.encodedPath = uRI.getRawPath();
        this.pathSegments = this.parsePath(uRI.getRawPath(), this.charset != null ? this.charset : Consts.UTF_8);
        this.encodedQuery = uRI.getRawQuery();
        this.queryParams = this.parseQuery(uRI.getRawQuery(), this.charset != null ? this.charset : Consts.UTF_8);
        this.encodedFragment = uRI.getRawFragment();
        this.fragment = uRI.getFragment();
    }

    private String encodeUserInfo(String string) {
        return URLEncodedUtils.encUserInfo(string, this.charset != null ? this.charset : Consts.UTF_8);
    }

    private String encodePath(List<String> list) {
        return URLEncodedUtils.formatSegments(list, this.charset != null ? this.charset : Consts.UTF_8);
    }

    private String encodeUrlForm(List<NameValuePair> list) {
        return URLEncodedUtils.format(list, this.charset != null ? this.charset : Consts.UTF_8);
    }

    private String encodeUric(String string) {
        return URLEncodedUtils.encUric(string, this.charset != null ? this.charset : Consts.UTF_8);
    }

    public URIBuilder setScheme(String string) {
        this.scheme = string;
        return this;
    }

    public URIBuilder setUserInfo(String string) {
        this.userInfo = string;
        this.encodedSchemeSpecificPart = null;
        this.encodedAuthority = null;
        this.encodedUserInfo = null;
        return this;
    }

    public URIBuilder setUserInfo(String string, String string2) {
        return this.setUserInfo(string + ':' + string2);
    }

    public URIBuilder setHost(String string) {
        this.host = string;
        this.encodedSchemeSpecificPart = null;
        this.encodedAuthority = null;
        return this;
    }

    public URIBuilder setPort(int n) {
        this.port = n < 0 ? -1 : n;
        this.encodedSchemeSpecificPart = null;
        this.encodedAuthority = null;
        return this;
    }

    public URIBuilder setPath(String string) {
        return this.setPathSegments(string != null ? URLEncodedUtils.splitPathSegments(string) : null);
    }

    public URIBuilder setPathSegments(String ... stringArray) {
        this.pathSegments = stringArray.length > 0 ? Arrays.asList(stringArray) : null;
        this.encodedSchemeSpecificPart = null;
        this.encodedPath = null;
        return this;
    }

    public URIBuilder setPathSegments(List<String> list) {
        this.pathSegments = list != null && list.size() > 0 ? new ArrayList<String>(list) : null;
        this.encodedSchemeSpecificPart = null;
        this.encodedPath = null;
        return this;
    }

    public URIBuilder removeQuery() {
        this.queryParams = null;
        this.query = null;
        this.encodedQuery = null;
        this.encodedSchemeSpecificPart = null;
        return this;
    }

    @Deprecated
    public URIBuilder setQuery(String string) {
        this.queryParams = this.parseQuery(string, this.charset != null ? this.charset : Consts.UTF_8);
        this.query = null;
        this.encodedQuery = null;
        this.encodedSchemeSpecificPart = null;
        return this;
    }

    public URIBuilder setParameters(List<NameValuePair> list) {
        if (this.queryParams == null) {
            this.queryParams = new ArrayList<NameValuePair>();
        } else {
            this.queryParams.clear();
        }
        this.queryParams.addAll(list);
        this.encodedQuery = null;
        this.encodedSchemeSpecificPart = null;
        this.query = null;
        return this;
    }

    public URIBuilder addParameters(List<NameValuePair> list) {
        if (this.queryParams == null) {
            this.queryParams = new ArrayList<NameValuePair>();
        }
        this.queryParams.addAll(list);
        this.encodedQuery = null;
        this.encodedSchemeSpecificPart = null;
        this.query = null;
        return this;
    }

    public URIBuilder setParameters(NameValuePair ... nameValuePairArray) {
        if (this.queryParams == null) {
            this.queryParams = new ArrayList<NameValuePair>();
        } else {
            this.queryParams.clear();
        }
        Collections.addAll(this.queryParams, nameValuePairArray);
        this.encodedQuery = null;
        this.encodedSchemeSpecificPart = null;
        this.query = null;
        return this;
    }

    public URIBuilder addParameter(String string, String string2) {
        if (this.queryParams == null) {
            this.queryParams = new ArrayList<NameValuePair>();
        }
        this.queryParams.add(new BasicNameValuePair(string, string2));
        this.encodedQuery = null;
        this.encodedSchemeSpecificPart = null;
        this.query = null;
        return this;
    }

    public URIBuilder setParameter(String string, String string2) {
        if (this.queryParams == null) {
            this.queryParams = new ArrayList<NameValuePair>();
        }
        if (!this.queryParams.isEmpty()) {
            Iterator<NameValuePair> iterator2 = this.queryParams.iterator();
            while (iterator2.hasNext()) {
                NameValuePair nameValuePair = iterator2.next();
                if (!nameValuePair.getName().equals(string)) continue;
                iterator2.remove();
            }
        }
        this.queryParams.add(new BasicNameValuePair(string, string2));
        this.encodedQuery = null;
        this.encodedSchemeSpecificPart = null;
        this.query = null;
        return this;
    }

    public URIBuilder clearParameters() {
        this.queryParams = null;
        this.encodedQuery = null;
        this.encodedSchemeSpecificPart = null;
        return this;
    }

    public URIBuilder setCustomQuery(String string) {
        this.query = string;
        this.encodedQuery = null;
        this.encodedSchemeSpecificPart = null;
        this.queryParams = null;
        return this;
    }

    public URIBuilder setFragment(String string) {
        this.fragment = string;
        this.encodedFragment = null;
        return this;
    }

    public boolean isAbsolute() {
        return this.scheme != null;
    }

    public boolean isOpaque() {
        return this.pathSegments == null && this.encodedPath == null;
    }

    public String getScheme() {
        return this.scheme;
    }

    public String getUserInfo() {
        return this.userInfo;
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public boolean isPathEmpty() {
        return !(this.pathSegments != null && !this.pathSegments.isEmpty() || this.encodedPath != null && !this.encodedPath.isEmpty());
    }

    public List<String> getPathSegments() {
        return this.pathSegments != null ? new ArrayList<String>(this.pathSegments) : new ArrayList();
    }

    public String getPath() {
        if (this.pathSegments == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : this.pathSegments) {
            stringBuilder.append('/').append(string);
        }
        return stringBuilder.toString();
    }

    public boolean isQueryEmpty() {
        return (this.queryParams == null || this.queryParams.isEmpty()) && this.encodedQuery == null;
    }

    public List<NameValuePair> getQueryParams() {
        return this.queryParams != null ? new ArrayList<NameValuePair>(this.queryParams) : new ArrayList();
    }

    public String getFragment() {
        return this.fragment;
    }

    public String toString() {
        return this.buildString();
    }
}

