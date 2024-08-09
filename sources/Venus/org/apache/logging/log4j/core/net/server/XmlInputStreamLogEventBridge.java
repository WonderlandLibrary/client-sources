/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.net.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.Charset;
import org.apache.logging.log4j.core.jackson.Log4jXmlObjectMapper;
import org.apache.logging.log4j.core.net.server.InputStreamLogEventBridge;

public class XmlInputStreamLogEventBridge
extends InputStreamLogEventBridge {
    private static final String EVENT_END = "</Event>";
    private static final String EVENT_START_NS_N = "<Event>";
    private static final String EVENT_START_NS_Y = "<Event ";

    public XmlInputStreamLogEventBridge() {
        this(1024, Charset.defaultCharset());
    }

    public XmlInputStreamLogEventBridge(int n, Charset charset) {
        super((ObjectMapper)((Object)new Log4jXmlObjectMapper()), n, charset, EVENT_END);
    }

    @Override
    protected int[] getEventIndices(String string, int n) {
        int n2 = string.indexOf(EVENT_START_NS_Y, n);
        int n3 = 7;
        if (n2 < 0) {
            n2 = string.indexOf(EVENT_START_NS_N, n);
            n3 = 7;
        }
        int n4 = n2 < 0 ? -1 : string.indexOf(EVENT_END, n2 + n3);
        return new int[]{n2, n4};
    }
}

