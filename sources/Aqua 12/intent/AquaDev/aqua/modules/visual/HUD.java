// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.visual;

import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import intent.AquaDev.aqua.utils.UnicodeFontRenderer;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiNewChat;
import intent.AquaDev.aqua.fr.lavache.anime.Easing;
import net.minecraft.client.Minecraft;
import java.util.Objects;
import events.listeners.EventPostRender2D;
import intent.AquaDev.aqua.utils.RenderUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import java.awt.Color;
import events.listeners.EventRender2D;
import org.lwjgl.input.Keyboard;
import java.util.ArrayList;
import intent.AquaDev.aqua.gui.novolineOld.themesScreen.ThemeScreen;
import events.listeners.EventTick;
import events.Event;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.utils.TimeUtil;
import intent.AquaDev.aqua.fr.lavache.anime.Animate;
import intent.AquaDev.aqua.modules.Module;

public class HUD extends Module
{
    Animate anim;
    Animate anim2;
    Animate anim3;
    private int index;
    private int indexModules;
    private boolean opened;
    private int last;
    private boolean reversed;
    private boolean reversed2;
    private TimeUtil moveDelayTimer;
    private int maxModules;
    
    public HUD() {
        super("HUD", Type.Visual, "HUD", 0, Category.Visual);
        this.anim = new Animate();
        this.anim2 = new Animate();
        this.anim3 = new Animate();
        this.last = 0;
        this.reversed = false;
        this.reversed2 = false;
        this.moveDelayTimer = new TimeUtil();
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
    public void setup() {
        Aqua.setmgr.register(new Setting("Red", this, 255.0, 0.0, 255.0, true));
        Aqua.setmgr.register(new Setting("Green", this, 26.0, 0.0, 255.0, true));
        Aqua.setmgr.register(new Setting("Blue", this, 42.0, 0.0, 255.0, true));
        Aqua.setmgr.register(new Setting("Color", this));
        Aqua.setmgr.register(new Setting("Watermarks", this, "Modern", new String[] { "Normal", "Japan", "Modern", "NEW", "Aqua" }));
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventTick && ThemeScreen.themeLoaded) {
            final List<Module> modules = new ArrayList<Module>();
            for (final Module module : Aqua.moduleManager.modules) {
                if (module.type == Type.values()[this.index]) {
                    modules.add(module);
                }
            }
            if (!this.opened) {
                this.indexModules = 0;
            }
            this.maxModules = modules.size();
            if (this.reversed) {
                this.opened = false;
            }
            if (!this.opened) {
                if (Keyboard.isKeyDown(208) && this.index < Type.values().length - 1 && this.moveDelayTimer.hasReached(120L)) {
                    ++this.index;
                    this.moveDelayTimer.reset();
                }
                if (Keyboard.isKeyDown(200) && this.index > 0 && this.moveDelayTimer.hasReached(120L)) {
                    --this.index;
                    this.moveDelayTimer.reset();
                }
            }
            else {
                if (Keyboard.isKeyDown(208) && this.indexModules < this.maxModules - 1 && this.moveDelayTimer.hasReached(120L)) {
                    ++this.indexModules;
                    this.moveDelayTimer.reset();
                }
                if (Keyboard.isKeyDown(200) && this.indexModules > 0 && this.moveDelayTimer.hasReached(120L)) {
                    --this.indexModules;
                    this.moveDelayTimer.reset();
                }
            }
            if (Keyboard.isKeyDown(205)) {
                if (this.opened && this.moveDelayTimer.hasReached(200L)) {
                    modules.get(this.indexModules).setState(!modules.get(this.indexModules).isToggled());
                    this.moveDelayTimer.reset();
                }
                else {
                    this.moveDelayTimer.reset();
                    this.reversed = false;
                    this.opened = true;
                }
            }
            if (Keyboard.isKeyDown(203)) {
                this.reversed = true;
            }
        }
        if (event instanceof EventRender2D) {
            if (ThemeScreen.themeRise || ThemeScreen.themeRise6) {
                final int color = Arraylist.getGradientOffset(new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), new Color(Aqua.setmgr.getSetting("ArraylistColor").getColor()), this.index / 12.4).getRGB();
                if (ThemeScreen.themeRise) {
                    Aqua.INSTANCE.comfortaa.drawStringWithShadow("Rise", 0.0f, 0.0f, color);
                    Aqua.INSTANCE.comfortaa4.drawStringWithShadow("" + Aqua.build, 40.0f, 2.0f, color);
                }
                else {
                    ShaderMultiplier.drawGlowESP(() -> Aqua.INSTANCE.comfortaa.drawString("Rise", 0.0f, 0.0f, color), false);
                    Aqua.INSTANCE.comfortaa.drawString("Rise", 0.0f, 0.0f, color);
                }
            }
            if (ThemeScreen.themeXave) {
                Aqua.INSTANCE.esp.drawStringWithShadow("Xave", 0.0f, 0.0f, this.getColor2());
            }
            if (ThemeScreen.themeTenacity) {
                Shadow.drawGlow(() -> Aqua.INSTANCE.tenacity.drawStringWithShadow("Aqua " + Aqua.build, 2.0f, 2.0f, this.getColor2()), false);
                Aqua.INSTANCE.tenacity.drawStringWithShadow("Aqua " + Aqua.build, 2.0f, 2.0f, this.getColor2());
            }
            if (ThemeScreen.themeHero) {
                final List<Module> modules = new ArrayList<Module>();
                for (final Module module : Aqua.moduleManager.modules) {
                    if (module.type == Type.values()[this.index]) {
                        modules.add(module);
                    }
                }
                final ScaledResolution sr = new ScaledResolution(HUD.mc);
                final float x = 0.0f;
                final float y = 17.0f;
                final float height = (float)(Type.values().length * 14);
                final float width = 56.0f;
                Gui.drawRect2(width, 0.0, width + 1.0f, height, new Color(6, 226, 70, 160).getRGB());
                Gui.drawRect2(0.0, height - 1.0f, width + 1.0f, height, new Color(6, 226, 70, 160).getRGB());
                Gui.drawRect2(x, 0.0, x + width, y + height - 18.0f, new Color(12, 12, 12, 170).getRGB());
                Gui.drawRect((int)width, (int)(y + this.index * 11 + 4.0f), 0, (int)(y + 14.0f + this.index * 11), new Color(6, 226, 70, 160).getRGB());
                for (int yT = 0; yT < Type.values().length; ++yT) {
                    Aqua.INSTANCE.roboto2.drawString(Type.values()[yT].name(), x + 1.0f, y + (yT * 11 + 1.5f), Color.white.getRGB());
                }
                if (this.opened) {
                    final float moduleHeight = (float)(modules.size() * 11);
                    Gui.drawRect2(x + width + 3.0f, y + this.index * 11 + 3.0f, x + width + 62.5f + 7.5f + this.maxModules, y + this.index * 11 + moduleHeight + 4.0f, new Color(12, 12, 12, 170).getRGB());
                    for (int i = 0; i < modules.size(); ++i) {
                        Gui.drawRect2(x + width + 3.0f, y + this.index * 11 + this.indexModules * 11 + 3.0f, x + width + 5.0f, y + this.index * 11 + 13.0f + this.indexModules * 11 + 2.0f, new Color(6, 226, 70, 160).getRGB());
                        Aqua.INSTANCE.roboto2.drawString(modules.get(i).getName(), x + width + 6.0f, y + this.index * 11 + i * 11 + 2.0f, (this.indexModules == i || modules.get(i).isToggled()) ? new Color(6, 226, 70, 160).getRGB() : Color.white.getRGB());
                    }
                }
                Aqua.INSTANCE.robotoTabguiName.drawString("Hero", 2.0f, -4.0f, new Color(6, 226, 70, 160).getRGB());
            }
            if (ThemeScreen.themeJello) {
                final List<Module> modules = new ArrayList<Module>();
                for (final Module module : Aqua.moduleManager.modules) {
                    if (module.type == Type.values()[this.index]) {
                        modules.add(module);
                    }
                }
                final ScaledResolution sr = new ScaledResolution(HUD.mc);
                final float x = 5.0f;
                final float y = 35.0f;
                final float height = (float)(Type.values().length * 15);
                final float width = 68.0f;
                final float n;
                final float n2;
                final float width2;
                final float height2;
                Shadow.drawGlow(() -> Gui.drawRect2(n, n2, n + width2, n2 + height2 - 13.0f, Color.darkGray.getRGB()), false);
                final float n3;
                final float n4;
                Blur.drawBlurred(() -> Gui.drawRect2(n3, n4, n3 + width2, n4 + height2 - 13.0f, new Color(255, 254, 254, 55).getRGB()), false);
                final float n5;
                final float n6;
                final float x2;
                Blur.drawBlurred(() -> Gui.drawRect((int)n5, (int)(n6 + this.index * 13), (int)x2, (int)(n6 + 14.0f + (this.index * 13 - 2)), new Color(79, 82, 79, 160).getRGB()), false);
                if (this.opened) {
                    final float moduleHeight = (float)(modules.size() * 13);
                    final Object o;
                    final Object o2;
                    final float n7;
                    final float moduleHeight2;
                    Shadow.drawGlow(() -> Gui.drawRect2((double)(o + o2 + 3.0f), n7 + this.index * 13, (double)(o + o2 + 62.5f + 7.5f + (float)this.maxModules), n7 + this.index * 13 + moduleHeight2, Color.darkGray.getRGB()), false);
                    final Object o3;
                    final Object o4;
                    final float n8;
                    Blur.drawBlurred(() -> Gui.drawRect2((double)(o3 + o4 + 3.0f), n8 + this.index * 13, (double)(o3 + o4 + 62.5f + 7.5f + (float)this.maxModules), n8 + this.index * 13 + moduleHeight2, new Color(20, 20, 20, 120).getRGB()), false);
                    for (int i = 0; i < modules.size(); ++i) {
                        Gui.drawRect2(x + width + 3.0f, y + this.index * 13 + this.indexModules * 13, x + width + 5.0f, y + this.index * 13 + 13.0f + this.indexModules * 13, Color.lightGray.getRGB());
                        Aqua.INSTANCE.sigma.drawString(modules.get(i).getName(), x + width + 6.0f, y + this.index * 13 + i * 13 + 2.0f, (this.indexModules == i || modules.get(i).isToggled()) ? Color.lightGray.getRGB() : Color.white.getRGB());
                    }
                }
                Shadow.drawGlow(() -> Aqua.INSTANCE.jelloTabGUI.drawString("Sigma", 10.0f, -1.0f, Color.black.getRGB()), false);
                Shadow.drawGlow(() -> Aqua.INSTANCE.jelloClickguiPanelBottom.drawString("Jello", 10.0f, 22.0f, Color.black.getRGB()), false);
                Aqua.INSTANCE.jelloTabGUI.drawString("Sigma", 10.0f, -1.0f, new Color(250, 250, 250, 210).getRGB());
                Aqua.INSTANCE.jelloClickguiPanelBottom.drawString("Jello", 10.0f, 22.0f, new Color(250, 250, 250, 210).getRGB());
            }
            if (ThemeScreen.themeSigma) {
                final List<Module> modules = new ArrayList<Module>();
                for (final Module module : Aqua.moduleManager.modules) {
                    if (module.type == Type.values()[this.index]) {
                        modules.add(module);
                    }
                }
                final ScaledResolution sr = new ScaledResolution(HUD.mc);
                final float x = 5.0f;
                final float y = 20.0f;
                final float height = (float)(Type.values().length * 15);
                final float width = 60.0f;
                Gui.drawRect2(x - 4.0f, y - 20.0f, x + width - 10.0f, y + height - 13.0f, new Color(20, 20, 20, 120).getRGB());
                RenderUtil.drawGradientRectHorizontal(x - 4.0f, y + this.index * 13, x + 47.0f, 12.0, Color.black.getRGB(), this.getColor2());
                for (int yT = 0; yT < Type.values().length; ++yT) {
                    Aqua.INSTANCE.sigma.drawString(Type.values()[yT].name(), x - 3.0f, y + (yT * 13 + 2), Color.white.getRGB());
                }
                if (this.opened) {
                    final float moduleHeight = (float)(modules.size() * 13);
                    Gui.drawRect2(x + width - 8.0f, y + this.index * 13, x + width + 62.5f + 2.5f + this.maxModules, y + this.index * 13 + moduleHeight, new Color(20, 20, 20, 120).getRGB());
                    for (int i = 0; i < modules.size(); ++i) {
                        Gui.drawRect2(x + width - 8.0f, y + this.index * 13 + this.indexModules * 13, x + width - 5.0f, y + this.index * 13 + 13.0f + this.indexModules * 13, this.getColor2());
                        Aqua.INSTANCE.sigma.drawString(modules.get(i).getName(), x + width - 2.0f, y + this.index * 13 + i * 13 + 2.0f, (this.indexModules == i || modules.get(i).isToggled()) ? this.getColor2() : Color.white.getRGB());
                    }
                }
                Aqua.INSTANCE.sigma2.drawStringWithShadow("Sigma", 1.0f, 2.0f, Color.lightGray.getRGB());
                Aqua.INSTANCE.sigma3.drawString("v5.0", 38.0f, 2.0f, Arraylist.rainbowSigma(this.index * 6));
                Gui.drawRect2(5.0, 17.5, 51.0, 18.5, Color.white.getRGB());
            }
            if (ThemeScreen.themeLoaded) {
                return;
            }
            if (Aqua.setmgr.getSetting("HUDWatermarks").getCurrentMode().equalsIgnoreCase("Modern")) {
                final int posX = 0;
                final int posY = 4;
                final int width3 = 55;
                final int height3 = 15;
                if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                    Blur.drawBlurred(() -> RenderUtil.drawRoundedRect2Alpha(posX - 5, posY, width3, height3, 3.0, new Color(0, 0, 0, 255)), false);
                }
            }
        }
        if (event instanceof EventPostRender2D) {
            if (Aqua.setmgr.getSetting("HUDWatermarks").getCurrentMode().equalsIgnoreCase("Aqua") && (!ThemeScreen.themeLoaded || ThemeScreen.themeAqua2)) {
                final int posX = 3;
                final int posY = 6;
                final String beta = (Objects.equals(Aqua.INSTANCE.ircClient.getNickname(), "LCA_MODZ") || Objects.equals(Aqua.INSTANCE.ircClient.getNickname(), "luigiaqua")) ? " Beta" : "";
                final int width4 = Aqua.INSTANCE.tenacityNormal.getStringWidth(Aqua.name + " b" + Aqua.build + ((Objects.equals(Aqua.INSTANCE.ircClient.getNickname(), "LCA_MODZ") || Objects.equals(Aqua.INSTANCE.ircClient.getNickname(), "luigiaqua")) ? 22 : 0) + Minecraft.getDebugFPS() + "FPS |  | " + HUD.mc.session.getUsername()) + 50;
                final int height4 = 15;
                if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {
                    final int posX2;
                    final int posY2;
                    Shadow.drawGlow(() -> RenderUtil.drawRoundedRect2Alpha(posX2, posY2, width4, height4 + 1, 3.4, new Color(20, 20, 20, 200)), false);
                }
                RenderUtil.drawRoundedRect2Alpha(posX, posY, width4, height4 + 1, 3.4, new Color(20, 20, 20, 70));
                Aqua.INSTANCE.tenacityNormal.drawString(Aqua.name + " b" + Aqua.build + beta, (float)(posX + 5), (float)(posY + 1), this.getColor2());
                Aqua.INSTANCE.tenacityNormal.drawString(Minecraft.getDebugFPS() + "FPS", (float)(Aqua.INSTANCE.tenacityNormal.getStringWidth((Objects.equals(Aqua.INSTANCE.ircClient.getNickname(), "LCA_MODZ") || Objects.equals(Aqua.INSTANCE.ircClient.getNickname(), "luigiaqua")) ? "Beta" : "") + ((Objects.equals(Aqua.INSTANCE.ircClient.getNickname(), "LCA_MODZ") || Objects.equals(Aqua.INSTANCE.ircClient.getNickname(), "luigiaqua")) ? (posX + 50) : (posX + 55))), (float)(posY + 1), -1);
                Aqua.INSTANCE.tenacityNormal.drawString(HUD.mc.session.getUsername(), (float)(Aqua.INSTANCE.tenacityNormal.getStringWidth(Aqua.name + " b" + Aqua.build + Minecraft.getDebugFPS() + "FPS | ") + ((Objects.equals(Aqua.INSTANCE.ircClient.getNickname(), "LCA_MODZ") || Objects.equals(Aqua.INSTANCE.ircClient.getNickname(), "luigiaqua")) ? 35 : (posX + 17))), (float)(posY + 1), -1);
                Aqua.INSTANCE.tenacityNormal.drawString(String.valueOf(HUD.mc.isSingleplayer() ? "0" : Long.valueOf(HUD.mc.getCurrentServerData().pingToServer)) + "MS", (float)(Aqua.INSTANCE.tenacityNormal.getStringWidth(Aqua.name + " b" + Aqua.build + Minecraft.getDebugFPS() + "FPS |  | " + HUD.mc.session.getUsername()) + ((Objects.equals(Aqua.INSTANCE.ircClient.getNickname(), "LCA_MODZ") || Objects.equals(Aqua.INSTANCE.ircClient.getNickname(), "luigiaqua")) ? 35 : 22)), (float)(posY + 1), -1);
                Aqua.INSTANCE.tenacityNormal.drawString(" | ", (float)((Objects.equals(Aqua.INSTANCE.ircClient.getNickname(), "LCA_MODZ") || Objects.equals(Aqua.INSTANCE.ircClient.getNickname(), "luigiaqua")) ? (posX + 62) : (posX + 47)), (float)(posY + 1), new Color(20, 20, 20, 10).getRGB());
                Aqua.INSTANCE.tenacityNormal.drawString(" | ", (float)(Aqua.INSTANCE.tenacityNormal.getStringWidth(Aqua.name + " b" + Aqua.build + Minecraft.getDebugFPS() + "FPS | ") + ((Objects.equals(Aqua.INSTANCE.ircClient.getNickname(), "LCA_MODZ") || Objects.equals(Aqua.INSTANCE.ircClient.getNickname(), "luigiaqua")) ? 29 : 10)), (float)(posY + 1), new Color(20, 20, 20, 10).getRGB());
                Aqua.INSTANCE.tenacityNormal.drawString("  | ", (float)(Aqua.INSTANCE.tenacityNormal.getStringWidth(Aqua.name + " b" + Aqua.build + Minecraft.getDebugFPS() + "FPS |  | " + HUD.mc.session.getUsername()) + ((Objects.equals(Aqua.INSTANCE.ircClient.getNickname(), "LCA_MODZ") || Objects.equals(Aqua.INSTANCE.ircClient.getNickname(), "luigiaqua")) ? 26 : 11)), (float)(posY + 1), new Color(20, 20, 20, 10).getRGB());
            }
            if (Aqua.setmgr.getSetting("HUDWatermarks").getCurrentMode().equalsIgnoreCase("NEW")) {
                final int posX = 3;
                final int posY = 6;
                final int width3 = (int)Aqua.INSTANCE.tenacityNormal.getWidth(Aqua.name + " b" + Aqua.build + " | " + HUD.mc.getCurrentServerData().serverIP + " | " + HUD.mc.session.getUsername() + 15);
                final int height3 = 15;
                final int left;
                final int top;
                final int width5;
                Shadow.drawGlow(() -> Gui.drawRect(left, top, width5, top - 2, Arraylist.getGradientOffset(new Color(Aqua.setmgr.getSetting("ArraylistColor").getColor()), new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), this.index / 12.4).getRGB()), false);
                final int posX3;
                final int posY3;
                final int height5;
                Shadow.drawGlow(() -> Gui.drawRect(posX3, posY3 - 2, width5, height5 + 2, new Color(0, 0, 0, 160).getRGB()), false);
                Gui.drawRect(posX, posY - 2, width3, height3 + 2, new Color(0, 0, 0, 60).getRGB());
                Gui.drawRect(posX, posY, width3, posY - 2, Arraylist.getGradientOffset(new Color(Aqua.setmgr.getSetting("ArraylistColor").getColor()), new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), this.index / 12.4).getRGB());
                Aqua.INSTANCE.tenacityNormal.drawString(Aqua.name + " b" + Aqua.build + " | " + HUD.mc.getCurrentServerData().serverIP + " | " + HUD.mc.session.getUsername(), (float)(posX + 2), (float)(posY - 2), -1);
            }
            if (ThemeScreen.themeJello) {
                final List<Module> modules = new ArrayList<Module>();
                for (final Module module : Aqua.moduleManager.modules) {
                    if (module.type == Type.values()[this.index]) {
                        modules.add(module);
                    }
                }
                final ScaledResolution sr = new ScaledResolution(HUD.mc);
                final float x = 5.0f;
                final float y = 35.0f;
                final float height = (float)(Type.values().length * 15);
                final float width = 68.0f;
                Gui.drawRect((int)(width + 5.0f), (int)(y + this.index * 13), (int)x, (int)(y + 14.0f + (this.index * 13 - 2)), new Color(52, 51, 51, 25).getRGB());
                for (int yT = 0; yT < Type.values().length; ++yT) {
                    Aqua.INSTANCE.jelloTabGUIBottom.drawString(Type.values()[yT].name(), x + 1.0f, y + yT * 13, Color.white.getRGB());
                }
                if (this.opened) {
                    final float moduleHeight = (float)(modules.size() * 13);
                    for (int i = 0; i < modules.size(); ++i) {
                        Gui.drawRect2(x + width + 3.0f, y + this.index * 13 + this.indexModules * 13, x + width + 5.0f, y + this.index * 13 + 13.0f + this.indexModules * 13, new Color(79, 82, 79, 160).getRGB());
                        Aqua.INSTANCE.sigma.drawString(modules.get(i).getName(), x + width + 6.0f, y + this.index * 13 + i * 13 + 2.0f, (this.indexModules == i || modules.get(i).isToggled()) ? new Color(79, 82, 79, 160).getRGB() : Color.white.getRGB());
                    }
                }
                Shadow.drawGlow(() -> Aqua.INSTANCE.jelloTabGUI.drawString("Sigma", 10.0f, -1.0f, Color.gray.getRGB()), false);
                Shadow.drawGlow(() -> Aqua.INSTANCE.jelloClickguiPanelBottom.drawString("Jello", 10.0f, 22.0f, Color.gray.getRGB()), false);
            }
            this.anim2.setEase(Easing.LINEAR).setMin(0.0f).setMax(27.0f).setSpeed(45.0f).setReversed(false).update();
            this.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax(13.0f).setSpeed(45.0f).setReversed(false).update();
            this.anim3.setEase(Easing.LINEAR).setMin(0.0f).setMax(27.0f).setSpeed(45.0f).setReversed(!GuiNewChat.animatedChatOpen).update();
            final ScaledResolution scaledResolution = new ScaledResolution(HUD.mc);
            final int posX4 = 4;
            final int posY4 = (int)(scaledResolution.getScaledHeight() - this.anim2.getValue());
            final int posY5 = (int)(scaledResolution.getScaledHeight() + this.anim.getValue());
            Gui.drawRect2(2.0, GuiScreen.height - this.anim3.getValue() + 12.0f, GuiScreen.width - 2, GuiScreen.height, new Color(0, 0, 0, 70).getRGB());
            if (GuiNewChat.getChatOpen()) {
                GuiNewChat.animatedChatOpen = true;
                Aqua.INSTANCE.comfortaa4.drawString("Version : " + Aqua.INSTANCE.ircClient.getRank().name() + " | Nickname : " + HUD.mc.session.getUsername() + " | BPS : " + calculateBPS(), (float)posX4, (float)posY4, this.getColor2());
                this.anim.reset();
            }
            else {
                GuiNewChat.animatedChatOpen = false;
                this.anim2.reset();
                Aqua.INSTANCE.comfortaa4.drawString("Version : " + Aqua.INSTANCE.ircClient.getRank().name() + " | Nickname : " + HUD.mc.session.getUsername() + " | BPS : " + calculateBPS(), (float)posX4, (float)(posY5 - 25), this.getColor2());
            }
            if (ThemeScreen.themeLoaded) {
                return;
            }
            this.drawLogo();
        }
    }
    
    public void drawLogo() {
        final int i = 0;
        final int color = Aqua.setmgr.getSetting("HUDColor").getColor();
        if (Aqua.setmgr.getSetting("HUDWatermarks").getCurrentMode().equalsIgnoreCase("Modern")) {
            final int posX = 0;
            final int posY = 4;
            final int width = 55;
            final int height = 15;
            if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {
                Shadow.drawGlow(() -> RenderUtil.drawRoundedRect2Alpha(posX - 5, posY, width, height, 1.0, new Color(0, 0, 0, 255)), false);
            }
            RenderUtil.drawRoundedRect2Alpha(posX - 5, posY, width, height, 1.0, new Color(0, 0, 0, 90));
            Aqua.INSTANCE.comfortaa4.drawString("Aqua ", 6.0f, 7.0f, -1);
            Aqua.INSTANCE.comfortaa4.drawString("b" + Aqua.build, 31.0f, 7.0f, Arraylist.getGradientOffset(new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), new Color(Aqua.setmgr.getSetting("ArraylistColor").getColor()), 15.0).getRGB());
            Gui.drawRect2(posX, posY, 2.0, height + 4, Arraylist.getGradientOffset(new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), new Color(Aqua.setmgr.getSetting("ArraylistColor").getColor()), 15.0).getRGB());
        }
        if (Aqua.setmgr.getSetting("HUDWatermarks").getCurrentMode().equalsIgnoreCase("Normal")) {
            final double x = 5.0;
            final double y = 4.0;
            final UnicodeFontRenderer comfortaa3 = Aqua.INSTANCE.comfortaa3;
            final StringBuilder append = new StringBuilder().append("Aqua | ");
            final Minecraft mc = HUD.mc;
            RenderUtil.drawRoundedRect2Alpha(x, y, comfortaa3.getWidth(append.append(Minecraft.getDebugFPS()).append(" | ").append(HUD.mc.getSession().getUsername()).append(5).toString()), 13.0, 2.0, new Color(20, 20, 20, 70));
            if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {
                final UnicodeFontRenderer comfortaa4;
                final Minecraft mc2;
                final Object x2;
                final double y2;
                final StringBuilder sb;
                Shadow.drawGlow(() -> {
                    comfortaa4 = Aqua.INSTANCE.comfortaa3;
                    new StringBuilder().append("Aqua | ");
                    mc2 = HUD.mc;
                    RenderUtil.drawRoundedRect((double)x2, y2, comfortaa4.getWidth(sb.append(Minecraft.getDebugFPS()).append(" | ").append(HUD.mc.getSession().getUsername()).append(5).toString()), 13.0, 2.0, new Color(0, 0, 0, 255).getRGB());
                    return;
                }, false);
            }
            if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                final UnicodeFontRenderer comfortaa5;
                final Minecraft mc3;
                final Object x3;
                final double y3;
                final StringBuilder sb2;
                Blur.drawBlurred(() -> {
                    comfortaa5 = Aqua.INSTANCE.comfortaa3;
                    new StringBuilder().append("Aqua | ");
                    mc3 = HUD.mc;
                    RenderUtil.drawRoundedRect((double)x3, y3, comfortaa5.getWidth(sb2.append(Minecraft.getDebugFPS()).append(" | ").append(HUD.mc.getSession().getUsername()).append(5).toString()), 16.0, 2.0, new Color(0, 0, 0, 255).getRGB());
                    return;
                }, false);
            }
            if (Aqua.moduleManager.getModuleByName("Arraylist").isToggled()) {
                final UnicodeFontRenderer comfortaa6;
                final Minecraft mc4;
                final Object x4;
                final double y4;
                final StringBuilder sb3;
                Arraylist.drawGlowArray(() -> {
                    comfortaa6 = Aqua.INSTANCE.comfortaa3;
                    new StringBuilder().append("Aqua | ");
                    mc4 = HUD.mc;
                    RenderUtil.drawRoundedRect2Alpha((double)x4, y4, comfortaa6.getWidth(sb3.append(Minecraft.getDebugFPS()).append(" | ").append(HUD.mc.getSession().getUsername()).append(5).toString()), 2.0, 1.0, Arraylist.getGradientOffset(new Color(this.getColor2()), new Color(this.getColor2()), 0.0));
                    return;
                }, false);
            }
            final double x5 = 5.0;
            final double y5 = 17.0;
            final UnicodeFontRenderer comfortaa7 = Aqua.INSTANCE.comfortaa3;
            final StringBuilder append2 = new StringBuilder().append("Aqua | ");
            final Minecraft mc5 = HUD.mc;
            RenderUtil.drawRoundedRect2Alpha(x5, y5, comfortaa7.getWidth(append2.append(Minecraft.getDebugFPS()).append(" | ").append(HUD.mc.getSession().getUsername()).append(5).toString()), 2.0, 1.0, Arraylist.getGradientOffset(new Color(this.getColor2()), new Color(this.getColor2()), 0.0));
            final UnicodeFontRenderer comfortaa8 = Aqua.INSTANCE.comfortaa3;
            final StringBuilder append3 = new StringBuilder().append("Aqua | ");
            final Minecraft mc6 = HUD.mc;
            comfortaa8.drawString(append3.append(Minecraft.getDebugFPS()).append(" | ").append(HUD.mc.getSession().getUsername()).toString(), 7.0f, 5.0f, -1);
        }
        if (Aqua.setmgr.getSetting("HUDWatermarks").getCurrentMode().equalsIgnoreCase("Japan")) {
            Arraylist.drawGlowArray(() -> Gui.drawRect(4, 2, Aqua.INSTANCE.japan.getStringWidth("Aqua") + 8, 21, color), false);
            Gui.drawRect(4, 2, Aqua.INSTANCE.japan.getStringWidth("Aqua") + 8, 21, new Color(0, 0, 0, 60).getRGB());
            Blur.drawBlurred(() -> Gui.drawRect(4, 2, Aqua.INSTANCE.japan.getStringWidth("Aqua") + 8, 21, new Color(0, 0, 0, 60).getRGB()), false);
            Aqua.INSTANCE.japan.drawString("A", 5.0f, 1.0f, color);
            Aqua.INSTANCE.japan.drawString("qua", 23.0f, 1.0f, -1);
        }
    }
    
    public static int rainbow(final int delay) {
        double rainbowState = Math.ceil((double)((System.currentTimeMillis() + delay) / 7L));
        rainbowState %= 360.0;
        return Color.getHSBColor((float)(rainbowState / 360.0), 0.9f, 1.0f).getRGB();
    }
    
    public int getColor2() {
        try {
            return Aqua.setmgr.getSetting("HUDColor").getColor();
        }
        catch (Exception e) {
            return Color.white.getRGB();
        }
    }
    
    public static Color setAlpha(final int color, final int alpha) {
        return new Color(new Color(color).getRed(), new Color(color).getGreen(), new Color(color).getBlue(), alpha);
    }
    
    public static int getColor(final int red, final int green, final int blue, final int alpha) {
        int color = MathHelper.clamp_int(alpha, 0, 255) << 24;
        color |= MathHelper.clamp_int(red, 0, 255) << 16;
        color |= MathHelper.clamp_int(green, 0, 255) << 8;
        color |= MathHelper.clamp_int(blue, 0, 255);
        return color;
    }
    
    public static double calculateBPS() {
        final double calculateTicks = (HUD.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed) != null && Aqua.moduleManager.getModuleByName("Speed").isToggled()) ? 30.0 : 20.0;
        final double bps = Math.hypot(HUD.mc.thePlayer.posX - HUD.mc.thePlayer.prevPosX, HUD.mc.thePlayer.posZ - HUD.mc.thePlayer.prevPosZ) * HUD.mc.timer.timerSpeed * calculateTicks;
        return Math.round(bps * 100.0) / 100.0;
    }
}
