package net.silentclient.client.mixin.mixins;

import com.google.common.util.concurrent.ListenableFuture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.silentclient.client.Client;
import net.silentclient.client.keybinds.KeyBindManager;
import net.silentclient.client.mixin.ducks.GameSettingsExt;
import net.silentclient.client.mods.player.ZoomMod;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GameSettings.class)
public class GameSettingsMixin implements GameSettingsExt {
    @Shadow protected Minecraft mc;
    private boolean silent$needsResourceRefresh;

    @Override
    public void silent$onSettingsGuiClosed() {
        if (silent$needsResourceRefresh) {
            mc.scheduleResourcesRefresh();
            silent$needsResourceRefresh = false;
        }
    }

    //#if MC==10809
    @Redirect(
            method = "setOptionFloatValue",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;scheduleResourcesRefresh()Lcom/google/common/util/concurrent/ListenableFuture;")
    )
    private ListenableFuture<Object> silent$scheduleResourceRefresh(Minecraft instance) {
        silent$needsResourceRefresh = true;
        return null;
    }
    //#endif

    @Redirect(method = "<init>(Lnet/minecraft/client/Minecraft;Ljava/io/File;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/settings/GameSettings;loadOptions()V"))
    private void loadKeyBindManager(GameSettings instance) {
        Client.getInstance().setKeyBindManager(new KeyBindManager(instance));
        instance.loadOptions();
    }

    //#if MC==10809
    /**
     * @author asbyth
     * @reason Resolve Chat Key bound to a unicode char causing crashes while creative inventory is opened (MC-102867)
     */
    @Overwrite
    public static boolean isKeyDown(KeyBinding key) {
        if(Client.getInstance().getModInstances().getZoomMod().isEnabled() && key.getKeyDescription().equalsIgnoreCase("of.key.zoom") && key.getKeyCode() == Client.getInstance().getSettingsManager().getSettingByClass(ZoomMod.class, "Keybind").getKeybind()) {
            return false;
        }
        int keyCode = key.getKeyCode();
        if (keyCode != 0 && keyCode < 256) {
            return keyCode < 0 ? Mouse.isButtonDown(keyCode + 100) : Keyboard.isKeyDown(keyCode);
        } else {
            return false;
        }
    }
    //#endif
}
