package dev.darkmoon.client.utility.math;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

@AllArgsConstructor
public class ScaleMath {
    @Getter
    @Setter
    private int scale;

    public void pushScale(){
        Minecraft.getMinecraft().entityRenderer.setupOverlayRendering(2);
    }

    public void popScale(){
        Minecraft.getMinecraft().entityRenderer.setupOverlayRendering();
    }

    public int calc(int value) {
        ScaledResolution rs = new ScaledResolution(Minecraft.getMinecraft());
        return value * rs.getScaleFactor() / this.scale;
    }
    public double calc(double value) {
        ScaledResolution rs = new ScaledResolution(Minecraft.getMinecraft());
        return value * rs.getScaleFactor() / this.scale;
    }
    public float calc(float value) {
        ScaledResolution rs = new ScaledResolution(Minecraft.getMinecraft());
        return value * rs.getScaleFactor() / this.scale;
    }

    public Vec2i getMouse(int mouseX, int mouseY, ScaledResolution rs){
        return new Vec2i(mouseX * rs.getScaleFactor() / this.scale, mouseY * rs.getScaleFactor() / this.scale);
    }
}