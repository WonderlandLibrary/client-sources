package xyz.gucciclient.modules.mods.render;

import xyz.gucciclient.modules.*;
import xyz.gucciclient.utils.*;

public class Fullbright extends Module
{
    private float gamma;
    
    public Fullbright() {
        super(Modules.Fullbright.name(), 0, Category.RENDER);
    }
    
    @Override
    public void onEnable() {
        this.gamma = Wrapper.getMinecraft().gameSettings.gammaSetting;
        Wrapper.getMinecraft().gameSettings.gammaSetting = 1000.0f;
    }
    
    @Override
    public void onDisable() {
        Wrapper.getMinecraft().gameSettings.gammaSetting = this.gamma;
    }
}
