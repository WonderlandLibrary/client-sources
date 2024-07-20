/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.handler.ssl.CipherSuiteConverter;
import io.netty.handler.ssl.OpenSslEngine;
import io.netty.handler.ssl.ReferenceCountedOpenSslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.internal.tcnative.Buffer;
import io.netty.internal.tcnative.Library;
import io.netty.internal.tcnative.SSL;
import io.netty.internal.tcnative.SSLContext;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.NativeLibraryLoader;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

public final class OpenSsl {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(OpenSsl.class);
    private static final String LINUX = "linux";
    private static final String UNKNOWN = "unknown";
    private static final Throwable UNAVAILABILITY_CAUSE;
    static final Set<String> AVAILABLE_CIPHER_SUITES;
    private static final Set<String> AVAILABLE_OPENSSL_CIPHER_SUITES;
    private static final Set<String> AVAILABLE_JAVA_CIPHER_SUITES;
    private static final boolean SUPPORTS_KEYMANAGER_FACTORY;
    private static final boolean SUPPORTS_HOSTNAME_VALIDATION;
    private static final boolean USE_KEYMANAGER_FACTORY;
    static final String PROTOCOL_SSL_V2_HELLO = "SSLv2Hello";
    static final String PROTOCOL_SSL_V2 = "SSLv2";
    static final String PROTOCOL_SSL_V3 = "SSLv3";
    static final String PROTOCOL_TLS_V1 = "TLSv1";
    static final String PROTOCOL_TLS_V1_1 = "TLSv1.1";
    static final String PROTOCOL_TLS_V1_2 = "TLSv1.2";
    static final Set<String> SUPPORTED_PROTOCOLS_SET;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static boolean doesSupportProtocol(int protocol) {
        long sslCtx = -1L;
        try {
            sslCtx = SSLContext.make(protocol, 2);
            boolean bl = true;
            return bl;
        } catch (Exception ignore) {
            boolean bl = false;
            return bl;
        } finally {
            if (sslCtx != -1L) {
                SSLContext.free(sslCtx);
            }
        }
    }

    public static boolean isAvailable() {
        return UNAVAILABILITY_CAUSE == null;
    }

    public static boolean isAlpnSupported() {
        return (long)OpenSsl.version() >= 0x10002000L;
    }

    public static int version() {
        if (OpenSsl.isAvailable()) {
            return SSL.version();
        }
        return -1;
    }

    public static String versionString() {
        if (OpenSsl.isAvailable()) {
            return SSL.versionString();
        }
        return null;
    }

    public static void ensureAvailability() {
        if (UNAVAILABILITY_CAUSE != null) {
            throw (Error)new UnsatisfiedLinkError("failed to load the required native library").initCause(UNAVAILABILITY_CAUSE);
        }
    }

    public static Throwable unavailabilityCause() {
        return UNAVAILABILITY_CAUSE;
    }

    @Deprecated
    public static Set<String> availableCipherSuites() {
        return OpenSsl.availableOpenSslCipherSuites();
    }

    public static Set<String> availableOpenSslCipherSuites() {
        return AVAILABLE_OPENSSL_CIPHER_SUITES;
    }

    public static Set<String> availableJavaCipherSuites() {
        return AVAILABLE_JAVA_CIPHER_SUITES;
    }

    public static boolean isCipherSuiteAvailable(String cipherSuite) {
        String converted = CipherSuiteConverter.toOpenSsl(cipherSuite);
        if (converted != null) {
            cipherSuite = converted;
        }
        return AVAILABLE_OPENSSL_CIPHER_SUITES.contains(cipherSuite);
    }

    public static boolean supportsKeyManagerFactory() {
        return SUPPORTS_KEYMANAGER_FACTORY;
    }

    public static boolean supportsHostnameValidation() {
        return SUPPORTS_HOSTNAME_VALIDATION;
    }

    static boolean useKeyManagerFactory() {
        return USE_KEYMANAGER_FACTORY;
    }

    static long memoryAddress(ByteBuf buf) {
        assert (buf.isDirect());
        return buf.hasMemoryAddress() ? buf.memoryAddress() : Buffer.address(buf.nioBuffer());
    }

    private OpenSsl() {
    }

    private static void loadTcNative() throws Exception {
        String os = OpenSsl.normalizeOs(SystemPropertyUtil.get("os.name", ""));
        String arch = OpenSsl.normalizeArch(SystemPropertyUtil.get("os.arch", ""));
        LinkedHashSet<String> libNames = new LinkedHashSet<String>(3);
        libNames.add("netty-tcnative-" + os + '-' + arch);
        if (LINUX.equalsIgnoreCase(os)) {
            libNames.add("netty-tcnative-" + os + '-' + arch + "-fedora");
        }
        libNames.add("netty-tcnative");
        NativeLibraryLoader.loadFirstAvailable(SSL.class.getClassLoader(), libNames.toArray(new String[libNames.size()]));
    }

    private static boolean initializeTcNative() throws Exception {
        return Library.initialize();
    }

    private static String normalizeOs(String value) {
        if ((value = OpenSsl.normalize(value)).startsWith("aix")) {
            return "aix";
        }
        if (value.startsWith("hpux")) {
            return "hpux";
        }
        if (value.startsWith("os400") && (value.length() <= 5 || !Character.isDigit(value.charAt(5)))) {
            return "os400";
        }
        if (value.startsWith(LINUX)) {
            return LINUX;
        }
        if (value.startsWith("macosx") || value.startsWith("osx")) {
            return "osx";
        }
        if (value.startsWith("freebsd")) {
            return "freebsd";
        }
        if (value.startsWith("openbsd")) {
            return "openbsd";
        }
        if (value.startsWith("netbsd")) {
            return "netbsd";
        }
        if (value.startsWith("solaris") || value.startsWith("sunos")) {
            return "sunos";
        }
        if (value.startsWith("windows")) {
            return "windows";
        }
        return UNKNOWN;
    }

    private static String normalizeArch(String value) {
        if ((value = OpenSsl.normalize(value)).matches("^(x8664|amd64|ia32e|em64t|x64)$")) {
            return "x86_64";
        }
        if (value.matches("^(x8632|x86|i[3-6]86|ia32|x32)$")) {
            return "x86_32";
        }
        if (value.matches("^(ia64|itanium64)$")) {
            return "itanium_64";
        }
        if (value.matches("^(sparc|sparc32)$")) {
            return "sparc_32";
        }
        if (value.matches("^(sparcv9|sparc64)$")) {
            return "sparc_64";
        }
        if (value.matches("^(arm|arm32)$")) {
            return "arm_32";
        }
        if ("aarch64".equals(value)) {
            return "aarch_64";
        }
        if (value.matches("^(ppc|ppc32)$")) {
            return "ppc_32";
        }
        if ("ppc64".equals(value)) {
            return "ppc_64";
        }
        if ("ppc64le".equals(value)) {
            return "ppcle_64";
        }
        if ("s390".equals(value)) {
            return "s390_32";
        }
        if ("s390x".equals(value)) {
            return "s390_64";
        }
        return UNKNOWN;
    }

    private static String normalize(String value) {
        return value.toLowerCase(Locale.US).replaceAll("[^a-z0-9]+", "");
    }

    static void releaseIfNeeded(ReferenceCounted counted) {
        if (counted.refCnt() > 0) {
            ReferenceCountUtil.safeRelease(counted);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static {
        Throwable cause = null;
        try {
            Class.forName("io.netty.internal.tcnative.SSL", false, OpenSsl.class.getClassLoader());
        } catch (ClassNotFoundException t) {
            cause = t;
            logger.debug("netty-tcnative not in the classpath; " + OpenSslEngine.class.getSimpleName() + " will be unavailable.");
        }
        if (cause == null) {
            try {
                OpenSsl.loadTcNative();
            } catch (Throwable t) {
                cause = t;
                logger.debug("Failed to load netty-tcnative; " + OpenSslEngine.class.getSimpleName() + " will be unavailable, unless the application has already loaded the symbols by some other means. See http://netty.io/wiki/forked-tomcat-native.html for more information.", t);
            }
            try {
                OpenSsl.initializeTcNative();
                cause = null;
            } catch (Throwable t) {
                if (cause == null) {
                    cause = t;
                }
                logger.debug("Failed to initialize netty-tcnative; " + OpenSslEngine.class.getSimpleName() + " will be unavailable. See http://netty.io/wiki/forked-tomcat-native.html for more information.", t);
            }
        }
        UNAVAILABILITY_CAUSE = cause;
        if (cause == null) {
            logger.debug("netty-tcnative using native library: {}", (Object)SSL.versionString());
            LinkedHashSet<String> availableOpenSslCipherSuites = new LinkedHashSet<String>(128);
            boolean supportsKeyManagerFactory = false;
            boolean useKeyManagerFactory = false;
            boolean supportsHostNameValidation = false;
            try {
                long sslCtx = SSLContext.make(31, 1);
                long privateKeyBio = 0L;
                long certBio = 0L;
                try {
                    SSLContext.setCipherSuite(sslCtx, "ALL");
                    long ssl = SSL.newSSL(sslCtx, true);
                    try {
                        for (String c : SSL.getCiphers(ssl)) {
                            if (c == null || c.isEmpty() || availableOpenSslCipherSuites.contains(c)) continue;
                            availableOpenSslCipherSuites.add(c);
                        }
                        try {
                            SSL.setHostNameValidation(ssl, 0, "netty.io");
                            supportsHostNameValidation = true;
                        } catch (Throwable ignore) {
                            logger.debug("Hostname Verification not supported.");
                        }
                        try {
                            SelfSignedCertificate cert = new SelfSignedCertificate();
                            certBio = ReferenceCountedOpenSslContext.toBIO(cert.cert());
                            SSL.setCertificateChainBio(ssl, certBio, false);
                            supportsKeyManagerFactory = true;
                            try {
                                useKeyManagerFactory = AccessController.doPrivileged(new PrivilegedAction<Boolean>(){

                                    @Override
                                    public Boolean run() {
                                        return SystemPropertyUtil.getBoolean("io.netty.handler.ssl.openssl.useKeyManagerFactory", true);
                                    }
                                });
                            } catch (Throwable ignore) {
                                logger.debug("Failed to get useKeyManagerFactory system property.");
                            }
                        } catch (Throwable ignore) {
                            logger.debug("KeyManagerFactory not supported.");
                        }
                    } finally {
                        SSL.freeSSL(ssl);
                        if (privateKeyBio != 0L) {
                            SSL.freeBIO(privateKeyBio);
                        }
                        if (certBio != 0L) {
                            SSL.freeBIO(certBio);
                        }
                    }
                } finally {
                    SSLContext.free(sslCtx);
                }
            } catch (Exception e) {
                logger.warn("Failed to get the list of available OpenSSL cipher suites.", e);
            }
            AVAILABLE_OPENSSL_CIPHER_SUITES = Collections.unmodifiableSet(availableOpenSslCipherSuites);
            LinkedHashSet<String> availableJavaCipherSuites = new LinkedHashSet<String>(AVAILABLE_OPENSSL_CIPHER_SUITES.size() * 2);
            for (String cipher : AVAILABLE_OPENSSL_CIPHER_SUITES) {
                availableJavaCipherSuites.add(CipherSuiteConverter.toJava(cipher, "TLS"));
                availableJavaCipherSuites.add(CipherSuiteConverter.toJava(cipher, "SSL"));
            }
            AVAILABLE_JAVA_CIPHER_SUITES = Collections.unmodifiableSet(availableJavaCipherSuites);
            LinkedHashSet<String> availableCipherSuites = new LinkedHashSet<String>(AVAILABLE_OPENSSL_CIPHER_SUITES.size() + AVAILABLE_JAVA_CIPHER_SUITES.size());
            for (String cipher : AVAILABLE_OPENSSL_CIPHER_SUITES) {
                availableCipherSuites.add(cipher);
            }
            for (String cipher : AVAILABLE_JAVA_CIPHER_SUITES) {
                availableCipherSuites.add(cipher);
            }
            AVAILABLE_CIPHER_SUITES = availableCipherSuites;
            SUPPORTS_KEYMANAGER_FACTORY = supportsKeyManagerFactory;
            SUPPORTS_HOSTNAME_VALIDATION = supportsHostNameValidation;
            USE_KEYMANAGER_FACTORY = useKeyManagerFactory;
            LinkedHashSet<String> protocols = new LinkedHashSet<String>(6);
            protocols.add(PROTOCOL_SSL_V2_HELLO);
            if (OpenSsl.doesSupportProtocol(1)) {
                protocols.add(PROTOCOL_SSL_V2);
            }
            if (OpenSsl.doesSupportProtocol(2)) {
                protocols.add(PROTOCOL_SSL_V3);
            }
            if (OpenSsl.doesSupportProtocol(4)) {
                protocols.add(PROTOCOL_TLS_V1);
            }
            if (OpenSsl.doesSupportProtocol(8)) {
                protocols.add(PROTOCOL_TLS_V1_1);
            }
            if (OpenSsl.doesSupportProtocol(16)) {
                protocols.add(PROTOCOL_TLS_V1_2);
            }
            SUPPORTED_PROTOCOLS_SET = Collections.unmodifiableSet(protocols);
        } else {
            AVAILABLE_OPENSSL_CIPHER_SUITES = Collections.emptySet();
            AVAILABLE_JAVA_CIPHER_SUITES = Collections.emptySet();
            AVAILABLE_CIPHER_SUITES = Collections.emptySet();
            SUPPORTS_KEYMANAGER_FACTORY = false;
            SUPPORTS_HOSTNAME_VALIDATION = false;
            USE_KEYMANAGER_FACTORY = false;
            SUPPORTED_PROTOCOLS_SET = Collections.emptySet();
        }
    }
}

