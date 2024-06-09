package me.travis.wurstplus.module.modules.render;

import me.travis.wurstplus.setting.Settings;
import me.travis.wurstplus.setting.Setting;
import me.travis.wurstplus.util.Invdraw;

import me.travis.wurstplus.module.Module;

@Module.Info(name = "Show INV", category = Module.Category.RENDER)
public class InvPreview extends Module
{
    private Setting<Integer> xSetting;
    private Setting<Integer> ySetting;
    
    public InvPreview() {
        this.xSetting = register(Settings.i("X Coord", 784));
        this.ySetting = register(Settings.i("Y Coord", 46));
    }

    public void onRender()
    {
        if(!this.isEnabled()) return;
       
        Invdraw i = new Invdraw();
        i.drawInventory(xSetting.getValue(), ySetting.getValue(), mc.player);
    }
}
