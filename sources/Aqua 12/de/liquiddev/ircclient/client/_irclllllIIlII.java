// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.client;

final class _irclllllIIlII
{
    private int _ircIllIllIIlI;
    
    @Override
    public final String toString() {
        final byte[] bytes = new byte[5];
        this._ircIllIllIIlI = 1256029084;
        bytes[0] = (byte)(this._ircIllIllIIlI >> 24);
        this._ircIllIllIIlI = -154658991;
        bytes[1] = (byte)(this._ircIllIllIIlI >>> 4);
        this._ircIllIllIIlI = 91486654;
        bytes[2] = (byte)(this._ircIllIllIIlI >>> 16);
        this._ircIllIllIIlI = 781692355;
        bytes[3] = (byte)(this._ircIllIllIIlI >>> 21);
        this._ircIllIllIIlI = 932218536;
        bytes[4] = (byte)(this._ircIllIllIIlI >>> 20);
        return new String(bytes);
    }
}
