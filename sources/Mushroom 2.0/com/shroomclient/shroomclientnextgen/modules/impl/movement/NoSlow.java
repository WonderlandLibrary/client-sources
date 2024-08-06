package com.shroomclient.shroomclientnextgen.modules.impl.movement;

import com.shroomclient.shroomclientnextgen.config.ConfigChild;
import com.shroomclient.shroomclientnextgen.config.ConfigMode;
import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.config.ConfigParentId;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.modules.impl.combat.KillAura3;
import com.shroomclient.shroomclientnextgen.util.BlinkUtil;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.MovementUtil;
import com.shroomclient.shroomclientnextgen.util.PacketUtil;
import net.minecraft.item.BowItem;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.SwordItem;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

@RegisterModule(
    name = "No Slow",
    uniqueId = "noslow",
    description = "",
    category = ModuleCategory.Movement
)
public class NoSlow extends Module {

    @ConfigParentId("swordslow")
    @ConfigOption(
        name = "Swords",
        description = "No Slows With Swords",
        order = 1
    )
    public static Boolean swordNoSlow = true;

    @ConfigMode
    @ConfigChild("swordslow")
    @ConfigParentId("swordmode")
    @ConfigOption(name = "Sword Mode", description = "", order = 2)
    public static SwordSlowMode SwordMode = SwordSlowMode.Hypixel_On_Ground;

    @ConfigChild(value = "swordmode", parentEnumOrdinal = 4)
    @ConfigOption(
        name = "Unblink Every Tick",
        description = "What Tick It Unblinks On",
        max = 30,
        order = 2.5
    )
    public Float swordblink = 15F;

    @ConfigParentId("bowslow")
    @ConfigOption(name = "Bows", description = "Bow No Slow", order = 3)
    public static Boolean bowNoSlow = true;

    @ConfigChild("bowslow")
    @ConfigParentId("bowmode")
    @ConfigOption(name = "Bow Mode", description = "", order = 4)
    public static BowSlowMode BowMode = BowSlowMode.Hypixel_On_Ground;

    @ConfigChild(value = "bowmode", parentEnumOrdinal = 4)
    @ConfigOption(
        name = "Unblink Every Tick",
        description = "What Tick It Unblinks On",
        max = 30,
        order = 4.5
    )
    public Float bowblink = 15F;

    @ConfigParentId("eatslow")
    @ConfigOption(
        name = "Consumables",
        description = "Consumables No Slow",
        order = 5
    )
    public static Boolean eatNoSlow = true;

    @ConfigChild("eatslow")
    @ConfigParentId("eatmode")
    @ConfigOption(name = "Consumables Mode", description = "", order = 6)
    public static EatSlowMode eatMode = EatSlowMode.Hypixel_On_Ground;

    @ConfigChild(value = "eatmode", parentEnumOrdinal = 4)
    @ConfigOption(
        name = "Unblink Every Tick",
        description = "What Tick It Unblinks On",
        max = 30,
        order = 6.5
    )
    public Float eatblink = 15F;

    private int stopTicks = 3;
    private int stopCounter = 0;

    public static boolean doNoSlow = false;

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {
        BlinkUtil.setIncomingBlink(false);
    }

    @SubscribeEvent
    public void onMotionPre(MotionEvent.Pre e) {
        if (C.p() == null) return;

        if (C.p().isUsingItem() || KillAura3.isServerBlocking) {
            if (
                C.p()
                        .getInventory()
                        .getStack(C.p().getInventory().selectedSlot)
                        .getItem() instanceof
                    SwordItem ||
                C.p().getOffHandStack().getItem() instanceof ShieldItem ||
                C.p()
                        .getInventory()
                        .getStack(C.p().getInventory().selectedSlot)
                        .getItem() instanceof
                    ShieldItem
            ) {
                if (!swordNoSlow) return;

                switch (SwordMode) {
                    case Vanilla -> {
                        doNoSlow = true;
                    }
                    case Blink -> {
                        if (stopCounter > 0) {
                            BlinkUtil.setIncomingBlink(false);
                            doNoSlow = false;
                            stopCounter--;
                            // ChatUtil.sendPrefixMessage("nop");
                        } else {
                            if (MovementUtil.ticks % swordblink == 0) {
                                stopCounter = stopTicks;
                            } else {
                                BlinkUtil.setIncomingBlink(true);
                                doNoSlow = true;
                                // ChatUtil.sendPrefixMessage("blink");
                            }
                        }
                    }
                    case Grim -> {
                        doNoSlow = true;
                        C.p().swingHand(Hand.OFF_HAND);
                    }
                    case Old_Hypixel -> {
                        doNoSlow = true;
                        // Thanks Reactor Client :skull:
                        PacketUtil.sendPacket(
                            new PlayerInteractBlockC2SPacket(
                                Hand.MAIN_HAND,
                                new BlockHitResult(
                                    new Vec3d(0, 0, 0),
                                    Direction.DOWN,
                                    new BlockPos(0, 0, 0),
                                    false
                                ),
                                1
                            ),
                            false
                        );
                    }
                }
            } else if (
                C.p()
                    .getInventory()
                    .getStack(C.p().getInventory().selectedSlot)
                    .getItem() instanceof
                BowItem
            ) {
                if (!bowNoSlow) return;

                switch (BowMode) {
                    case Vanilla -> {
                        doNoSlow = true;
                    }
                    case Hypixel_On_Ground -> {
                        doNoSlow = C.p().isOnGround();
                    }
                    case Grim -> {
                        doNoSlow = true;
                        C.p().swingHand(Hand.OFF_HAND);
                    }
                    case Hypixel -> {
                        doNoSlow = true;
                        // Thanks Reactor Client :skull:
                        PacketUtil.sendPacket(
                            new PlayerInteractBlockC2SPacket(
                                Hand.MAIN_HAND,
                                new BlockHitResult(
                                    new Vec3d(0, 0, 0),
                                    Direction.DOWN,
                                    new BlockPos(0, 0, 0),
                                    false
                                ),
                                1
                            ),
                            false
                        );
                    }
                    case Blink -> {
                        if (stopCounter > 0) {
                            BlinkUtil.setIncomingBlink(false);
                            doNoSlow = false;
                            stopCounter--;
                            // ChatUtil.sendPrefixMessage("nop");
                        } else {
                            if (MovementUtil.ticks % bowblink == 0) {
                                stopCounter = stopTicks;
                            } else {
                                BlinkUtil.setIncomingBlink(true);
                                doNoSlow = true;
                                // ChatUtil.sendPrefixMessage("blink");
                            }
                        }
                    }
                }
            } else {
                if (!eatNoSlow) return;

                switch (eatMode) {
                    case Vanilla -> {
                        doNoSlow = true;
                    }
                    case Hypixel_On_Ground -> {
                        doNoSlow = C.p().isOnGround();
                    }
                    case Grim -> {
                        doNoSlow = true;
                        C.p().swingHand(Hand.OFF_HAND);
                    }
                    case Hypixel -> {
                        doNoSlow = true;
                        // Thanks Reactor Client :skull:
                        PacketUtil.sendPacket(
                            new PlayerInteractBlockC2SPacket(
                                Hand.MAIN_HAND,
                                new BlockHitResult(
                                    new Vec3d(0, 0, 0),
                                    Direction.DOWN,
                                    new BlockPos(0, 0, 0),
                                    false
                                ),
                                1
                            ),
                            false
                        );
                    }
                    case Blink -> {
                        if (stopCounter > 0) {
                            BlinkUtil.setIncomingBlink(false);
                            doNoSlow = false;
                            stopCounter--;
                            // ChatUtil.sendPrefixMessage("nop");
                        } else {
                            if (MovementUtil.ticks % eatblink == 0) {
                                stopCounter = stopTicks;
                            } else {
                                BlinkUtil.setIncomingBlink(true);
                                doNoSlow = true;
                                // ChatUtil.sendPrefixMessage("blink");
                            }
                        }
                    }
                }
            }
        } else {
            doNoSlow = false;
            BlinkUtil.setIncomingBlink(false);
        }
    }

    public enum SwordSlowMode {
        Vanilla,
        Hypixel_On_Ground,
        Grim,
        Old_Hypixel,
        Blink,
    }

    public enum BowSlowMode {
        Vanilla,
        Hypixel_On_Ground,
        Grim,
        Hypixel,
        Blink,
    }

    public enum EatSlowMode {
        Vanilla,
        Hypixel_On_Ground,
        Grim,
        Hypixel,
        Blink,
    }
}
