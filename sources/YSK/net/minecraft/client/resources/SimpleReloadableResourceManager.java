package net.minecraft.client.resources;

import net.minecraft.client.resources.data.*;
import org.apache.logging.log4j.*;
import java.util.*;
import net.minecraft.util.*;
import java.io.*;
import com.google.common.base.*;
import com.google.common.collect.*;

public class SimpleReloadableResourceManager implements IReloadableResourceManager
{
    private static final Joiner joinerResourcePacks;
    private static final Logger logger;
    private final Map<String, FallbackResourceManager> domainResourceManagers;
    private static final String[] I;
    private final IMetadataSerializer rmMetadataSerializer;
    private final List<IResourceManagerReloadListener> reloadListeners;
    private final Set<String> setResourceDomains;
    
    @Override
    public Set<String> getResourceDomains() {
        return this.setResourceDomains;
    }
    
    public SimpleReloadableResourceManager(final IMetadataSerializer rmMetadataSerializer) {
        this.domainResourceManagers = (Map<String, FallbackResourceManager>)Maps.newHashMap();
        this.reloadListeners = (List<IResourceManagerReloadListener>)Lists.newArrayList();
        this.setResourceDomains = (Set<String>)Sets.newLinkedHashSet();
        this.rmMetadataSerializer = rmMetadataSerializer;
    }
    
    static {
        I();
        logger = LogManager.getLogger();
        joinerResourcePacks = Joiner.on(SimpleReloadableResourceManager.I["".length()]);
    }
    
    private void notifyReloadListeners() {
        final Iterator<IResourceManagerReloadListener> iterator = this.reloadListeners.iterator();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            iterator.next().onResourceManagerReload(this);
        }
    }
    
    @Override
    public void registerReloadListener(final IResourceManagerReloadListener resourceManagerReloadListener) {
        this.reloadListeners.add(resourceManagerReloadListener);
        resourceManagerReloadListener.onResourceManagerReload(this);
    }
    
    @Override
    public IResource getResource(final ResourceLocation resourceLocation) throws IOException {
        final FallbackResourceManager fallbackResourceManager = this.domainResourceManagers.get(resourceLocation.getResourceDomain());
        if (fallbackResourceManager != null) {
            return fallbackResourceManager.getResource(resourceLocation);
        }
        throw new FileNotFoundException(resourceLocation.toString());
    }
    
    @Override
    public List<IResource> getAllResources(final ResourceLocation resourceLocation) throws IOException {
        final FallbackResourceManager fallbackResourceManager = this.domainResourceManagers.get(resourceLocation.getResourceDomain());
        if (fallbackResourceManager != null) {
            return fallbackResourceManager.getAllResources(resourceLocation);
        }
        throw new FileNotFoundException(resourceLocation.toString());
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
            if (1 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void reloadResources(final List<IResourcePack> list) {
        this.clearResources();
        SimpleReloadableResourceManager.logger.info(SimpleReloadableResourceManager.I[" ".length()] + SimpleReloadableResourceManager.joinerResourcePacks.join(Iterables.transform((Iterable)list, (Function)new Function<IResourcePack, String>(this) {
            final SimpleReloadableResourceManager this$0;
            
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
                    if (4 == 1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public Object apply(final Object o) {
                return this.apply((IResourcePack)o);
            }
            
            public String apply(final IResourcePack resourcePack) {
                return resourcePack.getPackName();
            }
        })));
        final Iterator<IResourcePack> iterator = list.iterator();
        "".length();
        if (-1 >= 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            this.reloadResourcePack(iterator.next());
        }
        this.notifyReloadListeners();
    }
    
    private void clearResources() {
        this.domainResourceManagers.clear();
        this.setResourceDomains.clear();
    }
    
    public void reloadResourcePack(final IResourcePack resourcePack) {
        final Iterator<String> iterator = resourcePack.getResourceDomains().iterator();
        "".length();
        if (4 < 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            final String s = iterator.next();
            this.setResourceDomains.add(s);
            FallbackResourceManager fallbackResourceManager = this.domainResourceManagers.get(s);
            if (fallbackResourceManager == null) {
                fallbackResourceManager = new FallbackResourceManager(this.rmMetadataSerializer);
                this.domainResourceManagers.put(s, fallbackResourceManager);
            }
            fallbackResourceManager.addResourcePack(resourcePack);
        }
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("}S", "QsIsn");
        SimpleReloadableResourceManager.I[" ".length()] = I("\u0013,\u000b\u000b+% \t\u0003j\u0013,\u0014\u000b?3*\u0002)+/(\u0000\u00018{i", "AIgdJ");
    }
}
