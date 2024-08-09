/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.dns;

import io.netty.channel.AddressedEnvelope;
import io.netty.handler.codec.dns.DnsMessage;
import io.netty.handler.codec.dns.DnsQuery;
import io.netty.handler.codec.dns.DnsResponse;
import io.netty.handler.codec.dns.DnsSection;
import io.netty.util.internal.StringUtil;

final class DnsMessageUtil {
    static StringBuilder appendQuery(StringBuilder stringBuilder, DnsQuery dnsQuery) {
        DnsMessageUtil.appendQueryHeader(stringBuilder, dnsQuery);
        DnsMessageUtil.appendAllRecords(stringBuilder, dnsQuery);
        return stringBuilder;
    }

    static StringBuilder appendResponse(StringBuilder stringBuilder, DnsResponse dnsResponse) {
        DnsMessageUtil.appendResponseHeader(stringBuilder, dnsResponse);
        DnsMessageUtil.appendAllRecords(stringBuilder, dnsResponse);
        return stringBuilder;
    }

    static StringBuilder appendRecordClass(StringBuilder stringBuilder, int n) {
        String string;
        switch (n &= 0xFFFF) {
            case 1: {
                string = "IN";
                break;
            }
            case 2: {
                string = "CSNET";
                break;
            }
            case 3: {
                string = "CHAOS";
                break;
            }
            case 4: {
                string = "HESIOD";
                break;
            }
            case 254: {
                string = "NONE";
                break;
            }
            case 255: {
                string = "ANY";
                break;
            }
            default: {
                string = null;
            }
        }
        if (string != null) {
            stringBuilder.append(string);
        } else {
            stringBuilder.append("UNKNOWN(").append(n).append(')');
        }
        return stringBuilder;
    }

    private static void appendQueryHeader(StringBuilder stringBuilder, DnsQuery dnsQuery) {
        stringBuilder.append(StringUtil.simpleClassName(dnsQuery)).append('(');
        DnsMessageUtil.appendAddresses(stringBuilder, dnsQuery).append(dnsQuery.id()).append(", ").append(dnsQuery.opCode());
        if (dnsQuery.isRecursionDesired()) {
            stringBuilder.append(", RD");
        }
        if (dnsQuery.z() != 0) {
            stringBuilder.append(", Z: ").append(dnsQuery.z());
        }
        stringBuilder.append(')');
    }

    private static void appendResponseHeader(StringBuilder stringBuilder, DnsResponse dnsResponse) {
        stringBuilder.append(StringUtil.simpleClassName(dnsResponse)).append('(');
        DnsMessageUtil.appendAddresses(stringBuilder, dnsResponse).append(dnsResponse.id()).append(", ").append(dnsResponse.opCode()).append(", ").append(dnsResponse.code()).append(',');
        boolean bl = true;
        if (dnsResponse.isRecursionDesired()) {
            bl = false;
            stringBuilder.append(" RD");
        }
        if (dnsResponse.isAuthoritativeAnswer()) {
            bl = false;
            stringBuilder.append(" AA");
        }
        if (dnsResponse.isTruncated()) {
            bl = false;
            stringBuilder.append(" TC");
        }
        if (dnsResponse.isRecursionAvailable()) {
            bl = false;
            stringBuilder.append(" RA");
        }
        if (dnsResponse.z() != 0) {
            if (!bl) {
                stringBuilder.append(',');
            }
            stringBuilder.append(" Z: ").append(dnsResponse.z());
        }
        if (bl) {
            stringBuilder.setCharAt(stringBuilder.length() - 1, ')');
        } else {
            stringBuilder.append(')');
        }
    }

    private static StringBuilder appendAddresses(StringBuilder stringBuilder, DnsMessage dnsMessage) {
        if (!(dnsMessage instanceof AddressedEnvelope)) {
            return stringBuilder;
        }
        AddressedEnvelope addressedEnvelope = (AddressedEnvelope)((Object)dnsMessage);
        Object a = addressedEnvelope.sender();
        if (a != null) {
            stringBuilder.append("from: ").append(a).append(", ");
        }
        if ((a = addressedEnvelope.recipient()) != null) {
            stringBuilder.append("to: ").append(a).append(", ");
        }
        return stringBuilder;
    }

    private static void appendAllRecords(StringBuilder stringBuilder, DnsMessage dnsMessage) {
        DnsMessageUtil.appendRecords(stringBuilder, dnsMessage, DnsSection.QUESTION);
        DnsMessageUtil.appendRecords(stringBuilder, dnsMessage, DnsSection.ANSWER);
        DnsMessageUtil.appendRecords(stringBuilder, dnsMessage, DnsSection.AUTHORITY);
        DnsMessageUtil.appendRecords(stringBuilder, dnsMessage, DnsSection.ADDITIONAL);
    }

    private static void appendRecords(StringBuilder stringBuilder, DnsMessage dnsMessage, DnsSection dnsSection) {
        int n = dnsMessage.count(dnsSection);
        for (int i = 0; i < n; ++i) {
            stringBuilder.append(StringUtil.NEWLINE).append('\t').append(dnsMessage.recordAt(dnsSection, i));
        }
    }

    private DnsMessageUtil() {
    }
}

