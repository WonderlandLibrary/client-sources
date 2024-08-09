/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.auth;

import java.io.Serializable;
import java.security.Principal;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.util.Args;
import org.apache.http.util.LangUtils;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public final class BasicUserPrincipal
implements Principal,
Serializable {
    private static final long serialVersionUID = -2266305184969850467L;
    private final String username;

    public BasicUserPrincipal(String string) {
        Args.notNull(string, "User name");
        this.username = string;
    }

    @Override
    public String getName() {
        return this.username;
    }

    @Override
    public int hashCode() {
        int n = 17;
        n = LangUtils.hashCode(n, this.username);
        return n;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object instanceof BasicUserPrincipal) {
            BasicUserPrincipal basicUserPrincipal = (BasicUserPrincipal)object;
            if (LangUtils.equals(this.username, basicUserPrincipal.username)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[principal: ");
        stringBuilder.append(this.username);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

