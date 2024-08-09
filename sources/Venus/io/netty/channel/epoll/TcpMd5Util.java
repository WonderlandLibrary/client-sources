/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.epoll;

import io.netty.channel.epoll.AbstractEpollChannel;
import io.netty.channel.epoll.Native;
import io.netty.util.internal.ObjectUtil;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

final class TcpMd5Util {
    static Collection<InetAddress> newTcpMd5Sigs(AbstractEpollChannel abstractEpollChannel, Collection<InetAddress> collection, Map<InetAddress, byte[]> map) throws IOException {
        ObjectUtil.checkNotNull(abstractEpollChannel, "channel");
        ObjectUtil.checkNotNull(collection, "current");
        ObjectUtil.checkNotNull(map, "newKeys");
        for (Map.Entry<InetAddress, byte[]> object : map.entrySet()) {
            byte[] byArray = object.getValue();
            if (object.getKey() == null) {
                throw new IllegalArgumentException("newKeys contains an entry with null address: " + map);
            }
            if (byArray == null) {
                throw new NullPointerException("newKeys[" + object.getKey() + ']');
            }
            if (byArray.length == 0) {
                throw new IllegalArgumentException("newKeys[" + object.getKey() + "] has an empty key.");
            }
            if (byArray.length <= Native.TCP_MD5SIG_MAXKEYLEN) continue;
            throw new IllegalArgumentException("newKeys[" + object.getKey() + "] has a key with invalid length; should not exceed the maximum length (" + Native.TCP_MD5SIG_MAXKEYLEN + ')');
        }
        for (InetAddress inetAddress : collection) {
            if (map.containsKey(inetAddress)) continue;
            abstractEpollChannel.socket.setTcpMd5Sig(inetAddress, null);
        }
        if (map.isEmpty()) {
            return Collections.emptySet();
        }
        ArrayList arrayList = new ArrayList(map.size());
        for (Map.Entry<InetAddress, byte[]> entry : map.entrySet()) {
            abstractEpollChannel.socket.setTcpMd5Sig(entry.getKey(), entry.getValue());
            arrayList.add(entry.getKey());
        }
        return arrayList;
    }

    private TcpMd5Util() {
    }
}

