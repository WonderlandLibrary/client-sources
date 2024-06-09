// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.net.packet;

final class _ircIllIllIIlI
{
    private int _ircIllIllIIlI;
    
    _ircIllIllIIlI(final Irc00Login irc00Login) {
    }
    
    @Override
    public final String toString() {
        final byte[] bytes = new byte[11];
        this._ircIllIllIIlI = 474235532;
        bytes[0] = (byte)(this._ircIllIllIIlI >>> 8);
        this._ircIllIllIIlI = 1598229833;
        bytes[1] = (byte)(this._ircIllIllIIlI >>> 11);
        this._ircIllIllIIlI = -650960799;
        bytes[2] = (byte)(this._ircIllIllIIlI >>> 11);
        this._ircIllIllIIlI = 2003962985;
        bytes[3] = (byte)(this._ircIllIllIIlI >>> 12);
        this._ircIllIllIIlI = -691028979;
        bytes[4] = (byte)(this._ircIllIllIIlI >>> 20);
        this._ircIllIllIIlI = 506323391;
        bytes[5] = (byte)(this._ircIllIllIIlI >>> 2);
        this._ircIllIllIIlI = 1008437455;
        bytes[6] = (byte)(this._ircIllIllIIlI >>> 1);
        this._ircIllIllIIlI = 1969810745;
        bytes[7] = (byte)(this._ircIllIllIIlI >>> 5);
        this._ircIllIllIIlI = 930195647;
        bytes[8] = (byte)(this._ircIllIllIIlI >>> 23);
        this._ircIllIllIIlI = -1530443214;
        bytes[9] = (byte)(this._ircIllIllIIlI >>> 13);
        this._ircIllIllIIlI = 788793813;
        bytes[10] = (byte)(this._ircIllIllIIlI >>> 13);
        return new String(bytes);
    }
}
