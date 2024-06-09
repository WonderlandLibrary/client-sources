package me.travis.wurstplus.module.modules.misc;

import me.travis.wurstplus.module.Module;
import me.travis.wurstplus.setting.Setting;
import me.travis.wurstplus.setting.Settings;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.item.ItemEndCrystal;

@Module.Info(name = "Tickplace", category = Module.Category.MISC)
public class Fastplace extends Module {
    
    private Setting<Boolean> xp = this.register(Settings.b("EXP & Crystal only", false));

    @Override
    public void onUpdate() {
        if (this.isDisabled() || mc.player == null) {
            return;
        }
        if (xp.getValue()) {
            if (mc.player.inventory.getCurrentItem().getItem() instanceof ItemExpBottle || mc.player.inventory.getCurrentItem().getItem() instanceof ItemEndCrystal) {
                mc.rightClickDelayTimer = 0;
            }
        } else {
            mc.rightClickDelayTimer = 0;
        }
    }
}