package rip.helium.gui.hud.element.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import rip.helium.Helium;
import rip.helium.cheat.Cheat;
import rip.helium.cheat.CheatCategory;
import rip.helium.cheat.impl.visual.Hud;
import rip.helium.gui.hud.element.Element;
import rip.helium.gui.hud.element.Quadrant;
import rip.helium.utils.ColorCreator;
import rip.helium.utils.Draw;
import rip.helium.utils.GuiUtil;
import rip.helium.utils.RenderUtil;
import rip.helium.utils.font.Fonts;
import rip.helium.utils.property.impl.ColorProperty;
import rip.helium.utils.property.impl.DoubleProperty;
import rip.helium.utils.property.impl.StringsProperty;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author antja03
 */
public class ToggledList extends Element {

    protected Minecraft mc = Minecraft.getMinecraft();

    private DoubleProperty propertyHeight = new DoubleProperty("Entry Height", "The height of each cheat name", null,
            10, 8, 15, 1, "px");

    private StringsProperty propertyColorMode = new StringsProperty("Color Type", "The way that each cheat name will be colored", null,
            false, true, new String[]{"Solid", "Pulsing", "Rainbow"}, new Boolean[]{true, false, false});

    private ColorProperty propertyNameColor = new ColorProperty("Name Color", "The color of each cheat name", () -> !propertyColorMode.getValue().get("Rainbow"),
            0, 0, 100, 255);

    private ColorProperty propertyBackgroundColor = new ColorProperty("Background Color", "The color of the background", null,
            0, 0, 0, 120);

    public ToggledList() {
        super("Toggled List", Quadrant.TOP_RIGHT, 2, 2);
        values.add(propertyHeight);
        values.add(propertyColorMode);
        values.add(propertyNameColor);
        values.add(propertyBackgroundColor);
    }

    public Color colorr;
    private int brightness = 130;
    private float hue;
    private boolean ascending;
    private float[] hsb = new float[3];
    private Hud hud;

    @Override
    public void drawElement(boolean editor) {
        if ((boolean) values.get(0).getValue() && !editor)
            return;

        this.editX = positionX - 20; //20
        //this.editX = positionX + 1;
        this.editY = positionY;
        this.width = getLongestToggledLabelLength() + 3;
        this.height = getToggledCheats().size() * propertyHeight.getValue();

        if (hud == null) {
            hud = (Hud) Helium.instance.cheatManager.getCheatRegistry().get("Hud");
        }
        if (!hud.prop_arraylist.getValue() || !Helium.instance.cheatManager.isCheatEnabled("Hud"))
            return;
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        if (ascending) {
            brightness++;
        } else {
            brightness--;
        }

        if (brightness <= 160F) {
            ascending = true;
        }

        if (brightness >= 255F) {
            ascending = false;
        }
        Color c = Hud.prop_color.getValue();
        this.hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsb);
        colorr = Color.getHSBColor(this.hsb[0], this.hsb[1], this.brightness / 255.0F);
        int y = (int) this.positionY - 2;
        for (Cheat m : getToggledModulesSortedLTS()) {
            CheatCategory mCat = m.getCategory();
            int co0l0rrr = 5526612;
            int catColor = mCat.equals(CheatCategory.COMBAT) ? new Color(45, 248, 35).getRGB() : mCat.equals(CheatCategory.MOVEMENT) ? new Color(186, 63, 29).getRGB() : mCat.equals(CheatCategory.VISUAL) ? new Color(133, 119, 252).getRGB() : mCat.equals(CheatCategory.MISC) ? new Color(225, 68, 250).getRGB() : mCat.equals(CheatCategory.PLAYER) ? new Color(250, 197, 24).getRGB() : 0xFFFFFF;
            int color;
            if (hud.prop_colormode.getValue().get("Pulsing")) {
                color = colorr.getRGB();
            } else {
                color = hud.prop_colormode.getValue().get("Rainbow") ? ColorCreator.createRainbowFromOffset(3200, y * -15) : hud.prop_colormode.getValue().get("Custom") ? hud.prop_color.getValue().getRGB() : hud.prop_colormode.getValue().get("Categories") ? catColor : 0xFFFFFF;
            }
            String name;
            if (Hud.lowercase.getValue()) {
                name = m.getId().toLowerCase() + (m.getMode() != null ? " " + EnumChatFormatting.GRAY + m.getMode() : "").toLowerCase();
            } else {
                name = m.getId() + (m.getMode() != null ? " " + EnumChatFormatting.GRAY + m.getMode() : "");
            }
            /*/if (!name.toLowerCase().contains("interface")) {
                if (m.getAnimation() > 0) m.setAnimation(m.getAnimation() - 1);
                if (hud.prop_arraylistmode.getValue().get("FadeAway")) {
                	y += Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT; // remove if buggy
                    double length = mc.fontRendererObj.getStringWidth(name);
                    double length1 = (hud.prop_customfont.getValue() ? Fonts.verdana3.getStringWidth(name) : mc.fontRendererObj.getStringWidth(name));
                    Draw.drawBorderedRectangle((int) (sr.getScaledWidth() - positionX - 3 - length + m.getAnimation()), y, (int) (sr.getScaledWidth() - positionX + 3), y + 10, 1, new Color(30, 30, 30).getRGB(), color, true);
                    Gui.drawRect((int) (sr.getScaledWidth() - positionX - 2 - length + m.getAnimation()), y, (int) (sr.getScaledWidth() - positionX + 3), y + 9, new Color(30, 30, 30).getRGB());
                }
                if (!hud.prop_arraylistmode.getValue().get("FadeAway")) {
                	y += Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 2;
                }
                if (hud.prop_customfont.getValue())
                	mc.fontRendererObj.drawStringWithShadow(name, (float) (sr.getScaledWidth() - positionX  + m.getAnimation()- mc.fontRendererObj.getStringWidth(name)), y + 1, color);
                else
                    mc.fontRendererObj.drawStringWithShadow(name, (float) (sr.getScaledWidth() - positionX  + m.getAnimation()- mc.fontRendererObj.getStringWidth(name)), y + 1, color);
               // y += Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
                		//+ 2;
            }/*/
            if (!name.toLowerCase().contains("interface")) {
                if (Hud.prop_theme.getValue().get("Helium")) {
                    if (m.getAnimation() > 0) m.setAnimation(m.getAnimation() - 1);
                    if (hud.prop_arraylistmode.getValue().get("Helium")) {
                        double length = Fonts.Arial.getStringWidth(name);
                        //double length = (hud.prop_customfont.getValue() ? Fonts.verdana3.getStringWidth(name) : mc.fontRendererObj.getStringWidth(name));
                        //Draw.drawBorderedRectangle((int) (sr.getScaledWidth() - positionX - 3 - length + m.getAnimation()), y, (int) (sr.getScaledWidth() - positionX + 3), y + 12, 1, new Color(30, 30, 30).getRGB(), color, true);
                        //Draw.drawBorderedRectangle((int) (sr.getScaledWidth() - positionX - 3 - length + m.getAnimation()), y, (int) (sr.getScaledWidth() - positionX + 3), y + 12, 1, new Color(30, 30, 30).getRGB(), color, true);
                        //Draw.drawCircle(12, 12, 45, color);

                        Gui.drawRect((int) (sr.getScaledWidth() - positionX - 2 - length - 2 + 1), y, (int) (sr.getScaledWidth() - positionX + 3), y + 9 + 2, RenderUtil.withTransparency(0, Hud.backgroundcolor.getValue().floatValue()));
                        //Gui.drawRect((int) (sr.getScaledWidth() - positionX + 87 - length - 2 + 1), y, (int) (sr.getScaledWidth() - positionX + 3), y + 9 + 90, color);
                        //new Color(30, 30, 30).getRGB());
                    }
                    if (hud.prop_customfont.getValue())
                        if (!hud.shadowfont_enabled.getValue()) {
                            Fonts.Arial.drawString(name, (float) (sr.getScaledWidth() - positionX + m.getAnimation() - Fonts.Arial.getStringWidth(name)), y + 1, color); //with shadow???
                        } else {
                            Fonts.Arial.drawStringWithShadow(name, (float) (sr.getScaledWidth() - positionX + m.getAnimation() - Fonts.Arial.getStringWidth(name)), y + 1, color);
                        }
                    else if (!hud.shadowfont_enabled.getValue()) {
                        Fonts.Arial.drawString(name, (float) (sr.getScaledWidth() - positionX + m.getAnimation() - Fonts.Arial.getStringWidth(name)), y + 1, color);
                    } else {
                        Fonts.Arial.drawStringWithShadow(name, (float) (sr.getScaledWidth() - positionX + m.getAnimation() - Fonts.Arial.getStringWidth(name)), y + 1, color);
                    }
                    y += Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 2;
                } else if (Hud.prop_theme.getValue().get("Virtue")) {
                    if (m.getAnimation() > 0) m.setAnimation(m.getAnimation() - 1);
                    if (hud.prop_arraylistmode.getValue().get("Helium")) {
                        double length = Fonts.Arial.getStringWidth(name);
                    }
                    mc.fontRendererObj.drawStringWithShadow(name, (float) (sr.getScaledWidth() - positionX + m.getAnimation() - mc.fontRendererObj.getStringWidth(name)), y, new Color(255,255,255).getRGB());
                    y += Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 1;
                    }
            }
        }
    }

    

    private ArrayList<Cheat> getToggledCheats() {
        ArrayList<Cheat> toggledCheats = new ArrayList<>();
        for (Cheat cheat : Helium.instance.cheatManager.getCheatRegistry().values()) {
            if (cheat.getState()) {
                toggledCheats.add(cheat);
            }
        }
        return toggledCheats;
    }

    public java.util.ArrayList<Cheat> getToggledModulesSortedLTS() {
        java.util.ArrayList<Cheat> toggledCheats = getToggledCheats();
        toggledCheats.sort((cheat1, cheat2) -> (hud.prop_customfont.getValue() ? Fonts.Arial.getStringWidth(cheat2.getId() + (cheat2.getMode() != null ? " " + cheat2.getMode() : "")) : Fonts.Arial.getStringWidth(cheat2.getId() + (cheat2.getMode() != null ? " " + cheat2.getMode() : ""))) - (hud.prop_customfont.getValue() ? Fonts.Arial.getStringWidth(cheat1.getId() + (cheat1.getMode() != null ? " " + cheat1.getMode() : "")) : Fonts.Arial.getStringWidth(cheat1.getId() + (cheat1.getMode() != null ? " " + cheat1.getMode() : ""))));
        return toggledCheats;
    }

    public double getLongestToggledLabelLength() {
        double longestLabelLength = 0;
        for (Cheat cheat : getToggledCheats()) {
            final double labelLength = Fonts.Arial.getStringWidth(cheat.getId());
            if (labelLength > longestLabelLength) {
                longestLabelLength = labelLength;
            }
        }
        return longestLabelLength;
    }

}
