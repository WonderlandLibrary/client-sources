package club.bluezenith.module.modules.render.targethuds.components.provider;

import net.minecraft.entity.player.EntityPlayer;

@FunctionalInterface
public interface WidthProvider {
    float getWidth(float targetWidth, float currentWidth, EntityPlayer player);
}
