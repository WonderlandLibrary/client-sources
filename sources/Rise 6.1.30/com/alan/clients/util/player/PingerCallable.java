package com.alan.clients.util.player;

import net.minecraft.client.multiplayer.ServerAddress;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.Callable;

public class PingerCallable implements Callable<Long> {
    private final SocketAddress address;

    public PingerCallable(String address) {
        ServerAddress serveraddress = ServerAddress.func_78860_a(address);
        this.address = new InetSocketAddress(serveraddress.getIP(), serveraddress.getPort());
    }

    @Override
    public Long call() {
        try {
            Socket socket = new Socket();
            long time = System.currentTimeMillis();
            socket.connect(this.address);
            socket.close();
            return System.currentTimeMillis() - time;
        } catch (Exception exception) {
            return 0L;
        }
    }
}
