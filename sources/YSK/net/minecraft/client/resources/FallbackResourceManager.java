package net.minecraft.client.resources;

import net.minecraft.client.resources.data.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import org.apache.logging.log4j.*;
import java.util.*;
import java.io.*;

public class FallbackResourceManager implements IResourceManager
{
    private final IMetadataSerializer frmMetadataSerializer;
    private static final Logger logger;
    protected final List<IResourcePack> resourcePacks;
    private static final String[] I;
    
    public FallbackResourceManager(final IMetadataSerializer frmMetadataSerializer) {
        this.resourcePacks = (List<IResourcePack>)Lists.newArrayList();
        this.frmMetadataSerializer = frmMetadataSerializer;
    }
    
    protected InputStream getInputStream(final ResourceLocation resourceLocation, final IResourcePack resourcePack) throws IOException {
        final InputStream inputStream = resourcePack.getInputStream(resourceLocation);
        InputStream inputStream2;
        if (FallbackResourceManager.logger.isDebugEnabled()) {
            inputStream2 = new InputStreamLeakedResourceLogger(inputStream, resourceLocation, resourcePack.getPackName());
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            inputStream2 = inputStream;
        }
        return inputStream2;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("A=\u0004!\u001c\u001b1", "oPgLy");
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
            if (!true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void addResourcePack(final IResourcePack resourcePack) {
        this.resourcePacks.add(resourcePack);
    }
    
    static {
        I();
        logger = LogManager.getLogger();
    }
    
    static ResourceLocation getLocationMcmeta(final ResourceLocation resourceLocation) {
        return new ResourceLocation(resourceLocation.getResourceDomain(), String.valueOf(resourceLocation.getResourcePath()) + FallbackResourceManager.I["".length()]);
    }
    
    @Override
    public Set<String> getResourceDomains() {
        return null;
    }
    
    @Override
    public IResource getResource(final ResourceLocation resourceLocation) throws IOException {
        IResourcePack resourcePack = null;
        final ResourceLocation locationMcmeta = getLocationMcmeta(resourceLocation);
        int i = this.resourcePacks.size() - " ".length();
        "".length();
        if (2 == 1) {
            throw null;
        }
        while (i >= 0) {
            final IResourcePack resourcePack2 = this.resourcePacks.get(i);
            if (resourcePack == null && resourcePack2.resourceExists(locationMcmeta)) {
                resourcePack = resourcePack2;
            }
            if (resourcePack2.resourceExists(resourceLocation)) {
                InputStream inputStream = null;
                if (resourcePack != null) {
                    inputStream = this.getInputStream(locationMcmeta, resourcePack);
                }
                return new SimpleResource(resourcePack2.getPackName(), resourceLocation, this.getInputStream(resourceLocation, resourcePack2), inputStream, this.frmMetadataSerializer);
            }
            --i;
        }
        throw new FileNotFoundException(resourceLocation.toString());
    }
    
    @Override
    public List<IResource> getAllResources(final ResourceLocation resourceLocation) throws IOException {
        final ArrayList arrayList = Lists.newArrayList();
        final ResourceLocation locationMcmeta = getLocationMcmeta(resourceLocation);
        final Iterator<IResourcePack> iterator = this.resourcePacks.iterator();
        "".length();
        if (2 >= 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            final IResourcePack resourcePack = iterator.next();
            if (resourcePack.resourceExists(resourceLocation)) {
                InputStream inputStream;
                if (resourcePack.resourceExists(locationMcmeta)) {
                    inputStream = this.getInputStream(locationMcmeta, resourcePack);
                    "".length();
                    if (0 >= 1) {
                        throw null;
                    }
                }
                else {
                    inputStream = null;
                }
                arrayList.add(new SimpleResource(resourcePack.getPackName(), resourceLocation, this.getInputStream(resourceLocation, resourcePack), inputStream, this.frmMetadataSerializer));
            }
        }
        if (arrayList.isEmpty()) {
            throw new FileNotFoundException(resourceLocation.toString());
        }
        return (List<IResource>)arrayList;
    }
    
    static Logger access$0() {
        return FallbackResourceManager.logger;
    }
    
    static class InputStreamLeakedResourceLogger extends InputStream
    {
        private final InputStream field_177330_a;
        private final String field_177328_b;
        private boolean field_177329_c;
        private static final String[] I;
        
        public InputStreamLeakedResourceLogger(final InputStream field_177330_a, final ResourceLocation resourceLocation, final String s) {
            this.field_177329_c = ("".length() != 0);
            this.field_177330_a = field_177330_a;
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            new Exception().printStackTrace(new PrintStream(byteArrayOutputStream));
            this.field_177328_b = InputStreamLeakedResourceLogger.I["".length()] + resourceLocation + InputStreamLeakedResourceLogger.I[" ".length()] + s + InputStreamLeakedResourceLogger.I["  ".length()] + byteArrayOutputStream.toString();
        }
        
        private static void I() {
            (I = new String["   ".length()])["".length()] = I("\u0014\u001392\u000b<V*<\u001d7\u0003*:\u000bbV\u007f", "XvXYn");
            InputStreamLeakedResourceLogger.I[" ".length()] = I("cU\u001d\u00043 \u0010\u0015K46\u001a\u001cK\"%\u0016\u001aQrc", "DuqkR");
            InputStreamLeakedResourceLogger.I["  ".length()] = I("HX", "oRmys");
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
                if (4 == 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public void close() throws IOException {
            this.field_177330_a.close();
            this.field_177329_c = (" ".length() != 0);
        }
        
        @Override
        protected void finalize() throws Throwable {
            if (!this.field_177329_c) {
                FallbackResourceManager.access$0().warn(this.field_177328_b);
            }
            super.finalize();
        }
        
        @Override
        public int read() throws IOException {
            return this.field_177330_a.read();
        }
        
        static {
            I();
        }
    }
}
