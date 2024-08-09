/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http;

import java.util.Iterator;
import org.apache.http.Header;

public interface HeaderIterator
extends Iterator<Object> {
    @Override
    public boolean hasNext();

    public Header nextHeader();
}

