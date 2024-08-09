/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class CipherSuiteConverter {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(CipherSuiteConverter.class);
    private static final Pattern JAVA_CIPHERSUITE_PATTERN = Pattern.compile("^(?:TLS|SSL)_((?:(?!_WITH_).)+)_WITH_(.*)_(.*)$");
    private static final Pattern OPENSSL_CIPHERSUITE_PATTERN = Pattern.compile("^(?:((?:(?:EXP-)?(?:(?:DHE|EDH|ECDH|ECDHE|SRP|RSA)-(?:DSS|RSA|ECDSA|PSK)|(?:ADH|AECDH|KRB5|PSK|SRP)))|EXP)-)?(.*)-(.*)$");
    private static final Pattern JAVA_AES_CBC_PATTERN = Pattern.compile("^(AES)_([0-9]+)_CBC$");
    private static final Pattern JAVA_AES_PATTERN = Pattern.compile("^(AES)_([0-9]+)_(.*)$");
    private static final Pattern OPENSSL_AES_CBC_PATTERN = Pattern.compile("^(AES)([0-9]+)$");
    private static final Pattern OPENSSL_AES_PATTERN = Pattern.compile("^(AES)([0-9]+)-(.*)$");
    private static final ConcurrentMap<String, String> j2o = PlatformDependent.newConcurrentHashMap();
    private static final ConcurrentMap<String, Map<String, String>> o2j = PlatformDependent.newConcurrentHashMap();

    static void clearCache() {
        j2o.clear();
        o2j.clear();
    }

    static boolean isJ2OCached(String string, String string2) {
        return string2.equals(j2o.get(string));
    }

    static boolean isO2JCached(String string, String string2, String string3) {
        Map map = (Map)o2j.get(string);
        if (map == null) {
            return true;
        }
        return string3.equals(map.get(string2));
    }

    static String toOpenSsl(Iterable<String> iterable) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : iterable) {
            if (string == null) break;
            String string2 = CipherSuiteConverter.toOpenSsl(string);
            if (string2 != null) {
                string = string2;
            }
            stringBuilder.append(string);
            stringBuilder.append(':');
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.setLength(stringBuilder.length() - 1);
            return stringBuilder.toString();
        }
        return "";
    }

    static String toOpenSsl(String string) {
        String string2 = (String)j2o.get(string);
        if (string2 != null) {
            return string2;
        }
        return CipherSuiteConverter.cacheFromJava(string);
    }

    private static String cacheFromJava(String string) {
        String string2 = CipherSuiteConverter.toOpenSslUncached(string);
        if (string2 == null) {
            return null;
        }
        j2o.putIfAbsent(string, string2);
        String string3 = string.substring(4);
        HashMap<String, String> hashMap = new HashMap<String, String>(4);
        hashMap.put("", string3);
        hashMap.put("SSL", "SSL_" + string3);
        hashMap.put("TLS", "TLS_" + string3);
        o2j.put(string2, hashMap);
        logger.debug("Cipher suite mapping: {} => {}", (Object)string, (Object)string2);
        return string2;
    }

    static String toOpenSslUncached(String string) {
        Matcher matcher = JAVA_CIPHERSUITE_PATTERN.matcher(string);
        if (!matcher.matches()) {
            return null;
        }
        String string2 = CipherSuiteConverter.toOpenSslHandshakeAlgo(matcher.group(1));
        String string3 = CipherSuiteConverter.toOpenSslBulkCipher(matcher.group(2));
        String string4 = CipherSuiteConverter.toOpenSslHmacAlgo(matcher.group(3));
        if (string2.isEmpty()) {
            return string3 + '-' + string4;
        }
        if (string3.contains("CHACHA20")) {
            return string2 + '-' + string3;
        }
        return string2 + '-' + string3 + '-' + string4;
    }

    private static String toOpenSslHandshakeAlgo(String string) {
        boolean bl = string.endsWith("_EXPORT");
        if (bl) {
            string = string.substring(0, string.length() - 7);
        }
        if ("RSA".equals(string)) {
            string = "";
        } else if (string.endsWith("_anon")) {
            string = 'A' + string.substring(0, string.length() - 5);
        }
        if (bl) {
            string = string.isEmpty() ? "EXP" : "EXP-" + string;
        }
        return string.replace('_', '-');
    }

    private static String toOpenSslBulkCipher(String string) {
        if (string.startsWith("AES_")) {
            Matcher matcher = JAVA_AES_CBC_PATTERN.matcher(string);
            if (matcher.matches()) {
                return matcher.replaceFirst("$1$2");
            }
            matcher = JAVA_AES_PATTERN.matcher(string);
            if (matcher.matches()) {
                return matcher.replaceFirst("$1$2-$3");
            }
        }
        if ("3DES_EDE_CBC".equals(string)) {
            return "DES-CBC3";
        }
        if ("RC4_128".equals(string) || "RC4_40".equals(string)) {
            return "RC4";
        }
        if ("DES40_CBC".equals(string) || "DES_CBC_40".equals(string)) {
            return "DES-CBC";
        }
        if ("RC2_CBC_40".equals(string)) {
            return "RC2-CBC";
        }
        return string.replace('_', '-');
    }

    private static String toOpenSslHmacAlgo(String string) {
        return string;
    }

    static String toJava(String string, String string2) {
        Map<String, String> map = (Map<String, String>)o2j.get(string);
        if (map == null && (map = CipherSuiteConverter.cacheFromOpenSsl(string)) == null) {
            return null;
        }
        String string3 = map.get(string2);
        if (string3 == null) {
            string3 = string2 + '_' + map.get("");
        }
        return string3;
    }

    private static Map<String, String> cacheFromOpenSsl(String string) {
        String string2 = CipherSuiteConverter.toJavaUncached(string);
        if (string2 == null) {
            return null;
        }
        String string3 = "SSL_" + string2;
        String string4 = "TLS_" + string2;
        HashMap<String, String> hashMap = new HashMap<String, String>(4);
        hashMap.put("", string2);
        hashMap.put("SSL", string3);
        hashMap.put("TLS", string4);
        o2j.putIfAbsent(string, hashMap);
        j2o.putIfAbsent(string4, string);
        j2o.putIfAbsent(string3, string);
        logger.debug("Cipher suite mapping: {} => {}", (Object)string4, (Object)string);
        logger.debug("Cipher suite mapping: {} => {}", (Object)string3, (Object)string);
        return hashMap;
    }

    static String toJavaUncached(String string) {
        boolean bl;
        Matcher matcher = OPENSSL_CIPHERSUITE_PATTERN.matcher(string);
        if (!matcher.matches()) {
            return null;
        }
        String string2 = matcher.group(1);
        if (string2 == null) {
            string2 = "";
            bl = false;
        } else if (string2.startsWith("EXP-")) {
            string2 = string2.substring(4);
            bl = true;
        } else if ("EXP".equals(string2)) {
            string2 = "";
            bl = true;
        } else {
            bl = false;
        }
        string2 = CipherSuiteConverter.toJavaHandshakeAlgo(string2, bl);
        String string3 = CipherSuiteConverter.toJavaBulkCipher(matcher.group(2), bl);
        String string4 = CipherSuiteConverter.toJavaHmacAlgo(matcher.group(3));
        String string5 = string2 + "_WITH_" + string3 + '_' + string4;
        return string3.contains("CHACHA20") ? string5 + "_SHA256" : string5;
    }

    private static String toJavaHandshakeAlgo(String string, boolean bl) {
        if (string.isEmpty()) {
            string = "RSA";
        } else if ("ADH".equals(string)) {
            string = "DH_anon";
        } else if ("AECDH".equals(string)) {
            string = "ECDH_anon";
        }
        string = string.replace('-', '_');
        if (bl) {
            return string + "_EXPORT";
        }
        return string;
    }

    private static String toJavaBulkCipher(String string, boolean bl) {
        if (string.startsWith("AES")) {
            Matcher matcher = OPENSSL_AES_CBC_PATTERN.matcher(string);
            if (matcher.matches()) {
                return matcher.replaceFirst("$1_$2_CBC");
            }
            matcher = OPENSSL_AES_PATTERN.matcher(string);
            if (matcher.matches()) {
                return matcher.replaceFirst("$1_$2_$3");
            }
        }
        if ("DES-CBC3".equals(string)) {
            return "3DES_EDE_CBC";
        }
        if ("RC4".equals(string)) {
            if (bl) {
                return "RC4_40";
            }
            return "RC4_128";
        }
        if ("DES-CBC".equals(string)) {
            if (bl) {
                return "DES_CBC_40";
            }
            return "DES_CBC";
        }
        if ("RC2-CBC".equals(string)) {
            if (bl) {
                return "RC2_CBC_40";
            }
            return "RC2_CBC";
        }
        return string.replace('-', '_');
    }

    private static String toJavaHmacAlgo(String string) {
        return string;
    }

    private CipherSuiteConverter() {
    }
}

