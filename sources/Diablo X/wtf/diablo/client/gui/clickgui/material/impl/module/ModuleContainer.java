package wtf.diablo.client.gui.clickgui.material.impl.module;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.gui.clickgui.material.MaterialClickGUI;
import wtf.diablo.client.gui.clickgui.material.impl.main.MainPanel;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.util.render.gl.ScissorUtils;
import wtf.diablo.client.util.system.mouse.MouseUtils;

import java.util.ArrayList;
import java.util.Collection;

public final class ModuleContainer {

    private final MainPanel clickGUI;
    private final ModuleCategoryEnum EnumWeedModuleCategory;
    private final Collection<ModulePanel> modulePanels = new ArrayList<>();
    private int scrollY, allHeight;

    public ModuleContainer(MainPanel mainPanel, ModuleCategoryEnum EnumWeedModuleCategory) {
        this.clickGUI = mainPanel;
        this.EnumWeedModuleCategory = EnumWeedModuleCategory;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.getModulePanels();
        
        this.doScrolling(mouseX, mouseY);

        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        ScissorUtils.scissorArea(new ScaledResolution(Minecraft.getMinecraft()), this.clickGUI.getX(), this.clickGUI.getY() + 27, this.clickGUI.getWidth(), this.clickGUI.getHeight() - 30);
        int count = 0;
        int yHeight = 0;
        int yHeight1 = 0;
        for (final ModulePanel modulePanel : this.modulePanels) {
            modulePanel.setWidth((float) (this.clickGUI.getWidth() / 2f - 7.5f));
            modulePanel.setX((int) (this.clickGUI.getX() + 5 + (count % 2 != 1 ? 0 : modulePanel.getWidth() + 5)));
            modulePanel.setY((int) (this.clickGUI.getY() + 30 + (count % 2 != 1 ? yHeight1 : yHeight) + scrollY));

            if (count % 2 == 1) {
                yHeight += modulePanel.getHeight() + 5;
            } else {
                yHeight1 += modulePanel.getHeight() + 5;
            }

            modulePanel.drawScreen(mouseX, mouseY, partialTicks);
            count++;
        }
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        this.allHeight = Math.max(yHeight, yHeight1) - 10;
    }

    public void getModulePanels() {
        if (this.modulePanels.size() != Diablo.getInstance().getModuleRepository().getModulesByCategory(EnumWeedModuleCategory).size()) {
            if (!this.modulePanels.isEmpty()) {
                this.modulePanels.clear();
            }
            Diablo.getInstance().getModuleRepository().getModulesByCategory(EnumWeedModuleCategory).forEach(module -> this.modulePanels.add(new ModulePanel(this, module)));
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        this.modulePanels.forEach(modulePanel -> modulePanel.mouseClicked(mouseX, mouseY, mouseButton));
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        this.modulePanels.forEach(modulePanel -> modulePanel.mouseReleased(mouseX, mouseY, state));
    }

    public void onGuiClosed() {
        this.modulePanels.forEach(modulePanel -> modulePanel.onGuiClosed());
    }

    // weed code
    private void doScrolling(int mouseX, int mouseY) {
        int panelHeight = (int) (clickGUI.getHeight() - 27);
        if(MouseUtils.isHoveringCoords((float) clickGUI.getX(), (float) (clickGUI.getY() + 27), (float) clickGUI.getWidth(), panelHeight, mouseX, mouseY)) {
            int eventDWheel = Mouse.getDWheel();
            if (scrollY < 0 && eventDWheel > 0) {
                scrollY += 5;
            }
            if(scrollY > 0) {
                scrollY = 0;
            }
            if((allHeight + 5) > (panelHeight - 10)) {
                if((allHeight + 5) > ((panelHeight - 10) - scrollY) && eventDWheel < 0 && !(Math.abs(scrollY) > Math.abs((panelHeight - 10) - (allHeight + 5)))) {
                    scrollY -= 5;
                }
            } else {
                scrollY = 0;
            }
        }
        if(Math.abs(scrollY) > Math.abs((panelHeight - 10) - (allHeight + 5))) {
            scrollY = ((panelHeight - 10) - (allHeight + 5));
        }
    }

    public MainPanel getMainPanel() {
        return clickGUI;
    }
}
