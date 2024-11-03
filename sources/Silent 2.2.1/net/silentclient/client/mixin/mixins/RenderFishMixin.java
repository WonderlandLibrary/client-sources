package net.silentclient.client.mixin.mixins;

import net.minecraft.client.renderer.entity.RenderFish;
import net.minecraft.util.Vec3;
import net.silentclient.client.mods.render.AnimationsMod;
import net.silentclient.client.utils.animations.FishingLineHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = RenderFish.class, priority = 1001)
public class RenderFishMixin {
    @Redirect(method = "doRender", at = @At(value = "NEW", target = "net/minecraft/util/Vec3", ordinal = 0))
    private Vec3 oldFishingLine(double x, double y, double z) {
        return !AnimationsMod.getSettingBoolean("1.7 Rod Position") ? new Vec3(x, y, z) : FishingLineHandler.getInstance().getOffset();
    }
}