package io.github.nevalackin.radium.module.impl.other;

import io.github.nevalackin.radium.event.impl.packet.PacketSendEvent;
import io.github.nevalackin.radium.event.impl.player.UpdatePositionEvent;
import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.module.ModuleInfo;
import io.github.nevalackin.radium.property.impl.EnumProperty;
import io.github.nevalackin.radium.utils.ServerUtils;
import io.github.nevalackin.radium.utils.TimerUtil;
import io.github.nevalackin.radium.utils.Wrapper;
import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;

import java.util.ArrayList;
import java.util.List;

@ModuleInfo(label = "Ping Spoof", category = ModuleCategory.OTHER)
public final class PingSpoof extends Module {

    private final EnumProperty<PingSpoofMode> modeProperty = new EnumProperty<>("Mode", PingSpoofMode.TRANSACTION);

    private final List<Packet<?>> packets = new ArrayList<>();

    private final TimerUtil timer = new TimerUtil();

    public PingSpoof() {
        setSuffixListener(modeProperty);
    }

    @Listener
    public void onUpdatePositionEvent(UpdatePositionEvent event) {
        if (event.isPre()) {
            switch (modeProperty.getValue()) {
                case TRANSACTION:
                    if (!ServerUtils.isOnHypixel())
                        return;

                    if (Wrapper.getPlayer().ticksExisted < 5 && packets.size() > 0) {
                        packets.clear();
                        return;
                    }

                    if (timer.hasElapsed((long) (5000 + (Math.random() * 4000)))) {
                        while (packets.size() > 0)
                            Wrapper.sendPacketDirect(packets.remove(0));
                        timer.reset();
                    }
                    break;
            }
        }
    }

    @Listener
    public void onPacketSendEvent(PacketSendEvent e) {
        switch (modeProperty.getValue()) {
            case TRANSACTION:
                if (e.getPacket() instanceof C0FPacketConfirmTransaction) {
                    C0FPacketConfirmTransaction packet = (C0FPacketConfirmTransaction) e.getPacket();
                    if (packet.getUid() < 0) {
                        packets.add(packet);
                        e.setCancelled();
                    }
                } else if (e.getPacket() instanceof C00PacketKeepAlive) {
                    packets.add(e.getPacket());
                    e.setCancelled();
                }
                break;
        }
    }

    private enum PingSpoofMode {
        TRANSACTION
    }

}
