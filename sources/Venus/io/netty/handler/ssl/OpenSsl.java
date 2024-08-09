/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  io.netty.internal.tcnative.Buffer
 *  io.netty.internal.tcnative.Library
 *  io.netty.internal.tcnative.SSL
 *  io.netty.internal.tcnative.SSLContext
 */
package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.handler.ssl.CipherSuiteConverter;
import io.netty.handler.ssl.OpenSslEngine;
import io.netty.handler.ssl.ReferenceCountedOpenSslContext;
import io.netty.handler.ssl.SslUtils;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.internal.tcnative.Buffer;
import io.netty.internal.tcnative.Library;
import io.netty.internal.tcnative.SSL;
import io.netty.internal.tcnative.SSLContext;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.NativeLibraryLoader;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public final class OpenSsl {
    private static final InternalLogger logger;
    private static final Throwable UNAVAILABILITY_CAUSE;
    static final List<String> DEFAULT_CIPHERS;
    static final Set<String> AVAILABLE_CIPHER_SUITES;
    private static final Set<String> AVAILABLE_OPENSSL_CIPHER_SUITES;
    private static final Set<String> AVAILABLE_JAVA_CIPHER_SUITES;
    private static final boolean SUPPORTS_KEYMANAGER_FACTORY;
    private static final boolean SUPPORTS_HOSTNAME_VALIDATION;
    private static final boolean USE_KEYMANAGER_FACTORY;
    private static final boolean SUPPORTS_OCSP;
    static final Set<String> SUPPORTED_PROTOCOLS_SET;
    static final boolean $assertionsDisabled;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static boolean doesSupportOcsp() {
        boolean bl = false;
        if ((long)OpenSsl.version() >= 0x10002000L) {
            long l = -1L;
            try {
                l = SSLContext.make((int)16, (int)1);
                SSLContext.enableOcsp((long)l, (boolean)false);
                bl = true;
            } catch (Exception exception) {
            } finally {
                if (l != -1L) {
                    SSLContext.free((long)l);
                }
            }
        }
        return bl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static boolean doesSupportProtocol(int n) {
        long l = -1L;
        try {
            l = SSLContext.make((int)n, (int)2);
            boolean bl = true;
            return bl;
        } catch (Exception exception) {
            boolean bl = false;
            return bl;
        } finally {
            if (l != -1L) {
                SSLContext.free((long)l);
            }
        }
    }

    public static boolean isAvailable() {
        return UNAVAILABILITY_CAUSE == null;
    }

    public static boolean isAlpnSupported() {
        return (long)OpenSsl.version() >= 0x10002000L;
    }

    public static boolean isOcspSupported() {
        return SUPPORTS_OCSP;
    }

    public static int version() {
        return OpenSsl.isAvailable() ? SSL.version() : -1;
    }

    public static String versionString() {
        return OpenSsl.isAvailable() ? SSL.versionString() : null;
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

    public static boolean isCipherSuiteAvailable(String string) {
        String string2 = CipherSuiteConverter.toOpenSsl(string);
        if (string2 != null) {
            string = string2;
        }
        return AVAILABLE_OPENSSL_CIPHER_SUITES.contains(string);
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

    static long memoryAddress(ByteBuf byteBuf) {
        if (!$assertionsDisabled && !byteBuf.isDirect()) {
            throw new AssertionError();
        }
        return byteBuf.hasMemoryAddress() ? byteBuf.memoryAddress() : Buffer.address((ByteBuffer)byteBuf.nioBuffer());
    }

    private OpenSsl() {
    }

    private static void loadTcNative() throws Exception {
        String string = PlatformDependent.normalizedOs();
        String string2 = PlatformDependent.normalizedArch();
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>(4);
        String string3 = "netty_tcnative";
        linkedHashSet.add(string3 + "_" + string + '_' + string2);
        if ("linux".equalsIgnoreCase(string)) {
            linkedHashSet.add(string3 + "_" + string + '_' + string2 + "_fedora");
        }
        linkedHashSet.add(string3 + "_" + string2);
        linkedHashSet.add(string3);
        NativeLibraryLoader.loadFirstAvailable(SSL.class.getClassLoader(), linkedHashSet.toArray(new String[linkedHashSet.size()]));
    }

    private static boolean initializeTcNative() throws Exception {
        return Library.initialize();
    }

    static void releaseIfNeeded(ReferenceCounted referenceCounted) {
        if (referenceCounted.refCnt() > 0) {
            ReferenceCountUtil.safeRelease(referenceCounted);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static {
        $assertionsDisabled = !OpenSsl.class.desiredAssertionStatus();
        logger = InternalLoggerFactory.getInstance(OpenSsl.class);
        Throwable throwable = null;
        if (SystemPropertyUtil.getBoolean("io.netty.handler.ssl.noOpenSsl", false)) {
            throwable = new UnsupportedOperationException("OpenSSL was explicit disabled with -Dio.netty.handler.ssl.noOpenSsl=true");
            logger.debug("netty-tcnative explicit disabled; " + OpenSslEngine.class.getSimpleName() + " will be unavailable.", throwable);
        } else {
            try {
                Class.forName("io.netty.internal.tcnative.SSL", false, OpenSsl.class.getClassLoader());
            } catch (ClassNotFoundException classNotFoundException) {
                throwable = classNotFoundException;
                logger.debug("netty-tcnative not in the classpath; " + OpenSslEngine.class.getSimpleName() + " will be unavailable.");
            }
            if (throwable == null) {
                try {
                    OpenSsl.loadTcNative();
                } catch (Throwable throwable2) {
                    throwable = throwable2;
                    logger.debug("Failed to load netty-tcnative; " + OpenSslEngine.class.getSimpleName() + " will be unavailable, unless the application has already loaded the symbols by some other means. See http://netty.io/wiki/forked-tomcat-native.html for more information.", throwable2);
                }
                try {
                    OpenSsl.initializeTcNative();
                    throwable = null;
                } catch (Throwable throwable3) {
                    if (throwable == null) {
                        throwable = throwable3;
                    }
                    logger.debug("Failed to initialize netty-tcnative; " + OpenSslEngine.class.getSimpleName() + " will be unavailable. See http://netty.io/wiki/forked-tomcat-native.html for more information.", throwable3);
                }
            }
        }
        UNAVAILABILITY_CAUSE = throwable;
        if (throwable == null) {
            logger.debug("netty-tcnative using native library: {}", (Object)SSL.versionString());
            ArrayList<String> arrayList = new ArrayList<String>();
            LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>(128);
            boolean bl = false;
            boolean bl2 = false;
            boolean bl3 = false;
            try {
                long l = SSLContext.make((int)31, (int)1);
                long l2 = 0L;
                SelfSignedCertificate selfSignedCertificate = null;
                try {
                    SSLContext.setCipherSuite((long)l, (String)"ALL");
                    long l3 = SSL.newSSL((long)l, (boolean)true);
                    try {
                        for (String string : SSL.getCiphers((long)l3)) {
                            if (string == null || string.isEmpty() || linkedHashSet.contains(string)) continue;
                            linkedHashSet.add(string);
                        }
                        try {
                            SSL.setHostNameValidation((long)l3, (int)0, (String)"netty.io");
                            bl3 = true;
                        } catch (Throwable throwable4) {
                            logger.debug("Hostname Verification not supported.");
                        }
                        try {
                            selfSignedCertificate = new SelfSignedCertificate();
                            l2 = ReferenceCountedOpenSslContext.toBIO(selfSignedCertificate.cert());
                            SSL.setCertificateChainBio((long)l3, (long)l2, (boolean)false);
                            bl = true;
                            try {
                                bl2 = AccessController.doPrivileged(new PrivilegedAction<Boolean>(){

                                    @Override
                                    public Boolean run() {
                                        return SystemPropertyUtil.getBoolean("io.netty.handler.ssl.openssl.useKeyManagerFactory", true);
                                    }

                                    @Override
                                    public Object run() {
                                        return this.run();
                                    }
                                });
                            } catch (Throwable throwable5) {
                                logger.debug("Failed to get useKeyManagerFactory system property.");
                            }
                        } catch (Throwable throwable6) {
                            logger.debug("KeyManagerFactory not supported.");
                        }
                    } finally {
                        SSL.freeSSL((long)l3);
                        if (l2 != 0L) {
                            SSL.freeBIO((long)l2);
                        }
                        if (selfSignedCertificate != null) {
                            selfSignedCertificate.delete();
                        }
                    }
                } finally {
                    SSLContext.free((long)l);
                }
            } catch (Exception exception) {
                logger.warn("Failed to get the list of available OpenSSL cipher suites.", exception);
            }
            AVAILABLE_OPENSSL_CIPHER_SUITES = Collections.unmodifiableSet(linkedHashSet);
            LinkedHashSet<String> linkedHashSet2 = new LinkedHashSet<String>(AVAILABLE_OPENSSL_CIPHER_SUITES.size() * 2);
            for (String string : AVAILABLE_OPENSSL_CIPHER_SUITES) {
                linkedHashSet2.add(CipherSuiteConverter.toJava(string, "TLS"));
                linkedHashSet2.add(CipherSuiteConverter.toJava(string, "SSL"));
            }
            SslUtils.addIfSupported(linkedHashSet2, arrayList, SslUtils.DEFAULT_CIPHER_SUITES);
            SslUtils.useFallbackCiphersIfDefaultIsEmpty(arrayList, linkedHashSet2);
            DEFAULT_CIPHERS = Collections.unmodifiableList(arrayList);
            AVAILABLE_JAVA_CIPHER_SUITES = Collections.unmodifiableSet(linkedHashSet2);
            LinkedHashSet linkedHashSet3 = new LinkedHashSet(AVAILABLE_OPENSSL_CIPHER_SUITES.size() + AVAILABLE_JAVA_CIPHER_SUITES.size());
            linkedHashSet3.addAll(AVAILABLE_OPENSSL_CIPHER_SUITES);
            linkedHashSet3.addAll(AVAILABLE_JAVA_CIPHER_SUITES);
            AVAILABLE_CIPHER_SUITES = linkedHashSet3;
            SUPPORTS_KEYMANAGER_FACTORY = bl;
            SUPPORTS_HOSTNAME_VALIDATION = bl3;
            USE_KEYMANAGER_FACTORY = bl2;
            LinkedHashSet<String> linkedHashSet4 = new LinkedHashSet<String>(6);
            linkedHashSet4.add("SSLv2Hello");
            if (OpenSsl.doesSupportProtocol(1)) {
                linkedHashSet4.add("SSLv2");
            }
            if (OpenSsl.doesSupportProtocol(2)) {
                linkedHashSet4.add("SSLv3");
            }
            if (OpenSsl.doesSupportProtocol(4)) {
                linkedHashSet4.add("TLSv1");
            }
            if (OpenSsl.doesSupportProtocol(8)) {
                linkedHashSet4.add("TLSv1.1");
            }
            if (OpenSsl.doesSupportProtocol(16)) {
                linkedHashSet4.add("TLSv1.2");
            }
            SUPPORTED_PROTOCOLS_SET = Collections.unmodifiableSet(linkedHashSet4);
            SUPPORTS_OCSP = OpenSsl.doesSupportOcsp();
            if (logger.isDebugEnabled()) {
                logger.debug("Supported protocols (OpenSSL): {} ", (Object)Arrays.asList(SUPPORTED_PROTOCOLS_SET));
                logger.debug("Default cipher suites (OpenSSL): {}", (Object)DEFAULT_CIPHERS);
            }
        } else {
            DEFAULT_CIPHERS = Collections.emptyList();
            AVAILABLE_OPENSSL_CIPHER_SUITES = Collections.emptySet();
            AVAILABLE_JAVA_CIPHER_SUITES = Collections.emptySet();
            AVAILABLE_CIPHER_SUITES = Collections.emptySet();
            SUPPORTS_KEYMANAGER_FACTORY = false;
            SUPPORTS_HOSTNAME_VALIDATION = false;
            USE_KEYMANAGER_FACTORY = false;
            SUPPORTED_PROTOCOLS_SET = Collections.emptySet();
            SUPPORTS_OCSP = false;
        }
    }
}

