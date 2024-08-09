/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.HpackHeaderField;

final class HpackDynamicTable {
    HpackHeaderField[] hpackHeaderFields;
    int head;
    int tail;
    private long size;
    private long capacity = -1L;

    HpackDynamicTable(long l) {
        this.setCapacity(l);
    }

    public int length() {
        int n = this.head < this.tail ? this.hpackHeaderFields.length - this.tail + this.head : this.head - this.tail;
        return n;
    }

    public long size() {
        return this.size;
    }

    public long capacity() {
        return this.capacity;
    }

    public HpackHeaderField getEntry(int n) {
        if (n <= 0 || n > this.length()) {
            throw new IndexOutOfBoundsException();
        }
        int n2 = this.head - n;
        if (n2 < 0) {
            return this.hpackHeaderFields[n2 + this.hpackHeaderFields.length];
        }
        return this.hpackHeaderFields[n2];
    }

    public void add(HpackHeaderField hpackHeaderField) {
        int n = hpackHeaderField.size();
        if ((long)n > this.capacity) {
            this.clear();
            return;
        }
        while (this.capacity - this.size < (long)n) {
            this.remove();
        }
        this.hpackHeaderFields[this.head++] = hpackHeaderField;
        this.size += (long)hpackHeaderField.size();
        if (this.head == this.hpackHeaderFields.length) {
            this.head = 0;
        }
    }

    public HpackHeaderField remove() {
        HpackHeaderField hpackHeaderField = this.hpackHeaderFields[this.tail];
        if (hpackHeaderField == null) {
            return null;
        }
        this.size -= (long)hpackHeaderField.size();
        this.hpackHeaderFields[this.tail++] = null;
        if (this.tail == this.hpackHeaderFields.length) {
            this.tail = 0;
        }
        return hpackHeaderField;
    }

    public void clear() {
        while (this.tail != this.head) {
            this.hpackHeaderFields[this.tail++] = null;
            if (this.tail != this.hpackHeaderFields.length) continue;
            this.tail = 0;
        }
        this.head = 0;
        this.tail = 0;
        this.size = 0L;
    }

    public void setCapacity(long l) {
        if (l < 0L || l > 0xFFFFFFFFL) {
            throw new IllegalArgumentException("capacity is invalid: " + l);
        }
        if (this.capacity == l) {
            return;
        }
        this.capacity = l;
        if (l == 0L) {
            this.clear();
        } else {
            while (this.size > l) {
                this.remove();
            }
        }
        int n = (int)(l / 32L);
        if (l % 32L != 0L) {
            ++n;
        }
        if (this.hpackHeaderFields != null && this.hpackHeaderFields.length == n) {
            return;
        }
        HpackHeaderField[] hpackHeaderFieldArray = new HpackHeaderField[n];
        int n2 = this.length();
        int n3 = this.tail;
        for (int i = 0; i < n2; ++i) {
            HpackHeaderField hpackHeaderField;
            hpackHeaderFieldArray[i] = hpackHeaderField = this.hpackHeaderFields[n3++];
            if (n3 != this.hpackHeaderFields.length) continue;
            n3 = 0;
        }
        this.tail = 0;
        this.head = this.tail + n2;
        this.hpackHeaderFields = hpackHeaderFieldArray;
    }
}

