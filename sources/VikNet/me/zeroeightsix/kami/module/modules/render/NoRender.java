package me.zeroeightsix.kami.module.modules.render;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.event.events.PacketEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.*;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;

/**
 * Created by 086 on 4/02/2018.
 */
@Module.Info(name = "NoRender", category = Module.Category.RENDER, description = "Ignore entity spawn packets")
public class NoRender extends Module {

    private Setting<Boolean> mobs = register(Settings.b("Mobs", false));
    private Setting<Boolean> globalEntities = register(Settings.b("Global Entities", false));
    private Setting<Boolean> objects = register(Settings.b("Objects", false));
    private Setting<Boolean> xpOrbs = register(Settings.b("XP Orbs", false));
    private Setting<Boolean> paintings = register(Settings.b("Paintings", false));
    public static Setting<Boolean> fire = Settings.b("Fire", true);
	public static Setting<Boolean> portalOverlay = Settings.b("PortalOverlay", true);
	
    private static NoRender INSTANCE = new NoRender();
    
    public NoRender() {
        INSTANCE = this;
        register(fire);
		register(portalOverlay);
    }
	
	public static boolean enabled() {
        return INSTANCE.isEnabled();
    }


    @EventHandler
    public Listener<PacketEvent.Receive> receiveListener = new Listener<>(event -> {
        Packet packet = event.getPacket();
        if ((packet instanceof SPacketSpawnMob && mobs.getValue()) ||
                (packet instanceof SPacketSpawnGlobalEntity && globalEntities.getValue()) ||
                (packet instanceof SPacketSpawnObject && objects.getValue()) ||
                (packet instanceof SPacketSpawnExperienceOrb && xpOrbs.getValue()) ||
                (packet instanceof SPacketSpawnPainting && paintings.getValue())) {
            event.cancel();
        }
    });

    @EventHandler
    public Listener<RenderBlockOverlayEvent> blockOverlayEventListener = new Listener<>(event -> {
        if (fire.getValue() && event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.FIRE) {
            event.setCanceled(true);
        }
    });

}
