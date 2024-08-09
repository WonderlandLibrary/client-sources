/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.sevenz;

import java.util.LinkedList;
import org.apache.commons.compress.archivers.sevenz.BindPair;
import org.apache.commons.compress.archivers.sevenz.Coder;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
class Folder {
    Coder[] coders;
    long totalInputStreams;
    long totalOutputStreams;
    BindPair[] bindPairs;
    long[] packedStreams;
    long[] unpackSizes;
    boolean hasCrc;
    long crc;
    int numUnpackSubStreams;

    Folder() {
    }

    Iterable<Coder> getOrderedCoders() {
        LinkedList<Coder> linkedList = new LinkedList<Coder>();
        int n = (int)this.packedStreams[0];
        while (n != -1) {
            linkedList.addLast(this.coders[n]);
            int n2 = this.findBindPairForOutStream(n);
            n = n2 != -1 ? (int)this.bindPairs[n2].inIndex : -1;
        }
        return linkedList;
    }

    int findBindPairForInStream(int n) {
        for (int i = 0; i < this.bindPairs.length; ++i) {
            if (this.bindPairs[i].inIndex != (long)n) continue;
            return i;
        }
        return 1;
    }

    int findBindPairForOutStream(int n) {
        for (int i = 0; i < this.bindPairs.length; ++i) {
            if (this.bindPairs[i].outIndex != (long)n) continue;
            return i;
        }
        return 1;
    }

    long getUnpackSize() {
        if (this.totalOutputStreams == 0L) {
            return 0L;
        }
        for (int i = (int)this.totalOutputStreams - 1; i >= 0; --i) {
            if (this.findBindPairForOutStream(i) >= 0) continue;
            return this.unpackSizes[i];
        }
        return 0L;
    }
}

