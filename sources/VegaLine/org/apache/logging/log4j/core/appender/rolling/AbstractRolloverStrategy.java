/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.rolling;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LoggingException;
import org.apache.logging.log4j.core.appender.rolling.FileExtension;
import org.apache.logging.log4j.core.appender.rolling.RollingFileManager;
import org.apache.logging.log4j.core.appender.rolling.RolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.action.Action;
import org.apache.logging.log4j.core.appender.rolling.action.CompositeAction;
import org.apache.logging.log4j.core.lookup.StrSubstitutor;
import org.apache.logging.log4j.core.pattern.NotANumber;
import org.apache.logging.log4j.status.StatusLogger;

public abstract class AbstractRolloverStrategy
implements RolloverStrategy {
    protected static final Logger LOGGER = StatusLogger.getLogger();
    protected final StrSubstitutor strSubstitutor;

    protected AbstractRolloverStrategy(StrSubstitutor strSubstitutor) {
        this.strSubstitutor = strSubstitutor;
    }

    public StrSubstitutor getStrSubstitutor() {
        return this.strSubstitutor;
    }

    protected Action merge(Action compressAction, List<Action> custom, boolean stopOnError) {
        if (custom.isEmpty()) {
            return compressAction;
        }
        if (compressAction == null) {
            return new CompositeAction(custom, stopOnError);
        }
        ArrayList<Action> all = new ArrayList<Action>();
        all.add(compressAction);
        all.addAll(custom);
        return new CompositeAction(all, stopOnError);
    }

    protected int suffixLength(String lowFilename) {
        for (FileExtension extension : FileExtension.values()) {
            if (!extension.isExtensionFor(lowFilename)) continue;
            return extension.length();
        }
        return 0;
    }

    protected SortedMap<Integer, Path> getEligibleFiles(RollingFileManager manager) {
        return this.getEligibleFiles(manager, true);
    }

    protected SortedMap<Integer, Path> getEligibleFiles(RollingFileManager manager, boolean isAscending) {
        StringBuilder buf = new StringBuilder();
        String pattern = manager.getPatternProcessor().getPattern();
        manager.getPatternProcessor().formatFileName(this.strSubstitutor, buf, (Object)NotANumber.NAN);
        return this.getEligibleFiles(buf.toString(), pattern, isAscending);
    }

    protected SortedMap<Integer, Path> getEligibleFiles(String path, String pattern) {
        return this.getEligibleFiles(path, pattern, true);
    }

    protected SortedMap<Integer, Path> getEligibleFiles(String path, String logfilePattern, boolean isAscending) {
        TreeMap<Integer, Path> eligibleFiles = new TreeMap<Integer, Path>();
        File file = new File(path);
        File parent = file.getParentFile();
        if (parent == null) {
            parent = new File(".");
        } else {
            parent.mkdirs();
        }
        if (!logfilePattern.contains("%i")) {
            return eligibleFiles;
        }
        Path dir = parent.toPath();
        String fileName = file.getName();
        int suffixLength = this.suffixLength(fileName);
        if (suffixLength > 0) {
            fileName = fileName.substring(0, fileName.length() - suffixLength) + ".*";
        }
        String filePattern = fileName.replace("\u0000", "(\\d+)");
        Pattern pattern = Pattern.compile(filePattern);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir);){
            for (Path entry : stream) {
                Matcher matcher = pattern.matcher(entry.toFile().getName());
                if (!matcher.matches()) continue;
                Integer index = Integer.parseInt(matcher.group(1));
                eligibleFiles.put(index, entry);
            }
        } catch (IOException ioe) {
            throw new LoggingException("Error reading folder " + dir + " " + ioe.getMessage(), ioe);
        }
        return isAscending ? eligibleFiles : eligibleFiles.descendingMap();
    }
}

