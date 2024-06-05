package gq.vapu.czfclient.Module.Modules.Misc;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.World.EventPreUpdate;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import java.awt.*;

public class FastUse extends Module {

    public FastUse() {
        super("FastUse", new String[]{"fasteat", "fuse"}, ModuleType.World);
    }

    private boolean canConsume() {
        return mc.thePlayer.getCurrentEquippedItem() != null
                && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemPotion
                || mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemFood;
    }

    @EventHandler
    private void onUpdate(EventPreUpdate e) {
        this.setColor(new Color(100, 200, 200).getRGB());
        if (mc.thePlayer.onGround && mc.thePlayer.getItemInUseDuration() == 16
                && Minecraft.getMinecraft().gameSettings.keyBindUseItem.pressed
                && !(mc.thePlayer.getItemInUse().getItem() instanceof ItemBow)
                && !(mc.thePlayer.getItemInUse().getItem() instanceof ItemSword)) {
            int i = 0;
            while (i < 17) {
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(
                        mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, mc.thePlayer.onGround));
                ++i;
            }
            mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(
                    C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        }
    }
}
