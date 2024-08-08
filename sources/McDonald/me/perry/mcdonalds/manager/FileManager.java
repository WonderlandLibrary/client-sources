// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.manager;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.io.File;
import java.nio.file.attribute.FileAttribute;
import java.util.function.Function;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.List;
import java.io.IOException;
import java.nio.file.StandardOpenOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.nio.file.Paths;
import java.util.Iterator;
import me.perry.mcdonalds.features.modules.Module;
import me.perry.mcdonalds.McDonalds;
import java.nio.file.Path;
import me.perry.mcdonalds.features.Feature;

public class FileManager extends Feature
{
    private final Path base;
    private final Path config;
    
    public FileManager() {
        this.base = this.getMkDirectory(this.getRoot(), "McDonalds");
        this.config = this.getMkDirectory(this.base, "config");
        this.getMkDirectory(this.base, "pvp");
        for (final Module.Category category : McDonalds.moduleManager.getCategories()) {
            this.getMkDirectory(this.config, category.getName());
        }
    }
    
    public static boolean appendTextFile(final String data, final String file) {
        try {
            final Path path = Paths.get(file, new String[0]);
            Files.write(path, Collections.singletonList(data), StandardCharsets.UTF_8, Files.exists(path, new LinkOption[0]) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
        }
        catch (IOException e) {
            System.out.println("WARNING: Unable to write file: " + file);
            return false;
        }
        return true;
    }
    
    public static List<String> readTextFileAllLines(final String file) {
        try {
            final Path path = Paths.get(file, new String[0]);
            return Files.readAllLines(path, StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            System.out.println("WARNING: Unable to read file, creating new file: " + file);
            appendTextFile("", file);
            return Collections.emptyList();
        }
    }
    
    private String[] expandPath(final String fullPath) {
        return fullPath.split(":?\\\\\\\\|\\/");
    }
    
    private Stream<String> expandPaths(final String... paths) {
        return Arrays.stream(paths).map((Function<? super String, ?>)this::expandPath).flatMap((Function<? super Object, ? extends Stream<? extends String>>)Arrays::stream);
    }
    
    private Path lookupPath(final Path root, final String... paths) {
        return Paths.get(root.toString(), paths);
    }
    
    private Path getRoot() {
        return Paths.get("", new String[0]);
    }
    
    private void createDirectory(final Path dir) {
        try {
            if (!Files.isDirectory(dir, new LinkOption[0])) {
                if (Files.exists(dir, new LinkOption[0])) {
                    Files.delete(dir);
                }
                Files.createDirectories(dir, (FileAttribute<?>[])new FileAttribute[0]);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private Path getMkDirectory(final Path parent, final String... paths) {
        if (paths.length < 1) {
            return parent;
        }
        final Path dir = this.lookupPath(parent, paths);
        this.createDirectory(dir);
        return dir;
    }
    
    public Path getBasePath() {
        return this.base;
    }
    
    public Path getBaseResolve(final String... paths) {
        final String[] names = this.expandPaths(paths).toArray(String[]::new);
        if (names.length < 1) {
            throw new IllegalArgumentException("missing path");
        }
        return this.lookupPath(this.getBasePath(), names);
    }
    
    public Path getMkBaseResolve(final String... paths) {
        final Path path = this.getBaseResolve(paths);
        this.createDirectory(path.getParent());
        return path;
    }
    
    public Path getConfig() {
        return this.getBasePath().resolve("config");
    }
    
    public Path getCache() {
        return this.getBasePath().resolve("cache");
    }
    
    public Path getMkBaseDirectory(final String... names) {
        return this.getMkDirectory(this.getBasePath(), this.expandPaths(names).collect(Collectors.joining(File.separator)));
    }
    
    public Path getMkConfigDirectory(final String... names) {
        return this.getMkDirectory(this.getConfig(), this.expandPaths(names).collect(Collectors.joining(File.separator)));
    }
}
