package com.shroomclient.shroomclientnextgen.modules.impl.movement;

import com.shroomclient.shroomclientnextgen.config.ConfigMode;
import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.MovementUtil;
import com.shroomclient.shroomclientnextgen.util.PacketUtil;
import com.shroomclient.shroomclientnextgen.util.WorldUtil;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.Hand;

@RegisterModule(
    name = "Spider",
    uniqueId = "wallclimb",
    description = "Climbs Up Walls",
    category = ModuleCategory.Movement
)
public class Spider extends Module {

    @ConfigMode
    @ConfigOption(name = "Mode", description = "", order = 1)
    public static Mode mode = Mode.Vanilla;

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}

    private final ScheduledExecutorService scheduler =
        Executors.newScheduledThreadPool(1);
    int startSlot = -1;
    int block = -1;
    public boolean stop = false;

    @SubscribeEvent
    public void onMotionPre(MotionEvent.Pre e) {
        switch (mode) {
            case Vanilla -> {
                if (C.p().horizontalCollision) {
                    MovementUtil.setYmotion(0.3);
                }
            }
            case Vulcan_Block -> {
                if (C.p().horizontalCollision) {
                    // only works while holding blocks
                    block = Scaffold.getBlock();

                    if (block != -1) {
                        MovementUtil.setYmotion(0.4);

                        if (startSlot == -1) startSlot = C.p()
                            .getInventory()
                            .selectedSlot;
                        C.p().getInventory().selectedSlot = block;

                        PacketUtil.sendSequencedPacket(
                            sequence ->
                                new PlayerInteractBlockC2SPacket(
                                    Hand.MAIN_HAND,
                                    WorldUtil.rayTrace(
                                        0,
                                        MovementUtil.getYaw(),
                                        5
                                    ),
                                    sequence
                                )
                        );
                    }
                } else if (startSlot != -1) {
                    C.p().getInventory().selectedSlot = startSlot;
                    startSlot = -1;
                }
            }
            case Vulcan -> {
                if (C.p().horizontalCollision) {
                    if (!C.p().isClimbing()) {
                        stop = true;
                        scheduler.schedule(
                            () -> {
                                C.p()
                                    .setVelocity(
                                        C.p().getVelocity().getX(),
                                        1.3599696,
                                        C.p().getVelocity().getZ()
                                    );
                                scheduler.schedule(
                                    () -> {
                                        C.p().setVelocity(0.0, 0.0001, 0.0);
                                    },
                                    200,
                                    TimeUnit.MILLISECONDS
                                );
                            },
                            200,
                            TimeUnit.MILLISECONDS
                        );
                    }
                } else if (stop) {
                    C.p()
                        .setVelocity(
                            C.p().getVelocity().getX(),
                            0,
                            C.p().getVelocity().getZ()
                        );
                    stop = false;
                }
            }
        }
    }

    public enum Mode {
        Vanilla,
        Vulcan_Block,
        Vulcan,
    }
}
