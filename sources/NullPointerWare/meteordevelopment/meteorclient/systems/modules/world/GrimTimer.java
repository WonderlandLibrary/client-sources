/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.world;

import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.movement.VulcanFly;
import meteordevelopment.meteorclient.systems.modules.movement.VulcanTP;
import meteordevelopment.meteorclient.utils.misc.Keybind;
import meteordevelopment.meteorclient.utils.network.LongPacketClass;
import meteordevelopment.meteorclient.utils.player.PlayerUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.common.CommonPingS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.concurrent.CopyOnWriteArrayList;

public final class GrimTimer extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public final Setting<Mode> mode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("Mode")
        .description("Mode to use")
        .defaultValue(Mode.MultiplePackets)
        .build()
    );

    private final Setting<Integer> packetamount = sgGeneral.add(new IntSetting.Builder()
        .name("packetamount")
        .description("How many exploit packets timer should send.")
        .defaultValue(1)
        .min(1)
        .sliderMax(100)
        .visible(() -> mode.get() == Mode.MultiplePackets)
        .build()
    );

    private final Setting<Boolean> onlyuse = sgGeneral.add(new BoolSetting.Builder()
        .name("only-use")
        .description("If only to send packets if you are using an item.")
        .defaultValue(true)
        .visible(() -> mode.get() == Mode.MultiplePackets)
        .build()
    );

    private final Setting<Boolean> faster = sgGeneral.add(new BoolSetting.Builder()
        .name("faster")
        .description("If to send a funny packet to make everything faster.")
        .defaultValue(true)
        .visible(() -> mode.get() == Mode.MultiplePackets)
        .build()
    );

    private final Setting<Keybind> timerkeybind = sgGeneral.add(new KeybindSetting.Builder()
        .name("timer-bind")
        .description("This keybind needs to be pressed for timer to work..")
        .visible(() -> mode.get() == Mode.BalanceAbuse)
        .defaultValue(Keybind.none())
        .build()
    );

    public final Setting<Integer> maxuse = sgGeneral.add(new IntSetting.Builder()
        .name("maxuse")
        .description("What should be the max power")
        .defaultValue(1)
        .min(1)
        .sliderMax(500)
        .visible(() -> mode.get() == Mode.BalanceAbuse)
        .build()
    );

    public final Setting<Integer> delay = sgGeneral.add(new IntSetting.Builder()
        .name("delay")
        .description("What should be the packet delay")
        .defaultValue(55000)
        .min(1)
        .sliderMax(55000)
        .visible(() -> mode.get() == Mode.BalanceAbuse)
        .build()
    );

    private final Setting<Double> multiplier = sgGeneral.add(new DoubleSetting.Builder()
        .name("multiplier")
        .description("The timer multiplier amount.")
        .defaultValue(1)
        .min(0.1)
        .sliderMin(0.1)
        .build()
    );


    public GrimTimer() {
        super(Categories.World, "grim-timer", "Timer but for Grim");
    }

    @Override
    public void onActivate() {
        releasedkey = false;
        usingTimer = false;
        cancelTimer = 0;
        power = 0;
    }

    //Balance
    boolean releasedkey = false;
    public boolean usingTimer = false;


    CopyOnWriteArrayList<LongPacketClass> longPacketClassCopyOnWriteArrayList = new CopyOnWriteArrayList<>();

    int cancelTimer = 0;
    public int power = 0;



    @EventHandler
    public void onTick(final TickEvent.Pre e) {
        switch (mode.get()) {
            case MultiplePackets -> {
                if (onlyuse.get() && !mc.player.isUsingItem()) return;
                if (Modules.get().get(VulcanFly.class).isActive() || Modules.get().get(VulcanTP.class).isActive()) return;
                for (int i = 0; i < packetamount.get(); i++)
                    sendNoEvent(new PlayerMoveC2SPacket.Full(mc.player.getX(), mc.player.getY(), mc.player.getZ(), mc.player.getYaw(), mc.player.getPitch(), mc.player.isOnGround()));


                if (mc.player.getInventory().getMainHandStack().getItem() == Items.BOW && mc.player.isUsingItem()) {
                    sendNoEvent(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.RELEASE_USE_ITEM, new BlockPos(0,0,0), Direction.DOWN));
                }
                if (faster.get() && mc.player.isUsingItem())
                    sendNoEvent(new PlayerInteractItemC2SPacket(Hand.OFF_HAND, 0));

            }
            case BalanceAbuse -> {
                cancelTimer++;
                if (!timerkeybind.get().isValid()) {
                    error("Keybind is not valid!");
                    this.toggle();
                }
                if (!timerkeybind.get().isPressed() && mc.currentScreen == null)
                    releasedkey = true;
                if (timerkeybind.get().isPressed() && releasedkey && mc.currentScreen == null) {
                    usingTimer = !usingTimer;
                    releasedkey = false;
                }

                if (!PlayerUtils.isMoving() && mc.player.getVelocity().x == 0.0 && mc.player.getVelocity().z == 0.0) {
                    power = power + 1 > maxuse.get() ? maxuse.get() : ++power;
                }
                if (usingTimer && power > 0) {
                    --power;
                }
                for (LongPacketClass longPacketClass : longPacketClassCopyOnWriteArrayList) {
                    if (longPacketClass.getId() + delay.get() < System.currentTimeMillis() && longPacketClass.getPacket() != null) {
                        longPacketClass.getPacket().apply(mc.getNetworkHandler());
                        longPacketClassCopyOnWriteArrayList.remove(longPacketClass);
                    }
                }
                Modules.get().get(Timer.class).setOverride(usingTimer && power > 10 ? multiplier.get() : 1.0f);
            }
            default -> {
                error("Invalid mode!");
                this.toggle();
            }
        }
    }

    @EventHandler
    public void onPacketRecieve(final PacketEvent.Receive e) {
        if (mode.get() == Mode.BalanceAbuse) {
            if (e.packet instanceof CommonPingS2CPacket packet) {
                longPacketClassCopyOnWriteArrayList.add(new LongPacketClass(packet, System.currentTimeMillis()));
                e.setCancelled(true);
            }
            if (e.packet instanceof EntityVelocityUpdateS2CPacket packet && packet.getId() == mc.player.getId()) {
                dump();
            }
            if (e.packet instanceof ExplosionS2CPacket) {
                dump();
            }
        }
    }

    private void dump() {
        for (LongPacketClass longPacketClass : longPacketClassCopyOnWriteArrayList) {
            longPacketClass.getPacket().apply(mc.getNetworkHandler());
            longPacketClassCopyOnWriteArrayList.remove(longPacketClass);
        }
    }

    @Override
    public void onDeactivate() {
        Modules.get().get(Timer.class).setOverride(Timer.OFF);
        power = 0;
        // Dump doesnt flag but bombs with hundreds of packets
        //dump();
        longPacketClassCopyOnWriteArrayList.clear();
    }


    public enum Mode {
        MultiplePackets,
        BalanceAbuse
    }
}
