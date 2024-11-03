package net.silentclient.client.utils.resource;

import java.util.List;
import java.util.Set;

import net.minecraft.client.resources.FallbackResourceManager;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;

public class SaturationResourceManager extends FallbackResourceManager implements IResourceManager {
	public SaturationResourceManager(IMetadataSerializer frmMetadataSerializerIn) {
        super(frmMetadataSerializerIn);
    }

    @Override
    public Set<String> getResourceDomains() {
        return null;
    }

    @Override
    public IResource getResource(ResourceLocation location) {
        return new SaturationResource();
    }

    @Override
    public List<IResource> getAllResources(ResourceLocation location) {
        return null;
    }
}
