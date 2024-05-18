/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.util.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import pw.vertexcode.util.Nameable;

public class Logger
implements Nameable {
    private static boolean debug = false;
    private final String name;

    public Logger(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    private String getTime() {
        SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
        return sf.format(new Date());
    }

    public void info(CharSequence message) {
    }
}

