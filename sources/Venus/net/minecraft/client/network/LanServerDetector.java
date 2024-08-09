/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.network;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.multiplayer.LanServerPingThread;
import net.minecraft.client.network.LanServerInfo;
import net.minecraft.util.DefaultUncaughtExceptionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LanServerDetector {
    private static final AtomicInteger ATOMIC_COUNTER = new AtomicInteger(0);
    private static final Logger LOGGER = LogManager.getLogger();

    public static class LanServerList {
        private final List<LanServerInfo> listOfLanServers = Lists.newArrayList();
        private boolean wasUpdated;

        public synchronized boolean getWasUpdated() {
            return this.wasUpdated;
        }

        public synchronized void setWasNotUpdated() {
            this.wasUpdated = false;
        }

        public synchronized List<LanServerInfo> getLanServers() {
            return Collections.unmodifiableList(this.listOfLanServers);
        }

        public synchronized void addServer(String string, InetAddress inetAddress) {
            String string2 = LanServerPingThread.getMotdFromPingResponse(string);
            Object object = LanServerPingThread.getAdFromPingResponse(string);
            if (object != null) {
                object = inetAddress.getHostAddress() + ":" + (String)object;
                boolean bl = false;
                for (LanServerInfo lanServerInfo : this.listOfLanServers) {
                    if (!lanServerInfo.getServerIpPort().equals(object)) continue;
                    lanServerInfo.updateLastSeen();
                    bl = true;
                    break;
                }
                if (!bl) {
                    this.listOfLanServers.add(new LanServerInfo(string2, (String)object));
                    this.wasUpdated = true;
                }
            }
        }
    }

    public static class LanServerFindThread
    extends Thread {
        private final LanServerList localServerList;
        private final InetAddress broadcastAddress;
        private final MulticastSocket socket;

        public LanServerFindThread(LanServerList lanServerList) throws IOException {
            super("LanServerDetector #" + ATOMIC_COUNTER.incrementAndGet());
            this.localServerList = lanServerList;
            this.setDaemon(false);
            this.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(LOGGER));
            this.socket = new MulticastSocket(4445);
            this.broadcastAddress = InetAddress.getByName("224.0.2.60");
            this.socket.setSoTimeout(5000);
            this.socket.joinGroup(this.broadcastAddress);
        }

        @Override
        public void run() {
            byte[] byArray = new byte[1024];
            while (!this.isInterrupted()) {
                DatagramPacket datagramPacket = new DatagramPacket(byArray, byArray.length);
                try {
                    this.socket.receive(datagramPacket);
                } catch (SocketTimeoutException socketTimeoutException) {
                    continue;
                } catch (IOException iOException) {
                    LOGGER.error("Couldn't ping server", (Throwable)iOException);
                    break;
                }
                String string = new String(datagramPacket.getData(), datagramPacket.getOffset(), datagramPacket.getLength(), StandardCharsets.UTF_8);
                LOGGER.debug("{}: {}", (Object)datagramPacket.getAddress(), (Object)string);
                this.localServerList.addServer(string, datagramPacket.getAddress());
            }
            try {
                this.socket.leaveGroup(this.broadcastAddress);
            } catch (IOException iOException) {
                // empty catch block
            }
            this.socket.close();
        }
    }
}

