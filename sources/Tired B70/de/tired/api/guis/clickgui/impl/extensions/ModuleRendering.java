package de.tired.api.guis.clickgui.impl.extensions;

import de.tired.api.extension.Extension;
import de.tired.api.guis.clickgui.impl.ClickGui;
import de.tired.api.guis.clickgui.impl.clickable.Clickable;
import de.tired.api.util.font.FontManager;
import de.tired.api.util.render.Scissoring;
import de.tired.api.util.render.Translate;
import de.tired.api.util.shader.renderapi.AnimationUtil;
import de.tired.interfaces.IHook;
import de.tired.module.Module;
import de.tired.module.impl.list.visual.ClickGUI;
import de.tired.tired.Tired;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class ModuleRendering implements IHook {

    public final Module module;

    public double x, y;

    public static boolean allowInteraction = true;

    private boolean hover;

    public int scrollAmount = 0;

    public Translate translate;

    public float animation = 0;

    public float circleAnimation = 0;

    public ModuleRendering(final Module mod) {
        this.module = mod;
        this.translate = new Translate(0, 0);
    }

    public void renderTextLayer(int mouseX, int mouseY, int x, int y) {


        //font
        {

            animation = (float) AnimationUtil.getAnimationState(animation, hover ? 2 : 0, 15);
            FontManager.robotoF.drawString(module.getName(), Clickable.calculateMiddle(module.getName(), FontManager.robotoF, x, Clickable.getWidth()) + animation, y + 8.5f, -1);

        }

    }

    public void renderDownLayer(int x, int y) {
        circleAnimation = (float) AnimationUtil.getAnimationState(circleAnimation, module.isState() ? 70 : 0, 322);
        Gui.drawRect(x, y, x + Clickable.getWidth(), y + Clickable.getHeight() + 1, new Color(30, 33, 39).getRGB());

    }

    public void renderEnableLayer(int mouseX, int mouseY, int x, int y) {

        circleAnimation = (float) AnimationUtil.getAnimationState(circleAnimation, module.isState() ? 70 : 0, 322);
        Scissoring.SCISSORING.startScissor();
        Scissoring.SCISSORING.doScissor((float) x, (float) y, (float) (x + Clickable.getWidth()), (float) (y + Clickable.getHeight()));
        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.renderObjectCircle(x + Clickable.getWidth() / 2, y + 3, circleAnimation, ClickGUI.getInstance().colorPickerSetting.ColorPickerC.getRGB());
        Scissoring.SCISSORING.disableScissor();
    }


    public void renderLayer1(int mouseX, int mouseY, int x, int y, boolean rectangle) {

        if (allowInteraction) {
            this.x = x;
            this.y = y;
        }


        hover = Clickable.isOver(x, y, (int) Clickable.getWidth(), (int) Clickable.getHeight(), mouseX, mouseY);


        Gui.drawRect(x, y, x + Clickable.getWidth(), y + Clickable.getHeight(), new Color(30, 33, 39).getRGB());

        if (hover) {
            Gui.drawRect(x, y, x + Clickable.getWidth(), y + Clickable.getHeight(), Integer.MIN_VALUE);
        }


    }

    public void mouseClicked(int mouseButton) {
        if (hover) {
            if (allowInteraction) {
                if (mouseButton == 0) {
                    module.executeMod();
                }
            }
            if (mouseButton == 1 && allowInteraction) {

                Tired.INSTANCE.usingModule = this.module;

                ClickGui.clicks = 0;
                ClickGui.addTimes = 0;

                allowInteraction = false;
            }
        }

    }

}
