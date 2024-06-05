package gq.vapu.czfclient.Module.Modules.Render;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.Render.EventPostRenderPlayer;
import gq.vapu.czfclient.API.Events.Render.EventPreRenderPlayer;
import gq.vapu.czfclient.API.Value.Mode;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Chams extends Module {
    public Mode<Enum> mode = new Mode("Mode", "Mode", ChamsMode.values(), ChamsMode.Normal);

    public Chams() {
        super("Chams", new String[]{"seethru", "cham"}, ModuleType.Render);
        this.addValues(this.mode);
        this.setColor(new Color(159, 190, 192).getRGB());
    }

    @EventHandler
    private void preRenderPlayer(EventPreRenderPlayer e) {
        GL11.glEnable(32823);
        GL11.glPolygonOffset(1.0f, -1100000.0f);
    }

    @EventHandler
    private void postRenderPlayer(EventPostRenderPlayer e) {
        GL11.glDisable(32823);
        GL11.glPolygonOffset(1.0f, 1100000.0f);
    }

    public enum ChamsMode {
        Normal
    }

}
