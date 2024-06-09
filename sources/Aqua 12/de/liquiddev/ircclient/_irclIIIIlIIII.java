// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient;

final class _irclIIIIlIIII
{
    private int _ircIllIllIIlI;
    
    @Override
    public final String toString() {
        final byte[] bytes = new byte[4];
        this._ircIllIllIIlI = 464663274;
        bytes[0] = (byte)(this._ircIllIllIIlI >>> 7);
        this._ircIllIllIIlI = -1671792580;
        bytes[1] = (byte)(this._ircIllIllIIlI >>> 8);
        this._ircIllIllIIlI = 454791589;
        bytes[2] = (byte)(this._ircIllIllIIlI >>> 2);
        this._ircIllIllIIlI = 487221729;
        bytes[3] = (byte)(this._ircIllIllIIlI >>> 22);
        return new String(bytes);
    }
}
