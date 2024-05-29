package dev.client.tenacity.module.impl.player;

import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.event.EventListener;
import dev.event.impl.network.PacketSendEvent;
import dev.event.impl.player.MotionEvent;
import dev.settings.impl.ModeSetting;
import dev.settings.impl.NumberSetting;
import dev.utils.network.PacketUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("unused")
public final class Blink extends Module {
    private final ModeSetting mode = new ModeSetting("Mode", "Fake Lag", "Fake Lag");
    private final NumberSetting packetCap = new NumberSetting("Packet Cap", 1, 1000, 1, 1);
    private final List<Packet<?>> packetsList = new CopyOnWriteArrayList<>();
    private final EventListener<PacketSendEvent> packetSendEventEventListener = event -> {
        if(event.getPacket() instanceof C03PacketPlayer){
            if(event.getPacket() instanceof C03PacketPlayer.C04PacketPlayerPosition){
                packetsList.add(event.getPacket());
                event.cancel();
            }
            if(event.getPacket() instanceof C03PacketPlayer.C05PacketPlayerLook){
                packetsList.add(event.getPacket());
                event.cancel();
            }
            if(event.getPacket() instanceof C03PacketPlayer.C06PacketPlayerPosLook){
                packetsList.add(event.getPacket());
                event.cancel();
            }
        }
    };

    private final EventListener<MotionEvent> motionEventEventListener = event -> {
        if(packetsList.size() >= packetCap.getValue().intValue() && !packetsList.isEmpty()){
            for(Packet<?> packet : packetsList){
                PacketUtils.sendPacketNoEvent(packet);
                packetsList.remove(packet);
            }
        }
    };

    public Blink() {
        super("Blink", Category.PLAYER, "holds movement packets");
        this.addSettings(mode, packetCap);
    }
}
