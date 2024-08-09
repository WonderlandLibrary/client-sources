/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Stack;
import org.apache.http.HttpHost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.routing.RouteInfo;
import org.apache.http.util.Args;
import org.apache.http.util.TextUtils;

public class URIUtils {
    public static final EnumSet<UriFlag> NO_FLAGS = EnumSet.noneOf(UriFlag.class);
    public static final EnumSet<UriFlag> DROP_FRAGMENT = EnumSet.of(UriFlag.DROP_FRAGMENT);
    public static final EnumSet<UriFlag> NORMALIZE = EnumSet.of(UriFlag.NORMALIZE);
    public static final EnumSet<UriFlag> DROP_FRAGMENT_AND_NORMALIZE = EnumSet.of(UriFlag.DROP_FRAGMENT, UriFlag.NORMALIZE);

    @Deprecated
    public static URI createURI(String string, String string2, int n, String string3, String string4, String string5) throws URISyntaxException {
        StringBuilder stringBuilder = new StringBuilder();
        if (string2 != null) {
            if (string != null) {
                stringBuilder.append(string);
                stringBuilder.append("://");
            }
            stringBuilder.append(string2);
            if (n > 0) {
                stringBuilder.append(':');
                stringBuilder.append(n);
            }
        }
        if (string3 == null || !string3.startsWith("/")) {
            stringBuilder.append('/');
        }
        if (string3 != null) {
            stringBuilder.append(string3);
        }
        if (string4 != null) {
            stringBuilder.append('?');
            stringBuilder.append(string4);
        }
        if (string5 != null) {
            stringBuilder.append('#');
            stringBuilder.append(string5);
        }
        return new URI(stringBuilder.toString());
    }

    @Deprecated
    public static URI rewriteURI(URI uRI, HttpHost httpHost, boolean bl) throws URISyntaxException {
        return URIUtils.rewriteURI(uRI, httpHost, bl ? DROP_FRAGMENT : NO_FLAGS);
    }

    public static URI rewriteURI(URI uRI, HttpHost httpHost, EnumSet<UriFlag> enumSet) throws URISyntaxException {
        Args.notNull(uRI, "URI");
        Args.notNull(enumSet, "URI flags");
        if (uRI.isOpaque()) {
            return uRI;
        }
        URIBuilder uRIBuilder = new URIBuilder(uRI);
        if (httpHost != null) {
            uRIBuilder.setScheme(httpHost.getSchemeName());
            uRIBuilder.setHost(httpHost.getHostName());
            uRIBuilder.setPort(httpHost.getPort());
        } else {
            uRIBuilder.setScheme(null);
            uRIBuilder.setHost(null);
            uRIBuilder.setPort(-1);
        }
        if (enumSet.contains((Object)UriFlag.DROP_FRAGMENT)) {
            uRIBuilder.setFragment(null);
        }
        if (enumSet.contains((Object)UriFlag.NORMALIZE)) {
            List<String> list = uRIBuilder.getPathSegments();
            ArrayList<String> arrayList = new ArrayList<String>(list);
            Iterator iterator2 = arrayList.iterator();
            while (iterator2.hasNext()) {
                String string = (String)iterator2.next();
                if (!string.isEmpty() || !iterator2.hasNext()) continue;
                iterator2.remove();
            }
            if (arrayList.size() != list.size()) {
                uRIBuilder.setPathSegments(arrayList);
            }
        }
        if (uRIBuilder.isPathEmpty()) {
            uRIBuilder.setPathSegments("");
        }
        return uRIBuilder.build();
    }

    public static URI rewriteURI(URI uRI, HttpHost httpHost) throws URISyntaxException {
        return URIUtils.rewriteURI(uRI, httpHost, NORMALIZE);
    }

    public static URI rewriteURI(URI uRI) throws URISyntaxException {
        Args.notNull(uRI, "URI");
        if (uRI.isOpaque()) {
            return uRI;
        }
        URIBuilder uRIBuilder = new URIBuilder(uRI);
        if (uRIBuilder.getUserInfo() != null) {
            uRIBuilder.setUserInfo(null);
        }
        if (uRIBuilder.getPathSegments().isEmpty()) {
            uRIBuilder.setPathSegments("");
        }
        if (TextUtils.isEmpty(uRIBuilder.getPath())) {
            uRIBuilder.setPath("/");
        }
        if (uRIBuilder.getHost() != null) {
            uRIBuilder.setHost(uRIBuilder.getHost().toLowerCase(Locale.ROOT));
        }
        uRIBuilder.setFragment(null);
        return uRIBuilder.build();
    }

    public static URI rewriteURIForRoute(URI uRI, RouteInfo routeInfo) throws URISyntaxException {
        return URIUtils.rewriteURIForRoute(uRI, routeInfo, true);
    }

    public static URI rewriteURIForRoute(URI uRI, RouteInfo routeInfo, boolean bl) throws URISyntaxException {
        if (uRI == null) {
            return null;
        }
        if (routeInfo.getProxyHost() != null && !routeInfo.isTunnelled()) {
            return uRI.isAbsolute() ? URIUtils.rewriteURI(uRI) : URIUtils.rewriteURI(uRI, routeInfo.getTargetHost(), bl ? DROP_FRAGMENT_AND_NORMALIZE : DROP_FRAGMENT);
        }
        return uRI.isAbsolute() ? URIUtils.rewriteURI(uRI, null, bl ? DROP_FRAGMENT_AND_NORMALIZE : DROP_FRAGMENT) : URIUtils.rewriteURI(uRI);
    }

    public static URI resolve(URI uRI, String string) {
        return URIUtils.resolve(uRI, URI.create(string));
    }

    public static URI resolve(URI uRI, URI uRI2) {
        URI uRI3;
        Args.notNull(uRI, "Base URI");
        Args.notNull(uRI2, "Reference URI");
        String string = uRI2.toASCIIString();
        if (string.startsWith("?")) {
            String string2 = uRI.toASCIIString();
            int n = string2.indexOf(63);
            string2 = n > -1 ? string2.substring(0, n) : string2;
            return URI.create(string2 + string);
        }
        boolean bl = string.isEmpty();
        if (bl) {
            uRI3 = uRI.resolve(URI.create("#"));
            String string3 = uRI3.toASCIIString();
            uRI3 = URI.create(string3.substring(0, string3.indexOf(35)));
        } else {
            uRI3 = uRI.resolve(uRI2);
        }
        try {
            return URIUtils.normalizeSyntax(uRI3);
        } catch (URISyntaxException uRISyntaxException) {
            throw new IllegalArgumentException(uRISyntaxException);
        }
    }

    public static URI normalizeSyntax(URI uRI) throws URISyntaxException {
        if (uRI.isOpaque() || uRI.getAuthority() == null) {
            return uRI;
        }
        URIBuilder uRIBuilder = new URIBuilder(uRI);
        List<String> list = uRIBuilder.getPathSegments();
        Stack<String> stack = new Stack<String>();
        for (String string : list) {
            if (".".equals(string)) continue;
            if ("..".equals(string)) {
                if (stack.isEmpty()) continue;
                stack.pop();
                continue;
            }
            stack.push(string);
        }
        if (stack.size() == 0) {
            stack.add("");
        }
        uRIBuilder.setPathSegments(stack);
        if (uRIBuilder.getScheme() != null) {
            uRIBuilder.setScheme(uRIBuilder.getScheme().toLowerCase(Locale.ROOT));
        }
        if (uRIBuilder.getHost() != null) {
            uRIBuilder.setHost(uRIBuilder.getHost().toLowerCase(Locale.ROOT));
        }
        return uRIBuilder.build();
    }

    public static HttpHost extractHost(URI uRI) {
        if (uRI == null) {
            return null;
        }
        if (uRI.isAbsolute()) {
            if (uRI.getHost() == null) {
                if (uRI.getAuthority() != null) {
                    int n;
                    String string;
                    String string2 = uRI.getAuthority();
                    int n2 = string2.indexOf(64);
                    if (n2 != -1) {
                        string2 = string2.substring(n2 + 1);
                    }
                    String string3 = uRI.getScheme();
                    n2 = string2.indexOf(":");
                    if (n2 != -1) {
                        string = string2.substring(0, n2);
                        try {
                            String string4 = string2.substring(n2 + 1);
                            n = !TextUtils.isEmpty(string4) ? Integer.parseInt(string4) : -1;
                        } catch (NumberFormatException numberFormatException) {
                            return null;
                        }
                    } else {
                        string = string2;
                        n = -1;
                    }
                    try {
                        return new HttpHost(string, n, string3);
                    } catch (IllegalArgumentException illegalArgumentException) {}
                }
            } else {
                return new HttpHost(uRI.getHost(), uRI.getPort(), uRI.getScheme());
            }
        }
        return null;
    }

    public static URI resolve(URI uRI, HttpHost httpHost, List<URI> list) throws URISyntaxException {
        URIBuilder uRIBuilder;
        Args.notNull(uRI, "Request URI");
        if (list == null || list.isEmpty()) {
            uRIBuilder = new URIBuilder(uRI);
        } else {
            uRIBuilder = new URIBuilder(list.get(list.size() - 1));
            String string = uRIBuilder.getFragment();
            for (int i = list.size() - 1; string == null && i >= 0; --i) {
                string = list.get(i).getFragment();
            }
            uRIBuilder.setFragment(string);
        }
        if (uRIBuilder.getFragment() == null) {
            uRIBuilder.setFragment(uRI.getFragment());
        }
        if (httpHost != null && !uRIBuilder.isAbsolute()) {
            uRIBuilder.setScheme(httpHost.getSchemeName());
            uRIBuilder.setHost(httpHost.getHostName());
            uRIBuilder.setPort(httpHost.getPort());
        }
        return uRIBuilder.build();
    }

    private URIUtils() {
    }

    public static enum UriFlag {
        DROP_FRAGMENT,
        NORMALIZE;

    }
}

