/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.gui;

import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;

public class GuiButtonOF
extends Button {
    public final int id;

    public GuiButtonOF(int n, int n2, int n3, int n4, int n5, String string, Button.IPressable iPressable) {
        super(n2, n3, n4, n5, new StringTextComponent(string), iPressable);
        this.id = n;
    }

    public GuiButtonOF(int n, int n2, int n3, int n4, int n5, String string) {
        this(n, n2, n3, n4, n5, string, GuiButtonOF::lambda$new$0);
    }

    public GuiButtonOF(int n, int n2, int n3, String string) {
        this(n, n2, n3, 200, 20, string, GuiButtonOF::lambda$new$1);
    }

    public void setMessage(String string) {
        super.setMessage(new StringTextComponent(string));
    }

    private static void lambda$new$1(Button button) {
    }

    private static void lambda$new$0(Button button) {
    }
}

