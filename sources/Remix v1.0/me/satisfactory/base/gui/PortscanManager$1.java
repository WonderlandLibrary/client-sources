package me.satisfactory.base.gui;

import java.util.concurrent.*;
import java.net.*;

class PortscanManager$1 implements Callable<Boolean> {
    final /* synthetic */ String val$ip2;
    final /* synthetic */ int val$port;
    final /* synthetic */ int val$timeout;
    
    @Override
    public Boolean call() {
        try {
            final Socket socket = new Socket();
            socket.connect(new InetSocketAddress(this.val$ip2, this.val$port), this.val$timeout);
            socket.close();
            System.out.println("Discovered open port: " + this.val$port);
            Object object = PortscanManager.ports;
            synchronized (object) {
                PortscanManager.ports.add(this.val$port);
            }
            object = PortscanManager.access$000(PortscanManager.this);
            synchronized (object) {
                final Integer n = PortscanManager.access$000(PortscanManager.this);
                PortscanManager.access$002(PortscanManager.this, PortscanManager.access$000(PortscanManager.this) + 1);
            }
            return true;
        }
        catch (Exception ex) {
            synchronized (PortscanManager.access$000(PortscanManager.this)) {
                final Integer localInteger1 = PortscanManager.access$000(PortscanManager.this);
                PortscanManager.access$002(PortscanManager.this, PortscanManager.access$000(PortscanManager.this) + 1);
            }
            return false;
        }
    }
}