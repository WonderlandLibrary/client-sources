/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver;

import io.netty.resolver.HostsFileEntries;
import io.netty.resolver.HostsFileEntriesResolver;
import io.netty.resolver.HostsFileParser;
import io.netty.resolver.ResolvedAddressTypes;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.Locale;
import java.util.Map;

public final class DefaultHostsFileEntriesResolver
implements HostsFileEntriesResolver {
    private final Map<String, Inet4Address> inet4Entries;
    private final Map<String, Inet6Address> inet6Entries;

    public DefaultHostsFileEntriesResolver() {
        this(HostsFileParser.parseSilently());
    }

    DefaultHostsFileEntriesResolver(HostsFileEntries hostsFileEntries) {
        this.inet4Entries = hostsFileEntries.inet4Entries();
        this.inet6Entries = hostsFileEntries.inet6Entries();
    }

    @Override
    public InetAddress address(String string, ResolvedAddressTypes resolvedAddressTypes) {
        String string2 = this.normalize(string);
        switch (1.$SwitchMap$io$netty$resolver$ResolvedAddressTypes[resolvedAddressTypes.ordinal()]) {
            case 1: {
                return this.inet4Entries.get(string2);
            }
            case 2: {
                return this.inet6Entries.get(string2);
            }
            case 3: {
                Inet4Address inet4Address = this.inet4Entries.get(string2);
                return inet4Address != null ? inet4Address : (InetAddress)this.inet6Entries.get(string2);
            }
            case 4: {
                Inet6Address inet6Address = this.inet6Entries.get(string2);
                return inet6Address != null ? inet6Address : (InetAddress)this.inet4Entries.get(string2);
            }
        }
        throw new IllegalArgumentException("Unknown ResolvedAddressTypes " + (Object)((Object)resolvedAddressTypes));
    }

    String normalize(String string) {
        return string.toLowerCase(Locale.ENGLISH);
    }
}

