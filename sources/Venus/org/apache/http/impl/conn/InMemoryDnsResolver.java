/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.conn;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.DnsResolver;
import org.apache.http.util.Args;

public class InMemoryDnsResolver
implements DnsResolver {
    private final Log log = LogFactory.getLog(InMemoryDnsResolver.class);
    private final Map<String, InetAddress[]> dnsMap = new ConcurrentHashMap<String, InetAddress[]>();

    public void add(String string, InetAddress ... inetAddressArray) {
        Args.notNull(string, "Host name");
        Args.notNull(inetAddressArray, "Array of IP addresses");
        this.dnsMap.put(string, inetAddressArray);
    }

    @Override
    public InetAddress[] resolve(String string) throws UnknownHostException {
        Object[] objectArray = this.dnsMap.get(string);
        if (this.log.isInfoEnabled()) {
            this.log.info("Resolving " + string + " to " + Arrays.deepToString(objectArray));
        }
        if (objectArray == null) {
            throw new UnknownHostException(string + " cannot be resolved");
        }
        return objectArray;
    }
}

