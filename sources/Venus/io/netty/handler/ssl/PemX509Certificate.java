/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.Unpooled;
import io.netty.handler.ssl.PemEncoded;
import io.netty.handler.ssl.PemValue;
import io.netty.handler.ssl.SslUtils;
import io.netty.util.CharsetUtil;
import io.netty.util.IllegalReferenceCountException;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.ObjectUtil;
import java.math.BigInteger;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class PemX509Certificate
extends X509Certificate
implements PemEncoded {
    private static final byte[] BEGIN_CERT = "-----BEGIN CERTIFICATE-----\n".getBytes(CharsetUtil.US_ASCII);
    private static final byte[] END_CERT = "\n-----END CERTIFICATE-----\n".getBytes(CharsetUtil.US_ASCII);
    private final ByteBuf content;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static PemEncoded toPEM(ByteBufAllocator byteBufAllocator, boolean bl, X509Certificate ... x509CertificateArray) throws CertificateEncodingException {
        X509Certificate x509Certificate;
        if (x509CertificateArray == null || x509CertificateArray.length == 0) {
            throw new IllegalArgumentException("X.509 certificate chain can't be null or empty");
        }
        if (x509CertificateArray.length == 1 && (x509Certificate = x509CertificateArray[0]) instanceof PemEncoded) {
            return ((PemEncoded)((Object)x509Certificate)).retain();
        }
        boolean bl2 = false;
        ReferenceCounted referenceCounted = null;
        try {
            for (X509Certificate x509Certificate2 : x509CertificateArray) {
                if (x509Certificate2 == null) {
                    throw new IllegalArgumentException("Null element in chain: " + Arrays.toString(x509CertificateArray));
                }
                referenceCounted = x509Certificate2 instanceof PemEncoded ? PemX509Certificate.append(byteBufAllocator, bl, (PemEncoded)((Object)x509Certificate2), x509CertificateArray.length, (ByteBuf)referenceCounted) : PemX509Certificate.append(byteBufAllocator, bl, x509Certificate2, x509CertificateArray.length, (ByteBuf)referenceCounted);
            }
            PemValue pemValue = new PemValue((ByteBuf)referenceCounted, false);
            bl2 = true;
            PemValue pemValue2 = pemValue;
            return pemValue2;
        } finally {
            if (!bl2 && referenceCounted != null) {
                referenceCounted.release();
            }
        }
    }

    private static ByteBuf append(ByteBufAllocator byteBufAllocator, boolean bl, PemEncoded pemEncoded, int n, ByteBuf byteBuf) {
        ByteBuf byteBuf2 = pemEncoded.content();
        if (byteBuf == null) {
            byteBuf = PemX509Certificate.newBuffer(byteBufAllocator, bl, byteBuf2.readableBytes() * n);
        }
        byteBuf.writeBytes(byteBuf2.slice());
        return byteBuf;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static ByteBuf append(ByteBufAllocator byteBufAllocator, boolean bl, X509Certificate x509Certificate, int n, ByteBuf byteBuf) throws CertificateEncodingException {
        ByteBuf byteBuf2 = Unpooled.wrappedBuffer(x509Certificate.getEncoded());
        try {
            ByteBuf byteBuf3 = SslUtils.toBase64(byteBufAllocator, byteBuf2);
            try {
                if (byteBuf == null) {
                    byteBuf = PemX509Certificate.newBuffer(byteBufAllocator, bl, (BEGIN_CERT.length + byteBuf3.readableBytes() + END_CERT.length) * n);
                }
                byteBuf.writeBytes(BEGIN_CERT);
                byteBuf.writeBytes(byteBuf3);
                byteBuf.writeBytes(END_CERT);
            } finally {
                byteBuf3.release();
            }
        } finally {
            byteBuf2.release();
        }
        return byteBuf;
    }

    private static ByteBuf newBuffer(ByteBufAllocator byteBufAllocator, boolean bl, int n) {
        return bl ? byteBufAllocator.directBuffer(n) : byteBufAllocator.buffer(n);
    }

    public static PemX509Certificate valueOf(byte[] byArray) {
        return PemX509Certificate.valueOf(Unpooled.wrappedBuffer(byArray));
    }

    public static PemX509Certificate valueOf(ByteBuf byteBuf) {
        return new PemX509Certificate(byteBuf);
    }

    private PemX509Certificate(ByteBuf byteBuf) {
        this.content = ObjectUtil.checkNotNull(byteBuf, "content");
    }

    @Override
    public boolean isSensitive() {
        return true;
    }

    @Override
    public int refCnt() {
        return this.content.refCnt();
    }

    @Override
    public ByteBuf content() {
        int n = this.refCnt();
        if (n <= 0) {
            throw new IllegalReferenceCountException(n);
        }
        return this.content;
    }

    @Override
    public PemX509Certificate copy() {
        return this.replace(this.content.copy());
    }

    @Override
    public PemX509Certificate duplicate() {
        return this.replace(this.content.duplicate());
    }

    @Override
    public PemX509Certificate retainedDuplicate() {
        return this.replace(this.content.retainedDuplicate());
    }

    @Override
    public PemX509Certificate replace(ByteBuf byteBuf) {
        return new PemX509Certificate(byteBuf);
    }

    @Override
    public PemX509Certificate retain() {
        this.content.retain();
        return this;
    }

    @Override
    public PemX509Certificate retain(int n) {
        this.content.retain(n);
        return this;
    }

    @Override
    public PemX509Certificate touch() {
        this.content.touch();
        return this;
    }

    @Override
    public PemX509Certificate touch(Object object) {
        this.content.touch(object);
        return this;
    }

    @Override
    public boolean release() {
        return this.content.release();
    }

    @Override
    public boolean release(int n) {
        return this.content.release(n);
    }

    @Override
    public byte[] getEncoded() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasUnsupportedCriticalExtension() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<String> getCriticalExtensionOIDs() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<String> getNonCriticalExtensionOIDs() {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte[] getExtensionValue(String string) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void checkValidity() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void checkValidity(Date date) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getVersion() {
        throw new UnsupportedOperationException();
    }

    @Override
    public BigInteger getSerialNumber() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Principal getIssuerDN() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Principal getSubjectDN() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Date getNotBefore() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Date getNotAfter() {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte[] getTBSCertificate() {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte[] getSignature() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getSigAlgName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getSigAlgOID() {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte[] getSigAlgParams() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean[] getIssuerUniqueID() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean[] getSubjectUniqueID() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean[] getKeyUsage() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getBasicConstraints() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void verify(PublicKey publicKey) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void verify(PublicKey publicKey, String string) {
        throw new UnsupportedOperationException();
    }

    @Override
    public PublicKey getPublicKey() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof PemX509Certificate)) {
            return true;
        }
        PemX509Certificate pemX509Certificate = (PemX509Certificate)object;
        return this.content.equals(pemX509Certificate.content);
    }

    @Override
    public int hashCode() {
        return this.content.hashCode();
    }

    @Override
    public String toString() {
        return this.content.toString(CharsetUtil.UTF_8);
    }

    @Override
    public PemEncoded touch(Object object) {
        return this.touch(object);
    }

    @Override
    public PemEncoded touch() {
        return this.touch();
    }

    @Override
    public PemEncoded retain(int n) {
        return this.retain(n);
    }

    @Override
    public PemEncoded retain() {
        return this.retain();
    }

    @Override
    public PemEncoded replace(ByteBuf byteBuf) {
        return this.replace(byteBuf);
    }

    @Override
    public PemEncoded retainedDuplicate() {
        return this.retainedDuplicate();
    }

    @Override
    public PemEncoded duplicate() {
        return this.duplicate();
    }

    @Override
    public PemEncoded copy() {
        return this.copy();
    }

    @Override
    public ByteBufHolder touch(Object object) {
        return this.touch(object);
    }

    @Override
    public ByteBufHolder touch() {
        return this.touch();
    }

    @Override
    public ByteBufHolder retain(int n) {
        return this.retain(n);
    }

    @Override
    public ByteBufHolder retain() {
        return this.retain();
    }

    @Override
    public ByteBufHolder replace(ByteBuf byteBuf) {
        return this.replace(byteBuf);
    }

    @Override
    public ByteBufHolder retainedDuplicate() {
        return this.retainedDuplicate();
    }

    @Override
    public ByteBufHolder duplicate() {
        return this.duplicate();
    }

    @Override
    public ByteBufHolder copy() {
        return this.copy();
    }

    @Override
    public ReferenceCounted touch(Object object) {
        return this.touch(object);
    }

    @Override
    public ReferenceCounted touch() {
        return this.touch();
    }

    @Override
    public ReferenceCounted retain(int n) {
        return this.retain(n);
    }

    @Override
    public ReferenceCounted retain() {
        return this.retain();
    }
}

