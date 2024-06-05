package koks.theme.themes;

import com.sun.org.apache.xpath.internal.operations.Mod;
import koks.Koks;
import koks.hud.tabgui.CategoryTab;
import koks.hud.tabgui.ModuleTab;
import koks.modules.Module;
import koks.modules.impl.visuals.ClearTag;
import koks.theme.Theme;
import koks.utilities.ColorUtil;
import koks.utilities.CustomFont;
import koks.utilities.DeltaTime;
import koks.utilities.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
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
public class LiquidSense extends Theme {

    public final FontRenderer arrayListFont = Minecraft.getMinecraft().fontRendererObj;
    private final ColorUtil colorUtil = new ColorUtil();

    public LiquidSense() {
        super(ThemeCategory.LIQUIDSENSE);
    }

    @Override
    public void arrayListDesign() {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

        float xOffest = 3;
        int[] y = {2};
        float offest = 5;

        List<Module> list = getSortedArrayList();

        for (int i = 0; i < list.size(); i++) {
            Module module = list.get(i);
            String finalText = Koks.getKoks().moduleManager.getModule(ClearTag.class).isToggled() ? module.getDisplayName() : module.getNameForArrayList();

            if (module.isToggled()) {

                if (module.getAnimationModule().getYAnimation() < arrayListFont.FONT_HEIGHT + offest) ;
                module.getAnimationModule().setYAnimation(module.getAnimationModule().getYAnimation() + 0.075F * DeltaTime.getDeltaTime());
                if (module.getAnimationModule().getYAnimation() > arrayListFont.FONT_HEIGHT + offest)
                    module.getAnimationModule().setYAnimation(arrayListFont.FONT_HEIGHT + offest);


                if (module.getAnimationModule().getZoomAnimation() < 1F)
                    module.getAnimationModule().setZoomAnimation(module.getAnimationModule().getZoomAnimation() + 0.0035F * DeltaTime.getDeltaTime());
                if (module.getAnimationModule().getZoomAnimation() > 1F)
                    module.getAnimationModule().setZoomAnimation(1F);

            } else {
                if (module.getAnimationModule().getYAnimation() > 0)
                    module.getAnimationModule().setYAnimation(module.getAnimationModule().getYAnimation() - 0.055F * DeltaTime.getDeltaTime());

                if (module.getAnimationModule().getZoomAnimation() > 0F)
                    module.getAnimationModule().setZoomAnimation(module.getAnimationModule().getZoomAnimation() - 0.0025F * DeltaTime.getDeltaTime());
                if (module.getAnimationModule().getZoomAnimation() < 0F)
                    module.getAnimationModule().setZoomAnimation(0F);

            }
            if (module.getAnimationModule().getZoomAnimation() > 0.1) {
                GL11.glPushMatrix();
                GlStateManager.disableAlpha();
                GlStateManager.enableBlend();
                try {
                    Module nextEnabledModule = returnNextToggledModule(list, i + 1);
                    double difference = fr.getStringWidth(finalText) - fr.getStringWidth(Koks.getKoks().moduleManager.getModule(ClearTag.class).isToggled() ? nextEnabledModule.getDisplayName() : nextEnabledModule.getNameForArrayList());
                    Gui.drawRect(scaledResolution.getScaledWidth() - arrayListFont.getStringWidth(finalText) - 2 - xOffest, y[0] + module.getAnimationModule().getYAnimation() - 1, scaledResolution.getScaledWidth() - arrayListFont.getStringWidth(finalText) - xOffest + difference - 1, y[0] + module.getAnimationModule().getYAnimation(), Koks.getKoks().client_color.getRGB());
                    Gui.drawRect(scaledResolution.getScaledWidth() - arrayListFont.getStringWidth(finalText) - 2 - xOffest, y[0], scaledResolution.getScaledWidth() - arrayListFont.getStringWidth(finalText) - xOffest - 1, y[0] + module.getAnimationModule().getYAnimation(), Koks.getKoks().client_color.getRGB());
                } catch (Exception e) {
                    Gui.drawRect(scaledResolution.getScaledWidth() - fr.getStringWidth(finalText) - 1 - xOffest, y[0] + module.getAnimationModule().getYAnimation() - 1, scaledResolution.getScaledWidth() - xOffest + 1, y[0] + module.getAnimationModule().getYAnimation(), Koks.getKoks().client_color.getRGB());
                    Gui.drawRect(scaledResolution.getScaledWidth() - fr.getStringWidth(finalText) - 2 - xOffest, y[0], scaledResolution.getScaledWidth() - fr.getStringWidth(finalText) - xOffest - 1, y[0] + module.getAnimationModule().getYAnimation(), Koks.getKoks().client_color.getRGB());

                }

                if (i == 0) {
                    Gui.drawRect(scaledResolution.getScaledWidth() - fr.getStringWidth(finalText) - 2 - xOffest, y[0] - 1, scaledResolution.getScaledWidth() - xOffest + 1, y[0], Koks.getKoks().client_color.getRGB());
                }
                GlStateManager.enableAlpha();
                GlStateManager.disableBlend();

                GL11.glPopMatrix();
                GL11.glPushMatrix();
                GL11.glTranslated(scaledResolution.getScaledWidth() - arrayListFont.getStringWidth(finalText) / 2 - xOffest, (float) (y[0] + module.getAnimationModule().getYAnimation() / 2), 0);
                GL11.glScalef((float) module.getAnimationModule().getZoomAnimation(), (float) module.getAnimationModule().getZoomAnimation(), 0);
                GL11.glTranslated(-(scaledResolution.getScaledWidth() - arrayListFont.getStringWidth(finalText) / 2) - xOffest, -(float) (y[0] + module.getAnimationModule().getYAnimation() / 2), 0);
                GlStateManager.disableAlpha();
                GlStateManager.enableBlend();
                arrayListFont.drawStringWithShadow(finalText, (float) (scaledResolution.getScaledWidth() - arrayListFont.getStringWidth(finalText)) + 3, y[0] + offest / 2, Koks.getKoks().client_color.getRGB());
                y[0] += module.getAnimationModule().getYAnimation();
                GlStateManager.enableAlpha();
                GlStateManager.disableBlend();

                GL11.glPopMatrix();
            }
        }
        GL11.glPushMatrix();
        GlStateManager.disableAlpha();
        GlStateManager.enableBlend();
        Gui.drawRect(scaledResolution.getScaledWidth() - xOffest + 1, 1, scaledResolution.getScaledWidth() - xOffest + 2, y[0], Koks.getKoks().client_color.getRGB());
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GL11.glPopMatrix();
    }

    public List<Module> getSortedArrayList() {
        List list = Koks.getKoks().moduleManager.getModules().stream().filter(Module::isVisible).sorted(Comparator.comparingDouble(module -> -arrayListFont.getStringWidth(Koks.getKoks().moduleManager.getModule(ClearTag.class).isToggled() ? module.getDisplayName() : module.getNameForArrayList()))).collect(Collectors.toList());
        return list;
    }

    @Override
    public void waterMarkDesign() {

        GL11.glPushMatrix();
        GL11.glScaled(1.5, 1.5, 1.5);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(Koks.getKoks().CLIENT_NAME.substring(0, 1).toUpperCase(), 2, 2, colorUtil.rainbow(3500, 0, 1));
        GL11.glPopMatrix();
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("§f" + Koks.getKoks().CLIENT_NAME.substring(1) + "sense"
                , 2 + Minecraft.getMinecraft().fontRendererObj.getStringWidth(Koks.getKoks().CLIENT_NAME.substring(0, 1).toUpperCase()) * 1.5F + 1, 2 + Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT * 1.5F - Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, colorUtil.rainbow(3500, 0, 1));
    }

    public Module returnNextToggledModule(List list, int index) {
        for (int i = index; i < list.size(); i++) {
            Module module = (Module) list.get(i);
            if (module.getAnimationModule().getZoomAnimation() > 0.5)
                return module;
        }
        return null;
    }

    @Override
    public void hotBarDesign(int x, int y, int width, int height, int chooseX, int chooseWidth) {
        Gui.drawRect(x, y, x + width, y + height, Integer.MIN_VALUE);
        Gui.drawRect(chooseX, y, chooseX + chooseWidth, y + height, Integer.MAX_VALUE);
    }

    @Override
    public boolean drawHotBar() {
        return true;
    }

    @Override
    public boolean drawTabGUI() {
        return false;
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
