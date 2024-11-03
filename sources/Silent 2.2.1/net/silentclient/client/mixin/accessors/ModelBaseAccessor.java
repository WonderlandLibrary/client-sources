package net.silentclient.client.mixin.accessors;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.TextureOffset;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Map;

@Mixin(ModelBase.class)
public interface ModelBaseAccessor {
    @Accessor
    Map<String, TextureOffset> getModelTextureMap();
}
