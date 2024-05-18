package net.minecraft.client.resources;

import java.util.*;
import java.awt.image.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import java.io.*;
import net.minecraft.client.resources.data.*;
import com.google.common.collect.*;

public class DefaultResourcePack implements IResourcePack
{
    private static final String[] I;
    public static final Set<String> defaultResourceDomains;
    private final Map<String, File> mapAssets;
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public BufferedImage getPackImage() throws IOException {
        return TextureUtil.readBufferedImage(DefaultResourcePack.class.getResourceAsStream(DefaultResourcePack.I[0xB ^ 0xE] + new ResourceLocation(DefaultResourcePack.I[0x2C ^ 0x2A]).getResourcePath()));
    }
    
    public InputStream getInputStreamAssets(final ResourceLocation resourceLocation) throws FileNotFoundException, IOException {
        final File file = this.mapAssets.get(resourceLocation.toString());
        InputStream inputStream;
        if (file != null && file.isFile()) {
            inputStream = new FileInputStream(file);
            "".length();
            if (3 <= 0) {
                throw null;
            }
        }
        else {
            inputStream = null;
        }
        return inputStream;
    }
    
    @Override
    public String getPackName() {
        return DefaultResourcePack.I[0x18 ^ 0x1F];
    }
    
    @Override
    public Set<String> getResourceDomains() {
        return DefaultResourcePack.defaultResourceDomains;
    }
    
    @Override
    public InputStream getInputStream(final ResourceLocation resourceLocation) throws IOException {
        final InputStream resourceStream = this.getResourceStream(resourceLocation);
        if (resourceStream != null) {
            return resourceStream;
        }
        final InputStream inputStreamAssets = this.getInputStreamAssets(resourceLocation);
        if (inputStreamAssets != null) {
            return inputStreamAssets;
        }
        throw new FileNotFoundException(resourceLocation.getResourcePath());
    }
    
    @Override
    public <T extends IMetadataSection> T getPackMetadata(final IMetadataSerializer metadataSerializer, final String s) throws IOException {
        try {
            return AbstractResourcePack.readMetadata(metadataSerializer, new FileInputStream(this.mapAssets.get(DefaultResourcePack.I[0x68 ^ 0x6C])), s);
        }
        catch (RuntimeException ex) {
            return null;
        }
        catch (FileNotFoundException ex2) {
            return null;
        }
    }
    
    private InputStream getResourceStream(final ResourceLocation resourceLocation) {
        return DefaultResourcePack.class.getResourceAsStream(DefaultResourcePack.I["  ".length()] + resourceLocation.getResourceDomain() + DefaultResourcePack.I["   ".length()] + resourceLocation.getResourcePath());
    }
    
    private static void I() {
        (I = new String[0x39 ^ 0x31])["".length()] = I("\u0006:\u0003\u0010&\u00192\u000b\u0001", "kSmuE");
        DefaultResourcePack.I[" ".length()] = I("\u0003\u0017\u0003%5\u0002", "qrbIX");
        DefaultResourcePack.I["  ".length()] = I("h&9&\u000e34e", "GGJUk");
        DefaultResourcePack.I["   ".length()] = I("V", "yPhgL");
        DefaultResourcePack.I[0xC4 ^ 0xC0] = I("\n\u0006\u0016>b\u0017\u0004\u001808\u001b", "zguUL");
        DefaultResourcePack.I[0x47 ^ 0x42] = I("X", "wNHLc");
        DefaultResourcePack.I[0x90 ^ 0x96] = I("?\u0014+\u0018D?\u001b/", "OuHsj");
        DefaultResourcePack.I[0x2C ^ 0x2B] = I("1533;\u0019$", "uPURN");
    }
    
    static {
        I();
        defaultResourceDomains = (Set)ImmutableSet.of((Object)DefaultResourcePack.I["".length()], (Object)DefaultResourcePack.I[" ".length()]);
    }
    
    @Override
    public boolean resourceExists(final ResourceLocation resourceLocation) {
        if (this.getResourceStream(resourceLocation) == null && !this.mapAssets.containsKey(resourceLocation.toString())) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    public DefaultResourcePack(final Map<String, File> mapAssets) {
        this.mapAssets = mapAssets;
    }
}
