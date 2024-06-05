package rip.helium.cheat.impl.combat;

import me.hippo.systems.lwjeb.annotation.Collect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import rip.helium.cheat.Cheat;
import rip.helium.cheat.CheatCategory;
import rip.helium.event.minecraft.PlayerUpdateEvent;
import rip.helium.event.minecraft.SendPacketEvent;
import rip.helium.utils.RotationUtils;
import rip.helium.utils.Stopwatch;

import java.util.ArrayList;
import java.util.List;

public class SchoolShooter extends Cheat {

    public SchoolShooter(){
        super("School Shooter", "shoots ur mom", CheatCategory.COMBAT);

    }

    Stopwatch timer = new Stopwatch();
    public boolean isShooting;

    int amount;
    boolean stop;

    @Collect
    public void packetEvent(SendPacketEvent e) {
        amount++;
    }

    @Collect
    public void updateevent(PlayerUpdateEvent event) {
        EntityPlayerSP p = Minecraft.getMinecraft().thePlayer;
        // Make sure the player has a bow in the hand and is pressing the use button.
        if (p.inventory.getCurrentItem() != null && p.inventory.getCurrentItem().getItem() instanceof ItemBow
                && Minecraft.getMinecraft().gameSettings.keyBindUseItem.isKeyDown() && p.onGround) {
            // Send use item packet.
            Minecraft.getMinecraft().playerController.sendUseItem(p, Minecraft.getMinecraft().theWorld, p.inventory.getCurrentItem());
            // Send an item right click event.
            p.inventory.getCurrentItem().getItem().onItemRightClick(p.inventory.getCurrentItem(), Minecraft.getMinecraft().theWorld, p);
            // Spam player position packets which claim that the player is not on the ground.
            for (int i = 0; i < 20; i++)
                p.sendQueue.addToSendQueue(new C03PacketPlayer(false));
            // Send a release using item packet.
            Minecraft.getMinecraft().getNetHandler()
                    .addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));
            // Send an release using item event.
            p.inventory.getCurrentItem().getItem().onPlayerStoppedUsing(p.inventory.getCurrentItem(), Minecraft.getMinecraft().theWorld, p, 10);
        }
    }

}
