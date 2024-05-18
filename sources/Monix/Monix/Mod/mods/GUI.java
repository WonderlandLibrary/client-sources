/*
 * Decompiled with CFR 0_122.
 */
package Monix.Mod.mods;

import Monix.Category.Category;
import Monix.Mod.Mod;
import Monix.UI.GuiDrag.ClickGUI;
import Monix.UI.GuiDrag.DragGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class GUI
extends Mod {
    public GUI() {
        super("ClickGUI", "", 54, Category.RENDER);
    }

    @Override
    public void onEnable() {
// Settings ClickGUI
//        mc.displayGuiScreen(new DragGUI());
// No Settings ClickGUI
        mc.displayGuiScreen(new ClickGUI());
		
        this.toggle();
    }
}

