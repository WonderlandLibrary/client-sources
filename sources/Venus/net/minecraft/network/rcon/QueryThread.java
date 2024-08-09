/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.rcon;

import com.google.common.collect.Maps;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.PortUnreachableException;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.network.rcon.IServer;
import net.minecraft.network.rcon.RConOutputStream;
import net.minecraft.network.rcon.RConThread;
import net.minecraft.network.rcon.RConUtils;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class QueryThread
extends RConThread {
    private static final Logger field_232648_d_ = LogManager.getLogger();
    private long lastAuthCheckTime;
    private final int queryPort;
    private final int serverPort;
    private final int maxPlayers;
    private final String serverMotd;
    private final String worldName;
    private DatagramSocket querySocket;
    private final byte[] buffer = new byte[1460];
    private String queryHostname;
    private String serverHostname;
    private final Map<SocketAddress, Auth> queryClients;
    private final RConOutputStream output;
    private long lastQueryResponseTime;
    private final IServer field_232649_r_;

    private QueryThread(IServer iServer, int n) {
        super("Query Listener");
        this.field_232649_r_ = iServer;
        this.queryPort = n;
        this.serverHostname = iServer.getHostname();
        this.serverPort = iServer.getPort();
        this.serverMotd = iServer.getMotd();
        this.maxPlayers = iServer.getMaxPlayers();
        this.worldName = iServer.func_230542_k__();
        this.lastQueryResponseTime = 0L;
        this.queryHostname = "0.0.0.0";
        if (!this.serverHostname.isEmpty() && !this.queryHostname.equals(this.serverHostname)) {
            this.queryHostname = this.serverHostname;
        } else {
            this.serverHostname = "0.0.0.0";
            try {
                InetAddress inetAddress = InetAddress.getLocalHost();
                this.queryHostname = inetAddress.getHostAddress();
            } catch (UnknownHostException unknownHostException) {
                field_232648_d_.warn("Unable to determine local host IP, please set server-ip in server.properties", (Throwable)unknownHostException);
            }
        }
        this.output = new RConOutputStream(1460);
        this.queryClients = Maps.newHashMap();
    }

    @Nullable
    public static QueryThread func_242129_a(IServer iServer) {
        int n = iServer.getServerProperties().queryPort;
        if (0 < n && 65535 >= n) {
            QueryThread queryThread = new QueryThread(iServer, n);
            return !queryThread.func_241832_a() ? null : queryThread;
        }
        field_232648_d_.warn("Invalid query port {} found in server.properties (queries disabled)", (Object)n);
        return null;
    }

    private void sendResponsePacket(byte[] byArray, DatagramPacket datagramPacket) throws IOException {
        this.querySocket.send(new DatagramPacket(byArray, byArray.length, datagramPacket.getSocketAddress()));
    }

    private boolean parseIncomingPacket(DatagramPacket datagramPacket) throws IOException {
        byte[] byArray = datagramPacket.getData();
        int n = datagramPacket.getLength();
        SocketAddress socketAddress = datagramPacket.getSocketAddress();
        field_232648_d_.debug("Packet len {} [{}]", (Object)n, (Object)socketAddress);
        if (3 <= n && -2 == byArray[0] && -3 == byArray[1]) {
            field_232648_d_.debug("Packet '{}' [{}]", (Object)RConUtils.getByteAsHexString(byArray[2]), (Object)socketAddress);
            switch (byArray[2]) {
                case 0: {
                    if (!this.verifyClientAuth(datagramPacket).booleanValue()) {
                        field_232648_d_.debug("Invalid challenge [{}]", (Object)socketAddress);
                        return true;
                    }
                    if (15 == n) {
                        this.sendResponsePacket(this.createQueryResponse(datagramPacket), datagramPacket);
                        field_232648_d_.debug("Rules [{}]", (Object)socketAddress);
                    } else {
                        RConOutputStream rConOutputStream = new RConOutputStream(1460);
                        rConOutputStream.writeInt(0);
                        rConOutputStream.writeByteArray(this.getRequestID(datagramPacket.getSocketAddress()));
                        rConOutputStream.writeString(this.serverMotd);
                        rConOutputStream.writeString("SMP");
                        rConOutputStream.writeString(this.worldName);
                        rConOutputStream.writeString(Integer.toString(this.field_232649_r_.getCurrentPlayerCount()));
                        rConOutputStream.writeString(Integer.toString(this.maxPlayers));
                        rConOutputStream.writeShort((short)this.serverPort);
                        rConOutputStream.writeString(this.queryHostname);
                        this.sendResponsePacket(rConOutputStream.toByteArray(), datagramPacket);
                        field_232648_d_.debug("Status [{}]", (Object)socketAddress);
                    }
                }
                default: {
                    return false;
                }
                case 9: 
            }
            this.sendAuthChallenge(datagramPacket);
            field_232648_d_.debug("Challenge [{}]", (Object)socketAddress);
            return false;
        }
        field_232648_d_.debug("Invalid packet [{}]", (Object)socketAddress);
        return true;
    }

    private byte[] createQueryResponse(DatagramPacket datagramPacket) throws IOException {
        String[] stringArray;
        long l = Util.milliTime();
        if (l < this.lastQueryResponseTime + 5000L) {
            byte[] byArray = this.output.toByteArray();
            byte[] byArray2 = this.getRequestID(datagramPacket.getSocketAddress());
            byArray[1] = byArray2[0];
            byArray[2] = byArray2[1];
            byArray[3] = byArray2[2];
            byArray[4] = byArray2[3];
            return byArray;
        }
        this.lastQueryResponseTime = l;
        this.output.reset();
        this.output.writeInt(0);
        this.output.writeByteArray(this.getRequestID(datagramPacket.getSocketAddress()));
        this.output.writeString("splitnum");
        this.output.writeInt(128);
        this.output.writeInt(0);
        this.output.writeString("hostname");
        this.output.writeString(this.serverMotd);
        this.output.writeString("gametype");
        this.output.writeString("SMP");
        this.output.writeString("game_id");
        this.output.writeString("MINECRAFT");
        this.output.writeString("version");
        this.output.writeString(this.field_232649_r_.getMinecraftVersion());
        this.output.writeString("plugins");
        this.output.writeString(this.field_232649_r_.getPlugins());
        this.output.writeString("map");
        this.output.writeString(this.worldName);
        this.output.writeString("numplayers");
        this.output.writeString("" + this.field_232649_r_.getCurrentPlayerCount());
        this.output.writeString("maxplayers");
        this.output.writeString("" + this.maxPlayers);
        this.output.writeString("hostport");
        this.output.writeString("" + this.serverPort);
        this.output.writeString("hostip");
        this.output.writeString(this.queryHostname);
        this.output.writeInt(0);
        this.output.writeInt(1);
        this.output.writeString("player_");
        this.output.writeInt(0);
        for (String string : stringArray = this.field_232649_r_.getOnlinePlayerNames()) {
            this.output.writeString(string);
        }
        this.output.writeInt(0);
        return this.output.toByteArray();
    }

    private byte[] getRequestID(SocketAddress socketAddress) {
        return this.queryClients.get(socketAddress).getRequestId();
    }

    private Boolean verifyClientAuth(DatagramPacket datagramPacket) {
        SocketAddress socketAddress = datagramPacket.getSocketAddress();
        if (!this.queryClients.containsKey(socketAddress)) {
            return false;
        }
        byte[] byArray = datagramPacket.getData();
        return this.queryClients.get(socketAddress).getRandomChallenge() == RConUtils.getBytesAsBEint(byArray, 7, datagramPacket.getLength());
    }

    private void sendAuthChallenge(DatagramPacket datagramPacket) throws IOException {
        Auth auth = new Auth(datagramPacket);
        this.queryClients.put(datagramPacket.getSocketAddress(), auth);
        this.sendResponsePacket(auth.getChallengeValue(), datagramPacket);
    }

    private void cleanQueryClientsMap() {
        long l;
        if (this.running && (l = Util.milliTime()) >= this.lastAuthCheckTime + 30000L) {
            this.lastAuthCheckTime = l;
            this.queryClients.values().removeIf(arg_0 -> QueryThread.lambda$cleanQueryClientsMap$0(l, arg_0));
        }
    }

    @Override
    public void run() {
        field_232648_d_.info("Query running on {}:{}", (Object)this.serverHostname, (Object)this.queryPort);
        this.lastAuthCheckTime = Util.milliTime();
        DatagramPacket datagramPacket = new DatagramPacket(this.buffer, this.buffer.length);
        try {
            while (this.running) {
                try {
                    this.querySocket.receive(datagramPacket);
                    this.cleanQueryClientsMap();
                    this.parseIncomingPacket(datagramPacket);
                } catch (SocketTimeoutException socketTimeoutException) {
                    this.cleanQueryClientsMap();
                } catch (PortUnreachableException portUnreachableException) {
                } catch (IOException iOException) {
                    this.stopWithException(iOException);
                }
            }
        } finally {
            field_232648_d_.debug("closeSocket: {}:{}", (Object)this.serverHostname, (Object)this.queryPort);
            this.querySocket.close();
        }
    }

    @Override
    public boolean func_241832_a() {
        if (this.running) {
            return false;
        }
        return !this.initQuerySystem() ? false : super.func_241832_a();
    }

    private void stopWithException(Exception exception) {
        if (this.running) {
            field_232648_d_.warn("Unexpected exception", (Throwable)exception);
            if (!this.initQuerySystem()) {
                field_232648_d_.error("Failed to recover from exception, shutting down!");
                this.running = false;
            }
        }
    }

    private boolean initQuerySystem() {
        try {
            this.querySocket = new DatagramSocket(this.queryPort, InetAddress.getByName(this.serverHostname));
            this.querySocket.setSoTimeout(500);
            return true;
        } catch (Exception exception) {
            field_232648_d_.warn("Unable to initialise query system on {}:{}", (Object)this.serverHostname, (Object)this.queryPort, (Object)exception);
            return true;
        }
    }

    private static boolean lambda$cleanQueryClientsMap$0(long l, Auth auth) {
        return auth.hasExpired(l);
    }

    static class Auth {
        private final long timestamp = new Date().getTime();
        private final int randomChallenge;
        private final byte[] requestId;
        private final byte[] challengeValue;
        private final String requestIdAsString;

        public Auth(DatagramPacket datagramPacket) {
            byte[] byArray = datagramPacket.getData();
            this.requestId = new byte[4];
            this.requestId[0] = byArray[3];
            this.requestId[1] = byArray[4];
            this.requestId[2] = byArray[5];
            this.requestId[3] = byArray[6];
            this.requestIdAsString = new String(this.requestId, StandardCharsets.UTF_8);
            this.randomChallenge = new Random().nextInt(0x1000000);
            this.challengeValue = String.format("\t%s%d\u0000", this.requestIdAsString, this.randomChallenge).getBytes(StandardCharsets.UTF_8);
        }

        public Boolean hasExpired(long l) {
            return this.timestamp < l;
        }

        public int getRandomChallenge() {
            return this.randomChallenge;
        }

        public byte[] getChallengeValue() {
            return this.challengeValue;
        }

        public byte[] getRequestId() {
            return this.requestId;
        }
    }
}

