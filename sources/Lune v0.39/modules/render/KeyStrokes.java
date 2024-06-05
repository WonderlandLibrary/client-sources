package me.superskidder.lune.modules.render;

import java.awt.Color;

import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.events.EventRender2D;
import me.superskidder.lune.font.CFontRenderer;
import me.superskidder.lune.font.FontLoaders;
import me.superskidder.lune.manager.event.EventTarget;
import me.superskidder.lune.values.type.Num;
import net.minecraft.client.gui.Gui;

public class KeyStrokes extends Mod {


    float anima;
    float anima2;
    float anima3;
    float anima4;
    float anima5;
    float anima6;
    private Num<Double> x = new Num<Double>("X", 500.0, 1.0, 1920.0);
    private Num<Double> y = new Num<Double>("Y", 2.0, 1.0, 1080.0);
    private double rainbowTick;

    public KeyStrokes() {
        super("KeyRender", ModCategory.Render,"Just like name");
        this.addValues(this.x, this.y);
    }

    @EventTarget
    public void onGui(EventRender2D e) {
        CFontRenderer font = FontLoaders.F18;
        Color rainbow = Gui.rainbow(1, 1, 1);
        float xOffset = ((Double) this.x.getValue()).floatValue();
        float yOffset = ((Double) this.y.getValue()).floatValue();
        Gui.drawRect((double) xOffset + 26, (double) yOffset, (double) (xOffset + 51), (double) (yOffset + 25), new Color(0, 0, 0, 150).getRGB());//w
        Gui.drawRect((double) xOffset + 26, (double) yOffset + 26, (double) (xOffset + 51), (double) (yOffset + 51), new Color(0, 0, 0, 150).getRGB());//s
        Gui.drawRect((double) xOffset, (double) yOffset + 26, (double) (xOffset + 25), (double) (yOffset + 51), new Color(0, 0, 0, 150).getRGB());//a
        Gui.drawRect((double) xOffset + 52, (double) yOffset + 26, (double) (xOffset + 77), (double) (yOffset + 51), new Color(0, 0, 0, 150).getRGB());//d
        Gui.drawRect((double) xOffset + 1 + 77 / 2, (double) yOffset + 52, (double) (xOffset + 77), (double) (yOffset + 77), new Color(0, 0, 0, 150).getRGB());//LMB
        Gui.drawRect((double) xOffset, (double) yOffset + 52, (double) (xOffset + 77 / 2), (double) (yOffset + 77), new Color(0, 0, 0, 150).getRGB());//RMB
        font.drawStringWithShadow("W", xOffset + (float) 34.5, yOffset + 9, rainbow.getRGB());
        font.drawStringWithShadow("S", xOffset + (float) 36, yOffset + 35, rainbow.getRGB());
        font.drawStringWithShadow("A", xOffset + (float) 10, yOffset + 35, rainbow.getRGB());
        font.drawStringWithShadow("D", xOffset + (float) 62, yOffset + 35, rainbow.getRGB());
        font.drawStringWithShadow("LMB", xOffset + (float) 10, yOffset + 60, rainbow.getRGB());
        font.drawStringWithShadow("RMB", xOffset + (float) 50, yOffset + 60, rainbow.getRGB());
        if (++rainbowTick > 10000) {
            rainbowTick = 0;
        }
        //w
        if (mc.gameSettings.keyBindForward.pressed) {
            if (this.anima < 25) {
                this.anima = this.anima + 360f / mc.debugFPS;
            }
        } else if (this.anima > 0) {
            this.anima = this.anima - 360f / mc.debugFPS;
        }
        //s
        if (mc.gameSettings.keyBindBack.pressed) {
            if (this.anima2 < 25) {
                this.anima2 = this.anima2 + 360f / mc.debugFPS;
            }
        } else if (this.anima2 > 0) {
            this.anima2 = this.anima2 - 360f / mc.debugFPS;
        }
        //a
        if (mc.gameSettings.keyBindLeft.pressed) {
            if (this.anima3 < 25) {
                this.anima3 = this.anima3 + 360f / mc.debugFPS;
            }
        } else if (this.anima3 > 0) {
            this.anima3 = this.anima3 - 360f / mc.debugFPS;
        }
        //d
        if (mc.gameSettings.keyBindRight.pressed) {
            if (this.anima4 < 25) {
                this.anima4 = this.anima4 + 360f / mc.debugFPS;
            }
        } else if (this.anima4 > 0) {
            this.anima4 = this.anima4 - 360f / mc.debugFPS;
        }
        //LMB
        if (mc.gameSettings.keyBindUseItem.pressed) {
            if (this.anima5 < 25) {
                this.anima5 = this.anima5 + 360f / mc.debugFPS;
            }
        } else if (this.anima5 > 0) {
            this.anima5 = this.anima5 - 360f / mc.debugFPS;
        }
        //RMB
        if (mc.gameSettings.keyBindAttack.pressed) {
            if (this.anima6 < 25) {
                this.anima6 = this.anima6 + 360f / mc.debugFPS;
            }
        } else if (this.anima6 > 0) {
            this.anima6 = this.anima6 - 360f / mc.debugFPS;
        }
        Gui.drawRect((double) xOffset + 26, (double) yOffset + 25, (double) (xOffset + 51), (double) (yOffset + 25 - anima), new Color(255, 255, 255, 120).getRGB());//w
        Gui.drawRect((double) xOffset + 26, (double) yOffset + 51, (double) (xOffset + 51), (double) (yOffset + 51 - anima2), new Color(255, 255, 255, 120).getRGB());//s
        Gui.drawRect((double) xOffset, (double) yOffset + 51, (double) (xOffset + 25), (double) (yOffset + 51 - anima3), new Color(255, 255, 255, 120).getRGB());//a
        Gui.drawRect((double) xOffset + 52, (double) yOffset + 51, (double) (xOffset + 77), (double) (yOffset + 51 - anima4), new Color(255, 255, 255, 120).getRGB());//d
        Gui.drawRect((double) xOffset + 1 + 77 / 2, (double) yOffset + 77, (double) (xOffset + 77), (double) (yOffset + 77 - anima5), new Color(255, 255, 255, 120).getRGB());//LMB
        Gui.drawRect((double) xOffset, (double) yOffset + 77, (double) (xOffset + 77 / 2), (double) (yOffset + 77 - anima6), new Color(255, 255, 255, 120).getRGB());//RMB
    }

    public void onDisable() {
        this.anima = 0;
        this.anima2 = 0;
        this.anima3 = 0;
        this.anima4 = 0;
        this.anima5 = 0;
        this.anima6 = 0;
        super.onDisable();
    }

}
