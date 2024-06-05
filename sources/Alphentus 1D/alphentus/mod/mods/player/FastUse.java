package alphentus.mod.mods.player;

import alphentus.event.Event;
import alphentus.event.Type;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Keyboard;

/**
 * @author avox | lmao
 * @since on 30.07.2020.
 */
public class FastUse extends Mod {

    public FastUse() {
        super("FastUse", Keyboard.KEY_NONE, true, ModCategory.PLAYER);
    }

    @Override
    public void onEnable () {
        super.onEnable();
    }

    @Override
    public void onDisable () {
        super.onDisable();
    }

    @EventTarget
    public void event(Event event) {
        if (event.getType() != Type.RENDER2D)
            return;
        if (!getState())
            return;

        if (mc.thePlayer.isUsingItem() && !(mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) && !(mc.thePlayer.getHeldItem().getItem() instanceof ItemBow) && !(mc.thePlayer.getHeldItem().getItem() instanceof ItemFishingRod)) {
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
            mc.thePlayer.clearItemInUse();
        }
    }
}