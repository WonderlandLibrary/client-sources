package net.minecraft.src;

import java.util.*;
import java.io.*;
import java.net.*;

public class RConThreadMain extends RConThreadBase
{
    private int rconPort;
    private int serverPort;
    private String hostname;
    private ServerSocket serverSocket;
    private String rconPassword;
    private Map clientThreads;
    
    public RConThreadMain(final IServer par1IServer) {
        super(par1IServer);
        this.serverSocket = null;
        this.rconPort = par1IServer.getIntProperty("rcon.port", 0);
        this.rconPassword = par1IServer.getStringProperty("rcon.password", "");
        this.hostname = par1IServer.getHostname();
        this.serverPort = par1IServer.getPort();
        if (this.rconPort == 0) {
            this.rconPort = this.serverPort + 10;
            this.logInfo("Setting default rcon port to " + this.rconPort);
            par1IServer.setProperty("rcon.port", this.rconPort);
            if (this.rconPassword.length() == 0) {
                par1IServer.setProperty("rcon.password", "");
            }
            par1IServer.saveProperties();
        }
        if (this.hostname.length() == 0) {
            this.hostname = "0.0.0.0";
        }
        this.initClientThreadList();
        this.serverSocket = null;
    }
    
    private void initClientThreadList() {
        this.clientThreads = new HashMap();
    }
    
    private void cleanClientThreadsMap() {
        final Iterator var1 = this.clientThreads.entrySet().iterator();
        while (var1.hasNext()) {
            final Map.Entry var2 = var1.next();
            if (!var2.getValue().isRunning()) {
                var1.remove();
            }
        }
    }
    
    @Override
    public void run() {
        this.logInfo("RCON running on " + this.hostname + ":" + this.rconPort);
        try {
            while (this.running) {
                try {
                    final Socket var1 = this.serverSocket.accept();
                    var1.setSoTimeout(500);
                    final RConThreadClient var2 = new RConThreadClient(this.server, var1);
                    var2.startThread();
                    this.clientThreads.put(var1.getRemoteSocketAddress(), var2);
                    this.cleanClientThreadsMap();
                }
                catch (SocketTimeoutException var4) {
                    this.cleanClientThreadsMap();
                }
                catch (IOException var3) {
                    if (!this.running) {
                        continue;
                    }
                    this.logInfo("IO: " + var3.getMessage());
                }
            }
        }
        finally {
            this.closeServerSocket(this.serverSocket);
        }
        this.closeServerSocket(this.serverSocket);
    }
    
    @Override
    public void startThread() {
        if (this.rconPassword.length() == 0) {
            this.logWarning("No rcon password set in '" + this.server.getSettingsFilename() + "', rcon disabled!");
        }
        else if (this.rconPort > 0 && 65535 >= this.rconPort) {
            if (!this.running) {
                try {
                    (this.serverSocket = new ServerSocket(this.rconPort, 0, InetAddress.getByName(this.hostname))).setSoTimeout(500);
                    super.startThread();
                }
                catch (IOException var2) {
                    this.logWarning("Unable to initialise rcon on " + this.hostname + ":" + this.rconPort + " : " + var2.getMessage());
                }
            }
        }
        else {
            this.logWarning("Invalid rcon port " + this.rconPort + " found in '" + this.server.getSettingsFilename() + "', rcon disabled!");
        }
    }
}
