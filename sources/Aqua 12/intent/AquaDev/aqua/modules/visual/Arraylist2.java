// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.visual;

import intent.AquaDev.aqua.utils.RenderUtil;
import intent.AquaDev.aqua.utils.UnicodeFontRenderer7;
import intent.AquaDev.aqua.utils.UnicodeFontRenderer4;
import intent.AquaDev.aqua.fr.lavache.anime.Animate;
import net.minecraft.client.renderer.GlStateManager;
import intent.AquaDev.aqua.fr.lavache.anime.Easing;
import net.minecraft.client.gui.FontRenderer;
import intent.AquaDev.aqua.utils.UnicodeFontRenderer9;
import intent.AquaDev.aqua.utils.UnicodeFontRenderer;
import java.util.Iterator;
import intent.AquaDev.aqua.utils.UnicodeFontRenderer6;
import net.minecraft.client.gui.Gui;
import java.awt.Color;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Comparator;
import net.minecraft.util.EnumChatFormatting;
import java.util.List;
import java.nio.ByteBuffer;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.gui.ScaledResolution;
import events.listeners.EventRender2D;
import intent.AquaDev.aqua.gui.novolineOld.themesScreen.ThemeScreen;
import events.listeners.EventPostRender2D;
import events.Event;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.modules.Module;

public class Arraylist2 extends Module
{
    public Arraylist2() {
        super("Arraylist", Type.Visual, "Arraylist", 0, Category.Visual);
        Aqua.setmgr.register(new Setting("Fade", this, true));
        Aqua.setmgr.register(new Setting("Rainbow", this, false));
        Aqua.setmgr.register(new Setting("Bloom", this, true));
        Aqua.setmgr.register(new Setting("Sort", this, true));
        Aqua.setmgr.register(new Setting("Background", this, true));
        Aqua.setmgr.register(new Setting("BloomMode", this, "Glow", new String[] { "Glow", "Shadow" }));
        Aqua.setmgr.register(new Setting("SuffixMode", this, "-", new String[] { "-", "<>" }));
        Aqua.setmgr.register(new Setting("Fonts", this, "Arial", new String[] { "Comforta", "Arial", "Tenacity", "MC" }));
        Aqua.setmgr.register(new Setting("Color", this));
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventPostRender2D) {
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
        }
        if (e instanceof EventRender2D) {
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
                final String currentMode = Aqua.setmgr.getSetting("ArraylistFonts").getCurrentMode();
                switch (currentMode) {
                    case "Arial": {
                        final ScaledResolution sr = new ScaledResolution(Arraylist2.mc);
                        int index = 0;
                        final float offset2 = 0.0f;
                        GL11.glBlendFunc(770, 771);
                        final ByteBuffer pixelBuf = ByteBuffer.allocateDirect(4);
                        float n2;
                        UnicodeFontRenderer6 novoline;
                        String s;
                        float n3;
                        UnicodeFontRenderer6 novoline2;
                        String s2;
                        final List<Module> collect = (List<Module>)(Aqua.setmgr.getSetting("ArraylistSuffixMode").getCurrentMode().equalsIgnoreCase("-") ? Aqua.moduleManager.modules.stream().filter(Module::isToggled).sorted(Comparator.comparingInt(value -> {
                            n2 = (float)(-sr.getScaledWidth());
                            novoline = Aqua.INSTANCE.novoline;
                            if (value.getMode().isEmpty()) {
                                s = value.getName();
                            }
                            else {
                                s = String.format("%s %s- %s", value.getName(), EnumChatFormatting.WHITE, value.getMode() + "");
                            }
                            return (int)(n2 - novoline.getWidth(s));
                        })).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()) : Aqua.moduleManager.modules.stream().filter(Module::isToggled).sorted(Comparator.comparingInt(value -> {
                            n3 = (float)(-sr.getScaledWidth());
                            novoline2 = Aqua.INSTANCE.novoline;
                            if (value.getMode().isEmpty()) {
                                s2 = value.getName();
                            }
                            else {
                                s2 = String.format("%s %s<%s>", value.getName(), EnumChatFormatting.WHITE, value.getMode() + "");
                            }
                            return (int)(n3 - novoline2.getWidth(s2));
                        })).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
                        for (int i = 0, collectSize = collect.size(); i < collectSize; ++i) {
                            final Module m = collect.get(i);
                            Module nextModule = null;
                            if (i < collectSize - 1) {
                                nextModule = collect.get(i + 1);
                            }
                            float wSetNext = (float)sr.getScaledWidth();
                            if (nextModule != null) {
                                wSetNext = sr.getScaledWidth() - Aqua.INSTANCE.novoline.getWidth(nextModule.getName()) - 5.0f;
                            }
                        }
                        float offset3 = 0.0f;
                        final Iterator<Module> iterator = collect.iterator();
                        while (iterator.hasNext()) {
                            final Module m = iterator.next();
                            if (!m.isToggled()) {
                                continue;
                            }
                            final float wSet2 = Aqua.setmgr.getSetting("ArraylistSuffixMode").getCurrentMode().equalsIgnoreCase("-") ? (sr.getScaledWidth() - Aqua.INSTANCE.novoline.getWidth(m.getMode().isEmpty() ? m.getName() : String.format("%s %s- %s", m.getName(), EnumChatFormatting.GRAY, m.getMode() + ""))) : (sr.getScaledWidth() - Aqua.INSTANCE.novoline.getWidth(m.getMode().isEmpty() ? m.getName() : String.format("%s %s<%s>", m.getName(), EnumChatFormatting.GRAY, m.getMode() + "")));
                            if (Aqua.setmgr.getSetting("ArraylistBackground").isState()) {
                                Gui.drawRect2(wSet2 - 5.0f, offset3, sr.getScaledWidth(), offset3 + 11.0f, new Color(0, 0, 0, 90).getRGB());
                            }
                            final int rainbow = Arraylist.rainbow((int)offset3 * 9);
                            final int color = Arraylist.getGradientOffset(new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), new Color(this.getArraylistColor()), index / 12.4).getRGB();
                            final int finalColor = Aqua.setmgr.getSetting("ArraylistFade").isState() ? color : new Color(Aqua.setmgr.getSetting("HUDColor").getColor()).getRGB();
                            final int doubleFinalColor = Aqua.setmgr.getSetting("ArraylistRainbow").isState() ? rainbow : finalColor;
                            if (Aqua.setmgr.getSetting("ArraylistBloom").isState()) {
                                final String currentMode2 = Aqua.setmgr.getSetting("ArraylistBloomMode").getCurrentMode();
                                switch (currentMode2) {
                                    case "Glow": {
                                        final float finalOffset = offset3;
                                        final float finalOffset2 = offset3;
                                        if (Aqua.moduleManager.getModuleByName("ShaderMultiplier").isToggled()) {
                                            ShaderMultiplier.drawGlowESP(() -> Gui.drawRect2(wSet2 - 5.0f, finalOffset, sr.getScaledWidth(), finalOffset2 + 11.0f, doubleFinalColor), false);
                                            break;
                                        }
                                        break;
                                    }
                                    case "Shadow": {
                                        final float finalOffset = offset3;
                                        final float finalOffset2 = offset3;
                                        if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {
                                            final float finalOffset3;
                                            final float finalOffset4;
                                            Shadow.drawGlow(() -> Gui.drawRect2(wSet2 - 5.0f, finalOffset3, sr.getScaledWidth(), finalOffset4 + 11.0f, new Color(0, 0, 0, 255).getRGB()), false);
                                            break;
                                        }
                                        break;
                                    }
                                }
                            }
                            final String currentMode3 = Aqua.setmgr.getSetting("ArraylistSuffixMode").getCurrentMode();
                            switch (currentMode3) {
                                case "-": {
                                    Aqua.INSTANCE.novoline.drawString(m.getMode().isEmpty() ? m.getName() : String.format("%s %s- %s", m.getName(), EnumChatFormatting.GRAY, m.getMode()), wSet2 - 3.0f, offset3, doubleFinalColor);
                                    break;
                                }
                                case "<>": {
                                    Aqua.INSTANCE.novoline.drawString(m.getMode().isEmpty() ? m.getName() : String.format("%s %s<%s>", m.getName(), EnumChatFormatting.GRAY, m.getMode()), wSet2 - 3.0f, offset3, doubleFinalColor);
                                    break;
                                }
                            }
                            ++index;
                            offset3 += 11.0f;
                        }
                        break;
                    }
                    case "Comforta": {
                        final ScaledResolution sr = new ScaledResolution(Arraylist2.mc);
                        int index = 0;
                        final float offset2 = 0.0f;
                        GL11.glBlendFunc(770, 771);
                        final ByteBuffer pixelBuf = ByteBuffer.allocateDirect(4);
                        final ScaledResolution sr2;
                        float n6;
                        UnicodeFontRenderer comfortaa3;
                        String s3;
                        float n7;
                        UnicodeFontRenderer comfortaa4;
                        String s4;
                        final List<Module> collect = (List<Module>)(Aqua.setmgr.getSetting("ArraylistSuffixMode").getCurrentMode().equalsIgnoreCase("-") ? Aqua.moduleManager.modules.stream().filter(Module::isToggled).sorted(Comparator.comparingInt(value -> {
                            n6 = (float)(-sr2.getScaledWidth());
                            comfortaa3 = Aqua.INSTANCE.comfortaa3;
                            if (value.getMode().isEmpty()) {
                                s3 = value.getName();
                            }
                            else {
                                s3 = String.format("%s %s- %s", value.getName(), EnumChatFormatting.WHITE, value.getMode() + "");
                            }
                            return (int)(n6 - comfortaa3.getWidth(s3));
                        })).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()) : Aqua.moduleManager.modules.stream().filter(Module::isToggled).sorted(Comparator.comparingInt(value -> {
                            n7 = (float)(-sr2.getScaledWidth());
                            comfortaa4 = Aqua.INSTANCE.comfortaa3;
                            if (value.getMode().isEmpty()) {
                                s4 = value.getName();
                            }
                            else {
                                s4 = String.format("%s %s<%s>", value.getName(), EnumChatFormatting.WHITE, value.getMode() + "");
                            }
                            return (int)(n7 - comfortaa4.getWidth(s4));
                        })).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
                        for (int i = 0, collectSize = collect.size(); i < collectSize; ++i) {
                            final Module m = collect.get(i);
                            Module nextModule = null;
                            if (i < collectSize - 1) {
                                nextModule = collect.get(i + 1);
                            }
                            float wSetNext = (float)sr.getScaledWidth();
                            if (nextModule != null) {
                                wSetNext = sr.getScaledWidth() - Aqua.INSTANCE.novoline.getWidth(nextModule.getName()) - 5.0f;
                            }
                        }
                        float offset3 = 0.0f;
                        final Iterator<Module> iterator2 = collect.iterator();
                        while (iterator2.hasNext()) {
                            final Module m = iterator2.next();
                            if (!m.isToggled()) {
                                continue;
                            }
                            final float wSet2 = Aqua.setmgr.getSetting("ArraylistSuffixMode").getCurrentMode().equalsIgnoreCase("-") ? (sr.getScaledWidth() - Aqua.INSTANCE.comfortaa3.getWidth(m.getMode().isEmpty() ? m.getName() : String.format("%s %s- %s", m.getName(), EnumChatFormatting.GRAY, m.getMode() + ""))) : (sr.getScaledWidth() - Aqua.INSTANCE.comfortaa3.getWidth(m.getMode().isEmpty() ? m.getName() : String.format("%s %s<%s>", m.getName(), EnumChatFormatting.GRAY, m.getMode() + "")));
                            if (Aqua.setmgr.getSetting("ArraylistBackground").isState()) {
                                Gui.drawRect2(wSet2 - 5.0f, offset3, sr.getScaledWidth(), offset3 + 11.0f, new Color(0, 0, 0, 90).getRGB());
                            }
                            final int rainbow = Arraylist.rainbow((int)offset3 * 9);
                            final int color = Arraylist.getGradientOffset(new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), new Color(this.getArraylistColor()), index / 12.4).getRGB();
                            final int finalColor = Aqua.setmgr.getSetting("ArraylistFade").isState() ? color : new Color(Aqua.setmgr.getSetting("HUDColor").getColor()).getRGB();
                            final int doubleFinalColor = Aqua.setmgr.getSetting("ArraylistRainbow").isState() ? rainbow : finalColor;
                            if (Aqua.setmgr.getSetting("ArraylistBloom").isState()) {
                                final String currentMode4 = Aqua.setmgr.getSetting("ArraylistBloomMode").getCurrentMode();
                                switch (currentMode4) {
                                    case "Glow": {
                                        final float finalOffset = offset3;
                                        final float finalOffset2 = offset3;
                                        if (Aqua.moduleManager.getModuleByName("ShaderMultiplier").isToggled()) {
                                            final float wSet3;
                                            final float finalOffset5;
                                            final float finalOffset6;
                                            final int doubleFinalColor2;
                                            ShaderMultiplier.drawGlowESP(() -> Gui.drawRect2(wSet3 - 5.0f, finalOffset5, sr2.getScaledWidth(), finalOffset6 + 11.0f, doubleFinalColor2), false);
                                            break;
                                        }
                                        break;
                                    }
                                    case "Shadow": {
                                        final float finalOffset = offset3;
                                        final float finalOffset2 = offset3;
                                        if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {
                                            final float wSet3;
                                            final float finalOffset7;
                                            final float finalOffset8;
                                            Shadow.drawGlow(() -> Gui.drawRect2(wSet3 - 5.0f, finalOffset7, sr2.getScaledWidth(), finalOffset8 + 11.0f, new Color(0, 0, 0, 255).getRGB()), false);
                                            break;
                                        }
                                        break;
                                    }
                                }
                            }
                            final String currentMode5 = Aqua.setmgr.getSetting("ArraylistSuffixMode").getCurrentMode();
                            switch (currentMode5) {
                                case "-": {
                                    Aqua.INSTANCE.comfortaa3.drawString(m.getMode().isEmpty() ? m.getName() : String.format("%s %s- %s", m.getName(), EnumChatFormatting.GRAY, m.getMode()), wSet2 - 3.0f, offset3, doubleFinalColor);
                                    break;
                                }
                                case "<>": {
                                    Aqua.INSTANCE.comfortaa3.drawString(m.getMode().isEmpty() ? m.getName() : String.format("%s %s<%s>", m.getName(), EnumChatFormatting.GRAY, m.getMode()), wSet2 - 3.0f, offset3, doubleFinalColor);
                                    break;
                                }
                            }
                            ++index;
                            offset3 += 11.0f;
                        }
                        break;
                    }
                    case "Tenacity": {
                        final ScaledResolution sr = new ScaledResolution(Arraylist2.mc);
                        int index = 0;
                        final float offset2 = 0.0f;
                        GL11.glBlendFunc(770, 771);
                        final ByteBuffer pixelBuf = ByteBuffer.allocateDirect(4);
                        final ScaledResolution sr3;
                        float n10;
                        UnicodeFontRenderer9 tenacityNormal;
                        String s5;
                        float n11;
                        UnicodeFontRenderer9 tenacityNormal2;
                        String s6;
                        final List<Module> collect = (List<Module>)(Aqua.setmgr.getSetting("ArraylistSuffixMode").getCurrentMode().equalsIgnoreCase("-") ? Aqua.moduleManager.modules.stream().filter(Module::isToggled).sorted(Comparator.comparingInt(value -> {
                            n10 = (float)(-sr3.getScaledWidth());
                            tenacityNormal = Aqua.INSTANCE.tenacityNormal;
                            if (value.getMode().isEmpty()) {
                                s5 = value.getName();
                            }
                            else {
                                s5 = String.format("%s %s- %s", value.getName(), EnumChatFormatting.WHITE, value.getMode() + "");
                            }
                            return (int)(n10 - tenacityNormal.getWidth(s5));
                        })).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()) : Aqua.moduleManager.modules.stream().filter(Module::isToggled).sorted(Comparator.comparingInt(value -> {
                            n11 = (float)(-sr3.getScaledWidth());
                            tenacityNormal2 = Aqua.INSTANCE.tenacityNormal;
                            if (value.getMode().isEmpty()) {
                                s6 = value.getName();
                            }
                            else {
                                s6 = String.format("%s %s<%s>", value.getName(), EnumChatFormatting.WHITE, value.getMode() + "");
                            }
                            return (int)(n11 - tenacityNormal2.getWidth(s6));
                        })).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
                        for (int i = 0, collectSize = collect.size(); i < collectSize; ++i) {
                            final Module m = collect.get(i);
                            Module nextModule = null;
                            if (i < collectSize - 1) {
                                nextModule = collect.get(i + 1);
                            }
                            float wSetNext = (float)sr.getScaledWidth();
                            if (nextModule != null) {
                                wSetNext = sr.getScaledWidth() - Aqua.INSTANCE.tenacityNormal.getWidth(nextModule.getName()) - 5.0f;
                            }
                        }
                        float offset3 = 0.0f;
                        final Iterator<Module> iterator3 = collect.iterator();
                        while (iterator3.hasNext()) {
                            final Module m = iterator3.next();
                            if (!m.isToggled()) {
                                continue;
                            }
                            final float wSet2 = Aqua.setmgr.getSetting("ArraylistSuffixMode").getCurrentMode().equalsIgnoreCase("-") ? (sr.getScaledWidth() - Aqua.INSTANCE.tenacityNormal.getWidth(m.getMode().isEmpty() ? m.getName() : String.format("%s %s- %s", m.getName(), EnumChatFormatting.GRAY, m.getMode() + ""))) : (sr.getScaledWidth() - Aqua.INSTANCE.tenacityNormal.getWidth(m.getMode().isEmpty() ? m.getName() : String.format("%s %s<%s>", m.getName(), EnumChatFormatting.GRAY, m.getMode() + "")));
                            if (Aqua.setmgr.getSetting("ArraylistBackground").isState()) {
                                Gui.drawRect2(wSet2 - 5.0f, offset3, sr.getScaledWidth(), offset3 + 11.0f, new Color(0, 0, 0, 90).getRGB());
                            }
                            final int rainbow = Arraylist.rainbow((int)offset3 * 9);
                            final int color = Arraylist.getGradientOffset(new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), new Color(this.getArraylistColor()), index / 12.4).getRGB();
                            final int finalColor = Aqua.setmgr.getSetting("ArraylistFade").isState() ? color : new Color(Aqua.setmgr.getSetting("HUDColor").getColor()).getRGB();
                            final int doubleFinalColor = Aqua.setmgr.getSetting("ArraylistRainbow").isState() ? rainbow : finalColor;
                            if (Aqua.setmgr.getSetting("ArraylistBloom").isState()) {
                                final String currentMode6 = Aqua.setmgr.getSetting("ArraylistBloomMode").getCurrentMode();
                                switch (currentMode6) {
                                    case "Glow": {
                                        final float finalOffset = offset3;
                                        final float finalOffset2 = offset3;
                                        if (Aqua.moduleManager.getModuleByName("ShaderMultiplier").isToggled()) {
                                            final float wSet4;
                                            final float finalOffset9;
                                            final float finalOffset10;
                                            final int doubleFinalColor3;
                                            ShaderMultiplier.drawGlowESP(() -> Gui.drawRect2(wSet4 - 5.0f, finalOffset9, sr3.getScaledWidth(), finalOffset10 + 11.0f, doubleFinalColor3), false);
                                            break;
                                        }
                                        break;
                                    }
                                    case "Shadow": {
                                        final float finalOffset = offset3;
                                        final float finalOffset2 = offset3;
                                        if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {
                                            final float wSet4;
                                            final float finalOffset11;
                                            final float finalOffset12;
                                            Shadow.drawGlow(() -> Gui.drawRect2(wSet4 - 5.0f, finalOffset11, sr3.getScaledWidth(), finalOffset12 + 11.0f, new Color(0, 0, 0, 255).getRGB()), false);
                                            break;
                                        }
                                        break;
                                    }
                                }
                            }
                            final String currentMode7 = Aqua.setmgr.getSetting("ArraylistSuffixMode").getCurrentMode();
                            switch (currentMode7) {
                                case "-": {
                                    Aqua.INSTANCE.tenacityNormal.drawString(m.getMode().isEmpty() ? m.getName() : String.format("%s %s- %s", m.getName(), EnumChatFormatting.GRAY, m.getMode()), wSet2 - 3.0f, offset3 - 1.0f, doubleFinalColor);
                                    break;
                                }
                                case "<>": {
                                    Aqua.INSTANCE.tenacityNormal.drawString(m.getMode().isEmpty() ? m.getName() : String.format("%s %s<%s>", m.getName(), EnumChatFormatting.GRAY, m.getMode()), wSet2 - 3.0f, offset3 - 1.0f, doubleFinalColor);
                                    break;
                                }
                            }
                            ++index;
                            offset3 += 11.0f;
                        }
                        break;
                    }
                    case "MC": {
                        final ScaledResolution sr = new ScaledResolution(Arraylist2.mc);
                        int index = 0;
                        final float offset2 = 0.0f;
                        GL11.glBlendFunc(770, 771);
                        final ByteBuffer pixelBuf = ByteBuffer.allocateDirect(4);
                        final ScaledResolution sr4;
                        int n14;
                        FontRenderer fontRendererObj;
                        String text;
                        int n15;
                        FontRenderer fontRendererObj2;
                        String text2;
                        final List<Module> collect = (List<Module>)(Aqua.setmgr.getSetting("ArraylistSuffixMode").getCurrentMode().equalsIgnoreCase("-") ? Aqua.moduleManager.modules.stream().filter(Module::isToggled).sorted(Comparator.comparingInt(value -> {
                            n14 = -sr4.getScaledWidth();
                            fontRendererObj = Arraylist2.mc.fontRendererObj;
                            if (value.getMode().isEmpty()) {
                                text = value.getName();
                            }
                            else {
                                text = String.format("%s %s- %s", value.getName(), EnumChatFormatting.WHITE, value.getMode() + "");
                            }
                            return n14 - fontRendererObj.getStringWidth(text);
                        })).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()) : Aqua.moduleManager.modules.stream().filter(Module::isToggled).sorted(Comparator.comparingInt(value -> {
                            n15 = -sr4.getScaledWidth();
                            fontRendererObj2 = Arraylist2.mc.fontRendererObj;
                            if (value.getMode().isEmpty()) {
                                text2 = value.getName();
                            }
                            else {
                                text2 = String.format("%s %s<%s>", value.getName(), EnumChatFormatting.WHITE, value.getMode() + "");
                            }
                            return n15 - fontRendererObj2.getStringWidth(text2);
                        })).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
                        for (int i = 0, collectSize = collect.size(); i < collectSize; ++i) {
                            final Module m = collect.get(i);
                            Module nextModule = null;
                            if (i < collectSize - 1) {
                                nextModule = collect.get(i + 1);
                            }
                            float wSetNext = (float)sr.getScaledWidth();
                            if (nextModule != null) {
                                wSetNext = (float)(sr.getScaledWidth() - Arraylist2.mc.fontRendererObj.getStringWidth(nextModule.getName()) - 5);
                            }
                        }
                        float offset3 = 0.0f;
                        final Iterator<Module> iterator4 = collect.iterator();
                        while (iterator4.hasNext()) {
                            final Module m = iterator4.next();
                            if (!m.isToggled()) {
                                continue;
                            }
                            final float wSet2 = Aqua.setmgr.getSetting("ArraylistSuffixMode").getCurrentMode().equalsIgnoreCase("-") ? ((float)(sr.getScaledWidth() - Arraylist2.mc.fontRendererObj.getStringWidth(m.getMode().isEmpty() ? m.getName() : String.format("%s %s- %s", m.getName(), EnumChatFormatting.GRAY, m.getMode() + "")))) : ((float)(sr.getScaledWidth() - Arraylist2.mc.fontRendererObj.getStringWidth(m.getMode().isEmpty() ? m.getName() : String.format("%s %s<%s>", m.getName(), EnumChatFormatting.GRAY, m.getMode() + ""))));
                            if (Aqua.setmgr.getSetting("ArraylistBackground").isState()) {
                                Gui.drawRect2(wSet2 - 5.0f, offset3, sr.getScaledWidth(), offset3 + 11.0f, new Color(0, 0, 0, 90).getRGB());
                            }
                            final int rainbow = Arraylist.rainbow((int)offset3 * 9);
                            final int color = Arraylist.getGradientOffset(new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), new Color(this.getArraylistColor()), index / 12.4).getRGB();
                            final int finalColor = Aqua.setmgr.getSetting("ArraylistFade").isState() ? color : new Color(Aqua.setmgr.getSetting("HUDColor").getColor()).getRGB();
                            final int doubleFinalColor = Aqua.setmgr.getSetting("ArraylistRainbow").isState() ? rainbow : finalColor;
                            if (Aqua.setmgr.getSetting("ArraylistBloom").isState()) {
                                final String currentMode8 = Aqua.setmgr.getSetting("ArraylistBloomMode").getCurrentMode();
                                switch (currentMode8) {
                                    case "Glow": {
                                        final float finalOffset = offset3;
                                        final float finalOffset2 = offset3;
                                        if (Aqua.moduleManager.getModuleByName("ShaderMultiplier").isToggled()) {
                                            final float wSet5;
                                            final float finalOffset13;
                                            final float finalOffset14;
                                            final int doubleFinalColor4;
                                            ShaderMultiplier.drawGlowESP(() -> Gui.drawRect2(wSet5 - 5.0f, finalOffset13, sr4.getScaledWidth(), finalOffset14 + 11.0f, doubleFinalColor4), false);
                                            break;
                                        }
                                        break;
                                    }
                                    case "Shadow": {
                                        final float finalOffset = offset3;
                                        final float finalOffset2 = offset3;
                                        if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {
                                            final float wSet5;
                                            final float finalOffset15;
                                            final float finalOffset16;
                                            Shadow.drawGlow(() -> Gui.drawRect2(wSet5 - 5.0f, finalOffset15, sr4.getScaledWidth(), finalOffset16 + 11.0f, new Color(0, 0, 0, 255).getRGB()), false);
                                            break;
                                        }
                                        break;
                                    }
                                }
                            }
                            final String currentMode9 = Aqua.setmgr.getSetting("ArraylistSuffixMode").getCurrentMode();
                            switch (currentMode9) {
                                case "-": {
                                    Arraylist2.mc.fontRendererObj.drawString(m.getMode().isEmpty() ? m.getName() : String.format("%s %s- %s", m.getName(), EnumChatFormatting.GRAY, m.getMode()), (int)(wSet2 - 3.0f), (int)(offset3 + 2.0f), doubleFinalColor);
                                    break;
                                }
                                case "<>": {
                                    Arraylist2.mc.fontRendererObj.drawString(m.getMode().isEmpty() ? m.getName() : String.format("%s %s<%s>", m.getName(), EnumChatFormatting.GRAY, m.getMode()), (int)(wSet2 - 3.0f), (int)(offset3 + 2.0f), doubleFinalColor);
                                    break;
                                }
                            }
                            ++index;
                            offset3 += 11.0f;
                        }
                        break;
                    }
                }
            }
            GL11.glDisable(3042);
        }
    }
    
    public int getArraylistColor() {
        try {
            return Aqua.setmgr.getSetting("ArraylistColor").getColor();
        }
        catch (Exception e) {
            return Color.white.getRGB();
        }
    }
    
    public void drawRiseBlur() {
        final ScaledResolution sr = new ScaledResolution(Arraylist2.mc);
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
        final ScaledResolution sr = new ScaledResolution(Arraylist2.mc);
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
            final int rainbow = Arraylist.rainbow((int)offset3 * 9);
            final int color = Arraylist.getGradientOffset(new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), new Color(this.getArraylistColor()), index / 12.4).getRGB();
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
        final ScaledResolution sr = new ScaledResolution(Arraylist2.mc);
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
        final ScaledResolution sr = new ScaledResolution(Arraylist2.mc);
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
            final int rainbow = Arraylist.rainbow((int)offset3 * 9);
            final int color = Arraylist.getGradientOffset(new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), new Color(this.getArraylistColor()), index / 12.4).getRGB();
            final int finalColor = Aqua.setmgr.getSetting("ArraylistFade").isState() ? color : new Color(Aqua.setmgr.getSetting("HUDColor").getColor()).getRGB();
            final int doubleFinalColor = Aqua.setmgr.getSetting("ArraylistRainbow").isState() ? rainbow : finalColor;
            final float finalOffset2 = offset3;
            final UnicodeFontRenderer comfortaa5;
            final Module module;
            String text;
            final float n2;
            final float n3;
            final int color2;
            ShaderMultiplier.drawGlowESP(() -> {
                comfortaa5 = Aqua.INSTANCE.comfortaa4;
                if (module.getMode().isEmpty()) {
                    text = module.getName();
                }
                else {
                    text = String.format("%s %s%s", module.getName(), EnumChatFormatting.WHITE, module.getMode());
                }
                comfortaa5.drawString(text, (float)(int)(n2 - 5.0f), (float)(int)(n3 + 12.0f), color2);
                return;
            }, false);
            Aqua.INSTANCE.comfortaa4.drawString(m.getMode().isEmpty() ? m.getName() : String.format("%s %s%s", m.getName(), EnumChatFormatting.WHITE, m.getMode()), (float)(int)(wSet4 - 5.0f), (float)(int)(offset3 + 12.0f), doubleFinalColor);
            ++index;
            offset3 += 11.0f;
        }
        ++index;
        ++index;
        ++offset2;
        GL11.glDisable(3042);
    }
    
    public void drawSigmaArraylist() {
        final ScaledResolution sr = new ScaledResolution(Arraylist2.mc);
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
            Aqua.INSTANCE.sigma.drawString(m.getMode().isEmpty() ? m.getName() : String.format("%s %s%s", m.getName(), EnumChatFormatting.GRAY, m.getMode()), (float)(int)(wSet4 + 6.0f), (float)(int)(offset3 + 2.0f), Arraylist.rainbowSigma((int)(offset3 * 6.0f)));
            offset3 += 11.0f;
        }
        ++index;
        ++index;
        ++offset2;
        GL11.glDisable(3042);
    }
    
    public void drawTenacityArraylist() {
        final ScaledResolution sr = new ScaledResolution(Arraylist2.mc);
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
            final int rainbow = Arraylist.rainbow((int)offset3 * 9);
            final int color = Arraylist.getGradientOffset(new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), new Color(this.getArraylistColor()), index / 12.4).getRGB();
            final int finalColor = Aqua.setmgr.getSetting("ArraylistFade").isState() ? color : new Color(Aqua.setmgr.getSetting("HUDColor").getColor()).getRGB();
            final int doubleFinalColor = Aqua.setmgr.getSetting("ArraylistRainbow").isState() ? rainbow : finalColor;
            final float finalOffset2 = offset3;
            final float n2;
            final float n3;
            Shadow.drawGlow(() -> Gui.drawRect2(n2 + 3.0f, n3, sr.getScaledWidth(), n3 + 11.0f, doubleFinalColor), false);
            Gui.drawRect2(wSet4 + 3.0f, offset3, sr.getScaledWidth(), offset3 + 11.0f, new Color(0, 0, 0, 40).getRGB());
            Aqua.INSTANCE.tenacityNormal.drawStringWithShadow(m.getMode().isEmpty() ? m.getName() : String.format("%s %s%s", m.getName(), EnumChatFormatting.GRAY, m.getMode()), (float)(int)(wSet4 + 5.0f), (float)(int)(offset3 - 1.5f), doubleFinalColor);
            ++index;
            offset3 += 11.0f;
        }
        ++index;
        ++index;
        ++offset2;
        GL11.glDisable(3042);
    }
    
    public void drawTenacityArraylistBlur() {
        final ScaledResolution sr = new ScaledResolution(Arraylist2.mc);
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
            final int rainbow = Arraylist.rainbow((int)offset3 * 9);
            final int color = Arraylist.getGradientOffset(new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), new Color(this.getArraylistColor()), index / 12.4).getRGB();
            final int finalColor = Aqua.setmgr.getSetting("Arraylist2Fade").isState() ? color : new Color(Aqua.setmgr.getSetting("HUDColor").getColor()).getRGB();
            final int doubleFinalColor = Aqua.setmgr.getSetting("Arraylist2Rainbow").isState() ? rainbow : finalColor;
            final float finalOffset2 = offset3;
            final float n2;
            final float n3;
            Blur.drawBlurred(() -> Gui.drawRect2(n2 + 3.0f, n3, sr.getScaledWidth(), n3 + 11.0f, doubleFinalColor), false);
            ++index;
            offset3 += 11.0f;
        }
        ++index;
        ++index;
        ++offset2;
        GL11.glDisable(3042);
    }
    
    public void drawSigmaArraylistBackground() {
        final ScaledResolution sr = new ScaledResolution(Arraylist2.mc);
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
        final ScaledResolution sr = new ScaledResolution(Arraylist2.mc);
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
        final ScaledResolution sr = new ScaledResolution(Arraylist2.mc);
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
    
    public static void drawRectsSuffix2() {
        final ScaledResolution sr = new ScaledResolution(Arraylist2.mc);
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
    
    public static void drawRectsSuffix() {
        final ScaledResolution sr = new ScaledResolution(Arraylist2.mc);
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
            if (Aqua.moduleManager.getModuleByName("ShaderMultiplier").isToggled()) {
                final Object o;
                final float n2;
                final ScaledResolution scaledResolution;
                final Object o2;
                final int n3;
                ShaderMultiplier.drawGlowESP(() -> RenderUtil.drawRoundedRect3((double)(o - 2.0f), n2 + 5.0f, (double)((float)scaledResolution.getScaledWidth() - o - 2.0f), 11.0, Math.min(3.0f, (float)(o2 - o)), n3 == 0, n3 == 0, n3 == listSize, true, new Color(20, 20, 20, 230)), false);
            }
            else if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {
                final Object o3;
                final float n4;
                final ScaledResolution scaledResolution2;
                final Object o4;
                final int n5;
                Shadow.drawGlow(() -> RenderUtil.drawRoundedRect3((double)(o3 - 2.0f), n4 + 5.0f, (double)((float)scaledResolution2.getScaledWidth() - o3 - 2.0f), 11.0, Math.min(3.0f, (float)(o4 - o3)), n5 == 0, n5 == 0, n5 == listSize, true, new Color(20, 20, 20, 230)), false);
            }
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
    
    public void drawStringsSuffix() {
        final ScaledResolution sr = new ScaledResolution(Arraylist2.mc);
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
            final int rainbow = Arraylist.rainbow((int)offset3 * 9);
            final int color = Arraylist.getGradientOffset(new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), new Color(this.getArraylistColor()), index / 12.4).getRGB();
            final int finalColor = Aqua.setmgr.getSetting("ArraylistFade").isState() ? color : new Color(Aqua.setmgr.getSetting("HUDColor").getColor()).getRGB();
            final int doubleFinalColor = Aqua.setmgr.getSetting("ArraylistRainbow").isState() ? rainbow : finalColor;
            final float finalOffset2 = offset3;
            final UnicodeFontRenderer comfortaa4;
            final Module module;
            String text;
            final float n2;
            final float n3;
            final int color2;
            Shadow.drawGlow(() -> {
                comfortaa4 = Aqua.INSTANCE.comfortaa3;
                if (module.getMode().isEmpty()) {
                    text = module.getName();
                }
                else {
                    text = String.format("%s %s%s", module.getName(), EnumChatFormatting.GRAY, module.getMode());
                }
                comfortaa4.drawString(text, n2 + 0.5f, n3 + 5.0f, color2);
                return;
            }, false);
            Aqua.INSTANCE.comfortaa3.drawString(m.getMode().isEmpty() ? m.getName() : String.format("%s %s%s", m.getName(), EnumChatFormatting.GRAY, m.getMode()), wSet4 + 0.5f, offset3 + 5.0f, doubleFinalColor);
            offset3 += 11.0f;
            ++index;
        }
        ++index;
        ++offset2;
        GL11.glDisable(3042);
    }
}
