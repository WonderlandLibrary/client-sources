package zamorozka.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;

public class CheckUtils {
    Minecraft mc = Minecraft.getMinecraft();

    public static boolean isLowHealth(EntityLivingBase entity, EntityLivingBase entityPriority) {
        return entityPriority == null || entity.getHealth() < entityPriority.getHealth();
    }

    public static boolean isClosest(EntityLivingBase entity, EntityLivingBase entityPriority) {
        return entityPriority == null || Minecraft.player.getDistanceToEntity(entity) < Minecraft.player.getDistanceToEntity(entityPriority);
    }
}