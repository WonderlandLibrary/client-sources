package club.bluezenith.module.modules.render.targethuds.components.provider;

import net.minecraft.entity.player.EntityPlayer;

@FunctionalInterface
public interface ColorProvider {
    int getColorFromTarget(EntityPlayer target);
}
