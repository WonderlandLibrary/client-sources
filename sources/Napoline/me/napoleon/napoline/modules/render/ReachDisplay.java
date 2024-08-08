package me.napoleon.napoline.modules.render;

import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;

import me.napoleon.napoline.events.EventAttack;
import me.napoleon.napoline.events.EventRender2D;
import me.napoleon.napoline.font.FontLoaders;
import me.napoleon.napoline.junk.values.type.Num;
import me.napoleon.napoline.manager.event.EventTarget;
import me.napoleon.napoline.modules.Mod;
import me.napoleon.napoline.modules.ModCategory;
import me.napoleon.napoline.utils.render.RenderUtils;

import java.awt.*;
import java.text.DecimalFormat;

public class ReachDisplay extends Mod {
    public Num<Float> x = new Num<>("X", 1F, 0F, 100F);
    public Num<Float> y = new Num<>("Y", 1F, 0F, 100F);

    String distance = "0.00";
    float dragX = 0;
    float dragY = 0;

    public ReachDisplay() {
        super("ReachDisplay", ModCategory.Render,"Display your last hit reach");
        this.addValues(x, y);
    }

    @EventTarget
    public void onRender2D(EventRender2D e){
        ScaledResolution sr = new ScaledResolution(mc);
        float realX = ((Number) x.getValue()).floatValue() / 100 * sr.getScaledWidth();
        float realY = ((Number) y.getValue()).floatValue() / 100 * sr.getScaledHeight();

        int mouseX =  Mouse.getX() * sr.getScaledWidth() / mc.displayWidth;
        int mouseY = sr.getScaledHeight() - Mouse.getY() * sr.getScaledHeight() / mc.displayHeight - 1;
        boolean isLeftKeyDown = Mouse.isButtonDown(0);

        if (mouseX >= realX && mouseX <= realX + 25 && mouseY >= realY && mouseY <= realY + 15 && isLeftKeyDown && !Mouse.isGrabbed()) {
            if (dragX == 0 && dragY == 0) {
                dragX = mouseX - realX;
                dragY = mouseY - realY;
            } else {
                realX = mouseX - dragX;
                realY = mouseY - dragY;
            }
            x.setValue((realX / sr.getScaledWidth() * 100));
            y.setValue((realY / sr.getScaledHeight() * 100));
        } else if (dragX != 0 || dragY != 0) {
            dragX = 0;
            dragY = 0;
        }

        RenderUtils.drawRect(realX,realY,realX+25,realY+15,new Color(0,0,0,150).getRGB());
        FontLoaders.F22.drawStringWithShadow(distance,realX+1,realY+2,-1);
    }

    @EventTarget
    public void onAttack(EventAttack e){
        if(e.getTarget() != null){
            DecimalFormat df = new DecimalFormat("0.00");
            distance =df.format(mc.thePlayer.getDistanceToEntity(e.getTarget()));
        }
    }
}
