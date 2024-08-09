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
    public static DirectWriteRolloverStrategy createStrategy(@PluginAttribute(value="maxFiles") String string, @PluginAttribute(value="compressionLevel") String string2, @PluginElement(value="Actions") Action[] actionArray, @PluginAttribute(value="stopCustomActionsOnError", defaultBoolean=true) boolean bl, @PluginConfiguration Configuration configuration) {
        int n = Integer.MAX_VALUE;
        if (string != null) {
            n = Integer.parseInt(string);
            if (n < 0) {
                n = Integer.MAX_VALUE;
            } else if (n < 2) {
                LOGGER.error("Maximum files too small. Limited to 7");
                n = 7;
            }
        }
        int n2 = Integers.parseInt(string2, -1);
        return new DirectWriteRolloverStrategy(n, n2, configuration.getStrSubstitutor(), actionArray, bl);
    }

    protected DirectWriteRolloverStrategy(int n, int n2, StrSubstitutor strSubstitutor, Action[] actionArray, boolean bl) {
        super(strSubstitutor);
        this.maxFiles = n;
        this.compressionLevel = n2;
        this.stopCustomActionsOnError = bl;
        this.customActions = actionArray == null ? Collections.emptyList() : Arrays.asList(actionArray);
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

    private int purge(RollingFileManager rollingFileManager) {
        SortedMap<Integer, Path> sortedMap = this.getEligibleFiles(rollingFileManager);
        LOGGER.debug("Found {} eligible files, max is  {}", (Object)sortedMap.size(), (Object)this.maxFiles);
        while (sortedMap.size() >= this.maxFiles) {
            try {
                Integer n = sortedMap.firstKey();
                Files.delete((Path)sortedMap.get(n));
                sortedMap.remove(n);
            } catch (IOException iOException) {
                LOGGER.error("Unable to delete {}", (Object)sortedMap.firstKey(), (Object)iOException);
                break;
            }
        }
        return sortedMap.size() > 0 ? sortedMap.lastKey() : 1;
    }

    @Override
    public String getCurrentFileName(RollingFileManager rollingFileManager) {
        if (this.currentFileName == null) {
            String string;
            SortedMap<Integer, Path> sortedMap = this.getEligibleFiles(rollingFileManager);
            int n = sortedMap.size() > 0 ? (this.nextIndex > 0 ? this.nextIndex : sortedMap.size()) : 1;
            StringBuilder stringBuilder = new StringBuilder(255);
            rollingFileManager.getPatternProcessor().formatFileName(this.strSubstitutor, stringBuilder, true, (Object)n);
            int n2 = this.suffixLength(stringBuilder.toString());
            this.currentFileName = string = n2 > 0 ? stringBuilder.substring(0, stringBuilder.length() - n2) : stringBuilder.toString();
        }
        return this.currentFileName;
    }

    @Override
    public RolloverDescription rollover(RollingFileManager rollingFileManager) throws SecurityException {
        LOGGER.debug("Rolling " + this.currentFileName);
        if (this.maxFiles < 0) {
            return null;
        }
        long l = System.nanoTime();
        int n = this.purge(rollingFileManager);
        if (LOGGER.isTraceEnabled()) {
            double d = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - l);
            LOGGER.trace("DirectWriteRolloverStrategy.purge() took {} milliseconds", (Object)d);
        }
        Action action = null;
        String string = this.currentFileName;
        this.currentFileName = null;
        this.nextIndex = n + 1;
        FileExtension fileExtension = rollingFileManager.getFileExtension();
        if (fileExtension != null) {
            action = fileExtension.createCompressAction(string, string + fileExtension.getExtension(), true, this.compressionLevel);
        }
        Action action2 = this.merge(action, this.customActions, this.stopCustomActionsOnError);
        return new RolloverDescriptionImpl(string, false, null, action2);
    }

    public String toString() {
        return "DirectWriteRolloverStrategy(maxFiles=" + this.maxFiles + ')';
    }
}

