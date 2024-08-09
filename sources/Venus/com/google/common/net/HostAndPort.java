/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.net;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import java.io.Serializable;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Beta
@Immutable
@GwtCompatible
public final class HostAndPort
implements Serializable {
    private static final int NO_PORT = -1;
    private final String host;
    private final int port;
    private final boolean hasBracketlessColons;
    private static final long serialVersionUID = 0L;

    private HostAndPort(String string, int n, boolean bl) {
        this.host = string;
        this.port = n;
        this.hasBracketlessColons = bl;
    }

    public String getHost() {
        return this.host;
    }

    @Deprecated
    public String getHostText() {
        return this.host;
    }

    public boolean hasPort() {
        return this.port >= 0;
    }

    public int getPort() {
        Preconditions.checkState(this.hasPort());
        return this.port;
    }

    public int getPortOrDefault(int n) {
        return this.hasPort() ? this.port : n;
    }

    public static HostAndPort fromParts(String string, int n) {
        Preconditions.checkArgument(HostAndPort.isValidPort(n), "Port out of range: %s", n);
        HostAndPort hostAndPort = HostAndPort.fromString(string);
        Preconditions.checkArgument(!hostAndPort.hasPort(), "Host has a port: %s", (Object)string);
        return new HostAndPort(hostAndPort.host, n, hostAndPort.hasBracketlessColons);
    }

    public static HostAndPort fromHost(String string) {
        HostAndPort hostAndPort = HostAndPort.fromString(string);
        Preconditions.checkArgument(!hostAndPort.hasPort(), "Host has a port: %s", (Object)string);
        return hostAndPort;
    }

    public static HostAndPort fromString(String string) {
        String string2;
        Preconditions.checkNotNull(string);
        String string3 = null;
        boolean bl = false;
        if (string.startsWith("[")) {
            String[] stringArray = HostAndPort.getHostAndPortFromBracketedHost(string);
            string2 = stringArray[0];
            string3 = stringArray[5];
        } else {
            int n = string.indexOf(58);
            if (n >= 0 && string.indexOf(58, n + 1) == -1) {
                string2 = string.substring(0, n);
                string3 = string.substring(n + 1);
            } else {
                string2 = string;
                bl = n >= 0;
            }
        }
        int n = -1;
        if (!Strings.isNullOrEmpty(string3)) {
            Preconditions.checkArgument(!string3.startsWith("+"), "Unparseable port number: %s", (Object)string);
            try {
                n = Integer.parseInt(string3);
            } catch (NumberFormatException numberFormatException) {
                throw new IllegalArgumentException("Unparseable port number: " + string);
            }
            Preconditions.checkArgument(HostAndPort.isValidPort(n), "Port number out of range: %s", (Object)string);
        }
        return new HostAndPort(string2, n, bl);
    }

    private static String[] getHostAndPortFromBracketedHost(String string) {
        int n = 0;
        int n2 = 0;
        Preconditions.checkArgument(string.charAt(0) == '[', "Bracketed host-port string must start with a bracket: %s", (Object)string);
        n = string.indexOf(58);
        n2 = string.lastIndexOf(93);
        Preconditions.checkArgument(n > -1 && n2 > n, "Invalid bracketed host/port: %s", (Object)string);
        String string2 = string.substring(1, n2);
        if (n2 + 1 == string.length()) {
            return new String[]{string2, ""};
        }
        Preconditions.checkArgument(string.charAt(n2 + 1) == ':', "Only a colon may follow a close bracket: %s", (Object)string);
        for (int i = n2 + 2; i < string.length(); ++i) {
            Preconditions.checkArgument(Character.isDigit(string.charAt(i)), "Port must be numeric: %s", (Object)string);
        }
        return new String[]{string2, string.substring(n2 + 2)};
    }

    public HostAndPort withDefaultPort(int n) {
        Preconditions.checkArgument(HostAndPort.isValidPort(n));
        if (this.hasPort() || this.port == n) {
            return this;
        }
        return new HostAndPort(this.host, n, this.hasBracketlessColons);
    }

    public HostAndPort requireBracketsForIPv6() {
        Preconditions.checkArgument(!this.hasBracketlessColons, "Possible bracketless IPv6 literal: %s", (Object)this.host);
        return this;
    }

    public boolean equals(@Nullable Object object) {
        if (this == object) {
            return false;
        }
        if (object instanceof HostAndPort) {
            HostAndPort hostAndPort = (HostAndPort)object;
            return Objects.equal(this.host, hostAndPort.host) && this.port == hostAndPort.port && this.hasBracketlessColons == hostAndPort.hasBracketlessColons;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hashCode(this.host, this.port, this.hasBracketlessColons);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(this.host.length() + 8);
        if (this.host.indexOf(58) >= 0) {
            stringBuilder.append('[').append(this.host).append(']');
        } else {
            stringBuilder.append(this.host);
        }
        if (this.hasPort()) {
            stringBuilder.append(':').append(this.port);
        }
        return stringBuilder.toString();
    }

    private static boolean isValidPort(int n) {
        return n >= 0 && n <= 65535;
    }
}

