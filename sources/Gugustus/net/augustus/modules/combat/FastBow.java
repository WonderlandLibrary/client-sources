package net.augustus.modules.combat;

import net.augustus.events.EventUpdate;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import java.awt.*;

public class FastBow extends Module {
    public FastBow() {
        super("FastBow", Color.ORANGE, Categorys.COMBAT);
    }
    @EventTarget
    public void onUpdate(EventUpdate event) {
        if
        (
                mc.thePlayer.getHealth() > 0
                        && (mc.thePlayer.onGround || mc.thePlayer.capabilities.isCreativeMode)
                        && mc.thePlayer.inventory.getCurrentItem() != null
                        && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBow
                        && mc.gameSettings.keyBindUseItem.pressed
        )
        {
            mc.playerController.sendUseItem
                    (
                            mc.thePlayer,
                            mc.theWorld,
                            mc.thePlayer.inventory.getCurrentItem()
                    );
            mc.thePlayer.inventory.getCurrentItem().getItem().onItemRightClick
                    (
                            mc.thePlayer.inventory.getCurrentItem(),
                            mc.theWorld,
                            mc.thePlayer
                    );
            for(int i = 0; i < 20; i++)
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(false));
            mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            mc.thePlayer.inventory.getCurrentItem().getItem().onPlayerStoppedUsing
                    (
                            mc.thePlayer.inventory.getCurrentItem(),
                            mc.theWorld,
                            mc.thePlayer,
                            10
                    );
        }

    }
}
