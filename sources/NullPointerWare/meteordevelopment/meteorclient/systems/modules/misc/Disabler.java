package meteordevelopment.meteorclient.systems.modules.misc;

import meteordevelopment.meteorclient.events.entity.player.SendMovementPacketsEvent;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.mixin.PlayerMoveC2SPacketAccessor;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.common.CommonPingS2CPacket;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;

import java.util.concurrent.CopyOnWriteArrayList;

public final class Disabler extends Module {

    private final SettingGroup sgGeneral = settings.getDefaultGroup();
   // private final SettingGroup sgModes = settings.createGroup("Modes"); //Pog

    public Disabler() {
        super(Categories.Movement, "NoAntiCheat", "Removes the Anticheat...");
    }

    public final Setting<DisablerMode> mode = sgGeneral.add(new EnumSetting.Builder<DisablerMode>()
        .name("mode")
        .description("The mode for the Disabler.")
        .defaultValue(Disabler.DisablerMode.Sentinel)
        .build()
    );

    public final Setting<Integer> vulcanTickTimer = sgGeneral.add(new IntSetting.Builder()
        .name("Ticks")
        .description("How many seconds to wait between sending funny packet")
        .defaultValue(19)
        .sliderMin(0)
        .sliderMax(50)
        .visible(() -> (mode.get().equals(DisablerMode.VulcanScaffold)))
        .build()
    );

    public final Setting<Boolean> autodisable = sgGeneral.add(new BoolSetting.Builder()
        .name("autodisable")
        .description("Autodisable.")
        .defaultValue(true)
        .visible(() -> mode.get().equals(DisablerMode.VulcanFullPing) || mode.get().equals(DisablerMode.VulcanFullBoth) || mode.get().equals(DisablerMode.VulcanFullScreenHandler))
        .build()
    );

    public final Setting<Boolean> spoofOnGround = sgGeneral.add(new BoolSetting.Builder()
        .name("spoof-onground")
        .description("Spoofs onground.")
        .defaultValue(true)
        .visible(() -> mode.get().equals(DisablerMode.VulcanFullPing) || mode.get().equals(DisablerMode.VulcanFullScreenHandler) || mode.get().equals(DisablerMode.VulcanFullBoth))
        .build()
    );

    public final Setting<onGroundEnum> onGroundMode = sgGeneral.add(new EnumSetting.Builder<onGroundEnum>()
        .name("mode")
        .description("The mode for the Disabler.")
        .defaultValue(onGroundEnum.ON_GROUND)
        .visible(spoofOnGround::get)
        .build()
    );

    public final Setting<Boolean> redo = sgGeneral.add(new BoolSetting.Builder()
        .name("minemalia-redo")
        .description("Redoes the disabler on minemalia.")
        .defaultValue(true)
        .visible(() -> (mode.get().equals(DisablerMode.VulcanFullPing) || mode.get().equals(DisablerMode.VulcanFullScreenHandler) || mode.get().equals(DisablerMode.VulcanFullBoth)) && autodisable.get())
        .build()
    );

    public final Setting<Integer> seconds = sgGeneral.add(new IntSetting.Builder()
        .name("seconds")
        .description("How many seconds to wait before disable.")
        .defaultValue(10)
        .sliderMin(0)
        .sliderMax(50)
        .visible(() -> (mode.get().equals(DisablerMode.VulcanFullPing) || mode.get().equals(DisablerMode.VulcanFullScreenHandler) || mode.get().equals(DisablerMode.VulcanFullBoth)) && autodisable.get())
        .build()
    );

    CopyOnWriteArrayList<ScreenHandlerSlotUpdateS2CPacket> packetListTransac = new CopyOnWriteArrayList<>();
    CopyOnWriteArrayList<CommonPingS2CPacket> packetListPing = new CopyOnWriteArrayList<>();


    //vulcan
    public int vulcanfullticks = 0;
    public int vulcanScaffoldTicks = 0;

    @Override
    public void onDeactivate() {
        for (ScreenHandlerSlotUpdateS2CPacket packet : packetListTransac) {
            packet.apply(mc.getNetworkHandler());
        }
        for (CommonPingS2CPacket packet : packetListPing) {
            packet.apply(mc.getNetworkHandler());
        }

        packetListTransac.clear();
        packetListPing.clear();
        vulcanfullticks = 0;
    }

    @Override
    public String getInfoString() {
        return mode.get().toString();
    }

    @Override
    public void onActivate() {
    }

    @EventHandler
    private void onPacket(final PacketEvent.Receive e) {
        Packet<?> p = e.packet;
        if (mode.get().equals(DisablerMode.Sentinel) && p instanceof CommonPingS2CPacket)
            e.setCancelled(true);
        if ((mode.get().equals(DisablerMode.VulcanFullScreenHandler) || mode.get().equals(DisablerMode.VulcanFullBoth)) && e.packet instanceof ScreenHandlerSlotUpdateS2CPacket packet) {
            packetListTransac.add(packet);
            e.setCancelled(true);
        }
        if ((mode.get().equals(DisablerMode.VulcanFullPing) || mode.get().equals(DisablerMode.VulcanFullBoth)) && e.packet instanceof CommonPingS2CPacket pingPacket) {
            packetListPing.add(pingPacket);
            e.setCancelled(true);
        }

    }

    @EventHandler
    private void onPacket(final PacketEvent.Send event) {
        if (mode.get() == DisablerMode.VulcanFullBoth || mode.get() == DisablerMode.VulcanFullPing || mode.get() == DisablerMode.VulcanFullScreenHandler && spoofOnGround.get()) {
            if (event.packet instanceof PlayerMoveC2SPacket.LookAndOnGround) {
                ((PlayerMoveC2SPacketAccessor) event.packet).setOnGround(onGroundMode.get().getValue());
            }

            if (event.packet instanceof PlayerMoveC2SPacket.Full) {
                ((PlayerMoveC2SPacketAccessor) event.packet).setOnGround(onGroundMode.get().getValue());
            }
        }
    }

    @EventHandler
    private void onUpdate(final SendMovementPacketsEvent.Pre e) {
        vulcanfullticks++;
        vulcanScaffoldTicks++;

        if (spoofOnGround.get())
            mc.player.setOnGround(onGroundMode.get().getValue());

        if (autodisable.get() && vulcanfullticks >= seconds.get()*20) {
            if (!redo.get())
                this.toggle();
            else {
                mc.player.updatePosition(mc.player.getX(), mc.player.getY() - 128, mc.player.getZ());
                onDeactivate();
            }
            vulcanfullticks = 0;
        }


        if (mode.get().equals(DisablerMode.VulcanSprint)) {
            sendNoEvent(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_SPRINTING));
            sendNoEvent(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.STOP_SPRINTING));
        }

        if (mode.get().equals(DisablerMode.VulcanScaffold) && vulcanScaffoldTicks >= vulcanTickTimer.get()) {
            sendNoEvent(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY));
            sendNoEvent(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY));
            vulcanScaffoldTicks = 0;
        }
    }

    public boolean isVulcanMode() {
        return mode.get().equals(DisablerMode.VulcanFullBoth) || mode.get().equals(DisablerMode.VulcanFullPing) || mode.get().equals(DisablerMode.VulcanFullScreenHandler);
    }

    public enum DisablerMode {
        Sentinel,
        VulcanSprint,
        VulcanFullPing,
        VulcanFullScreenHandler,
        VulcanFullBoth,
        VulcanScaffold
    }

    public enum onGroundEnum {
        ON_GROUND(true),
        OFF_GROUND(false);

        private final boolean value;

        onGroundEnum(boolean value) {
            this.value = value;
        }

        public boolean getValue() {
            return value;
        }
    }
}
