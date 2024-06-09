// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.net.packet;

final class _ircIIIlIlllII
{
    private int _ircIllIllIIlI;
    
    _ircIIIlIlllII(final Irc11PopupMessage irc11PopupMessage) {
    }
    
    @Override
    public final String toString() {
        final byte[] bytes = new byte[11];
        this._ircIllIllIIlI = 472336876;
        bytes[0] = (byte)(this._ircIllIllIIlI >>> 8);
        this._ircIllIllIIlI = 925447187;
        bytes[1] = (byte)(this._ircIllIllIIlI >>> 20);
        this._ircIllIllIIlI = -1288743540;
        bytes[2] = (byte)(this._ircIllIllIIlI >>> 2);
        this._ircIllIllIIlI = 1623476237;
        bytes[3] = (byte)(this._ircIllIllIIlI >>> 6);
        this._ircIllIllIIlI = -1823581693;
        bytes[4] = (byte)(this._ircIllIllIIlI >>> 22);
        this._ircIllIllIIlI = 2071811667;
        bytes[5] = (byte)(this._ircIllIllIIlI >>> 4);
        this._ircIllIllIIlI = 546950621;
        bytes[6] = (byte)(this._ircIllIllIIlI >>> 10);
        this._ircIllIllIIlI = 1000284115;
        bytes[7] = (byte)(this._ircIllIllIIlI >>> 19);
        this._ircIllIllIIlI = -779618805;
        bytes[8] = (byte)(this._ircIllIllIIlI >>> 18);
        this._ircIllIllIIlI = -1621502525;
        bytes[9] = (byte)(this._ircIllIllIIlI >>> 14);
        this._ircIllIllIIlI = -912788210;
        bytes[10] = (byte)(this._ircIllIllIIlI >>> 18);
        return new String(bytes);
    }
}
