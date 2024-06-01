package com.polarware.module.impl.player.antivoid;

import com.polarware.module.impl.movement.FlightModule;
import com.polarware.module.impl.movement.LongJumpModule;
import com.polarware.module.impl.other.TestModule;
import com.polarware.module.impl.player.AntiVoidModule;
import com.polarware.module.impl.player.ScaffoldModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.other.TeleportEvent;
import com.polarware.event.impl.other.WorldChangeEvent;
import com.polarware.event.impl.network.PacketSendEvent;
import com.polarware.util.packet.PacketUtil;
import com.polarware.util.player.PlayerUtil;
import com.polarware.value.Mode;
import com.polarware.value.impl.NumberValue;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.Vec3;

import java.util.concurrent.ConcurrentLinkedQueue;

public class WatchdogAntiVoid extends Mode<AntiVoidModule> {

    private final NumberValue distance = new NumberValue("Distance", this, 5, 0, 10, 1);

    private final ConcurrentLinkedQueue<C03PacketPlayer> packets = new ConcurrentLinkedQueue<>();
    private Vec3 position;

    private ScaffoldModule scaffoldModule;
    private LongJumpModule longJump;
    private FlightModule fly;
    private TestModule test;

    public WatchdogAntiVoid(String name, AntiVoidModule parent) {
        super(name, parent);
    }


    @EventLink()
    public final Listener<PacketSendEvent> onPacketSend = event -> {

        if (scaffoldModule == null) {
            scaffoldModule = getModule(ScaffoldModule.class);
        }

        if (fly== null) {
            fly = getModule(FlightModule.class);
        }

        if (longJump == null) {
            longJump = getModule(LongJumpModule.class);
        }

        if (test == null) {
            test = getModule(TestModule.class);
        }

        if (scaffoldModule.isEnabled() || longJump.isEnabled() || test.isEnabled() || fly.isEnabled()) {
            return;
        }

        final Packet<?> p = event.getPacket();

        if (p instanceof C03PacketPlayer) {
            final C03PacketPlayer wrapper = (C03PacketPlayer) p;

            if (!PlayerUtil.isBlockUnder()) {
                packets.add(wrapper);
                event.setCancelled(true);

                if (position != null && mc.thePlayer.fallDistance > distance.getValue().floatValue()) {
                    PacketUtil.sendNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(position.xCoord, position.yCoord + 0.1, position.zCoord, false));
                }
            } else {
                if (mc.thePlayer.onGround) {
                    position = new Vec3(wrapper.x, wrapper.y, wrapper.z);
                }

                if (!packets.isEmpty()) {
                    packets.forEach(PacketUtil::sendNoEvent);
                    packets.clear();
                }
            }
        }
    };


    @EventLink()
    public final Listener<TeleportEvent> onTeleport = event -> {

        if (packets.size() > 1) {
            packets.clear();
        }
    };

    @EventLink()
    public final Listener<WorldChangeEvent> onWorldChange = event -> {
        packets.clear();
    };
}