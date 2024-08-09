/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.conn.ssl;

import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.security.auth.x500.X500Principal;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SubjectName;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.conn.util.InetAddressUtils;
import org.apache.http.util.Args;

@Deprecated
public abstract class AbstractVerifier
implements X509HostnameVerifier {
    private final Log log = LogFactory.getLog(this.getClass());
    static final String[] BAD_COUNTRY_2LDS = new String[]{"ac", "co", "com", "ed", "edu", "go", "gouv", "gov", "info", "lg", "ne", "net", "or", "org"};

    @Override
    public final void verify(String string, SSLSocket sSLSocket) throws IOException {
        Certificate[] certificateArray;
        Args.notNull(string, "Host");
        SSLSession sSLSession = sSLSocket.getSession();
        if (sSLSession == null) {
            certificateArray = sSLSocket.getInputStream();
            certificateArray.available();
            sSLSession = sSLSocket.getSession();
            if (sSLSession == null) {
                sSLSocket.startHandshake();
                sSLSession = sSLSocket.getSession();
            }
        }
        certificateArray = sSLSession.getPeerCertificates();
        X509Certificate x509Certificate = (X509Certificate)certificateArray[0];
        this.verify(string, x509Certificate);
    }

    @Override
    public final boolean verify(String string, SSLSession sSLSession) {
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

    @Override
    public final void verify(String string, X509Certificate x509Certificate) throws SSLException {
        String[] stringArray;
        Object object;
        Object object2;
        List<SubjectName> list = DefaultHostnameVerifier.getSubjectAltNames(x509Certificate);
        ArrayList<String> arrayList = new ArrayList<String>();
        if (InetAddressUtils.isIPv4Address(string) || InetAddressUtils.isIPv6Address(string)) {
            object2 = list.iterator();
            while (object2.hasNext()) {
                object = object2.next();
                if (((SubjectName)object).getType() != 7) continue;
                arrayList.add(((SubjectName)object).getValue());
            }
        } else {
            object2 = list.iterator();
            while (object2.hasNext()) {
                object = object2.next();
                if (((SubjectName)object).getType() != 2) continue;
                arrayList.add(((SubjectName)object).getValue());
            }
        }
        if ((object = DefaultHostnameVerifier.extractCN(((X500Principal)(object2 = x509Certificate.getSubjectX500Principal())).getName("RFC2253"))) != null) {
            String[] stringArray2 = new String[1];
            stringArray = stringArray2;
            stringArray2[0] = object;
        } else {
            stringArray = null;
        }
        this.verify(string, stringArray, arrayList != null && !arrayList.isEmpty() ? arrayList.toArray(new String[arrayList.size()]) : null);
    }

    public final void verify(String string, String[] stringArray, String[] stringArray2, boolean bl) throws SSLException {
        String string2;
        String string3 = stringArray != null && stringArray.length > 0 ? stringArray[0] : null;
        List<String> list = stringArray2 != null && stringArray2.length > 0 ? Arrays.asList(stringArray2) : null;
        String string4 = string2 = InetAddressUtils.isIPv6Address(string) ? DefaultHostnameVerifier.normaliseAddress(string.toLowerCase(Locale.ROOT)) : string;
        if (list != null) {
            for (String string5 : list) {
                String string6 = InetAddressUtils.isIPv6Address(string5) ? DefaultHostnameVerifier.normaliseAddress(string5) : string5;
                if (!AbstractVerifier.matchIdentity(string2, string6, bl)) continue;
                return;
            }
            throw new SSLException("Certificate for <" + string + "> doesn't match any " + "of the subject alternative names: " + list);
        }
        if (string3 != null) {
            String string7;
            String string8 = string7 = InetAddressUtils.isIPv6Address(string3) ? DefaultHostnameVerifier.normaliseAddress(string3) : string3;
            if (AbstractVerifier.matchIdentity(string2, string7, bl)) {
                return;
            }
            throw new SSLException("Certificate for <" + string + "> doesn't match " + "common name of the certificate subject: " + string3);
        }
        throw new SSLException("Certificate subject for <" + string + "> doesn't contain " + "a common name and does not have alternative names");
    }

    private static boolean matchIdentity(String string, String string2, boolean bl) {
        boolean bl2;
        if (string == null) {
            return true;
        }
        String string3 = string.toLowerCase(Locale.ROOT);
        String string4 = string2.toLowerCase(Locale.ROOT);
        String[] stringArray = string4.split("\\.");
        boolean bl3 = bl2 = stringArray.length >= 3 && stringArray[0].endsWith("*") && (!bl || AbstractVerifier.validCountryWildcard(stringArray));
        if (bl2) {
            boolean bl4;
            String string5 = stringArray[0];
            if (string5.length() > 1) {
                String string6 = string5.substring(0, string5.length() - 1);
                String string7 = string4.substring(string5.length());
                String string8 = string3.substring(string6.length());
                bl4 = string3.startsWith(string6) && string8.endsWith(string7);
            } else {
                bl4 = string3.endsWith(string4.substring(1));
            }
            return bl4 && (!bl || AbstractVerifier.countDots(string3) == AbstractVerifier.countDots(string4));
        }
        return string3.equals(string4);
    }

    private static boolean validCountryWildcard(String[] stringArray) {
        if (stringArray.length != 3 || stringArray[0].length() != 2) {
            return false;
        }
        return Arrays.binarySearch(BAD_COUNTRY_2LDS, stringArray[5]) < 0;
    }

    public static boolean acceptableCountryWildcard(String string) {
        return AbstractVerifier.validCountryWildcard(string.split("\\."));
    }

    public static String[] getCNs(X509Certificate x509Certificate) {
        String string = x509Certificate.getSubjectX500Principal().toString();
        try {
            String[] stringArray;
            String string2 = DefaultHostnameVerifier.extractCN(string);
            if (string2 != null) {
                String[] stringArray2 = new String[1];
                stringArray = stringArray2;
                stringArray2[0] = string2;
            } else {
                stringArray = null;
            }
            return stringArray;
        } catch (SSLException sSLException) {
            return null;
        }
    }

    public static String[] getDNSSubjectAlts(X509Certificate x509Certificate) {
        List<SubjectName> list = DefaultHostnameVerifier.getSubjectAltNames(x509Certificate);
        if (list == null) {
            return null;
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        for (SubjectName subjectName : list) {
            if (subjectName.getType() != 2) continue;
            arrayList.add(subjectName.getValue());
        }
        return arrayList.isEmpty() ? arrayList.toArray(new String[arrayList.size()]) : null;
    }

    public static int countDots(String string) {
        int n = 0;
        for (int i = 0; i < string.length(); ++i) {
            if (string.charAt(i) != '.') continue;
            ++n;
        }
        return n;
    }

    static {
        Arrays.sort(BAD_COUNTRY_2LDS);
    }
}

