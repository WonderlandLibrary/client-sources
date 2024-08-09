/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.conn;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

@Deprecated
@Contract(threading=ThreadingBehavior.IMMUTABLE)
public final class MultihomePlainSocketFactory
implements SocketFactory {
    private static final MultihomePlainSocketFactory DEFAULT_FACTORY = new MultihomePlainSocketFactory();

    public static MultihomePlainSocketFactory getSocketFactory() {
        return DEFAULT_FACTORY;
    }

    private MultihomePlainSocketFactory() {
    }

    @Override
    public Socket createSocket() {
        return new Socket();
    }

    @Override
    public Socket connectSocket(Socket socket, String string, int n, InetAddress inetAddress, int n2, HttpParams httpParams) throws IOException {
        Args.notNull(string, "Target host");
        Args.notNull(httpParams, "HTTP parameters");
        Socket socket2 = socket;
        if (socket2 == null) {
            socket2 = this.createSocket();
        }
        if (inetAddress != null || n2 > 0) {
            InetSocketAddress inetSocketAddress = new InetSocketAddress(inetAddress, n2 > 0 ? n2 : 0);
            socket2.bind(inetSocketAddress);
        }
        int n3 = HttpConnectionParams.getConnectionTimeout(httpParams);
        InetAddress[] inetAddressArray = InetAddress.getAllByName(string);
        ArrayList<InetAddress> arrayList = new ArrayList<InetAddress>(inetAddressArray.length);
        arrayList.addAll(Arrays.asList(inetAddressArray));
        Collections.shuffle(arrayList);
        IOException iOException = null;
        for (InetAddress inetAddress2 : arrayList) {
            try {
                socket2.connect(new InetSocketAddress(inetAddress2, n), n3);
                break;
            } catch (SocketTimeoutException socketTimeoutException) {
                throw new ConnectTimeoutException("Connect to " + inetAddress2 + " timed out");
            } catch (IOException iOException2) {
                socket2 = new Socket();
                iOException = iOException2;
            }
        }
        if (iOException != null) {
            throw iOException;
        }
        return socket2;
    }

    @Override
    public boolean isSecure(Socket socket) throws IllegalArgumentException {
        Args.notNull(socket, "Socket");
        Asserts.check(!socket.isClosed(), "Socket is closed");
        return true;
    }
}

