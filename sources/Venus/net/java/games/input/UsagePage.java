/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.lang.reflect.Method;
import net.java.games.input.Usage;

final class UsagePage {
    private static final UsagePage[] map = new UsagePage[255];
    public static final UsagePage UNDEFINED = new UsagePage(0);
    public static final UsagePage GENERIC_DESKTOP = new UsagePage(1, class$net$java$games$input$GenericDesktopUsage == null ? (class$net$java$games$input$GenericDesktopUsage = UsagePage.class$("net.java.games.input.GenericDesktopUsage")) : class$net$java$games$input$GenericDesktopUsage);
    public static final UsagePage SIMULATION = new UsagePage(2);
    public static final UsagePage VR = new UsagePage(3);
    public static final UsagePage SPORT = new UsagePage(4);
    public static final UsagePage GAME = new UsagePage(5);
    public static final UsagePage KEYBOARD_OR_KEYPAD = new UsagePage(7, class$net$java$games$input$KeyboardUsage == null ? (class$net$java$games$input$KeyboardUsage = UsagePage.class$("net.java.games.input.KeyboardUsage")) : class$net$java$games$input$KeyboardUsage);
    public static final UsagePage LEDS = new UsagePage(8);
    public static final UsagePage BUTTON = new UsagePage(9, class$net$java$games$input$ButtonUsage == null ? (class$net$java$games$input$ButtonUsage = UsagePage.class$("net.java.games.input.ButtonUsage")) : class$net$java$games$input$ButtonUsage);
    public static final UsagePage ORDINAL = new UsagePage(10);
    public static final UsagePage TELEPHONY = new UsagePage(11);
    public static final UsagePage CONSUMER = new UsagePage(12);
    public static final UsagePage DIGITIZER = new UsagePage(13);
    public static final UsagePage PID = new UsagePage(15);
    public static final UsagePage UNICODE = new UsagePage(16);
    public static final UsagePage ALPHANUMERIC_DISPLAY = new UsagePage(20);
    public static final UsagePage POWER_DEVICE = new UsagePage(132);
    public static final UsagePage BATTERY_SYSTEM = new UsagePage(133);
    public static final UsagePage BAR_CODE_SCANNER = new UsagePage(140);
    public static final UsagePage SCALE = new UsagePage(141);
    public static final UsagePage CAMERACONTROL = new UsagePage(144);
    public static final UsagePage ARCADE = new UsagePage(145);
    private final Class usage_class;
    private final int usage_page_id;
    static Class class$net$java$games$input$GenericDesktopUsage;
    static Class class$net$java$games$input$KeyboardUsage;
    static Class class$net$java$games$input$ButtonUsage;

    public static final UsagePage map(int n) {
        if (n < 0 || n >= map.length) {
            return null;
        }
        return map[n];
    }

    private UsagePage(int n, Class clazz) {
        UsagePage.map[n] = this;
        this.usage_class = clazz;
        this.usage_page_id = n;
    }

    private UsagePage(int n) {
        this(n, null);
    }

    public final String toString() {
        return "UsagePage (0x" + Integer.toHexString(this.usage_page_id) + ")";
    }

    public final Usage mapUsage(int n) {
        if (this.usage_class == null) {
            return null;
        }
        try {
            Method method = this.usage_class.getMethod("map", Integer.TYPE);
            Object object = method.invoke(null, new Integer(n));
            return (Usage)object;
        } catch (Exception exception) {
            throw new Error(exception);
        }
    }

    static Class class$(String string) {
        try {
            return Class.forName(string);
        } catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError().initCause(classNotFoundException);
        }
    }
}

