// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.api;

public interface IrcApi
{
    void onChatMessage(final String p0);
    
    void onPlayerChatMessage(final String p0, final String p1, final String p2);
    
    void onLocalPlayerChatMessage(final String p0, final String p1, final String p2);
    
    void onWhisperMessage(final String p0, final String p1, final boolean p2);
}
