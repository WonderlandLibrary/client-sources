/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal;

import io.netty.util.internal.PlatformDependent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Enumeration;

public final class SocketUtils {
    private SocketUtils() {
    }

    public static void connect(Socket socket, SocketAddress socketAddress, int n) throws IOException {
        try {
            AccessController.doPrivileged(new PrivilegedExceptionAction<Void>(socket, socketAddress, n){
                final Socket val$socket;
                final SocketAddress val$remoteAddress;
                final int val$timeout;
                {
                    this.val$socket = socket;
                    this.val$remoteAddress = socketAddress;
                    this.val$timeout = n;
                }

                @Override
                public Void run() throws IOException {
                    this.val$socket.connect(this.val$remoteAddress, this.val$timeout);
                    return null;
                }

                @Override
                public Object run() throws Exception {
                    return this.run();
                }
            });
        } catch (PrivilegedActionException privilegedActionException) {
            throw (IOException)privilegedActionException.getCause();
        }
    }

    public static void bind(Socket socket, SocketAddress socketAddress) throws IOException {
        try {
            AccessController.doPrivileged(new PrivilegedExceptionAction<Void>(socket, socketAddress){
                final Socket val$socket;
                final SocketAddress val$bindpoint;
                {
                    this.val$socket = socket;
                    this.val$bindpoint = socketAddress;
                }

                @Override
                public Void run() throws IOException {
                    this.val$socket.bind(this.val$bindpoint);
                    return null;
                }

                @Override
                public Object run() throws Exception {
                    return this.run();
                }
            });
        } catch (PrivilegedActionException privilegedActionException) {
            throw (IOException)privilegedActionException.getCause();
        }
    }

    public static boolean connect(SocketChannel socketChannel, SocketAddress socketAddress) throws IOException {
        try {
            return AccessController.doPrivileged(new PrivilegedExceptionAction<Boolean>(socketChannel, socketAddress){
                final SocketChannel val$socketChannel;
                final SocketAddress val$remoteAddress;
                {
                    this.val$socketChannel = socketChannel;
                    this.val$remoteAddress = socketAddress;
                }

                @Override
                public Boolean run() throws IOException {
                    return this.val$socketChannel.connect(this.val$remoteAddress);
                }

                @Override
                public Object run() throws Exception {
                    return this.run();
                }
            });
        } catch (PrivilegedActionException privilegedActionException) {
            throw (IOException)privilegedActionException.getCause();
        }
    }

    public static void bind(SocketChannel socketChannel, SocketAddress socketAddress) throws IOException {
        try {
            AccessController.doPrivileged(new PrivilegedExceptionAction<Void>(socketChannel, socketAddress){
                final SocketChannel val$socketChannel;
                final SocketAddress val$address;
                {
                    this.val$socketChannel = socketChannel;
                    this.val$address = socketAddress;
                }

                @Override
                public Void run() throws IOException {
                    this.val$socketChannel.bind(this.val$address);
                    return null;
                }

                @Override
                public Object run() throws Exception {
                    return this.run();
                }
            });
        } catch (PrivilegedActionException privilegedActionException) {
            throw (IOException)privilegedActionException.getCause();
        }
    }

    public static SocketChannel accept(ServerSocketChannel serverSocketChannel) throws IOException {
        try {
            return AccessController.doPrivileged(new PrivilegedExceptionAction<SocketChannel>(serverSocketChannel){
                final ServerSocketChannel val$serverSocketChannel;
                {
                    this.val$serverSocketChannel = serverSocketChannel;
                }

                @Override
                public SocketChannel run() throws IOException {
                    return this.val$serverSocketChannel.accept();
                }

                @Override
                public Object run() throws Exception {
                    return this.run();
                }
            });
        } catch (PrivilegedActionException privilegedActionException) {
            throw (IOException)privilegedActionException.getCause();
        }
    }

    public static void bind(DatagramChannel datagramChannel, SocketAddress socketAddress) throws IOException {
        try {
            AccessController.doPrivileged(new PrivilegedExceptionAction<Void>(datagramChannel, socketAddress){
                final DatagramChannel val$networkChannel;
                final SocketAddress val$address;
                {
                    this.val$networkChannel = datagramChannel;
                    this.val$address = socketAddress;
                }

                @Override
                public Void run() throws IOException {
                    this.val$networkChannel.bind(this.val$address);
                    return null;
                }

                @Override
                public Object run() throws Exception {
                    return this.run();
                }
            });
        } catch (PrivilegedActionException privilegedActionException) {
            throw (IOException)privilegedActionException.getCause();
        }
    }

    public static SocketAddress localSocketAddress(ServerSocket serverSocket) {
        return AccessController.doPrivileged(new PrivilegedAction<SocketAddress>(serverSocket){
            final ServerSocket val$socket;
            {
                this.val$socket = serverSocket;
            }

            @Override
            public SocketAddress run() {
                return this.val$socket.getLocalSocketAddress();
            }

            @Override
            public Object run() {
                return this.run();
            }
        });
    }

    public static InetAddress addressByName(String string) throws UnknownHostException {
        try {
            return AccessController.doPrivileged(new PrivilegedExceptionAction<InetAddress>(string){
                final String val$hostname;
                {
                    this.val$hostname = string;
                }

                @Override
                public InetAddress run() throws UnknownHostException {
                    return InetAddress.getByName(this.val$hostname);
                }

                @Override
                public Object run() throws Exception {
                    return this.run();
                }
            });
        } catch (PrivilegedActionException privilegedActionException) {
            throw (UnknownHostException)privilegedActionException.getCause();
        }
    }

    public static InetAddress[] allAddressesByName(String string) throws UnknownHostException {
        try {
            return AccessController.doPrivileged(new PrivilegedExceptionAction<InetAddress[]>(string){
                final String val$hostname;
                {
                    this.val$hostname = string;
                }

                @Override
                public InetAddress[] run() throws UnknownHostException {
                    return InetAddress.getAllByName(this.val$hostname);
                }

                @Override
                public Object run() throws Exception {
                    return this.run();
                }
            });
        } catch (PrivilegedActionException privilegedActionException) {
            throw (UnknownHostException)privilegedActionException.getCause();
        }
    }

    public static InetSocketAddress socketAddress(String string, int n) {
        return AccessController.doPrivileged(new PrivilegedAction<InetSocketAddress>(string, n){
            final String val$hostname;
            final int val$port;
            {
                this.val$hostname = string;
                this.val$port = n;
            }

            @Override
            public InetSocketAddress run() {
                return new InetSocketAddress(this.val$hostname, this.val$port);
            }

            @Override
            public Object run() {
                return this.run();
            }
        });
    }

    public static Enumeration<InetAddress> addressesFromNetworkInterface(NetworkInterface networkInterface) {
        return AccessController.doPrivileged(new PrivilegedAction<Enumeration<InetAddress>>(networkInterface){
            final NetworkInterface val$intf;
            {
                this.val$intf = networkInterface;
            }

            @Override
            public Enumeration<InetAddress> run() {
                return this.val$intf.getInetAddresses();
            }

            @Override
            public Object run() {
                return this.run();
            }
        });
    }

    public static InetAddress loopbackAddress() {
        return AccessController.doPrivileged(new PrivilegedAction<InetAddress>(){

            @Override
            public InetAddress run() {
                if (PlatformDependent.javaVersion() >= 7) {
                    return InetAddress.getLoopbackAddress();
                }
                try {
                    return InetAddress.getByName(null);
                } catch (UnknownHostException unknownHostException) {
                    throw new IllegalStateException(unknownHostException);
                }
            }

            @Override
            public Object run() {
                return this.run();
            }
        });
    }

    public static byte[] hardwareAddressFromNetworkInterface(NetworkInterface networkInterface) throws SocketException {
        try {
            return AccessController.doPrivileged(new PrivilegedExceptionAction<byte[]>(networkInterface){
                final NetworkInterface val$intf;
                {
                    this.val$intf = networkInterface;
                }

                @Override
                public byte[] run() throws SocketException {
                    return this.val$intf.getHardwareAddress();
                }

                @Override
                public Object run() throws Exception {
                    return this.run();
                }
            });
        } catch (PrivilegedActionException privilegedActionException) {
            throw (SocketException)privilegedActionException.getCause();
        }
    }
}

