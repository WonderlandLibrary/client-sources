package me.finz0.osiris.module.modules.gui;

import de.Hero.settings.Setting;
import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.gui.editor.GuiHudEditor;
import me.finz0.osiris.module.Module;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public final class HudEditorModule extends Module {

    private boolean open;

    public HudEditorModule() {
        super("HudEditor", Category.GUI);
        setBind(Keyboard.KEY_Y);
        setDrawn(false);
    }

    public Setting blur;

    public void setup() {

        AuroraMod.getInstance().settingsManager.rSetting(blur = new Setting("Blur", this, true, "HudEditorBlur"));

    }

    @Override
    public void onEnable() {
        super.onEnable();
        Minecraft.getMinecraft().displayGuiScreen(new GuiHudEditor());
        this.open = true;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
