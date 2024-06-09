// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.api;

class SimpleIrcApi$22
{
    int t;
    final /* synthetic */ SimpleIrcApi this$0;
    
    SimpleIrcApi$22(final SimpleIrcApi this$0) {
        this.this$0 = this$0;
    }
    
    @Override
    public String toString() {
        final byte[] bytes = new byte[2];
        this.t = 473423573;
        bytes[0] = (byte)(this.t >>> 16);
        this.t = 2025316673;
        bytes[1] = (byte)(this.t >>> 17);
        return new String(bytes);
    }
}
