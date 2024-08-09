/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.auth;

import java.io.Serializable;
import java.security.Principal;
import java.util.Locale;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.util.Args;
import org.apache.http.util.LangUtils;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class NTUserPrincipal
implements Principal,
Serializable {
    private static final long serialVersionUID = -6870169797924406894L;
    private final String username;
    private final String domain;
    private final String ntname;

    public NTUserPrincipal(String string, String string2) {
        Args.notNull(string2, "User name");
        this.username = string2;
        this.domain = string != null ? string.toUpperCase(Locale.ROOT) : null;
        if (this.domain != null && !this.domain.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.domain);
            stringBuilder.append('\\');
            stringBuilder.append(this.username);
            this.ntname = stringBuilder.toString();
        } else {
            this.ntname = this.username;
        }
    }

    @Override
    public String getName() {
        return this.ntname;
    }

    public String getDomain() {
        return this.domain;
    }

    public String getUsername() {
        return this.username;
    }

    @Override
    public int hashCode() {
        int n = 17;
        n = LangUtils.hashCode(n, this.username);
        n = LangUtils.hashCode(n, this.domain);
        return n;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object instanceof NTUserPrincipal) {
            NTUserPrincipal nTUserPrincipal = (NTUserPrincipal)object;
            if (LangUtils.equals(this.username, nTUserPrincipal.username) && LangUtils.equals(this.domain, nTUserPrincipal.domain)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return this.ntname;
    }
}

