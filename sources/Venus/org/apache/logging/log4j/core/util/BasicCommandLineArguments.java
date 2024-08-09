/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.beust.jcommander.JCommander
 *  com.beust.jcommander.Parameter
 */
package org.apache.logging.log4j.core.util;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class BasicCommandLineArguments {
    @Parameter(names={"--help", "-?", "-h"}, help=true, description="Prints this help.")
    private boolean help;

    public static <T extends BasicCommandLineArguments> T parseCommandLine(String[] stringArray, Class<?> clazz, T t) {
        JCommander jCommander = new JCommander(t);
        jCommander.setProgramName(clazz.getName());
        jCommander.setCaseSensitiveOptions(true);
        jCommander.parse(stringArray);
        if (t.isHelp()) {
            jCommander.usage();
        }
        return t;
    }

    public boolean isHelp() {
        return this.help;
    }

    public void setHelp(boolean bl) {
        this.help = bl;
    }
}

