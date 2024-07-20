/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.cfg;

import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import ru.govno.client.Client;
import ru.govno.client.cfg.Config;
import ru.govno.client.cfg.ConfigManager;
import ru.govno.client.clickgui.ClickGuiScreen;
import ru.govno.client.module.modules.ClickGui;
import ru.govno.client.newfont.CFontRenderer;
import ru.govno.client.newfont.Fonts;
import ru.govno.client.utils.CTextField;
import ru.govno.client.utils.HoverUtils;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Render.AnimationUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;

public class GuiConfig
extends GuiScreen {
    public static Config selectedConfig = null;
    public static String loadedConfig = null;
    private int width;
    private int height;
    private int so;
    boolean dragging = false;
    int dragX;
    int dragY;
    int x = 200;
    int y = 200;
    int w = 200;
    int h = 200;
    boolean colose;
    boolean keyAddClicked;
    boolean keyRemoveClicked;
    boolean keyClearClicked;
    boolean keyLoadClicked;
    boolean keySaveClicked;
    boolean keyRenameClicked;
    boolean isClicked;
    CTextField textFieldSearch;
    public static AnimationUtils scrollY = new AnimationUtils(0.0f, 0.0f, 0.1f);
    public static AnimationUtils stringAnim = new AnimationUtils(0.0f, 0.0f, 0.1f);
    AnimationUtils keyCfgAnimScale = new AnimationUtils(1.0f, 1.0f, 0.15f);
    AnimationUtils keyCfgAnimRotate = new AnimationUtils(0.0f, 0.0f, 0.15f);
    boolean openCfgKey;
    boolean isClickedCfgKey;
    public static AnimationUtils cfgScale = new AnimationUtils(0.0f, 0.0f, 0.1f);
    private final float expandSelect = 0.0f;

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 1) {
            Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u041b\u0438\u0441\u0442 \u043a\u043e\u043d\u0444\u0438\u0433\u043e\u0432 \u043e\u0447\u0438\u0449\u0435\u043d.", false);
            ConfigManager.getLoadedConfigs().clear();
            Client.configManager.load();
        }
        if (selectedConfig != null) {
            if (button.id == 2) {
                if (Client.configManager.loadConfig(selectedConfig.getName())) {
                    loadedConfig = selectedConfig.getName();
                    Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u041a\u043e\u043d\u0444\u0438\u0433 [\u00a7l" + selectedConfig.getName() + "\u00a7r\u00a77] \u00a77\u0437\u0430\u0433\u0440\u0443\u0436\u0435\u043d.", false);
                } else {
                    Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u041a\u043e\u043d\u0444\u0438\u0433 [\u00a7l" + selectedConfig.getName() + "\u00a7r\u00a77] \u00a77\u043d\u0435\u0431\u044b\u043b \u0437\u0430\u0433\u0440\u0443\u0436\u0435\u043d.", false);
                }
            } else if (button.id == 3) {
                if (Client.configManager.saveConfig(selectedConfig.getName())) {
                    Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u041a\u043e\u043d\u0444\u0438\u0433 [\u00a7l" + selectedConfig.getName() + "\u00a7r\u00a77] \u00a77\u0441\u043e\u0445\u0440\u0430\u043d\u0451\u043d.", false);
                    ConfigManager.getLoadedConfigs().clear();
                    Client.configManager.load();
                } else {
                    Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u041a\u043e\u043d\u0444\u0438\u0433 [\u00a7l" + selectedConfig.getName() + "\u00a7r\u00a77] \u00a77\u043d\u0435\u0431\u044b\u043b \u0441\u043e\u0445\u0440\u0430\u043d\u0451\u043d.", false);
                }
            } else if (button.id == 4) {
                if (Client.configManager.deleteConfig(selectedConfig.getName())) {
                    Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u041a\u043e\u043d\u0444\u0438\u0433 [\u00a7l" + selectedConfig.getName() + "\u00a7r\u00a77] \u00a77\u0443\u0434\u0430\u043b\u0451\u043d.", false);
                } else {
                    Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u041a\u043e\u043d\u0444\u0438\u0433 [\u00a7l" + selectedConfig.getName() + "\u00a7r\u00a77] \u00a77\u043d\u0435\u0431\u044b\u043b \u0443\u0434\u0430\u043b\u0451\u043d.", false);
                }
            }
        }
        super.actionPerformed(button);
    }

    private boolean isHoveredConfig(int x, int y, int width, int height, int mouseX, int mouseY) {
        return MouseHelper.isHovered(x, y, x + width, y + height, mouseX, mouseY);
    }

    @Override
    public void initGui() {
        this.textFieldSearch = new CTextField(1, Fonts.minecraftia_18, 0, 0, this.w - 18, 15);
        ScaledResolution sr = new ScaledResolution(this.mc);
        this.width = sr.getScaledWidth() / 2;
        this.height = sr.getScaledHeight() / 2;
        GuiConfig.cfgScale.to = 0.0f;
        this.colose = false;
        super.initGui();
    }

    void cfgGuiCloser(int mouseX, int mouseY) {
        float y;
        ScaledResolution sr = new ScaledResolution(this.mc);
        if (this.openCfgKey) {
            GuiConfig.cfgScale.to = 1.0f;
            this.colose = true;
        }
        int size = 32;
        float x = sr.getScaledWidth() - size - 10;
        this.keyCfgAnimScale.to = HoverUtils.isHovered((int)x, (int)(y = 10.0f), (int)x + size, (int)y + size, mouseX, mouseY) ? 1.2f : 1.0f;
        float f = this.keyCfgAnimRotate.to = System.currentTimeMillis() % 1000L < 150L && HoverUtils.isHovered((int)x, (int)y, (int)x + size, (int)y + size, mouseX, mouseY) ? -15.0f : 0.0f;
        if (Mouse.isButtonDown(0)) {
            if (HoverUtils.isHovered((int)x, (int)y, (int)x + size, (int)y + size, mouseX, mouseY) && this.isClickedCfgKey) {
                this.openCfgKey = !this.openCfgKey;
            }
            this.isClickedCfgKey = false;
        } else {
            this.isClickedCfgKey = true;
        }
        GL11.glPushMatrix();
        RenderUtils.customScaledObject2D(x, y, size, size, this.keyCfgAnimScale.getAnim());
        RenderUtils.customRotatedObject2D(x, y, size, size, this.keyCfgAnimRotate.getAnim());
        RenderUtils.drawImageWithAlpha(new ResourceLocation("vegaline/ui/clickgui/config/buttons/images/cfgouticon.png"), x, y, size, size, ColorUtils.fadeColor(ColorUtils.getColor(255, 0, 55), ColorUtils.getColor(255, 255, 255), 0.2f), (int)(100.0f + (this.keyCfgAnimScale.getAnim() - 1.0f) * 5.0f * 55.0f));
        GL11.glPopMatrix();
        if ((double)cfgScale.getAnim() > 0.95) {
            this.colose = true;
            GuiConfig.cfgScale.to = 0.0f;
            this.openCfgKey = false;
        }
        if ((double)cfgScale.getAnim() > 0.05) {
            RenderUtils.drawRect(0.0, 0.0, 10000.0, 10000.0, ColorUtils.getColor(0, 0, 0, (int)(255.0f * cfgScale.getAnim())));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        boolean HoverkeyAdd = false;
        boolean HoverkeyRemove = false;
        boolean HoverkeyClear = false;
        boolean HoverkeyLoad = false;
        boolean HoverkeySave = false;
        boolean HoverkeyRename = false;
        boolean Hoveris = false;
        GuiConfig.stringAnim.to = this.textFieldSearch.isFocused() ? 1.0f : 0.0f;
        int extendKeys = 40;
        if (HoverUtils.isHovered(this.x + this.w - 16, this.y + this.h, this.x + this.w, this.y + this.h + 16, mouseX, mouseY) && this.isClicked && Mouse.isButtonDown(0) && this.keyAddClicked) {
            this.keyAddClicked = false;
            Client.configManager.saveConfig(this.textFieldSearch.getText());
            Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u041a\u043e\u043d\u0444\u0438\u0433 [\u00a7l" + this.textFieldSearch.getText() + "\u00a7r\u00a77] \u00a77\u0441\u043e\u0445\u0440\u0430\u043d\u0451\u043d.", false);
            ConfigManager.getLoadedConfigs().clear();
            Client.configManager.load();
            loadedConfig = this.textFieldSearch.getText();
            selectedConfig = Client.configManager.findConfig(this.textFieldSearch.getText());
            this.textFieldSearch.setText("");
        } else if (HoverUtils.isHovered(this.x + this.w - 32, this.y + this.h, this.x + this.w - 16, this.y + this.h + 16, mouseX, mouseY) && this.isClicked && Mouse.isButtonDown(0) && this.keyAddClicked) {
            this.keyAddClicked = false;
            this.textFieldSearch.setText("");
        }
        if (this.keyRemoveClicked) {
            if (selectedConfig != null) {
                Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u043a\u043e\u043d\u0444\u0438\u0433 [\u00a7l" + selectedConfig.getName() + "\u00a7r\u00a77] \u00a77\u0443\u0434\u0430\u043b\u0451\u043d.", false);
                Client.configManager.deleteConfig(selectedConfig.getName());
                selectedConfig = null;
            } else {
                Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u0432\u044b\u0431\u0435\u0440\u0438 \u043a\u043e\u043d\u0444\u0438\u0433 \u0434\u043b\u044f \u0434\u0435\u0439\u0441\u0442\u0432\u0438\u0439.", false);
            }
            this.keyRemoveClicked = false;
        }
        if (this.keyLoadClicked) {
            if (selectedConfig != null) {
                Client.configManager.loadConfig(selectedConfig.getName());
                loadedConfig = selectedConfig.getName();
                Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u043a\u043e\u043d\u0444\u0438\u0433 [\u00a7l" + selectedConfig.getName() + "\u00a7r\u00a77] \u00a77\u0437\u0430\u0433\u0440\u0443\u0436\u0435\u043d.", false);
                selectedConfig = null;
            } else {
                Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u0432\u044b\u0431\u0435\u0440\u0438 \u043a\u043e\u043d\u0444\u0438\u0433 \u0434\u043b\u044f \u0434\u0435\u0439\u0441\u0442\u0432\u0438\u0439.", false);
            }
            this.keyLoadClicked = false;
        }
        if (this.keySaveClicked) {
            if (selectedConfig != null) {
                Client.configManager.saveConfig(selectedConfig.getName());
                Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u043a\u043e\u043d\u0444\u0438\u0433 [\u00a7l" + selectedConfig.getName() + "\u00a7r\u00a77] \u00a77\u0441\u043e\u0445\u0440\u0430\u043d\u0451\u043d.", false);
                selectedConfig = null;
            } else {
                Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u0432\u044b\u0431\u0435\u0440\u0438 \u043a\u043e\u043d\u0444\u0438\u0433 \u0434\u043b\u044f \u0434\u0435\u0439\u0441\u0442\u0432\u0438\u0439.", false);
            }
            this.keySaveClicked = false;
        }
        if (this.keyClearClicked) {
            boolean hasCfg;
            boolean bl = hasCfg = Client.configManager.getContents().size() != 0;
            if (hasCfg) {
                selectedConfig = null;
                loadedConfig = "";
                for (Config cfgs : Client.configManager.getContents()) {
                    if (cfgs == null) continue;
                    if (Client.configManager.deleteConfig(cfgs.getName())) {
                        Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u043a\u043e\u043d\u0444\u0438\u0433 [\u00a7l" + cfgs.getName() + "\u00a7r\u00a77] \u0443\u0434\u0430\u043b\u0451\u043d.", false);
                        return;
                    }
                    Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u0432\u044b\u0431\u0435\u0440\u0438 \u043a\u043e\u043d\u0444\u0438\u0433 \u0434\u043b\u044f \u0434\u0435\u0439\u0441\u0442\u0432\u0438\u0439.", false);
                    return;
                }
            } else {
                Client.msg("\u00a7a\u00a7lConfigs:\u00a7r \u00a77\u0443 \u0432\u0430\u0441 \u043d\u0435\u0442 \u043d\u0438 \u043e\u0434\u043d\u043e\u0433\u043e \u043a\u043e\u043d\u0444\u0438\u0433\u0430.", false);
            }
            this.keyClearClicked = false;
        }
        if (HoverUtils.isHovered(this.x + this.h - 96, this.y + this.h - 24 - extendKeys, this.x + this.h - 8, this.y + this.h - 8 - extendKeys, mouseX, mouseY)) {
            HoverkeyRemove = true;
            if (this.isClicked && Mouse.isButtonDown(0)) {
                this.keyRemoveClicked = true;
            }
        }
        if (HoverUtils.isHovered(this.x + this.h - 96, this.y + this.h - 24 - (extendKeys += 21), this.x + this.h - 8, this.y + this.h - 8 - extendKeys, mouseX, mouseY)) {
            HoverkeyClear = true;
            if (this.isClicked && Mouse.isButtonDown(0)) {
                this.keyClearClicked = true;
            }
        }
        if (HoverUtils.isHovered(this.x + this.h - 96, this.y + this.h - 24 - (extendKeys += 21), this.x + this.h - 8, this.y + this.h - 8 - extendKeys, mouseX, mouseY)) {
            HoverkeySave = true;
            if (this.isClicked && Mouse.isButtonDown(0)) {
                this.keySaveClicked = true;
            }
        }
        if (HoverUtils.isHovered(this.x + this.h - 96, this.y + this.h - 24 - (extendKeys += 21), this.x + this.h - 8, this.y + this.h - 8 - extendKeys, mouseX, mouseY)) {
            HoverkeyLoad = true;
            if (this.isClicked && Mouse.isButtonDown(0)) {
                this.keyLoadClicked = true;
            }
        }
        if (HoverUtils.isHovered(this.x + this.h - 96, this.y + this.h - 24 - (extendKeys += 21), this.x + this.h - 8, this.y + this.h - 8 - extendKeys, mouseX, mouseY)) {
            HoverkeyAdd = true;
            if (this.isClicked && Mouse.isButtonDown(0)) {
                this.keyAddClicked = true;
            }
        }
        boolean bl = this.isClicked = !Mouse.isButtonDown(0);
        if (this.dragging) {
            this.x = mouseX - this.dragX;
            this.y = mouseY - this.dragY;
        } else {
            this.dragX = mouseX - this.x;
            this.dragY = mouseY - this.y;
        }
        if (HoverUtils.isHovered(this.x, this.y - 24, this.x + this.w, this.y, mouseX, mouseY) && Mouse.isButtonDown(0)) {
            this.dragging = true;
        }
        ScaledResolution sr = new ScaledResolution(this.mc);
        this.textFieldSearch.xPosition = this.x;
        this.textFieldSearch.yPosition = this.y + this.h;
        this.textFieldSearch.setMaxStringLength(27);
        this.textFieldSearch.setFocused(this.keyAddClicked);
        float stored = stringAnim.getAnim();
        this.textFieldSearch.drawTextBox(Fonts.mntsb_16, stored);
        if (HoverUtils.isHovered(this.x, this.y, this.x + 87, this.y + this.h, mouseX, mouseY)) {
            float wheel = (float)Mouse.getDWheel() / 7.5f;
            this.so = (int)((float)this.so - wheel);
            this.so = MathUtils.clamp(this.so, -this.h + 35, (Client.configManager.getContents().size() + 2) * 20 - this.h - 5);
            GuiConfig.scrollY.to = this.so;
        }
        RenderUtils.drawAlphedRect(this.x, this.y, this.x + this.w, this.y + this.h, ColorUtils.getColor(0, 0, 11, 90));
        RenderUtils.drawRect(this.x - 4, this.y - 24, this.x + this.w + 4, this.y, ColorUtils.getColor(0, 0, 11, 170));
        RenderUtils.drawFullGradientRectPro(this.x - 4, this.y, this.x, (float)(this.y + this.h) + 16.0f * stored, 0, ColorUtils.getColor(0, 0, 11, 90), ColorUtils.getColor(0, 0, 11, 90), ColorUtils.getColor(0, 0, 11, 90), false);
        RenderUtils.drawFullGradientRectPro(this.x + this.w, this.y, this.x + this.w + 4, (float)(this.y + this.h) + 16.0f * stored, ColorUtils.getColor(0, 0, 11, 90), 0, ColorUtils.getColor(0, 0, 11, 90), ColorUtils.getColor(0, 0, 11, 90), false);
        RenderUtils.drawAlphedRect(this.x, this.y + this.h, this.x + this.w, (float)(this.y + this.h) + 16.0f * stored, ColorUtils.getColor(0, 0, 11, 170));
        RenderUtils.drawAlphedSideways(this.x + this.w / 2 - 7, this.y, this.x + this.w / 2, this.y + this.h, 0, ColorUtils.getColor(0, 9, 11, 55));
        RenderUtils.drawAlphedSideways(this.x + this.w / 2 - 14, this.y, this.x + this.w / 2 - 7, this.y + this.h, ColorUtils.getColor(0, 9, 11, 55), 0);
        RenderUtils.drawAlphedRect(this.x + this.w - 15, this.y + this.h + 1, this.x + this.w - 1, this.y + this.h + 15, ColorUtils.getColor(40, 120, 255, (int)((float)(HoverUtils.isHovered(this.x + this.w - 16, this.y + this.h, this.x + this.w, this.y + this.h + 16, mouseX, mouseY) ? 70 : 40) * stored)));
        RenderUtils.fixShadows();
        if (140.0f * MathUtils.clamp(stored, 0.0f, 1.0f) >= 26.0f) {
            Fonts.minecraftia_20.drawString("+", this.x + this.w - 11, this.y + this.h + 3, ColorUtils.getColor(40, 120, 255, (int)((float)(HoverUtils.isHovered(this.x + this.w - 16, this.y + this.h, this.x + this.w, this.y + this.h + 16, mouseX, mouseY) ? 190 : 140) * MathUtils.clamp(stored, 0.0f, 1.0f))));
        }
        RenderUtils.drawAlphedRect(this.x + this.w - 31, this.y + this.h + 1, this.x + this.w - 17, this.y + this.h + 15, ColorUtils.getColor(40, 120, 255, (int)((float)(HoverUtils.isHovered(this.x + this.w - 32, this.y + this.h, this.x + this.w - 16, this.y + this.h + 16, mouseX, mouseY) ? 70 : 40) * stored)));
        RenderUtils.fixShadows();
        if (140.0f * MathUtils.clamp(stored, 0.0f, 1.0f) >= 26.0f) {
            Fonts.minecraftia_20.drawString("x", this.x + this.w - 27, this.y + this.h + 2, ColorUtils.getColor(40, 120, 255, (int)((float)(HoverUtils.isHovered(this.x + this.w - 32, this.y + this.h, this.x + this.w - 16, this.y + this.h + 16, mouseX, mouseY) ? 190 : 140) * MathUtils.clamp(stored, 0.0f, 1.0f))));
        }
        RenderUtils.drawAlphedRect((float)this.x - 2.0f * (1.0f - stored), this.y + this.h, (float)(this.x + this.w) + 2.0f * (1.0f - stored), (float)(this.y + this.h) + 3.0f * (1.0f - stored), ColorUtils.getColor(0, 9, 11, (int)(170.0f * (1.0f - stored))));
        Fonts.comfortaaBold_18.drawStringWithOutline("Configs", this.x + 4, this.y - 15, -1);
        int yDist = 20;
        Fonts.comfortaaBold_18.drawString("\u00a77Loaded cfg: [\u00a7l" + (loadedConfig == null ? "null" : loadedConfig) + "\u00a7r\u00a77]", this.x + this.w - 2 - Fonts.comfortaaBold_18.getStringWidth("\u00a77Loaded cfg: [\u00a7l" + (loadedConfig == null ? "null" : loadedConfig) + "\u00a7r\u00a77]"), this.y - 16, ColorUtils.getColor(225, 225, 225));
        GL11.glEnable(3089);
        RenderUtils.scissorRect(this.x + 2, this.y + 1, this.x + 82, this.y + this.h - 1);
        for (Config config : Client.configManager.getContents()) {
            int color;
            if (config == null) continue;
            int aplus = 0;
            if (this.isHoveredConfig(this.x + 4, this.y + yDist - (int)scrollY.getAnim() - 4, 76, 15, mouseX, mouseY) && HoverUtils.isHovered(this.x, this.y, this.x + this.w, this.y + this.h, mouseX, mouseY)) {
                color = ColorUtils.TwoColoreffect(255, 255, 255, 150, 150, 150, (double)Math.abs(System.currentTimeMillis() / 4L) / 100.1275);
                aplus += 90;
                if (Mouse.isButtonDown(0)) {
                    selectedConfig = new Config(config.getName());
                }
            } else {
                color = ColorUtils.getColor(150, 150, 150);
            }
            RenderUtils.drawRect(this.x + 5, (float)(this.y + yDist) - scrollY.getAnim() - 3.0f, this.x + 80, (float)(this.y + 9 + yDist) - scrollY.getAnim(), ColorUtils.getColor(0, 100, 140, 100 + aplus));
            if (selectedConfig != null && config.getName().equals(selectedConfig.getName())) {
                RenderUtils.drawRect(this.x + 4, (float)(this.y + yDist) - scrollY.getAnim() - 4.0f, this.x + 81, (float)(this.y + 10 + yDist) - scrollY.getAnim(), ColorUtils.getColor(255, 255, 255, 60));
            }
            Fonts.comfortaaBold_15.drawStringWithOutline(config.getName().isEmpty() ? "Unknown name" : config.getName(), this.x + 8, (float)(this.y + 1 + yDist) - scrollY.getAnim(), color);
            yDist += 15;
        }
        GL11.glDisable(3089);
        RenderUtils.drawFullGradientRectPro(this.x - 4, this.y, this.x + this.w + 4, this.y + 10, 0, 0, ColorUtils.getColor(0, 0, 11, 170), ColorUtils.getColor(0, 0, 11, 170), false);
        RenderUtils.drawFullGradientRectPro(this.x, this.y + this.h - 10, this.x + this.w, this.y + this.h, ColorUtils.getColor(0, 0, 11, 170), ColorUtils.getColor(0, 0, 11, 170), 0, 0, false);
        int extendKeys2 = 40;
        RenderUtils.drawAlphedRect(this.x + this.h - 96, this.y + this.h - 24 - extendKeys2, this.x + this.h - 8, this.y + this.h - 8 - extendKeys2, ColorUtils.getColor(0, 100, 140, 100 + (HoverkeyRemove ? 90 : 0)));
        CFontRenderer font1 = HoverkeyRemove ? Fonts.mntsb_18 : Fonts.mntsb_16;
        font1.drawStringWithOutline("Remove", this.x + this.h - 96 + (this.x + this.h - 8 - (this.x + this.h - 96)) / 2 - font1.getStringWidth("Remove") / 2, (float)(this.y + this.h - 18 - extendKeys2) - (HoverkeyRemove ? 0.5f : 0.0f), -1);
        RenderUtils.drawAlphedRect(this.x + this.h - 96, this.y + this.h - 24 - (extendKeys2 += 21), this.x + this.h - 8, this.y + this.h - 8 - extendKeys2, ColorUtils.getColor(0, 100, 140, 100 + (HoverkeyClear ? 90 : 0)));
        CFontRenderer font2 = HoverkeyClear ? Fonts.mntsb_18 : Fonts.mntsb_16;
        font2.drawStringWithOutline("Clear", this.x + this.h - 96 + (this.x + this.h - 8 - (this.x + this.h - 96)) / 2 - font2.getStringWidth("Clear") / 2, (float)(this.y + this.h - 18 - extendKeys2) - (HoverkeyClear ? 0.5f : 0.0f), -1);
        RenderUtils.drawAlphedRect(this.x + this.h - 96, this.y + this.h - 24 - (extendKeys2 += 21), this.x + this.h - 8, this.y + this.h - 8 - extendKeys2, ColorUtils.getColor(0, 100, 140, 100 + (HoverkeySave ? 90 : 0)));
        CFontRenderer font3 = HoverkeySave ? Fonts.mntsb_18 : Fonts.mntsb_16;
        font3.drawStringWithOutline("Save", this.x + this.h - 96 + (this.x + this.h - 8 - (this.x + this.h - 96)) / 2 - font3.getStringWidth("Save") / 2, (float)(this.y + this.h - 18 - extendKeys2) - (HoverkeySave ? 0.5f : 0.0f), -1);
        RenderUtils.drawAlphedRect(this.x + this.h - 96, this.y + this.h - 24 - (extendKeys2 += 21), this.x + this.h - 8, this.y + this.h - 8 - extendKeys2, ColorUtils.getColor(0, 100, 140, 100 + (HoverkeyLoad ? 90 : 0)));
        CFontRenderer font4 = HoverkeyLoad ? Fonts.mntsb_18 : Fonts.mntsb_16;
        font4.drawStringWithOutline("Load", this.x + this.h - 96 + (this.x + this.h - 8 - (this.x + this.h - 96)) / 2 - font4.getStringWidth("Load") / 2, (float)(this.y + this.h - 18 - extendKeys2) - (HoverkeyLoad ? 0.5f : 0.0f), -1);
        RenderUtils.drawAlphedRect(this.x + this.h - 96, this.y + this.h - 24 - (extendKeys2 += 21), this.x + this.h - 8, this.y + this.h - 8 - extendKeys2, ColorUtils.getColor(0, 100, 140, 100 + (HoverkeyAdd ? 90 : 0)));
        CFontRenderer font6 = HoverkeyAdd ? Fonts.mntsb_18 : Fonts.mntsb_16;
        font6.drawStringWithOutline("Add", this.x + this.h - 96 + (this.x + this.h - 8 - (this.x + this.h - 96)) / 2 - font6.getStringWidth("Add") / 2, (float)(this.y + this.h - 18 - extendKeys2) - (HoverkeyAdd ? 0.5f : 0.0f), -1);
        this.cfgGuiCloser(mouseX, mouseY);
        if ((double)cfgScale.getAnim() > 0.95 && this.colose) {
            ClickGuiScreen.cfgScale.setAnim(0.8f);
            this.mc.displayGuiScreen(Client.clickGuiScreen);
            ClickGui.instance.toggleSilent(true);
            this.colose = false;
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if (state == 0) {
            this.dragging = false;
        }
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void updateScreen() {
        this.textFieldSearch.updateCursorCounter();
        this.textFieldSearch.setFocused(this.keyAddClicked);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        this.textFieldSearch.textboxKeyTyped(typedChar, keyCode);
        if (keyCode == 1) {
            this.colose = true;
            GuiConfig.cfgScale.to = 1.0f;
        }
        try {
            super.keyTyped(typedChar, keyCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.keyTyped(typedChar, keyCode);
    }
}

