/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.internal.tcnative;

final class NativeStaticallyReferencedJniMethods {
    private NativeStaticallyReferencedJniMethods() {
    }

    static native int sslOpCipherServerPreference();

    static native int sslOpNoSSLv2();

    static native int sslOpNoSSLv3();

    static native int sslOpNoTLSv1();

    static native int sslOpNoTLSv11();

    static native int sslOpNoTLSv12();

    static native int sslOpNoTicket();

    static native int sslOpNoCompression();

    static native int sslSessCacheOff();

    static native int sslSessCacheServer();

    static native int sslStConnect();

    static native int sslStAccept();

    static native int sslModeEnablePartialWrite();

    static native int sslModeAcceptMovingWriteBuffer();

    static native int sslModeReleaseBuffers();

    static native int sslSendShutdown();

    static native int sslReceivedShutdown();

    static native int sslErrorNone();

    static native int sslErrorSSL();

    static native int sslErrorWantRead();

    static native int sslErrorWantWrite();

    static native int sslErrorWantX509Lookup();

    static native int sslErrorSyscall();

    static native int sslErrorZeroReturn();

    static native int sslErrorWantConnect();

    static native int sslErrorWantAccept();

    static native int x509CheckFlagAlwaysCheckSubject();

    static native int x509CheckFlagDisableWildCards();

    static native int x509CheckFlagNoPartialWildCards();

    static native int x509CheckFlagMultiLabelWildCards();

    static native int x509vOK();

    static native int x509vErrUnspecified();

    static native int x509vErrUnableToGetIssuerCert();

    static native int x509vErrUnableToGetCrl();

    static native int x509vErrUnableToDecryptCertSignature();

    static native int x509vErrUnableToDecryptCrlSignature();

    static native int x509vErrUnableToDecodeIssuerPublicKey();

    static native int x509vErrCertSignatureFailure();

    static native int x509vErrCrlSignatureFailure();

    static native int x509vErrCertNotYetValid();

    static native int x509vErrCertHasExpired();

    static native int x509vErrCrlNotYetValid();

    static native int x509vErrCrlHasExpired();

    static native int x509vErrErrorInCertNotBeforeField();

    static native int x509vErrErrorInCertNotAfterField();

    static native int x509vErrErrorInCrlLastUpdateField();

    static native int x509vErrErrorInCrlNextUpdateField();

    static native int x509vErrOutOfMem();

    static native int x509vErrDepthZeroSelfSignedCert();

    static native int x509vErrSelfSignedCertInChain();

    static native int x509vErrUnableToGetIssuerCertLocally();

    static native int x509vErrUnableToVerifyLeafSignature();

    static native int x509vErrCertChainTooLong();

    static native int x509vErrCertRevoked();

    static native int x509vErrInvalidCa();

    static native int x509vErrPathLengthExceeded();

    static native int x509vErrInvalidPurpose();

    static native int x509vErrCertUntrusted();

    static native int x509vErrCertRejected();

    static native int x509vErrSubjectIssuerMismatch();

    static native int x509vErrAkidSkidMismatch();

    static native int x509vErrAkidIssuerSerialMismatch();

    static native int x509vErrKeyUsageNoCertSign();

    static native int x509vErrUnableToGetCrlIssuer();

    static native int x509vErrUnhandledCriticalExtension();

    static native int x509vErrKeyUsageNoCrlSign();

    static native int x509vErrUnhandledCriticalCrlExtension();

    static native int x509vErrInvalidNonCa();

    static native int x509vErrProxyPathLengthExceeded();

    static native int x509vErrKeyUsageNoDigitalSignature();

    static native int x509vErrProxyCertificatesNotAllowed();

    static native int x509vErrInvalidExtension();

    static native int x509vErrInvalidPolicyExtension();

    static native int x509vErrNoExplicitPolicy();

    static native int x509vErrDifferntCrlScope();

    static native int x509vErrUnsupportedExtensionFeature();

    static native int x509vErrUnnestedResource();

    static native int x509vErrPermittedViolation();

    static native int x509vErrExcludedViolation();

    static native int x509vErrSubtreeMinMax();

    static native int x509vErrApplicationVerification();

    static native int x509vErrUnsupportedConstraintType();

    static native int x509vErrUnsupportedConstraintSyntax();

    static native int x509vErrUnsupportedNameSyntax();

    static native int x509vErrCrlPathValidationError();

    static native int x509vErrPathLoop();

    static native int x509vErrSuiteBInvalidVersion();

    static native int x509vErrSuiteBInvalidAlgorithm();

    static native int x509vErrSuiteBInvalidCurve();

    static native int x509vErrSuiteBInvalidSignatureAlgorithm();

    static native int x509vErrSuiteBLosNotAllowed();

    static native int x509vErrSuiteBCannotSignP384WithP256();

    static native int x509vErrHostnameMismatch();

    static native int x509vErrEmailMismatch();

    static native int x509vErrIpAddressMismatch();

    static native int x509vErrDaneNoMatch();
}

