package lol.point.returnclient.module.impl.visual;

import lol.point.returnclient.events.impl.packet.EventPacket;
import lol.point.returnclient.events.impl.render.EventRender2D;
import lol.point.returnclient.module.Category;
import lol.point.returnclient.module.Module;
import lol.point.returnclient.module.ModuleInfo;
import lol.point.returnclient.settings.impl.NumberSetting;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.network.play.server.S03PacketTimeUpdate;

@ModuleInfo(
        name = "Weather",
        description = "change the weather",
        category = Category.RENDER
)
public class Weather extends Module {
    private final NumberSetting time = new NumberSetting("Time", 18000, 0, 24000);

    public Weather(){
        this.addSettings(time);
    }

    public String getSuffix() {
        return String.valueOf(time.value.intValue());
    }

    @Subscribe
    public final Listener<EventRender2D> onRender2D = new Listener<>(eventRender2D -> {
        mc.theWorld.setWorldTime(time.value.longValue());
    });

    @Subscribe
    public final Listener<EventPacket> onPacket = new Listener<>(eventPacket -> {
        if (eventPacket.packet instanceof S03PacketTimeUpdate) {
            eventPacket.cancel();
        }
    });
}
