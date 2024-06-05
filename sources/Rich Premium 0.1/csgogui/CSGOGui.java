package csgogui;

import clickgui.panel.component.Component;
import clickgui.setting.Setting;
import com.sun.jna.platform.win32.WinUser;
import csgogui.comp.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.lang3.text.WordUtils;
import org.lwjgl.opengl.GL11;
import white.floor.Main;
import white.floor.features.Category;
import white.floor.features.Feature;
import white.floor.features.FeatureDirector;
import white.floor.features.impl.combat.KillauraTest;
import white.floor.features.impl.display.ClickGUI;
import white.floor.font.Fonts;
import white.floor.helpers.DrawHelper;
import white.floor.helpers.render.AnimationHelper;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class CSGOGui extends GuiScreen {

    public double posX, posY, width, height, dragX, dragY;
    public boolean dragging;
    public Category selectedCategory;
    public static float bg;
    private Feature selectedModule;
    public int modeIndex;
    public static float anim = 0.0f;

    public ArrayList<Comp> comps = new ArrayList<>();

    public CSGOGui() {
        dragging = false;
        posX = getScaledRes().getScaledWidth() / 2 - 150;
        posY = getScaledRes().getScaledHeight() / 2 - 100;
        width = posX + 150 * 2;
        height = height + 200;
        selectedCategory = Category.COMBAT;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        drawBG();

        anim = AnimationHelper.animation(anim, 1.0F, (float) (0.001f * Feature.deltaTime()));

        GL11.glPushMatrix();
        GL11.glScalef(anim, anim, anim);
        GL11.glEnable(3089);
        DrawHelper.prepareScissorBox((float) posX,(float)  posY,(float) width, (float) height);
        String theme = Main.settingsManager.getSettingByName(Main.featureDirector.getModule(ClickGUI.class), "Theme Mode").getValString();


        if (dragging) {
            posX = mouseX - dragX;
            posY = mouseY - dragY;
        }

            if (ClickGUI.opacite < 255) {
                ClickGUI.opacite = 255;
            }

        width = posX + 500;
        height = posY + 400;

        int color = -1;
        int color2 = -1;
        int color3 = -1;
        int color4 = -1;
        int color5 = -1;
        int color6 = -1;
        int color7 = -1;
        int color8 = -1;
        int color9 = -1;
        int color10 = -1;
        int color11 = -1;
        int white = -1;
        int textcolor = -1;
        int textcolorbackg = -1;

        if(theme.equalsIgnoreCase("White")) {
            color = new Color(60, 61, 60).getRGB();
            color2 = new Color(60, 61, 60).getRGB();
            color3 = new Color(60, 61, 60).getRGB();
            textcolor = new Color(245, 245, 245, 215).getRGB();
            textcolorbackg = new Color(245, 245, 245, 145).getRGB();
        } else if(theme.equalsIgnoreCase("Dark")) {
            color = new Color(60, 61, 60).getRGB();
            color2 = new Color(60, 61, 60).getRGB();
            color3 = new Color(60, 61, 60).getRGB();
            textcolor = new Color(245, 245, 245, 215).getRGB();
            textcolorbackg = new Color(245, 245, 245, 145).getRGB();
        } else if(theme.equalsIgnoreCase("Blue")) {
            color = new Color(20,20,20, ClickGUI.opacite).getRGB();
            color2 = new Color(24,24,24, ClickGUI.opacite).getRGB();
            color3 = new Color(50,51,52, ClickGUI.opacite).getRGB();
            color4 = new Color(255,255,255, ClickGUI.opacite).getRGB();
            color5 = new Color(255, 255, 255, ClickGUI.opacite).getRGB();
            color6 = new Color(34,34,34, ClickGUI.opacite).getRGB();
            color7 = new Color(28,28,28, 0).getRGB();
            color8 = new Color(29,29,29, ClickGUI.opacite).getRGB();
            color9 = new Color(255,255,255, ClickGUI.opacite).getRGB();
            color10 = new Color(32,32,32, ClickGUI.opacite).getRGB();
            color11 = new Color(26,26,26, ClickGUI.opacite).getRGB();
            white = new Color(255, 255, 255, ClickGUI.opacite).getRGB();
            textcolor = new Color(255, 255, 255, 255).getRGB();
        }

        //DrawHelper.drawSmoothRect((float) posX - 0.5f, (float) posY - 0.5f, (float)width + 0.5f, (float)height + 0.5f, new Color(25,125,255, ClickGUI.opacite).getRGB());
        DrawHelper.drawSmoothRect((float) posX, (float) posY, (float) width, (float) height, color);

        Gui.drawRect(posX , posY, posX + 61, height, color2);
        Gui.drawRect(posX + 150, posY + 3, posX + 150.5, height - 3, color3);
        Gui.drawRect(posX + 153, posY + 40, width - 3, posY + 40.5, color3);
        Gui.drawRect(posX + 3,  posY + 39.5,  posX + 58.5,  posY + 40, color3);
        Fonts.urw16.drawStringWithShadow("Rich Premium", posX + 7, posY + 18, textcolor);

        Fonts.urw18.drawCenteredStringWithShadow(selectedCategory.toString(), posX + 107, posY + 16, textcolor);

        Fonts.urw15.drawCenteredStringWithShadow("Number of modules - " + Main.featureDirector.getModulesInCategory(selectedCategory).length, posX + 106, height - 9, textcolor);

        int offset = 0;
        for (Category category : Category.values()) {
            if(theme.equalsIgnoreCase("Blue")) {
                color4 = category.equals(selectedCategory) ? new Color(27, 28, 30, ClickGUI.opacite).getRGB() : new Color(23, 23, 23, ClickGUI.opacite).getRGB();
            }

            Gui.drawRect(posX + 13.5,posY + 45.5 + offset,posX + 48.5,posY + 79.5 + offset, category.equals(selectedCategory) ? new Color(255, 255, 255).getRGB() : color3);
            Gui.drawRect(posX + 14,posY + 46 + offset,posX + 48,posY + 79 + offset, color4);

            offset += 42;
        }

        int bob = 0;

        for (Category category : Category.values()) {

            String name = "richclient/icons/" + category.name().toUpperCase() +  ".png";

            DrawHelper.startSmooth();
            DrawHelper.drawImage(new ResourceLocation(name), (int) posX + 9, (int) posY + 40 + bob, 46, 46, category.equals(selectedCategory) ? new Color(80, 80, 80, ClickGUI.opacite / 2).getRGB() : new Color(45, 45, 45).getRGB());
            DrawHelper.drawImage(new ResourceLocation(name), (int) posX + 9, (int) posY + 40 + bob, 44, 44, category.equals(selectedCategory) ? white : color5);
            DrawHelper.endSmooth();

            bob += 42;
        }

        offset = 0;

        for (Feature m : Main.featureDirector.getModulesInCategory(selectedCategory)) {
            Gui.drawRect(posX + 61,posY + 40 + offset,posX + 150,posY + 62 + offset, m.isToggled() ? color10 : color11);
            DrawHelper.drawGradientSideways(posX + 61,posY + 40 + offset,posX + 65,posY + 62 + offset, m.isToggled() ? color9 : color7, color7);
            Fonts.urw18.drawStringWithShadow(m.getName(),posX + 67, posY + 47 + offset, textcolor);
            offset += 23;
        }

        for (Comp comp : comps) {
            comp.drawScreen(mouseX, mouseY);
        }

        int blob = 0;

        for (Feature m : Main.featureDirector.getModulesInCategory(selectedCategory)) {
            if (isInside(mouseX, mouseY, posX + 61,posY + 40 + blob,posX + 150,posY + 62 + blob)) {

                Gui.drawRect(mouseX + 11.5, mouseY - 1, mouseX + Fonts.urw16.getStringWidth(m.getDesc().toLowerCase()) + 14.5, mouseY + Fonts.urw16.getStringHeight(m.getDesc()) + 1, color4);
                Gui.drawRect(mouseX + 12, mouseY - 1, mouseX + Fonts.urw16.getStringWidth(m.getDesc().toLowerCase()) + 14, mouseY + Fonts.urw16.getStringHeight(m.getDesc()) + 1, color6);
                Fonts.urw16.drawStringWithShadow(m.getDesc().toLowerCase(), mouseX + 13, mouseY, textcolor);
            }
            blob += 23;
        }

        int blbob = 0;

        for (Category category : Category.values()) {
            if (isInside(mouseX, mouseY, posX + 14,posY + 46 + blbob,posX + 48,posY + 79 + blbob)) {
                Gui.drawRect(mouseX + 11.5, mouseY - 1, mouseX + Fonts.urw16.getStringWidth(category.name().toLowerCase()) + 14.5, mouseY + Fonts.urw16.getStringHeight(category.name().toLowerCase()) + 1, color4);
                Gui.drawRect(mouseX + 12, mouseY - 1, mouseX + Fonts.urw16.getStringWidth(category.name().toLowerCase()) + 14, mouseY + Fonts.urw16.getStringHeight(category.name().toLowerCase()) + 1, color6);
                Fonts.urw16.drawStringWithShadow(category.name().toLowerCase(), mouseX + 13, mouseY, textcolor);
            }

            blbob += 42;
        }

        Fonts.urw14.drawCenteredStringWithShadow(mc.session.getUsername(), posX + 31, height - 9, -1);
        GL11.glDisable(3089);
        GL11.glPopMatrix();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        for (Comp comp : comps) {
            comp.keyTyped(typedChar, keyCode);
        }
    }

    public void drawBG() {
        bg = AnimationHelper.animation(bg, getScaledRes().getScaledHeight(), 3);
        Gui.drawRect(0, 0, getScaledRes().getScaledWidth(), getScaledRes().getScaledHeight(), new Color(0, 0, 0, 160).getRGB());
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (isInside(mouseX, mouseY, posX,  posY,  posX + 61,  posY + 40) && mouseButton == 0) {
            dragging = true;
            dragX = mouseX - posX;
            dragY = mouseY - posY;
        }

        int offset = 0;
        for (Category category : Category.values()) {
            if (isInside(mouseX, mouseY, posX + 14,posY + 46 + offset,posX + 48,posY + 79 + offset) && mouseButton == 0) {
                selectedCategory = category;
            }
            offset += 42;
        }
        offset = 0;
        for (Feature m : Main.featureDirector.getModulesInCategory(selectedCategory)) {
            if (isInside(mouseX, mouseY,posX + 61,posY + 40 + offset,posX + 150,posY + 62 + offset)) {
                if (mouseButton == 0) {
                    m.toggle();
                }

                if (mouseButton == 1) {
                    int sOffset = 45;
                    comps.clear();
                    if (Main.settingsManager.getSettingsByMod(m) != null)
                    for (Setting setting : Main.settingsManager.getSettingsByMod(m)) {
                        selectedModule = m;
                        if (setting.isMass()) {
                            comps.add(new Mass(225, sOffset, this, selectedModule, setting));
                            sOffset += 13;
                        }
                        if (setting.isCombo()) {
                            comps.add(new Combo(225, sOffset, this, selectedModule, setting));
                            sOffset += 12;
                        }
                        if (setting.isCheck()) {
                            comps.add(new CheckBox(225, sOffset, this, selectedModule, setting));
                            sOffset += 12;
                        }
                        if (setting.isSlider()) {
                            comps.add(new Slider(225, sOffset, this, selectedModule, setting));
                            sOffset += 12;
                        }
                    }
                }
            }

            offset += 23;
        }
        for (Comp comp : comps) {
            comp.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        dragging = false;
        for (Comp comp : comps) {
            comp.mouseReleased(mouseX, mouseY, state);
        }
    }

    @Override
    public void initGui() {
        super.initGui();
        dragging = false;
    }

    @Override
    public void onGuiClosed() {
    }


    public boolean isInside(int mouseX, int mouseY, double x, double y, double x2, double y2) {
        return (mouseX > x && mouseX < x2) && (mouseY > y && mouseY < y2);
    }

    public ScaledResolution getScaledRes() {
        return new ScaledResolution(Minecraft.getMinecraft());
    }

}
