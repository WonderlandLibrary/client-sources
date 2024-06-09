// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.util.WeakHashMap;
import java.util.Map;
import java.security.MessageDigest;

public final class _ircIllIllIIlI
{
    private static MessageDigest _ircIllIllIIlI;
    private static Map _ircIllIllIIlI;
    
    static {
        de.liquiddev.ircclient.util._ircIllIllIIlI._ircIllIllIIlI = new WeakHashMap();
    }
    
    private static void _ircIllIllIIlI() {
        try {
            de.liquiddev.ircclient.util._ircIllIllIIlI._ircIllIllIIlI = MessageDigest.getInstance(new _irclllllIIlII().toString());
        }
        catch (NoSuchAlgorithmException cause) {
            throw new RuntimeException(cause);
        }
    }
    
    public static byte[] _ircIllIllIIlI(final String s) {
        if (de.liquiddev.ircclient.util._ircIllIllIIlI._ircIllIllIIlI.containsKey(s)) {
            return de.liquiddev.ircclient.util._ircIllIllIIlI._ircIllIllIIlI.get(s).array();
        }
        if (de.liquiddev.ircclient.util._ircIllIllIIlI._ircIllIllIIlI == null) {
            try {
                de.liquiddev.ircclient.util._ircIllIllIIlI._ircIllIllIIlI = MessageDigest.getInstance(new _irclllllIIlII().toString());
            }
            catch (NoSuchAlgorithmException cause) {
                throw new RuntimeException(cause);
            }
        }
        try {
            de.liquiddev.ircclient.util._ircIllIllIIlI._ircIllIllIIlI.update(s.getBytes(new _ircIllIlIlIII().toString()));
            final byte[] digest = de.liquiddev.ircclient.util._ircIllIllIIlI._ircIllIllIIlI.digest();
            de.liquiddev.ircclient.util._ircIllIllIIlI._ircIllIllIIlI.put(s, ByteBuffer.wrap(digest));
            return digest;
        }
        catch (UnsupportedEncodingException cause2) {
            throw new RuntimeException(cause2);
        }
    }
}
