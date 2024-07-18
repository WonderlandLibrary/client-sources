package com.alan.clients.util.input;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class Input {
    public static final int EVENT_SIZE = 18;
    public static final int CHAR_NONE = 0;
    public static final int INPUT_KEY_NONE = 0;
    public static final int INPUT_KEY_ESCAPE = 1;
    public static final int INPUT_KEY_1 = 2;
    public static final int INPUT_KEY_2 = 3;
    public static final int INPUT_KEY_3 = 4;
    public static final int INPUT_KEY_4 = 5;
    public static final int INPUT_KEY_5 = 6;
    public static final int INPUT_KEY_6 = 7;
    public static final int INPUT_KEY_7 = 8;
    public static final int INPUT_KEY_8 = 9;
    public static final int INPUT_KEY_9 = 10;
    public static final int INPUT_KEY_0 = 11;
    public static final int INPUT_KEY_MINUS = 12;
    public static final int INPUT_KEY_EQUALS = 13;
    public static final int INPUT_KEY_BACK = 14;
    public static final int INPUT_KEY_TAB = 15;
    public static final int INPUT_KEY_Q = 16;
    public static final int INPUT_KEY_W = 17;
    public static final int INPUT_KEY_E = 18;
    public static final int INPUT_KEY_R = 19;
    public static final int INPUT_KEY_T = 20;
    public static final int INPUT_KEY_Y = 21;
    public static final int INPUT_KEY_U = 22;
    public static final int INPUT_KEY_I = 23;
    public static final int INPUT_KEY_O = 24;
    public static final int INPUT_KEY_P = 25;
    public static final int INPUT_KEY_LBRACKET = 26;
    public static final int INPUT_KEY_RBRACKET = 27;
    public static final int INPUT_KEY_RETURN = 28;
    public static final int INPUT_KEY_LCONTROL = 29;
    public static final int INPUT_KEY_A = 30;
    public static final int INPUT_KEY_S = 31;
    public static final int INPUT_KEY_D = 32;
    public static final int INPUT_KEY_F = 33;
    public static final int INPUT_KEY_G = 34;
    public static final int INPUT_KEY_H = 35;
    public static final int INPUT_KEY_J = 36;
    public static final int INPUT_KEY_K = 37;
    public static final int INPUT_KEY_L = 38;
    public static final int INPUT_KEY_SEMICOLON = 39;
    public static final int INPUT_KEY_APOSTROPHE = 40;
    public static final int INPUT_KEY_GRAVE = 41;
    public static final int INPUT_KEY_LSHIFT = 42;
    public static final int INPUT_KEY_BACKSLASH = 43;
    public static final int INPUT_KEY_Z = 44;
    public static final int INPUT_KEY_X = 45;
    public static final int INPUT_KEY_C = 46;
    public static final int INPUT_KEY_V = 47;
    public static final int INPUT_KEY_B = 48;
    public static final int INPUT_KEY_N = 49;
    public static final int INPUT_KEY_M = 50;
    public static final int INPUT_KEY_COMMA = 51;
    public static final int INPUT_KEY_PERIOD = 52;
    public static final int INPUT_KEY_SLASH = 53;
    public static final int INPUT_KEY_RSHIFT = 54;
    public static final int INPUT_KEY_MULTIPLY = 55;
    public static final int INPUT_KEY_LMENU = 56;
    public static final int INPUT_KEY_SPACE = 57;
    public static final int INPUT_KEY_CAPITAL = 58;
    public static final int INPUT_KEY_F1 = 59;
    public static final int INPUT_KEY_F2 = 60;
    public static final int INPUT_KEY_F3 = 61;
    public static final int INPUT_KEY_F4 = 62;
    public static final int INPUT_KEY_F5 = 63;
    public static final int INPUT_KEY_F6 = 64;
    public static final int INPUT_KEY_F7 = 65;
    public static final int INPUT_KEY_F8 = 66;
    public static final int INPUT_KEY_F9 = 67;
    public static final int INPUT_KEY_F10 = 68;
    public static final int INPUT_KEY_NUMLOCK = 69;
    public static final int INPUT_KEY_SCROLL = 70;
    public static final int INPUT_KEY_NUMPAD7 = 71;
    public static final int INPUT_KEY_NUMPAD8 = 72;
    public static final int INPUT_KEY_NUMPAD9 = 73;
    public static final int INPUT_KEY_SUBTRACT = 74;
    public static final int INPUT_KEY_NUMPAD4 = 75;
    public static final int INPUT_KEY_NUMPAD5 = 76;
    public static final int INPUT_KEY_NUMPAD6 = 77;
    public static final int INPUT_KEY_ADD = 78;
    public static final int INPUT_KEY_NUMPAD1 = 79;
    public static final int INPUT_KEY_NUMPAD2 = 80;
    public static final int INPUT_KEY_NUMPAD3 = 81;
    public static final int INPUT_KEY_NUMPAD0 = 82;
    public static final int INPUT_KEY_DECIMAL = 83;
    public static final int INPUT_KEY_F11 = 87;
    public static final int INPUT_KEY_F12 = 88;
    public static final int INPUT_KEY_F13 = 100;
    public static final int INPUT_KEY_F14 = 101;
    public static final int INPUT_KEY_F15 = 102;
    public static final int INPUT_KEY_F16 = 103;
    public static final int INPUT_KEY_F17 = 104;
    public static final int INPUT_KEY_F18 = 105;
    public static final int INPUT_KEY_KANA = 112;
    public static final int INPUT_KEY_F19 = 113;
    public static final int INPUT_KEY_CONVERT = 121;
    public static final int INPUT_KEY_NOCONVERT = 123;
    public static final int INPUT_KEY_YEN = 125;
    public static final int INPUT_KEY_NUMPADEQUALS = 141;
    public static final int INPUT_KEY_CIRCUMFLEX = 144;
    public static final int INPUT_KEY_AT = 145;
    public static final int INPUT_KEY_COLON = 146;
    public static final int INPUT_KEY_UNDERLINE = 147;
    public static final int INPUT_KEY_KANJI = 148;
    public static final int INPUT_KEY_STOP = 149;
    public static final int INPUT_KEY_AX = 150;
    public static final int INPUT_KEY_UNLABELED = 151;
    public static final int INPUT_KEY_NUMPADENTER = 156;
    public static final int INPUT_KEY_RCONTROL = 157;
    public static final int INPUT_KEY_SECTION = 167;
    public static final int INPUT_KEY_NUMPADCOMMA = 179;
    public static final int INPUT_KEY_DIVIDE = 181;
    public static final int INPUT_KEY_SYSRQ = 183;
    public static final int INPUT_KEY_RMENU = 184;
    public static final int INPUT_KEY_FUNCTION = 196;
    public static final int INPUT_KEY_PAUSE = 197;
    public static final int INPUT_KEY_HOME = 199;
    public static final int INPUT_KEY_UP = 200;
    public static final int INPUT_KEY_PRIOR = 201;
    public static final int INPUT_KEY_LEFT = 203;
    public static final int INPUT_KEY_RIGHT = 205;
    public static final int INPUT_KEY_END = 207;
    public static final int INPUT_KEY_DOWN = 208;
    public static final int INPUT_KEY_NEXT = 209;
    public static final int INPUT_KEY_INSERT = 210;
    public static final int INPUT_KEY_DELETE = 211;
    public static final int INPUT_KEY_CLEAR = 218;
    public static final int INPUT_KEY_LMETA = 219;
    public static final int INPUT_KEY_RMETA = 220;
    public static final int INPUT_KEY_APPS = 221;
    public static final int INPUT_KEY_POWER = 222;
    public static final int INPUT_KEY_SLEEP = 223;
    public static final int INPUT_MOU_MOUSE0 = 224;
    public static final int INPUT_MOU_MOUSE1 = 225;
    public static final int INPUT_MOU_MOUSE2 = 226;
    public static final int INPUT_MOU_MOUSE3 = 227;
    public static final int INPUT_MOU_MOUSE4 = 228;
    public static final int INPUT_MOU_MOUSE5 = 229;

    private static final String[] keyName = new String[256];
    private static final Map<String, Integer> keyMap = new HashMap<>(253);

    public static synchronized String getInputName(int key) {
        return keyName[key];
    }

    public static synchronized int getInputIndex(String keyName) {
        Integer ret = keyMap.get(keyName);
        return ret == null ? 0 : ret;
    }

    static {
        Field[] fields = Input.class.getFields();

        try {
            Field[] arr$ = fields;
            int len$ = fields.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                Field field = arr$[i$];
                if (Modifier.isStatic(field.getModifiers()) && Modifier.isPublic(field.getModifiers()) && Modifier.isFinal(field.getModifiers()) && field.getType().equals(Integer.TYPE) && field.getName().startsWith("INPUT_") && !field.getName().endsWith("WIN")) {
                    int key = field.getInt(null);
                    String name = field.getName().substring(10);
                    keyName[key] = name;
                    keyMap.put(name, key);
                }
            }
        } catch (Exception ignored) {
        }
    }
}
