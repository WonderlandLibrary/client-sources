/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import io.netty.internal.tcnative.CertificateVerifier;
import java.security.cert.CertificateException;

public final class OpenSslCertificateException
extends CertificateException {
    private static final long serialVersionUID = 5542675253797129798L;
    private final int errorCode;

    public OpenSslCertificateException(int errorCode) {
        this((String)null, errorCode);
    }

    public OpenSslCertificateException(String msg, int errorCode) {
        super(msg);
        this.errorCode = OpenSslCertificateException.checkErrorCode(errorCode);
    }

    public OpenSslCertificateException(String message, Throwable cause, int errorCode) {
        super(message, cause);
        this.errorCode = OpenSslCertificateException.checkErrorCode(errorCode);
    }

    public OpenSslCertificateException(Throwable cause, int errorCode) {
        this(null, cause, errorCode);
    }

    public int errorCode() {
        return this.errorCode;
    }

    private static int checkErrorCode(int errorCode) {
        if (errorCode < CertificateVerifier.X509_V_OK || errorCode > CertificateVerifier.X509_V_ERR_DANE_NO_MATCH) {
            throw new IllegalArgumentException("errorCode must be " + CertificateVerifier.X509_V_OK + " => " + CertificateVerifier.X509_V_ERR_DANE_NO_MATCH + ". See https://www.openssl.org/docs/manmaster/apps/verify.html .");
        }
        return errorCode;
    }
}

