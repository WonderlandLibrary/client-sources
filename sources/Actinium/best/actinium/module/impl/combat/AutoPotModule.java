package best.actinium.module.impl.combat;

import best.actinium.component.componets.RotationComponent;
import best.actinium.event.api.Callback;
import best.actinium.event.impl.game.UpdateEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.property.impl.NumberProperty;
import best.actinium.util.player.PlayerUtil;
import best.actinium.util.render.ChatUtil;
import net.minecraft.init.Items;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import org.lwjglx.util.vector.Vector2f;

@ModuleInfo(name = "Auto Pot",description = "Idk",category = ModuleCategory.COMBAT)
public class AutoPotModule extends Module {
    private NumberProperty health = new NumberProperty("Health",this,1,1,20,1);

    @Callback
    public void onUpdate(UpdateEvent event) {

        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);
            if (mc.thePlayer.getHealth() < health.getValue() && stack != null && stack.getItem() ==
                    Items.potionitem && ItemPotion.isSplash(stack.getMetadata())) {

                ChatUtil.display("Splash");
                mc.thePlayer.inventory.currentItem = i;
                RotationComponent.setRotations(new Vector2f(mc.thePlayer.rotationYaw - 180, 90));
                PlayerUtil.sendClick(1, true);
            }
        }
    }

    private void splash() {

    }
}
