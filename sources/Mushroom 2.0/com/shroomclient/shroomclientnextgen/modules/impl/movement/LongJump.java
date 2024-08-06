package com.shroomclient.shroomclientnextgen.modules.impl.movement;

import com.shroomclient.shroomclientnextgen.config.ConfigChild;
import com.shroomclient.shroomclientnextgen.config.ConfigMode;
import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.config.ConfigParentId;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.ClientTickEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import com.shroomclient.shroomclientnextgen.events.impl.PacketEvent;
import com.shroomclient.shroomclientnextgen.listeners.MoveListener;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.modules.impl.client.Notifications;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.MovementUtil;
import com.shroomclient.shroomclientnextgen.util.RotationUtil;
import java.awt.*;
import java.util.Objects;
import net.minecraft.item.FireChargeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

@RegisterModule(
    name = "Long Jump",
    uniqueId = "longjump",
    description = "",
    category = ModuleCategory.Movement
)
public class LongJump extends Module {

    // pretend this isnt decompiled code, i pulled batman and it overwrote my longjump and idk why, only save i have was mc classes

    @ConfigMode
    @ConfigParentId("mode")
    @ConfigOption(name = "Mode", description = "", order = 1.0)
    public static Mode mode = LongJump.Mode.Vanilla;

    public static boolean fireballed = false;

    public static boolean kbed = false;

    @ConfigOption(
        name = "Auto Disable",
        description = "Disables Module When done",
        order = 2.0
    )
    public Boolean autoDisable = true;

    @ConfigChild(value = "mode", parentEnumOrdinal = 1)
    @ConfigOption(
        name = "Auto Switch",
        description = "Auto Selects A Fireball In Your Hotbar",
        order = 2.0
    )
    public Boolean switcha = true;

    @ConfigChild(value = "mode", parentEnumOrdinal = 1)
    @ConfigOption(
        name = "Fireball Fly",
        description = "Makes You Fly After Using A Fireball (Hypixel)",
        order = 2.2
    )
    public static Boolean hypixelBallin = true;

    @ConfigChild(value = "mode", parentEnumOrdinal = 0)
    @ConfigOption(
        name = "Speed",
        description = "Speed You Travel At In Air",
        min = 0.1,
        max = 10.0,
        precision = 1,
        order = 3.0
    )
    public Float Speed = 2.0F;

    Boolean jumped = false;
    int stack = 0;
    boolean swapped = false;
    boolean finishedRotation = false;
    boolean rotatingDone = false;
    Vec2f rots = null;
    public static int fireballTicks = 0;

    static int getFireball() {
        if (C.p() != null) {
            Item var1 = C.p()
                .getInventory()
                .getStack(C.p().getInventory().selectedSlot)
                .getItem();
            if (var1 instanceof FireChargeItem) {
                FireChargeItem h = (FireChargeItem) var1;
                return C.p().getInventory().selectedSlot;
            }
        }

        int current = -1;
        int stackSize = 0;

        for (int i = 0; i < 9; ++i) {
            ItemStack stack = C.p().getInventory().getStack(i);
            if (stack != null && stackSize < stack.getCount()) {
                Item var5 = stack.getItem();
                if (var5 instanceof FireChargeItem) {
                    FireChargeItem h = (FireChargeItem) var5;
                    stackSize = stack.getCount();
                    current = i;
                }
            }
        }

        return current;
    }

    protected void onEnable() {
        switch (mode) {
            case Fireball:
                if (fireballed) {
                    ModuleManager.setEnabled(LongJump.class, false, false);
                }

                if (this.switcha && C.p() != null) {
                    kbed = true;
                    this.swapped = false;
                    this.finishedRotation = false;
                    this.rotatingDone = false;
                    this.rots = null;
                    fireballed = false;
                    MoveListener.fireballed = false;
                    this.stack = C.p().getInventory().selectedSlot;
                    MoveListener.recieved = false;
                    //BlinkUtil.setIncomingBlink(false);
                    //BlinkUtil.setOutgoingBlink(false);
                }
            default:
        }
    }

    protected void onDisable() {
        switch (mode) {
            case Fireball:
                if (this.switcha && C.p() != null) {
                    C.p().getInventory().selectedSlot = this.stack;
                }
            default:
        }
    }

    @SubscribeEvent
    public void onMotionPre(MotionEvent.Pre e) {
        switch (mode) {
            case Vanilla:
                if (C.mc.options.jumpKey.isPressed()) {
                    this.jumped = true;
                }

                if (this.jumped && C.p().isOnGround()) {
                    C.p().setVelocity(0.0, 0.4, 0.0);
                    MovementUtil.setMotion((double) this.Speed);
                    this.jumped = false;
                    if (this.autoDisable) {
                        ModuleManager.setEnabled(LongJump.class, false, false);
                    }
                }
                break;
            case Fireball:
                if (this.switcha) {
                    int current = getFireball();
                    if (current == -1) {
                        Notifications.notify(
                            "No Fireball Found",
                            new Color(200, 0, 0),
                            2
                        );
                        ModuleManager.setEnabled(LongJump.class, false, false);
                        return;
                    }

                    C.p().getInventory().selectedSlot = getFireball();
                    this.swapped = true;
                }

                if (
                    C.p()
                        .getInventory()
                        .getStack(C.p().getInventory().selectedSlot)
                        .getItem() instanceof
                    FireChargeItem
                ) {
                    float rotationYaw = MovementUtil.getYaw() - 180.0F;
                    float rotPitch = (float) (89f + Math.random());
                    if (!this.rotatingDone) {
                        Vec2f rotsNew = RotationUtil.getSmoothRotation(
                            Objects.requireNonNullElseGet(
                                this.rots,
                                () -> new Vec2f(e.getPitch(), e.getYaw())
                            ),
                            new Vec2f(rotPitch, rotationYaw),
                            1.5F
                        );
                        e.setYaw(rotsNew.y);
                        e.setPitch(rotsNew.x);
                        this.rots = new Vec2f(e.getPitch(), e.getYaw());
                        if (
                            RotationUtil.getAngleDifference(
                                    e.getYaw(),
                                    rotationYaw
                                ) <
                                3.0F &&
                            RotationUtil.getAngleDifference(
                                    e.getPitch(),
                                    rotPitch
                                ) <
                                3.0F
                        ) {
                            this.rotatingDone = true;
                            e.setYaw(rotationYaw);
                            e.setPitch(rotPitch);
                        }
                    }

                    if (this.finishedRotation) {
                        if (!C.p().isOnGround() && hypixelBallin) {
                            Notifications.notify(
                                "Please Start On Ground",
                                new Color(232, 49, 173),
                                2
                            );
                            ModuleManager.setEnabled(
                                LongJump.class,
                                false,
                                false
                            );
                        } else {
                            e.setYaw(rotationYaw);
                            e.setPitch(rotPitch);
                            if (C.p().isOnGround() && !hypixelBallin) {
                                MovementUtil.jump();
                            }

                            BlockHitResult blockHit = Scaffold.realRayTrace(
                                e.getYaw(),
                                e.getPitch(),
                                5.0F
                            );

                            C.mc.interactionManager.interactBlock(
                                C.p(),
                                Hand.MAIN_HAND,
                                blockHit
                            );
                            C.mc.interactionManager.interactItem(
                                C.p(),
                                Hand.MAIN_HAND
                            );

                            fireballed = true;
                            kbed = false;
                            fireballTicks = MovementUtil.ticks;
                            //BlinkUtil.setOutgoingBlink(true);
                            //BlinkUtil.setIncomingBlink(true);
                            Notifications.notify(
                                "Nyoooom",
                                new Color(2, 206, 96),
                                2
                            );
                            ModuleManager.setEnabled(
                                LongJump.class,
                                false,
                                false
                            );
                        }
                    }
                }
        }
    }

    boolean onPosLook = false;

    Vec3d prevVelo;

    @SubscribeEvent
    public void onClientTick(ClientTickEvent e) {
        if (mode == Mode.Matrix && C.p().fallDistance > 0) {
            MovementUtil.setYmotion(0.42f);
            MovementUtil.setMotion(1.5f);
        }
    }

    @SubscribeEvent
    public void onPacketReceive(PacketEvent.Receive.Pre e) {
        if (mode == Mode.Matrix) {
            if (e.getPacket() instanceof PlayerPositionLookS2CPacket) {
                onPosLook = true;
                prevVelo = C.p().getVelocity();
            }
        }
    }

    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send.Pre e) {}

    @SubscribeEvent
    public void onSendPacket(PacketEvent.Send.Pre e) {
        if (e.getPacket() instanceof PlayerMoveC2SPacket.Full && rotatingDone) {
            finishedRotation = true;
        }

        if (mode == Mode.Matrix) {
            if (e.getPacket() instanceof PlayerMoveC2SPacket.Full) {
                if (onPosLook) {
                    C.p().setVelocity(prevVelo);
                    onPosLook = false;

                    if (autoDisable) ModuleManager.setEnabled(
                        LongJump.class,
                        false,
                        false
                    );
                }
            }
        }
    }

    public enum Mode {
        Vanilla,
        Fireball,
        // flags, then u can just keep the velocity from before flagging :skull:
        Matrix,
    }
}
