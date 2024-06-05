package me.satisfactory.base.gui;

import java.net.*;
import java.util.concurrent.*;
import java.util.*;

public class PortscanManager
{
    public static ArrayList<Integer> ports;
    static int openPorts;
    public boolean isChecking;
    public boolean AlwaysTrue;
    ExecutorService es;
    private Integer Scanned_Ports;
    
    public PortscanManager() {
        this.isChecking = false;
        this.AlwaysTrue = true;
        this.Scanned_Ports = 0;
        this.Scanned_Ports = 0;
    }
    
    public void onStop() {
        this.isChecking = false;
        try {
            this.es.shutdownNow();
        }
        catch (Exception ex) {}
    }
    
    public String GetInfo() {
        return "Scanned ports: " + this.Scanned_Ports + "/65535!";
    }
    
    public String GetName() {
        return "Portscan";
    }
    
    public Future<Boolean> portIsOpen(final ExecutorService es2, final String ip2, final int port, final int timeout) {
        return es2.submit((Callable<Boolean>)new Callable<Boolean>() {
            @Override
            public Boolean call() {
                try {
                    final Socket socket = new Socket();
                    socket.connect(new InetSocketAddress(ip2, port), timeout);
                    socket.close();
                    System.out.println("Discovered open port: " + port);
                    Object object = PortscanManager.ports;
                    synchronized (object) {
                        PortscanManager.ports.add(port);
                    }
                    object = PortscanManager.this.Scanned_Ports;
                    synchronized (object) {
                        final Integer n = PortscanManager.this.Scanned_Ports;
                        ++PortscanManager.this.Scanned_Ports;
                    }
                    return true;
                }
                catch (Exception ex) {
                    synchronized (PortscanManager.this.Scanned_Ports) {
                        final Integer localInteger1 = PortscanManager.this.Scanned_Ports;
                        ++PortscanManager.this.Scanned_Ports;
                    }
                    return false;
                }
            }
        });
    }
    
    public void Portscan(final String ServerIP, final int startport, final int endport, final int timeoutt, final int botamount) {
        PortscanManager.openPorts = 0;
        PortscanManager.ports.clear();
        this.es = Executors.newFixedThreadPool(botamount);
        final String serverIP = ServerIP;
        final String ip2 = serverIP.split(":")[0];
        final int timeout = timeoutt;
        final ArrayList<Future<Boolean>> futures = new ArrayList<Future<Boolean>>();
        for (int port = startport; port <= endport; ++port) {
            futures.add(this.portIsOpen(this.es, ip2, port, 2000));
        }
        this.es.shutdown();
        for (final Future f2 : futures) {
            try {
                this.isChecking = true;
                if (!f2.get()) {
                    continue;
                }
                ++PortscanManager.openPorts;
            }
            catch (InterruptedException e) {
                this.isChecking = false;
                e.printStackTrace();
            }
            catch (ExecutionException e2) {
                this.isChecking = false;
                e2.printStackTrace();
            }
        }
        System.out.println("There are " + PortscanManager.openPorts + " open ports on host " + ip2 + " (probed with a timeout of " + 2000 + "ms)");
    }
    
    static {
        PortscanManager.ports = new ArrayList<Integer>();
        PortscanManager.openPorts = 0;
    }
}
