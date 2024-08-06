package com.shroomclient.shroomclientnextgen.modules.impl.movement;

import com.shroomclient.shroomclientnextgen.config.ConfigChild;
import com.shroomclient.shroomclientnextgen.config.ConfigMode;
import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.config.ConfigParentId;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import com.shroomclient.shroomclientnextgen.events.impl.StepEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.MovementUtil;
import com.shroomclient.shroomclientnextgen.util.TimerUtil;

@RegisterModule(
    name = "Step",
    uniqueId = "step",
    description = "Auto Steps Up Blocks",
    category = ModuleCategory.Movement
)
public class Step extends Module {

    @ConfigMode
    @ConfigParentId("mode")
    @ConfigOption(name = "Mode", description = "", order = 1)
    public static Mode mode = Mode.Vanilla;

    @ConfigChild(value = "mode", parentEnumOrdinal = { 2, 3 })
    @ConfigOption(
        name = "Timer Speed",
        description = "Timer While Stepping Speed",
        max = 5,
        precision = 2,
        order = 1.1
    )
    public Float TimerSpeed = 2F;

    @ConfigChild(value = "mode", parentEnumOrdinal = { 2, 3 })
    @ConfigOption(
        name = "Balance Timer",
        description = "Balances The Timer Speed To Make It Bypass Better",
        order = 1.2
    )
    public Boolean balanceTimer = true;

    @ConfigChild(value = "mode", parentEnumOrdinal = { 2, 3 })
    @ConfigOption(
        name = "Jump When Finished",
        description = "Jumps After Finishing To Climb Faster + Lower VL",
        order = 1.3
    )
    public Boolean doubleJump = true;

    @ConfigChild(value = "mode", parentEnumOrdinal = 0)
    @ConfigOption(
        name = "Height",
        description = "",
        min = 0,
        max = 9,
        order = 2,
        precision = 2
    )
    public static Float Height = 2F;

    @ConfigParentId("reverse")
    @ConfigOption(
        name = "Reverse",
        description = "Steps Down Blocks",
        order = 3
    )
    public static Boolean Reverse = true;

    @ConfigChild("reverse")
    @ConfigOption(
        name = "Speed",
        description = "Speed For Reverse Step",
        min = 1,
        max = 10,
        precision = 2,
        order = 4
    )
    public Float Speed = 1F;

    Boolean OnGround = false;

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {
        bypissStepTick = -1;
    }

    float stepYaw = 0;
    public static int bypissStepTick = -1;

    @SubscribeEvent
    public void onMotionPre(MotionEvent.Pre e) {
        if (
            !C.p().isOnGround() &&
            OnGround &&
            C.p().getVelocity().getY() <= 0 &&
            Reverse
        ) {
            C.p().setVelocity(0, -Speed, 0);
        }

        if (bypissStepTick != -1) {
            bypissStepTick++;

            switch (mode) {
                case NCP -> {
                    switch (bypissStepTick) {
                        case 1:
                            TimerUtil.setTPS(TimerSpeed * 20f);
                            MovementUtil.jumpBoostless();
                            stepYaw = MovementUtil.getYaw();
                            break;
                        case 2:
                            C.p()
                                .setVelocity(
                                    C.p().getVelocity().x,
                                    0.33,
                                    C.p().getVelocity().z
                                );
                            break;
                        case 3:
                            C.p()
                                .setVelocity(
                                    C.p().getVelocity().x,
                                    1 - (C.p().getY() % 1),
                                    C.p().getVelocity().z
                                );
                            break;
                        case 4:
                            C.p()
                                .setVelocity(
                                    C.p().getVelocity().x,
                                    0,
                                    C.p().getVelocity().z
                                );
                            break;
                        case 5:
                            if (balanceTimer) TimerUtil.setTPS(
                                (1 / (4 * TimerSpeed)) * 20
                            );
                            else TimerUtil.resetTPS();

                            if (doubleJump) MovementUtil.jump();
                            break;
                        case 6:
                            TimerUtil.resetTPS();
                            bypissStepTick = -1;
                            break;
                    }
                }
                case Hypixel -> {
                    switch (bypissStepTick) {
                        case 1:
                            TimerUtil.setTPS(TimerSpeed * 20f);
                            MovementUtil.jumpBoostless();
                            stepYaw = MovementUtil.getYaw();
                            break;
                        case 2:
                            C.p()
                                .setVelocity(
                                    C.p().getVelocity().x,
                                    0.33,
                                    C.p().getVelocity().z
                                );
                            MovementUtil.doMotion(0.1, stepYaw - 180);
                            break;
                        case 3:
                            C.p()
                                .setVelocity(
                                    C.p().getVelocity().x,
                                    1 - (C.p().getY() % 1),
                                    C.p().getVelocity().z
                                );
                            MovementUtil.doMotion(0.1, stepYaw - 180);
                            break;
                        case 4:
                            C.p()
                                .setVelocity(
                                    C.p().getVelocity().x,
                                    0,
                                    C.p().getVelocity().z
                                );
                            MovementUtil.doMotion(0.3, stepYaw);
                            break;
                        case 5:
                            if (balanceTimer) TimerUtil.setTPS(
                                (1 / (4 * TimerSpeed)) * 20
                            );
                            else TimerUtil.resetTPS();

                            MovementUtil.setMotion(0.1);

                            if (doubleJump) MovementUtil.jump();
                            break;
                        case 6:
                            TimerUtil.resetTPS();
                            bypissStepTick = -1;
                            break;
                    }
                }
                //case Vulcan -> {
                //    if (bypissStepTick == 1) {
                //        MovementUtil.setYmotion(1);
                //    } else {
                //        PacketUtil.sendSequencedPacket(sequence -> new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, WorldUtil.rayTrace(90, MovementUtil.getYaw(), 5), sequence));
                //
                //        if (startSlot != -1) {
                //            C.p().getInventory().selectedSlot = startSlot;
                //            startSlot = -1;
                //        }
                //
                //        MovementUtil.setYmotion(0);
                //        bypissStepTick = -1;
                //    }
                //}
            }
        }

        OnGround = C.p().isOnGround();
    }

    int startSlot = -1;
    int block = -1;

    @SubscribeEvent
    public void onStepEvent(StepEvent e) {
        switch (mode) {
            case Jump -> {
                if (e.height < 1.3) {
                    MovementUtil.jump();
                }
            }
            case NCP, Hypixel -> {
                if (bypissStepTick == -1) {
                    if (e.height < 1.3) {
                        bypissStepTick = 0;
                    }
                }
            }
            //case Vulcan -> {
            //    if (bypissStepTick == -1) {
            //        // only works while holding blocks
            //        block = Scaffold.getBlock();
            //
            //        if (e.height < 1.3) {
            //            if (block != -1) {
            //
            //                if (startSlot == -1)
            //                    startSlot = C.p().getInventory().selectedSlot;
            //                C.p().getInventory().selectedSlot = block;
            //
            //                bypissStepTick = 0;
            //            }
            //        }
            //    }
            //}
        }
    }

    public enum Mode {
        Vanilla,
        Jump,
        NCP,
        Hypixel,
        //Vulcan
    }
}
