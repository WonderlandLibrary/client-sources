/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.cookie;

import java.util.List;
import org.apache.http.FormattedHeader;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.cookie.CommonCookieAttributeHandler;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie2;
import org.apache.http.impl.cookie.BasicCommentHandler;
import org.apache.http.impl.cookie.BasicDomainHandler;
import org.apache.http.impl.cookie.BasicExpiresHandler;
import org.apache.http.impl.cookie.BasicMaxAgeHandler;
import org.apache.http.impl.cookie.BasicPathHandler;
import org.apache.http.impl.cookie.BasicSecureHandler;
import org.apache.http.impl.cookie.NetscapeDraftHeaderParser;
import org.apache.http.impl.cookie.NetscapeDraftSpec;
import org.apache.http.impl.cookie.RFC2109DomainHandler;
import org.apache.http.impl.cookie.RFC2109Spec;
import org.apache.http.impl.cookie.RFC2109VersionHandler;
import org.apache.http.impl.cookie.RFC2965CommentUrlAttributeHandler;
import org.apache.http.impl.cookie.RFC2965DiscardAttributeHandler;
import org.apache.http.impl.cookie.RFC2965DomainAttributeHandler;
import org.apache.http.impl.cookie.RFC2965PortAttributeHandler;
import org.apache.http.impl.cookie.RFC2965Spec;
import org.apache.http.impl.cookie.RFC2965VersionAttributeHandler;
import org.apache.http.message.ParserCursor;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

@Contract(threading=ThreadingBehavior.SAFE)
public class DefaultCookieSpec
implements CookieSpec {
    private final RFC2965Spec strict;
    private final RFC2109Spec obsoleteStrict;
    private final NetscapeDraftSpec netscapeDraft;

    DefaultCookieSpec(RFC2965Spec rFC2965Spec, RFC2109Spec rFC2109Spec, NetscapeDraftSpec netscapeDraftSpec) {
        this.strict = rFC2965Spec;
        this.obsoleteStrict = rFC2109Spec;
        this.netscapeDraft = netscapeDraftSpec;
    }

    public DefaultCookieSpec(String[] stringArray, boolean bl) {
        String[] stringArray2;
        this.strict = new RFC2965Spec(bl, new RFC2965VersionAttributeHandler(), new BasicPathHandler(), new RFC2965DomainAttributeHandler(), new RFC2965PortAttributeHandler(), new BasicMaxAgeHandler(), new BasicSecureHandler(), new BasicCommentHandler(), new RFC2965CommentUrlAttributeHandler(), new RFC2965DiscardAttributeHandler());
        this.obsoleteStrict = new RFC2109Spec(bl, new RFC2109VersionHandler(), new BasicPathHandler(), new RFC2109DomainHandler(), new BasicMaxAgeHandler(), new BasicSecureHandler(), new BasicCommentHandler());
        CommonCookieAttributeHandler[] commonCookieAttributeHandlerArray = new CommonCookieAttributeHandler[5];
        commonCookieAttributeHandlerArray[0] = new BasicDomainHandler();
        commonCookieAttributeHandlerArray[1] = new BasicPathHandler();
        commonCookieAttributeHandlerArray[2] = new BasicSecureHandler();
        commonCookieAttributeHandlerArray[3] = new BasicCommentHandler();
        if (stringArray != null) {
            stringArray2 = (String[])stringArray.clone();
        } else {
            String[] stringArray3 = new String[1];
            stringArray2 = stringArray3;
            stringArray3[0] = "EEE, dd-MMM-yy HH:mm:ss z";
        }
        commonCookieAttributeHandlerArray[4] = new BasicExpiresHandler(stringArray2);
        this.netscapeDraft = new NetscapeDraftSpec(commonCookieAttributeHandlerArray);
    }

    public DefaultCookieSpec() {
        this(null, false);
    }

    @Override
    public List<Cookie> parse(Header header, CookieOrigin cookieOrigin) throws MalformedCookieException {
        Args.notNull(header, "Header");
        Args.notNull(cookieOrigin, "Cookie origin");
        HeaderElement[] headerElementArray = header.getElements();
        boolean bl = false;
        boolean bl2 = false;
        for (HeaderElement object : headerElementArray) {
            if (object.getParameterByName("version") != null) {
                bl = true;
            }
            if (object.getParameterByName("expires") == null) continue;
            bl2 = true;
        }
        if (bl2 || !bl) {
            ParserCursor parserCursor;
            CharArrayBuffer charArrayBuffer;
            NetscapeDraftHeaderParser netscapeDraftHeaderParser = NetscapeDraftHeaderParser.DEFAULT;
            if (header instanceof FormattedHeader) {
                charArrayBuffer = ((FormattedHeader)header).getBuffer();
                parserCursor = new ParserCursor(((FormattedHeader)header).getValuePos(), charArrayBuffer.length());
            } else {
                String string = header.getValue();
                if (string == null) {
                    throw new MalformedCookieException("Header value is null");
                }
                charArrayBuffer = new CharArrayBuffer(string.length());
                charArrayBuffer.append(string);
                parserCursor = new ParserCursor(0, charArrayBuffer.length());
            }
            headerElementArray = new HeaderElement[]{netscapeDraftHeaderParser.parseHeader(charArrayBuffer, parserCursor)};
            return this.netscapeDraft.parse(headerElementArray, cookieOrigin);
        }
        return "Set-Cookie2".equals(header.getName()) ? this.strict.parse(headerElementArray, cookieOrigin) : this.obsoleteStrict.parse(headerElementArray, cookieOrigin);
    }

    @Override
    public void validate(Cookie cookie, CookieOrigin cookieOrigin) throws MalformedCookieException {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieOrigin, "Cookie origin");
        if (cookie.getVersion() > 0) {
            if (cookie instanceof SetCookie2) {
                this.strict.validate(cookie, cookieOrigin);
            } else {
                this.obsoleteStrict.validate(cookie, cookieOrigin);
            }
        } else {
            this.netscapeDraft.validate(cookie, cookieOrigin);
        }
    }

    @Override
    public boolean match(Cookie cookie, CookieOrigin cookieOrigin) {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieOrigin, "Cookie origin");
        if (cookie.getVersion() > 0) {
            return cookie instanceof SetCookie2 ? this.strict.match(cookie, cookieOrigin) : this.obsoleteStrict.match(cookie, cookieOrigin);
        }
        return this.netscapeDraft.match(cookie, cookieOrigin);
    }

    @Override
    public List<Header> formatCookies(List<Cookie> list) {
        Args.notNull(list, "List of cookies");
        int n = Integer.MAX_VALUE;
        boolean bl = true;
        for (Cookie cookie : list) {
            if (!(cookie instanceof SetCookie2)) {
                bl = false;
            }
            if (cookie.getVersion() >= n) continue;
            n = cookie.getVersion();
        }
        if (n > 0) {
            return bl ? this.strict.formatCookies(list) : this.obsoleteStrict.formatCookies(list);
        }
        return this.netscapeDraft.formatCookies(list);
    }

    @Override
    public int getVersion() {
        return this.strict.getVersion();
    }

    @Override
    public Header getVersionHeader() {
        return null;
    }

    public String toString() {
        return "default";
    }
}

