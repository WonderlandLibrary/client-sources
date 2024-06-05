package koks.modules.impl.player;

import koks.event.Event;
import koks.event.impl.EventUpdate;
import koks.modules.Module;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C03PacketPlayer;

/**
 * @author avox | lmao | kroko
 * @created on 02.09.2020 : 22:38
 */
public class FastConsume extends Module {

    public FastConsume() {
        super("FastConsume", "You eating very fast", Category.PLAYER);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            if (mc.thePlayer.getCurrentEquippedItem().getItem() != null && mc.thePlayer.isUsingItem() && !(mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) && !(mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow) && !(mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemFishFood)) {
                for (int i = 0; i < 15; i++) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
                }
            }
        }
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

}