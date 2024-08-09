/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.dns;

import io.netty.util.internal.ObjectUtil;

public class DnsOpCode
implements Comparable<DnsOpCode> {
    public static final DnsOpCode QUERY = new DnsOpCode(0, "QUERY");
    public static final DnsOpCode IQUERY = new DnsOpCode(1, "IQUERY");
    public static final DnsOpCode STATUS = new DnsOpCode(2, "STATUS");
    public static final DnsOpCode NOTIFY = new DnsOpCode(4, "NOTIFY");
    public static final DnsOpCode UPDATE = new DnsOpCode(5, "UPDATE");
    private final byte byteValue;
    private final String name;
    private String text;

    public static DnsOpCode valueOf(int n) {
        switch (n) {
            case 0: {
                return QUERY;
            }
            case 1: {
                return IQUERY;
            }
            case 2: {
                return STATUS;
            }
            case 4: {
                return NOTIFY;
            }
            case 5: {
                return UPDATE;
            }
        }
        return new DnsOpCode(n);
    }

    private DnsOpCode(int n) {
        this(n, "UNKNOWN");
    }

    public DnsOpCode(int n, String string) {
        this.byteValue = (byte)n;
        this.name = ObjectUtil.checkNotNull(string, "name");
    }

    public byte byteValue() {
        return this.byteValue;
    }

    public int hashCode() {
        return this.byteValue;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof DnsOpCode)) {
            return true;
        }
        return this.byteValue == ((DnsOpCode)object).byteValue;
    }

    @Override
    public int compareTo(DnsOpCode dnsOpCode) {
        return this.byteValue - dnsOpCode.byteValue;
    }

    public String toString() {
        String string = this.text;
        if (string == null) {
            this.text = string = this.name + '(' + (this.byteValue & 0xFF) + ')';
        }
        return string;
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((DnsOpCode)object);
    }
}

