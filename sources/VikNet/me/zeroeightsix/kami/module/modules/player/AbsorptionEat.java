package me.zeroeightsix.kami.module.modules.player;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemStack;
/**
 * Created by 086 on 8/04/2018. made better by cryroByte
 * thanks krts for helping because i am legit brain dead - cryrobyte
 * Updated by Viktisen 26th april 2020 (now eats based on absorption instead of health and fixed some bugs)
 * See VikNetAura onEnable and onDisable for more info
 */

@Module.Info(name = "AbsorptionEat", description = "eats based on absorption points", category = Module.Category.PLAYER)
public class AbsorptionEat extends Module {

    private Setting ABSORPTION = this.register(Settings.integerBuilder("ABSORPTION").withMinimum(4).withMaximum(32).withValue(20).build());
    private boolean eating = false;

    private boolean isValid(ItemStack stack, int food) {
        return stack.getItem() instanceof ItemAppleGold && ((int) this.ABSORPTION.getValue() - food) >= ((ItemAppleGold) stack.getItem()).getHealAmount(stack);
    }

    @Override
    public void onUpdate() {
        if (eating && !mc.player.isHandActive()) {
            eating = false;
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
            return;
        }
        if (eating) return;
        float playerAbsorption = mc.player.getAbsorptionAmount();
        for (int i = 0; i < 9; i++) {
            if (isValid(mc.player.inventory.getStackInSlot(i), (int) mc.player.getAbsorptionAmount() + (int) playerAbsorption)) { /*stupid way i did it ik ik ik*/
                if (mc.player.getHeldItemMainhand().getItem() == Items.GOLDEN_APPLE) {
                    eating = true;
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
                    mc.rightClickMouse();
                    return;
                }
            }
        }
    }
}




