package gq.vapu.czfclient.Module.Modules.Misc;

import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;

public class MemoryFix extends Module {
    private int width;

    public MemoryFix() {
        super("MemoryFix", new String[]{"MemoryFix"}, ModuleType.Player);
    }

    @Override
    public void onEnable() {
        Runtime.getRuntime().gc();
        super.onEnable();
    }
}
