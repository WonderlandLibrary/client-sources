package net.minecraft.client.resources;

import java.awt.image.BufferedImage;
import java.io.*;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Charsets;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;

public abstract class AbstractResourcePack implements IResourcePack {
	private static final Logger LOGGER = LogManager.getLogger();
	public final File resourcePackFile;

	public AbstractResourcePack(File resourcePackFileIn) {
		this.resourcePackFile = resourcePackFileIn;
	}

	private static String locationToName(ResourceLocation location) {
		return String.format("%s/%s/%s", new Object[] { "assets", location.getResourceDomain(), location.getResourcePath() });
	}

	protected static String getRelativeName(File p_110595_0_, File p_110595_1_) {
		return p_110595_0_.toURI().relativize(p_110595_1_.toURI()).getPath();
	}

	@Override
	public InputStream getInputStream(ResourceLocation location) throws IOException {
		return this.getInputStreamByName(locationToName(location));
	}

	@Override
	public boolean resourceExists(ResourceLocation location) {
		return this.hasResourceName(locationToName(location));
	}

	protected abstract InputStream getInputStreamByName(String name) throws IOException;

	protected abstract boolean hasResourceName(String name);

	protected void logNameNotLowercase(String name) {
		LOGGER.warn("ResourcePack: ignored non-lowercase namespace: {} in {}", new Object[] { name, this.resourcePackFile });
	}

	@Override
	public <T extends IMetadataSection> T getPackMetadata(MetadataSerializer metadataSerializer, String metadataSectionName) throws IOException {
		return readMetadata(metadataSerializer, this.getInputStreamByName("pack.mcmeta"), metadataSectionName);
	}

	static <T extends IMetadataSection> T readMetadata(MetadataSerializer metadataSerializer, InputStream p_110596_1_, String sectionName) {
		JsonObject jsonobject = null;
		BufferedReader bufferedreader = null;

		try {
			bufferedreader = new BufferedReader(new InputStreamReader(p_110596_1_, Charsets.UTF_8));
			jsonobject = (new JsonParser()).parse(bufferedreader).getAsJsonObject();
		} catch (RuntimeException runtimeexception) {
			throw new JsonParseException(runtimeexception);
		} finally {
			IOUtils.closeQuietly(bufferedreader);
		}

		return metadataSerializer.parseMetadataSection(sectionName, jsonobject);
	}

	@Override
	public BufferedImage getPackImage() throws IOException {
		return TextureUtil.readBufferedImage(this.getInputStreamByName("pack.png"));
	}

	@Override
	public String getPackName() {
		return this.resourcePackFile.getName();
	}
}
