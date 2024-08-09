/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.cookie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import org.apache.http.FormattedHeader;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.NameValuePair;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieAttributeHandler;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.cookie.BasicCommentHandler;
import org.apache.http.impl.cookie.BasicDomainHandler;
import org.apache.http.impl.cookie.BasicExpiresHandler;
import org.apache.http.impl.cookie.BasicMaxAgeHandler;
import org.apache.http.impl.cookie.BasicPathHandler;
import org.apache.http.impl.cookie.BasicSecureHandler;
import org.apache.http.impl.cookie.BrowserCompatSpecFactory;
import org.apache.http.impl.cookie.BrowserCompatVersionAttributeHandler;
import org.apache.http.impl.cookie.CookieSpecBase;
import org.apache.http.impl.cookie.NetscapeDraftHeaderParser;
import org.apache.http.message.BasicHeaderElement;
import org.apache.http.message.BasicHeaderValueFormatter;
import org.apache.http.message.BufferedHeader;
import org.apache.http.message.ParserCursor;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

@Deprecated
@Contract(threading=ThreadingBehavior.SAFE)
public class BrowserCompatSpec
extends CookieSpecBase {
    private static final String[] DEFAULT_DATE_PATTERNS = new String[]{"EEE, dd MMM yyyy HH:mm:ss zzz", "EEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy", "EEE, dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MMM-yyyy HH-mm-ss z", "EEE, dd MMM yy HH:mm:ss z", "EEE dd-MMM-yyyy HH:mm:ss z", "EEE dd MMM yyyy HH:mm:ss z", "EEE dd-MMM-yyyy HH-mm-ss z", "EEE dd-MMM-yy HH:mm:ss z", "EEE dd MMM yy HH:mm:ss z", "EEE,dd-MMM-yy HH:mm:ss z", "EEE,dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MM-yyyy HH:mm:ss z"};

    public BrowserCompatSpec(String[] stringArray, BrowserCompatSpecFactory.SecurityLevel securityLevel) {
        super(new BrowserCompatVersionAttributeHandler(), new BasicDomainHandler(), securityLevel == BrowserCompatSpecFactory.SecurityLevel.SECURITYLEVEL_IE_MEDIUM ? new BasicPathHandler(){

            @Override
            public void validate(Cookie cookie, CookieOrigin cookieOrigin) throws MalformedCookieException {
            }
        } : new BasicPathHandler(), new BasicMaxAgeHandler(), new BasicSecureHandler(), new BasicCommentHandler(), new BasicExpiresHandler(stringArray != null ? (String[])stringArray.clone() : DEFAULT_DATE_PATTERNS));
    }

    public BrowserCompatSpec(String[] stringArray) {
        this(stringArray, BrowserCompatSpecFactory.SecurityLevel.SECURITYLEVEL_DEFAULT);
    }

    public BrowserCompatSpec() {
        this(null, BrowserCompatSpecFactory.SecurityLevel.SECURITYLEVEL_DEFAULT);
    }

    @Override
    public List<Cookie> parse(Header header, CookieOrigin cookieOrigin) throws MalformedCookieException {
        Args.notNull(header, "Header");
        Args.notNull(cookieOrigin, "Cookie origin");
        String string = header.getName();
        if (!string.equalsIgnoreCase("Set-Cookie")) {
            throw new MalformedCookieException("Unrecognized cookie header '" + header.toString() + "'");
        }
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
                String string2 = header.getValue();
                if (string2 == null) {
                    throw new MalformedCookieException("Header value is null");
                }
                charArrayBuffer = new CharArrayBuffer(string2.length());
                charArrayBuffer.append(string2);
                parserCursor = new ParserCursor(0, charArrayBuffer.length());
            }
            HeaderElement headerElement = netscapeDraftHeaderParser.parseHeader(charArrayBuffer, parserCursor);
            String string3 = headerElement.getName();
            String string4 = headerElement.getValue();
            if (string3 == null || string3.isEmpty()) {
                throw new MalformedCookieException("Cookie name may not be empty");
            }
            BasicClientCookie basicClientCookie = new BasicClientCookie(string3, string4);
            basicClientCookie.setPath(BrowserCompatSpec.getDefaultPath(cookieOrigin));
            basicClientCookie.setDomain(BrowserCompatSpec.getDefaultDomain(cookieOrigin));
            NameValuePair[] nameValuePairArray = headerElement.getParameters();
            for (int i = nameValuePairArray.length - 1; i >= 0; --i) {
                NameValuePair nameValuePair = nameValuePairArray[i];
                String string5 = nameValuePair.getName().toLowerCase(Locale.ROOT);
                basicClientCookie.setAttribute(string5, nameValuePair.getValue());
                CookieAttributeHandler cookieAttributeHandler = this.findAttribHandler(string5);
                if (cookieAttributeHandler == null) continue;
                cookieAttributeHandler.parse(basicClientCookie, nameValuePair.getValue());
            }
            if (bl2) {
                basicClientCookie.setVersion(0);
            }
            return Collections.singletonList(basicClientCookie);
        }
        return this.parse(headerElementArray, cookieOrigin);
    }

    private static boolean isQuoteEnclosed(String string) {
        return string != null && string.startsWith("\"") && string.endsWith("\"");
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
            String string = cookie.getName();
            String string2 = cookie.getValue();
            if (cookie.getVersion() > 0 && !BrowserCompatSpec.isQuoteEnclosed(string2)) {
                BasicHeaderValueFormatter.INSTANCE.formatHeaderElement(charArrayBuffer, new BasicHeaderElement(string, string2), true);
                continue;
            }
            charArrayBuffer.append(string);
            charArrayBuffer.append("=");
            if (string2 == null) continue;
            charArrayBuffer.append(string2);
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
        return "compatibility";
    }
}

