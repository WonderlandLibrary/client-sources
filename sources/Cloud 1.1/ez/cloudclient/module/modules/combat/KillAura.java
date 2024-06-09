package ez.cloudclient.module.modules.combat;

import ez.cloudclient.module.Module;
import ez.cloudclient.setting.settings.BooleanSetting;
import net.minecraft.item.ItemSword;

public class KillAura extends Module {
    int l_Slot = -1;

    public KillAura() {
        super("KillAura", Category.COMBAT, "Automatically attacks enemies in range.");
    }

    @Override
    public void selfSettings() {
        settings.addSetting("Auto Switch", new BooleanSetting(true));
    }

    @Override
    public void onTick() {
        if (settings.getSetting("Auto Switch", BooleanSetting.class).getValue()) {
            for (int l_I = 0; l_I < 9; ++l_I) {
                if (mc.player.inventory.getStackInSlot(l_I).getItem() instanceof ItemSword) {
                    l_Slot = l_I;
                    mc.player.inventory.currentItem = l_Slot;
                    mc.playerController.updateController();
                }
            }
        }
    }
}