package dev.stephen.nexus.module.modules.ghost;

import dev.stephen.nexus.Client;
import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.render.EventRender3D;
import dev.stephen.nexus.module.Module;
import dev.stephen.nexus.module.ModuleCategory;
import dev.stephen.nexus.module.modules.combat.AntiBot;
import dev.stephen.nexus.module.setting.impl.BooleanSetting;
import dev.stephen.nexus.module.setting.impl.RangeSetting;
import dev.stephen.nexus.utils.math.MathUtils;
import dev.stephen.nexus.utils.math.RandomUtil;
import dev.stephen.nexus.utils.mc.CombatUtils;
import dev.stephen.nexus.utils.rotation.RotationUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.HitResult;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AimAssist extends Module {
    public final BooleanSetting clickOnly = new BooleanSetting("ClickOnly", true);
    public final BooleanSetting fovBased = new BooleanSetting("FovBased", true);
    public final BooleanSetting pitchAssist = new BooleanSetting("PitchAssist", true);
    public final BooleanSetting breakBlocks = new BooleanSetting("Break Blocks", false);
    public final BooleanSetting attackTeamMates = new BooleanSetting("Attack TeamMates", false);
    public final RangeSetting rotSpeedV = new RangeSetting("Speed", 1, 100, 20, 50, 1);

    public AimAssist() {
        super("AimAssist", "Aims for you", 0, ModuleCategory.GHOST);
        this.addSettings(clickOnly, fovBased, pitchAssist, attackTeamMates, breakBlocks, rotSpeedV);
    }

    private final ArrayList<PlayerEntity> targetArray = new ArrayList<>();

    @Override
    public void onEnable() {
        targetArray.clear();
        super.onEnable();
    }

    @EventLink
    public Listener<EventRender3D> eventTickPreListener = event -> {
        if (isNull()) {
            return;
        }

        if (mc.currentScreen != null) return;
        if (clickOnly.getValue() && !mc.options.attackKey.isPressed()) return;
        if (breakBlocks.getValue() && mc.crosshairTarget.getType() == HitResult.Type.BLOCK) return;
        EntityAndRots target = getTargetAndRotations();
        if (target == null) return;

        double fov = CombatUtils.fovFromEntity(target.entity);
        double rotSpeed = RandomUtil.smartRandom(rotSpeedV.getValueMin(), rotSpeedV.getValueMax()) * (fovBased.getValue() ? (Math.abs(fov) * 2 / 180) : 1);

        float[] rots = RotationUtils.getPatchedAndCappedRots(
                new float[]{mc.player.prevYaw, mc.player.prevPitch},
                new float[]{target.yaw, target.pitch},
                (float) rotSpeed
        );

        mc.player.setYaw(rots[0]);
        mc.player.setPitch(rots[1]);
    };

    private EntityAndRots getTargetAndRotations() {
        EntityAndRots target = new EntityAndRots();
        target.entity = getTarget();
        if (target.entity == null) return null;
        float[] rotationsToTarget = getRotationsToEntity(target.entity);
        target.yaw = rotationsToTarget[0];
        target.pitch = (pitchAssist.getValue() ? rotationsToTarget[1] : mc.player.getPitch());
        return target;
    }

    private PlayerEntity getTarget() {
        if (mc.player == null || mc.world == null) {
            return null;
        }
        PlayerEntity temp_target = null;
        float health = Float.MAX_VALUE;
        targetArray.clear();
        List<PlayerEntity> inAttackRangeTargets = new ArrayList<>();

        for (PlayerEntity e : mc.world.getPlayers()) {
            if (Client.INSTANCE.getModuleManager().getModule(AntiBot.class).isBot(e)) {
                continue;
            }

            double calculatedDistance = CombatUtils.getDistanceToEntity(e);

            if (calculatedDistance <= 5) {
                if (e != mc.player && e.getHealth() > 0) {
                    if (CombatUtils.isSameTeam(e)) {
                        if (attackTeamMates.getValue()) {
                            targetArray.add(e);
                            if (calculatedDistance <= 3) {
                                inAttackRangeTargets.add(e);
                            }
                            if (e.getHealth() < health) {
                                health = e.getHealth();
                                temp_target = e;
                            }
                        }
                    } else {
                        targetArray.add(e);
                        if (calculatedDistance <= 3) {
                            inAttackRangeTargets.add(e);
                        }
                        if (e.getHealth() < health) {
                            health = e.getHealth();
                            temp_target = e;
                        }
                    }
                }
            }
        }

        targetArray.sort(Comparator.comparingDouble(CombatUtils::getDistanceToEntity));

        targetArray.sort(Comparator.comparingDouble(CombatUtils::getDistanceToEntity));
        if (!targetArray.isEmpty()) {
            temp_target = targetArray.getFirst();
        }

        return temp_target;
    }

    public static float[] getRotationsToEntity(PlayerEntity e) {
        if (e == null) return null;
        double x = e.getX() - mc.player.getX();
        double y = e.getPos().y - mc.player.getPos().y;
        double z = e.getZ() - mc.player.getZ();
        double distance = MathUtils.sqrt_double((x * x) + (z * z));
        float targetYaw = (float) ((Math.toDegrees(Math.atan2(z, x))) - 90);
        float targetPitch = (float) (-Math.toDegrees(Math.atan2(y, distance)));
        return new float[]{targetYaw, targetPitch};
    }

    private static class EntityAndRots {
        public PlayerEntity entity;
        public float yaw, pitch;
    }
}
