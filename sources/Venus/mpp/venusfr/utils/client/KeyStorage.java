/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.client;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.resources.I18n;

public class KeyStorage {
    private static final Map<String, Integer> keyMap = new HashMap<String, Integer>();
    private static final Map<Integer, String> reverseKeyMap = new HashMap<Integer, String>();

    public static String getKey(int n) {
        if (n < 0) {
            return switch (n) {
                case -100 -> I18n.format("key.mouse.left", new Object[0]);
                case -99 -> I18n.format("key.mouse.right", new Object[0]);
                case -98 -> I18n.format("key.mouse.middle", new Object[0]);
                default -> "MOUSE" + (n + 101);
            };
        }
        return KeyStorage.getReverseKey(n);
    }

    public static String getReverseKey(int n) {
        return reverseKeyMap.getOrDefault(n, "");
    }

    public static Integer getKey(String string) {
        return keyMap.getOrDefault(string, -1);
    }

    private static void putMappings() {
        keyMap.put("A", 65);
        keyMap.put("B", 66);
        keyMap.put("C", 67);
        keyMap.put("D", 68);
        keyMap.put("E", 69);
        keyMap.put("F", 70);
        keyMap.put("G", 71);
        keyMap.put("H", 72);
        keyMap.put("I", 73);
        keyMap.put("J", 74);
        keyMap.put("K", 75);
        keyMap.put("L", 76);
        keyMap.put("M", 77);
        keyMap.put("N", 78);
        keyMap.put("O", 79);
        keyMap.put("P", 80);
        keyMap.put("Q", 81);
        keyMap.put("R", 82);
        keyMap.put("S", 83);
        keyMap.put("T", 84);
        keyMap.put("U", 85);
        keyMap.put("V", 86);
        keyMap.put("W", 87);
        keyMap.put("X", 88);
        keyMap.put("Y", 89);
        keyMap.put("Z", 90);
        keyMap.put("0", 48);
        keyMap.put("1", 49);
        keyMap.put("2", 50);
        keyMap.put("3", 51);
        keyMap.put("4", 52);
        keyMap.put("5", 53);
        keyMap.put("6", 54);
        keyMap.put("7", 55);
        keyMap.put("8", 56);
        keyMap.put("9", 57);
        keyMap.put("F1", 290);
        keyMap.put("F2", 291);
        keyMap.put("F3", 292);
        keyMap.put("F4", 293);
        keyMap.put("F5", 294);
        keyMap.put("F6", 295);
        keyMap.put("F7", 296);
        keyMap.put("F8", 297);
        keyMap.put("F9", 298);
        keyMap.put("F10", 299);
        keyMap.put("F11", 300);
        keyMap.put("F12", 301);
        keyMap.put("NUMPAD1", 321);
        keyMap.put("NUMPAD2", 322);
        keyMap.put("NUMPAD3", 323);
        keyMap.put("NUMPAD4", 324);
        keyMap.put("NUMPAD5", 325);
        keyMap.put("NUMPAD6", 326);
        keyMap.put("NUMPAD7", 327);
        keyMap.put("NUMPAD8", 328);
        keyMap.put("NUMPAD9", 329);
        keyMap.put("SPACE", 32);
        keyMap.put("ENTER", 257);
        keyMap.put("ESCAPE", 256);
        keyMap.put("HOME", 268);
        keyMap.put("INSERT", 260);
        keyMap.put("DELETE", 261);
        keyMap.put("END", 269);
        keyMap.put("PAGEUP", 266);
        keyMap.put("PAGEDOWN", 267);
        keyMap.put("RIGHT", 262);
        keyMap.put("LEFT", 263);
        keyMap.put("DOWN", 264);
        keyMap.put("UP", 265);
        keyMap.put("RIGHT_SHIFT", 344);
        keyMap.put("LEFT_SHIFT", 340);
        keyMap.put("RIGHT_CONTROL", 345);
        keyMap.put("LEFT_CONTROL", 341);
        keyMap.put("RIGHT_ALT", 346);
        keyMap.put("LEFT_ALT", 342);
        keyMap.put("RIGHT_SUPER", 347);
        keyMap.put("LEFT_SUPER", 343);
        keyMap.put("MENU", 348);
        keyMap.put("CAPS_LOCK", 280);
        keyMap.put("NUM_LOCK", 282);
        keyMap.put("SCROLL_LOCK", 281);
        keyMap.put("KP_DECIMAL", 330);
        keyMap.put("KP_DIVIDE", 331);
        keyMap.put("KP_MULTIPLY", 332);
        keyMap.put("KP_SUBTRACT", 333);
        keyMap.put("KP_PLUS", 334);
        keyMap.put("KP_ENTER", 335);
        keyMap.put("KP_EQUAL", 336);
        keyMap.put("'", 39);
        keyMap.put("/", 47);
        keyMap.put("-", 45);
        keyMap.put("+", 61);
        keyMap.put("BACK", 259);
        keyMap.put("BACKSLASH", 92);
        keyMap.put(".", 46);
        keyMap.put("COMMA", 44);
        keyMap.put("PAUSE", 284);
        keyMap.put("`", 96);
    }

    private static void reverseMappings() {
        for (Map.Entry<String, Integer> entry : keyMap.entrySet()) {
            reverseKeyMap.put(entry.getValue(), entry.getKey());
        }
    }

    static {
        KeyStorage.putMappings();
        KeyStorage.reverseMappings();
    }
}

