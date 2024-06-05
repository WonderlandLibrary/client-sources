package net.minecraft.util;

import me.dinozoid.strife.module.implementations.visuals.MotionBlurModule;
import net.minecraft.client.resources.FallbackResourceManager;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.IMetadataSerializer;

import java.io.IOException;

public class MotionBlurResourceManager extends FallbackResourceManager implements IResourceManager {
    public MotionBlurResourceManager(IMetadataSerializer frmMetadataSerializerIn) {
        super(frmMetadataSerializerIn);
    }
    @Override
    public IResource getResource(ResourceLocation location) throws IOException {
        return new MotionBlurResource(MotionBlurModule.instance().blurStrength().value());
    }
}
