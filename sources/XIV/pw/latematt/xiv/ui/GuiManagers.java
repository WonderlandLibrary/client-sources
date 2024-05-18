package pw.latematt.xiv.ui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import pw.latematt.xiv.ui.managers.alt.GuiAltManager;
import pw.latematt.xiv.ui.managers.mod.GuiModManager;
import pw.latematt.xiv.ui.managers.waypoint.GuiWaypointManager;

/**
 * @author Rederpz
 */
public class GuiManagers extends GuiScreen {

    private final GuiScreen parent;

    public GuiManagers(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + (height / 3), 200, 20, "Cancel"));

        this.buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + (height / 3) - 22, 98, 20, "Alts"));
        this.buttonList.add(new GuiButton(2, width / 2 + 2, height / 4 + (height / 3) - 22, 98, 20, "Mods"));
        this.buttonList.add(new GuiButton(3, width / 2 - 48, height / 4 + (height / 3) - 44, 98, 20, "Waypoints"));
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if (button.enabled) {
            switch (button.id) {
                case 0:
                    mc.displayGuiScreen(parent);
                    break;
                case 1:
                    mc.displayGuiScreen(new GuiAltManager(this));
                    break;
                case 2:
                    mc.displayGuiScreen(new GuiModManager(this));
                    break;
                case 3:
                    mc.displayGuiScreen(new GuiWaypointManager(this));
                    break;
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);

        String title = "XIV";
        GlStateManager.pushMatrix();
        GlStateManager.scale(4.0F, 4.0F, 4.0F);
        mc.fontRendererObj.drawStringWithShadow(title, (width - mc.fontRendererObj.getStringWidth(title)) / 8 - 6, 6, 0xFFFFFFFF);
        GlStateManager.popMatrix();
    }
}
