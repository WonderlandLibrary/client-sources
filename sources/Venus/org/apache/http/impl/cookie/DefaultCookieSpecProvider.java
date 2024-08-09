/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.cookie;

import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.cookie.CommonCookieAttributeHandler;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.impl.cookie.BasicCommentHandler;
import org.apache.http.impl.cookie.BasicDomainHandler;
import org.apache.http.impl.cookie.BasicExpiresHandler;
import org.apache.http.impl.cookie.BasicMaxAgeHandler;
import org.apache.http.impl.cookie.BasicPathHandler;
import org.apache.http.impl.cookie.BasicSecureHandler;
import org.apache.http.impl.cookie.DefaultCookieSpec;
import org.apache.http.impl.cookie.NetscapeDraftSpec;
import org.apache.http.impl.cookie.PublicSuffixDomainFilter;
import org.apache.http.impl.cookie.RFC2109DomainHandler;
import org.apache.http.impl.cookie.RFC2109Spec;
import org.apache.http.impl.cookie.RFC2109VersionHandler;
import org.apache.http.impl.cookie.RFC2965CommentUrlAttributeHandler;
import org.apache.http.impl.cookie.RFC2965DiscardAttributeHandler;
import org.apache.http.impl.cookie.RFC2965DomainAttributeHandler;
import org.apache.http.impl.cookie.RFC2965PortAttributeHandler;
import org.apache.http.impl.cookie.RFC2965Spec;
import org.apache.http.impl.cookie.RFC2965VersionAttributeHandler;
import org.apache.http.protocol.HttpContext;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class DefaultCookieSpecProvider
implements CookieSpecProvider {
    private final CompatibilityLevel compatibilityLevel;
    private final PublicSuffixMatcher publicSuffixMatcher;
    private final String[] datepatterns;
    private final boolean oneHeader;
    private volatile CookieSpec cookieSpec;

    public DefaultCookieSpecProvider(CompatibilityLevel compatibilityLevel, PublicSuffixMatcher publicSuffixMatcher, String[] stringArray, boolean bl) {
        this.compatibilityLevel = compatibilityLevel != null ? compatibilityLevel : CompatibilityLevel.DEFAULT;
        this.publicSuffixMatcher = publicSuffixMatcher;
        this.datepatterns = stringArray;
        this.oneHeader = bl;
    }

    public DefaultCookieSpecProvider(CompatibilityLevel compatibilityLevel, PublicSuffixMatcher publicSuffixMatcher) {
        this(compatibilityLevel, publicSuffixMatcher, null, false);
    }

    public DefaultCookieSpecProvider(PublicSuffixMatcher publicSuffixMatcher) {
        this(CompatibilityLevel.DEFAULT, publicSuffixMatcher, null, false);
    }

    public DefaultCookieSpecProvider() {
        this(CompatibilityLevel.DEFAULT, null, null, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public CookieSpec create(HttpContext httpContext) {
        if (this.cookieSpec == null) {
            DefaultCookieSpecProvider defaultCookieSpecProvider = this;
            synchronized (defaultCookieSpecProvider) {
                if (this.cookieSpec == null) {
                    String[] stringArray;
                    RFC2965Spec rFC2965Spec = new RFC2965Spec(this.oneHeader, new RFC2965VersionAttributeHandler(), new BasicPathHandler(), PublicSuffixDomainFilter.decorate(new RFC2965DomainAttributeHandler(), this.publicSuffixMatcher), new RFC2965PortAttributeHandler(), new BasicMaxAgeHandler(), new BasicSecureHandler(), new BasicCommentHandler(), new RFC2965CommentUrlAttributeHandler(), new RFC2965DiscardAttributeHandler());
                    RFC2109Spec rFC2109Spec = new RFC2109Spec(this.oneHeader, new RFC2109VersionHandler(), new BasicPathHandler(), PublicSuffixDomainFilter.decorate(new RFC2109DomainHandler(), this.publicSuffixMatcher), new BasicMaxAgeHandler(), new BasicSecureHandler(), new BasicCommentHandler());
                    CommonCookieAttributeHandler[] commonCookieAttributeHandlerArray = new CommonCookieAttributeHandler[5];
                    commonCookieAttributeHandlerArray[0] = PublicSuffixDomainFilter.decorate(new BasicDomainHandler(), this.publicSuffixMatcher);
                    commonCookieAttributeHandlerArray[1] = this.compatibilityLevel == CompatibilityLevel.IE_MEDIUM_SECURITY ? new BasicPathHandler(this){
                        final DefaultCookieSpecProvider this$0;
                        {
                            this.this$0 = defaultCookieSpecProvider;
                        }

                        @Override
                        public void validate(Cookie cookie, CookieOrigin cookieOrigin) throws MalformedCookieException {
                        }
                    } : new BasicPathHandler();
                    commonCookieAttributeHandlerArray[2] = new BasicSecureHandler();
                    commonCookieAttributeHandlerArray[3] = new BasicCommentHandler();
                    if (this.datepatterns != null) {
                        stringArray = (String[])this.datepatterns.clone();
                    } else {
                        String[] stringArray2 = new String[1];
                        stringArray = stringArray2;
                        stringArray2[0] = "EEE, dd-MMM-yy HH:mm:ss z";
                    }
                    commonCookieAttributeHandlerArray[4] = new BasicExpiresHandler(stringArray);
                    NetscapeDraftSpec netscapeDraftSpec = new NetscapeDraftSpec(commonCookieAttributeHandlerArray);
                    this.cookieSpec = new DefaultCookieSpec(rFC2965Spec, rFC2109Spec, netscapeDraftSpec);
                }
            }
        }
        return this.cookieSpec;
    }

    public static enum CompatibilityLevel {
        DEFAULT,
        IE_MEDIUM_SECURITY;

    }
}

