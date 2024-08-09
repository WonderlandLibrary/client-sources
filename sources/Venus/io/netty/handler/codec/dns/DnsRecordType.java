/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.dns;

import io.netty.util.collection.IntObjectHashMap;
import java.util.HashMap;
import java.util.Map;

public class DnsRecordType
implements Comparable<DnsRecordType> {
    public static final DnsRecordType A = new DnsRecordType(1, "A");
    public static final DnsRecordType NS = new DnsRecordType(2, "NS");
    public static final DnsRecordType CNAME = new DnsRecordType(5, "CNAME");
    public static final DnsRecordType SOA = new DnsRecordType(6, "SOA");
    public static final DnsRecordType PTR = new DnsRecordType(12, "PTR");
    public static final DnsRecordType MX = new DnsRecordType(15, "MX");
    public static final DnsRecordType TXT = new DnsRecordType(16, "TXT");
    public static final DnsRecordType RP = new DnsRecordType(17, "RP");
    public static final DnsRecordType AFSDB = new DnsRecordType(18, "AFSDB");
    public static final DnsRecordType SIG = new DnsRecordType(24, "SIG");
    public static final DnsRecordType KEY = new DnsRecordType(25, "KEY");
    public static final DnsRecordType AAAA = new DnsRecordType(28, "AAAA");
    public static final DnsRecordType LOC = new DnsRecordType(29, "LOC");
    public static final DnsRecordType SRV = new DnsRecordType(33, "SRV");
    public static final DnsRecordType NAPTR = new DnsRecordType(35, "NAPTR");
    public static final DnsRecordType KX = new DnsRecordType(36, "KX");
    public static final DnsRecordType CERT = new DnsRecordType(37, "CERT");
    public static final DnsRecordType DNAME = new DnsRecordType(39, "DNAME");
    public static final DnsRecordType OPT = new DnsRecordType(41, "OPT");
    public static final DnsRecordType APL = new DnsRecordType(42, "APL");
    public static final DnsRecordType DS = new DnsRecordType(43, "DS");
    public static final DnsRecordType SSHFP = new DnsRecordType(44, "SSHFP");
    public static final DnsRecordType IPSECKEY = new DnsRecordType(45, "IPSECKEY");
    public static final DnsRecordType RRSIG = new DnsRecordType(46, "RRSIG");
    public static final DnsRecordType NSEC = new DnsRecordType(47, "NSEC");
    public static final DnsRecordType DNSKEY = new DnsRecordType(48, "DNSKEY");
    public static final DnsRecordType DHCID = new DnsRecordType(49, "DHCID");
    public static final DnsRecordType NSEC3 = new DnsRecordType(50, "NSEC3");
    public static final DnsRecordType NSEC3PARAM = new DnsRecordType(51, "NSEC3PARAM");
    public static final DnsRecordType TLSA = new DnsRecordType(52, "TLSA");
    public static final DnsRecordType HIP = new DnsRecordType(55, "HIP");
    public static final DnsRecordType SPF = new DnsRecordType(99, "SPF");
    public static final DnsRecordType TKEY = new DnsRecordType(249, "TKEY");
    public static final DnsRecordType TSIG = new DnsRecordType(250, "TSIG");
    public static final DnsRecordType IXFR = new DnsRecordType(251, "IXFR");
    public static final DnsRecordType AXFR = new DnsRecordType(252, "AXFR");
    public static final DnsRecordType ANY = new DnsRecordType(255, "ANY");
    public static final DnsRecordType CAA = new DnsRecordType(257, "CAA");
    public static final DnsRecordType TA = new DnsRecordType(32768, "TA");
    public static final DnsRecordType DLV = new DnsRecordType(32769, "DLV");
    private static final Map<String, DnsRecordType> BY_NAME = new HashMap<String, DnsRecordType>();
    private static final IntObjectHashMap<DnsRecordType> BY_TYPE = new IntObjectHashMap();
    private static final String EXPECTED;
    private final int intValue;
    private final String name;
    private String text;

    public static DnsRecordType valueOf(int n) {
        DnsRecordType dnsRecordType = BY_TYPE.get(n);
        if (dnsRecordType == null) {
            return new DnsRecordType(n);
        }
        return dnsRecordType;
    }

    public static DnsRecordType valueOf(String string) {
        DnsRecordType dnsRecordType = BY_NAME.get(string);
        if (dnsRecordType == null) {
            throw new IllegalArgumentException("name: " + string + EXPECTED);
        }
        return dnsRecordType;
    }

    private DnsRecordType(int n) {
        this(n, "UNKNOWN");
    }

    public DnsRecordType(int n, String string) {
        if ((n & 0xFFFF) != n) {
            throw new IllegalArgumentException("intValue: " + n + " (expected: 0 ~ 65535)");
        }
        this.intValue = n;
        this.name = string;
    }

    public String name() {
        return this.name;
    }

    public int intValue() {
        return this.intValue;
    }

    public int hashCode() {
        return this.intValue;
    }

    public boolean equals(Object object) {
        return object instanceof DnsRecordType && ((DnsRecordType)object).intValue == this.intValue;
    }

    @Override
    public int compareTo(DnsRecordType dnsRecordType) {
        return this.intValue() - dnsRecordType.intValue();
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
        return this.compareTo((DnsRecordType)object);
    }

    static {
        DnsRecordType[] dnsRecordTypeArray = new DnsRecordType[]{A, NS, CNAME, SOA, PTR, MX, TXT, RP, AFSDB, SIG, KEY, AAAA, LOC, SRV, NAPTR, KX, CERT, DNAME, OPT, APL, DS, SSHFP, IPSECKEY, RRSIG, NSEC, DNSKEY, DHCID, NSEC3, NSEC3PARAM, TLSA, HIP, SPF, TKEY, TSIG, IXFR, AXFR, ANY, CAA, TA, DLV};
        StringBuilder stringBuilder = new StringBuilder(512);
        stringBuilder.append(" (expected: ");
        for (DnsRecordType dnsRecordType : dnsRecordTypeArray) {
            BY_NAME.put(dnsRecordType.name(), dnsRecordType);
            BY_TYPE.put(dnsRecordType.intValue(), dnsRecordType);
            stringBuilder.append(dnsRecordType.name()).append('(').append(dnsRecordType.intValue()).append("), ");
        }
        stringBuilder.setLength(stringBuilder.length() - 2);
        stringBuilder.append(')');
        EXPECTED = stringBuilder.toString();
    }
}

