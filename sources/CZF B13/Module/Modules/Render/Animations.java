package gq.vapu.czfclient.Module.Modules.Render;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.World.EventPreUpdate;
import gq.vapu.czfclient.API.Value.Mode;
import gq.vapu.czfclient.API.Value.Option;
import gq.vapu.czfclient.API.Value.Value;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;

public class Animations extends Module {
    public static Mode<Enum> mode = new Mode("Mode", "mode", renderMode.values(), renderMode.Jigsaw);
    public static Option<Boolean> smooth = new Option("Smooth", "Smooth", Boolean.valueOf(false));

    public Animations() {
        super("Animations", new String[]{"BlockHitanimations"}, ModuleType.Render);
        addValues(mode, smooth);
        this.setEnabled(true);
        this.setRemoved(true);
    }

    @EventHandler
    public void OnUpdate(EventPreUpdate event) {
        this.setSuffix(mode.getValue());
    }

    public enum renderMode {
        Sigma, old, Vanilla, Luna, Jigsaw, Swang, Swank, Swong
    }
}
