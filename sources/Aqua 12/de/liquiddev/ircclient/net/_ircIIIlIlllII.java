// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.net;

final class _ircIIIlIlllII
{
    private int _ircIllIllIIlI;
    
    _ircIIIlIlllII(final _ircIllIllIIlI ircIllIllIIlI) {
    }
    
    @Override
    public final String toString() {
        final byte[] bytes = new byte[4];
        this._ircIllIllIIlI = 475819584;
        bytes[0] = (byte)(this._ircIllIllIIlI >>> 8);
        this._ircIllIllIIlI = -1314674316;
        bytes[1] = (byte)(this._ircIllIllIIlI >>> 11);
        this._ircIllIllIIlI = -1690813368;
        bytes[2] = (byte)(this._ircIllIllIIlI >>> 22);
        this._ircIllIllIIlI = 912574386;
        bytes[3] = (byte)(this._ircIllIllIIlI >>> 23);
        return new String(bytes);
    }
}
