package net.minecraft.src;

import java.net.*;

class ThreadConnectToServer extends Thread
{
    final String ip;
    final int port;
    final GuiConnecting connectingGui;
    
    ThreadConnectToServer(final GuiConnecting par1GuiConnecting, final String par2Str, final int par3) {
        this.connectingGui = par1GuiConnecting;
        this.ip = par2Str;
        this.port = par3;
    }
    
    @Override
    public void run() {
        try {
            GuiConnecting.setNetClientHandler(this.connectingGui, new NetClientHandler(GuiConnecting.func_74256_a(this.connectingGui), this.ip, this.port));
            if (GuiConnecting.isCancelled(this.connectingGui)) {
                return;
            }
            GuiConnecting.getNetClientHandler(this.connectingGui).addToSendQueue(new Packet2ClientProtocol(61, GuiConnecting.func_74254_c(this.connectingGui).session.username, this.ip, this.port));
        }
        catch (UnknownHostException var5) {
            if (GuiConnecting.isCancelled(this.connectingGui)) {
                return;
            }
            GuiConnecting.func_74250_f(this.connectingGui).displayGuiScreen(new GuiDisconnected(GuiConnecting.func_98097_e(this.connectingGui), "connect.failed", "disconnect.genericReason", new Object[] { "Unknown host '" + this.ip + "'" }));
        }
        catch (ConnectException var3) {
            if (GuiConnecting.isCancelled(this.connectingGui)) {
                return;
            }
            GuiConnecting.func_74251_g(this.connectingGui).displayGuiScreen(new GuiDisconnected(GuiConnecting.func_98097_e(this.connectingGui), "connect.failed", "disconnect.genericReason", new Object[] { var3.getMessage() }));
        }
        catch (Exception var4) {
            if (GuiConnecting.isCancelled(this.connectingGui)) {
                return;
            }
            var4.printStackTrace();
            GuiConnecting.func_98096_h(this.connectingGui).displayGuiScreen(new GuiDisconnected(GuiConnecting.func_98097_e(this.connectingGui), "connect.failed", "disconnect.genericReason", new Object[] { var4.toString() }));
        }
    }
}
