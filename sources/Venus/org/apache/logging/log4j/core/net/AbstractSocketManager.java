/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.net;

import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.appender.OutputStreamManager;

public abstract class AbstractSocketManager
extends OutputStreamManager {
    protected final InetAddress inetAddress;
    protected final String host;
    protected final int port;

    public AbstractSocketManager(String string, OutputStream outputStream, InetAddress inetAddress, String string2, int n, Layout<? extends Serializable> layout, boolean bl, int n2) {
        super(outputStream, string, layout, bl, n2);
        this.inetAddress = inetAddress;
        this.host = string2;
        this.port = n;
    }

    @Override
    public Map<String, String> getContentFormat() {
        HashMap<String, String> hashMap = new HashMap<String, String>(super.getContentFormat());
        hashMap.put("port", Integer.toString(this.port));
        hashMap.put("address", this.inetAddress.getHostAddress());
        return hashMap;
    }
}

