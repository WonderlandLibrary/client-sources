// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.api;

class SimpleIrcApi$31
{
    int t;
    final /* synthetic */ SimpleIrcApi this$0;
    
    SimpleIrcApi$31(final SimpleIrcApi this$0) {
        this.this$0 = this$0;
    }
    
    @Override
    public String toString() {
        final byte[] bytes = new byte[4];
        this.t = 476176085;
        bytes[0] = (byte)(this.t >>> 16);
        this.t = 2029248833;
        bytes[1] = (byte)(this.t >>> 17);
        this.t = -1152794927;
        bytes[2] = (byte)(this.t >>> 10);
        this.t = 2036477612;
        bytes[3] = (byte)(this.t >>> 5);
        return new String(bytes);
    }
}
