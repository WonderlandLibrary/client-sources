// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.net;

final class _ircIllIlIlIII
{
    private int _ircIllIllIIlI;
    
    _ircIllIlIlIII(final _ircIllIllIIlI ircIllIllIIlI) {
    }
    
    @Override
    public final String toString() {
        final byte[] bytes = new byte[2];
        this._ircIllIllIIlI = 475806272;
        bytes[0] = (byte)(this._ircIllIllIIlI >>> 8);
        this._ircIllIllIIlI = -1314848396;
        bytes[1] = (byte)(this._ircIllIllIIlI >>> 11);
        return new String(bytes);
    }
}
