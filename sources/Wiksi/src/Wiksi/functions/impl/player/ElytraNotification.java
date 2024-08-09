package src.Wiksi.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import java.util.Iterator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;

@FunctionRegister(name = "ElytraNotification", type = Category.Player)
public class ElytraNotification extends Function {
    private boolean elytraPresent = true;

    public ElytraNotification() {
    }

    @Subscribe
    private void onUpdate(EventUpdate e) {
        int elytraCount = countItems(Items.ELYTRA);
        elytraCount += countItemsInOffhand(Items.ELYTRA);
        if (elytraCount == 0 && elytraPresent) {
            print("Античит выбросил ваши элитры!");
            elytraPresent = false;
        } else if (elytraCount > 0) {
            elytraPresent = true;
        }

    }

    private int countItems(Item item) {
        int count = 0;
        Iterator was = mc.player.inventory.mainInventory.iterator();

        while(was.hasNext()) {
            ItemStack stack = (ItemStack)was.next();
            if (stack.getItem() == item) {
                count += stack.getCount();
            }
        }

        return count;
    }

    private int countItemsInOffhand(Item item) {
        ItemStack offhandStack = mc.player.getHeldItemOffhand();
        return offhandStack.getItem() == item ? offhandStack.getCount() : 0;
    }
}
