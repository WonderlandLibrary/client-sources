package net.minecraft.client.renderer.texture;

import net.minecraft.client.resources.*;
import optfine.*;
import com.google.common.collect.*;
import org.apache.logging.log4j.*;
import java.io.*;
import java.util.concurrent.*;
import net.minecraft.util.*;
import net.minecraft.crash.*;
import java.util.*;

public class TextureManager implements IResourceManagerReloadListener, ITickable
{
    private final List listTickables;
    private final Map mapTextureCounters;
    private static final String __OBFID;
    private static final Logger logger;
    private final Map mapTextureObjects;
    private static final String[] I;
    private IResourceManager theResourceManager;
    
    public ResourceLocation getDynamicTextureLocation(final String s, final DynamicTexture dynamicTexture) {
        final Integer n = this.mapTextureCounters.get(s);
        Integer n2;
        if (n == null) {
            n2 = " ".length();
            "".length();
            if (3 < 3) {
                throw null;
            }
        }
        else {
            n2 = n + " ".length();
        }
        this.mapTextureCounters.put(s, n2);
        final String s2 = TextureManager.I[0x8B ^ 0x8D];
        final Object[] array = new Object["  ".length()];
        array["".length()] = s;
        array[" ".length()] = n2;
        final ResourceLocation resourceLocation = new ResourceLocation(String.format(s2, array));
        this.loadTexture(resourceLocation, dynamicTexture);
        return resourceLocation;
    }
    
    public ITextureObject getTexture(final ResourceLocation resourceLocation) {
        return this.mapTextureObjects.get(resourceLocation);
    }
    
    public void bindTexture(ResourceLocation textureLocation) {
        if (Config.isRandomMobs()) {
            textureLocation = RandomMobs.getTextureLocation(textureLocation);
        }
        ITextureObject textureObject = this.mapTextureObjects.get(textureLocation);
        if (textureObject == null) {
            textureObject = new SimpleTexture(textureLocation);
            this.loadTexture(textureLocation, textureObject);
        }
        TextureUtil.bindTexture(textureObject.getGlTextureId());
    }
    
    public void deleteTexture(final ResourceLocation resourceLocation) {
        final ITextureObject texture = this.getTexture(resourceLocation);
        if (texture != null) {
            TextureUtil.deleteTexture(texture.getGlTextureId());
        }
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
            if (3 == 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public boolean loadTickableTexture(final ResourceLocation resourceLocation, final ITickableTextureObject tickableTextureObject) {
        if (this.loadTexture(resourceLocation, tickableTextureObject)) {
            this.listTickables.add(tickableTextureObject);
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public TextureManager(final IResourceManager theResourceManager) {
        this.mapTextureObjects = Maps.newHashMap();
        this.listTickables = Lists.newArrayList();
        this.mapTextureCounters = Maps.newHashMap();
        this.theResourceManager = theResourceManager;
    }
    
    static {
        I();
        __OBFID = TextureManager.I["".length()];
        logger = LogManager.getLogger();
    }
    
    public boolean loadTexture(final ResourceLocation resourceLocation, final ITextureObject textureObject) {
        int n = " ".length();
        ITextureObject missingTexture = textureObject;
        try {
            textureObject.loadTexture(this.theResourceManager);
            "".length();
            if (4 < 2) {
                throw null;
            }
        }
        catch (IOException ex) {
            TextureManager.logger.warn(TextureManager.I[" ".length()] + resourceLocation, (Throwable)ex);
            missingTexture = TextureUtil.missingTexture;
            this.mapTextureObjects.put(resourceLocation, missingTexture);
            n = "".length();
            "".length();
            if (0 < 0) {
                throw null;
            }
        }
        catch (Throwable t) {
            final CrashReport crashReport = CrashReport.makeCrashReport(t, TextureManager.I["  ".length()]);
            final CrashReportCategory category = crashReport.makeCategory(TextureManager.I["   ".length()]);
            category.addCrashSection(TextureManager.I[0xBD ^ 0xB9], resourceLocation);
            category.addCrashSectionCallable(TextureManager.I[0x4A ^ 0x4F], new Callable(this, textureObject) {
                private final ITextureObject val$textureObj;
                private static final String __OBFID;
                final TextureManager this$0;
                private static final String[] I;
                
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
                        if (0 == 4) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
                
                private static void I() {
                    (I = new String[" ".length()])["".length()] = I("6)%\u007fiEUK\u007fo@", "uezOY");
                }
                
                @Override
                public String call() throws Exception {
                    return this.val$textureObj.getClass().getName();
                }
                
                static {
                    I();
                    __OBFID = TextureManager$1.I["".length()];
                }
                
                @Override
                public Object call() throws Exception {
                    return this.call();
                }
            });
            throw new ReportedException(crashReport);
        }
        this.mapTextureObjects.put(resourceLocation, missingTexture);
        return n != 0;
    }
    
    @Override
    public void onResourceManagerReload(final IResourceManager resourceManager) {
        Config.dbg(TextureManager.I[0xAB ^ 0xAC]);
        Config.log(TextureManager.I[0x55 ^ 0x5D] + Config.getResourcePackNames());
        final Iterator<ResourceLocation> iterator = this.mapTextureObjects.keySet().iterator();
        "".length();
        if (4 < 2) {
            throw null;
        }
        while (iterator.hasNext()) {
            final ResourceLocation resourceLocation = iterator.next();
            if (resourceLocation.getResourcePath().startsWith(TextureManager.I[0x9A ^ 0x93])) {
                final ITextureObject textureObject = this.mapTextureObjects.get(resourceLocation);
                if (textureObject instanceof AbstractTexture) {
                    ((AbstractTexture)textureObject).deleteGlTexture();
                }
                iterator.remove();
            }
        }
        final Iterator<Map.Entry<ResourceLocation, V>> iterator2 = this.mapTextureObjects.entrySet().iterator();
        "".length();
        if (0 <= -1) {
            throw null;
        }
        while (iterator2.hasNext()) {
            final Map.Entry<ResourceLocation, V> next = iterator2.next();
            this.loadTexture(next.getKey(), (ITextureObject)next.getValue());
        }
    }
    
    @Override
    public void tick() {
        final Iterator<ITickable> iterator = this.listTickables.iterator();
        "".length();
        if (3 <= 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            iterator.next().tick();
        }
    }
    
    private static void I() {
        (I = new String[0x97 ^ 0x9D])["".length()] = I("6\u00038uvE\u007fVupA", "uOgEF");
        TextureManager.I[" ".length()] = I("1*\u0000\r+\u0013k\u001d\u000en\u001b$\b\u0005n\u0003.\u0011\u0015;\u0005.SA", "wKiaN");
        TextureManager.I["  ".length()] = I("$7\u001e=2\u00027\u000b=/\u0011r\r19\u0002'\u000b1", "vRyTA");
        TextureManager.I["   ".length()] = I("\u0000\u000f\u0019 \u0000 \t\u000fo\u0019=\t\u000b;\u001c=\u0004J-\u0010;\u0004\ro\u00077\r\u0003<\u00017\u0018\u000f+", "RjjOu");
        TextureManager.I[0xA4 ^ 0xA0] = I("\b1\u001b(!(7\rg857\t3=5:", "ZThGT");
        TextureManager.I[0x92 ^ 0x97] = I("\u001f\u0002;\u000e\u00129\u0002c\u0015\u0005!\u0002 \u000eG(\u000b\"\t\u0014", "KgCzg");
        TextureManager.I[0x9D ^ 0x9B] = I("\u0007#9\u00178\n9xS&<\u007f3", "cZWvU");
        TextureManager.I[0x4B ^ 0x4C] = I("X\\De3\u0017\u001a\u0001$\u0005\u001b\u0018\te\u0015\u0017\u000e\u001a0\u0013\u0017\u0005NoKX", "rvnEa");
        TextureManager.I[0x27 ^ 0x2F] = I("5\u001d\u001e\u00016\u0015\u001b\bN3\u0006\u001b\u0006\u001dyG", "gxmnC");
        TextureManager.I[0x8B ^ 0x82] = I("\t\u0015\u001e\f\f\u0007\u001e\u000b\u001fW", "dvnmx");
    }
}
