package net.silentclient.client.mixin.mixins;

import net.minecraft.nbt.NBTTagString;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NBTTagString.class)
public class NBTTagStringMixin {
    @Shadow private String data;
    @Unique private String silent$dataCache;

    @Inject(method = "read", at = @At("HEAD"))
    private void silent$emptyDataCache(CallbackInfo ci) {
        this.silent$dataCache = null;
    }

    /**
     * @author asbyth
     * @reason Utilize data cache
     */
    @Overwrite
    public String toString() {
        if (this.silent$dataCache == null) {
            this.silent$dataCache = "\"" + this.data.replace("\"", "\\\"") + "\"";
        }

        return this.silent$dataCache;
    }
}
