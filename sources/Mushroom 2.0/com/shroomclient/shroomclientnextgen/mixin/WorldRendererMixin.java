package com.shroomclient.shroomclientnextgen.mixin;

import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.impl.render.ESP;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.ThemeUtil;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    // swig best
    // change esp color yayay
    @Redirect(
        method = "render",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/Entity;getTeamColorValue()I"
        )
    )
    public int espColor(Entity instance) {
        if (
            instance instanceof ItemEntity
        ) return ThemeUtil.themeColors()[0].getRGB();

        if (ModuleManager.isEnabled(ESP.class)) {
            if (ESP.teamColor) return instance.getTeamColorValue();

            if (ESP.visibilityCheck) {
                if (C.p() == null) return ThemeUtil.themeColors()[0].getRGB();
                if (
                    C.p().canSee(instance)
                ) return ThemeUtil.getGradient()[0].getRGB();
                return ThemeUtil.getGradient()[1].getRGB();
            }
            return ThemeUtil.themeColors(
                (int) instance.getX(),
                (int) instance.getZ(),
                50,
                0.002f
            )[0].getRGB();
        }

        return instance.getTeamColorValue();
    }
}
