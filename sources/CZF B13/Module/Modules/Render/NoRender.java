package gq.vapu.czfclient.Module.Modules.Render;

import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;

import java.awt.*;

public class NoRender extends Module {
    public NoRender() {
        super("NoRender", new String[]{"noitems"}, ModuleType.Render);
        this.setColor(new Color(166, 185, 123).getRGB());
    }
}

