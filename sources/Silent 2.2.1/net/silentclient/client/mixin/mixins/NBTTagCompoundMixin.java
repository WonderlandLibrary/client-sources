package net.silentclient.client.mixin.mixins;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NBTTagCompound.class)
public class NBTTagCompoundMixin {
    @Inject(method = "setTag", at = @At("HEAD"))
    private void silent$failFast(String key, NBTBase value, CallbackInfo ci) {
        if (value == null) throw new IllegalArgumentException("Invalid null NBT value with key " + key);
    }
}
