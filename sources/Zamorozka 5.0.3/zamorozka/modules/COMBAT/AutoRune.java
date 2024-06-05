package zamorozka.modules.COMBAT;

import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemAir;
import net.minecraft.item.ItemStack;
import zamorozka.event.EventTarget;
import zamorozka.event.events.MouseAttackEvent;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.TimerHelper;

public class AutoRune extends Module {

    private ItemStack rune;
    
    private TimerHelper timer;
	
	public AutoRune() {
		super("AutoRune", 0, Category.COMBAT);
	}
	
	@EventTarget
	public void onMouseAttack(MouseAttackEvent event) {
        if (this.mc.player.getHeldItemOffhand().getItem() != Items.FIREWORK_CHARGE && this.rune() != -1 && (this.mc.currentScreen instanceof GuiInventory || this.mc.currentScreen == null)) {
            this.mc.playerController.windowClick(0, this.rune(), 1, ClickType.PICKUP, this.mc.player);
            this.mc.playerController.windowClick(0, 45, 1, ClickType.PICKUP, this.mc.player);
        }
	}
	
    public int rune() {
        for (int i = 0; i < 45; ++i) {
            final ItemStack itemStack = this.mc.player.inventoryContainer.getSlot(i).getStack();
            if (itemStack.getItem() == Items.FIREWORK_CHARGE) {
                return i;
            }
        }
        return -1;
    }
    
    public int runes() {
        int count = 0;
        for (int i = 0; i < this.mc.player.inventory.getSizeInventory(); ++i) {
            final ItemStack stack = this.mc.player.inventory.getStackInSlot(i);
            if (!(stack.getItem() instanceof ItemAir) && stack.getItem() == Items.FIREWORK_CHARGE) {
                ++count;
            }
        }
        return count;
    }
	
}