/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.realms;

import java.time.Duration;
import java.util.Arrays;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.realms.RepeatedNarrator;
import net.minecraft.util.Util;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.StringTextComponent;

public class RealmsNarratorHelper {
    private static final RepeatedNarrator field_239548_a_ = new RepeatedNarrator(Duration.ofSeconds(5L));

    public static void func_239550_a_(String string) {
        NarratorChatListener narratorChatListener = NarratorChatListener.INSTANCE;
        narratorChatListener.clear();
        narratorChatListener.say(ChatType.SYSTEM, new StringTextComponent(RealmsNarratorHelper.func_239554_c_(string)), Util.DUMMY_UUID);
    }

    private static String func_239554_c_(String string) {
        return string.replace("\\n", System.lineSeparator());
    }

    public static void func_239551_a_(String ... stringArray) {
        RealmsNarratorHelper.func_239549_a_(Arrays.asList(stringArray));
    }

    public static void func_239549_a_(Iterable<String> iterable) {
        RealmsNarratorHelper.func_239550_a_(RealmsNarratorHelper.func_239552_b_(iterable));
    }

    public static String func_239552_b_(Iterable<String> iterable) {
        return String.join((CharSequence)System.lineSeparator(), iterable);
    }

    public static void func_239553_b_(String string) {
        field_239548_a_.func_231415_a_(RealmsNarratorHelper.func_239554_c_(string));
    }
}

