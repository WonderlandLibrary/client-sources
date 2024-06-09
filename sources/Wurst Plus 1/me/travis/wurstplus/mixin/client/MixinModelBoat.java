package me.travis.wurstplus.mixin.client;

import me.travis.wurstplus.module.ModuleManager;
import me.travis.wurstplus.util.Wrapper;
import net.minecraft.client.model.ModelBoat;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created by 086 on 15/12/2017.
 */
@Mixin(ModelBoat.class)
public class MixinModelBoat {

    @Inject(method = "render", at = @At("HEAD"))
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, CallbackInfo info) {
        if (Wrapper.getPlayer().getRidingEntity() == entityIn && ModuleManager.isModuleEnabled("EntitySpeed")) {
            GlStateManager.color(1, 1, 1, 0.5f);
            GlStateManager.enableBlend();
        }
    }

}
