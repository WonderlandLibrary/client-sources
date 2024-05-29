package dev.client.tenacity.ui.clickguis.dropdown.impl;

import dev.client.tenacity.Tenacity;
import dev.client.tenacity.module.Module;
import dev.client.tenacity.module.impl.render.ClickGuiMod;
import dev.client.tenacity.module.impl.render.HudMod;
import dev.client.tenacity.utils.render.GradientUtil;
import dev.settings.Setting;
import dev.settings.impl.*;
import dev.client.tenacity.ui.GuiEvents;
import dev.utils.animations.Animation;
import dev.utils.animations.Direction;
import dev.utils.animations.impl.DecelerateAnimation;
import dev.utils.animations.impl.EaseBackIn;
import dev.utils.animations.impl.EaseInOutQuad;
import dev.utils.font.FontUtil;
import dev.client.tenacity.utils.misc.HoveringUtil;
import dev.utils.misc.MathUtils;
import dev.client.tenacity.utils.objects.PasswordField;
import dev.client.tenacity.utils.render.ColorUtil;
import dev.client.tenacity.utils.render.RenderUtil;
import dev.client.tenacity.utils.render.RoundedUtil;
import dev.utils.render.GLUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_ZERO;

public class SettingComponents extends Component {
    public static float scale;
    private final Module module;
    public Animation settingHeightScissor;
    private final HashMap<KeybindSetting, Animation[]> keySettingAnimMap = new HashMap<>();
    private final HashMap<NumberSetting, Float> sliderMap = new HashMap<>();
    private final HashMap<NumberSetting, Animation[]> sliderAnimMap = new HashMap<>();
    private final HashMap<BooleanSetting, Animation[]> toggleAnimation = new HashMap<>();
    private final HashMap<ModeSetting, Animation[]> modeSettingAnimMap = new HashMap<>();
    private final HashMap<ModeSetting, Boolean> modeSettingClick = new HashMap<>();
    private final HashMap<ModeSetting, HashMap<String, Animation>> modesHoverAnimation = new HashMap<>();
    private final HashMap<ColorSetting, Animation[]> colorAnimationMap = new HashMap<>();
    private final HashMap<MultipleBoolSetting, Animation[]> multiBoolMap = new HashMap<>();
    private final HashMap<MultipleBoolSetting, HashMap<BooleanSetting, Animation[]>> multiBoolHover = new HashMap<>();
    public Module binding;
    public Setting draggingNumber;
    public float x, y, width, rectHeight, panelLimitY;
    public int alphaAnimation;
    public double settingSize;
    private PasswordField selectedField;
    private StringSetting selectedStringSetting;
    private boolean hueFlag;

    public SettingComponents(Module module) {
        this.module = module;
        for (Setting setting : module.getSettingsList()) {
            if (setting instanceof KeybindSetting) {
                keySettingAnimMap.put((KeybindSetting) setting, new Animation[]{new EaseInOutQuad(250, 1, Direction.BACKWARDS),
                        new DecelerateAnimation(225, 1, Direction.BACKWARDS)});
            }
            if (setting instanceof NumberSetting) {
                sliderMap.put((NumberSetting) setting, 0f);
                sliderAnimMap.put((NumberSetting) setting, new Animation[]{new DecelerateAnimation(250, 1, Direction.BACKWARDS), new DecelerateAnimation(200, 1, Direction.BACKWARDS)});
            }
            if (setting instanceof BooleanSetting) {
                toggleAnimation.put((BooleanSetting) setting, new Animation[]{new DecelerateAnimation(225, 1, Direction.BACKWARDS),
                        new DecelerateAnimation(200, 1, Direction.BACKWARDS)});
            }
            if (setting instanceof ModeSetting) {
                ModeSetting modeSetting = (ModeSetting) setting;
                modeSettingClick.put(modeSetting, false);
                modeSettingAnimMap.put(modeSetting, new Animation[]{new DecelerateAnimation(225, 1, Direction.BACKWARDS),
                        new EaseInOutQuad(250, 1, Direction.BACKWARDS)});

                HashMap<String, Animation> modeMap = new HashMap<>();
                for (String mode : modeSetting.modes) {
                    modeMap.put(mode, new DecelerateAnimation(225, 1, Direction.BACKWARDS));
                }

                modesHoverAnimation.put(modeSetting, modeMap);
            }
            if (setting instanceof ColorSetting) {
                ColorSetting colorSetting = (ColorSetting) setting;
                colorAnimationMap.put(colorSetting, new Animation[]{new DecelerateAnimation(250, 1, Direction.BACKWARDS),
                        new DecelerateAnimation(250, 1, Direction.BACKWARDS)});
            }
            if (setting instanceof MultipleBoolSetting) {
                MultipleBoolSetting multipleBoolSetting = (MultipleBoolSetting) setting;
                multiBoolMap.put(multipleBoolSetting, new Animation[]{new DecelerateAnimation(225, 1, Direction.BACKWARDS),
                        new DecelerateAnimation(250, 1, Direction.BACKWARDS)});

                HashMap<BooleanSetting, Animation[]> boolMap = new HashMap<>();
                for (BooleanSetting booleanSetting : multipleBoolSetting.getBoolSettings()) {
                    boolMap.put(booleanSetting, new Animation[]{new DecelerateAnimation(225, 1),
                            new EaseBackIn(300, 1, 2)});
                }
                multiBoolHover.put(multipleBoolSetting, boolMap);
            }
        }
    }

    @Override
    public void initGui() {

    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (binding != null) {
            selectedField = null;
            selectedStringSetting = null;
            if (keyCode == Keyboard.KEY_SPACE || keyCode == Keyboard.KEY_ESCAPE || keyCode == Keyboard.KEY_DELETE)
                binding.getKeybind().setCode(Keyboard.KEY_NONE);
            else
                binding.getKeybind().setCode(keyCode);
            binding = null;
            return;
        }
        if (selectedField != null) {
            if (keyCode == 1) {
                selectedField = null;
                selectedStringSetting = null;
                return;
            }
            selectedField.textboxKeyTyped(typedChar, keyCode);
            selectedStringSetting.setString(selectedField.getText());
        }
    }


    public void handle(int mouseX, int mouseY, int button, GuiEvents type) {
        //Setting up the colors
        Color textColor = new Color(255, 255, 255, alphaAnimation);
        Color darkRectColor = new Color(48, 50, 55, alphaAnimation);
        Color darkRectColorDisabled = new Color(52, 52, 52, alphaAnimation);
        Color darkRectHover = ColorUtil.brighter(darkRectColor, .8f);

        Color disabledTextColor = new Color(195,195,195, alphaAnimation);

        Color clickModColor = ClickGuiMod.color.getColor();

        //Accent color from clickgui mod
        Color accentColor = new Color(clickModColor.getRed(), clickModColor.getGreen(), clickModColor.getBlue(), alphaAnimation);


        HudMod hudMod = (HudMod) Tenacity.INSTANCE.getModuleCollection().get(HudMod.class);
        Color[] colors = new Color[2];

        boolean accent = ClickGuiMod.settingAccent.is("Color");

        switch (ClickGuiMod.colorMode.getMode()) {
            case "Sync":
                colors = hudMod.getClientColors();
                break;
            case "Dynamic Sync":
                Color[] clientColors = hudMod.getClientColors();
                Color color = ColorUtil.interpolateColorsBackAndForth(15, 1, clientColors[0], clientColors[1], HudMod.hueInterpolation.isEnabled());
                colors = new Color[]{color, color};
                break;
            case "Dynamic":
                Color color2 = ColorUtil.interpolateColorsBackAndForth(15, 1, ClickGuiMod.color.getColor(), ClickGuiMod.color2.getColor(), HudMod.hueInterpolation.isEnabled());
                colors = new Color[]{color2, color2};
                break;
            case "Static":
                colors = new Color[]{ClickGuiMod.color.getColor(), ClickGuiMod.color.getColor()};
                break;
            case "Double Color":
                colors = new Color[]{ClickGuiMod.color.getColor(), ClickGuiMod.color2.getColor()};
                break;
        }
        Color accentedColor = ColorUtil.applyOpacity(colors[0], alphaAnimation / 255f);
        Color accentedColor2 = ColorUtil.applyOpacity(colors[1], alphaAnimation / 255f);


        double count = 0;
        for (Setting setting : module.getSettingsList()) {
            if (setting.shouldBeShownTenacityClickgui()) continue;

            float settingY = (float) MathUtils.roundToHalf(y + (count * rectHeight));
            boolean hoveringSetting = HoveringUtil.isHovering(x, settingY, width, rectHeight, mouseX, mouseY);
            if (setting instanceof KeybindSetting) {
                KeybindSetting keybindSetting = (KeybindSetting) setting;

                String bind = Keyboard.getKeyName(keybindSetting.getCode());

                boolean hoveringBindRect = isClickable(settingY + FontUtil.tenacityBoldFont18.getMiddleOfBox(rectHeight) - 1) && HoveringUtil.isHovering((float) (x + width - (FontUtil.tenacityBoldFont18.getStringWidth(bind) + 7)), settingY + FontUtil.tenacityBoldFont18.getMiddleOfBox(rectHeight) - 1, (float) (FontUtil.tenacityBoldFont18.getStringWidth(bind) + 4), FontUtil.tenacityBoldFont18.getHeight() + 4, mouseX, mouseY);

                if (type == GuiEvents.CLICK && hoveringBindRect && button == 0) {
                    binding = module;
                    return;
                }
                Animation[] animations = keySettingAnimMap.get(keybindSetting);

                animations[1].setDirection(binding == module ? Direction.FORWARDS : Direction.BACKWARDS);

                FontUtil.tenacityFont18.drawString("Bind", x + 5,
                        settingY + FontUtil.tenacityFont18.getMiddleOfBox(rectHeight) + 1,
                        textColor.getRGB());


                animations[0].setDirection(hoveringBindRect ? Direction.FORWARDS : Direction.BACKWARDS);


                RoundedUtil.drawRound((float) (x + width - (FontUtil.tenacityBoldFont18.getStringWidth(bind) + 9)),
                        settingY + FontUtil.tenacityBoldFont18.getMiddleOfBox(rectHeight) - 1, (float) (FontUtil.tenacityBoldFont18.getStringWidth(bind) + 5),
                        FontUtil.tenacityBoldFont18.getHeight() + 3.5f, 5, ColorUtil.applyOpacity(darkRectHover, (float) (.4 + (.2 * animations[0].getOutput()))));

                FontUtil.tenacityBoldFont18.drawString(bind, x + width - (FontUtil.tenacityBoldFont18.getStringWidth(bind) + 6),
                        settingY + FontUtil.tenacityBoldFont18.getMiddleOfBox(rectHeight) + 1,
                        ColorUtil.interpolateColor(textColor, accentedColor2, (float) animations[1].getOutput()));
            }
            if (setting instanceof NumberSetting) {
                NumberSetting numberSetting = (NumberSetting) setting;

                String value = Double.toString(MathUtils.round(numberSetting.getValue(), 2));
                float regularFontWidth = (float) FontUtil.tenacityFont18.getStringWidth(numberSetting.name + ": ");
                float valueFontWidth = (float) FontUtil.tenacityBoldFont18.getStringWidth(value);

                float titleX = x + width / 2f - (regularFontWidth + valueFontWidth) / 2f;
                float titleY = settingY + FontUtil.tenacityFont18.getMiddleOfBox(rectHeight) -
                        FontUtil.tenacityFont18.getMiddleOfBox(rectHeight) / 2f + 1;
                GlStateManager.color(1, 1, 1, 1);

                FontUtil.tenacityFont18.drawString(numberSetting.name + ": ", titleX, titleY, textColor.getRGB());
                GlStateManager.color(1, 1, 1, 1);

                FontUtil.tenacityBoldFont18.drawString(value, titleX + regularFontWidth, titleY, textColor.getRGB());


                Animation hoverAnimation = sliderAnimMap.get(numberSetting)[0];
                Animation selectAnimtion = sliderAnimMap.get(numberSetting)[1];

                float totalSliderWidth = width - 10;
                boolean hoveringSlider = isClickable(settingY + 17) && HoveringUtil.isHovering(x + 5, settingY + 17, totalSliderWidth, 6, mouseX, mouseY);

                if (type == GuiEvents.RELEASE) {
                    draggingNumber = null;
                }
                hoverAnimation.setDirection(hoveringSlider || draggingNumber == numberSetting ? Direction.FORWARDS : Direction.BACKWARDS);
                selectAnimtion.setDirection(draggingNumber == numberSetting ? Direction.FORWARDS : Direction.BACKWARDS);
                if (type == GuiEvents.CLICK && hoveringSlider && button == 0) {
                    draggingNumber = numberSetting;
                }


                double currentValue = numberSetting.getValue();


                if (draggingNumber != null && draggingNumber == setting) {
                    float percent = Math.min(1, Math.max(0, (mouseX - (x + 5)) / totalSliderWidth));
                    double newValue = (percent * (numberSetting.getMaxValue()
                            - numberSetting.getMinValue())) + numberSetting.getMinValue();
                    numberSetting.setValue(newValue);
                }

                float sliderMath = (float) (((currentValue) - numberSetting.getMinValue())
                        / (numberSetting.getMaxValue() - numberSetting.getMinValue()));


                sliderMap.put(numberSetting, (float) RenderUtil.animate(totalSliderWidth * sliderMath, sliderMap.get(numberSetting), .1));


                float sliderY = (settingY + 18);
                RoundedUtil.drawRound(x + 5, sliderY, totalSliderWidth, 3, 1.5f, ColorUtil.applyOpacity(darkRectHover, (float) (.4f + (.2 * hoverAnimation.getOutput()))));
                RoundedUtil.drawRound(x + 5, sliderY, Math.max(4, sliderMap.get(numberSetting)), 3, 1.5f, accent ? accentedColor2 : textColor);

                RenderUtil.setAlphaLimit(0);
                RenderUtil.fakeCircleGlow(x + 4 + Math.max(4, sliderMap.get(numberSetting)), sliderY + 1.5f, 6,
                        Color.BLACK, .3f);

                RenderUtil.drawGoodCircle(x + 4 + Math.max(4, sliderMap.get(numberSetting)), sliderY + 1.5f, 3.75f, accent ? accentedColor2.getRGB() : textColor.getRGB());
                count += .5f;
            }
            if (setting instanceof BooleanSetting) {
                BooleanSetting booleanSetting = (BooleanSetting) setting;

                Animation toggleAnimation = this.toggleAnimation.get(booleanSetting)[0];
                Animation hoverAnimation = this.toggleAnimation.get(booleanSetting)[1];

                RenderUtil.resetColor();
                OpenGlHelper.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ZERO);
                GlStateManager.enableBlend();

                FontUtil.tenacityFont18.drawString(booleanSetting.name, MathUtils.roundToHalf(x + 4), settingY +
                        5, textColor.getRGB());

                float switchWidth = 16;

                boolean hoveringSwitch = isClickable(settingY + FontUtil.tenacityFont18.getMiddleOfBox(rectHeight) - 1) &&
                        HoveringUtil.isHovering(x + width - (switchWidth + 6), settingY + FontUtil.tenacityFont18.getMiddleOfBox(rectHeight) - 1,
                                switchWidth, 8, mouseX, mouseY);

                hoverAnimation.setDirection(hoveringSwitch ? Direction.FORWARDS : Direction.BACKWARDS);

                if (type == GuiEvents.CLICK && hoveringSwitch && button == 0) {
                    booleanSetting.toggle();
                }

                toggleAnimation.setDirection(booleanSetting.isEnabled() ? Direction.FORWARDS : Direction.BACKWARDS);
                RenderUtil.resetColor();
                Color accentCircle = accent ? ColorUtil.applyOpacity(accentedColor, .8f) : ColorUtil.darker(textColor, .8f);
                RoundedUtil.drawRound(x + width - (switchWidth + 5.5f), settingY + FontUtil.tenacityFont18.getMiddleOfBox(rectHeight) + 2, switchWidth, 4.5f,
                        2, ColorUtil.interpolateColorC(ColorUtil.applyOpacity(darkRectHover, .5f), accentCircle, (float) toggleAnimation.getOutput()));

                RenderUtil.fakeCircleGlow((float) ((x + width - (switchWidth + 3)) + ((switchWidth - 5) * toggleAnimation.getOutput())),
                        settingY + FontUtil.tenacityFont18.getMiddleOfBox(rectHeight) + 4, 6, Color.BLACK, .3f);

                RenderUtil.resetColor();

                RoundedUtil.drawRound((float) (x + width - (switchWidth + 6) + ((switchWidth - 5) * toggleAnimation.getOutput())),
                        settingY + FontUtil.tenacityFont18.getMiddleOfBox(rectHeight) + 1, 6.5f,
                        6.5f, 3, textColor);


            }
            if (setting instanceof ModeSetting) {
                ModeSetting modeSetting = (ModeSetting) setting;
                Animation hoverAnimation = modeSettingAnimMap.get(modeSetting)[0];
                Animation openAnimation = modeSettingAnimMap.get(modeSetting)[1];

                boolean hoveringModeSettingRect = isClickable(settingY + 5) &&
                        HoveringUtil.isHovering(x + 5, settingY + 5, width - 10, rectHeight + 7, mouseX, mouseY);

                if (type == GuiEvents.CLICK && hoveringModeSettingRect && button == 1) {
                    modeSettingClick.put(modeSetting, !modeSettingClick.get(modeSetting));
                }

                hoverAnimation.setDirection(hoveringModeSettingRect ? Direction.FORWARDS : Direction.BACKWARDS);
                openAnimation.setDirection(modeSettingClick.get(modeSetting) ? Direction.FORWARDS : Direction.BACKWARDS);

                float math = (modeSetting.modes.size() - 1) * rectHeight;
                RoundedUtil.drawRound(x + 5, (float) (settingY + rectHeight + 2 + (12 * openAnimation.getOutput())),
                        width - 10, (float) (math * openAnimation.getOutput()), 3, ColorUtil.applyOpacity(darkRectHover, (float) (.35f * openAnimation.getOutput())));

                if (!openAnimation.isDone() && type == GuiEvents.DRAW) {
                    GL11.glEnable(GL11.GL_SCISSOR_TEST);
                    RenderUtil.scissor(x + 5, (float) (settingY + 7 + rectHeight + (3 * openAnimation.getOutput())),
                            width - 10, (float) (math * openAnimation.getOutput()));
                }
                float modeCount = 0;
                for (String mode : modeSetting.modes) {
                    if (mode.equals(modeSetting.getMode())) continue;
                    float modeY = (float) (settingY + rectHeight + 11 + ((8 + (modeCount * rectHeight)) * openAnimation.getOutput()));
                    RenderUtil.resetColor();

                    boolean hoveringMode = isClickable(modeY - 5) && openAnimation.getDirection().equals(Direction.FORWARDS) &&
                            HoveringUtil.isHovering(x + 5, modeY - 5, width - 10, rectHeight, mouseX, mouseY);
                    Animation modeHoverAnimation = modesHoverAnimation.get(modeSetting).get(mode);

                    if (type == GuiEvents.CLICK && button == 0 && hoveringMode) {
                        modeSettingClick.put(modeSetting, !modeSettingClick.get(setting));
                        modeSetting.setCurrentMode(mode);
                    }


                    modeHoverAnimation.setDirection(hoveringMode ? Direction.FORWARDS : Direction.BACKWARDS);


                    if (modeHoverAnimation.finished(Direction.FORWARDS) || !modeHoverAnimation.isDone()) {
                        RoundedUtil.drawRound(x + 5, modeY - 5, width - 10, rectHeight, 3,
                                ColorUtil.applyOpacity(textColor, (float) (.2f * modeHoverAnimation.getOutput())));
                    }

                    FontUtil.tenacityFont18.drawString(mode, x + 13, modeY,ColorUtil.applyOpacity(textColor, (float) openAnimation.getOutput()).getRGB());

                    modeCount++;
                }
                if (!openAnimation.isDone() && type == GuiEvents.DRAW) {
                    GL11.glDisable(GL11.GL_SCISSOR_TEST);
                }
                if (settingHeightScissor.isDone() && openAnimation.isDone() && GL11.glIsEnabled(GL11.GL_SCISSOR_TEST)) {
                    GL11.glDisable(GL11.GL_SCISSOR_TEST);
                }

                RoundedUtil.drawRound(x + 5, settingY + 5, width - 10, rectHeight + 7, 3, ColorUtil.applyOpacity(darkRectHover, .45f));

                if(!hoverAnimation.isDone() || hoverAnimation.finished(Direction.FORWARDS)) {
                    RoundedUtil.drawRound(x + 5, settingY + 5, width - 10, rectHeight + 7, 3, ColorUtil.applyOpacity(textColor, (float) (.2f * hoverAnimation.getOutput())));
                }


                float selectRectWidth = (float) ((width - 10) * openAnimation.getOutput());
                // RenderUtil.renderRoundedRect(x + 5, settingY + rectHeight + 7, width - 10, 2, .5f, disabledTextColor.getRGB());
                if (openAnimation.isDone() && openAnimation.getDirection().equals(Direction.FORWARDS) || !openAnimation.isDone()) {
                    RoundedUtil.drawRound(x + 5 + ((width - 10) / 2f - selectRectWidth / 2f), settingY + rectHeight + 10.5f,
                            Math.max(2, selectRectWidth), 1.5f, .5f, accent ? accentedColor2 : textColor);
                }


                FontUtil.tenacityFont14.drawString(modeSetting.name, x + 13, settingY + 9, textColor.getRGB());

                RenderUtil.resetColor();
                FontUtil.tenacityBoldFont18.drawString(modeSetting.getMode(), x + 13, settingY + 17.5, textColor.getRGB());

                RenderUtil.resetColor();
                RenderUtil.drawClickGuiArrow(x + width - 15, settingY + 17, 5, openAnimation, textColor.getRGB());


                count += 1 + ((math / rectHeight) * openAnimation.getOutput());
            }
            if (setting instanceof ColorSetting) {
                ColorSetting colorSetting = (ColorSetting) setting;
                FontUtil.tenacityFont18.drawString(colorSetting.name, x + 5, settingY + FontUtil.tenacityFont18.getMiddleOfBox(rectHeight), textColor.getRGB());

                float limitWidth = (float) MathUtils.roundToHalf((width - 45) - (float) (5 + FontUtil.tenacityFont18.getStringWidth(colorSetting.name)));
                Animation hoverAnimation = colorAnimationMap.get(colorSetting)[0];
                Animation openAnimation = colorAnimationMap.get(colorSetting)[1];
                float chosenColorWidth = (float) (35 + (limitWidth * openAnimation.getOutput()));
                float chosenColorHeight = 9;


                float chosenColorX = x + width - (chosenColorWidth + 5);
                boolean hoveringColor = isClickable(settingY + 4.5f) && HoveringUtil.isHovering(chosenColorX,
                        settingY + 4.5f, chosenColorWidth, chosenColorHeight, mouseX, mouseY);


                if (type == GuiEvents.CLICK && hoveringColor && button == 1) {
                    openAnimation.changeDirection();

                }

                hoverAnimation.setDirection(hoveringColor ? Direction.FORWARDS : Direction.BACKWARDS);

                Color currentColor = colorSetting.getColor();

                Color chosenColor = new Color(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), alphaAnimation);


                RenderUtil.renderRoundedRect(chosenColorX, settingY + 4.5f, chosenColorWidth, chosenColorHeight, 3,
                        ColorUtil.interpolateColor(chosenColor, ColorUtil.darker(chosenColor, .8f), (float) hoverAnimation.getOutput()));


                if (!openAnimation.isDone() || openAnimation.getDirection().equals(Direction.FORWARDS)) {
                    float[] hsb = {(float) (colorSetting).getHue(), (float) (colorSetting).getSaturation(),
                            (float) (colorSetting).getBrightness()};

                    float gradientX = x + 5;
                    float gradientY = settingY + 22;
                    float gradientWidth = width - 10;
                    float gradientHeight = (float) (15 + (50 * openAnimation.getOutput()));

                    if (type == GuiEvents.CLICK) {
                        if (openAnimation.getDirection().equals(Direction.FORWARDS) && button == 0) {
                            if (isClickable(gradientY + gradientHeight + 5) &&
                                    HoveringUtil.isHovering(gradientX, gradientY + gradientHeight + 5, gradientWidth, 5, mouseX, mouseY)) {
                                draggingNumber = setting;
                                hueFlag = true;
                            }
                            if (isClickable(gradientY) && HoveringUtil.isHovering(gradientX, gradientY, gradientWidth, gradientHeight, mouseX, mouseY)) {
                                draggingNumber = setting;
                                hueFlag = false;
                            }
                        }
                    }
                    if (type == GuiEvents.RELEASE) {
                        draggingNumber = null;
                    }
                    if (draggingNumber != null && draggingNumber.equals(setting)) {
                        if (hueFlag) {
                            colorSetting.setHue(Math.min(1, Math.max(0, (mouseX - gradientX) / 100)));
                        } else {
                            colorSetting.setBrightness(Math.min(1, Math.max(0, 1 - ((mouseY - gradientY) / gradientHeight))));
                            colorSetting.setSaturation(Math.min(1, Math.max(0, (mouseX - gradientX) / gradientWidth)));
                        }
                    }

                    RenderUtil.setAlphaLimit(0);
                    int hsbZeroOneOne = ColorUtil.applyOpacity(Color.getHSBColor(hsb[0], 1, 1), (float) openAnimation.getOutput() * (alphaAnimation / 255f)).getRGB();

                    int hsbZeroZeroOne = ColorUtil.applyOpacity(Color.getHSBColor(hsb[0], 0, 1), (float) openAnimation.getOutput() * (alphaAnimation / 255f)).getRGB();

                    int hsbZeroOneZero = ColorUtil.applyOpacity(Color.getHSBColor(hsb[0], 1, 0), (float) openAnimation.getOutput() * (alphaAnimation / 255f)).getRGB();


                    //Picker gradients
                    Gui.drawRect2(gradientX, gradientY, gradientWidth, gradientHeight, hsbZeroOneOne);


                    Gui.drawGradientRectSideways2(gradientX, gradientY, gradientWidth, gradientHeight,
                            hsbZeroZeroOne,
                            ColorUtil.applyOpacity(Color.getHSBColor(hsb[0], 0, 1).getRGB(), 0));

                    Gui.drawGradientRect2(gradientX, gradientY, gradientWidth, gradientHeight,
                            ColorUtil.applyOpacity(Color.getHSBColor(hsb[0], 1, 0).getRGB(), 0), hsbZeroOneZero);

                    float pickerY = gradientY + (gradientHeight * (1 - hsb[2]));
                    float pickerX = gradientX + (gradientWidth * hsb[1] - 1);
                    pickerY = Math.max(Math.min(gradientY + gradientHeight - 2, pickerY), gradientY);
                    pickerX = Math.max(Math.min(gradientX + gradientWidth - 2, pickerX), gradientX);

                    int whiteColor = ColorUtil.applyOpacity(-1, (float) openAnimation.getOutput());
                    GL11.glEnable(GL11.GL_BLEND);
                    RenderUtil.color(whiteColor);
                    mc.getTextureManager().bindTexture(new ResourceLocation("Tenacity/colorpicker2.png"));
                    Gui.drawModalRectWithCustomSizedTexture(pickerX, pickerY, 0, 0, 4, 4, 4, 4);


                    // Hue bar
                    RenderUtil.resetColor();
                    mc.getTextureManager().bindTexture(new ResourceLocation("Tenacity/hue.png"));
                    RoundedUtil.drawRoundTextured(gradientX, gradientY + gradientHeight + 5, gradientWidth, 5, 2, (float) openAnimation.getOutput());
                    //   Gui.drawModalRectWithCustomSizedTexture(gradientX, gradientY + gradientHeight + 5, 0, 0, gradientWidth, 4, gradientWidth, 4);
                    GlStateManager.color(1, 1, 1, 1);


                    RenderUtil.drawGoodCircle(gradientX + (gradientWidth * hsb[0]), gradientY + gradientHeight + 7.5f, 4, whiteColor);
                    RenderUtil.resetColor();
                    RenderUtil.drawGoodCircle(gradientX + (gradientWidth * hsb[0]), gradientY + gradientHeight + 7.5f, 2.5f,
                            hsbZeroOneOne);

                    //Hue slider
                    //  Gui.drawRect2(gradientX + (100 * hsb[0]), gradientY + gradientHeight + 4, 1, 6, whiteColor);
                }


                count += 5 * openAnimation.getOutput();
            }
            if (setting instanceof StringSetting) {
                StringSetting stringSetting = (StringSetting) setting;

                RenderUtil.resetColor();
                FontUtil.tenacityFont16.drawString(stringSetting.name, x + 5, settingY + 2, textColor.getRGB());

                PasswordField stringSettingField = new PasswordField("Type Here...", 0,
                        (int) (x + 5), (int) (settingY + 15), (int) (width - 10), 10, FontUtil.tenacityFont18);

                stringSettingField.setText(stringSetting.getString());
                stringSettingField.setFocused(selectedStringSetting == stringSetting);
                stringSettingField.bottomBarColor = textColor.getRGB();
                stringSettingField.textColor = textColor.getRGB();
                stringSettingField.placeHolderTextX = x + 30;
                if (type == GuiEvents.CLICK) stringSettingField.mouseClicked(mouseX, mouseY, button);
                if (stringSettingField.isFocused()) {
                    selectedField = stringSettingField;
                    selectedStringSetting = stringSetting;

                } else if (selectedStringSetting == stringSetting) {
                    selectedStringSetting = null;
                    selectedField = null;
                }

                stringSettingField.drawTextBox();
                //Writes to StringSetting
                stringSetting.setString(stringSettingField.getText());

                count++;
            }
            if (setting instanceof MultipleBoolSetting) {
                MultipleBoolSetting multipleBoolSetting = (MultipleBoolSetting) setting;

                Animation hoverAnimation = multiBoolMap.get(multipleBoolSetting)[0];
                Animation openingAnimation = multiBoolMap.get(multipleBoolSetting)[1];

                boolean hoveringBigRect = isClickable(settingY + 5) && HoveringUtil.isHovering(x + 5, settingY + 5, width - 10,
                        rectHeight + 7, mouseX, mouseY);

                if (type == GuiEvents.CLICK && hoveringBigRect && button == 1) {
                    openingAnimation.changeDirection();
                }

                hoverAnimation.setDirection(hoveringBigRect ? Direction.FORWARDS : Direction.BACKWARDS);

                int disabledToAccentColor = ColorUtil.interpolateColor(disabledTextColor, accentColor, (float) openingAnimation.getOutput());
                float math = multipleBoolSetting.getBoolSettings().size() * rectHeight;

                RoundedUtil.drawRound(x + 5, (float) (settingY + 5 + rectHeight + (10 * openingAnimation.getOutput())),
                        width - 10, (float) (math * openingAnimation.getOutput()), 3, ColorUtil.applyOpacity(darkRectHover, (float) (.45f * openingAnimation.getOutput())));

                if (!openingAnimation.isDone() && type == GuiEvents.DRAW) {
                    GL11.glEnable(GL11.GL_SCISSOR_TEST);
                    RenderUtil.scissor(x + 5, settingY + rectHeight + 7 + (4 * openingAnimation.getOutput()),
                            width - 10, math * openingAnimation.getOutput());
                }

                int boolCount = 0;
                for (BooleanSetting booleanSetting : multipleBoolSetting.getBoolSettings()) {
                    float boolY = (float) (settingY + rectHeight + 8 + (12 + (boolCount * rectHeight)) * openingAnimation.getOutput());

                    boolean hoveringBool = openingAnimation.getDirection().equals(Direction.FORWARDS) && isClickable(boolY) &&
                            HoveringUtil.isHovering(x + 5, boolY - 5, width - 10, rectHeight, mouseX, mouseY);


                    if (type == GuiEvents.CLICK && hoveringBool && button == 0) {
                        booleanSetting.toggle();
                    }

                    Animation boolHoverAnimation = multiBoolHover.get(multipleBoolSetting).get(booleanSetting)[0];
                    Animation boolToggleAnimation = multiBoolHover.get(multipleBoolSetting).get(booleanSetting)[1];


                    boolToggleAnimation.setDirection(booleanSetting.isEnabled() ? Direction.FORWARDS : Direction.BACKWARDS);
                    boolHoverAnimation.setDirection(hoveringBool ? Direction.FORWARDS : Direction.BACKWARDS);


                    RenderUtil.resetColor();
                    if (boolHoverAnimation.isDone() && boolHoverAnimation.getDirection().equals(Direction.FORWARDS) || !boolHoverAnimation.isDone()) {
                        RoundedUtil.drawRound(x + 5, boolY - 5, width - 10, rectHeight, 3, ColorUtil.applyOpacity(textColor, (float) (.2f * boolHoverAnimation.getOutput())));
                    }

                    RenderUtil.resetColor();
                    FontUtil.tenacityFont18.drawString(booleanSetting.name, x + 10, boolY,
                            ColorUtil.interpolateColor(new Color(255, 255, 255, 0), textColor, (float) openingAnimation.getOutput()));

                    RenderUtil.setAlphaLimit(0);
                    RenderUtil.resetColor();
                    if (!boolToggleAnimation.isDone() || boolToggleAnimation.getDirection().equals(Direction.FORWARDS)) {
                        RenderUtil.scale(x + (width - 17), boolY + 4, (float) boolToggleAnimation.getOutput(), () -> {
                            FontUtil.iconFont20.drawSmoothString(FontUtil.CHECKMARK, x + (width - 20), boolY + 2,
                                    ColorUtil.interpolateColor(new Color(255, 255, 255, 0), accent ? accentedColor2 : textColor, (float) openingAnimation.getOutput()));
                        });
                    }
                    RenderUtil.setAlphaLimit(1);


                    boolCount++;
                }
                if (!openingAnimation.isDone() && type == GuiEvents.DRAW) {
                    GL11.glDisable(GL11.GL_SCISSOR_TEST);
                }

                if (settingHeightScissor.isDone() && openingAnimation.isDone() && GL11.glIsEnabled(GL11.GL_SCISSOR_TEST)) {
                    GL11.glDisable(GL11.GL_SCISSOR_TEST);
                }

                RoundedUtil.drawRound(x + 5, settingY + 5, width - 10, rectHeight + 7, 3, ColorUtil.applyOpacity(darkRectHover, .45f));

                if(!hoverAnimation.isDone() || hoverAnimation.finished(Direction.FORWARDS)) {
                    RoundedUtil.drawRound(x + 5, settingY + 5, width - 10, rectHeight + 7, 3, ColorUtil.applyOpacity(textColor, (float) (.2f * hoverAnimation.getOutput())));
                }

                FontUtil.tenacityFont18.drawCenteredString(multipleBoolSetting.name, x + width / 2f, settingY + 5 +
                        FontUtil.tenacityFont18.getMiddleOfBox(rectHeight + 7), textColor.getRGB());

                RenderUtil.drawClickGuiArrow(x + width - 15, settingY + 7.5f +
                        FontUtil.tenacityFont18.getMiddleOfBox(rectHeight + 7), 5, openingAnimation, textColor.getRGB());


                count += 1 + ((math / rectHeight) * openingAnimation.getOutput());
            }

            count++;
        }
        settingSize = count;
        // settingSize = count;
    }


    @Override
    public void drawScreen(int mouseX, int mouseY) {
        handle(mouseX, mouseY, -1, GuiEvents.DRAW);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        handle(mouseX, mouseY, button, GuiEvents.CLICK);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        handle(mouseX, mouseY, state, GuiEvents.RELEASE);
    }


    public boolean isClickable(float y) {
        return y > panelLimitY && y < panelLimitY + 17 + Module.allowedClickGuiHeight;
    }
}
