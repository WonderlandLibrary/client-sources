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

    public DeletingVisitor(Path basePath, List<? extends PathCondition> pathConditions, boolean testMode) {
        this.testMode = testMode;
        this.basePath = Objects.requireNonNull(basePath, "basePath");
        this.pathConditions = Objects.requireNonNull(pathConditions, "pathConditions");
        for (PathCondition pathCondition : pathConditions) {
            pathCondition.beforeFileTreeWalk();
        }
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        for (PathCondition pathCondition : this.pathConditions) {
            Path relative;
            if (pathCondition.accept(this.basePath, relative = this.basePath.relativize(file), attrs)) continue;
            LOGGER.trace("Not deleting base={}, relative={}", (Object)this.basePath, (Object)relative);
            return FileVisitResult.CONTINUE;
        }
        if (this.isTestMode()) {
            LOGGER.info("Deleting {} (TEST MODE: file not actually deleted)", (Object)file);
        } else {
            this.delete(file);
        }
        return FileVisitResult.CONTINUE;
    }

    protected void delete(Path file) throws IOException {
        LOGGER.trace("Deleting {}", (Object)file);
        Files.deleteIfExists(file);
    }

    public boolean isTestMode() {
        return this.testMode;
    }
}

