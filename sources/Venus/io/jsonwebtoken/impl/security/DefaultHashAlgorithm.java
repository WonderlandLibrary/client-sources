/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.CheckedFunction;
import io.jsonwebtoken.impl.security.CryptoAlgorithm;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.HashAlgorithm;
import io.jsonwebtoken.security.Request;
import io.jsonwebtoken.security.VerifyDigestRequest;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Locale;

public final class DefaultHashAlgorithm
extends CryptoAlgorithm
implements HashAlgorithm {
    public static final HashAlgorithm SHA1 = new DefaultHashAlgorithm("sha-1");

    DefaultHashAlgorithm(String string) {
        super(string, string.toUpperCase(Locale.ENGLISH));
    }

    @Override
    public byte[] digest(Request<InputStream> request) {
        Assert.notNull(request, "Request cannot be null.");
        InputStream inputStream = (InputStream)Assert.notNull(request.getPayload(), "Request payload cannot be null.");
        return this.jca(request).withMessageDigest(new CheckedFunction<MessageDigest, byte[]>(this, inputStream){
            final InputStream val$payload;
            final DefaultHashAlgorithm this$0;
            {
                this.this$0 = defaultHashAlgorithm;
                this.val$payload = inputStream;
            }

            @Override
            public byte[] apply(MessageDigest messageDigest) throws IOException {
                byte[] byArray = new byte[1024];
                int n = 0;
                while (n != -1) {
                    n = this.val$payload.read(byArray);
                    if (n <= 0) continue;
                    messageDigest.update(byArray, 0, n);
                }
                return messageDigest.digest();
            }

            @Override
            public Object apply(Object object) throws Exception {
                return this.apply((MessageDigest)object);
            }
        });
    }

    @Override
    public boolean verify(VerifyDigestRequest verifyDigestRequest) {
        Assert.notNull(verifyDigestRequest, "VerifyDigestRequest cannot be null.");
        byte[] byArray = Assert.notNull(verifyDigestRequest.getDigest(), "Digest cannot be null.");
        byte[] byArray2 = this.digest(verifyDigestRequest);
        return MessageDigest.isEqual(byArray2, byArray);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        return super.equals(object);
    }

    @Override
    public String getId() {
        return super.getId();
    }
}

