package net.minecraft.client.resources;

import java.io.*;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;

import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;

public class FallbackResourceManager implements IResourceManager {
	private static final Logger LOGGER = LogManager.getLogger();
	protected final List<IResourcePack> resourcePacks = Lists.<IResourcePack> newArrayList();
	private final MetadataSerializer frmMetadataSerializer;

	public FallbackResourceManager(MetadataSerializer frmMetadataSerializerIn) {
		this.frmMetadataSerializer = frmMetadataSerializerIn;
	}

	public void addResourcePack(IResourcePack resourcePack) {
		this.resourcePacks.add(resourcePack);
	}

	@Override
	public Set<String> getResourceDomains() {
		return null;
	}

	@Override
	public IResource getResource(ResourceLocation location) throws IOException {
		this.checkResourcePath(location);
		IResourcePack iresourcepack = null;
		ResourceLocation resourcelocation = getLocationMcmeta(location);

		for (int i = this.resourcePacks.size() - 1; i >= 0; --i) {
			IResourcePack iresourcepack1 = this.resourcePacks.get(i);

			if ((iresourcepack == null) && iresourcepack1.resourceExists(resourcelocation)) {
				iresourcepack = iresourcepack1;
			}

			if (iresourcepack1.resourceExists(location)) {
				InputStream inputstream = null;

				if (iresourcepack != null) {
					inputstream = this.getInputStream(resourcelocation, iresourcepack);
				}

				return new SimpleResource(iresourcepack1.getPackName(), location, this.getInputStream(location, iresourcepack1), inputstream, this.frmMetadataSerializer);
			}
		}

		throw new FileNotFoundException(location.toString());
	}

	protected InputStream getInputStream(ResourceLocation location, IResourcePack resourcePack) throws IOException {
		InputStream inputstream = resourcePack.getInputStream(location);
		return LOGGER.isDebugEnabled() ? new FallbackResourceManager.InputStreamLeakedResourceLogger(inputstream, location, resourcePack.getPackName()) : inputstream;
	}

	private void checkResourcePath(ResourceLocation p_188552_1_) throws IOException {
		if (p_188552_1_.getResourcePath().contains("..")) { throw new IOException("Invalid relative path to resource: " + p_188552_1_); }
	}

	@Override
	public List<IResource> getAllResources(ResourceLocation location) throws IOException {
		this.checkResourcePath(location);
		List<IResource> list = Lists.<IResource> newArrayList();
		ResourceLocation resourcelocation = getLocationMcmeta(location);

		for (IResourcePack iresourcepack : this.resourcePacks) {
			if (iresourcepack.resourceExists(location)) {
				InputStream inputstream = iresourcepack.resourceExists(resourcelocation) ? this.getInputStream(resourcelocation, iresourcepack) : null;
				list.add(new SimpleResource(iresourcepack.getPackName(), location, this.getInputStream(location, iresourcepack), inputstream, this.frmMetadataSerializer));
			}
		}

		if (list.isEmpty()) {
			throw new FileNotFoundException(location.toString());
		} else {
			return list;
		}
	}

	static ResourceLocation getLocationMcmeta(ResourceLocation location) {
		return new ResourceLocation(location.getResourceDomain(), location.getResourcePath() + ".mcmeta");
	}

	static class InputStreamLeakedResourceLogger extends InputStream {
		private final InputStream inputStream;
		private final String message;
		private boolean isClosed;

		public InputStreamLeakedResourceLogger(InputStream p_i46093_1_, ResourceLocation location, String resourcePack) {
			this.inputStream = p_i46093_1_;
			ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
			(new Exception()).printStackTrace(new PrintStream(bytearrayoutputstream));
			this.message = "Leaked resource: \'" + location + "\' loaded from pack: \'" + resourcePack + "\'\n" + bytearrayoutputstream;
		}

		@Override
		public void close() throws IOException {
			this.inputStream.close();
			this.isClosed = true;
		}

		@Override
		protected void finalize() throws Throwable {
			if (!this.isClosed) {
				FallbackResourceManager.LOGGER.warn(this.message);
			}

			super.finalize();
		}

		@Override
		public int read() throws IOException {
			return this.inputStream.read();
		}
	}
}
