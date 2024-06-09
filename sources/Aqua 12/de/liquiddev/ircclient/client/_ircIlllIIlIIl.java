// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.client;

final class _ircIlllIIlIIl
{
    private int _ircIllIllIIlI;
    
    _ircIlllIIlIIl(final SkidIrc skidIrc) {
    }
    
    @Override
    public final String toString() {
        final byte[] bytes = new byte[4];
        this._ircIllIllIIlI = 836062199;
        bytes[0] = (byte)(this._ircIllIllIIlI >>> 23);
        this._ircIllIllIIlI = -1746125443;
        bytes[1] = (byte)(this._ircIllIllIIlI >>> 13);
        this._ircIllIllIIlI = -1205763624;
        bytes[2] = (byte)(this._ircIllIllIIlI >>> 23);
        this._ircIllIllIIlI = 1412621006;
        bytes[3] = (byte)(this._ircIllIllIIlI >>> 15);
        return new String(bytes);
    }
}
