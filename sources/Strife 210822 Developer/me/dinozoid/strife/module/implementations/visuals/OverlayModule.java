package me.dinozoid.strife.module.implementations.visuals;

import me.dinozoid.strife.StrifeClient;
import me.dinozoid.strife.alpine.listener.EventHandler;
import me.dinozoid.strife.alpine.listener.Listener;
import me.dinozoid.strife.event.implementations.render.Render2DEvent;
import me.dinozoid.strife.module.Category;
import me.dinozoid.strife.module.Module;
import me.dinozoid.strife.module.ModuleInfo;
import me.dinozoid.strife.property.Property;
import me.dinozoid.strife.property.implementations.DoubleProperty;
import me.dinozoid.strife.property.implementations.EnumProperty;
import me.dinozoid.strife.property.implementations.HueProperty;
import me.dinozoid.strife.property.implementations.MultiSelectEnumProperty;
import me.dinozoid.strife.util.render.RenderUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ModuleInfo(name = "HUD", renderName = "HUD", description = "The Heads-Up-Display module.", category = Category.VISUALS)
public class OverlayModule extends Module {

    private final EnumProperty<Font> fontProperty = new EnumProperty("Font", Font.VANILLA);
    private final EnumProperty<Background> backgroundProperty = new EnumProperty("Background", Background.OUTLINE);
    private final EnumProperty<ToggleAnimation> animationProperty = new EnumProperty("Animation", ToggleAnimation.STRIFE);
    private final MultiSelectEnumProperty<Element> elementsProperty = new MultiSelectEnumProperty("Elements", Element.ARRAYLIST, Element.SPEED, Element.NAME);

    private final DoubleProperty fontSizeProperty = new DoubleProperty("Font Size", 19, 14, 36, 1, Property.Representation.INT, () -> fontProperty.value() == Font.CLIENT);
    private final DoubleProperty backgroundAlphaProperty = new DoubleProperty("Background Alpha", 120, 0, 255, 1, Property.Representation.INT);

    private static final EnumProperty<ColorMode> colorModeProperty = new EnumProperty("Color Mode", ColorMode.ASTOLFO);
    private static final HueProperty colorProperty = new HueProperty("Color", 0D, () -> colorModeProperty.value() == ColorMode.STATIC || colorModeProperty.value() == ColorMode.PULSE);
    private final Property<Boolean> logoProperty = new Property("Logo", true);

    private List<AnimatedModule> animatedModules = new ArrayList<>();

    private ResourceLocation strifeLogo;

    @Override
    public void init() {
        super.init();
        toggle();
    }

    @EventHandler
    private final Listener<Render2DEvent> render2DListener = new Listener<>(event -> {
        if(strifeLogo == null) strifeLogo = new ResourceLocation("strife/gui/Strife-128x.png");
        if (!mc.gameSettings.showDebugInfo) {
            if(logoProperty.value()) {
                RenderUtil.drawImage(strifeLogo, 15, 15, 50, 50);
            }
            if (animatedModules.isEmpty()) {
                for (Module module : StrifeClient.INSTANCE.moduleRepository().modules()) {
                    animatedModules.add(new AnimatedModule(module, -getWidth(module.renderName()) - 3, 0, getWidth(module.renderName()), 0));
                }
            }
            ScaledResolution sc = new ScaledResolution(mc);
            for (Element element : elementsProperty.values()) {
                if (elementsProperty.selected(element)) {
                    switch (element) {
                        case NAME: {
                            if(!logoProperty.value())
                                drawStringWithShadow(StrifeClient.NAME.charAt(0) + "\u00A77" + StrifeClient.NAME.substring(1), 2, 2, getColor(200));
                        }
                        break;
                        case SPEED: {
                            double bps = Math.hypot(mc.thePlayer.posX - mc.thePlayer.prevPosX, mc.thePlayer.posZ - mc.thePlayer.prevPosZ) * mc.timer.timerSpeed * 20;
                            drawStringWithShadow(Math.round(bps * 100.0) / 100.0 + " blocks/sec", 0, sc.getScaledHeight() - getHeight(Math.round(bps * 100.0) / 100.0 + " blocks/sec"), new Color(255, 255, 255).getRGB());
                        }
                        break;
                        case ARRAYLIST: {
                            animatedModules.sort(Comparator.comparingDouble(module -> getWidth(((AnimatedModule) module).module().renderName())).reversed());
                            List<AnimatedModule> activeModules = animatedModules.stream().filter(AnimatedModule::toggled).collect(Collectors.toList());
                            int i = 0;
                            for (AnimatedModule animatedModule : animatedModules) {
                                Module module = animatedModule.module();
                                if (module.toggled()) {
                                    switch (animationProperty.value()) {
                                        case ASTOLFO:
                                            break;
                                        case STRIFE:
                                            animatedModule.x(RenderUtil.animate(getWidth(module.renderName()), animatedModule.x(), 0.1D));
                                            animatedModule.y(RenderUtil.animate(i * getHeight(module.renderName()), animatedModule.y(), 0.1D));
                                            animatedModule.height(RenderUtil.animate(getHeight(module.renderName()), animatedModule.height(), 0.1D));
                                            break;
                                    }
                                } else {
                                    switch (animationProperty.value()) {
                                        case ASTOLFO:
                                            break;
                                        case STRIFE:
                                            animatedModule.x(RenderUtil.animate(animatedModule.origX(), animatedModule.x(), 0.1D));
                                            animatedModule.y(RenderUtil.animate(animatedModule.origY(), animatedModule.y(), 0.001D));
                                            animatedModule.height(RenderUtil.animate(animatedModule.origHeight(), animatedModule.height(), 0.1D));
                                            break;
                                    }
                                }
                                if (animatedModule.toggled()) {
                                    switch (backgroundProperty.value()) {
                                        case BARLEFT:
                                            Gui.drawRect(sc.getScaledWidth() - animatedModule.x() - 3, animatedModule.y(), sc.getScaledWidth(), animatedModule.y() + animatedModule.height(), new Color(0, 0, 0, backgroundAlphaProperty.value().floatValue() / 255).getRGB());
                                            Gui.drawRect(sc.getScaledWidth() - animatedModule.x() - 3, animatedModule.y(), sc.getScaledWidth() - animatedModule.x() - 2, animatedModule.y() + animatedModule.height(), getColor(i * 200));
                                            drawStringWithShadow(module.renderName(), sc.getScaledWidth() - animatedModule.x(), animatedModule.y() + 1, getColor(i * 200));
                                            break;
                                        case BARRIGHT:
                                            Gui.drawRect(sc.getScaledWidth() - animatedModule.x() - 4, animatedModule.y(), sc.getScaledWidth(), animatedModule.y() + animatedModule.height(), new Color(0, 0, 0, backgroundAlphaProperty.value().floatValue() / 255).getRGB());
                                            Gui.drawRect(sc.getScaledWidth() - 1, animatedModule.y(), sc.getScaledWidth(), animatedModule.y() + animatedModule.height(), getColor(i * 200));
                                            drawStringWithShadow(module.renderName(), sc.getScaledWidth() - animatedModule.x() - 2, animatedModule.y() + 1, getColor(i * 200));
                                            break;
                                        case OUTLINE:
                                            Gui.drawRect(sc.getScaledWidth() - animatedModule.x() - 5, animatedModule.y(), sc.getScaledWidth(), animatedModule.y() + animatedModule.height(), new Color(0, 0, 0, backgroundAlphaProperty.value().floatValue() / 255).getRGB());
                                            Gui.drawRect(sc.getScaledWidth() - animatedModule.x() - 5, animatedModule.y(), sc.getScaledWidth() - animatedModule.x() - 4, animatedModule.y() + animatedModule.height(), getColor(i * 200));
                                            Gui.drawRect(sc.getScaledWidth() - animatedModule.x() - 5, animatedModule.y() + animatedModule.height() - 1, (activeModules.indexOf(animatedModule) >= activeModules.size() - 1 ? sc.getScaledWidth() : activeModules.indexOf(animatedModule) < activeModules.size() ? sc.getScaledWidth() - 4 - getWidth(activeModules.get(activeModules.indexOf(animatedModule) + 1).module().renderName()) : null), animatedModule.y() + animatedModule.height(), getColor(i * 200));
                                            drawStringWithShadow(module.renderName(), sc.getScaledWidth() - animatedModule.x() - 2, animatedModule.y() + 1, getColor(i * 200));
                                            break;

                                        case NONE:
                                            Gui.drawRect(sc.getScaledWidth() - animatedModule.x() - 3, animatedModule.y(), sc.getScaledWidth(), animatedModule.y() + animatedModule.height(), new Color(0, 0, 0, backgroundAlphaProperty.value().floatValue() / 255).getRGB());
                                            break;
                                    }
                                    i++;
                                }
                            }
                        }
                        break;
                    }
                }
            }
        }
    });

    private void drawStringWithShadow(String text, float x, float y, int color) {
        if (fontProperty.value() == Font.VANILLA) {
            // TODO: Handle scaling.
            mc.fontRendererObj.drawStringWithShadow(text, x, y, color);
        } else {
            StrifeClient.INSTANCE.fontRepository().currentFont().size(fontSizeProperty.value().intValue()).drawStringWithShadow(text, x, y, color);
        }
    }

    private float getWidth(String text) {
        if (fontProperty.value() == Font.VANILLA) {
            // TODO: Handle scaling.
            return mc.fontRendererObj.getStringWidth(text);
        } else {
            return StrifeClient.INSTANCE.fontRepository().currentFont().size(fontSizeProperty.value().intValue()).getWidth(text);
        }
    }

    private float getHeight(String text) {
        if (fontProperty.value() == Font.VANILLA) {
            // TODO: Handle scaling.
            return mc.fontRendererObj.FONT_HEIGHT + 2;
        } else {
            return StrifeClient.INSTANCE.fontRepository().currentFont().size(fontSizeProperty.value().intValue()).getHeight(text) + 3;
        }
    }

    public static int getColor(int index) {
        switch (colorModeProperty.value()) {
            case PULSE:
                return RenderUtil.fade(Color.getHSBColor(colorProperty.value().floatValue(), 0.8f, 1f), 4 * 100, index);
            case ASTOLFO:
                return RenderUtil.astolfo(4, 0.5f, 1f, index);
            case RAINBOW:
                return RenderUtil.rainbow(4, 0.4f, 0.8f, index);
        }
        return Color.getHSBColor(colorProperty.value().floatValue(), 100, 100).getRGB();
    }

    private final class AnimatedModule {
        private Module module;
        private float origX, origY, x, y, origWidth, origHeight, width, height;

        public AnimatedModule(Module module, float origX, float origY, float width, float height) {
            this.module = module;
            this.origX = origX;
            this.origY = origY;
            this.x = -1;
            this.y = -1;
            this.origWidth = width;
            this.origHeight = height;
        }

        public boolean hidden() {
            return module.hidden();
        }

        public boolean toggled() {
            return module.toggled();
        }

        public boolean visible() {
//            System.out.println((int)origX + " " + (int)x);
            return x == -1 && module.toggled() || (int) x > (int) origX;
        }

        public Module module() {
            return module;
        }

        public void module(Module module) {
            this.module = module;
        }

        public float origX() {
            return origX;
        }

        public void origX(float origX) {
            this.origX = origX;
        }

        public float origY() {
            return origY;
        }

        public void origY(float origY) {
            this.origY = origY;
        }

        public float x() {
            return x;
        }

        public void x(float x) {
            this.x = x;
        }

        public float y() {
            return y;
        }

        public void y(float y) {
            this.y = y;
        }

        public float width() {
            return width;
        }

        public void width(float width) {
            this.width = width;
        }

        public void height(float height) {
            this.height = height;
        }

        public float height() {
            return height;
        }

        public float origWidth() {
            return origWidth;
        }

        public void origWidth(float origWidth) {
            this.origWidth = origWidth;
        }

        public float origHeight() {
            return origHeight;
        }

        public void origHeight(float origHeight) {
            this.origHeight = origHeight;
        }
    }

    private enum Font {
        CLIENT, VANILLA
    }

    private enum Background {
        OUTLINE, BARLEFT, BARRIGHT, NONE
    }

    private enum ToggleAnimation {
        STRIFE, ASTOLFO
    }

    private enum Element {
        NAME, ARRAYLIST, TIME, NOTIFICATIONS, FPS, SPEED, COORDS, POTIONSTATUS, BOSSBAR, ARMORHUD
    }

    private enum ColorMode {
        ASTOLFO, PULSE, RAINBOW, STATIC
    }

}
