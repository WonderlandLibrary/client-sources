package dev.vertic.module.impl.player;

import dev.vertic.event.api.EventLink;
import dev.vertic.event.impl.motion.PreMotionEvent;
import dev.vertic.event.impl.packet.PacketSendEvent;
import dev.vertic.module.Module;
import dev.vertic.module.api.Category;
import dev.vertic.setting.impl.BooleanSetting;
import dev.vertic.setting.impl.NumberSetting;
import dev.vertic.util.player.PacketUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Blink extends Module {

    private final BooleanSetting autoSend = new BooleanSetting("Auto Send", false);
    private final NumberSetting packetCap = new NumberSetting("Packet Limit", autoSend::isEnabled, 20, 1, 500, 1);
    private final List<Packet<?>> packetsList = new CopyOnWriteArrayList<>();

    public Blink() {
        super("Blink", "Pauses sending packets to the server while enabled.", Category.PLAYER);
        this.addSettings(autoSend, packetCap);
    }

    @Override
    protected void onDisable() {
        sendPackets();
    }

    @EventLink
    public void packetSendEvent(PacketSendEvent event) {
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
    }

    @EventLink
    public void onPreMotion(PreMotionEvent event) {
        if(autoSend.isEnabled()) {
            sendPackets();
        }
    }

    public void sendPackets() {
        if(packetsList.size() >= packetCap.getInt() && !packetsList.isEmpty()){
            for(Packet<?> packet : packetsList){
                PacketUtil.sendPacketWithoutEvent(packet);
                packetsList.remove(packet);
            }
        }
    }

    @Override
    public String getSuffix() {
        return packetsList.size() + "";
    }
}
