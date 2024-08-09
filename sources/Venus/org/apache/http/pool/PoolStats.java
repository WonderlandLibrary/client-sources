/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.pool;

import java.io.Serializable;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class PoolStats
implements Serializable {
    private static final long serialVersionUID = -2807686144795228544L;
    private final int leased;
    private final int pending;
    private final int available;
    private final int max;

    public PoolStats(int n, int n2, int n3, int n4) {
        this.leased = n;
        this.pending = n2;
        this.available = n3;
        this.max = n4;
    }

    public int getLeased() {
        return this.leased;
    }

    public int getPending() {
        return this.pending;
    }

    public int getAvailable() {
        return this.available;
    }

    public int getMax() {
        return this.max;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[leased: ");
        stringBuilder.append(this.leased);
        stringBuilder.append("; pending: ");
        stringBuilder.append(this.pending);
        stringBuilder.append("; available: ");
        stringBuilder.append(this.available);
        stringBuilder.append("; max: ");
        stringBuilder.append(this.max);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

