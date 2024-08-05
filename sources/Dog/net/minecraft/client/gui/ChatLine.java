package net.minecraft.client.gui;

import fr.dog.util.render.animation.Animation;
import fr.dog.util.render.animation.Easing;
import net.minecraft.util.IChatComponent;

public class ChatLine {
    private final int updateCounterCreated;
    private final IChatComponent lineString;
    private final int chatLineID;

    public final Animation popupAnimation = new Animation(Easing.EASE_IN_OUT_QUAD, 250);

    public ChatLine(int p_i45000_1_, IChatComponent p_i45000_2_, int p_i45000_3_) {
        this.lineString = p_i45000_2_;
        this.updateCounterCreated = p_i45000_1_;
        this.chatLineID = p_i45000_3_;
    }

    public IChatComponent getChatComponent() {
        return this.lineString;
    }

    public int getUpdatedCounter() {
        return this.updateCounterCreated;
    }

    public int getChatLineID() {
        return this.chatLineID;
    }
}
