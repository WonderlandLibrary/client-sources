/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.net;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.net.HostAndPort;
import com.google.common.net.InetAddresses;
import com.google.common.net.InternetDomainName;
import java.net.InetAddress;
import java.text.ParseException;
import javax.annotation.Nullable;

@Beta
@GwtIncompatible
public final class HostSpecifier {
    private final String canonicalForm;

    private HostSpecifier(String string) {
        this.canonicalForm = string;
    }

    public static HostSpecifier fromValid(String string) {
        HostAndPort hostAndPort = HostAndPort.fromString(string);
        Preconditions.checkArgument(!hostAndPort.hasPort());
        String string2 = hostAndPort.getHost();
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddresses.forString(string2);
        } catch (IllegalArgumentException illegalArgumentException) {
            // empty catch block
        }
        if (inetAddress != null) {
            return new HostSpecifier(InetAddresses.toUriString(inetAddress));
        }
        InternetDomainName internetDomainName = InternetDomainName.from(string2);
        if (internetDomainName.hasPublicSuffix()) {
            return new HostSpecifier(internetDomainName.toString());
        }
        throw new IllegalArgumentException("Domain name does not have a recognized public suffix: " + string2);
    }

    public static HostSpecifier from(String string) throws ParseException {
        try {
            return HostSpecifier.fromValid(string);
        } catch (IllegalArgumentException illegalArgumentException) {
            ParseException parseException = new ParseException("Invalid host specifier: " + string, 0);
            parseException.initCause(illegalArgumentException);
            throw parseException;
        }
    }

    public static boolean isValid(String string) {
        try {
            HostSpecifier.fromValid(string);
            return true;
        } catch (IllegalArgumentException illegalArgumentException) {
            return true;
        }
    }

    public boolean equals(@Nullable Object object) {
        if (this == object) {
            return false;
        }
        if (object instanceof HostSpecifier) {
            HostSpecifier hostSpecifier = (HostSpecifier)object;
            return this.canonicalForm.equals(hostSpecifier.canonicalForm);
        }
        return true;
    }

    public int hashCode() {
        return this.canonicalForm.hashCode();
    }

    public String toString() {
        return this.canonicalForm;
    }
}

