// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.net;

final class _ircIlIIIIlIlI
{
    private int _ircIllIllIIlI;
    
    _ircIlIIIIlIlI(final _ircIIIIlIlIlI ircIIIIlIlIlI) {
    }
    
    @Override
    public final String toString() {
        final byte[] bytes = new byte[3];
        this._ircIllIllIIlI = 470836536;
        bytes[0] = (byte)(this._ircIllIllIIlI >>> 8);
        this._ircIllIllIIlI = -478789656;
        bytes[1] = (byte)(this._ircIllIllIIlI >>> 19);
        this._ircIllIllIIlI = -81055207;
        bytes[2] = (byte)(this._ircIllIllIIlI >>> 7);
        return new String(bytes);
    }
}
