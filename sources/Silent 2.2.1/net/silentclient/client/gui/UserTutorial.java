package net.silentclient.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.gui.elements.Button;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.lite.GuiNews;
import net.silentclient.client.gui.lite.clickgui.utils.MouseUtils;
import net.silentclient.client.gui.lite.clickgui.utils.RenderUtils;
import net.silentclient.client.gui.silentmainmenu.MainMenuConcept;
import net.silentclient.client.gui.util.RenderUtil;
import net.silentclient.client.utils.FileUtils;
import net.silentclient.client.utils.MouseCursorHandler;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class UserTutorial extends SilentScreen {
    private int step = 1;
    private ArrayList<String> configs = new ArrayList<>();
    private int configIndex = 0;

    @Override
    public void initGui() {
        super.initGui();
        defaultCursor = false;
        this.configs.clear();
        this.buttonList.clear();
        Client.backgroundPanorama.updateWidthHeight(this.width, this.height);
        int blockY = this.height / 2 - 90;
        int blockX = this.width / 2 - 175;
        if(blockY < 40) {
            blockY = 40;
        }
        this.buttonList.add(new Button(1, blockX + (350 / 2) - 50, blockY + 180 - 35, 100, 20, "Get Started"));
        this.configs.add("BedWars");
        this.configs.add("Minigames");
        this.configs.add("PvP");
        this.configs.add("Survival");
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        MouseCursorHandler.CursorType cursorType = getCursor(silentInputs, buttonList);
        GlStateManager.disableAlpha();
        Client.backgroundPanorama.renderSkybox(mouseX, mouseY, partialTicks);
        GlStateManager.enableAlpha();
        this.drawGradientRect(0, 0, this.width, this.height, new Color(0, 0, 0, 127).getRGB(), new Color(0, 0, 0, 200).getRGB());
        RenderUtil.drawImage(new ResourceLocation("silentclient/logos/logo.png"), this.width / 2 - 48.8F, 15, 97.7F, 19);
        int blockY = this.height / 2 - 90;
        int blockX = this.width / 2 - 175;
        if(blockY < 40) {
            blockY = 40;
        }
        RenderUtils.drawRect(blockX, blockY, 350, 180, new Color(20, 20, 20).getRGB());
        super.drawScreen(mouseX, mouseY, partialTicks);
        if(MouseUtils.isInside(mouseX, mouseY, blockX + (350 / 2) - (Client.getInstance().getSilentFontRenderer().getStringWidth("Skip Tutorial", 10, SilentFontRenderer.FontType.TITLE) / 2), blockY + 180 - 12, Client.getInstance().getSilentFontRenderer().getStringWidth("Skip Tutorial", 10, SilentFontRenderer.FontType.TITLE), 10)) {
            cursorType = MouseCursorHandler.CursorType.POINTER;
        }
        Client.getInstance().getSilentFontRenderer().drawCenteredString("Skip Tutorial", blockX + (350 / 2), blockY + 180 - 12, 10, SilentFontRenderer.FontType.TITLE);
        switch (step) {
            case 1:
                this.buttonList.get(0).displayString = "Get Started";

                Client.getInstance().getSilentFontRenderer().drawCenteredString("Welcome to Silent Client 2.0", blockX + (350 / 2), blockY + 3, 16, SilentFontRenderer.FontType.TITLE);

                RenderUtil.drawImage(new ResourceLocation("silentclient/tutorial/features/feature1.png"), blockX + 132 - (86) - 10, blockY + 22, 86, 120);
                RenderUtil.drawImage(new ResourceLocation("silentclient/tutorial/features/feature2.png"), blockX + 132, blockY + 22, 86, 120);
                RenderUtil.drawImage(new ResourceLocation("silentclient/tutorial/features/feature3.png"), blockX + 132 + 86 + 10, blockY + 22, 86, 120);
                break;
            case 2:
                this.buttonList.get(0).displayString = "Next";

                Client.getInstance().getSilentFontRenderer().drawCenteredString("Choose a version of Silent Client", blockX + (350 / 2), blockY + 3, 16, SilentFontRenderer.FontType.TITLE);
                if(MouseUtils.isInside(mouseX, mouseY, blockX + 9, blockY + 29, 161, 102) || MouseUtils.isInside(mouseX, mouseY, blockX + 180, blockY + 29, 161, 102)) {
                    cursorType = MouseCursorHandler.CursorType.POINTER;
                }
                if(!Client.getInstance().getGlobalSettings().isLite()) {
                    RenderUtils.drawRect(blockX + 9, blockY + 29, 161, 102, -1);
                }
                RenderUtil.drawImage(new ResourceLocation("silentclient/tutorial/version/regular.png"), blockX + 10, blockY + 30, 159, 100);

                if(Client.getInstance().getGlobalSettings().isLite()) {
                    RenderUtils.drawRect(blockX + 180, blockY + 29, 161, 102, -1);
                }
                RenderUtil.drawImage(new ResourceLocation("silentclient/tutorial/version/lite.png"), blockX + 181, blockY + 30, 159, 100);

                break;
            case 3:
                this.buttonList.get(0).displayString = "Next";

                Client.getInstance().getSilentFontRenderer().drawCenteredString("Choose a config preset", blockX + (350 / 2), blockY + 3, 16, SilentFontRenderer.FontType.TITLE);
                if(MouseUtils.isInside(mouseX, mouseY, blockX + 60, blockY + 35 + 50 - 10, 20, 20) || MouseUtils.isInside(mouseX, mouseY, blockX + 95 + 159 + 15, blockY + 35 + 50 - 10, 20, 20)) {
                    cursorType = MouseCursorHandler.CursorType.POINTER;
                }
                RenderUtil.drawImage(new ResourceLocation("silentclient/icons/back.png"), blockX + 60, blockY + 35 + 50 - 10, 20, 20);
                RenderUtil.drawImage(new ResourceLocation("silentclient/icons/next.png"), blockX + 95 + 159 + 15, blockY + 35 + 50 - 10, 20, 20);

                Client.getInstance().getSilentFontRenderer().drawCenteredString(this.configs.get(configIndex), blockX + (350 / 2), blockY + 20, 12, SilentFontRenderer.FontType.TITLE);
                RenderUtil.drawImage(new ResourceLocation(String.format("silentclient/configs/screenshots/%s.png", this.configs.get(configIndex))), blockX + 95, blockY + 35, 159, 100);

                break;
        }

        Client.getInstance().getMouseCursorHandler().enableCursor(cursorType);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        if(button.id == 1) {
            switch (step) {
                case 1:
                    step = 2;
                    break;
                case 2:
                    step = 3;
                    break;
                case 3:
                    try {
                        FileUtils.exportResource(String.format("/assets/minecraft/silentclient/configs/%s.txt", this.configs.get(configIndex)), String.format(Client.getInstance().configDir.toString() + "/%s (Preset).txt", this.configs.get(configIndex)));
                        Client.getInstance().getConfigManager().loadConfig(String.format("%s (Preset).txt", this.configs.get(configIndex)));
                    } catch (Exception e) {
                        Client.logger.catching(e);
                    }
                    Client.getInstance().getGlobalSettings().setDisplayedTutorial(true);
                    Client.getInstance().getGlobalSettings().save();
                    mc.displayGuiScreen(Client.getInstance().getGlobalSettings().lite ? new GuiNews() : new MainMenuConcept());
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        int blockY = this.height / 2 - 90;
        int blockX = this.width / 2 - 175;
        if(blockY < 40) {
            blockY = 40;
        }
        if(MouseUtils.isInside(mouseX, mouseY, blockX + (350 / 2) - (Client.getInstance().getSilentFontRenderer().getStringWidth("Skip Tutorial", 10, SilentFontRenderer.FontType.TITLE) / 2), blockY + 180 - 12, Client.getInstance().getSilentFontRenderer().getStringWidth("Skip Tutorial", 10, SilentFontRenderer.FontType.TITLE), 10)) {
            Client.getInstance().getGlobalSettings().setDisplayedTutorial(true);
            Client.getInstance().getGlobalSettings().save();
            mc.displayGuiScreen(Client.getInstance().getGlobalSettings().lite ? new GuiNews() : new MainMenuConcept());
            return;
        }

        switch (step) {
            case 2:
                if(MouseUtils.isInside(mouseX, mouseY, blockX + 10, blockY + 30, 159, 100)) {
                    Client.getInstance().getGlobalSettings().setLite(false);
                    break;
                }
                if(MouseUtils.isInside(mouseX, mouseY, blockX + 181, blockY + 30, 159, 100)) {
                    Client.getInstance().getGlobalSettings().setLite(true);
                    break;
                }
                break;
            case 3:
                if(MouseUtils.isInside(mouseX, mouseY, blockX + 60, blockY + 35 + 50 - 10, 20, 20)) {
                    if(configIndex == 0) {
                        this.configIndex = this.configs.size() - 1;
                    } else {
                        this.configIndex -= 1;
                    }
                    break;
                }

                if(MouseUtils.isInside(mouseX, mouseY, blockX + 95 + 159 + 15, blockY + 35 + 50 - 10, 20, 20)) {
                    if(configIndex == this.configs.size() - 1) {
                        this.configIndex = 0;
                    } else {
                        this.configIndex += 1;
                    }
                    break;
                }

                break;
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        Client.backgroundPanorama.tickPanorama();
    }
}
