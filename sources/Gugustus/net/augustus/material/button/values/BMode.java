package net.augustus.material.button.values;

import net.augustus.events.EventClickGui;
import net.augustus.font.UnicodeFontRenderer;
import net.augustus.material.Main;
import net.augustus.material.Tab;
import net.augustus.material.button.Button;
import net.augustus.settings.Setting;
import net.augustus.settings.StringValue;
import net.augustus.utils.skid.tomorrow.RenderUtil;
import net.lenni0451.eventapi.manager.EventManager;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class BMode extends Button {
    private static UnicodeFontRenderer arial18;
    private static UnicodeFontRenderer arial16;
    static {
        try {
            arial18 = new UnicodeFontRenderer(Font.createFont(0, BMode.class.getResourceAsStream("/ressources/arial.ttf")).deriveFont(18F));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            arial16 = new UnicodeFontRenderer(Font.createFont(0, BMode.class.getResourceAsStream("/ressources/arial.ttf")).deriveFont(16F));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public BMode(float x, float y, Setting v, Tab moduleTab) {
        super(x, y, v, moduleTab);
    }

    @Override
    public void draw(float mouseX, float mouseY) {
        arial16.drawString(v.getName(), x + 2 - animation / (((StringValue) v).getStringList().length * 20) * 5, y - animation / (((StringValue) v).getStringList().length * 20) * 5, new Color(255, 255, 255).getRGB());
        arial18.drawString(((StringValue) v).getSelected(), x + 5, y + 5, new Color(120, 120, 120).getRGB());
        RenderUtil.drawBorderedRect(x, y - 5, x + 65, y - 5 + animation, 0.5f, new Color(100, 100, 100).getRGB(), new Color(0, 0, 0, 0).getRGB());
        arial16.drawString("V", x + 55, y + 4, new Color(200, 200, 200).getRGB());

        float modY = y + 25;
        for (String e : ((StringValue) v).getStringList()) {
            if (e.equals(((StringValue) v).getSelected()))
                continue;
            if (modY <= y - 5 + animation) {
                arial18.drawString(e, x + 5, modY, new Color(120, 120, 120).getRGB());
//                if (Main.isHovered(x, modY - 5, x + 65, modY + 25, mouseX, mouseY) && Mouse.isButtonDown(0)) {
//                    drag = false;
//                    v.setValue(e);
//                }
            }
            modY += 20;
        }

        if (drag) {
            animation = animationUtils.animate(((StringValue) v).getStringList().length * 20, animation, 0.1f);
        } else {
            animation = animationUtils.animate(20, animation, 0.1f);
        }

        super.draw(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY) {
        super.mouseClicked(mouseX, mouseY);
        if (Main.isHovered(x, y - 5, x + 65, y + 15, mouseX, mouseY)) {
            drag = !drag;
        }

        float modY = y + 25;
        for (String e : ((StringValue) v).getStringList()) {
            if (e.equals(((StringValue) v).getSelected()))
                continue;
            if (modY <= y - 5 + animation) {
                if (Main.isHovered(x, modY, x + 65, modY + 20, mouseX, mouseY) && Mouse.isButtonDown(0)) {
                    drag = false;
                    ((StringValue) v).setString(e);
                    EventManager.call(new EventClickGui());
                }
            }
            modY += 20;
        }
    }


}
