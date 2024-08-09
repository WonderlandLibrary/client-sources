/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.rolling.action;

import java.io.IOException;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import org.apache.logging.log4j.core.appender.rolling.action.AbstractPathAction;
import org.apache.logging.log4j.core.appender.rolling.action.DeletingVisitor;
import org.apache.logging.log4j.core.appender.rolling.action.PathCondition;
import org.apache.logging.log4j.core.appender.rolling.action.PathSortByModificationTime;
import org.apache.logging.log4j.core.appender.rolling.action.PathSorter;
import org.apache.logging.log4j.core.appender.rolling.action.PathWithAttributes;
import org.apache.logging.log4j.core.appender.rolling.action.ScriptCondition;
import org.apache.logging.log4j.core.appender.rolling.action.SortingVisitor;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.lookup.StrSubstitutor;

@Plugin(name="Delete", category="Core", printObject=true)
public class DeleteAction
extends AbstractPathAction {
    private final PathSorter pathSorter;
    private final boolean testMode;
    private final ScriptCondition scriptCondition;

    DeleteAction(String string, boolean bl, int n, boolean bl2, PathSorter pathSorter, PathCondition[] pathConditionArray, ScriptCondition scriptCondition, StrSubstitutor strSubstitutor) {
        super(string, bl, n, pathConditionArray, strSubstitutor);
        this.testMode = bl2;
        this.pathSorter = Objects.requireNonNull(pathSorter, "sorter");
        this.scriptCondition = scriptCondition;
        if (scriptCondition == null && (pathConditionArray == null || pathConditionArray.length == 0)) {
            LOGGER.error("Missing Delete conditions: unconditional Delete not supported");
            throw new IllegalArgumentException("Unconditional Delete not supported");
        }
    }

    @Override
    public boolean execute() throws IOException {
        return this.scriptCondition != null ? this.executeScript() : super.execute();
    }

    private boolean executeScript() throws IOException {
        List<PathWithAttributes> list = this.callScript();
        if (list == null) {
            LOGGER.trace("Script returned null list (no files to delete)");
            return false;
        }
        this.deleteSelectedFiles(list);
        return false;
    }

    private List<PathWithAttributes> callScript() throws IOException {
        List<PathWithAttributes> list = this.getSortedPaths();
        this.trace("Sorted paths:", list);
        List<PathWithAttributes> list2 = this.scriptCondition.selectFilesToDelete(this.getBasePath(), list);
        return list2;
    }

    private void deleteSelectedFiles(List<PathWithAttributes> list) throws IOException {
        this.trace("Paths the script selected for deletion:", list);
        for (PathWithAttributes pathWithAttributes : list) {
            Path path;
            Path path2 = path = pathWithAttributes == null ? null : pathWithAttributes.getPath();
            if (this.isTestMode()) {
                LOGGER.info("Deleting {} (TEST MODE: file not actually deleted)", (Object)path);
                continue;
            }
            this.delete(path);
        }
    }

    protected void delete(Path path) throws IOException {
        LOGGER.trace("Deleting {}", (Object)path);
        Files.deleteIfExists(path);
    }

    @Override
    public boolean execute(FileVisitor<Path> fileVisitor) throws IOException {
        List<PathWithAttributes> list = this.getSortedPaths();
        this.trace("Sorted paths:", list);
        for (PathWithAttributes pathWithAttributes : list) {
            try {
                fileVisitor.visitFile(pathWithAttributes.getPath(), pathWithAttributes.getAttributes());
            } catch (IOException iOException) {
                LOGGER.error("Error in post-rollover Delete when visiting {}", (Object)pathWithAttributes.getPath(), (Object)iOException);
                fileVisitor.visitFileFailed(pathWithAttributes.getPath(), iOException);
            }
        }
        return false;
    }

    private void trace(String string, List<PathWithAttributes> list) {
        LOGGER.trace(string);
        for (PathWithAttributes pathWithAttributes : list) {
            LOGGER.trace(pathWithAttributes);
        }
    }

    List<PathWithAttributes> getSortedPaths() throws IOException {
        SortingVisitor sortingVisitor = new SortingVisitor(this.pathSorter);
        super.execute(sortingVisitor);
        List<PathWithAttributes> list = sortingVisitor.getSortedPaths();
        return list;
    }

    public boolean isTestMode() {
        return this.testMode;
    }

    @Override
    protected FileVisitor<Path> createFileVisitor(Path path, List<PathCondition> list) {
        return new DeletingVisitor(path, list, this.testMode);
    }

    @PluginFactory
    public static DeleteAction createDeleteAction(@PluginAttribute(value="basePath") String string, @PluginAttribute(value="followLinks") boolean bl, @PluginAttribute(value="maxDepth", defaultInt=1) int n, @PluginAttribute(value="testMode") boolean bl2, @PluginElement(value="PathSorter") PathSorter pathSorter, @PluginElement(value="PathConditions") PathCondition[] pathConditionArray, @PluginElement(value="ScriptCondition") ScriptCondition scriptCondition, @PluginConfiguration Configuration configuration) {
        PathSorter pathSorter2 = pathSorter == null ? new PathSortByModificationTime(true) : pathSorter;
        return new DeleteAction(string, bl, n, bl2, pathSorter2, pathConditionArray, scriptCondition, configuration.getStrSubstitutor());
    }
}

