// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.visuals;

import gg.childtrafficking.smokex.gui.animation.animations.SmoothMoveAnimation;
import java.util.Comparator;
import gg.childtrafficking.smokex.gui.animation.Animation;
import gg.childtrafficking.smokex.gui.animation.animations.SmoothSizeAnimation;
import gg.childtrafficking.smokex.gui.animation.Easing;
import gg.childtrafficking.smokex.gui.element.elements.ContainerElement;
import gg.childtrafficking.smokex.gui.element.VAlignment;
import gg.childtrafficking.smokex.gui.element.HAlignment;
import java.util.Iterator;
import gg.childtrafficking.smokex.module.ModuleManager;
import gg.childtrafficking.smokex.SmokeXClient;
import gg.childtrafficking.smokex.utils.render.RenderingUtils;
import gg.childtrafficking.smokex.gui.element.Element;
import java.awt.Color;
import gg.childtrafficking.smokex.gui.element.elements.RectElement;
import net.minecraft.client.gui.GuiChat;
import gg.childtrafficking.smokex.gui.element.elements.TextElement;
import wtf.eviate.protection.impl.UserCache;
import java.util.ArrayList;
import gg.childtrafficking.smokex.event.events.player.EventUpdate;
import gg.childtrafficking.smokex.event.events.render.EventRender2D;
import gg.childtrafficking.smokex.event.EventListener;
import net.minecraft.client.gui.ScaledResolution;
import java.util.List;
import gg.childtrafficking.smokex.module.modules.visuals.hud.SecondaryColorMode;
import gg.childtrafficking.smokex.module.modules.visuals.hud.ColorMode;
import gg.childtrafficking.smokex.property.properties.EnumProperty;
import gg.childtrafficking.smokex.property.properties.BooleanProperty;
import gg.childtrafficking.smokex.module.ModuleCategory;
import gg.childtrafficking.smokex.module.ModuleInfo;
import gg.childtrafficking.smokex.module.Module;

@ModuleInfo(name = "HUD", renderName = "HUD", description = "Displays useful information.", category = ModuleCategory.VISUALS)
public final class HUDModule extends Module
{
    public String watermark;
    private final BooleanProperty fagMode;
    private final BooleanProperty customFont;
    private final BooleanProperty watermarkBackground;
    private final BooleanProperty wavyProperty;
    public final EnumProperty<ColorMode> colorModeEnumProperty;
    public final EnumProperty<SecondaryColorMode> secondaryColorModeEnumProperty;
    public List<Module> savedModules;
    public boolean panic;
    private int color;
    private int topColor;
    float offsetY;
    public float aOffset;
    ScaledResolution scaledResolution;
    private final EventListener<EventRender2D> eventRender2D;
    private final EventListener<EventUpdate> eventUpdate;
    
    public HUDModule() {
        this.watermark = "Smoke 1.10.0";
        this.fagMode = new BooleanProperty("Fag", false);
        this.customFont = new BooleanProperty("Custom Font", true);
        this.watermarkBackground = new BooleanProperty("Watermark Background", false);
        this.wavyProperty = new BooleanProperty("Wave", "Wave", true, () -> !this.fagMode.getValue());
        this.colorModeEnumProperty = new EnumProperty<ColorMode>("Color", "Color", ColorMode.RED, () -> !this.fagMode.getValue());
        this.secondaryColorModeEnumProperty = new EnumProperty<SecondaryColorMode>("OtherColor", "Other Color", SecondaryColorMode.RED, () -> !this.fagMode.getValue());
        this.savedModules = new ArrayList<Module>();
        this.color = 0;
        this.topColor = 0;
        this.offsetY = 0.0f;
        this.aOffset = 0.0f;
        this.scaledResolution = new ScaledResolution(this.mc);
        this.eventRender2D = (event -> {
            final String ok = String.format("%04d", UserCache.getUid());
            final TextElement grenjr = (TextElement)this.getElement("userinfo");
            grenjr.setText("§fRelease 1.10.0 - §r" + UserCache.getUsername() + " §f- §r" + ok);
            if (this.mc.currentScreen instanceof GuiChat) {
                grenjr.setY(20.0f);
            }
            else {
                grenjr.setY(5.0f);
            }
            final RectElement rectElement = (RectElement)this.getElement("watermarkBackground");
            rectElement.setColor(new Color(0, 0, 0, 255).getRGB());
            this.updateWatermark();
            int moduleIndex = 0;
            this.getElement("modules").getElements().iterator();
            final Iterator iterator;
            while (iterator.hasNext()) {
                final Element element = iterator.next();
                if (element instanceof TextElement) {
                    final TextElement textElement = (TextElement)element;
                    if (this.fagMode.getValue()) {
                        this.aOffset = (float)(moduleIndex * 100);
                        this.color = RenderingUtils.rainbow(4.0f, 0.9f, 0.9f, (long)this.aOffset);
                    }
                    else {
                        this.aOffset = (System.currentTimeMillis() + moduleIndex * 100L) % 2000L / 1000.0f;
                        this.color = (this.wavyProperty.getValue() ? RenderingUtils.fadeBetween(this.colorModeEnumProperty.getValue().getColor(), this.secondaryColorModeEnumProperty.getValue().getColor(), this.aOffset) : this.colorModeEnumProperty.getValue().getColor());
                    }
                    if (moduleIndex == 0) {
                        this.topColor = this.color;
                    }
                    textElement.setColor(this.color);
                }
                ++moduleIndex;
            }
            final List<Element> elements = this.getElement("leavingModules").getElements();
            for (int i = 0; i < elements.size(); ++i) {
                if (elements.get(i).getAnimation() == null) {
                    elements.remove(elements.get(i));
                    --i;
                }
            }
            ((RectElement)this.getElement("watermarkBackgroundTop")).setColor(this.topColor);
            ((TextElement)this.getElement("watermark")).setColor(this.topColor);
            grenjr.setColor(this.color);
            return;
        });
        this.eventUpdate = (event -> {
            if (this.watermarkBackground.getValue()) {
                this.getElement("watermarkBackgroundTop").visible = true;
                this.getElement("watermarkBackground").visible = true;
            }
            else {
                this.getElement("watermarkBackgroundTop").visible = false;
                this.getElement("watermarkBackground").visible = false;
            }
            if (SmokeXClient.getInstance().fontRenderer.getMcFont() == this.customFont.getValue()) {
                SmokeXClient.getInstance().fontRenderer.setMcFont(!this.customFont.getValue());
                ModuleManager.getInstance(HUDModule.class).sortModules();
            }
            final TextElement Watermark = (TextElement)this.getElement("watermark");
            if (!Watermark.getText().equals(this.watermark)) {
                Watermark.setText(this.watermark);
            }
            this.sortModules();
        });
    }
    
    @Override
    public void init() {
        this.addElement(new RectElement("watermarkBackgroundTop", 3.0f, 1.0f, 0.0f, 3.0f, -1));
        this.addElement(new RectElement("watermarkBackground", 3.0f, 3.0f, 0.0f, 13.0f, new Color(0, 0, 0, 0).getRGB()));
        this.addElement(new TextElement("watermark", 5.0f, 5.0f, "Smoke X", -1277204));
        this.addElement(new TextElement("userinfo", 5.0f, 5.0f, "Release Build - " + UserCache.getUsername() + " - " + UserCache.getUid(), Color.GRAY.getRGB())).setHAlignment(HAlignment.RIGHT).setVAlignment(VAlignment.BOTTOM);
        this.addElement(new ContainerElement("modules", 10.0f, 15.0f)).setHAlignment(HAlignment.RIGHT);
        this.addElement(new ContainerElement("leavingModules", 10.0f, 15.0f)).setHAlignment(HAlignment.RIGHT);
        this.setHidden(true);
        this.watermark = "Smoke 1.10.0";
        this.toggle();
        super.init();
    }
    
    @Override
    public void onEnable() {
        if (this.panic) {
            for (final Module module : this.savedModules) {
                if (!module.isToggled()) {
                    module.toggle();
                }
            }
        }
        this.panic = false;
        this.savedModules.clear();
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        for (final Module module : SmokeXClient.getInstance().getModuleManager().getModules()) {
            if (module.isToggled()) {
                this.savedModules.add(module);
                module.toggle();
            }
        }
        super.onDisable();
    }
    
    public void updateWatermark() {
        final RectElement rectElement = (RectElement)this.getElement("watermarkBackground");
        final RectElement rectElement2 = (RectElement)this.getElement("watermarkBackgroundTop");
        float targetWidth = SmokeXClient.getInstance().fontRenderer.getWidth(this.watermark);
        if (this.customFont.getValue()) {
            targetWidth -= 2.0f;
        }
        if (rectElement.getWidth() != targetWidth) {
            if (rectElement.getAnimation() != null) {
                if (rectElement.getAnimation().width != targetWidth) {
                    rectElement.setAnimation(new SmoothSizeAnimation(targetWidth, rectElement.getHeight() + (this.mc.gameSettings.showDebugInfo ? 100 : 0), 150L, Easing.EASE_OUT));
                }
            }
            else {
                rectElement.setAnimation(new SmoothSizeAnimation(targetWidth, rectElement.getHeight() + (this.mc.gameSettings.showDebugInfo ? 100 : 0), 150L, Easing.EASE_OUT));
            }
        }
        if (rectElement2.getWidth() != targetWidth) {
            if (rectElement2.getAnimation() != null) {
                if (rectElement2.getAnimation().width != targetWidth) {
                    rectElement2.setAnimation(new SmoothSizeAnimation(targetWidth, rectElement2.getHeight() + (this.mc.gameSettings.showDebugInfo ? 100 : 0), 150L, Easing.EASE_OUT));
                }
            }
            else {
                rectElement2.setAnimation(new SmoothSizeAnimation(targetWidth, rectElement2.getHeight() + (this.mc.gameSettings.showDebugInfo ? 100 : 0), 150L, Easing.EASE_OUT));
            }
        }
    }
    
    public void sortModules() {
        this.offsetY = 0.0f;
        this.getElement("modules").getElements().sort(Comparator.comparingDouble(e -> -e.getWidth()));
        for (final Element element : this.getElement("modules").getElements()) {
            if (!element.isAtPosition(0.0, this.offsetY + (this.mc.gameSettings.showDebugInfo ? 100 : 0))) {
                if (element.getAnimation() != null) {
                    if (element.getAnimation().x != 0.0f || element.getAnimation().y != this.offsetY + (this.mc.gameSettings.showDebugInfo ? 100 : 0)) {
                        element.setAnimation(new SmoothMoveAnimation(0.0f, this.offsetY + (this.mc.gameSettings.showDebugInfo ? 100 : 0), 450L, Easing.EASE_OUT));
                    }
                }
                else {
                    element.setAnimation(new SmoothMoveAnimation(0.0f, this.offsetY + (this.mc.gameSettings.showDebugInfo ? 100 : 0), 450L, Easing.EASE_OUT));
                }
            }
            this.offsetY += 10.0f;
        }
    }
    
    public int getColor() {
        return this.color;
    }
    
    public int getTopColor() {
        return this.topColor;
    }
    
    public float getOffsetY() {
        return this.offsetY;
    }
}
