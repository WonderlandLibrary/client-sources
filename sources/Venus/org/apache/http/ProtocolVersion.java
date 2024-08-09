/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http;

import java.io.Serializable;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.util.Args;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class ProtocolVersion
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 8950662842175091068L;
    protected final String protocol;
    protected final int major;
    protected final int minor;

    public ProtocolVersion(String string, int n, int n2) {
        this.protocol = Args.notNull(string, "Protocol name");
        this.major = Args.notNegative(n, "Protocol major version");
        this.minor = Args.notNegative(n2, "Protocol minor version");
    }

    public final String getProtocol() {
        return this.protocol;
    }

    public final int getMajor() {
        return this.major;
    }

    public final int getMinor() {
        return this.minor;
    }

    public ProtocolVersion forVersion(int n, int n2) {
        if (n == this.major && n2 == this.minor) {
            return this;
        }
        return new ProtocolVersion(this.protocol, n, n2);
    }

    public final int hashCode() {
        return this.protocol.hashCode() ^ this.major * 100000 ^ this.minor;
    }

    public final boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof ProtocolVersion)) {
            return true;
        }
        ProtocolVersion protocolVersion = (ProtocolVersion)object;
        return this.protocol.equals(protocolVersion.protocol) && this.major == protocolVersion.major && this.minor == protocolVersion.minor;
    }

    public boolean isComparable(ProtocolVersion protocolVersion) {
        return protocolVersion != null && this.protocol.equals(protocolVersion.protocol);
    }

    public int compareToVersion(ProtocolVersion protocolVersion) {
        Args.notNull(protocolVersion, "Protocol version");
        Args.check(this.protocol.equals(protocolVersion.protocol), "Versions for different protocols cannot be compared: %s %s", this, protocolVersion);
        int n = this.getMajor() - protocolVersion.getMajor();
        if (n == 0) {
            n = this.getMinor() - protocolVersion.getMinor();
        }
        return n;
    }

    public final boolean greaterEquals(ProtocolVersion protocolVersion) {
        return this.isComparable(protocolVersion) && this.compareToVersion(protocolVersion) >= 0;
    }

    public final boolean lessEquals(ProtocolVersion protocolVersion) {
        return this.isComparable(protocolVersion) && this.compareToVersion(protocolVersion) <= 0;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.protocol);
        stringBuilder.append('/');
        stringBuilder.append(Integer.toString(this.major));
        stringBuilder.append('.');
        stringBuilder.append(Integer.toString(this.minor));
        return stringBuilder.toString();
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

