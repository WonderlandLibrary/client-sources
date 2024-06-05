package alphentus.gui.customhud;

import alphentus.gui.customhud.dragging.DraggingHUD;
import alphentus.gui.customhud.settings.DrawTabs;
import alphentus.gui.customhud.settings.settings.ValueTab;
import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.mod.mods.hud.HUD;
import alphentus.utils.RenderUtils;
import alphentus.utils.fontrenderer.UnicodeFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ChatComponentText;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author avox | lmao
 * @since on 13.08.2020.
 */
public class CustomHUD extends GuiScreen {

    private final UnicodeFontRenderer fontRenderer = Init.getInstance().fontManager.myinghei21;
    private final HUD hud = Init.getInstance().modManager.getModuleByClass(HUD.class);
    private java.util.ArrayList<ValueTab> valueTabs = new ArrayList<>();
    private final Minecraft mc = Minecraft.getMinecraft();
    private ValueTab selectedTab;
    private int x, y, width, height;

    public CustomHUD() {
        for (ValueTab valueTab : ValueTab.values()) {
            valueTabs.add(valueTab);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        x = 100;
        y = 50;
        height = 20;
        width = (scaledResolution.getScaledWidth() - x * 2) / ValueTab.values().length;

        // Draws Background
        Gui.drawRect(x , y, scaledResolution.getScaledWidth() - x, scaledResolution.getScaledHeight() - y, hud.guiColor4.getRGB());
        Gui.drawRect(x, y + height, scaledResolution.getScaledWidth() - x, y + height + 1, Init.getInstance().CLIENT_COLOR.getRGB());

        // Draw Top Tabs
        for (ValueTab valueTab : valueTabs) {
            DrawTabs drawTabs = new DrawTabs(valueTab, x, y, width, height);
            drawTabs.drawScreen(mouseX, mouseY, partialTicks);
            x += width;
        }

        // Draws the Drag Elements Gui
        RenderUtils.drawFilledCircle(scaledResolution.getScaledWidth() - 30, scaledResolution.getScaledHeight() - 30, 15, hud.guiColor1);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        x = 100;
        for (ValueTab valueTab : valueTabs) {
            DrawTabs drawTabs = new DrawTabs(valueTab, x, y, width, height);
            drawTabs.mouseClicked(mouseX, mouseY, mouseButton);
            x += width;
        }

        if (mouseButton == 0 && mouseX >= Gui.getScaledResolution().getScaledWidth() - 45 && mouseX <= Gui.getScaledResolution().getScaledWidth() - 15 && mouseY >= Gui.getScaledResolution().getScaledHeight() - 45 && mouseY <= Gui.getScaledResolution().getScaledHeight() - 15) {
            mc.displayGuiScreen(new DraggingHUD());
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        x = 100;
        for (ValueTab valueTab : valueTabs) {
            DrawTabs drawTabs = new DrawTabs(valueTab, x, y, width, height);
            drawTabs.mouseReleased(mouseX, mouseY, state);
            x += width;
        }
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }

    public ValueTab getSelectedTab() {
        return selectedTab;
    }

    public void setSelectedTab(ValueTab selectedTab) {
        this.selectedTab = selectedTab;
    }
}