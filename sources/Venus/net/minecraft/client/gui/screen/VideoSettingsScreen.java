/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.GPUWarningScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.GPUWarning;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GraphicsFanciness;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.optifine.Config;
import net.optifine.Lang;
import net.optifine.gui.GuiAnimationSettingsOF;
import net.optifine.gui.GuiButtonOF;
import net.optifine.gui.GuiDetailSettingsOF;
import net.optifine.gui.GuiOtherSettingsOF;
import net.optifine.gui.GuiPerformanceSettingsOF;
import net.optifine.gui.GuiQualitySettingsOF;
import net.optifine.gui.GuiScreenButtonOF;
import net.optifine.gui.GuiScreenOF;
import net.optifine.gui.TooltipManager;
import net.optifine.gui.TooltipProviderOptions;
import net.optifine.shaders.gui.GuiShaders;
import net.optifine.util.GuiUtils;
import org.lwjgl.glfw.GLFW;

public class VideoSettingsScreen
extends GuiScreenOF {
    private Screen parentGuiScreen;
    private GameSettings guiGameSettings;
    private static AbstractOption[] videoOptions = new AbstractOption[]{AbstractOption.GRAPHICS, AbstractOption.RENDER_DISTANCE, AbstractOption.AO, AbstractOption.FRAMERATE_LIMIT, AbstractOption.AO_LEVEL, AbstractOption.VIEW_BOBBING, AbstractOption.GUI_SCALE, AbstractOption.ENTITY_SHADOWS, AbstractOption.GAMMA, AbstractOption.ATTACK_INDICATOR, AbstractOption.DYNAMIC_LIGHTS, AbstractOption.DYNAMIC_FOV};
    private GPUWarning field_241604_x_;
    private static final ITextComponent field_241598_c_ = new TranslationTextComponent("options.graphics.fabulous").mergeStyle(TextFormatting.ITALIC);
    private static final ITextComponent field_241599_p_ = new TranslationTextComponent("options.graphics.warning.message", field_241598_c_, field_241598_c_);
    private static final ITextComponent field_241600_q_ = new TranslationTextComponent("options.graphics.warning.title").mergeStyle(TextFormatting.RED);
    private static final ITextComponent field_241601_r_ = new TranslationTextComponent("options.graphics.warning.accept");
    private static final ITextComponent field_241602_s_ = new TranslationTextComponent("options.graphics.warning.cancel");
    private static final ITextComponent field_241603_t_ = new StringTextComponent("\n");
    private TooltipManager tooltipManager = new TooltipManager(this, new TooltipProviderOptions());
    private List<Widget> buttonList = this.buttons;
    private Widget buttonGuiScale;

    public VideoSettingsScreen(Screen screen, GameSettings gameSettings) {
        super(new TranslationTextComponent("options.videoTitle"));
        this.parentGuiScreen = screen;
        this.guiGameSettings = gameSettings;
        this.field_241604_x_ = this.parentGuiScreen.minecraft.getGPUWarning();
        this.field_241604_x_.func_241702_i_();
        if (this.guiGameSettings.graphicFanciness == GraphicsFanciness.FABULOUS) {
            this.field_241604_x_.func_241698_e_();
        }
    }

    @Override
    public void init() {
        int n;
        this.buttonList.clear();
        for (n = 0; n < videoOptions.length; ++n) {
            AbstractOption abstractOption = videoOptions[n];
            if (abstractOption == null) continue;
            int n2 = this.width / 2 - 155 + n % 2 * 160;
            int n3 = this.height / 6 + 21 * (n / 2) - 12;
            Widget widget = this.addButton(abstractOption.createWidget(this.minecraft.gameSettings, n2, n3, 150));
            if (abstractOption != AbstractOption.GUI_SCALE) continue;
            this.buttonGuiScale = widget;
        }
        n = this.height / 6 + 21 * (videoOptions.length / 2) - 12;
        int n4 = 0;
        n4 = this.width / 2 - 155 + 0;
        this.addButton(new GuiScreenButtonOF(231, n4, n, Lang.get("of.options.shaders")));
        n4 = this.width / 2 - 155 + 160;
        this.addButton(new GuiScreenButtonOF(202, n4, n, Lang.get("of.options.quality")));
        n4 = this.width / 2 - 155 + 0;
        this.addButton(new GuiScreenButtonOF(201, n4, n += 21, Lang.get("of.options.details")));
        n4 = this.width / 2 - 155 + 160;
        this.addButton(new GuiScreenButtonOF(212, n4, n, Lang.get("of.options.performance")));
        n4 = this.width / 2 - 155 + 0;
        this.addButton(new GuiScreenButtonOF(211, n4, n += 21, Lang.get("of.options.animations")));
        n4 = this.width / 2 - 155 + 160;
        this.addButton(new GuiScreenButtonOF(222, n4, n, Lang.get("of.options.other")));
        n += 21;
        this.addButton(new GuiButtonOF(200, this.width / 2 - 100, this.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
    }

    @Override
    protected void actionPerformed(Widget widget) {
        if (widget == this.buttonGuiScale) {
            this.updateGuiScale();
        }
        this.checkFabulousWarning();
        if (widget instanceof GuiButtonOF) {
            GuiButtonOF guiButtonOF = (GuiButtonOF)widget;
            this.actionPerformed(guiButtonOF, 1);
        }
    }

    private void checkFabulousWarning() {
        if (this.field_241604_x_.func_241700_g_()) {
            String string;
            String string2;
            ArrayList<ITextProperties> arrayList = Lists.newArrayList(field_241599_p_, field_241603_t_);
            String string3 = this.field_241604_x_.func_241703_j_();
            if (string3 != null) {
                arrayList.add(field_241603_t_);
                arrayList.add(new TranslationTextComponent("options.graphics.warning.renderer", string3).mergeStyle(TextFormatting.GRAY));
            }
            if ((string2 = this.field_241604_x_.func_241705_l_()) != null) {
                arrayList.add(field_241603_t_);
                arrayList.add(new TranslationTextComponent("options.graphics.warning.vendor", string2).mergeStyle(TextFormatting.GRAY));
            }
            if ((string = this.field_241604_x_.func_241704_k_()) != null) {
                arrayList.add(field_241603_t_);
                arrayList.add(new TranslationTextComponent("options.graphics.warning.version", string).mergeStyle(TextFormatting.GRAY));
            }
            this.minecraft.displayGuiScreen(new GPUWarningScreen(field_241600_q_, arrayList, ImmutableList.of(new GPUWarningScreen.Option(field_241601_r_, this::lambda$checkFabulousWarning$0), new GPUWarningScreen.Option(field_241602_s_, this::lambda$checkFabulousWarning$1))));
        }
    }

    @Override
    protected void actionPerformedRightClick(Widget widget) {
        if (widget == this.buttonGuiScale) {
            AbstractOption.GUI_SCALE.setValueIndex(this.guiGameSettings, -1);
            this.updateGuiScale();
        }
    }

    private void updateGuiScale() {
        this.minecraft.updateWindowSize();
        MainWindow mainWindow = this.minecraft.getMainWindow();
        int n = GuiUtils.getWidth(this.buttonGuiScale);
        int n2 = GuiUtils.getHeight(this.buttonGuiScale);
        int n3 = this.buttonGuiScale.x + (n - n2);
        int n4 = this.buttonGuiScale.y + n2 / 2;
        GLFW.glfwSetCursorPos(mainWindow.getHandle(), (double)n3 * mainWindow.getGuiScaleFactor(), (double)n4 * mainWindow.getGuiScaleFactor());
    }

    private void actionPerformed(GuiButtonOF guiButtonOF, int n) {
        if (guiButtonOF.active) {
            GuiScreenOF guiScreenOF;
            if (guiButtonOF.id == 200) {
                this.minecraft.gameSettings.saveOptions();
                this.minecraft.displayGuiScreen(this.parentGuiScreen);
            }
            if (guiButtonOF.id == 201) {
                this.minecraft.gameSettings.saveOptions();
                guiScreenOF = new GuiDetailSettingsOF(this, this.guiGameSettings);
                this.minecraft.displayGuiScreen(guiScreenOF);
            }
            if (guiButtonOF.id == 202) {
                this.minecraft.gameSettings.saveOptions();
                guiScreenOF = new GuiQualitySettingsOF(this, this.guiGameSettings);
                this.minecraft.displayGuiScreen(guiScreenOF);
            }
            if (guiButtonOF.id == 211) {
                this.minecraft.gameSettings.saveOptions();
                guiScreenOF = new GuiAnimationSettingsOF(this, this.guiGameSettings);
                this.minecraft.displayGuiScreen(guiScreenOF);
            }
            if (guiButtonOF.id == 212) {
                this.minecraft.gameSettings.saveOptions();
                guiScreenOF = new GuiPerformanceSettingsOF(this, this.guiGameSettings);
                this.minecraft.displayGuiScreen(guiScreenOF);
            }
            if (guiButtonOF.id == 222) {
                this.minecraft.gameSettings.saveOptions();
                guiScreenOF = new GuiOtherSettingsOF(this, this.guiGameSettings);
                this.minecraft.displayGuiScreen(guiScreenOF);
            }
            if (guiButtonOF.id == 231) {
                if (Config.isAntialiasing() || Config.isAntialiasingConfigured()) {
                    Config.showGuiMessage(Lang.get("of.message.shaders.aa1"), Lang.get("of.message.shaders.aa2"));
                    return;
                }
                if (Config.isGraphicsFabulous()) {
                    Config.showGuiMessage(Lang.get("of.message.shaders.gf1"), Lang.get("of.message.shaders.gf2"));
                    return;
                }
                this.minecraft.gameSettings.saveOptions();
                guiScreenOF = new GuiShaders(this, this.guiGameSettings);
                this.minecraft.displayGuiScreen(guiScreenOF);
            }
        }
    }

    @Override
    public void onClose() {
        this.minecraft.gameSettings.saveOptions();
        super.onClose();
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        VideoSettingsScreen.drawCenteredString(matrixStack, this.minecraft.fontRenderer, this.title, this.width / 2, 15, 0xFFFFFF);
        String string = Config.getVersion();
        String string2 = "HD_U";
        if (string2.equals("HD")) {
            string = "OptiFine HD G8";
        }
        if (string2.equals("HD_U")) {
            string = "OptiFine HD G8 Ultra";
        }
        if (string2.equals("L")) {
            string = "OptiFine G8 Light";
        }
        VideoSettingsScreen.drawString(matrixStack, this.minecraft.fontRenderer, string, 2, this.height - 10, 0x808080);
        String string3 = "Minecraft 1.16.5";
        int n3 = this.minecraft.fontRenderer.getStringWidth(string3);
        VideoSettingsScreen.drawString(matrixStack, this.minecraft.fontRenderer, string3, this.width - n3 - 2, this.height - 10, 0x808080);
        super.render(matrixStack, n, n2, f);
        this.tooltipManager.drawTooltips(matrixStack, n, n2, this.buttonList);
    }

    public static String getGuiChatText(ChatScreen chatScreen) {
        return chatScreen.inputField.getText();
    }

    private void lambda$checkFabulousWarning$1(Button button) {
        this.field_241604_x_.func_241699_f_();
        this.minecraft.displayGuiScreen(this);
    }

    private void lambda$checkFabulousWarning$0(Button button) {
        this.guiGameSettings.graphicFanciness = GraphicsFanciness.FABULOUS;
        Minecraft.getInstance().worldRenderer.loadRenderers();
        this.field_241604_x_.func_241698_e_();
        this.minecraft.displayGuiScreen(this);
    }
}

