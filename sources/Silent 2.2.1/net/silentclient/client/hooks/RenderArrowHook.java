package net.silentclient.client.hooks;

import net.minecraft.entity.projectile.EntityArrow;
import net.silentclient.client.Client;
import net.silentclient.client.mixin.accessors.EntityArrowAccessor;
import net.silentclient.client.mods.settings.FPSBoostMod;

public class RenderArrowHook {
    public static boolean cancelRendering(EntityArrow entity) {
        boolean grounded = ((EntityArrowAccessor) entity).getInGround();
        return Client.getInstance().getSettingsManager().getSettingByClass(FPSBoostMod.class, "Hide Ground Arrows").getValBoolean() && grounded;
    }
}
