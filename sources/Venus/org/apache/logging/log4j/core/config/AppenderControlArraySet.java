/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.config.AppenderControl;
import org.apache.logging.log4j.util.PerformanceSensitive;

@PerformanceSensitive
public class AppenderControlArraySet {
    private final AtomicReference<AppenderControl[]> appenderArray = new AtomicReference<AppenderControl[]>(new AppenderControl[0]);

    public boolean add(AppenderControl appenderControl) {
        AppenderControl[] appenderControlArray;
        AppenderControl[] appenderControlArray2;
        boolean bl;
        do {
            for (AppenderControl appenderControl2 : appenderControlArray2 = this.appenderArray.get()) {
                if (!appenderControl2.equals(appenderControl)) continue;
                return true;
            }
            appenderControlArray = Arrays.copyOf(appenderControlArray2, appenderControlArray2.length + 1);
            appenderControlArray[appenderControlArray.length - 1] = appenderControl;
        } while (!(bl = this.appenderArray.compareAndSet(appenderControlArray2, appenderControlArray)));
        return false;
    }

    public AppenderControl remove(String string) {
        boolean bl;
        block0: do {
            bl = true;
            AppenderControl[] appenderControlArray = this.appenderArray.get();
            for (int i = 0; i < appenderControlArray.length; ++i) {
                AppenderControl appenderControl = appenderControlArray[i];
                if (!Objects.equals(string, appenderControl.getAppenderName())) continue;
                AppenderControl[] appenderControlArray2 = this.removeElementAt(i, appenderControlArray);
                if (this.appenderArray.compareAndSet(appenderControlArray, appenderControlArray2)) {
                    return appenderControl;
                }
                bl = false;
                continue block0;
            }
        } while (!bl);
        return null;
    }

    private AppenderControl[] removeElementAt(int n, AppenderControl[] appenderControlArray) {
        AppenderControl[] appenderControlArray2 = Arrays.copyOf(appenderControlArray, appenderControlArray.length - 1);
        System.arraycopy(appenderControlArray, n + 1, appenderControlArray2, n, appenderControlArray2.length - n);
        return appenderControlArray2;
    }

    public Map<String, Appender> asMap() {
        HashMap<String, Appender> hashMap = new HashMap<String, Appender>();
        for (AppenderControl appenderControl : this.appenderArray.get()) {
            hashMap.put(appenderControl.getAppenderName(), appenderControl.getAppender());
        }
        return hashMap;
    }

    public AppenderControl[] clear() {
        return this.appenderArray.getAndSet(new AppenderControl[0]);
    }

    public boolean isEmpty() {
        return this.appenderArray.get().length == 0;
    }

    public AppenderControl[] get() {
        return this.appenderArray.get();
    }
}

