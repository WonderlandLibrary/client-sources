// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.net.packet;

import java.awt.Component;
import javax.swing.JOptionPane;
import java.awt.TrayIcon;
import javax.swing.JFrame;
import de.liquiddev.ircclient.net._ircIllIllIIlI;
import de.liquiddev.ircclient.net.IrcPacket;

public class Irc11PopupMessage implements IrcPacket
{
    public String message;
    
    @Override
    public void read(final _ircIllIllIIlI connection) {
        this.message = connection._ircIllIllIIlI();
    }
    
    @Override
    public void write(final _ircIllIllIIlI connection) {
        connection._irclllllIIlII(this.message);
    }
    
    @Override
    public void handle(final _ircIllIllIIlI connection) {
        final JFrame parentComponent;
        (parentComponent = new JFrame()).setAlwaysOnTop(true);
        JOptionPane.showMessageDialog(parentComponent, this.message, new _ircIIIlIlllII(this).toString(), TrayIcon.MessageType.ERROR.ordinal());
    }
}
