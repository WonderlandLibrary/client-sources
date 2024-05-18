package net.minecraft.client.resources;

import net.minecraft.client.resources.data.*;
import com.google.common.base.*;
import org.apache.commons.io.*;
import com.google.gson.*;
import net.minecraft.util.*;
import java.io.*;
import org.apache.logging.log4j.*;
import java.awt.image.*;
import net.minecraft.client.renderer.texture.*;

public abstract class AbstractResourcePack implements IResourcePack
{
    public final File resourcePackFile;
    private static final Logger resourceLog;
    private static final String[] I;
    
    static <T extends IMetadataSection> T readMetadata(final IMetadataSerializer metadataSerializer, final InputStream inputStream, final String s) {
        JsonObject asJsonObject = null;
        Reader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream, Charsets.UTF_8));
            asJsonObject = new JsonParser().parse(reader).getAsJsonObject();
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        catch (RuntimeException ex) {
            throw new JsonParseException((Throwable)ex);
        }
        finally {
            IOUtils.closeQuietly(reader);
        }
        IOUtils.closeQuietly(reader);
        return (T)metadataSerializer.parseMetadataSection(s, asJsonObject);
    }
    
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
            if (0 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static String locationToName(final ResourceLocation resourceLocation) {
        final String s = AbstractResourcePack.I["".length()];
        final Object[] array = new Object["   ".length()];
        array["".length()] = AbstractResourcePack.I[" ".length()];
        array[" ".length()] = resourceLocation.getResourceDomain();
        array["  ".length()] = resourceLocation.getResourcePath();
        return String.format(s, array);
    }
    
    @Override
    public <T extends IMetadataSection> T getPackMetadata(final IMetadataSerializer metadataSerializer, final String s) throws IOException {
        return readMetadata(metadataSerializer, this.getInputStreamByName(AbstractResourcePack.I["   ".length()]), s);
    }
    
    static {
        I();
        resourceLog = LogManager.getLogger();
    }
    
    protected abstract boolean hasResourceName(final String p0);
    
    protected static String getRelativeName(final File file, final File file2) {
        return file.toURI().relativize(file2.toURI()).getPath();
    }
    
    public AbstractResourcePack(final File resourcePackFile) {
        this.resourcePackFile = resourcePackFile;
    }
    
    @Override
    public boolean resourceExists(final ResourceLocation resourceLocation) {
        return this.hasResourceName(locationToName(resourceLocation));
    }
    
    @Override
    public String getPackName() {
        return this.resourcePackFile.getName();
    }
    
    protected abstract InputStream getInputStreamByName(final String p0) throws IOException;
    
    private static void I() {
        (I = new String[0x8C ^ 0x89])["".length()] = I("A\nMi'K\\\u0011", "dybLT");
        AbstractResourcePack.I[" ".length()] = I("\u00139\"\u0001>\u0001", "rJQdJ");
        AbstractResourcePack.I["  ".length()] = I("\u0000\b2;\u001e \u000e$\u0004\n1\u0006{t\u00025\u0003.&\u000e6M/;\u0005\u007f\u0001.#\u000e \u000e '\u000er\u0003 9\u000e!\u001d 7\u000ehMd'K;\u0003aq\u0018", "RmATk");
        AbstractResourcePack.I["   ".length()] = I("227/}/09!'#", "BSTDS");
        AbstractResourcePack.I[0xA7 ^ 0xA3] = I("7\u0004\u0017\u0011d7\u000b\u0013", "GetzJ");
    }
    
    protected void logNameNotLowercase(final String s) {
        final Logger resourceLog = AbstractResourcePack.resourceLog;
        final String s2 = AbstractResourcePack.I["  ".length()];
        final Object[] array = new Object["  ".length()];
        array["".length()] = s;
        array[" ".length()] = this.resourcePackFile;
        resourceLog.warn(s2, array);
    }
    
    @Override
    public BufferedImage getPackImage() throws IOException {
        return TextureUtil.readBufferedImage(this.getInputStreamByName(AbstractResourcePack.I[0x9 ^ 0xD]));
    }
    
    @Override
    public InputStream getInputStream(final ResourceLocation resourceLocation) throws IOException {
        return this.getInputStreamByName(locationToName(resourceLocation));
    }
}
