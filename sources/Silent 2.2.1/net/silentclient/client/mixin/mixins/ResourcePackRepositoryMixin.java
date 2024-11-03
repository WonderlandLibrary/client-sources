package net.silentclient.client.mixin.mixins;

import net.minecraft.client.resources.ResourcePackRepository;
import net.silentclient.client.hooks.ResourcePackRepositoryHook;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(ResourcePackRepository.class)
public class ResourcePackRepositoryMixin {
    @Shadow @Final private File dirServerResourcepacks;

    @Inject(method = "updateRepositoryEntriesAll", at = @At("HEAD"), cancellable = true)
    private void silent$searchUsingSet(CallbackInfo ci) {
        ResourcePackRepositoryHook.updateRepositoryEntriesAll((ResourcePackRepository) (Object) this);
        ci.cancel();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Inject(method = "deleteOldServerResourcesPacks", at = @At("HEAD"))
    private void silent$createDirectory(CallbackInfo ci) {
        if (!this.dirServerResourcepacks.exists()) {
            this.dirServerResourcepacks.mkdirs();
        }
    }
}
