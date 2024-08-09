/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.combat;


import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.mixininterface.IPlayerInteractEntityC2SPacket;
import meteordevelopment.meteorclient.mixininterface.IPlayerMoveC2SPacket;
import meteordevelopment.meteorclient.mixininterface.IVec3d;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.MathHelper;

public final class Criticals extends Module {

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Mode> mode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("mode")
        .description("The mode on how Criticals will function.")
        .defaultValue(Mode.Packet)
        .build()
    );

    private final Setting<Double> grimPosition = sgGeneral.add(new DoubleSetting.Builder()
        .name("grim-position")
        .description("How much to go up when critting.")
        .sliderRange(0.0001, 0.002)
        .visible(() -> mode.get().equals(Mode.Grim))
        .decimalPlaces(4)
        .build()
    );

    private final Setting<Double> attackSetting = sgGeneral.add(new DoubleSetting.Builder()
        .name("attacks")
        .description("the number of attacks to yk, do shit on")
        .defaultValue(7)
        .range(0, 20)
        .sliderMax(20)
        .visible(() -> mode.get().equals(Mode.Vulcant))
        .build()
    );

    private final Setting<Boolean> packetSetting = sgGeneral.add(new BoolSetting.Builder()
        .name("Packet")
        .description("sends cool packet")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> ka = sgGeneral.add(new BoolSetting.Builder()
        .name("only-killaura")
        .description("Only performs crits when using killaura.")
        .defaultValue(false)
        .build()
    );

    private PlayerInteractEntityC2SPacket attackPacket;
    private HandSwingC2SPacket swingPacket;
    private boolean sendPackets;
    private int sendTimer, attacks;
    public Criticals() {
        super(Categories.Combat, "criticals", "Performs critical attacks when you hit your target.");
    }

    @Override
    public void onActivate() {
        attackPacket = null;
        swingPacket = null;
        sendPackets = false;
        sendTimer = 0;
        attacks = 0;
    }

    @EventHandler
    private void onSendPacket(final PacketEvent.Send event) {
        if (event.packet instanceof IPlayerInteractEntityC2SPacket packet && packet.getType() == PlayerInteractEntityC2SPacket.InteractType.ATTACK) {
            if (skipCrit()) return;
            attacks++;

            Entity entity = packet.getEntity();

            if (!(entity instanceof LivingEntity) || (entity != Modules.get().get(Aura.class).getTarget() && ka.get())) return;

            if (packetSetting.get())  sendNoEvent(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_SPRINTING));

            switch (mode.get()) {
                case Packet -> {
                    if (mc.player.isOnGround()) {
                        sendPacket(0.0625);
                        sendPacket(0);
                    }
                }
                case Grim -> {
                    if (!mc.player.isOnGround()) {
                        sendNoEvent(new PlayerMoveC2SPacket.Full(mc.player.getX(), mc.player.getY() - grimPosition.get(), mc.player.getZ(), mc.player.getYaw(), mc.player.getPitch(), mc.player.isOnGround()));
                        sendNoEvent((Packet<?>) packet);
                        sendNoEvent(new PlayerMoveC2SPacket.Full(mc.player.getX(), mc.player.getY(), mc.player.getZ(), mc.player.getYaw(), mc.player.getPitch(), mc.player.isOnGround()));
                        event.cancel();
                    }
                }
                case Bypass -> {
                    if (mc.player.isOnGround()) {
                        sendPacket(0.11);
                        sendPacket(0.1100013579);
                        sendPacket(0.0000013579);
                    }
                }
                case Vulcant -> {
                    if (attacks > attackSetting.get() && mc.player.isOnGround()) {
                        attacks = 0;
                        sendPacket(0.16477328182606651);
                        sendPacket(0.08307781780646721);
                        sendPacket(0.0030162615090425808);
                    }
                }
                default -> {
                    if (!sendPackets && mc.player.isOnGround()) {
                        sendPackets = true;
                        sendTimer = mode.get() == Mode.Jump ? 6 : 4;
                        attackPacket = (PlayerInteractEntityC2SPacket) event.packet;

                        if (mode.get() == Mode.Jump) mc.player.jump();
                        else ((IVec3d) mc.player.getVelocity()).setY(0.25);
                        event.cancel();
                    }
                }
            }
        }
        else if (event.packet instanceof HandSwingC2SPacket && mode.get() != Mode.Packet) {
            if (skipCrit()) return;

            if (sendPackets && swingPacket == null) {
                swingPacket = (HandSwingC2SPacket) event.packet;

                event.cancel();
            }
        }
    }

    @EventHandler
    private void onTick(final TickEvent.Pre event){
        if (sendPackets) {
            if (sendTimer <= 0) {
                sendPackets = false;

                if (attackPacket == null || swingPacket == null) return;
                mc.getNetworkHandler().sendPacket(attackPacket);
                mc.getNetworkHandler().sendPacket(swingPacket);

                attackPacket = null;
                swingPacket = null;
            } else {
                sendTimer--;
            }
        }
    }

    private void sendPacket(double height) {
        double x = mc.player.getX();
        double y = mc.player.getY();
        double z = mc.player.getZ();

        PlayerMoveC2SPacket packet = new PlayerMoveC2SPacket.PositionAndOnGround(x, y + height, z, false);
        ((IPlayerMoveC2SPacket) packet).setTag(1337);

        mc.player.networkHandler.sendPacket(packet);
    }

    private boolean skipCrit() {
        return mc.player.isSubmergedInWater() || mc.player.isInLava() || mc.player.isClimbing() || mc.player.isInsideWaterOrBubbleColumn();
    }

    @Override
    public String getInfoString() {
        return mode.get().name();
    }

    public enum Mode {
        Packet,
        Bypass,
        Grim,
        // Credits to ouuuu for grim crits
        Vulcant,
        Jump,
        MiniJump
    }
}
