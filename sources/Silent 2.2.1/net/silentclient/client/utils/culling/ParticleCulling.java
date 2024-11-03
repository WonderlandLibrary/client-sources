package net.silentclient.client.utils.culling;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.culling.ICamera;
import net.silentclient.client.mixin.ducks.EntityFXExt;

public class ParticleCulling {
	public static ICamera camera;

    public static boolean shouldRender(EntityFX entityFX) {
        return entityFX != null && (camera == null || ((EntityFXExt) entityFX).getCullState() > -1);
    }
}
