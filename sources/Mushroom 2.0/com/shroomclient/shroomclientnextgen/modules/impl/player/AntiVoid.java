package com.shroomclient.shroomclientnextgen.modules.impl.player;

import com.shroomclient.shroomclientnextgen.config.ConfigChild;
import com.shroomclient.shroomclientnextgen.config.ConfigMode;
import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.config.ConfigParentId;
import com.shroomclient.shroomclientnextgen.events.EventBus;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import com.shroomclient.shroomclientnextgen.events.impl.PacketEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.modules.impl.movement.Scaffold;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.MovementUtil;
import com.shroomclient.shroomclientnextgen.util.PacketUtil;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Hand;

@RegisterModule(
    name = "Anti Void",
    uniqueId = "antivoid",
    description = "Stops You From Falling Into The void",
    category = ModuleCategory.Player
)
public class AntiVoid extends Module {

    @ConfigMode
    @ConfigParentId("mode")
    @ConfigOption(name = "Mode", description = "", order = 1)
    public static Mode mode = Mode.Blink;

    @ConfigChild(value = "mode", parentEnumOrdinal = 1)
    @ConfigOption(
        name = "Always Send Back",
        description = "Sends You Back To Last Pos Even If AC Doesn't",
        order = 2
    )
    public static Boolean clipBack = true;

    private double motionY = 0.0;

    @Override
    protected void onEnable() {
        if (
            C.p() != null && NoFall.isOverVoid() && mode == Mode.Blink
        ) ModuleManager.toggle(AntiVoid.class, false);
    }

    @Override
    protected void onDisable() {}

    private static final Queue<PlayerMoveC2SPacket> packetQueue =
        new ConcurrentLinkedQueue<>();

    boolean tisSpoofTime = false;

    @SubscribeEvent(value = EventBus.Priority.HIGHEST)
    public void onPacket(PacketEvent.Send.Pre event) {
        switch (mode) {
            case Blink -> {
                // mode blink
                if (
                    !ModuleManager.isEnabled(Scaffold.class) &&
                    event.getPacket() instanceof PlayerMoveC2SPacket p
                ) {
                    if (
                        NoFall.isOverVoid() &&
                        MovementUtil.lastLeaveGroundPos ==
                            MovementUtil.lastLeaveGroundPosNoScaffold
                    ) {
                        packetQueue.offer(p);
                        event.cancel();
                        if (C.p().fallDistance > 5) {
                            packetQueue.clear();
                            C.p().fallDistance = 0f;
                            C.p()
                                .setPosition(
                                    MovementUtil.lastNotOverVoidPos.x,
                                    MovementUtil.lastNotOverVoidPos.y,
                                    MovementUtil.lastNotOverVoidPos.z
                                );
                            C.p().setVelocity(0.0, motionY, 0.0);
                        }
                    } else {
                        motionY = C.p().getVelocity().y;
                        while (!packetQueue.isEmpty()) {
                            PacketUtil.sendPacket(packetQueue.poll(), false);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent(EventBus.Priority.LOWEST)
    public void onMotionPre(MotionEvent.Pre event) {
        switch (mode) {
            case Collision -> {
                if (
                    C.p().fallDistance > 3 &&
                    NoFall.isOverVoid() &&
                    !C.p().isOnGround()
                ) {
                    C.p().swingHand(Hand.MAIN_HAND);
                    MovementUtil.setYmotion(0);
                    event.setOnGround(true);
                    tisSpoofTime = false;

                    if (clipBack) {
                        C.p()
                            .setPosition(
                                MovementUtil.lastNotOverVoidPos.x,
                                MovementUtil.lastNotOverVoidPos.y,
                                MovementUtil.lastNotOverVoidPos.z
                            );
                        C.p().setVelocity(0, 0, 0);
                    }

                    C.p().fallDistance = 0;
                }
            }
        }
    }

    public enum Mode {
        Blink,
        Collision,
    }
}
