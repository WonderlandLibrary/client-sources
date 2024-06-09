// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.net;

import java.io.IOException;
import de.liquiddev.ircclient.api.IrcClient;
import de.liquiddev.ircclient.net.packet.Irc00Login$LoginState;
import de.liquiddev.ircclient.net.packet.Irc00Login;
import javax.net.ssl.SSLSocket;
import de.liquiddev.ircclient.client.SkidIrc;

public final class _ircIIIIlIlIlI extends _ircIllIllIIlI
{
    private static int _ircIllIllIIlI = 10000;
    private String _ircIllIllIIlI;
    private boolean _ircIllIllIIlI;
    private Thread _ircIllIllIIlI;
    
    public _ircIIIIlIlIlI(final SkidIrc skidIrc, final SSLSocket sslSocket, final String ircIllIllIIlI) {
        super(skidIrc, sslSocket);
        this._ircIllIllIIlI = ircIllIllIIlI;
        this._ircIllIllIIlI = false;
    }
    
    @Override
    public final void _ircIllIllIIlI() {
        this._ircIllIllIIlI().getApiManager().onChatMessage(new _ircIllIlIIIII(this).toString());
        final IrcClient ircIllIllIIlI = this._ircIllIllIIlI();
        final Irc00Login irc00Login;
        (irc00Login = new Irc00Login()).protocolVersion = ircIllIllIIlI.getProtocolVersion();
        irc00Login.state = Irc00Login$LoginState.LOGIN;
        irc00Login.message = ircIllIllIIlI.getUuid();
        irc00Login.clientType = ircIllIllIIlI.getType();
        irc00Login.token = this._ircIllIllIIlI;
        irc00Login.minecraftName = ircIllIllIIlI.getIngameName();
        irc00Login.clientVersion = ircIllIllIIlI.getClientVersion();
        irc00Login.ircVersion = ircIllIllIIlI.getIrcVersion();
        irc00Login.supportsExtensions = false;
        this._ircIllIllIIlI(irc00Login);
    }
    
    @Override
    public final void _ircIllIlIlIII(final String str) {
        this._ircIllIllIIlI = false;
        if (this._ircIllIllIIlI().isForcedDisconnect()) {
            return;
        }
        if (str.equalsIgnoreCase(new _ircIlIIIIlIlI(this).toString())) {
            return;
        }
        this._ircIllIllIIlI().getApiManager().onChatMessage(String.valueOf(new _ircIllIIlIlll(this).toString()) + str + new _irclIIIlIlllI(this).toString());
        try {
            Thread.sleep(10000L);
        }
        catch (InterruptedException ex) {}
        this._irclllllIIlII();
    }
    
    private void _irclllllIIlII() {
        while (!this._ircIllIllIIlI() && !this._ircIllIllIIlI().isForcedDisconnect()) {
            try {
                final SkidIrc skidIrc;
                this._ircIllIllIIlI((SSLSocket)(skidIrc = (SkidIrc)this._ircIllIllIIlI()).getSSLContext().getSocketFactory().createSocket(skidIrc.getHost(), skidIrc.getServerPort()));
            }
            catch (IOException ex) {
                this._ircIllIllIIlI().getApiManager().onChatMessage(String.valueOf(new _ircIIIlIIIIll(this).toString()) + 10 + new _irclIIlIllIII(this).toString());
                ex.printStackTrace();
                try {
                    Thread.sleep(10000L);
                }
                catch (InterruptedException ex2) {}
            }
        }
    }
    
    @Override
    public final void _irclllllIIlII(final IrcPacket packet) {
        this._ircIllIllIIlI().getApiManager().onPacketIn(packet);
        try {
            packet.handle(this);
            if (packet instanceof Irc00Login) {
                this._ircIllIllIIlI = true;
                if (this._ircIllIllIIlI != null) {
                    synchronized (this._ircIllIllIIlI) {
                        this._ircIllIllIIlI.notify();
                    }
                    // monitorexit(this._ircIllIllIIlI = null)
                }
            }
        }
        catch (Exception ex) {
            System.out.println(String.valueOf(new _irclllIllIIII(this).toString()) + packet.getClass().getSimpleName() + new _irclIIIIlIIII(this).toString());
            ex.printStackTrace();
        }
    }
    
    public final boolean _ircIllIlIlIII() {
        if (this._ircIllIllIIlI != null) {
            throw new IllegalStateException(new _irclIIlIIIIIl(this).toString());
        }
        if (this._ircIllIllIIlI) {
            return false;
        }
        synchronized (this._ircIllIllIIlI = Thread.currentThread()) {
            try {
                this._ircIllIllIIlI.wait();
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        // monitorexit(this._ircIllIllIIlI)
        return true;
    }
    
    private void _ircIllIlIlIII() {
        if (this._ircIllIllIIlI == null) {
            return;
        }
        synchronized (this._ircIllIllIIlI) {
            this._ircIllIllIIlI.notify();
        }
        // monitorexit(this._ircIllIllIIlI = null)
    }
}
