package club.bluezenith.ui.clickguis.novo;

import club.bluezenith.BlueZenith;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.modules.render.ClickGUI;
import club.bluezenith.module.modules.render.hud.HUD;
import club.bluezenith.ui.clickguis.novo.components.Component;
import club.bluezenith.ui.clickguis.novo.components.panels.ModulePanelComponent;
import club.bluezenith.ui.clickguis.novo.components.panels.TargetsPanelComponent;
import club.bluezenith.ui.clickguis.novo.components.panels.config.ConfigPanelComponent;
import club.bluezenith.util.render.RenderUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static club.bluezenith.BlueZenith.getBlueZenith;

public class AncientNovoGUI extends GuiScreen {
    /* The gap on the X axis between each panel */
    private static final float PANEL_GAP = 5;

    public static final float TITLE_RECT_HEIGHT = 15;

    private final List<Component> panels = new ArrayList<>();
    private final List<Component> reversedListOfPanels = new ArrayList<>();

    private Component draggingPanel;
    private ConfigPanelComponent configPanel;

    private final ClickGUI clickGUI;

    public AncientNovoGUI(JsonObject config) {
        float x = 10, y = 0;

        for (ModuleCategory moduleCategory : ModuleCategory.values()) {
            ModulePanelComponent component;

            panels.add(component = (ModulePanelComponent) new ModulePanelComponent(
                                    this,
                                    getBlueZenith().getModuleManager().getModulesByCategory(moduleCategory),
                                    x, y
            ).setIdentifier(moduleCategory.novoGuiName));

            if(config.has(component.identifier)) {
                final JsonObject componentObject = config.get(component.identifier).getAsJsonObject();
                component.moveTo(componentObject.get("x").getAsFloat(), componentObject.get("y").getAsFloat());
                component.shown = componentObject.get("shown").getAsBoolean();
            } else component.shown = true;

            x += component.getWidth() + PANEL_GAP;
        }

        panels.add(configPanel = new ConfigPanelComponent(this, x, y));

        x += 100 + PANEL_GAP;

        configPanel.updateEntries();
        configPanel.shown = true;
        configPanel.setIdentifier("Configs");

        Component imFuckingTired;

        panels.add(imFuckingTired = new TargetsPanelComponent(this, x, y).setIdentifier("Targets"));

        imFuckingTired.setWidth(100);
        imFuckingTired.shown = true;

        reversedListOfPanels.addAll(panels);
        Collections.reverse(reversedListOfPanels);

        clickGUI = getBlueZenith().getModuleManager().getAndCast(ClickGUI.class);
    }

    @Override
    public void initGui() {
        super.initGui();
        configPanel.updateEntries();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        final ClickGUI clickGUI = BlueZenith.getBlueZenith().getModuleManager().getAndCast(ClickGUI.class);

        if(clickGUI.blurBackground.get())
            RenderUtil.blur(0, 0, width, height);
        if(clickGUI.backgroundAlpha.get() > 0)
            RenderUtil.rect(0, 0, width, height, new Color(0, 0, 0, clickGUI.backgroundAlpha.get()));

        for (Component panel : panels) {
            panel.draw(mouseX, mouseY, new ScaledResolution(mc));
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        draggingPanel = null;

        for (Component panel : reversedListOfPanels) {
            if(panel.isMouseOver(mouseX, mouseY)) {
                panel.mouseReleased(mouseX, mouseY, state);
            }
        }

        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        for (Component panel : reversedListOfPanels) {
            if(panel.isMouseOver(mouseX, mouseY)) {
                if(!(panel instanceof ConfigPanelComponent)) configPanel.missclick();

                panel.mouseClicked(mouseX, mouseY, mouseButton);
                return;
            }
        }
        configPanel.missclick();
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if(draggingPanel != null) {
            draggingPanel.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        } else {
            for (Component panel : reversedListOfPanels) {
                if (panel.isMouseOver(mouseX, mouseY)) {
                    panel.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);

                    if(panel.shouldLockDragging()) draggingPanel = panel;
                    break;
                }
            }
        }
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if(keyCode == 1) {
            mc.displayGuiScreen(null);
            mc.setIngameFocus();
        } else for (Component panel : reversedListOfPanels) {
            if(panel.isAcceptingKeypresses()) {
                panel.keyTyped(typedChar, keyCode);
            }
        }
    }

    @Override
    public void onGuiClosed() {
        for (Component panel : reversedListOfPanels) {
            panel.onGuiClosed();
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public static final Color GET_REAL = new Color(132, 116, 248);

    public int getGuiAccentColor() {
        if ("Match HUD".equals(clickGUI.colorMode.get()))
            return HUD.module.primaryColor.getRGB();
        return clickGUI.primaryColor.getRGB();
    }

    public JsonObject getConfig() {
        final JsonObject daObject = new JsonObject();

        for (Component panel : this.panels) {
            final JsonObject panelObject = new JsonObject();

            panelObject.add("x", new JsonPrimitive(panel.getX()));
            panelObject.add("y", new JsonPrimitive(panel.getY()));
            panelObject.add("shown", new JsonPrimitive(panel.shown));

            daObject.add(panel.identifier, panelObject);
        }

        return daObject;
    }
}
