package me.kaimson.melonclient.features.modules;

import me.kaimson.melonclient.features.modules.utils.*;

public class FPSModule extends DefaultModuleRenderer
{
    public FPSModule() {
        super("FPS");
    }
    
    @Override
    public Object getValue() {
        return ave.ai();
    }
}
