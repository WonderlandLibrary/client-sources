// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.net;

final class _irclIIIIlIIII
{
    private int _ircIllIllIIlI;
    
    _irclIIIIlIIII(final _ircIIIIlIlIlI ircIIIIlIlIlI) {
    }
    
    @Override
    public final String toString() {
        final byte[] bytes = new byte[2];
        this._ircIllIllIIlI = 470825528;
        bytes[0] = (byte)(this._ircIllIllIIlI >>> 8);
        this._ircIllIllIIlI = -519684120;
        bytes[1] = (byte)(this._ircIllIllIIlI >>> 19);
        return new String(bytes);
    }
}
