package alphentus.mod.mods.hud;

import alphentus.event.Event;
import alphentus.event.Type;
import alphentus.gui.customhud.dragging.DraggingUtil;
import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import alphentus.settings.Setting;
import alphentus.utils.RenderUtils;
import alphentus.utils.fontrenderer.UnicodeFontRenderer;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Comparator;

/**
 * @author avox | lmao
 * @since on 04/08/2020.
 */
public class ArrayList extends Mod {

    private java.util.ArrayList<Mod> mods = new java.util.ArrayList<>();
    private DraggingUtil draggingUtil = Init.getInstance().draggingUtil;
    private UnicodeFontRenderer fr;
    private final UnicodeFontRenderer fontRendererCustom;
    private final UnicodeFontRenderer fontRendererBryan;
    private final UnicodeFontRenderer fontRendererAbraxas;
    public int x, y, width, height;

    String[] rectModes = {"None", "Left", "Right", "Outline"};
    public Setting rectMode = new Setting("Rect Theme", rectModes, "None", this);
    public Setting whiteColor = new Setting("White", false, this);
    public Setting backGround = new Setting("BackGround", false, this);

    public Setting blur = new Setting("Blur", false, this);

    public Setting backGroundAlpha = new Setting("Alpha", 0, 255, 255, true, this);
    public Setting backGroundColor = new Setting("Color", 0, 255, 32, true, this);

    public ArrayList() {
        super("ArrayList", Keyboard.KEY_NONE, false, ModCategory.HUD);

        Init.getInstance().settingManager.addSetting(rectMode);
        Init.getInstance().settingManager.addSetting(whiteColor);
        Init.getInstance().settingManager.addSetting(backGround);
        Init.getInstance().settingManager.addSetting(backGroundAlpha);
        Init.getInstance().settingManager.addSetting(backGroundColor);
        Init.getInstance().settingManager.addSetting(blur);

        this.fontRendererCustom = Init.getInstance().fontManager.myinghei20;
        this.fontRendererBryan = Init.getInstance().fontManager.thruster24;
        this.fontRendererAbraxas = Init.getInstance().fontManager.arial24;
    }

    @EventTarget
    public void event(Event event) {
        if (event.getType() != Type.RENDER2D)
            return;

        if (Init.getInstance().modManager.getModuleByClass(HUD.class).isCustom()) {
            rectMode.setVisible(true);
            whiteColor.setVisible(true);
            backGround.setVisible(true);
            backGroundAlpha.setVisible(true);
            backGroundColor.setVisible(true);
            blur.setVisible(true);
        } else {
            rectMode.setVisible(false);
            whiteColor.setVisible(false);
            backGround.setVisible(false);
            backGroundAlpha.setVisible(false);
            backGroundColor.setVisible(false);
            blur.setVisible(false);
        }

        if (backGround.isState()) {
            backGroundAlpha.setVisible(true);
            backGroundColor.setVisible(true);
        } else {
            backGroundAlpha.setVisible(false);
            backGroundColor.setVisible(false);
        }
        if (!getState())
            return;

        setState(false);

        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;

        final int[] color = {(int) backGroundColor.getCurrent()};
        int alpha = (int) backGroundAlpha.getCurrent();
        boolean leftRect = rectMode.getSelectedCombo().equals("Left");
        boolean rightRect = rectMode.getSelectedCombo().equals("Right");
        boolean outline = rectMode.getSelectedCombo().equals("Outline");
        boolean custom = false;
        boolean alphentus = false;
        boolean sigma = false;
        boolean moon = false;
        boolean abraxas = false;
        boolean cerberus = false;


        if (Init.getInstance().modManager.getModuleByClass(HUD.class).isCustom()) {
            custom = true;
            alphentus = false;
            sigma = false;
            moon = false;
            abraxas = false;
            cerberus = false;
            fr = fontRendererCustom;
        } else if (Init.getInstance().modManager.getModuleByClass(HUD.class).theme.getSelectedCombo().equals("Alphentus")) {
            custom = false;
            alphentus = true;
            sigma = false;
            moon = false;
            abraxas = false;
            cerberus = false;
            fr = fontRendererBryan;
        } else if (Init.getInstance().modManager.getModuleByClass(HUD.class).theme.getSelectedCombo().equals("Sigma")) {
            custom = false;
            alphentus = false;
            sigma = true;
            moon = false;
            abraxas = false;
            cerberus = false;
            fr = fontRendererCustom;
        } else if (Init.getInstance().modManager.getModuleByClass(HUD.class).theme.getSelectedCombo().equals("Moon")) {
            custom = false;
            alphentus = false;
            sigma = false;
            moon = true;
            abraxas = false;
            cerberus = false;
            fr = fontRendererCustom;
        } else if (Init.getInstance().modManager.getModuleByClass(HUD.class).theme.getSelectedCombo().equals("Abraxas")) {
            custom = false;
            alphentus = false;
            sigma = false;
            moon = false;
            abraxas = true;
            cerberus = false;
            fr = fontRendererAbraxas;
        } else if (Init.getInstance().modManager.getModuleByClass(HUD.class).theme.getSelectedCombo().equals("Cerberus")) {
            custom = false;
            alphentus = false;
            sigma = false;
            moon = false;
            abraxas = false;
            cerberus = true;
            fr = fontRendererCustom;
        }


        Color bgColor = new Color(color[0], color[0], color[0], alpha);
        final int[] y = {outline ? 2 : alphentus ? 1 : cerberus ? 3 : 1};

        boolean finalCerberus = cerberus;
        Init.getInstance().modManager.getModArrayList().stream().filter(Mod::getState).sorted(Comparator.comparingDouble(mod -> -(finalCerberus ? fontRenderer : fr).getStringWidth(mod.getModuleName() + (mod.getInfoName().equals("") ? "" : " " + mod.getInfoName())))).forEach(mod -> {

            if (!mod.isVisible())
                return;

            String renderStuff = mod.getModuleName() + (mod.getInfoName().equals("") ? "" : " " + mod.getInfoName());

            mods.add(mod);

            x = scaledResolution.getScaledWidth() - width;
            this.y = outline ? 2 : Init.getInstance().modManager.getModuleByClass(HUD.class).theme.getSelectedCombo().equals("Bryan") ? 1 : -1;
            if (fr.getStringWidth(renderStuff + 5) >= width)
                width = fr.getStringWidth(renderStuff + 5);
            height = (int) (y[0] + mod.getTranslate().getY());

            if (Init.getInstance().modManager.getModuleByClass(HUD.class).isCustom()) {

                if (outline)
                    Gui.drawRect((int) (scaledResolution.getScaledWidth() - mod.getTranslate().getX() - 5), y[0] - 2, scaledResolution.getScaledWidth(), y[0] + fr.FONT_HEIGHT + 1, whiteColor.isState() ? -1 : Init.getInstance().CLIENT_COLOR.hashCode());

                if (blur.isState())
                    Init.getInstance().blurUtil.blur((int) (scaledResolution.getScaledWidth() - mod.getTranslate().getX() - 4 + (rightRect ? 1 : 0)), y[0] - 1, scaledResolution.getScaledWidth(), fr.FONT_HEIGHT + 3, 30);

                if (backGround.isState())
                    Gui.drawRect((int) (scaledResolution.getScaledWidth() - mod.getTranslate().getX() - 4 + (rightRect ? 1 : 0)), y[0] - 1, scaledResolution.getScaledWidth() - (outline ? 1 : 0), y[0] + fr.FONT_HEIGHT, bgColor.getRGB());

                fr.drawStringWithShadow(renderStuff, scaledResolution.getScaledWidth() - mod.getTranslate().getX() - 1 + (leftRect ? 1 : 0), y[0] - 1, whiteColor.isState() ? 14737632 : Init.getInstance().CLIENT_COLOR.hashCode());

                if (leftRect)
                    Gui.drawRect((int) (scaledResolution.getScaledWidth() - mod.getTranslate().getX() - 4), y[0] - 1, (int) (scaledResolution.getScaledWidth() - mod.getTranslate().getX() - 2), y[0] + fr.FONT_HEIGHT, whiteColor.isState() ? -1 : Init.getInstance().CLIENT_COLOR.hashCode());
                if (rightRect)
                    Gui.drawRect(scaledResolution.getScaledWidth() - 2, y[0] - 1, scaledResolution.getScaledWidth(), y[0] + fr.FONT_HEIGHT, whiteColor.isState() ? -1 : Init.getInstance().CLIENT_COLOR.hashCode());

                mod.getTranslate().interpolate(fr.getStringWidth(renderStuff) + (rightRect ? 4 : outline ? 3 : 2), fr.FONT_HEIGHT + (outline ? 2 : 1), 1);

                y[0] += mod.getTranslate().getY();


            } else if (Init.getInstance().modManager.getModuleByClass(HUD.class).theme.getSelectedCombo().equals("Alphentus")) {

                // Right Rect
                Gui.drawRect(scaledResolution.getScaledWidth() - 2, y[0] - 1, scaledResolution.getScaledWidth(), y[0] + fr.FONT_HEIGHT + 1, 0xFFFFFFFF);

                // Background
                Gui.drawRect((int) (scaledResolution.getScaledWidth() - mod.getTranslate().getX() - 5), y[0] - 1, scaledResolution.getScaledWidth() - 2, y[0] + fr.FONT_HEIGHT + 1, 0xFF202020);

                // Actual Text
                fr.drawStringWithShadow(renderStuff, scaledResolution.getScaledWidth() - mod.getTranslate().getX() - 3, y[0] - 1.5F, 0xFFFFFFFF, false);

                // Animation
                mod.getTranslate().interpolate(fr.getStringWidth(renderStuff) + 2, fr.FONT_HEIGHT + 1, 1);

                // Y
                y[0] += mod.getTranslate().getY() + 1;


            } else if (Init.getInstance().modManager.getModuleByClass(HUD.class).theme.getSelectedCombo().equals("Sigma")) {

                if (mod.animationDavid < 0.1F)
                    mod.animationDavid += 0.001 * RenderUtils.deltaTime;
                if (mod.animationDavid > 0.1F)
                    mod.animationDavid = 0.1F;

                GL11.glPushMatrix();
                GL11.glTranslatef(scaledResolution.getScaledWidth() - fr.getStringWidth(renderStuff) / 2, y[0] - fr.FONT_HEIGHT / 2 - 1, 0);
                GL11.glScalef(0.9F + mod.animationDavid, 0.9F + mod.animationDavid, 0);
                GL11.glTranslatef(-(scaledResolution.getScaledWidth() - fr.getStringWidth(renderStuff) / 2), -(y[0] - fr.FONT_HEIGHT / 2 - 1), 0);

                fr.drawStringWithShadow(renderStuff, scaledResolution.getScaledWidth() - fr.getStringWidth(renderStuff) - 2, y[0] - 1, whiteColor.isState() ? 14737632 : Init.getInstance().CLIENT_COLOR.hashCode());
                GL11.glPopMatrix();

                mod.getTranslate().interpolate(0.1F, fr.FONT_HEIGHT + (outline ? 2 : 1), 1);
                y[0] += mod.getTranslate().getY();
            } else if (Init.getInstance().modManager.getModuleByClass(HUD.class).theme.getSelectedCombo().equals("Moon")) {
                Gui.drawRect((int) (scaledResolution.getScaledWidth() - mod.getTranslate().getX() - 2), y[0] - 1, scaledResolution.getScaledWidth(), y[0] + fr.FONT_HEIGHT, Integer.MIN_VALUE);
                fr.drawStringWithShadow(renderStuff, scaledResolution.getScaledWidth() - mod.getTranslate().getX(), y[0] - 1, Init.getInstance().CLIENT_COLOR.getRGB());
                mod.getTranslate().interpolate(fr.getStringWidth(renderStuff) + 1, fr.FONT_HEIGHT + 1, 1);
                y[0] += fr.FONT_HEIGHT + 1;
            } else if (Init.getInstance().modManager.getModuleByClass(HUD.class).theme.getSelectedCombo().equals("Abraxas")) {
                Gui.drawRect((int) (scaledResolution.getScaledWidth() - mod.getTranslate().getX() - 2), y[0] - 3, scaledResolution.getScaledWidth(), y[0] + fr.FONT_HEIGHT, Integer.MIN_VALUE);
                fr.drawStringWithShadow(renderStuff, scaledResolution.getScaledWidth() - mod.getTranslate().getX(), y[0] - 1, Color.WHITE.getRGB());
                mod.getTranslate().interpolate(fr.getStringWidth(renderStuff) + 1, fr.FONT_HEIGHT + 1, 1);
                y[0] += fr.FONT_HEIGHT;
            } else if (Init.getInstance().modManager.getModuleByClass(HUD.class).theme.getSelectedCombo().equals("Cerberus")) {
                Gui.drawRect((int) (scaledResolution.getScaledWidth() - mod.getTranslate().getX() - 2), y[0] - 3, scaledResolution.getScaledWidth(), y[0] + fontRenderer.FONT_HEIGHT, Integer.MIN_VALUE);
                fontRenderer.drawStringWithShadow(renderStuff, scaledResolution.getScaledWidth() - mod.getTranslate().getX(), y[0] - 1, getRainbow(4000, y[0] * 3));
                mod.getTranslate().interpolate(fontRenderer.getStringWidth(renderStuff) + 1, fontRenderer.FONT_HEIGHT + 1, 1);
                y[0] += fontRenderer.FONT_HEIGHT + 3;
            }
        });
    }

    public static int getRainbow(final int speed, final double d) {
        float hue = (float) ((System.currentTimeMillis() - (d % speed) / 0.25) % speed);
        hue /= speed;
        return Color.getHSBColor(hue, (float) (0.55f), (float) (1F)).getRGB();
    }

    public void updatePosition(int mouseX, int mouseY, int dragX, int dragY) {
        x = dragX + mouseX;
        y = dragY + mouseY;
    }
}