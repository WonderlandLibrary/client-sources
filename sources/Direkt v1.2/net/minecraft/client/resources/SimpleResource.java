package net.minecraft.client.resources;

import java.io.*;
import java.util.Map;

import javax.annotation.Nullable;

import org.apache.commons.io.IOUtils;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;

public class SimpleResource implements IResource, Closeable {
	private final Map<String, IMetadataSection> mapMetadataSections = Maps.<String, IMetadataSection> newHashMap();
	private final String resourcePackName;
	private final ResourceLocation srResourceLocation;
	private final InputStream resourceInputStream;
	private final InputStream mcmetaInputStream;
	private final MetadataSerializer srMetadataSerializer;
	private boolean mcmetaJsonChecked;
	private JsonObject mcmetaJson;

	public SimpleResource(String resourcePackNameIn, ResourceLocation srResourceLocationIn, InputStream resourceInputStreamIn, InputStream mcmetaInputStreamIn,
			MetadataSerializer srMetadataSerializerIn) {
		this.resourcePackName = resourcePackNameIn;
		this.srResourceLocation = srResourceLocationIn;
		this.resourceInputStream = resourceInputStreamIn;
		this.mcmetaInputStream = mcmetaInputStreamIn;
		this.srMetadataSerializer = srMetadataSerializerIn;
	}

	@Override
	public ResourceLocation getResourceLocation() {
		return this.srResourceLocation;
	}

	@Override
	public InputStream getInputStream() {
		return this.resourceInputStream;
	}

	@Override
	public boolean hasMetadata() {
		return this.mcmetaInputStream != null;
	}

	@Override
	@Nullable
	public <T extends IMetadataSection> T getMetadata(String sectionName) {
		if (!this.hasMetadata()) {
			return null;
		} else {
			if ((this.mcmetaJson == null) && !this.mcmetaJsonChecked) {
				this.mcmetaJsonChecked = true;
				BufferedReader bufferedreader = null;

				try {
					bufferedreader = new BufferedReader(new InputStreamReader(this.mcmetaInputStream));
					this.mcmetaJson = (new JsonParser()).parse(bufferedreader).getAsJsonObject();
				} finally {
					IOUtils.closeQuietly(bufferedreader);
				}
			}

			T t = (T) this.mapMetadataSections.get(sectionName);

			if (t == null) {
				t = this.srMetadataSerializer.parseMetadataSection(sectionName, this.mcmetaJson);
			}

			return t;
		}
	}

	@Override
	public String getResourcePackName() {
		return this.resourcePackName;
	}

	@Override
	public boolean equals(Object p_equals_1_) {
		if (this == p_equals_1_) {
			return true;
		} else if (!(p_equals_1_ instanceof SimpleResource)) {
			return false;
		} else {
			SimpleResource simpleresource = (SimpleResource) p_equals_1_;

			if (this.srResourceLocation != null) {
				if (!this.srResourceLocation.equals(simpleresource.srResourceLocation)) { return false; }
			} else if (simpleresource.srResourceLocation != null) { return false; }

			if (this.resourcePackName != null) {
				if (!this.resourcePackName.equals(simpleresource.resourcePackName)) { return false; }
			} else if (simpleresource.resourcePackName != null) { return false; }

			return true;
		}
	}

	@Override
	public int hashCode() {
		int i = this.resourcePackName != null ? this.resourcePackName.hashCode() : 0;
		i = (31 * i) + (this.srResourceLocation != null ? this.srResourceLocation.hashCode() : 0);
		return i;
	}

	@Override
	public void close() throws IOException {
		this.resourceInputStream.close();

		if (this.mcmetaInputStream != null) {
			this.mcmetaInputStream.close();
		}
	}
}
