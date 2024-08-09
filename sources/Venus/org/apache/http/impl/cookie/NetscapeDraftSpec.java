/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.cookie;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.FormattedHeader;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.Obsolete;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.cookie.CommonCookieAttributeHandler;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.impl.cookie.BasicCommentHandler;
import org.apache.http.impl.cookie.BasicExpiresHandler;
import org.apache.http.impl.cookie.BasicPathHandler;
import org.apache.http.impl.cookie.BasicSecureHandler;
import org.apache.http.impl.cookie.CookieSpecBase;
import org.apache.http.impl.cookie.NetscapeDomainHandler;
import org.apache.http.impl.cookie.NetscapeDraftHeaderParser;
import org.apache.http.message.BufferedHeader;
import org.apache.http.message.ParserCursor;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

@Obsolete
@Contract(threading=ThreadingBehavior.SAFE)
public class NetscapeDraftSpec
extends CookieSpecBase {
    protected static final String EXPIRES_PATTERN = "EEE, dd-MMM-yy HH:mm:ss z";

    public NetscapeDraftSpec(String[] stringArray) {
        String[] stringArray2;
        CommonCookieAttributeHandler[] commonCookieAttributeHandlerArray = new CommonCookieAttributeHandler[5];
        commonCookieAttributeHandlerArray[0] = new BasicPathHandler();
        commonCookieAttributeHandlerArray[1] = new NetscapeDomainHandler();
        commonCookieAttributeHandlerArray[2] = new BasicSecureHandler();
        commonCookieAttributeHandlerArray[3] = new BasicCommentHandler();
        if (stringArray != null) {
            stringArray2 = (String[])stringArray.clone();
        } else {
            String[] stringArray3 = new String[1];
            stringArray2 = stringArray3;
            stringArray3[0] = EXPIRES_PATTERN;
        }
        commonCookieAttributeHandlerArray[4] = new BasicExpiresHandler(stringArray2);
        super(commonCookieAttributeHandlerArray);
    }

    NetscapeDraftSpec(CommonCookieAttributeHandler ... commonCookieAttributeHandlerArray) {
        super(commonCookieAttributeHandlerArray);
    }

    public NetscapeDraftSpec() {
        this((String[])null);
    }

    @Override
    public List<Cookie> parse(Header header, CookieOrigin cookieOrigin) throws MalformedCookieException {
        ParserCursor parserCursor;
        CharArrayBuffer charArrayBuffer;
        Args.notNull(header, "Header");
        Args.notNull(cookieOrigin, "Cookie origin");
        if (!header.getName().equalsIgnoreCase("Set-Cookie")) {
            throw new MalformedCookieException("Unrecognized cookie header '" + header.toString() + "'");
        }
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
        return this.parse(new HeaderElement[]{netscapeDraftHeaderParser.parseHeader(charArrayBuffer, parserCursor)}, cookieOrigin);
    }

    @Override
    public List<Header> formatCookies(List<Cookie> list) {
        Args.notEmpty(list, "List of cookies");
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(20 * list.size());
        charArrayBuffer.append("Cookie");
        charArrayBuffer.append(": ");
        for (int i = 0; i < list.size(); ++i) {
            Cookie cookie = list.get(i);
            if (i > 0) {
                charArrayBuffer.append("; ");
            }
            charArrayBuffer.append(cookie.getName());
            String string = cookie.getValue();
            if (string == null) continue;
            charArrayBuffer.append("=");
            charArrayBuffer.append(string);
        }
        ArrayList<Header> arrayList = new ArrayList<Header>(1);
        arrayList.add(new BufferedHeader(charArrayBuffer));
        return arrayList;
    }

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public Header getVersionHeader() {
        return null;
    }

    public String toString() {
        return "netscape";
    }
}

