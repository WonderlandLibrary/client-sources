/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.player;

import java.util.Arrays;
import java.util.Comparator;
import net.minecraft.util.math.MathHelper;

public enum ChatVisibility {
    FULL(0, "options.chat.visibility.full"),
    SYSTEM(1, "options.chat.visibility.system"),
    HIDDEN(2, "options.chat.visibility.hidden");

    private static final ChatVisibility[] field_221255_d;
    private final int id;
    private final String resourceKey;

    private ChatVisibility(int n2, String string2) {
        this.id = n2;
        this.resourceKey = string2;
    }

    public int getId() {
        return this.id;
    }

    public String getResourceKey() {
        return this.resourceKey;
    }

    public static ChatVisibility getValue(int n) {
        return field_221255_d[MathHelper.normalizeAngle(n, field_221255_d.length)];
    }

    private static ChatVisibility[] lambda$static$0(int n) {
        return new ChatVisibility[n];
    }

    static {
        field_221255_d = (ChatVisibility[])Arrays.stream(ChatVisibility.values()).sorted(Comparator.comparingInt(ChatVisibility::getId)).toArray(ChatVisibility::lambda$static$0);
    }
}

