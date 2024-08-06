package com.shroomclient.shroomclientnextgen.util;

import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.impl.combat.AntiBot;
import com.shroomclient.shroomclientnextgen.modules.impl.combat.Target;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class TargetUtil {

    public static boolean isBot(Entity entity) {
        if (ModuleManager.isEnabled(AntiBot.class)) {
            if (entity instanceof PlayerEntity && entity != C.p()) {
                AntiBot.EntityData data = AntiBot.entityData.get(
                    entity.getId()
                );
                if (data != null) {
                    if (AntiBot.invisible) {
                        if (
                            data.getTicksInvisible() >=
                            (data.getTicksExisted() + 1) / 2
                        ) return true;
                    }
                    if (AntiBot.tablist) {
                        if (
                            data.getTabTicks() <= 250 ||
                            data.getTabTicks() <=
                                (data.getTicksExisted() + 1) / 2
                        ) return true;
                    }
                    if (AntiBot.npc) {
                        return AntiBot.isNPC(entity);
                    }
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    // idfk we might need this later
    public static boolean isTeam(PlayerEntity p) {
        return isTeam(p, false);
    }

    public static boolean isTeam(PlayerEntity p, boolean bester) {
        if (bester) return (
            (p.getTeamColorValue() == C.p().getTeamColorValue() &&
                ModuleManager.getModule(Target.class).nameColorCheck) ||
            (C.p().isTeammate(p) &&
                ModuleManager.getModule(Target.class).onTeamCheck)
        );
        return (
            p.getTeamColorValue() == C.p().getTeamColorValue() ||
            C.p().isTeammate(p)
        );
    }

    public static boolean shouldTarget(Entity entity) {
        if (!entity.isAlive()) return false;
        // Don't attack self :skull:
        if (entity.getId() == C.p().getId()) return false;
        boolean flag =
            entity instanceof PlayerEntity && !isBot((PlayerEntity) entity);
        if (!flag) return false;
        PlayerEntity p = (PlayerEntity) entity;
        if (isTeam(p, true)) return false;
        return true;
    }

    public static Vec3d getTargetVec(Entity ent, TargetBox box) {
        Vec3d v = ent.getPos();
        Box aabb = ent.getBoundingBox();
        Double offset = null;
        if (box == TargetBox.BOTTOM) {
            offset = 0d;
        } else if (box == TargetBox.MIDDLE) {
            offset = (aabb.maxY - aabb.minY) / 2;
        } else if (box == TargetBox.TOP) {
            offset = aabb.maxY - aabb.minY;
        }

        return v.add(new Vec3d(0, offset, 0));
    }

    public enum TargetBox {
        BOTTOM,
        MIDDLE,
        TOP,
    }
}
