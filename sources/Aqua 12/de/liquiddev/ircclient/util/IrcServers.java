// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.util;

import java.io.DataInputStream;
import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Iterator;
import java.util.Collections;
import java.util.Arrays;
import java.util.List;

public class IrcServers
{
    private static final List _ircIllIllIIlI;
    private static final int _ircIllIllIIlI = 25000;
    
    static {
        _ircIllIllIIlI = Collections.unmodifiableList((List<?>)Arrays.asList(new _irclIIlIlllll().toString(), new _irclllIIlIlIl().toString(), new _ircIIlIIIlIlI().toString(), new _ircIIIlIlllII().toString()));
    }
    
    public static List getDefaultServers() {
        return IrcServers._ircIllIllIIlI;
    }
    
    public static int getDefaultPort() {
        return 25000;
    }
    
    public static String getOnlineServer(final List servers, final int port) {
        final Iterator<String> iterator = servers.iterator();
        while (iterator.hasNext()) {
            final String s;
            if (_ircIllIllIIlI(s = iterator.next(), port)) {
                return s;
            }
        }
        return servers.get(0);
    }
    
    private static boolean _ircIllIllIIlI(String utf, final int n) {
        try {
            Throwable t = null;
            try {
                final Socket socket = new Socket();
                try {
                    socket.connect(new InetSocketAddress(utf, n + 1), (int)TimeUnit.SECONDS.toMillis(5L));
                    utf = new DataInputStream(socket.getInputStream()).readUTF();
                    System.out.println(utf);
                    return utf.equalsIgnoreCase(new _ircIllIIIIllI().toString());
                }
                finally {
                    socket.close();
                }
            }
            finally {
                if (t == null) {
                    final Throwable exception;
                    t = exception;
                }
                else {
                    final Throwable exception;
                    if (t != exception) {
                        t.addSuppressed(exception);
                    }
                }
            }
        }
        catch (Exception ex) {
            return false;
        }
    }
}
