/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodec;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.io.Streams;
import io.jsonwebtoken.impl.lang.Bytes;
import io.jsonwebtoken.io.CompressionAlgorithm;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.lang.Strings;
import java.io.InputStream;
import java.io.OutputStream;

class Payload {
    static final Payload EMPTY = new Payload(Bytes.EMPTY, null);
    private final CharSequence string;
    private final byte[] bytes;
    private final Claims claims;
    private final InputStream inputStream;
    private final boolean inputStreamEmpty;
    private final String contentType;
    private CompressionAlgorithm zip;
    private boolean claimsExpected;

    Payload(Claims claims) {
        this(claims, null, null, null, null);
    }

    Payload(CharSequence charSequence, String string) {
        this(null, charSequence, null, null, string);
    }

    Payload(byte[] byArray, String string) {
        this(null, null, byArray, null, string);
    }

    Payload(InputStream inputStream, String string) {
        this(null, null, null, inputStream, string);
    }

    private Payload(Claims claims, CharSequence charSequence, byte[] byArray, InputStream inputStream, String string) {
        this.claims = claims;
        this.string = Strings.clean(charSequence);
        this.contentType = Strings.clean(string);
        InputStream inputStream2 = inputStream;
        byte[] byArray2 = Bytes.nullSafe(byArray);
        if (Strings.hasText(this.string)) {
            byArray2 = Strings.utf8(this.string);
        }
        this.bytes = byArray2;
        if (inputStream2 == null && !Bytes.isEmpty(this.bytes)) {
            inputStream2 = Streams.of(byArray2);
        }
        this.inputStreamEmpty = inputStream2 == null;
        this.inputStream = this.inputStreamEmpty ? Streams.of(Bytes.EMPTY) : inputStream2;
    }

    boolean isClaims() {
        return !Collections.isEmpty(this.claims);
    }

    Claims getRequiredClaims() {
        return Assert.notEmpty(this.claims, "Claims cannot be null or empty when calling this method.");
    }

    boolean isString() {
        return Strings.hasText(this.string);
    }

    String getContentType() {
        return this.contentType;
    }

    public void setZip(CompressionAlgorithm compressionAlgorithm) {
        this.zip = compressionAlgorithm;
    }

    boolean isCompressed() {
        return this.zip != null;
    }

    public void setClaimsExpected(boolean bl) {
        this.claimsExpected = bl;
    }

    boolean isConsumable() {
        return !this.isClaims() && (this.isString() || !Bytes.isEmpty(this.bytes) || this.inputStream != null && this.claimsExpected);
    }

    boolean isEmpty() {
        return !this.isClaims() && !this.isString() && Bytes.isEmpty(this.bytes) && this.inputStreamEmpty;
    }

    public OutputStream compress(OutputStream outputStream) {
        return this.zip != null ? this.zip.compress(outputStream) : outputStream;
    }

    public Payload decompress(CompressionAlgorithm compressionAlgorithm) {
        Assert.notNull(compressionAlgorithm, "CompressionAlgorithm cannot be null.");
        Payload payload = this;
        if (!this.isString() && this.isConsumable()) {
            if (compressionAlgorithm.equals(Jwts.ZIP.DEF) && !Bytes.isEmpty(this.bytes)) {
                byte[] byArray = ((CompressionCodec)compressionAlgorithm).decompress(this.bytes);
                payload = new Payload(this.claims, this.string, byArray, null, this.getContentType());
            } else {
                InputStream inputStream = this.toInputStream();
                inputStream = compressionAlgorithm.decompress(inputStream);
                payload = new Payload(this.claims, this.string, this.bytes, inputStream, this.getContentType());
            }
            payload.setClaimsExpected(this.claimsExpected);
        }
        return payload;
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    InputStream toInputStream() {
        Assert.state(!this.isClaims(), "Claims exist, cannot convert to InputStream directly.");
        return this.inputStream;
    }
}

