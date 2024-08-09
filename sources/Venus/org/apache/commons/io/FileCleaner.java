/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io;

import java.io.File;
import org.apache.commons.io.FileCleaningTracker;
import org.apache.commons.io.FileDeleteStrategy;

@Deprecated
public class FileCleaner {
    static final FileCleaningTracker theInstance = new FileCleaningTracker();

    @Deprecated
    public static void track(File file, Object object) {
        theInstance.track(file, object);
    }

    @Deprecated
    public static void track(File file, Object object, FileDeleteStrategy fileDeleteStrategy) {
        theInstance.track(file, object, fileDeleteStrategy);
    }

    @Deprecated
    public static void track(String string, Object object) {
        theInstance.track(string, object);
    }

    @Deprecated
    public static void track(String string, Object object, FileDeleteStrategy fileDeleteStrategy) {
        theInstance.track(string, object, fileDeleteStrategy);
    }

    @Deprecated
    public static int getTrackCount() {
        return theInstance.getTrackCount();
    }

    @Deprecated
    public static synchronized void exitWhenFinished() {
        theInstance.exitWhenFinished();
    }

    public static FileCleaningTracker getInstance() {
        return theInstance;
    }
}

