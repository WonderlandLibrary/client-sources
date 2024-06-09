// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.api;

import de.liquiddev.ircclient.client.IrcPlayer;

public interface CustomDataListener
{
    void onCustomDataReceived(final IrcPlayer p0, final String p1, final byte[] p2);
}
