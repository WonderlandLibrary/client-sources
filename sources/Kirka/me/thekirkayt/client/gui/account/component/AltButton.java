/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.gui.account.component;

import me.thekirkayt.client.account.Alt;
import me.thekirkayt.client.account.LoginThread;
import me.thekirkayt.client.gui.account.component.Button;

public class AltButton
extends Button {
    private Alt alt;

    public AltButton(String text, int x1, int x2, int y1, int y2, int minHex, int maxHex, Alt alt) {
        super(text, x1, x2, y1, y2, minHex, maxHex);
        this.alt = alt;
    }

    @Override
    public void onClick(int button) {
        LoginThread thread = new LoginThread(this.alt);
        thread.start();
    }

    public Alt getAlt() {
        return this.alt;
    }

    public void setAlt(Alt alt) {
        this.alt = alt;
    }
}

