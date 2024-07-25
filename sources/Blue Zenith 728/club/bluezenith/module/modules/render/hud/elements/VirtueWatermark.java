package club.bluezenith.module.modules.render.hud.elements;

import club.bluezenith.module.modules.render.hud.HUD;
import club.bluezenith.util.math.MathUtil;
import club.bluezenith.util.render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static club.bluezenith.util.MinecraftInstance.mc;

public class VirtueWatermark  {

    private final DateFormat dateFormat = new SimpleDateFormat("hh:mm a");

    public void draw() {
        final FontRenderer font = mc.fontRendererObj;

        String formatted = dateFormat.format(new Date());
        if(formatted.startsWith("0"))
            formatted = formatted.replaceFirst("0", "");

        font.drawString(String.format("Virtue §7(%s)", formatted), 2, 2, -1, true);

        float y = 3 + font.FONT_HEIGHT;

        final String[] categories = {
                "Combat", "Render", "Movement", "Player", "World"
        };

        for (String value : categories) {
            RenderUtil.rect(2, y, 60, y + 15, 150 << 24);

            if(value.equals("Combat")) {
                RenderUtil.rect(3, y + 1, 59, y + 14, 255 << 24 | 230 << 16 | 230 << 8 | 230);

            }

            font.drawString(
                    (!value.equals("Combat") ? "§7" : "") + value,
                    2 + (29 - font.getStringWidthF(value) / 2F),
                    y + 7.5F - font.FONT_HEIGHT / 2F,
                    -1,
                    value.equals("Combat")
            );
            y += 15;
        }

        final String[] elements = new String[3];

        if(HUD.elements.getOptionState("FPS"))
            elements[0] = "§7FPS: §f" + Minecraft.getDebugFPS();
        if(HUD.elements.getOptionState("Speed"))
            elements[1] = "§7BPS: §f" + MathUtil.round(HUD.getBPS(), 2);
        if(HUD.elements.getOptionState("Coords"))
            elements[2] ="§7XYZ: §f" + Math.round(mc.thePlayer.posX) + ", " + Math.round(mc.thePlayer.posY) + ", " + Math.round(mc.thePlayer.posZ);

        y += 2;

        for (String element : elements) {
            if(element == null) continue;
            font.drawString(element, 2, y, -1, true);
            y += 1 + font.FONT_HEIGHT;
        }
    }

}
