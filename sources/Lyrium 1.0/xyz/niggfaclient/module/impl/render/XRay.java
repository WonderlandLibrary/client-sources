// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.render;

import java.util.Collection;
import net.minecraft.network.login.server.S02PacketLoginSuccess;
import net.minecraft.network.play.server.S07PacketRespawn;
import com.google.common.collect.Lists;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.PacketEvent;
import xyz.niggfaclient.eventbus.Listener;
import java.util.HashSet;
import xyz.niggfaclient.property.impl.DoubleProperty;
import java.util.List;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "XRay", description = "Allows you to see blocks through walls", cat = Category.RENDER)
public class XRay extends Module
{
    public final List<Integer> KEY_IDS;
    public static DoubleProperty opacity;
    public HashSet<Integer> blockIDs;
    @EventLink
    private final Listener<PacketEvent> packetEventListener;
    
    public XRay() {
        this.KEY_IDS = Lists.newArrayList(10, 11, 8, 9, 14, 15, 16, 21, 41, 42, 46, 48, 52, 56, 57, 61, 62, 73, 74, 84, 89, 103, 116, 117, 118, 120, 129, 133, 137, 145, 152, 153, 154);
        this.blockIDs = new HashSet<Integer>();
        this.packetEventListener = (e -> {
            if (e.getState() == PacketEvent.State.RECEIVE && (e.getPacket() instanceof S07PacketRespawn || e.getPacket() instanceof S02PacketLoginSuccess)) {
                this.blockIDs.clear();
            }
        });
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.blockIDs.clear();
        try {
            this.blockIDs.addAll((Collection<?>)this.KEY_IDS);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        this.mc.renderGlobal.loadRenderers();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        this.mc.renderGlobal.loadRenderers();
    }
    
    static {
        XRay.opacity = new DoubleProperty("Opacity", 100.0, 0.0, 255.0, 5.0);
    }
}
