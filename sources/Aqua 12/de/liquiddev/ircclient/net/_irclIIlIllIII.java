// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.net;

final class _irclIIlIllIII
{
    private int _ircIllIllIIlI;
    
    _irclIIlIllIII(final _ircIIIIlIlIlI ircIIIIlIlIlI) {
    }
    
    @Override
    public final String toString() {
        final byte[] bytes = new byte[9];
        this._ircIllIllIIlI = 470818872;
        bytes[0] = (byte)(this._ircIllIllIIlI >>> 8);
        this._ircIllIllIIlI = -476168216;
        bytes[1] = (byte)(this._ircIllIllIIlI >>> 19);
        this._ircIllIllIIlI = -81055079;
        bytes[2] = (byte)(this._ircIllIllIIlI >>> 7);
        this._ircIllIllIIlI = -2095425787;
        bytes[3] = (byte)(this._ircIllIllIIlI >>> 19);
        this._ircIllIllIIlI = 694606714;
        bytes[4] = (byte)(this._ircIllIllIIlI >>> 3);
        this._ircIllIllIIlI = 728874464;
        bytes[5] = (byte)(this._ircIllIllIIlI >>> 19);
        this._ircIllIllIIlI = 1896235067;
        bytes[6] = (byte)(this._ircIllIllIIlI >>> 12);
        this._ircIllIllIIlI = 194569704;
        bytes[7] = (byte)(this._ircIllIllIIlI >>> 19);
        this._ircIllIllIIlI = 388301069;
        bytes[8] = (byte)(this._ircIllIllIIlI >>> 3);
        return new String(bytes);
    }
}
