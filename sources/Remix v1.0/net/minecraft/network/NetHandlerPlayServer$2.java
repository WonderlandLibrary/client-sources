package net.minecraft.network;

class NetHandlerPlayServer$2 implements Runnable {
    @Override
    public void run() {
        NetHandlerPlayServer.this.netManager.checkDisconnected();
    }
}