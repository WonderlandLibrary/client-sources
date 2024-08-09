/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.message;

import java.util.List;
import java.util.NoSuchElementException;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

public class BasicListHeaderIterator
implements HeaderIterator {
    protected final List<Header> allHeaders;
    protected int currentIndex;
    protected int lastIndex;
    protected String headerName;

    public BasicListHeaderIterator(List<Header> list, String string) {
        this.allHeaders = Args.notNull(list, "Header list");
        this.headerName = string;
        this.currentIndex = this.findNext(-1);
        this.lastIndex = -1;
    }

    protected int findNext(int n) {
        int n2 = n;
        if (n2 < -1) {
            return 1;
        }
        int n3 = this.allHeaders.size() - 1;
        boolean bl = false;
        while (!bl && n2 < n3) {
            bl = this.filterHeader(++n2);
        }
        return bl ? n2 : -1;
    }

    protected boolean filterHeader(int n) {
        if (this.headerName == null) {
            return false;
        }
        String string = this.allHeaders.get(n).getName();
        return this.headerName.equalsIgnoreCase(string);
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
        this.lastIndex = n;
        this.currentIndex = this.findNext(n);
        return this.allHeaders.get(n);
    }

    @Override
    public final Object next() throws NoSuchElementException {
        return this.nextHeader();
    }

    @Override
    public void remove() throws UnsupportedOperationException {
        Asserts.check(this.lastIndex >= 0, "No header to remove");
        this.allHeaders.remove(this.lastIndex);
        this.lastIndex = -1;
        --this.currentIndex;
    }
}

