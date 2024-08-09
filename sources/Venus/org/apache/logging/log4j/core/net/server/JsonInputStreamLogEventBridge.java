/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.net.server;

import java.nio.charset.Charset;
import org.apache.logging.log4j.core.jackson.Log4jJsonObjectMapper;
import org.apache.logging.log4j.core.net.server.InputStreamLogEventBridge;

public class JsonInputStreamLogEventBridge
extends InputStreamLogEventBridge {
    private static final int[] END_PAIR = new int[]{-1, -1};
    private static final char EVENT_END_MARKER = '}';
    private static final char EVENT_START_MARKER = '{';
    private static final char JSON_ESC = '\\';
    private static final char JSON_STR_DELIM = '\"';
    private static final boolean THREAD_CONTEXT_MAP_AS_LIST = false;

    public JsonInputStreamLogEventBridge() {
        this(1024, Charset.defaultCharset());
    }

    public JsonInputStreamLogEventBridge(int n, Charset charset) {
        super(new Log4jJsonObjectMapper(false, true), n, charset, String.valueOf('}'));
    }

    @Override
    protected int[] getEventIndices(String string, int n) {
        int n2 = string.indexOf(123, n);
        if (n2 == -1) {
            return END_PAIR;
        }
        char[] cArray = string.toCharArray();
        int n3 = 0;
        boolean bl = false;
        boolean bl2 = false;
        for (int i = n2; i < cArray.length; ++i) {
            char c = cArray[i];
            if (bl2) {
                bl2 = false;
                continue;
            }
            switch (c) {
                case '{': {
                    if (bl) break;
                    ++n3;
                    break;
                }
                case '}': {
                    if (bl) break;
                    --n3;
                    break;
                }
                case '\"': {
                    bl = !bl;
                    break;
                }
                case '\\': {
                    bl2 = true;
                }
            }
            if (n3 != 0) continue;
            return new int[]{n2, i};
        }
        return END_PAIR;
    }
}

