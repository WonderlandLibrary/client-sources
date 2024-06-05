package me.mees.remix.modules.render;

import me.satisfactory.base.module.*;
import me.satisfactory.base.setting.*;
import net.minecraft.client.gui.*;

public class ClickGui extends Module
{
    public me.satisfactory.base.hero.clickgui.ClickGui clickgui;
    
    public ClickGui() {
        super("ClickGUI", 54, Category.RENDER);
        this.addSetting(new Setting("Rainbow", this, false));
        this.addSetting(new Setting("RainbowSpeed", this, 10.0, 1.0, 19.99, true, 1.0));
    }
    
    @Override
    public void onEnable() {
        if (ClickGui.mc.ingameGUI != null) {
            if (this.clickgui == null) {
                this.clickgui = new me.satisfactory.base.hero.clickgui.ClickGui();
            }
            ClickGui.mc.displayGuiScreen(this.clickgui);
        }
    }
    
    @Override
    public void onDisable() {
        super.toggle();
    }
}
