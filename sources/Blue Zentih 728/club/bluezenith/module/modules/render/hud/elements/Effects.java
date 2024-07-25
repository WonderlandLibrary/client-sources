package club.bluezenith.module.modules.render.hud.elements;

import club.bluezenith.BlueZenith;
import club.bluezenith.events.impl.Render2DEvent;
import club.bluezenith.module.modules.render.hud.HUD;
import club.bluezenith.ui.draggables.Draggable;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.PotionEffect;

import java.awt.*;
import java.util.List;

import static club.bluezenith.module.Module.player;
import static club.bluezenith.module.modules.render.hud.HUD.elements;
import static club.bluezenith.module.modules.render.hud.HUD.module;
import static club.bluezenith.util.MinecraftInstance.mc;
import static club.bluezenith.util.math.MathUtil.convertSecondsToHMmSs;
import static java.awt.Color.RGBtoHSB;
import static java.awt.Color.getHSBColor;
import static java.lang.Float.MIN_VALUE;
import static java.lang.Float.max;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;
import static net.minecraft.potion.Potion.potionTypes;
import static net.minecraft.src.Config.isCustomColors;
import static net.optifine.CustomColors.getPotionColor;

public class Effects implements Draggable {
    private float x = MIN_VALUE, y = MIN_VALUE, width, height = mc.fontRendererObj.FONT_HEIGHT;

    @Override
    public boolean shouldBeRendered() {
        return HUD.module.getState() && elements.getOptionState("Effects");
    }

    @Override
    public boolean isMouseOver(int mouseX, int mouseY) {
        return false;//checkMouseBounds(mouseX, mouseY, x, y, x + width, y + height);
    }

    private static final String[] amplifiers = new String[] { "", " II", " III", " IV", " V", " VI", " VII", " VIII", " IX", " X", " XI", " XII", " XIII" };

    private String convertAmplifierToString(final int level) {
        if(level >= amplifiers.length) return "";
        if(level == -1) return " CCLV";
        return amplifiers[level];
    }

    @Override
    public void draw(Render2DEvent e) {
        final FontRenderer font = module.font.get();

        final boolean isBuildInfo = elements.getOptionState("Build Info");
        float y = (float) e.getHeight() - (isBuildInfo ? (font.FONT_HEIGHT * 2) + 5 : font.FONT_HEIGHT + (BlueZenith.isVirtueTheme ? 4 : 3));

        final List<PotionEffect> potionEffects = player.getActivePotionEffects()
                .stream().
                sorted(comparingInt(PotionEffect::getDuration))
                .collect(toList());

        for(final PotionEffect effect : potionEffects) {
            final String amplifier = convertAmplifierToString(effect.getAmplifier()),
                    name = I18n.format(effect.getEffectName()) + amplifier + "§7: ",
                    duration = convertSecondsToHMmSs((effect.getDuration() * 50L)/1000L);

            final int normalColor = potionTypes[effect.getPotionID()].getLiquidColor();
            final Color liquidColor = new Color(isCustomColors() ? getPotionColor(effect.getPotionID(), normalColor) : normalColor);
            final Color result = getHSBColor(RGBtoHSB(liquidColor.getRed(), liquidColor.getGreen(), liquidColor.getBlue(), new float[3])[0], 0.64f, 1f); //oh god

            font.drawString(
                    name + "§7" + duration,
                    (float) e.getWidth() - font.getStringWidthF(name + duration) - 2,
                    y,
                    result.getRGB(),
                    true
            );

            y -= font.FONT_HEIGHT + 1;

            width = max(font.getStringWidthF(name + duration) + 1, width);
        }

        this.height = y - this.y;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {

    }

    @Override
    public String getIdentifier() {
        return "Effects";
    }

    @Override
    public void resetPosition() {
        x = y = MIN_VALUE;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void moveTo(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
