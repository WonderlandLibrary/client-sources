package client.metaware.api.clickgui.panel.implementations;

import client.metaware.Metaware;
import client.metaware.api.clickgui.panel.Panel;
import client.metaware.api.font.CustomFontRenderer;
import client.metaware.api.module.api.Module;
import client.metaware.impl.utils.render.RenderUtil;
import client.metaware.impl.utils.system.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class ModulePanel extends Panel {

    private Module module;
    private final TimerUtil timer = new TimerUtil();
    private CustomFontRenderer font = Metaware.INSTANCE.getFontManager().currentFont().size(17);

    public ModulePanel(Module module, float x, float y, float width, float height) {
        super(x, y, width, height, false);
        this.module = module;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        theme.drawModule(this, x, y, width, height);
        String description = "No description.";
        ScaledResolution sc = new ScaledResolution(Minecraft.getMinecraft());
        if(!module.getDescription().isEmpty())
            description = module.getDescription();
        if(isHovered(mouseX, mouseY))
            font.drawStringWithShadow(description, sc.getScaledWidth() - font.getWidth(description), sc.getScaledHeight() - font.getHeight(description), -1);
        origHeight = RenderUtil.animate(totalHeight(), origHeight, 0.1f) + 0.1f;
        super.drawScreen(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(isHovered(mouseX, mouseY)) {
            if(mouseButton == 0) {
                module.toggled(!module.isToggled());
                timer.reset();
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        super.keyTyped(typedChar, keyCode);
    }

    public Module module() {
        return module;
    }
    public void module(Module module) {
        this.module = module;
    }

}
