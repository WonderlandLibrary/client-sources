/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.awt.Color;
import java.util.Arrays;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import ru.govno.client.clickgui.ClickGuiScreen;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.ClientTune;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.newfont.CFontRenderer;
import ru.govno.client.newfont.Fonts;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Render.AnimationUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;
import ru.govno.client.utils.Render.StencilUtil;
import ru.govno.client.utils.TimerHelper;

public class ClientColors
extends Module {
    public String[] presetsList = new String[PresetColors.values().length];
    public String[] modesList = new String[]{"Astolfo", "Rainbow", "Colored", "TwoColored", "Fade", "Presets"};
    public static ClientColors get;
    public Settings XPos;
    public Settings YPos;
    public Settings Mode;
    public Settings Preset;
    public Settings Pick1;
    public Settings Pick2;
    static PresetColors prevPreset;
    static AnimationUtils presetStepAnim;
    static boolean smoothPresets;
    static float presetStepAnimGetAnim;
    static String prevMode;
    static AnimationUtils modeStepAnim;
    static float modeStepAnimGetAnim;

    public ClientColors() {
        super("ClientColors", 0, Module.Category.MISC, false, () -> false);
        float w = new ScaledResolution(mc).getScaledWidth();
        float h = new ScaledResolution(mc).getScaledHeight();
        this.XPos = new Settings("XPos", (w - 115.0f) / w, 1.0f, 0.0f, this);
        this.settings.add(this.XPos);
        this.YPos = new Settings("YPos", 55.0f / h, 1.0f, 0.0f, this);
        this.settings.add(this.YPos);
        this.Mode = new Settings("Mode", "Colored", (Module)this, this.modesList);
        this.settings.add(this.Mode);
        int i = 0;
        for (PresetColors preset : PresetColors.values()) {
            this.presetsList[i] = preset.name;
            ++i;
        }
        this.Preset = new Settings("Preset", this.presetsList[0], (Module)this, this.presetsList);
        this.settings.add(this.Preset);
        this.Pick1 = new Settings("Pick1", ColorUtils.getColor(254, 39, 171), (Module)this);
        this.settings.add(this.Pick1);
        this.Pick2 = new Settings("Pick2", ColorUtils.getColor(255, 173, 139), (Module)this);
        this.settings.add(this.Pick2);
        get = this;
    }

    public static PresetColors getPresetByName(String name) {
        return Arrays.asList(PresetColors.values()).stream().filter(preset -> preset.name.equalsIgnoreCase(name)).findAny().orElse(null);
    }

    public static PresetColors getCurrentPreset() {
        PresetColors preset = ClientColors.getPresetByName(ClientColors.get.Preset.currentMode);
        if (prevPreset == null) {
            prevPreset = preset;
        }
        return preset;
    }

    public static int[] getColorsByMode(String mode, int index) {
        int col1 = -1;
        int col2 = -1;
        switch (mode) {
            case "Presets": {
                float fadeSpeed = 0.3f;
                PresetColors preset = ClientColors.getCurrentPreset();
                if (presetStepAnimGetAnim != 0.0f) {
                    col1 = ColorUtils.getOverallColorFrom(ClientColors.prevPreset.twoColored ? ColorUtils.fadeColor(ClientColors.prevPreset.color1, ClientColors.prevPreset.color2, fadeSpeed, (int)((float)index / fadeSpeed / 8.0f)) : ClientColors.prevPreset.color1, preset.twoColored ? ColorUtils.fadeColor(preset.color1, preset.color2, fadeSpeed, (int)((float)index / fadeSpeed / 8.0f)) : preset.color1, presetStepAnimGetAnim);
                    col2 = ClientColors.prevPreset.twoColored ? ColorUtils.fadeColor(ClientColors.prevPreset.color2, ClientColors.prevPreset.color1, fadeSpeed, (int)((float)index / fadeSpeed / 8.0f)) : ClientColors.prevPreset.color2;
                    col2 = ColorUtils.getOverallColorFrom(col2, preset.twoColored ? ColorUtils.fadeColor(preset.color2, preset.color1, fadeSpeed, (int)((float)index / fadeSpeed / 8.0f)) : preset.color2, presetStepAnimGetAnim);
                    break;
                }
                col1 = preset.twoColored ? ColorUtils.fadeColor(preset.color1, preset.color2, fadeSpeed, (int)((float)index / fadeSpeed / 8.0f)) : preset.color1;
                col2 = preset.twoColored ? ColorUtils.fadeColor(preset.color2, preset.color1, fadeSpeed, (int)((float)index / fadeSpeed / 8.0f)) : preset.color2;
                break;
            }
            case "Colored": {
                col1 = ClientColors.get.Pick1.color;
                col2 = ClientColors.get.Pick1.color;
                break;
            }
            case "TwoColored": {
                col1 = ClientColors.get.Pick1.color;
                col2 = ClientColors.get.Pick2.color;
                break;
            }
            case "Fade": {
                float fadeSpeed = 0.3f;
                col1 = ColorUtils.fadeColor(ClientColors.get.Pick1.color, ClientColors.get.Pick2.color, fadeSpeed, (int)((float)index / fadeSpeed / 8.0f));
                col2 = ColorUtils.fadeColor(ClientColors.get.Pick2.color, ClientColors.get.Pick1.color, fadeSpeed, (int)((float)index / fadeSpeed / 8.0f));
                break;
            }
            case "Astolfo": {
                col1 = ColorUtils.astolfoColorsCool(0, (int)((float)index / 6.7f));
                col2 = ColorUtils.astolfoColorsCool(0, (int)((float)(index + 648) / 6.7f));
                break;
            }
            case "Rainbow": {
                col1 = ColorUtils.rainbowGui(0, index);
                col2 = ColorUtils.rainbowGui(0, index + 648);
            }
        }
        if (mode.equalsIgnoreCase("Presets")) {
            float fadeSpeed = 0.3f;
            PresetColors preset = ClientColors.getCurrentPreset();
            if (presetStepAnimGetAnim != 0.0f) {
                col1 = ColorUtils.getOverallColorFrom(ClientColors.prevPreset.twoColored ? ColorUtils.fadeColor(ClientColors.prevPreset.color1, ClientColors.prevPreset.color2, fadeSpeed, (int)((float)index / fadeSpeed / 8.0f)) : ClientColors.prevPreset.color1, preset.twoColored ? ColorUtils.fadeColor(preset.color1, preset.color2, fadeSpeed, (int)((float)index / fadeSpeed / 8.0f)) : preset.color1, presetStepAnimGetAnim);
                col2 = ClientColors.prevPreset.twoColored ? ColorUtils.fadeColor(ClientColors.prevPreset.color2, ClientColors.prevPreset.color1, fadeSpeed, (int)((float)index / fadeSpeed / 8.0f)) : ClientColors.prevPreset.color2;
                col2 = ColorUtils.getOverallColorFrom(col2, preset.twoColored ? ColorUtils.fadeColor(preset.color2, preset.color1, fadeSpeed, (int)((float)index / fadeSpeed / 8.0f)) : preset.color2, presetStepAnimGetAnim);
            } else {
                col1 = preset.twoColored ? ColorUtils.fadeColor(preset.color1, preset.color2, fadeSpeed, (int)((float)index / fadeSpeed / 8.0f)) : preset.color1;
                col2 = preset.twoColored ? ColorUtils.fadeColor(preset.color2, preset.color1, fadeSpeed, (int)((float)index / fadeSpeed / 8.0f)) : preset.color2;
            }
        }
        return new int[]{col1, col2};
    }

    public static int[] getColors(int index) {
        String mode = ClientColors.get.Mode.currentMode;
        if (prevMode == null) {
            prevMode = mode;
        }
        if (modeStepAnimGetAnim != 0.0f) {
            int c1 = ColorUtils.getOverallColorFrom(ClientColors.getColorsByMode(prevMode, index)[0], ClientColors.getColorsByMode(mode, index)[0], modeStepAnimGetAnim);
            int c2 = ColorUtils.getOverallColorFrom(ClientColors.getColorsByMode(prevMode, index)[1], ClientColors.getColorsByMode(mode, index)[1], modeStepAnimGetAnim);
            return new int[]{c1, c2};
        }
        return ClientColors.getColorsByMode(ClientColors.get.Mode.currentMode, index);
    }

    public static int getColor1(int index) {
        return ClientColors.getColors(index)[0];
    }

    public static int getColor2(int index) {
        return ClientColors.getColors(index)[1];
    }

    public static int getColor1(int index, float aPC) {
        return ColorUtils.swapAlpha(ClientColors.getColor1(index), (float)ColorUtils.getAlphaFromColor(ClientColors.getColor1(index)) * aPC);
    }

    public static int getColor2(int index, float aPC) {
        return ColorUtils.swapAlpha(ClientColors.getColor2(index), (float)ColorUtils.getAlphaFromColor(ClientColors.getColor2(index)) * aPC);
    }

    public static int getColor1() {
        return ClientColors.getColors(0)[0];
    }

    public static int getColor2() {
        return ClientColors.getColors(0)[1];
    }

    static {
        presetStepAnim = new AnimationUtils(0.0f, 0.0f, 0.035f);
        presetStepAnimGetAnim = 0.0f;
        modeStepAnim = new AnimationUtils(0.0f, 0.0f, 0.025f);
        modeStepAnimGetAnim = 0.0f;
    }

    static enum PresetColors {
        WHITE(-1, -1, false, "White"),
        PINK(ColorUtils.getColor(255, 115, 220), ColorUtils.getColor(255, 0, 190), true, "Pink"),
        WINTER(ColorUtils.getColor(165, 235, 255), ColorUtils.getColor(240, 155, 255), true, "Winter"),
        BLOODY(ColorUtils.getColor(255, 0, 0), ColorUtils.getColor(135, 0, 0), true, "Bloody"),
        HERO(ColorUtils.getColor(255, 0, 0), ColorUtils.getColor(0, 255, 0), true, "Hero"),
        EARLBLUE(ColorUtils.getColor(50, 100, 50), ColorUtils.getColor(0, 0, 255), true, "EarlBlue"),
        FLAME(ColorUtils.getColor(255, 95, 0), ColorUtils.getColor(255, 195, 0), true, "Flame"),
        NEON(ColorUtils.getColor(160, 100, 255), ColorUtils.getColor(75, 60, 185), true, "Neon"),
        STELL(ColorUtils.getColor(118, 114, 232), ColorUtils.getColor(59, 59, 122), true, "Stell"),
        GRAY(ColorUtils.getColor(90, 92, 112), ColorUtils.getColor(90, 92, 112), false, "Gray"),
        NIGHTFALL(ColorUtils.getColor(115, 0, 255), ColorUtils.getColor(255, 80, 0), true, "NightFall"),
        BROWN(ColorUtils.getColor(87, 43, 0), ColorUtils.getColor(87, 43, 0), false, "Brown"),
        BREATH(ColorUtils.getColor(120, 255, 227), ColorUtils.getColor(0, 186, 255), true, "Breath"),
        OCEAN(ColorUtils.getColor(251, 255, 38), ColorUtils.getColor(95, 255, 38), true, "Ocean"),
        BLUESKY(ColorUtils.getColor(60, 205, 255), ColorUtils.getColor(60, 205, 255), false, "BlueSky"),
        CANDY(ColorUtils.getColor(255, 117, 252), ColorUtils.getColor(255, 45, 45), true, "Candy");

        boolean twoColored;
        int color1;
        int color2;
        String name;

        private PresetColors(int color1, int color2, boolean twoColored, String name) {
            this.color1 = color1;
            this.color2 = color2;
            this.twoColored = twoColored;
            this.name = name;
        }
    }

    public static class ClientColorsHud {
        AnimationUtils heightAnimation = new AnimationUtils(22.0f, 22.0f, 0.125f);
        AnimationUtils widthAnimation = new AnimationUtils(22.0f, 22.0f, 0.125f);
        String className = "Client color ui";
        float x;
        float y;
        AnimationUtils xRepos = new AnimationUtils(this.x, this.x, 0.15f);
        AnimationUtils yRepos = new AnimationUtils(this.y, this.y, 0.15f);
        AnimationUtils rotate = new AnimationUtils(0.0f, 0.0f, 0.125f);
        boolean dragging = false;
        float dragX = 0.0f;
        float dragY = 0.0f;
        boolean open = false;
        TimerHelper soundTicker = new TimerHelper();
        float offsetX = -1.2312312E8f;
        float offsetY = -1.2312312E8f;
        float offsetX2 = -1.2312312E8f;
        float offsetX3 = -1.2312312E8f;
        boolean dragginggr = false;
        boolean dragginghsb = false;
        boolean draggingalpha = false;
        float offsetXs = -1.2312312E8f;
        float offsetYs = -1.2312312E8f;
        float offsetX2s = -1.2312312E8f;
        float offsetX3s = -1.2312312E8f;
        boolean dragginggrs = false;
        boolean dragginghsbs = false;
        boolean draggingalphas = false;

        public void setXYMenu(float x, float y, ScaledResolution sr) {
            ((Settings)ClientColors.get.settings.get((int)0)).fValue = x / (float)sr.getScaledWidth();
            ((Settings)ClientColors.get.settings.get((int)1)).fValue = y / (float)sr.getScaledHeight();
            this.x = x;
            this.y = y;
        }

        public boolean isHover(float x, float y, float w, float h, int mouseX, int mouseY) {
            return (float)mouseX <= x + w && (float)mouseX >= x && (float)mouseY <= y + h && (float)mouseY >= y;
        }

        public float getWidth() {
            this.widthAnimation.to = 100.0f;
            return this.widthAnimation.getAnim();
        }

        public float getRotate() {
            this.rotate.to = this.x - this.getX();
            return MathUtils.clamp(this.rotate.getAnim() / 2.25f, -90.0f, 90.0f);
        }

        public float toHeight() {
            return this.open ? MathUtils.clamp(this.getTotalElementsHeight(), 106.0f, 202.0f) : 22.0f;
        }

        public void heightCalculate() {
            this.heightAnimation.to = this.toHeight();
        }

        public float getHeight() {
            return this.heightAnimation.getAnim();
        }

        public void updatePosXY() {
            this.xRepos.to = this.x;
            this.yRepos.to = this.y;
        }

        public float getX() {
            return this.xRepos.getAnim();
        }

        public float getY() {
            return this.yRepos.getAnim();
        }

        public float getScrollY() {
            return ClickGuiScreen.scrollAnimation.getAnim();
        }

        public void dragUpdate(int mouseX, int mouseY) {
            ScaledResolution sr = new ScaledResolution(Module.mc);
            if (!this.dragging) {
                this.setXYMenu(ClientColors.get.XPos.fValue * (float)sr.getScaledWidth(), ClientColors.get.YPos.fValue * (float)sr.getScaledHeight(), sr);
                this.dragX = (float)mouseX - this.x;
                this.dragY = (float)mouseY - this.y;
            } else {
                this.setXYMenu((float)mouseX - this.dragX, (float)mouseY - this.dragY, sr);
            }
        }

        float getModesHeight() {
            float yStep = 10.0f;
            Settings setting = (Settings)ClientColors.get.settings.get(2);
            for (String mode : setting.modes) {
                yStep += 10.0f;
            }
            return yStep;
        }

        public void drawColorModes(float x, float y, float w, float h, ScaledResolution sr, float alphaPC) {
            Settings setting = (Settings)ClientColors.get.settings.get(2);
            String currentMode = ClientColors.get.Mode.currentMode;
            RenderUtils.drawAlphedRect(x, y, x + w, y + h, ColorUtils.getColor(0, 0, 0, 60.0f * alphaPC));
            RenderUtils.drawLightContureRect(x, y, x + w, y + h, ColorUtils.getColor(255, 255, 255, 60.0f * alphaPC));
            int czC1 = ColorUtils.swapAlpha(ColorUtils.getOverallColorFrom(-1, ClientColors.getColor1(180)), 55.0f * alphaPC);
            int czC2 = ColorUtils.swapAlpha(ColorUtils.getOverallColorFrom(-1, ClientColors.getColor2()), 55.0f * alphaPC);
            int czC3 = ColorUtils.swapAlpha(ColorUtils.getOverallColorFrom(-1, ClientColors.getColor2(180)), 25.0f * alphaPC);
            int czC4 = ColorUtils.swapAlpha(ColorUtils.getOverallColorFrom(-1, ClientColors.getColor1(0)), 25.0f * alphaPC);
            RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x, y, x + w, y + h, 1.0f, 9.0f, czC1, czC2, czC3, czC4, true, false, true);
            if (255.0f * alphaPC >= 28.0f) {
                Fonts.mntsb_15.drawString("Color mode", x + 3.0f, y + 3.0f, ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), 255.0f * alphaPC));
            }
            float yStep = 10.0f;
            for (String mode : setting.modes) {
                int color1 = mode.equalsIgnoreCase(currentMode) ? ClientColors.getColor1(0) : -1;
                int color2 = mode.equalsIgnoreCase(currentMode) ? ClientColors.getColor2(0) : -1;
                int colorSelect1 = ColorUtils.getColor(255, 255, 255, 20.0f * alphaPC);
                int colorSelect2 = ColorUtils.getColor(255, 255, 255, 20.0f * alphaPC);
                RenderUtils.drawFullGradientRectPro(x, y + yStep, x + w, y + yStep + 10.0f, colorSelect1, colorSelect2, colorSelect2, colorSelect1, false);
                if (mode.equalsIgnoreCase(currentMode)) {
                    float changePC = modeStepAnimGetAnim;
                    if ((double)changePC < 0.5) {
                        changePC = (double)(changePC *= 2.0f) > 0.5 ? 1.0f - changePC : changePC;
                        changePC *= 2.0f;
                    } else {
                        changePC = 0.0f;
                    }
                    colorSelect1 = ColorUtils.swapAlpha(color1, (45.0f + changePC * 50.0f) * alphaPC);
                    colorSelect2 = ColorUtils.swapAlpha(color2, (45.0f + changePC * 50.0f) * alphaPC);
                    float WMM = w / 2.0f - (float)Fonts.comfortaaBold_14.getStringWidth(mode) / 2.0f;
                    RenderUtils.drawFullGradientRectPro(x, y + yStep, x + w, y + yStep + 10.0f, colorSelect1, colorSelect2, colorSelect2, colorSelect1, true);
                    changePC = modeStepAnimGetAnim;
                    changePC = (double)changePC > 0.5 ? 1.0f - changePC : changePC;
                    RenderUtils.drawAlphedRect(x, y + yStep, x + 1.0f, y + yStep + 10.0f * modeStepAnimGetAnim, ColorUtils.swapAlpha(-1, 255.0f * (changePC *= 2.0f) * alphaPC));
                    RenderUtils.drawAlphedRect(x + w - 1.0f, y + yStep + 10.0f * (1.0f - modeStepAnimGetAnim), x + w, y + yStep + 10.0f, ColorUtils.swapAlpha(-1, 255.0f * changePC * alphaPC));
                    RenderUtils.drawAlphedSideways(x, y + yStep, x + w * modeStepAnimGetAnim, y + yStep + 10.0f, 0, ColorUtils.swapAlpha(-1, 255.0f * changePC * alphaPC), true);
                    RenderUtils.drawAlphedSideways(x + w * modeStepAnimGetAnim, y + yStep, x + w, y + yStep + 10.0f, ColorUtils.swapAlpha(-1, 255.0f * changePC * alphaPC), 0, true);
                }
                int i = 0;
                for (char c : mode.toCharArray()) {
                    int modeColor;
                    int n = modeColor = mode.equalsIgnoreCase(currentMode) ? ColorUtils.getOverallColorFrom(ClientColors.getColor1(i), ClientColors.getColor2(i), i / Fonts.comfortaaBold_14.getStringWidth(mode)) : -1;
                    if (255.0f * alphaPC >= 33.0f) {
                        Fonts.comfortaaBold_14.drawStringWithShadow(String.valueOf(c), x + w / 2.0f - (float)(Fonts.comfortaaBold_14.getStringWidth(mode) / 2) + (float)i, y + yStep + 3.0f, ColorUtils.swapAlpha(modeColor, 255.0f * alphaPC));
                    }
                    i += Fonts.comfortaaBold_14.getStringWidth(String.valueOf(c));
                }
                yStep += 10.0f;
            }
        }

        public void drawPanel(int mouseX, int mouseY, float partialTicks, float x, float y, float w, float h, boolean opennd, ScaledResolution sr, float alphaPC) {
            CFontRenderer font = Fonts.mntsb_18;
            CFontRenderer icon = Fonts.iconswex_24;
            String currentMode = ClientColors.get.Mode.currentMode;
            float x2 = x + w;
            float y2 = y + h;
            int bgColor = ColorUtils.getColor(0, 0, 0, 200.0f * alphaPC);
            int bgColor2 = ColorUtils.getColor(0, 0, 0, 140.0f * alphaPC);
            int bgColorOutline = ColorUtils.getColor(255, 255, 255, 40.0f * alphaPC);
            int shadowCol = ColorUtils.getColor(0, 0, 0, 70.0f * alphaPC);
            int textColor = ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), 255.0f * alphaPC);
            int lineCol = ColorUtils.getColor(255, 255, 255, 255.0f * alphaPC);
            int lineCol2 = ColorUtils.getColor(255, 255, 255, 130.0f * alphaPC);
            int lineCol3 = ColorUtils.getColor(255, 255, 255, 50.0f * alphaPC);
            RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x, y, x2, y2, 3.0f, 6.0f, shadowCol, shadowCol, shadowCol, shadowCol, false, false, true);
            StencilUtil.initStencilToWrite();
            RenderUtils.drawAlphedRect(x, y + 18.5f, x2, y2, -1);
            StencilUtil.readStencilBuffer(0);
            RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x, y, x2, y2, 4.0f, 6.0f, bgColor, bgColor, bgColor, bgColor, false, true, false);
            StencilUtil.readStencilBuffer(1);
            RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x, y, x2, y2, 3.0f, 6.0f, bgColor2, bgColor2, bgColor2, bgColor2, false, true, false);
            RenderUtils.drawTwoAlphedSideways(x + 0.5f, y + 19.0f, x2 - 0.5f, y + 20.0f, lineCol, lineCol3, false);
            StencilUtil.uninitStencilBuffer();
            StencilUtil.initStencilToWrite();
            RenderUtils.drawRect(x, y2 - 2.0f, x2, y2, -1);
            StencilUtil.readStencilBuffer(1);
            RenderUtils.drawTwoAlphedSideways(x + 1.0f, y2 - 2.0f, x2 - 1.0f, y2 - 1.5f, lineCol, lineCol3, false);
            RenderUtils.drawTwoAlphedSideways(x + 1.5f, y2 - 1.5f, x2 - 1.5f, y2 - 1.0f, lineCol, lineCol3, false);
            RenderUtils.drawTwoAlphedSideways(x + 2.0f, y2 - 1.0f, x2 - 2.0f, y2 - 0.5f, lineCol2, lineCol3, false);
            StencilUtil.uninitStencilBuffer();
            RenderUtils.drawOutsideAndInsideFullRoundedFullGradientShadowRectWithBloomBoolShadowsBoolChangeShadowSize(x, y, x2, y2, 0.0f, 4.0f, 0.0f, lineCol2, lineCol2, lineCol3, lineCol3, false, true, false);
            RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x, y, x2, y2, 4.0f, 1.5f, lineCol2, lineCol2, lineCol3, lineCol3, false, false, true);
            StencilUtil.initStencilToWrite();
            RenderUtils.drawRect(x, y, x2, y2, -1);
            StencilUtil.readStencilBuffer(1);
            if (255.0f * alphaPC >= 28.0f) {
                font.drawString(this.className, x + 23.0f, y + 7.0f, textColor);
                icon.drawString("G", x + 6.0f, y + 8.0f, textColor);
            }
            StencilUtil.uninitStencilBuffer();
            if (this.getHeight() >= 23.0f) {
                StencilUtil.initStencilToWrite();
                RenderUtils.drawRect(x, y + 20.0f, x2, y2 - 2.0f, -1);
                StencilUtil.readStencilBuffer(1);
                float modesX = x + 5.0f;
                float modesY = (y -= this.getScrollY()) + 25.0f;
                float modesW = w - 10.0f;
                float modesH = this.getModesHeight();
                this.drawColorModes(modesX, modesY, modesW, modesH, sr, alphaPC);
                if (this.hasSettings()) {
                    RenderUtils.drawTwoAlphedSideways(x + 7.0f, modesY + modesH + 5.0f, x + w - 7.0f, modesY + modesH + 5.5f, lineCol2, lineCol3, false);
                }
                float presetsX = x + 5.0f;
                float presetsY = modesY + modesH + 11.0f;
                float presetsW = w - 10.0f;
                float presetsH = this.getPresetsHeight();
                float pickerX = x + 5.0f;
                float pickerY = modesY + modesH + 11.0f;
                float pickerW = w - 10.0f;
                float pickerH = this.getPickerHeight();
                float pickerX2 = pickerX;
                float pickerY2 = pickerY + pickerH + 11.0f;
                if (currentMode.equalsIgnoreCase("Presets")) {
                    this.drawColorPresets(presetsX, presetsY, presetsW, presetsH, sr, alphaPC);
                }
                if (currentMode.equalsIgnoreCase("Colored") || currentMode.equalsIgnoreCase("TwoColored") || currentMode.equalsIgnoreCase("Fade")) {
                    this.drawColorPicker(pickerX, pickerY, pickerW, pickerH, sr, mouseX, mouseY, alphaPC);
                }
                if (currentMode.equalsIgnoreCase("TwoColored") || currentMode.equalsIgnoreCase("Fade")) {
                    RenderUtils.drawTwoAlphedSideways(x + 7.0f, pickerY + pickerH + 5.0f, x + w - 7.0f, pickerY + pickerH + 5.5f, lineCol2, lineCol3, false);
                    this.drawColorPicker2(pickerX, pickerY + 11.0f + pickerH, pickerW, pickerH, sr, mouseX, mouseY, alphaPC);
                }
                float flatY = y + this.getTotalElementsHeight() + 29.0f;
                float flatStepX = 2.0f;
                float flatsCount = 45.0f;
                int flatsTimeOn = 600;
                int flatsTimeStep = 15;
                float flatX = x + this.getWidth() / 2.0f - flatStepX * (flatsCount - 1.0f) / 2.0f;
                if (alphaPC * 255.0f >= 32.0f) {
                    int c = 0;
                    while ((float)c < flatsCount) {
                        float timePC = (float)((System.currentTimeMillis() + (long)(flatsTimeStep * c)) % (long)flatsTimeOn) / (float)flatsTimeOn;
                        timePC = (double)timePC > 0.5 ? 1.0f - timePC : timePC;
                        RenderUtils.drawSmoothCircle(flatX, flatY + ((timePC *= 2.0f) - 0.5f) * 2.0f, 1.0f, ClientColors.getColor1(c * 10));
                        RenderUtils.drawSmoothCircle(flatX, flatY + (timePC - 0.5f) * 3.0f, 1.0f, ClientColors.getColor1(c * 13));
                        RenderUtils.drawSmoothCircle(flatX, flatY + (timePC - 0.5f) * 4.0f, 1.0f, ClientColors.getColor1(c * 16));
                        RenderUtils.drawSmoothCircle(flatX, flatY + (timePC - 0.5f) * 6.0f, 0.5f, ClientColors.getColor1(c * 19));
                        flatX += flatStepX;
                        ++c;
                    }
                }
                StencilUtil.uninitStencilBuffer();
            }
        }

        private double getMouseSpeed() {
            float dX = Mouse.getDX();
            float dY = Mouse.getDX();
            return Math.sqrt(dX * dX + dY * dY);
        }

        private long getTimerateSoundMove(double mouseSpeed) {
            if (mouseSpeed == 0.0) {
                return Long.MAX_VALUE;
            }
            long time = 1000L;
            time = (long)((double)time / MathUtils.clamp(mouseSpeed * 10.0, 1.0, 100.0));
            return time;
        }

        private void updateSliderSounds() {
            if (this.soundTicker.hasReached(this.getTimerateSoundMove(this.getMouseSpeed()))) {
                ClientTune.get.playGuiSliderMoveSong();
                this.soundTicker.reset();
            }
        }

        public void drawColorPicker(float x, float y, float w, float h, ScaledResolution sr, int mouseX, int mouseY, float alphaPC) {
            if (255.0f * alphaPC <= 26.0f) {
                return;
            }
            Settings setting = (Settings)ClientColors.get.settings.get(4);
            boolean doublePicker = false;
            RenderUtils.drawAlphedRect(x, y + 15.0f, x + w, y + h, ColorUtils.getColor(0, 0, 0, 60.0f * alphaPC));
            RenderUtils.drawLightContureRect(x, y, x + w, y + h, ColorUtils.getColor(255, 255, 255, 60.0f * alphaPC));
            RenderUtils.drawAlphedRect(x, y, x + w, y + 15.0f, ColorUtils.getColor(0, 0, 0, 90.0f * alphaPC));
            int czC1 = ColorUtils.swapAlpha(ColorUtils.getOverallColorFrom(-1, ClientColors.getColor1(180)), 55.0f * alphaPC);
            int czC2 = ColorUtils.swapAlpha(ColorUtils.getOverallColorFrom(-1, ClientColors.getColor2()), 55.0f * alphaPC);
            int czC3 = ColorUtils.swapAlpha(ColorUtils.getOverallColorFrom(-1, ClientColors.getColor2(180)), 25.0f * alphaPC);
            int czC4 = ColorUtils.swapAlpha(ColorUtils.getOverallColorFrom(-1, ClientColors.getColor1(0)), 25.0f * alphaPC);
            RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x, y, x + w, y + h, 1.0f, 9.0f, czC1, czC2, czC3, czC4, true, false, true);
            if (255.0f * alphaPC >= 28.0f) {
                Fonts.mntsb_15.drawString("Pick color", x + 14.0f, y + 6.0f, ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), 255.0f * alphaPC));
                Fonts.stylesicons_20.drawString("M", x + 3.0f, y + 6.5f, ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), 255.0f * alphaPC));
            }
            int finalColor = ColorUtils.swapAlpha(setting.color, (float)ColorUtils.getAlphaFromColor(setting.color) * alphaPC);
            int finalColor2 = ColorUtils.swapAlpha(setting.color, (float)ColorUtils.getAlphaFromColor(setting.color) / 2.5f * alphaPC);
            RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x + w - 18.0f, y + 6.0f, x + w - 18.0f + 11.0f, y + 11.0f, 2.0f, 0.5f, finalColor, finalColor, finalColor, finalColor, false, true, true);
            RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x + w - 18.0f, y + 6.0f, x + w - 18.0f + 11.0f, y + 11.0f, 2.0f, 6.0f, finalColor2, finalColor2, finalColor2, finalColor2, true, false, true);
            int colHSB = -1;
            float hsbX = x + 6.0f;
            float hsbY = y + 68.0f;
            float hsbW = w - 12.0f;
            float hsbH = 6.0f;
            float alphaX = x + 6.0f;
            float alphaY = y + 86.0f;
            float alphaW = w - 12.0f;
            float alphaH = 6.0f;
            float grX = x + 5.0f;
            float grY = y + 20.0f;
            float grH = 35.0f;
            float grW = w - 10.0f;
            float draggXgr = grX + this.offsetX;
            float draggYgr = grY + this.offsetY;
            float percXgr = this.offsetX / grW;
            float percYgr = this.offsetY / grH;
            if (this.dragginggr) {
                this.dragginghsb = false;
                this.draggingalpha = false;
                this.updateSliderSounds();
                this.offsetX = MathUtils.clamp((float)mouseX - grX, 0.0f, grW);
                this.offsetY = MathUtils.clamp((float)mouseY - grY, 0.0f, grH);
            } else if (ColorUtils.getSaturateFromColor(setting.color) != 0.0f) {
                this.offsetX = ColorUtils.getSaturateFromColor(setting.color) * grW;
                this.offsetY = grH - ColorUtils.getBrightnessFromColor(setting.color) * grH;
            }
            RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(grX, grY, grX + grW, grY + grH, 2.0f, 1.0f, ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), 255.0f * alphaPC), ColorUtils.swapAlpha(Color.getHSBColor(this.offsetX2 / hsbW, 1.0f, 1.0f).getRGB(), 255.0f * alphaPC), ColorUtils.swapAlpha(ColorUtils.getColor(0, 0, 0), 255.0f * alphaPC), ColorUtils.swapAlpha(ColorUtils.getColor(0, 0, 0), 255.0f * alphaPC), false, true, true);
            RenderUtils.resetBlender();
            RenderUtils.drawLightContureRect(hsbX, hsbY, hsbX + hsbW, hsbY + hsbH, ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), 255.0f * alphaPC));
            float draggXhsb = hsbX + this.offsetX2;
            float HSBCC = this.offsetX2 / hsbW * 360.0f % 360.0f;
            if (HSBCC < 0.0f) {
                HSBCC = 360.0f - HSBCC;
            }
            float percXhsb = HSBCC / 360.0f;
            float percXalpha = this.offsetX3 / alphaW;
            Fonts.comfortaaBold_12.drawStringWithShadow("Hsb - " + String.format("%.2f", Float.valueOf(HSBCC)), hsbX + 1.0f, hsbY - 7.0f, ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), 255.0f * alphaPC));
            for (int i = 0; i < 359; ++i) {
                float hsb = (float)i / 360.0f;
                hsb = hsb < 0.0f ? 0.0f : (hsb > 1.0f ? 1.0f : hsb);
                colHSB = Color.getHSBColor(hsb, 1.0f, 1.0f).getRGB();
                float pc = (float)i / 360.0f * (hsbW - 0.5f);
                RenderUtils.drawAlphedRect(hsbX + pc, hsbY, hsbX + pc + 0.5f, hsbY + hsbH, ColorUtils.swapAlpha(colHSB, 255.0f * alphaPC));
            }
            RenderUtils.drawLightContureRect(alphaX, alphaY, alphaX + alphaW, alphaY + alphaH, ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), 255.0f * alphaPC));
            if (255.0f * alphaPC >= 28.0f) {
                Fonts.comfortaaBold_12.drawStringWithShadow("Alpha - " + (int)(percXalpha * 255.0f), alphaX + 1.0f, alphaY - 7.0f, ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), 255.0f * alphaPC));
            }
            alphaW *= 2.0f;
            alphaH *= 2.0f;
            int i2 = 0;
            while ((float)i2 < alphaH) {
                int i = 0;
                while ((float)i < alphaW) {
                    int colTest = i % 8 == 0 && i2 != 4 || i2 == 4 && i % 8 != 0 ? ColorUtils.getColor(125, 125, 125, 125) : ColorUtils.getColor(200, 200, 200, 125);
                    int colAlpha = ColorUtils.getOverallColorFrom(colTest, ColorUtils.swapAlpha(setting.color, 255.0f), (float)i / alphaW);
                    RenderUtils.drawAlphedRect(alphaX + (float)(i / 2), alphaY + (float)(i2 / 2), alphaX + (float)(i / 2) + 2.0f, alphaY + (float)(i2 / 2) + 2.0f, ColorUtils.swapAlpha(colAlpha, 255.0f * alphaPC));
                    i += 4;
                }
                i2 += 4;
            }
            alphaW /= 2.0f;
            float alphaXCursor = alphaX + this.offsetX3;
            float alphaYCursor = alphaY + (alphaH /= 2.0f) / 2.0f;
            RenderUtils.drawAlphedRect(alphaXCursor - 1.5f, alphaYCursor - 2.5f, alphaXCursor + 1.5f, alphaYCursor + 2.5f, ColorUtils.swapAlpha(ColorUtils.getColor(0, 0, 0), 255.0f * alphaPC));
            RenderUtils.drawAlphedRect(alphaXCursor - 1.0f, alphaYCursor - 2.0f, alphaXCursor + 1.0f, alphaYCursor + 2.0f, ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), 255.0f * alphaPC));
            RenderUtils.drawAlphedRect(draggXgr - 1.5f, draggYgr - 1.5f, draggXgr + 1.5f, draggYgr + 1.5f, ColorUtils.swapAlpha(ColorUtils.getColor(0, 0, 0), 255.0f * alphaPC));
            RenderUtils.drawAlphedRect(draggXgr - 1.0f, draggYgr - 1.0f, draggXgr + 1.0f, draggYgr + 1.0f, ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), 255.0f * alphaPC));
            float hsbXCursor = hsbX + this.offsetX2;
            float hsbYCursor = hsbY + hsbH / 2.0f;
            RenderUtils.drawAlphedRect(hsbXCursor - 1.5f, hsbYCursor - 2.5f, hsbXCursor + 1.5f, hsbYCursor + 2.5f, ColorUtils.swapAlpha(ColorUtils.getColor(0, 0, 0), 255.0f * alphaPC));
            RenderUtils.drawAlphedRect(hsbXCursor - 1.0f, hsbYCursor - 2.0f, hsbXCursor + 1.0f, hsbYCursor + 2.0f, ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), 255.0f * alphaPC));
            if (this.dragginghsb) {
                this.dragginggr = false;
                this.draggingalpha = false;
                this.offsetX2 = MathUtils.clamp((float)mouseX - hsbX, 0.0f, hsbW);
                this.updateSliderSounds();
            } else if (ColorUtils.getHueFromColor(setting.color) != 0 && !this.dragginggr) {
                this.offsetX2 = (float)ColorUtils.getHueFromColor(setting.color) / 360.0f * hsbW;
            } else if (this.offsetX2 == -1.2312312E8f) {
                this.offsetX2 = 0.0f;
            }
            if (this.draggingalpha) {
                this.dragginggr = false;
                this.dragginghsb = false;
                this.offsetX3 = MathUtils.clamp((float)mouseX - alphaX, 0.0f, alphaW);
                this.updateSliderSounds();
            } else {
                this.offsetX3 = (float)ColorUtils.getAlphaFromColor(setting.color) / 255.0f * alphaW;
            }
            float hsb = percXhsb;
            int col = Color.getHSBColor(MathUtils.clamp(percXhsb, 0.0f, 0.999f), percXgr, 1.0f - percYgr).getRGB();
            col = ColorUtils.swapAlpha(col, 255.0f * percXalpha);
            if (col != 0 && Mouse.isButtonDown(0)) {
                setting.color = col;
            }
        }

        public void drawColorPicker2(float x, float y, float w, float h, ScaledResolution sr, int mouseX, int mouseY, float alphaPC) {
            if (255.0f * alphaPC <= 26.0f) {
                return;
            }
            Settings setting = (Settings)ClientColors.get.settings.get(5);
            boolean doublePicker = false;
            RenderUtils.drawAlphedRect(x, y + 15.0f, x + w, y + h, ColorUtils.getColor(0, 0, 0, 60.0f * alphaPC));
            RenderUtils.drawLightContureRect(x, y, x + w, y + h, ColorUtils.getColor(255, 255, 255, 60.0f * alphaPC));
            RenderUtils.drawAlphedRect(x, y, x + w, y + 15.0f, ColorUtils.getColor(0, 0, 0, 90.0f * alphaPC));
            int czC1 = ColorUtils.swapAlpha(ColorUtils.getOverallColorFrom(-1, ClientColors.getColor1(180)), 55.0f * alphaPC);
            int czC2 = ColorUtils.swapAlpha(ColorUtils.getOverallColorFrom(-1, ClientColors.getColor2()), 55.0f * alphaPC);
            int czC3 = ColorUtils.swapAlpha(ColorUtils.getOverallColorFrom(-1, ClientColors.getColor2(180)), 25.0f * alphaPC);
            int czC4 = ColorUtils.swapAlpha(ColorUtils.getOverallColorFrom(-1, ClientColors.getColor1(0)), 25.0f * alphaPC);
            RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x, y, x + w, y + h, 1.0f, 9.0f, czC1, czC2, czC3, czC4, true, false, true);
            if (255.0f * alphaPC >= 28.0f) {
                Fonts.mntsb_15.drawString("Pick color 2", x + 14.0f, y + 6.0f, ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), 255.0f * alphaPC));
                Fonts.stylesicons_20.drawString("M", x + 3.0f, y + 6.5f, ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), 255.0f * alphaPC));
            }
            int finalColor = ColorUtils.swapAlpha(setting.color, (float)ColorUtils.getAlphaFromColor(setting.color) * alphaPC);
            int finalColor2 = ColorUtils.swapAlpha(setting.color, (float)ColorUtils.getAlphaFromColor(setting.color) / 2.5f * alphaPC);
            RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x + w - 18.0f, y + 6.0f, x + w - 18.0f + 11.0f, y + 11.0f, 2.0f, 0.5f, finalColor, finalColor, finalColor, finalColor, false, true, true);
            RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x + w - 18.0f, y + 6.0f, x + w - 18.0f + 11.0f, y + 11.0f, 2.0f, 6.0f, finalColor2, finalColor2, finalColor2, finalColor2, true, false, true);
            int colHSB = -1;
            float hsbX = x + 6.0f;
            float hsbY = y + 68.0f;
            float hsbW = w - 12.0f;
            float hsbH = 6.0f;
            float alphaX = x + 6.0f;
            float alphaY = y + 86.0f;
            float alphaW = w - 12.0f;
            float alphaH = 6.0f;
            float grX = x + 5.0f;
            float grY = y + 20.0f;
            float grH = 35.0f;
            float grW = w - 10.0f;
            float draggXgr = grX + this.offsetXs;
            float draggYgr = grY + this.offsetYs;
            float percXgr = this.offsetXs / grW;
            float percYgr = this.offsetYs / grH;
            if (this.dragginggrs) {
                this.dragginghsbs = false;
                this.draggingalphas = false;
                this.updateSliderSounds();
                this.offsetXs = MathUtils.clamp((float)mouseX - grX, 0.0f, grW);
                this.offsetYs = MathUtils.clamp((float)mouseY - grY, 0.0f, grH);
            } else if (ColorUtils.getSaturateFromColor(setting.color) != 0.0f) {
                this.offsetXs = ColorUtils.getSaturateFromColor(setting.color) * grW;
                this.offsetYs = grH - ColorUtils.getBrightnessFromColor(setting.color) * grH;
            }
            RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(grX, grY, grX + grW, grY + grH, 2.0f, 1.0f, ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), 255.0f * alphaPC), ColorUtils.swapAlpha(Color.getHSBColor(this.offsetX2s / hsbW, 1.0f, 1.0f).getRGB(), 255.0f * alphaPC), ColorUtils.swapAlpha(ColorUtils.getColor(0, 0, 0), 255.0f * alphaPC), ColorUtils.swapAlpha(ColorUtils.getColor(0, 0, 0), 255.0f * alphaPC), false, true, true);
            RenderUtils.resetBlender();
            RenderUtils.drawLightContureRect(hsbX, hsbY, hsbX + hsbW, hsbY + hsbH, ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), 255.0f * alphaPC));
            float draggXhsb = hsbX + this.offsetX2s;
            float HSBCC = this.offsetX2s / hsbW * 360.0f % 360.0f;
            if (HSBCC < 0.0f) {
                HSBCC = 360.0f - HSBCC;
            }
            float percXhsb = HSBCC / 360.0f;
            float percXalpha = this.offsetX3s / alphaW;
            if (255.0f * alphaPC >= 28.0f) {
                Fonts.comfortaaBold_12.drawStringWithShadow("Hsb - " + String.format("%.2f", Float.valueOf(HSBCC)), hsbX + 1.0f, hsbY - 7.0f, ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), 255.0f * alphaPC));
            }
            for (int i = 0; i < 359; ++i) {
                float hsb = (float)i / 360.0f;
                hsb = hsb < 0.0f ? 0.0f : (hsb > 1.0f ? 1.0f : hsb);
                colHSB = Color.getHSBColor(hsb, 1.0f, 1.0f).getRGB();
                float pc = (float)i / 360.0f * (hsbW - 0.5f);
                RenderUtils.drawAlphedRect(hsbX + pc, hsbY, hsbX + pc + 0.5f, hsbY + hsbH, ColorUtils.swapAlpha(colHSB, 255.0f * alphaPC));
            }
            RenderUtils.drawLightContureRect(alphaX, alphaY, alphaX + alphaW, alphaY + alphaH, ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), 255.0f * alphaPC));
            if (255.0f * alphaPC >= 28.0f) {
                Fonts.comfortaaBold_12.drawStringWithShadow("Alpha - " + (int)(percXalpha * 255.0f), alphaX + 1.0f, alphaY - 7.0f, ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), 255.0f * alphaPC));
            }
            alphaW *= 2.0f;
            alphaH *= 2.0f;
            int i2 = 0;
            while ((float)i2 < alphaH) {
                int i = 0;
                while ((float)i < alphaW) {
                    int colTest = i % 8 == 0 && i2 != 4 || i2 == 4 && i % 8 != 0 ? ColorUtils.getColor(125, 125, 125, 125) : ColorUtils.getColor(200, 200, 200, 125);
                    int colAlpha = ColorUtils.getOverallColorFrom(colTest, ColorUtils.swapAlpha(setting.color, 255.0f), (float)i / alphaW);
                    RenderUtils.drawAlphedRect(alphaX + (float)(i / 2), alphaY + (float)(i2 / 2), alphaX + (float)(i / 2) + 2.0f, alphaY + (float)(i2 / 2) + 2.0f, ColorUtils.swapAlpha(colAlpha, 255.0f * alphaPC));
                    i += 4;
                }
                i2 += 4;
            }
            alphaW /= 2.0f;
            float alphaXCursor = alphaX + this.offsetX3s;
            float alphaYCursor = alphaY + (alphaH /= 2.0f) / 2.0f;
            RenderUtils.drawAlphedRect(alphaXCursor - 1.5f, alphaYCursor - 2.5f, alphaXCursor + 1.5f, alphaYCursor + 2.5f, ColorUtils.swapAlpha(ColorUtils.getColor(0, 0, 0), 255.0f * alphaPC));
            RenderUtils.drawAlphedRect(alphaXCursor - 1.0f, alphaYCursor - 2.0f, alphaXCursor + 1.0f, alphaYCursor + 2.0f, ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), 255.0f * alphaPC));
            RenderUtils.drawAlphedRect(draggXgr - 1.5f, draggYgr - 1.5f, draggXgr + 1.5f, draggYgr + 1.5f, ColorUtils.swapAlpha(ColorUtils.getColor(0, 0, 0), 255.0f * alphaPC));
            RenderUtils.drawAlphedRect(draggXgr - 1.0f, draggYgr - 1.0f, draggXgr + 1.0f, draggYgr + 1.0f, ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), 255.0f * alphaPC));
            float hsbXCursor = hsbX + this.offsetX2s;
            float hsbYCursor = hsbY + hsbH / 2.0f;
            RenderUtils.drawAlphedRect(hsbXCursor - 1.5f, hsbYCursor - 2.5f, hsbXCursor + 1.5f, hsbYCursor + 2.5f, ColorUtils.swapAlpha(ColorUtils.getColor(0, 0, 0), 255.0f * alphaPC));
            RenderUtils.drawAlphedRect(hsbXCursor - 1.0f, hsbYCursor - 2.0f, hsbXCursor + 1.0f, hsbYCursor + 2.0f, ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), 255.0f * alphaPC));
            if (this.dragginghsbs) {
                this.dragginggrs = false;
                this.draggingalphas = false;
                this.offsetX2s = MathUtils.clamp((float)mouseX - hsbX, 0.0f, hsbW);
                this.updateSliderSounds();
            } else if (ColorUtils.getHueFromColor(setting.color) != 0 && !this.dragginggrs) {
                this.offsetX2s = (float)ColorUtils.getHueFromColor(setting.color) / 360.0f * hsbW;
            } else if (this.offsetX2s == -1.2312312E8f) {
                this.offsetX2s = 0.0f;
            }
            if (this.draggingalphas) {
                this.dragginggrs = false;
                this.dragginghsbs = false;
                this.offsetX3s = MathUtils.clamp((float)mouseX - alphaX, 0.0f, alphaW);
                this.updateSliderSounds();
            } else {
                this.offsetX3s = (float)ColorUtils.getAlphaFromColor(setting.color) / 255.0f * alphaW;
            }
            float hsb = percXhsb;
            int col = Color.getHSBColor(MathUtils.clamp(percXhsb, 0.0f, 0.999f), percXgr, 1.0f - percYgr).getRGB();
            col = ColorUtils.swapAlpha(col, 255.0f * percXalpha);
            if (col != 0 && Mouse.isButtonDown(0)) {
                setting.color = col;
            }
        }

        public void drawColorPresets(float x, float y, float w, float h, ScaledResolution sr, float alphaPC) {
            RenderUtils.drawAlphedRect(x, y + 15.0f, x + w, y + h, ColorUtils.getColor(0, 0, 0, 60.0f * alphaPC));
            RenderUtils.drawLightContureRect(x, y, x + w, y + h, ColorUtils.getColor(255, 255, 255, 60.0f * alphaPC));
            RenderUtils.drawAlphedRect(x, y, x + w, y + 15.0f, ColorUtils.getColor(0, 0, 0, 90.0f * alphaPC));
            int czC1 = ColorUtils.swapAlpha(ColorUtils.getOverallColorFrom(-1, ClientColors.getColor1(180)), 55.0f * alphaPC);
            int czC2 = ColorUtils.swapAlpha(ColorUtils.getOverallColorFrom(-1, ClientColors.getColor2()), 55.0f * alphaPC);
            int czC3 = ColorUtils.swapAlpha(ColorUtils.getOverallColorFrom(-1, ClientColors.getColor2(180)), 25.0f * alphaPC);
            int czC4 = ColorUtils.swapAlpha(ColorUtils.getOverallColorFrom(-1, ClientColors.getColor1(0)), 25.0f * alphaPC);
            RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x, y, x + w, y + h, 1.0f, 9.0f, czC1, czC2, czC3, czC4, true, false, true);
            if (255.0f * alphaPC >= 28.0f) {
                Fonts.mntsb_15.drawString("Presets", x + 14.0f, y + 6.0f, ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), 255.0f * alphaPC));
                Fonts.stylesicons_20.drawString("M", x + 3.0f, y + 6.5f, ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), 255.0f * alphaPC));
            }
            CFontRenderer font = Fonts.noise_15;
            float yStep = 15.0f;
            for (PresetColors colors : PresetColors.values()) {
                int bgElementCol = colors.equals((Object)ClientColors.getCurrentPreset()) ? ColorUtils.swapAlpha(ColorUtils.getOverallColorFrom(ColorUtils.getOverallColorFrom(ClientColors.getColor1(), ClientColors.getColor2()), -1, presetStepAnimGetAnim == 0.0f ? 1.0f : presetStepAnimGetAnim), (30.0f + (presetStepAnimGetAnim == 0.0f ? 0.0f : 1.0f - presetStepAnimGetAnim) * 225.0f) * alphaPC) : 0;
                RenderUtils.drawAlphedRect(x + 1.0f, y + yStep + 1.0f, x + w - 1.0f, y + yStep + 20.0f - 1.0f, bgElementCol);
                if (colors.equals((Object)ClientColors.getCurrentPreset())) {
                    RenderUtils.drawAlphedSideways(x + 3.0f, y + yStep + 1.0f, x + w / 2.0f, y + yStep + 20.0f - 1.0f, bgElementCol, 0, true);
                    RenderUtils.drawAlphedSideways(x + 1.0f, y + yStep + 1.0f, x + 3.0f, y + yStep + 20.0f - 1.0f, 0, bgElementCol, true);
                }
                if (colors.equals((Object)ClientColors.getCurrentPreset()) && presetStepAnimGetAnim != 0.0f) {
                    float changePC = presetStepAnimGetAnim;
                    changePC = (double)changePC > 0.5 ? 1.0f - changePC : changePC;
                    int waveC = ColorUtils.swapAlpha(-1, (changePC *= 2.0f) * 255.0f * alphaPC);
                    if (ColorUtils.getAlphaFromColor(waveC) > 26) {
                        RenderUtils.drawAlphedSideways(x + 1.0f, y + yStep + 2.0f, x + (w - 1.0f) * presetStepAnimGetAnim, y + yStep + 20.0f - 2.0f, 0, waveC, true);
                        RenderUtils.drawAlphedSideways(x + (w - 1.0f) * presetStepAnimGetAnim, y + yStep + 2.0f, x + w - 1.0f, y + yStep + 20.0f - 2.0f, waveC, 0, true);
                        RenderUtils.drawAlphedRect(x + (w - 1.0f) - (w - 1.0f) * presetStepAnimGetAnim, y + yStep + 1.0f, x + w - 1.0f, y + yStep + 2.0f, waveC);
                        RenderUtils.drawAlphedRect(x + (w - 1.0f) - (w - 1.0f) * presetStepAnimGetAnim, y + yStep + 20.0f - 2.0f, x + w - 1.0f, y + yStep + 20.0f - 1.0f, waveC);
                        RenderUtils.drawAlphedRect(x + 1.0f, y + yStep + 1.0f, x + 2.0f, y + yStep + 20.0f * presetStepAnimGetAnim - 1.0f, waveC);
                        RenderUtils.drawAlphedRect(x + (w - 2.0f), y + yStep + 1.0f + 20.0f - 20.0f * presetStepAnimGetAnim, x + (w - 1.0f), y + yStep + 20.0f - 1.0f, waveC);
                        GL11.glPushMatrix();
                        GL11.glTranslated(2.0f * changePC, 0.0, 0.0);
                        changePC = MathUtils.clamp(presetStepAnimGetAnim, 0.0f, 0.5f) * 2.0f;
                        changePC = (double)changePC > 0.5 ? 1.0f - changePC : changePC;
                        RenderUtils.customRotatedObject2D(x + 6.0f, y + yStep, font.getStringWidth(colors.name), 20.0f, (changePC *= 2.0f) * 20.0f);
                    }
                }
                int i = 0;
                for (char c : colors.name.toCharArray()) {
                    int textColor = ColorUtils.getOverallColorFrom(ColorUtils.getFixedWhiteColor(), ColorUtils.getOverallColorFrom(colors.color1, colors.color2, (float)i / (float)font.getStringWidth(colors.name)), 0.6f);
                    font.drawStringWithOutline(String.valueOf(c), x + 6.0f + (float)i, y + yStep + 8.0f, ColorUtils.swapAlpha(textColor, 255.0f * alphaPC));
                    i += font.getStringWidth(String.valueOf(c));
                }
                if (colors.equals((Object)ClientColors.getCurrentPreset()) && presetStepAnimGetAnim != 0.0f) {
                    GL11.glPopMatrix();
                }
                float ocX = w - 35.0f;
                float tcX = w - 20.0f;
                float otcSize = 10.0f;
                float otxExtY = 5.0f;
                int c1 = ColorUtils.swapAlpha(colors.color1, 255.0f * alphaPC);
                int c2 = ColorUtils.swapAlpha(colors.color2, 255.0f * alphaPC);
                int cBGC = ColorUtils.getColor(0, 0, 0, 120.0f * alphaPC);
                RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x + ocX - 1.0f, y + yStep + otxExtY - 1.0f, x + tcX + otcSize + 1.0f, y + yStep + otcSize + otxExtY + 1.0f, 3.0f, 2.0f, cBGC, cBGC, cBGC, cBGC, false, true, true);
                if (c1 == c2) {
                    boolean scalling = colors.equals((Object)ClientColors.getCurrentPreset()) && presetStepAnimGetAnim != 0.0f;
                    float scale = 0.0f;
                    if (scalling) {
                        scale = presetStepAnimGetAnim;
                        scale = (double)scale > 0.5 ? 1.0f - scale : scale;
                        scale *= 2.0f;
                        scale = 1.0f - 0.25f * scale;
                    }
                    if (scale != 0.0f) {
                        GL11.glPushMatrix();
                        RenderUtils.customScaledObject2D(x + ocX, y + yStep + otxExtY, tcX - ocX + otcSize, otcSize, scale);
                    }
                    RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x + ocX, y + yStep + otxExtY, x + tcX + otcSize, y + yStep + otcSize + otxExtY, 2.0f, 8.0f, ColorUtils.swapAlpha(c1, 65.0f * alphaPC), ColorUtils.swapAlpha(c2, 85.0f * alphaPC), ColorUtils.swapAlpha(c2, 85.0f * alphaPC), ColorUtils.swapAlpha(c1, 65.0f * alphaPC), true, false, true);
                    RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x + ocX, y + yStep + otxExtY, x + tcX + otcSize, y + yStep + otcSize + otxExtY, 2.0f, 1.0f, c1, c2, c2, c1, false, true, true);
                    if (scale != 0.0f) {
                        GL11.glPopMatrix();
                    }
                } else {
                    boolean rotate = colors.equals((Object)ClientColors.getCurrentPreset()) && presetStepAnimGetAnim != 0.0f;
                    float rot = 0.0f;
                    if (rotate) {
                        GL11.glPushMatrix();
                        rot = presetStepAnimGetAnim;
                        rot *= 180.0f;
                    }
                    if (rot != 0.0f) {
                        RenderUtils.customRotatedObject2D(x + ocX, y + yStep + otxExtY, otcSize, otcSize, rot);
                    }
                    RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x + ocX, y + yStep + otxExtY, x + ocX + otcSize, y + yStep + otcSize + otxExtY, 2.0f, 8.0f, ColorUtils.swapAlpha(c1, 65.0f * alphaPC), ColorUtils.swapAlpha(c1, 75.0f * alphaPC), ColorUtils.swapAlpha(c1, 75.0f * alphaPC), ColorUtils.swapAlpha(c1, 65.0f * alphaPC), true, false, true);
                    RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x + ocX, y + yStep + otxExtY, x + ocX + otcSize, y + yStep + otcSize + otxExtY, 2.0f, 1.0f, c1, c1, c1, c1, false, true, true);
                    if (rot != 0.0f) {
                        RenderUtils.customRotatedObject2D(x + ocX, y + yStep + otxExtY, otcSize, otcSize, -rot);
                    }
                    if (rot != 0.0f) {
                        RenderUtils.customRotatedObject2D(x + tcX, y + yStep + otxExtY, otcSize, otcSize, rot);
                    }
                    RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x + tcX, y + yStep + otxExtY, x + tcX + otcSize, y + yStep + otcSize + otxExtY, 2.0f, 8.0f, ColorUtils.swapAlpha(c2, 75.0f * alphaPC), ColorUtils.swapAlpha(c2, 85.0f * alphaPC), ColorUtils.swapAlpha(c2, 85.0f * alphaPC), ColorUtils.swapAlpha(c2, 75.0f * alphaPC), true, false, true);
                    RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x + tcX, y + yStep + otxExtY, x + tcX + otcSize, y + yStep + otcSize + otxExtY, 2.0f, 1.0f, c2, c2, c2, c2, false, true, true);
                    if (rotate) {
                        GL11.glPopMatrix();
                    }
                }
                yStep += 20.0f;
            }
        }

        public float getPresetsHeight() {
            int height = 15;
            for (PresetColors colors : PresetColors.values()) {
                height += 20;
            }
            return ClientColors.get.Mode.currentMode.equalsIgnoreCase("Presets") ? (float)height : 0.0f;
        }

        public boolean pickerIsDouble() {
            String currentMode = ClientColors.get.Mode.currentMode;
            return currentMode.equalsIgnoreCase("TwoColored") || currentMode.equalsIgnoreCase("Fade");
        }

        public float getPickerHeight() {
            return 98.0f;
        }

        public boolean isHoveredToPanel(int mouseX, int mouseY) {
            return this.isHover(this.getX(), this.getY(), this.getWidth(), this.getHeight(), mouseX, mouseY);
        }

        public boolean hasSettings() {
            String mode = ClientColors.get.Mode.currentMode;
            return mode.equalsIgnoreCase("Presets") || mode.equalsIgnoreCase("Colored") || mode.equalsIgnoreCase("TwoColored") || mode.equalsIgnoreCase("Fade");
        }

        public float getTotalElementsHeight() {
            boolean hasPickerDouble;
            String mode = ClientColors.get.Mode.currentMode;
            boolean hasPicker = mode.equalsIgnoreCase("Colored") || mode.equalsIgnoreCase("TwoColored") || mode.equalsIgnoreCase("Fade");
            boolean hasPresets = mode.equalsIgnoreCase("Presets");
            boolean bl = hasPickerDouble = hasPicker && this.pickerIsDouble();
            float getPickerHeight = hasPicker ? (hasPickerDouble ? 22.0f + this.getPickerHeight() * 2.0f : 12.0f + this.getPickerHeight()) : 0.0f;
            float getPresetsHeight = hasPresets ? 11.0f + this.getPresetsHeight() : 0.0f;
            float getModesHeight = this.getModesHeight();
            return getModesHeight + getPresetsHeight + getPickerHeight;
        }

        public void drawScreen(int mouseX, int mouseY, float partialTicks) {
            if (ClientColors.modeStepAnim.to == 1.0f) {
                modeStepAnimGetAnim = modeStepAnim.getAnim();
                if ((double)modeStepAnim.getAnim() > 0.99) {
                    modeStepAnim.setAnim(0.0f);
                    ClientColors.modeStepAnim.to = 0.0f;
                }
            } else {
                modeStepAnimGetAnim = 0.0f;
            }
            if (prevPreset != null && ClientColors.presetStepAnim.to == 1.0f) {
                presetStepAnimGetAnim = presetStepAnim.getAnim();
                if ((double)presetStepAnim.getAnim() > 0.99) {
                    presetStepAnim.setAnim(0.0f);
                    ClientColors.presetStepAnim.to = 0.0f;
                }
            } else {
                presetStepAnimGetAnim = 0.0f;
            }
            GlStateManager.disableDepth();
            this.updatePosXY();
            this.heightCalculate();
            this.dragUpdate(mouseX, mouseY);
            float alphaPC = ClickGuiScreen.globalAlpha.getAnim() / 255.0f * MathUtils.clamp(ClickGuiScreen.scale.getAnim(), 0.0f, 1.0f) * MathUtils.clamp(ClickGuiScreen.scale.getAnim(), 0.0f, 1.0f) * MathUtils.clamp(ClickGuiScreen.scale.getAnim(), 0.0f, 1.0f);
            GL11.glPushMatrix();
            RenderUtils.customScaledObject2D(this.getX(), this.getY(), this.getWidth(), this.getHeight(), MathUtils.clamp(ClickGuiScreen.scale.getAnim() * ClickGuiScreen.scale.getAnim() * ClickGuiScreen.scale.getAnim() * 1.01f, 0.0f, 1.0f));
            RenderUtils.customRotatedObject2D(this.getX(), this.getY(), this.dragX * 2.0f, this.dragY * 2.0f, this.getRotate());
            this.drawPanel(mouseX, mouseY, partialTicks, this.getX(), this.getY(), this.getWidth(), this.getHeight(), this.open, new ScaledResolution(Module.mc), alphaPC);
            GL11.glPopMatrix();
            GlStateManager.disableDepth();
            if (this.isHover(this.getX() - 5.0f, this.getY() - 5.0f, this.getWidth() + 10.0f, this.getHeight() + 10.0f, mouseX, mouseY)) {
                ClickGuiScreen.resetHolds();
            }
        }

        public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
            float grW;
            float grH;
            float grY;
            float grX;
            float alphaH;
            float alphaW;
            float alphaY;
            float posY;
            float yPos = this.getY();
            float xPos = this.getX();
            if (this.isHover(xPos, yPos, this.getWidth(), 20.0f, mouseX, mouseY)) {
                if (mouseButton == 0) {
                    this.dragging = true;
                } else if (mouseButton == 1) {
                    this.open = !this.open;
                    ClientTune.get.playGuiColorsScreenOpenOrCloseSong(this.open);
                }
            }
            if (this.getHeight() < 30.0f) {
                return;
            }
            yPos -= this.getScrollY();
            if (this.isHover(this.getX(), this.getY() + 20.0f, this.getWidth(), this.getHeight(), mouseX, mouseY)) {
                Settings setting;
                float height;
                int curMode;
                if (mouseButton == 0 && this.isHover(xPos + 5.0f, yPos + 35.0f, this.getModesWidth(), this.getModesHeight() - 10.0f, mouseX, mouseY)) {
                    curMode = -1;
                    height = 0.0f;
                    setting = (Settings)ClientColors.get.settings.get(2);
                    if (setting.category == Settings.Category.String_Massive && this.open) {
                        for (String mode : setting.modes) {
                            if (!this.isHover(xPos + 5.0f, yPos + 25.0f + (height += 10.0f), this.getModesWidth() - 10.0f, 10.0f, mouseX, mouseY)) continue;
                            curMode = (int)(height / 10.0f - 1.0f);
                        }
                    }
                    try {
                        ClickGuiScreen.scrollAnimation.to = 0.0f;
                        ClickGuiScreen.dWhell = 0.0f;
                        if (setting.currentMode != setting.modes[curMode] && curMode != -1) {
                            prevMode = setting.currentMode;
                            setting.currentMode = setting.modes[curMode];
                            ClientTune.get.playGuiClientcolorsChangeModeSong();
                            modeStepAnim.setAnim(0.0f);
                            ClientColors.modeStepAnim.to = 1.0f;
                            modeStepAnimGetAnim = 0.001f;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (ClientColors.get.Mode.currentMode.equalsIgnoreCase("Presets") && mouseButton == 0 && this.isHover(xPos + 5.0f, yPos + 35.0f + this.getModesHeight() - 10.0f + 11.0f + 15.0f, this.getPresetsWidth(), this.getPresetsHeight() - 15.0f, mouseX, mouseY)) {
                    curMode = -1;
                    height = 0.0f;
                    setting = (Settings)ClientColors.get.settings.get(3);
                    if (setting.category == Settings.Category.String_Massive && this.open) {
                        for (String mode : setting.modes) {
                            if (!this.isHover(xPos + 5.0f, yPos + 15.0f + this.getModesHeight() - 10.0f + 11.0f + 15.0f + (height += 20.0f), this.getModesWidth() - 15.0f, 20.0f, mouseX, mouseY)) continue;
                            curMode = (int)(height / 20.0f - 1.0f);
                        }
                    }
                    try {
                        if (setting.currentMode != setting.modes[curMode] && curMode != -1) {
                            prevPreset = ClientColors.getCurrentPreset();
                            setting.currentMode = setting.modes[curMode];
                            ClientTune.get.playGuiClientcolorsChangePresetSong();
                            presetStepAnim.setAnim(0.0f);
                            ClientColors.presetStepAnim.to = 1.0f;
                            presetStepAnimGetAnim = 0.01f;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if ((ClientColors.get.Mode.currentMode.equalsIgnoreCase("Colored") || ClientColors.get.Mode.currentMode.equalsIgnoreCase("TwoColored") || ClientColors.get.Mode.currentMode.equalsIgnoreCase("Fade")) && mouseButton == 0 && this.isHover(xPos + 5.0f, yPos + 35.0f + this.getModesHeight() - 10.0f + 11.0f + 15.0f, this.getPickerWidth(), this.getPickerHeight() - 15.0f, mouseX, mouseY)) {
                float posX = xPos + 5.0f;
                posY = yPos + 35.0f + this.getModesHeight() - 10.0f + 11.0f;
                float hsbX = posX + 6.0f;
                float hsbY = posY + 68.0f;
                float hsbW = this.getPickerWidth() - 12.0f;
                float hsbH = 6.0f;
                float alphaX = posX + 6.0f;
                alphaY = posY + 86.0f;
                alphaW = this.getPickerWidth() - 12.0f;
                alphaH = 6.0f;
                grX = posX + 5.0f;
                grY = posY + 20.0f;
                grH = 35.0f;
                grW = this.getPickerWidth() - 10.0f;
                if (mouseButton == 0 && this.isHover(grX, grY, grW, grH, mouseX, mouseY)) {
                    this.dragginggr = true;
                }
                if (mouseButton == 0 && this.isHover(hsbX, hsbY, hsbW, hsbH, mouseX, mouseY)) {
                    this.dragginghsb = true;
                }
                if (mouseButton == 0 && this.isHover(alphaX, alphaY, alphaW, alphaH, mouseX, mouseY)) {
                    this.draggingalpha = true;
                }
            }
            if ((ClientColors.get.Mode.currentMode.equalsIgnoreCase("TwoColored") || ClientColors.get.Mode.currentMode.equalsIgnoreCase("Fade")) && mouseButton == 0 && this.isHover(this.x + 5.0f, yPos + 35.0f + this.getModesHeight() - 10.0f + 11.0f + 15.0f + 11.0f + this.getPickerHeight(), this.getPickerWidth(), this.getPickerHeight() - 15.0f, mouseX, mouseY)) {
                float posX = xPos + 5.0f;
                posY = yPos + 35.0f + this.getModesHeight() - 10.0f + 11.0f + 11.0f + this.getPickerHeight();
                float hsbX = posX + 6.0f;
                float hsbY = posY + 68.0f;
                float hsbW = this.getPickerWidth() - 12.0f;
                float hsbH = 6.0f;
                float alphaX = posX + 6.0f;
                alphaY = posY + 86.0f;
                alphaW = this.getPickerWidth() - 12.0f;
                alphaH = 6.0f;
                grX = posX + 5.0f;
                grY = posY + 20.0f;
                grH = 35.0f;
                grW = this.getPickerWidth() - 10.0f;
                if (mouseButton == 0 && this.isHover(grX, grY, grW, grH, mouseX, mouseY)) {
                    this.dragginggrs = true;
                }
                if (mouseButton == 0 && this.isHover(hsbX, hsbY, hsbW, hsbH, mouseX, mouseY)) {
                    this.dragginghsbs = true;
                }
                if (mouseButton == 0 && this.isHover(alphaX, alphaY, alphaW, alphaH, mouseX, mouseY)) {
                    this.draggingalphas = true;
                }
            }
        }

        public float getModesWidth() {
            return this.getWidth() - 10.0f;
        }

        public float getPresetsWidth() {
            return this.getWidth() - 10.0f;
        }

        public float getPickerWidth() {
            return this.getWidth() - 10.0f;
        }

        public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
            if (mouseButton == 0) {
                this.dragginggr = false;
                this.dragginghsb = false;
                this.draggingalpha = false;
                this.dragginggrs = false;
                this.dragginghsbs = false;
                this.draggingalphas = false;
                this.dragging = false;
            }
        }

        public void keyTyped(char typedChar, int keyCode) {
        }

        public void initGui() {
        }

        public void onGuiClosed() {
            this.draggingalpha = false;
            this.dragginggr = false;
            this.dragginghsb = false;
            this.draggingalphas = false;
            this.dragginggrs = false;
            this.dragginghsbs = false;
            this.dragging = false;
        }
    }
}

