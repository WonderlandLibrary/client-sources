package net.minecraft.src;

import java.net.*;
import java.io.*;

public class RConThreadClient extends RConThreadBase
{
    private boolean loggedIn;
    private Socket clientSocket;
    private byte[] buffer;
    private String rconPassword;
    
    RConThreadClient(final IServer par1IServer, final Socket par2Socket) {
        super(par1IServer);
        this.loggedIn = false;
        this.buffer = new byte[1460];
        this.clientSocket = par2Socket;
        try {
            this.clientSocket.setSoTimeout(0);
        }
        catch (Exception var4) {
            this.running = false;
        }
        this.rconPassword = par1IServer.getStringProperty("rcon.password", "");
        this.logInfo("Rcon connection from: " + par2Socket.getInetAddress());
    }
    
    @Override
    public void run() {
        try {
            while (this.running) {
                final BufferedInputStream var1 = new BufferedInputStream(this.clientSocket.getInputStream());
                final int var2 = var1.read(this.buffer, 0, 1460);
                if (10 <= var2) {
                    final byte var3 = 0;
                    final int var4 = RConUtils.getBytesAsLEInt(this.buffer, 0, var2);
                    if (var4 != var2 - 4) {
                        return;
                    }
                    int var5 = var3 + 4;
                    final int var6 = RConUtils.getBytesAsLEInt(this.buffer, var5, var2);
                    var5 += 4;
                    final int var7 = RConUtils.getRemainingBytesAsLEInt(this.buffer, var5);
                    var5 += 4;
                    switch (var7) {
                        case 2: {
                            if (this.loggedIn) {
                                final String var8 = RConUtils.getBytesAsString(this.buffer, var5, var2);
                                try {
                                    this.sendMultipacketResponse(var6, this.server.executeCommand(var8));
                                }
                                catch (Exception var9) {
                                    this.sendMultipacketResponse(var6, "Error executing: " + var8 + " (" + var9.getMessage() + ")");
                                }
                                continue;
                            }
                            this.sendLoginFailedResponse();
                            continue;
                        }
                        case 3: {
                            final String var10 = RConUtils.getBytesAsString(this.buffer, var5, var2);
                            final int var11 = var5 + var10.length();
                            if (var10.length() != 0 && var10.equals(this.rconPassword)) {
                                this.loggedIn = true;
                                this.sendResponse(var6, 2, "");
                                continue;
                            }
                            this.loggedIn = false;
                            this.sendLoginFailedResponse();
                            continue;
                        }
                        default: {
                            this.sendMultipacketResponse(var6, String.format("Unknown request %s", Integer.toHexString(var7)));
                            continue;
                        }
                    }
                }
            }
        }
        catch (SocketTimeoutException ex) {}
        catch (IOException ex2) {}
        catch (Exception var12) {
            System.out.println(var12);
        }
        finally {
            this.closeSocket();
        }
        this.closeSocket();
    }
    
    private void sendResponse(final int par1, final int par2, final String par3Str) throws IOException {
        final ByteArrayOutputStream var4 = new ByteArrayOutputStream(1248);
        final DataOutputStream var5 = new DataOutputStream(var4);
        var5.writeInt(Integer.reverseBytes(par3Str.length() + 10));
        var5.writeInt(Integer.reverseBytes(par1));
        var5.writeInt(Integer.reverseBytes(par2));
        var5.writeBytes(par3Str);
        var5.write(0);
        var5.write(0);
        this.clientSocket.getOutputStream().write(var4.toByteArray());
    }
    
    private void sendLoginFailedResponse() throws IOException {
        this.sendResponse(-1, 2, "");
    }
    
    private void sendMultipacketResponse(final int par1, String par2Str) throws IOException {
        int var3 = par2Str.length();
        do {
            final int var4 = (4096 <= var3) ? 4096 : var3;
            this.sendResponse(par1, 0, par2Str.substring(0, var4));
            par2Str = par2Str.substring(var4);
            var3 = par2Str.length();
        } while (var3 != 0);
    }
    
    private void closeSocket() {
        if (this.clientSocket != null) {
            try {
                this.clientSocket.close();
            }
            catch (IOException var2) {
                this.logWarning("IO: " + var2.getMessage());
            }
            this.clientSocket = null;
        }
    }
}
