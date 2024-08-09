package xyz.mathax.mathaxclient.systems.modules.movement;

import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.packets.PacketEvent;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.mixin.EntityVelocityUpdateS2CPacketAccessor;
import xyz.mathax.mathaxclient.mixininterface.IVec3d;
import xyz.mathax.mathaxclient.settings.BoolSetting;
import xyz.mathax.mathaxclient.settings.DoubleSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;

public class Velocity extends Module {
    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    public final Setting<Boolean> knockbackSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Knockback")
            .description("Modify the amount of knockback you take from attacks.")
            .defaultValue(true)
            .build()
    );

    public final Setting<Double> knockbackHorizontalSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Knockback horizontal")
            .description("How much horizontal knockback you will take.")
            .defaultValue(0)
            .min(0)
            .sliderRange(0, 1)
            .visible(knockbackSetting::get)
            .build()
    );

    public final Setting<Double> knockbackVerticalSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Knockback vertical")
            .description("How much vertical knockback you will take.")
            .defaultValue(0)
            .min(0)
            .sliderRange(0, 1)
            .visible(knockbackSetting::get)
            .build()
    );

    public final Setting<Boolean> explosionsSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Explosions")
            .description("Modify your knockback from explosions.")
            .defaultValue(true)
            .build()
    );

    public final Setting<Double> explosionsHorizontalSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Explosions horizontal")
            .description("How much velocity you will take from explosions horizontally.")
            .defaultValue(0)
            .min(0)
            .sliderRange(0, 1)
            .visible(explosionsSetting::get)
            .build()
    );

    public final Setting<Double> explosionsVerticalSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Explosions vertical")
            .description("How much velocity you will take from explosions vertically.")
            .defaultValue(0)
            .min(0)
            .sliderRange(0, 1)
            .visible(explosionsSetting::get)
            .build()
    );

    public final Setting<Boolean> liquidsSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Liquids")
            .description("Modifies the amount you are pushed by flowing liquids.")
            .defaultValue(true)
            .build()
    );

    public final Setting<Double> liquidsHorizontalSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Liquids horizontal")
            .description("How much velocity you will take from liquids horizontally.")
            .defaultValue(0)
            .min(0)
            .sliderRange(0, 1)
            .visible(liquidsSetting::get)
            .build()
    );

    public final Setting<Double> liquidsVerticalSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Liquids vertical")
            .description("How much velocity you will take from liquids vertically.")
            .defaultValue(0)
            .min(0)
            .sliderRange(0, 1)
            .visible(liquidsSetting::get)
            .build()
    );

    public final Setting<Boolean> entityPushSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Entity push")
            .description("Modify the amount you are pushed by entities.")
            .defaultValue(true)
            .build()
    );

    public final Setting<Double> entityPushAmountSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Entity push amount")
            .description("How much you will be pushed.")
            .defaultValue(0)
            .min(0)
            .sliderRange(0, 1)
            .visible(entityPushSetting::get)
            .build()
    );

    public final Setting<Boolean> blocksSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Blocks")
            .description("Prevent you from being pushed out of blocks.")
            .defaultValue(true)
            .build()
    );

    public final Setting<Boolean> sinkingSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Sinking")
            .description("Prevent you from sinking in liquids.")
            .defaultValue(false)
            .build()
    );

    public Velocity(Category category) {
        super(category, "Velocity", "Prevents you from being moved by external forces.");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (!sinkingSetting.get()) {
            return;
        }

        if (mc.options.jumpKey.isPressed() || mc.options.sneakKey.isPressed()) {
            return;
        }

        if ((mc.player.isTouchingWater() || mc.player.isInLava()) && mc.player.getVelocity().y < 0) {
            ((IVec3d) mc.player.getVelocity()).setY(0);
        }
    }

    @EventHandler
    private void onPacketReceive(PacketEvent.Receive event) {
        if (knockbackSetting.get() && event.packet instanceof EntityVelocityUpdateS2CPacket packet && ((EntityVelocityUpdateS2CPacket) event.packet).getId() == mc.player.getId()) {
            double velX = (packet.getVelocityX() / 8000d - mc.player.getVelocity().x) * knockbackHorizontalSetting.get();
            double velY = (packet.getVelocityY() / 8000d - mc.player.getVelocity().y) * knockbackVerticalSetting.get();
            double velZ = (packet.getVelocityZ() / 8000d - mc.player.getVelocity().z) * knockbackHorizontalSetting.get();
            ((EntityVelocityUpdateS2CPacketAccessor) packet).setX((int) (velX * 8000 + mc.player.getVelocity().x * 8000));
            ((EntityVelocityUpdateS2CPacketAccessor) packet).setY((int) (velY * 8000 + mc.player.getVelocity().y * 8000));
            ((EntityVelocityUpdateS2CPacketAccessor) packet).setZ((int) (velZ * 8000 + mc.player.getVelocity().z * 8000));
        }
    }

    public double getHorizontal(Setting<Double> setting) {
        return isEnabled() ? setting.get() : 1;
    }

    public double getVertical(Setting<Double> setting) {
        return isEnabled() ? setting.get() : 1;
    }
}