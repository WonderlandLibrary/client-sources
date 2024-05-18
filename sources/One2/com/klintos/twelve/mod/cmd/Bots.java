// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod.cmd;

import java.util.Iterator;
import org.spacehq.packetlib.packet.Packet;
import org.spacehq.mc.protocol.packet.ingame.client.ClientKeepAlivePacket;
import org.spacehq.packetlib.event.session.SessionListener;
import org.spacehq.packetlib.event.session.DisconnectedEvent;
import org.spacehq.packetlib.event.session.ConnectedEvent;
import org.spacehq.packetlib.event.session.SessionAdapter;
import com.klintos.twelve.utils.FileUtils;
import com.klintos.twelve.bots.Bot;
import java.util.ArrayList;

public class Bots extends Cmd
{
    public static ArrayList<String> accs;
    public static ArrayList<Bot> bots;
    private boolean running;
    
    static {
        Bots.accs = new ArrayList<String>();
        Bots.bots = new ArrayList<Bot>();
    }
    
    public Bots() {
        super("bots", "BOTNET a server.", "bots <Connect/Disconnect/Rl>");
    }
    
    @Override
    public void runCmd(final String msg, final String[] args) {
        try {
            if (args[1].equalsIgnoreCase("rl")) {
                Bots.accs.clear();
                FileUtils.loadBots();
                this.addMessage("Reloaded bot accounts.");
            }
            else if (args[1].equalsIgnoreCase("connect")) {
                new Thread("Bot Thread") {
                    @Override
                    public void run() {
                        Bots.access$0(Bots.this, true);
                        Bots.this.addMessage("Connecting accounts.");
                        if (Bots.accs.size() > 0) {
                            for (final String s : Bots.accs) {
                                try {
                                    final String[] split = s.split(":");
                                    final Bot bot = new Bot(split[0], split[1], Bots.this.mc.getCurrentServerData().serverIP, 25565);
                                    bot.addListener((SessionListener)new SessionAdapter() {
                                        public void connected(final ConnectedEvent event) {
                                            System.out.println("Connected bot: " + bot.getUsername());
                                            Bots.bots.add(bot);
                                        }
                                        
                                        public void disconnected(final DisconnectedEvent event) {
                                            System.out.println("Disconnected: " + event.getReason());
                                            Bots.bots.remove(bot);
                                        }
                                    });
                                    bot.getSession().connect(true);
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            while (Bots.this.running) {
                                if (Bots.bots.size() > 0) {
                                    for (final Bot bot2 : Bots.bots) {
                                        if (bot2 != null && bot2.getSession().isConnected()) {
                                            System.out.println("keepalive");
                                            bot2.getSession().send((Packet)new ClientKeepAlivePacket(0));
                                        }
                                    }
                                    try {
                                        Thread.sleep(20000L);
                                    }
                                    catch (InterruptedException ex) {}
                                }
                            }
                        }
                        else {
                            Bots.this.addMessage("No accounts loaded.");
                        }
                    }
                }.start();
            }
            else if (args[1].equalsIgnoreCase("disconnect")) {
                this.running = false;
                this.addMessage("Disconnecting accounts.");
            }
        }
        catch (Exception e) {
            this.runHelp();
        }
    }
    
    static /* synthetic */ void access$0(final Bots bots, final boolean running) {
        bots.running = running;
    }
}
