/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.auth;

import java.io.Serializable;
import java.security.Principal;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.auth.BasicUserPrincipal;
import org.apache.http.auth.Credentials;
import org.apache.http.util.Args;
import org.apache.http.util.LangUtils;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class UsernamePasswordCredentials
implements Credentials,
Serializable {
    private static final long serialVersionUID = 243343858802739403L;
    private final BasicUserPrincipal principal;
    private final String password;

    @Deprecated
    public UsernamePasswordCredentials(String string) {
        Args.notNull(string, "Username:password string");
        int n = string.indexOf(58);
        if (n >= 0) {
            this.principal = new BasicUserPrincipal(string.substring(0, n));
            this.password = string.substring(n + 1);
        } else {
            this.principal = new BasicUserPrincipal(string);
            this.password = null;
        }
    }

    public UsernamePasswordCredentials(String string, String string2) {
        Args.notNull(string, "Username");
        this.principal = new BasicUserPrincipal(string);
        this.password = string2;
    }

    @Override
    public Principal getUserPrincipal() {
        return this.principal;
    }

    public String getUserName() {
        return this.principal.getName();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public int hashCode() {
        return this.principal.hashCode();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object instanceof UsernamePasswordCredentials) {
            UsernamePasswordCredentials usernamePasswordCredentials = (UsernamePasswordCredentials)object;
            if (LangUtils.equals(this.principal, usernamePasswordCredentials.principal)) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        return this.principal.toString();
    }
}

