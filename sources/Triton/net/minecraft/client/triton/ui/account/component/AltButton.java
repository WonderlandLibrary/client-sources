// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.ui.account.component;

import net.minecraft.client.triton.management.account.Alt;
import net.minecraft.client.triton.management.account.LoginThread;

public class AltButton
extends Button {
    private Alt alt;

    public AltButton(String text, int x1, int x2, int y1, int y2, int minHex, int maxHex, Alt alt) {
        super(text, x1, x2, y1, y2, minHex, maxHex);
        this.alt = alt;
    }

    @Override
    public void onClick(int button) {
        LoginThread thread = new LoginThread(this.alt.email, this.alt.pass);
        thread.start();
    }

    public Alt getAlt() {
        return this.alt;
    }

    public void setAlt(Alt alt) {
        this.alt = alt;
    }
}

