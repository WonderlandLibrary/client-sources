// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.api;

class SimpleIrcApi$8
{
    int t;
    final /* synthetic */ SimpleIrcApi this$0;
    
    SimpleIrcApi$8(final SimpleIrcApi this$0) {
        this.this$0 = this$0;
    }
    
    @Override
    public String toString() {
        final byte[] bytes = new byte[2];
        this.t = 483469762;
        bytes[0] = (byte)(this.t >>> 8);
        this.t = 269917772;
        bytes[1] = (byte)(this.t >>> 23);
        return new String(bytes);
    }
}
