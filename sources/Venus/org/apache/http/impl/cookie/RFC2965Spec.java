/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.cookie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.NameValuePair;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.Obsolete;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.CommonCookieAttributeHandler;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieAttributeHandler;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieRestrictionViolationException;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.impl.cookie.BasicClientCookie2;
import org.apache.http.impl.cookie.BasicCommentHandler;
import org.apache.http.impl.cookie.BasicExpiresHandler;
import org.apache.http.impl.cookie.BasicMaxAgeHandler;
import org.apache.http.impl.cookie.BasicPathHandler;
import org.apache.http.impl.cookie.BasicSecureHandler;
import org.apache.http.impl.cookie.RFC2109Spec;
import org.apache.http.impl.cookie.RFC2965CommentUrlAttributeHandler;
import org.apache.http.impl.cookie.RFC2965DiscardAttributeHandler;
import org.apache.http.impl.cookie.RFC2965DomainAttributeHandler;
import org.apache.http.impl.cookie.RFC2965PortAttributeHandler;
import org.apache.http.impl.cookie.RFC2965VersionAttributeHandler;
import org.apache.http.message.BufferedHeader;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

@Obsolete
@Contract(threading=ThreadingBehavior.SAFE)
public class RFC2965Spec
extends RFC2109Spec {
    public RFC2965Spec() {
        this(null, false);
    }

    public RFC2965Spec(String[] stringArray, boolean bl) {
        super(bl, new RFC2965VersionAttributeHandler(), new BasicPathHandler(){

            @Override
            public void validate(Cookie cookie, CookieOrigin cookieOrigin) throws MalformedCookieException {
                if (!this.match(cookie, cookieOrigin)) {
                    throw new CookieRestrictionViolationException("Illegal 'path' attribute \"" + cookie.getPath() + "\". Path of origin: \"" + cookieOrigin.getPath() + "\"");
                }
            }
        }, new RFC2965DomainAttributeHandler(), new RFC2965PortAttributeHandler(), new BasicMaxAgeHandler(), new BasicSecureHandler(), new BasicCommentHandler(), new BasicExpiresHandler(stringArray != null ? (String[])stringArray.clone() : DATE_PATTERNS), new RFC2965CommentUrlAttributeHandler(), new RFC2965DiscardAttributeHandler());
    }

    RFC2965Spec(boolean bl, CommonCookieAttributeHandler ... commonCookieAttributeHandlerArray) {
        super(bl, commonCookieAttributeHandlerArray);
    }

    @Override
    public List<Cookie> parse(Header header, CookieOrigin cookieOrigin) throws MalformedCookieException {
        Args.notNull(header, "Header");
        Args.notNull(cookieOrigin, "Cookie origin");
        if (!header.getName().equalsIgnoreCase("Set-Cookie2")) {
            throw new MalformedCookieException("Unrecognized cookie header '" + header.toString() + "'");
        }
        HeaderElement[] headerElementArray = header.getElements();
        return this.createCookies(headerElementArray, RFC2965Spec.adjustEffectiveHost(cookieOrigin));
    }

    @Override
    protected List<Cookie> parse(HeaderElement[] headerElementArray, CookieOrigin cookieOrigin) throws MalformedCookieException {
        return this.createCookies(headerElementArray, RFC2965Spec.adjustEffectiveHost(cookieOrigin));
    }

    private List<Cookie> createCookies(HeaderElement[] headerElementArray, CookieOrigin cookieOrigin) throws MalformedCookieException {
        ArrayList<Cookie> arrayList = new ArrayList<Cookie>(headerElementArray.length);
        for (HeaderElement headerElement : headerElementArray) {
            String string = headerElement.getName();
            String string2 = headerElement.getValue();
            if (string == null || string.isEmpty()) {
                throw new MalformedCookieException("Cookie name may not be empty");
            }
            BasicClientCookie2 basicClientCookie2 = new BasicClientCookie2(string, string2);
            basicClientCookie2.setPath(RFC2965Spec.getDefaultPath(cookieOrigin));
            basicClientCookie2.setDomain(RFC2965Spec.getDefaultDomain(cookieOrigin));
            basicClientCookie2.setPorts(new int[]{cookieOrigin.getPort()});
            NameValuePair[] nameValuePairArray = headerElement.getParameters();
            HashMap<String, NameValuePair> hashMap = new HashMap<String, NameValuePair>(nameValuePairArray.length);
            for (int i = nameValuePairArray.length - 1; i >= 0; --i) {
                NameValuePair nameValuePair = nameValuePairArray[i];
                hashMap.put(nameValuePair.getName().toLowerCase(Locale.ROOT), nameValuePair);
            }
            for (Map.Entry entry : hashMap.entrySet()) {
                NameValuePair nameValuePair = (NameValuePair)entry.getValue();
                String string3 = nameValuePair.getName().toLowerCase(Locale.ROOT);
                basicClientCookie2.setAttribute(string3, nameValuePair.getValue());
                CookieAttributeHandler cookieAttributeHandler = this.findAttribHandler(string3);
                if (cookieAttributeHandler == null) continue;
                cookieAttributeHandler.parse(basicClientCookie2, nameValuePair.getValue());
            }
            arrayList.add(basicClientCookie2);
        }
        return arrayList;
    }

    @Override
    public void validate(Cookie cookie, CookieOrigin cookieOrigin) throws MalformedCookieException {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieOrigin, "Cookie origin");
        super.validate(cookie, RFC2965Spec.adjustEffectiveHost(cookieOrigin));
    }

    @Override
    public boolean match(Cookie cookie, CookieOrigin cookieOrigin) {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieOrigin, "Cookie origin");
        return super.match(cookie, RFC2965Spec.adjustEffectiveHost(cookieOrigin));
    }

    @Override
    protected void formatCookieAsVer(CharArrayBuffer charArrayBuffer, Cookie cookie, int n) {
        String string;
        super.formatCookieAsVer(charArrayBuffer, cookie, n);
        if (cookie instanceof ClientCookie && (string = ((ClientCookie)cookie).getAttribute("port")) != null) {
            int[] nArray;
            charArrayBuffer.append("; $Port");
            charArrayBuffer.append("=\"");
            if (!string.trim().isEmpty() && (nArray = cookie.getPorts()) != null) {
                int n2 = nArray.length;
                for (int i = 0; i < n2; ++i) {
                    if (i > 0) {
                        charArrayBuffer.append(",");
                    }
                    charArrayBuffer.append(Integer.toString(nArray[i]));
                }
            }
            charArrayBuffer.append("\"");
        }
    }

    private static CookieOrigin adjustEffectiveHost(CookieOrigin cookieOrigin) {
        String string = cookieOrigin.getHost();
        boolean bl = true;
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (c != '.' && c != ':') continue;
            bl = false;
            break;
        }
        return bl ? new CookieOrigin(string + ".local", cookieOrigin.getPort(), cookieOrigin.getPath(), cookieOrigin.isSecure()) : cookieOrigin;
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public Header getVersionHeader() {
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(40);
        charArrayBuffer.append("Cookie2");
        charArrayBuffer.append(": ");
        charArrayBuffer.append("$Version=");
        charArrayBuffer.append(Integer.toString(this.getVersion()));
        return new BufferedHeader(charArrayBuffer);
    }

    @Override
    public String toString() {
        return "rfc2965";
    }
}

