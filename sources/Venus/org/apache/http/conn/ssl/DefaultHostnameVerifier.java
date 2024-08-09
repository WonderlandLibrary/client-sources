/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.conn.ssl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import javax.naming.InvalidNameException;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.security.auth.x500.X500Principal;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.conn.ssl.SubjectName;
import org.apache.http.conn.util.DnsUtils;
import org.apache.http.conn.util.DomainType;
import org.apache.http.conn.util.InetAddressUtils;
import org.apache.http.conn.util.PublicSuffixMatcher;

@Contract(threading=ThreadingBehavior.IMMUTABLE_CONDITIONAL)
public final class DefaultHostnameVerifier
implements HostnameVerifier {
    private final Log log = LogFactory.getLog(this.getClass());
    private final PublicSuffixMatcher publicSuffixMatcher;

    public DefaultHostnameVerifier(PublicSuffixMatcher publicSuffixMatcher) {
        this.publicSuffixMatcher = publicSuffixMatcher;
    }

    public DefaultHostnameVerifier() {
        this(null);
    }

    @Override
    public boolean verify(String string, SSLSession sSLSession) {
        try {
            Certificate[] certificateArray = sSLSession.getPeerCertificates();
            X509Certificate x509Certificate = (X509Certificate)certificateArray[0];
            this.verify(string, x509Certificate);
            return true;
        } catch (SSLException sSLException) {
            if (this.log.isDebugEnabled()) {
                this.log.debug(sSLException.getMessage(), sSLException);
            }
            return true;
        }
    }

    public void verify(String string, X509Certificate x509Certificate) throws SSLException {
        HostNameType hostNameType = DefaultHostnameVerifier.determineHostFormat(string);
        List<SubjectName> list = DefaultHostnameVerifier.getSubjectAltNames(x509Certificate);
        if (list != null && !list.isEmpty()) {
            switch (1.$SwitchMap$org$apache$http$conn$ssl$DefaultHostnameVerifier$HostNameType[hostNameType.ordinal()]) {
                case 1: {
                    DefaultHostnameVerifier.matchIPAddress(string, list);
                    break;
                }
                case 2: {
                    DefaultHostnameVerifier.matchIPv6Address(string, list);
                    break;
                }
                default: {
                    DefaultHostnameVerifier.matchDNSName(string, list, this.publicSuffixMatcher);
                    break;
                }
            }
        } else {
            X500Principal x500Principal = x509Certificate.getSubjectX500Principal();
            String string2 = DefaultHostnameVerifier.extractCN(x500Principal.getName("RFC2253"));
            if (string2 == null) {
                throw new SSLException("Certificate subject for <" + string + "> doesn't contain " + "a common name and does not have alternative names");
            }
            DefaultHostnameVerifier.matchCN(string, string2, this.publicSuffixMatcher);
        }
    }

    static void matchIPAddress(String string, List<SubjectName> list) throws SSLException {
        for (int i = 0; i < list.size(); ++i) {
            SubjectName subjectName = list.get(i);
            if (subjectName.getType() != 7 || !string.equals(subjectName.getValue())) continue;
            return;
        }
        throw new SSLPeerUnverifiedException("Certificate for <" + string + "> doesn't match any " + "of the subject alternative names: " + list);
    }

    static void matchIPv6Address(String string, List<SubjectName> list) throws SSLException {
        String string2 = DefaultHostnameVerifier.normaliseAddress(string);
        for (int i = 0; i < list.size(); ++i) {
            String string3;
            SubjectName subjectName = list.get(i);
            if (subjectName.getType() != 7 || !string2.equals(string3 = DefaultHostnameVerifier.normaliseAddress(subjectName.getValue()))) continue;
            return;
        }
        throw new SSLPeerUnverifiedException("Certificate for <" + string + "> doesn't match any " + "of the subject alternative names: " + list);
    }

    static void matchDNSName(String string, List<SubjectName> list, PublicSuffixMatcher publicSuffixMatcher) throws SSLException {
        String string2 = DnsUtils.normalize(string);
        for (int i = 0; i < list.size(); ++i) {
            String string3;
            SubjectName subjectName = list.get(i);
            if (subjectName.getType() != 2 || !DefaultHostnameVerifier.matchIdentityStrict(string2, string3 = DnsUtils.normalize(subjectName.getValue()), publicSuffixMatcher)) continue;
            return;
        }
        throw new SSLPeerUnverifiedException("Certificate for <" + string + "> doesn't match any " + "of the subject alternative names: " + list);
    }

    static void matchCN(String string, String string2, PublicSuffixMatcher publicSuffixMatcher) throws SSLException {
        String string3;
        String string4 = DnsUtils.normalize(string);
        if (!DefaultHostnameVerifier.matchIdentityStrict(string4, string3 = DnsUtils.normalize(string2), publicSuffixMatcher)) {
            throw new SSLPeerUnverifiedException("Certificate for <" + string + "> doesn't match " + "common name of the certificate subject: " + string2);
        }
    }

    static boolean matchDomainRoot(String string, String string2) {
        if (string2 == null) {
            return true;
        }
        return string.endsWith(string2) && (string.length() == string2.length() || string.charAt(string.length() - string2.length() - 1) == '.');
    }

    private static boolean matchIdentity(String string, String string2, PublicSuffixMatcher publicSuffixMatcher, DomainType domainType, boolean bl) {
        if (publicSuffixMatcher != null && string.contains(".") && !DefaultHostnameVerifier.matchDomainRoot(string, publicSuffixMatcher.getDomainRoot(string2, domainType))) {
            return true;
        }
        int n = string2.indexOf(42);
        if (n != -1) {
            String string3;
            String string4 = string2.substring(0, n);
            String string5 = string2.substring(n + 1);
            if (!string4.isEmpty() && !string.startsWith(string4)) {
                return true;
            }
            if (!string5.isEmpty() && !string.endsWith(string5)) {
                return true;
            }
            return bl && (string3 = string.substring(string4.length(), string.length() - string5.length())).contains(".");
        }
        return string.equalsIgnoreCase(string2);
    }

    static boolean matchIdentity(String string, String string2, PublicSuffixMatcher publicSuffixMatcher) {
        return DefaultHostnameVerifier.matchIdentity(string, string2, publicSuffixMatcher, null, false);
    }

    static boolean matchIdentity(String string, String string2) {
        return DefaultHostnameVerifier.matchIdentity(string, string2, null, null, false);
    }

    static boolean matchIdentityStrict(String string, String string2, PublicSuffixMatcher publicSuffixMatcher) {
        return DefaultHostnameVerifier.matchIdentity(string, string2, publicSuffixMatcher, null, true);
    }

    static boolean matchIdentityStrict(String string, String string2) {
        return DefaultHostnameVerifier.matchIdentity(string, string2, null, null, true);
    }

    static boolean matchIdentity(String string, String string2, PublicSuffixMatcher publicSuffixMatcher, DomainType domainType) {
        return DefaultHostnameVerifier.matchIdentity(string, string2, publicSuffixMatcher, domainType, false);
    }

    static boolean matchIdentityStrict(String string, String string2, PublicSuffixMatcher publicSuffixMatcher, DomainType domainType) {
        return DefaultHostnameVerifier.matchIdentity(string, string2, publicSuffixMatcher, domainType, true);
    }

    static String extractCN(String string) throws SSLException {
        if (string == null) {
            return null;
        }
        try {
            LdapName ldapName = new LdapName(string);
            List<Rdn> list = ldapName.getRdns();
            for (int i = list.size() - 1; i >= 0; --i) {
                Rdn rdn = list.get(i);
                Attributes attributes = rdn.toAttributes();
                Attribute attribute = attributes.get("cn");
                if (attribute == null) continue;
                try {
                    Object object = attribute.get();
                    if (object == null) continue;
                    return object.toString();
                } catch (NoSuchElementException noSuchElementException) {
                    continue;
                } catch (NamingException namingException) {
                    // empty catch block
                }
            }
            return null;
        } catch (InvalidNameException invalidNameException) {
            throw new SSLException(string + " is not a valid X500 distinguished name");
        }
    }

    static HostNameType determineHostFormat(String string) {
        if (InetAddressUtils.isIPv4Address(string)) {
            return HostNameType.IPv4;
        }
        String string2 = string;
        if (string2.startsWith("[") && string2.endsWith("]")) {
            string2 = string.substring(1, string.length() - 1);
        }
        if (InetAddressUtils.isIPv6Address(string2)) {
            return HostNameType.IPv6;
        }
        return HostNameType.DNS;
    }

    static List<SubjectName> getSubjectAltNames(X509Certificate x509Certificate) {
        try {
            Collection<List<?>> collection = x509Certificate.getSubjectAlternativeNames();
            if (collection == null) {
                return Collections.emptyList();
            }
            ArrayList<SubjectName> arrayList = new ArrayList<SubjectName>();
            for (List<?> list : collection) {
                Integer n = list.size() >= 2 ? (Integer)list.get(0) : null;
                if (n == null || n != 2 && n != 7) continue;
                Object obj = list.get(1);
                if (obj instanceof String) {
                    arrayList.add(new SubjectName((String)obj, n));
                    continue;
                }
                if (!(obj instanceof byte[])) continue;
            }
            return arrayList;
        } catch (CertificateParsingException certificateParsingException) {
            return Collections.emptyList();
        }
    }

    static String normaliseAddress(String string) {
        if (string == null) {
            return string;
        }
        try {
            InetAddress inetAddress = InetAddress.getByName(string);
            return inetAddress.getHostAddress();
        } catch (UnknownHostException unknownHostException) {
            return string;
        }
    }

    static class 1 {
        static final int[] $SwitchMap$org$apache$http$conn$ssl$DefaultHostnameVerifier$HostNameType = new int[HostNameType.values().length];

        static {
            try {
                1.$SwitchMap$org$apache$http$conn$ssl$DefaultHostnameVerifier$HostNameType[HostNameType.IPv4.ordinal()] = 1;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                1.$SwitchMap$org$apache$http$conn$ssl$DefaultHostnameVerifier$HostNameType[HostNameType.IPv6.ordinal()] = 2;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
        }
    }

    static enum HostNameType {
        IPv4(7),
        IPv6(7),
        DNS(2);

        final int subjectType;

        private HostNameType(int n2) {
            this.subjectType = n2;
        }
    }
}

