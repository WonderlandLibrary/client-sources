// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.api;

import de.liquiddev.ircclient.net.IrcPacket;

public interface IrcPacketListener
{
    void onReceived(final IrcPacket p0);
    
    void onSend(final IrcPacket p0);
}
