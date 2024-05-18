package me.valk.overlay.tabGui.parts;

import me.valk.module.ModMode;
import me.valk.module.Module;
import me.valk.overlay.tabGui.TabPart;

public class TabModePart extends TabPart
{
    private Module module;
    private ModMode mode;
    
    public TabModePart(final Module module, final ModMode mode, final TabPanel parent) {
        super(mode.getName(), parent);
        this.module = module;
        this.mode = mode;
    }
    
    @Override
    public void onKeyPress(final int key) {
        if (key == 28) {
            this.module.setMode(this.mode);
        }
    }
    
    public Module getModule() {
        return this.module;
    }
    
    public ModMode getMode() {
        return this.mode;
    }
}
