package net.silentclient.client.mixin.accessors;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.data.IMetadataSerializer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.io.File;

@Mixin(Minecraft.class)
public interface MinecraftAccessor {
    @Accessor
    File getFileAssets();

    @Accessor
    void setLeftClickCounter(int a);

    @Accessor boolean isRunning();
}
