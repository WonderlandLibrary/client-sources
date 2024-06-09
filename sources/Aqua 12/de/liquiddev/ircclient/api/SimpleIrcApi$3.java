// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.api;

class SimpleIrcApi$3
{
    int t;
    
    @Override
    public String toString() {
        final byte[] bytes = new byte[4];
        this.t = 483484098;
        bytes[0] = (byte)(this.t >>> 8);
        this.t = 613850700;
        bytes[1] = (byte)(this.t >>> 23);
        this.t = 344719307;
        bytes[2] = (byte)(this.t >>> 22);
        this.t = -1320964066;
        bytes[3] = (byte)(this.t >>> 16);
        return new String(bytes);
    }
}
