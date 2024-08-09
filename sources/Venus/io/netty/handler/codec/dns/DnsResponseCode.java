/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.dns;

import io.netty.util.internal.ObjectUtil;

public class DnsResponseCode
implements Comparable<DnsResponseCode> {
    public static final DnsResponseCode NOERROR = new DnsResponseCode(0, "NoError");
    public static final DnsResponseCode FORMERR = new DnsResponseCode(1, "FormErr");
    public static final DnsResponseCode SERVFAIL = new DnsResponseCode(2, "ServFail");
    public static final DnsResponseCode NXDOMAIN = new DnsResponseCode(3, "NXDomain");
    public static final DnsResponseCode NOTIMP = new DnsResponseCode(4, "NotImp");
    public static final DnsResponseCode REFUSED = new DnsResponseCode(5, "Refused");
    public static final DnsResponseCode YXDOMAIN = new DnsResponseCode(6, "YXDomain");
    public static final DnsResponseCode YXRRSET = new DnsResponseCode(7, "YXRRSet");
    public static final DnsResponseCode NXRRSET = new DnsResponseCode(8, "NXRRSet");
    public static final DnsResponseCode NOTAUTH = new DnsResponseCode(9, "NotAuth");
    public static final DnsResponseCode NOTZONE = new DnsResponseCode(10, "NotZone");
    public static final DnsResponseCode BADVERS_OR_BADSIG = new DnsResponseCode(16, "BADVERS_OR_BADSIG");
    public static final DnsResponseCode BADKEY = new DnsResponseCode(17, "BADKEY");
    public static final DnsResponseCode BADTIME = new DnsResponseCode(18, "BADTIME");
    public static final DnsResponseCode BADMODE = new DnsResponseCode(19, "BADMODE");
    public static final DnsResponseCode BADNAME = new DnsResponseCode(20, "BADNAME");
    public static final DnsResponseCode BADALG = new DnsResponseCode(21, "BADALG");
    private final int code;
    private final String name;
    private String text;

    public static DnsResponseCode valueOf(int n) {
        switch (n) {
            case 0: {
                return NOERROR;
            }
            case 1: {
                return FORMERR;
            }
            case 2: {
                return SERVFAIL;
            }
            case 3: {
                return NXDOMAIN;
            }
            case 4: {
                return NOTIMP;
            }
            case 5: {
                return REFUSED;
            }
            case 6: {
                return YXDOMAIN;
            }
            case 7: {
                return YXRRSET;
            }
            case 8: {
                return NXRRSET;
            }
            case 9: {
                return NOTAUTH;
            }
            case 10: {
                return NOTZONE;
            }
            case 16: {
                return BADVERS_OR_BADSIG;
            }
            case 17: {
                return BADKEY;
            }
            case 18: {
                return BADTIME;
            }
            case 19: {
                return BADMODE;
            }
            case 20: {
                return BADNAME;
            }
            case 21: {
                return BADALG;
            }
        }
        return new DnsResponseCode(n);
    }

    private DnsResponseCode(int n) {
        this(n, "UNKNOWN");
    }

    public DnsResponseCode(int n, String string) {
        if (n < 0 || n > 65535) {
            throw new IllegalArgumentException("code: " + n + " (expected: 0 ~ 65535)");
        }
        this.code = n;
        this.name = ObjectUtil.checkNotNull(string, "name");
    }

    public int intValue() {
        return this.code;
    }

    @Override
    public int compareTo(DnsResponseCode dnsResponseCode) {
        return this.intValue() - dnsResponseCode.intValue();
    }

    public int hashCode() {
        return this.intValue();
    }

    public boolean equals(Object object) {
        if (!(object instanceof DnsResponseCode)) {
            return true;
        }
        return this.intValue() == ((DnsResponseCode)object).intValue();
    }

    public String toString() {
        String string = this.text;
        if (string == null) {
            this.text = string = this.name + '(' + this.intValue() + ')';
        }
        return string;
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((DnsResponseCode)object);
    }
}

