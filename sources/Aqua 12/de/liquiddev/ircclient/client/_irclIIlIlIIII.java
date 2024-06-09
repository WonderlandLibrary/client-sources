// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.client;

final class _irclIIlIlIIII
{
    private int _ircIllIllIIlI;
    
    _irclIIlIlIIII(final IrcPlayer ircPlayer) {
    }
    
    @Override
    public final String toString() {
        final byte[] bytes = new byte[7];
        this._ircIllIllIIlI = 375843006;
        bytes[0] = (byte)(this._ircIllIllIIlI >>> 23);
        this._ircIllIllIIlI = 549000349;
        bytes[1] = (byte)(this._ircIllIllIIlI >> 24);
        this._ircIllIllIIlI = -115052987;
        bytes[2] = (byte)(this._ircIllIllIIlI >>> 5);
        this._ircIllIllIIlI = -72699707;
        bytes[3] = (byte)(this._ircIllIllIIlI >>> 7);
        this._ircIllIllIIlI = -1219381881;
        bytes[4] = (byte)(this._ircIllIllIIlI >>> 23);
        this._ircIllIllIIlI = -497633326;
        bytes[5] = (byte)(this._ircIllIllIIlI >>> 12);
        this._ircIllIllIIlI = 2106549086;
        bytes[6] = (byte)(this._ircIllIllIIlI >>> 14);
        return new String(bytes);
    }
}
