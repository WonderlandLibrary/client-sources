/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http;

import org.apache.http.HttpMessage;
import org.apache.http.RequestLine;

public interface HttpRequest
extends HttpMessage {
    public RequestLine getRequestLine();
}

