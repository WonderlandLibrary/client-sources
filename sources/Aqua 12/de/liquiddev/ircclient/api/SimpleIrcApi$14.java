// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.api;

class SimpleIrcApi$14
{
    int t;
    final /* synthetic */ SimpleIrcApi this$0;
    
    SimpleIrcApi$14(final SimpleIrcApi this$0) {
        this.this$0 = this$0;
    }
    
    @Override
    public String toString() {
        final byte[] bytes = new byte[6];
        this.t = 473161429;
        bytes[0] = (byte)(this.t >>> 16);
        this.t = 2023350593;
        bytes[1] = (byte)(this.t >>> 17);
        this.t = -1152794927;
        bytes[2] = (byte)(this.t >>> 10);
        this.t = 2036477036;
        bytes[3] = (byte)(this.t >>> 5);
        this.t = 1393223022;
        bytes[4] = (byte)(this.t >>> 19);
        this.t = -1540985607;
        bytes[5] = (byte)(this.t >>> 8);
        return new String(bytes);
    }
}
