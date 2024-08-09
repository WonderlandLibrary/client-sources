/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver.dns;

import io.netty.resolver.dns.DefaultDnsServerAddressStreamProvider;
import io.netty.resolver.dns.DnsServerAddressStream;
import io.netty.resolver.dns.DnsServerAddressStreamProvider;
import io.netty.resolver.dns.DnsServerAddresses;
import io.netty.util.NetUtil;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.SocketUtils;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class UnixResolverDnsServerAddressStreamProvider
implements DnsServerAddressStreamProvider {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(UnixResolverDnsServerAddressStreamProvider.class);
    private static final String ETC_RESOLV_CONF_FILE = "/etc/resolv.conf";
    private static final String ETC_RESOLVER_DIR = "/etc/resolver";
    private static final String NAMESERVER_ROW_LABEL = "nameserver";
    private static final String SORTLIST_ROW_LABEL = "sortlist";
    private static final String OPTIONS_ROW_LABEL = "options";
    private static final String DOMAIN_ROW_LABEL = "domain";
    private static final String PORT_ROW_LABEL = "port";
    private static final String NDOTS_LABEL = "ndots:";
    static final int DEFAULT_NDOTS = 1;
    private final DnsServerAddresses defaultNameServerAddresses;
    private final Map<String, DnsServerAddresses> domainToNameServerStreamMap;

    static DnsServerAddressStreamProvider parseSilently() {
        try {
            UnixResolverDnsServerAddressStreamProvider unixResolverDnsServerAddressStreamProvider = new UnixResolverDnsServerAddressStreamProvider(ETC_RESOLV_CONF_FILE, ETC_RESOLVER_DIR);
            return unixResolverDnsServerAddressStreamProvider.mayOverrideNameServers() ? unixResolverDnsServerAddressStreamProvider : DefaultDnsServerAddressStreamProvider.INSTANCE;
        } catch (Exception exception) {
            logger.debug("failed to parse {} and/or {}", ETC_RESOLV_CONF_FILE, ETC_RESOLVER_DIR, exception);
            return DefaultDnsServerAddressStreamProvider.INSTANCE;
        }
    }

    public UnixResolverDnsServerAddressStreamProvider(File file, File ... fileArray) throws IOException {
        Map<String, DnsServerAddresses> map = UnixResolverDnsServerAddressStreamProvider.parse(ObjectUtil.checkNotNull(file, "etcResolvConf"));
        boolean bl = fileArray != null && fileArray.length != 0;
        this.domainToNameServerStreamMap = bl ? UnixResolverDnsServerAddressStreamProvider.parse(fileArray) : map;
        DnsServerAddresses dnsServerAddresses = map.get(file.getName());
        if (dnsServerAddresses == null) {
            Collection<DnsServerAddresses> collection = map.values();
            if (collection.isEmpty()) {
                throw new IllegalArgumentException(file + " didn't provide any name servers");
            }
            this.defaultNameServerAddresses = collection.iterator().next();
        } else {
            this.defaultNameServerAddresses = dnsServerAddresses;
        }
        if (bl) {
            this.domainToNameServerStreamMap.putAll(map);
        }
    }

    public UnixResolverDnsServerAddressStreamProvider(String string, String string2) throws IOException {
        this(string == null ? null : new File(string), string2 == null ? null : new File(string2).listFiles());
    }

    @Override
    public DnsServerAddressStream nameServerAddressStream(String string) {
        int n;
        while ((n = string.indexOf(46, 1)) >= 0 && n != string.length() - 1) {
            DnsServerAddresses dnsServerAddresses = this.domainToNameServerStreamMap.get(string);
            if (dnsServerAddresses != null) {
                return dnsServerAddresses.stream();
            }
            string = string.substring(n + 1);
        }
        return this.defaultNameServerAddresses.stream();
    }

    private boolean mayOverrideNameServers() {
        return !this.domainToNameServerStreamMap.isEmpty() || this.defaultNameServerAddresses.stream().next() != null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static Map<String, DnsServerAddresses> parse(File ... fileArray) throws IOException {
        HashMap<String, DnsServerAddresses> hashMap = new HashMap<String, DnsServerAddresses>(fileArray.length << 1);
        for (File file : fileArray) {
            if (!file.isFile()) continue;
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = null;
            try {
                String string;
                bufferedReader = new BufferedReader(fileReader);
                ArrayList<InetSocketAddress> arrayList = new ArrayList<InetSocketAddress>(2);
                String string2 = file.getName();
                int n = 53;
                while ((string = bufferedReader.readLine()) != null) {
                    int n2;
                    char c;
                    if ((string = string.trim()).isEmpty() || (c = string.charAt(0)) == '#' || c == ';') continue;
                    if (string.startsWith(NAMESERVER_ROW_LABEL)) {
                        n2 = StringUtil.indexOfNonWhiteSpace(string, 10);
                        if (n2 < 0) {
                            throw new IllegalArgumentException("error parsing label nameserver in file " + file + ". value: " + string);
                        }
                        String string3 = string.substring(n2);
                        if (!NetUtil.isValidIpV4Address(string3) && !NetUtil.isValidIpV6Address(string3)) {
                            n2 = string3.lastIndexOf(46);
                            if (n2 + 1 >= string3.length()) {
                                throw new IllegalArgumentException("error parsing label nameserver in file " + file + ". invalid IP value: " + string);
                            }
                            n = Integer.parseInt(string3.substring(n2 + 1));
                            string3 = string3.substring(0, n2);
                        }
                        arrayList.add(SocketUtils.socketAddress(string3, n));
                        continue;
                    }
                    if (string.startsWith(DOMAIN_ROW_LABEL)) {
                        n2 = StringUtil.indexOfNonWhiteSpace(string, 6);
                        if (n2 < 0) {
                            throw new IllegalArgumentException("error parsing label domain in file " + file + " value: " + string);
                        }
                        string2 = string.substring(n2);
                        if (!arrayList.isEmpty()) {
                            UnixResolverDnsServerAddressStreamProvider.putIfAbsent(hashMap, string2, arrayList);
                        }
                        arrayList = new ArrayList(2);
                        continue;
                    }
                    if (string.startsWith(PORT_ROW_LABEL)) {
                        n2 = StringUtil.indexOfNonWhiteSpace(string, 4);
                        if (n2 < 0) {
                            throw new IllegalArgumentException("error parsing label port in file " + file + " value: " + string);
                        }
                        n = Integer.parseInt(string.substring(n2));
                        continue;
                    }
                    if (!string.startsWith(SORTLIST_ROW_LABEL)) continue;
                    logger.info("row type {} not supported. ignoring line: {}", (Object)SORTLIST_ROW_LABEL, (Object)string);
                }
                if (arrayList.isEmpty()) continue;
                UnixResolverDnsServerAddressStreamProvider.putIfAbsent(hashMap, string2, arrayList);
            } finally {
                if (bufferedReader == null) {
                    fileReader.close();
                } else {
                    bufferedReader.close();
                }
            }
        }
        return hashMap;
    }

    private static void putIfAbsent(Map<String, DnsServerAddresses> map, String string, List<InetSocketAddress> list) {
        UnixResolverDnsServerAddressStreamProvider.putIfAbsent(map, string, DnsServerAddresses.sequential(list));
    }

    private static void putIfAbsent(Map<String, DnsServerAddresses> map, String string, DnsServerAddresses dnsServerAddresses) {
        DnsServerAddresses dnsServerAddresses2 = map.put(string, dnsServerAddresses);
        if (dnsServerAddresses2 != null) {
            map.put(string, dnsServerAddresses2);
            logger.debug("Domain name {} already maps to addresses {} so new addresses {} will be discarded", string, dnsServerAddresses2, dnsServerAddresses);
        }
    }

    static int parseEtcResolverFirstNdots() throws IOException {
        return UnixResolverDnsServerAddressStreamProvider.parseEtcResolverFirstNdots(new File(ETC_RESOLV_CONF_FILE));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static int parseEtcResolverFirstNdots(File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = null;
        try {
            String string;
            bufferedReader = new BufferedReader(fileReader);
            while ((string = bufferedReader.readLine()) != null) {
                if (!string.startsWith(OPTIONS_ROW_LABEL)) continue;
                int n = string.indexOf(NDOTS_LABEL);
                if (n < 0) break;
                int n2 = string.indexOf(32, n += 6);
                int n3 = Integer.parseInt(string.substring(n, n2 < 0 ? string.length() : n2));
                return n3;
            }
        } finally {
            if (bufferedReader == null) {
                fileReader.close();
            } else {
                bufferedReader.close();
            }
        }
        return 0;
    }
}

