// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient;

final class _irclllllIIlII
{
    private int _ircIllIllIIlI;
    
    @Override
    public final String toString() {
        final byte[] bytes = new byte[5];
        this._ircIllIllIIlI = 464656618;
        bytes[0] = (byte)(this._ircIllIllIIlI >>> 7);
        this._ircIllIllIIlI = -1671811524;
        bytes[1] = (byte)(this._ircIllIllIIlI >>> 8);
        this._ircIllIllIIlI = 454791365;
        bytes[2] = (byte)(this._ircIllIllIIlI >>> 2);
        this._ircIllIllIIlI = 193620449;
        bytes[3] = (byte)(this._ircIllIllIIlI >>> 22);
        this._ircIllIllIIlI = 365481179;
        bytes[4] = (byte)(this._ircIllIllIIlI >>> 6);
        return new String(bytes);
    }
}
