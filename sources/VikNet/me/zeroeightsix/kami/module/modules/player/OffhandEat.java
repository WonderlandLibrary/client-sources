package me.zeroeightsix.kami.module.modules.player;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.setting.Setting;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

/**
 * Created by 086 on 8/04/2018. made better by cryroByte
 * Updated by Viktisen 28 April 2020
 */
@Module.Info(name = "OffhandEat", description = "Automatically eat when low on health", category = Module.Category.PLAYER)
public class OffhandEat extends Module {

    private Setting healthbar = this.register(Settings.integerBuilder("Health Slider").withMinimum(0).withMaximum(36).withValue((int)20).build());
    private boolean eating = false;

    private boolean isValid(ItemStack stack, int food) {
        return stack.getItem() instanceof ItemFood && ((int)this.healthbar.getValue()-food)>=((ItemFood) stack.getItem()).getHealAmount(stack);

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
        if (isValid(mc.player.getHeldItemOffhand(), (int) mc.player.getHealth() + (int) playerAbsorption)) { /*stupid way i did it ik ik ik*/
            mc.player.setActiveHand(EnumHand.OFF_HAND);
            eating = true;
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
            mc.rightClickMouse();
            return;
                }
            }
        }
