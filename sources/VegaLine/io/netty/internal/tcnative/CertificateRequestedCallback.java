/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.internal.tcnative;

public interface CertificateRequestedCallback {
    public static final byte TLS_CT_RSA_SIGN = 1;
    public static final byte TLS_CT_DSS_SIGN = 2;
    public static final byte TLS_CT_RSA_FIXED_DH = 3;
    public static final byte TLS_CT_DSS_FIXED_DH = 4;
    public static final byte TLS_CT_ECDSA_SIGN = 64;
    public static final byte TLS_CT_RSA_FIXED_ECDH = 65;
    public static final byte TLS_CT_ECDSA_FIXED_ECDH = 66;

    public KeyMaterial requested(long var1, byte[] var3, byte[][] var4);

    public static class KeyMaterial {
        private final long certificateChain;
        private final long privateKey;

        public KeyMaterial(long certificateChain, long privateKey) {
            this.certificateChain = certificateChain;
            this.privateKey = privateKey;
        }

        public final long privateKey() {
            return this.privateKey;
        }

        public final long certificateChain() {
            return this.certificateChain;
        }
    }
}

