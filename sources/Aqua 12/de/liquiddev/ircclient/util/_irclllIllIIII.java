// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.util;

final class _irclllIllIIII
{
    private int _ircIllIllIIlI;
    
    @Override
    public final String toString() {
        final byte[] bytes = new byte[3];
        this._ircIllIllIIlI = 734168789;
        bytes[0] = (byte)(this._ircIllIllIIlI >>> 23);
        this._ircIllIllIIlI = -721268881;
        bytes[1] = (byte)(this._ircIllIllIIlI >>> 11);
        this._ircIllIllIIlI = -746009228;
        bytes[2] = (byte)(this._ircIllIllIIlI >>> 22);
        return new String(bytes);
    }
}
