/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.base64.Base64;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class PemReader {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(PemReader.class);
    private static final Pattern CERT_PATTERN = Pattern.compile("-+BEGIN\\s+.*CERTIFICATE[^-]*-+(?:\\s|\\r|\\n)+([a-z0-9+/=\\r\\n]+)-+END\\s+.*CERTIFICATE[^-]*-+", 2);
    private static final Pattern KEY_PATTERN = Pattern.compile("-+BEGIN\\s+.*PRIVATE\\s+KEY[^-]*-+(?:\\s|\\r|\\n)+([a-z0-9+/=\\r\\n]+)-+END\\s+.*PRIVATE\\s+KEY[^-]*-+", 2);

    static ByteBuf[] readCertificates(File file) throws CertificateException {
        ByteBuf[] byteBufArray;
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            byteBufArray = PemReader.readCertificates(fileInputStream);
        } catch (Throwable throwable) {
            try {
                PemReader.safeClose(fileInputStream);
                throw throwable;
            } catch (FileNotFoundException fileNotFoundException) {
                throw new CertificateException("could not find certificate file: " + file);
            }
        }
        PemReader.safeClose(fileInputStream);
        return byteBufArray;
    }

    static ByteBuf[] readCertificates(InputStream inputStream) throws CertificateException {
        String string;
        try {
            string = PemReader.readContent(inputStream);
        } catch (IOException iOException) {
            throw new CertificateException("failed to read certificate input stream", iOException);
        }
        ArrayList<ByteBuf> arrayList = new ArrayList<ByteBuf>();
        Matcher matcher = CERT_PATTERN.matcher(string);
        int n = 0;
        while (matcher.find(n)) {
            ByteBuf byteBuf = Unpooled.copiedBuffer(matcher.group(1), CharsetUtil.US_ASCII);
            ByteBuf byteBuf2 = Base64.decode(byteBuf);
            byteBuf.release();
            arrayList.add(byteBuf2);
            n = matcher.end();
        }
        if (arrayList.isEmpty()) {
            throw new CertificateException("found no certificates in input stream");
        }
        return arrayList.toArray(new ByteBuf[arrayList.size()]);
    }

    static ByteBuf readPrivateKey(File file) throws KeyException {
        ByteBuf byteBuf;
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            byteBuf = PemReader.readPrivateKey(fileInputStream);
        } catch (Throwable throwable) {
            try {
                PemReader.safeClose(fileInputStream);
                throw throwable;
            } catch (FileNotFoundException fileNotFoundException) {
                throw new KeyException("could not find key file: " + file);
            }
        }
        PemReader.safeClose(fileInputStream);
        return byteBuf;
    }

    static ByteBuf readPrivateKey(InputStream inputStream) throws KeyException {
        String string;
        try {
            string = PemReader.readContent(inputStream);
        } catch (IOException iOException) {
            throw new KeyException("failed to read key input stream", iOException);
        }
        Matcher matcher = KEY_PATTERN.matcher(string);
        if (!matcher.find()) {
            throw new KeyException("could not find a PKCS #8 private key in input stream (see http://netty.io/wiki/sslcontextbuilder-and-private-key.html for more information)");
        }
        ByteBuf byteBuf = Unpooled.copiedBuffer(matcher.group(1), CharsetUtil.US_ASCII);
        ByteBuf byteBuf2 = Base64.decode(byteBuf);
        byteBuf.release();
        return byteBuf2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static String readContent(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            int n;
            byte[] byArray = new byte[8192];
            while ((n = inputStream.read(byArray)) >= 0) {
                byteArrayOutputStream.write(byArray, 0, n);
            }
            String string = byteArrayOutputStream.toString(CharsetUtil.US_ASCII.name());
            return string;
        } finally {
            PemReader.safeClose(byteArrayOutputStream);
        }
    }

    private static void safeClose(InputStream inputStream) {
        try {
            inputStream.close();
        } catch (IOException iOException) {
            logger.warn("Failed to close a stream.", iOException);
        }
    }

    private static void safeClose(OutputStream outputStream) {
        try {
            outputStream.close();
        } catch (IOException iOException) {
            logger.warn("Failed to close a stream.", iOException);
        }
    }

    private PemReader() {
    }
}

