/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.conn.util;

import java.net.IDN;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.conn.util.DnsUtils;
import org.apache.http.conn.util.DomainType;
import org.apache.http.conn.util.PublicSuffixList;
import org.apache.http.util.Args;

@Contract(threading=ThreadingBehavior.SAFE)
public final class PublicSuffixMatcher {
    private final Map<String, DomainType> rules;
    private final Map<String, DomainType> exceptions;

    public PublicSuffixMatcher(Collection<String> collection, Collection<String> collection2) {
        this(DomainType.UNKNOWN, collection, collection2);
    }

    public PublicSuffixMatcher(DomainType domainType, Collection<String> collection, Collection<String> collection2) {
        Args.notNull(domainType, "Domain type");
        Args.notNull(collection, "Domain suffix rules");
        this.rules = new ConcurrentHashMap<String, DomainType>(collection.size());
        for (String string : collection) {
            this.rules.put(string, domainType);
        }
        this.exceptions = new ConcurrentHashMap<String, DomainType>();
        if (collection2 != null) {
            for (String string : collection2) {
                this.exceptions.put(string, domainType);
            }
        }
    }

    public PublicSuffixMatcher(Collection<PublicSuffixList> collection) {
        Args.notNull(collection, "Domain suffix lists");
        this.rules = new ConcurrentHashMap<String, DomainType>();
        this.exceptions = new ConcurrentHashMap<String, DomainType>();
        for (PublicSuffixList publicSuffixList : collection) {
            DomainType domainType = publicSuffixList.getType();
            List<String> list = publicSuffixList.getRules();
            for (String string : list) {
                this.rules.put(string, domainType);
            }
            List<String> list2 = publicSuffixList.getExceptions();
            if (list2 == null) continue;
            for (String string : list2) {
                this.exceptions.put(string, domainType);
            }
        }
    }

    private static DomainType findEntry(Map<String, DomainType> map, String string) {
        if (map == null) {
            return null;
        }
        return map.get(string);
    }

    private static boolean match(DomainType domainType, DomainType domainType2) {
        return domainType != null && (domainType2 == null || domainType.equals((Object)domainType2));
    }

    public String getDomainRoot(String string) {
        return this.getDomainRoot(string, null);
    }

    public String getDomainRoot(String string, DomainType domainType) {
        String string2;
        if (string == null) {
            return null;
        }
        if (string.startsWith(".")) {
            return null;
        }
        String string3 = string2 = DnsUtils.normalize(string);
        String string4 = null;
        while (string3 != null) {
            DomainType domainType2;
            String string5;
            String string6 = IDN.toUnicode(string3);
            DomainType domainType3 = PublicSuffixMatcher.findEntry(this.exceptions, string6);
            if (PublicSuffixMatcher.match(domainType3, domainType)) {
                return string3;
            }
            DomainType domainType4 = PublicSuffixMatcher.findEntry(this.rules, string6);
            if (PublicSuffixMatcher.match(domainType4, domainType)) {
                if (domainType4 == DomainType.PRIVATE) {
                    return string3;
                }
                return string4;
            }
            int n = string3.indexOf(46);
            String string7 = string5 = n != -1 ? string3.substring(n + 1) : null;
            if (string5 != null && PublicSuffixMatcher.match(domainType2 = PublicSuffixMatcher.findEntry(this.rules, "*." + IDN.toUnicode(string5)), domainType)) {
                if (domainType2 == DomainType.PRIVATE) {
                    return string3;
                }
                return string4;
            }
            string4 = string3;
            string3 = string5;
        }
        if (domainType == null || domainType == DomainType.UNKNOWN) {
            return string4;
        }
        return null;
    }

    public boolean matches(String string) {
        return this.matches(string, null);
    }

    public boolean matches(String string, DomainType domainType) {
        if (string == null) {
            return true;
        }
        String string2 = this.getDomainRoot(string.startsWith(".") ? string.substring(1) : string, domainType);
        return string2 == null;
    }
}

