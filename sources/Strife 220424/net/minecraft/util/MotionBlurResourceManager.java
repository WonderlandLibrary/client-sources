package net.minecraft.util;

import club.strifeclient.module.implementations.visual.MotionBlur;
import net.minecraft.client.resources.FallbackResourceManager;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.IMetadataSerializer;

import java.io.IOException;

public class MotionBlurResourceManager extends FallbackResourceManager implements IResourceManager {

    private MotionBlur motionBlur;

    public MotionBlurResourceManager(final IMetadataSerializer frmMetadataSerializerIn, final MotionBlur motionBlur) {
        super(frmMetadataSerializerIn);
        this.motionBlur = motionBlur;
    }

    public MotionBlurResourceManager(IMetadataSerializer frmMetadataSerializerIn) {
        super(frmMetadataSerializerIn);
    }

    @Override
    public IResource getResource(ResourceLocation location) throws IOException {
        return new MotionBlurResource(motionBlur.strengthSetting.getDouble());
    }
}