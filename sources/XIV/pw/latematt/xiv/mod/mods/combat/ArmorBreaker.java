package pw.latematt.xiv.mod.mods.combat;

import net.minecraft.item.*;
import org.lwjgl.input.Keyboard;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.AttackEntityEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;
import pw.latematt.xiv.mod.mods.player.AutoHeal;
import pw.latematt.xiv.utils.InventoryUtils;

/**
 * @author Jack
 */
public class ArmorBreaker extends Mod implements Listener<AttackEntityEvent> {
    public ArmorBreaker() {
        super("ArmorBreaker", ModType.COMBAT, Keyboard.KEY_NONE, 0xFF808080);
    }

    @Override
    public void onEventCalled(AttackEntityEvent event) {
        AutoHeal autoHeal = (AutoHeal) XIV.getInstance().getModManager().find("autoheal");
        if (autoHeal != null && autoHeal.isHealing())
            return;

        ItemStack current = mc.thePlayer.getCurrentEquippedItem();
        ItemStack toSwitch = mc.thePlayer.inventoryContainer.getSlot(27).getStack();
        if (current != null && toSwitch != null)
            if (current.getItem() instanceof ItemSword || current.getItem() instanceof ItemAxe || current.getItem() instanceof ItemPickaxe || current.getItem() instanceof ItemSpade)
                if (toSwitch.getItem() instanceof ItemSword || toSwitch.getItem() instanceof ItemAxe || toSwitch.getItem() instanceof ItemPickaxe || toSwitch.getItem() instanceof ItemSpade)
                    mc.playerController.windowClick(0, 27, mc.thePlayer.inventory.currentItem, 2, mc.thePlayer);
    }

    @Override
    public void onEnabled() {
        XIV.getInstance().getListenerManager().add(this);
    }

    @Override
    public void onDisabled() {
        XIV.getInstance().getListenerManager().remove(this);
    }
}