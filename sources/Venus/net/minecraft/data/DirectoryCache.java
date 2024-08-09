/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DirectoryCache {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Path outputFolder;
    private final Path cacheFile;
    private int hits;
    private final Map<Path, String> staleFiles = Maps.newHashMap();
    private final Map<Path, String> createdFiles = Maps.newHashMap();
    private final Set<Path> protectedPaths = Sets.newHashSet();

    public DirectoryCache(Path path, String string) throws IOException {
        this.outputFolder = path;
        Path path2 = path.resolve(".cache");
        Files.createDirectories(path2, new FileAttribute[0]);
        this.cacheFile = path2.resolve(string);
        this.getFiles().forEach(this::lambda$new$0);
        if (Files.isReadable(this.cacheFile)) {
            IOUtils.readLines(Files.newInputStream(this.cacheFile, new OpenOption[0]), Charsets.UTF_8).forEach(arg_0 -> this.lambda$new$1(path, arg_0));
        }
    }

    public void writeCache() throws IOException {
        BufferedWriter bufferedWriter;
        this.deleteStale();
        try {
            bufferedWriter = Files.newBufferedWriter(this.cacheFile, new OpenOption[0]);
        } catch (IOException iOException) {
            LOGGER.warn("Unable write cachefile {}: {}", (Object)this.cacheFile, (Object)iOException.toString());
            return;
        }
        IOUtils.writeLines((Collection)this.createdFiles.entrySet().stream().map(this::lambda$writeCache$2).collect(Collectors.toList()), System.lineSeparator(), bufferedWriter);
        ((Writer)bufferedWriter).close();
        LOGGER.debug("Caching: cache hits: {}, created: {} removed: {}", (Object)this.hits, (Object)(this.createdFiles.size() - this.hits), (Object)this.staleFiles.size());
    }

    @Nullable
    public String getPreviousHash(Path path) {
        return this.staleFiles.get(path);
    }

    public void recordHash(Path path, String string) {
        this.createdFiles.put(path, string);
        if (Objects.equals(this.staleFiles.remove(path), string)) {
            ++this.hits;
        }
    }

    public boolean isStale(Path path) {
        return this.staleFiles.containsKey(path);
    }

    public void addProtectedPath(Path path) {
        this.protectedPaths.add(path);
    }

    private void deleteStale() throws IOException {
        this.getFiles().forEach(this::lambda$deleteStale$3);
    }

    private Stream<Path> getFiles() throws IOException {
        return Files.walk(this.outputFolder, new FileVisitOption[0]).filter(this::lambda$getFiles$4);
    }

    private boolean lambda$getFiles$4(Path path) {
        return !Objects.equals(this.cacheFile, path) && !Files.isDirectory(path, new LinkOption[0]);
    }

    private void lambda$deleteStale$3(Path path) {
        if (this.isStale(path) && !this.protectedPaths.contains(path)) {
            try {
                Files.delete(path);
            } catch (IOException iOException) {
                LOGGER.debug("Unable to delete: {} ({})", (Object)path, (Object)iOException.toString());
            }
        }
    }

    private String lambda$writeCache$2(Map.Entry entry) {
        return (String)entry.getValue() + " " + this.outputFolder.relativize((Path)entry.getKey());
    }

    private void lambda$new$1(Path path, String string) {
        int n = string.indexOf(32);
        this.staleFiles.put(path.resolve(string.substring(n + 1)), string.substring(0, n));
    }

    private void lambda$new$0(Path path) {
        String string = this.staleFiles.put(path, "");
    }
}

