// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.utils;

import javax.crypto.Cipher;
import java.security.Key;

public class Encryption {
    private String field476;
    private Key field477;
    private Cipher field478;

    public Encryption() throws Exception {
        this.method348(String.valueOf(System.currentTimeMillis() / 0x4AA2486D64DDL ^ 0x4AA2486D64D7L));
    }

    private static native String method353(final int p0, final int p1, final int p2);

    public native void method348(final String key) throws Exception;

    public native String method349(final String in) throws Exception;

    public native String method350(final byte[] in) throws Exception;

    public native void method351(final String val) throws Exception;

    public native String method352();
}
