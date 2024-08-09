/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SigningKeyResolver;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.lang.Assert;
import java.security.Key;
import javax.crypto.spec.SecretKeySpec;

@Deprecated
public class SigningKeyResolverAdapter
implements SigningKeyResolver {
    @Override
    public Key resolveSigningKey(JwsHeader jwsHeader, Claims claims) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.forName(jwsHeader.getAlgorithm());
        Assert.isTrue(signatureAlgorithm.isHmac(), "The default resolveSigningKey(JwsHeader, Claims) implementation cannot be used for asymmetric key algorithms (RSA, Elliptic Curve).  Override the resolveSigningKey(JwsHeader, Claims) method instead and return a Key instance appropriate for the " + signatureAlgorithm.name() + " algorithm.");
        byte[] byArray = this.resolveSigningKeyBytes(jwsHeader, claims);
        return new SecretKeySpec(byArray, signatureAlgorithm.getJcaName());
    }

    @Override
    public Key resolveSigningKey(JwsHeader jwsHeader, byte[] byArray) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.forName(jwsHeader.getAlgorithm());
        Assert.isTrue(signatureAlgorithm.isHmac(), "The default resolveSigningKey(JwsHeader, byte[]) implementation cannot be used for asymmetric key algorithms (RSA, Elliptic Curve).  Override the resolveSigningKey(JwsHeader, byte[]) method instead and return a Key instance appropriate for the " + signatureAlgorithm.name() + " algorithm.");
        byte[] byArray2 = this.resolveSigningKeyBytes(jwsHeader, byArray);
        return new SecretKeySpec(byArray2, signatureAlgorithm.getJcaName());
    }

    public byte[] resolveSigningKeyBytes(JwsHeader jwsHeader, Claims claims) {
        throw new UnsupportedJwtException("The specified SigningKeyResolver implementation does not support Claims JWS signing key resolution.  Consider overriding either the resolveSigningKey(JwsHeader, Claims) method or, for HMAC algorithms, the resolveSigningKeyBytes(JwsHeader, Claims) method.");
    }

    public byte[] resolveSigningKeyBytes(JwsHeader jwsHeader, byte[] byArray) {
        throw new UnsupportedJwtException("The specified SigningKeyResolver implementation does not support content JWS signing key resolution.  Consider overriding either the resolveSigningKey(JwsHeader, byte[]) method or, for HMAC algorithms, the resolveSigningKeyBytes(JwsHeader, byte[]) method.");
    }
}

