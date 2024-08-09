/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.base64.Base64;
import io.netty.handler.ssl.util.BouncyCastleSelfSignedCertGenerator;
import io.netty.handler.ssl.util.OpenJdkSelfSignedCertGenerator;
import io.netty.handler.ssl.util.ThreadLocalInsecureRandom;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;

public final class SelfSignedCertificate {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(SelfSignedCertificate.class);
    private static final Date DEFAULT_NOT_BEFORE = new Date(SystemPropertyUtil.getLong("io.netty.selfSignedCertificate.defaultNotBefore", System.currentTimeMillis() - 31536000000L));
    private static final Date DEFAULT_NOT_AFTER = new Date(SystemPropertyUtil.getLong("io.netty.selfSignedCertificate.defaultNotAfter", 253402300799000L));
    private final File certificate;
    private final File privateKey;
    private final X509Certificate cert;
    private final PrivateKey key;

    public SelfSignedCertificate() throws CertificateException {
        this(DEFAULT_NOT_BEFORE, DEFAULT_NOT_AFTER);
    }

    public SelfSignedCertificate(Date date, Date date2) throws CertificateException {
        this("example.com", date, date2);
    }

    public SelfSignedCertificate(String string) throws CertificateException {
        this(string, DEFAULT_NOT_BEFORE, DEFAULT_NOT_AFTER);
    }

    public SelfSignedCertificate(String string, Date date, Date date2) throws CertificateException {
        this(string, ThreadLocalInsecureRandom.current(), 1024, date, date2);
    }

    public SelfSignedCertificate(String string, SecureRandom secureRandom, int n) throws CertificateException {
        this(string, secureRandom, n, DEFAULT_NOT_BEFORE, DEFAULT_NOT_AFTER);
    }

    public SelfSignedCertificate(String string, SecureRandom secureRandom, int n, Date date, Date date2) throws CertificateException {
        KeyPair keyPair;
        String[] stringArray;
        try {
            stringArray = KeyPairGenerator.getInstance("RSA");
            stringArray.initialize(n, secureRandom);
            keyPair = stringArray.generateKeyPair();
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new Error(noSuchAlgorithmException);
        }
        try {
            stringArray = OpenJdkSelfSignedCertGenerator.generate(string, keyPair, secureRandom, date, date2);
        } catch (Throwable throwable) {
            logger.debug("Failed to generate a self-signed X.509 certificate using sun.security.x509:", throwable);
            try {
                stringArray = BouncyCastleSelfSignedCertGenerator.generate(string, keyPair, secureRandom, date, date2);
            } catch (Throwable throwable2) {
                logger.debug("Failed to generate a self-signed X.509 certificate using Bouncy Castle:", throwable2);
                throw new CertificateException("No provider succeeded to generate a self-signed certificate. See debug log for the root cause.", throwable2);
            }
        }
        this.certificate = new File(stringArray[0]);
        this.privateKey = new File(stringArray[5]);
        this.key = keyPair.getPrivate();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(this.certificate);
            this.cert = (X509Certificate)CertificateFactory.getInstance("X509").generateCertificate(fileInputStream);
        } catch (Exception exception) {
            throw new CertificateEncodingException(exception);
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException iOException) {
                    logger.warn("Failed to close a file: " + this.certificate, iOException);
                }
            }
        }
    }

    public File certificate() {
        return this.certificate;
    }

    public File privateKey() {
        return this.privateKey;
    }

    public X509Certificate cert() {
        return this.cert;
    }

    public PrivateKey key() {
        return this.key;
    }

    public void delete() {
        SelfSignedCertificate.safeDelete(this.certificate);
        SelfSignedCertificate.safeDelete(this.privateKey);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static String[] newSelfSignedCertificate(String string, PrivateKey privateKey, X509Certificate x509Certificate) throws IOException, CertificateEncodingException {
        String string2;
        String string3;
        ByteBuf byteBuf;
        ByteBuf byteBuf2 = Unpooled.wrappedBuffer(privateKey.getEncoded());
        try {
            byteBuf = Base64.encode(byteBuf2, true);
            try {
                string3 = "-----BEGIN PRIVATE KEY-----\n" + byteBuf.toString(CharsetUtil.US_ASCII) + "\n-----END PRIVATE KEY-----\n";
            } finally {
                byteBuf.release();
            }
        } finally {
            byteBuf2.release();
        }
        File file = File.createTempFile("keyutil_" + string + '_', ".key");
        file.deleteOnExit();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        try {
            ((OutputStream)fileOutputStream).write(string3.getBytes(CharsetUtil.US_ASCII));
            ((OutputStream)fileOutputStream).close();
            fileOutputStream = null;
        } finally {
            if (fileOutputStream != null) {
                SelfSignedCertificate.safeClose(file, fileOutputStream);
                SelfSignedCertificate.safeDelete(file);
            }
        }
        byteBuf2 = Unpooled.wrappedBuffer(x509Certificate.getEncoded());
        try {
            byteBuf = Base64.encode(byteBuf2, true);
            try {
                string2 = "-----BEGIN CERTIFICATE-----\n" + byteBuf.toString(CharsetUtil.US_ASCII) + "\n-----END CERTIFICATE-----\n";
            } finally {
                byteBuf.release();
            }
        } finally {
            byteBuf2.release();
        }
        File file2 = File.createTempFile("keyutil_" + string + '_', ".crt");
        file2.deleteOnExit();
        FileOutputStream fileOutputStream2 = new FileOutputStream(file2);
        try {
            ((OutputStream)fileOutputStream2).write(string2.getBytes(CharsetUtil.US_ASCII));
            ((OutputStream)fileOutputStream2).close();
            fileOutputStream2 = null;
        } finally {
            if (fileOutputStream2 != null) {
                SelfSignedCertificate.safeClose(file2, fileOutputStream2);
                SelfSignedCertificate.safeDelete(file2);
                SelfSignedCertificate.safeDelete(file);
            }
        }
        return new String[]{file2.getPath(), file.getPath()};
    }

    private static void safeDelete(File file) {
        if (!file.delete()) {
            logger.warn("Failed to delete a file: " + file);
        }
    }

    private static void safeClose(File file, OutputStream outputStream) {
        try {
            outputStream.close();
        } catch (IOException iOException) {
            logger.warn("Failed to close a file: " + file, iOException);
        }
    }
}

