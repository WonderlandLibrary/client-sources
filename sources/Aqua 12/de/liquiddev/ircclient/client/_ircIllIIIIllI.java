// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.client;

final class _ircIllIIIIllI
{
    private int _ircIllIllIIlI;
    
    @Override
    public final String toString() {
        final byte[] bytes = new byte[4];
        this._ircIllIllIIlI = 1272806300;
        bytes[0] = (byte)(this._ircIllIllIIlI >> 24);
        this._ircIllIllIIlI = -154659087;
        bytes[1] = (byte)(this._ircIllIllIIlI >>> 4);
        this._ircIllIllIIlI = 90962366;
        bytes[2] = (byte)(this._ircIllIllIIlI >>> 16);
        this._ircIllIllIIlI = 779595203;
        bytes[3] = (byte)(this._ircIllIllIIlI >>> 21);
        return new String(bytes);
    }
}
