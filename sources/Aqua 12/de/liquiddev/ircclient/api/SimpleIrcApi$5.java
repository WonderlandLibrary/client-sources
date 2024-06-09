// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.api;

class SimpleIrcApi$5
{
    int t;
    
    @Override
    public String toString() {
        final byte[] bytes = { 0 };
        this.t = 483473346;
        bytes[0] = (byte)(this.t >>> 8);
        return new String(bytes);
    }
}
