package me.r.touchgrass.module.modules.combat;

import com.darkmagician6.eventapi.EventTarget;
import me.r.touchgrass.module.Category;
import me.r.touchgrass.module.Info;
import me.r.touchgrass.module.Module;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Mouse;
import me.r.touchgrass.events.EventUpdate;


/**
 * Created by r on 26/07/2021
 */

@Info(name = "FastBow", description = "Removes the bow load-up time", category = Category.Combat)
public class FastBow extends Module {

    public FastBow() {}

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (mc.thePlayer == null || mc.thePlayer.getCurrentEquippedItem() == null) return;
            if (Mouse.isButtonDown(1) && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow && mc.thePlayer.onGround) {
                for (int power = 20, i = 0; i < power; ++i) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.onGround));
                }
                mc.playerController.onStoppedUsingItem(mc.thePlayer);
            }

    }
}
