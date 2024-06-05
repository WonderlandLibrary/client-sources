package koks.theme.themes;

import com.sun.org.apache.xpath.internal.operations.Mod;
import koks.Koks;
import koks.hud.tabgui.CategoryTab;
import koks.hud.tabgui.ModuleTab;
import koks.modules.Module;
import koks.modules.impl.visuals.ClearTag;
import koks.theme.Theme;
import koks.utilities.CustomFont;
import koks.utilities.DeltaTime;
import koks.utilities.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.UnicodeFont;

import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author avox | lmao | kroko
 * @created on 07.09.2020 : 11:39
 */
public class Moon extends Theme {

    public final CustomFont unicodeFont = new CustomFont("fonts/Comfortaa-SemiBold.ttf", 28);
    public final CustomFont tabGuiFont = new CustomFont("fonts/Comfortaa-SemiBold.ttf", 18);

    public final CustomFont arrayListFont = new CustomFont("fonts/Comfortaa-SemiBold.ttf", 16);
    public final CustomFont moduleFont = new CustomFont("fonts/Comfortaa-SemiBold.ttf", 15);

    public Moon() {
        super(ThemeCategory.MOON);
        setUpTabGUI(4, 27, 80, 14, true, true, moduleFont);
    }

    @Override
    public void arrayListDesign() {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        int[] y = {0};


        List<Module> list = getSortedArrayList();

        for (int i = 0; i < list.size(); i++) {
            Module module = list.get(i);
            String finalText = Koks.getKoks().moduleManager.getModule(ClearTag.class).isToggled() ? module.getDisplayName() : module.getNameForArrayList();

            if (module.isToggled()) {
                if (module.getAnimationModule().getSlideAnimation() < arrayListFont.getStringWidth(finalText) + 2)
                    module.getAnimationModule().setSlideAnimation(module.getAnimationModule().getSlideAnimation() + (module.getAnimationModule().getSlideAnimation() / 45) * DeltaTime.getDeltaTime());
                if (module.getAnimationModule().getSlideAnimation() > arrayListFont.getStringWidth(finalText) + 2)
                    module.getAnimationModule().setSlideAnimation(arrayListFont.getStringWidth(finalText) + 2);

                if (module.getAnimationModule().getYAnimation() < arrayListFont.FONT_HEIGHT)
                    module.getAnimationModule().setYAnimation(module.getAnimationModule().getYAnimation() + 0.075 * DeltaTime.getDeltaTime());
                if (module.getAnimationModule().getYAnimation() > arrayListFont.FONT_HEIGHT)
                    module.getAnimationModule().setYAnimation(arrayListFont.FONT_HEIGHT);
            } else {
                if (module.getAnimationModule().getSlideAnimation() > 0)
                    module.getAnimationModule().setSlideAnimation(module.getAnimationModule().getSlideAnimation() - module.getAnimationModule().getSlideAnimation() / 90 * DeltaTime.getDeltaTime());
                if (module.getAnimationModule().getSlideAnimation() < 0)
                    module.getAnimationModule().setSlideAnimation(0);
                if (module.getAnimationModule().getYAnimation() > 0 && module.getAnimationModule().getSlideAnimation() < arrayListFont.getStringWidth(finalText) / 16)
                    module.getAnimationModule().setYAnimation(module.getAnimationModule().getYAnimation() - 0.075 * DeltaTime.getDeltaTime());
                if (module.getAnimationModule().getYAnimation() < 0)
                    module.getAnimationModule().setYAnimation(0);
            }

            if (module.getAnimationModule().getSlideAnimation() > 0.5) {

                Gui.drawRect(scaledResolution.getScaledWidth() - module.getAnimationModule().getSlideAnimation() - 1, y[0], scaledResolution.getScaledWidth(), y[0] + module.getAnimationModule().getYAnimation(), Integer.MIN_VALUE);

                try {
                    Module nextEnabledModule = returnNextToggledModule(list, i + 1);
                    double difference = module.getAnimationModule().getSlideAnimation() - nextEnabledModule.getAnimationModule().getSlideAnimation();
                    Gui.drawRect(scaledResolution.getScaledWidth() - module.getAnimationModule().getSlideAnimation() - 2, y[0] + module.getAnimationModule().getYAnimation() - 1, scaledResolution.getScaledWidth() - module.getAnimationModule().getSlideAnimation() + difference - 1, y[0] + module.getAnimationModule().getYAnimation(), Koks.getKoks().client_color.getRGB());
                    Gui.drawRect(scaledResolution.getScaledWidth() - module.getAnimationModule().getSlideAnimation() - 2, y[0], scaledResolution.getScaledWidth() - module.getAnimationModule().getSlideAnimation() - 1, y[0] + module.getAnimationModule().getYAnimation(), Koks.getKoks().client_color.getRGB());
                } catch (Exception e) {
                    Gui.drawRect(scaledResolution.getScaledWidth() - module.getAnimationModule().getSlideAnimation() - 1, y[0] + module.getAnimationModule().getYAnimation() - 1, scaledResolution.getScaledWidth(), y[0] + module.getAnimationModule().getYAnimation(), Koks.getKoks().client_color.getRGB());
                    Gui.drawRect(scaledResolution.getScaledWidth() - module.getAnimationModule().getSlideAnimation() - 2, y[0], scaledResolution.getScaledWidth() - module.getAnimationModule().getSlideAnimation() - 1, y[0] + module.getAnimationModule().getYAnimation(), Koks.getKoks().client_color.getRGB());

                }

                arrayListFont.drawStringWithShadow(finalText, (float) (scaledResolution.getScaledWidth() - module.getAnimationModule().getSlideAnimation()), y[0], Koks.getKoks().client_color.getRGB());
                y[0] += module.getAnimationModule().getYAnimation();
            }
        }


        /*
        for (int i = 0; i < getSortedArrayList().size(); i++) {
            Module module = getSortedArrayList().get(i);

            String finalText = Koks.getKoks().moduleManager.getModule(ClearTag.class).isToggled() ? module.getDisplayName() : module.getNameForArrayList();


            Gui.drawRect(scaledResolution.getScaledWidth() - module.getAnimationModule().getSlideAnimation() - 1, y[0], scaledResolution.getScaledWidth(), y[0] + module.getAnimationModule().getYAnimation(), Integer.MIN_VALUE);

            Module nextEnabledModule = null;
            if (getSortedArrayList().indexOf(module) + 1 < getSortedArrayList().size()) {
                nextEnabledModule = getSortedArrayList().get(i + 1);
                double difference = module.getAnimationModule().getSlideAnimation() - nextEnabledModule.getAnimationModule().getSlideAnimation();
                Gui.drawRect(scaledResolution.getScaledWidth() - module.getAnimationModule().getSlideAnimation() - 1, y[0] + module.getAnimationModule().getYAnimation() - 1, scaledResolution.getScaledWidth() - module.getAnimationModule().getSlideAnimation() + difference - 1, y[0] + module.getAnimationModule().getYAnimation(), Koks.getKoks().client_color.getRGB());
                Gui.drawRect(scaledResolution.getScaledWidth() - module.getAnimationModule().getSlideAnimation() - 2, y[0], scaledResolution.getScaledWidth() - module.getAnimationModule().getSlideAnimation() - 1, y[0] + module.getAnimationModule().getYAnimation(), Koks.getKoks().client_color.getRGB());
            }


            arrayListFont.drawStringWithShadow(finalText, (float) (scaledResolution.getScaledWidth() - module.getAnimationModule().getSlideAnimation()), y[0], Koks.getKoks().client_color.getRGB());
            y[0] += module.getAnimationModule().getYAnimation();
        }
        */
    }

    public List<Module> getSortedArrayList() {
        List list = Koks.getKoks().moduleManager.getModules().stream().filter(Module::isVisible).sorted(Comparator.comparingDouble(module -> -arrayListFont.getStringWidth(Koks.getKoks().moduleManager.getModule(ClearTag.class).isToggled() ? module.getDisplayName() : module.getNameForArrayList()))).collect(Collectors.toList());
        return list;
    }

    @Override
    public void categoryTabGUI(CategoryTab categoryTab, int x, int y, int width, int height) {
        Gui.drawRect(x, y, x + width, y + height, Integer.MIN_VALUE);
        if (categoryTab.isCurrentCategory())
            Gui.drawRect(x, y, x + width, y + height, new Color(0, 0, 0, 100).getRGB());
        tabGuiFont.drawStringWithShadow(categoryTab.category.name().substring(0, 1).toUpperCase() + categoryTab.category.name().substring(1).toLowerCase(), x + categoryTab.animationX, y + height / 2 - tabGuiFont.FONT_HEIGHT / 2, -1);
    }

    @Override
    public void moduleTabGUI(ModuleTab moduleTab, int x, int y, int width, int height) {
        Gui.drawRect(x, y, x + width, y + height, Integer.MIN_VALUE);

        if (moduleTab.isSelectedModule())
            Gui.drawRect(x, y, x + width, y + height, new Color(0, 0, 0, 100).getRGB());
        moduleFont.drawStringWithShadow(moduleTab.getModule().getDisplayName(), x + 2, y + height / 2 - moduleFont.FONT_HEIGHT / 2, moduleTab.getModule().isToggled() ? -1 : new Color(255, 255, 255, 150).getRGB());
    }

    @Override
    public void waterMarkDesign() {

        GL11.glPushMatrix();
        GlStateManager.disableAlpha();
        GlStateManager.enableBlend();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glColor4f(1, 1, 1, 1);
        getRenderUtils().drawImage(new ResourceLocation("client/shadows/arraylistshadow.png"), -13, -5, 70, 40, false);

        unicodeFont.drawStringWithShadow(Koks.getKoks().CLIENT_NAME.substring(0, 1), 5, 5, Koks.getKoks().client_color.getRGB());
        unicodeFont.drawStringWithShadow(Koks.getKoks().CLIENT_NAME.substring(1), 5 + unicodeFont.getStringWidth(Koks.getKoks().CLIENT_NAME.substring(0, 1)), 5, -1);

        String cords = Math.round(Minecraft.getMinecraft().thePlayer.posX) + " " + Math.round(Minecraft.getMinecraft().thePlayer.posY) + " " + Math.round(Minecraft.getMinecraft().thePlayer.posZ);

        GL11.glColor4f(1, 1, 1, 1);
        getRenderUtils().drawImage(new ResourceLocation("client/shadows/arraylistshadow.png"), 5, 107, 70, 40, false);

        arrayListFont.drawStringWithShadow(cords, 5 / 2 + 80 / 2 - arrayListFont.getStringWidth(cords) / 2, 115, -1);
        arrayListFont.drawStringWithShadow(Minecraft.getMinecraft().getDebugFPS() + " FPS", 5 / 2 + 80 / 2 - arrayListFont.getStringWidth(Minecraft.getDebugFPS() + " FPS") / 2, 115 + 3 + arrayListFont.FONT_HEIGHT, -1);

        GL11.glDisable(GL11.GL_BLEND);
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GL11.glPopMatrix();
    }

    public Module returnNextToggledModule(List list, int index) {
        for (int i = index; i < list.size(); i++) {
            Module module = (Module) list.get(i);
            if (module.getAnimationModule().getSlideAnimation() > 0.5)
                return module;
        }
        return null;
    }

    @Override
    public void hotBarDesign(int x, int y, int width, int height, int chooseX, int chooseWidth) {

    }

    @Override
    public boolean drawHotBar() {
        return false;
    }

    @Override
    public boolean drawTabGUI() {
        return true;
    }

    @Override
    public boolean drawWaterMark() {
        return true;
    }

    @Override
    public boolean drawArrayList() {
        return true;
    }
}
