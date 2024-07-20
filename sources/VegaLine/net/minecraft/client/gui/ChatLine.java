/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui;

import net.minecraft.util.text.ITextComponent;
import ru.govno.client.utils.Render.AnimationUtils;

public class ChatLine {
    private final int updateCounterCreated;
    private final ITextComponent lineString;
    AnimationUtils anim = new AnimationUtils(0.0f, 0.0f, 0.05f);
    AnimationUtils anim2 = new AnimationUtils(0.0f, 8.0f, 0.1f);
    AnimationUtils anim3 = new AnimationUtils(0.0f, 8.0f, 0.08f);
    private final int chatLineID;

    public ChatLine(int p_i45000_1_, ITextComponent p_i45000_2_, int p_i45000_3_) {
        this.lineString = p_i45000_2_;
        this.updateCounterCreated = p_i45000_1_;
        this.chatLineID = p_i45000_3_;
    }

    public ITextComponent getChatComponent() {
        return this.lineString;
    }

    public String getChatString() {
        String str = this.lineString.getFormattedText();
        return str;
    }

    public int getUpdatedCounter() {
        return this.updateCounterCreated;
    }

    public int getChatLineID() {
        return this.chatLineID;
    }
}

