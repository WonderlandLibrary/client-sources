/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.nemphis.modules;

import me.imfr0zen.guiapi.ClickGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import pw.vertexcode.nemphis.module.Category;
import pw.vertexcode.util.module.ModuleInformation;
import pw.vertexcode.util.module.types.ToggleableModule;

@ModuleInformation(category=Category.RENDER, color=0, name="Gui")
public class Gui
extends ToggleableModule {
    @Override
    public void onEnabled() {
        mc.displayGuiScreen(new ClickGui());
        this.toggleModule();
    }
}

