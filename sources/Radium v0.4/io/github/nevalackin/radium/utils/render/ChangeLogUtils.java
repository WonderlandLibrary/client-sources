package io.github.nevalackin.radium.utils.render;

import io.github.nevalackin.radium.utils.Wrapper;

import java.util.Arrays;
import java.util.List;

public final class ChangeLogUtils {

    private static final List<ChangeLogEntry> ENTRIES;

    static {
        ENTRIES = Arrays.asList(
                new ChangeLogEntry("Added Change log", ChangeType.ADD),
                new ChangeLogEntry("Added Alt manager", ChangeType.ADD),
                new ChangeLogEntry("Fixed Jump boost with fly, speed and long jump", ChangeType.FIX),
                new ChangeLogEntry("Removed Minor version number", ChangeType.REMOVE),
                new ChangeLogEntry("Fixed Third person rotations with auto pot", ChangeType.FIX),
                new ChangeLogEntry("Inventory manager bug fix", ChangeType.FIX),
                new ChangeLogEntry("Renamed Auto Heal to Auto Potion", ChangeType.FIX),
                new ChangeLogEntry("Added delay option to Criticals", ChangeType.ADD),
                new ChangeLogEntry("Added Ray Tracing option to Kill Aura", ChangeType.ADD),
                new ChangeLogEntry("Removed Walls, Target HUD and Prediction options from Kill Aura", ChangeType.REMOVE),
                new ChangeLogEntry("Fixed Fly silent flag", ChangeType.FIX),
                new ChangeLogEntry("Fixed No Fall (use Packet mode)", ChangeType.FIX),
                new ChangeLogEntry("Added Gradient Health Bar Start and End options to ESP", ChangeType.ADD),
                new ChangeLogEntry("Added Tags and Tags Health options to ESP", ChangeType.ADD),
                new ChangeLogEntry("Added Armor Bar option to ESP", ChangeType.ADD),
                new ChangeLogEntry("Fixed Sprint Bugging Out", ChangeType.FIX),
                new ChangeLogEntry("Added void and block above checks to Auto Potion", ChangeType.FIX),
                new ChangeLogEntry("Added Color Pickers", ChangeType.ADD),
                new ChangeLogEntry("Added Value representations (ms, %, m) to Click Gui sliders", ChangeType.ADD),
                new ChangeLogEntry("Added Scaffold", ChangeType.ADD));
    }

    public static void drawChangeLog() {
        Wrapper.getMinecraftFontRenderer().drawStringWithShadow("Change Log:", 2, 2, -1);
        int y = 13;
        for (ChangeLogEntry entry : ENTRIES) {
            Wrapper.getMinecraftFontRenderer().drawStringWithShadow(entry.type.prefix +  entry.text, 2, y, entry.type.color);
            y += 11;
        }
    }

    private enum ChangeType {
        ADD("[+] ", 0xFF00FF00),
        FIX("[*] ", 0xFFFFFF00),
        REMOVE("[-] ", 0xFFFF0000);

        private final String prefix;
        private final int color;

        ChangeType(String prefix, int color) {
            this.prefix = prefix;
            this.color = color;
        }
    }

    private static class ChangeLogEntry {
        private final String text;
        private final ChangeType type;

        public ChangeLogEntry(String text, ChangeType type) {
            this.text = text;
            this.type = type;
        }
    }
}
