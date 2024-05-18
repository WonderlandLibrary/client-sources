package net.minecraft.client;

import java.applet.*;
import net.minecraft.src.*;
import java.awt.*;

public class MinecraftApplet extends Applet
{
    private Canvas mcCanvas;
    private Minecraft mc;
    private Thread mcThread;
    
    public MinecraftApplet() {
        this.mcThread = null;
    }
    
    @Override
    public void init() {
        this.mcCanvas = new CanvasMinecraftApplet(this);
        final boolean var1 = "true".equalsIgnoreCase(this.getParameter("fullscreen"));
        this.mc = new MinecraftAppletImpl(this, this.mcCanvas, this, this.getWidth(), this.getHeight(), var1);
        this.mc.minecraftUri = this.getDocumentBase().getHost();
        if (this.getDocumentBase().getPort() > 0) {
            this.mc.minecraftUri = String.valueOf(this.mc.minecraftUri) + ":" + this.getDocumentBase().getPort();
        }
        if (this.getParameter("username") != null && this.getParameter("sessionid") != null) {
            this.mc.session = new Session(this.getParameter("username"), this.getParameter("sessionid"));
            this.mc.getLogAgent().logInfo("Setting user: " + this.mc.session.username);
            System.out.println("(Session ID is " + this.mc.session.sessionId + ")");
        }
        else {
            this.mc.session = new Session("Player", "");
        }
        this.mc.setDemo("true".equals(this.getParameter("demo")));
        if (this.getParameter("server") != null && this.getParameter("port") != null) {
            this.mc.setServer(this.getParameter("server"), Integer.parseInt(this.getParameter("port")));
        }
        this.mc.hideQuitButton = !"true".equals(this.getParameter("stand-alone"));
        this.setLayout(new BorderLayout());
        this.add(this.mcCanvas, "Center");
        this.mcCanvas.setFocusable(true);
        this.mcCanvas.setFocusTraversalKeysEnabled(false);
        this.validate();
    }
    
    public void startMainThread() {
        if (this.mcThread == null) {
            (this.mcThread = new Thread(this.mc, "Minecraft main thread")).start();
        }
    }
    
    @Override
    public void start() {
        if (this.mc != null) {
            this.mc.isGamePaused = false;
        }
    }
    
    @Override
    public void stop() {
        if (this.mc != null) {
            this.mc.isGamePaused = true;
        }
    }
    
    @Override
    public void destroy() {
        this.shutdown();
    }
    
    public void shutdown() {
        if (this.mcThread != null) {
            this.mc.shutdown();
            try {
                this.mcThread.join(10000L);
            }
            catch (InterruptedException var4) {
                try {
                    this.mc.shutdownMinecraftApplet();
                }
                catch (Exception var3) {
                    var3.printStackTrace();
                }
            }
            this.mcThread = null;
        }
    }
}
