package dev.vertic.module.impl.render;

import dev.vertic.event.api.EventLink;
import dev.vertic.event.impl.packet.PacketReceiveEvent;
import dev.vertic.event.impl.render.Render2DEvent;
import dev.vertic.module.Module;
import dev.vertic.module.api.Category;
import dev.vertic.setting.impl.NumberSetting;
import net.minecraft.network.play.server.S03PacketTimeUpdate;

public class TimeChanger extends Module {

    private final NumberSetting time = new NumberSetting("Time", 18000, 0, 24000, 500);

    public TimeChanger() {
        super("TimeChanger", "Changes world time clientside.", Category.RENDER);
        this.addSettings(time);
    }

    @EventLink
    public void on2DRender(Render2DEvent event) {
        mc.theWorld.setWorldTime(time.getLong());
    }

    @EventLink
    public void onPacketSend(PacketReceiveEvent event) {
        if (event.getPacket() instanceof S03PacketTimeUpdate) {
            event.cancel();
        }
    }

}
