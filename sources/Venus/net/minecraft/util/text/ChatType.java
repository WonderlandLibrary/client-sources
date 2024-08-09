/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.text;

public enum ChatType {
    CHAT(0, false),
    SYSTEM(1, true),
    GAME_INFO(2, true);

    private final byte id;
    private final boolean interrupts;

    private ChatType(byte by, boolean bl) {
        this.id = by;
        this.interrupts = bl;
    }

    public byte getId() {
        return this.id;
    }

    public static ChatType byId(byte by) {
        for (ChatType chatType : ChatType.values()) {
            if (by != chatType.id) continue;
            return chatType;
        }
        return CHAT;
    }

    public boolean getInterrupts() {
        return this.interrupts;
    }
}

