package arsenic.module.impl.other;

import arsenic.event.bus.Listener;
import arsenic.event.bus.annotations.EventLink;
import arsenic.event.impl.EventPacket;
import arsenic.main.Nexus;
import arsenic.module.Module;
import arsenic.module.ModuleCategory;
import arsenic.module.ModuleInfo;
import arsenic.utils.minecraft.PlayerUtils;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
@ModuleInfo(name = "FlagDetector", category = ModuleCategory.Other)
public class FlagDetector extends Module {
    @EventLink
    public final Listener<EventPacket.Incoming.Pre> packetEvent = event -> {
        if(Nexus.getInstance().getModuleManager().getModuleByName("Flight").isEnabled())return;

        if(event.getPacket() instanceof S08PacketPlayerPosLook){
            PlayerUtils.addWaterMarkedMessageToChat("§c[S08]§f Flag detected");
        }
    };
}
