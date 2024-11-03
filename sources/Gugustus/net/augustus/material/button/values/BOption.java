package net.augustus.material.button.values;

import net.augustus.events.EventClickGui;
import net.augustus.font.UnicodeFontRenderer;
import net.augustus.material.Main;
import net.augustus.material.Tab;
import net.augustus.material.button.Button;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.Setting;
import net.augustus.utils.skid.tomorrow.AnimationUtils;
import net.augustus.utils.skid.tomorrow.ColorUtils;
import net.augustus.utils.skid.tomorrow.RenderUtil;
import net.lenni0451.eventapi.manager.EventManager;

import java.awt.*;

public class BOption extends Button {
    private static UnicodeFontRenderer arial18;
    static {
        try {
            arial18 = new UnicodeFontRenderer(Font.createFont(0, BOption.class.getResourceAsStream("/ressources/arial.ttf")).deriveFont(18F));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    AnimationUtils au = new AnimationUtils();

    public BOption(float x, float y, Setting v, Tab moduleTab) {
        super(x, y, v,moduleTab);
    }

    @Override
    public void draw(float mouseX, float mouseY) {
        super.draw(mouseX, mouseY);
        arial18.drawString(v.getName(),x+30,y+2,new Color(255, 255, 255).getRGB());
        if (((BooleanValue)v).getBoolean()) {
            RenderUtil.drawRoundedRect(x, y + 1, x + 20, y + 9, 2, ColorUtils.lighter(Main.clientColor, 0.3F));
            animation = au.animate(20, animation, 0.05f);
            if (Main.isHovered(x, y + 1, x + 20, y + 9, mouseX, mouseY)) {
                RenderUtil.drawCircle(x + animation - 3.5f, y + 5, 9, ColorUtils.reAlpha(Main.clientColor.getRGB(), 0.1f));
            }
//            if(animation != 20) {
//                RenderUtil.drawCircle(x + animation - 3.5f, y + 5, animation / 2, ColorUtils.reAlpha(Main.clientColor.getRGB(), 0.5f));
//            }
            RenderUtil.drawCircle(x + animation - 3, y + 5, 6, new Color(240, 240, 240).getRGB());

        } else {
            RenderUtil.drawRoundedRect(x, y + 1, x + 20, y + 9, 2, new Color(180, 180, 180));
            animation = au.animate(0, animation, 0.05f);
            if (Main.isHovered(x, y, x + 20, y + 10, mouseX, mouseY)) {
                RenderUtil.drawCircle(x + animation - 1.5f, y + 5, 9, new Color(0, 0, 0, 30).getRGB());
            }
            RenderUtil.drawCircle(x + animation - 1, y + 5, 6, new Color(240, 240, 240).getRGB());
        }
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY) {
        super.mouseClicked(mouseX, mouseY);
        if (Main.isHovered(x, y, x + 20, y + 10, mouseX, mouseY)) {
            ((BooleanValue)v).setBoolean(!((boolean) ((BooleanValue)v).getBoolean()));
            EventManager.call(new EventClickGui());
        }
    }
}
