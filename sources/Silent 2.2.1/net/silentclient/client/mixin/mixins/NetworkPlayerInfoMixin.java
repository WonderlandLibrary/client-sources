package net.silentclient.client.mixin.mixins;

import com.google.common.base.Objects;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.admin.AdminRender;
import net.silentclient.client.mixin.accessors.NetworkPlayerInfoAccessor;
import net.silentclient.client.mixin.ducks.AbstractClientPlayerExt;
import net.silentclient.client.mixin.ducks.NetworkPlayerInfoExt;
import net.silentclient.client.mods.player.NickHiderMod;
import net.silentclient.client.mods.render.AnimationsMod;
import net.silentclient.client.mods.settings.FPSBoostMod;
import net.silentclient.client.utils.NetworkPlayerInfoOptimization;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NetworkPlayerInfo.class)
public abstract class NetworkPlayerInfoMixin implements NetworkPlayerInfoExt {
    @Shadow public abstract GameProfile getGameProfile();

    @Shadow @Final private GameProfile gameProfile;

    @Shadow private String skinType;

    @Shadow private ResourceLocation locationSkin;

    @Shadow protected abstract void loadPlayerTextures();

    @Inject(method = "loadPlayerTextures", at = @At("HEAD"), cancellable = true)
    public void optimizationOfLoadingTexturesStart(CallbackInfo ci) {
        if(NetworkPlayerInfoOptimization.isLoadingSkin) {
            ci.cancel();
            return;
        }
        NetworkPlayerInfoOptimization.isLoadingSkin = true;
    }

    @Inject(method = "loadPlayerTextures", at = @At("HEAD"))
    public void optimizationOfLoadingTexturesEnd(CallbackInfo ci) {
        (new Thread(() -> {
            try {
                Thread.sleep(FPSBoostMod.advancedEnabled() ? 1000 : 100);
            } catch (InterruptedException e) {

            }
            NetworkPlayerInfoOptimization.isLoadingSkin = false;
        })).start();
    }

    @Inject(method = "getLocationSkin", at = @At("HEAD"), cancellable = true)
    public void customSkinLocation(CallbackInfoReturnable<ResourceLocation> cir) {
        if(Minecraft.getMinecraft().currentScreen instanceof AdminRender) {
            cir.setReturnValue(new ResourceLocation("textures/entity/steve.png"));
            cir.cancel();
            return;
        }
        if(Client.getInstance().getModInstances().getNickHiderMod().isEnabled() && Client.getInstance().getSettingsManager().getSettingByClass(NickHiderMod.class, "Hide Skins").getValBoolean()) {
            if(Client.getInstance().getSettingsManager().getSettingByClass(NickHiderMod.class, "Use Own Skin For All").getValBoolean()) {
                if(((AbstractClientPlayerExt) Minecraft.getMinecraft().thePlayer).silent$getPlayerInfo() != null) {
                    if (((NetworkPlayerInfoExt) ((AbstractClientPlayerExt) Minecraft.getMinecraft().thePlayer).silent$getPlayerInfo()).silent$getLocationSkin() == null)
                    {
                        ((NetworkPlayerInfoAccessor) ((AbstractClientPlayerExt) Minecraft.getMinecraft().thePlayer).silent$getPlayerInfo()).silent$loadPlayerTextures();
                    }
                    cir.setReturnValue((ResourceLocation) Objects.firstNonNull(((NetworkPlayerInfoExt) ((AbstractClientPlayerExt) Minecraft.getMinecraft().thePlayer).silent$getPlayerInfo()).silent$getLocationSkin(), DefaultPlayerSkin.getDefaultSkin(this.gameProfile.getId())));
                    cir.cancel();
                    return;
                } else {
                    cir.setReturnValue(DefaultPlayerSkin.getDefaultSkin(Minecraft.getMinecraft().thePlayer.getGameProfile().getId()));
                    cir.cancel();
                    return;
                }
            }

            if(this.gameProfile.getId().equals(Minecraft.getMinecraft().thePlayer.getGameProfile().getId()) && Client.getInstance().getSettingsManager().getSettingByClass(NickHiderMod.class, "Use Real Skin For Self").getValBoolean()) {
                if (this.locationSkin == null)
                {
                    this.loadPlayerTextures();
                }
                cir.setReturnValue((ResourceLocation)Objects.firstNonNull(this.locationSkin, DefaultPlayerSkin.getDefaultSkin(this.gameProfile.getId())));
                cir.cancel();
                return;
            }

            cir.setReturnValue(DefaultPlayerSkin.getDefaultSkin(this.gameProfile.getId()));
            cir.cancel();
        }
    }

    @Inject(method = "getSkinType", at = @At("HEAD"), cancellable = true)
    public void customSkinType(CallbackInfoReturnable<String> cir) {
        if(Client.getInstance().getModInstances().getModByClass(AnimationsMod.class).isEnabled() && Client.getInstance().getSettingsManager().getSettingByClass(AnimationsMod.class, "1.7 Skins").getValBoolean()) {
            cir.setReturnValue("default");
            cir.cancel();
            return;
        }
        if(Client.getInstance().getModInstances().getNickHiderMod().isEnabled() && Client.getInstance().getSettingsManager().getSettingByClass(NickHiderMod.class, "Hide Skins").getValBoolean() && !Client.getInstance().getSettingsManager().getSettingByClass(NickHiderMod.class, "Use Real Skin For Self").getValBoolean()) {
            if(Client.getInstance().getSettingsManager().getSettingByClass(NickHiderMod.class, "Use Own Skin For All").getValBoolean()) {
                if(((AbstractClientPlayerExt) Minecraft.getMinecraft().thePlayer).silent$getPlayerInfo() != null) {
                    cir.setReturnValue(((NetworkPlayerInfoExt) ((AbstractClientPlayerExt) Minecraft.getMinecraft().thePlayer).silent$getPlayerInfo()).silent$getSkinType() == null ? DefaultPlayerSkin.getSkinType(this.gameProfile.getId()) : ((NetworkPlayerInfoExt) ((AbstractClientPlayerExt) Minecraft.getMinecraft().thePlayer).silent$getPlayerInfo()).silent$getSkinType());
                    cir.cancel();
                    return;
                } else {
                    cir.setReturnValue(DefaultPlayerSkin.getSkinType(Minecraft.getMinecraft().thePlayer.getGameProfile().getId()));
                    cir.cancel();
                    return;
                }
            }

            if(this.gameProfile.getId().equals(Minecraft.getMinecraft().thePlayer.getGameProfile().getId()) && Client.getInstance().getSettingsManager().getSettingByClass(NickHiderMod.class, "Use Real Skin For Self").getValBoolean()) {
                cir.setReturnValue(this.skinType == null ? DefaultPlayerSkin.getSkinType(this.gameProfile.getId()) : this.skinType);
                cir.cancel();
                return;
            }

            cir.setReturnValue(DefaultPlayerSkin.getSkinType(this.gameProfile.getId()));
            cir.cancel();
            return;
        }
    }

    @Override
    public String silent$getSkinType() {
        return skinType;
    }

    @Override
    public Object silent$getLocationSkin() {
        return locationSkin;
    }
}
