// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.client;

final class _ircIlIIlIllIl
{
    private int _ircIllIllIIlI;
    
    @Override
    public final String toString() {
        final byte[] bytes = new byte[4];
        this._ircIllIllIIlI = 1188920220;
        bytes[0] = (byte)(this._ircIllIllIIlI >> 24);
        this._ircIllIllIIlI = -154659135;
        bytes[1] = (byte)(this._ircIllIllIIlI >>> 4);
        this._ircIllIllIIlI = 90569150;
        bytes[2] = (byte)(this._ircIllIllIIlI >>> 16);
        this._ircIllIllIIlI = 790080963;
        bytes[3] = (byte)(this._ircIllIllIIlI >>> 21);
        return new String(bytes);
    }
}
