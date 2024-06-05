package gq.vapu.czfclient.Module.Modules.Render;

import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Xray extends Module {
    public List<Integer> blocks = new ArrayList<Integer>();

    public Xray() {
        super("Xray", new String[]{"xrai", "oreesp"}, ModuleType.Render);
        this.setColor(Color.GREEN.getRGB());
        this.blocks.add(16);
        this.blocks.add(56);
        this.blocks.add(14);
        this.blocks.add(15);
        this.blocks.add(129);
        this.blocks.add(73);
    }

    @Override
    public void onEnable() {
        mc.renderGlobal.loadRenderers();
    }

    @Override
    public void onDisable() {
        mc.renderGlobal.loadRenderers();
    }

    public List<Integer> getBlocks() {
        return this.blocks;
    }
}
