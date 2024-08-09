/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.rolling;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.core.appender.rolling.AbstractRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.FileExtension;
import org.apache.logging.log4j.core.appender.rolling.RollingFileManager;
import org.apache.logging.log4j.core.appender.rolling.RolloverDescription;
import org.apache.logging.log4j.core.appender.rolling.RolloverDescriptionImpl;
import org.apache.logging.log4j.core.appender.rolling.action.Action;
import org.apache.logging.log4j.core.appender.rolling.action.FileRenameAction;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.lookup.StrSubstitutor;
import org.apache.logging.log4j.core.util.Integers;

@Plugin(name="DefaultRolloverStrategy", category="Core", printObject=true)
public class DefaultRolloverStrategy
extends AbstractRolloverStrategy {
    private static final int MIN_WINDOW_SIZE = 1;
    private static final int DEFAULT_WINDOW_SIZE = 7;
    private final int maxIndex;
    private final int minIndex;
    private final boolean useMax;
    private final int compressionLevel;
    private final List<Action> customActions;
    private final boolean stopCustomActionsOnError;

    @PluginFactory
    public static DefaultRolloverStrategy createStrategy(@PluginAttribute(value="max") String string, @PluginAttribute(value="min") String string2, @PluginAttribute(value="fileIndex") String string3, @PluginAttribute(value="compressionLevel") String string4, @PluginElement(value="Actions") Action[] actionArray, @PluginAttribute(value="stopCustomActionsOnError", defaultBoolean=true) boolean bl, @PluginConfiguration Configuration configuration) {
        boolean bl2;
        int n;
        int n2;
        if (string3 != null && string3.equalsIgnoreCase("nomax")) {
            n2 = Integer.MIN_VALUE;
            n = Integer.MAX_VALUE;
            bl2 = false;
        } else {
            bl2 = string3 == null ? true : string3.equalsIgnoreCase("max");
            n2 = 1;
            if (string2 != null && (n2 = Integer.parseInt(string2)) < 1) {
                LOGGER.error("Minimum window size too small. Limited to 1");
                n2 = 1;
            }
            n = 7;
            if (string != null && (n = Integer.parseInt(string)) < n2) {
                n = n2 < 7 ? 7 : n2;
                LOGGER.error("Maximum window size must be greater than the minimum windows size. Set to " + n);
            }
        }
        int n3 = Integers.parseInt(string4, -1);
        return new DefaultRolloverStrategy(n2, n, bl2, n3, configuration.getStrSubstitutor(), actionArray, bl);
    }

    protected DefaultRolloverStrategy(int n, int n2, boolean bl, int n3, StrSubstitutor strSubstitutor, Action[] actionArray, boolean bl2) {
        super(strSubstitutor);
        this.minIndex = n;
        this.maxIndex = n2;
        this.useMax = bl;
        this.compressionLevel = n3;
        this.stopCustomActionsOnError = bl2;
        this.customActions = actionArray == null ? Collections.emptyList() : Arrays.asList(actionArray);
    }

    public int getCompressionLevel() {
        return this.compressionLevel;
    }

    public List<Action> getCustomActions() {
        return this.customActions;
    }

    public int getMaxIndex() {
        return this.maxIndex;
    }

    public int getMinIndex() {
        return this.minIndex;
    }

    public boolean isStopCustomActionsOnError() {
        return this.stopCustomActionsOnError;
    }

    public boolean isUseMax() {
        return this.useMax;
    }

    private int purge(int n, int n2, RollingFileManager rollingFileManager) {
        return this.useMax ? this.purgeAscending(n, n2, rollingFileManager) : this.purgeDescending(n, n2, rollingFileManager);
    }

    private int purgeAscending(int n, int n2, RollingFileManager rollingFileManager) {
        Serializable serializable;
        SortedMap<Integer, Path> sortedMap = this.getEligibleFiles(rollingFileManager);
        int n3 = n2 - n + 1;
        boolean bl = false;
        while (sortedMap.size() >= n3) {
            try {
                LOGGER.debug("Eligible files: {}", (Object)sortedMap);
                serializable = sortedMap.firstKey();
                LOGGER.debug("Deleting {}", (Object)((Path)sortedMap.get(serializable)).toFile().getAbsolutePath());
                Files.delete((Path)sortedMap.get(serializable));
                sortedMap.remove(serializable);
                bl = true;
            } catch (IOException iOException) {
                LOGGER.error("Unable to delete {}, {}", (Object)sortedMap.firstKey(), (Object)iOException.getMessage(), (Object)iOException);
                break;
            }
        }
        serializable = new StringBuilder();
        if (bl) {
            for (Map.Entry<Integer, Path> entry : sortedMap.entrySet()) {
                ((StringBuilder)serializable).setLength(0);
                rollingFileManager.getPatternProcessor().formatFileName(this.strSubstitutor, (StringBuilder)serializable, (Object)(entry.getKey() - 1));
                String string = entry.getValue().toFile().getName();
                String string2 = ((StringBuilder)serializable).toString();
                int n4 = this.suffixLength(string2);
                if (n4 > 0 && this.suffixLength(string) == 0) {
                    string2 = string2.substring(0, string2.length() - n4);
                }
                FileRenameAction fileRenameAction = new FileRenameAction(entry.getValue().toFile(), new File(string2), true);
                try {
                    LOGGER.debug("DefaultRolloverStrategy.purgeAscending executing {}", (Object)fileRenameAction);
                    if (fileRenameAction.execute()) continue;
                    return -1;
                } catch (Exception exception) {
                    LOGGER.warn("Exception during purge in RollingFileAppender", (Throwable)exception);
                    return 1;
                }
            }
        }
        return sortedMap.size() > 0 ? (sortedMap.lastKey() < n2 ? sortedMap.lastKey() + 1 : n2) : n;
    }

    private int purgeDescending(int n, int n2, RollingFileManager rollingFileManager) {
        Serializable serializable;
        SortedMap<Integer, Path> sortedMap = this.getEligibleFiles(rollingFileManager, true);
        int n3 = n2 - n + 1;
        while (sortedMap.size() >= n3) {
            try {
                serializable = sortedMap.firstKey();
                Files.delete((Path)sortedMap.get(serializable));
                sortedMap.remove(serializable);
            } catch (IOException iOException) {
                LOGGER.error("Unable to delete {}, {}", (Object)sortedMap.firstKey(), (Object)iOException.getMessage(), (Object)iOException);
                break;
            }
        }
        serializable = new StringBuilder();
        for (Map.Entry<Integer, Path> entry : sortedMap.entrySet()) {
            ((StringBuilder)serializable).setLength(0);
            rollingFileManager.getPatternProcessor().formatFileName(this.strSubstitutor, (StringBuilder)serializable, (Object)(entry.getKey() + 1));
            String string = entry.getValue().toFile().getName();
            String string2 = ((StringBuilder)serializable).toString();
            int n4 = this.suffixLength(string2);
            if (n4 > 0 && this.suffixLength(string) == 0) {
                string2 = string2.substring(0, string2.length() - n4);
            }
            FileRenameAction fileRenameAction = new FileRenameAction(entry.getValue().toFile(), new File(string2), true);
            try {
                LOGGER.debug("DefaultRolloverStrategy.purgeDescending executing {}", (Object)fileRenameAction);
                if (fileRenameAction.execute()) continue;
                return -1;
            } catch (Exception exception) {
                LOGGER.warn("Exception during purge in RollingFileAppender", (Throwable)exception);
                return 1;
            }
        }
        return n;
    }

    @Override
    public RolloverDescription rollover(RollingFileManager rollingFileManager) throws SecurityException {
        String string;
        int n;
        SortedMap<Integer, Path> sortedMap;
        if (this.minIndex == Integer.MIN_VALUE) {
            sortedMap = this.getEligibleFiles(rollingFileManager);
            n = sortedMap.size() > 0 ? sortedMap.lastKey() + 1 : 1;
        } else {
            if (this.maxIndex < 0) {
                return null;
            }
            long l = System.nanoTime();
            n = this.purge(this.minIndex, this.maxIndex, rollingFileManager);
            if (n < 0) {
                return null;
            }
            if (LOGGER.isTraceEnabled()) {
                double d = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - l);
                LOGGER.trace("DefaultRolloverStrategy.purge() took {} milliseconds", (Object)d);
            }
        }
        sortedMap = new StringBuilder(255);
        rollingFileManager.getPatternProcessor().formatFileName(this.strSubstitutor, (StringBuilder)((Object)sortedMap), (Object)n);
        String string2 = rollingFileManager.getFileName();
        String string3 = string = ((StringBuilder)((Object)sortedMap)).toString();
        Action action = null;
        FileExtension fileExtension = rollingFileManager.getFileExtension();
        if (fileExtension != null) {
            string = string.substring(0, string.length() - fileExtension.length());
            action = fileExtension.createCompressAction(string, string3, true, this.compressionLevel);
        }
        if (string2.equals(string)) {
            LOGGER.warn("Attempt to rename file {} to itself will be ignored", (Object)string2);
            return new RolloverDescriptionImpl(string2, false, null, null);
        }
        FileRenameAction fileRenameAction = new FileRenameAction(new File(string2), new File(string), rollingFileManager.isRenameEmptyFiles());
        Action action2 = this.merge(action, this.customActions, this.stopCustomActionsOnError);
        return new RolloverDescriptionImpl(string2, false, fileRenameAction, action2);
    }

    public String toString() {
        return "DefaultRolloverStrategy(min=" + this.minIndex + ", max=" + this.maxIndex + ", useMax=" + this.useMax + ")";
    }
}

