/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.internal.tcnative;

import io.netty.internal.tcnative.NativeStaticallyReferencedJniMethods;

public interface CertificateVerifier {
    public static final int X509_V_OK = NativeStaticallyReferencedJniMethods.x509vOK();
    public static final int X509_V_ERR_UNSPECIFIED = NativeStaticallyReferencedJniMethods.x509vErrUnspecified();
    public static final int X509_V_ERR_UNABLE_TO_GET_ISSUER_CERT = NativeStaticallyReferencedJniMethods.x509vErrUnableToGetIssuerCert();
    public static final int X509_V_ERR_UNABLE_TO_GET_CRL = NativeStaticallyReferencedJniMethods.x509vErrUnableToGetCrl();
    public static final int X509_V_ERR_UNABLE_TO_DECRYPT_CERT_SIGNATURE = NativeStaticallyReferencedJniMethods.x509vErrUnableToDecryptCertSignature();
    public static final int X509_V_ERR_UNABLE_TO_DECRYPT_CRL_SIGNATURE = NativeStaticallyReferencedJniMethods.x509vErrUnableToDecryptCrlSignature();
    public static final int X509_V_ERR_UNABLE_TO_DECODE_ISSUER_PUBLIC_KEY = NativeStaticallyReferencedJniMethods.x509vErrUnableToDecodeIssuerPublicKey();
    public static final int X509_V_ERR_CERT_SIGNATURE_FAILURE = NativeStaticallyReferencedJniMethods.x509vErrCertSignatureFailure();
    public static final int X509_V_ERR_CRL_SIGNATURE_FAILURE = NativeStaticallyReferencedJniMethods.x509vErrCrlSignatureFailure();
    public static final int X509_V_ERR_CERT_NOT_YET_VALID = NativeStaticallyReferencedJniMethods.x509vErrCertNotYetValid();
    public static final int X509_V_ERR_CERT_HAS_EXPIRED = NativeStaticallyReferencedJniMethods.x509vErrCertHasExpired();
    public static final int X509_V_ERR_CRL_NOT_YET_VALID = NativeStaticallyReferencedJniMethods.x509vErrCrlNotYetValid();
    public static final int X509_V_ERR_CRL_HAS_EXPIRED = NativeStaticallyReferencedJniMethods.x509vErrCrlHasExpired();
    public static final int X509_V_ERR_ERROR_IN_CERT_NOT_BEFORE_FIELD = NativeStaticallyReferencedJniMethods.x509vErrErrorInCertNotBeforeField();
    public static final int X509_V_ERR_ERROR_IN_CERT_NOT_AFTER_FIELD = NativeStaticallyReferencedJniMethods.x509vErrErrorInCertNotAfterField();
    public static final int X509_V_ERR_ERROR_IN_CRL_LAST_UPDATE_FIELD = NativeStaticallyReferencedJniMethods.x509vErrErrorInCrlLastUpdateField();
    public static final int X509_V_ERR_ERROR_IN_CRL_NEXT_UPDATE_FIELD = NativeStaticallyReferencedJniMethods.x509vErrErrorInCrlNextUpdateField();
    public static final int X509_V_ERR_OUT_OF_MEM = NativeStaticallyReferencedJniMethods.x509vErrOutOfMem();
    public static final int X509_V_ERR_DEPTH_ZERO_SELF_SIGNED_CERT = NativeStaticallyReferencedJniMethods.x509vErrDepthZeroSelfSignedCert();
    public static final int X509_V_ERR_SELF_SIGNED_CERT_IN_CHAIN = NativeStaticallyReferencedJniMethods.x509vErrSelfSignedCertInChain();
    public static final int X509_V_ERR_UNABLE_TO_GET_ISSUER_CERT_LOCALLY = NativeStaticallyReferencedJniMethods.x509vErrUnableToGetIssuerCertLocally();
    public static final int X509_V_ERR_UNABLE_TO_VERIFY_LEAF_SIGNATURE = NativeStaticallyReferencedJniMethods.x509vErrUnableToVerifyLeafSignature();
    public static final int X509_V_ERR_CERT_CHAIN_TOO_LONG = NativeStaticallyReferencedJniMethods.x509vErrCertChainTooLong();
    public static final int X509_V_ERR_CERT_REVOKED = NativeStaticallyReferencedJniMethods.x509vErrCertRevoked();
    public static final int X509_V_ERR_INVALID_CA = NativeStaticallyReferencedJniMethods.x509vErrInvalidCa();
    public static final int X509_V_ERR_PATH_LENGTH_EXCEEDED = NativeStaticallyReferencedJniMethods.x509vErrPathLengthExceeded();
    public static final int X509_V_ERR_INVALID_PURPOSE = NativeStaticallyReferencedJniMethods.x509vErrInvalidPurpose();
    public static final int X509_V_ERR_CERT_UNTRUSTED = NativeStaticallyReferencedJniMethods.x509vErrCertUntrusted();
    public static final int X509_V_ERR_CERT_REJECTED = NativeStaticallyReferencedJniMethods.x509vErrCertRejected();
    public static final int X509_V_ERR_SUBJECT_ISSUER_MISMATCH = NativeStaticallyReferencedJniMethods.x509vErrSubjectIssuerMismatch();
    public static final int X509_V_ERR_AKID_SKID_MISMATCH = NativeStaticallyReferencedJniMethods.x509vErrAkidSkidMismatch();
    public static final int X509_V_ERR_AKID_ISSUER_SERIAL_MISMATCH = NativeStaticallyReferencedJniMethods.x509vErrAkidIssuerSerialMismatch();
    public static final int X509_V_ERR_KEYUSAGE_NO_CERTSIGN = NativeStaticallyReferencedJniMethods.x509vErrKeyUsageNoCertSign();
    public static final int X509_V_ERR_UNABLE_TO_GET_CRL_ISSUER = NativeStaticallyReferencedJniMethods.x509vErrUnableToGetCrlIssuer();
    public static final int X509_V_ERR_UNHANDLED_CRITICAL_EXTENSION = NativeStaticallyReferencedJniMethods.x509vErrUnhandledCriticalExtension();
    public static final int X509_V_ERR_KEYUSAGE_NO_CRL_SIGN = NativeStaticallyReferencedJniMethods.x509vErrKeyUsageNoCrlSign();
    public static final int X509_V_ERR_UNHANDLED_CRITICAL_CRL_EXTENSION = NativeStaticallyReferencedJniMethods.x509vErrUnhandledCriticalCrlExtension();
    public static final int X509_V_ERR_INVALID_NON_CA = NativeStaticallyReferencedJniMethods.x509vErrInvalidNonCa();
    public static final int X509_V_ERR_PROXY_PATH_LENGTH_EXCEEDED = NativeStaticallyReferencedJniMethods.x509vErrProxyPathLengthExceeded();
    public static final int X509_V_ERR_KEYUSAGE_NO_DIGITAL_SIGNATURE = NativeStaticallyReferencedJniMethods.x509vErrKeyUsageNoDigitalSignature();
    public static final int X509_V_ERR_PROXY_CERTIFICATES_NOT_ALLOWED = NativeStaticallyReferencedJniMethods.x509vErrProxyCertificatesNotAllowed();
    public static final int X509_V_ERR_INVALID_EXTENSION = NativeStaticallyReferencedJniMethods.x509vErrInvalidExtension();
    public static final int X509_V_ERR_INVALID_POLICY_EXTENSION = NativeStaticallyReferencedJniMethods.x509vErrInvalidPolicyExtension();
    public static final int X509_V_ERR_NO_EXPLICIT_POLICY = NativeStaticallyReferencedJniMethods.x509vErrNoExplicitPolicy();
    public static final int X509_V_ERR_DIFFERENT_CRL_SCOPE = NativeStaticallyReferencedJniMethods.x509vErrDifferntCrlScope();
    public static final int X509_V_ERR_UNSUPPORTED_EXTENSION_FEATURE = NativeStaticallyReferencedJniMethods.x509vErrUnsupportedExtensionFeature();
    public static final int X509_V_ERR_UNNESTED_RESOURCE = NativeStaticallyReferencedJniMethods.x509vErrUnnestedResource();
    public static final int X509_V_ERR_PERMITTED_VIOLATION = NativeStaticallyReferencedJniMethods.x509vErrPermittedViolation();
    public static final int X509_V_ERR_EXCLUDED_VIOLATION = NativeStaticallyReferencedJniMethods.x509vErrExcludedViolation();
    public static final int X509_V_ERR_SUBTREE_MINMAX = NativeStaticallyReferencedJniMethods.x509vErrSubtreeMinMax();
    public static final int X509_V_ERR_APPLICATION_VERIFICATION = NativeStaticallyReferencedJniMethods.x509vErrApplicationVerification();
    public static final int X509_V_ERR_UNSUPPORTED_CONSTRAINT_TYPE = NativeStaticallyReferencedJniMethods.x509vErrUnsupportedConstraintType();
    public static final int X509_V_ERR_UNSUPPORTED_CONSTRAINT_SYNTAX = NativeStaticallyReferencedJniMethods.x509vErrUnsupportedConstraintSyntax();
    public static final int X509_V_ERR_UNSUPPORTED_NAME_SYNTAX = NativeStaticallyReferencedJniMethods.x509vErrUnsupportedNameSyntax();
    public static final int X509_V_ERR_CRL_PATH_VALIDATION_ERROR = NativeStaticallyReferencedJniMethods.x509vErrCrlPathValidationError();
    public static final int X509_V_ERR_PATH_LOOP = NativeStaticallyReferencedJniMethods.x509vErrPathLoop();
    public static final int X509_V_ERR_SUITE_B_INVALID_VERSION = NativeStaticallyReferencedJniMethods.x509vErrSuiteBInvalidVersion();
    public static final int X509_V_ERR_SUITE_B_INVALID_ALGORITHM = NativeStaticallyReferencedJniMethods.x509vErrSuiteBInvalidAlgorithm();
    public static final int X509_V_ERR_SUITE_B_INVALID_CURVE = NativeStaticallyReferencedJniMethods.x509vErrSuiteBInvalidCurve();
    public static final int X509_V_ERR_SUITE_B_INVALID_SIGNATURE_ALGORITHM = NativeStaticallyReferencedJniMethods.x509vErrSuiteBInvalidSignatureAlgorithm();
    public static final int X509_V_ERR_SUITE_B_LOS_NOT_ALLOWED = NativeStaticallyReferencedJniMethods.x509vErrSuiteBLosNotAllowed();
    public static final int X509_V_ERR_SUITE_B_CANNOT_SIGN_P_384_WITH_P_256 = NativeStaticallyReferencedJniMethods.x509vErrSuiteBCannotSignP384WithP256();
    public static final int X509_V_ERR_HOSTNAME_MISMATCH = NativeStaticallyReferencedJniMethods.x509vErrHostnameMismatch();
    public static final int X509_V_ERR_EMAIL_MISMATCH = NativeStaticallyReferencedJniMethods.x509vErrEmailMismatch();
    public static final int X509_V_ERR_IP_ADDRESS_MISMATCH = NativeStaticallyReferencedJniMethods.x509vErrIpAddressMismatch();
    public static final int X509_V_ERR_DANE_NO_MATCH = NativeStaticallyReferencedJniMethods.x509vErrDaneNoMatch();

    public int verify(long var1, byte[][] var3, String var4);
}

