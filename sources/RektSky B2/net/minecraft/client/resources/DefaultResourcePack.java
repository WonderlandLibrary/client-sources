package net.minecraft.client.resources;

import java.util.*;
import net.minecraft.util.*;
import java.io.*;
import net.minecraft.client.resources.data.*;
import java.awt.image.*;
import net.minecraft.client.renderer.texture.*;
import com.google.common.collect.*;

public class DefaultResourcePack implements IResourcePack
{
    public static final Set<String> defaultResourceDomains;
    private final Map<String, File> mapAssets;
    
    public DefaultResourcePack(final Map<String, File> mapAssetsIn) {
        this.mapAssets = mapAssetsIn;
    }
    
    @Override
    public InputStream getInputStream(final ResourceLocation location) throws IOException {
        final InputStream inputstream = this.getResourceStream(location);
        if (inputstream != null) {
            return inputstream;
        }
        final InputStream inputstream2 = this.getInputStreamAssets(location);
        if (inputstream2 != null) {
            return inputstream2;
        }
        throw new FileNotFoundException(location.getResourcePath());
    }
    
    public InputStream getInputStreamAssets(final ResourceLocation location) throws IOException, FileNotFoundException {
        final File file1 = this.mapAssets.get(location.toString());
        return (file1 != null && file1.isFile()) ? new FileInputStream(file1) : null;
    }
    
    private InputStream getResourceStream(final ResourceLocation location) {
        return DefaultResourcePack.class.getResourceAsStream("/assets/" + location.getResourceDomain() + "/" + location.getResourcePath());
    }
    
    @Override
    public boolean resourceExists(final ResourceLocation location) {
        return this.getResourceStream(location) != null || this.mapAssets.containsKey(location.toString());
    }
    
    @Override
    public Set<String> getResourceDomains() {
        return DefaultResourcePack.defaultResourceDomains;
    }
    
    @Override
    public <T extends IMetadataSection> T getPackMetadata(final IMetadataSerializer p_135058_1_, final String p_135058_2_) throws IOException {
        try {
            final InputStream inputstream = new FileInputStream(this.mapAssets.get("pack.mcmeta"));
            return AbstractResourcePack.readMetadata(p_135058_1_, inputstream, p_135058_2_);
        }
        catch (RuntimeException var4) {
            return null;
        }
        catch (FileNotFoundException var5) {
            return null;
        }
    }
    
    @Override
    public BufferedImage getPackImage() throws IOException {
        return TextureUtil.readBufferedImage(DefaultResourcePack.class.getResourceAsStream("/" + new ResourceLocation("pack.png").getResourcePath()));
    }
    
    @Override
    public String getPackName() {
        return "Default";
    }
    
    static {
        defaultResourceDomains = ImmutableSet.of("minecraft", "realms");
    }
}
