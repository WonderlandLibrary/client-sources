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

    protected Action merge(Action action, List<Action> list, boolean bl) {
        if (list.isEmpty()) {
            return action;
        }
        if (action == null) {
            return new CompositeAction(list, bl);
        }
        ArrayList<Action> arrayList = new ArrayList<Action>();
        arrayList.add(action);
        arrayList.addAll(list);
        return new CompositeAction(arrayList, bl);
    }

    protected int suffixLength(String string) {
        for (FileExtension fileExtension : FileExtension.values()) {
            if (!fileExtension.isExtensionFor(string)) continue;
            return fileExtension.length();
        }
        return 1;
    }

    protected SortedMap<Integer, Path> getEligibleFiles(RollingFileManager rollingFileManager) {
        return this.getEligibleFiles(rollingFileManager, false);
    }

    protected SortedMap<Integer, Path> getEligibleFiles(RollingFileManager rollingFileManager, boolean bl) {
        StringBuilder stringBuilder = new StringBuilder();
        String string = rollingFileManager.getPatternProcessor().getPattern();
        rollingFileManager.getPatternProcessor().formatFileName(this.strSubstitutor, stringBuilder, (Object)NotANumber.NAN);
        return this.getEligibleFiles(stringBuilder.toString(), string, bl);
    }

    protected SortedMap<Integer, Path> getEligibleFiles(String string, String string2) {
        return this.getEligibleFiles(string, string2, false);
    }

    protected SortedMap<Integer, Path> getEligibleFiles(String string, String string2, boolean bl) {
        TreeMap<Integer, Path> treeMap = new TreeMap<Integer, Path>();
        File file = new File(string);
        File file2 = file.getParentFile();
        if (file2 == null) {
            file2 = new File(".");
        } else {
            file2.mkdirs();
        }
        if (!string2.contains("%i")) {
            return treeMap;
        }
        Path path = file2.toPath();
        String string3 = file.getName();
        int n = this.suffixLength(string3);
        if (n > 0) {
            string3 = string3.substring(0, string3.length() - n) + ".*";
        }
        String string4 = string3.replace("\u0000", "(\\d+)");
        Pattern pattern = Pattern.compile(string4);
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path);){
            for (Path path2 : directoryStream) {
                Matcher matcher = pattern.matcher(path2.toFile().getName());
                if (!matcher.matches()) continue;
                Integer n2 = Integer.parseInt(matcher.group(1));
                treeMap.put(n2, path2);
            }
        } catch (IOException iOException) {
            throw new LoggingException("Error reading folder " + path + " " + iOException.getMessage(), iOException);
        }
        return bl ? treeMap : treeMap.descendingMap();
    }
}

