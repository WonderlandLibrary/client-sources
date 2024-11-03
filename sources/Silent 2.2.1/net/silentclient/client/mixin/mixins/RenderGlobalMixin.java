package net.silentclient.client.mixin.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.event.impl.RenderTickEvent;
import net.silentclient.client.mods.render.BlockOverlayMod;
import net.silentclient.client.mods.settings.FPSBoostMod;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderGlobal.class)
public abstract class RenderGlobalMixin {
    @Shadow @Final private Minecraft mc;

    @Shadow @Final private TextureManager renderEngine;

    @Shadow @Final private static ResourceLocation locationCloudsPng;

    @Shadow private WorldClient theWorld;

    @Shadow private int cloudTickCounter;

    @Inject(method = "renderEntities", at = @At("HEAD"))
    public void tickEvent(Entity renderViewEntity, ICamera camera, float partialTicks, CallbackInfo ci) {
        new RenderTickEvent().call();
    }

    /**
     * @author kirillsaint
     * @reason BlockOverlayMod
     */
    @Overwrite
    public void drawSelectionBox(EntityPlayer player, MovingObjectPosition movingObjectPositionIn, int p_72731_3_, float partialTicks)
    {
        BlockOverlayMod.drawSelectionBox(player, movingObjectPositionIn, p_72731_3_, partialTicks);
    }

    @Inject(method = "renderWorldBorder", at = @At("HEAD"), cancellable = true)
    public void cancelRenderWorldBorder(Entity entityIn, float partialTicks, CallbackInfo ci) {
        if(Client.getInstance().getSettingsManager().getSettingByClass(FPSBoostMod.class, "Hide World Border").getValBoolean()) {
            ci.cancel();
        }
    }
}
