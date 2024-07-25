package club.bluezenith.ui.clickgui;

import club.bluezenith.BlueZenith;
import club.bluezenith.core.data.preferences.Preferences;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.modules.render.ClickGUI;
import club.bluezenith.ui.clickgui.components.Panel;
import club.bluezenith.ui.clickgui.components.Panels.ConfigsPanel;
import club.bluezenith.ui.clickgui.components.Panels.ModulePanel;
import club.bluezenith.ui.clickgui.components.Panels.TargetsPanel;
import club.bluezenith.util.font.FontUtil;
import club.bluezenith.util.render.RenderUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import static club.bluezenith.util.render.RenderUtil.rect;
import static org.lwjgl.input.Keyboard.KEY_LSHIFT;
import static org.lwjgl.input.Keyboard.KEY_RETURN;

public class ClickGui extends GuiScreen {
    private final ArrayList<Panel> panels = new ArrayList<>();
    private final ConfigsPanel configsPanel;
    public boolean listeningForKey;
    public boolean shouldDrawSpotlight, shouldDoSearch;
    public String typedString = "";
    float textFieldCounter = 0;
    public int scroll;

    public ClickGui() {
        float x = 20;
        float y = 10;
        float maxHeight = 0;
        for (ModuleCategory v : ModuleCategory.values()) {
            ModulePanel panel = new ModulePanel(x, y, v);
            for (Module m : BlueZenith.getBlueZenith().getModuleManager().getModules()) {
                if(m.getCategory() == v){
                    panel.addModule(m);
                }
            }
            panels.add(panel.calculateSize());
            x += panel.width + 5;
        }
        panels.add(configsPanel = new ConfigsPanel(x, 16.5f));
        panels.add(new TargetsPanel(x, 16 + configsPanel.getHeightFirstTime() + 16.5f));
    }

    public void initGui() {
        configsPanel.update();
        if(mc.thePlayer == null)
            BlueZenith.getBlueZenith().getMainMenu().setWorldAndResolution(mc, width, height);
    }

    public void refreshModules() {
        for(Panel p : panels) {
            if(p instanceof ModulePanel) {
                ModulePanel modulePanel = (ModulePanel) p;
                modulePanel.modules.clear();
                modulePanel.modules.addAll(BlueZenith.getBlueZenith().getModuleManager().getModulesByCategory(modulePanel.category));
            }
        }
    }
    private Panel selectedPanel = null;
    public boolean mousePressed = false;
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if(mc.thePlayer == null) {
            BlueZenith.getBlueZenith().getMainMenu().drawScreen(-1, -1, 0);
            if(Preferences.useBlurInMenus)
            RenderUtil.blur(0, 0, width, height);
            else RenderUtil.rect(0, 0, width, height, new Color(90, 90, 90, 100));
        }

        if(Mouse.hasWheel()) {
            final float wheel = Mouse.getDWheel();

            if(wheel != 0)
            scroll += wheel > 0 ? 15 : -15;
        }

        final ClickGUI clickGUI = BlueZenith.getBlueZenith().getModuleManager().getAndCast(ClickGUI.class);

        if(clickGUI.backgroundAlpha.get() > 0 && mc.thePlayer != null)
            RenderUtil.rect(0, 0, width, height, new Color(1, 1, 1, clickGUI.backgroundAlpha.get()));

        if(clickGUI.blurBackground.get())
            RenderUtil.blur(0, 0, this.width, this.height);

   /*     RenderUtil.drawImage(new ResourceLocation("club/bluezenith/ui/dualipa.png"),
                this.width - 280,
                this.height - 300,
                  280,
                300, 1);*/

        mouseY -= scroll;

        GlStateManager.translate(0, scroll, 0);
        for (Panel p : panels) {
            p.drawPanel(mouseX, mouseY, partialTicks, selectedPanel == null);
            boolean d = i(mouseX, mouseY, p.x, p.y, p.x + p.width, p.y + p.mHeight);
            if(Mouse.isButtonDown(0) && ((d && selectedPanel == null) || selectedPanel == p)){
                if(!mousePressed){
                    selectedPanel = p;
                    if(!Keyboard.isKeyDown(KEY_LSHIFT)) {
                        p.prevX = (mouseX - p.x);
                    }
                    p.prevY = (mouseY - p.y);
                }
                if(!Keyboard.isKeyDown(KEY_LSHIFT)) {
                    p.x = mouseX - p.prevX;
                }
                p.y = mouseY - p.prevY;
            }else if(selectedPanel == p){
                selectedPanel = null;
            }
            if(d){
                if(!mousePressed && Mouse.isButtonDown(1)){
                    p.showContent = !p.showContent;
                    p.toggleSound();
                }
            }

            GlStateManager.resetColor();
        }
        GlStateManager.translate(0, -scroll, 0);
        mousePressed = Mouse.isButtonDown(0) || Mouse.isButtonDown(1);

        final ScaledResolution scaledResolution = new ScaledResolution(mc);
        final float height = (float) scaledResolution.getScaledHeight_double();
        final float width = (float) scaledResolution.getScaledWidth_double();
        if(shouldDoSearch) {
            textFieldCounter = (textFieldCounter + RenderUtil.delta * 0.003f) % 1;
        }
        rect(width - 150, height - 27, width - 2, height - 2, new Color(1, 1, 1, 150));
        rect(width - 130, height - 5, width - 4, height - 4.5f, -1);
        FontUtil.ICON_searchIcon.drawString(" ", width - 146, height - 18, -1);
        if("".equals(typedString)) {
            FontUtil.quickSandRegular31.drawString("Search..." + (textFieldCounter > 0.5 && shouldDoSearch ? "_" : ""), width - 130, height - 10 - FontUtil.quickSandRegular31.FONT_HEIGHT, new Color(190, 190, 190).getRGB());
        } else FontUtil.quickSandRegular31.drawString(typedString + (textFieldCounter > 0.5 && shouldDoSearch ? "_" : ""), width - 130, height - 10 - FontUtil.quickSandRegular31.FONT_HEIGHT, -1);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    public static boolean i(int mouseX, int mouseY, float x, float y, float x2, float y2){
        return mouseX >= x && mouseY >= y && mouseX <= x2 && mouseY <= y2;
    }
    protected void keyTyped(char typedChar, int keyCode) {
        if(shouldDoSearch) {
            if (keyCode == Keyboard.KEY_BACK) {
                if(typedString.length() <= 0) return;
                typedString = typedString.substring(0, typedString.length() - 1);
            } else if (keyCode == Keyboard.KEY_ESCAPE) {
                shouldDoSearch = false;
                typedString = "";
            } else if(keyCode == KEY_RETURN && typedString.isEmpty()) {
                shouldDoSearch = false;
                typedString = "";
            } else if(Character.isLetter(typedChar) || Character.isWhitespace(typedChar) && typedString.length() > 0 && !Character.isWhitespace(typedString.toCharArray()[0]) || Character.isDigit(typedChar)) {
                typedString += typedChar;
            }
            if(!shouldDoSearch) {
                panels.forEach(p -> {if(p instanceof ModulePanel)((ModulePanel)p).onCancelSearch();});
            }
        }
        for (Panel p : panels) {
            p.keyTyped(typedChar, keyCode);
            if(shouldDoSearch && p instanceof ModulePanel) {
                ((ModulePanel) p).onSearch();
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        panels.stream().filter(p -> p instanceof ModulePanel).forEach(panel -> ((ModulePanel)panel).onClick(mouseButton));
        final ScaledResolution sr = new ScaledResolution(mc);
        final float height = (float) sr.getScaledHeight_double();
        final float width = (float) sr.getScaledWidth_double();
        if(mouseButton == 0) {
            if(i(mouseX, mouseY, width - 150, height - 27, width - 2, height - 2)) {
                shouldDoSearch = !shouldDoSearch;
            } else shouldDoSearch = false;
        }
    }

    Integer h() {
        return null;
    }

    public ArrayList<Panel> getPanels(){
        return panels;
    }
    public Panel getPanel(String identifier) {
        return panels.stream().filter(p -> p.id.equalsIgnoreCase(identifier)).findFirst().orElse(null);
    }

    @Override
    public void onGuiClosed() {
        BlueZenith.getBlueZenith().getModuleManager().getModule(ClickGUI.class).setState(false);
    }
    public boolean doesGuiPauseGame(){
        return false;
    }
}