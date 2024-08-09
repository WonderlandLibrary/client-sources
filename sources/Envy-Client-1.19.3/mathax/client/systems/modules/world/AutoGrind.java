package mathax.client.systems.modules.world;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.game.OpenScreenEvent;
import mathax.client.settings.*;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.GrindstoneScreenHandler;

import java.util.List;
import java.util.Map;

public class AutoGrind extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Integer> delay = sgGeneral.add(new IntSetting.Builder()
        .name("delay")
        .description("The tick delay between grinding items.")
        .defaultValue(50)
        .sliderMax(500)
        .min(0)
        .build()
    );

    private final Setting<List<Item>> itemBlacklist = sgGeneral.add(new ItemListSetting.Builder()
        .name("item-blacklist")
        .description("Items that should be ignored.")
        .defaultValue()
        .filter(Item::isDamageable)
        .build()
    );

    private final Setting<List<Enchantment>> enchantmentBlacklist = sgGeneral.add(new EnchantmentListSetting.Builder()
        .name("enchantment-blacklist")
        .description("Enchantments that should be ignored.")
        .defaultValue()
        .build()
    );

    public AutoGrind() {
        super(Categories.World, Items.BOOK, "auto-grind", "Automatically disenchants items.");
    }

    @EventHandler
    private void onOpenScreen(OpenScreenEvent event) {
        if (!(mc.player.currentScreenHandler instanceof GrindstoneScreenHandler))
            return;

    }

    private boolean canGrind(ItemStack stack) { //but i cant grind?
        if (itemBlacklist.get().contains(stack.getItem())) return false;

        Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(stack);
        int availEnchs = 0;

        for (Enchantment enchantment : enchantments.keySet()) {
            availEnchs++;
            if (enchantment.isCursed())
                availEnchs--;
            if (enchantmentBlacklist.get().contains(enchantment))
                return false;
        }

        return enchantments.size() > 0 && availEnchs > 0;
    }
}
