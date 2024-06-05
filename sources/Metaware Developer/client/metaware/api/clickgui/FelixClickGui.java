package client.metaware.api.clickgui;

import client.metaware.Metaware;
import client.metaware.api.clickgui.component.Component;
import client.metaware.api.clickgui.component.implementations.BindComponent;
import client.metaware.api.clickgui.component.implementations.BooleanComponent;
import client.metaware.api.clickgui.component.implementations.EnumComponent;
import client.metaware.api.clickgui.component.implementations.SliderComponent;
import client.metaware.api.clickgui.panel.implementations.CategoryPanel;
import client.metaware.api.clickgui.panel.implementations.ModulePanel;
import client.metaware.api.clickgui.theme.Theme;
import client.metaware.api.clickgui.theme.implementations.RetardTheme;
import client.metaware.api.font.CustomFontRenderer;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.properties.property.Property;
import client.metaware.api.properties.property.impl.DoubleProperty;
import client.metaware.api.properties.property.impl.EnumProperty;
import client.metaware.impl.utils.render.RenderUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FelixClickGui extends GuiScreen {

    private Theme currentTheme;

    private List<client.metaware.api.clickgui.component.Component> objects = new ArrayList<>();

    private float componentWidth = 110;
    private float componentHeight = 15;

    public FelixClickGui() {
        currentTheme = new RetardTheme();
        float posX = 6;
        float posY = 4;
        for(Category category : Category.values()) {
            objects.add(new CategoryPanel(category, posX, posY, componentWidth, componentHeight) {
                @Override
                public void init() {
                    for(Module module : Metaware.INSTANCE.getModuleManager().getModules()) {
                        if(module.getCategory() == category) {
                            components().add(new ModulePanel(module, x, y, componentWidth, componentHeight) {
                                @Override
                                public void init() {
                                    components.add(new BindComponent(module, x, y, componentWidth, componentHeight));
                                    for(Property property : Module.getPropertyRepository().propertiesBy(module.getClass())) {
                                        if(property.getValue() instanceof Boolean)
                                            components.add(new BooleanComponent(property, x, y, componentWidth, componentHeight, property.isAvailable()));
                                        if(property instanceof EnumProperty)
                                            components.add(new EnumComponent((EnumProperty) property, x, y, componentWidth, componentHeight, property.isAvailable()));
                                        if(property instanceof DoubleProperty)
                                            components.add(new SliderComponent((DoubleProperty) property, x, y, componentWidth, componentHeight, property.isAvailable()));
                                        property.addValueChange((oldValue, value) -> updateComponents());
                                    }
                                    updateComponents();
                                }
                            });
                        }
                    }
                }
            });
            posX += componentWidth + 3;
        }
    }

    @Override
    public void initGui() {
        objects.forEach(client.metaware.api.clickgui.component.Component::reset);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(mc);
        CustomFontRenderer fr = Metaware.INSTANCE.getFontManager().currentFont().size(4);
        RenderUtil.drawGradientRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), new Color(85, 85, 255, 100).getRGB(), new Color(0, 0, 0, 30).getRGB());
        objects.forEach(panel -> { if(panel.visible()) panel.drawScreen(mouseX, mouseY); });
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        objects.forEach(panel -> { if(panel.visible()) panel.mouseClicked(mouseX, mouseY, mouseButton); });
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        objects.forEach(panel -> { if(panel.visible()) panel.mouseReleased(mouseX, mouseY, state); });
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        boolean focused = false;
        for(Component panel : objects)
            if(panel.visible() && panel.focused())
                focused = true;
        if(!focused) {
            super.keyTyped(typedChar, keyCode);
        }
        objects.forEach(panel -> { if(panel.visible()) panel.keyTyped(typedChar, keyCode); });
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
