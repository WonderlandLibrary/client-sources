/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.auth;

import java.io.Serializable;
import java.security.Principal;
import java.util.Locale;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.NTUserPrincipal;
import org.apache.http.util.Args;
import org.apache.http.util.LangUtils;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class NTCredentials
implements Credentials,
Serializable {
    private static final long serialVersionUID = -7385699315228907265L;
    private final NTUserPrincipal principal;
    private final String password;
    private final String workstation;

    @Deprecated
    public NTCredentials(String string) {
        String string2;
        Args.notNull(string, "Username:password string");
        int n = string.indexOf(58);
        if (n >= 0) {
            string2 = string.substring(0, n);
            this.password = string.substring(n + 1);
        } else {
            string2 = string;
            this.password = null;
        }
        int n2 = string2.indexOf(47);
        this.principal = n2 >= 0 ? new NTUserPrincipal(string2.substring(0, n2).toUpperCase(Locale.ROOT), string2.substring(n2 + 1)) : new NTUserPrincipal(null, string2.substring(n2 + 1));
        this.workstation = null;
    }

    public NTCredentials(String string, String string2, String string3, String string4) {
        Args.notNull(string, "User name");
        this.principal = new NTUserPrincipal(string4, string);
        this.password = string2;
        this.workstation = string3 != null ? string3.toUpperCase(Locale.ROOT) : null;
    }

    @Override
    public Principal getUserPrincipal() {
        return this.principal;
    }

    public String getUserName() {
        return this.principal.getUsername();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public String getDomain() {
        return this.principal.getDomain();
    }

    public String getWorkstation() {
        return this.workstation;
    }

    public int hashCode() {
        int n = 17;
        n = LangUtils.hashCode(n, this.principal);
        n = LangUtils.hashCode(n, this.workstation);
        return n;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object instanceof NTCredentials) {
            NTCredentials nTCredentials = (NTCredentials)object;
            if (LangUtils.equals(this.principal, nTCredentials.principal) && LangUtils.equals(this.workstation, nTCredentials.workstation)) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[principal: ");
        stringBuilder.append(this.principal);
        stringBuilder.append("][workstation: ");
        stringBuilder.append(this.workstation);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

