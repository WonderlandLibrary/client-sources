package dev.darkmoon.client.module.impl.render;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.event.render.EventRender2D;
import dev.darkmoon.client.manager.dragging.DragManager;
import dev.darkmoon.client.manager.dragging.Draggable;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.module.setting.impl.ModeSetting;
import dev.darkmoon.client.utility.math.BlurUtility;
import dev.darkmoon.client.utility.render.ColorUtility2;
import dev.darkmoon.client.utility.render.RenderUtility;
import dev.darkmoon.client.utility.render.SmartScissor;
import dev.darkmoon.client.utility.render.animation.AnimationMath;
import dev.darkmoon.client.utility.render.font.Fonts;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@ModuleAnnotation(name = "Keybinds", category = Category.RENDER)
public class Keybinds extends Module {
    private final Draggable keybindsDraggable = DragManager.create(this, "Keybinds", 200, 200);

    public float animHeight = 0.0F;
    public float animWidth = 0.0F;
    public boolean binds = false;
    int color = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0].getRGB();
    int color2 = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1].getRGB();
    Color color11 = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0];
    Color color22 = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1];
    Color gradientColor11 = ColorUtility2.interpolateColorsBackAndForth(4, 0, color11, color22, false);
    Color gradientColor22 = ColorUtility2.interpolateColorsBackAndForth(4, 90, color11, color22, false);
    public Keybinds() {
    }

    @EventTarget
    public void onRender2D(EventRender2D event) {
        DarkMoon.getInstance().getScaleMath().pushScale();
        List<Module> sortedBinds = (List)DarkMoon.getInstance().getModuleManager().getModules().stream().filter((modulex) -> {
            return modulex.getBind() != 0 && modulex.isEnabled();
        }).sorted(Comparator.comparing((modulex) -> {
            return Fonts.nunitoBold15.getStringWidth(modulex.getName());
        }, Comparator.reverseOrder())).collect(Collectors.toList());
        int glowColor = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0].getRGB();
        int glowColor2 = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1].getRGB();
        int offset = -1;
        int width = 75;
        Iterator var8;
        if (!sortedBinds.isEmpty()) {
                width = (Integer) sortedBinds.stream().max(Comparator.comparingInt((modulex) -> {
                    return Fonts.nunitoBold15.getStringWidth(this.getText(modulex));
                })).map((modulex) -> {
                    return Fonts.nunitoBold15.getStringWidth(this.getText(modulex));
                }).get() + 16;
            }
            if (width < 80) {
                width = 75;
            }

            offset = 0;

            for (Module module : sortedBinds) {
                offset += 11;
        }

        this.animHeight = AnimationMath.fast(this.animHeight, (float)offset, 13.0F);
        this.animWidth = AnimationMath.fast(this.animWidth, (float)width, 13.0F);
        this.keybindsDraggable.setWidth((float)(width + 10));
        this.keybindsDraggable.setHeight((float)(21 + offset));
        RenderUtility.drawDarkMoonShader((float)this.keybindsDraggable.getX(), (float)this.keybindsDraggable.getY(), this.animWidth, 18.5F + this.animHeight, 7.0F);
        Fonts.tenacityBold20.drawCenteredString("key binds", (float) this.keybindsDraggable.getX() + this.animWidth / 2.0F + 1.0F, (float) (this.keybindsDraggable.getY() + 7), -1);
        SmartScissor.push();
        SmartScissor.setFromComponentCoordinates((double)this.keybindsDraggable.getX(), (double)this.keybindsDraggable.getY(), (double)(width + 40), (double)(19.0F + this.animHeight));
        offset = 0;

        for (Module module : sortedBinds) {
            int finalOffset = offset;
            Fonts.tenacityBold14.drawString(module.getName(), (float)(this.keybindsDraggable.getX() + 3), (float)this.keybindsDraggable.getY() + 22.0F + (float) finalOffset, Color.WHITE.getRGB());
            String bindText = "[" + (module.bind < 0 ? "MOUSE " + module.getMouseBind() : Keyboard.getKeyName(module.getBind())) + "]";
            Fonts.tenacityBold14.drawString(bindText, (float)this.keybindsDraggable.getX() + this.animWidth - (float)Fonts.tenacityBold13.getStringWidth(bindText) - 4.0F, (float)(this.keybindsDraggable.getY() + 22 + offset), Color.WHITE.getRGB());
            offset += 11;
        }

        SmartScissor.unset();
        SmartScissor.pop();
        DarkMoon.getInstance().getScaleMath().popScale();
    }

    private String getText(Module module) {
        String var10000 = module.getName().toLowerCase();
        return var10000 + "[" + (module.bind < 0 ? "MOUSE" + module.getMouseBind() : Keyboard.getKeyName(module.getBind())) + "]";
    }
}
