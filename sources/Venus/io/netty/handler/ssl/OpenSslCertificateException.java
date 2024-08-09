/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  io.netty.internal.tcnative.CertificateVerifier
 */
package io.netty.handler.ssl;

import io.netty.internal.tcnative.CertificateVerifier;
import java.security.cert.CertificateException;

public final class OpenSslCertificateException
extends CertificateException {
    private static final long serialVersionUID = 5542675253797129798L;
    private final int errorCode;

    public OpenSslCertificateException(int n) {
        this((String)null, n);
    }

    public OpenSslCertificateException(String string, int n) {
        super(string);
        this.errorCode = OpenSslCertificateException.checkErrorCode(n);
    }

    public OpenSslCertificateException(String string, Throwable throwable, int n) {
        super(string, throwable);
        this.errorCode = OpenSslCertificateException.checkErrorCode(n);
    }

    public OpenSslCertificateException(Throwable throwable, int n) {
        this(null, throwable, n);
    }

    public int errorCode() {
        return this.errorCode;
    }

    private static int checkErrorCode(int n) {
        if (!CertificateVerifier.isValid((int)n)) {
            throw new IllegalArgumentException("errorCode '" + n + "' invalid, see https://www.openssl.org/docs/man1.0.2/apps/verify.html.");
        }
        return n;
    }
}

