/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.rolling.action;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.apache.logging.log4j.core.appender.rolling.action.PathSorter;
import org.apache.logging.log4j.core.appender.rolling.action.PathWithAttributes;

public class SortingVisitor
extends SimpleFileVisitor<Path> {
    private final PathSorter sorter;
    private final List<PathWithAttributes> collected = new ArrayList<PathWithAttributes>();

    public SortingVisitor(PathSorter pathSorter) {
        this.sorter = Objects.requireNonNull(pathSorter, "sorter");
    }

    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
        this.collected.add(new PathWithAttributes(path, basicFileAttributes));
        return FileVisitResult.CONTINUE;
    }

    public List<PathWithAttributes> getSortedPaths() {
        Collections.sort(this.collected, this.sorter);
        return this.collected;
    }

    @Override
    public FileVisitResult visitFile(Object object, BasicFileAttributes basicFileAttributes) throws IOException {
        return this.visitFile((Path)object, basicFileAttributes);
    }
}

