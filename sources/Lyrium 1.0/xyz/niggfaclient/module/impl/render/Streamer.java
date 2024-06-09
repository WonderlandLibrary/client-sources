// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.render;

import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.network.NetworkPlayerInfo;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.Render2DEvent;
import xyz.niggfaclient.eventbus.Listener;
import java.util.List;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "Streamer", description = "Hides everyones names", cat = Category.RENDER)
public class Streamer extends Module
{
    public static final List<String> PLAYERS;
    @EventLink
    private final Listener<Render2DEvent> render2DEventListener;
    
    public Streamer() {
        final Iterator<NetworkPlayerInfo> iterator;
        NetworkPlayerInfo player;
        this.render2DEventListener = (e -> {
            if (this.mc.thePlayer.ticksExisted < 5) {
                Streamer.PLAYERS.clear();
            }
            this.mc.getNetHandler().getPlayerInfoMap().iterator();
            while (iterator.hasNext()) {
                player = iterator.next();
                if (this.mc.thePlayer.getGameProfile().getName().length() < 3) {
                    continue;
                }
                else if (!Streamer.PLAYERS.contains(this.mc.thePlayer.getGameProfile().getName())) {
                    Streamer.PLAYERS.add(this.mc.thePlayer.getGameProfile().getName());
                }
                else {
                    continue;
                }
            }
        });
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        Streamer.PLAYERS.clear();
    }
    
    static {
        PLAYERS = new ArrayList<String>();
    }
}
