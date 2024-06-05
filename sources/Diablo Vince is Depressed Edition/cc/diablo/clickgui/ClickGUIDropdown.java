/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.clickgui;

import cc.diablo.clickgui.impl.Frame;
import cc.diablo.module.Category;
import net.minecraft.client.gui.GuiScreen;

public class ClickGUIDropdown
extends GuiScreen {
    double x;
    double y;
    double x2;
    double y2;
    double width;
    double height;

    @Override
    public void initGui() {
        this.x = this.sr.getScaledWidth_double() / 7.0;
        this.y = this.sr.getScaledHeight_double() / 7.0;
        this.width = 200.0;
        this.height = 20.0;
        this.x2 = this.x;
        this.y2 = this.y;
        for (Category e : Category.values()) {
            new Frame(this.x, this.y, this.width, this.height, e);
            this.x += this.sr.getScaledWidth_double() / 7.0;
            this.y = this.sr.getScaledHeight_double() / 7.0;
            this.y += this.y;
        }
        super.initGui();
    }
}

