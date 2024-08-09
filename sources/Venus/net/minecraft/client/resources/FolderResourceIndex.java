/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.resources;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.client.resources.ResourceIndex;
import net.minecraft.util.ResourceLocation;

public class FolderResourceIndex
extends ResourceIndex {
    private final File baseDir;

    public FolderResourceIndex(File file) {
        this.baseDir = file;
    }

    @Override
    public File getFile(ResourceLocation resourceLocation) {
        return new File(this.baseDir, resourceLocation.toString().replace(':', '/'));
    }

    @Override
    public File getFile(String string) {
        return new File(this.baseDir, string);
    }

    @Override
    public Collection<ResourceLocation> getFiles(String string, String string2, int n, Predicate<String> predicate) {
        block10: {
            Collection collection;
            block9: {
                Path path = this.baseDir.toPath().resolve(string2);
                Stream<Path> stream = Files.walk(path.resolve(string), n, new FileVisitOption[0]);
                try {
                    collection = stream.filter(FolderResourceIndex::lambda$getFiles$0).filter(FolderResourceIndex::lambda$getFiles$1).filter(arg_0 -> FolderResourceIndex.lambda$getFiles$2(predicate, arg_0)).map(arg_0 -> FolderResourceIndex.lambda$getFiles$3(string2, path, arg_0)).collect(Collectors.toList());
                    if (stream == null) break block9;
                } catch (Throwable throwable) {
                    try {
                        if (stream != null) {
                            try {
                                stream.close();
                            } catch (Throwable throwable2) {
                                throwable.addSuppressed(throwable2);
                            }
                        }
                        throw throwable;
                    } catch (NoSuchFileException noSuchFileException) {
                        break block10;
                    } catch (IOException iOException) {
                        LOGGER.warn("Unable to getFiles on {}", (Object)string, (Object)iOException);
                    }
                }
                stream.close();
            }
            return collection;
        }
        return Collections.emptyList();
    }

    private static ResourceLocation lambda$getFiles$3(String string, Path path, Path path2) {
        return new ResourceLocation(string, path.relativize(path2).toString().replaceAll("\\\\", "/"));
    }

    private static boolean lambda$getFiles$2(Predicate predicate, Path path) {
        return predicate.test(path.getFileName().toString());
    }

    private static boolean lambda$getFiles$1(Path path) {
        return !path.endsWith(".mcmeta");
    }

    private static boolean lambda$getFiles$0(Path path) {
        return Files.isRegularFile(path, new LinkOption[0]);
    }
}

