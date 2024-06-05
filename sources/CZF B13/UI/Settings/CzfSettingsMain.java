package gq.vapu.czfclient.UI.Settings;

import gq.vapu.czfclient.Manager.ModuleManager;
import gq.vapu.czfclient.Module.Modules.Render.ClickGui;
import gq.vapu.czfclient.UI.ClickUi.ClickUi;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;

public class CzfSettingsMain extends GuiScreen {
    private final GuiScreen parent;

    public CzfSettingsMain(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void initGui() {
        buttonList.add(
                new GuiButton(
                        1, width / 2 - 155, height / 6 + 42, 150, 20,
                        "ClickGUI"
                )
        );
        buttonList.add(
                new GuiButton(
                        100, width / 2 - 100, height / 6 + 168,
                        "Back"
                )
        );
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if (button.enabled) {
            switch (button.id) {
                case 100:
                    mc.displayGuiScreen(parent);
                    break;
                case 1:
                    ModuleManager.getModuleByClass(ClickGui.class).setEnabled(true);
                    mc.displayGuiScreen(new ClickUi());
                    break;
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawCenteredString(fontRendererObj, "Czf Client Settings", width / 2, 15, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
