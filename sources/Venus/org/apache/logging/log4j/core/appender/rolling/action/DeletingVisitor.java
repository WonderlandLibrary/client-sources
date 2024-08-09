/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.rolling.action;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Objects;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.appender.rolling.action.PathCondition;
import org.apache.logging.log4j.status.StatusLogger;

public class DeletingVisitor
extends SimpleFileVisitor<Path> {
    private static final Logger LOGGER = StatusLogger.getLogger();
    private final Path basePath;
    private final boolean testMode;
    private final List<? extends PathCondition> pathConditions;

    public DeletingVisitor(Path path, List<? extends PathCondition> list, boolean bl) {
        this.testMode = bl;
        this.basePath = Objects.requireNonNull(path, "basePath");
        this.pathConditions = Objects.requireNonNull(list, "pathConditions");
        for (PathCondition pathCondition : list) {
            pathCondition.beforeFileTreeWalk();
        }
    }

    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
        for (PathCondition pathCondition : this.pathConditions) {
            Path path2;
            if (pathCondition.accept(this.basePath, path2 = this.basePath.relativize(path), basicFileAttributes)) continue;
            LOGGER.trace("Not deleting base={}, relative={}", (Object)this.basePath, (Object)path2);
            return FileVisitResult.CONTINUE;
        }
        if (this.isTestMode()) {
            LOGGER.info("Deleting {} (TEST MODE: file not actually deleted)", (Object)path);
        } else {
            this.delete(path);
        }
        return FileVisitResult.CONTINUE;
    }

    protected void delete(Path path) throws IOException {
        LOGGER.trace("Deleting {}", (Object)path);
        Files.deleteIfExists(path);
    }

    public boolean isTestMode() {
        return this.testMode;
    }

    @Override
    public FileVisitResult visitFile(Object object, BasicFileAttributes basicFileAttributes) throws IOException {
        return this.visitFile((Path)object, basicFileAttributes);
    }
}

