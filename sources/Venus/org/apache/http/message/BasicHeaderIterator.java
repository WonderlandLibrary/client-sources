/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.message;

import java.util.NoSuchElementException;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.util.Args;

public class BasicHeaderIterator
implements HeaderIterator {
    protected final Header[] allHeaders;
    protected int currentIndex;
    protected String headerName;

    public BasicHeaderIterator(Header[] headerArray, String string) {
        this.allHeaders = Args.notNull(headerArray, "Header array");
        this.headerName = string;
        this.currentIndex = this.findNext(-1);
    }

    protected int findNext(int n) {
        int n2 = n;
        if (n2 < -1) {
            return 1;
        }
        int n3 = this.allHeaders.length - 1;
        boolean bl = false;
        while (!bl && n2 < n3) {
            bl = this.filterHeader(++n2);
        }
        return bl ? n2 : -1;
    }

    protected boolean filterHeader(int n) {
        return this.headerName == null || this.headerName.equalsIgnoreCase(this.allHeaders[n].getName());
    }

    @Override
    public boolean hasNext() {
        return this.currentIndex >= 0;
    }

    @Override
    public Header nextHeader() throws NoSuchElementException {
        int n = this.currentIndex;
        if (n < 0) {
            throw new NoSuchElementException("Iteration already finished.");
        }
        this.currentIndex = this.findNext(n);
        return this.allHeaders[n];
    }

    @Override
    public final Object next() throws NoSuchElementException {
        return this.nextHeader();
    }

    @Override
    public void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Removing headers is not supported.");
    }
}

