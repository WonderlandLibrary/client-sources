package me.travis.wurstplus.mixin.client;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import javax.annotation.Nullable;
import me.travis.wurstplus.module.ModuleManager;
import me.travis.wurstplus.util.CapeManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={AbstractClientPlayer.class})
public abstract class MixinAbstractClientPlayer {
    @Shadow
    @Nullable
    protected abstract NetworkPlayerInfo getPlayerInfo();

    @Inject(method={"getLocationCape"}, at={@At(value="HEAD")}, cancellable=true)
    public void getLocationCape(CallbackInfoReturnable<ResourceLocation> callbackInfoReturnable) {
        if (ModuleManager.isModuleEnabled("Capes")) {
            NetworkPlayerInfo info = this.getPlayerInfo();
            UUID uuid = null;
            if (info != null) {
                uuid = this.getPlayerInfo().getGameProfile().getId();
            }
            if (uuid != null && CapeManager.hasCape(uuid)) {
                if (CapeManager.isOg(uuid)) {
                    callbackInfoReturnable.setReturnValue(new ResourceLocation("textures/cape.png"));
                } else {
                    callbackInfoReturnable.setReturnValue(new ResourceLocation("textures/cape.png"));
                }
            }
        }
    }
}