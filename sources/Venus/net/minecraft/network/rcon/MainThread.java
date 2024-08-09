/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.rcon;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.rcon.ClientThread;
import net.minecraft.network.rcon.IServer;
import net.minecraft.network.rcon.RConThread;
import net.minecraft.server.dedicated.ServerProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainThread
extends RConThread {
    private static final Logger field_232652_d_ = LogManager.getLogger();
    private final ServerSocket serverSocket;
    private final String rconPassword;
    private final List<ClientThread> clientThreads = Lists.newArrayList();
    private final IServer field_232653_j_;

    private MainThread(IServer iServer, ServerSocket serverSocket, String string) {
        super("RCON Listener");
        this.field_232653_j_ = iServer;
        this.serverSocket = serverSocket;
        this.rconPassword = string;
    }

    private void cleanClientThreadsMap() {
        this.clientThreads.removeIf(MainThread::lambda$cleanClientThreadsMap$0);
    }

    @Override
    public void run() {
        try {
            while (this.running) {
                try {
                    Socket socket = this.serverSocket.accept();
                    ClientThread clientThread = new ClientThread(this.field_232653_j_, this.rconPassword, socket);
                    clientThread.func_241832_a();
                    this.clientThreads.add(clientThread);
                    this.cleanClientThreadsMap();
                } catch (SocketTimeoutException socketTimeoutException) {
                    this.cleanClientThreadsMap();
                } catch (IOException iOException) {
                    if (!this.running) continue;
                    field_232652_d_.info("IO exception: ", (Throwable)iOException);
                }
            }
        } finally {
            this.func_232655_a_(this.serverSocket);
        }
    }

    @Nullable
    public static MainThread func_242130_a(IServer iServer) {
        int n;
        ServerProperties serverProperties = iServer.getServerProperties();
        String string = iServer.getHostname();
        if (string.isEmpty()) {
            string = "0.0.0.0";
        }
        if (0 < (n = serverProperties.rconPort) && 65535 >= n) {
            String string2 = serverProperties.rconPassword;
            if (string2.isEmpty()) {
                field_232652_d_.warn("No rcon password set in server.properties, rcon disabled!");
                return null;
            }
            try {
                ServerSocket serverSocket = new ServerSocket(n, 0, InetAddress.getByName(string));
                serverSocket.setSoTimeout(500);
                MainThread mainThread = new MainThread(iServer, serverSocket, string2);
                if (!mainThread.func_241832_a()) {
                    return null;
                }
                field_232652_d_.info("RCON running on {}:{}", (Object)string, (Object)n);
                return mainThread;
            } catch (IOException iOException) {
                field_232652_d_.warn("Unable to initialise RCON on {}:{}", (Object)string, (Object)n, (Object)iOException);
                return null;
            }
        }
        field_232652_d_.warn("Invalid rcon port {} found in server.properties, rcon disabled!", (Object)n);
        return null;
    }

    @Override
    public void func_219591_b() {
        this.running = false;
        this.func_232655_a_(this.serverSocket);
        super.func_219591_b();
        for (ClientThread clientThread : this.clientThreads) {
            if (!clientThread.isRunning()) continue;
            clientThread.func_219591_b();
        }
        this.clientThreads.clear();
    }

    private void func_232655_a_(ServerSocket serverSocket) {
        field_232652_d_.debug("closeSocket: {}", (Object)serverSocket);
        try {
            serverSocket.close();
        } catch (IOException iOException) {
            field_232652_d_.warn("Failed to close socket", (Throwable)iOException);
        }
    }

    private static boolean lambda$cleanClientThreadsMap$0(ClientThread clientThread) {
        return !clientThread.isRunning();
    }
}

