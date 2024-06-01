package best.actinium.module.impl.world;

import best.actinium.event.EventType;
import best.actinium.event.api.Callback;
import best.actinium.event.impl.network.PacketEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.util.render.ChatUtil;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;

@ModuleInfo(
        name = "Blink",
        description = "Chocks on that dic- i mean packets",
        category = ModuleCategory.WORLD
)
public class BlinkModule extends Module {
    @Callback
    public void onPacket(PacketEvent event) {
        if (event.getType() == EventType.INCOMING) {
            return;
        }

        if(event.getPacket() instanceof C03PacketPlayer || event.getPacket() instanceof C00PacketKeepAlive) {
            event.setCancelled(true);
        }
    }
}
