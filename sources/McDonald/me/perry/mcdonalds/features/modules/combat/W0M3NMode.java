// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.modules.combat;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import java.net.URI;
import java.awt.Desktop;
import me.perry.mcdonalds.util.Wrapper;
import me.perry.mcdonalds.features.setting.Setting;
import me.perry.mcdonalds.features.modules.Module;

public class W0M3NMode extends Module
{
    private final Setting<Integer> range;
    
    public W0M3NMode() {
        super("InfiniteRangeCA", "Crazy module", Category.COMBAT, true, false, false);
        this.range = (Setting<Integer>)this.register(new Setting("Range", (T)20, (T)0, (T)50));
        final Setting<mode> mode = (Setting<mode>)this.register(new Setting("Bypass:", (T)W0M3NMode.mode.CRYSTALPVPCC));
    }
    
    @Override
    public void onUpdate() {
        if (Wrapper.mc.world == null || Wrapper.mc.player == null) {
            this.disable();
        }
    }
    
    @Override
    public void onEnable() {
        if (Wrapper.mc.world != null && Wrapper.getPlayer() != null) {
            try {
                Desktop.getDesktop().browse(URI.create("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
            }
            catch (Exception ex) {}
            this.disable();
        }
    }
    
    @SubscribeEvent
    public void onClientDisconnect(final FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        this.disable();
    }
    
    public enum mode
    {
        CRYSTALPVPCC, 
        ELITEANARCHY, 
        CloudAnarchyCore;
    }
}
