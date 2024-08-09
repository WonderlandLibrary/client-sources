/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http;

import org.apache.http.ProtocolVersion;

public interface RequestLine {
    public String getMethod();

    public ProtocolVersion getProtocolVersion();

    public String getUri();
}

