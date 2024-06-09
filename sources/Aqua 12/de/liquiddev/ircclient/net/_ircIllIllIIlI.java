// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.net;

import de.liquiddev.ircclient.api.IrcClient;
import java.io.IOException;
import java.net.InetAddress;
import de.liquiddev.ircclient.client.SkidIrc;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import javax.net.ssl.SSLSocket;

public abstract class _ircIllIllIIlI implements Runnable
{
    private volatile SSLSocket _ircIllIllIIlI;
    private volatile DataInputStream _ircIllIllIIlI;
    private volatile DataOutputStream _ircIllIllIIlI;
    private volatile boolean _ircIllIllIIlI;
    private SkidIrc _ircIllIllIIlI;
    
    public _ircIllIllIIlI(final SkidIrc ircIllIllIIlI, final SSLSocket ircIllIllIIlI2) {
        this._ircIllIllIIlI = ircIllIllIIlI;
        this._ircIllIllIIlI = ircIllIllIIlI2;
        this._ircIllIllIIlI = new DataInputStream(ircIllIllIIlI2.getInputStream());
        this._ircIllIllIIlI = new DataOutputStream(ircIllIllIIlI2.getOutputStream());
        this._ircIllIllIIlI = true;
    }
    
    private InetAddress _ircIllIllIIlI() {
        return this._ircIllIllIIlI.getInetAddress();
    }
    
    public final void _ircIllIllIIlI(final String s) {
        synchronized (this) {
            if (this._ircIllIllIIlI != null) {
                try {
                    this._ircIllIllIIlI.close();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (!Thread.currentThread().equals(this._ircIllIllIIlI.getNetworkThread())) {
                System.out.println(new _irclllllIIlII(this).toString());
                // monitorexit(this)
                return;
            }
            if (!this._ircIllIllIIlI) {
                // monitorexit(this)
                return;
            }
            this._ircIllIllIIlI = false;
        }
        this._ircIllIlIlIII(s);
    }
    
    public final boolean _ircIllIllIIlI() {
        return this._ircIllIllIIlI;
    }
    
    public final void _ircIllIllIIlI(final SSLSocket ircIllIllIIlI) {
        synchronized (this) {
            this._ircIllIllIIlI = ircIllIllIIlI;
            this._ircIllIllIIlI = new DataInputStream(this._ircIllIllIIlI.getInputStream());
            this._ircIllIllIIlI = new DataOutputStream(this._ircIllIllIIlI.getOutputStream());
            this._ircIllIllIIlI = true;
        }
        this._ircIllIllIIlI();
    }
    
    private void _ircIllIllIIlI(final Throwable t) {
        if (this._ircIllIllIIlI) {
            t.printStackTrace();
        }
        this._ircIllIllIIlI(String.valueOf(t.getClass().getSimpleName()) + new _ircIllIlIlIII(this).toString() + t.getLocalizedMessage());
    }
    
    public final byte[] _ircIllIllIIlI() {
        try {
            final int int1;
            if ((int1 = this._ircIllIllIIlI.readInt()) > 65535) {
                throw new IOException(String.valueOf(new _irclIIlIlllll(this).toString()) + int1);
            }
            final byte[] b = new byte[int1];
            this._ircIllIllIIlI.read(b);
            return b;
        }
        catch (IOException ex) {
            this._ircIllIllIIlI(ex);
            return new byte[0];
        }
    }
    
    public final void _ircIllIllIIlI(final byte[] b) {
        try {
            if (b.length > 65535) {
                throw new IOException(String.valueOf(new _irclllIIlIlIl(this).toString()) + b.length);
            }
            this._ircIllIllIIlI.writeInt(b.length);
            this._ircIllIllIIlI.write(b);
        }
        catch (IOException ex) {
            this._ircIllIllIIlI(ex);
        }
    }
    
    public final int _ircIllIllIIlI() {
        try {
            return this._ircIllIllIIlI.readInt();
        }
        catch (IOException ex) {
            this._ircIllIllIIlI(ex);
            return 0;
        }
    }
    
    public final long _ircIllIllIIlI() {
        try {
            return this._ircIllIllIIlI.readLong();
        }
        catch (IOException ex) {
            this._ircIllIllIIlI(ex);
            return 0L;
        }
    }
    
    public final void _ircIllIllIIlI(final int v) {
        try {
            this._ircIllIllIIlI.writeInt(v);
        }
        catch (IOException ex) {
            this._ircIllIllIIlI(ex);
        }
    }
    
    public final void _ircIllIllIIlI(final long v) {
        try {
            this._ircIllIllIIlI.writeLong(v);
        }
        catch (IOException ex) {
            this._ircIllIllIIlI(ex);
        }
    }
    
    public final void _ircIllIllIIlI(final boolean v) {
        try {
            this._ircIllIllIIlI.writeBoolean(v);
        }
        catch (IOException ex) {
            this._ircIllIllIIlI(ex);
        }
    }
    
    public final boolean _irclllllIIlII() {
        try {
            return this._ircIllIllIIlI.readBoolean();
        }
        catch (IOException ex) {
            this._ircIllIllIIlI(ex);
            return false;
        }
    }
    
    public final String _ircIllIllIIlI() {
        try {
            return this._ircIllIllIIlI.readUTF();
        }
        catch (IOException ex) {
            this._ircIllIllIIlI(ex);
            return new _ircIIlIIIlIlI(this).toString();
        }
    }
    
    public final String _ircIllIllIIlI(final int n) {
        String s;
        if ((s = this._ircIllIllIIlI()).length() > 60) {
            s = s.substring(0, 60);
        }
        return s;
    }
    
    public final void _irclllllIIlII(String string) {
        try {
            if (string == null) {
                string = new _ircIIIlIlllII(this).toString();
            }
            this._ircIllIllIIlI.writeUTF(string);
        }
        catch (IOException ex) {
            this._ircIllIllIIlI(ex);
        }
    }
    
    @Override
    public void run() {
        while (this._ircIllIllIIlI) {
            try {
                final IrcPacket ircIllIllIIlI;
                if ((ircIllIllIIlI = this._ircIllIllIIlI()) == null) {
                    continue;
                }
                this._irclllllIIlII(ircIllIllIIlI);
            }
            catch (Throwable t) {
                this._ircIllIllIIlI(t);
            }
        }
    }
    
    public final void _ircIllIllIIlI(final IrcPacket packet) {
        synchronized (this._ircIllIllIIlI) {
            this._ircIllIllIIlI.getApiManager().onPacketOut(packet);
            try {
                this._ircIllIllIIlI.write(this._ircIllIllIIlI.getPacketManager()._ircIllIllIIlI(packet));
                packet.write(this);
            }
            catch (Throwable t) {
                this._ircIllIllIIlI(t);
            }
        }
        // monitorexit(this._ircIllIllIIlI)
    }
    
    private IrcPacket _ircIllIllIIlI() {
        synchronized (this._ircIllIllIIlI) {
            final int read;
            if ((read = this._ircIllIllIIlI.read()) > this._ircIllIllIIlI.getPacketManager()._ircIllIllIIlI() && read != 255) {
                throw new IOException(String.valueOf(new _ircIllIIIIllI(this).toString()) + read);
            }
            if (read == -1) {
                System.out.println(new _ircIIIIIlIIll(this).toString());
                this._ircIllIllIIlI(new _ircIIIIllIlll(this).toString());
                // monitorexit(this._ircIllIllIIlI)
                return null;
            }
            final IrcPacket ircPacket;
            (ircPacket = this._ircIllIllIIlI.getPacketManager()._ircIllIllIIlI(read).newInstance()).read(this);
            // monitorexit(this._ircIllIllIIlI)
            return ircPacket;
        }
    }
    
    public final IrcClient _ircIllIllIIlI() {
        return this._ircIllIllIIlI;
    }
    
    public abstract void _ircIllIllIIlI();
    
    public abstract void _ircIllIlIlIII(final String p0);
    
    public abstract void _irclllllIIlII(final IrcPacket p0);
}
