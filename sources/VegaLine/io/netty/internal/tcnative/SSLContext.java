/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.internal.tcnative;

import io.netty.internal.tcnative.CertificateRequestedCallback;
import io.netty.internal.tcnative.CertificateVerifier;
import io.netty.internal.tcnative.SessionTicketKey;

public final class SSLContext {
    private SSLContext() {
    }

    public static native long make(int var0, int var1) throws Exception;

    public static native int free(long var0);

    public static native void setContextId(long var0, String var2);

    public static native void setOptions(long var0, int var2);

    public static native int getOptions(long var0);

    public static native void clearOptions(long var0, int var2);

    public static native boolean setCipherSuite(long var0, String var2) throws Exception;

    public static native boolean setCertificateChainFile(long var0, String var2, boolean var3);

    public static native boolean setCertificateChainBio(long var0, long var2, boolean var4);

    public static native boolean setCertificate(long var0, String var2, String var3, String var4) throws Exception;

    public static native boolean setCertificateBio(long var0, long var2, long var4, String var6) throws Exception;

    public static native long setSessionCacheSize(long var0, long var2);

    public static native long getSessionCacheSize(long var0);

    public static native long setSessionCacheTimeout(long var0, long var2);

    public static native long getSessionCacheTimeout(long var0);

    public static native long setSessionCacheMode(long var0, long var2);

    public static native long getSessionCacheMode(long var0);

    public static native long sessionAccept(long var0);

    public static native long sessionAcceptGood(long var0);

    public static native long sessionAcceptRenegotiate(long var0);

    public static native long sessionCacheFull(long var0);

    public static native long sessionCbHits(long var0);

    public static native long sessionConnect(long var0);

    public static native long sessionConnectGood(long var0);

    public static native long sessionConnectRenegotiate(long var0);

    public static native long sessionHits(long var0);

    public static native long sessionMisses(long var0);

    public static native long sessionNumber(long var0);

    public static native long sessionTimeouts(long var0);

    public static native long sessionTicketKeyNew(long var0);

    public static native long sessionTicketKeyResume(long var0);

    public static native long sessionTicketKeyRenew(long var0);

    public static native long sessionTicketKeyFail(long var0);

    public static void setSessionTicketKeys(long ctx, SessionTicketKey[] keys) {
        if (keys == null || keys.length == 0) {
            throw new IllegalArgumentException("Length of the keys should be longer than 0.");
        }
        byte[] binaryKeys = new byte[keys.length * 48];
        for (int i = 0; i < keys.length; ++i) {
            SessionTicketKey key = keys[i];
            int dstCurPos = 48 * i;
            System.arraycopy(key.name, 0, binaryKeys, dstCurPos, 16);
            System.arraycopy(key.hmacKey, 0, binaryKeys, dstCurPos += 16, 16);
            System.arraycopy(key.aesKey, 0, binaryKeys, dstCurPos += 16, 16);
        }
        SSLContext.setSessionTicketKeys0(ctx, binaryKeys);
    }

    private static native void setSessionTicketKeys0(long var0, byte[] var2);

    public static native boolean setCACertificateBio(long var0, long var2);

    public static native void setVerify(long var0, int var2, int var3);

    public static native void setCertVerifyCallback(long var0, CertificateVerifier var2);

    public static native void setCertRequestedCallback(long var0, CertificateRequestedCallback var2);

    public static native void setNpnProtos(long var0, String[] var2, int var3);

    public static native void setAlpnProtos(long var0, String[] var2, int var3);

    public static native void setTmpDHLength(long var0, int var2);

    public static native boolean setSessionIdContext(long var0, byte[] var2);

    public static native int setMode(long var0, int var2);

    public static native int getMode(long var0);
}

