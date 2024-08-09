package mathax.client.systems.modules.movement;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.entity.player.PlayerMoveEvent;
import mathax.client.events.movement.EventMove;
import mathax.client.settings.*;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.player.PlayerUtils;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.util.math.Vec3d;

import java.awt.*;

//This is broken, why I don't fucking know
//Someone Else Figure it out
public class HypnoticFly extends Module {

    public HypnoticFly() {
        super(Categories.Movement, Items.ELYTRA, "hypnotic-fly", "Allows you to fly with an elytra. || Currently Broken || Stolen from Hypnotic Client");
    }

    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final Setting<Mode> mode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("mode")
        .description("The mode to use for flying.")
        .defaultValue(Mode.Vanilla)
        .build()
    );

    private final Setting<Boolean> autoElytra = sgGeneral.add(new BoolSetting.Builder()
        .name("autoElytra")
        .description("Automatically activates the elytra when you fall.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Double> fallDistance = sgGeneral.add(new DoubleSetting.Builder()
        .name("fallDistance")
        .description("The distance you have to fall before the elytra is activated.")
        .defaultValue(3.0)
        .min(0)
        .sliderMax(10)
        .build()
    );

    private final Setting<Double> flyspeed = sgGeneral.add(new DoubleSetting.Builder()
        .name("fly-speed")
        .description("The speed to fly at.")
        .defaultValue(1)
        .min(0)
        .sliderMax(10)
        .build()
    );

    private final Setting<Double> glideSpeed = sgGeneral.add(new DoubleSetting.Builder()
        .name("glide-speed")
        .description("The speed to glide at.")
        .defaultValue(0.05)
        .min(0)
        .sliderMax(10)
        .build()
    );

    private final Setting<Double> downspeed = sgGeneral.add(new DoubleSetting.Builder()
        .name("down-speed")
        .description("The speed to go down at.")
        .defaultValue(1)
        .min(0)
        .sliderMax(10)
        .build()
    );

    private final Setting<Boolean> slowglide = sgGeneral.add(new BoolSetting.Builder()
        .name("slow-glide")
        .description("Slows down your glide speed.")
        .defaultValue(true)
        .build()
    );

    public enum Mode {
        Vanilla("Vanilla"),
        Firework("Firework"),
        NCP("NCP");

        private final String title;

        Mode(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }


    @EventHandler
    private void onTick(PlayerMoveEvent event) {
        this.setDisplayName("ElytraFly " + Color.gray.getRGB() + mode.get());
        if (wearingElytra() && (autoElytra.get().booleanValue() && mc.player.fallDistance >= fallDistance.get().doubleValue() && !mc.player.isOnGround() && !mc.player.isFallFlying())) {
            if (mc.player.age % 5 == 0)
                mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_FALL_FLYING));
        }

        if (mc.player.isFallFlying()) {
            if (mode.get() == Mode.Firework) {
                Vec3d vec3d_1 = mc.player.getRotationVector();
                Vec3d vec3d_2 = mc.player.getVelocity();
                mc.player.setVelocity(vec3d_2.add(vec3d_1.x * 0.1D + (vec3d_1.x * 1.5D - vec3d_2.x) * 0.5D, vec3d_1.y * 0.1D + (vec3d_1.y * 1.5D - vec3d_2.y) * 0.5D, vec3d_1.z * 0.1D + (vec3d_1.z * 1.5D - vec3d_2.z) * 0.5D));
            }
                if (mode.get() == Mode.NCP || mode.get() == Mode.Vanilla) {
                    PlayerUtils.setMoveSpeed2(event, flyspeed.get().doubleValue());
                    if (event.getY() <= 0)
                        event.setY(mc.player.isSneaking() ? (float)-downspeed.get() : (slowglide.get() ? -0.01 : 0));
                    if (mode.get() == Mode.NCP) {
                        if (mc.options.jumpKey.isPressed()) event.setY(flyspeed.get().doubleValue());
                        if (mc.options.sneakKey.isPressed()) event.setY(-flyspeed.get().doubleValue());
                }
            }
        }
    }

    private boolean wearingElytra() {
        ItemStack equippedStack = mc.player.getEquippedStack(EquipmentSlot.CHEST);
        return equippedStack != null && equippedStack.getItem() == Items.ELYTRA;
    }
}
