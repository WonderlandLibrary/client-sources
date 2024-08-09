/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import org.apache.http.config.ConnectionConfig;

public final class ConnSupport {
    public static CharsetDecoder createDecoder(ConnectionConfig connectionConfig) {
        if (connectionConfig == null) {
            return null;
        }
        Charset charset = connectionConfig.getCharset();
        CodingErrorAction codingErrorAction = connectionConfig.getMalformedInputAction();
        CodingErrorAction codingErrorAction2 = connectionConfig.getUnmappableInputAction();
        if (charset != null) {
            return charset.newDecoder().onMalformedInput(codingErrorAction != null ? codingErrorAction : CodingErrorAction.REPORT).onUnmappableCharacter(codingErrorAction2 != null ? codingErrorAction2 : CodingErrorAction.REPORT);
        }
        return null;
    }

    public static CharsetEncoder createEncoder(ConnectionConfig connectionConfig) {
        if (connectionConfig == null) {
            return null;
        }
        Charset charset = connectionConfig.getCharset();
        if (charset != null) {
            CodingErrorAction codingErrorAction = connectionConfig.getMalformedInputAction();
            CodingErrorAction codingErrorAction2 = connectionConfig.getUnmappableInputAction();
            return charset.newEncoder().onMalformedInput(codingErrorAction != null ? codingErrorAction : CodingErrorAction.REPORT).onUnmappableCharacter(codingErrorAction2 != null ? codingErrorAction2 : CodingErrorAction.REPORT);
        }
        return null;
    }
}

