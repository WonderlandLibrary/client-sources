package me.dinozoid.strife.module.implementations.movement;

import me.dinozoid.strife.alpine.listener.EventHandler;
import me.dinozoid.strife.alpine.listener.Listener;
import me.dinozoid.strife.event.implementations.network.PacketInboundEvent;
import me.dinozoid.strife.event.implementations.network.PacketOutboundEvent;
import me.dinozoid.strife.event.implementations.player.UpdatePlayerEvent;
import me.dinozoid.strife.module.Category;
import me.dinozoid.strife.module.Module;
import me.dinozoid.strife.module.ModuleInfo;
import me.dinozoid.strife.property.implementations.EnumProperty;
import me.dinozoid.strife.util.player.PlayerUtil;
import net.minecraft.network.play.client.C03PacketPlayer;

@ModuleInfo(name = "NoFall", renderName = "NoFall", description = "Take no fall damage.", category = Category.MOVEMENT)
public class NoFallModule extends Module {

    private final EnumProperty<NoFallMode> noFallModeProperty = new EnumProperty<>("Mode", NoFallMode.PACKET);

    @Override
    public void init() {
        super.init();
        addValueChangeListener(noFallModeProperty);
    }

    @EventHandler
    private final Listener<UpdatePlayerEvent> updatePlayerListener = new Listener<>(event -> {
        switch (noFallModeProperty.value()) {
            case SPOOF:
                if (mc.thePlayer.fallDistance > 3) {
                    event.ground(true);
                }
                break;
            case PACKET:
                if (mc.thePlayer.fallDistance > 3 && PlayerUtil.isBlockUnder()) {
                    mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer(true));
                    mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
                    mc.thePlayer.fallDistance = 0;
                }
                break;
        }
    });
    @EventHandler
    private final Listener<PacketOutboundEvent> packetOutboundListener = new Listener<>(event -> {
        if (event.packet() instanceof C03PacketPlayer) {
            C03PacketPlayer packet = (C03PacketPlayer) event.packet();
            switch (noFallModeProperty.value()) {
                case NOGROUND:
                    packet.setOnGround(false);
                    break;
                case EDIT:
                    packet.setOnGround(true);
                    break;
            }
        }
    });

    private enum NoFallMode {
        SPOOF, NOGROUND, EDIT, PACKET
    }

}
