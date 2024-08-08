package lol.point.returnclient.module.impl.movement;

import lol.point.returnclient.events.impl.player.EventMotion;
import lol.point.returnclient.events.impl.update.EventUpdate;
import lol.point.returnclient.module.Category;
import lol.point.returnclient.module.Module;
import lol.point.returnclient.module.ModuleInfo;
import lol.point.returnclient.module.impl.combat.Velocity;
import lol.point.returnclient.settings.impl.StringSetting;
import lol.point.returnclient.util.minecraft.MoveUtil;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.entity.item.EntityBoat;

@ModuleInfo(
        name = "LongJump",
        description = "allows you to perform longer jumps",
        category = Category.MOVEMENT
)
public class LongJump extends Module {

    private final StringSetting mode = new StringSetting("Mode", "Vanilla", new String[]{"Vanilla", "Grim boat", "Hypixel keep-y", "Hypixel glide"});

    private boolean grimBoatLaunch;
    private boolean vanillaLaunch;

    private int ticks = 0;
    private boolean started = false;

    public LongJump() {
        addSettings(mode);
    }

    public String getSuffix() {
        return mode.value;
    }

    public void onDisable() {
        grimBoatLaunch = false;
        vanillaLaunch = false;
        mc.gameSettings.keyBindSneak.pressed = false;
        ticks = 0;
        enabled = false;
    }

    @Subscribe
    private final Listener<EventMotion> onMotion = new Listener<>(eventMotion -> {
        switch (mode.value) {
            case "Vanilla" -> {
                if (mc.thePlayer.onGround) {
                    mc.thePlayer.jump();
                    vanillaLaunch = true;
                }

                if (vanillaLaunch && !mc.thePlayer.onGround) {
                    mc.thePlayer.motionY = 0.4;
                    mc.thePlayer.motionX *= 0.3;
                    mc.thePlayer.motionZ *= 0.3;
                    MoveUtil.setSpeed(1.5);
                    vanillaLaunch = false;
                    setEnabled(false);
                }
            }
            case "Grim boat" -> {
                if (mc.thePlayer.isRiding() && mc.thePlayer.ridingEntity instanceof EntityBoat boat) {
                    float yaw = boat.rotationYaw;
                    float pitch = 90;
                    mc.thePlayer.rotationYaw = yaw;
                    mc.thePlayer.rotationPitch = pitch;

                    if (mc.thePlayer.isRiding() && mc.thePlayer.ridingEntity instanceof EntityBoat) {
                        grimBoatLaunch = true;
                    }
                }

                if (grimBoatLaunch && mc.thePlayer.isRiding()) {
                    mc.gameSettings.keyBindSneak.pressed = true;
                }

                if (grimBoatLaunch && !mc.thePlayer.isRiding()) {
                    mc.thePlayer.motionY = 1.8;
                    MoveUtil.strafe(1.5);
                    grimBoatLaunch = false;
                    setEnabled(false);
                }
            }
            case "Hypixel keep-y" -> {
                if (mc.thePlayer.hurtTime >= 3) {
                    started = true;
                }
                if (started) {
                    ticks++;

                    if (mc.thePlayer.hurtTime == 9) {
                        MoveUtil.strafe(1.43);
                    }
                    if (ticks > 0 && ticks < 30) {
                        eventMotion.y = 0.01;
                    }
                }
            }
            case "Hypixel glide" -> {
                if (mc.thePlayer.hurtTime >= 3) {
                    started = true;
                }
                if (started) {
                    ticks++;

                    if (mc.thePlayer.hurtTime == 9) {
                        MoveUtil.strafe(1.43);
                    }
                }

                if (ticks > 0 && ticks < 30) {
                    if (ticks == 1) {
                        eventMotion.y = eventMotion.y + 0.061;
                    } else if (ticks >= 30) {
                        eventMotion.y = eventMotion.y + 0.0283;
                    }
                }
            }
        }
    });
}
