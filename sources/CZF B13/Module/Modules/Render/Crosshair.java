package gq.vapu.czfclient.Module.Modules.Render;


import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.Render.EventRender2D;
import gq.vapu.czfclient.API.Value.Numbers;
import gq.vapu.czfclient.API.Value.Option;
import gq.vapu.czfclient.API.Value.Value;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MovementInput;

public class Crosshair extends Module {
    public static Numbers<Double> GAP;
    public static Numbers<Double> SIZE;

    static {
        Crosshair.GAP = (Numbers<Double>) new Numbers("gap", "gap", 5.0, 0.25, 15.0, 0.25);
        Crosshair.SIZE = (Numbers<Double>) new Numbers("size", "size", 7.0, 0.25, 15.0, 0.25);
    }

    float hue;
    private boolean dragging;
    private final Option<Boolean> DYNAMIC;
    private final Numbers<Double> WIDTH;

    public Crosshair() {
        super("Crosshair", new String[]{"Crosshair"}, ModuleType.Render);
        this.DYNAMIC = (Option<Boolean>) new Option("DYNAMIC", "DYNAMIC", true);
        this.WIDTH = (Numbers<Double>) new Numbers("width", "width", 2.0, 0.25, 10.0, 0.25);
        this.addValues(this.DYNAMIC, Crosshair.GAP, this.WIDTH, Crosshair.SIZE);
    }

    @EventHandler
    public void onGui(final EventRender2D e) {

        throw new IllegalStateException("An error occurred while decompiling this method.");
    }

    public boolean isMoving() {
        if (this.DYNAMIC.getValue()) {
            final Minecraft mc = Crosshair.mc;
            if (!mc.thePlayer.isCollidedHorizontally) {
                final Minecraft mc2 = Crosshair.mc;
                if (!mc.thePlayer.isSneaking()) {
                    final Minecraft mc3 = Crosshair.mc;
                    final MovementInput movementInput = mc.thePlayer.movementInput;
                    if (MovementInput.moveForward == 0.0f) {
                        final Minecraft mc4 = Crosshair.mc;
                        final MovementInput movementInput2 = mc.thePlayer.movementInput;
                        return MovementInput.moveStrafe != 0.0f;
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
