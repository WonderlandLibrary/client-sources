package tk.rektsky.Module.Player;

import tk.rektsky.Module.*;
import java.util.*;
import net.minecraft.network.*;
import tk.rektsky.Event.*;
import tk.rektsky.Event.Events.*;
import net.minecraft.item.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.player.*;

public class FastEat extends Module
{
    ArrayList<Packet> packets;
    
    public FastEat() {
        super("FastEat", "Insta eat moment", Category.PLAYER);
        this.packets = new ArrayList<Packet>();
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof WorldTickEvent && this.mc.gameSettings.keyBindUseItem.pressed && this.mc.thePlayer.getHeldItem() != null && this.mc.thePlayer.getHeldItem().getItem() instanceof ItemFood) {
            for (int i = 0; i != 37; ++i) {
                this.mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer(this.mc.thePlayer.onGround));
            }
            this.mc.gameSettings.keyBindUseItem.pressed = false;
            this.mc.playerController.onStoppedUsingItem(this.mc.thePlayer);
        }
    }
}
