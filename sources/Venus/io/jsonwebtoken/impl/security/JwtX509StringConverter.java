/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.Bytes;
import io.jsonwebtoken.impl.lang.Converter;
import io.jsonwebtoken.impl.security.JcaTemplate;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.SecurityException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

public class JwtX509StringConverter
implements Converter<X509Certificate, CharSequence> {
    public static final JwtX509StringConverter INSTANCE = new JwtX509StringConverter();

    @Override
    public String applyTo(X509Certificate x509Certificate) {
        byte[] byArray;
        Assert.notNull(x509Certificate, "X509Certificate cannot be null.");
        try {
            byArray = x509Certificate.getEncoded();
        } catch (CertificateEncodingException certificateEncodingException) {
            String string = "Unable to access X509Certificate encoded bytes necessary to perform DER Base64-encoding. Certificate: {" + x509Certificate + "}. Cause: " + certificateEncodingException.getMessage();
            throw new IllegalArgumentException(string, certificateEncodingException);
        }
        if (Bytes.isEmpty(byArray)) {
            String string = "X509Certificate encoded bytes cannot be null or empty.  Certificate: {" + x509Certificate + "}.";
            throw new IllegalArgumentException(string);
        }
        return Encoders.BASE64.encode(byArray);
    }

    protected X509Certificate toCert(byte[] byArray) throws SecurityException {
        return new JcaTemplate("X.509").generateX509Certificate(byArray);
    }

    @Override
    public X509Certificate applyFrom(CharSequence charSequence) {
        Assert.hasText(charSequence, "X.509 Certificate encoded string cannot be null or empty.");
        try {
            byte[] byArray = Decoders.BASE64.decode(charSequence);
            return this.toCert(byArray);
        } catch (Exception exception) {
            String string = "Unable to convert Base64 String '" + charSequence + "' to X509Certificate instance. Cause: " + exception.getMessage();
            throw new IllegalArgumentException(string, exception);
        }
    }

    @Override
    public Object applyFrom(Object object) {
        return this.applyFrom((CharSequence)object);
    }

    @Override
    public Object applyTo(Object object) {
        return this.applyTo((X509Certificate)object);
    }
}

