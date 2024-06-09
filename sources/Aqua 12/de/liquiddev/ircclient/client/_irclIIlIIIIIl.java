// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.client;

final class _irclIIlIIIIIl
{
    private int _ircIllIllIIlI;
    
    @Override
    public final String toString() {
        final byte[] bytes = new byte[6];
        this._ircIllIllIIlI = 1407024028;
        bytes[0] = (byte)(this._ircIllIllIIlI >> 24);
        this._ircIllIllIIlI = -154659135;
        bytes[1] = (byte)(this._ircIllIllIIlI >>> 4);
        this._ircIllIllIIlI = 91224510;
        bytes[2] = (byte)(this._ircIllIllIIlI >>> 16);
        this._ircIllIllIIlI = 787983811;
        bytes[3] = (byte)(this._ircIllIllIIlI >>> 21);
        this._ircIllIllIIlI = 918587048;
        bytes[4] = (byte)(this._ircIllIllIIlI >>> 20);
        this._ircIllIllIIlI = -1891800029;
        bytes[5] = (byte)(this._ircIllIllIIlI >>> 21);
        return new String(bytes);
    }
}
