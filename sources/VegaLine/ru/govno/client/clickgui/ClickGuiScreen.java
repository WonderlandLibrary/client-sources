/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.clickgui;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec2f;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import ru.govno.client.Client;
import ru.govno.client.cfg.GuiConfig;
import ru.govno.client.clickgui.Mod;
import ru.govno.client.clickgui.Panel;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.ClickGui;
import ru.govno.client.module.modules.ClientColors;
import ru.govno.client.module.modules.ClientTune;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.newfont.CFontRenderer;
import ru.govno.client.newfont.Fonts;
import ru.govno.client.utils.CTextField;
import ru.govno.client.utils.HoverUtils;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Math.TimerHelper;
import ru.govno.client.utils.MusicHelper;
import ru.govno.client.utils.Render.AnimationUtils;
import ru.govno.client.utils.Render.BloomUtil;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;

public class ClickGuiScreen
extends GuiScreen {
    private static Clip clip;
    private static AudioInputStream stream;
    boolean playBindHold = false;
    public static AnimationUtils scale;
    public static AnimationUtils globalAlpha;
    static AnimationUtils scrolled;
    public List<Panel> panels = Arrays.asList(new Panel(Module.Category.COMBAT), new Panel(Module.Category.MOVEMENT), new Panel(Module.Category.RENDER), new Panel(Module.Category.PLAYER), new Panel(Module.Category.MISC));
    public static AnimationUtils cfgScale;
    public static boolean colose;
    private final List<Parts> parts = new CopyOnWriteArrayList<Parts>();
    private final List<FallPartsEffect> partsEff = new CopyOnWriteArrayList<FallPartsEffect>();
    static final CTextField textFieldSearch;
    static AnimationUtils searchAnim;
    static AnimationUtils searchStringWAnim;
    public static float scrollSmoothX;
    public static float scrollSmoothY;
    public static float dWhell;
    public static AnimationUtils scrollAnimation;
    static AnimationUtils imageScale;
    static AnimationUtils imageRender;
    String imageMode = null;
    static AnimationUtils waveAlphaRender;
    static AnimationUtils waveAlphaHRender;
    static AnimationUtils blurStrenghRender;
    static Module.Category colorCategory;
    static String descriptionName;
    float xn;
    float yn;
    static AnimationUtils keyCfgAnimScale;
    static AnimationUtils keyCfgAnimRotate;
    boolean openCfgKey;
    boolean isClickedCfgKey;
    static AnimationUtils crossRotate;
    static AnimationUtils crossClick;
    boolean checkCustomCross = false;
    static ResourceLocation MOUSE_TEXTURE_BASE;
    static ResourceLocation MOUSE_TEXTURE_OVERLAY;
    static AnimationUtils darkness;
    public static AnimationUtils epilepsy;
    public static AnimationUtils scanLines;
    static final ResourceLocation BOUND_UP;
    static final ResourceLocation BOUND_DOWN;
    public static final ResourceLocation BOUND_CONFLICT;
    static Tessellator tessellator;
    static BufferBuilder builder;
    static AnimationUtils boundsToggleAnim;
    static AnimationUtils keySaveSound;
    static AnimationUtils keySaveSoundToggle;
    private static final ResourceLocation MUSIC_SAVE_BUTTON;
    public TimerHelper timeMouse0Hold = new TimerHelper();
    public TimerHelper timeMouse1Hold = new TimerHelper();
    public TimerHelper timeMouse2Hold = new TimerHelper();
    public static boolean checkMouse0Hold;
    public static boolean checkMouse1Hold;
    public static boolean checkMouse2Hold;
    public static boolean clickedMouse0;
    public static boolean clickedMouse1;
    public static boolean clickedMouse2;
    static ArrayList<searchParticle> particles;
    private final ResourceLocation BLOOM_TEX = new ResourceLocation("vegaline/ui/clickgui/bloomsimulate/bloom.png");

    public void setPlayBindHold(boolean canPlay) {
        if (!(ClientTune.get.actived && ClientTune.get.actived && ClientTune.get.ClickGui.getBool())) {
            canPlay = false;
        }
        if (this.playBindHold != canPlay) {
            this.playBindHold = canPlay;
            ClientTune.get.playGuiModuleBindingHoldStatusSong(!canPlay);
            if (canPlay) {
                try {
                    String resourcePath = "/assets/minecraft/vegaline/sounds/guibindhold.wav";
                    InputStream inputStream = MusicHelper.class.getResourceAsStream(resourcePath);
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                    stream = AudioSystem.getAudioInputStream(bufferedInputStream);
                } catch (Exception resourcePath) {
                    // empty catch block
                }
                try {
                    clip = AudioSystem.getClip();
                } catch (LineUnavailableException e2) {
                    e2.printStackTrace();
                }
                if (stream == null) {
                    return;
                }
                try {
                    clip.open(stream);
                } catch (LineUnavailableException e2) {
                    e2.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                clip.start();
            } else if (clip != null) {
                clip.stop();
                clip = null;
            }
        }
    }

    void updatePlayingBinding() {
        this.setPlayBindHold(this.panels.stream().anyMatch(panel -> panel.mods.stream().anyMatch(mod -> mod.binding && mod.bindHoldAnim.to != 0.0f)));
    }

    public ClickGuiScreen() {
        int x = 80;
        for (Module.Category category : Module.Category.values()) {
            Panel panel = new Panel(category);
            panel.X = x;
            panel.Y = 20.0f;
            panel.posX.setAnim(panel.X);
            panel.posX.to = panel.X;
            panel.posY.setAnim(panel.Y);
            panel.posY.to = panel.Y;
            x += 135;
        }
    }

    @Override
    public void updateScreen() {
        textFieldSearch.updateCursorCounter();
    }

    void searchClickReader(float x, float y, float xPw, float yPw, int mouseX, int mouseY) {
        if (HoverUtils.isHovered((int)x, (int)y, (int)xPw, (int)yPw, mouseX, mouseY)) {
            textFieldSearch.setFocused(!textFieldSearch.isFocused());
        }
    }

    void searchUpdate(ScaledResolution sr, int mouseX, int mouseY, boolean isFocused, String text, float x, float y, float xPw, float yPw) {
        if (textFieldSearch.isFocused() && text != "" && isFocused && Keyboard.isKeyDown(13)) {
            textFieldSearch.setFocused(false);
        }
        Keyboard.enableRepeatEvents(true);
        textFieldSearch.setMaxStringLength(170);
    }

    void forSearch(ScaledResolution sr, int mouseX, int mouseY) {
        float percentAlpha = globalAlpha.getAnim() / 255.0f;
        ClickGuiScreen.searchAnim.to = textFieldSearch.isFocused() && !colose ? 1.0f : 0.0f;
        ClickGuiScreen.searchStringWAnim.to = (float)MathUtils.clamp(15 + Fonts.mntsb_20.getStringWidth(textFieldSearch.getText()), 21, sr.getScaledWidth() - 30) * searchAnim.getAnim();
        float w = 20.0f + searchStringWAnim.getAnim();
        float h = 20.0f;
        float x = 5.0f;
        float y = 5.0f;
        float x2 = 5.0f + w;
        float y2 = 25.0f;
        this.searchUpdate(sr, mouseX, mouseY, textFieldSearch.isFocused(), textFieldSearch.getText(), 5.0f + w - 20.0f, 5.0f, 5.0f + w, 25.0f);
        int cs1 = ColorUtils.getColor(0, 255, 135, (int)(170.0f * percentAlpha * percentAlpha));
        int cs2 = ColorUtils.getColor(140, 255, 0, (int)(55.0f * percentAlpha * percentAlpha));
        int cs3 = ColorUtils.getColor(0, 255, 135, (int)(70.0f * percentAlpha));
        int cs4 = ColorUtils.getColor(140, 255, 0, (int)(20.0f * percentAlpha));
        int cr1 = ColorUtils.getColor(0, 0, 0, (int)(110.0f * percentAlpha));
        int cr2 = ColorUtils.getColor(0, 0, 0, (int)(160.0f * percentAlpha));
        int ct1 = ColorUtils.getColor(255, 255, 254, (int)MathUtils.clamp(255.0f * percentAlpha, 0.0f, 255.0f));
        float round = 4.0f + 2.0f * (1.0f - percentAlpha);
        float shadownSize = 3.0f + (1.0f - percentAlpha) * 2.0f;
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(5.0f, 5.0f, x2, 25.0f, round, shadownSize, cs1, cs1, cs2, cs2, false, false, true);
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(5.0f, 5.0f, x2, 25.0f, round, shadownSize, cs3, cs3, cs4, cs4, false, true, false);
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x2 - 20.0f + 3.0f, 8.0f, x2 - 3.0f, 22.0f, round, shadownSize * 0.75f, cr1, cr1, cr2, cr2, false, true, true);
        GlStateManager.enableBlend();
        if (ColorUtils.getAlphaFromColor(ct1) >= 26) {
            if (scale.getAnim() >= 1.0f) {
                Fonts.stylesicons_20.drawStringWithOutline(textFieldSearch.isFocused() ? "I" : "G", x2 - 14.5f, 13.5, ct1);
            } else {
                Fonts.stylesicons_20.drawStringWithShadow(textFieldSearch.isFocused() ? "I" : "G", x2 - 14.5f, 13.5, ct1);
            }
        }
        GL11.glEnable(3089);
        RenderUtils.scissorRected(5.0, 5.0, x2 - 20.0f, 25.0);
        textFieldSearch.drawTextBox(Fonts.mntsb_20, 0.1f + searchAnim.getAnim() * 0.9f);
        GL11.glDisable(3089);
    }

    boolean moduleHasEqualSearch(Module module) {
        return !textFieldSearch.getText().equalsIgnoreCase("") && textFieldSearch.isFocused() && module.getName().toLowerCase().contains(textFieldSearch.getText().toLowerCase());
    }

    public static void scrolls() {
        int whells = MathUtils.clamp(Mouse.getDWheel() * 1000, -1, 1);
        if (Mouse.hasWheel()) {
            dWhell -= (float)whells * 10.0f;
        }
        if (MathUtils.getDifferenceOf(ClickGuiScreen.scrollAnimation.to, dWhell = MathUtils.clamp(dWhell, 0.0f, Client.clientColosUI.getTotalElementsHeight() > Client.clientColosUI.getHeight() - 24.0f ? Client.clientColosUI.getTotalElementsHeight() - (Client.clientColosUI.getHeight() - 35.0f) : 0.0f)) > 1.0) {
            ClientTune.get.playGuiScreenScrollSong();
        }
        ClickGuiScreen.scrollAnimation.to = dWhell;
    }

    public void scroll(int mouseX, int mouseY) {
        if (Client.clientColosUI.isHoveredToPanel(mouseX, mouseY) && Client.clientColosUI.getHeight() >= 23.0f) {
            ClickGuiScreen.scrolls();
        }
        int whell = MathUtils.clamp(Mouse.getDWheel() * 1000, -1, 1);
        boolean shift = Keyboard.isKeyDown(42);
        if (Mouse.hasWheel()) {
            float f = ClickGuiScreen.scrolled.to = Mouse.hasWheel() ? (float)(whell * 20) : scrolled.getAnim();
        }
        if (shift) {
            scrollSmoothY = 0.0f;
            scrollSmoothX = scrolled.getAnim() / 3.0f;
        } else {
            scrollSmoothY = scrolled.getAnim() / 3.0f;
            scrollSmoothX = 0.0f;
        }
        if (whell != 0) {
            ClientTune.get.playGuiScreenScrollSong();
        }
        this.panels.forEach(panel -> {
            if (shift) {
                panel.X += scrolled.getAnim();
            } else {
                panel.Y += scrolled.getAnim();
            }
        });
    }

    public static int getColor(int step, Module.Category category) {
        float categoryFactor = ClickGui.categoryColorFactor.getAnim();
        boolean CCFade = true;
        int c = ClientColors.getColor1(step);
        if (category != null && (double)categoryFactor > 0.03) {
            int cc;
            int n = category == Module.Category.COMBAT ? ColorUtils.getColor(255, 50, 50) : (category == Module.Category.MOVEMENT ? ColorUtils.getColor(50, 115, 255) : (category == Module.Category.RENDER ? ColorUtils.getColor(255, 170, 50) : (category == Module.Category.PLAYER ? ColorUtils.getColor(50, 255, 160) : (cc = category == Module.Category.MISC ? ColorUtils.getColor(255, 90, 210) : 0))));
            if (CCFade) {
                cc = ColorUtils.fadeColor(cc, ColorUtils.toDark(cc, 0.5f), 0.333333f, (int)((float)step * 3.3333f));
            }
            return (double)ClickGui.categoryColorFactor.getAnim() < 0.97 ? ColorUtils.getOverallColorFrom(c, cc, categoryFactor) : cc;
        }
        return c;
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        ClickGuiScreen.resetHolds();
        if (colose) {
            return;
        }
        for (Panel panel : this.panels) {
            panel.mouseReleased(mouseX, mouseY, state);
        }
        Client.clientColosUI.mouseReleased(mouseX, mouseY, state);
    }

    void renderImages(ScaledResolution sr, boolean render) {
        float f = ClickGuiScreen.imageRender.to = render ? 1.0f : 0.0f;
        if ((double)imageRender.getAnim() > 0.05) {
            ResourceLocation image;
            String imageName;
            String mode = ClickGui.Image.getMode();
            if (this.imageMode != mode) {
                if (this.imageMode == null) {
                    Arrays.asList(ClickGui.instance.getSetting((String)"Image", (Settings.Category)Settings.Category.String_Massive).modes).stream().filter(str -> !str.equalsIgnoreCase(mode)).forEach(another -> this.mc.getTextureManager().bindTexture(new ResourceLocation(another.toLowerCase() + ".png")));
                }
                ClickGuiScreen.imageScale.to = 0.1f;
                if ((double)imageScale.getAnim() > 0.0995) {
                    this.imageMode = mode;
                }
            } else if ((double)imageScale.getAnim() >= 0.09) {
                ClickGuiScreen.imageScale.to = 0.0f;
            }
            if ((imageName = this.imageMode.toLowerCase()) != null && (image = new ResourceLocation("vegaline/modules/clickgui/images/" + imageName.toLowerCase() + ".png")) != null) {
                int size = (int)(512.0f * (1.0f + imageScale.getAnim()) + 0.001f);
                float x1 = sr.getScaledWidth() - size;
                float x2 = sr.getScaledWidth();
                float y1 = sr.getScaledHeight() - size;
                float y2 = sr.getScaledHeight();
                y1 -= (imageRender.getAnim() - 1.0f) * 50.0f;
                y2 -= (imageRender.getAnim() - 1.0f) * 50.0f;
                x1 -= (imageRender.getAnim() - 1.0f) * 150.0f;
                x2 -= (imageRender.getAnim() - 1.0f) * 150.0f;
                int alpha = (int)(MathUtils.clamp(255.0f - imageScale.getAnim() * 10.0f * 255.0f, 0.0f, 255.0f) * imageRender.getAnim() * MathUtils.clamp((globalAlpha.getAnim() - 26.0f) / 255.0f * 1.1019608f * MathUtils.clamp(scale.getAnim(), 0.0f, 1.0f), 0.0f, 1.0f));
                if (alpha > 1) {
                    RenderUtils.drawImageWithAlpha(image, x1, y1, x2 - x1, y2 - y1, ColorUtils.swapDark(ColorUtils.getFixedWhiteColor(), (float)alpha / 255.0f / 2.0f + 0.5f), alpha);
                }
            }
        }
    }

    void drawWaveRect(ScaledResolution sr) {
        ClickGuiScreen.waveAlphaHRender.to = ClickGui.GradientAlpha.getFloat() / 255.0f;
        ClickGuiScreen.waveAlphaRender.to = ClickGui.Gradient.getBool() ? 1.0f : 0.0f;
        ClickGuiScreen.waveAlphaRender.speed = 0.0333f;
        float alphaColosePercent = MathUtils.clamp(globalAlpha.getAnim() / 255.0f * scale.getAnim(), 0.0f, 1.0f) * waveAlphaRender.getAnim();
        int step = 200;
        int c1 = ColorUtils.getOverallColorFrom(ClickGuiScreen.getColor(step, Module.Category.COMBAT), ClickGuiScreen.getColor(step * 2, Module.Category.MOVEMENT));
        int c2 = ColorUtils.getOverallColorFrom(ClickGuiScreen.getColor(step * 3, Module.Category.RENDER), ClickGuiScreen.getColor(step * 4, Module.Category.PLAYER));
        int c3 = ColorUtils.getOverallColorFrom(ClickGuiScreen.getColor(step * 5, Module.Category.MISC), ColorUtils.getOverallColorFrom(c1, c2));
        float alphaButtom = waveAlphaHRender.getAnim() * alphaColosePercent;
        int cb = ColorUtils.getOverallColorFrom(ColorUtils.getOverallColorFrom(c1, c2), c3);
        int colorButtom = ColorUtils.swapAlpha(cb, (float)ColorUtils.getAlphaFromColor(cb) * alphaButtom);
        int alpha = (int)(alphaButtom * 255.0f);
        int col1 = ColorUtils.swapAlpha(ClickGuiScreen.getColor(step, Module.Category.COMBAT), alpha);
        int col2 = ColorUtils.swapAlpha(ClickGuiScreen.getColor(step * 10, Module.Category.MOVEMENT), alpha);
        int col3 = ColorUtils.swapAlpha(ClickGuiScreen.getColor(step * 20, Module.Category.RENDER), alpha);
        int col4 = ColorUtils.swapAlpha(ColorUtils.getOverallColorFrom(ClickGuiScreen.getColor(step * 30, Module.Category.COMBAT), ClickGuiScreen.getColor(step * 40, Module.Category.COMBAT)), alpha);
        float x = 0.0f;
        float x2 = sr.getScaledWidth();
        float y = (float)sr.getScaledHeight() / (1.0f + waveAlphaHRender.getAnim());
        float y2 = sr.getScaledHeight();
        if ((double)waveAlphaRender.getAnim() > 0.02) {
            RenderUtils.drawWaveGradient(x, y, x2, y2, alphaColosePercent / 8.0f, col1, col2, col3, col4, true, true);
        }
    }

    void drawBlur(ScaledResolution sr) {
        ClickGuiScreen.blurStrenghRender.to = ClickGui.BlurBackground.getBool() ? 1.0f : 0.0f;
        float percentBlur = globalAlpha.getAnim() / 255.0f * blurStrenghRender.getAnim();
        float blurStrengh = ClickGui.BlurStrengh.getFloat() * 15.0f;
        float blurStrengh2 = ClickGui.BlurStrengh.getFloat() / 2.0f;
        if ((double)percentBlur > 0.05) {
            Client.blur.blur(0.0f, 0.0f, sr.getScaledWidth(), sr.getScaledHeight(), (int)MathUtils.clamp(blurStrengh * percentBlur, 0.5f, 100.0f));
        }
    }

    void renderDescriptions() {
        ScaledResolution sr = new ScaledResolution(this.mc);
        this.xn = MathUtils.lerp(this.xn, Mod.xn, (float)Minecraft.frameTime * (MathUtils.getDifferenceOf(this.xn, Mod.xn) < 20.0 ? 0.0075f : 0.0125f));
        this.yn = MathUtils.lerp(this.yn, Mod.yn, (float)Minecraft.frameTime * (MathUtils.getDifferenceOf(this.yn, Mod.yn) < 20.0 ? 0.0075f : 0.0125f));
        if (Mod.xn - 15.0f == (float)(sr.getScaledWidth() / 2) && Mod.yn - 15.0f == (float)(sr.getScaledHeight() / 2)) {
            this.xn = Mod.xn;
            this.yn = Mod.yn;
        }
        if (Mod.alphaD < 10.0f) {
            Mod.xn = 0.0f;
            Mod.yn = 0.0f;
        } else {
            CFontRenderer font = Fonts.comfortaaBold_14;
            int i = 0;
            float alpha = Mod.alphaD * (globalAlpha.getAnim() / 255.0f) * MathUtils.clamp(scale.getAnim(), 0.0f, 1.0f);
            String text = MathUtils.getStringPercent(Mod.descript, alpha / 255.0f * (alpha / 255.0f) * 5.0f);
            GL11.glDisable(2929);
            for (char c : text.toCharArray()) {
                i += font.getStringWidth(String.valueOf(c));
            }
            int s = i;
            Runnable rect = () -> {
                RenderUtils.glRenderStart();
                GL11.glDisable(3008);
                GL11.glBlendFunc(770, 771);
                GL11.glBegin(9);
                GL11.glVertex2d(this.xn - 6.0f, this.yn - 3.0f);
                GL11.glVertex2d(this.xn + (float)s + 2.0f, this.yn - 3.0f);
                GL11.glVertex2d(this.xn + (float)s + 6.0f, this.yn + (float)font.getHeight() + 2.0f);
                GL11.glVertex2d(this.xn - 2.0f, this.yn + (float)font.getHeight() + 2.0f);
                GL11.glEnd();
                GL11.glBlendFunc(770, 771);
                GL11.glEnable(3008);
                RenderUtils.glRenderStop();
            };
            BloomUtil.renderShadow(() -> rect.run(), ColorUtils.swapAlpha(Integer.MIN_VALUE, alpha / 4.0f), 3, 1, 1.7f * (alpha / 255.0f), false);
            Runnable textDraw = () -> {
                int n = 0;
                for (char c : text.toCharArray()) {
                    if (alpha >= 32.0f) {
                        font.drawString(String.valueOf(c), this.xn + (float)n, this.yn, ColorUtils.swapAlpha(ClickGuiScreen.getColor(n, colorCategory), alpha));
                    }
                    n += font.getStringWidth(String.valueOf(c));
                }
            };
            BloomUtil.renderShadow(textDraw, ColorUtils.getOverallColorFrom(-1, ClickGuiScreen.getColor(i, colorCategory)), 5, 0, alpha / 255.0f, false);
            textDraw.run();
            Mod.alphaD = Mod.alphaD > 0.0f ? Mod.alphaD - 5.0f : 0.0f;
            GL11.glEnable(2929);
        }
    }

    void cfgGuiOpenner(int mouseX, int mouseY) {
        ScaledResolution sr = new ScaledResolution(this.mc);
        ClickGuiScreen.cfgScale.to = this.openCfgKey ? 1.0f : 0.0f;
        int size = 32;
        float x = sr.getScaledWidth() - size - 10;
        float y = 10.0f;
        ClickGuiScreen.keyCfgAnimScale.to = HoverUtils.isHovered((int)x, (int)y, (int)x + size, (int)y + size, mouseX, mouseY) ? 1.2f : 1.0f;
        float f = ClickGuiScreen.keyCfgAnimRotate.to = System.currentTimeMillis() % 1000L < 150L && HoverUtils.isHovered((int)x, (int)y, (int)x + size, (int)y + size, mouseX, mouseY) ? -15.0f : 0.0f;
        if (Mouse.isButtonDown(0)) {
            if (HoverUtils.isHovered((int)x, (int)y, (int)x + size, (int)y + size, mouseX, mouseY) && this.isClickedCfgKey) {
                this.openCfgKey = !this.openCfgKey;
            }
            this.isClickedCfgKey = false;
        } else {
            this.isClickedCfgKey = true;
        }
        GL11.glPushMatrix();
        RenderUtils.customScaledObject2D(x, y, size, size, keyCfgAnimScale.getAnim() * scale.getAnim());
        RenderUtils.customRotatedObject2D(x, y, size, size, keyCfgAnimRotate.getAnim());
        RenderUtils.drawImageWithAlpha(new ResourceLocation("vegaline/ui/clickgui/config/buttons/images/cfgicon.png"), x, y, size, size, ColorUtils.fadeColor(ColorUtils.getColor(255, 0, 55), ColorUtils.getColor(255, 255, 255), 0.2f), (int)((100.0f + (keyCfgAnimScale.getAnim() - 1.0f) * 5.0f * 55.0f) * scale.getAnim()));
        GL11.glPopMatrix();
        if ((double)cfgScale.getAnim() > 0.95 && this.openCfgKey) {
            GuiConfig.cfgScale.setAnim(1.0f);
            this.mc.displayGuiScreen(new GuiConfig());
            this.openCfgKey = false;
        }
        if ((double)cfgScale.getAnim() > 0.05) {
            RenderUtils.drawRect(0.0, 0.0, 10000.0, 10000.0, ColorUtils.getColor(0, 0, 0, (int)(255.0f * cfgScale.getAnim())));
        }
    }

    @Override
    public void onGuiClosed() {
        if (!ClickGui.SaveMusic.bValue) {
            Client.clickGuiMusic.setPlaying(false);
        }
        this.panels.forEach(panel -> panel.onCloseGui());
        this.setPlayBindHold(false);
        Keyboard.enableRepeatEvents(false);
        Client.clientColosUI.onGuiClosed();
        ClickGuiScreen.resetHolds();
    }

    void drawCrosshair(boolean render, ScaledResolution scaled) {
        boolean ofScreen;
        if (!render) {
            return;
        }
        GlStateManager.disableDepth();
        double x = (double)Mouse.getX() / 2.0;
        double y = (double)scaled.getScaledHeight() - (double)Mouse.getY() / 2.0;
        float dx = Mouse.getDX();
        float dy = Mouse.getDY();
        boolean bl = ofScreen = !Mouse.isInsideWindow() || x < 0.0 || x > (double)scaled.getScaledWidth() || y < 0.0 || y > (double)scaled.getScaledHeight();
        if (ofScreen) {
            if (Mouse.isGrabbed()) {
                Mouse.setCursorPosition(Mouse.getX() + (int)dx, Mouse.getY() + (int)dy);
            }
            Mouse.setGrabbed(false);
            crossClick.setAnim(-1.0f);
            return;
        }
        if (crossClick.getAnim() >= -0.001f && crossClick.getAnim() < 0.0f) {
            Mouse.setGrabbed(true);
        }
        if (!Mouse.isGrabbed()) {
            return;
        }
        double texW = 16.0;
        double texH = 16.0;
        double x2 = (x -= 1.0) + texW;
        double y2 = (y -= 1.0) + texH;
        ClickGuiScreen.crossRotate.speed = 0.08f;
        if (MathUtils.getDifferenceOf(crossClick.getAnim(), ClickGuiScreen.crossClick.to) < (double)0.05f) {
            ClickGuiScreen.crossClick.to = 0.0f;
            ClickGuiScreen.crossClick.speed = 0.01f;
        } else {
            ClickGuiScreen.crossClick.speed = 0.15f;
        }
        ClickGuiScreen.crossRotate.to = MathUtils.clamp((float)(Mouse.getDX() + Mouse.getDY()) * 6.0f, -800.0f, 800.0f);
        float angle = -crossRotate.getAnim() / 3.0f;
        if (MathUtils.getDifferenceOf(angle, 0.0f) < 1.0) {
            angle = 0.0f;
        }
        float alpha = globalAlpha.getAnim() * MathUtils.clamp(scale.getAnim(), 0.0f, 1.0f);
        int colorCross = ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), MathUtils.clamp(alpha + Math.abs(angle) / 90.0f, 0.0f, 255.0f));
        int colorOverlay = ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), MathUtils.clamp(alpha * (crossClick.getAnim() + Math.abs(angle) / 45.0f), 0.0f, 255.0f));
        GL11.glEnable(3553);
        GL11.glEnable(3042);
        GL11.glDisable(2929);
        GL11.glBlendFunc(770, 32772);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        this.mc.getTextureManager().bindTexture(MOUSE_TEXTURE_BASE);
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        buffer.pos(x, y).tex(0.0, 0.0).color(colorCross).endVertex();
        buffer.pos(x, y2).tex(0.0, 1.0).color(colorCross).endVertex();
        buffer.pos(x2, y2).tex(1.0, 1.0).color(colorCross).endVertex();
        buffer.pos(x2, y).tex(1.0, 0.0).color(colorCross).endVertex();
        GL11.glPushMatrix();
        RenderUtils.customRotatedObject2D((float)x, (float)y, (float)texW / 1.5f, (float)texW / 1.5f, angle / 4.0f);
        RenderUtils.customScaledObject2D((float)x, (float)y, (float)texW / 1.5f, (float)texW / 1.5f, 1.0f - crossClick.getAnim() / 1.75f + Math.abs(angle) / 160.0f / 4.0f);
        tessellator.draw();
        GL11.glPopMatrix();
        this.mc.getTextureManager().bindTexture(MOUSE_TEXTURE_OVERLAY);
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        buffer.pos(x, y).tex(0.0, 0.0).color(colorOverlay).endVertex();
        buffer.pos(x, y2).tex(0.0, 1.0).color(colorOverlay).endVertex();
        buffer.pos(x2, y2).tex(1.0, 1.0).color(colorOverlay).endVertex();
        buffer.pos(x2, y).tex(1.0, 0.0).color(colorOverlay).endVertex();
        GL11.glPushMatrix();
        RenderUtils.customRotatedObject2D((float)x, (float)y, (float)texW / 1.5f, (float)texW / 1.5f, angle * 1.25f / 4.0f);
        RenderUtils.customScaledObject2D((float)x, (float)y, (float)texW / 1.35f, (float)texH / 1.35f, 1.0f + Math.abs(angle) / 80.0f / 4.0f);
        tessellator.draw(2);
        GL11.glPopMatrix();
        GL11.glBlendFunc(770, 771);
        GlStateManager.enableDepth();
        if (this.checkCustomCross != render) {
            this.checkCustomCross = render;
            Mouse.setCursorPosition((int)x, (int)y);
        }
    }

    void drawDark(ScaledResolution scaled) {
        ClickGuiScreen.darkness.to = ClickGui.Darkness.getBool() && !colose ? ClickGui.DarkOpacity.getFloat() : 0.0f;
        float alphaColosePercent = MathUtils.clamp(globalAlpha.getAnim() / 255.0f * scale.getAnim(), 0.0f, 1.0f);
        int upC = ColorUtils.swapAlpha(0, darkness.getAnim() * alphaColosePercent);
        int downC = ColorUtils.swapAlpha(0, ClickGuiScreen.darkness.anim * alphaColosePercent * alphaColosePercent);
        RenderUtils.drawFullGradientRectPro(0.0f, 0.0f, scaled.getScaledWidth(), scaled.getScaledHeight(), downC, downC, upC, upC, false);
    }

    private void drawParts(float alphaPC) {
        if (this.parts.size() < 20) {
            this.parts.add(new Parts());
        }
        if (this.parts.size() != 0 || this.partsEff.size() != 0) {
            this.startDrawsParts();
        }
        for (Parts part2 : this.parts) {
            if (part2 == null) continue;
            part2.updatePhisics();
            if (part2.canDrawPart()) {
                part2.drawPart(alphaPC);
            }
            part2.removeAuto(part2);
        }
        this.partsEff.forEach(part -> {
            part.update();
            part.draw(alphaPC);
        });
        if (this.parts.size() != 0 || this.partsEff.size() != 0) {
            this.stopDrawsParts();
        }
    }

    void drawScanLines(ScaledResolution sr, float scanLinesDistance, float scanLinesWidth, float alphaPC, int intervalTime, int scanTower) {
        float f;
        ClickGuiScreen.scanLines.to = ClickGui.ScanLinesOverlay.getBool() ? 1.0f : 0.0f;
        alphaPC *= scanLines.getAnim();
        if ((double)f < 0.03) {
            return;
        }
        float timeExtendStopPC = 1.0f;
        float timePC = (float)(System.currentTimeMillis() % (long)((int)((float)intervalTime + timeExtendStopPC * (float)intervalTime))) / (float)intervalTime;
        timePC = timePC > 1.0f ? 1.0f : timePC;
        int color = ColorUtils.getColor(255, 255, 255);
        float w = sr.getScaledWidth();
        float h = (float)sr.getScaledHeight() + (float)scanTower * 4.0f;
        int linesCount = (int)h - 1;
        float x = 0.0f;
        float y = (float)(-scanTower) * 2.0f;
        float x2 = x + w;
        float y2 = y + h;
        float alphaXExt = 50.0f;
        y += scanLinesDistance / 2.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glDisable(3008);
        GL11.glShadeModel(7425);
        GL11.glBlendFunc(770, 32772);
        float prevAlphaPC = alphaPC;
        alphaPC *= MathUtils.clamp(((double)timePC > 0.5 ? 1.0f - timePC : timePC) * 1.5f, 0.0f, 1.0f);
        alphaPC = alphaPC < 0.25f ? 0.25f : (alphaPC *= 0.3f);
        float forceY = y;
        for (int index = 0; index < linesCount; ++index) {
            float diffY = (float)MathUtils.getDifferenceOf(timePC * (float)linesCount, y);
            float aPC = 1.0f - MathUtils.clamp(diffY < scanLinesWidth ? 0.0f : diffY / (float)scanTower * (y < timePC * (float)linesCount ? 0.25f : 1.0f), 0.0f, 1.0f);
            if ((aPC = (aPC < 0.2f ? 0.2f : aPC) * prevAlphaPC) != 0.0f) {
                float alphaXExtInc = alphaXExt + alphaXExt / 3.0f * aPC * aPC;
                float sellPC = MathUtils.clamp(alphaPC * ((diffY < scanLinesWidth * 2.0f ? 1.06f : 1.0f) / 2.0f) * 2.0f, 0.0f, 1.0f);
                GL11.glLineWidth(scanLinesWidth);
                GL11.glBegin(3);
                RenderUtils.setupColor(color, 0.0f);
                GL11.glVertex2d(x, y);
                RenderUtils.setupColor(color, 255.0f * sellPC * aPC);
                GL11.glVertex2d(x + alphaXExtInc, y);
                GL11.glVertex2d(x2 - alphaXExtInc, y);
                RenderUtils.setupColor(color, 0.0f);
                GL11.glVertex2d(x2, y);
                GL11.glEnd();
                GL11.glLineWidth(scanLinesDistance + scanLinesWidth * 2.0f);
                GL11.glBegin(3);
                RenderUtils.setupColor(color, 0.0f);
                GL11.glVertex2d(x, y);
                RenderUtils.setupColor(color, 95.0f * sellPC * aPC);
                GL11.glVertex2d(x + alphaXExtInc, y);
                GL11.glVertex2d(x2 - alphaXExtInc, y);
                RenderUtils.setupColor(color, 0.0f);
                GL11.glVertex2d(x2, y);
                GL11.glEnd();
            }
            y += scanLinesDistance;
        }
        GL11.glShadeModel(7424);
        GL11.glLineWidth(1.0f);
        GL11.glEnable(3008);
        GlStateManager.resetColor();
        GL11.glEnable(3553);
        GL11.glBlendFunc(770, 771);
    }

    void drawBounds(ScaledResolution sr, float alphaPC, int xAnimationDelay) {
        float texPosX;
        int boundsUpCount;
        alphaPC = alphaPC > 1.0f ? 1.0f : (alphaPC < 0.0f ? 0.0f : alphaPC);
        ClickGuiScreen.boundsToggleAnim.to = ClickGui.ScreenBounds.getBool() ? 1.0f : 0.0f;
        if ((double)(alphaPC *= boundsToggleAnim.getAnim()) < 0.05) {
            return;
        }
        float timePC = (float)(System.currentTimeMillis() % (long)xAnimationDelay) / (float)xAnimationDelay;
        int texsScaleX = 15;
        int texsScaleY = 60;
        float xUpBound = (float)(-texsScaleX) * 2.0f + (float)texsScaleX * timePC;
        float yUpBound = (float)(-texsScaleY) * (1.0f - alphaPC * 0.5f);
        float xDownBound = (float)(-texsScaleX) - (float)texsScaleX * timePC;
        float yDownBound = (float)sr.getScaledHeight() - (float)texsScaleY * alphaPC * 0.5f;
        int boundsDownCount = boundsUpCount = (int)((float)sr.getScaledWidth() / (float)texsScaleX + 2.0f);
        GL11.glEnable(3553);
        GL11.glEnable(3042);
        GL11.glShadeModel(7425);
        GL11.glDisable(3008);
        GL11.glBlendFunc(770, 32772);
        int color = ColorUtils.getColor(255, 255, 255, 225.0f * alphaPC);
        int color2 = ColorUtils.getColor(255, 255, 255, 5.0f * alphaPC);
        this.mc.getTextureManager().bindTexture(BOUND_UP);
        int startedBoundsUpCount = boundsUpCount;
        while (boundsUpCount > 0) {
            texPosX = xUpBound + (float)boundsUpCount * (float)texsScaleX;
            builder.begin(9, DefaultVertexFormats.POSITION_TEX_COLOR);
            builder.pos(texPosX + (float)texsScaleX, yUpBound + (float)texsScaleY).tex(1.0, 1.0).color(color).endVertex();
            builder.pos(texPosX + (float)texsScaleX, yUpBound).tex(1.0, 0.0).color(color2).endVertex();
            builder.pos(texPosX, yUpBound).tex(0.0, 0.0).color(color2).endVertex();
            builder.pos(texPosX, yUpBound + (float)texsScaleY).tex(0.0, 1.0).color(color).endVertex();
            tessellator.draw();
            --boundsUpCount;
        }
        this.mc.getTextureManager().bindTexture(BOUND_DOWN);
        while (boundsDownCount > 0) {
            texPosX = xDownBound + (float)boundsDownCount * (float)texsScaleX;
            builder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            builder.pos(texPosX + (float)texsScaleX, yDownBound + (float)texsScaleY).tex(1.0, 1.0).color(color2).endVertex();
            builder.pos(texPosX + (float)texsScaleX, yDownBound).tex(1.0, 0.0).color(color).endVertex();
            builder.pos(texPosX, yDownBound).tex(0.0, 0.0).color(color).endVertex();
            builder.pos(texPosX, yDownBound + (float)texsScaleY).tex(0.0, 1.0).color(color2).endVertex();
            tessellator.draw();
            --boundsDownCount;
        }
        GlStateManager.resetColor();
        GL11.glShadeModel(7424);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3008);
    }

    private void drawSaveMusicButton(int mouseX, int mouseY, ScaledResolution sr, float alphaPC) {
        float f = ClickGui.MusicInGui.getBool() ? 0.8f + (this.isHoverToSaveMusicIco(mouseX, mouseY) ? 0.2f : 0.0f) : (ClickGuiScreen.keySaveSound.to = 0.0f);
        if ((double)(keySaveSound.getAnim() * alphaPC) >= 0.03) {
            ClickGuiScreen.keySaveSoundToggle.to = ClickGui.SaveMusic.getBool() ? 1.0f : 0.0f;
            int color = ColorUtils.getOverallColorFrom(ColorUtils.getColor(110, 110, 110, 190.0f * alphaPC * ClickGuiScreen.keySaveSound.anim), ColorUtils.getColor(255, 255, 255, 255.0f * alphaPC * ClickGuiScreen.keySaveSound.anim), keySaveSoundToggle.getAnim());
            float rot = 360.0f * ((double)ClickGuiScreen.keySaveSoundToggle.anim > 0.5 ? 1.0f - ClickGuiScreen.keySaveSoundToggle.anim : ClickGuiScreen.keySaveSoundToggle.anim) * 2.0f * (ClickGuiScreen.keySaveSoundToggle.to == 0.0f ? -1.0f : 1.0f);
            float scalePlus = 0.4f * ((double)ClickGuiScreen.keySaveSoundToggle.anim > 0.5 ? 1.0f - ClickGuiScreen.keySaveSoundToggle.anim : ClickGuiScreen.keySaveSoundToggle.anim) * 2.0f;
            this.mc.getTextureManager().bindTexture(MUSIC_SAVE_BUTTON);
            float x = 10.0f;
            float y = (float)(sr.getScaledHeight() - 38) - ClickGuiScreen.boundsToggleAnim.anim * 27.0f;
            float size = 28.0f;
            builder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            builder.pos(x + size, y + size).tex(1.0, 1.0).color(color).endVertex();
            builder.pos(x + size, y).tex(1.0, 0.0).color(color).endVertex();
            builder.pos(x, y).tex(0.0, 0.0).color(color).endVertex();
            builder.pos(x, y + size).tex(0.0, 1.0).color(color).endVertex();
            GL11.glEnable(3553);
            GL11.glEnable(3042);
            GL11.glShadeModel(7425);
            GL11.glDisable(3008);
            GL11.glBlendFunc(770, 32772);
            GL11.glPushMatrix();
            RenderUtils.customRotatedObject2D(x, y, size, size, -rot);
            RenderUtils.customScaledObject2D(x, y, size, size, 1.0f + scalePlus);
            tessellator.draw(2);
            GL11.glPopMatrix();
            GlStateManager.resetColor();
            GL11.glShadeModel(7424);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3008);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.updatePlayingBinding();
        ClickGuiScreen.globalAlpha.speed = 0.05f;
        float f = ClickGuiScreen.scale.speed = colose && ClickGuiScreen.scale.anim <= 1.0f ? 0.02f : 0.035f;
        if (!colose) {
            this.scroll(mouseX, mouseY);
        }
        if ((double)scale.getAnim() > 1.04) {
            float f2 = ClickGuiScreen.scale.to = colose ? 0.25f : 1.0f;
            if (!colose && !ClickGui.MusicInGui.getBool()) {
                ClientTune.get.playGuiScreenFoneticSong();
            }
            float f3 = ClickGuiScreen.globalAlpha.to = colose ? 26.0f : 255.0f;
        }
        if ((double)scale.getAnim() < 0.35) {
            Minecraft.player.closeScreen();
            colose = false;
            globalAlpha.setAnim(26.0f);
        }
        ScaledResolution sr = new ScaledResolution(this.mc);
        this.drawDark(sr);
        float f4 = ClickGuiScreen.epilepsy.to = ClickGui.Epilepsy.getBool() ? 1.0f : 0.0f;
        if ((double)epilepsy.getAnim() > 0.03) {
            GL11.glPushMatrix();
            GL11.glDepthMask(false);
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            float r = 10.0f;
            float size = 273.0f;
            float aPC = MathUtils.clamp(scale.getAnim() * (globalAlpha.getAnim() / 255.0f) * epilepsy.getAnim(), 0.0f, 1.0f);
            float extXY = 0.0f;
            int ii = 0;
            while ((float)ii < r) {
                int i = 0;
                while ((float)i < r) {
                    float pc = (float)i / r;
                    float pc2 = (float)ii / r;
                    ScaledResolution s = new ScaledResolution(this.mc);
                    float x = extXY + ((float)s.getScaledWidth() - size / 1.5f - extXY * 2.0f) * pc;
                    float y = extXY + ((float)s.getScaledHeight() - size / 1.5f - extXY * 2.0f) * pc2;
                    float dx = x - ((float)s.getScaledWidth() - size) / 2.0f;
                    float dy = y - ((float)s.getScaledHeight() - size) / 2.0f;
                    int cInd = i * 100 - ii * 60 * (ii % 2 == 0 ? 1 : -1);
                    float xpc = x / ((float)sr.getScaledWidth() - size);
                    Module.Category cat = (double)xpc <= 0.2 ? Module.Category.COMBAT : ((double)xpc <= 0.4 ? Module.Category.MOVEMENT : ((double)xpc <= 0.6 ? Module.Category.RENDER : ((double)xpc <= 0.8 ? Module.Category.PLAYER : Module.Category.MISC)));
                    int e = ClickGuiScreen.getColor(cInd, cat);
                    int c = ColorUtils.fadeColorIndexed(e, 0, 0.3f, (int)((float)cInd * 0.3f));
                    c = ColorUtils.swapAlpha(c, MathUtils.clamp((float)ColorUtils.getAlphaFromColor(c) * aPC * 1.2f, 0.0f, 255.0f));
                    this.drawImage(this.BLOOM_TEX, x, y, size, size, c);
                    ++i;
                }
                ++ii;
            }
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GL11.glDepthMask(true);
            GL11.glPopMatrix();
        }
        this.drawBlur(sr);
        this.drawScanLines(sr, 4.0f, 1.0f, MathUtils.clamp(globalAlpha.getAnim() / 255.0f * scale.getAnim(), 0.0f, 1.0f), 3500, 23);
        this.drawBounds(sr, MathUtils.clamp(globalAlpha.getAnim() / 255.0f * scale.getAnim() * scale.getAnim(), 0.0f, 1.0f), 300);
        if (ClickGui.Particles.getBool()) {
            GL11.glPushMatrix();
            this.drawParts(MathUtils.clamp(scale.getAnim() * scale.getAnim(), 0.0f, 1.0f));
            GL11.glPopMatrix();
        } else if (this.parts.size() != 0) {
            this.startDrawsParts();
            for (Parts part : this.parts) {
                if (part == null) continue;
                part.alphaPC.to = 0.0f;
                part.removeAuto(part);
                if (!part.canDrawPart()) continue;
                part.drawPart(MathUtils.clamp(scale.getAnim() * scale.getAnim(), 0.0f, 1.0f) * part.alphaPC.getAnim());
            }
            this.stopDrawsParts();
        }
        this.drawWaveRect(sr);
        this.forSearch(sr, mouseX, mouseY);
        this.renderImages(sr, ClickGui.Images.getBool());
        GlStateManager.pushMatrix();
        RenderUtils.customScaledObject2D(0.0f, 0.0f, sr.getScaledWidth(), sr.getScaledHeight(), 1.0f + MathUtils.clamp((1.0f - scale.getAnim()) * (scale.getAnim() > 1.0f ? 1.0f : 24.0f * (colose ? 1.0f : 0.2f)), -0.1f, 25.0f * (colose ? 1.0f : 0.2f)));
        for (Panel panel : this.panels) {
            panel.drawScreen(mouseX, mouseY, partialTicks);
        }
        GlStateManager.popMatrix();
        this.renderDescriptions();
        this.cfgGuiOpenner(mouseX, mouseY);
        this.particleRender();
        ClickGuiScreen.particleRemoveAuto();
        RenderUtils.fixShadows();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.drawSaveMusicButton(mouseX, mouseY, sr, MathUtils.clamp(ClickGuiScreen.scale.anim * ClickGuiScreen.scale.anim * ClickGuiScreen.globalAlpha.anim / 255.0f, 0.0f, 1.0f));
        Client.clientColosUI.drawScreen(mouseX, mouseY, partialTicks);
        this.drawCrosshair(ClickGui.CustomCursor.getBool(), sr);
        this.updateHolds(mouseX, mouseY, 600.0f);
    }

    public static void resetHolds() {
        clickedMouse0 = false;
        clickedMouse1 = false;
        clickedMouse2 = false;
    }

    private void updateHolds(int mouseX, int mouseY, float timeCheckHold) {
        if (colose) {
            ClickGuiScreen.resetHolds();
        }
        if (clickedMouse0) {
            checkMouse0Hold = true;
        } else {
            this.timeMouse0Hold.reset();
        }
        if (clickedMouse1) {
            checkMouse1Hold = true;
        } else {
            this.timeMouse1Hold.reset();
        }
        if (clickedMouse2) {
            checkMouse2Hold = true;
        } else {
            this.timeMouse2Hold.reset();
        }
        if (this.timeMouse0Hold.hasReached(timeCheckHold) && checkMouse0Hold) {
            this.panels.forEach(panel -> {
                int i = 26;
                for (Mod mod : panel.mods) {
                    mod.mouseClicked((int)panel.X, (int)panel.Y + i, mouseX, mouseY, 0);
                    i = (int)((float)i + (mod.openAnim.anim + 1.0f));
                }
            });
            Client.clientColosUI.mouseClicked(mouseX, mouseY, 0);
            checkMouse0Hold = false;
        }
        if (this.timeMouse1Hold.hasReached(timeCheckHold) && checkMouse1Hold) {
            this.panels.forEach(panel -> {
                int i = 26;
                for (Mod mod : panel.mods) {
                    mod.mouseClicked((int)panel.X, (int)panel.Y + i, mouseX, mouseY, 1);
                    i = (int)((float)i + (mod.openAnim.getAnim() + 1.0f));
                }
            });
            Client.clientColosUI.mouseClicked(mouseX, mouseY, 1);
            checkMouse1Hold = false;
        }
        if (this.timeMouse2Hold.hasReached(timeCheckHold) && checkMouse2Hold) {
            this.panels.forEach(panel -> {
                int i = 26;
                for (Mod mod : panel.mods) {
                    mod.mouseClicked((int)panel.X, (int)panel.Y + i, mouseX, mouseY, 2);
                    i = (int)((float)i + (mod.openAnim.getAnim() + 1.0f));
                }
            });
            Client.clientColosUI.mouseClicked(mouseX, mouseY, 2);
            checkMouse2Hold = false;
        }
    }

    private boolean isHoverToSaveMusicIco(int mouseX, int mouseY) {
        if (!ClickGui.MusicInGui.bValue) {
            return false;
        }
        float dx = 24.0f - (float)mouseX;
        ScaledResolution sr = new ScaledResolution(this.mc);
        float dy = (float)sr.getScaledHeight() - 10.0f - 14.0f - ClickGuiScreen.boundsToggleAnim.anim * 27.0f - (float)mouseY;
        return Math.sqrt(dx * dx + dy * dy) <= 11.0;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton == 0) {
            clickedMouse0 = true;
        }
        if (mouseButton == 1) {
            clickedMouse1 = true;
        }
        if (mouseButton == 2) {
            clickedMouse2 = true;
        }
        if (!colose && !Client.clientColosUI.isHoveredToPanel(mouseX, mouseY)) {
            if (this.isHoverToSaveMusicIco(mouseX, mouseY) && mouseButton == 0) {
                ClickGui.SaveMusic.bValue = !ClickGui.SaveMusic.bValue;
                ClientTune.get.playGuiScreenMusicSaveToggleSong(ClickGui.SaveMusic.bValue);
            }
            if (ClickGui.CustomCursor.getBool()) {
                float f = ClickGuiScreen.crossClick.to = mouseButton == 0 ? 0.55f : 0.35f;
            }
            if (mouseButton == 0) {
                float w = 20.0f + searchStringWAnim.getAnim();
                float h = 20.0f;
                float x = 5.0f;
                float y = 5.0f;
                float x2 = 5.0f + w;
                float y2 = 25.0f;
                this.searchClickReader(x2 - 20.0f, 5.0f, x2, 25.0f, mouseX, mouseY);
            }
            ArrayList<Panel> reversedPanels = new ArrayList<Panel>(this.panels);
            Collections.reverse(reversedPanels);
            for (Panel panel : reversedPanels) {
                panel.mouseClicked(mouseX, mouseY, mouseButton);
                if (!this.panels.stream().anyMatch(panel2 -> panel2.callClicked)) continue;
                this.panels.stream().forEach(panel3 -> {
                    panel3.callClicked = false;
                });
                break;
            }
            if (mouseButton == 0) {
                Mouse.setGrabbed(ClickGui.CustomCursor.getBool());
            }
        }
        Client.clientColosUI.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (colose) {
            return;
        }
        textFieldSearch.textboxKeyTyped(typedChar, keyCode);
        if ((Keyboard.isKeyDown(28) || Keyboard.isKeyDown(1)) && textFieldSearch.isFocused()) {
            textFieldSearch.setText("");
            textFieldSearch.setFocused(false);
        } else if (Keyboard.isKeyDown(29) && Keyboard.isKeyDown(33)) {
            textFieldSearch.setFocused(!textFieldSearch.isFocused());
        }
        if (!textFieldSearch.isFocused()) {
            textFieldSearch.setText("");
        }
        if ((keyCode == 1 || keyCode == ClickGui.instance.getBind() && keyCode != 29) && !colose) {
            this.mc.setIngameFocus();
            Mouse.setGrabbed(true);
            colose = true;
            Client.configManager.saveConfig("Default");
            if (!ClickGui.SaveMusic.bValue) {
                Client.clickGuiMusic.setPlaying(false);
            }
            Keyboard.enableRepeatEvents(false);
            ClickGuiScreen.scale.to = 1.08f;
            ClientTune.get.playGuiScreenOpenOrCloseSong(false);
        }
        for (Panel panel : this.panels) {
            panel.keyPressed(keyCode);
        }
        Client.clientColosUI.keyTyped(typedChar, keyCode);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void initGui() {
        super.initGui();
        ClickGuiScreen.resetHolds();
        colose = false;
        globalAlpha.setAnim(26.0f);
        ClickGuiScreen.globalAlpha.to = 255.0f;
        scale.setAnim(0.8f);
        ClickGuiScreen.scale.to = 1.08f;
        ClickGuiScreen.scale.speed = 0.075f;
        if (ClickGui.CustomCursor.getBool()) {
            Mouse.setGrabbed(true);
        }
        Client.clientColosUI.initGui();
        ClientTune.get.playGuiScreenOpenOrCloseSong(true);
    }

    public void particleRender() {
        for (int i = 0; i < particles.size(); ++i) {
            if (particles.get(i) == null) continue;
            RenderUtils.drawClientCircle(ClickGuiScreen.particles.get((int)i).x, ClickGuiScreen.particles.get((int)i).y, ClickGuiScreen.particles.get((int)i).radius.getAnim(), 359.0f, (1.0f - ClickGuiScreen.particles.get((int)i).radius.getAnim() / ClickGuiScreen.particles.get((int)i).radius.to) * 5.0f + 2.0f, ClickGuiScreen.particles.get((int)i).alpha.getAnim() / 180.0f * (globalAlpha.getAnim() / 255.0f));
            RenderUtils.fixShadows();
        }
    }

    static void particleRemoveAuto() {
        for (int i = 0; i < particles.size(); ++i) {
            if (!(ClickGuiScreen.particles.get((int)i).radius.getAnim() >= ClickGuiScreen.particles.get((int)i).radius.to - 0.25f) && !(ClickGuiScreen.particles.get((int)i).alpha.getAnim() <= 26.0f)) continue;
            particles.remove(i);
        }
    }

    static void spawnParticleRandPos(float x, float y, float randomizeValX, float randomizeValY, long spawnDelay) {
        x = (float)((double)x + MathUtils.getRandomInRange(-randomizeValX, randomizeValX));
        y = (float)((double)y + MathUtils.getRandomInRange(-randomizeValY, randomizeValY));
        if (System.currentTimeMillis() % spawnDelay == 0L) {
            particles.add(new searchParticle(x, y));
        }
    }

    private void startDrawsParts() {
        GlStateManager.enableBlend();
        GL11.glDisable(3008);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GL11.glDepthMask(false);
    }

    private void stopDrawsParts() {
        GL11.glDepthMask(true);
        GlStateManager.resetColor();
        GlStateManager.enableBlend();
        GL11.glEnable(3008);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.enableAlpha();
    }

    private void drawImage(ResourceLocation imageIII, float x, float y, float w, float h, int color) {
        this.mc.getTextureManager().bindTexture(imageIII);
        RenderUtils.glColor(color);
        GlStateManager.translate(x, y, 1.0f);
        Gui.drawModalRectWithCustomSizedTexture(0.0f, 0.0f, 0.0f, 0.0f, w, h, w, h);
        GlStateManager.translate(-x, -y, -1.0f);
    }

    static {
        scale = new AnimationUtils(0.0f, 0.0f, 0.1f);
        globalAlpha = new AnimationUtils(0.0f, 0.0f, 0.1f);
        scrolled = new AnimationUtils(0.0f, 0.0f, 0.1f);
        cfgScale = new AnimationUtils(0.0f, 0.0f, 0.1f);
        colose = false;
        textFieldSearch = new CTextField(1, Fonts.mntsb_20, 5, 5, 100, 20);
        searchAnim = new AnimationUtils(0.0f, 0.0f, 0.1f);
        searchStringWAnim = new AnimationUtils(0.0f, 0.0f, 0.1f);
        scrollAnimation = new AnimationUtils(0.0f, 0.0f, 0.125f);
        imageScale = new AnimationUtils(0.0f, 0.0f, 0.1f);
        imageRender = new AnimationUtils(0.0f, 0.0f, 0.1f);
        waveAlphaRender = new AnimationUtils(0.0f, 0.0f, 0.1f);
        waveAlphaHRender = new AnimationUtils(0.0f, 0.0f, 0.1f);
        blurStrenghRender = new AnimationUtils(0.0f, 0.0f, 0.03f);
        keyCfgAnimScale = new AnimationUtils(1.0f, 1.0f, 0.15f);
        keyCfgAnimRotate = new AnimationUtils(0.0f, 0.0f, 0.15f);
        crossRotate = new AnimationUtils(0.0f, 0.0f, 0.05f);
        crossClick = new AnimationUtils(0.0f, 0.0f, 0.06f);
        MOUSE_TEXTURE_BASE = new ResourceLocation("vegaline/ui/clickgui/mousecrosshair/mousebase32.png");
        MOUSE_TEXTURE_OVERLAY = new ResourceLocation("vegaline/ui/clickgui/mousecrosshair/mouseoverlay32.png");
        darkness = new AnimationUtils(0.0f, 0.0f, 0.01f);
        epilepsy = new AnimationUtils(0.0f, 0.0f, 0.05f);
        scanLines = new AnimationUtils(0.0f, 0.0f, 0.05f);
        BOUND_UP = new ResourceLocation("vegaline/ui/clickgui/cornerbounds/cornersectionup.png");
        BOUND_DOWN = new ResourceLocation("vegaline/ui/clickgui/cornerbounds/cornersectiondown.png");
        BOUND_CONFLICT = new ResourceLocation("vegaline/ui/clickgui/components/mod/binding/bindconflict.png");
        tessellator = Tessellator.getInstance();
        builder = tessellator.getBuffer();
        boundsToggleAnim = new AnimationUtils(ClickGui.ScreenBounds.getBool() ? 1.0f : 0.0f, ClickGui.ScreenBounds.getBool() ? 1.0f : 0.0f, 0.075f);
        keySaveSound = new AnimationUtils(ClickGui.MusicInGui.getBool() ? 1.0f : 0.0f, ClickGui.MusicInGui.getBool() ? 1.0f : 0.0f, 0.075f);
        keySaveSoundToggle = new AnimationUtils(ClickGui.SaveMusic.getBool() ? 1.0f : 0.0f, ClickGui.SaveMusic.getBool() ? 1.0f : 0.0f, 0.1f);
        MUSIC_SAVE_BUTTON = new ResourceLocation("vegaline/ui/clickgui/musictuner/buttons/musicsavebutton.png");
        particles = new ArrayList();
    }

    private class Parts {
        List<PartTrail> partTrails = new ArrayList<PartTrail>();
        Vec2f pos = this.getPos();
        AnimationUtils alphaPC = new AnimationUtils(0.0f, 1.0f, 0.02f);
        ResourceLocation part = this.getNewPart();
        double motionX = this.getMotionsStart()[0];
        double motionY = this.getMotionsStart()[1];
        int randomIndex = (int)(Math.random() * 12000.0);
        double rotateNumb = this.getRotateStart();
        double rotate = 0.0;
        long timeOfSpawn = System.currentTimeMillis();
        int randomInt = (int)(1.0 + Math.random() * 4.5);
        Module.Category colorCategory = this.randomInt == 1 ? Module.Category.COMBAT : (this.randomInt == 2 ? Module.Category.MOVEMENT : (this.randomInt == 3 ? Module.Category.RENDER : (this.randomInt == 4 ? Module.Category.PLAYER : (this.randomInt == 5 ? Module.Category.MISC : (this.randomInt == 1 ? Module.Category.COMBAT : null)))));

        private boolean canDrawPart() {
            return !(this.part == null || (double)this.alphaPC.getAnim() < 0.05 && this.alphaPC.to == 0.0f);
        }

        public Module.Category getColorCategory() {
            return this.colorCategory;
        }

        private double getRotateStart() {
            return -(Math.random() * 20.0) + Math.random() * 40.0;
        }

        private double[] getMotionsStart() {
            return new double[]{-(Math.random() * 3.0) + Math.random() * 6.0, -(0.25 * Math.random()) + Math.random() * 1.0};
        }

        private boolean[] isColiddedByPart(Parts part) {
            if (part != null && part.getTime() > 200L) {
                boolean yg;
                boolean xgN = MathUtils.getDifferenceOf((double)(this.pos.x + 8.0f) + this.motionX / 4.0, (double)(part.pos.x + 8.0f) + part.motionX / 4.0) < 16.0;
                boolean ygN = MathUtils.getDifferenceOf((double)(this.pos.y + 8.0f) + this.motionX / 4.0, (double)(part.pos.y + 8.0f) + part.motionX / 4.0) < 16.0;
                boolean xg = MathUtils.getDifferenceOf((double)(this.pos.x + 8.0f) + this.motionX / 4.0, (double)(part.pos.x + 8.0f) + part.motionX / 4.0) < 12.0;
                boolean bl = yg = MathUtils.getDifferenceOf((double)(this.pos.y + 8.0f) + this.motionX / 4.0, (double)(part.pos.y + 8.0f) + part.motionX / 4.0) < 12.0;
                if (xg || xgN || ygN || yg) {
                    return new boolean[]{xg && ygN, yg && xgN};
                }
            }
            return new boolean[]{false, false};
        }

        private void updatePhisics() {
            ScaledResolution sr = new ScaledResolution(ClickGuiScreen.this.mc);
            if (this.alphaPC.to != 0.0f) {
                this.pos.x = (float)((double)this.pos.x + this.motionX);
                this.pos.y = (float)((double)this.pos.y + this.motionY);
                this.motionY += (double)0.02f;
            }
            this.rotate += this.rotateNumb;
            boolean collideX = (double)this.pos.x + this.motionX / 2.0 + 5.0 <= 0.0 || (double)this.pos.x + this.motionX / 2.0 + 16.0 - 5.0 >= (double)sr.getScaledWidth();
            boolean collideY = (double)this.pos.y + this.motionY / 2.0 + 5.0 <= 0.0 || (double)this.pos.y + this.motionY / 2.0 + 16.0 - 5.0 >= (double)sr.getScaledHeight();
            this.motionX *= collideX ? (double)-0.98f : (double)0.9995f;
            this.motionY *= collideY ? (double)-0.98f : (double)0.9995f;
            if (this.getSpeed() > 3.0 && (collideX || collideY) && this.alphaPC.to != 0.0f) {
                this.alphaPC.to = 0.0f;
                this.timeOfSpawn += 10000000L;
                if (!ClickGuiScreen.this.partsEff.stream().anyMatch(p -> p.part == this)) {
                    ClickGuiScreen.this.partsEff.add(new FallPartsEffect(this));
                }
            }
            if (collideX) {
                this.motionY *= (double)0.9f;
            }
            if (collideY || this.getSpeed() < 0.01) {
                this.motionX *= (double)0.97f;
                if (this.getSpeed() < (double)0.2f && this.alphaPC.to != 0.0f) {
                    this.alphaPC.to = 0.0f;
                    this.timeOfSpawn += 10000000L;
                    if (!ClickGuiScreen.this.partsEff.stream().anyMatch(p -> p.part == this)) {
                        ClickGuiScreen.this.partsEff.add(new FallPartsEffect(this));
                    }
                }
            }
            if (this.getSpeed() < 0.15 && this.alphaPC.to != 0.0f) {
                this.alphaPC.to = 0.0f;
                this.timeOfSpawn += 10000000L;
            }
            for (Parts part : ClickGuiScreen.this.parts) {
                Parts lose;
                boolean collidePartY;
                boolean[] collidePart = this.isColiddedByPart(part);
                boolean collidePartX = part != null && part != this && this.alphaPC.to != 0.0f && collidePart[0];
                boolean bl = collidePartY = part != null && part != this && this.alphaPC.to != 0.0f && collidePart[1];
                if (this.alphaPC.to == 0.0f || !collidePartX && !collidePartY || part == this) continue;
                Parts best = part.getSpeed() > this.getSpeed() ? part : this;
                Parts parts = lose = part.getSpeed() > this.getSpeed() ? this : part;
                if (collidePartX) {
                    best.motionX *= (double)0.95f;
                    lose.motionX *= (double)1.1f;
                }
                if (collidePartY) {
                    best.motionY *= (double)0.95f;
                    lose.motionY *= (double)1.1f;
                }
                if (collidePartX) {
                    lose.motionX *= -1.0;
                    best.motionX *= -1.0;
                }
                if (collidePartY) {
                    best.motionY *= -1.0;
                    lose.motionY *= -1.0;
                }
                best.rotateNumb *= (double)-0.9f;
                lose.rotateNumb *= (double)-1.05f;
                lose.timeOfSpawn += 150L;
                best.timeOfSpawn += 50L;
                if ((double)best.getTimePC() >= 0.8 && !ClickGuiScreen.this.partsEff.stream().anyMatch(p -> p.part == best)) {
                    ClickGuiScreen.this.partsEff.add(new FallPartsEffect(best));
                }
                if (!ClickGuiScreen.this.partsEff.stream().anyMatch(p -> p.part == lose)) {
                    ClickGuiScreen.this.partsEff.add(new FallPartsEffect(lose));
                }
                if (best.getSpeed() < 0.4) {
                    if (lose.getSpeed() < 0.5) {
                        if (collidePartX) {
                            lose.motionX = best.motionX / 2.0;
                        }
                        if (collidePartY) {
                            lose.motionY = best.motionY / 2.0;
                        }
                        lose.motionY += (double)0.02f;
                        lose.timeOfSpawn += 500L;
                    }
                    if (collidePartX) {
                        best.motionX /= 2.0;
                    }
                    if (collidePartY) {
                        best.motionY /= 2.0;
                        lose.motionY += (double)0.1f;
                        best.motionY += (double)0.02f;
                    }
                    lose.timeOfSpawn += 100L;
                }
                if (!(best.getSpeed() > (double)4.7f) || !(lose.getSpeed() > (double)4.7f)) continue;
                if (best.alphaPC.to != 0.0f) {
                    best.alphaPC.to = 0.0f;
                    best.timeOfSpawn += 10000000L;
                    if (!ClickGuiScreen.this.partsEff.stream().anyMatch(p -> p.part == best)) {
                        ClickGuiScreen.this.partsEff.add(new FallPartsEffect(best));
                    }
                }
                if (lose.alphaPC.to == 0.0f) continue;
                lose.alphaPC.to = 0.0f;
                lose.timeOfSpawn += 10000000L;
                if (ClickGuiScreen.this.partsEff.stream().anyMatch(p -> p.part == lose)) continue;
                ClickGuiScreen.this.partsEff.add(new FallPartsEffect(lose));
            }
            this.rotateNumb *= collideY || collideX ? (double)-0.9f : (double)0.9993f;
            if (collideY || collideX) {
                this.rotate += this.rotateNumb * 1.25;
                if (this.alphaPC.to == 1.0f && this.alphaPC.getAnim() > 0.75f) {
                    this.alphaPC.setAnim(0.75f);
                }
            }
            this.controlPartTrailsCount();
        }

        private double getSpeed() {
            return Math.sqrt(this.motionX * this.motionX + this.motionY * this.motionY);
        }

        private long getCurrentTime() {
            return System.currentTimeMillis();
        }

        private long getSpawnTime() {
            return this.timeOfSpawn;
        }

        private long getTime() {
            return this.getCurrentTime() - this.getSpawnTime();
        }

        private float getMaxTime() {
            return 20000.0f;
        }

        private float getTimePC() {
            return MathUtils.clamp((float)this.getTime() / this.getMaxTime(), 0.0f, 1.0f);
        }

        private Vec2f getPos() {
            ScaledResolution sr = new ScaledResolution(ClickGuiScreen.this.mc);
            return new Vec2f((float)((double)sr.getScaledWidth() * Math.random()), (float)((double)sr.getScaledHeight() * Math.random()));
        }

        private ResourceLocation getNewPart() {
            Object numb = "vegaline/ui/clickgui/particles/recochetparticles/default/part";
            int randNumb = (int)MathUtils.clamp(Math.random() * 7.5, 1.0, 7.0);
            numb = (String)numb + randNumb;
            ResourceLocation rs = new ResourceLocation((String)numb + ".png");
            return rs;
        }

        private void removeAuto(Parts part) {
            if (part != null && part.getTimePC() >= 1.0f) {
                part.alphaPC.to = 0.0f;
            }
            ClickGuiScreen.this.parts.removeIf(part2 -> part2 != null && (double)part2.alphaPC.getAnim() < 0.1 && part2.alphaPC.to == 0.0f);
        }

        private void draw(float alphaPC) {
            float alpha = MathUtils.clamp(alphaPC * this.alphaPC.getAnim(), 0.0f, 1.0f);
            RenderUtils.customRotatedObject2D(this.pos.x, this.pos.y, 16.0f, 16.0f, (float)this.rotate);
            GL11.glPushMatrix();
            RenderUtils.customScaledObject2D(this.pos.x, this.pos.y, 16.0f, 16.0f, alpha);
            ClickGuiScreen.this.drawImage(this.part, this.pos.x, this.pos.y, 16.0f, 16.0f, ColorUtils.swapAlpha(ClickGuiScreen.getColor(this.randomIndex, this.getColorCategory()), 255.0f * alpha));
            GL11.glPopMatrix();
            RenderUtils.customRotatedObject2D(this.pos.x, this.pos.y, 16.0f, 16.0f, (float)(-this.rotate));
        }

        private void drawPart(float alphaPC) {
            this.draw(alphaPC);
            this.drawPartTrails(alphaPC);
        }

        private int partTrailsMaxLength() {
            return (int)((7.0 + MathUtils.clamp(10.0 * this.getSpeed(), 0.0, 50.0)) * (double)this.alphaPC.getAnim());
        }

        private void controlPartTrailsCount() {
            int maxLength = this.partTrailsMaxLength();
            if (maxLength == 0) {
                this.partTrails.clear();
            } else {
                this.partTrails.add(new PartTrail(this, this.partTrails.size(), this.pos.x + 8.0f - (float)this.motionX * 3.0f, this.pos.y + 8.0f - (float)this.motionY * 3.0f));
                this.partTrails.forEach(PartTrail::updateIndex);
                this.partTrails.removeIf(part -> part.indexPart >= (float)maxLength);
            }
        }

        private void drawPartTrails(float alphaPC) {
            if (this.partTrails.isEmpty()) {
                return;
            }
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            GL11.glDisable(3553);
            GL11.glShadeModel(7425);
            GL11.glLineWidth(1.0E-4f + 6.0f * this.alphaPC.getAnim() * (float)MathUtils.clamp(this.getSpeed() / 5.0, 0.5, 1.0));
            GL11.glBegin(3);
            this.partTrails.forEach(trail -> trail.drawVertex(alphaPC * this.alphaPC.getAnim()));
            GL11.glEnd();
            GL11.glLineWidth(1.0f);
            GL11.glShadeModel(7424);
            GL11.glEnable(3553);
            GL11.glHint(3154, 4352);
            GL11.glDisable(2848);
        }
    }

    static class searchParticle {
        final float x;
        final float y;
        final AnimationUtils radius = new AnimationUtils(0.0f, 12.5f, 0.02f);
        final AnimationUtils alpha = new AnimationUtils(180.0f, 0.0f, 0.02f);

        public searchParticle(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    private class FallPartsEffect {
        AnimationUtils alphaPC = new AnimationUtils(0.5f, 1.0f, 0.1f);
        Parts part;

        public FallPartsEffect(Parts part) {
            this.part = part;
        }

        public void update() {
            if ((double)this.alphaPC.getAnim() > 0.9995 && this.alphaPC.to != 0.0f) {
                this.alphaPC.to = 0.0f;
                this.alphaPC.speed = 0.05f;
            }
            ClickGuiScreen.this.partsEff.removeIf(part -> part != null && (double)part.alphaPC.getAnim() < 0.1 && part.alphaPC.to == 0.0f);
        }

        public void draw(float alphaPC) {
            float alpha = MathUtils.clamp(alphaPC * this.alphaPC.getAnim(), 0.0f, 1.0f);
            GL11.glPushMatrix();
            RenderUtils.customScaledObject2D(this.part.pos.x, this.part.pos.y, 16.0f, 16.0f, alpha);
            ClickGuiScreen.this.drawImage(ClickGuiScreen.this.BLOOM_TEX, this.part.pos.x - 8.0f, this.part.pos.y - 8.0f, 32.0f, 32.0f, ColorUtils.swapAlpha(ClickGuiScreen.getColor(this.part.randomIndex, this.part.getColorCategory()), 255.0f * alpha));
            ClickGuiScreen.this.drawImage(ClickGuiScreen.this.BLOOM_TEX, this.part.pos.x, this.part.pos.y, 16.0f, 16.0f, ColorUtils.swapAlpha(ClickGuiScreen.getColor(this.part.randomIndex, this.part.getColorCategory()), 255.0f * alpha));
            ClickGuiScreen.this.drawImage(ClickGuiScreen.this.BLOOM_TEX, this.part.pos.x + 4.0f, this.part.pos.y + 4.0f, 8.0f, 8.0f, ColorUtils.swapAlpha(ClickGuiScreen.getColor(this.part.randomIndex, this.part.getColorCategory()), 255.0f * alpha));
            GL11.glPopMatrix();
        }
    }

    private class PartTrail {
        float indexPart = 0.0f;
        float maxSizeIndex;
        Parts part;
        float x;
        float y;

        public PartTrail(Parts part, int maxSizeIndex, float vertexX, float vertexY) {
            this.part = part;
            this.maxSizeIndex = maxSizeIndex;
            this.x = vertexX;
            this.y = vertexY;
        }

        public void updateIndex() {
            this.indexPart += 1.0f;
        }

        public void drawVertex(float alphaPC) {
            float pc = this.getIndexOfMax();
            pc = (pc *= MathUtils.clamp((this.getIndexPart() - 5.0f) / 10.0f, 0.0f, 1.0f)) > 0.5f ? 1.0f - pc : pc;
            RenderUtils.glColor(ColorUtils.swapAlpha(ClickGuiScreen.getColor(0, this.part.getColorCategory()), 255.0f * (alphaPC *= (pc *= 2.0f))));
            GL11.glVertex2f(this.x, this.y);
        }

        public float getIndexPart() {
            return this.indexPart;
        }

        public float getMaxSizeIndexPart() {
            return this.maxSizeIndex;
        }

        public float getIndexOfMax() {
            return MathUtils.clamp(this.getIndexPart() / this.getMaxSizeIndexPart(), 0.0f, 1.0f);
        }
    }
}

