/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.turikhay.caf;

import com.turikhay.caf.util.DualFingerprint;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.security.auth.x500.X500Principal;

public class Cert {
    private final Certificate certificate;
    private final Set<String> aliases;
    private DualFingerprint fingerprint;
    private String subject;

    public Certificate getCertificate() {
        return this.certificate;
    }

    public Set<String> getAliases() {
        return this.aliases;
    }

    public Optional<X509Certificate> asX509() {
        return this.certificate instanceof X509Certificate ? Optional.of((X509Certificate)this.certificate) : Optional.empty();
    }

    public Cert(Certificate certificate, Set<String> set) {
        this.certificate = certificate;
        this.aliases = set;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        Cert cert = (Cert)object;
        return this.certificate.equals(cert.certificate);
    }

    public int hashCode() {
        return Objects.hash(this.certificate);
    }

    public String toString() {
        return String.format(Locale.ROOT, "Cert{%s,%s}", this.getSubject(), this.getFingerprintFormatted());
    }

    private DualFingerprint getFingerprint() throws CertificateEncodingException {
        if (this.fingerprint == null) {
            this.fingerprint = Cert.computeFingerprint(this.certificate);
        }
        return this.fingerprint;
    }

    private String getFingerprintFormatted() {
        DualFingerprint dualFingerprint;
        try {
            dualFingerprint = this.getFingerprint();
        } catch (CertificateEncodingException certificateEncodingException) {
            return certificateEncodingException.toString();
        }
        return String.format(Locale.ROOT, "SHA-1:%s,SHA-256:%s", dualFingerprint.getSha1(), dualFingerprint.getSha256());
    }

    private String getSubject() {
        if (this.subject == null) {
            this.subject = Cert.extractSubject(this.certificate);
        }
        return this.subject;
    }

    private static DualFingerprint computeFingerprint(Certificate certificate) throws CertificateEncodingException {
        return DualFingerprint.compute(certificate.getEncoded());
    }

    private static String extractSubject(Certificate certificate) {
        String string;
        X509Certificate x509Certificate;
        X500Principal x500Principal;
        if (certificate instanceof X509Certificate && (x500Principal = (x509Certificate = (X509Certificate)certificate).getSubjectX500Principal()) != null && (string = x500Principal.getName()) != null) {
            return string;
        }
        return "";
    }
}

