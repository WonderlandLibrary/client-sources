/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.multiplayer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.util.DefaultUncaughtExceptionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LanServerPingThread
extends Thread {
    private static final AtomicInteger UNIQUE_THREAD_ID = new AtomicInteger(0);
    private static final Logger LOGGER = LogManager.getLogger();
    private final String motd;
    private final DatagramSocket socket;
    private boolean isStopping = true;
    private final String address;

    public LanServerPingThread(String string, String string2) throws IOException {
        super("LanServerPinger #" + UNIQUE_THREAD_ID.incrementAndGet());
        this.motd = string;
        this.address = string2;
        this.setDaemon(false);
        this.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(LOGGER));
        this.socket = new DatagramSocket();
    }

    @Override
    public void run() {
        String string = LanServerPingThread.getPingResponse(this.motd, this.address);
        byte[] byArray = string.getBytes(StandardCharsets.UTF_8);
        while (!this.isInterrupted() && this.isStopping) {
            try {
                InetAddress inetAddress = InetAddress.getByName("224.0.2.60");
                DatagramPacket datagramPacket = new DatagramPacket(byArray, byArray.length, inetAddress, 4445);
                this.socket.send(datagramPacket);
            } catch (IOException iOException) {
                LOGGER.warn("LanServerPinger: {}", (Object)iOException.getMessage());
                break;
            }
            try {
                LanServerPingThread.sleep(1500L);
            } catch (InterruptedException interruptedException) {}
        }
    }

    @Override
    public void interrupt() {
        super.interrupt();
        this.isStopping = false;
    }

    public static String getPingResponse(String string, String string2) {
        return "[MOTD]" + string + "[/MOTD][AD]" + string2 + "[/AD]";
    }

    public static String getMotdFromPingResponse(String string) {
        int n = string.indexOf("[MOTD]");
        if (n < 0) {
            return "missing no";
        }
        int n2 = string.indexOf("[/MOTD]", n + 6);
        return n2 < n ? "missing no" : string.substring(n + 6, n2);
    }

    public static String getAdFromPingResponse(String string) {
        int n = string.indexOf("[/MOTD]");
        if (n < 0) {
            return null;
        }
        int n2 = string.indexOf("[/MOTD]", n + 7);
        if (n2 >= 0) {
            return null;
        }
        int n3 = string.indexOf("[AD]", n + 7);
        if (n3 < 0) {
            return null;
        }
        int n4 = string.indexOf("[/AD]", n3 + 4);
        return n4 < n3 ? null : string.substring(n3 + 4, n4);
    }
}

