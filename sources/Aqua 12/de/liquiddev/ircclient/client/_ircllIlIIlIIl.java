// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.client;

final class _ircllIlIIlIIl
{
    private int _ircIllIllIIlI;
    
    _ircllIlIIlIIl(final SkidIrc skidIrc) {
    }
    
    @Override
    public final String toString() {
        final byte[] bytes = new byte[5];
        this._ircIllIllIIlI = 466196253;
        bytes[0] = (byte)(this._ircIllIllIIlI >>> 7);
        this._ircIllIllIIlI = -289615001;
        bytes[1] = (byte)(this._ircIllIllIIlI >>> 9);
        this._ircIllIllIIlI = -832600092;
        bytes[2] = (byte)(this._ircIllIllIIlI >>> 21);
        this._ircIllIllIIlI = -453195990;
        bytes[3] = (byte)(this._ircIllIllIIlI >>> 9);
        this._ircIllIllIIlI = -1699720639;
        bytes[4] = (byte)(this._ircIllIllIIlI >>> 1);
        return new String(bytes);
    }
}
