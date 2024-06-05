package Ascii4UwUWareClient.Module.Modules.Render;

import java.awt.Color;


import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.Render.EventRender2D;
import Ascii4UwUWareClient.API.Value.Numbers;
import Ascii4UwUWareClient.API.Value.Option;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;
import Ascii4UwUWareClient.Util.Render.Colors;
import Ascii4UwUWareClient.Util.Render.RenderUtil;
import net.minecraft.client.gui.ScaledResolution;


public class Crosshair
        extends Module {
    private boolean dragging;
    float hue;
    private Option<Boolean> DYNAMIC = new Option<Boolean>("DYNAMIC", "DYNAMIC", true);
    public static Numbers<Double> GAP = new Numbers<Double>("gap", "gap", 5.0, 0.25, 15.0, 0.25);
    private Numbers<Double> WIDTH = new Numbers<Double>("width", "width", 2.0, 0.25, 10.0, 0.25);
    public static Numbers<Double> SIZE = new Numbers<Double>("size", "size", 7.0, 0.25, 15.0, 0.25);

    public Crosshair() {
        super("Crosshair", new String[]{"Crosshair"}, ModuleType.Render);
        this.addValues(this.DYNAMIC, this.GAP, this.WIDTH, this.SIZE);
    }

    @EventHandler
    public void onGui(EventRender2D e) {
        int red = 255;
        int green = 255;
        int blue = 255;
        int alph = 255;
        double gap = ((Double) this.GAP.getValue()).doubleValue();
        double width = ((Double) this.WIDTH.getValue()).doubleValue();
        double size = ((Double) this.SIZE.getValue()).doubleValue();
        ScaledResolution scaledRes = new ScaledResolution(mc);
        RenderUtil.rectangleBordered(
                scaledRes.getScaledWidth() / 2 - width,
                scaledRes.getScaledHeight() / 2 - gap - size - (isMoving() ? 2 : 0),
                scaledRes.getScaledWidth() / 2 + 1.0f + width,
                scaledRes.getScaledHeight() / 2 - gap - (isMoving() ? 2 : 0), 0.5f, Colors.getColor(red, green, blue, alph),
                new Color(0, 0, 0, alph).getRGB());
        RenderUtil.rectangleBordered(
                scaledRes.getScaledWidth() / 2 - width,
                scaledRes.getScaledHeight() / 2 + gap + 1 + (isMoving() ? 2 : 0) - 0.15,
                scaledRes.getScaledWidth() / 2 + 1.0f + width,
                scaledRes.getScaledHeight() / 2 + 1 + gap + size + (isMoving() ? 2 : 0) - 0.15, 0.5f, Colors.getColor(red, green, blue, alph),
                new Color(0, 0, 0, alph).getRGB());
        RenderUtil.rectangleBordered(
                scaledRes.getScaledWidth() / 2 - gap - size - (isMoving() ? 2 : 0) + 0.15,
                scaledRes.getScaledHeight() / 2 - width,
                scaledRes.getScaledWidth() / 2 - gap - (isMoving() ? 2 : 0) + 0.15,
                scaledRes.getScaledHeight() / 2 + 1.0f + width, 0.5f, Colors.getColor(red, green, blue, alph),
                new Color(0, 0, 0, alph).getRGB());
        RenderUtil.rectangleBordered(
                scaledRes.getScaledWidth() / 2 + 1 + gap + (isMoving() ? 2 : 0),
                scaledRes.getScaledHeight() / 2 - width,
                scaledRes.getScaledWidth() / 2 + size + gap + 1 + (isMoving() ? 2 : 0),
                scaledRes.getScaledHeight() / 2 + 1.0f + width, 0.5f, Colors.getColor(red, green, blue, alph),
                new Color(0, 0, 0, alph).getRGB());
    }

    public boolean isMoving() {
        return DYNAMIC.getValue() && (!mc.thePlayer.isCollidedHorizontally) && (!mc.thePlayer.isSneaking()) && ((mc.thePlayer.movementInput.moveForward != 0.0F) || (mc.thePlayer.movementInput.moveStrafe != 0.0F));
    }

}
