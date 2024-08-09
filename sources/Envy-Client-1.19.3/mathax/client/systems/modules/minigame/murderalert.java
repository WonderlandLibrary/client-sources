package mathax.client.systems.modules.minigame;

import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.List;

public class murderalert extends Module {

    public murderalert() {
        super(Categories.Experimental, Items.DIAMOND_BOOTS, "Murder-Alert", "Alert's When A Player Shows an Iron Sword");
    }

    public void onTick() {
        MinecraftClient mc = MinecraftClient.getInstance();
        List<AbstractClientPlayerEntity> players = mc.world.getPlayers();

        for (PlayerEntity player : players) {
            ItemStack heldItem = player.getInventory().getMainHandStack();
            if (heldItem.getItem() == Items.DIAMOND) {
                //player.sendMessage(Text.of("You are holding a diamond!", toString(player.getName()));
            }
        }
    }

    //private Object toString(Text name) {
    //}
}

