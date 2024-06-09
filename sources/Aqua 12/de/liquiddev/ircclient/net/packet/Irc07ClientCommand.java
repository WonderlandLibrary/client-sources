// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.net.packet;

import de.liquiddev.ircclient.net._ircIllIllIIlI;
import de.liquiddev.ircclient.net.IrcPacket;

public class Irc07ClientCommand implements IrcPacket
{
    public String command;
    
    @Override
    public void read(final _ircIllIllIIlI connection) {
        this.command = connection._ircIllIllIIlI();
    }
    
    @Override
    public void write(final _ircIllIllIIlI connection) {
        connection._irclllllIIlII(this.command);
    }
    
    @Override
    public void handle(final _ircIllIllIIlI connection) {
    }
}
