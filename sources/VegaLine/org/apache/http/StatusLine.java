/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http;

import org.apache.http.ProtocolVersion;

public interface StatusLine {
    public ProtocolVersion getProtocolVersion();

    public int getStatusCode();

    public String getReasonPhrase();
}

