// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.util;

final class _irclllllIIlII
{
    private int _ircIllIllIIlI;
    
    @Override
    public final String toString() {
        final byte[] bytes = new byte[7];
        this._ircIllIllIIlI = 475241983;
        bytes[0] = (byte)(this._ircIllIllIIlI >>> 16);
        this._ircIllIllIIlI = 1672499721;
        bytes[1] = (byte)(this._ircIllIllIIlI >>> 6);
        this._ircIllIllIIlI = 1158699654;
        bytes[2] = (byte)(this._ircIllIllIIlI >>> 14);
        this._ircIllIllIIlI = 695194491;
        bytes[3] = (byte)(this._ircIllIllIIlI >>> 19);
        this._ircIllIllIIlI = 219255195;
        bytes[4] = (byte)(this._ircIllIllIIlI >>> 11);
        this._ircIllIllIIlI = 901805322;
        bytes[5] = (byte)(this._ircIllIllIIlI >> 24);
        this._ircIllIllIIlI = -1263197771;
        bytes[6] = (byte)(this._ircIllIllIIlI >>> 3);
        return new String(bytes);
    }
}
