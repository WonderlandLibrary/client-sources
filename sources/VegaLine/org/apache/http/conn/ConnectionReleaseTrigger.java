/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.conn;

import java.io.IOException;

public interface ConnectionReleaseTrigger {
    public void releaseConnection() throws IOException;

    public void abortConnection() throws IOException;
}

