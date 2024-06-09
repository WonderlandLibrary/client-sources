// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.util;

final class _ircIllIlIlIII
{
    private int _ircIllIllIIlI;
    
    @Override
    public final String toString() {
        final byte[] bytes = new byte[5];
        this._ircIllIllIIlI = 475373055;
        bytes[0] = (byte)(this._ircIllIllIIlI >>> 16);
        this._ircIllIllIIlI = 1672500489;
        bytes[1] = (byte)(this._ircIllIllIIlI >>> 6);
        this._ircIllIllIIlI = 1158781574;
        bytes[2] = (byte)(this._ircIllIllIIlI >>> 14);
        this._ircIllIllIIlI = 695194491;
        bytes[3] = (byte)(this._ircIllIllIIlI >>> 19);
        this._ircIllIllIIlI = 219267483;
        bytes[4] = (byte)(this._ircIllIllIIlI >>> 11);
        return new String(bytes);
    }
}
