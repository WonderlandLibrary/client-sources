package net.shoreline.client.mixin.accessor;

import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferBuilderStorage;
import net.minecraft.client.render.RenderLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.SortedMap;

@Mixin(BufferBuilderStorage.class)
public interface AccessorBufferBuilderStorage
{
    @Accessor("entityBuilders")
    SortedMap<RenderLayer, BufferBuilder> hookGetEntityBuilders();
}
