package fr.dog.module.impl.render;

import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.network.PacketReceiveEvent;
import fr.dog.event.impl.player.PlayerTickEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.property.impl.NumberProperty;
import net.minecraft.network.play.server.S03PacketTimeUpdate;


public class Ambiance extends Module {

    private final NumberProperty val = NumberProperty.newInstance("Amount", 0f, 0f, 24000f, 100f);

    public Ambiance() {
        super("Ambience", ModuleCategory.RENDER);
        this.registerProperty(val);
    }

    @SubscribeEvent
    private void onPre(PlayerTickEvent e) {
        mc.theWorld.setWorldTime(val.getValue().intValue());
    }


    @SubscribeEvent
    private void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacket() instanceof S03PacketTimeUpdate)
            event.setCancelled(true);
    }
}





