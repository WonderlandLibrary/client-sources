/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.optifine.Config;
import net.optifine.Lang;
import net.optifine.gui.GuiButtonOF;
import net.optifine.gui.GuiScreenOF;
import net.optifine.gui.TooltipManager;
import net.optifine.gui.TooltipProviderEnumShaderOptions;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.ShadersTex;
import net.optifine.shaders.config.EnumShaderOption;
import net.optifine.shaders.gui.GuiButtonDownloadShaders;
import net.optifine.shaders.gui.GuiButtonEnumShaderOption;
import net.optifine.shaders.gui.GuiShaderOptions;
import net.optifine.shaders.gui.GuiSlotShaders;

public class GuiShaders
extends GuiScreenOF {
    protected Screen parentGui;
    private TooltipManager tooltipManager = new TooltipManager(this, new TooltipProviderEnumShaderOptions());
    private int updateTimer = -1;
    private GuiSlotShaders shaderList;
    private boolean saved = false;
    private static float[] QUALITY_MULTIPLIERS = new float[]{0.5f, 0.6f, 0.6666667f, 0.75f, 0.8333333f, 0.9f, 1.0f, 1.1666666f, 1.3333334f, 1.5f, 1.6666666f, 1.8f, 2.0f};
    private static String[] QUALITY_MULTIPLIER_NAMES = new String[]{"0.5x", "0.6x", "0.66x", "0.75x", "0.83x", "0.9x", "1x", "1.16x", "1.33x", "1.5x", "1.66x", "1.8x", "2x"};
    private static float QUALITY_MULTIPLIER_DEFAULT = 1.0f;
    private static float[] HAND_DEPTH_VALUES = new float[]{0.0625f, 0.125f, 0.25f};
    private static String[] HAND_DEPTH_NAMES = new String[]{"0.5x", "1x", "2x"};
    private static float HAND_DEPTH_DEFAULT = 0.125f;
    public static final int EnumOS_UNKNOWN = 0;
    public static final int EnumOS_WINDOWS = 1;
    public static final int EnumOS_OSX = 2;
    public static final int EnumOS_SOLARIS = 3;
    public static final int EnumOS_LINUX = 4;

    public GuiShaders(Screen screen, GameSettings gameSettings) {
        super(new StringTextComponent(I18n.format("of.options.shadersTitle", new Object[0])));
        this.parentGui = screen;
    }

    @Override
    public void init() {
        if (Shaders.shadersConfig == null) {
            Shaders.loadConfig();
        }
        int n = 120;
        int n2 = 20;
        int n3 = this.width - n - 10;
        int n4 = 30;
        int n5 = 20;
        int n6 = this.width - n - 20;
        this.shaderList = new GuiSlotShaders(this, n6, this.height, n4, this.height - 50, 16);
        this.children.add(this.shaderList);
        this.addButton(new GuiButtonEnumShaderOption(EnumShaderOption.ANTIALIASING, n3, 0 * n5 + n4, n, n2));
        this.addButton(new GuiButtonEnumShaderOption(EnumShaderOption.NORMAL_MAP, n3, 1 * n5 + n4, n, n2));
        this.addButton(new GuiButtonEnumShaderOption(EnumShaderOption.SPECULAR_MAP, n3, 2 * n5 + n4, n, n2));
        this.addButton(new GuiButtonEnumShaderOption(EnumShaderOption.RENDER_RES_MUL, n3, 3 * n5 + n4, n, n2));
        this.addButton(new GuiButtonEnumShaderOption(EnumShaderOption.SHADOW_RES_MUL, n3, 4 * n5 + n4, n, n2));
        this.addButton(new GuiButtonEnumShaderOption(EnumShaderOption.HAND_DEPTH_MUL, n3, 5 * n5 + n4, n, n2));
        this.addButton(new GuiButtonEnumShaderOption(EnumShaderOption.OLD_HAND_LIGHT, n3, 6 * n5 + n4, n, n2));
        this.addButton(new GuiButtonEnumShaderOption(EnumShaderOption.OLD_LIGHTING, n3, 7 * n5 + n4, n, n2));
        int n7 = Math.min(150, n6 / 2 - 10);
        int n8 = n6 / 4 - n7 / 2;
        int n9 = this.height - 25;
        this.addButton(new GuiButtonOF(201, n8, n9, n7 - 22 + 1, n2, Lang.get("of.options.shaders.shadersFolder")));
        this.addButton(new GuiButtonDownloadShaders(210, n8 + n7 - 22 - 1, n9));
        this.addButton(new GuiButtonOF(202, n6 / 4 * 3 - n7 / 2, this.height - 25, n7, n2, I18n.format("gui.done", new Object[0])));
        this.addButton(new GuiButtonOF(203, n3, this.height - 25, n, n2, Lang.get("of.options.shaders.shaderOptions")));
        this.setListener(this.shaderList);
        this.updateButtons();
    }

    public void updateButtons() {
        boolean bl = Config.isShaders();
        for (Widget widget : this.buttonList) {
            if (!(widget instanceof GuiButtonOF)) continue;
            GuiButtonOF guiButtonOF = (GuiButtonOF)widget;
            if (guiButtonOF.id == 201 || guiButtonOF.id == 202 || guiButtonOF.id == 210 || guiButtonOF.id == EnumShaderOption.ANTIALIASING.ordinal()) continue;
            guiButtonOF.active = bl;
        }
    }

    @Override
    protected void actionPerformed(Widget widget) {
        this.actionPerformed(widget, true);
    }

    @Override
    protected void actionPerformedRightClick(Widget widget) {
        this.actionPerformed(widget, false);
    }

    private void actionPerformed(Widget widget, boolean bl) {
        if (widget.active) {
            if (widget instanceof GuiButtonEnumShaderOption) {
                GuiButtonEnumShaderOption guiButtonEnumShaderOption = (GuiButtonEnumShaderOption)widget;
                switch (1.$SwitchMap$net$optifine$shaders$config$EnumShaderOption[guiButtonEnumShaderOption.getEnumShaderOption().ordinal()]) {
                    case 1: {
                        Shaders.nextAntialiasingLevel(!bl);
                        if (GuiShaders.hasShiftDown()) {
                            Shaders.configAntialiasingLevel = 0;
                        }
                        Shaders.uninit();
                        break;
                    }
                    case 2: {
                        boolean bl2 = Shaders.configNormalMap = !Shaders.configNormalMap;
                        if (GuiShaders.hasShiftDown()) {
                            Shaders.configNormalMap = true;
                        }
                        Shaders.uninit();
                        this.minecraft.scheduleResourcesRefresh();
                        break;
                    }
                    case 3: {
                        boolean bl3 = Shaders.configSpecularMap = !Shaders.configSpecularMap;
                        if (GuiShaders.hasShiftDown()) {
                            Shaders.configSpecularMap = true;
                        }
                        Shaders.uninit();
                        this.minecraft.scheduleResourcesRefresh();
                        break;
                    }
                    case 4: {
                        Shaders.configRenderResMul = this.getNextValue(Shaders.configRenderResMul, QUALITY_MULTIPLIERS, QUALITY_MULTIPLIER_DEFAULT, !bl, GuiShaders.hasShiftDown());
                        Shaders.uninit();
                        Shaders.scheduleResize();
                        break;
                    }
                    case 5: {
                        Shaders.configShadowResMul = this.getNextValue(Shaders.configShadowResMul, QUALITY_MULTIPLIERS, QUALITY_MULTIPLIER_DEFAULT, !bl, GuiShaders.hasShiftDown());
                        Shaders.uninit();
                        Shaders.scheduleResizeShadow();
                        break;
                    }
                    case 6: {
                        Shaders.configHandDepthMul = this.getNextValue(Shaders.configHandDepthMul, HAND_DEPTH_VALUES, HAND_DEPTH_DEFAULT, !bl, GuiShaders.hasShiftDown());
                        Shaders.uninit();
                        break;
                    }
                    case 7: {
                        Shaders.configOldHandLight.nextValue(!bl);
                        if (GuiShaders.hasShiftDown()) {
                            Shaders.configOldHandLight.resetValue();
                        }
                        Shaders.uninit();
                        break;
                    }
                    case 8: {
                        Shaders.configOldLighting.nextValue(!bl);
                        if (GuiShaders.hasShiftDown()) {
                            Shaders.configOldLighting.resetValue();
                        }
                        Shaders.updateBlockLightLevel();
                        Shaders.uninit();
                        this.minecraft.scheduleResourcesRefresh();
                        break;
                    }
                    case 9: {
                        Shaders.configTweakBlockDamage = !Shaders.configTweakBlockDamage;
                        break;
                    }
                    case 10: {
                        Shaders.configCloudShadow = !Shaders.configCloudShadow;
                        break;
                    }
                    case 11: {
                        Shaders.configTexMinFilN = Shaders.configTexMinFilS = (Shaders.configTexMinFilB = (Shaders.configTexMinFilB + 1) % 3);
                        guiButtonEnumShaderOption.setMessage("Tex Min: " + Shaders.texMinFilDesc[Shaders.configTexMinFilB]);
                        ShadersTex.updateTextureMinMagFilter();
                        break;
                    }
                    case 12: {
                        Shaders.configTexMagFilN = (Shaders.configTexMagFilN + 1) % 2;
                        guiButtonEnumShaderOption.setMessage("Tex_n Mag: " + Shaders.texMagFilDesc[Shaders.configTexMagFilN]);
                        ShadersTex.updateTextureMinMagFilter();
                        break;
                    }
                    case 13: {
                        Shaders.configTexMagFilS = (Shaders.configTexMagFilS + 1) % 2;
                        guiButtonEnumShaderOption.setMessage("Tex_s Mag: " + Shaders.texMagFilDesc[Shaders.configTexMagFilS]);
                        ShadersTex.updateTextureMinMagFilter();
                        break;
                    }
                    case 14: {
                        Shaders.configShadowClipFrustrum = !Shaders.configShadowClipFrustrum;
                        guiButtonEnumShaderOption.setMessage("ShadowClipFrustrum: " + GuiShaders.toStringOnOff(Shaders.configShadowClipFrustrum));
                        ShadersTex.updateTextureMinMagFilter();
                    }
                }
                guiButtonEnumShaderOption.updateButtonText();
            } else if (!bl && widget instanceof GuiButtonOF) {
                GuiButtonOF guiButtonOF = (GuiButtonOF)widget;
                switch (guiButtonOF.id) {
                    case 201: {
                        switch (GuiShaders.getOSType()) {
                            case 1: {
                                String string = String.format("cmd.exe /C start \"Open file\" \"%s\"", Shaders.shaderPacksDir.getAbsolutePath());
                                try {
                                    Runtime.getRuntime().exec(string);
                                    return;
                                } catch (IOException iOException) {
                                    iOException.printStackTrace();
                                    break;
                                }
                            }
                            case 2: {
                                try {
                                    Runtime.getRuntime().exec(new String[]{"/usr/bin/open", Shaders.shaderPacksDir.getAbsolutePath()});
                                    return;
                                } catch (IOException iOException) {
                                    iOException.printStackTrace();
                                }
                            }
                        }
                        boolean bl4 = false;
                        try {
                            URI uRI = new File(this.minecraft.gameDir, "shaderpacks").toURI();
                            Util.getOSType().openURI(uRI);
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                            bl4 = true;
                        }
                        if (!bl4) break;
                        Config.dbg("Opening via system class!");
                        Util.getOSType().openURI("file://" + Shaders.shaderPacksDir.getAbsolutePath());
                        break;
                    }
                    case 202: {
                        Shaders.storeConfig();
                        this.saved = true;
                        this.minecraft.displayGuiScreen(this.parentGui);
                        break;
                    }
                    case 203: {
                        GuiShaderOptions guiShaderOptions = new GuiShaderOptions(this, Config.getGameSettings());
                        Config.getMinecraft().displayGuiScreen(guiShaderOptions);
                    }
                    default: {
                        break;
                    }
                    case 210: {
                        try {
                            URI uRI = new URI("http://optifine.net/shaderPacks");
                            Util.getOSType().openURI(uRI);
                            break;
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onClose() {
        if (!this.saved) {
            Shaders.storeConfig();
            this.saved = true;
        }
        super.onClose();
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        this.shaderList.render(matrixStack, n, n2, f);
        if (this.updateTimer <= 0) {
            this.shaderList.updateList();
            this.updateTimer += 20;
        }
        GuiShaders.drawCenteredString(matrixStack, this.fontRenderer, this.title, this.width / 2, 15, 0xFFFFFF);
        String string = "OpenGL: " + Shaders.glVersionString + ", " + Shaders.glVendorString + ", " + Shaders.glRendererString;
        int n3 = this.fontRenderer.getStringWidth(string);
        if (n3 < this.width - 5) {
            GuiShaders.drawCenteredString(matrixStack, this.fontRenderer, string, this.width / 2, this.height - 40, 0x808080);
        } else {
            GuiShaders.drawString(matrixStack, this.fontRenderer, string, 5, this.height - 40, 0x808080);
        }
        super.render(matrixStack, n, n2, f);
        this.tooltipManager.drawTooltips(matrixStack, n, n2, this.buttonList);
    }

    @Override
    public void tick() {
        super.tick();
        --this.updateTimer;
    }

    public Minecraft getMc() {
        return this.minecraft;
    }

    public void drawCenteredString(MatrixStack matrixStack, String string, int n, int n2, int n3) {
        GuiShaders.drawCenteredString(matrixStack, this.fontRenderer, string, n, n2, n3);
    }

    public static String toStringOnOff(boolean bl) {
        String string = Lang.getOn();
        String string2 = Lang.getOff();
        return bl ? string : string2;
    }

    public static String toStringAa(int n) {
        if (n == 2) {
            return "FXAA 2x";
        }
        return n == 4 ? "FXAA 4x" : Lang.getOff();
    }

    public static String toStringValue(float f, float[] fArray, String[] stringArray) {
        int n = GuiShaders.getValueIndex(f, fArray);
        return stringArray[n];
    }

    private float getNextValue(float f, float[] fArray, float f2, boolean bl, boolean bl2) {
        if (bl2) {
            return f2;
        }
        int n = GuiShaders.getValueIndex(f, fArray);
        if (bl) {
            if (++n >= fArray.length) {
                n = 0;
            }
        } else if (--n < 0) {
            n = fArray.length - 1;
        }
        return fArray[n];
    }

    public static int getValueIndex(float f, float[] fArray) {
        for (int i = 0; i < fArray.length; ++i) {
            float f2 = fArray[i];
            if (!(f2 >= f)) continue;
            return i;
        }
        return fArray.length - 1;
    }

    public static String toStringQuality(float f) {
        return GuiShaders.toStringValue(f, QUALITY_MULTIPLIERS, QUALITY_MULTIPLIER_NAMES);
    }

    public static String toStringHandDepth(float f) {
        return GuiShaders.toStringValue(f, HAND_DEPTH_VALUES, HAND_DEPTH_NAMES);
    }

    public static int getOSType() {
        String string = System.getProperty("os.name").toLowerCase();
        if (string.contains("win")) {
            return 0;
        }
        if (string.contains("mac")) {
            return 1;
        }
        if (string.contains("solaris")) {
            return 0;
        }
        if (string.contains("sunos")) {
            return 0;
        }
        if (string.contains("linux")) {
            return 1;
        }
        return string.contains("unix") ? 4 : 0;
    }
}

