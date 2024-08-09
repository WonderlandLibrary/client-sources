/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.resources;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.resources.FolderPack;
import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.ResourcePack;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.resources.data.IMetadataSectionSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VanillaPack
implements IResourcePack {
    public static Path basePath;
    private static final Logger LOGGER;
    public static Class<?> baseClass;
    private static final Map<ResourcePackType, FileSystem> FILE_SYSTEMS_BY_PACK_TYPE;
    public final Set<String> resourceNamespaces;
    private static final boolean ON_WINDOWS;
    private static final boolean FORGE;

    public VanillaPack(String ... stringArray) {
        this.resourceNamespaces = ImmutableSet.copyOf(stringArray);
    }

    @Override
    public InputStream getRootResourceStream(String string) throws IOException {
        if (!string.contains("/") && !string.contains("\\")) {
            Path path;
            if (basePath != null && Files.exists(path = basePath.resolve(string), new LinkOption[0])) {
                return Files.newInputStream(path, new OpenOption[0]);
            }
            return this.getInputStreamVanilla(string);
        }
        throw new IllegalArgumentException("Root resources can only be filenames, not paths (no / allowed!)");
    }

    @Override
    public InputStream getResourceStream(ResourcePackType resourcePackType, ResourceLocation resourceLocation) throws IOException {
        InputStream inputStream = this.getInputStreamVanilla(resourcePackType, resourceLocation);
        if (inputStream != null) {
            return inputStream;
        }
        throw new FileNotFoundException(resourceLocation.getPath());
    }

    @Override
    public Collection<ResourceLocation> getAllResourceLocations(ResourcePackType resourcePackType, String string, String string2, int n, Predicate<String> predicate) {
        URI uRI;
        Object object;
        HashSet<ResourceLocation> hashSet = Sets.newHashSet();
        if (basePath != null) {
            try {
                VanillaPack.collectResources(hashSet, n, string, basePath.resolve(resourcePackType.getDirectoryName()), string2, predicate);
            } catch (IOException iOException) {
                // empty catch block
            }
            if (resourcePackType == ResourcePackType.CLIENT_RESOURCES) {
                object = null;
                try {
                    object = baseClass.getClassLoader().getResources(resourcePackType.getDirectoryName() + "/");
                } catch (IOException iOException) {
                    // empty catch block
                }
                while (object != null && object.hasMoreElements()) {
                    try {
                        uRI = ((URL)object.nextElement()).toURI();
                        if (!"file".equals(uRI.getScheme())) continue;
                        VanillaPack.collectResources(hashSet, n, string, Paths.get(uRI), string2, predicate);
                    } catch (IOException | URISyntaxException exception) {}
                }
            }
        }
        try {
            object = VanillaPack.class.getResource("/" + resourcePackType.getDirectoryName() + "/.mcassetsroot");
            if (object == null) {
                LOGGER.error("Couldn't find .mcassetsroot, cannot load vanilla resources");
                return hashSet;
            }
            uRI = ((URL)object).toURI();
            if ("file".equals(uRI.getScheme())) {
                URL uRL = new URL(((URL)object).toString().substring(0, ((URL)object).toString().length() - 13));
                Path path = Paths.get(uRL.toURI());
                VanillaPack.collectResources(hashSet, n, string, path, string2, predicate);
            } else if ("jar".equals(uRI.getScheme())) {
                Path path = FILE_SYSTEMS_BY_PACK_TYPE.get((Object)resourcePackType).getPath("/" + resourcePackType.getDirectoryName(), new String[0]);
                VanillaPack.collectResources(hashSet, n, "minecraft", path, string2, predicate);
            } else {
                LOGGER.error("Unsupported scheme {} trying to list vanilla resources (NYI?)", (Object)uRI);
            }
        } catch (FileNotFoundException | NoSuchFileException iOException) {
        } catch (IOException | URISyntaxException exception) {
            LOGGER.error("Couldn't get a list of all vanilla resources", (Throwable)exception);
        }
        return hashSet;
    }

    private static void collectResources(Collection<ResourceLocation> collection, int n, String string, Path path, String string2, Predicate<String> predicate) throws IOException {
        Path path2 = path.resolve(string);
        try (Stream<Path> stream = Files.walk(path2.resolve(string2), n, new FileVisitOption[0]);){
            stream.filter(arg_0 -> VanillaPack.lambda$collectResources$1(predicate, arg_0)).map(arg_0 -> VanillaPack.lambda$collectResources$2(string, path2, arg_0)).forEach(collection::add);
        }
    }

    @Nullable
    protected InputStream getInputStreamVanilla(ResourcePackType resourcePackType, ResourceLocation resourceLocation) {
        Object object;
        String string = VanillaPack.getPath(resourcePackType, resourceLocation);
        InputStream inputStream = ReflectorForge.getOptiFineResourceStream(string);
        if (inputStream != null) {
            return inputStream;
        }
        if (basePath != null && Files.exists((Path)(object = basePath.resolve(resourcePackType.getDirectoryName() + "/" + resourceLocation.getNamespace() + "/" + resourceLocation.getPath())), new LinkOption[0])) {
            try {
                return Files.newInputStream((Path)object, new OpenOption[0]);
            } catch (IOException iOException) {
                // empty catch block
            }
        }
        try {
            object = VanillaPack.class.getResource(string);
            return VanillaPack.isValid(string, (URL)object) ? (FORGE ? this.getExtraInputStream(resourcePackType, string) : ((URL)object).openStream()) : null;
        } catch (IOException iOException) {
            return VanillaPack.class.getResourceAsStream(string);
        }
    }

    private static String getPath(ResourcePackType resourcePackType, ResourceLocation resourceLocation) {
        return "/" + resourcePackType.getDirectoryName() + "/" + resourceLocation.getNamespace() + "/" + resourceLocation.getPath();
    }

    private static boolean isValid(String string, @Nullable URL uRL) throws IOException {
        return uRL != null && (uRL.getProtocol().equals("jar") || VanillaPack.validatePath(new File(uRL.getFile()), string));
    }

    @Nullable
    protected InputStream getInputStreamVanilla(String string) {
        return FORGE ? this.getExtraInputStream(ResourcePackType.SERVER_DATA, "/" + string) : VanillaPack.class.getResourceAsStream("/" + string);
    }

    @Override
    public boolean resourceExists(ResourcePackType resourcePackType, ResourceLocation resourceLocation) {
        Object object;
        String string = VanillaPack.getPath(resourcePackType, resourceLocation);
        InputStream inputStream = ReflectorForge.getOptiFineResourceStream(string);
        if (inputStream != null) {
            return false;
        }
        if (basePath != null && Files.exists((Path)(object = basePath.resolve(resourcePackType.getDirectoryName() + "/" + resourceLocation.getNamespace() + "/" + resourceLocation.getPath())), new LinkOption[0])) {
            return false;
        }
        try {
            object = VanillaPack.class.getResource(string);
            return VanillaPack.isValid(string, (URL)object);
        } catch (IOException iOException) {
            return true;
        }
    }

    @Override
    public Set<String> getResourceNamespaces(ResourcePackType resourcePackType) {
        return this.resourceNamespaces;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    @Nullable
    public <T> T getMetadata(IMetadataSectionSerializer<T> iMetadataSectionSerializer) throws IOException {
        try (InputStream inputStream = this.getRootResourceStream("pack.mcmeta");){
            T t = ResourcePack.getResourceMetadata(iMetadataSectionSerializer, inputStream);
            return t;
        } catch (FileNotFoundException | RuntimeException exception) {
            return null;
        }
    }

    @Override
    public String getName() {
        return "Default";
    }

    @Override
    public void close() {
    }

    private static boolean validatePath(File file, String string) throws IOException {
        String string2 = file.getPath();
        if (string2.startsWith("file:")) {
            if (ON_WINDOWS) {
                string2 = string2.replace("\\", "/");
            }
            return string2.endsWith(string);
        }
        return FolderPack.validatePath(file, string);
    }

    private InputStream getExtraInputStream(ResourcePackType resourcePackType, String string) {
        try {
            FileSystem fileSystem = FILE_SYSTEMS_BY_PACK_TYPE.get((Object)resourcePackType);
            return fileSystem != null ? Files.newInputStream(fileSystem.getPath(string, new String[0]), new OpenOption[0]) : VanillaPack.class.getResourceAsStream(string);
        } catch (IOException iOException) {
            return VanillaPack.class.getResourceAsStream(string);
        }
    }

    private static ResourceLocation lambda$collectResources$2(String string, Path path, Path path2) {
        return new ResourceLocation(string, path.relativize(path2).toString().replaceAll("\\\\", "/"));
    }

    private static boolean lambda$collectResources$1(Predicate predicate, Path path) {
        return !path.endsWith(".mcmeta") && Files.isRegularFile(path, new LinkOption[0]) && predicate.test(path.getFileName().toString());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void lambda$static$0(HashMap hashMap) {
        Class<VanillaPack> clazz = VanillaPack.class;
        synchronized (VanillaPack.class) {
            for (ResourcePackType resourcePackType : ResourcePackType.values()) {
                URL uRL = VanillaPack.class.getResource("/" + resourcePackType.getDirectoryName() + "/.mcassetsroot");
                try {
                    FileSystem fileSystem;
                    URI uRI = uRL.toURI();
                    if (!"jar".equals(uRI.getScheme())) continue;
                    try {
                        fileSystem = FileSystems.getFileSystem(uRI);
                    } catch (FileSystemNotFoundException fileSystemNotFoundException) {
                        fileSystem = FileSystems.newFileSystem(uRI, Collections.emptyMap());
                    }
                    hashMap.put(resourcePackType, fileSystem);
                } catch (IOException | URISyntaxException exception) {
                    LOGGER.error("Couldn't get a list of all vanilla resources", (Throwable)exception);
                }
            }
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return;
        }
    }

    static {
        LOGGER = LogManager.getLogger();
        FILE_SYSTEMS_BY_PACK_TYPE = Util.make(Maps.newHashMap(), VanillaPack::lambda$static$0);
        ON_WINDOWS = Util.getOSType() == Util.OS.WINDOWS;
        FORGE = Reflector.ForgeHooksClient.exists();
    }
}

