/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver.dns;

import io.netty.resolver.dns.DnsServerAddressStream;
import io.netty.resolver.dns.DnsServerAddressStreamProvider;
import io.netty.resolver.dns.DnsServerAddresses;
import io.netty.util.NetUtil;
import io.netty.util.internal.SocketUtils;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.lang.reflect.Method;
import java.net.Inet6Address;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;

public final class DefaultDnsServerAddressStreamProvider
implements DnsServerAddressStreamProvider {
    private static final InternalLogger logger;
    public static final DefaultDnsServerAddressStreamProvider INSTANCE;
    private static final List<InetSocketAddress> DEFAULT_NAME_SERVER_LIST;
    private static final InetSocketAddress[] DEFAULT_NAME_SERVER_ARRAY;
    private static final DnsServerAddresses DEFAULT_NAME_SERVERS;
    static final int DNS_PORT = 53;

    private DefaultDnsServerAddressStreamProvider() {
    }

    @Override
    public DnsServerAddressStream nameServerAddressStream(String string) {
        return DEFAULT_NAME_SERVERS.stream();
    }

    public static List<InetSocketAddress> defaultAddressList() {
        return DEFAULT_NAME_SERVER_LIST;
    }

    public static DnsServerAddresses defaultAddresses() {
        return DEFAULT_NAME_SERVERS;
    }

    static InetSocketAddress[] defaultAddressArray() {
        return (InetSocketAddress[])DEFAULT_NAME_SERVER_ARRAY.clone();
    }

    static {
        Object object;
        Object object2;
        Object object3;
        logger = InternalLoggerFactory.getInstance(DefaultDnsServerAddressStreamProvider.class);
        INSTANCE = new DefaultDnsServerAddressStreamProvider();
        ArrayList<InetSocketAddress> arrayList = new ArrayList<InetSocketAddress>(2);
        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        hashtable.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
        hashtable.put("java.naming.provider.url", "dns://");
        try {
            object3 = new InitialDirContext(hashtable);
            object2 = (String)object3.getEnvironment().get("java.naming.provider.url");
            if (object2 != null && !((String)object2).isEmpty()) {
                object = ((String)object2).split(" ");
                for (String string : object) {
                    try {
                        URI uRISyntaxException = new URI(string);
                        String string2 = new URI(string).getHost();
                        if (string2 == null || string2.isEmpty()) {
                            logger.debug("Skipping a nameserver URI as host portion could not be extracted: {}", (Object)string);
                            continue;
                        }
                        int n = uRISyntaxException.getPort();
                        arrayList.add(SocketUtils.socketAddress(uRISyntaxException.getHost(), n == -1 ? 53 : n));
                    } catch (URISyntaxException uRISyntaxException) {
                        logger.debug("Skipping a malformed nameserver URI: {}", (Object)string, (Object)uRISyntaxException);
                    }
                }
            }
        } catch (NamingException namingException) {
            // empty catch block
        }
        if (arrayList.isEmpty()) {
            try {
                object3 = Class.forName("sun.net.dns.ResolverConfiguration");
                object2 = ((Class)object3).getMethod("open", new Class[0]);
                object = ((Class)object3).getMethod("nameservers", new Class[0]);
                Object object4 = ((Method)object2).invoke(null, new Object[0]);
                List list = (List)((Method)object).invoke(object4, new Object[0]);
                for (String string : list) {
                    if (string == null) continue;
                    arrayList.add(new InetSocketAddress(SocketUtils.addressByName(string), 53));
                }
            } catch (Exception exception) {
                // empty catch block
            }
        }
        if (!arrayList.isEmpty()) {
            if (logger.isDebugEnabled()) {
                logger.debug("Default DNS servers: {} (sun.net.dns.ResolverConfiguration)", (Object)arrayList);
            }
        } else {
            if (NetUtil.isIpV6AddressesPreferred() || NetUtil.LOCALHOST instanceof Inet6Address && !NetUtil.isIpV4StackPreferred()) {
                Collections.addAll(arrayList, SocketUtils.socketAddress("2001:4860:4860::8888", 53), SocketUtils.socketAddress("2001:4860:4860::8844", 53));
            } else {
                Collections.addAll(arrayList, SocketUtils.socketAddress("8.8.8.8", 53), SocketUtils.socketAddress("8.8.4.4", 53));
            }
            if (logger.isWarnEnabled()) {
                logger.warn("Default DNS servers: {} (Google Public DNS as a fallback)", (Object)arrayList);
            }
        }
        DEFAULT_NAME_SERVER_LIST = Collections.unmodifiableList(arrayList);
        DEFAULT_NAME_SERVER_ARRAY = arrayList.toArray(new InetSocketAddress[arrayList.size()]);
        DEFAULT_NAME_SERVERS = DnsServerAddresses.sequential(DEFAULT_NAME_SERVER_ARRAY);
    }
}

