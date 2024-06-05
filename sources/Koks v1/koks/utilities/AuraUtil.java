package koks.utilities;

import com.google.common.base.Predicates;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.*;
import optifine.Reflector;

import java.util.List;

/**
 * @author avox | lmao | kroko
 * @created on 04.09.2020 : 11:59
 */
public class AuraUtil {

    private final RotationUtil rotationUtil = new RotationUtil();
    private final RandomUtil randomUtil = new RandomUtil();
    private final Minecraft mc = Minecraft.getMinecraft();

    public Entity getNearest(List<Entity> entityList) {
        Entity nearestEntity = null;

        if (!entityList.isEmpty()) {
            for (Entity entity : entityList) {
                if (nearestEntity == null) {
                    nearestEntity = entity;
                } else if (mc.thePlayer.getDistanceToEntity(nearestEntity) > mc.thePlayer.getDistanceToEntity(entity)) {
                    nearestEntity = entity;
                }
            }
        }

        return nearestEntity;
    }

    public Entity getLowest(List<Entity> entityList) {
        Entity lowestEntity = null;

        if (!entityList.isEmpty()) {
            for (Entity entity : entityList) {
                if (Float.isNaN(((EntityLivingBase) entity).getHealth()) || ((EntityLivingBase) entity).getHealth() <= 0 || ((EntityLivingBase) entity).getHealth() > 24) {
                    lowestEntity = getNearest(entityList);
                } else {
                    if (lowestEntity == null) {
                        lowestEntity = entity;
                    } else if (((EntityLivingBase) lowestEntity).getHealth() > ((EntityLivingBase) entity).getHealth()) {
                        lowestEntity = entity;
                    }
                }
            }
        }

        return lowestEntity;
    }
}