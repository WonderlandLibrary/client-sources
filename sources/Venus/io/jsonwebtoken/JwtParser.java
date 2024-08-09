/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwe;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtHandler;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Parser;
import io.jsonwebtoken.security.SecurityException;
import io.jsonwebtoken.security.SignatureException;
import java.io.InputStream;

public interface JwtParser
extends Parser<Jwt<?, ?>> {
    public boolean isSigned(CharSequence var1);

    @Override
    public Jwt<?, ?> parse(CharSequence var1) throws ExpiredJwtException, MalformedJwtException, SignatureException, SecurityException, IllegalArgumentException;

    @Deprecated
    public <T> T parse(CharSequence var1, JwtHandler<T> var2) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, SecurityException, IllegalArgumentException;

    @Deprecated
    public Jwt<Header, byte[]> parseContentJwt(CharSequence var1) throws UnsupportedJwtException, MalformedJwtException, SignatureException, SecurityException, IllegalArgumentException;

    @Deprecated
    public Jwt<Header, Claims> parseClaimsJwt(CharSequence var1) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, SecurityException, IllegalArgumentException;

    @Deprecated
    public Jws<byte[]> parseContentJws(CharSequence var1) throws UnsupportedJwtException, MalformedJwtException, SignatureException, SecurityException, IllegalArgumentException;

    @Deprecated
    public Jws<Claims> parseClaimsJws(CharSequence var1) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, SecurityException, IllegalArgumentException;

    public Jwt<Header, byte[]> parseUnsecuredContent(CharSequence var1) throws JwtException, IllegalArgumentException;

    public Jwt<Header, Claims> parseUnsecuredClaims(CharSequence var1) throws JwtException, IllegalArgumentException;

    public Jws<byte[]> parseSignedContent(CharSequence var1) throws JwtException, IllegalArgumentException;

    public Jws<byte[]> parseSignedContent(CharSequence var1, byte[] var2);

    public Jws<byte[]> parseSignedContent(CharSequence var1, InputStream var2);

    public Jws<Claims> parseSignedClaims(CharSequence var1) throws JwtException, IllegalArgumentException;

    public Jws<Claims> parseSignedClaims(CharSequence var1, byte[] var2) throws JwtException, IllegalArgumentException;

    public Jws<Claims> parseSignedClaims(CharSequence var1, InputStream var2) throws JwtException, IllegalArgumentException;

    public Jwe<byte[]> parseEncryptedContent(CharSequence var1) throws JwtException, IllegalArgumentException;

    public Jwe<Claims> parseEncryptedClaims(CharSequence var1) throws JwtException, IllegalArgumentException;
}

