package net.minecraft.client.resources;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableSet;

import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.src.ReflectorForge;
import net.minecraft.util.ResourceLocation;

public class DefaultResourcePack implements IResourcePack {
	public static final Set<String> DEFAULT_RESOURCE_DOMAINS = ImmutableSet.<String> of("minecraft", "realms");
	private final ResourceIndex resourceIndex;

	public DefaultResourcePack(ResourceIndex resourceIndexIn) {
		this.resourceIndex = resourceIndexIn;
	}

	@Override
	public InputStream getInputStream(ResourceLocation location) throws IOException {
		InputStream inputstream = this.getResourceStream(location);

		if (inputstream != null) {
			return inputstream;
		} else {
			InputStream inputstream1 = this.getInputStreamAssets(location);

			if (inputstream1 != null) {
				return inputstream1;
			} else {
				throw new FileNotFoundException(location.getResourcePath());
			}
		}
	}

	@Nullable
	public InputStream getInputStreamAssets(ResourceLocation location) throws IOException, FileNotFoundException {
		File file1 = this.resourceIndex.getFile(location);
		return (file1 != null) && file1.isFile() ? new FileInputStream(file1) : null;
	}

	private InputStream getResourceStream(ResourceLocation location) {
		String s = "/assets/" + location.getResourceDomain() + "/" + location.getResourcePath();
		InputStream inputstream = ReflectorForge.getOptiFineResourceStream(s);
		return inputstream != null ? inputstream : DefaultResourcePack.class.getResourceAsStream("/assets/" + location.getResourceDomain() + "/" + location.getResourcePath());
	}

	@Override
	public boolean resourceExists(ResourceLocation location) {
		return (this.getResourceStream(location) != null) || this.resourceIndex.isFileExisting(location);
	}

	@Override
	public Set<String> getResourceDomains() {
		return DEFAULT_RESOURCE_DOMAINS;
	}

	@Override
	public <T extends IMetadataSection> T getPackMetadata(MetadataSerializer metadataSerializer, String metadataSectionName) throws IOException {
		try {
			InputStream inputstream = new FileInputStream(this.resourceIndex.getPackMcmeta());
			return AbstractResourcePack.readMetadata(metadataSerializer, inputstream, metadataSectionName);
		} catch (RuntimeException var4) {
			return (T) (null);
		} catch (FileNotFoundException var5) {
			return (T) (null);
		}
	}

	@Override
	public BufferedImage getPackImage() throws IOException {
		return TextureUtil.readBufferedImage(DefaultResourcePack.class.getResourceAsStream("/" + (new ResourceLocation("pack.png")).getResourcePath()));
	}

	@Override
	public String getPackName() {
		return "Default";
	}
}
