package de.tired.api.guis.clickgui.impl;

import de.tired.api.extension.Extension;
import de.tired.api.extension.processors.extensions.generally.RenderProcessor;
import de.tired.api.guis.clickgui.impl.extensions.ModuleRendering;
import de.tired.api.guis.clickgui.impl.extensions.Panel;
import de.tired.api.guis.clickgui.impl.values.CheckBox;
import de.tired.api.guis.clickgui.impl.values.ColorPicker;
import de.tired.api.guis.clickgui.impl.values.ModeSelector;
import de.tired.api.guis.clickgui.impl.values.Slider;
import de.tired.api.guis.clickgui.setting.ModeSetting;
import de.tired.api.guis.clickgui.setting.NumberSetting;
import de.tired.api.guis.clickgui.setting.Setting;
import de.tired.api.guis.clickgui.setting.impl.BooleanSetting;
import de.tired.api.guis.clickgui.setting.impl.ColorPickerSetting;
import de.tired.api.util.font.FontManager;
import de.tired.api.util.render.Scissoring;
import de.tired.api.util.render.Translate;
import de.tired.api.util.shader.renderapi.AnimationUtil;
import de.tired.interfaces.FHook;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;
import de.tired.module.impl.list.visual.Shader;
import de.tired.shaderloader.ShaderRenderer;
import de.tired.tired.Tired;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ClickGui extends GuiScreen {

    private ArrayList<CheckBox> checkBoxArrayList;
    private final ArrayList<ColorPicker> colorPickers;
    private final ArrayList<ModeSelector> modeSelector;
    private ArrayList<Panel> panels;
    private final ArrayList<Slider> sliders;
    public static int addTimes = 0;
    private int scrollAmount = 4;
    private Translate translate;
    private Translate translate2;
    public static int clicks;
    public float float2;

    public float scrollAnimation = 0;

    private int alphaValue = 0;

    public ClickGui() {
        this.panels = new ArrayList<>();
        this.sliders = new ArrayList<>();
        this.modeSelector = new ArrayList<>();
        this.colorPickers = new ArrayList<>();
        this.translate = new Translate(0, 0);
        this.translate2 = new Translate(0, 0);
        int x = 0;
        for (ModuleCategory category : ModuleCategory.values()) {
            panels.add(new Panel(20 + x, 30, category));
            x += 120;
        }
        this.checkBoxArrayList = new ArrayList<>();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void onGuiClosed() {
        clicks = 0;
        addTimes = 0;
        checkBoxArrayList.clear();
        sliders.clear();
        colorPickers.clear();
        modeSelector.clear();
        for (Panel panel : panels) {
            panel.animation = 0;

        }
    }
    @Override
    public void initGui() {
        if (ModuleRendering.allowInteraction) {
            clicks = 2;
        }
        checkBoxArrayList.clear();
        sliders.clear();
        colorPickers.clear();
        modeSelector.clear();
        translate2 = new Translate(0, 0);
        super.initGui();
    }

    public void renderScreen(int mouseX, int mouseY) {
        for (Panel panel : panels) {
            panel.drawPanel(mouseX, mouseY, true);
        }

        if (!ModuleRendering.allowInteraction) {
            if (Shader.getInstance().isState() && Shader.getInstance().clickGUIBlur.getValue()) {
                ShaderRenderer.stopBlur(12);
            }
        }


        final double widthA = 190;

        if (!ModuleRendering.allowInteraction) {
            if (float2 < 0.5F) {
                translate.interpolate((width / 2f), height / 2, 12);

            }
        } else {
            checkBoxArrayList.clear();
            sliders.clear();
            colorPickers.clear();
            modeSelector.clear();
            translate.interpolate(0, 0, 8);
            float2 = 0;
        }

        alphaValue = (int) AnimationUtil.getAnimationState(alphaValue, !ModuleRendering.allowInteraction ? 255 : 0, !ModuleRendering.allowInteraction ? 944 : 300);


        GL11.glPushMatrix();
        GL11.glTranslatef(width / 2f, height / 2f, 0);
        GL11.glScaled(0.5 + translate.getX() / width - float2, 0.5 + translate.getY() / height - float2, 0);
        GL11.glTranslatef(-width / 2f, -height / 2f, 0);

        if (translate.getX() != 0.0) {

            RenderProcessor.drawRoundedRectangle((int) ((int)width / 2f -(int) widthA), height / 4f, (int) (width / 2f + widthA), height / 4f + 225,  2, new Color(22, 23, 25, alphaValue).getRGB());


            if (Tired.INSTANCE.usingModule != null) {
                if (addTimes > 1) {
                    addTimes = 1;
                }
                if (addTimes == 0) {
                    if (Tired.INSTANCE.settingsManager.getSettingsByMod(Tired.INSTANCE.usingModule) != null) {
                        for (Setting s : Tired.INSTANCE.settingsManager.getSettingsByMod(Tired.INSTANCE.usingModule)) {
                            if (s instanceof BooleanSetting) {
                                checkBoxArrayList.add(new CheckBox((BooleanSetting) s));
                            } else if (s instanceof NumberSetting) {
                                sliders.add(new Slider((NumberSetting) s));
                            } else if (s instanceof ColorPickerSetting) {
                                colorPickers.add(new ColorPicker((ColorPickerSetting) s));
                            } else if (s instanceof ModeSetting) {
                                modeSelector.add(new ModeSelector((ModeSetting) s, ((ModeSetting) s).getOptions()));
                            }
                        }
                        addTimes = 1;
                    }
                }


                int yAxis = (int) (height / 4F) - 20;

                int wheel = Mouse.getDWheel();

                if (wheel < 0) {
                    if (yAxis + height > height) scrollAmount -= 16;
                } else if (wheel > 0) {
                    scrollAmount += 34;
                    if (scrollAmount > 0)
                        scrollAmount = 0;
                }

                scrollAnimation = (float) AnimationUtil.getAnimationState(scrollAnimation, scrollAmount, Math.max(2.6D, Math.abs((double) this.scrollAnimation - scrollAmount - 10) * 3));

                GlStateManager.pushMatrix();
                GL11.glEnable(GL11.GL_SCISSOR_TEST);
                Scissoring.SCISSORING.doScissor((float) (width / 2f - widthA), height / 4f, (float) (width / 2f + widthA), height / 4f + 225);

                for (ModeSelector selector : modeSelector) {
                    if (selector.set.isVisible()) {
                        selector.drawSlider((int) ((float) (width / (int) 2f - (int) widthA) + 5), (int) (scrollAnimation + yAxis + 20), mouseX, mouseY);
                        yAxis += selector.yOffset + 4;
                    }
                }


                for (CheckBox checkBox : checkBoxArrayList) {
                    if (checkBox.value.isVisible()) {
                        checkBox.drawCheckBox((int) ((float) (width / (int) 2f - (int) widthA) + 5), (int) (scrollAnimation + yAxis + 20), (int) width, (int) height, true);
                        yAxis += checkBox.HEIGHT + 4;
                    }
                }
                for (Slider slider : sliders) {
                    if (slider.setting.isVisible()) {
                        slider.drawSlider((int) ((float) (width / (int) 2f - (int) widthA) + 5), (int) (scrollAnimation + yAxis + 20), mouseX, mouseY, false, false);
                        yAxis += slider.HEIGHT;
                    }
                }
                for (ColorPicker colorPickers : colorPickers) {
                    if (colorPickers.s.isVisible()) {
                        colorPickers.draw((int) ((float) (width / (int) 2f - (int) widthA) + 5), (int) (scrollAnimation + yAxis + 20), mouseX, mouseY);
                        yAxis += ColorPicker.HEIGHT + 20;
                    }
                }

                for (Panel panel : panels) {
                    for (Module module : panel.modules) {
                        if (module == Tired.INSTANCE.usingModule) {
                            if (module.renderPreview) {

                                mc.getTextureManager().bindTexture(new ResourceLocation("client/img_2.png"));
                                Gui.drawModalRectWithCustomSizedTexture((int) ((float) (width / (int) 2f)) - 100, (int) (scrollAnimation + yAxis + 70), 200, 100, 200, 100, 200, 100);

                                FontManager.inter.drawStringWithShadow("Preview", (int) ((float) (width / (int) 2f)) - 20, (int) (scrollAnimation + yAxis + 50), -1);
                                module.onRender((int) ((float) (width / (int) 2f)), (int) (scrollAnimation + yAxis + 115), 70, 70);
                                yAxis += 70 + 20;
                            }
                        }
                    }
                }

                Extension.EXTENSION.getGenerallyProcessor().renderProcessor.renderGradient((int) (width / 2f - widthA), (int) (height / 1.5f), (int) (width / 2f + widthA), (int) (height / 4f + 225), new Color(22, 23, 25, alphaValue).getRGB(), new Color(20, 20, 20, alphaValue).getRGB());
                GlStateManager.disableBlend();

             //   FontManager.light.drawString(Tired.INSTANCE.usingModule.getName(), (float) (width / 2f + widthA) - FontManager.light.getStringWidth(Tired.INSTANCE.usingModule.getName()) - 3, Math.round(height / 4f) + 5, -1);
                FontManager.confortaa.drawString(Tired.INSTANCE.usingModule.clickGUIText, (int) (width / 2f - widthA) + 3, (int) (height / 1.5f) + 5, Integer.MAX_VALUE);

                boolean stop = Math.abs(scrollAmount) * 1.33 > Math.abs(yAxis);

                if (stop && scrollAnimation != 0) {
                    scrollAmount = (int) AnimationUtil.getAnimationState(scrollAnimation, 0, 4282);
                }

                GL11.glDisable(GL11.GL_SCISSOR_TEST);
                GlStateManager.popMatrix();
            }

        }
        GlStateManager.popMatrix();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        ShaderRenderer.stopBlur();

        renderScreen(mouseX, mouseY);


        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            ModuleRendering.allowInteraction = true;
            clicks++;
            checkBoxArrayList.clear();
            sliders.clear();
            colorPickers.clear();
            modeSelector.clear();

            //clear key
        }
        if (clicks > 1) {
            super.keyTyped(typedChar, keyCode);
        }
        colorPickers.forEach(checkboxButton -> checkboxButton.handleInput(typedChar, keyCode));
    }



    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for (Panel panel : panels) {
            panel.mouseReleased();
        }
        sliders.forEach(slider -> slider.mouseReleased(mouseX, mouseY));
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (Panel panel : panels) {
            panel.mouseClicked(mouseX, mouseY, mouseButton);
        }
        colorPickers.forEach(checkboxButton -> checkboxButton.mouseClicked(mouseX, mouseY, mouseButton));
        modeSelector.forEach(modeSelector -> modeSelector.mouseClicked(mouseX, mouseY, mouseButton));
        checkBoxArrayList.forEach(checkboxButton -> checkboxButton.mouseClicked(mouseX, mouseY, mouseButton));
        sliders.forEach(slider -> slider.mouseClicked(mouseX, mouseY, mouseButton));
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }


}