package best.actinium.module.impl.ghost;

import best.actinium.event.api.Callback;
import best.actinium.event.impl.game.TickEvent;
import best.actinium.event.impl.input.ClickEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.property.impl.BooleanProperty;
import best.actinium.property.impl.NumberProperty;
import best.actinium.util.io.TimerUtil;
import best.actinium.util.player.PlayerUtil;
import best.actinium.util.render.ChatUtil;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;

@ModuleInfo(
        name = "Auto Refill",
        description = "I wonder",
        category = ModuleCategory.GHOST
)
public class AutoRefillModule extends Module {
    private NumberProperty delay = new NumberProperty("Delay",this,0,100,1000,1);
    private BooleanProperty invOpen = new BooleanProperty("Inventory Open",this,true);
    private TimerUtil timer = new TimerUtil();

    public static void refill() {
        for(int i = 9; i < 37; ++i) {
            ItemStack itemstack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();

            if (itemstack != null && itemstack.getItem() == Items.potionitem && ItemPotion.isSplash(itemstack.getMetadata())) {
                mc.playerController.windowClick(0, i, 0, 1, mc.thePlayer);
                break;
            }
        }

    }

    @Callback
    public void onClick(ClickEvent event) {
        for (ItemStack itemStack : mc.thePlayer.inventory.mainInventory) {
            if ((!this.invOpen.isEnabled() || mc.currentScreen instanceof GuiInventory) && !PlayerUtil.isHotbarFull() && itemStack != null &&
                    itemStack.getItem() == Item.getItemById(373) && timer.finished(this.delay.getValue().longValue())) {

                refill();
                timer.reset();
            }
        }
    }
}
