/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.settings;

import java.util.Arrays;
import java.util.Comparator;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public enum NarratorStatus {
    OFF(0, "options.narrator.off"),
    ALL(1, "options.narrator.all"),
    CHAT(2, "options.narrator.chat"),
    SYSTEM(3, "options.narrator.system");

    private static final NarratorStatus[] BY_ID;
    private final int id;
    private final ITextComponent field_238232_g_;

    private NarratorStatus(int n2, String string2) {
        this.id = n2;
        this.field_238232_g_ = new TranslationTextComponent(string2);
    }

    public int getId() {
        return this.id;
    }

    public ITextComponent func_238233_b_() {
        return this.field_238232_g_;
    }

    public static NarratorStatus byId(int n) {
        return BY_ID[MathHelper.normalizeAngle(n, BY_ID.length)];
    }

    private static NarratorStatus[] lambda$static$0(int n) {
        return new NarratorStatus[n];
    }

    static {
        BY_ID = (NarratorStatus[])Arrays.stream(NarratorStatus.values()).sorted(Comparator.comparingInt(NarratorStatus::getId)).toArray(NarratorStatus::lambda$static$0);
    }
}

