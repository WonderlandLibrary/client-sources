package in.momin5.cookieclient.api.mixin.mixins;

import in.momin5.cookieclient.CookieClient;
import in.momin5.cookieclient.api.mixin.mixins.accessor.AccessorEntityPlayerSP;
import in.momin5.cookieclient.api.module.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.crash.CrashReport;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Minecraft.class)
public class MixinMinecraft {
    @Shadow
    public EntityPlayerSP player;
    @Shadow public PlayerControllerMP playerController;

    private boolean handActive = false;
    private boolean isHittingBlock = false;

    @Inject(method = "rightClickMouse", at = @At("HEAD"))
    public void rightClickMousePre(CallbackInfo ci) {
        if (ModuleManager.isModuleEnabled("MultiTask")) {
            isHittingBlock = playerController.getIsHittingBlock();
            playerController.isHittingBlock = false;
        }
    }

    @Inject(method = "rightClickMouse", at = @At("RETURN"))
    public void rightClickMousePost(CallbackInfo ci) {
        if (ModuleManager.isModuleEnabled("MultiTask") && !playerController.getIsHittingBlock()) {
            playerController.isHittingBlock = isHittingBlock;
        }
    }

    @Inject(method = "sendClickBlockToController", at = @At("HEAD"))
    public void sendClickBlockToControllerPre(boolean leftClick, CallbackInfo ci) {
        if (ModuleManager.isModuleEnabled("MultiTask")) {
            handActive = player.isHandActive();
            ((AccessorEntityPlayerSP) player).gsSetHandActive(false);
        }
    }

    @Inject(method = "sendClickBlockToController", at = @At("RETURN"))
    public void sendClickBlockToControllerPost(boolean leftClick, CallbackInfo ci) {
        if (ModuleManager.isModuleEnabled("MultiTask") && !player.isHandActive()) {
            ((AccessorEntityPlayerSP) player).gsSetHandActive(handActive);
        }
    }

    // powered by gamesense, swag code
    @Inject(method = "crashed", at = @At("HEAD"))
    public void crashed(CrashReport crash, CallbackInfo callbackInfo) {
        if(CookieClient.configSave != null) {
            CookieClient.configSave.save();
        }
    }

    @Inject(method = "shutdown", at = @At("HEAD"))
    public void shutdown(CallbackInfo callbackInfo) {
        if(CookieClient.configSave != null) {
            CookieClient.configSave.save();
        }
    }
}
