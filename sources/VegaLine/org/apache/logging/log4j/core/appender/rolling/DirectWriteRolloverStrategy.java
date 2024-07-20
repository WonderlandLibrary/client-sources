/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.rolling;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.core.appender.rolling.AbstractRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.DirectFileRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.FileExtension;
import org.apache.logging.log4j.core.appender.rolling.RollingFileManager;
import org.apache.logging.log4j.core.appender.rolling.RolloverDescription;
import org.apache.logging.log4j.core.appender.rolling.RolloverDescriptionImpl;
import org.apache.logging.log4j.core.appender.rolling.action.Action;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.lookup.StrSubstitutor;
import org.apache.logging.log4j.core.util.Integers;

@Plugin(name="DirectWriteRolloverStrategy", category="Core", printObject=true)
public class DirectWriteRolloverStrategy
extends AbstractRolloverStrategy
implements DirectFileRolloverStrategy {
    private static final int DEFAULT_MAX_FILES = 7;
    private final int maxFiles;
    private final int compressionLevel;
    private final List<Action> customActions;
    private final boolean stopCustomActionsOnError;
    private volatile String currentFileName;
    private int nextIndex = -1;

    @PluginFactory
    public static DirectWriteRolloverStrategy createStrategy(@PluginAttribute(value="maxFiles") String maxFiles, @PluginAttribute(value="compressionLevel") String compressionLevelStr, @PluginElement(value="Actions") Action[] customActions, @PluginAttribute(value="stopCustomActionsOnError", defaultBoolean=true) boolean stopCustomActionsOnError, @PluginConfiguration Configuration config) {
        int maxIndex = Integer.MAX_VALUE;
        if (maxFiles != null) {
            maxIndex = Integer.parseInt(maxFiles);
            if (maxIndex < 0) {
                maxIndex = Integer.MAX_VALUE;
            } else if (maxIndex < 2) {
                LOGGER.error("Maximum files too small. Limited to 7");
                maxIndex = 7;
            }
        }
        int compressionLevel = Integers.parseInt(compressionLevelStr, -1);
        return new DirectWriteRolloverStrategy(maxIndex, compressionLevel, config.getStrSubstitutor(), customActions, stopCustomActionsOnError);
    }

    protected DirectWriteRolloverStrategy(int maxFiles, int compressionLevel, StrSubstitutor strSubstitutor, Action[] customActions, boolean stopCustomActionsOnError) {
        super(strSubstitutor);
        this.maxFiles = maxFiles;
        this.compressionLevel = compressionLevel;
        this.stopCustomActionsOnError = stopCustomActionsOnError;
        this.customActions = customActions == null ? Collections.emptyList() : Arrays.asList(customActions);
    }

    public int getCompressionLevel() {
        return this.compressionLevel;
    }

    public List<Action> getCustomActions() {
        return this.customActions;
    }

    public int getMaxFiles() {
        return this.maxFiles;
    }

    public boolean isStopCustomActionsOnError() {
        return this.stopCustomActionsOnError;
    }

    private int purge(RollingFileManager manager) {
        SortedMap<Integer, Path> eligibleFiles = this.getEligibleFiles(manager);
        LOGGER.debug("Found {} eligible files, max is  {}", (Object)eligibleFiles.size(), (Object)this.maxFiles);
        while (eligibleFiles.size() >= this.maxFiles) {
            try {
                Integer key = eligibleFiles.firstKey();
                Files.delete((Path)eligibleFiles.get(key));
                eligibleFiles.remove(key);
            } catch (IOException ioe) {
                LOGGER.error("Unable to delete {}", (Object)eligibleFiles.firstKey(), (Object)ioe);
                break;
            }
        }
        return eligibleFiles.size() > 0 ? eligibleFiles.lastKey() : 1;
    }

    @Override
    public String getCurrentFileName(RollingFileManager manager) {
        if (this.currentFileName == null) {
            String name;
            SortedMap<Integer, Path> eligibleFiles = this.getEligibleFiles(manager);
            int fileIndex = eligibleFiles.size() > 0 ? (this.nextIndex > 0 ? this.nextIndex : eligibleFiles.size()) : 1;
            StringBuilder buf = new StringBuilder(255);
            manager.getPatternProcessor().formatFileName(this.strSubstitutor, buf, true, (Object)fileIndex);
            int suffixLength = this.suffixLength(buf.toString());
            this.currentFileName = name = suffixLength > 0 ? buf.substring(0, buf.length() - suffixLength) : buf.toString();
        }
        return this.currentFileName;
    }

    @Override
    public RolloverDescription rollover(RollingFileManager manager) throws SecurityException {
        LOGGER.debug("Rolling " + this.currentFileName);
        if (this.maxFiles < 0) {
            return null;
        }
        long startNanos = System.nanoTime();
        int fileIndex = this.purge(manager);
        if (LOGGER.isTraceEnabled()) {
            double durationMillis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanos);
            LOGGER.trace("DirectWriteRolloverStrategy.purge() took {} milliseconds", (Object)durationMillis);
        }
        Action compressAction = null;
        String sourceName = this.currentFileName;
        this.currentFileName = null;
        this.nextIndex = fileIndex + 1;
        FileExtension fileExtension = manager.getFileExtension();
        if (fileExtension != null) {
            compressAction = fileExtension.createCompressAction(sourceName, sourceName + fileExtension.getExtension(), true, this.compressionLevel);
        }
        Action asyncAction = this.merge(compressAction, this.customActions, this.stopCustomActionsOnError);
        return new RolloverDescriptionImpl(sourceName, false, null, asyncAction);
    }

    public String toString() {
        return "DirectWriteRolloverStrategy(maxFiles=" + this.maxFiles + ')';
    }
}

