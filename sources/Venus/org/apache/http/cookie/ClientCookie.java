/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.cookie;

import org.apache.http.annotation.Obsolete;
import org.apache.http.cookie.Cookie;

public interface ClientCookie
extends Cookie {
    @Obsolete
    public static final String VERSION_ATTR = "version";
    public static final String PATH_ATTR = "path";
    public static final String DOMAIN_ATTR = "domain";
    public static final String MAX_AGE_ATTR = "max-age";
    public static final String SECURE_ATTR = "secure";
    @Obsolete
    public static final String COMMENT_ATTR = "comment";
    public static final String EXPIRES_ATTR = "expires";
    @Obsolete
    public static final String PORT_ATTR = "port";
    @Obsolete
    public static final String COMMENTURL_ATTR = "commenturl";
    @Obsolete
    public static final String DISCARD_ATTR = "discard";

    public String getAttribute(String var1);

    public boolean containsAttribute(String var1);
}

