package club.strifeclient.ui.implementations.ingame;

import club.strifeclient.Client;
import club.strifeclient.font.CustomFont;
import club.strifeclient.module.Module;
import club.strifeclient.module.implementations.visual.ClickGUI;
import club.strifeclient.module.implementations.visual.HUD;
import club.strifeclient.ui.implementations.hud.clickgui.GuiClickGUI;
import club.strifeclient.ui.implementations.imgui.GuiImGui;
import club.strifeclient.util.rendering.ColorUtil;
import club.strifeclient.util.rendering.DrawUtil;
import club.strifeclient.util.rendering.InterpolateUtil;
import club.strifeclient.util.system.Stopwatch;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.lwjgl.opengl.GL11.*;

public class GuiIngameHook extends GuiIngame {
    protected Minecraft mc = Minecraft.getMinecraft();

    @Getter
    private GuiClickGUI guiClickGUI;
    @Getter
    private GuiImGui guiImGui;
    private ClickGUI clickGUI;
    private ScaledResolution scaledResolution;

    private CustomFont currentFont;

    private HUD hud;

    private List<AnimatedModule> animatedModules;

    private Stopwatch timer = new Stopwatch();

    public GuiIngameHook(Minecraft mcIn) {
        super(mcIn);
    }

    public void init() {
        Client.INSTANCE.getEventBus().register(this);
        clickGUI = Client.INSTANCE.getModuleManager().getModule(ClickGUI.class);
        hud = Client.INSTANCE.getModuleManager().getModule(HUD.class);
        scaledResolution = new ScaledResolution(mc);
        guiImGui = new GuiImGui();
        guiClickGUI = new GuiClickGUI();
        animatedModules = new ArrayList<>();
        hud.fontSetting.addChangeCallback((old, change) -> currentFont = Client.INSTANCE.getFontManager().getCurrentFont());
        hud.animationSetting.addChangeCallback((old, change) -> animatedModules.clear());
        hud.fontSetting.addChangeCallback((old, change) -> currentFont = Client.INSTANCE.getFontManager().getFontByName(change.getValue().getFontName()));
        currentFont = Client.INSTANCE.getFontManager().getFontByName(hud.fontSetting.getValue().getFontName());
    }
    
    public void deInit() {

    }
    
    @Override
    public void renderGameOverlay(float partialTicks, ScaledResolution scaledResolution) {
        this.scaledResolution = scaledResolution;
        super.renderGameOverlay(partialTicks, scaledResolution);
        if (hud.isEnabled()) {
            drawStringWithShadow("S§ftrife", 3 - 2,3, ColorUtil.getHUDColor(1));
            for (Enum<?> element : hud.elementsSetting.getValue().keySet()) {
                final HUD.Element hudElement = (HUD.Element) element;
                if (hud.elementsSetting.isSelected(hudElement)) {
                    switch (hudElement) {
                        case ARRAYLIST: {
                            drawArraylist();
                            break;
                        }
                    }
                }
            }
            drawStringWithShadow("Cutzu is King", scaledResolution.getScaledWidth() / 2f - getFontWidth("Cutzu is King") / 2, scaledResolution.getScaledHeight() / 2f - getFontHeight("Cutzu is King") / 2 - 10, ColorUtil.getHUDColor(1));
        }
    }


    private void drawArraylist() {
        if (animatedModules.isEmpty()) {
            final Collection<Module> modules = Client.INSTANCE.getModuleManager().getModules();
            switch (hud.animationSetting.getValue()) {
                case STRIFE:
                case ASTOLFO: {
                    for (Module module : modules) {
                        animatedModules.add(new AnimatedModule(module, -getFontWidth(module.getRenderName()) - 3, 0, getFontWidth(module.getRenderName()), 0));
                    }
                    break;
                }
            }
        }
        final float duration = 1500;
        final List<AnimatedModule> activeModules = animatedModules.stream().filter(animatedModule -> animatedModule.isVisible(duration)).collect(Collectors.toList());
        activeModules.sort(Comparator.comparing(module -> {
            final AnimatedModule animatedModule = (AnimatedModule) module;
            return getFontWidth(prepareName(animatedModule.module.getRenderName(), animatedModule.module.getSuffix()));
        }).reversed());
        float yOffset = -12.5f;
        int i = 0;
        for (AnimatedModule animatedModule : activeModules) {
            final Module module = animatedModule.module;
            final String name = prepareName(module.getRenderName(), module.getSuffix());
            final float t = Math.min(1, (System.currentTimeMillis() - animatedModule.start) / duration);
            if (module.isEnabled()) {
                switch (hud.animationSetting.getValue()) {
                    case STRIFE:
                    case ASTOLFO: {
                        animatedModule.x = InterpolateUtil.interpolateFloat(animatedModule.x, getFontWidth(name), t);
                        animatedModule.y = InterpolateUtil.interpolateFloat(animatedModule.y, yOffset + getFontHeight(name), t);
                        animatedModule.height = InterpolateUtil.interpolateFloat(animatedModule.height, getFontHeight(name), t);
                        break;
                    }
                }
            } else {
                switch (hud.animationSetting.getValue()) {
                    case STRIFE: {
                        animatedModule.x = InterpolateUtil.interpolateFloat(animatedModule.x, animatedModule.origX, t);
                        animatedModule.height = InterpolateUtil.interpolateFloat(animatedModule.height, animatedModule.origHeight, t);
                        break;
                    }
                    case ASTOLFO: {
                        animatedModule.x = InterpolateUtil.interpolateFloat(animatedModule.x, animatedModule.origX, t);
                        animatedModule.y = InterpolateUtil.interpolateFloat(animatedModule.y, animatedModule.origY, t);
                        animatedModule.height = InterpolateUtil.interpolateFloat(animatedModule.height, animatedModule.origHeight, t);
                        break;
                    }
                }
            }
            switch (hud.backgroundSetting.getValue()) {
                case BAR_RIGHT: {
                    DrawUtil.drawRect(scaledResolution.getScaledWidth() - animatedModule.x - 4, animatedModule.y, scaledResolution.getScaledWidth(),
                            animatedModule.y + animatedModule.height, new Color(0, 0, 0, hud.backgroundOpacitySetting.getInt()));
                    DrawUtil.drawRect(scaledResolution.getScaledWidth() - 1, animatedModule.y, scaledResolution.getScaledWidth(),
                            animatedModule.y + animatedModule.height, ColorUtil.getHUDColor(i * 200));
                    drawStringWithShadow(name, scaledResolution.getScaledWidth() - animatedModule.x - 2,
                            hud.fontSetting.getValue() == HUD.Font.CLIENT ? animatedModule.y + 1.5f : animatedModule.y + 2.5f, ColorUtil.getHUDColor(i * 200));
                    break;
                }
            }
            yOffset += animatedModule.height;
            i++;
        }

    }

    private String prepareName(String name, Supplier<Object> suffix) {
        if (suffix != null && suffix.get() != null) {
            switch (hud.suffixModeSetting.getValue()) {
                case BRACKET: name = name + "\u00A77 [" + suffix.get() + "]";
                break;
                case DASH: name = name + "\u00A77 - " + suffix.get();
                break;
                case SIMPLE: name = name + "\u00A77 " + suffix.get();
                break;
            }
        }
        return hud.lowercaseSetting.getValue() ? name.toLowerCase() : name;
    }

    public void drawStringWithShadow(String text, float x, float y, Color color) {
        drawStringWithShadow(text, x, y, color, 1);
    }
    public void drawStringWithShadow(String text, float x, float y, Color color, float scale) {
        if (hud.fontSetting.getValue() == HUD.Font.VANILLA) {
            glPushMatrix();
            glTranslatef(x, y, 0);
            glScalef(scale, scale, 0);
            glTranslatef(-x, -y, 0);
            mc.fontRendererObj.drawStringWithShadow(text, x, y, color.getRGB());
            glScalef(1, 1, 0);
            glPopMatrix();
        } else
            currentFont.size(hud.fontSizeSetting.getInt()).drawStringWithShadow(text, x, y, color.getRGB());
    }

    public float getFontWidth(String text) {
        return getFontWidth(text, 1);
    }
    public float getFontWidth(String text, float scale) {
        if (hud.fontSetting.getValue() == HUD.Font.VANILLA)
            return mc.fontRendererObj.getStringWidth(text) * scale;
        return currentFont.size(hud.fontSizeSetting.getInt()).getWidth(text);
    }

    public float getFontHeight(String text) {
        return getFontHeight(text, 1);
    }
    public float getFontHeight(String text, float scale) {
        if (hud.fontSetting.getValue() == HUD.Font.VANILLA)
            return mc.fontRendererObj.FONT_HEIGHT + 2 * scale;
        return currentFont.size(hud.fontSizeSetting.getInt()).getHeight(text) + 3;
    }


    public GuiScreen getActiveClickGUI() {
        switch ((ClickGUI.ClickGuiMode) clickGUI.getSettingByName("Mode").getValue()) {
            case STRIFE:
                return guiClickGUI;
            case IMGUI:
                break;
        }
        return mc.currentScreen;
    }

    private static class AnimatedModule {
        public final float origWidth, origHeight, origX, origY;
        public float x, y, width, height;
        public final Module module;
        public long start;

        public AnimatedModule(Module module, float origX, float origY, float width, float height) {
            this.module = module;
            this.origX = origX;
            this.origY = origY;
            this.x = -1;
            this.y = -1;
            this.origWidth = width;
            this.origHeight = height;
            module.addToggleCallback(toggle -> start = System.currentTimeMillis());
        }
        public boolean isVisible(final float duration) {
            return (x > origX || (module.isEnabled() && !module.isHidden()));
        }
    }

}
