// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.visual;

import intent.AquaDev.aqua.utils.RenderUtil;
import intent.AquaDev.aqua.utils.UnicodeFontRenderer7;
import intent.AquaDev.aqua.utils.UnicodeFontRenderer9;
import intent.AquaDev.aqua.utils.UnicodeFontRenderer4;
import java.util.Iterator;
import intent.AquaDev.aqua.utils.UnicodeFontRenderer;
import net.minecraft.util.EnumChatFormatting;
import java.nio.ByteBuffer;
import net.minecraft.client.renderer.GlStateManager;
import intent.AquaDev.aqua.fr.lavache.anime.Animate;
import intent.AquaDev.aqua.fr.lavache.anime.Easing;
import intent.AquaDev.aqua.utils.ColorUtils;
import net.minecraft.client.gui.Gui;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.List;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.gui.ScaledResolution;
import events.listeners.EventPostRender2D;
import intent.AquaDev.aqua.gui.novolineOld.themesScreen.ThemeScreen;
import events.listeners.EventRender2D;
import events.Event;
import java.util.Random;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import intent.AquaDev.aqua.modules.Module;

public class Arraylist extends Module
{
    Minecraft MC;
    Color randomColor;
    
    public Arraylist() {
        super("Arraylist", Type.Visual, "Arraylist", 0, Category.Visual);
        this.MC = Minecraft.getMinecraft();
        Aqua.setmgr.register(new Setting("Rainbow", this, true));
        Aqua.setmgr.register(new Setting("BiggerOffset", this, true));
        Aqua.setmgr.register(new Setting("Fade", this, true));
        Aqua.setmgr.register(new Setting("ShaderFade", this, true));
        Aqua.setmgr.register(new Setting("GlowRects", this, true));
        Aqua.setmgr.register(new Setting("GlowStrings", this, true));
        Aqua.setmgr.register(new Setting("Blur", this, true));
        Aqua.setmgr.register(new Setting("Background", this, true));
        Aqua.setmgr.register(new Setting("BackgroundFade", this, true));
        Aqua.setmgr.register(new Setting("FontShadow", this, false));
        Aqua.setmgr.register(new Setting("ReverseFade", this, true));
        Aqua.setmgr.register(new Setting("Sort", this, true));
        Aqua.setmgr.register(new Setting("StringAlpha", this, 255.0, 0.0, 255.0, true));
        Aqua.setmgr.register(new Setting("Sigma", this, 5.0, 0.0, 50.0, true));
        Aqua.setmgr.register(new Setting("Multiplier", this, 1.0, 0.0, 3.0, false));
        Aqua.setmgr.register(new Setting("Alpha", this, 80.0, 5.0, 240.0, true));
        Aqua.setmgr.register(new Setting("Color", this));
        Aqua.setmgr.register(new Setting("Fonts", this, "Comfortaa", new String[] { "Comfortaa", "Minecraft" }));
        Aqua.setmgr.register(new Setting("Shader", this, "Glow", new String[] { "Glow", "Shadow" }));
    }
    
    @Override
    public void onEnable() {
        final Random rand = new Random();
        final float r = rand.nextFloat();
        final float g = rand.nextFloat();
        final float b = rand.nextFloat();
        this.randomColor = new Color(r, g, b);
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    public static void drawGlowArray(final Runnable runnable, final boolean renderTwice) {
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventRender2D) {
            if (ThemeScreen.themeLoaded) {
                if (ThemeScreen.themeRise) {
                    this.drawRiseBlur();
                }
                if (ThemeScreen.themeAqua2) {
                    drawRectsSuffix2();
                }
                if (ThemeScreen.themeTenacity) {
                    this.drawTenacityArraylistBlur();
                }
            }
            if (!ThemeScreen.themeLoaded) {
                if (Aqua.setmgr.getSetting("ArraylistFonts").getCurrentMode().equalsIgnoreCase("Comfortaa")) {
                    if (Aqua.setmgr.getSetting("ArraylistBlur").isState()) {
                        if (!Aqua.setmgr.getSetting("ArraylistBiggerOffset").isState()) {
                            this.drawShaderRectsBlur();
                        }
                        else {
                            this.drawShaderRectsBlurBiggerOffset();
                        }
                    }
                    if (Aqua.setmgr.getSetting("ArraylistGlowRects").isState()) {
                        if (!Aqua.setmgr.getSetting("ArraylistBiggerOffset").isState()) {
                            this.drawShaderRects();
                        }
                        else {
                            this.drawShaderRectsBiggerOffset();
                        }
                    }
                }
                if (Aqua.setmgr.getSetting("ArraylistFonts").getCurrentMode().equalsIgnoreCase("Minecraft")) {
                    if (Aqua.setmgr.getSetting("ArraylistBlur").isState()) {
                        this.drawShaderRectsMinecraftBlur();
                    }
                    if (Aqua.setmgr.getSetting("ArraylistGlowRects").isState()) {
                        this.drawShaderRectsMinecraft();
                    }
                }
            }
        }
        if (event instanceof EventPostRender2D) {
            if (ThemeScreen.themeHero) {
                this.drawHeroArraylist();
            }
            if (ThemeScreen.themeRise6) {
                this.drawRise6Arraylist();
            }
            if (ThemeScreen.themeAqua2) {
                drawRectsSuffix();
                this.drawStringsSuffix();
            }
            if (ThemeScreen.themeSigma) {
                this.drawSigmaArraylistBackground();
                this.drawSigmaArraylist();
            }
            if (ThemeScreen.themeTenacity) {
                this.drawTenacityArraylist();
            }
            if (ThemeScreen.themeJello) {
                this.drawJelloArraylist();
            }
            if (ThemeScreen.themeRise) {
                this.drawRiseArraylist();
            }
            if (ThemeScreen.themeXave) {
                this.drawXaveArray();
            }
            if (!ThemeScreen.themeLoaded) {
                if (Aqua.setmgr.getSetting("ArraylistFonts").getCurrentMode().equalsIgnoreCase("Comfortaa")) {
                    if (!Aqua.setmgr.getSetting("ArraylistBiggerOffset").isState()) {
                        this.drawRects2();
                    }
                    else {
                        this.drawRects2BiggerOffset();
                    }
                }
                if (Aqua.setmgr.getSetting("ArraylistFonts").getCurrentMode().equalsIgnoreCase("Minecraft")) {
                    this.drawRects2Minecraft();
                }
                if (Aqua.setmgr.getSetting("ArraylistFonts").getCurrentMode().equalsIgnoreCase("Comfortaa")) {
                    if (Aqua.setmgr.getSetting("ArraylistGlowStrings").isState()) {
                        if (!Aqua.setmgr.getSetting("ArraylistBiggerOffset").isState()) {
                            Shadow.drawGlow(() -> this.drawStrings(), false);
                        }
                    }
                    else {
                        Shadow.drawGlow(() -> this.drawStringsBiggerOffset(), false);
                    }
                }
                if (Aqua.setmgr.getSetting("ArraylistFonts").getCurrentMode().equalsIgnoreCase("Minecraft") && Aqua.setmgr.getSetting("ArraylistGlowStrings").isState()) {
                    Shadow.drawGlow(() -> this.drawMinecraftStrings(), false);
                }
                if (Aqua.setmgr.getSetting("ArraylistFonts").getCurrentMode().equalsIgnoreCase("Comfortaa")) {
                    if (!Aqua.setmgr.getSetting("ArraylistBiggerOffset").isState()) {
                        this.drawStrings();
                    }
                    else {
                        this.drawStringsBiggerOffset();
                    }
                }
                if (Aqua.setmgr.getSetting("ArraylistFonts").getCurrentMode().equalsIgnoreCase("Minecraft")) {
                    this.drawMinecraftStrings();
                }
            }
        }
    }
    
    public void drawShaderRectsBlur() {
        final ScaledResolution sr = new ScaledResolution(Arraylist.mc);
        int index = 0;
        float offset2 = 0.0f;
        GL11.glBlendFunc(770, 771);
        float offset3 = 0.0f;
        final List<Module> collect = Aqua.moduleManager.modules.stream().filter(Module::isToggled).sorted(Comparator.comparingInt(value -> (int)(-sr.getScaledWidth() - Aqua.INSTANCE.comfortaa3.getWidth(value.getName())))).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
        for (int i = 0, collectSize = collect.size(); i < collectSize; ++i) {
            final Module m1 = collect.get(i);
            Module nextModule = null;
            if (i < collectSize - 1) {
                nextModule = collect.get(i + 1);
            }
            if (m1.isToggled()) {
                final float wSet2 = sr.getScaledWidth() - Aqua.INSTANCE.comfortaa3.getWidth(m1.getName()) - 5.0f;
                ++index;
                final float finalOffset = offset3;
                final int finalIndex = index;
                final int rainbow = rainbow((int)offset3 * 9);
                final int color = Aqua.setmgr.getSetting("ArraylistReverseFade").isState() ? getGradientOffset(new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), new Color(this.getColor2()), index / 12.4).getRGB() : getGradientOffset(new Color(this.getColor2()), new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), index / 12.4).getRGB();
                final int finalColor = Aqua.setmgr.getSetting("ArraylistFade").isState() ? color : new Color(Aqua.setmgr.getSetting("HUDColor").getColor()).getRGB();
                final int doubleFinalColor = Aqua.setmgr.getSetting("ArraylistRainbow").isState() ? rainbow : finalColor;
                Gui.drawRect((int)(sr.getScaledWidth() - 5.0f), (int)(offset3 + 4.0f), sr.getScaledWidth() - 3, (int)(offset3 + 15.0f), doubleFinalColor);
                if (Aqua.moduleManager.getModuleByName("Blur").isToggled() && Aqua.setmgr.getSetting("ArraylistBlur").isState()) {
                    final float n;
                    final float n2;
                    Blur.drawBlurred(() -> Gui.drawRect2(n - 5.0f, n2 + 4.0f, sr.getScaledWidth() - 5, n2 + 15.0f, Integer.MIN_VALUE), false);
                }
                ++index;
                offset3 += 11.0f;
                ++offset2;
                GL11.glDisable(3042);
            }
        }
    }
    
    public void drawShaderRectsBlurBiggerOffset() {
        final ScaledResolution sr = new ScaledResolution(Arraylist.mc);
        int index = 0;
        float offset2 = 0.0f;
        GL11.glBlendFunc(770, 771);
        float offset3 = 0.0f;
        final List<Module> collect = Aqua.moduleManager.modules.stream().filter(Module::isToggled).sorted(Comparator.comparingInt(value -> (int)(-sr.getScaledWidth() - Aqua.INSTANCE.comfortaa3.getWidth(value.getName())))).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
        for (int i = 0, collectSize = collect.size(); i < collectSize; ++i) {
            final Module m1 = collect.get(i);
            Module nextModule = null;
            if (i < collectSize - 1) {
                nextModule = collect.get(i + 1);
            }
            if (m1.isToggled()) {
                final float wSet2 = sr.getScaledWidth() - Aqua.INSTANCE.comfortaa3.getWidth(m1.getName()) - 5.0f;
                ++index;
                final float finalOffset = offset3;
                final int finalIndex = index;
                final int rainbow = rainbow((int)offset3 * 9);
                final int color = Aqua.setmgr.getSetting("ArraylistReverseFade").isState() ? getGradientOffset(new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), new Color(this.getColor2()), index / 12.4).getRGB() : getGradientOffset(new Color(this.getColor2()), new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), index / 12.4).getRGB();
                final int finalColor = Aqua.setmgr.getSetting("ArraylistFade").isState() ? color : new Color(Aqua.setmgr.getSetting("HUDColor").getColor()).getRGB();
                final int doubleFinalColor = Aqua.setmgr.getSetting("ArraylistRainbow").isState() ? rainbow : finalColor;
                if (Aqua.moduleManager.getModuleByName("Blur").isToggled() && Aqua.setmgr.getSetting("ArraylistBlur").isState()) {
                    final float n;
                    final float n2;
                    Blur.drawBlurred(() -> Gui.drawRect2(n - 5.0f, n2 + 2.0f, sr.getScaledWidth() - 5, n2 + 16.0f, Integer.MIN_VALUE), false);
                }
                ++index;
                offset3 += 14.0f;
                ++offset2;
                GL11.glDisable(3042);
            }
        }
    }
    
    public void drawShaderRects() {
        final ScaledResolution sr = new ScaledResolution(Arraylist.mc);
        int index = 0;
        float offset2 = 0.0f;
        GL11.glBlendFunc(770, 771);
        float offset3 = 0.0f;
        final List<Module> collect = Aqua.moduleManager.modules.stream().filter(Module::isToggled).sorted(Comparator.comparingInt(value -> (int)(-sr.getScaledWidth() - Aqua.INSTANCE.comfortaa3.getWidth(value.getName())))).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
        for (int i = 0, collectSize = collect.size(); i < collectSize; ++i) {
            final Module m1 = collect.get(i);
            Module nextModule = null;
            if (i < collectSize - 1) {
                nextModule = collect.get(i + 1);
            }
            if (m1.isToggled()) {
                final float wSet2 = sr.getScaledWidth() - Aqua.INSTANCE.comfortaa3.getWidth(m1.getName()) - 5.0f;
                ++index;
                final float finalOffset = offset3;
                final int finalIndex = index;
                final int rainbow = rainbow((int)offset3 * 9);
                final int color = Aqua.setmgr.getSetting("ArraylistReverseFade").isState() ? getGradientOffset(new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), new Color(this.getColor2()), index / 12.4).getRGB() : getGradientOffset(new Color(this.getColor2()), new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), index / 12.4).getRGB();
                final int finalColor = Aqua.setmgr.getSetting("ArraylistFade").isState() ? color : new Color(Aqua.setmgr.getSetting("HUDColor").getColor()).getRGB();
                final int doubleFinalColor = Aqua.setmgr.getSetting("ArraylistRainbow").isState() ? rainbow : finalColor;
                if (Aqua.setmgr.getSetting("ArraylistShader").getCurrentMode().equalsIgnoreCase("Glow")) {
                    final float n;
                    final float n2;
                    ShaderMultiplier.drawGlowESP(() -> Gui.drawRect2(n - 5.0f, n2 + 4.0f, sr.getScaledWidth() - 5, n2 + 15.0f, new Color(doubleFinalColor).getRGB()), false);
                }
                else {
                    final float n3;
                    final float n4;
                    Shadow.drawGlow(() -> Gui.drawRect2(n3 - 5.0f, n4 + 4.0f, sr.getScaledWidth() - 5, n4 + 15.0f, Color.black.getRGB()), false);
                }
                if (Aqua.moduleManager.getModuleByName("Blur").isToggled() && Aqua.setmgr.getSetting("ArraylistBlur").isState()) {
                    final float n5;
                    final float n6;
                    Blur.drawBlurred(() -> Gui.drawRect2(n5 - 5.0f, n6 + 4.0f, sr.getScaledWidth() - 5, n6 + 15.0f, Integer.MIN_VALUE), false);
                }
                ++index;
                offset3 += 11.0f;
                ++offset2;
                GL11.glDisable(3042);
            }
        }
    }
    
    public void drawShaderRectsBiggerOffset() {
        final ScaledResolution sr = new ScaledResolution(Arraylist.mc);
        int index = 0;
        float offset2 = 0.0f;
        GL11.glBlendFunc(770, 771);
        float offset3 = 0.0f;
        final List<Module> collect = Aqua.moduleManager.modules.stream().filter(Module::isToggled).sorted(Comparator.comparingInt(value -> (int)(-sr.getScaledWidth() - Aqua.INSTANCE.comfortaa3.getWidth(value.getName())))).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
        for (int i = 0, collectSize = collect.size(); i < collectSize; ++i) {
            final Module m1 = collect.get(i);
            Module nextModule = null;
            if (i < collectSize - 1) {
                nextModule = collect.get(i + 1);
            }
            if (m1.isToggled()) {
                final float wSet2 = sr.getScaledWidth() - Aqua.INSTANCE.comfortaa3.getWidth(m1.getName()) - 5.0f;
                ++index;
                final float finalOffset = offset3;
                final int finalIndex = index;
                final int rainbow = rainbow((int)offset3 * 9);
                final int color = Aqua.setmgr.getSetting("ArraylistReverseFade").isState() ? getGradientOffset(new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), new Color(this.getColor2()), index / 12.4).getRGB() : getGradientOffset(new Color(this.getColor2()), new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), index / 12.4).getRGB();
                final int finalColor = Aqua.setmgr.getSetting("ArraylistFade").isState() ? color : new Color(Aqua.setmgr.getSetting("HUDColor").getColor()).getRGB();
                final int doubleFinalColor = Aqua.setmgr.getSetting("ArraylistRainbow").isState() ? rainbow : finalColor;
                if (Aqua.setmgr.getSetting("ArraylistShader").getCurrentMode().equalsIgnoreCase("Glow")) {
                    Gui.drawRect((int)(sr.getScaledWidth() - 5.0f), (int)(offset3 + 2.0f), sr.getScaledWidth() - 3, (int)(offset3 + 16.0f), doubleFinalColor);
                    final float n;
                    final float n2;
                    drawGlowArray(() -> Gui.drawRect2(n - 5.0f, n2 + 2.0f, sr.getScaledWidth() - 5, n2 + 16.0f, new Color(doubleFinalColor).getRGB()), false);
                }
                else {
                    final float finalOffset2 = offset3;
                    final ScaledResolution scaledResolution;
                    final float n3;
                    drawGlowArray(() -> Gui.drawRect((int)(scaledResolution.getScaledWidth() - 5.0f), (int)(n3 + 2.0f), scaledResolution.getScaledWidth() - 3, (int)(n3 + 16.0f), doubleFinalColor), false);
                    final float n4;
                    final float n5;
                    Shadow.drawGlow(() -> Gui.drawRect2(n4 - 5.0f, n5 + 2.0f, sr.getScaledWidth() - 5, n5 + 16.0f, Color.black.getRGB()), false);
                    Gui.drawRect((int)(sr.getScaledWidth() - 5.0f), (int)(offset3 + 2.0f), sr.getScaledWidth() - 3, (int)(offset3 + 16.0f), doubleFinalColor);
                }
                if (!Aqua.moduleManager.getModuleByName("Blur").isToggled() || Aqua.setmgr.getSetting("ArraylistBlur").isState()) {}
                ++index;
                offset3 += 14.0f;
                ++offset2;
                GL11.glDisable(3042);
            }
        }
    }
    
    public void drawShaderRectsMinecraftBlur() {
        final ScaledResolution sr = new ScaledResolution(Arraylist.mc);
        int index = 0;
        float offset2 = 0.0f;
        GL11.glBlendFunc(770, 771);
        float offset3 = 0.0f;
        final List<Module> collect = Aqua.moduleManager.modules.stream().filter(Module::isToggled).sorted(Comparator.comparingInt(value -> -sr.getScaledWidth() - Arraylist.mc.fontRendererObj.getStringWidth(value.getName()))).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
        for (int i = 0, collectSize = collect.size(); i < collectSize; ++i) {
            final Module m1 = collect.get(i);
            Module nextModule = null;
            if (i < collectSize - 1) {
                nextModule = collect.get(i + 1);
            }
            if (m1.isToggled()) {
                final float wSet2 = (float)(sr.getScaledWidth() - Arraylist.mc.fontRendererObj.getStringWidth(m1.getName()) - 5);
                ++index;
                final float finalOffset = offset3;
                final int finalIndex = index;
                final int rainbow = rainbow((int)offset3 * 9);
                final int color = Aqua.setmgr.getSetting("ArraylistReverseFade").isState() ? getGradientOffset(new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), new Color(this.getColor2()), index / 12.4).getRGB() : getGradientOffset(new Color(this.getColor2()), new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), index / 12.4).getRGB();
                final int finalColor = Aqua.setmgr.getSetting("ArraylistFade").isState() ? color : new Color(Aqua.setmgr.getSetting("HUDColor").getColor()).getRGB();
                final int doubleFinalColor = Aqua.setmgr.getSetting("ArraylistRainbow").isState() ? rainbow : finalColor;
                Gui.drawRect((int)(sr.getScaledWidth() - 5.0f), (int)(offset3 + 4.0f), sr.getScaledWidth() - 3, (int)(offset3 + 15.0f), doubleFinalColor);
                if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                    final float n;
                    final float n2;
                    Blur.drawBlurred(() -> Gui.drawRect2(n - 5.0f, n2 + 4.0f, sr.getScaledWidth() - 5, n2 + 15.0f, Integer.MIN_VALUE), false);
                }
                ++index;
                offset3 += 11.0f;
                ++offset2;
                GL11.glDisable(3042);
            }
        }
    }
    
    public void drawShaderRectsMinecraft() {
        final ScaledResolution sr = new ScaledResolution(Arraylist.mc);
        int index = 0;
        float offset2 = 0.0f;
        GL11.glBlendFunc(770, 771);
        float offset3 = 0.0f;
        final List<Module> collect = Aqua.moduleManager.modules.stream().filter(Module::isToggled).sorted(Comparator.comparingInt(value -> -sr.getScaledWidth() - Arraylist.mc.fontRendererObj.getStringWidth(value.getName()))).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
        for (int i = 0, collectSize = collect.size(); i < collectSize; ++i) {
            final Module m1 = collect.get(i);
            Module nextModule = null;
            if (i < collectSize - 1) {
                nextModule = collect.get(i + 1);
            }
            if (m1.isToggled()) {
                final float wSet2 = (float)(sr.getScaledWidth() - Arraylist.mc.fontRendererObj.getStringWidth(m1.getName()) - 5);
                ++index;
                final float finalOffset = offset3;
                final int finalIndex = index;
                final int rainbow = rainbow((int)offset3 * 9);
                final int color = Aqua.setmgr.getSetting("ArraylistReverseFade").isState() ? getGradientOffset(new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), new Color(this.getColor2()), index / 12.4).getRGB() : getGradientOffset(new Color(this.getColor2()), new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), index / 12.4).getRGB();
                final int finalColor = Aqua.setmgr.getSetting("ArraylistFade").isState() ? color : new Color(Aqua.setmgr.getSetting("HUDColor").getColor()).getRGB();
                final int doubleFinalColor = Aqua.setmgr.getSetting("ArraylistRainbow").isState() ? rainbow : finalColor;
                if (Aqua.setmgr.getSetting("ArraylistShader").getCurrentMode().equalsIgnoreCase("Glow")) {
                    final float n;
                    final float n2;
                    drawGlowArray(() -> Gui.drawRect2(n - 5.0f, n2 + 4.0f, sr.getScaledWidth() - 5, n2 + 15.0f, new Color(doubleFinalColor).getRGB()), false);
                }
                else {
                    final float n3;
                    final float n4;
                    Shadow.drawGlow(() -> Gui.drawRect2(n3 - 5.0f, n4 + 4.0f, sr.getScaledWidth() - 5, n4 + 15.0f, Color.black.getRGB()), false);
                }
                if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                    final float n5;
                    final float n6;
                    Blur.drawBlurred(() -> Gui.drawRect2(n5 - 5.0f, n6 + 4.0f, sr.getScaledWidth() - 5, n6 + 15.0f, Integer.MIN_VALUE), false);
                }
                ++index;
                offset3 += 11.0f;
                ++offset2;
                GL11.glDisable(3042);
            }
        }
    }
    
    public void drawRects2() {
        final ScaledResolution sr = new ScaledResolution(Arraylist.mc);
        int index = 0;
        float offset2 = 0.0f;
        GL11.glBlendFunc(770, 771);
        float offset3 = 0.0f;
        final List<Module> collect = Aqua.moduleManager.modules.stream().filter(Module::isToggled).sorted(Comparator.comparingInt(value -> (int)(-sr.getScaledWidth() - Aqua.INSTANCE.comfortaa3.getWidth(value.getName())))).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
        for (int i = 0, collectSize = collect.size(); i < collectSize; ++i) {
            final Module m1 = collect.get(i);
            Module nextModule = null;
            if (i < collectSize - 1) {
                nextModule = collect.get(i + 1);
            }
            if (m1.isToggled()) {
                final float wSet2 = sr.getScaledWidth() - Aqua.INSTANCE.comfortaa3.getWidth(m1.getName()) - 5.0f;
                ++index;
                final float finalOffset = offset3;
                final int alphaBackground = (int)Aqua.setmgr.getSetting("ArraylistAlpha").getCurrentNumber();
                if (Aqua.setmgr.getSetting("ArraylistBackground").isState() && !Aqua.setmgr.getSetting("ArraylistBackgroundFade").isState()) {
                    Gui.drawRect2(wSet2 - 5.0f, finalOffset + 4.0f, sr.getScaledWidth() - 5, finalOffset + 15.0f, new Color(0, 0, 0, alphaBackground).getRGB());
                }
                if (Aqua.setmgr.getSetting("ArraylistBackground").isState() && Aqua.setmgr.getSetting("ArraylistBackgroundFade").isState()) {
                    Gui.drawRect2(wSet2 - 5.0f, finalOffset + 4.0f, sr.getScaledWidth() - 5, finalOffset + 15.0f, new Color(0, 0, 0, alphaBackground).getRGB());
                    Gui.drawRect2(wSet2 - 5.0f, finalOffset + 4.0f, sr.getScaledWidth() - 5, finalOffset + 15.0f, ColorUtils.getColorAlpha(getGradientOffset(new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), new Color(this.getColor2()), index / 12.4).getRGB(), alphaBackground).getRGB());
                    ++index;
                }
                offset3 += 11.0f;
                ++offset2;
                GL11.glDisable(3042);
            }
        }
    }
    
    public void drawRects2BiggerOffset() {
        final ScaledResolution sr = new ScaledResolution(Arraylist.mc);
        int index = 0;
        float offset2 = 0.0f;
        GL11.glBlendFunc(770, 771);
        float offset3 = 0.0f;
        final List<Module> collect = Aqua.moduleManager.modules.stream().filter(Module::isToggled).sorted(Comparator.comparingInt(value -> (int)(-sr.getScaledWidth() - Aqua.INSTANCE.comfortaa3.getWidth(value.getName())))).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
        for (int i = 0, collectSize = collect.size(); i < collectSize; ++i) {
            final Module m1 = collect.get(i);
            Module nextModule = null;
            if (i < collectSize - 1) {
                nextModule = collect.get(i + 1);
            }
            if (m1.isToggled()) {
                final float wSet2 = sr.getScaledWidth() - Aqua.INSTANCE.comfortaa3.getWidth(m1.getName()) - 5.0f;
                ++index;
                final float finalOffset = offset3;
                final int alphaBackground = (int)Aqua.setmgr.getSetting("ArraylistAlpha").getCurrentNumber();
                if (Aqua.setmgr.getSetting("ArraylistBackground").isState()) {
                    Gui.drawRect2(wSet2 - 5.0f, finalOffset + 2.0f, sr.getScaledWidth() - 5, finalOffset + 16.0f, new Color(0, 0, 0, alphaBackground).getRGB());
                }
                offset3 += 14.0f;
                ++offset2;
                GL11.glDisable(3042);
            }
        }
    }
    
    public void drawRects2Minecraft() {
        final ScaledResolution sr = new ScaledResolution(Arraylist.mc);
        int index = 0;
        float offset2 = 0.0f;
        GL11.glBlendFunc(770, 771);
        float offset3 = 0.0f;
        final List<Module> collect = Aqua.moduleManager.modules.stream().filter(Module::isToggled).sorted(Comparator.comparingInt(value -> -sr.getScaledWidth() - Arraylist.mc.fontRendererObj.getStringWidth(value.getName()))).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
        for (int i = 0, collectSize = collect.size(); i < collectSize; ++i) {
            final Module m1 = collect.get(i);
            Module nextModule = null;
            if (i < collectSize - 1) {
                nextModule = collect.get(i + 1);
            }
            if (m1.isToggled()) {
                final float wSet2 = (float)(sr.getScaledWidth() - Arraylist.mc.fontRendererObj.getStringWidth(m1.getName()) - 5);
                ++index;
                final float finalOffset = offset3;
                final int alphaBackground = (int)Aqua.setmgr.getSetting("ArraylistAlpha").getCurrentNumber();
                if (Aqua.setmgr.getSetting("ArraylistBackground").isState()) {
                    Gui.drawRect2(wSet2 - 5.0f, finalOffset + 4.0f, sr.getScaledWidth() - 5, finalOffset + 15.0f, new Color(0, 0, 0, alphaBackground).getRGB());
                }
                ++index;
                offset3 += 11.0f;
                ++offset2;
                GL11.glDisable(3042);
            }
        }
    }
    
    public void drawMinecraftStrings() {
        final ScaledResolution sr = new ScaledResolution(Arraylist.mc);
        int index = 0;
        float offset2 = 0.0f;
        GL11.glBlendFunc(770, 771);
        float offset3 = 0.0f;
        final List<Module> collect = Aqua.moduleManager.modules.stream().filter(Module::isToggled).sorted(Comparator.comparingInt(value -> -sr.getScaledWidth() - Arraylist.mc.fontRendererObj.getStringWidth(value.getName()))).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
        for (int i = 0, collectSize = collect.size(); i < collectSize; ++i) {
            final Module m1 = collect.get(i);
            Module nextModule = null;
            if (i < collectSize - 1) {
                nextModule = collect.get(i + 1);
            }
            if (m1.isToggled()) {
                final float wSet2 = (float)(sr.getScaledWidth() - Arraylist.mc.fontRendererObj.getStringWidth(m1.getName()) - 5);
                final int rainbow = rainbow((int)offset3 * 9);
                final int color = Aqua.setmgr.getSetting("ArraylistReverseFade").isState() ? getGradientOffset(new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), new Color(this.getColor2()), index / 12.4).getRGB() : getGradientOffset(new Color(this.getColor2()), new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), index / 12.4).getRGB();
                final int finalColor = Aqua.setmgr.getSetting("ArraylistFade").isState() ? color : new Color(Aqua.setmgr.getSetting("HUDColor").getColor()).getRGB();
                final int doubleFinalColor = Aqua.setmgr.getSetting("ArraylistRainbow").isState() ? rainbow : finalColor;
                if (Aqua.setmgr.getSetting("ArraylistFontShadow").isState()) {
                    Arraylist.mc.fontRendererObj.drawStringWithShadow(m1.getName(), wSet2 - 2.0f, offset3 + 6.0f, doubleFinalColor);
                }
                else {
                    Arraylist.mc.fontRendererObj.drawString(m1.getName(), (int)(wSet2 - 2.0f), (int)(offset3 + 6.0f), doubleFinalColor);
                }
                ++index;
                ++index;
                offset3 += 11.0f;
                ++offset2;
                GL11.glDisable(3042);
            }
        }
    }
    
    public void drawStrings() {
        final ScaledResolution sr = new ScaledResolution(Arraylist.mc);
        int index = 0;
        float offset2 = 0.0f;
        GL11.glBlendFunc(770, 771);
        float offset3 = 0.0f;
        final List<Module> collect = Aqua.moduleManager.modules.stream().filter(Module::isToggled).sorted(Comparator.comparingInt(value -> (int)(-sr.getScaledWidth() - Aqua.INSTANCE.comfortaa3.getWidth(value.getName())))).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
        for (int i = 0, collectSize = collect.size(); i < collectSize; ++i) {
            final Module m1 = collect.get(i);
            Module nextModule = null;
            if (i < collectSize - 1) {
                nextModule = collect.get(i + 1);
            }
            if (m1.isToggled()) {
                m1.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax(Aqua.INSTANCE.comfortaa3.getWidth(m1.getName()) + 5.0f).setSpeed(25.0f).setReversed(!m1.isToggled()).update();
                final Animate setMin = m1.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                Aqua.INSTANCE.comfortaa3.getClass();
                setMin.setMax(9.0f).setSpeed(25.0f).setReversed(!m1.isToggled()).update();
                final float wSet2 = sr.getScaledWidth() - m1.anim.getValue();
                final int rainbow = rainbow((int)offset3 * 9);
                final int color = Aqua.setmgr.getSetting("ArraylistReverseFade").isState() ? getGradientOffset(new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), new Color(this.getColor2()), index / 12.4).getRGB() : getGradientOffset(new Color(this.getColor2()), new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), index / 12.4).getRGB();
                final int finalColor = Aqua.setmgr.getSetting("ArraylistFade").isState() ? color : new Color(Aqua.setmgr.getSetting("HUDColor").getColor()).getRGB();
                final int doubleFinalColor = Aqua.setmgr.getSetting("ArraylistRainbow").isState() ? rainbow : finalColor;
                final Color alphaFinalColor = ColorUtils.getColorAlpha(new Color(doubleFinalColor).getRGB(), (int)Aqua.setmgr.getSetting("ArraylistStringAlpha").getCurrentNumber());
                if (Aqua.setmgr.getSetting("ArraylistFontShadow").isState()) {
                    Aqua.INSTANCE.comfortaa3.drawStringWithShadow(m1.getName(), wSet2 - 3.0f, offset3 + 5.0f, alphaFinalColor.getRGB());
                }
                else {
                    Aqua.INSTANCE.comfortaa3.drawString(m1.getName(), wSet2 - 3.0f, offset3 + 5.0f, alphaFinalColor.getRGB());
                }
                ++index;
                ++index;
                offset3 += m1.anim2.getValue() + 2.0f;
                ++offset2;
                GL11.glDisable(3042);
            }
        }
    }
    
    public void drawStringsBiggerOffset() {
        final ScaledResolution sr = new ScaledResolution(Arraylist.mc);
        int index = 0;
        float offset2 = 0.0f;
        GL11.glBlendFunc(770, 771);
        float offset3 = 0.0f;
        final List<Module> collect = Aqua.moduleManager.modules.stream().filter(Module::isToggled).sorted(Comparator.comparingInt(value -> (int)(-sr.getScaledWidth() - Aqua.INSTANCE.comfortaa3.getWidth(value.getName())))).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
        for (int i = 0, collectSize = collect.size(); i < collectSize; ++i) {
            final Module m1 = collect.get(i);
            Module nextModule = null;
            if (i < collectSize - 1) {
                nextModule = collect.get(i + 1);
            }
            if (m1.isToggled()) {
                m1.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax(Aqua.INSTANCE.comfortaa3.getWidth(m1.getName()) + 5.0f).setSpeed(65.0f).setReversed(!m1.isToggled()).update();
                final Animate setMin = m1.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                Aqua.INSTANCE.comfortaa3.getClass();
                setMin.setMax(9.0f).setSpeed(25.0f).setReversed(!m1.isToggled()).update();
                final float wSet2 = sr.getScaledWidth() - m1.anim.getValue();
                final int rainbow = rainbow((int)offset3 * 9);
                final int color = Aqua.setmgr.getSetting("ArraylistReverseFade").isState() ? getGradientOffset(new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), new Color(this.getColor2()), index / 12.4).getRGB() : getGradientOffset(new Color(this.getColor2()), new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), index / 12.4).getRGB();
                final int finalColor = Aqua.setmgr.getSetting("ArraylistFade").isState() ? color : new Color(Aqua.setmgr.getSetting("HUDColor").getColor()).getRGB();
                final int doubleFinalColor = Aqua.setmgr.getSetting("ArraylistRainbow").isState() ? rainbow : finalColor;
                if (Aqua.setmgr.getSetting("ArraylistFontShadow").isState()) {
                    Aqua.INSTANCE.comfortaa3.drawStringWithShadow(m1.getName(), wSet2 - 3.0f, offset3 + 5.0f, doubleFinalColor);
                }
                else {
                    Aqua.INSTANCE.comfortaa3.drawString(m1.getName(), wSet2 - 3.0f, offset3 + 4.0f, doubleFinalColor);
                }
                ++index;
                ++index;
                offset3 += m1.anim2.getValue() + 5.0f;
                ++offset2;
                GL11.glDisable(3042);
            }
        }
    }
    
    public void drawRiseBlur() {
        final ScaledResolution sr = new ScaledResolution(Arraylist.mc);
        int index = 0;
        float offset2 = 0.0f;
        GL11.glBlendFunc(770, 771);
        float offset3 = 0.0f;
        final List<Module> collect = Aqua.moduleManager.modules.stream().filter(Module::isToggled).sorted(Comparator.comparingInt(value -> (int)(-sr.getScaledWidth() - Aqua.INSTANCE.comfortaa3.getWidth(value.getName())))).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
        for (int i = 0, collectSize = collect.size(); i < collectSize; ++i) {
            final Module m1 = collect.get(i);
            Module nextModule = null;
            if (i < collectSize - 1) {
                nextModule = collect.get(i + 1);
            }
            if (m1.isToggled()) {
                m1.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax(Aqua.INSTANCE.comfortaa3.getWidth(m1.getName()) + 5.0f).setSpeed(55.0f).setReversed(!m1.isToggled()).update();
                final Animate setMin = m1.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                Aqua.INSTANCE.comfortaa3.getClass();
                setMin.setMax(9.0f).setSpeed(55.0f).setReversed(!m1.isToggled()).update();
                final float wSet2 = sr.getScaledWidth() - Aqua.INSTANCE.rise.getWidth(m1.getName()) - 3.0f;
                GlStateManager.enableAlpha();
                final float finalOffset = offset3;
                if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                    final float n;
                    final float n2;
                    Blur.drawBlurred(() -> Gui.drawRect2(n - 8.0f, n2 + 7.2, sr.getScaledWidth() - 7, n2 + 19.0f, new Color(0, 0, 0, 200).getRGB()), false);
                }
                ++index;
                ++index;
                offset3 += m1.anim2.getValue() + 3.0f;
                ++offset2;
                GL11.glDisable(3042);
            }
        }
    }
    
    public void drawRiseArraylist() {
        final ScaledResolution sr = new ScaledResolution(Arraylist.mc);
        int index = 0;
        float offset2 = 0.0f;
        GL11.glBlendFunc(770, 771);
        float offset3 = 0.0f;
        final List<Module> collect = Aqua.moduleManager.modules.stream().filter(Module::isToggled).sorted(Comparator.comparingInt(value -> (int)(-sr.getScaledWidth() - Aqua.INSTANCE.rise.getWidth(value.getName())))).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
        for (int i = 0, collectSize = collect.size(); i < collectSize; ++i) {
            final Module m1 = collect.get(i);
            Module nextModule = null;
            if (i < collectSize - 1) {
                nextModule = collect.get(i + 1);
            }
            m1.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax(Aqua.INSTANCE.rise.getWidth(m1.getName()) + 5.0f).setSpeed(55.0f).setReversed(!m1.isToggled()).update();
            final Animate setMin = m1.anim2.setEase(Easing.LINEAR).setMin(0.0f);
            Aqua.INSTANCE.rise.getClass();
            setMin.setMax(9.0f).setSpeed(55.0f).setReversed(!m1.isToggled()).update();
            final float wSet2 = sr.getScaledWidth() - Aqua.INSTANCE.rise.getWidth(m1.getName()) - 3.0f;
            GlStateManager.enableAlpha();
            final float finalOffset = offset3;
            final int rainbow = rainbow((int)offset3 * 9);
            final int color = Aqua.setmgr.getSetting("ArraylistReverseFade").isState() ? getGradientOffset(new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), new Color(this.getColor2()), index / 12.4).getRGB() : getGradientOffset(new Color(this.getColor2()), new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), index / 12.4).getRGB();
            final int finalColor = Aqua.setmgr.getSetting("ArraylistFade").isState() ? color : new Color(Aqua.setmgr.getSetting("HUDColor").getColor()).getRGB();
            final int doubleFinalColor = Aqua.setmgr.getSetting("ArraylistRainbow").isState() ? rainbow : finalColor;
            final float n;
            final float n2;
            Shadow.drawGlow(() -> Gui.drawRect2(n - 8.0f, n2 + 7.2, sr.getScaledWidth() - 7, n2 + 19.0f, new Color(0, 0, 0, 200).getRGB()), false);
            Gui.drawRect2(wSet2 - 8.0f, finalOffset + 7.2, sr.getScaledWidth() - 7, finalOffset + 19.0f, new Color(0, 0, 0, 55).getRGB());
            Aqua.INSTANCE.rise.drawStringWithShadow(m1.getName(), wSet2 - 6.0f, offset3 + 8.6f, doubleFinalColor);
            ++index;
            ++index;
            offset3 += m1.anim2.getValue() + 3.0f;
            ++offset2;
            GL11.glDisable(3042);
        }
    }
    
    public void drawJelloArraylist() {
        final ScaledResolution sr = new ScaledResolution(Arraylist.mc);
        int index = 0;
        float offset2 = 0.0f;
        GL11.glBlendFunc(770, 771);
        float offset3 = 0.0f;
        final List<Module> collect = Aqua.moduleManager.modules.stream().filter(Module::isToggled).sorted(Comparator.comparingInt(value -> (int)(-sr.getScaledWidth() - Aqua.INSTANCE.jelloClickguiPanelBottom.getWidth(value.getName())))).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
        for (int i = 0, collectSize = collect.size(); i < collectSize; ++i) {
            final Module m1 = collect.get(i);
            Module nextModule = null;
            if (i < collectSize - 1) {
                nextModule = collect.get(i + 1);
            }
            if (m1.isToggled()) {
                m1.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax(Aqua.INSTANCE.jelloClickguiPanelBottom.getWidth(m1.getName()) + 5.0f).setSpeed(55.0f).setReversed(!m1.isToggled()).update();
                final Animate setMin = m1.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                Aqua.INSTANCE.jelloClickguiPanelBottom.getClass();
                setMin.setMax(9.0f).setSpeed(55.0f).setReversed(!m1.isToggled()).update();
                final float wSet2 = sr.getScaledWidth() - m1.anim.getValue();
                GlStateManager.enableAlpha();
                final float finalOffset = offset3;
                Shadow.drawGlow(() -> Aqua.INSTANCE.jelloClickguiPanelBottom.drawString(m1.getName(), wSet2 + 2.0f, finalOffset + 5.0f, Color.gray.getRGB()), false);
                Aqua.INSTANCE.jelloClickguiPanelBottom.drawString(m1.getName(), wSet2 + 2.0f, offset3 + 5.0f, new Color(250, 250, 250).getRGB());
                ++index;
                ++index;
                offset3 += m1.anim2.getValue() + 3.0f;
                ++offset2;
                GL11.glDisable(3042);
            }
        }
    }
    
    public void drawRise6Arraylist() {
        final ScaledResolution sr = new ScaledResolution(Arraylist.mc);
        int index = 0;
        float offset2 = 0.0f;
        GL11.glBlendFunc(770, 771);
        final ByteBuffer pixelBuf = ByteBuffer.allocateDirect(4);
        final int listSize = (int)Aqua.moduleManager.modules.stream().filter(Module::isToggled).count();
        final float n;
        final UnicodeFontRenderer comfortaa4;
        String s;
        final List<Module> collect = Aqua.moduleManager.modules.stream().filter(Module::isToggled).sorted(Comparator.comparingInt(value -> {
            n = (float)(-sr.getScaledWidth());
            comfortaa4 = Aqua.INSTANCE.comfortaa4;
            if (value.getMode().isEmpty()) {
                s = value.getName();
            }
            else {
                s = String.format("%s%s%s", value.getName(), EnumChatFormatting.WHITE, value.getMode() + "-");
            }
            return (int)(n - comfortaa4.getWidth(s));
        })).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
        for (int i = 0, collectSize = collect.size(); i < collectSize; ++i) {
            final Module m = collect.get(i);
            Module nextModule = null;
            if (i < collectSize - 1) {
                nextModule = collect.get(i + 1);
            }
            final float wSet = sr.getScaledWidth() - Aqua.INSTANCE.comfortaa4.getWidth(m.getName()) - 3.0f;
            final float wSet2 = sr.getScaledWidth() - Aqua.INSTANCE.comfortaa4.getWidth(m.getName()) - 5.0f;
            float wSetNext = (float)sr.getScaledWidth();
            if (nextModule != null) {
                wSetNext = sr.getScaledWidth() - Aqua.INSTANCE.comfortaa4.getWidth(nextModule.getName()) - 5.0f;
            }
            final float finalOffset = offset2;
            final float finalWSetNext = wSetNext;
            final int finalIndex = index;
        }
        float offset3 = 0.0f;
        final Iterator<Module> iterator = (Aqua.setmgr.getSetting("ArraylistSort").isState() ? collect : Aqua.moduleManager.modules).iterator();
        while (iterator.hasNext()) {
            final Module m = iterator.next();
            if (!m.isToggled()) {
                continue;
            }
            final float wSet3 = (float)sr.getScaledWidth();
            final float wSet4 = sr.getScaledWidth() - Aqua.INSTANCE.comfortaa4.getWidth(m.getMode().isEmpty() ? m.getName() : String.format("%s%s%s", m.getName(), EnumChatFormatting.GRAY, m.getMode() + "-")) - 7.0f;
            final int rainbow = rainbow((int)offset3 * 9);
            final int color = Aqua.setmgr.getSetting("ArraylistReverseFade").isState() ? getGradientOffset(new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), new Color(this.getColor2()), index / 12.4).getRGB() : getGradientOffset(new Color(this.getColor2()), new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), index / 12.4).getRGB();
            final int finalColor = Aqua.setmgr.getSetting("ArraylistFade").isState() ? color : new Color(Aqua.setmgr.getSetting("HUDColor").getColor()).getRGB();
            final int doubleFinalColor = Aqua.setmgr.getSetting("ArraylistRainbow").isState() ? rainbow : finalColor;
            final Color alphaFinalColor = ColorUtils.getColorAlpha(new Color(doubleFinalColor).getRGB(), (int)Aqua.setmgr.getSetting("ArraylistStringAlpha").getCurrentNumber());
            final float finalOffset2 = offset3;
            final UnicodeFontRenderer comfortaa5;
            final Module module;
            String text;
            final float n2;
            final float n3;
            final Color color2;
            ShaderMultiplier.drawGlowESP(() -> {
                comfortaa5 = Aqua.INSTANCE.comfortaa4;
                if (module.getMode().isEmpty()) {
                    text = module.getName();
                }
                else {
                    text = String.format("%s %s%s", module.getName(), EnumChatFormatting.WHITE, module.getMode());
                }
                comfortaa5.drawString(text, (float)(int)(n2 - 5.0f), (float)(int)(n3 + 12.0f), color2.getRGB());
                return;
            }, false);
            Aqua.INSTANCE.comfortaa4.drawString(m.getMode().isEmpty() ? m.getName() : String.format("%s %s%s", m.getName(), EnumChatFormatting.WHITE, m.getMode()), (float)(int)(wSet4 - 5.0f), (float)(int)(offset3 + 12.0f), alphaFinalColor.getRGB());
            ++index;
            offset3 += 11.0f;
        }
        ++index;
        ++index;
        ++offset2;
        GL11.glDisable(3042);
    }
    
    public void drawSigmaArraylist() {
        final ScaledResolution sr = new ScaledResolution(Arraylist.mc);
        int index = 0;
        float offset2 = 0.0f;
        GL11.glBlendFunc(770, 771);
        final ByteBuffer pixelBuf = ByteBuffer.allocateDirect(4);
        final int listSize = (int)Aqua.moduleManager.modules.stream().filter(Module::isToggled).count();
        final float n;
        final UnicodeFontRenderer4 sigma;
        String s;
        final List<Module> collect = Aqua.moduleManager.modules.stream().filter(Module::isToggled).sorted(Comparator.comparingInt(value -> {
            n = (float)(-sr.getScaledWidth());
            sigma = Aqua.INSTANCE.sigma;
            if (value.getMode().isEmpty()) {
                s = value.getName();
            }
            else {
                s = String.format("%s%s%s", value.getName(), EnumChatFormatting.WHITE, value.getMode() + "-");
            }
            return (int)(n - sigma.getWidth(s));
        })).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
        for (int i = 0, collectSize = collect.size(); i < collectSize; ++i) {
            final Module m = collect.get(i);
            Module nextModule = null;
            if (i < collectSize - 1) {
                nextModule = collect.get(i + 1);
            }
            final float wSet = sr.getScaledWidth() - Aqua.INSTANCE.sigma.getWidth(m.getName()) - 3.0f;
            final float wSet2 = sr.getScaledWidth() - Aqua.INSTANCE.sigma.getWidth(m.getName()) - 5.0f;
            float wSetNext = (float)sr.getScaledWidth();
            if (nextModule != null) {
                wSetNext = sr.getScaledWidth() - Aqua.INSTANCE.sigma.getWidth(nextModule.getName()) - 5.0f;
            }
            final float finalOffset = offset2;
            final float finalWSetNext = wSetNext;
            final int finalIndex = index;
        }
        float offset3 = 0.0f;
        final Iterator<Module> iterator = (Aqua.setmgr.getSetting("ArraylistSort").isState() ? collect : Aqua.moduleManager.modules).iterator();
        while (iterator.hasNext()) {
            final Module m = iterator.next();
            if (!m.isToggled()) {
                continue;
            }
            final float wSet3 = (float)sr.getScaledWidth();
            final float wSet4 = sr.getScaledWidth() - Aqua.INSTANCE.sigma.getWidth(m.getMode().isEmpty() ? m.getName() : String.format("%s%s%s", m.getName(), EnumChatFormatting.GRAY, m.getMode() + "-")) - 7.0f;
            Aqua.INSTANCE.sigma.drawString(m.getMode().isEmpty() ? m.getName() : String.format("%s %s%s", m.getName(), EnumChatFormatting.GRAY, m.getMode()), (float)(int)(wSet4 + 6.0f), (float)(int)(offset3 + 2.0f), rainbowSigma((int)(offset3 * 6.0f)));
            offset3 += 11.0f;
        }
        ++index;
        ++index;
        ++offset2;
        GL11.glDisable(3042);
    }
    
    public void drawTenacityArraylist() {
        final ScaledResolution sr = new ScaledResolution(Arraylist.mc);
        int index = 0;
        float offset2 = 0.0f;
        GL11.glBlendFunc(770, 771);
        final ByteBuffer pixelBuf = ByteBuffer.allocateDirect(4);
        final int listSize = (int)Aqua.moduleManager.modules.stream().filter(Module::isToggled).count();
        final float n;
        final UnicodeFontRenderer9 tenacityNormal;
        String s;
        final List<Module> collect = Aqua.moduleManager.modules.stream().filter(Module::isToggled).sorted(Comparator.comparingInt(value -> {
            n = (float)(-sr.getScaledWidth());
            tenacityNormal = Aqua.INSTANCE.tenacityNormal;
            if (value.getMode().isEmpty()) {
                s = value.getName();
            }
            else {
                s = String.format("%s%s%s", value.getName(), EnumChatFormatting.WHITE, value.getMode() + "-");
            }
            return (int)(n - tenacityNormal.getWidth(s));
        })).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
        for (int i = 0, collectSize = collect.size(); i < collectSize; ++i) {
            final Module m = collect.get(i);
            Module nextModule = null;
            if (i < collectSize - 1) {
                nextModule = collect.get(i + 1);
            }
            final float wSet = sr.getScaledWidth() - Aqua.INSTANCE.tenacityNormal.getWidth(m.getName()) - 3.0f;
            final float wSet2 = sr.getScaledWidth() - Aqua.INSTANCE.tenacityNormal.getWidth(m.getName()) - 5.0f;
            float wSetNext = (float)sr.getScaledWidth();
            if (nextModule != null) {
                wSetNext = sr.getScaledWidth() - Aqua.INSTANCE.tenacityNormal.getWidth(nextModule.getName()) - 5.0f;
            }
            final float finalOffset = offset2;
            final float finalWSetNext = wSetNext;
            final int finalIndex = index;
        }
        float offset3 = 0.0f;
        final Iterator<Module> iterator = (Aqua.setmgr.getSetting("ArraylistSort").isState() ? collect : Aqua.moduleManager.modules).iterator();
        while (iterator.hasNext()) {
            final Module m = iterator.next();
            if (!m.isToggled()) {
                continue;
            }
            final float wSet3 = (float)sr.getScaledWidth();
            final float wSet4 = sr.getScaledWidth() - Aqua.INSTANCE.tenacityNormal.getWidth(m.getMode().isEmpty() ? m.getName() : String.format("%s%s%s", m.getName(), EnumChatFormatting.GRAY, m.getMode() + "-")) - 7.0f;
            final int rainbow = rainbow((int)offset3 * 9);
            final int color = Aqua.setmgr.getSetting("ArraylistReverseFade").isState() ? getGradientOffset(new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), new Color(this.getColor2()), index / 12.4).getRGB() : getGradientOffset(new Color(this.getColor2()), new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), index / 12.4).getRGB();
            final int finalColor = Aqua.setmgr.getSetting("ArraylistFade").isState() ? color : new Color(Aqua.setmgr.getSetting("HUDColor").getColor()).getRGB();
            final int doubleFinalColor = Aqua.setmgr.getSetting("ArraylistRainbow").isState() ? rainbow : finalColor;
            final Color alphaFinalColor = ColorUtils.getColorAlpha(new Color(doubleFinalColor).getRGB(), (int)Aqua.setmgr.getSetting("ArraylistStringAlpha").getCurrentNumber());
            final float finalOffset2 = offset3;
            final float n2;
            final float n3;
            Shadow.drawGlow(() -> Gui.drawRect2(n2 + 3.0f, n3, sr.getScaledWidth(), n3 + 11.0f, alphaFinalColor.getRGB()), false);
            Gui.drawRect2(wSet4 + 3.0f, offset3, sr.getScaledWidth(), offset3 + 11.0f, new Color(0, 0, 0, 40).getRGB());
            Aqua.INSTANCE.tenacityNormal.drawStringWithShadow(m.getMode().isEmpty() ? m.getName() : String.format("%s %s%s", m.getName(), EnumChatFormatting.GRAY, m.getMode()), (float)(int)(wSet4 + 5.0f), (float)(int)(offset3 - 1.5f), alphaFinalColor.getRGB());
            ++index;
            offset3 += 11.0f;
        }
        ++index;
        ++index;
        ++offset2;
        GL11.glDisable(3042);
    }
    
    public void drawTenacityArraylistBlur() {
        final ScaledResolution sr = new ScaledResolution(Arraylist.mc);
        int index = 0;
        float offset2 = 0.0f;
        GL11.glBlendFunc(770, 771);
        final ByteBuffer pixelBuf = ByteBuffer.allocateDirect(4);
        final int listSize = (int)Aqua.moduleManager.modules.stream().filter(Module::isToggled).count();
        final float n;
        final UnicodeFontRenderer9 tenacityNormal;
        String s;
        final List<Module> collect = Aqua.moduleManager.modules.stream().filter(Module::isToggled).sorted(Comparator.comparingInt(value -> {
            n = (float)(-sr.getScaledWidth());
            tenacityNormal = Aqua.INSTANCE.tenacityNormal;
            if (value.getMode().isEmpty()) {
                s = value.getName();
            }
            else {
                s = String.format("%s%s%s", value.getName(), EnumChatFormatting.WHITE, value.getMode() + "-");
            }
            return (int)(n - tenacityNormal.getWidth(s));
        })).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
        for (int i = 0, collectSize = collect.size(); i < collectSize; ++i) {
            final Module m = collect.get(i);
            Module nextModule = null;
            if (i < collectSize - 1) {
                nextModule = collect.get(i + 1);
            }
            final float wSet = sr.getScaledWidth() - Aqua.INSTANCE.tenacityNormal.getWidth(m.getName()) - 3.0f;
            final float wSet2 = sr.getScaledWidth() - Aqua.INSTANCE.tenacityNormal.getWidth(m.getName()) - 5.0f;
            float wSetNext = (float)sr.getScaledWidth();
            if (nextModule != null) {
                wSetNext = sr.getScaledWidth() - Aqua.INSTANCE.tenacityNormal.getWidth(nextModule.getName()) - 5.0f;
            }
            final float finalOffset = offset2;
            final float finalWSetNext = wSetNext;
            final int finalIndex = index;
        }
        float offset3 = 0.0f;
        final Iterator<Module> iterator = (Aqua.setmgr.getSetting("ArraylistSort").isState() ? collect : Aqua.moduleManager.modules).iterator();
        while (iterator.hasNext()) {
            final Module m = iterator.next();
            if (!m.isToggled()) {
                continue;
            }
            final float wSet3 = (float)sr.getScaledWidth();
            final float wSet4 = sr.getScaledWidth() - Aqua.INSTANCE.tenacityNormal.getWidth(m.getMode().isEmpty() ? m.getName() : String.format("%s%s%s", m.getName(), EnumChatFormatting.GRAY, m.getMode() + "-")) - 7.0f;
            final int rainbow = rainbow((int)offset3 * 9);
            final int color = Aqua.setmgr.getSetting("ArraylistReverseFade").isState() ? getGradientOffset(new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), new Color(this.getColor2()), index / 12.4).getRGB() : getGradientOffset(new Color(this.getColor2()), new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), index / 12.4).getRGB();
            final int finalColor = Aqua.setmgr.getSetting("ArraylistFade").isState() ? color : new Color(Aqua.setmgr.getSetting("HUDColor").getColor()).getRGB();
            final int doubleFinalColor = Aqua.setmgr.getSetting("ArraylistRainbow").isState() ? rainbow : finalColor;
            final Color alphaFinalColor = ColorUtils.getColorAlpha(new Color(doubleFinalColor).getRGB(), (int)Aqua.setmgr.getSetting("ArraylistStringAlpha").getCurrentNumber());
            final float finalOffset2 = offset3;
            final float n2;
            final float n3;
            Blur.drawBlurred(() -> Gui.drawRect2(n2 + 3.0f, n3, sr.getScaledWidth(), n3 + 11.0f, alphaFinalColor.getRGB()), false);
            ++index;
            offset3 += 11.0f;
        }
        ++index;
        ++index;
        ++offset2;
        GL11.glDisable(3042);
    }
    
    public void drawSigmaArraylistBackground() {
        final ScaledResolution sr = new ScaledResolution(Arraylist.mc);
        int index = 0;
        float offset2 = 0.0f;
        GL11.glBlendFunc(770, 771);
        final ByteBuffer pixelBuf = ByteBuffer.allocateDirect(4);
        final int listSize = (int)Aqua.moduleManager.modules.stream().filter(Module::isToggled).count();
        final float n;
        final UnicodeFontRenderer4 sigma;
        String s;
        final List<Module> collect = Aqua.moduleManager.modules.stream().filter(Module::isToggled).sorted(Comparator.comparingInt(value -> {
            n = (float)(-sr.getScaledWidth());
            sigma = Aqua.INSTANCE.sigma;
            if (value.getMode().isEmpty()) {
                s = value.getName();
            }
            else {
                s = String.format("%s%s%s", value.getName(), EnumChatFormatting.WHITE, value.getMode() + "-");
            }
            return (int)(n - sigma.getWidth(s));
        })).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
        for (int i = 0, collectSize = collect.size(); i < collectSize; ++i) {
            final Module m = collect.get(i);
            Module nextModule = null;
            if (i < collectSize - 1) {
                nextModule = collect.get(i + 1);
            }
            final float wSet = sr.getScaledWidth() - Aqua.INSTANCE.sigma.getWidth(m.getName()) - 3.0f;
            final float wSet2 = sr.getScaledWidth() - Aqua.INSTANCE.sigma.getWidth(m.getName()) - 5.0f;
            float wSetNext = (float)sr.getScaledWidth();
            if (nextModule != null) {
                wSetNext = sr.getScaledWidth() - Aqua.INSTANCE.sigma.getWidth(nextModule.getName()) - 5.0f;
            }
            final float finalOffset = offset2;
            final float finalWSetNext = wSetNext;
            final int finalIndex = index;
        }
        float offset3 = 0.0f;
        final Iterator<Module> iterator = (Aqua.setmgr.getSetting("ArraylistSort").isState() ? collect : Aqua.moduleManager.modules).iterator();
        while (iterator.hasNext()) {
            final Module m = iterator.next();
            if (!m.isToggled()) {
                continue;
            }
            final float wSet3 = (float)sr.getScaledWidth();
            final float wSet4 = sr.getScaledWidth() - Aqua.INSTANCE.sigma.getWidth(m.getMode().isEmpty() ? m.getName() : String.format("%s%s%s", m.getName(), EnumChatFormatting.GRAY, m.getMode() + "-")) - 7.0f;
            final float finalOffset2 = offset3;
            final float finalOffset3 = offset3;
            final float finalOffset4 = offset3;
            Gui.drawRect2(wSet4 + 4.0f, offset3, sr.getScaledWidth(), offset3 + 11.0f, new Color(0, 0, 0, 90).getRGB());
            offset3 += 11.0f;
        }
        ++index;
        ++index;
        ++offset2;
        GL11.glDisable(3042);
    }
    
    public void drawXaveArray() {
        final ScaledResolution sr = new ScaledResolution(Arraylist.mc);
        int index = 0;
        float offset2 = 0.0f;
        GL11.glBlendFunc(770, 771);
        final ByteBuffer pixelBuf = ByteBuffer.allocateDirect(4);
        final int listSize = (int)Aqua.moduleManager.modules.stream().filter(Module::isToggled).count();
        final float n;
        final UnicodeFontRenderer7 verdana2;
        String s;
        final List<Module> collect = Aqua.moduleManager.modules.stream().filter(Module::isToggled).sorted(Comparator.comparingInt(value -> {
            n = (float)(-sr.getScaledWidth());
            verdana2 = Aqua.INSTANCE.verdana2;
            if (value.getMode().isEmpty()) {
                s = value.getName();
            }
            else {
                s = String.format("%s%s%s", value.getName(), EnumChatFormatting.WHITE, value.getMode() + "-");
            }
            return (int)(n - verdana2.getWidth(s));
        })).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
        for (int i = 0, collectSize = collect.size(); i < collectSize; ++i) {
            final Module m = collect.get(i);
            Module nextModule = null;
            if (i < collectSize - 1) {
                nextModule = collect.get(i + 1);
            }
            final float wSet = sr.getScaledWidth() - Aqua.INSTANCE.verdana2.getWidth(m.getName()) - 3.0f;
            final float wSet2 = sr.getScaledWidth() - Aqua.INSTANCE.verdana2.getWidth(m.getName()) - 5.0f;
            float wSetNext = (float)sr.getScaledWidth();
            if (nextModule != null) {
                wSetNext = sr.getScaledWidth() - Aqua.INSTANCE.verdana2.getWidth(nextModule.getName()) - 5.0f;
            }
            final float finalOffset = offset2;
            final float finalWSetNext = wSetNext;
            final int finalIndex = index;
        }
        float offset3 = 0.0f;
        final Iterator<Module> iterator = (Aqua.setmgr.getSetting("ArraylistSort").isState() ? collect : Aqua.moduleManager.modules).iterator();
        while (iterator.hasNext()) {
            final Module m = iterator.next();
            if (!m.isToggled()) {
                continue;
            }
            final float wSet3 = (float)sr.getScaledWidth();
            final float wSet4 = sr.getScaledWidth() - Aqua.INSTANCE.verdana2.getWidth(m.getMode().isEmpty() ? m.getName() : String.format("%s%s%s", m.getName(), EnumChatFormatting.GRAY, m.getMode() + "-")) - 7.0f;
            Gui.drawRect2(wSet4 + 4.0f, offset3, sr.getScaledWidth(), offset3 + 11.0f, new Color(40, 40, 40, 100).getRGB());
            Aqua.INSTANCE.verdana2.drawStringWithShadow(m.getMode().isEmpty() ? m.getName() : String.format("%s %s%s", m.getName(), EnumChatFormatting.GRAY, m.getMode()), (float)(int)(wSet4 + 6.0f), (float)(int)offset3, m.oneTimeColor3);
            offset3 += 11.0f;
        }
        ++index;
        ++index;
        ++offset2;
        GL11.glDisable(3042);
    }
    
    public void drawHeroArraylist() {
        final ScaledResolution sr = new ScaledResolution(Arraylist.mc);
        int index = 0;
        float offset2 = 0.0f;
        GL11.glBlendFunc(770, 771);
        float offset3 = 0.0f;
        final List<Module> collect = (List<Module>)(Aqua.setmgr.getSetting("ArraylistSort").isState() ? ((List<? super Object>)Aqua.moduleManager.modules.stream().filter(Module::isToggled).sorted(Comparator.comparingInt(value -> (int)(-sr.getScaledWidth() - Aqua.INSTANCE.roboto2.getWidth(value.getName())))).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList())) : Aqua.moduleManager.modules);
        for (int i = 0, collectSize = collect.size(); i < collectSize; ++i) {
            final Module m1 = collect.get(i);
            Module nextModule = null;
            if (i < collectSize - 1) {
                nextModule = collect.get(i + 1);
            }
            if (m1.isToggled()) {
                m1.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax(Aqua.INSTANCE.roboto2.getWidth(m1.getName()) + 5.0f).setSpeed(25.0f).setReversed(!m1.isToggled()).update();
                final Animate setMin = m1.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                Aqua.INSTANCE.roboto2.getClass();
                setMin.setMax(9.0f).setSpeed(25.0f).setReversed(!m1.isToggled()).update();
                final float wSet2 = sr.getScaledWidth() - m1.anim.getValue();
                GlStateManager.enableAlpha();
                final float finalOffset = offset3;
                Gui.drawRect((int)(sr.getScaledWidth() - 2.0f), (int)offset3, sr.getScaledWidth() + 2, (int)(offset3 + 11.0f), m1.heroColor);
                Gui.drawRect((int)wSet2, (int)offset3, sr.getScaledWidth() + 2, (int)(offset3 + 11.0f), new Color(12, 12, 12, 130).getRGB());
                Aqua.INSTANCE.roboto2.drawString(m1.getName(), wSet2 + 1.0f, offset3 - 1.5f, m1.heroColor);
                Gui.drawRect2(wSet2, offset3, wSet2 - 1.0f, offset3 + 11.0f, new Color(12, 12, 12, 170).getRGB());
                ++index;
                ++index;
                offset3 += m1.anim2.getValue() + 2.0f;
                ++offset2;
                GL11.glDisable(3042);
            }
        }
    }
    
    public void drawRects() {
        final ScaledResolution sr = new ScaledResolution(Arraylist.mc);
        int index = 0;
        float offset2 = 0.0f;
        GL11.glBlendFunc(770, 771);
        final int listSize = (int)Aqua.moduleManager.modules.stream().filter(Module::isToggled).count();
        final List<Module> collect = Aqua.moduleManager.modules.stream().filter(Module::isToggled).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
        for (int i = 0, collectSize = collect.size(); i < collectSize; ++i) {
            final Module m = collect.get(i);
            Module nextModule = null;
            if (i < collectSize - 1) {
                nextModule = collect.get(i + 1);
            }
            final float wSet2 = sr.getScaledWidth() - Aqua.INSTANCE.comfortaa3.getWidth(m.getName()) - 5.0f;
            float wSetNext = (float)sr.getScaledWidth();
            if (nextModule != null) {
                wSetNext = sr.getScaledWidth() - Aqua.INSTANCE.comfortaa3.getWidth(nextModule.getName()) - 5.0f;
            }
            final float finalWSetNext1 = wSetNext;
            final int finalIndex1 = index;
            final float finalOffset3 = offset2;
            final float finalOffset4 = offset2;
            final ScaledResolution scaledResolution;
            final float n;
            drawGlowArray(() -> Gui.drawRect((int)(scaledResolution.getScaledWidth() - 5.0f), (int)(n + 4.0f), scaledResolution.getScaledWidth() - 3, (int)(n + 15.0f), Aqua.setmgr.getSetting("HUDColor").getColor()), false);
            Gui.drawRect((int)(sr.getScaledWidth() - 5.0f), (int)(offset2 + 4.0f), sr.getScaledWidth() - 3, (int)(offset2 + 15.0f), Aqua.setmgr.getSetting("HUDColor").getColor());
            ++index;
            offset2 += 11.0f;
        }
        GL11.glDisable(3042);
    }
    
    public static void drawRectsSuffix() {
        final ScaledResolution sr = new ScaledResolution(Arraylist.mc);
        int index = 0;
        float offset2 = 0.0f;
        GL11.glBlendFunc(770, 771);
        final float n;
        final UnicodeFontRenderer comfortaa3;
        String s;
        final List<Module> collect = Aqua.moduleManager.modules.stream().filter(Module::isToggled).sorted(Comparator.comparingInt(value -> {
            n = (float)(-sr.getScaledWidth());
            comfortaa3 = Aqua.INSTANCE.comfortaa3;
            if (value.getMode().isEmpty()) {
                s = value.getName();
            }
            else {
                s = String.format("%s%s%s", value.getName(), EnumChatFormatting.WHITE, value.getMode() + "-");
            }
            return (int)(n - comfortaa3.getWidth(s));
        })).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
        final int listSize = collect.size();
        for (int i = 0, collectSize = collect.size(); i < collectSize; ++i) {
            final Module m = collect.get(i);
            Module nextModule = null;
            if (i < collectSize - 1) {
                nextModule = collect.get(i + 1);
            }
            final float wSet = sr.getScaledWidth() - Aqua.INSTANCE.comfortaa3.getWidth(m.getName()) - 3.0f;
            final float wSet2 = sr.getScaledWidth() - Aqua.INSTANCE.comfortaa3.getWidth(m.getMode().isEmpty() ? m.getName() : String.format("%s%s%s", m.getName(), EnumChatFormatting.GRAY, m.getMode() + "-")) - 7.0f;
            float wSetNext = (float)sr.getScaledWidth();
            if (nextModule != null) {
                wSetNext = sr.getScaledWidth() - Aqua.INSTANCE.comfortaa3.getWidth(nextModule.getName()) - 5.0f;
            }
            final float finalOffset = offset2;
            final float finalWSetNext = wSetNext;
            final int finalIndex = index;
            final float finalOffset2 = offset2;
            final float finalOffset3 = offset2;
            final float finalWSetNext2 = wSetNext;
            final int finalIndex2 = index;
            final float finalOffset4 = offset2;
            final int rainbow = 0;
            final int color = Aqua.setmgr.getSetting("ArraylistReverseFade").isState() ? getGradientOffset(new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), new Color(Aqua.setmgr.getSetting("ArraylistColor").getColor()), index / 12.4).getRGB() : getGradientOffset(new Color(Aqua.setmgr.getSetting("ArraylistColor").getColor()), new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), index / 12.4).getRGB();
            final int finalColor = Aqua.setmgr.getSetting("ArraylistFade").isState() ? color : new Color(Aqua.setmgr.getSetting("HUDColor").getColor()).getRGB();
            final int doubleFinalColor = Aqua.setmgr.getSetting("ArraylistRainbow").isState() ? rainbow : finalColor;
            final Color alphaFinalColor = ColorUtils.getColorAlpha(new Color(doubleFinalColor).getRGB(), (int)Aqua.setmgr.getSetting("ArraylistStringAlpha").getCurrentNumber());
            final Object o;
            final float n2;
            final ScaledResolution scaledResolution;
            final Object o2;
            final int n3;
            ShaderMultiplier.drawGlowESP(() -> RenderUtil.drawRoundedRect3((double)(o - 2.0f), n2 + 5.0f, (double)((float)scaledResolution.getScaledWidth() - o - 2.0f), 11.0, Math.min(3.0f, (float)(o2 - o)), n3 == 0, n3 == 0, n3 == listSize, true, new Color(20, 20, 20, 230)), false);
            RenderUtil.drawRoundedRect3(wSet2 - 2.0f, finalOffset4 + 5.0f, sr.getScaledWidth() - wSet2 - 2.0f, 11.0, Math.min(3.0f, finalWSetNext2 - wSet2), finalIndex2 == 0, finalIndex2 == 0, finalIndex2 == listSize, true, new Color(20, 20, 20, 70));
            ++index;
            offset2 += 11.0f;
        }
        float offset3 = 0.0f;
        final Iterator<Module> iterator = Aqua.moduleManager.modules.iterator();
        while (iterator.hasNext()) {
            final Module m = iterator.next();
            if (!m.isToggled()) {
                continue;
            }
            final float wSet3 = (float)sr.getScaledWidth();
            final float wSet4 = sr.getScaledWidth() - Aqua.INSTANCE.comfortaa3.getWidth(m.getName()) - 5.0f;
            ++index;
            ++index;
            ++offset3;
            ++offset2;
        }
        GL11.glDisable(3042);
    }
    
    public static void drawRectsSuffix2() {
        final ScaledResolution sr = new ScaledResolution(Arraylist.mc);
        int index = 0;
        float offset2 = 0.0f;
        GL11.glBlendFunc(770, 771);
        final float n;
        final UnicodeFontRenderer comfortaa3;
        String s;
        final List<Module> collect = Aqua.moduleManager.modules.stream().filter(Module::isToggled).sorted(Comparator.comparingInt(value -> {
            n = (float)(-sr.getScaledWidth());
            comfortaa3 = Aqua.INSTANCE.comfortaa3;
            if (value.getMode().isEmpty()) {
                s = value.getName();
            }
            else {
                s = String.format("%s%s%s", value.getName(), EnumChatFormatting.WHITE, value.getMode() + "-");
            }
            return (int)(n - comfortaa3.getWidth(s));
        })).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
        final int listSize = collect.size();
        for (int i = 0, collectSize = collect.size(); i < collectSize; ++i) {
            final Module m = collect.get(i);
            Module nextModule = null;
            if (i < collectSize - 1) {
                nextModule = collect.get(i + 1);
            }
            final float wSet = sr.getScaledWidth() - Aqua.INSTANCE.comfortaa3.getWidth(m.getName()) - 3.0f;
            final float wSet2 = sr.getScaledWidth() - Aqua.INSTANCE.comfortaa3.getWidth(m.getMode().isEmpty() ? m.getName() : String.format("%s%s%s", m.getName(), EnumChatFormatting.GRAY, m.getMode() + "-")) - 7.0f;
            float wSetNext = (float)sr.getScaledWidth();
            if (nextModule != null) {
                wSetNext = sr.getScaledWidth() - Aqua.INSTANCE.comfortaa3.getWidth(nextModule.getName()) - 5.0f;
            }
            final float finalOffset = offset2;
            final float finalWSetNext = wSetNext;
            final int finalIndex = index;
            final float finalOffset2 = offset2;
            final float finalOffset3 = offset2;
            final float finalWSetNext2 = wSetNext;
            final int finalIndex2 = index;
            final float finalOffset4 = offset2;
            final int rainbow = 0;
            final Object o;
            final float n2;
            final ScaledResolution scaledResolution;
            final Object o2;
            final int n3;
            Blur.drawBlurred(() -> RenderUtil.drawRoundedRect3((double)(o - 2.0f), n2 + 5.0f, (double)((float)scaledResolution.getScaledWidth() - o - 2.0f), 11.0, Math.min(3.0f, (float)(o2 - o)), n3 == 0, n3 == 0, n3 == listSize, true, Color.black), false);
            ++index;
            offset2 += 11.0f;
        }
        float offset3 = 0.0f;
        final Iterator<Module> iterator = Aqua.moduleManager.modules.iterator();
        while (iterator.hasNext()) {
            final Module m = iterator.next();
            if (!m.isToggled()) {
                continue;
            }
            final float wSet3 = (float)sr.getScaledWidth();
            final float wSet4 = sr.getScaledWidth() - Aqua.INSTANCE.comfortaa3.getWidth(m.getName()) - 5.0f;
            ++index;
            ++index;
            ++offset3;
            ++offset2;
        }
        GL11.glDisable(3042);
    }
    
    public void drawStringsSuffix() {
        final ScaledResolution sr = new ScaledResolution(Arraylist.mc);
        int index = 0;
        float offset2 = 0.0f;
        GL11.glBlendFunc(770, 771);
        final ByteBuffer pixelBuf = ByteBuffer.allocateDirect(4);
        final int listSize = (int)Aqua.moduleManager.modules.stream().filter(Module::isToggled).count();
        final float n;
        final UnicodeFontRenderer comfortaa3;
        String s;
        final List<Module> collect = Aqua.moduleManager.modules.stream().filter(Module::isToggled).sorted(Comparator.comparingInt(value -> {
            n = (float)(-sr.getScaledWidth());
            comfortaa3 = Aqua.INSTANCE.comfortaa3;
            if (value.getMode().isEmpty()) {
                s = value.getName();
            }
            else {
                s = String.format("%s%s%s", value.getName(), EnumChatFormatting.WHITE, value.getMode() + "-");
            }
            return (int)(n - comfortaa3.getWidth(s));
        })).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
        for (int i = 0, collectSize = collect.size(); i < collectSize; ++i) {
            final Module m = collect.get(i);
            Module nextModule = null;
            if (i < collectSize - 1) {
                nextModule = collect.get(i + 1);
            }
            final float wSet = sr.getScaledWidth() - Aqua.INSTANCE.comfortaa3.getWidth(m.getName()) - 3.0f;
            final float wSet2 = sr.getScaledWidth() - Aqua.INSTANCE.comfortaa3.getWidth(m.getName()) - 5.0f;
            float wSetNext = (float)sr.getScaledWidth();
            if (nextModule != null) {
                wSetNext = sr.getScaledWidth() - Aqua.INSTANCE.comfortaa3.getWidth(nextModule.getName()) - 5.0f;
            }
            final float finalOffset = offset2;
            final float finalWSetNext = wSetNext;
            final int finalIndex = index;
        }
        float offset3 = 0.0f;
        final Iterator<Module> iterator = collect.iterator();
        while (iterator.hasNext()) {
            final Module m = iterator.next();
            if (!m.isToggled()) {
                continue;
            }
            final float wSet3 = (float)sr.getScaledWidth();
            final float wSet4 = sr.getScaledWidth() - Aqua.INSTANCE.comfortaa3.getWidth(m.getMode().isEmpty() ? m.getName() : String.format("%s%s%s", m.getName(), EnumChatFormatting.GRAY, m.getMode() + "-")) - 7.0f;
            final int rainbow = rainbow((int)offset3 * 9);
            final int color = Aqua.setmgr.getSetting("ArraylistReverseFade").isState() ? getGradientOffset(new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), new Color(this.getColor2()), index / 12.4).getRGB() : getGradientOffset(new Color(this.getColor2()), new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), index / 12.4).getRGB();
            final int finalColor = Aqua.setmgr.getSetting("ArraylistFade").isState() ? color : new Color(Aqua.setmgr.getSetting("HUDColor").getColor()).getRGB();
            final int doubleFinalColor = Aqua.setmgr.getSetting("ArraylistRainbow").isState() ? rainbow : finalColor;
            final Color alphaFinalColor = ColorUtils.getColorAlpha(new Color(doubleFinalColor).getRGB(), (int)Aqua.setmgr.getSetting("ArraylistStringAlpha").getCurrentNumber());
            final float finalOffset2 = offset3;
            final UnicodeFontRenderer comfortaa4;
            final Module module;
            String text;
            final float n2;
            final float n3;
            final Color color2;
            Shadow.drawGlow(() -> {
                comfortaa4 = Aqua.INSTANCE.comfortaa3;
                if (module.getMode().isEmpty()) {
                    text = module.getName();
                }
                else {
                    text = String.format("%s %s%s", module.getName(), EnumChatFormatting.GRAY, module.getMode());
                }
                comfortaa4.drawString(text, n2 + 0.5f, n3 + 5.0f, color2.getRGB());
                return;
            }, false);
            Aqua.INSTANCE.comfortaa3.drawString(m.getMode().isEmpty() ? m.getName() : String.format("%s %s%s", m.getName(), EnumChatFormatting.GRAY, m.getMode()), wSet4 + 0.5f, offset3 + 5.0f, alphaFinalColor.getRGB());
            offset3 += 11.0f;
            ++index;
        }
        ++index;
        ++offset2;
        GL11.glDisable(3042);
    }
    
    private void drawTexturedQuad1(final int texture, final double width, final double height) {
        GlStateManager.enableBlend();
        GL11.glBindTexture(3553, texture);
        GL11.glBegin(7);
        GL11.glTexCoord2d(0.0, 1.0);
        GL11.glVertex2d(0.0, 0.0);
        GL11.glTexCoord2d(0.0, 0.0);
        GL11.glVertex2d(0.0, height);
        GL11.glTexCoord2d(1.0, 0.0);
        GL11.glVertex2d(width, height);
        GL11.glTexCoord2d(1.0, 1.0);
        GL11.glVertex2d(width, 0.0);
        GL11.glEnd();
    }
    
    public static Color getGradientOffset(final Color color1, final Color color2, final double index) {
        double offs = Math.abs(System.currentTimeMillis() / 13L) / 60.0 + index;
        if (offs > 1.0) {
            final double left = offs % 1.0;
            final int off = (int)offs;
            offs = ((off % 2 == 0) ? left : (1.0 - left));
        }
        final double inverse_percent = 1.0 - offs;
        final int redPart = (int)(color1.getRed() * inverse_percent + color2.getRed() * offs);
        final int greenPart = (int)(color1.getGreen() * inverse_percent + color2.getGreen() * offs);
        final int bluePart = (int)(color1.getBlue() * inverse_percent + color2.getBlue() * offs);
        return new Color(redPart, greenPart, bluePart);
    }
    
    public int getColor2() {
        try {
            return Aqua.setmgr.getSetting("ArraylistColor").getColor();
        }
        catch (Exception e) {
            return Color.white.getRGB();
        }
    }
    
    public static int rainbow(final int delay) {
        double rainbowState = Math.ceil((double)((System.currentTimeMillis() + delay) / 7L));
        rainbowState %= 360.0;
        return Color.getHSBColor((float)(rainbowState / 360.0), 0.9f, 1.0f).getRGB();
    }
    
    public static int rainbowSigma(final int delay) {
        double rainbowState = Math.ceil((double)((System.currentTimeMillis() + delay) / 9L));
        rainbowState %= 360.0;
        return Color.getHSBColor((float)(rainbowState / 360.0), 0.5f, 1.0f).getRGB();
    }
}
