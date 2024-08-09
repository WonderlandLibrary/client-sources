/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.text2speech;

import com.mojang.text2speech.NarratorDummy;
import com.mojang.text2speech.NarratorLinux;
import com.mojang.text2speech.NarratorOSX;
import com.mojang.text2speech.NarratorWindows;
import java.util.Locale;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface Narrator {
    public void say(String var1, boolean var2);

    public void clear();

    public boolean active();

    public void destroy();

    public static Narrator getNarrator() {
        Logger logger = LogManager.getLogger();
        try {
            String string = System.getProperty("os.name").toLowerCase(Locale.ROOT);
            if (string.contains("linux")) {
                Narrator.setJNAPath(":");
                return new NarratorLinux();
            }
            if (string.contains("win")) {
                Narrator.setJNAPath(";");
                return new NarratorWindows();
            }
            if (string.contains("mac")) {
                Narrator.setJNAPath(":");
                return new NarratorOSX();
            }
            return new NarratorDummy();
        } catch (Throwable throwable) {
            logger.error(String.format("Error while loading the narrator : %s", throwable));
            return new NarratorDummy();
        }
    }

    public static void setJNAPath(String string) {
        System.setProperty("jna.library.path", System.getProperty("jna.library.path") + string + "./src/natives/resources/");
        System.setProperty("jna.library.path", System.getProperty("jna.library.path") + string + System.getProperty("java.library.path"));
    }
}

