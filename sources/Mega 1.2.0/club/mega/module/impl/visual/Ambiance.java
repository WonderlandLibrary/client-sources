package club.mega.module.impl.visual;

import club.mega.event.impl.EventPacket;
import club.mega.event.impl.EventRender2D;
import club.mega.module.Category;
import club.mega.module.Module;
import club.mega.module.setting.impl.NumberSetting;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import rip.hippo.lwjeb.annotation.Handler;

@Module.ModuleInfo(name = "Ambiance", description = "Time Changer", category = Category.VISUAL)
public class Ambiance extends Module {

    public final NumberSetting time = new NumberSetting("Time", this, 0,24000,0,500);

    @Handler
    public final void packet(final EventPacket event) {
        if (event.getPacket() instanceof S03PacketTimeUpdate)
            event.setCancelled(true);
    }

    @Handler
    public final void render2D(final EventRender2D event) {
        MC.theWorld.setWorldTime(time.getAsLong());
    }

}
