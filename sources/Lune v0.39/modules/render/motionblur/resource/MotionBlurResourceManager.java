package me.superskidder.lune.modules.render.motionblur.resource;

import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @author: Fyu
 * @description:
 * @create: 2020/12/29-17:08
 */
public class MotionBlurResourceManager implements IResourceManager {
    @Override
    public Set<String> getResourceDomains() {
        return null;
    }

    @Override
    public IResource getResource(ResourceLocation location) throws IOException {
        return new MotionBlurResource();
    }

    @Override
    public List<IResource> getAllResources(ResourceLocation location) throws IOException {
        return null;
    }
}

