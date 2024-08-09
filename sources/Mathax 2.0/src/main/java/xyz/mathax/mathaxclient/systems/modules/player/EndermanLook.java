package xyz.mathax.mathaxclient.systems.modules.player;

import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.settings.EnumSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.entity.Target;
import xyz.mathax.mathaxclient.utils.player.Rotations;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.util.math.Vec3d;

public class EndermanLook extends Module {
    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    private final Setting<Mode> lookMode = generalSettings.add(new EnumSetting.Builder<Mode>()
            .name("Mode")
            .description("How this module behaves.")
            .defaultValue(Mode.Away)
            .build()
    );

    public EndermanLook(Category category) {
        super(category, "Enderman Look", "Either looks at all Endermen or prevents you from looking at Endermen.");
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (lookMode.get() == Mode.Away) {
            if (mc.player.getAbilities().creativeMode || !shouldLook()) {
                return;
            }

            Rotations.rotate(mc.player.getYaw(), 90, -75, null);
        } else {
            for (Entity entity : mc.world.getEntities()) {
                if (!(entity instanceof EndermanEntity enderman)) {
                    continue;
                }

                if (enderman.isAngry() || !enderman.isAlive() || !mc.player.canSee(enderman)) {
                    continue;
                }

                Rotations.rotate(Rotations.getYaw(enderman), Rotations.getPitch(enderman, Target.Head), -75, null);
                break;
            }
        }
    }

    private boolean shouldLook() {
        for (Entity entity : mc.world.getEntities()) {
            if (!(entity instanceof EndermanEntity)) {
                continue;
            }

            if (entity.isAlive() && angleCheck(entity)) {
                return true;
            }
        }

        return false;
    }

    private boolean angleCheck(Entity entity) {
        Vec3d vec3d = mc.player.getRotationVec(1.0F).normalize();
        Vec3d vec3d2 = new Vec3d(entity.getX() - mc.player.getX(), entity.getEyeY() - mc.player.getEyeY(), entity.getZ() - mc.player.getZ());

        double d = vec3d2.length();
        vec3d2 = vec3d2.normalize();
        double e = vec3d.dotProduct(vec3d2);

        return e > 1.0D - 0.025D / d && mc.player.canSee(entity);
    }

    public enum Mode {
        At("At"),
        Away("Away");

        private final String name;

        Mode(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}