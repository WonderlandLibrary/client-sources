/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.resources;

import java.util.IllegalFormatException;
import net.minecraft.util.text.LanguageMap;

public class I18n {
    private static volatile LanguageMap field_239501_a_ = LanguageMap.getInstance();

    static void func_239502_a_(LanguageMap languageMap) {
        field_239501_a_ = languageMap;
    }

    public static String format(String string, Object ... objectArray) {
        String string2 = field_239501_a_.func_230503_a_(string);
        try {
            return String.format(string2, objectArray);
        } catch (IllegalFormatException illegalFormatException) {
            return "Format error: " + string2;
        }
    }

    public static boolean hasKey(String string) {
        return field_239501_a_.func_230506_b_(string);
    }
}

