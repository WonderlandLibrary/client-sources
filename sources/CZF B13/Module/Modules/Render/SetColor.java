package gq.vapu.czfclient.Module.Modules.Render;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.World.EventPreUpdate;
import gq.vapu.czfclient.API.Value.Numbers;
import gq.vapu.czfclient.API.Value.Value;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;

public class SetColor
        extends Module {
    public static Numbers<Double> r = new Numbers("Red", "Red", 255.0, 0.0, 255.0, 1.0);
    public static Numbers<Double> g = new Numbers("Green", "Green", 255.0, 0.0, 255.0, 1.0);
    public static Numbers<Double> b = new Numbers("Blue", "Blue", 255.0, 0.0, 255.0, 1.0);
    public static Numbers<Double> a = new Numbers("Alpha", "Alpha", 255.0, 0.0, 255.0, 1.0);

    public SetColor() {
        super("CustomColor", new String[]{"SetColor"}, ModuleType.Render);
        this.addValues(r, g, b, a);
    }

    @EventHandler
    private void onUpdate(EventPreUpdate e) {
        this.setEnabled(false);
    }
}
