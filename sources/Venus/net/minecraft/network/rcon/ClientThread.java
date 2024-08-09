/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.rcon;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import net.minecraft.network.rcon.IServer;
import net.minecraft.network.rcon.RConThread;
import net.minecraft.network.rcon.RConUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientThread
extends RConThread {
    private static final Logger LOGGER = LogManager.getLogger();
    private boolean loggedIn;
    private final Socket clientSocket;
    private final byte[] buffer = new byte[1460];
    private final String rconPassword;
    private final IServer field_232651_i_;

    ClientThread(IServer iServer, String string, Socket socket) {
        super("RCON Client " + socket.getInetAddress());
        this.field_232651_i_ = iServer;
        this.clientSocket = socket;
        try {
            this.clientSocket.setSoTimeout(0);
        } catch (Exception exception) {
            this.running = false;
        }
        this.rconPassword = string;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        try {
            block14: while (true) {
                if (!this.running) {
                    return;
                }
                BufferedInputStream bufferedInputStream = new BufferedInputStream(this.clientSocket.getInputStream());
                int n = bufferedInputStream.read(this.buffer, 0, 1460);
                if (10 > n) break;
                int n2 = 0;
                int n3 = RConUtils.getBytesAsLEInt(this.buffer, 0, n);
                if (n3 != n - 4) {
                    return;
                }
                int n4 = RConUtils.getBytesAsLEInt(this.buffer, n2 += 4, n);
                int n5 = RConUtils.getRemainingBytesAsLEInt(this.buffer, n2 += 4);
                n2 += 4;
                switch (n5) {
                    case 2: {
                        String string;
                        if (this.loggedIn) {
                            string = RConUtils.getBytesAsString(this.buffer, n2, n);
                            try {
                                this.sendMultipacketResponse(n4, this.field_232651_i_.handleRConCommand(string));
                            } catch (Exception exception) {
                                this.sendMultipacketResponse(n4, "Error executing: " + string + " (" + exception.getMessage() + ")");
                            }
                            continue block14;
                        }
                        this.sendLoginFailedResponse();
                        continue block14;
                    }
                    case 3: {
                        String string = RConUtils.getBytesAsString(this.buffer, n2, n);
                        int n6 = n2 + string.length();
                        if (!string.isEmpty() && string.equals(this.rconPassword)) {
                            this.loggedIn = true;
                            this.sendResponse(n4, 2, "");
                            continue block14;
                        }
                        this.loggedIn = false;
                        this.sendLoginFailedResponse();
                        continue block14;
                    }
                }
                this.sendMultipacketResponse(n4, String.format("Unknown request %s", Integer.toHexString(n5)));
            }
            return;
        } catch (IOException iOException) {
            return;
        } catch (Exception exception) {
            LOGGER.error("Exception whilst parsing RCON input", (Throwable)exception);
            return;
        } finally {
            this.closeSocket();
            LOGGER.info("Thread {} shutting down", (Object)this.threadName);
            this.running = false;
        }
    }

    private void sendResponse(int n, int n2, String string) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1248);
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        byte[] byArray = string.getBytes(StandardCharsets.UTF_8);
        dataOutputStream.writeInt(Integer.reverseBytes(byArray.length + 10));
        dataOutputStream.writeInt(Integer.reverseBytes(n));
        dataOutputStream.writeInt(Integer.reverseBytes(n2));
        dataOutputStream.write(byArray);
        dataOutputStream.write(0);
        dataOutputStream.write(0);
        this.clientSocket.getOutputStream().write(byteArrayOutputStream.toByteArray());
    }

    private void sendLoginFailedResponse() throws IOException {
        this.sendResponse(-1, 2, "");
    }

    private void sendMultipacketResponse(int n, String string) throws IOException {
        int n2;
        int n3 = string.length();
        do {
            n2 = 4096 <= n3 ? 4096 : n3;
            this.sendResponse(n, 0, string.substring(0, n2));
        } while (0 != (n3 = (string = string.substring(n2)).length()));
    }

    @Override
    public void func_219591_b() {
        this.running = false;
        this.closeSocket();
        super.func_219591_b();
    }

    private void closeSocket() {
        try {
            this.clientSocket.close();
        } catch (IOException iOException) {
            LOGGER.warn("Failed to close socket", (Throwable)iOException);
        }
    }
}

