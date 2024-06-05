package alphentus.mod.mods.hud;

import alphentus.event.Event;
import alphentus.event.Type;
import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import alphentus.settings.Setting;
import alphentus.utils.ArmorHUD;
import alphentus.utils.RenderUtils;
import alphentus.utils.Translate;
import alphentus.utils.fontrenderer.FontManager;
import alphentus.utils.fontrenderer.UnicodeFontRenderer;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author avox
 * @since on 29/07/2020.
 */
public class HUD extends Mod {

    public boolean dark;
    public Color bgColor;
    public Color elementColor;
    public Color opposite;
    public Color panelColor;
    public Color guiColor1;
    public Color guiColor2;
    public Color guiColor3;
    public Color guiColor4;
    public Color textColor;

    String[] themeModes = {"Alphentus", "Sigma", "Moon", "Cerberus", "Abraxas", "Custom"};
    public Setting theme = new Setting("Theme", themeModes, "Moon", this);

    public Setting darkMode = new Setting("DarkMode", true, this);
    public Setting useThemeName = new Setting("Theme Name", true, this);
    public Setting fontSize = new Setting("FontSize", 1, 40, 20, true, this);
    ResourceLocation watermark = new ResourceLocation("client/LogoBryan.png");

    public ArrayList<ArmorHUD> armorHUDS = new ArrayList<>();

    public HUD() {
        super("HUD", Keyboard.KEY_NONE, false, ModCategory.HUD);

        Init.getInstance().settingManager.addSetting(theme);

        Init.getInstance().settingManager.addSetting(darkMode);
        Init.getInstance().settingManager.addSetting(useThemeName);
        Init.getInstance().settingManager.addSetting(fontSize);

        for (int i = 0; i < 4; i++) {
            armorHUDS.add(new ArmorHUD(i));
        }


    }

    @Override
    public void onEnable() {
        this.setState(false);
        super.onEnable();
    }

    @EventTarget
    public void drawHUD(Event event) {
        if (event.getType() == Type.TICKUPDATE) {
            for (ArmorHUD armorHUD : this.armorHUDS) {
                armorHUD.tickUpdate();
            }
            dark = darkMode.isState();
            int difference = 16;
            if (dark) {
                bgColor = new Color(14, 14, 14, 255);
                elementColor = new Color(0, 0, 0, 255);
                opposite = new Color(255, 255, 255, 255);
                panelColor = new Color(42, 42, 42, 255);
                guiColor1 = new Color(0, 0, 0, 255);
                guiColor2 = new Color(difference, difference, difference, 255);
                guiColor3 = new Color(difference * 2, difference * 2, difference * 2, 255);
                guiColor4 = new Color(26, 26, 26, 255);
                textColor = new Color(241, 241, 241, 255);
            } else {
                bgColor = new Color(241, 241, 241, 255);
                elementColor = new Color(255, 255, 255, 255);
                opposite = new Color(0, 0, 0, 255);
                panelColor = new Color(213, 213, 213, 255);
                guiColor1 = new Color(255, 255, 255, 255);
                guiColor2 = new Color(255 - difference, 255 - difference, 255 - difference, 255);
                guiColor3 = new Color(255 - difference * 2, 255 - difference * 2, 255 - difference * 2, 255);
                guiColor4 = new Color(229, 229, 229, 255);
                textColor = new Color(14, 14, 14, 255);
            }
        }

        if (event.getType() == Type.RENDER2D) {
            GL11.glPushMatrix();
            GL11.glScaled(0.75, 0.75, 0.75);
            if (theme.getSelectedCombo().equals("Alphentus")) {
                RenderUtils.drawImage(watermark, -45, 13, 250, 250);
            }
            GL11.glPopMatrix();

            if (isCustom()) {
                UnicodeFontRenderer fontRenderer = Init.getInstance().fontManager.getFont("Thruster-Regular", fontSize.getCurrent());
                fontRenderer.drawStringWithShadow("Alphentus", 50, 50, Color.WHITE.getRGB());
            }

            if (theme.getSelectedCombo().equals("Sigma"))
                Init.getInstance().fontManager.myinghei30.drawStringWithShadow(useThemeName.isState() ? "Sigma" : Init.getInstance().CLIENT_NAME, 3, 1, 14737632);

            if (theme.getSelectedCombo().equals("Moon")) {
                Init.getInstance().fontManager.myinghei35.drawStringWithShadow(useThemeName.isState() ? "M" : "A", 3, 1, Init.getInstance().CLIENT_COLOR.getRGB());
                Init.getInstance().fontManager.myinghei35.drawStringWithShadow(useThemeName.isState() ? "oon" : "lphentus", 4 + Init.getInstance().fontManager.myinghei35.getStringWidth(useThemeName.isState() ? "M" : "A"), 1, 14737632);
            }

            if (theme.getSelectedCombo().equals("Cerberus")) {

                GL11.glPushMatrix();
                GL11.glTranslated(-16, -32, 0);
                GL11.glRotated(0, 0, 0, 0);
                GL11.glTranslated(-16, -32, 0);
                RenderUtils.polygon(-1, -1, 123, 3, true, new Color(Init.getInstance().CLIENT_COLOR.getRed(), Init.getInstance().CLIENT_COLOR.getGreen(), Init.getInstance().CLIENT_COLOR.getBlue(), 150));
                RenderUtils.polygon(-1, -1, 123, 3, false, Color.black);
                GL11.glPopMatrix();

                GL11.glPushMatrix();
                GL11.glScaled(2.5, 2.5, 0);
                Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("C", 1, 1, Init.getInstance().CLIENT_COLOR.getRGB());
                GL11.glPopMatrix();
                Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("erberus", 12, 7, -1);
                Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("b" + Init.getInstance().CLIENT_VERSION, 10 + Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 6 + Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, -1);
            } else if (theme.getSelectedCombo().equals("Abraxas")) {
                Init.getInstance().fontManager.arial14.drawStringWithShadow("" + Init.getInstance().CLIENT_VERSION + "-beta", 1 + Init.getInstance().fontManager.arial24.getStringWidth(useThemeName.isState() ? "Abraxas" : "Alphentus"), 2, -1);
                Init.getInstance().fontManager.arial24.drawStringWithShadow(useThemeName.isState() ? "Abraxas" : "Alphentus", 1, 1, -1);

                int yPlus[] = {0};
                int yPlus2[] = {0};

                for (int i = 0; i < 4; i++) {
                    Gui.drawRect(0, 25 + yPlus[0], 19, 25 + 19 + yPlus[0], Integer.MIN_VALUE);
                    yPlus[0] += 19;
                }

                for (ArmorHUD armorHUD : this.armorHUDS) {
                    armorHUD.setInformations(-15,26+ yPlus2[0]);
                    armorHUD.drawScreen();
                    yPlus2[0] += 19;
                }

            }

        }

    }



    public boolean isCustom() {
        return theme.getSelectedCombo().equals("Custom");
    }
}